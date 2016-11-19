package com.upsight.android.analytics.internal.dispatcher.routing;

import com.upsight.android.analytics.internal.dispatcher.delivery.Queue;

class RouteStep
{
  private String mFailureReason;
  private Queue mQueue;
  
  public RouteStep(Queue paramQueue)
  {
    this.mQueue = paramQueue;
  }
  
  public RouteStep(RouteStep paramRouteStep)
  {
    this(paramRouteStep.mQueue);
  }
  
  public String getFailureReason()
  {
    return this.mFailureReason;
  }
  
  public Queue getQueue()
  {
    return this.mQueue;
  }
  
  public void setFailureReason(String paramString)
  {
    this.mFailureReason = paramString;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/routing/RouteStep.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */