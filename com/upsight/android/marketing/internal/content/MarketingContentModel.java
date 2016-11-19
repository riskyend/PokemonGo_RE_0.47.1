package com.upsight.android.marketing.internal.content;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;

public final class MarketingContentModel
{
  @Expose
  @SerializedName("content_id")
  String contentId;
  @Expose
  @SerializedName("context")
  JsonElement context;
  @Expose
  @SerializedName("presentation")
  Presentation presentation;
  @Expose
  @SerializedName("url")
  String templateUrl;
  
  static MarketingContentModel from(JsonElement paramJsonElement, Gson paramGson)
    throws IOException
  {
    try
    {
      paramJsonElement = (MarketingContentModel)paramGson.fromJson(paramJsonElement, MarketingContentModel.class);
      return paramJsonElement;
    }
    catch (JsonSyntaxException paramJsonElement)
    {
      throw new IOException(paramJsonElement);
    }
  }
  
  public String getContentID()
  {
    return this.contentId;
  }
  
  public JsonElement getContext()
  {
    return this.context;
  }
  
  public MarketingContentModel.Presentation.DialogLayout getDialogLayouts()
  {
    if (this.presentation != null) {
      return this.presentation.layout;
    }
    return null;
  }
  
  public String getPresentationStyle()
  {
    if (this.presentation != null) {
      return this.presentation.style;
    }
    return null;
  }
  
  public String getTemplateUrl()
  {
    return this.templateUrl;
  }
  
  public static class Presentation
  {
    public static final String STYLE_DIALOG = "dialog";
    public static final String STYLE_FULLSCREEN = "fullscreen";
    @Expose
    @SerializedName("layout")
    DialogLayout layout;
    @Expose
    @SerializedName("style")
    String style;
    
    public static class DialogLayout
    {
      @Expose
      @SerializedName("landscape")
      public Dimensions landscape;
      @Expose
      @SerializedName("portrait")
      public Dimensions portrait;
      
      public static class Dimensions
      {
        @Expose
        @SerializedName("h")
        public int h;
        @Expose
        @SerializedName("w")
        public int w;
        @Expose
        @SerializedName("x")
        public int x;
        @Expose
        @SerializedName("y")
        public int y;
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/MarketingContentModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */