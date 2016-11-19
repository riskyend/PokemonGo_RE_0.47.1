package rx.internal.producers;

import java.util.Iterator;
import java.util.List;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.internal.operators.BackpressureUtils;

public final class ProducerObserverArbiter<T>
  implements Producer, Observer<T>
{
  static final Producer NULL_PRODUCER = new Producer()
  {
    public void request(long paramAnonymousLong) {}
  };
  final Subscriber<? super T> child;
  Producer currentProducer;
  boolean emitting;
  volatile boolean hasError;
  Producer missedProducer;
  long missedRequested;
  Object missedTerminal;
  List<T> queue;
  long requested;
  
  public ProducerObserverArbiter(Subscriber<? super T> paramSubscriber)
  {
    this.child = paramSubscriber;
  }
  
  void emitLoop()
  {
    Subscriber localSubscriber = this.child;
    long l1 = 0L;
    Object localObject1 = null;
    for (;;)
    {
      int i = 0;
      long l5;
      Producer localProducer;
      Object localObject4;
      List localList;
      try
      {
        l5 = this.missedRequested;
        localProducer = this.missedProducer;
        localObject4 = this.missedTerminal;
        localList = this.queue;
        if ((l5 == 0L) && (localProducer == null) && (localList == null) && (localObject4 == null))
        {
          this.emitting = false;
          i = 1;
        }
        for (;;)
        {
          if (i == 0) {
            break;
          }
          if ((l1 != 0L) && (localObject1 != null)) {
            ((Producer)localObject1).request(l1);
          }
          label93:
          return;
          this.missedRequested = 0L;
          this.missedProducer = null;
          this.queue = null;
          this.missedTerminal = null;
        }
        if (localList == null) {
          break label139;
        }
      }
      finally {}
      if (localList.isEmpty()) {
        label139:
        i = 1;
      }
      while (localObject4 != null) {
        if (localObject4 != Boolean.TRUE)
        {
          localSubscriber.onError((Throwable)localObject4);
          return;
          i = 0;
        }
        else if (i != 0)
        {
          localSubscriber.onCompleted();
          return;
        }
      }
      long l4 = 0L;
      if (localList != null)
      {
        Iterator localIterator = localList.iterator();
        for (;;)
        {
          if (localIterator.hasNext())
          {
            localObject4 = localIterator.next();
            if (localSubscriber.isUnsubscribed()) {
              break label93;
            }
            if (this.hasError) {
              break;
            }
            try
            {
              localSubscriber.onNext(localObject4);
            }
            catch (Throwable localThrowable)
            {
              Exceptions.throwOrReport(localThrowable, localSubscriber, localObject4);
              return;
            }
          }
        }
        l4 = 0L + localList.size();
      }
      long l3 = this.requested;
      long l2 = l3;
      if (l3 != Long.MAX_VALUE)
      {
        l2 = l3;
        if (l5 != 0L)
        {
          l3 += l5;
          l2 = l3;
          if (l3 < 0L) {
            l2 = Long.MAX_VALUE;
          }
        }
        l3 = l2;
        if (l4 != 0L)
        {
          l3 = l2;
          if (l2 != Long.MAX_VALUE)
          {
            l3 = l2 - l4;
            if (l3 < 0L) {
              throw new IllegalStateException("More produced than requested");
            }
          }
        }
        this.requested = l3;
        l2 = l3;
      }
      Object localObject3;
      if (localProducer != null)
      {
        if (localProducer == NULL_PRODUCER)
        {
          this.currentProducer = null;
        }
        else
        {
          this.currentProducer = localProducer;
          if (l2 != 0L)
          {
            l1 = BackpressureUtils.addCap(l1, l2);
            localObject3 = localProducer;
          }
        }
      }
      else
      {
        localProducer = this.currentProducer;
        if ((localProducer != null) && (l5 != 0L))
        {
          l1 = BackpressureUtils.addCap(l1, l5);
          localObject3 = localProducer;
        }
      }
    }
  }
  
  public void onCompleted()
  {
    try
    {
      if (this.emitting)
      {
        this.missedTerminal = Boolean.valueOf(true);
        return;
      }
      this.emitting = true;
      this.child.onCompleted();
      return;
    }
    finally {}
  }
  
  public void onError(Throwable paramThrowable)
  {
    try
    {
      if (this.emitting) {
        this.missedTerminal = paramThrowable;
      }
      for (int i = 0;; i = 1)
      {
        if (i == 0) {
          break;
        }
        this.child.onError(paramThrowable);
        return;
        this.emitting = true;
      }
      this.hasError = true;
    }
    finally {}
  }
  
  /* Error */
  public void onNext(T paramT)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 56	rx/internal/producers/ProducerObserverArbiter:emitting	Z
    //   6: ifeq +46 -> 52
    //   9: aload_0
    //   10: getfield 54	rx/internal/producers/ProducerObserverArbiter:queue	Ljava/util/List;
    //   13: astore 5
    //   15: aload 5
    //   17: astore 4
    //   19: aload 5
    //   21: ifnonnull +19 -> 40
    //   24: new 138	java/util/ArrayList
    //   27: dup
    //   28: iconst_4
    //   29: invokespecial 141	java/util/ArrayList:<init>	(I)V
    //   32: astore 4
    //   34: aload_0
    //   35: aload 4
    //   37: putfield 54	rx/internal/producers/ProducerObserverArbiter:queue	Ljava/util/List;
    //   40: aload 4
    //   42: aload_1
    //   43: invokeinterface 145 2 0
    //   48: pop
    //   49: aload_0
    //   50: monitorexit
    //   51: return
    //   52: aload_0
    //   53: monitorexit
    //   54: aload_0
    //   55: getfield 41	rx/internal/producers/ProducerObserverArbiter:child	Lrx/Subscriber;
    //   58: aload_1
    //   59: invokevirtual 103	rx/Subscriber:onNext	(Ljava/lang/Object;)V
    //   62: aload_0
    //   63: getfield 115	rx/internal/producers/ProducerObserverArbiter:requested	J
    //   66: lstore_2
    //   67: lload_2
    //   68: ldc2_w 116
    //   71: lcmp
    //   72: ifeq +10 -> 82
    //   75: aload_0
    //   76: lload_2
    //   77: lconst_1
    //   78: lsub
    //   79: putfield 115	rx/internal/producers/ProducerObserverArbiter:requested	J
    //   82: aload_0
    //   83: invokevirtual 147	rx/internal/producers/ProducerObserverArbiter:emitLoop	()V
    //   86: iconst_1
    //   87: ifne +44 -> 131
    //   90: aload_0
    //   91: monitorenter
    //   92: aload_0
    //   93: iconst_0
    //   94: putfield 56	rx/internal/producers/ProducerObserverArbiter:emitting	Z
    //   97: aload_0
    //   98: monitorexit
    //   99: return
    //   100: astore_1
    //   101: aload_0
    //   102: monitorexit
    //   103: aload_1
    //   104: athrow
    //   105: astore_1
    //   106: aload_0
    //   107: monitorexit
    //   108: aload_1
    //   109: athrow
    //   110: astore_1
    //   111: iconst_0
    //   112: ifne +12 -> 124
    //   115: aload_0
    //   116: monitorenter
    //   117: aload_0
    //   118: iconst_0
    //   119: putfield 56	rx/internal/producers/ProducerObserverArbiter:emitting	Z
    //   122: aload_0
    //   123: monitorexit
    //   124: aload_1
    //   125: athrow
    //   126: astore_1
    //   127: aload_0
    //   128: monitorexit
    //   129: aload_1
    //   130: athrow
    //   131: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	132	0	this	ProducerObserverArbiter
    //   0	132	1	paramT	T
    //   66	11	2	l	long
    //   17	24	4	localObject	Object
    //   13	7	5	localList	List
    // Exception table:
    //   from	to	target	type
    //   92	99	100	finally
    //   101	103	100	finally
    //   2	15	105	finally
    //   24	40	105	finally
    //   40	51	105	finally
    //   52	54	105	finally
    //   106	108	105	finally
    //   54	67	110	finally
    //   75	82	110	finally
    //   82	86	110	finally
    //   117	124	126	finally
    //   127	129	126	finally
  }
  
  /* Error */
  public void request(long paramLong)
  {
    // Byte code:
    //   0: lload_1
    //   1: lconst_0
    //   2: lcmp
    //   3: ifge +13 -> 16
    //   6: new 150	java/lang/IllegalArgumentException
    //   9: dup
    //   10: ldc -104
    //   12: invokespecial 153	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   15: athrow
    //   16: lload_1
    //   17: lconst_0
    //   18: lcmp
    //   19: ifne +4 -> 23
    //   22: return
    //   23: aload_0
    //   24: monitorenter
    //   25: aload_0
    //   26: getfield 56	rx/internal/producers/ProducerObserverArbiter:emitting	Z
    //   29: ifeq +23 -> 52
    //   32: aload_0
    //   33: aload_0
    //   34: getfield 48	rx/internal/producers/ProducerObserverArbiter:missedRequested	J
    //   37: lload_1
    //   38: ladd
    //   39: putfield 48	rx/internal/producers/ProducerObserverArbiter:missedRequested	J
    //   42: aload_0
    //   43: monitorexit
    //   44: return
    //   45: astore 7
    //   47: aload_0
    //   48: monitorexit
    //   49: aload 7
    //   51: athrow
    //   52: aload_0
    //   53: iconst_1
    //   54: putfield 56	rx/internal/producers/ProducerObserverArbiter:emitting	Z
    //   57: aload_0
    //   58: monitorexit
    //   59: aload_0
    //   60: getfield 126	rx/internal/producers/ProducerObserverArbiter:currentProducer	Lrx/Producer;
    //   63: astore 7
    //   65: aload_0
    //   66: getfield 115	rx/internal/producers/ProducerObserverArbiter:requested	J
    //   69: lload_1
    //   70: ladd
    //   71: lstore 5
    //   73: lload 5
    //   75: lstore_3
    //   76: lload 5
    //   78: lconst_0
    //   79: lcmp
    //   80: ifge +7 -> 87
    //   83: ldc2_w 116
    //   86: lstore_3
    //   87: aload_0
    //   88: lload_3
    //   89: putfield 115	rx/internal/producers/ProducerObserverArbiter:requested	J
    //   92: aload_0
    //   93: invokevirtual 147	rx/internal/producers/ProducerObserverArbiter:emitLoop	()V
    //   96: iconst_1
    //   97: ifne +12 -> 109
    //   100: aload_0
    //   101: monitorenter
    //   102: aload_0
    //   103: iconst_0
    //   104: putfield 56	rx/internal/producers/ProducerObserverArbiter:emitting	Z
    //   107: aload_0
    //   108: monitorexit
    //   109: aload 7
    //   111: ifnull -89 -> 22
    //   114: aload 7
    //   116: lload_1
    //   117: invokeinterface 60 3 0
    //   122: return
    //   123: astore 7
    //   125: aload_0
    //   126: monitorexit
    //   127: aload 7
    //   129: athrow
    //   130: astore 7
    //   132: iconst_0
    //   133: ifne +12 -> 145
    //   136: aload_0
    //   137: monitorenter
    //   138: aload_0
    //   139: iconst_0
    //   140: putfield 56	rx/internal/producers/ProducerObserverArbiter:emitting	Z
    //   143: aload_0
    //   144: monitorexit
    //   145: aload 7
    //   147: athrow
    //   148: astore 7
    //   150: aload_0
    //   151: monitorexit
    //   152: aload 7
    //   154: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	155	0	this	ProducerObserverArbiter
    //   0	155	1	paramLong	long
    //   75	14	3	l1	long
    //   71	6	5	l2	long
    //   45	5	7	localObject1	Object
    //   63	52	7	localProducer	Producer
    //   123	5	7	localObject2	Object
    //   130	16	7	localObject3	Object
    //   148	5	7	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   25	44	45	finally
    //   47	49	45	finally
    //   52	59	45	finally
    //   102	109	123	finally
    //   125	127	123	finally
    //   65	73	130	finally
    //   87	96	130	finally
    //   138	145	148	finally
    //   150	152	148	finally
  }
  
  /* Error */
  public void setProducer(Producer paramProducer)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 56	rx/internal/producers/ProducerObserverArbiter:emitting	Z
    //   6: ifeq +22 -> 28
    //   9: aload_1
    //   10: ifnull +11 -> 21
    //   13: aload_0
    //   14: aload_1
    //   15: putfield 50	rx/internal/producers/ProducerObserverArbiter:missedProducer	Lrx/Producer;
    //   18: aload_0
    //   19: monitorexit
    //   20: return
    //   21: getstatic 36	rx/internal/producers/ProducerObserverArbiter:NULL_PRODUCER	Lrx/Producer;
    //   24: astore_1
    //   25: goto -12 -> 13
    //   28: aload_0
    //   29: iconst_1
    //   30: putfield 56	rx/internal/producers/ProducerObserverArbiter:emitting	Z
    //   33: aload_0
    //   34: monitorexit
    //   35: aload_0
    //   36: aload_1
    //   37: putfield 126	rx/internal/producers/ProducerObserverArbiter:currentProducer	Lrx/Producer;
    //   40: aload_0
    //   41: getfield 115	rx/internal/producers/ProducerObserverArbiter:requested	J
    //   44: lstore_2
    //   45: aload_0
    //   46: invokevirtual 147	rx/internal/producers/ProducerObserverArbiter:emitLoop	()V
    //   49: iconst_1
    //   50: ifne +12 -> 62
    //   53: aload_0
    //   54: monitorenter
    //   55: aload_0
    //   56: iconst_0
    //   57: putfield 56	rx/internal/producers/ProducerObserverArbiter:emitting	Z
    //   60: aload_0
    //   61: monitorexit
    //   62: aload_1
    //   63: ifnull +48 -> 111
    //   66: lload_2
    //   67: lconst_0
    //   68: lcmp
    //   69: ifeq +42 -> 111
    //   72: aload_1
    //   73: lload_2
    //   74: invokeinterface 60 3 0
    //   79: return
    //   80: astore_1
    //   81: aload_0
    //   82: monitorexit
    //   83: aload_1
    //   84: athrow
    //   85: astore_1
    //   86: aload_0
    //   87: monitorexit
    //   88: aload_1
    //   89: athrow
    //   90: astore_1
    //   91: iconst_0
    //   92: ifne +12 -> 104
    //   95: aload_0
    //   96: monitorenter
    //   97: aload_0
    //   98: iconst_0
    //   99: putfield 56	rx/internal/producers/ProducerObserverArbiter:emitting	Z
    //   102: aload_0
    //   103: monitorexit
    //   104: aload_1
    //   105: athrow
    //   106: astore_1
    //   107: aload_0
    //   108: monitorexit
    //   109: aload_1
    //   110: athrow
    //   111: return
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	112	0	this	ProducerObserverArbiter
    //   0	112	1	paramProducer	Producer
    //   44	30	2	l	long
    // Exception table:
    //   from	to	target	type
    //   2	9	80	finally
    //   13	20	80	finally
    //   21	25	80	finally
    //   28	35	80	finally
    //   81	83	80	finally
    //   55	62	85	finally
    //   86	88	85	finally
    //   45	49	90	finally
    //   97	104	106	finally
    //   107	109	106	finally
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/producers/ProducerObserverArbiter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */