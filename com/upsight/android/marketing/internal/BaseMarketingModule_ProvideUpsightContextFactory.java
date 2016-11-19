package com.upsight.android.marketing.internal;

import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class BaseMarketingModule_ProvideUpsightContextFactory
  implements Factory<UpsightContext>
{
  private final BaseMarketingModule module;
  
  static
  {
    if (!BaseMarketingModule_ProvideUpsightContextFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public BaseMarketingModule_ProvideUpsightContextFactory(BaseMarketingModule paramBaseMarketingModule)
  {
    assert (paramBaseMarketingModule != null);
    this.module = paramBaseMarketingModule;
  }
  
  public static Factory<UpsightContext> create(BaseMarketingModule paramBaseMarketingModule)
  {
    return new BaseMarketingModule_ProvideUpsightContextFactory(paramBaseMarketingModule);
  }
  
  public UpsightContext get()
  {
    return (UpsightContext)Preconditions.checkNotNull(this.module.provideUpsightContext(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/BaseMarketingModule_ProvideUpsightContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */