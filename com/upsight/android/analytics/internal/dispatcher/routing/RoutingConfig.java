package com.upsight.android.analytics.internal.dispatcher.routing;

import com.upsight.android.analytics.internal.dispatcher.delivery.QueueConfig;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RoutingConfig
{
  private Map<String, QueueConfig> mQueuesConfigs;
  private Map<String, List<String>> mRoutes;
  
  private RoutingConfig(Builder paramBuilder)
  {
    this.mQueuesConfigs = paramBuilder.mQueueConfigs;
    this.mRoutes = paramBuilder.mRoutes;
  }
  
  private boolean areQueueConfigsValid()
  {
    Iterator localIterator = this.mQueuesConfigs.values().iterator();
    while (localIterator.hasNext())
    {
      QueueConfig localQueueConfig = (QueueConfig)localIterator.next();
      if ((localQueueConfig == null) || (!localQueueConfig.isValid())) {
        return false;
      }
    }
    return true;
  }
  
  private boolean areRoutesValid()
  {
    Iterator localIterator1 = this.mRoutes.values().iterator();
    while (localIterator1.hasNext())
    {
      List localList = (List)localIterator1.next();
      if (localList == null) {
        return false;
      }
      Iterator localIterator2 = localList.iterator();
      while (localIterator2.hasNext())
      {
        String str = (String)localIterator2.next();
        if ((!this.mQueuesConfigs.containsKey(str)) && (!"trash".equals(str))) {
          return false;
        }
      }
      if (new HashSet(localList).size() != localList.size()) {
        return false;
      }
    }
    return true;
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
      paramObject = (RoutingConfig)paramObject;
    } while ((((RoutingConfig)paramObject).mQueuesConfigs.equals(this.mQueuesConfigs)) && (((RoutingConfig)paramObject).mRoutes.equals(this.mRoutes)));
    return false;
  }
  
  public Map<String, QueueConfig> getQueueConfigs()
  {
    return Collections.unmodifiableMap(this.mQueuesConfigs);
  }
  
  public Map<String, List<String>> getRouters()
  {
    return Collections.unmodifiableMap(this.mRoutes);
  }
  
  public boolean isValid()
  {
    return (areQueueConfigsValid()) && (areRoutesValid());
  }
  
  public static class Builder
  {
    private Map<String, QueueConfig> mQueueConfigs = new HashMap();
    private Map<String, List<String>> mRoutes = new HashMap();
    
    public Builder addQueueConfig(String paramString, QueueConfig paramQueueConfig)
    {
      if (this.mQueueConfigs.containsKey(paramString)) {
        throw new IllegalArgumentException("Queue with name " + paramString + " already exists");
      }
      this.mQueueConfigs.put(paramString, paramQueueConfig);
      return this;
    }
    
    public Builder addRoute(String paramString, List<String> paramList)
    {
      if (this.mRoutes.containsKey(paramString)) {
        throw new IllegalArgumentException("Filter with name " + paramString + " already exists");
      }
      this.mRoutes.put(paramString, paramList);
      return this;
    }
    
    public RoutingConfig build()
    {
      return new RoutingConfig(this, null);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/routing/RoutingConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */