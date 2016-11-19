package com.upsight.android.marketing.internal.content;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.internal.util.GsonHelper.JSONObjectSerializer;
import com.upsight.android.marketing.UpsightReward;
import java.io.IOException;
import org.json.JSONObject;

public final class Reward
  implements UpsightReward
{
  @Expose
  @SerializedName("product")
  String product;
  @Expose
  @SerializedName("quantity")
  int quantity;
  @Expose
  @SerializedName("signature_data")
  JsonObject signatureData;
  
  static UpsightReward from(JsonElement paramJsonElement, Gson paramGson)
    throws IOException
  {
    try
    {
      paramJsonElement = (UpsightReward)paramGson.fromJson(paramJsonElement, Reward.class);
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
  
  public JSONObject getSignatureData()
  {
    return GsonHelper.JSONObjectSerializer.fromJsonObject(this.signatureData);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/Reward.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */