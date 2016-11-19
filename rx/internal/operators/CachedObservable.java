package rx.internal.operators;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.internal.util.LinkedArrayList;
import rx.subscriptions.SerialSubscription;

public final class CachedObservable<T>
  extends Observable<T>
{
  private final CacheState<T> state;
  
  private CachedObservable(Observable.OnSubscribe<T> paramOnSubscribe, CacheState<T> paramCacheState)
  {
    super(paramOnSubscribe);
    this.state = paramCacheState;
  }
  
  public static <T> CachedObservable<T> from(Observable<? extends T> paramObservable)
  {
    return from(paramObservable, 16);
  }
  
  public static <T> CachedObservable<T> from(Observable<? extends T> paramObservable, int paramInt)
  {
    if (paramInt < 1) {
      throw new IllegalArgumentException("capacityHint > 0 required");
    }
    paramObservable = new CacheState(paramObservable, paramInt);
    return new CachedObservable(new CachedSubscribe(paramObservable), paramObservable);
  }
  
  boolean hasObservers()
  {
    return this.state.producers.length != 0;
  }
  
  boolean isConnected()
  {
    return this.state.isConnected;
  }
  
  static final class CacheState<T>
    extends LinkedArrayList
    implements Observer<T>
  {
    static final CachedObservable.ReplayProducer<?>[] EMPTY = new CachedObservable.ReplayProducer[0];
    final SerialSubscription connection;
    volatile boolean isConnected;
    final NotificationLite<T> nl;
    volatile CachedObservable.ReplayProducer<?>[] producers;
    final Observable<? extends T> source;
    boolean sourceDone;
    
    public CacheState(Observable<? extends T> paramObservable, int paramInt)
    {
      super();
      this.source = paramObservable;
      this.producers = EMPTY;
      this.nl = NotificationLite.instance();
      this.connection = new SerialSubscription();
    }
    
    public void addProducer(CachedObservable.ReplayProducer<T> paramReplayProducer)
    {
      synchronized (this.connection)
      {
        CachedObservable.ReplayProducer[] arrayOfReplayProducer1 = this.producers;
        int i = arrayOfReplayProducer1.length;
        CachedObservable.ReplayProducer[] arrayOfReplayProducer2 = new CachedObservable.ReplayProducer[i + 1];
        System.arraycopy(arrayOfReplayProducer1, 0, arrayOfReplayProducer2, 0, i);
        arrayOfReplayProducer2[i] = paramReplayProducer;
        this.producers = arrayOfReplayProducer2;
        return;
      }
    }
    
    public void connect()
    {
      Subscriber local1 = new Subscriber()
      {
        public void onCompleted()
        {
          CachedObservable.CacheState.this.onCompleted();
        }
        
        public void onError(Throwable paramAnonymousThrowable)
        {
          CachedObservable.CacheState.this.onError(paramAnonymousThrowable);
        }
        
        public void onNext(T paramAnonymousT)
        {
          CachedObservable.CacheState.this.onNext(paramAnonymousT);
        }
      };
      this.connection.set(local1);
      this.source.unsafeSubscribe(local1);
      this.isConnected = true;
    }
    
    void dispatch()
    {
      CachedObservable.ReplayProducer[] arrayOfReplayProducer = this.producers;
      int j = arrayOfReplayProducer.length;
      int i = 0;
      while (i < j)
      {
        arrayOfReplayProducer[i].replay();
        i += 1;
      }
    }
    
    public void onCompleted()
    {
      if (!this.sourceDone)
      {
        this.sourceDone = true;
        add(this.nl.completed());
        this.connection.unsubscribe();
        dispatch();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (!this.sourceDone)
      {
        this.sourceDone = true;
        add(this.nl.error(paramThrowable));
        this.connection.unsubscribe();
        dispatch();
      }
    }
    
    public void onNext(T paramT)
    {
      if (!this.sourceDone)
      {
        add(this.nl.next(paramT));
        dispatch();
      }
    }
    
    public void removeProducer(CachedObservable.ReplayProducer<T> paramReplayProducer)
    {
      for (;;)
      {
        CachedObservable.ReplayProducer[] arrayOfReplayProducer;
        int m;
        int i;
        int j;
        synchronized (this.connection)
        {
          arrayOfReplayProducer = this.producers;
          m = arrayOfReplayProducer.length;
          int k = -1;
          i = 0;
          j = k;
          if (i < m)
          {
            if (!arrayOfReplayProducer[i].equals(paramReplayProducer)) {
              break label120;
            }
            j = i;
          }
          if (j < 0) {
            return;
          }
          if (m == 1)
          {
            this.producers = EMPTY;
            return;
          }
        }
        paramReplayProducer = new CachedObservable.ReplayProducer[m - 1];
        System.arraycopy(arrayOfReplayProducer, 0, paramReplayProducer, 0, j);
        System.arraycopy(arrayOfReplayProducer, j + 1, paramReplayProducer, j, m - j - 1);
        this.producers = paramReplayProducer;
        return;
        label120:
        i += 1;
      }
    }
  }
  
  static final class CachedSubscribe<T>
    extends AtomicBoolean
    implements Observable.OnSubscribe<T>
  {
    private static final long serialVersionUID = -2817751667698696782L;
    final CachedObservable.CacheState<T> state;
    
    public CachedSubscribe(CachedObservable.CacheState<T> paramCacheState)
    {
      this.state = paramCacheState;
    }
    
    public void call(Subscriber<? super T> paramSubscriber)
    {
      CachedObservable.ReplayProducer localReplayProducer = new CachedObservable.ReplayProducer(paramSubscriber, this.state);
      this.state.addProducer(localReplayProducer);
      paramSubscriber.add(localReplayProducer);
      paramSubscriber.setProducer(localReplayProducer);
      if ((!get()) && (compareAndSet(false, true))) {
        this.state.connect();
      }
    }
  }
  
  static final class ReplayProducer<T>
    extends AtomicLong
    implements Producer, Subscription
  {
    private static final long serialVersionUID = -2557562030197141021L;
    final Subscriber<? super T> child;
    Object[] currentBuffer;
    int currentIndexInBuffer;
    boolean emitting;
    int index;
    boolean missed;
    final CachedObservable.CacheState<T> state;
    
    public ReplayProducer(Subscriber<? super T> paramSubscriber, CachedObservable.CacheState<T> paramCacheState)
    {
      this.child = paramSubscriber;
      this.state = paramCacheState;
    }
    
    public boolean isUnsubscribed()
    {
      return get() < 0L;
    }
    
    public long produced(long paramLong)
    {
      return addAndGet(-paramLong);
    }
    
    /* Error */
    public void replay()
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 58	rx/internal/operators/CachedObservable$ReplayProducer:emitting	Z
      //   6: ifeq +11 -> 17
      //   9: aload_0
      //   10: iconst_1
      //   11: putfield 60	rx/internal/operators/CachedObservable$ReplayProducer:missed	Z
      //   14: aload_0
      //   15: monitorexit
      //   16: return
      //   17: aload_0
      //   18: iconst_1
      //   19: putfield 58	rx/internal/operators/CachedObservable$ReplayProducer:emitting	Z
      //   22: aload_0
      //   23: monitorexit
      //   24: iconst_0
      //   25: istore 7
      //   27: iconst_0
      //   28: istore 8
      //   30: iconst_0
      //   31: istore 6
      //   33: iload 8
      //   35: istore_1
      //   36: aload_0
      //   37: getfield 39	rx/internal/operators/CachedObservable$ReplayProducer:state	Lrx/internal/operators/CachedObservable$CacheState;
      //   40: getfield 66	rx/internal/operators/CachedObservable$CacheState:nl	Lrx/internal/operators/NotificationLite;
      //   43: astore 16
      //   45: iload 8
      //   47: istore_1
      //   48: aload_0
      //   49: getfield 37	rx/internal/operators/CachedObservable$ReplayProducer:child	Lrx/Subscriber;
      //   52: astore 17
      //   54: iload 8
      //   56: istore_1
      //   57: aload_0
      //   58: invokevirtual 48	rx/internal/operators/CachedObservable$ReplayProducer:get	()J
      //   61: lstore 11
      //   63: lload 11
      //   65: lconst_0
      //   66: lcmp
      //   67: ifge +31 -> 98
      //   70: iconst_1
      //   71: ifne +652 -> 723
      //   74: aload_0
      //   75: monitorenter
      //   76: aload_0
      //   77: iconst_0
      //   78: putfield 58	rx/internal/operators/CachedObservable$ReplayProducer:emitting	Z
      //   81: aload_0
      //   82: monitorexit
      //   83: return
      //   84: astore 14
      //   86: aload_0
      //   87: monitorexit
      //   88: aload 14
      //   90: athrow
      //   91: astore 14
      //   93: aload_0
      //   94: monitorexit
      //   95: aload 14
      //   97: athrow
      //   98: iload 8
      //   100: istore_1
      //   101: aload_0
      //   102: getfield 39	rx/internal/operators/CachedObservable$ReplayProducer:state	Lrx/internal/operators/CachedObservable$CacheState;
      //   105: invokevirtual 70	rx/internal/operators/CachedObservable$CacheState:size	()I
      //   108: istore 9
      //   110: iload 9
      //   112: ifeq +515 -> 627
      //   115: iload 8
      //   117: istore_1
      //   118: aload_0
      //   119: getfield 72	rx/internal/operators/CachedObservable$ReplayProducer:currentBuffer	[Ljava/lang/Object;
      //   122: astore 15
      //   124: aload 15
      //   126: astore 14
      //   128: aload 15
      //   130: ifnonnull +24 -> 154
      //   133: iload 8
      //   135: istore_1
      //   136: aload_0
      //   137: getfield 39	rx/internal/operators/CachedObservable$ReplayProducer:state	Lrx/internal/operators/CachedObservable$CacheState;
      //   140: invokevirtual 76	rx/internal/operators/CachedObservable$CacheState:head	()[Ljava/lang/Object;
      //   143: astore 14
      //   145: iload 8
      //   147: istore_1
      //   148: aload_0
      //   149: aload 14
      //   151: putfield 72	rx/internal/operators/CachedObservable$ReplayProducer:currentBuffer	[Ljava/lang/Object;
      //   154: iload 8
      //   156: istore_1
      //   157: aload 14
      //   159: arraylength
      //   160: iconst_1
      //   161: isub
      //   162: istore 10
      //   164: iload 8
      //   166: istore_1
      //   167: aload_0
      //   168: getfield 78	rx/internal/operators/CachedObservable$ReplayProducer:index	I
      //   171: istore 4
      //   173: iload 8
      //   175: istore_1
      //   176: aload_0
      //   177: getfield 80	rx/internal/operators/CachedObservable$ReplayProducer:currentIndexInBuffer	I
      //   180: istore_2
      //   181: lload 11
      //   183: lconst_0
      //   184: lcmp
      //   185: ifne +112 -> 297
      //   188: aload 14
      //   190: iload_2
      //   191: aaload
      //   192: astore 14
      //   194: iload 8
      //   196: istore_1
      //   197: aload 16
      //   199: aload 14
      //   201: invokevirtual 86	rx/internal/operators/NotificationLite:isCompleted	(Ljava/lang/Object;)Z
      //   204: ifeq +38 -> 242
      //   207: iload 8
      //   209: istore_1
      //   210: aload 17
      //   212: invokevirtual 91	rx/Subscriber:onCompleted	()V
      //   215: iconst_1
      //   216: istore_1
      //   217: aload_0
      //   218: invokevirtual 94	rx/internal/operators/CachedObservable$ReplayProducer:unsubscribe	()V
      //   221: iconst_1
      //   222: ifne +501 -> 723
      //   225: aload_0
      //   226: monitorenter
      //   227: aload_0
      //   228: iconst_0
      //   229: putfield 58	rx/internal/operators/CachedObservable$ReplayProducer:emitting	Z
      //   232: aload_0
      //   233: monitorexit
      //   234: return
      //   235: astore 14
      //   237: aload_0
      //   238: monitorexit
      //   239: aload 14
      //   241: athrow
      //   242: iload 8
      //   244: istore_1
      //   245: aload 16
      //   247: aload 14
      //   249: invokevirtual 97	rx/internal/operators/NotificationLite:isError	(Ljava/lang/Object;)Z
      //   252: ifeq +375 -> 627
      //   255: iload 8
      //   257: istore_1
      //   258: aload 17
      //   260: aload 16
      //   262: aload 14
      //   264: invokevirtual 101	rx/internal/operators/NotificationLite:getError	(Ljava/lang/Object;)Ljava/lang/Throwable;
      //   267: invokevirtual 105	rx/Subscriber:onError	(Ljava/lang/Throwable;)V
      //   270: iconst_1
      //   271: istore_1
      //   272: aload_0
      //   273: invokevirtual 94	rx/internal/operators/CachedObservable$ReplayProducer:unsubscribe	()V
      //   276: iconst_1
      //   277: ifne +446 -> 723
      //   280: aload_0
      //   281: monitorenter
      //   282: aload_0
      //   283: iconst_0
      //   284: putfield 58	rx/internal/operators/CachedObservable$ReplayProducer:emitting	Z
      //   287: aload_0
      //   288: monitorexit
      //   289: return
      //   290: astore 14
      //   292: aload_0
      //   293: monitorexit
      //   294: aload 14
      //   296: athrow
      //   297: lload 11
      //   299: lconst_0
      //   300: lcmp
      //   301: ifle +326 -> 627
      //   304: iconst_0
      //   305: istore_3
      //   306: aload 14
      //   308: astore 15
      //   310: iload 4
      //   312: iload 9
      //   314: if_icmpge +241 -> 555
      //   317: lload 11
      //   319: lconst_0
      //   320: lcmp
      //   321: ifle +234 -> 555
      //   324: iload 8
      //   326: istore_1
      //   327: aload 17
      //   329: invokevirtual 107	rx/Subscriber:isUnsubscribed	()Z
      //   332: istore 13
      //   334: iload 13
      //   336: ifeq +24 -> 360
      //   339: iconst_1
      //   340: ifne +383 -> 723
      //   343: aload_0
      //   344: monitorenter
      //   345: aload_0
      //   346: iconst_0
      //   347: putfield 58	rx/internal/operators/CachedObservable$ReplayProducer:emitting	Z
      //   350: aload_0
      //   351: monitorexit
      //   352: return
      //   353: astore 14
      //   355: aload_0
      //   356: monitorexit
      //   357: aload 14
      //   359: athrow
      //   360: aload 15
      //   362: astore 14
      //   364: iload_2
      //   365: istore 5
      //   367: iload_2
      //   368: iload 10
      //   370: if_icmpne +22 -> 392
      //   373: iload 8
      //   375: istore_1
      //   376: aload 15
      //   378: iload 10
      //   380: aaload
      //   381: checkcast 108	[Ljava/lang/Object;
      //   384: checkcast 108	[Ljava/lang/Object;
      //   387: astore 14
      //   389: iconst_0
      //   390: istore 5
      //   392: aload 14
      //   394: iload 5
      //   396: aaload
      //   397: astore 15
      //   399: iload 6
      //   401: istore_2
      //   402: iload 8
      //   404: istore_1
      //   405: aload 16
      //   407: aload 17
      //   409: aload 15
      //   411: invokevirtual 112	rx/internal/operators/NotificationLite:accept	(Lrx/Observer;Ljava/lang/Object;)Z
      //   414: ifeq +113 -> 527
      //   417: iconst_1
      //   418: istore_1
      //   419: iconst_1
      //   420: istore_2
      //   421: aload_0
      //   422: invokevirtual 94	rx/internal/operators/CachedObservable$ReplayProducer:unsubscribe	()V
      //   425: iconst_1
      //   426: ifne +297 -> 723
      //   429: aload_0
      //   430: monitorenter
      //   431: aload_0
      //   432: iconst_0
      //   433: putfield 58	rx/internal/operators/CachedObservable$ReplayProducer:emitting	Z
      //   436: aload_0
      //   437: monitorexit
      //   438: return
      //   439: astore 14
      //   441: aload_0
      //   442: monitorexit
      //   443: aload 14
      //   445: athrow
      //   446: astore 14
      //   448: iload_2
      //   449: istore_1
      //   450: aload 14
      //   452: invokestatic 117	rx/exceptions/Exceptions:throwIfFatal	(Ljava/lang/Throwable;)V
      //   455: iconst_1
      //   456: istore_2
      //   457: iload_2
      //   458: istore_1
      //   459: aload_0
      //   460: invokevirtual 94	rx/internal/operators/CachedObservable$ReplayProducer:unsubscribe	()V
      //   463: iload_2
      //   464: istore_1
      //   465: aload 16
      //   467: aload 15
      //   469: invokevirtual 97	rx/internal/operators/NotificationLite:isError	(Ljava/lang/Object;)Z
      //   472: ifne +34 -> 506
      //   475: iload_2
      //   476: istore_1
      //   477: aload 16
      //   479: aload 15
      //   481: invokevirtual 86	rx/internal/operators/NotificationLite:isCompleted	(Ljava/lang/Object;)Z
      //   484: ifne +22 -> 506
      //   487: iload_2
      //   488: istore_1
      //   489: aload 17
      //   491: aload 14
      //   493: aload 16
      //   495: aload 15
      //   497: invokevirtual 121	rx/internal/operators/NotificationLite:getValue	(Ljava/lang/Object;)Ljava/lang/Object;
      //   500: invokestatic 127	rx/exceptions/OnErrorThrowable:addValueAsLastCause	(Ljava/lang/Throwable;Ljava/lang/Object;)Ljava/lang/Throwable;
      //   503: invokevirtual 105	rx/Subscriber:onError	(Ljava/lang/Throwable;)V
      //   506: iconst_1
      //   507: ifne +216 -> 723
      //   510: aload_0
      //   511: monitorenter
      //   512: aload_0
      //   513: iconst_0
      //   514: putfield 58	rx/internal/operators/CachedObservable$ReplayProducer:emitting	Z
      //   517: aload_0
      //   518: monitorexit
      //   519: return
      //   520: astore 14
      //   522: aload_0
      //   523: monitorexit
      //   524: aload 14
      //   526: athrow
      //   527: iload 5
      //   529: iconst_1
      //   530: iadd
      //   531: istore_2
      //   532: iload 4
      //   534: iconst_1
      //   535: iadd
      //   536: istore 4
      //   538: lload 11
      //   540: lconst_1
      //   541: lsub
      //   542: lstore 11
      //   544: iload_3
      //   545: iconst_1
      //   546: iadd
      //   547: istore_3
      //   548: aload 14
      //   550: astore 15
      //   552: goto -242 -> 310
      //   555: iload 8
      //   557: istore_1
      //   558: aload 17
      //   560: invokevirtual 107	rx/Subscriber:isUnsubscribed	()Z
      //   563: istore 13
      //   565: iload 13
      //   567: ifeq +24 -> 591
      //   570: iconst_1
      //   571: ifne +152 -> 723
      //   574: aload_0
      //   575: monitorenter
      //   576: aload_0
      //   577: iconst_0
      //   578: putfield 58	rx/internal/operators/CachedObservable$ReplayProducer:emitting	Z
      //   581: aload_0
      //   582: monitorexit
      //   583: return
      //   584: astore 14
      //   586: aload_0
      //   587: monitorexit
      //   588: aload 14
      //   590: athrow
      //   591: iload 8
      //   593: istore_1
      //   594: aload_0
      //   595: iload 4
      //   597: putfield 78	rx/internal/operators/CachedObservable$ReplayProducer:index	I
      //   600: iload 8
      //   602: istore_1
      //   603: aload_0
      //   604: iload_2
      //   605: putfield 80	rx/internal/operators/CachedObservable$ReplayProducer:currentIndexInBuffer	I
      //   608: iload 8
      //   610: istore_1
      //   611: aload_0
      //   612: aload 15
      //   614: putfield 72	rx/internal/operators/CachedObservable$ReplayProducer:currentBuffer	[Ljava/lang/Object;
      //   617: iload 8
      //   619: istore_1
      //   620: aload_0
      //   621: iload_3
      //   622: i2l
      //   623: invokevirtual 129	rx/internal/operators/CachedObservable$ReplayProducer:produced	(J)J
      //   626: pop2
      //   627: iload 8
      //   629: istore_1
      //   630: aload_0
      //   631: monitorenter
      //   632: iload 7
      //   634: istore_1
      //   635: aload_0
      //   636: getfield 60	rx/internal/operators/CachedObservable$ReplayProducer:missed	Z
      //   639: ifne +36 -> 675
      //   642: iload 7
      //   644: istore_1
      //   645: aload_0
      //   646: iconst_0
      //   647: putfield 58	rx/internal/operators/CachedObservable$ReplayProducer:emitting	Z
      //   650: iconst_1
      //   651: istore_1
      //   652: aload_0
      //   653: monitorexit
      //   654: iconst_1
      //   655: ifne +68 -> 723
      //   658: aload_0
      //   659: monitorenter
      //   660: aload_0
      //   661: iconst_0
      //   662: putfield 58	rx/internal/operators/CachedObservable$ReplayProducer:emitting	Z
      //   665: aload_0
      //   666: monitorexit
      //   667: return
      //   668: astore 14
      //   670: aload_0
      //   671: monitorexit
      //   672: aload 14
      //   674: athrow
      //   675: iload 7
      //   677: istore_1
      //   678: aload_0
      //   679: iconst_0
      //   680: putfield 60	rx/internal/operators/CachedObservable$ReplayProducer:missed	Z
      //   683: iload 7
      //   685: istore_1
      //   686: aload_0
      //   687: monitorexit
      //   688: goto -634 -> 54
      //   691: astore 14
      //   693: aload_0
      //   694: monitorexit
      //   695: aload 14
      //   697: athrow
      //   698: astore 14
      //   700: iload_1
      //   701: ifne +12 -> 713
      //   704: aload_0
      //   705: monitorenter
      //   706: aload_0
      //   707: iconst_0
      //   708: putfield 58	rx/internal/operators/CachedObservable$ReplayProducer:emitting	Z
      //   711: aload_0
      //   712: monitorexit
      //   713: aload 14
      //   715: athrow
      //   716: astore 14
      //   718: aload_0
      //   719: monitorexit
      //   720: aload 14
      //   722: athrow
      //   723: return
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	724	0	this	ReplayProducer
      //   35	666	1	i	int
      //   180	425	2	j	int
      //   305	317	3	k	int
      //   171	425	4	m	int
      //   365	166	5	n	int
      //   31	369	6	i1	int
      //   25	659	7	i2	int
      //   28	600	8	i3	int
      //   108	207	9	i4	int
      //   162	217	10	i5	int
      //   61	482	11	l	long
      //   332	234	13	bool	boolean
      //   84	5	14	localObject1	Object
      //   91	5	14	localObject2	Object
      //   126	74	14	localObject3	Object
      //   235	28	14	localObject4	Object
      //   290	17	14	localObject5	Object
      //   353	5	14	localObject6	Object
      //   362	31	14	localObject7	Object
      //   439	5	14	localObject8	Object
      //   446	46	14	localThrowable	Throwable
      //   520	29	14	localObject9	Object
      //   584	5	14	localObject10	Object
      //   668	5	14	localObject11	Object
      //   691	5	14	localObject12	Object
      //   698	16	14	localObject13	Object
      //   716	5	14	localObject14	Object
      //   122	491	15	localObject15	Object
      //   43	451	16	localNotificationLite	NotificationLite
      //   52	507	17	localSubscriber	Subscriber
      // Exception table:
      //   from	to	target	type
      //   76	83	84	finally
      //   86	88	84	finally
      //   2	16	91	finally
      //   17	24	91	finally
      //   93	95	91	finally
      //   227	234	235	finally
      //   237	239	235	finally
      //   282	289	290	finally
      //   292	294	290	finally
      //   345	352	353	finally
      //   355	357	353	finally
      //   431	438	439	finally
      //   441	443	439	finally
      //   405	417	446	java/lang/Throwable
      //   421	425	446	java/lang/Throwable
      //   512	519	520	finally
      //   522	524	520	finally
      //   576	583	584	finally
      //   586	588	584	finally
      //   660	667	668	finally
      //   670	672	668	finally
      //   635	642	691	finally
      //   645	650	691	finally
      //   652	654	691	finally
      //   678	683	691	finally
      //   686	688	691	finally
      //   693	695	691	finally
      //   36	45	698	finally
      //   48	54	698	finally
      //   57	63	698	finally
      //   101	110	698	finally
      //   118	124	698	finally
      //   136	145	698	finally
      //   148	154	698	finally
      //   157	164	698	finally
      //   167	173	698	finally
      //   176	181	698	finally
      //   197	207	698	finally
      //   210	215	698	finally
      //   217	221	698	finally
      //   245	255	698	finally
      //   258	270	698	finally
      //   272	276	698	finally
      //   327	334	698	finally
      //   376	389	698	finally
      //   405	417	698	finally
      //   421	425	698	finally
      //   450	455	698	finally
      //   459	463	698	finally
      //   465	475	698	finally
      //   477	487	698	finally
      //   489	506	698	finally
      //   558	565	698	finally
      //   594	600	698	finally
      //   603	608	698	finally
      //   611	617	698	finally
      //   620	627	698	finally
      //   630	632	698	finally
      //   695	698	698	finally
      //   706	713	716	finally
      //   718	720	716	finally
    }
    
    public void request(long paramLong)
    {
      long l3;
      long l1;
      do
      {
        l3 = get();
        if (l3 < 0L) {
          return;
        }
        long l2 = l3 + paramLong;
        l1 = l2;
        if (l2 < 0L) {
          l1 = Long.MAX_VALUE;
        }
      } while (!compareAndSet(l3, l1));
      replay();
    }
    
    public void unsubscribe()
    {
      if ((get() >= 0L) && (getAndSet(-1L) >= 0L)) {
        this.state.removeProducer(this);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/CachedObservable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */