package com.upsight.android.marketing.internal.content;

import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import rx.Scheduler;

public final class ContentModule_ProvideMarketingContentFactoryFactory
  implements Factory<MarketingContentFactory>
{
  private final Provider<MarketingContentMediatorManager> contentMediatorManagerProvider;
  private final Provider<MarketingContentStore> contentStoreProvider;
  private final Provider<ContentTemplateWebViewClientFactory> contentTemplateWebViewClientFactoryProvider;
  private final ContentModule module;
  private final Provider<Scheduler> schedulerProvider;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!ContentModule_ProvideMarketingContentFactoryFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ContentModule_ProvideMarketingContentFactoryFactory(ContentModule paramContentModule, Provider<UpsightContext> paramProvider, Provider<Scheduler> paramProvider1, Provider<MarketingContentMediatorManager> paramProvider2, Provider<MarketingContentStore> paramProvider3, Provider<ContentTemplateWebViewClientFactory> paramProvider4)
  {
    assert (paramContentModule != null);
    this.module = paramContentModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
    assert (paramProvider1 != null);
    this.schedulerProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.contentMediatorManagerProvider = paramProvider2;
    assert (paramProvider3 != null);
    this.contentStoreProvider = paramProvider3;
    assert (paramProvider4 != null);
    this.contentTemplateWebViewClientFactoryProvider = paramProvider4;
  }
  
  public static Factory<MarketingContentFactory> create(ContentModule paramContentModule, Provider<UpsightContext> paramProvider, Provider<Scheduler> paramProvider1, Provider<MarketingContentMediatorManager> paramProvider2, Provider<MarketingContentStore> paramProvider3, Provider<ContentTemplateWebViewClientFactory> paramProvider4)
  {
    return new ContentModule_ProvideMarketingContentFactoryFactory(paramContentModule, paramProvider, paramProvider1, paramProvider2, paramProvider3, paramProvider4);
  }
  
  public MarketingContentFactory get()
  {
    return (MarketingContentFactory)Preconditions.checkNotNull(this.module.provideMarketingContentFactory((UpsightContext)this.upsightProvider.get(), (Scheduler)this.schedulerProvider.get(), (MarketingContentMediatorManager)this.contentMediatorManagerProvider.get(), (MarketingContentStore)this.contentStoreProvider.get(), (ContentTemplateWebViewClientFactory)this.contentTemplateWebViewClientFactoryProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/ContentModule_ProvideMarketingContentFactoryFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */