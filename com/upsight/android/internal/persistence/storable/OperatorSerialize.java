package com.upsight.android.internal.persistence.storable;

import com.upsight.android.UpsightException;
import com.upsight.android.internal.persistence.Storable;
import com.upsight.android.persistence.UpsightStorableSerializer;
import rx.Observable.Operator;
import rx.Subscriber;

class OperatorSerialize<T>
  implements Observable.Operator<Storable, T>
{
  private final StorableIdFactory mIDFactory;
  private final StorableInfo<T> mStorableInfo;
  
  public OperatorSerialize(StorableInfo<T> paramStorableInfo, StorableIdFactory paramStorableIdFactory)
  {
    if (paramStorableInfo == null) {
      throw new IllegalArgumentException("StorableInfo can not be null.");
    }
    this.mStorableInfo = paramStorableInfo;
    this.mIDFactory = paramStorableIdFactory;
  }
  
  public Subscriber<? super T> call(Subscriber<? super Storable> paramSubscriber)
  {
    return new DeserializeSubscriber(this.mStorableInfo, this.mIDFactory, paramSubscriber);
  }
  
  private static class DeserializeSubscriber<T>
    extends Subscriber<T>
  {
    private final Subscriber<? super Storable> mChildSubscriber;
    private final StorableIdFactory mIdFactory;
    private final StorableInfo<T> mStorableInfo;
    
    public DeserializeSubscriber(StorableInfo<T> paramStorableInfo, StorableIdFactory paramStorableIdFactory, Subscriber<? super Storable> paramSubscriber)
    {
      super();
      this.mStorableInfo = paramStorableInfo;
      this.mChildSubscriber = paramSubscriber;
      this.mIdFactory = paramStorableIdFactory;
    }
    
    public void onCompleted()
    {
      this.mChildSubscriber.onCompleted();
    }
    
    public void onError(Throwable paramThrowable)
    {
      this.mChildSubscriber.onError(paramThrowable);
    }
    
    public void onNext(T paramT)
    {
      UpsightStorableSerializer localUpsightStorableSerializer = this.mStorableInfo.getDeserializer();
      try
      {
        if (!this.mChildSubscriber.isUnsubscribed())
        {
          String str1 = this.mStorableInfo.getIdentifierAccessor().getId(paramT);
          String str2 = this.mStorableInfo.getStorableTypeAccessor().getType(paramT);
          paramT = localUpsightStorableSerializer.toString(paramT);
          this.mChildSubscriber.onNext(Storable.create(str1, str2, paramT));
        }
        return;
      }
      catch (UpsightException paramT)
      {
        this.mChildSubscriber.onError(paramT);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/storable/OperatorSerialize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */