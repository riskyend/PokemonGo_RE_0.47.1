package com.upsight.android.analytics.internal.dispatcher.delivery;

import android.text.TextUtils;
import com.upsight.android.analytics.internal.DataStoreRecord;
import com.upsight.android.analytics.internal.dispatcher.routing.Packet;
import com.upsight.android.analytics.internal.dispatcher.schema.Schema;
import com.upsight.android.analytics.internal.dispatcher.util.Selector;
import java.util.HashMap;
import java.util.Map;

public class Queue
{
  private BatchSender mBatchSender;
  private Batcher.Factory mBatcherFactory;
  private Map<Schema, Batcher> mBatchers;
  private String mName;
  private Selector<Schema> mSchemaSelectorByName;
  private Selector<Schema> mSchemaSelectorByType;
  
  Queue(String paramString, Selector<Schema> paramSelector1, Selector<Schema> paramSelector2, Batcher.Factory paramFactory, BatchSender paramBatchSender)
  {
    this.mName = paramString;
    this.mSchemaSelectorByName = paramSelector1;
    this.mSchemaSelectorByType = paramSelector2;
    this.mBatchers = new HashMap();
    this.mBatcherFactory = paramFactory;
    this.mBatchSender = paramBatchSender;
  }
  
  private Schema selectSchema(DataStoreRecord paramDataStoreRecord)
  {
    Schema localSchema = null;
    Object localObject = paramDataStoreRecord.getIdentifiers();
    if (!TextUtils.isEmpty((CharSequence)localObject)) {
      localSchema = (Schema)this.mSchemaSelectorByName.select((String)localObject);
    }
    localObject = localSchema;
    if (localSchema == null) {
      localObject = (Schema)this.mSchemaSelectorByType.select(paramDataStoreRecord.getSourceType());
    }
    return (Schema)localObject;
  }
  
  public void enqueuePacket(Packet paramPacket)
  {
    Schema localSchema = selectSchema(paramPacket.getRecord());
    Batcher localBatcher2 = (Batcher)this.mBatchers.get(localSchema);
    Batcher localBatcher1 = localBatcher2;
    if (localBatcher2 == null)
    {
      localBatcher1 = this.mBatcherFactory.create(localSchema, this.mBatchSender);
      this.mBatchers.put(localSchema, localBatcher1);
    }
    localBatcher1.addPacket(paramPacket);
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public void setOnDeliveryListener(OnDeliveryListener paramOnDeliveryListener)
  {
    this.mBatchSender.setDeliveryListener(paramOnDeliveryListener);
  }
  
  public void setOnResponseListener(OnResponseListener paramOnResponseListener)
  {
    this.mBatchSender.setResponseListener(paramOnResponseListener);
  }
  
  public static final class Trash
    extends Queue
  {
    public static final String NAME = "trash";
    private OnDeliveryListener mOnDeliveryListener;
    
    public Trash()
    {
      super(null, null, null, null);
    }
    
    public void enqueuePacket(Packet paramPacket)
    {
      paramPacket.markTrashed();
      OnDeliveryListener localOnDeliveryListener = this.mOnDeliveryListener;
      if (localOnDeliveryListener != null) {
        localOnDeliveryListener.onDelivery(paramPacket);
      }
    }
    
    public void setOnDeliveryListener(OnDeliveryListener paramOnDeliveryListener)
    {
      this.mOnDeliveryListener = paramOnDeliveryListener;
    }
    
    public void setOnResponseListener(OnResponseListener paramOnResponseListener) {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/delivery/Queue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */