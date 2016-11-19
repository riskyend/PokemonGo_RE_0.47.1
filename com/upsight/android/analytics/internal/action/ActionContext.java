package com.upsight.android.analytics.internal.action;

import com.google.gson.Gson;
import com.squareup.otto.Bus;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.logger.UpsightLogger;
import rx.Scheduler.Worker;

public class ActionContext
{
  public final Bus mBus;
  public final Clock mClock;
  public final Gson mGson;
  public final UpsightLogger mLogger;
  public final Scheduler.Worker mMainWorker;
  public final UpsightContext mUpsight;
  
  public ActionContext(UpsightContext paramUpsightContext, Bus paramBus, Gson paramGson, Clock paramClock, Scheduler.Worker paramWorker, UpsightLogger paramUpsightLogger)
  {
    this.mUpsight = paramUpsightContext;
    this.mBus = paramBus;
    this.mGson = paramGson;
    this.mClock = paramClock;
    this.mMainWorker = paramWorker;
    this.mLogger = paramUpsightLogger;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/action/ActionContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */