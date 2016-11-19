package com.upsight.android.marketing.internal.content;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.marketing.UpsightPurchase;
import java.io.IOException;

public final class Purchase
  implements UpsightPurchase
{
  @Expose
  @SerializedName("product")
  String product;
  @Expose
  @SerializedName("quantity")
  int quantity;
  
  static UpsightPurchase from(JsonElement paramJsonElement, Gson paramGson)
    throws IOException
  {
    try
    {
      paramJsonElement = (UpsightPurchase)paramGson.fromJson(paramJsonElement, Purchase.class);
      return paramJsonElement;
    }
    catch (JsonSyntaxException paramJsonElement)
    {
      throw new IOException(paramJsonElement);
    }
  }
  
  public String getProduct()
  {
    return this.product;
  }
  
  public int getQuantity()
  {
    return this.quantity;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/Purchase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */