package com.upsight.android.marketing;

import com.upsight.android.UpsightExtension.BaseComponent;
import com.upsight.android.UpsightMarketingExtension;
import com.upsight.android.marketing.internal.billboard.BillboardDialogFragment;
import com.upsight.android.marketing.internal.billboard.BillboardManagementActivity;

public abstract interface UpsightMarketingComponent
  extends UpsightExtension.BaseComponent<UpsightMarketingExtension>
{
  public abstract void inject(BillboardDialogFragment paramBillboardDialogFragment);
  
  public abstract void inject(BillboardManagementActivity paramBillboardManagementActivity);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/UpsightMarketingComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */