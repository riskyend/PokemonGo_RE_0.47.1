package com.upsight.android.analytics.internal.session;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.upsight.android.Upsight;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.configuration.UpsightConfiguration;
import com.upsight.android.analytics.event.session.UpsightSessionPauseEvent;
import com.upsight.android.analytics.event.session.UpsightSessionPauseEvent.Builder;
import com.upsight.android.analytics.event.session.UpsightSessionResumeEvent;
import com.upsight.android.analytics.event.session.UpsightSessionResumeEvent.Builder;
import com.upsight.android.analytics.event.session.UpsightSessionStartEvent;
import com.upsight.android.analytics.event.session.UpsightSessionStartEvent.Builder;
import com.upsight.android.analytics.internal.DispatcherService;
import com.upsight.android.analytics.provider.UpsightLocationTracker;
import com.upsight.android.analytics.provider.UpsightSessionContext;
import com.upsight.android.analytics.session.UpsightSessionCallbacks;
import com.upsight.android.analytics.session.UpsightSessionInfo;
import com.upsight.android.internal.util.PreferencesHelper;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import com.upsight.android.persistence.annotation.Created;
import com.upsight.android.persistence.annotation.Updated;
import java.io.IOException;

public class SessionManagerImpl
  implements SessionManager
{
  private static final String KEY_SESSION = "com.upsight.session_callbacks";
  private static final String LOG_TAG = SessionManagerImpl.class.getSimpleName();
  private static final String PREFERENCES_KEY_JSON_CONFIG = "session_manager_json_config";
  private static final String PREFERENCES_KEY_LAST_KNOWN_SESSION_TIME = "last_known_session_time";
  private Context mAppContext;
  private Session mBackgroundSession;
  private final Clock mClock;
  private ConfigParser mConfigParser;
  private Config mCurrentConfig;
  private long mLastKnownSessionTs;
  private UpsightLogger mLogger;
  private Session mSession;
  private long mStopRequestedTs;
  private UpsightContext mUpsight;
  protected UpsightSessionCallbacks mUpsightSessionCallbacks;
  
  SessionManagerImpl(Context paramContext, UpsightContext paramUpsightContext, UpsightDataStore paramUpsightDataStore, UpsightLogger paramUpsightLogger, ConfigParser paramConfigParser, Clock paramClock)
  {
    this.mLogger = paramUpsightLogger;
    this.mConfigParser = paramConfigParser;
    this.mAppContext = paramContext;
    this.mUpsight = paramUpsightContext;
    this.mClock = paramClock;
    this.mBackgroundSession = BackgroundSessionImpl.create(paramContext, new BackgroundSessionInitializer());
    this.mLastKnownSessionTs = PreferencesHelper.getLong(paramContext, "last_known_session_time", 0L);
    this.mUpsightSessionCallbacks = loadSessionHook();
    paramUpsightDataStore.subscribe(this);
    applyConfiguration(fetchCurrentConfig());
  }
  
  private boolean applyConfiguration(String paramString)
  {
    Config localConfig;
    try
    {
      localConfig = this.mConfigParser.parseConfig(paramString);
      if ((localConfig == null) || (!localConfig.isValid()))
      {
        this.mLogger.w(LOG_TAG, "New config is invalid", new Object[0]);
        return false;
      }
      if (localConfig.equals(this.mCurrentConfig))
      {
        this.mLogger.w(LOG_TAG, "New config ignored because it is equal to current config", new Object[0]);
        return true;
      }
    }
    catch (IOException paramString)
    {
      this.mLogger.e(LOG_TAG, "Failed to apply new config", new Object[] { paramString });
      return false;
    }
    PreferencesHelper.putString(this.mAppContext, "session_manager_json_config", paramString);
    this.mCurrentConfig = localConfig;
    return true;
  }
  
  private String fetchCurrentConfig()
  {
    return PreferencesHelper.getString(this.mAppContext, "session_manager_json_config", "{\"session_gap\": 120}");
  }
  
  private boolean isExpired()
  {
    return ((this.mStopRequestedTs != 0L) && (this.mClock.currentTimeSeconds() - this.mStopRequestedTs > this.mCurrentConfig.timeToNewSession)) || ((this.mSession == null) && (this.mClock.currentTimeSeconds() - this.mLastKnownSessionTs > this.mCurrentConfig.timeToNewSession));
  }
  
  private boolean isSessionNull()
  {
    return this.mSession == null;
  }
  
  /* Error */
  private UpsightSessionCallbacks loadSessionHook()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 63	com/upsight/android/analytics/internal/session/SessionManagerImpl:mUpsight	Lcom/upsight/android/UpsightContext;
    //   4: invokevirtual 175	com/upsight/android/UpsightContext:getPackageManager	()Landroid/content/pm/PackageManager;
    //   7: aload_0
    //   8: getfield 63	com/upsight/android/analytics/internal/session/SessionManagerImpl:mUpsight	Lcom/upsight/android/UpsightContext;
    //   11: invokevirtual 178	com/upsight/android/UpsightContext:getPackageName	()Ljava/lang/String;
    //   14: sipush 128
    //   17: invokevirtual 184	android/content/pm/PackageManager:getApplicationInfo	(Ljava/lang/String;I)Landroid/content/pm/ApplicationInfo;
    //   20: getfield 190	android/content/pm/ApplicationInfo:metaData	Landroid/os/Bundle;
    //   23: astore_1
    //   24: aload_1
    //   25: ifnull +98 -> 123
    //   28: aload_1
    //   29: ldc 13
    //   31: invokevirtual 195	android/os/Bundle:containsKey	(Ljava/lang/String;)Z
    //   34: ifeq +89 -> 123
    //   37: aload_1
    //   38: ldc 13
    //   40: invokevirtual 198	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   43: astore_1
    //   44: aload_1
    //   45: invokestatic 202	java/lang/Class:forName	(Ljava/lang/String;)Ljava/lang/Class;
    //   48: astore_2
    //   49: ldc -52
    //   51: aload_2
    //   52: invokevirtual 208	java/lang/Class:isAssignableFrom	(Ljava/lang/Class;)Z
    //   55: ifne +70 -> 125
    //   58: new 210	java/lang/IllegalStateException
    //   61: dup
    //   62: ldc -44
    //   64: iconst_2
    //   65: anewarray 4	java/lang/Object
    //   68: dup
    //   69: iconst_0
    //   70: aload_2
    //   71: invokevirtual 215	java/lang/Class:getName	()Ljava/lang/String;
    //   74: aastore
    //   75: dup
    //   76: iconst_1
    //   77: ldc -52
    //   79: invokevirtual 215	java/lang/Class:getName	()Ljava/lang/String;
    //   82: aastore
    //   83: invokestatic 221	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   86: invokespecial 224	java/lang/IllegalStateException:<init>	(Ljava/lang/String;)V
    //   89: athrow
    //   90: astore_2
    //   91: aload_0
    //   92: getfield 57	com/upsight/android/analytics/internal/session/SessionManagerImpl:mLogger	Lcom/upsight/android/logger/UpsightLogger;
    //   95: ldc -30
    //   97: ldc -28
    //   99: iconst_1
    //   100: anewarray 4	java/lang/Object
    //   103: dup
    //   104: iconst_0
    //   105: aload_1
    //   106: aastore
    //   107: invokestatic 221	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   110: iconst_1
    //   111: anewarray 4	java/lang/Object
    //   114: dup
    //   115: iconst_0
    //   116: aload_2
    //   117: aastore
    //   118: invokeinterface 136 4 0
    //   123: aconst_null
    //   124: areturn
    //   125: aload_2
    //   126: invokevirtual 232	java/lang/Class:newInstance	()Ljava/lang/Object;
    //   129: checkcast 204	com/upsight/android/analytics/session/UpsightSessionCallbacks
    //   132: astore_2
    //   133: aload_2
    //   134: areturn
    //   135: astore_2
    //   136: aload_0
    //   137: getfield 57	com/upsight/android/analytics/internal/session/SessionManagerImpl:mLogger	Lcom/upsight/android/logger/UpsightLogger;
    //   140: ldc -30
    //   142: ldc -22
    //   144: iconst_1
    //   145: anewarray 4	java/lang/Object
    //   148: dup
    //   149: iconst_0
    //   150: aload_1
    //   151: aastore
    //   152: invokestatic 221	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   155: iconst_1
    //   156: anewarray 4	java/lang/Object
    //   159: dup
    //   160: iconst_0
    //   161: aload_2
    //   162: aastore
    //   163: invokeinterface 136 4 0
    //   168: goto -45 -> 123
    //   171: astore_1
    //   172: aload_0
    //   173: getfield 57	com/upsight/android/analytics/internal/session/SessionManagerImpl:mLogger	Lcom/upsight/android/logger/UpsightLogger;
    //   176: ldc -30
    //   178: ldc -20
    //   180: iconst_1
    //   181: anewarray 4	java/lang/Object
    //   184: dup
    //   185: iconst_0
    //   186: aload_1
    //   187: aastore
    //   188: invokeinterface 136 4 0
    //   193: goto -70 -> 123
    //   196: astore_2
    //   197: aload_0
    //   198: getfield 57	com/upsight/android/analytics/internal/session/SessionManagerImpl:mLogger	Lcom/upsight/android/logger/UpsightLogger;
    //   201: ldc -30
    //   203: ldc -18
    //   205: iconst_1
    //   206: anewarray 4	java/lang/Object
    //   209: dup
    //   210: iconst_0
    //   211: aload_1
    //   212: aastore
    //   213: invokestatic 221	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   216: iconst_1
    //   217: anewarray 4	java/lang/Object
    //   220: dup
    //   221: iconst_0
    //   222: aload_2
    //   223: aastore
    //   224: invokeinterface 136 4 0
    //   229: goto -106 -> 123
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	232	0	this	SessionManagerImpl
    //   23	128	1	localObject	Object
    //   171	41	1	localNameNotFoundException	android.content.pm.PackageManager.NameNotFoundException
    //   48	23	2	localClass	Class
    //   90	36	2	localClassNotFoundException	ClassNotFoundException
    //   132	2	2	localUpsightSessionCallbacks	UpsightSessionCallbacks
    //   135	27	2	localIllegalAccessException	IllegalAccessException
    //   196	27	2	localInstantiationException	InstantiationException
    // Exception table:
    //   from	to	target	type
    //   44	90	90	java/lang/ClassNotFoundException
    //   125	133	90	java/lang/ClassNotFoundException
    //   44	90	135	java/lang/IllegalAccessException
    //   125	133	135	java/lang/IllegalAccessException
    //   0	24	171	android/content/pm/PackageManager$NameNotFoundException
    //   28	44	171	android/content/pm/PackageManager$NameNotFoundException
    //   44	90	171	android/content/pm/PackageManager$NameNotFoundException
    //   91	123	171	android/content/pm/PackageManager$NameNotFoundException
    //   125	133	171	android/content/pm/PackageManager$NameNotFoundException
    //   136	168	171	android/content/pm/PackageManager$NameNotFoundException
    //   197	229	171	android/content/pm/PackageManager$NameNotFoundException
    //   44	90	196	java/lang/InstantiationException
    //   125	133	196	java/lang/InstantiationException
  }
  
  private Session startSession(boolean paramBoolean1, boolean paramBoolean2, SessionInitializer paramSessionInitializer)
  {
    if (!Upsight.isEnabled(this.mUpsight)) {
      return this.mBackgroundSession;
    }
    boolean bool = SessionInitializer.Type.PUSH.equals(paramSessionInitializer.getType());
    int i;
    if (this.mStopRequestedTs != 0L)
    {
      i = 1;
      this.mStopRequestedTs = 0L;
      if ((!bool) && (!paramBoolean2)) {
        break label180;
      }
      UpsightLocationTracker.purge(this.mUpsight);
      if (this.mUpsightSessionCallbacks != null) {
        this.mUpsightSessionCallbacks.onStart(new UpsightSessionContext(this.mUpsight), SessionImpl.getLatestSessionInfo(this.mUpsight));
      }
      this.mSession = SessionImpl.incrementAndCreate(this.mAppContext, this.mClock, paramSessionInitializer);
      UpsightSessionStartEvent.createBuilder().record(this.mUpsight);
      if (this.mUpsightSessionCallbacks != null) {
        this.mUpsightSessionCallbacks.onStarted(this.mUpsight);
      }
    }
    for (;;)
    {
      this.mLastKnownSessionTs = this.mClock.currentTimeSeconds();
      PreferencesHelper.putLong(this.mAppContext, "last_known_session_time", this.mLastKnownSessionTs);
      return this.mSession;
      i = 0;
      break;
      label180:
      if (paramBoolean1)
      {
        if (this.mUpsightSessionCallbacks != null) {
          this.mUpsightSessionCallbacks.onResume(new UpsightSessionContext(this.mUpsight), SessionImpl.getLatestSessionInfo(this.mUpsight));
        }
        this.mSession = SessionImpl.create(this.mAppContext, this.mClock, paramSessionInitializer);
        UpsightSessionResumeEvent.createBuilder().record(this.mUpsight);
        if (this.mUpsightSessionCallbacks != null) {
          this.mUpsightSessionCallbacks.onResumed(this.mUpsight);
        }
      }
      else if (i != 0)
      {
        if (this.mUpsightSessionCallbacks != null) {
          this.mUpsightSessionCallbacks.onResume(new UpsightSessionContext(this.mUpsight), SessionImpl.getLatestSessionInfo(this.mUpsight));
        }
        UpsightSessionResumeEvent.createBuilder().record(this.mUpsight);
        if (this.mUpsightSessionCallbacks != null) {
          this.mUpsightSessionCallbacks.onResumed(this.mUpsight);
        }
      }
    }
  }
  
  public Session getBackgroundSession()
  {
    this.mUpsight.startService(new Intent(this.mUpsight.getApplicationContext(), DispatcherService.class));
    return this.mBackgroundSession;
  }
  
  public UpsightSessionInfo getLatestSessionInfo()
  {
    return SessionImpl.getLatestSessionInfo(this.mUpsight);
  }
  
  /* Error */
  public Session getSession()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 63	com/upsight/android/analytics/internal/session/SessionManagerImpl:mUpsight	Lcom/upsight/android/UpsightContext;
    //   6: new 322	android/content/Intent
    //   9: dup
    //   10: aload_0
    //   11: getfield 63	com/upsight/android/analytics/internal/session/SessionManagerImpl:mUpsight	Lcom/upsight/android/UpsightContext;
    //   14: invokevirtual 326	com/upsight/android/UpsightContext:getApplicationContext	()Landroid/content/Context;
    //   17: ldc_w 328
    //   20: invokespecial 331	android/content/Intent:<init>	(Landroid/content/Context;Ljava/lang/Class;)V
    //   23: invokevirtual 335	com/upsight/android/UpsightContext:startService	(Landroid/content/Intent;)Landroid/content/ComponentName;
    //   26: pop
    //   27: aload_0
    //   28: invokespecial 339	com/upsight/android/analytics/internal/session/SessionManagerImpl:isSessionNull	()Z
    //   31: istore_1
    //   32: aload_0
    //   33: invokespecial 341	com/upsight/android/analytics/internal/session/SessionManagerImpl:isExpired	()Z
    //   36: istore_2
    //   37: iload_1
    //   38: ifne +7 -> 45
    //   41: iload_2
    //   42: ifeq +21 -> 63
    //   45: aload_0
    //   46: iload_1
    //   47: iload_2
    //   48: new 343	com/upsight/android/analytics/internal/session/StandardSessionInitializer
    //   51: dup
    //   52: invokespecial 344	com/upsight/android/analytics/internal/session/StandardSessionInitializer:<init>	()V
    //   55: invokespecial 346	com/upsight/android/analytics/internal/session/SessionManagerImpl:startSession	(ZZLcom/upsight/android/analytics/internal/session/SessionInitializer;)Lcom/upsight/android/analytics/internal/session/Session;
    //   58: astore_3
    //   59: aload_0
    //   60: monitorexit
    //   61: aload_3
    //   62: areturn
    //   63: aload_0
    //   64: getfield 160	com/upsight/android/analytics/internal/session/SessionManagerImpl:mSession	Lcom/upsight/android/analytics/internal/session/Session;
    //   67: astore_3
    //   68: goto -9 -> 59
    //   71: astore_3
    //   72: aload_0
    //   73: monitorexit
    //   74: aload_3
    //   75: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	76	0	this	SessionManagerImpl
    //   31	16	1	bool1	boolean
    //   36	12	2	bool2	boolean
    //   58	10	3	localSession	Session
    //   71	4	3	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   2	37	71	finally
    //   45	59	71	finally
    //   63	68	71	finally
  }
  
  @Updated
  public void onApplicationStatusUpdated(ApplicationStatus paramApplicationStatus)
  {
    try
    {
      if (ApplicationStatus.State.BACKGROUND.equals(paramApplicationStatus.getState()))
      {
        this.mLastKnownSessionTs = this.mClock.currentTimeSeconds();
        PreferencesHelper.putLong(this.mAppContext, "last_known_session_time", this.mLastKnownSessionTs);
        UpsightSessionPauseEvent.createBuilder().record(this.mUpsight);
      }
      return;
    }
    finally
    {
      paramApplicationStatus = finally;
      throw paramApplicationStatus;
    }
  }
  
  @Created
  public void onConfigurationCreated(UpsightConfiguration paramUpsightConfiguration)
  {
    try
    {
      if ("upsight.configuration.session_manager".equals(paramUpsightConfiguration.getScope())) {
        applyConfiguration(paramUpsightConfiguration.getConfiguration());
      }
      return;
    }
    finally
    {
      paramUpsightConfiguration = finally;
      throw paramUpsightConfiguration;
    }
  }
  
  public Session startSession(@NonNull SessionInitializer paramSessionInitializer)
  {
    try
    {
      this.mUpsight.startService(new Intent(this.mUpsight.getApplicationContext(), DispatcherService.class));
      paramSessionInitializer = startSession(isSessionNull(), isExpired(), paramSessionInitializer);
      return paramSessionInitializer;
    }
    finally
    {
      paramSessionInitializer = finally;
      throw paramSessionInitializer;
    }
  }
  
  public void stopSession()
  {
    try
    {
      Session localSession = getSession();
      this.mStopRequestedTs = this.mClock.currentTimeSeconds();
      localSession.updateDuration(this.mAppContext, this.mStopRequestedTs);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static final class Config
  {
    public final long timeToNewSession;
    
    Config(long paramLong)
    {
      this.timeToNewSession = paramLong;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      do
      {
        return true;
        if ((paramObject == null) || (getClass() != paramObject.getClass())) {
          return false;
        }
      } while (((Config)paramObject).timeToNewSession == this.timeToNewSession);
      return false;
    }
    
    public boolean isValid()
    {
      return this.timeToNewSession > 0L;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/session/SessionManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */