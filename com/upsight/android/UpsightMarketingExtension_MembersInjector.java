package com.upsight.android;

import com.upsight.android.marketing.UpsightMarketingApi;
import com.upsight.android.marketing.internal.content.DefaultContentMediator;
import com.upsight.android.marketing.internal.content.MarketingContentFactory;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UpsightMarketingExtension_MembersInjector
  implements MembersInjector<UpsightMarketingExtension>
{
  private final Provider<DefaultContentMediator> mDefaultContentMediatorProvider;
  private final Provider<MarketingContentFactory> mMarketingContentFactoryProvider;
  private final Provider<UpsightMarketingApi> mMarketingProvider;
  
  static
  {
    if (!UpsightMarketingExtension_MembersInjector.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public UpsightMarketingExtension_MembersInjector(Provider<UpsightMarketingApi> paramProvider, Provider<MarketingContentFactory> paramProvider1, Provider<DefaultContentMediator> paramProvider2)
  {
    assert (paramProvider != null);
    this.mMarketingProvider = paramProvider;
    assert (paramProvider1 != null);
    this.mMarketingContentFactoryProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.mDefaultContentMediatorProvider = paramProvider2;
  }
  
  public static MembersInjector<UpsightMarketingExtension> create(Provider<UpsightMarketingApi> paramProvider, Provider<MarketingContentFactory> paramProvider1, Provider<DefaultContentMediator> paramProvider2)
  {
    return new UpsightMarketingExtension_MembersInjector(paramProvider, paramProvider1, paramProvider2);
  }
  
  public static void injectMDefaultContentMediator(UpsightMarketingExtension paramUpsightMarketingExtension, Provider<DefaultContentMediator> paramProvider)
  {
    paramUpsightMarketingExtension.mDefaultContentMediator = ((DefaultContentMediator)paramProvider.get());
  }
  
  public static void injectMMarketing(UpsightMarketingExtension paramUpsightMarketingExtension, Provider<UpsightMarketingApi> paramProvider)
  {
    paramUpsightMarketingExtension.mMarketing = ((UpsightMarketingApi)paramProvider.get());
  }
  
  public static void injectMMarketingContentFactory(UpsightMarketingExtension paramUpsightMarketingExtension, Provider<MarketingContentFactory> paramProvider)
  {
    paramUpsightMarketingExtension.mMarketingContentFactory = ((MarketingContentFactory)paramProvider.get());
  }
  
  public void injectMembers(UpsightMarketingExtension paramUpsightMarketingExtension)
  {
    if (paramUpsightMarketingExtension == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    paramUpsightMarketingExtension.mMarketing = ((UpsightMarketingApi)this.mMarketingProvider.get());
    paramUpsightMarketingExtension.mMarketingContentFactory = ((MarketingContentFactory)this.mMarketingContentFactoryProvider.get());
    paramUpsightMarketingExtension.mDefaultContentMediator = ((DefaultContentMediator)this.mDefaultContentMediatorProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/UpsightMarketingExtension_MembersInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */