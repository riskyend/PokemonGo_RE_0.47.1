package com.upsight.android.analytics.provider;

import com.google.gson.annotations.Expose;
import com.upsight.android.UpsightAnalyticsExtension;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.UpsightAnalyticsApi;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.annotation.UpsightStorableIdentifier;
import com.upsight.android.persistence.annotation.UpsightStorableType;

public abstract class UpsightLocationTracker
{
  public static void purge(UpsightContext paramUpsightContext)
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null)
    {
      localUpsightAnalyticsExtension.getApi().purgeLocation();
      return;
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
  }
  
  public static void purge(UpsightSessionContext paramUpsightSessionContext)
  {
    purge(paramUpsightSessionContext.getUpsightContext());
  }
  
  public static void track(UpsightContext paramUpsightContext, Data paramData)
  {
    UpsightAnalyticsExtension localUpsightAnalyticsExtension = (UpsightAnalyticsExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.analytics");
    if (localUpsightAnalyticsExtension != null)
    {
      localUpsightAnalyticsExtension.getApi().trackLocation(paramData);
      return;
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.analytics must be registered in your Android Manifest", new Object[0]);
  }
  
  public static void track(UpsightSessionContext paramUpsightSessionContext, Data paramData)
  {
    track(paramUpsightSessionContext.getUpsightContext(), paramData);
  }
  
  public abstract void purge();
  
  public abstract void track(Data paramData);
  
  @UpsightStorableType("upsight.model.location")
  public static final class Data
  {
    @UpsightStorableIdentifier
    String id;
    @Expose
    double latitude;
    @Expose
    double longitude;
    
    Data() {}
    
    private Data(double paramDouble1, double paramDouble2)
    {
      this.latitude = paramDouble1;
      this.longitude = paramDouble2;
    }
    
    public static Data create(double paramDouble1, double paramDouble2)
    {
      return new Data(paramDouble1, paramDouble2);
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
        paramObject = (Data)paramObject;
        if (this.id == null) {
          break;
        }
      } while (this.id.equals(((Data)paramObject).id));
      for (;;)
      {
        return false;
        if (((Data)paramObject).id == null) {
          break;
        }
      }
    }
    
    public double getLatitude()
    {
      return this.latitude;
    }
    
    public double getLongitude()
    {
      return this.longitude;
    }
    
    public int hashCode()
    {
      if (this.id != null) {
        return this.id.hashCode();
      }
      return 0;
    }
    
    public void setLatitude(double paramDouble)
    {
      this.latitude = paramDouble;
    }
    
    public void setLongitude(double paramDouble)
    {
      this.longitude = paramDouble;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/provider/UpsightLocationTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */