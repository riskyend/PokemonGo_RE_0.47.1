package rx.internal.operators;

import rx.Single;
import rx.Single.OnSubscribe;
import rx.SingleSubscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

public class SingleOperatorOnErrorResumeNext<T>
  implements Single.OnSubscribe<T>
{
  private final Single<? extends T> originalSingle;
  private final Func1<Throwable, ? extends Single<? extends T>> resumeFunctionInCaseOfError;
  
  private SingleOperatorOnErrorResumeNext(Single<? extends T> paramSingle, Func1<Throwable, ? extends Single<? extends T>> paramFunc1)
  {
    if (paramSingle == null) {
      throw new NullPointerException("originalSingle must not be null");
    }
    if (paramFunc1 == null) {
      throw new NullPointerException("resumeFunctionInCaseOfError must not be null");
    }
    this.originalSingle = paramSingle;
    this.resumeFunctionInCaseOfError = paramFunc1;
  }
  
  public static <T> SingleOperatorOnErrorResumeNext<T> withFunction(Single<? extends T> paramSingle, Func1<Throwable, ? extends Single<? extends T>> paramFunc1)
  {
    return new SingleOperatorOnErrorResumeNext(paramSingle, paramFunc1);
  }
  
  public static <T> SingleOperatorOnErrorResumeNext<T> withOther(Single<? extends T> paramSingle1, Single<? extends T> paramSingle2)
  {
    if (paramSingle2 == null) {
      throw new NullPointerException("resumeSingleInCaseOfError must not be null");
    }
    new SingleOperatorOnErrorResumeNext(paramSingle1, new Func1()
    {
      public Single<? extends T> call(Throwable paramAnonymousThrowable)
      {
        return this.val$resumeSingleInCaseOfError;
      }
    });
  }
  
  public void call(final SingleSubscriber<? super T> paramSingleSubscriber)
  {
    SingleSubscriber local2 = new SingleSubscriber()
    {
      public void onError(Throwable paramAnonymousThrowable)
      {
        try
        {
          ((Single)SingleOperatorOnErrorResumeNext.this.resumeFunctionInCaseOfError.call(paramAnonymousThrowable)).subscribe(paramSingleSubscriber);
          return;
        }
        catch (Throwable paramAnonymousThrowable)
        {
          Exceptions.throwOrReport(paramAnonymousThrowable, paramSingleSubscriber);
        }
      }
      
      public void onSuccess(T paramAnonymousT)
      {
        paramSingleSubscriber.onSuccess(paramAnonymousT);
      }
    };
    paramSingleSubscriber.add(local2);
    this.originalSingle.subscribe(local2);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/SingleOperatorOnErrorResumeNext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */