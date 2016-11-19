package com.upsight.android.analytics.internal.dispatcher.delivery;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.logger.UpsightLogger;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import rx.Scheduler;

@Module
public final class DeliveryModule
{
  private static final boolean USE_PRETTY_JSON_LOGGING = false;
  
  @Provides
  @Singleton
  public QueueBuilder provideQueueBuilder(UpsightContext paramUpsightContext, Clock paramClock, @Named("dispatcher-threadpool") Scheduler paramScheduler1, @Named("dispatcher-batching") Scheduler paramScheduler2, SignatureVerifier paramSignatureVerifier, Provider<ResponseParser> paramProvider)
  {
    Gson localGson = paramUpsightContext.getCoreComponent().gson();
    JsonParser localJsonParser = paramUpsightContext.getCoreComponent().jsonParser();
    UpsightLogger localUpsightLogger = paramUpsightContext.getLogger();
    return new QueueBuilder(paramUpsightContext, localGson, new GsonBuilder().create(), localJsonParser, paramClock, localUpsightLogger, paramScheduler1, paramScheduler2, paramSignatureVerifier, paramProvider);
  }
  
  @Provides
  @Singleton
  public SignatureVerifier provideResponseVerifier(UpsightContext paramUpsightContext)
  {
    return new BouncySignatureVerifier(paramUpsightContext);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/delivery/DeliveryModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */