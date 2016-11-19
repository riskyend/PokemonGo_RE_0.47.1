package com.upsight.android.marketing.internal.billboard;

import com.upsight.android.UpsightContext;
import com.upsight.android.marketing.internal.content.MarketingContentStore;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class BillboardDialogFragment_MembersInjector
  implements MembersInjector<BillboardDialogFragment>
{
  private final Provider<MarketingContentStore> mContentStoreProvider;
  private final Provider<UpsightContext> mUpsightProvider;
  
  static
  {
    if (!BillboardDialogFragment_MembersInjector.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public BillboardDialogFragment_MembersInjector(Provider<UpsightContext> paramProvider, Provider<MarketingContentStore> paramProvider1)
  {
    assert (paramProvider != null);
    this.mUpsightProvider = paramProvider;
    assert (paramProvider1 != null);
    this.mContentStoreProvider = paramProvider1;
  }
  
  public static MembersInjector<BillboardDialogFragment> create(Provider<UpsightContext> paramProvider, Provider<MarketingContentStore> paramProvider1)
  {
    return new BillboardDialogFragment_MembersInjector(paramProvider, paramProvider1);
  }
  
  public static void injectMContentStore(BillboardDialogFragment paramBillboardDialogFragment, Provider<MarketingContentStore> paramProvider)
  {
    paramBillboardDialogFragment.mContentStore = ((MarketingContentStore)paramProvider.get());
  }
  
  public static void injectMUpsight(BillboardDialogFragment paramBillboardDialogFragment, Provider<UpsightContext> paramProvider)
  {
    paramBillboardDialogFragment.mUpsight = ((UpsightContext)paramProvider.get());
  }
  
  public void injectMembers(BillboardDialogFragment paramBillboardDialogFragment)
  {
    if (paramBillboardDialogFragment == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    paramBillboardDialogFragment.mUpsight = ((UpsightContext)this.mUpsightProvider.get());
    paramBillboardDialogFragment.mContentStore = ((MarketingContentStore)this.mContentStoreProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/billboard/BillboardDialogFragment_MembersInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */