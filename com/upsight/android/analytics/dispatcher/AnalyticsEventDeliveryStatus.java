package com.upsight.android.analytics.dispatcher;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.persistence.annotation.UpsightStorableIdentifier;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("upsight.dispatcher.delivery.status")
public final class AnalyticsEventDeliveryStatus
{
  @Expose
  @SerializedName("id")
  @UpsightStorableIdentifier
  String id;
  @Expose
  @SerializedName("failure_reason")
  private String mFailureReason;
  @Expose
  @SerializedName("source_event_id")
  private String mOriginEventId;
  @Expose
  @SerializedName("status")
  private boolean mStatus;
  
  AnalyticsEventDeliveryStatus() {}
  
  AnalyticsEventDeliveryStatus(String paramString1, boolean paramBoolean, String paramString2)
  {
    this.mOriginEventId = paramString1;
    this.mStatus = paramBoolean;
    this.mFailureReason = paramString2;
  }
  
  public static AnalyticsEventDeliveryStatus fromFailure(String paramString1, String paramString2)
  {
    return new AnalyticsEventDeliveryStatus(paramString1, false, paramString2);
  }
  
  public static AnalyticsEventDeliveryStatus fromSuccess(String paramString)
  {
    return new AnalyticsEventDeliveryStatus(paramString, true, null);
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
      paramObject = (AnalyticsEventDeliveryStatus)paramObject;
    } while ((this.id != null) && (((AnalyticsEventDeliveryStatus)paramObject).id != null) && (this.id.equals(((AnalyticsEventDeliveryStatus)paramObject).id)));
    return false;
  }
  
  public String getFailureReason()
  {
    return this.mFailureReason;
  }
  
  public String getSourceEventId()
  {
    return this.mOriginEventId;
  }
  
  public int hashCode()
  {
    if (this.id != null) {
      return this.id.hashCode();
    }
    return 0;
  }
  
  public boolean wasDelivered()
  {
    return this.mStatus;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/dispatcher/AnalyticsEventDeliveryStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */