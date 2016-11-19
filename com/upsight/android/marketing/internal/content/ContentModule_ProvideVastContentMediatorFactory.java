package com.upsight.android.marketing.internal.content;

import com.upsight.android.UpsightContext;
import com.upsight.android.marketing.internal.vast.VastContentMediator;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class ContentModule_ProvideVastContentMediatorFactory
  implements Factory<VastContentMediator>
{
  private final ContentModule module;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!ContentModule_ProvideVastContentMediatorFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ContentModule_ProvideVastContentMediatorFactory(ContentModule paramContentModule, Provider<UpsightContext> paramProvider)
  {
    assert (paramContentModule != null);
    this.module = paramContentModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
  }
  
  public static Factory<VastContentMediator> create(ContentModule paramContentModule, Provider<UpsightContext> paramProvider)
  {
    return new ContentModule_ProvideVastContentMediatorFactory(paramContentModule, paramProvider);
  }
  
  public VastContentMediator get()
  {
    return (VastContentMediator)Preconditions.checkNotNull(this.module.provideVastContentMediator((UpsightContext)this.upsightProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/ContentModule_ProvideVastContentMediatorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */