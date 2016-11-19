package com.upsight.android.marketing.internal.content;

import android.text.TextUtils;
import com.squareup.otto.Bus;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.marketing.UpsightMarketingContentStore;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class MarketingContentStoreImpl
  extends UpsightMarketingContentStore
  implements MarketingContentStore
{
  public static final long DEFAULT_TIME_TO_LIVE_MS = 600000L;
  private static final String LOG_TAG = MarketingContentStore.class.getSimpleName();
  private Bus mBus;
  private Clock mClock;
  private final Map<String, MarketingContent> mContentMap = new HashMap();
  private UpsightLogger mLogger;
  private final Map<String, String> mParentEligibilityMap = new HashMap();
  private final Map<String, Set<String>> mScopeEligibilityMap = new HashMap();
  private final Map<String, Long> mTimestamps = new HashMap();
  
  public MarketingContentStoreImpl(Bus paramBus, Clock paramClock, UpsightLogger paramUpsightLogger)
  {
    this.mBus = paramBus;
    this.mClock = paramClock;
    this.mLogger = paramUpsightLogger;
  }
  
  /* Error */
  public MarketingContent get(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 47	com/upsight/android/marketing/internal/content/MarketingContentStoreImpl:mTimestamps	Ljava/util/Map;
    //   6: aload_1
    //   7: invokeinterface 69 2 0
    //   12: checkcast 71	java/lang/Long
    //   15: astore_2
    //   16: aload_2
    //   17: ifnull +77 -> 94
    //   20: aload_0
    //   21: getfield 57	com/upsight/android/marketing/internal/content/MarketingContentStoreImpl:mClock	Lcom/upsight/android/analytics/internal/session/Clock;
    //   24: invokeinterface 77 1 0
    //   29: aload_2
    //   30: invokevirtual 80	java/lang/Long:longValue	()J
    //   33: ldc2_w 9
    //   36: ladd
    //   37: lcmp
    //   38: ifgt +56 -> 94
    //   41: aload_0
    //   42: getfield 59	com/upsight/android/marketing/internal/content/MarketingContentStoreImpl:mLogger	Lcom/upsight/android/logger/UpsightLogger;
    //   45: getstatic 37	com/upsight/android/marketing/internal/content/MarketingContentStoreImpl:LOG_TAG	Ljava/lang/String;
    //   48: new 82	java/lang/StringBuilder
    //   51: dup
    //   52: invokespecial 83	java/lang/StringBuilder:<init>	()V
    //   55: ldc 85
    //   57: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   60: aload_1
    //   61: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   64: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   67: iconst_0
    //   68: anewarray 94	java/lang/Object
    //   71: invokeinterface 100 4 0
    //   76: aload_0
    //   77: getfield 49	com/upsight/android/marketing/internal/content/MarketingContentStoreImpl:mContentMap	Ljava/util/Map;
    //   80: aload_1
    //   81: invokeinterface 69 2 0
    //   86: checkcast 102	com/upsight/android/marketing/internal/content/MarketingContent
    //   89: astore_1
    //   90: aload_0
    //   91: monitorexit
    //   92: aload_1
    //   93: areturn
    //   94: aload_0
    //   95: aload_1
    //   96: invokevirtual 106	com/upsight/android/marketing/internal/content/MarketingContentStoreImpl:remove	(Ljava/lang/String;)Z
    //   99: pop
    //   100: aconst_null
    //   101: astore_1
    //   102: goto -12 -> 90
    //   105: astore_1
    //   106: aload_0
    //   107: monitorexit
    //   108: aload_1
    //   109: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	110	0	this	MarketingContentStoreImpl
    //   0	110	1	paramString	String
    //   15	15	2	localLong	Long
    // Exception table:
    //   from	to	target	type
    //   2	16	105	finally
    //   20	90	105	finally
    //   94	100	105	finally
  }
  
  public Set<String> getIdsForScope(String paramString)
  {
    Object localObject;
    StringBuilder localStringBuilder;
    for (;;)
    {
      try
      {
        localObject = (Set)this.mScopeEligibilityMap.get(paramString);
        if (localObject == null)
        {
          localObject = new HashSet();
          localStringBuilder = new StringBuilder();
          Iterator localIterator = ((Set)localObject).iterator();
          if (!localIterator.hasNext()) {
            break;
          }
          localStringBuilder.append((String)localIterator.next()).append(" ");
          continue;
        }
        localObject = new HashSet((Collection)localObject);
      }
      finally {}
    }
    this.mLogger.d(LOG_TAG, "getIdsForScope scope=" + paramString + " ids=[ " + localStringBuilder + " ]", new Object[0]);
    return (Set<String>)localObject;
  }
  
  /* Error */
  public boolean isContentReady(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_1
    //   4: invokevirtual 148	com/upsight/android/marketing/internal/content/MarketingContentStoreImpl:getIdsForScope	(Ljava/lang/String;)Ljava/util/Set;
    //   7: invokeinterface 151 1 0
    //   12: istore_2
    //   13: iload_2
    //   14: ifne +9 -> 23
    //   17: iconst_1
    //   18: istore_2
    //   19: aload_0
    //   20: monitorexit
    //   21: iload_2
    //   22: ireturn
    //   23: iconst_0
    //   24: istore_2
    //   25: goto -6 -> 19
    //   28: astore_1
    //   29: aload_0
    //   30: monitorexit
    //   31: aload_1
    //   32: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	33	0	this	MarketingContentStoreImpl
    //   0	33	1	paramString	String
    //   12	13	2	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	13	28	finally
  }
  
  public boolean presentScopedContent(String paramString, String[] paramArrayOfString)
  {
    boolean bool2 = false;
    int i = 0;
    for (;;)
    {
      MarketingContent localMarketingContent;
      try
      {
        localMarketingContent = (MarketingContent)this.mContentMap.get(paramString);
        bool1 = bool2;
        if (localMarketingContent == null) {
          break label192;
        }
        bool1 = bool2;
        if (paramArrayOfString == null) {
          break label192;
        }
        bool1 = bool2;
        if (paramArrayOfString.length <= 0) {
          break label192;
        }
        int j = paramArrayOfString.length;
        if (i < j)
        {
          String str = paramArrayOfString[i];
          Object localObject = (Set)this.mScopeEligibilityMap.get(str);
          if (localObject != null)
          {
            ((Set)localObject).add(paramString);
          }
          else
          {
            localObject = new HashSet();
            ((Set)localObject).add(paramString);
            this.mScopeEligibilityMap.put(str, localObject);
          }
        }
      }
      finally {}
      localMarketingContent.markPresentable(new MarketingContent.ScopedAvailabilityEvent(paramString, paramArrayOfString), this.mBus);
      this.mLogger.d(LOG_TAG, "presentScopedContent id=" + paramString, new Object[0]);
      boolean bool1 = true;
      label192:
      return bool1;
      i += 1;
    }
  }
  
  public boolean presentScopelessContent(String paramString1, String paramString2)
  {
    boolean bool2 = false;
    try
    {
      MarketingContent localMarketingContent = (MarketingContent)this.mContentMap.get(paramString1);
      boolean bool1 = bool2;
      if (localMarketingContent != null)
      {
        bool1 = bool2;
        if (!TextUtils.isEmpty(paramString2))
        {
          this.mParentEligibilityMap.put(paramString2, paramString1);
          localMarketingContent.markPresentable(new MarketingContent.ScopelessAvailabilityEvent(paramString1, paramString2), this.mBus);
          this.mLogger.d(LOG_TAG, "presentScopelessContent id=" + paramString1 + " parentId=" + paramString2, new Object[0]);
          bool1 = true;
        }
      }
      return bool1;
    }
    finally {}
  }
  
  public boolean put(String paramString, MarketingContent paramMarketingContent)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    try
    {
      if (!TextUtils.isEmpty(paramString))
      {
        bool1 = bool2;
        if (paramMarketingContent != null)
        {
          this.mContentMap.put(paramString, paramMarketingContent);
          this.mTimestamps.put(paramString, Long.valueOf(this.mClock.currentTimeMillis()));
          bool1 = true;
        }
      }
      this.mLogger.d(LOG_TAG, "put id=" + paramString + " isAdded=" + bool1, new Object[0]);
      return bool1;
    }
    finally {}
  }
  
  public boolean remove(String paramString)
  {
    boolean bool2 = false;
    boolean bool1;
    Object localObject;
    for (;;)
    {
      try
      {
        if (TextUtils.isEmpty(paramString)) {
          break label219;
        }
        if (this.mContentMap.remove(paramString) != null)
        {
          bool1 = true;
          bool2 = bool1;
          if (!bool1) {
            break label219;
          }
          localIterator = this.mScopeEligibilityMap.keySet().iterator();
          if (!localIterator.hasNext()) {
            break;
          }
          localObject = (String)localIterator.next();
          localObject = (Set)this.mScopeEligibilityMap.get(localObject);
          if ((localObject == null) || (!((Set)localObject).contains(paramString))) {
            continue;
          }
          ((Set)localObject).remove(paramString);
          continue;
        }
        bool1 = false;
      }
      finally {}
    }
    Iterator localIterator = this.mParentEligibilityMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      localObject = (String)localIterator.next();
      String str = (String)this.mParentEligibilityMap.get(localObject);
      if ((paramString.equals(localObject)) || (paramString.equals(str))) {
        localIterator.remove();
      }
    }
    this.mTimestamps.remove(paramString);
    bool2 = bool1;
    label219:
    this.mLogger.d(LOG_TAG, "remove id=" + paramString + " isRemoved=" + bool2, new Object[0]);
    return bool2;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/MarketingContentStoreImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */