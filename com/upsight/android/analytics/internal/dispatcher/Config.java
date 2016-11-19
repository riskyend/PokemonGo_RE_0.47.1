package com.upsight.android.analytics.internal.dispatcher;

import com.upsight.android.analytics.internal.dispatcher.routing.RoutingConfig;
import com.upsight.android.analytics.internal.dispatcher.schema.IdentifierConfig;

class Config
{
  private IdentifierConfig mIdentifierConfig;
  private RoutingConfig mRoutingConfig;
  
  private Config(Builder paramBuilder)
  {
    this.mRoutingConfig = paramBuilder.mRoutingConfig;
    this.mIdentifierConfig = paramBuilder.mIdentifierConfig;
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
      paramObject = (Config)paramObject;
      if (this.mIdentifierConfig != null)
      {
        if (this.mIdentifierConfig.equals(((Config)paramObject).mIdentifierConfig)) {}
      }
      else {
        while (((Config)paramObject).mIdentifierConfig != null) {
          return false;
        }
      }
      if (this.mRoutingConfig == null) {
        break;
      }
    } while (this.mRoutingConfig.equals(((Config)paramObject).mRoutingConfig));
    for (;;)
    {
      return false;
      if (((Config)paramObject).mRoutingConfig == null) {
        break;
      }
    }
  }
  
  public IdentifierConfig getIdentifierConfig()
  {
    return this.mIdentifierConfig;
  }
  
  public RoutingConfig getRoutingConfig()
  {
    return this.mRoutingConfig;
  }
  
  public int hashCode()
  {
    int j = 0;
    if (this.mRoutingConfig != null) {}
    for (int i = this.mRoutingConfig.hashCode();; i = 0)
    {
      if (this.mIdentifierConfig != null) {
        j = this.mIdentifierConfig.hashCode();
      }
      return i * 31 + j;
    }
  }
  
  public boolean isValid()
  {
    return (this.mRoutingConfig != null) && (this.mRoutingConfig.isValid());
  }
  
  public static class Builder
  {
    private IdentifierConfig mIdentifierConfig;
    private RoutingConfig mRoutingConfig;
    
    public Config build()
    {
      return new Config(this, null);
    }
    
    public Builder setIdentifierConfig(IdentifierConfig paramIdentifierConfig)
    {
      this.mIdentifierConfig = paramIdentifierConfig;
      return this;
    }
    
    public Builder setRoutingConfig(RoutingConfig paramRoutingConfig)
    {
      this.mRoutingConfig = paramRoutingConfig;
      return this;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/Config.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */