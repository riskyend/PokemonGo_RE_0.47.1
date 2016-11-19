package com.upsight.android.unity;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.upsight.android.marketing.UpsightBillboard.AttachParameters;
import com.upsight.android.marketing.UpsightBillboardHandlers.DefaultHandler;
import com.upsight.android.marketing.UpsightPurchase;
import com.upsight.android.marketing.UpsightReward;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class BillboardHandler
  extends UpsightBillboardHandlers.DefaultHandler
{
  protected static final String TAG = "UpsightBillboardHandler";
  @Nullable
  private static String mCurrentScope;
  
  public BillboardHandler(Activity paramActivity)
  {
    super(paramActivity);
  }
  
  @Nullable
  public static String getCurrentScope()
  {
    return mCurrentScope;
  }
  
  @Nullable
  public UpsightBillboard.AttachParameters onAttach(@NonNull String paramString)
  {
    UpsightBillboard.AttachParameters localAttachParameters = super.onAttach(paramString);
    if (localAttachParameters != null)
    {
      mCurrentScope = paramString;
      UnityBridge.UnitySendMessage("onBillboardAppear", paramString);
    }
    return localAttachParameters;
  }
  
  public void onDetach()
  {
    super.onDetach();
    Log.i("UpsightBillboardHandler", "onDetach");
    UnityBridge.UnitySendMessage("onBillboardDismiss", mCurrentScope);
    mCurrentScope = null;
  }
  
  public void onNextView()
  {
    super.onNextView();
    Log.i("UpsightBillboardHandler", "onNextView");
  }
  
  public void onPurchases(@NonNull List<UpsightPurchase> paramList)
  {
    super.onPurchases(paramList);
    Log.i("UpsightBillboardHandler", "onPurchases");
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      UpsightPurchase localUpsightPurchase = (UpsightPurchase)paramList.next();
      try
      {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("productIdentifier", localUpsightPurchase.getProduct());
        localJSONObject.put("quantity", localUpsightPurchase.getQuantity());
        localJSONObject.put("billboardScope", mCurrentScope);
        UnityBridge.UnitySendMessage("billboardDidReceivePurchase", localJSONObject.toString());
      }
      catch (Exception localException)
      {
        Log.i("UpsightBillboardHandler", "Error creating JSON" + localException.getMessage());
      }
    }
  }
  
  public void onRewards(@NonNull List<UpsightReward> paramList)
  {
    super.onRewards(paramList);
    Log.i("UpsightBillboardHandler", "onRewards");
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      UpsightReward localUpsightReward = (UpsightReward)paramList.next();
      try
      {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("productIdentifier", localUpsightReward.getProduct());
        localJSONObject.put("quantity", localUpsightReward.getQuantity());
        localJSONObject.put("signatureData", localUpsightReward.getSignatureData());
        localJSONObject.put("billboardScope", mCurrentScope);
        UnityBridge.UnitySendMessage("billboardDidReceiveReward", localJSONObject.toString());
      }
      catch (Exception localException)
      {
        Log.i("UpsightBillboardHandler", "Error creating JSON" + localException.getMessage());
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/unity/BillboardHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */