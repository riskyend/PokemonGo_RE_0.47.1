package com.upsight.android.analytics.internal;

import android.content.Intent;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightException;
import com.upsight.android.analytics.UpsightGooglePlayHelper;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.monetization.UpsightMonetizationIapEvent;
import com.upsight.android.analytics.event.monetization.UpsightMonetizationIapEvent.Builder;
import com.upsight.android.logger.UpsightLogger;
import org.json.JSONException;
import org.json.JSONObject;

class GooglePlayHelper
  extends UpsightGooglePlayHelper
{
  private static final String STORE_NAME = "google_play";
  private Gson mGson;
  private UpsightContext mUpsight;
  
  GooglePlayHelper(UpsightContext paramUpsightContext, Gson paramGson)
  {
    this.mUpsight = paramUpsightContext;
    this.mGson = paramGson;
  }
  
  private JSONObject createIapBundle(int paramInt, String paramString1, String paramString2)
    throws JSONException
  {
    return new JSONObject().put("RESPONSE_CODE", paramInt).put("INAPP_PURCHASE_DATA", paramString1).put("INAPP_DATA_SIGNATURE", paramString2);
  }
  
  public void trackPurchase(int paramInt, String paramString1, double paramDouble1, double paramDouble2, String paramString2, Intent paramIntent, UpsightPublisherData paramUpsightPublisherData)
    throws UpsightException
  {
    int i = paramIntent.getIntExtra("RESPONSE_CODE", Integer.MIN_VALUE);
    switch (i)
    {
    default: 
      paramString1 = "Failed to track Google Play purchase. See response code for details. responseCode=" + i;
      this.mUpsight.getLogger().e("Upsight", paramString1, new Object[0]);
      throw new UpsightException(paramString1, new Object[0]);
    case 1: 
      UpsightMonetizationIapEvent.createBuilder("google_play", null, Double.valueOf(paramDouble2), Double.valueOf(paramDouble1), Integer.valueOf(paramInt), paramString1, paramString2).setResolution(Resolution.cancel.toString()).put(paramUpsightPublisherData).record(this.mUpsight);
      return;
    }
    String str1 = paramIntent.getStringExtra("INAPP_PURCHASE_DATA");
    String str2 = paramIntent.getStringExtra("INAPP_DATA_SIGNATURE");
    if (TextUtils.isEmpty(str1))
    {
      this.mUpsight.getLogger().e("Upsight", "Failed to track Google Play purchase due to null or empty purchase data.", new Object[0]);
      throw new UpsightException("Failed to track Google Play purchase due to null or empty purchase data.", new Object[0]);
    }
    if (TextUtils.isEmpty(str2))
    {
      this.mUpsight.getLogger().e("Upsight", "Failed to track Google Play purchase due to null or empty data signature.", new Object[0]);
      throw new UpsightException("Failed to track Google Play purchase due to null or empty data signature.", new Object[0]);
    }
    try
    {
      paramIntent = (PurchaseData)this.mGson.fromJson(str1, PurchaseData.class);
      if (paramIntent == null) {
        break label470;
      }
      switch (paramIntent.purchaseState)
      {
      default: 
        this.mUpsight.getLogger().e("Upsight", "Failed to track Google Play purchase. Invalid purchase state.", new Object[0]);
        throw new UpsightException("Failed to track Google Play purchase. Invalid purchase state.", new Object[0]);
      }
    }
    catch (JsonSyntaxException paramString1)
    {
      this.mUpsight.getLogger().e("Upsight", paramString1, "Failed to track Google Play purchase due to malformed purchase data JSON.", new Object[0]);
      throw new UpsightException(paramString1, "Failed to track Google Play purchase due to malformed purchase data JSON.", new Object[0]);
    }
    paramIntent = Resolution.buy;
    for (;;)
    {
      try
      {
        UpsightMonetizationIapEvent.createBuilder("google_play", createIapBundle(i, str1, str2), Double.valueOf(paramDouble2), Double.valueOf(paramDouble1), Integer.valueOf(paramInt), paramString1, paramString2).setResolution(paramIntent.toString()).put(paramUpsightPublisherData).record(this.mUpsight);
        return;
      }
      catch (JSONException paramString1)
      {
        this.mUpsight.getLogger().e("Upsight", paramString1, "Failed to track Google Play purchase. Unable to create iap_bundle.", new Object[0]);
        throw new UpsightException(paramString1, "Failed to track Google Play purchase. Unable to create iap_bundle.", new Object[0]);
      }
      paramIntent = Resolution.cancel;
      continue;
      paramIntent = Resolution.refund;
    }
    label470:
    this.mUpsight.getLogger().e("Upsight", "Failed to track Google Play purchase due to missing fields in purchase data.", new Object[0]);
    throw new UpsightException("Failed to track Google Play purchase due to missing fields in purchase data.", new Object[0]);
  }
  
  static class PurchaseData
  {
    @Expose
    @SerializedName("developerPayload")
    String developerPayload;
    @Expose
    @SerializedName("orderId")
    String orderId;
    @Expose
    @SerializedName("packageName")
    String packageName;
    @Expose
    @SerializedName("productId")
    String productId;
    @Expose
    @SerializedName("purchaseState")
    int purchaseState;
    @Expose
    @SerializedName("purchaseTime")
    long purchaseTime;
    @Expose
    @SerializedName("purchaseToken")
    String purchaseToken;
  }
  
  public static enum Resolution
  {
    buy,  cancel,  refund;
    
    private Resolution() {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/GooglePlayHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */