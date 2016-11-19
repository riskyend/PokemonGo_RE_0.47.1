package com.upsight.android.marketing.internal.vast;

import android.text.TextUtils;
import android.util.Base64;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class VastContentModel
{
  @Expose
  @SerializedName("adapter_id")
  Integer adapterId;
  @Expose
  @SerializedName("is_rewarded")
  Boolean isRewarded;
  @Expose
  @SerializedName("max_vast_file_size")
  String maxVastFileSize;
  @Expose
  @SerializedName("settings")
  Settings settings;
  @Expose
  @SerializedName("should_validate_schema")
  Boolean shouldValidateSchema;
  
  private void appendEndcard(Map<String, String> paramMap)
  {
    if ((TextUtils.isEmpty(this.settings.endcardScript)) || ((this.settings.isEndcardScriptEncoded != null) && (this.settings.isEndcardScriptEncoded.booleanValue()))) {}
    try
    {
      paramMap.put("endcard_script", Arrays.toString(Base64.decode(this.settings.endcardScript, 0)));
      return;
    }
    catch (IllegalArgumentException paramMap) {}
    paramMap.put("endcard_script", this.settings.endcardScript);
    return;
  }
  
  static VastContentModel from(JsonElement paramJsonElement, Gson paramGson)
    throws IOException
  {
    try
    {
      paramJsonElement = (VastContentModel)paramGson.fromJson(paramJsonElement, VastContentModel.class);
      return paramJsonElement;
    }
    catch (JsonSyntaxException paramJsonElement)
    {
      throw new IOException(paramJsonElement);
    }
  }
  
  public Integer getAdapterId()
  {
    return this.adapterId;
  }
  
  public String getMaxVastFileSize()
  {
    return this.maxVastFileSize;
  }
  
  public HashMap<String, String> getSettings()
  {
    new HashMap() {};
  }
  
  public Boolean isRewarded()
  {
    return this.isRewarded;
  }
  
  public Boolean shouldValidateSchema()
  {
    return this.shouldValidateSchema;
  }
  
  public static class Settings
  {
    @Expose
    @SerializedName("beacons")
    Beacons beacons;
    @Expose
    @SerializedName("campaign_id")
    Integer campaignId;
    @Expose
    @SerializedName("cb_ms")
    Integer cbMs;
    @Expose
    @SerializedName("cta")
    String cta;
    @Expose
    @SerializedName("endcard_script")
    String endcardScript;
    @Expose
    @SerializedName("id")
    Integer id;
    @Expose
    @SerializedName("is_endcard_script_encoded")
    Boolean isEndcardScriptEncoded;
    @Expose
    @SerializedName("postroll")
    Integer postroll;
    @Expose
    @SerializedName("script")
    String script;
    @Expose
    @SerializedName("t")
    Integer t;
    
    public static class Beacons
    {
      @Expose
      @SerializedName("click")
      String click;
      @Expose
      @SerializedName("impression")
      String impression;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/vast/VastContentModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */