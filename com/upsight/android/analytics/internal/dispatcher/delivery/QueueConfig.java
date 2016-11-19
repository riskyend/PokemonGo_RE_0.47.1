package com.upsight.android.analytics.internal.dispatcher.delivery;

import java.net.MalformedURLException;
import java.net.URL;

public class QueueConfig
{
  private BatchSender.Config mBatchSenderConfig;
  private Batcher.Config mBatcherConfig;
  private String mEndpointAddress;
  
  private QueueConfig(Builder paramBuilder)
  {
    this.mEndpointAddress = paramBuilder.mEndpointAddress;
    this.mBatchSenderConfig = paramBuilder.mBatchSenderConfig;
    this.mBatcherConfig = paramBuilder.mBatcherConfig;
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
      paramObject = (QueueConfig)paramObject;
      if (this.mBatchSenderConfig != null)
      {
        if (this.mBatchSenderConfig.equals(((QueueConfig)paramObject).mBatchSenderConfig)) {}
      }
      else {
        while (((QueueConfig)paramObject).mBatchSenderConfig != null) {
          return false;
        }
      }
      if (this.mBatcherConfig != null)
      {
        if (this.mBatcherConfig.equals(((QueueConfig)paramObject).mBatcherConfig)) {}
      }
      else {
        while (((QueueConfig)paramObject).mBatcherConfig != null) {
          return false;
        }
      }
      if (this.mEndpointAddress == null) {
        break;
      }
    } while (this.mEndpointAddress.equals(((QueueConfig)paramObject).mEndpointAddress));
    for (;;)
    {
      return false;
      if (((QueueConfig)paramObject).mEndpointAddress == null) {
        break;
      }
    }
  }
  
  public BatchSender.Config getBatchSenderConfig()
  {
    return this.mBatchSenderConfig;
  }
  
  public Batcher.Config getBatcherConfig()
  {
    return this.mBatcherConfig;
  }
  
  public String getEndpointAddress()
  {
    return this.mEndpointAddress;
  }
  
  public int hashCode()
  {
    int k = 0;
    int i;
    if (this.mEndpointAddress != null)
    {
      i = this.mEndpointAddress.hashCode();
      if (this.mBatchSenderConfig == null) {
        break label64;
      }
    }
    label64:
    for (int j = this.mBatchSenderConfig.hashCode();; j = 0)
    {
      if (this.mBatcherConfig != null) {
        k = this.mBatcherConfig.hashCode();
      }
      return (i * 31 + j) * 31 + k;
      i = 0;
      break;
    }
  }
  
  public boolean isValid()
  {
    boolean bool2 = false;
    try
    {
      new URL(this.mEndpointAddress);
      boolean bool1 = bool2;
      if (this.mBatcherConfig != null)
      {
        bool1 = bool2;
        if (this.mBatcherConfig.isValid())
        {
          bool1 = bool2;
          if (this.mBatchSenderConfig != null)
          {
            boolean bool3 = this.mBatchSenderConfig.isValid();
            bool1 = bool2;
            if (bool3) {
              bool1 = true;
            }
          }
        }
      }
      return bool1;
    }
    catch (MalformedURLException localMalformedURLException) {}
    return false;
  }
  
  public static class Builder
  {
    private BatchSender.Config mBatchSenderConfig;
    private Batcher.Config mBatcherConfig;
    private String mEndpointAddress;
    
    public QueueConfig build()
    {
      return new QueueConfig(this, null);
    }
    
    public Builder setBatchSenderConfig(BatchSender.Config paramConfig)
    {
      this.mBatchSenderConfig = paramConfig;
      return this;
    }
    
    public Builder setBatcherConfig(Batcher.Config paramConfig)
    {
      this.mBatcherConfig = paramConfig;
      return this;
    }
    
    public Builder setEndpointAddress(String paramString)
    {
      this.mEndpointAddress = paramString;
      return this;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/delivery/QueueConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */