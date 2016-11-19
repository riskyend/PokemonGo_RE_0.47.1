package rx.internal.operators;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Producer;
import rx.Subscriber;
import rx.Subscription;
import rx.exceptions.MissingBackpressureException;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.util.RxRingBuffer;
import rx.internal.util.SynchronizedQueue;
import rx.internal.util.unsafe.SpscArrayQueue;
import rx.internal.util.unsafe.UnsafeAccess;
import rx.observables.ConnectableObservable;
import rx.subscriptions.Subscriptions;

public final class OperatorPublish<T>
  extends ConnectableObservable<T>
{
  final AtomicReference<PublishSubscriber<T>> current;
  final Observable<? extends T> source;
  
  private OperatorPublish(Observable.OnSubscribe<T> paramOnSubscribe, Observable<? extends T> paramObservable, AtomicReference<PublishSubscriber<T>> paramAtomicReference)
  {
    super(paramOnSubscribe);
    this.source = paramObservable;
    this.current = paramAtomicReference;
  }
  
  public static <T, R> Observable<R> create(Observable<? extends T> paramObservable, Func1<? super Observable<T>, ? extends Observable<R>> paramFunc1)
  {
    return create(paramObservable, paramFunc1, false);
  }
  
  public static <T, R> Observable<R> create(final Observable<? extends T> paramObservable, final Func1<? super Observable<T>, ? extends Observable<R>> paramFunc1, boolean paramBoolean)
  {
    create(new Observable.OnSubscribe()
    {
      public void call(final Subscriber<? super R> paramAnonymousSubscriber)
      {
        final OnSubscribePublishMulticast localOnSubscribePublishMulticast = new OnSubscribePublishMulticast(RxRingBuffer.SIZE, this.val$delayError);
        Subscriber local1 = new Subscriber()
        {
          public void onCompleted()
          {
            localOnSubscribePublishMulticast.unsubscribe();
            paramAnonymousSubscriber.onCompleted();
          }
          
          public void onError(Throwable paramAnonymous2Throwable)
          {
            localOnSubscribePublishMulticast.unsubscribe();
            paramAnonymousSubscriber.onError(paramAnonymous2Throwable);
          }
          
          public void onNext(R paramAnonymous2R)
          {
            paramAnonymousSubscriber.onNext(paramAnonymous2R);
          }
          
          public void setProducer(Producer paramAnonymous2Producer)
          {
            paramAnonymousSubscriber.setProducer(paramAnonymous2Producer);
          }
        };
        paramAnonymousSubscriber.add(localOnSubscribePublishMulticast);
        paramAnonymousSubscriber.add(local1);
        ((Observable)paramFunc1.call(Observable.create(localOnSubscribePublishMulticast))).unsafeSubscribe(local1);
        paramObservable.unsafeSubscribe(localOnSubscribePublishMulticast.subscriber());
      }
    });
  }
  
  public static <T> ConnectableObservable<T> create(Observable<? extends T> paramObservable)
  {
    AtomicReference localAtomicReference = new AtomicReference();
    new OperatorPublish(new Observable.OnSubscribe()
    {
      public void call(Subscriber<? super T> paramAnonymousSubscriber)
      {
        Object localObject2;
        Object localObject1;
        do
        {
          do
          {
            localObject2 = (OperatorPublish.PublishSubscriber)this.val$curr.get();
            if (localObject2 != null)
            {
              localObject1 = localObject2;
              if (!((OperatorPublish.PublishSubscriber)localObject2).isUnsubscribed()) {
                break;
              }
            }
            localObject1 = new OperatorPublish.PublishSubscriber(this.val$curr);
            ((OperatorPublish.PublishSubscriber)localObject1).init();
          } while (!this.val$curr.compareAndSet(localObject2, localObject1));
          localObject2 = new OperatorPublish.InnerProducer((OperatorPublish.PublishSubscriber)localObject1, paramAnonymousSubscriber);
        } while (!((OperatorPublish.PublishSubscriber)localObject1).add((OperatorPublish.InnerProducer)localObject2));
        paramAnonymousSubscriber.add((Subscription)localObject2);
        paramAnonymousSubscriber.setProducer((Producer)localObject2);
      }
    }, paramObservable, localAtomicReference);
  }
  
  public void connect(Action1<? super Subscription> paramAction1)
  {
    PublishSubscriber localPublishSubscriber2;
    PublishSubscriber localPublishSubscriber1;
    do
    {
      localPublishSubscriber2 = (PublishSubscriber)this.current.get();
      if (localPublishSubscriber2 != null)
      {
        localPublishSubscriber1 = localPublishSubscriber2;
        if (!localPublishSubscriber2.isUnsubscribed()) {
          break;
        }
      }
      localPublishSubscriber1 = new PublishSubscriber(this.current);
      localPublishSubscriber1.init();
    } while (!this.current.compareAndSet(localPublishSubscriber2, localPublishSubscriber1));
    if ((!localPublishSubscriber1.shouldConnect.get()) && (localPublishSubscriber1.shouldConnect.compareAndSet(false, true))) {}
    for (int i = 1;; i = 0)
    {
      paramAction1.call(localPublishSubscriber1);
      if (i != 0) {
        this.source.unsafeSubscribe(localPublishSubscriber1);
      }
      return;
    }
  }
  
  static final class InnerProducer<T>
    extends AtomicLong
    implements Producer, Subscription
  {
    static final long NOT_REQUESTED = -4611686018427387904L;
    static final long UNSUBSCRIBED = Long.MIN_VALUE;
    private static final long serialVersionUID = -4453897557930727610L;
    final Subscriber<? super T> child;
    final OperatorPublish.PublishSubscriber<T> parent;
    
    public InnerProducer(OperatorPublish.PublishSubscriber<T> paramPublishSubscriber, Subscriber<? super T> paramSubscriber)
    {
      this.parent = paramPublishSubscriber;
      this.child = paramSubscriber;
      lazySet(-4611686018427387904L);
    }
    
    public boolean isUnsubscribed()
    {
      return get() == Long.MIN_VALUE;
    }
    
    public long produced(long paramLong)
    {
      if (paramLong <= 0L) {
        throw new IllegalArgumentException("Cant produce zero or less");
      }
      long l1;
      long l2;
      do
      {
        l1 = get();
        if (l1 == -4611686018427387904L) {
          throw new IllegalStateException("Produced without request");
        }
        if (l1 == Long.MIN_VALUE) {
          return Long.MIN_VALUE;
        }
        l2 = l1 - paramLong;
        if (l2 < 0L) {
          throw new IllegalStateException("More produced (" + paramLong + ") than requested (" + l1 + ")");
        }
      } while (!compareAndSet(l1, l2));
      return l2;
    }
    
    public void request(long paramLong)
    {
      if (paramLong < 0L) {
        return;
      }
      for (;;)
      {
        long l3 = get();
        if ((l3 == Long.MIN_VALUE) || ((l3 >= 0L) && (paramLong == 0L))) {
          break;
        }
        long l1;
        if (l3 == -4611686018427387904L) {
          l1 = paramLong;
        }
        while (compareAndSet(l3, l1))
        {
          this.parent.dispatch();
          return;
          long l2 = l3 + paramLong;
          l1 = l2;
          if (l2 < 0L) {
            l1 = Long.MAX_VALUE;
          }
        }
      }
    }
    
    public void unsubscribe()
    {
      if ((get() != Long.MIN_VALUE) && (getAndSet(Long.MIN_VALUE) != Long.MIN_VALUE))
      {
        this.parent.remove(this);
        this.parent.dispatch();
      }
    }
  }
  
  static final class PublishSubscriber<T>
    extends Subscriber<T>
    implements Subscription
  {
    static final OperatorPublish.InnerProducer[] EMPTY = new OperatorPublish.InnerProducer[0];
    static final OperatorPublish.InnerProducer[] TERMINATED = new OperatorPublish.InnerProducer[0];
    final AtomicReference<PublishSubscriber<T>> current;
    boolean emitting;
    boolean missed;
    final NotificationLite<T> nl;
    final AtomicReference<OperatorPublish.InnerProducer[]> producers;
    final Queue<Object> queue;
    final AtomicBoolean shouldConnect;
    volatile Object terminalEvent;
    
    public PublishSubscriber(AtomicReference<PublishSubscriber<T>> paramAtomicReference)
    {
      if (UnsafeAccess.isUnsafeAvailable()) {}
      for (Object localObject = new SpscArrayQueue(RxRingBuffer.SIZE);; localObject = new SynchronizedQueue(RxRingBuffer.SIZE))
      {
        this.queue = ((Queue)localObject);
        this.nl = NotificationLite.instance();
        this.producers = new AtomicReference(EMPTY);
        this.current = paramAtomicReference;
        this.shouldConnect = new AtomicBoolean();
        return;
      }
    }
    
    boolean add(OperatorPublish.InnerProducer<T> paramInnerProducer)
    {
      if (paramInnerProducer == null) {
        throw new NullPointerException();
      }
      OperatorPublish.InnerProducer[] arrayOfInnerProducer1;
      OperatorPublish.InnerProducer[] arrayOfInnerProducer2;
      do
      {
        arrayOfInnerProducer1 = (OperatorPublish.InnerProducer[])this.producers.get();
        if (arrayOfInnerProducer1 == TERMINATED) {
          return false;
        }
        int i = arrayOfInnerProducer1.length;
        arrayOfInnerProducer2 = new OperatorPublish.InnerProducer[i + 1];
        System.arraycopy(arrayOfInnerProducer1, 0, arrayOfInnerProducer2, 0, i);
        arrayOfInnerProducer2[i] = paramInnerProducer;
      } while (!this.producers.compareAndSet(arrayOfInnerProducer1, arrayOfInnerProducer2));
      return true;
    }
    
    boolean checkTerminated(Object paramObject, boolean paramBoolean)
    {
      if (paramObject != null)
      {
        int j;
        int i;
        if (this.nl.isCompleted(paramObject))
        {
          if (paramBoolean)
          {
            this.current.compareAndSet(this, null);
            try
            {
              paramObject = (OperatorPublish.InnerProducer[])this.producers.getAndSet(TERMINATED);
              j = paramObject.length;
              i = 0;
              while (i < j)
              {
                paramObject[i].child.onCompleted();
                i += 1;
              }
              return true;
            }
            finally
            {
              unsubscribe();
            }
          }
        }
        else
        {
          paramObject = this.nl.getError(paramObject);
          this.current.compareAndSet(this, null);
          try
          {
            OperatorPublish.InnerProducer[] arrayOfInnerProducer = (OperatorPublish.InnerProducer[])this.producers.getAndSet(TERMINATED);
            j = arrayOfInnerProducer.length;
            i = 0;
            while (i < j)
            {
              arrayOfInnerProducer[i].child.onError((Throwable)paramObject);
              i += 1;
            }
            return true;
          }
          finally
          {
            unsubscribe();
          }
        }
      }
      return false;
    }
    
    /* Error */
    void dispatch()
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 146	rx/internal/operators/OperatorPublish$PublishSubscriber:emitting	Z
      //   6: ifeq +11 -> 17
      //   9: aload_0
      //   10: iconst_1
      //   11: putfield 148	rx/internal/operators/OperatorPublish$PublishSubscriber:missed	Z
      //   14: aload_0
      //   15: monitorexit
      //   16: return
      //   17: aload_0
      //   18: iconst_1
      //   19: putfield 146	rx/internal/operators/OperatorPublish$PublishSubscriber:emitting	Z
      //   22: aload_0
      //   23: iconst_0
      //   24: putfield 148	rx/internal/operators/OperatorPublish$PublishSubscriber:missed	Z
      //   27: aload_0
      //   28: monitorexit
      //   29: iconst_0
      //   30: istore 5
      //   32: iconst_0
      //   33: istore 4
      //   35: iload 4
      //   37: istore_1
      //   38: aload_0
      //   39: getfield 150	rx/internal/operators/OperatorPublish$PublishSubscriber:terminalEvent	Ljava/lang/Object;
      //   42: astore 16
      //   44: iload 4
      //   46: istore_1
      //   47: aload_0
      //   48: getfield 65	rx/internal/operators/OperatorPublish$PublishSubscriber:queue	Ljava/util/Queue;
      //   51: invokeinterface 155 1 0
      //   56: istore 8
      //   58: iload 4
      //   60: istore_1
      //   61: aload_0
      //   62: aload 16
      //   64: iload 8
      //   66: invokevirtual 157	rx/internal/operators/OperatorPublish$PublishSubscriber:checkTerminated	(Ljava/lang/Object;Z)Z
      //   69: istore 9
      //   71: iload 9
      //   73: ifeq +31 -> 104
      //   76: iconst_1
      //   77: ifne +524 -> 601
      //   80: aload_0
      //   81: monitorenter
      //   82: aload_0
      //   83: iconst_0
      //   84: putfield 146	rx/internal/operators/OperatorPublish$PublishSubscriber:emitting	Z
      //   87: aload_0
      //   88: monitorexit
      //   89: return
      //   90: astore 16
      //   92: aload_0
      //   93: monitorexit
      //   94: aload 16
      //   96: athrow
      //   97: astore 16
      //   99: aload_0
      //   100: monitorexit
      //   101: aload 16
      //   103: athrow
      //   104: iload 8
      //   106: ifne +303 -> 409
      //   109: iload 4
      //   111: istore_1
      //   112: aload_0
      //   113: getfield 80	rx/internal/operators/OperatorPublish$PublishSubscriber:producers	Ljava/util/concurrent/atomic/AtomicReference;
      //   116: invokevirtual 101	java/util/concurrent/atomic/AtomicReference:get	()Ljava/lang/Object;
      //   119: checkcast 102	[Lrx/internal/operators/OperatorPublish$InnerProducer;
      //   122: astore 16
      //   124: iload 4
      //   126: istore_1
      //   127: aload 16
      //   129: arraylength
      //   130: istore 6
      //   132: ldc2_w 158
      //   135: lstore 10
      //   137: iconst_0
      //   138: istore_3
      //   139: iload 4
      //   141: istore_1
      //   142: aload 16
      //   144: arraylength
      //   145: istore 7
      //   147: iconst_0
      //   148: istore_2
      //   149: iload_2
      //   150: iload 7
      //   152: if_icmpge +39 -> 191
      //   155: iload 4
      //   157: istore_1
      //   158: aload 16
      //   160: iload_2
      //   161: aaload
      //   162: invokevirtual 162	rx/internal/operators/OperatorPublish$InnerProducer:get	()J
      //   165: lstore 14
      //   167: lload 14
      //   169: lconst_0
      //   170: lcmp
      //   171: iflt +444 -> 615
      //   174: iload 4
      //   176: istore_1
      //   177: lload 10
      //   179: lload 14
      //   181: invokestatic 168	java/lang/Math:min	(JJ)J
      //   184: lstore 12
      //   186: iload_3
      //   187: istore_1
      //   188: goto +414 -> 602
      //   191: iload 6
      //   193: iload_3
      //   194: if_icmpne +104 -> 298
      //   197: iload 4
      //   199: istore_1
      //   200: aload_0
      //   201: getfield 150	rx/internal/operators/OperatorPublish$PublishSubscriber:terminalEvent	Ljava/lang/Object;
      //   204: astore 16
      //   206: iload 4
      //   208: istore_1
      //   209: aload_0
      //   210: getfield 65	rx/internal/operators/OperatorPublish$PublishSubscriber:queue	Ljava/util/Queue;
      //   213: invokeinterface 171 1 0
      //   218: ifnonnull +45 -> 263
      //   221: iconst_1
      //   222: istore 8
      //   224: iload 4
      //   226: istore_1
      //   227: aload_0
      //   228: aload 16
      //   230: iload 8
      //   232: invokevirtual 157	rx/internal/operators/OperatorPublish$PublishSubscriber:checkTerminated	(Ljava/lang/Object;Z)Z
      //   235: istore 8
      //   237: iload 8
      //   239: ifeq +30 -> 269
      //   242: iconst_1
      //   243: ifne +358 -> 601
      //   246: aload_0
      //   247: monitorenter
      //   248: aload_0
      //   249: iconst_0
      //   250: putfield 146	rx/internal/operators/OperatorPublish$PublishSubscriber:emitting	Z
      //   253: aload_0
      //   254: monitorexit
      //   255: return
      //   256: astore 16
      //   258: aload_0
      //   259: monitorexit
      //   260: aload 16
      //   262: athrow
      //   263: iconst_0
      //   264: istore 8
      //   266: goto -42 -> 224
      //   269: iload 4
      //   271: istore_1
      //   272: aload_0
      //   273: lconst_1
      //   274: invokevirtual 175	rx/internal/operators/OperatorPublish$PublishSubscriber:request	(J)V
      //   277: goto -242 -> 35
      //   280: astore 16
      //   282: iload_1
      //   283: ifne +12 -> 295
      //   286: aload_0
      //   287: monitorenter
      //   288: aload_0
      //   289: iconst_0
      //   290: putfield 146	rx/internal/operators/OperatorPublish$PublishSubscriber:emitting	Z
      //   293: aload_0
      //   294: monitorexit
      //   295: aload 16
      //   297: athrow
      //   298: iconst_0
      //   299: istore_2
      //   300: iload_2
      //   301: i2l
      //   302: lload 10
      //   304: lcmp
      //   305: ifge +84 -> 389
      //   308: iload 4
      //   310: istore_1
      //   311: aload_0
      //   312: getfield 150	rx/internal/operators/OperatorPublish$PublishSubscriber:terminalEvent	Ljava/lang/Object;
      //   315: astore 17
      //   317: iload 4
      //   319: istore_1
      //   320: aload_0
      //   321: getfield 65	rx/internal/operators/OperatorPublish$PublishSubscriber:queue	Ljava/util/Queue;
      //   324: invokeinterface 171 1 0
      //   329: astore 18
      //   331: aload 18
      //   333: ifnonnull +45 -> 378
      //   336: iconst_1
      //   337: istore 8
      //   339: iload 4
      //   341: istore_1
      //   342: aload_0
      //   343: aload 17
      //   345: iload 8
      //   347: invokevirtual 157	rx/internal/operators/OperatorPublish$PublishSubscriber:checkTerminated	(Ljava/lang/Object;Z)Z
      //   350: istore 9
      //   352: iload 9
      //   354: ifeq +30 -> 384
      //   357: iconst_1
      //   358: ifne +243 -> 601
      //   361: aload_0
      //   362: monitorenter
      //   363: aload_0
      //   364: iconst_0
      //   365: putfield 146	rx/internal/operators/OperatorPublish$PublishSubscriber:emitting	Z
      //   368: aload_0
      //   369: monitorexit
      //   370: return
      //   371: astore 16
      //   373: aload_0
      //   374: monitorexit
      //   375: aload 16
      //   377: athrow
      //   378: iconst_0
      //   379: istore 8
      //   381: goto -42 -> 339
      //   384: iload 8
      //   386: ifeq +71 -> 457
      //   389: iload_2
      //   390: ifle +251 -> 641
      //   393: iload_2
      //   394: i2l
      //   395: lstore 12
      //   397: iload 4
      //   399: istore_1
      //   400: aload_0
      //   401: lload 12
      //   403: invokevirtual 175	rx/internal/operators/OperatorPublish$PublishSubscriber:request	(J)V
      //   406: goto +235 -> 641
      //   409: iload 4
      //   411: istore_1
      //   412: aload_0
      //   413: monitorenter
      //   414: iload 5
      //   416: istore_1
      //   417: aload_0
      //   418: getfield 148	rx/internal/operators/OperatorPublish$PublishSubscriber:missed	Z
      //   421: ifne +150 -> 571
      //   424: iload 5
      //   426: istore_1
      //   427: aload_0
      //   428: iconst_0
      //   429: putfield 146	rx/internal/operators/OperatorPublish$PublishSubscriber:emitting	Z
      //   432: iconst_1
      //   433: istore_1
      //   434: aload_0
      //   435: monitorexit
      //   436: iconst_1
      //   437: ifne +164 -> 601
      //   440: aload_0
      //   441: monitorenter
      //   442: aload_0
      //   443: iconst_0
      //   444: putfield 146	rx/internal/operators/OperatorPublish$PublishSubscriber:emitting	Z
      //   447: aload_0
      //   448: monitorexit
      //   449: return
      //   450: astore 16
      //   452: aload_0
      //   453: monitorexit
      //   454: aload 16
      //   456: athrow
      //   457: iload 4
      //   459: istore_1
      //   460: aload_0
      //   461: getfield 73	rx/internal/operators/OperatorPublish$PublishSubscriber:nl	Lrx/internal/operators/NotificationLite;
      //   464: aload 18
      //   466: invokevirtual 178	rx/internal/operators/NotificationLite:getValue	(Ljava/lang/Object;)Ljava/lang/Object;
      //   469: astore 17
      //   471: iload 4
      //   473: istore_1
      //   474: aload 16
      //   476: arraylength
      //   477: istore 6
      //   479: iconst_0
      //   480: istore_3
      //   481: iload_3
      //   482: iload 6
      //   484: if_icmpge +80 -> 564
      //   487: aload 16
      //   489: iload_3
      //   490: aaload
      //   491: astore 18
      //   493: iload 4
      //   495: istore_1
      //   496: aload 18
      //   498: invokevirtual 162	rx/internal/operators/OperatorPublish$InnerProducer:get	()J
      //   501: lstore 12
      //   503: lload 12
      //   505: lconst_0
      //   506: lcmp
      //   507: ifle +149 -> 656
      //   510: iload 4
      //   512: istore_1
      //   513: aload 18
      //   515: getfield 127	rx/internal/operators/OperatorPublish$InnerProducer:child	Lrx/Subscriber;
      //   518: aload 17
      //   520: invokevirtual 181	rx/Subscriber:onNext	(Ljava/lang/Object;)V
      //   523: iload 4
      //   525: istore_1
      //   526: aload 18
      //   528: lconst_1
      //   529: invokevirtual 185	rx/internal/operators/OperatorPublish$InnerProducer:produced	(J)J
      //   532: pop2
      //   533: goto +123 -> 656
      //   536: astore 19
      //   538: iload 4
      //   540: istore_1
      //   541: aload 18
      //   543: invokevirtual 186	rx/internal/operators/OperatorPublish$InnerProducer:unsubscribe	()V
      //   546: iload 4
      //   548: istore_1
      //   549: aload 19
      //   551: aload 18
      //   553: getfield 127	rx/internal/operators/OperatorPublish$InnerProducer:child	Lrx/Subscriber;
      //   556: aload 17
      //   558: invokestatic 192	rx/exceptions/Exceptions:throwOrReport	(Ljava/lang/Throwable;Lrx/Observer;Ljava/lang/Object;)V
      //   561: goto +95 -> 656
      //   564: iload_2
      //   565: iconst_1
      //   566: iadd
      //   567: istore_2
      //   568: goto -268 -> 300
      //   571: iload 5
      //   573: istore_1
      //   574: aload_0
      //   575: iconst_0
      //   576: putfield 148	rx/internal/operators/OperatorPublish$PublishSubscriber:missed	Z
      //   579: iload 5
      //   581: istore_1
      //   582: aload_0
      //   583: monitorexit
      //   584: goto -549 -> 35
      //   587: astore 16
      //   589: aload_0
      //   590: monitorexit
      //   591: aload 16
      //   593: athrow
      //   594: astore 16
      //   596: aload_0
      //   597: monitorexit
      //   598: aload 16
      //   600: athrow
      //   601: return
      //   602: iload_2
      //   603: iconst_1
      //   604: iadd
      //   605: istore_2
      //   606: lload 12
      //   608: lstore 10
      //   610: iload_1
      //   611: istore_3
      //   612: goto -463 -> 149
      //   615: lload 10
      //   617: lstore 12
      //   619: iload_3
      //   620: istore_1
      //   621: lload 14
      //   623: ldc2_w 193
      //   626: lcmp
      //   627: ifne -25 -> 602
      //   630: iload_3
      //   631: iconst_1
      //   632: iadd
      //   633: istore_1
      //   634: lload 10
      //   636: lstore 12
      //   638: goto -36 -> 602
      //   641: lload 10
      //   643: lconst_0
      //   644: lcmp
      //   645: ifeq -236 -> 409
      //   648: iload 8
      //   650: ifeq -615 -> 35
      //   653: goto -244 -> 409
      //   656: iload_3
      //   657: iconst_1
      //   658: iadd
      //   659: istore_3
      //   660: goto -179 -> 481
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	663	0	this	PublishSubscriber
      //   37	597	1	i	int
      //   148	458	2	j	int
      //   138	522	3	k	int
      //   33	514	4	m	int
      //   30	550	5	n	int
      //   130	355	6	i1	int
      //   145	8	7	i2	int
      //   56	593	8	bool1	boolean
      //   69	284	9	bool2	boolean
      //   135	507	10	l1	long
      //   184	453	12	l2	long
      //   165	457	14	l3	long
      //   42	21	16	localObject1	Object
      //   90	5	16	localObject2	Object
      //   97	5	16	localObject3	Object
      //   122	107	16	localObject4	Object
      //   256	5	16	localObject5	Object
      //   280	16	16	localObject6	Object
      //   371	5	16	localObject7	Object
      //   450	38	16	localObject8	Object
      //   587	5	16	localObject9	Object
      //   594	5	16	localObject10	Object
      //   315	242	17	localObject11	Object
      //   329	223	18	localObject12	Object
      //   536	14	19	localThrowable	Throwable
      // Exception table:
      //   from	to	target	type
      //   82	89	90	finally
      //   92	94	90	finally
      //   2	16	97	finally
      //   17	29	97	finally
      //   99	101	97	finally
      //   248	255	256	finally
      //   258	260	256	finally
      //   38	44	280	finally
      //   47	58	280	finally
      //   61	71	280	finally
      //   112	124	280	finally
      //   127	132	280	finally
      //   142	147	280	finally
      //   158	167	280	finally
      //   177	186	280	finally
      //   200	206	280	finally
      //   209	221	280	finally
      //   227	237	280	finally
      //   272	277	280	finally
      //   311	317	280	finally
      //   320	331	280	finally
      //   342	352	280	finally
      //   400	406	280	finally
      //   412	414	280	finally
      //   460	471	280	finally
      //   474	479	280	finally
      //   496	503	280	finally
      //   513	523	280	finally
      //   526	533	280	finally
      //   541	546	280	finally
      //   549	561	280	finally
      //   591	594	280	finally
      //   363	370	371	finally
      //   373	375	371	finally
      //   442	449	450	finally
      //   452	454	450	finally
      //   513	523	536	java/lang/Throwable
      //   417	424	587	finally
      //   427	432	587	finally
      //   434	436	587	finally
      //   574	579	587	finally
      //   582	584	587	finally
      //   589	591	587	finally
      //   288	295	594	finally
      //   596	598	594	finally
    }
    
    void init()
    {
      add(Subscriptions.create(new Action0()
      {
        public void call()
        {
          OperatorPublish.PublishSubscriber.this.producers.getAndSet(OperatorPublish.PublishSubscriber.TERMINATED);
          OperatorPublish.PublishSubscriber.this.current.compareAndSet(OperatorPublish.PublishSubscriber.this, null);
        }
      }));
    }
    
    public void onCompleted()
    {
      if (this.terminalEvent == null)
      {
        this.terminalEvent = this.nl.completed();
        dispatch();
      }
    }
    
    public void onError(Throwable paramThrowable)
    {
      if (this.terminalEvent == null)
      {
        this.terminalEvent = this.nl.error(paramThrowable);
        dispatch();
      }
    }
    
    public void onNext(T paramT)
    {
      if (!this.queue.offer(this.nl.next(paramT)))
      {
        onError(new MissingBackpressureException());
        return;
      }
      dispatch();
    }
    
    public void onStart()
    {
      request(RxRingBuffer.SIZE);
    }
    
    void remove(OperatorPublish.InnerProducer<T> paramInnerProducer)
    {
      OperatorPublish.InnerProducer[] arrayOfInnerProducer2 = (OperatorPublish.InnerProducer[])this.producers.get();
      if ((arrayOfInnerProducer2 == EMPTY) || (arrayOfInnerProducer2 == TERMINATED)) {}
      int m;
      int i;
      label39:
      int j;
      do
      {
        return;
        int k = -1;
        m = arrayOfInnerProducer2.length;
        i = 0;
        j = k;
        if (i < m)
        {
          if (!arrayOfInnerProducer2[i].equals(paramInnerProducer)) {
            break;
          }
          j = i;
        }
      } while (j < 0);
      OperatorPublish.InnerProducer[] arrayOfInnerProducer1;
      if (m == 1) {
        arrayOfInnerProducer1 = EMPTY;
      }
      while (this.producers.compareAndSet(arrayOfInnerProducer2, arrayOfInnerProducer1))
      {
        return;
        i += 1;
        break label39;
        arrayOfInnerProducer1 = new OperatorPublish.InnerProducer[m - 1];
        System.arraycopy(arrayOfInnerProducer2, 0, arrayOfInnerProducer1, 0, j);
        System.arraycopy(arrayOfInnerProducer2, j + 1, arrayOfInnerProducer1, j, m - j - 1);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/internal/operators/OperatorPublish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */