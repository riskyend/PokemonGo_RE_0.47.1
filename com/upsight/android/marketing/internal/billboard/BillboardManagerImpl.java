package com.upsight.android.marketing.internal.billboard;

import android.content.Intent;
import android.text.TextUtils;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.upsight.android.UpsightContext;
import com.upsight.android.marketing.UpsightBillboard.AttachParameters;
import com.upsight.android.marketing.UpsightBillboard.Handler;
import com.upsight.android.marketing.UpsightBillboardManager;
import com.upsight.android.marketing.internal.content.MarketingContent;
import com.upsight.android.marketing.internal.content.MarketingContent.ScopedAvailabilityEvent;
import com.upsight.android.marketing.internal.content.MarketingContentActions.PurchasesEvent;
import com.upsight.android.marketing.internal.content.MarketingContentActions.RewardsEvent;
import com.upsight.android.marketing.internal.content.MarketingContentStore;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class BillboardManagerImpl
  implements UpsightBillboardManager
{
  private final MarketingContentStore mContentStore;
  private final Map<String, Billboard> mUnfilledBillboards = new HashMap();
  private final UpsightContext mUpsight;
  
  BillboardManagerImpl(UpsightContext paramUpsightContext, MarketingContentStore paramMarketingContentStore, Bus paramBus)
  {
    this.mUpsight = paramUpsightContext;
    this.mContentStore = paramMarketingContentStore;
    paramBus.register(this);
  }
  
  private boolean tryAttachBillboard(String paramString, Billboard paramBillboard)
  {
    boolean bool2 = false;
    MarketingContent localMarketingContent = (MarketingContent)this.mContentStore.get(paramString);
    boolean bool1 = bool2;
    if (paramBillboard != null)
    {
      bool1 = bool2;
      if (localMarketingContent != null)
      {
        bool1 = bool2;
        if (localMarketingContent.isAvailable())
        {
          bool1 = bool2;
          if (localMarketingContent.getContentMediator() != null)
          {
            paramString = paramBillboard.getHandler().onAttach(paramBillboard.getScope());
            bool1 = bool2;
            if (paramString != null)
            {
              bool1 = bool2;
              if (paramString.getActivity() != null)
              {
                this.mUnfilledBillboards.remove(paramBillboard.getScope());
                localMarketingContent.bindBillboard(paramBillboard);
                paramBillboard = new Intent(this.mUpsight, BillboardManagementActivity.class).putExtra("marketingContentId", localMarketingContent.getId()).putExtra("marketingContentPreferredStyle", paramString.getPreferredPresentationStyle()).addFlags(268435456);
                paramString = paramString.getDialogTheme();
                if (paramString != null) {
                  paramBillboard.putExtra("marketingContentDialogTheme", paramString.intValue());
                }
                this.mUpsight.startActivity(paramBillboard);
                bool1 = true;
              }
            }
          }
        }
      }
    }
    return bool1;
  }
  
  @Subscribe
  public void handleActionEvent(MarketingContentActions.PurchasesEvent paramPurchasesEvent)
  {
    try
    {
      Object localObject = (MarketingContent)this.mContentStore.get(paramPurchasesEvent.mId);
      if (localObject != null)
      {
        localObject = ((MarketingContent)localObject).getBoundBillboard();
        if (localObject != null) {
          ((Billboard)localObject).getHandler().onPurchases(paramPurchasesEvent.mPurchases);
        }
      }
      return;
    }
    finally {}
  }
  
  @Subscribe
  public void handleActionEvent(MarketingContentActions.RewardsEvent paramRewardsEvent)
  {
    try
    {
      Object localObject = (MarketingContent)this.mContentStore.get(paramRewardsEvent.mId);
      if (localObject != null)
      {
        localObject = ((MarketingContent)localObject).getBoundBillboard();
        if (localObject != null) {
          ((Billboard)localObject).getHandler().onRewards(paramRewardsEvent.mRewards);
        }
      }
      return;
    }
    finally {}
  }
  
  @Subscribe
  public void handleAvailabilityEvent(MarketingContent.ScopedAvailabilityEvent paramScopedAvailabilityEvent)
  {
    try
    {
      Iterator localIterator = paramScopedAvailabilityEvent.getScopes().iterator();
      boolean bool;
      do
      {
        if (!localIterator.hasNext()) {
          break;
        }
        String str = (String)localIterator.next();
        bool = tryAttachBillboard(paramScopedAvailabilityEvent.getId(), (Billboard)this.mUnfilledBillboards.get(str));
      } while (!bool);
      return;
    }
    finally {}
  }
  
  public boolean registerBillboard(Billboard paramBillboard)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramBillboard != null) {}
    try
    {
      Object localObject = paramBillboard.getScope();
      bool1 = bool2;
      if (!TextUtils.isEmpty((CharSequence)localObject))
      {
        bool1 = bool2;
        if (paramBillboard.getHandler() != null)
        {
          bool1 = bool2;
          if (this.mUnfilledBillboards.get(localObject) == null)
          {
            bool2 = true;
            this.mUnfilledBillboards.put(localObject, paramBillboard);
            localObject = this.mContentStore.getIdsForScope((String)localObject).iterator();
            do
            {
              bool1 = bool2;
              if (!((Iterator)localObject).hasNext()) {
                break;
              }
              bool1 = tryAttachBillboard((String)((Iterator)localObject).next(), paramBillboard);
            } while (!bool1);
            bool1 = bool2;
          }
        }
      }
      return bool1;
    }
    finally {}
  }
  
  /* Error */
  public boolean unregisterBillboard(Billboard paramBillboard)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 23	com/upsight/android/marketing/internal/billboard/BillboardManagerImpl:mUnfilledBillboards	Ljava/util/Map;
    //   6: aload_1
    //   7: invokevirtual 62	com/upsight/android/marketing/internal/billboard/Billboard:getScope	()Ljava/lang/String;
    //   10: invokeinterface 80 2 0
    //   15: astore_1
    //   16: aload_1
    //   17: ifnull +9 -> 26
    //   20: iconst_1
    //   21: istore_2
    //   22: aload_0
    //   23: monitorexit
    //   24: iload_2
    //   25: ireturn
    //   26: iconst_0
    //   27: istore_2
    //   28: goto -6 -> 22
    //   31: astore_1
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_1
    //   35: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	36	0	this	BillboardManagerImpl
    //   0	36	1	paramBillboard	Billboard
    //   21	7	2	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	16	31	finally
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/billboard/BillboardManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */