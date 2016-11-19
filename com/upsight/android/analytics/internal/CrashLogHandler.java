package com.upsight.android.analytics.internal;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.UpsightAnalyticsApi;
import com.upsight.android.analytics.event.UpsightAnalyticsEvent;
import com.upsight.android.analytics.event.UpsightAnalyticsEvent.Builder;
import com.upsight.android.analytics.event.UpsightPublisherData;
import com.upsight.android.analytics.event.UpsightPublisherData.Builder;
import com.upsight.android.persistence.annotation.UpsightStorableType;
import java.io.PrintWriter;
import java.io.StringWriter;

class CrashLogHandler
  implements Thread.UncaughtExceptionHandler
{
  private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;
  private UpsightAnalyticsApi mUpsightAnalytics;
  
  public CrashLogHandler(UpsightAnalyticsApi paramUpsightAnalyticsApi)
  {
    this.mUpsightAnalytics = paramUpsightAnalyticsApi;
    this.mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
  }
  
  private void sendToServer(String paramString)
  {
    paramString = new CrashLogHandler.CrashLogEvent.Builder(paramString).build();
    this.mUpsightAnalytics.record(paramString);
  }
  
  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    for (Object localObject = paramThrowable; localObject != null; localObject = ((Throwable)localObject).getCause()) {
      ((Throwable)localObject).printStackTrace(localPrintWriter);
    }
    localObject = localStringWriter.toString();
    localPrintWriter.close();
    sendToServer((String)localObject);
    this.mDefaultExceptionHandler.uncaughtException(paramThread, paramThrowable);
  }
  
  @UpsightStorableType("upsight.crash_log")
  public static class CrashLogEvent
    extends UpsightAnalyticsEvent<UpsightData, UpsightPublisherData>
  {
    protected CrashLogEvent() {}
    
    protected CrashLogEvent(String paramString, UpsightData paramUpsightData, UpsightPublisherData paramUpsightPublisherData)
    {
      super(paramUpsightData, paramUpsightPublisherData);
    }
    
    public static class Builder
      extends UpsightAnalyticsEvent.Builder<CrashLogHandler.CrashLogEvent, CrashLogHandler.CrashLogEvent.UpsightData, UpsightPublisherData>
    {
      private String crashID;
      private UpsightPublisherData publisherData;
      private String stacktrace;
      
      public Builder(String paramString)
      {
        this.stacktrace = paramString;
        this.publisherData = new UpsightPublisherData.Builder().build();
      }
      
      public CrashLogHandler.CrashLogEvent build()
      {
        return new CrashLogHandler.CrashLogEvent("upsight.crashlog", new CrashLogHandler.CrashLogEvent.UpsightData(this), this.publisherData);
      }
      
      public Builder setCrashId(String paramString)
      {
        this.crashID = paramString;
        return this;
      }
      
      public Builder with(UpsightPublisherData paramUpsightPublisherData)
      {
        this.publisherData = paramUpsightPublisherData;
        return this;
      }
    }
    
    public static class UpsightData
    {
      @Expose
      @SerializedName("crashID")
      String crashID;
      @Expose
      @SerializedName("stacktrace")
      String stacktrace;
      
      protected UpsightData() {}
      
      protected UpsightData(CrashLogHandler.CrashLogEvent.Builder paramBuilder)
      {
        this.stacktrace = paramBuilder.stacktrace;
        this.crashID = paramBuilder.crashID;
      }
      
      public String getCrashID()
      {
        return this.crashID;
      }
      
      public String getStacktrace()
      {
        return this.stacktrace;
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/CrashLogHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */