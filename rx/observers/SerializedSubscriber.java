package rx.observers;

import rx.Observer;
import rx.Subscriber;

public class SerializedSubscriber<T>
  extends Subscriber<T>
{
  private final Observer<T> s;
  
  public SerializedSubscriber(Subscriber<? super T> paramSubscriber)
  {
    this(paramSubscriber, true);
  }
  
  public SerializedSubscriber(Subscriber<? super T> paramSubscriber, boolean paramBoolean)
  {
    super(paramSubscriber, paramBoolean);
    this.s = new SerializedObserver(paramSubscriber);
  }
  
  public void onCompleted()
  {
    this.s.onCompleted();
  }
  
  public void onError(Throwable paramThrowable)
  {
    this.s.onError(paramThrowable);
  }
  
  public void onNext(T paramT)
  {
    this.s.onNext(paramT);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/observers/SerializedSubscriber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */