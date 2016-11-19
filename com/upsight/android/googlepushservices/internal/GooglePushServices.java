package com.upsight.android.googlepushservices.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.UpsightException;
import com.upsight.android.analytics.event.comm.UpsightCommRegisterEvent;
import com.upsight.android.analytics.event.comm.UpsightCommRegisterEvent.Builder;
import com.upsight.android.analytics.event.comm.UpsightCommUnregisterEvent;
import com.upsight.android.analytics.event.comm.UpsightCommUnregisterEvent.Builder;
import com.upsight.android.googlepushservices.UpsightGooglePushServices.OnRegisterListener;
import com.upsight.android.googlepushservices.UpsightGooglePushServices.OnUnregisterListener;
import com.upsight.android.googlepushservices.UpsightGooglePushServicesApi;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.marketing.UpsightBillboard;
import com.upsight.android.marketing.UpsightBillboard.Handler;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import javax.inject.Singleton;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.HandlerScheduler;
import rx.functions.Action1;

@Singleton
public class GooglePushServices
  implements UpsightGooglePushServicesApi
{
  private static final String KEY_GCM = "com.upsight.gcm";
  private static final String LOG_TAG = GooglePushServices.class.getName();
  private static final String PREFERENCES_NAME = "com.upsight.android.googleadvertisingid.internal.registration";
  private static final String PROPERTY_LAST_PUSH_TOKEN_REGISTRATION_TIME = "lastPushTokenRegistrationTime";
  private static final String PROPERTY_REG_ID = "gcmRegistrationId";
  static final String PUSH_SCOPE = "com_upsight_push_scope";
  private final Scheduler mComputationScheduler;
  private UpsightLogger mLogger;
  private final Set<UpsightGooglePushServices.OnRegisterListener> mPendingRegisterListeners;
  private final Set<UpsightGooglePushServices.OnUnregisterListener> mPendingUnregisterListeners;
  private SharedPreferences mPrefs;
  private UpsightBillboard mPushBillboard;
  private PushConfigManager mPushConfigManager;
  private boolean mRegistrationIsInProgress;
  private final Handler mUiThreadHandler;
  private boolean mUnregistrationIsInProgress;
  private UpsightContext mUpsight;
  
  @Inject
  GooglePushServices(UpsightContext paramUpsightContext, PushConfigManager paramPushConfigManager)
  {
    this.mUpsight = paramUpsightContext;
    this.mPushConfigManager = paramPushConfigManager;
    this.mLogger = paramUpsightContext.getLogger();
    if (Looper.myLooper() != null) {}
    for (this.mUiThreadHandler = new Handler(Looper.myLooper());; this.mUiThreadHandler = new Handler(Looper.getMainLooper()))
    {
      this.mComputationScheduler = paramUpsightContext.getCoreComponent().subscribeOnScheduler();
      this.mRegistrationIsInProgress = false;
      this.mUnregistrationIsInProgress = false;
      this.mPendingRegisterListeners = new HashSet();
      this.mPendingUnregisterListeners = new HashSet();
      this.mPrefs = this.mUpsight.getSharedPreferences("com.upsight.android.googleadvertisingid.internal.registration", 0);
      return;
    }
  }
  
  private long getLastPushTokenRegistrationTime()
  {
    return this.mPrefs.getLong("lastPushTokenRegistrationTime", 0L);
  }
  
  private String getRegistrationId()
  {
    String str2 = this.mPrefs.getString("gcmRegistrationId", null);
    String str1 = str2;
    if (TextUtils.isEmpty(str2)) {
      str1 = null;
    }
    return str1;
  }
  
  private boolean hasPlayServices()
  {
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mUpsight);
    if (i != 0)
    {
      this.mLogger.e(LOG_TAG, "Google play service is not available: ", new Object[] { GooglePlayServicesUtil.getErrorString(i) });
      return false;
    }
    return true;
  }
  
  private boolean isRegistered()
  {
    return getRegistrationId() != null;
  }
  
  private void registerInBackground(final String paramString)
  {
    this.mRegistrationIsInProgress = true;
    Observable.create(new Observable.OnSubscribe()
    {
      public void call(Subscriber<? super String> paramAnonymousSubscriber)
      {
        try
        {
          String str = GoogleCloudMessaging.getInstance(GooglePushServices.this.mUpsight).register(new String[] { paramString });
          if (!TextUtils.isEmpty(str))
          {
            paramAnonymousSubscriber.onNext(str);
            paramAnonymousSubscriber.onCompleted();
            return;
          }
          paramAnonymousSubscriber.onError(new IOException("Invalid push token returned from GoogleCloudMessaging"));
          return;
        }
        catch (IOException localIOException)
        {
          paramAnonymousSubscriber.onError(localIOException);
        }
      }
    }).subscribeOn(this.mComputationScheduler).observeOn(HandlerScheduler.from(this.mUiThreadHandler)).subscribe(new Observer()
    {
      public void onCompleted() {}
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        synchronized (GooglePushServices.this)
        {
          HashSet localHashSet = new HashSet(GooglePushServices.this.mPendingRegisterListeners);
          GooglePushServices.this.mPendingRegisterListeners.clear();
          GooglePushServices.access$202(GooglePushServices.this, false);
          ??? = localHashSet.iterator();
          if (((Iterator)???).hasNext()) {
            ((UpsightGooglePushServices.OnRegisterListener)((Iterator)???).next()).onFailure(new UpsightException(paramAnonymousThrowable));
          }
        }
      }
      
      public void onNext(String paramAnonymousString)
      {
        synchronized (GooglePushServices.this)
        {
          GooglePushServices.this.registerPushToken(paramAnonymousString);
          HashSet localHashSet = new HashSet(GooglePushServices.this.mPendingRegisterListeners);
          GooglePushServices.this.mPendingRegisterListeners.clear();
          GooglePushServices.access$202(GooglePushServices.this, false);
          ??? = localHashSet.iterator();
          if (((Iterator)???).hasNext()) {
            ((UpsightGooglePushServices.OnRegisterListener)((Iterator)???).next()).onSuccess(paramAnonymousString);
          }
        }
      }
    });
  }
  
  private void registerPushToken(final String paramString)
  {
    try
    {
      this.mPushConfigManager.fetchCurrentConfigObservable().subscribeOn(this.mUpsight.getCoreComponent().subscribeOnScheduler()).observeOn(this.mUpsight.getCoreComponent().observeOnScheduler()).subscribe(new Action1()
      {
        public void call(PushConfigManager.Config paramAnonymousConfig)
        {
          long l = TimeUnit.SECONDS.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
          if ((!paramString.equals(GooglePushServices.this.getRegistrationId())) || (l - GooglePushServices.this.getLastPushTokenRegistrationTime() > paramAnonymousConfig.pushTokenTtl))
          {
            UpsightCommRegisterEvent.createBuilder().setToken(paramString).record(GooglePushServices.this.mUpsight);
            GooglePushServices.this.storeRegistrationInfo(paramString, l);
          }
        }
      });
      return;
    }
    catch (IOException paramString)
    {
      this.mLogger.e(LOG_TAG, "Failed to fetch push configurations", new Object[] { paramString });
    }
  }
  
  private void removeRegistrationInfo()
  {
    SharedPreferences.Editor localEditor = this.mPrefs.edit();
    localEditor.remove("gcmRegistrationId");
    localEditor.remove("lastPushTokenRegistrationTime");
    localEditor.apply();
  }
  
  private void storeRegistrationInfo(String paramString, long paramLong)
  {
    SharedPreferences.Editor localEditor = this.mPrefs.edit();
    localEditor.putString("gcmRegistrationId", paramString);
    localEditor.putLong("lastPushTokenRegistrationTime", paramLong);
    localEditor.apply();
  }
  
  private void unregisterInBackground()
  {
    this.mUnregistrationIsInProgress = true;
    Observable.create(new Observable.OnSubscribe()
    {
      public void call(Subscriber<? super String> paramAnonymousSubscriber)
      {
        try
        {
          GoogleCloudMessaging.getInstance(GooglePushServices.this.mUpsight).unregister();
          paramAnonymousSubscriber.onCompleted();
          return;
        }
        catch (IOException localIOException)
        {
          paramAnonymousSubscriber.onError(localIOException);
        }
      }
    }).subscribeOn(this.mComputationScheduler).observeOn(HandlerScheduler.from(this.mUiThreadHandler)).subscribe(new Observer()
    {
      public void onCompleted()
      {
        synchronized (GooglePushServices.this)
        {
          UpsightCommUnregisterEvent.createBuilder().record(GooglePushServices.this.mUpsight);
          GooglePushServices.this.removeRegistrationInfo();
          HashSet localHashSet = new HashSet(GooglePushServices.this.mPendingUnregisterListeners);
          GooglePushServices.this.mPendingUnregisterListeners.clear();
          GooglePushServices.access$602(GooglePushServices.this, false);
          ??? = localHashSet.iterator();
          if (((Iterator)???).hasNext()) {
            ((UpsightGooglePushServices.OnUnregisterListener)((Iterator)???).next()).onSuccess();
          }
        }
      }
      
      public void onError(Throwable paramAnonymousThrowable)
      {
        synchronized (GooglePushServices.this)
        {
          HashSet localHashSet = new HashSet(GooglePushServices.this.mPendingUnregisterListeners);
          GooglePushServices.this.mPendingUnregisterListeners.clear();
          GooglePushServices.access$602(GooglePushServices.this, false);
          ??? = localHashSet.iterator();
          if (((Iterator)???).hasNext()) {
            ((UpsightGooglePushServices.OnUnregisterListener)((Iterator)???).next()).onFailure(new UpsightException(paramAnonymousThrowable));
          }
        }
      }
      
      public void onNext(String paramAnonymousString) {}
    });
  }
  
  public UpsightBillboard createPushBillboard(UpsightContext paramUpsightContext, UpsightBillboard.Handler paramHandler)
    throws IllegalArgumentException, IllegalStateException
  {
    try
    {
      if (this.mPushBillboard != null)
      {
        this.mPushBillboard.destroy();
        this.mPushBillboard = null;
      }
      this.mPushBillboard = UpsightBillboard.create(paramUpsightContext, "com_upsight_push_scope", paramHandler);
      paramUpsightContext = this.mPushBillboard;
      return paramUpsightContext;
    }
    finally {}
  }
  
  public void register(UpsightGooglePushServices.OnRegisterListener paramOnRegisterListener)
  {
    if (paramOnRegisterListener == null) {
      try
      {
        throw new IllegalArgumentException("Listener could not be null");
      }
      finally {}
    }
    if (!hasPlayServices()) {
      paramOnRegisterListener.onFailure(new UpsightException("Google Play Services are not available", new Object[0]));
    }
    for (;;)
    {
      return;
      if (this.mUnregistrationIsInProgress)
      {
        paramOnRegisterListener.onFailure(new UpsightException("Unregistration is in progress, try later", new Object[0]));
      }
      else
      {
        Object localObject7 = null;
        Object localObject6 = null;
        Object localObject5 = null;
        Object localObject1 = localObject7;
        Object localObject4;
        try
        {
          Object localObject8 = this.mUpsight.getPackageManager().getApplicationInfo(this.mUpsight.getPackageName(), 128).metaData;
          localObject2 = localObject6;
          localObject4 = localObject5;
          if (localObject8 != null)
          {
            localObject1 = localObject7;
            localObject8 = ((Bundle)localObject8).getString("com.upsight.gcm");
            localObject2 = localObject6;
            localObject4 = localObject5;
            localObject1 = localObject7;
            if (!TextUtils.isEmpty((CharSequence)localObject8))
            {
              localObject1 = localObject7;
              localObject2 = ((String)localObject8).substring(0, ((String)localObject8).lastIndexOf('.'));
              localObject1 = localObject2;
              localObject4 = ((String)localObject8).substring(((String)localObject8).lastIndexOf('.') + 1);
            }
          }
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException)
        {
          for (;;)
          {
            Object localObject2;
            this.mLogger.e("Upsight", "Unexpected error: Package name missing!?", new Object[] { localNameNotFoundException });
            Object localObject3 = localObject1;
            localObject4 = localObject5;
          }
          this.mPendingRegisterListeners.add(paramOnRegisterListener);
        }
        if ((!this.mUpsight.getPackageName().equals(localObject2)) || (TextUtils.isEmpty((CharSequence)localObject4)))
        {
          this.mLogger.e(LOG_TAG, "Registration aborted, wrong or no value for com.upsight.gcm was defined", new Object[0]);
          if (!this.mUpsight.getPackageName().equals(localObject2)) {
            this.mLogger.e(LOG_TAG, "Check that the package name of your application is specified correctly", new Object[0]);
          }
          if (TextUtils.isEmpty((CharSequence)localObject4)) {
            this.mLogger.e(LOG_TAG, "Check that your GCM sender id is specified correctly", new Object[0]);
          }
          paramOnRegisterListener.onFailure(new UpsightException("GCM properties must be set in the Android Manifest with <meta-data android:name=\"com.upsight.gcm\" android:value=\"" + this.mUpsight.getPackageName() + ".GCM_SENDER_ID\" />", new Object[0]));
        }
        else if (!this.mRegistrationIsInProgress)
        {
          registerInBackground((String)localObject4);
        }
      }
    }
  }
  
  public void unregister(UpsightGooglePushServices.OnUnregisterListener paramOnUnregisterListener)
  {
    if (paramOnUnregisterListener == null) {
      try
      {
        throw new IllegalArgumentException("Listener could not be null");
      }
      finally {}
    }
    if (!isRegistered()) {
      paramOnUnregisterListener.onFailure(new UpsightException("Application is not registered to pushes yet", new Object[0]));
    }
    for (;;)
    {
      return;
      if (this.mRegistrationIsInProgress)
      {
        paramOnUnregisterListener.onFailure(new UpsightException("Registration is in progress, try later", new Object[0]));
      }
      else
      {
        this.mPendingUnregisterListeners.add(paramOnUnregisterListener);
        if (!this.mUnregistrationIsInProgress) {
          unregisterInBackground();
        }
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/internal/GooglePushServices.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */