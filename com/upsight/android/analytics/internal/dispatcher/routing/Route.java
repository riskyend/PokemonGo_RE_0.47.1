package com.upsight.android.analytics.internal.dispatcher.routing;

import com.upsight.android.analytics.internal.dispatcher.delivery.Queue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class Route
{
  private int mCurrentStepIndex = 0;
  private List<RouteStep> mSteps;
  
  public Route(Route paramRoute)
  {
    this(paramRoute.mSteps);
  }
  
  public Route(List<RouteStep> paramList)
  {
    this.mSteps = new ArrayList(paramList.size());
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      RouteStep localRouteStep = (RouteStep)paramList.next();
      this.mSteps.add(new RouteStep(localRouteStep));
    }
  }
  
  public void failedOnCurrentStep(String paramString)
  {
    ((RouteStep)this.mSteps.get(this.mCurrentStepIndex)).setFailureReason(paramString);
  }
  
  public Queue getCurrentQueue()
  {
    return ((RouteStep)this.mSteps.get(this.mCurrentStepIndex)).getQueue();
  }
  
  public String[] getRoutingStack()
  {
    LinkedList localLinkedList = new LinkedList();
    Iterator localIterator = this.mSteps.iterator();
    RouteStep localRouteStep;
    StringBuilder localStringBuilder;
    if (localIterator.hasNext())
    {
      localRouteStep = (RouteStep)localIterator.next();
      localStringBuilder = new StringBuilder().append(localRouteStep.getQueue().getName()).append(" - ");
      if (localRouteStep.getFailureReason() != null) {
        break label117;
      }
    }
    label117:
    for (String str = "delivered";; str = localRouteStep.getFailureReason())
    {
      localLinkedList.add(str);
      if (localRouteStep.getFailureReason() != null) {
        break;
      }
      return (String[])localLinkedList.toArray(new String[localLinkedList.size()]);
    }
  }
  
  public int getStepsCount()
  {
    return this.mSteps.size();
  }
  
  public boolean hasMoreSteps()
  {
    return this.mCurrentStepIndex < this.mSteps.size() - 1;
  }
  
  public void moveToNextStep()
  {
    if (!hasMoreSteps()) {
      throw new IllegalStateException("There are no more steps to move on");
    }
    this.mCurrentStepIndex += 1;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/routing/Route.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */