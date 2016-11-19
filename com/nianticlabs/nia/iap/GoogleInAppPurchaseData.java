package com.nianticlabs.nia.iap;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

final class GoogleInAppPurchaseData
{
  private static final String TAG = "GoogleInAppPurchaseData";
  private String developerPayload;
  private String orderId;
  private String packageName;
  private String productId;
  private String purchaseState;
  private long purchaseTime;
  
  static GoogleInAppPurchaseData fromJson(String paramString)
  {
    try
    {
      paramString = new JSONObject(paramString);
      GoogleInAppPurchaseData localGoogleInAppPurchaseData = new GoogleInAppPurchaseData();
      localGoogleInAppPurchaseData.orderId = stringFromJson(paramString, "orderId");
      localGoogleInAppPurchaseData.packageName = stringFromJson(paramString, "packageName");
      localGoogleInAppPurchaseData.productId = stringFromJson(paramString, "productId");
      localGoogleInAppPurchaseData.purchaseTime = longFromJson(paramString, "purchaseTime");
      localGoogleInAppPurchaseData.purchaseState = stringFromJson(paramString, "purchaseState");
      localGoogleInAppPurchaseData.developerPayload = stringFromJson(paramString, "developerPayload");
      return localGoogleInAppPurchaseData;
    }
    catch (JSONException paramString)
    {
      Log.e("GoogleInAppPurchaseData", "Failed to parse GoogleInAppPurchaseData: %s", paramString);
    }
    return null;
  }
  
  private static long longFromJson(JSONObject paramJSONObject, String paramString)
  {
    try
    {
      long l = paramJSONObject.getLong(paramString);
      return l;
    }
    catch (JSONException paramJSONObject) {}
    return 0L;
  }
  
  private static String stringFromJson(JSONObject paramJSONObject, String paramString)
  {
    try
    {
      paramJSONObject = paramJSONObject.getString(paramString);
      return paramJSONObject;
    }
    catch (JSONException paramJSONObject) {}
    return "";
  }
  
  String getDeveloperPayload()
  {
    return this.developerPayload;
  }
  
  String getOrderId()
  {
    return this.orderId;
  }
  
  String getPackageName()
  {
    return this.packageName;
  }
  
  String getProductId()
  {
    return this.productId;
  }
  
  String getPurchaseState()
  {
    return this.purchaseState;
  }
  
  long getPurchaseTime()
  {
    return this.purchaseTime;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/iap/GoogleInAppPurchaseData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */