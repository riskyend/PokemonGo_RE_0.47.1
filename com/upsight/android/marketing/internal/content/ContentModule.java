package com.upsight.android.marketing.internal.content;

import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.upsight.android.UpsightAnalyticsExtension;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.analytics.UpsightAnalyticsComponent;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.marketing.UpsightMarketingContentStore;
import com.upsight.android.marketing.internal.vast.VastContentMediator;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;
import rx.Scheduler;

@Module
public final class ContentModule
{
  @Provides
  @Singleton
  DefaultContentMediator provideDefaultContentMediator()
  {
    return new DefaultContentMediator();
  }
  
  @Provides
  @Singleton
  MarketingContentFactory provideMarketingContentFactory(UpsightContext paramUpsightContext, @Named("main") Scheduler paramScheduler, MarketingContentMediatorManager paramMarketingContentMediatorManager, MarketingContentStore paramMarketingContentStore, ContentTemplateWebViewClientFactory paramContentTemplateWebViewClientFactory)
  {
    Object localObject = paramUpsightContext.getCoreComponent();
    Bus localBus = ((UpsightCoreComponent)localObject).bus();
    localObject = ((UpsightCoreComponent)localObject).gson();
    UpsightLogger localUpsightLogger = paramUpsightContext.getLogger();
    return new MarketingContentFactory(new MarketingContentActions.MarketingContentActionContext(paramUpsightContext, localBus, (Gson)localObject, ((UpsightAnalyticsComponent)((UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics")).getComponent()).clock(), paramScheduler.createWorker(), localUpsightLogger, paramMarketingContentMediatorManager, paramMarketingContentStore, paramContentTemplateWebViewClientFactory));
  }
  
  @Provides
  @Singleton
  MarketingContentMediatorManager provideMarketingContentMediatorManager(DefaultContentMediator paramDefaultContentMediator)
  {
    return new MarketingContentMediatorManager(paramDefaultContentMediator);
  }
  
  @Provides
  @Singleton
  MarketingContentStore provideMarketingContentStore(MarketingContentStoreImpl paramMarketingContentStoreImpl)
  {
    return paramMarketingContentStoreImpl;
  }
  
  @Provides
  @Singleton
  MarketingContentStoreImpl provideMarketingContentStoreImpl(UpsightContext paramUpsightContext)
  {
    UpsightCoreComponent localUpsightCoreComponent = paramUpsightContext.getCoreComponent();
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    Object localObject3 = null;
    Object localObject4 = null;
    Object localObject2 = localObject3;
    Object localObject1 = localObject4;
    if (localUpsightCoreComponent != null)
    {
      localObject2 = localObject3;
      localObject1 = localObject4;
      if (localUpsightAnalyticsExtension != null)
      {
        localObject2 = localUpsightCoreComponent.bus();
        localObject1 = ((UpsightAnalyticsComponent)localUpsightAnalyticsExtension.getComponent()).clock();
      }
    }
    return new MarketingContentStoreImpl((Bus)localObject2, (Clock)localObject1, paramUpsightContext.getLogger());
  }
  
  @Provides
  @Singleton
  UpsightMarketingContentStore provideUpsightMarketingContentStore(MarketingContentStoreImpl paramMarketingContentStoreImpl)
  {
    return paramMarketingContentStoreImpl;
  }
  
  @Provides
  @Singleton
  VastContentMediator provideVastContentMediator(UpsightContext paramUpsightContext)
  {
    return new VastContentMediator(paramUpsightContext.getLogger(), paramUpsightContext.getCoreComponent().bus());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/ContentModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */