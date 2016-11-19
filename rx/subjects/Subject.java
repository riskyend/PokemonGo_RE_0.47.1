package rx.subjects;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;

public abstract class Subject<T, R>
  extends Observable<R>
  implements Observer<T>
{
  protected Subject(Observable.OnSubscribe<R> paramOnSubscribe)
  {
    super(paramOnSubscribe);
  }
  
  public abstract boolean hasObservers();
  
  public final SerializedSubject<T, R> toSerialized()
  {
    if (getClass() == SerializedSubject.class) {
      return (SerializedSubject)this;
    }
    return new SerializedSubject(this);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/subjects/Subject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */