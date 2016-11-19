package com.upsight.android.analytics.dispatcher;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.persistence.annotation.UpsightStorableIdentifier;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.dispatcher.response")
public final class EndpointResponse
{
  @Expose
  @SerializedName("id")
  @UpsightStorableIdentifier
  String id;
  @Expose
  @SerializedName("content")
  private String mContent;
  @Expose
  @SerializedName("type")
  private String mType;
  
  EndpointResponse() {}
  
  EndpointResponse(String paramString1, String paramString2)
  {
    this.mType = paramString1;
    this.mContent = paramString2;
  }
  
  public static EndpointResponse create(String paramString1, String paramString2)
  {
    return new EndpointResponse(paramString1, paramString2);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
      paramObject = (EndpointResponse)paramObject;
    } while ((this.id != null) && (((EndpointResponse)paramObject).id != null) && (this.id.equals(((EndpointResponse)paramObject).id)));
    return false;
  }
  
  public String getContent()
  {
    return this.mContent;
  }
  
  public String getType()
  {
    return this.mType;
  }
  
  public int hashCode()
  {
    if (this.id != null) {
      return this.id.hashCode();
    }
    return 0;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/dispatcher/EndpointResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */