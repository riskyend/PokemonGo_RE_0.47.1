package com.upsight.android.analytics.internal.dispatcher.delivery;

import com.upsight.android.analytics.internal.dispatcher.routing.Packet;
import com.upsight.android.analytics.internal.dispatcher.schema.Schema;
import java.util.concurrent.TimeUnit;
import rx.Scheduler;
import rx.Scheduler.Worker;
import rx.Subscription;
import rx.functions.Action0;

public class Batcher
{
  private static final int DISABLE_AGING_MAX_AGE = 0;
  private Scheduler mAgingExecutor;
  private Action0 mAgingRunnable = new Action0()
  {
    public void call()
    {
      Batcher.this.sendCurrentBatch();
    }
  };
  private Subscription mAgingTask;
  private BatchSender mBatchSender;
  private Config mConfig;
  private Batch mCurrentBatch;
  private Schema mSchema;
  
  Batcher(Config paramConfig, Schema paramSchema, BatchSender paramBatchSender, Scheduler paramScheduler)
  {
    this.mSchema = paramSchema;
    this.mBatchSender = paramBatchSender;
    this.mConfig = paramConfig;
    this.mAgingExecutor = paramScheduler;
  }
  
  private void sendCurrentBatch()
  {
    try
    {
      Batch localBatch = this.mCurrentBatch;
      if (localBatch != null)
      {
        this.mCurrentBatch = null;
        if (this.mAgingTask != null)
        {
          this.mAgingTask.unsubscribe();
          this.mAgingTask = null;
        }
        this.mBatchSender.submitRequest(new BatchSender.Request(localBatch, this.mSchema));
      }
      return;
    }
    finally {}
  }
  
  public void addPacket(Packet paramPacket)
  {
    try
    {
      if (this.mCurrentBatch == null)
      {
        this.mCurrentBatch = new Batch(this.mConfig.batchCapacity);
        if (this.mConfig.maxBatchAge != 0) {
          this.mAgingTask = this.mAgingExecutor.createWorker().schedule(this.mAgingRunnable, this.mConfig.maxBatchAge, TimeUnit.SECONDS);
        }
      }
      this.mCurrentBatch.addPacket(paramPacket);
      if (this.mCurrentBatch.capacityLeft() == 0) {
        sendCurrentBatch();
      }
      return;
    }
    finally {}
  }
  
  public static class Config
  {
    public final int batchCapacity;
    public final int maxBatchAge;
    
    public Config(int paramInt1, int paramInt2)
    {
      this.batchCapacity = paramInt1;
      this.maxBatchAge = paramInt2;
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
      } while ((((Config)paramObject).batchCapacity == this.batchCapacity) && (((Config)paramObject).maxBatchAge == this.maxBatchAge));
      return false;
    }
    
    public boolean isValid()
    {
      return (this.maxBatchAge >= 0) && (this.batchCapacity > 0);
    }
  }
  
  public static abstract interface Factory
  {
    public abstract Batcher create(Schema paramSchema, BatchSender paramBatchSender);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/delivery/Batcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */