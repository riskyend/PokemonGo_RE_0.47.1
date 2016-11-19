package com.upsight.android.unity;

import android.support.annotation.NonNull;
import android.util.Log;
import com.upsight.android.UpsightContext;
import com.upsight.android.marketing.UpsightBillboard;
import com.upsight.android.marketing.UpsightMarketingContentStore;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class UpsightMarketingManager
  implements IUpsightExtensionManager
{
  protected static final String TAG = "Upsight-UnityMarketing";
  @NonNull
  private Map<String, BillboardInfo> mBillboardMap = new HashMap();
  @NonNull
  private Set<String> mPreparedBillboards = new HashSet();
  private UpsightContext mUpsight;
  
  public void destroyBillboard(@NonNull final String paramString)
  {
    if (this.mUpsight == null) {
      return;
    }
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        Log.i("Upsight-UnityMarketing", "Destroying billboard for scope: " + paramString);
        ((UpsightMarketingManager.BillboardInfo)UpsightMarketingManager.this.mBillboardMap.remove(paramString)).billboard.destroy();
        UpsightMarketingManager.this.mPreparedBillboards.remove(paramString);
      }
    });
  }
  
  public void init(UpsightContext paramUpsightContext)
  {
    this.mUpsight = paramUpsightContext;
  }
  
  public boolean isContentReadyForBillboardWithScope(@NonNull String paramString)
  {
    if (this.mUpsight == null) {
      return false;
    }
    try
    {
      boolean bool = UpsightMarketingContentStore.isContentReady(this.mUpsight, paramString);
      return bool;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public void onApplicationPaused()
  {
    if (this.mUpsight == null) {
      return;
    }
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        if (this.val$currentScope != null) {
          UpsightMarketingManager.this.mBillboardMap.remove(this.val$currentScope);
        }
        UpsightMarketingManager.this.mPreparedBillboards.addAll(UpsightMarketingManager.this.mBillboardMap.keySet());
        Iterator localIterator = UpsightMarketingManager.this.mBillboardMap.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          ((UpsightMarketingManager.BillboardInfo)UpsightMarketingManager.this.mBillboardMap.get(str)).billboard.destroy();
        }
        UpsightMarketingManager.this.mBillboardMap.clear();
      }
    });
  }
  
  public void onApplicationResumed()
  {
    if (this.mUpsight == null) {
      return;
    }
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        Iterator localIterator = UpsightMarketingManager.this.mPreparedBillboards.iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          BillboardHandler localBillboardHandler = new BillboardHandler(UnityBridge.getActivity());
          UpsightBillboard localUpsightBillboard = UpsightBillboard.create(UpsightMarketingManager.this.mUpsight, str, localBillboardHandler);
          UpsightMarketingManager.this.mBillboardMap.put(str, new UpsightMarketingManager.BillboardInfo(localUpsightBillboard, localBillboardHandler));
        }
        UpsightMarketingManager.this.mPreparedBillboards.clear();
      }
    });
  }
  
  public void prepareBillboard(@NonNull final String paramString)
  {
    if (this.mUpsight == null) {
      return;
    }
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        if ((UpsightMarketingManager.this.mBillboardMap.containsKey(paramString)) || (UpsightMarketingManager.this.mPreparedBillboards.contains(paramString))) {
          return;
        }
        BillboardHandler localBillboardHandler = new BillboardHandler(UnityBridge.getActivity());
        UpsightBillboard localUpsightBillboard = UpsightBillboard.create(UpsightMarketingManager.this.mUpsight, paramString, localBillboardHandler);
        UpsightMarketingManager.this.mBillboardMap.put(paramString, new UpsightMarketingManager.BillboardInfo(localUpsightBillboard, localBillboardHandler));
      }
    });
  }
  
  private static class BillboardInfo
  {
    @NonNull
    public final UpsightBillboard billboard;
    @NonNull
    public final BillboardHandler handler;
    
    public BillboardInfo(@NonNull UpsightBillboard paramUpsightBillboard, @NonNull BillboardHandler paramBillboardHandler)
    {
      this.billboard = paramUpsightBillboard;
      this.handler = paramBillboardHandler;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/unity/UpsightMarketingManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */