package com.upsight.android.marketing.internal.billboard;

import com.upsight.android.UpsightContext;
import com.upsight.android.marketing.internal.content.MarketingContentStore;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class BillboardManagementActivity_MembersInjector
  implements MembersInjector<BillboardManagementActivity>
{
  private final Provider<MarketingContentStore> mContentStoreProvider;
  private final Provider<UpsightContext> mUpsightProvider;
  
  static
  {
    if (!BillboardManagementActivity_MembersInjector.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public BillboardManagementActivity_MembersInjector(Provider<UpsightContext> paramProvider, Provider<MarketingContentStore> paramProvider1)
  {
    assert (paramProvider != null);
    this.mUpsightProvider = paramProvider;
    assert (paramProvider1 != null);
    this.mContentStoreProvider = paramProvider1;
  }
  
  public static MembersInjector<BillboardManagementActivity> create(Provider<UpsightContext> paramProvider, Provider<MarketingContentStore> paramProvider1)
  {
    return new BillboardManagementActivity_MembersInjector(paramProvider, paramProvider1);
  }
  
  public static void injectMContentStore(BillboardManagementActivity paramBillboardManagementActivity, Provider<MarketingContentStore> paramProvider)
  {
    paramBillboardManagementActivity.mContentStore = ((MarketingContentStore)paramProvider.get());
  }
  
  public static void injectMUpsight(BillboardManagementActivity paramBillboardManagementActivity, Provider<UpsightContext> paramProvider)
  {
    paramBillboardManagementActivity.mUpsight = ((UpsightContext)paramProvider.get());
  }
  
  public void injectMembers(BillboardManagementActivity paramBillboardManagementActivity)
  {
    if (paramBillboardManagementActivity == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    paramBillboardManagementActivity.mUpsight = ((UpsightContext)this.mUpsightProvider.get());
    paramBillboardManagementActivity.mContentStore = ((MarketingContentStore)this.mContentStoreProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/billboard/BillboardManagementActivity_MembersInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */