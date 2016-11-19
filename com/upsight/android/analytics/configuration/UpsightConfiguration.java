package com.upsight.android.analytics.configuration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.persistence.annotation.UpsightStorableIdentifier;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.configuration")
public final class UpsightConfiguration
{
  @Expose
  @SerializedName("id")
  @UpsightStorableIdentifier
  String id;
  @Expose
  @SerializedName("scope")
  private String mScope;
  @Expose
  @SerializedName("session_num_created")
  private int mSessionNumCreated;
  @Expose
  @SerializedName("value")
  private String mValue;
  
  UpsightConfiguration() {}
  
  UpsightConfiguration(String paramString1, String paramString2, int paramInt)
  {
    this.mScope = paramString1;
    this.mValue = paramString2;
    this.mSessionNumCreated = paramInt;
  }
  
  public static UpsightConfiguration create(String paramString1, String paramString2, int paramInt)
  {
    return new UpsightConfiguration(paramString1, paramString2, paramInt);
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
      paramObject = (UpsightConfiguration)paramObject;
    } while ((this.id != null) && (((UpsightConfiguration)paramObject).id != null) && (this.id.equals(((UpsightConfiguration)paramObject).id)));
    return false;
  }
  
  public String getConfiguration()
  {
    return this.mValue;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getScope()
  {
    return this.mScope;
  }
  
  public int getSessionNumberCreated()
  {
    return this.mSessionNumCreated;
  }
  
  public int hashCode()
  {
    if (this.id != null) {
      return this.id.hashCode();
    }
    return 0;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/configuration/UpsightConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */