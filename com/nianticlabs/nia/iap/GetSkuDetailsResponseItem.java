package com.nianticlabs.nia.iap;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

class GetSkuDetailsResponseItem
{
  private static final String TAG = "SkuDetailsResponseItem";
  private String description;
  private String price;
  private String price_amount_micros;
  private String price_currency_code;
  private String productId;
  private String title;
  private String type;
  
  static GetSkuDetailsResponseItem fromJson(String paramString)
  {
    try
    {
      paramString = new JSONObject(paramString);
      GetSkuDetailsResponseItem localGetSkuDetailsResponseItem = new GetSkuDetailsResponseItem();
      localGetSkuDetailsResponseItem.productId = stringFromJson(paramString, "productId");
      localGetSkuDetailsResponseItem.type = stringFromJson(paramString, "type");
      localGetSkuDetailsResponseItem.price = stringFromJson(paramString, "price");
      localGetSkuDetailsResponseItem.price_amount_micros = stringFromJson(paramString, "price_amount_micros");
      localGetSkuDetailsResponseItem.price_currency_code = stringFromJson(paramString, "price_currency_code");
      localGetSkuDetailsResponseItem.title = stringFromJson(paramString, "title");
      localGetSkuDetailsResponseItem.description = stringFromJson(paramString, "description");
      return localGetSkuDetailsResponseItem;
    }
    catch (JSONException paramString)
    {
      Log.e("SkuDetailsResponseItem", "Failed to parse GetSkuDetailsResponseItem", paramString);
    }
    return null;
  }
  
  static GetSkuDetailsResponseItem fromPurchasableItemDetails(PurchasableItemDetails paramPurchasableItemDetails)
  {
    GetSkuDetailsResponseItem localGetSkuDetailsResponseItem = new GetSkuDetailsResponseItem();
    localGetSkuDetailsResponseItem.productId = paramPurchasableItemDetails.getItemId();
    localGetSkuDetailsResponseItem.type = "inapp";
    localGetSkuDetailsResponseItem.price = paramPurchasableItemDetails.getPrice();
    localGetSkuDetailsResponseItem.price_amount_micros = "";
    localGetSkuDetailsResponseItem.price_currency_code = "";
    localGetSkuDetailsResponseItem.title = paramPurchasableItemDetails.getTitle();
    localGetSkuDetailsResponseItem.description = paramPurchasableItemDetails.getDescription();
    return localGetSkuDetailsResponseItem;
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
  
  static PurchasableItemDetails toPurchasableItemDetails(GetSkuDetailsResponseItem paramGetSkuDetailsResponseItem)
  {
    return new PurchasableItemDetails(paramGetSkuDetailsResponseItem.productId, paramGetSkuDetailsResponseItem.title, paramGetSkuDetailsResponseItem.description, paramGetSkuDetailsResponseItem.price);
  }
  
  String getCurrencyCode()
  {
    return this.price_currency_code;
  }
  
  int getPriceE6()
  {
    try
    {
      int i = Integer.parseInt(this.price_amount_micros);
      return i;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return 0;
  }
  
  String getProductId()
  {
    return this.productId;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/iap/GetSkuDetailsResponseItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */