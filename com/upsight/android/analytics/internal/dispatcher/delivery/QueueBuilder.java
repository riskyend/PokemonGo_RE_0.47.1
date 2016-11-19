package com.upsight.android.analytics.internal.dispatcher.delivery;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.internal.dispatcher.schema.Schema;
import com.upsight.android.analytics.internal.dispatcher.util.Selector;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.logger.UpsightLogger;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.inject.Provider;
import rx.Scheduler;

public class QueueBuilder
{
  private static final String CHARSET_UTF_8 = "UTF-8";
  public static final String MACRO_APP_TOKEN = "{app_token}";
  public static final String MACRO_APP_VERSION = "{app_version}";
  public static final String MACRO_HOST = "{host}";
  public static final String MACRO_PROTOCOL = "{protocol}";
  public static final String MACRO_PROTOCOL_VERSION = "{version}";
  public static final String MACRO_SDK_VERSION = "{sdk_version}";
  private static final String PROTOCOL_VERSION = "v1";
  private Clock mClock;
  private Map<String, String> mEndpointMacros;
  private Gson mGson;
  private JsonParser mJsonParser;
  private UpsightLogger mLogger;
  private Gson mResponseLoggingGson;
  private Provider<ResponseParser> mResponseParserProvider;
  private Scheduler mRetryExecutor;
  private Scheduler mSendExecutor;
  private SignatureVerifier mSignatureVerifier;
  private UpsightContext mUpsight;
  
  QueueBuilder(UpsightContext paramUpsightContext, Gson paramGson1, Gson paramGson2, JsonParser paramJsonParser, Clock paramClock, UpsightLogger paramUpsightLogger, Scheduler paramScheduler1, Scheduler paramScheduler2, SignatureVerifier paramSignatureVerifier, Provider<ResponseParser> paramProvider)
  {
    this.mUpsight = paramUpsightContext;
    this.mGson = paramGson1;
    this.mResponseLoggingGson = paramGson2;
    this.mJsonParser = paramJsonParser;
    this.mClock = paramClock;
    this.mLogger = paramUpsightLogger;
    this.mRetryExecutor = paramScheduler1;
    this.mSendExecutor = paramScheduler2;
    this.mSignatureVerifier = paramSignatureVerifier;
    this.mResponseParserProvider = paramProvider;
    createEndpointMacroMap();
  }
  
  private void createEndpointMacroMap()
  {
    this.mEndpointMacros = new HashMap();
    this.mEndpointMacros.put("{version}", "v1");
    this.mEndpointMacros.put("{app_token}", this.mUpsight.getApplicationToken());
    this.mEndpointMacros.put("{app_version}", getAppVersionName(this.mUpsight));
    this.mEndpointMacros.put("{sdk_version}", this.mUpsight.getSdkVersion());
  }
  
  private String getAppVersionName(Context paramContext)
  {
    String str = "";
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0);
      paramContext = str;
      if (localPackageInfo != null)
      {
        paramContext = str;
        if (localPackageInfo.versionName != null) {
          paramContext = URLEncoder.encode(localPackageInfo.versionName, "UTF-8");
        }
      }
      return paramContext;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      this.mLogger.e(QueueBuilder.class.getSimpleName(), "Could not get package info", new Object[] { paramContext });
      return "";
    }
    catch (UnsupportedEncodingException paramContext)
    {
      this.mLogger.e(QueueBuilder.class.getSimpleName(), "UTF-8 encoding not supported", new Object[] { paramContext });
    }
    return "";
  }
  
  public Queue build(String paramString, QueueConfig paramQueueConfig, Selector<Schema> paramSelector1, Selector<Schema> paramSelector2)
  {
    Object localObject = new UpsightEndpoint(prepareEndpoint(paramQueueConfig.getEndpointAddress()), this.mSignatureVerifier, this.mGson, this.mJsonParser, this.mResponseLoggingGson, this.mLogger);
    localObject = new BatchSender(this.mUpsight, paramQueueConfig.getBatchSenderConfig(), this.mRetryExecutor, this.mSendExecutor, (UpsightEndpoint)localObject, (ResponseParser)this.mResponseParserProvider.get(), this.mJsonParser, this.mClock, this.mLogger);
    return new Queue(paramString, paramSelector1, paramSelector2, new BatcherFactory(paramQueueConfig.getBatcherConfig()), (BatchSender)localObject);
  }
  
  String prepareEndpoint(String paramString)
  {
    Iterator localIterator = this.mEndpointMacros.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      paramString = paramString.replace((CharSequence)localEntry.getKey(), (CharSequence)localEntry.getValue());
    }
    return paramString;
  }
  
  private class BatcherFactory
    implements Batcher.Factory
  {
    private Batcher.Config mConfig;
    
    public BatcherFactory(Batcher.Config paramConfig)
    {
      this.mConfig = paramConfig;
    }
    
    public Batcher create(Schema paramSchema, BatchSender paramBatchSender)
    {
      return new Batcher(this.mConfig, paramSchema, paramBatchSender, QueueBuilder.this.mRetryExecutor);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/delivery/QueueBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */