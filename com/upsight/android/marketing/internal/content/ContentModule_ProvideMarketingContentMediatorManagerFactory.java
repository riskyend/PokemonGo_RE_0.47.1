package com.upsight.android.marketing.internal.content;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ContentModule_ProvideMarketingContentMediatorManagerFactory
  implements Factory<MarketingContentMediatorManager>
{
  private final Provider<DefaultContentMediator> defaultContentMediatorProvider;
  private final ContentModule module;
  
  static
  {
    if (!ContentModule_ProvideMarketingContentMediatorManagerFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ContentModule_ProvideMarketingContentMediatorManagerFactory(ContentModule paramContentModule, Provider<DefaultContentMediator> paramProvider)
  {
    assert (paramContentModule != null);
    this.module = paramContentModule;
    assert (paramProvider != null);
    this.defaultContentMediatorProvider = paramProvider;
  }
  
  public static Factory<MarketingContentMediatorManager> create(ContentModule paramContentModule, Provider<DefaultContentMediator> paramProvider)
  {
    return new ContentModule_ProvideMarketingContentMediatorManagerFactory(paramContentModule, paramProvider);
  }
  
  public MarketingContentMediatorManager get()
  {
    return (MarketingContentMediatorManager)Preconditions.checkNotNull(this.module.provideMarketingContentMediatorManager((DefaultContentMediator)this.defaultContentMediatorProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/ContentModule_ProvideMarketingContentMediatorManagerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */