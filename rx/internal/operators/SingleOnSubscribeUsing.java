package rx.internal.operators;

import java.util.Arrays;
import rx.Single;
import rx.Single.OnSubscribe;
import rx.SingleSubscriber;
import rx.exceptions.CompositeException;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;

public final class SingleOnSubscribeUsing<T, Resource>
  implements Single.OnSubscribe<T>
{
  final Action1<? super Resource> disposeAction;
  final boolean disposeEagerly;
  final Func0<Resource> resourceFactory;
  final Func1<? super Resource, ? extends Single<? extends T>> singleFactory;
  
  public SingleOnSubscribeUsing(Func0<Resource> paramFunc0, Func1<? super Resource, ? extends Single<? extends T>> paramFunc1, Action1<? super Resource> paramAction1, boolean paramBoolean)
  {
    this.resourceFactory = paramFunc0;
    this.singleFactory = paramFunc1;
    this.disposeAction = paramAction1;
    this.disposeEagerly = paramBoolean;
  }
  
  public void call(final SingleSubscriber<? super T> paramSingleSubscriber)
  {
    try
    {
      Object localObject = this.resourceFactory.call();
      Single localSingle;
      local1 = new SingleSubscriber()
      {
        public void onError(Throwable paramAnonymousThrowable)
        {
          SingleOnSubscribeUsing.this.handleSubscriptionTimeError(paramSingleSubscriber, localThrowable1, paramAnonymousThrowable);
        }
        
        /* Error */
        public void onSuccess(T paramAnonymousT)
        {
          // Byte code:
          //   0: aload_0
          //   1: getfield 20	rx/internal/operators/SingleOnSubscribeUsing$1:this$0	Lrx/internal/operators/SingleOnSubscribeUsing;
          //   4: getfield 42	rx/internal/operators/SingleOnSubscribeUsing:disposeEagerly	Z
          //   7: ifeq +19 -> 26
          //   10: aload_0
          //   11: getfield 20	rx/internal/operators/SingleOnSubscribeUsing$1:this$0	Lrx/internal/operators/SingleOnSubscribeUsing;
          //   14: getfield 46	rx/internal/operators/SingleOnSubscribeUsing:disposeAction	Lrx/functions/Action1;
          //   17: aload_0
          //   18: getfield 22	rx/internal/operators/SingleOnSubscribeUsing$1:val$resource	Ljava/lang/Object;
          //   21: invokeinterface 50 2 0
          //   26: aload_0
          //   27: getfield 24	rx/internal/operators/SingleOnSubscribeUsing$1:val$child	Lrx/SingleSubscriber;
          //   30: aload_1
          //   31: invokevirtual 52	rx/SingleSubscriber:onSuccess	(Ljava/lang/Object;)V
          //   34: aload_0
          //   35: getfield 20	rx/internal/operators/SingleOnSubscribeUsing$1:this$0	Lrx/internal/operators/SingleOnSubscribeUsing;
          //   38: getfield 42	rx/internal/operators/SingleOnSubscribeUsing:disposeEagerly	Z
          //   41: ifne +19 -> 60
          //   44: aload_0
          //   45: getfield 20	rx/internal/operators/SingleOnSubscribeUsing$1:this$0	Lrx/internal/operators/SingleOnSubscribeUsing;
          //   48: getfield 46	rx/internal/operators/SingleOnSubscribeUsing:disposeAction	Lrx/functions/Action1;
          //   51: aload_0
          //   52: getfield 22	rx/internal/operators/SingleOnSubscribeUsing$1:val$resource	Ljava/lang/Object;
          //   55: invokeinterface 50 2 0
          //   60: return
          //   61: astore_1
          //   62: aload_1
          //   63: invokestatic 57	rx/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
          //   66: aload_0
          //   67: getfield 24	rx/internal/operators/SingleOnSubscribeUsing$1:val$child	Lrx/SingleSubscriber;
          //   70: aload_1
          //   71: invokevirtual 59	rx/SingleSubscriber:onError	(Ljava/lang/Throwable;)V
          //   74: return
          //   75: astore_1
          //   76: aload_1
          //   77: invokestatic 57	rx/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
          //   80: invokestatic 65	rx/plugins/RxJavaPlugins:getInstance	()Lrx/plugins/RxJavaPlugins;
          //   83: invokevirtual 69	rx/plugins/RxJavaPlugins:getErrorHandler	()Lrx/plugins/RxJavaErrorHandler;
          //   86: aload_1
          //   87: invokevirtual 74	rx/plugins/RxJavaErrorHandler:handleError	(Ljava/lang/Throwable;)V
          //   90: return
          // Local variable table:
          //   start	length	slot	name	signature
          //   0	91	0	this	1
          //   0	91	1	paramAnonymousT	T
          // Exception table:
          //   from	to	target	type
          //   10	26	61	java/lang/Throwable
          //   44	60	75	java/lang/Throwable
        }
      };
    }
    catch (Throwable localThrowable1)
    {
      try
      {
        localSingle = (Single)this.singleFactory.call(localObject);
        if (localSingle != null) {
          break label64;
        }
        handleSubscriptionTimeError(paramSingleSubscriber, localObject, new NullPointerException("The single"));
        return;
      }
      catch (Throwable localThrowable2)
      {
        handleSubscriptionTimeError(paramSingleSubscriber, localThrowable1, localThrowable2);
        return;
      }
      localThrowable1 = localThrowable1;
      Exceptions.throwIfFatal(localThrowable1);
      paramSingleSubscriber.onError(localThrowable1);
      return;
    }
    label64:
    SingleSubscriber local1;
    paramSingleSubscriber.add(local1);
    localThrowable2.subscribe(local1);
  }
  
  void handleSubscriptionTimeError(SingleSubscriber<? super T> paramSingleSubscriber, Resource paramResource, Throwable paramThrowable)
  {
    Exceptions.throwIfFatal(paramThrowable);
    Throwable localThrowable1 = paramThrowable;
    if (this.disposeEagerly) {}
    try
    {
      this.disposeAction.call(paramResource);
      localThrowable1 = paramThrowable;
    }
    catch (Throwable localThrowable2)
    {
      for (;;)
      {
        try
        {
          this.disposeAction.call(paramResource);
          return;
        }
        catch (Throwable paramSingleSubscriber)
        {
          CompositeException localCompositeException;
          Exceptions.throwIfFatal(paramSingleSubscriber);
          RxJavaPlugins.getInstance().getErrorHandler().handleError(paramSingleSubscriber);
        }
        localThrowable2 = localThrowable2;
        Exceptions.throwIfFatal(localThrowable2);
        localCompositeException = new CompositeException(Arrays.asList(new Throwable[] { paramThrowable, localThrowable2 }));
      }
    }
    paramSingleSubscriber.onError(localThrowable1);
    if (!this.disposeEagerly) {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/SingleOnSubscribeUsing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */