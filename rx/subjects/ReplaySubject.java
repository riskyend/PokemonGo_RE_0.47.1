package rx.subjects;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import rx.Observable.OnSubscribe;
import rx.Observer;
import rx.Scheduler;
import rx.annotations.Beta;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.internal.operators.NotificationLite;
import rx.internal.util.UtilityFunctions;
import rx.schedulers.Timestamped;

public final class ReplaySubject<T>
  extends Subject<T, T>
{
  private static final Object[] EMPTY_ARRAY = new Object[0];
  final SubjectSubscriptionManager<T> ssm;
  final ReplayState<T, ?> state;
  
  ReplaySubject(Observable.OnSubscribe<T> paramOnSubscribe, SubjectSubscriptionManager<T> paramSubjectSubscriptionManager, ReplayState<T, ?> paramReplayState)
  {
    super(paramOnSubscribe);
    this.ssm = paramSubjectSubscriptionManager;
    this.state = paramReplayState;
  }
  
  private boolean caughtUp(SubjectSubscriptionManager.SubjectObserver<? super T> paramSubjectObserver)
  {
    boolean bool = true;
    if (!paramSubjectObserver.caughtUp)
    {
      if (this.state.replayObserver(paramSubjectObserver))
      {
        paramSubjectObserver.caughtUp = true;
        paramSubjectObserver.index(null);
      }
      bool = false;
    }
    return bool;
  }
  
  public static <T> ReplaySubject<T> create()
  {
    return create(16);
  }
  
  public static <T> ReplaySubject<T> create(int paramInt)
  {
    UnboundedReplayState localUnboundedReplayState = new UnboundedReplayState(paramInt);
    SubjectSubscriptionManager localSubjectSubscriptionManager = new SubjectSubscriptionManager();
    localSubjectSubscriptionManager.onStart = new Action1()
    {
      public void call(SubjectSubscriptionManager.SubjectObserver<T> paramAnonymousSubjectObserver)
      {
        paramAnonymousSubjectObserver.index(Integer.valueOf(this.val$state.replayObserverFromIndex(Integer.valueOf(0), paramAnonymousSubjectObserver).intValue()));
      }
    };
    localSubjectSubscriptionManager.onAdded = new Action1()
    {
      /* Error */
      public void call(SubjectSubscriptionManager.SubjectObserver<T> paramAnonymousSubjectObserver)
      {
        // Byte code:
        //   0: aload_1
        //   1: monitorenter
        //   2: aload_1
        //   3: getfield 33	rx/subjects/SubjectSubscriptionManager$SubjectObserver:first	Z
        //   6: ifeq +10 -> 16
        //   9: aload_1
        //   10: getfield 36	rx/subjects/SubjectSubscriptionManager$SubjectObserver:emitting	Z
        //   13: ifeq +6 -> 19
        //   16: aload_1
        //   17: monitorexit
        //   18: return
        //   19: aload_1
        //   20: iconst_0
        //   21: putfield 33	rx/subjects/SubjectSubscriptionManager$SubjectObserver:first	Z
        //   24: aload_1
        //   25: iconst_1
        //   26: putfield 36	rx/subjects/SubjectSubscriptionManager$SubjectObserver:emitting	Z
        //   29: aload_1
        //   30: monitorexit
        //   31: iconst_0
        //   32: istore 4
        //   34: iconst_0
        //   35: istore_3
        //   36: iload 4
        //   38: istore_2
        //   39: aload_0
        //   40: getfield 18	rx/subjects/ReplaySubject$2:val$state	Lrx/subjects/ReplaySubject$UnboundedReplayState;
        //   43: astore 7
        //   45: iload 4
        //   47: istore_2
        //   48: aload_1
        //   49: invokevirtual 40	rx/subjects/SubjectSubscriptionManager$SubjectObserver:index	()Ljava/lang/Object;
        //   52: checkcast 42	java/lang/Integer
        //   55: invokevirtual 46	java/lang/Integer:intValue	()I
        //   58: istore 6
        //   60: iload 4
        //   62: istore_2
        //   63: aload 7
        //   65: invokevirtual 51	rx/subjects/ReplaySubject$UnboundedReplayState:get	()I
        //   68: istore 5
        //   70: iload 6
        //   72: iload 5
        //   74: if_icmpeq +21 -> 95
        //   77: iload 4
        //   79: istore_2
        //   80: aload_1
        //   81: aload 7
        //   83: iload 6
        //   85: invokestatic 55	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
        //   88: aload_1
        //   89: invokevirtual 59	rx/subjects/ReplaySubject$UnboundedReplayState:replayObserverFromIndex	(Ljava/lang/Integer;Lrx/subjects/SubjectSubscriptionManager$SubjectObserver;)Ljava/lang/Integer;
        //   92: invokevirtual 61	rx/subjects/SubjectSubscriptionManager$SubjectObserver:index	(Ljava/lang/Object;)V
        //   95: iload 4
        //   97: istore_2
        //   98: aload_1
        //   99: monitorenter
        //   100: iload_3
        //   101: istore_2
        //   102: iload 5
        //   104: aload 7
        //   106: invokevirtual 51	rx/subjects/ReplaySubject$UnboundedReplayState:get	()I
        //   109: if_icmpne +42 -> 151
        //   112: iload_3
        //   113: istore_2
        //   114: aload_1
        //   115: iconst_0
        //   116: putfield 36	rx/subjects/SubjectSubscriptionManager$SubjectObserver:emitting	Z
        //   119: iconst_1
        //   120: istore_2
        //   121: aload_1
        //   122: monitorexit
        //   123: iconst_1
        //   124: ifne +66 -> 190
        //   127: aload_1
        //   128: monitorenter
        //   129: aload_1
        //   130: iconst_0
        //   131: putfield 36	rx/subjects/SubjectSubscriptionManager$SubjectObserver:emitting	Z
        //   134: aload_1
        //   135: monitorexit
        //   136: return
        //   137: astore 7
        //   139: aload_1
        //   140: monitorexit
        //   141: aload 7
        //   143: athrow
        //   144: astore 7
        //   146: aload_1
        //   147: monitorexit
        //   148: aload 7
        //   150: athrow
        //   151: iload_3
        //   152: istore_2
        //   153: aload_1
        //   154: monitorexit
        //   155: goto -110 -> 45
        //   158: astore 7
        //   160: aload_1
        //   161: monitorexit
        //   162: aload 7
        //   164: athrow
        //   165: astore 7
        //   167: iload_2
        //   168: ifne +12 -> 180
        //   171: aload_1
        //   172: monitorenter
        //   173: aload_1
        //   174: iconst_0
        //   175: putfield 36	rx/subjects/SubjectSubscriptionManager$SubjectObserver:emitting	Z
        //   178: aload_1
        //   179: monitorexit
        //   180: aload 7
        //   182: athrow
        //   183: astore 7
        //   185: aload_1
        //   186: monitorexit
        //   187: aload 7
        //   189: athrow
        //   190: return
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	191	0	this	2
        //   0	191	1	paramAnonymousSubjectObserver	SubjectSubscriptionManager.SubjectObserver<T>
        //   38	130	2	i	int
        //   35	117	3	j	int
        //   32	64	4	k	int
        //   68	42	5	m	int
        //   58	26	6	n	int
        //   43	62	7	localUnboundedReplayState	ReplaySubject.UnboundedReplayState
        //   137	5	7	localObject1	Object
        //   144	5	7	localObject2	Object
        //   158	5	7	localObject3	Object
        //   165	16	7	localObject4	Object
        //   183	5	7	localObject5	Object
        // Exception table:
        //   from	to	target	type
        //   129	136	137	finally
        //   139	141	137	finally
        //   2	16	144	finally
        //   16	18	144	finally
        //   19	31	144	finally
        //   146	148	144	finally
        //   102	112	158	finally
        //   114	119	158	finally
        //   121	123	158	finally
        //   153	155	158	finally
        //   160	162	158	finally
        //   39	45	165	finally
        //   48	60	165	finally
        //   63	70	165	finally
        //   80	95	165	finally
        //   98	100	165	finally
        //   162	165	165	finally
        //   173	180	183	finally
        //   185	187	183	finally
      }
    };
    localSubjectSubscriptionManager.onTerminated = new Action1()
    {
      public void call(SubjectSubscriptionManager.SubjectObserver<T> paramAnonymousSubjectObserver)
      {
        Integer localInteger2 = (Integer)paramAnonymousSubjectObserver.index();
        Integer localInteger1 = localInteger2;
        if (localInteger2 == null) {
          localInteger1 = Integer.valueOf(0);
        }
        this.val$state.replayObserverFromIndex(localInteger1, paramAnonymousSubjectObserver);
      }
    };
    return new ReplaySubject(localSubjectSubscriptionManager, localSubjectSubscriptionManager, localUnboundedReplayState);
  }
  
  static <T> ReplaySubject<T> createUnbounded()
  {
    BoundedState localBoundedState = new BoundedState(new EmptyEvictionPolicy(), UtilityFunctions.identity(), UtilityFunctions.identity());
    return createWithState(localBoundedState, new DefaultOnAdd(localBoundedState));
  }
  
  public static <T> ReplaySubject<T> createWithSize(int paramInt)
  {
    BoundedState localBoundedState = new BoundedState(new SizeEvictionPolicy(paramInt), UtilityFunctions.identity(), UtilityFunctions.identity());
    return createWithState(localBoundedState, new DefaultOnAdd(localBoundedState));
  }
  
  static <T> ReplaySubject<T> createWithState(BoundedState<T> paramBoundedState, Action1<SubjectSubscriptionManager.SubjectObserver<T>> paramAction1)
  {
    SubjectSubscriptionManager localSubjectSubscriptionManager = new SubjectSubscriptionManager();
    localSubjectSubscriptionManager.onStart = paramAction1;
    localSubjectSubscriptionManager.onAdded = new Action1()
    {
      /* Error */
      public void call(SubjectSubscriptionManager.SubjectObserver<T> paramAnonymousSubjectObserver)
      {
        // Byte code:
        //   0: aload_1
        //   1: monitorenter
        //   2: aload_1
        //   3: getfield 33	rx/subjects/SubjectSubscriptionManager$SubjectObserver:first	Z
        //   6: ifeq +10 -> 16
        //   9: aload_1
        //   10: getfield 36	rx/subjects/SubjectSubscriptionManager$SubjectObserver:emitting	Z
        //   13: ifeq +6 -> 19
        //   16: aload_1
        //   17: monitorexit
        //   18: return
        //   19: aload_1
        //   20: iconst_0
        //   21: putfield 33	rx/subjects/SubjectSubscriptionManager$SubjectObserver:first	Z
        //   24: aload_1
        //   25: iconst_1
        //   26: putfield 36	rx/subjects/SubjectSubscriptionManager$SubjectObserver:emitting	Z
        //   29: aload_1
        //   30: monitorexit
        //   31: iconst_0
        //   32: istore 4
        //   34: iconst_0
        //   35: istore_3
        //   36: iload 4
        //   38: istore_2
        //   39: aload_1
        //   40: invokevirtual 40	rx/subjects/SubjectSubscriptionManager$SubjectObserver:index	()Ljava/lang/Object;
        //   43: checkcast 42	rx/subjects/ReplaySubject$NodeList$Node
        //   46: astore 5
        //   48: iload 4
        //   50: istore_2
        //   51: aload_0
        //   52: getfield 18	rx/subjects/ReplaySubject$4:val$state	Lrx/subjects/ReplaySubject$BoundedState;
        //   55: invokevirtual 48	rx/subjects/ReplaySubject$BoundedState:tail	()Lrx/subjects/ReplaySubject$NodeList$Node;
        //   58: astore 6
        //   60: aload 5
        //   62: aload 6
        //   64: if_acmpeq +20 -> 84
        //   67: iload 4
        //   69: istore_2
        //   70: aload_1
        //   71: aload_0
        //   72: getfield 18	rx/subjects/ReplaySubject$4:val$state	Lrx/subjects/ReplaySubject$BoundedState;
        //   75: aload 5
        //   77: aload_1
        //   78: invokevirtual 52	rx/subjects/ReplaySubject$BoundedState:replayObserverFromIndex	(Lrx/subjects/ReplaySubject$NodeList$Node;Lrx/subjects/SubjectSubscriptionManager$SubjectObserver;)Lrx/subjects/ReplaySubject$NodeList$Node;
        //   81: invokevirtual 54	rx/subjects/SubjectSubscriptionManager$SubjectObserver:index	(Ljava/lang/Object;)V
        //   84: iload 4
        //   86: istore_2
        //   87: aload_1
        //   88: monitorenter
        //   89: iload_3
        //   90: istore_2
        //   91: aload 6
        //   93: aload_0
        //   94: getfield 18	rx/subjects/ReplaySubject$4:val$state	Lrx/subjects/ReplaySubject$BoundedState;
        //   97: invokevirtual 48	rx/subjects/ReplaySubject$BoundedState:tail	()Lrx/subjects/ReplaySubject$NodeList$Node;
        //   100: if_acmpne +42 -> 142
        //   103: iload_3
        //   104: istore_2
        //   105: aload_1
        //   106: iconst_0
        //   107: putfield 36	rx/subjects/SubjectSubscriptionManager$SubjectObserver:emitting	Z
        //   110: iconst_1
        //   111: istore_2
        //   112: aload_1
        //   113: monitorexit
        //   114: iconst_1
        //   115: ifne +66 -> 181
        //   118: aload_1
        //   119: monitorenter
        //   120: aload_1
        //   121: iconst_0
        //   122: putfield 36	rx/subjects/SubjectSubscriptionManager$SubjectObserver:emitting	Z
        //   125: aload_1
        //   126: monitorexit
        //   127: return
        //   128: astore 5
        //   130: aload_1
        //   131: monitorexit
        //   132: aload 5
        //   134: athrow
        //   135: astore 5
        //   137: aload_1
        //   138: monitorexit
        //   139: aload 5
        //   141: athrow
        //   142: iload_3
        //   143: istore_2
        //   144: aload_1
        //   145: monitorexit
        //   146: goto -110 -> 36
        //   149: astore 5
        //   151: aload_1
        //   152: monitorexit
        //   153: aload 5
        //   155: athrow
        //   156: astore 5
        //   158: iload_2
        //   159: ifne +12 -> 171
        //   162: aload_1
        //   163: monitorenter
        //   164: aload_1
        //   165: iconst_0
        //   166: putfield 36	rx/subjects/SubjectSubscriptionManager$SubjectObserver:emitting	Z
        //   169: aload_1
        //   170: monitorexit
        //   171: aload 5
        //   173: athrow
        //   174: astore 5
        //   176: aload_1
        //   177: monitorexit
        //   178: aload 5
        //   180: athrow
        //   181: return
        // Local variable table:
        //   start	length	slot	name	signature
        //   0	182	0	this	4
        //   0	182	1	paramAnonymousSubjectObserver	SubjectSubscriptionManager.SubjectObserver<T>
        //   38	121	2	i	int
        //   35	108	3	j	int
        //   32	53	4	k	int
        //   46	30	5	localNode1	ReplaySubject.NodeList.Node
        //   128	5	5	localObject1	Object
        //   135	5	5	localObject2	Object
        //   149	5	5	localObject3	Object
        //   156	16	5	localObject4	Object
        //   174	5	5	localObject5	Object
        //   58	34	6	localNode2	ReplaySubject.NodeList.Node
        // Exception table:
        //   from	to	target	type
        //   120	127	128	finally
        //   130	132	128	finally
        //   2	16	135	finally
        //   16	18	135	finally
        //   19	31	135	finally
        //   137	139	135	finally
        //   91	103	149	finally
        //   105	110	149	finally
        //   112	114	149	finally
        //   144	146	149	finally
        //   151	153	149	finally
        //   39	48	156	finally
        //   51	60	156	finally
        //   70	84	156	finally
        //   87	89	156	finally
        //   153	156	156	finally
        //   164	171	174	finally
        //   176	178	174	finally
      }
    };
    localSubjectSubscriptionManager.onTerminated = new Action1()
    {
      public void call(SubjectSubscriptionManager.SubjectObserver<T> paramAnonymousSubjectObserver)
      {
        ReplaySubject.NodeList.Node localNode2 = (ReplaySubject.NodeList.Node)paramAnonymousSubjectObserver.index();
        ReplaySubject.NodeList.Node localNode1 = localNode2;
        if (localNode2 == null) {
          localNode1 = this.val$state.head();
        }
        this.val$state.replayObserverFromIndex(localNode1, paramAnonymousSubjectObserver);
      }
    };
    return new ReplaySubject(localSubjectSubscriptionManager, localSubjectSubscriptionManager, paramBoundedState);
  }
  
  public static <T> ReplaySubject<T> createWithTime(long paramLong, TimeUnit paramTimeUnit, Scheduler paramScheduler)
  {
    paramTimeUnit = new BoundedState(new TimeEvictionPolicy(paramTimeUnit.toMillis(paramLong), paramScheduler), new AddTimestamped(paramScheduler), new RemoveTimestamped());
    return createWithState(paramTimeUnit, new TimedOnAdd(paramTimeUnit, paramScheduler));
  }
  
  public static <T> ReplaySubject<T> createWithTimeAndSize(long paramLong, TimeUnit paramTimeUnit, int paramInt, Scheduler paramScheduler)
  {
    paramTimeUnit = new BoundedState(new PairEvictionPolicy(new SizeEvictionPolicy(paramInt), new TimeEvictionPolicy(paramTimeUnit.toMillis(paramLong), paramScheduler)), new AddTimestamped(paramScheduler), new RemoveTimestamped());
    return createWithState(paramTimeUnit, new TimedOnAdd(paramTimeUnit, paramScheduler));
  }
  
  @Beta
  public Throwable getThrowable()
  {
    NotificationLite localNotificationLite = this.ssm.nl;
    Object localObject = this.ssm.getLatest();
    if (localNotificationLite.isError(localObject)) {
      return localNotificationLite.getError(localObject);
    }
    return null;
  }
  
  @Beta
  public T getValue()
  {
    return (T)this.state.latest();
  }
  
  @Beta
  public Object[] getValues()
  {
    Object[] arrayOfObject2 = getValues((Object[])EMPTY_ARRAY);
    Object[] arrayOfObject1 = arrayOfObject2;
    if (arrayOfObject2 == EMPTY_ARRAY) {
      arrayOfObject1 = new Object[0];
    }
    return arrayOfObject1;
  }
  
  @Beta
  public T[] getValues(T[] paramArrayOfT)
  {
    return this.state.toArray(paramArrayOfT);
  }
  
  @Beta
  public boolean hasAnyValue()
  {
    return !this.state.isEmpty();
  }
  
  @Beta
  public boolean hasCompleted()
  {
    NotificationLite localNotificationLite = this.ssm.nl;
    Object localObject = this.ssm.getLatest();
    return (localObject != null) && (!localNotificationLite.isError(localObject));
  }
  
  public boolean hasObservers()
  {
    return this.ssm.observers().length > 0;
  }
  
  @Beta
  public boolean hasThrowable()
  {
    return this.ssm.nl.isError(this.ssm.getLatest());
  }
  
  @Beta
  public boolean hasValue()
  {
    return hasAnyValue();
  }
  
  public void onCompleted()
  {
    if (this.ssm.active)
    {
      this.state.complete();
      SubjectSubscriptionManager.SubjectObserver[] arrayOfSubjectObserver = this.ssm.terminate(NotificationLite.instance().completed());
      int j = arrayOfSubjectObserver.length;
      int i = 0;
      while (i < j)
      {
        SubjectSubscriptionManager.SubjectObserver localSubjectObserver = arrayOfSubjectObserver[i];
        if (caughtUp(localSubjectObserver)) {
          localSubjectObserver.onCompleted();
        }
        i += 1;
      }
    }
  }
  
  public void onError(Throwable paramThrowable)
  {
    if (this.ssm.active)
    {
      this.state.error(paramThrowable);
      Object localObject1 = null;
      SubjectSubscriptionManager.SubjectObserver[] arrayOfSubjectObserver = this.ssm.terminate(NotificationLite.instance().error(paramThrowable));
      int j = arrayOfSubjectObserver.length;
      int i = 0;
      Object localObject2;
      for (;;)
      {
        if (i >= j) {
          break label123;
        }
        SubjectSubscriptionManager.SubjectObserver localSubjectObserver = arrayOfSubjectObserver[i];
        localObject2 = localObject1;
        try
        {
          if (caughtUp(localSubjectObserver))
          {
            localSubjectObserver.onError(paramThrowable);
            localObject2 = localObject1;
          }
        }
        catch (Throwable localThrowable)
        {
          for (;;)
          {
            localObject2 = localObject1;
            if (localObject1 == null) {
              localObject2 = new ArrayList();
            }
            ((List)localObject2).add(localThrowable);
          }
        }
        i += 1;
        localObject1 = localObject2;
      }
      label123:
      Exceptions.throwIfAny((List)localObject1);
    }
  }
  
  public void onNext(T paramT)
  {
    if (this.ssm.active)
    {
      this.state.next(paramT);
      SubjectSubscriptionManager.SubjectObserver[] arrayOfSubjectObserver = this.ssm.observers();
      int j = arrayOfSubjectObserver.length;
      int i = 0;
      while (i < j)
      {
        SubjectSubscriptionManager.SubjectObserver localSubjectObserver = arrayOfSubjectObserver[i];
        if (caughtUp(localSubjectObserver)) {
          localSubjectObserver.onNext(paramT);
        }
        i += 1;
      }
    }
  }
  
  @Beta
  public int size()
  {
    return this.state.size();
  }
  
  int subscriberCount()
  {
    return ((SubjectSubscriptionManager.State)this.ssm.get()).observers.length;
  }
  
  static final class AddTimestamped
    implements Func1<Object, Object>
  {
    final Scheduler scheduler;
    
    public AddTimestamped(Scheduler paramScheduler)
    {
      this.scheduler = paramScheduler;
    }
    
    public Object call(Object paramObject)
    {
      return new Timestamped(this.scheduler.now(), paramObject);
    }
  }
  
  static final class BoundedState<T>
    implements ReplaySubject.ReplayState<T, ReplaySubject.NodeList.Node<Object>>
  {
    final Func1<Object, Object> enterTransform;
    final ReplaySubject.EvictionPolicy evictionPolicy;
    final Func1<Object, Object> leaveTransform;
    final ReplaySubject.NodeList<Object> list = new ReplaySubject.NodeList();
    final NotificationLite<T> nl = NotificationLite.instance();
    volatile ReplaySubject.NodeList.Node<Object> tail = this.list.tail;
    volatile boolean terminated;
    
    public BoundedState(ReplaySubject.EvictionPolicy paramEvictionPolicy, Func1<Object, Object> paramFunc11, Func1<Object, Object> paramFunc12)
    {
      this.evictionPolicy = paramEvictionPolicy;
      this.enterTransform = paramFunc11;
      this.leaveTransform = paramFunc12;
    }
    
    public void accept(Observer<? super T> paramObserver, ReplaySubject.NodeList.Node<Object> paramNode)
    {
      this.nl.accept(paramObserver, this.leaveTransform.call(paramNode.value));
    }
    
    public void acceptTest(Observer<? super T> paramObserver, ReplaySubject.NodeList.Node<Object> paramNode, long paramLong)
    {
      paramNode = paramNode.value;
      if (!this.evictionPolicy.test(paramNode, paramLong)) {
        this.nl.accept(paramObserver, this.leaveTransform.call(paramNode));
      }
    }
    
    public void complete()
    {
      if (!this.terminated)
      {
        this.terminated = true;
        this.list.addLast(this.enterTransform.call(this.nl.completed()));
        this.evictionPolicy.evictFinal(this.list);
        this.tail = this.list.tail;
      }
    }
    
    public void error(Throwable paramThrowable)
    {
      if (!this.terminated)
      {
        this.terminated = true;
        this.list.addLast(this.enterTransform.call(this.nl.error(paramThrowable)));
        this.evictionPolicy.evictFinal(this.list);
        this.tail = this.list.tail;
      }
    }
    
    public ReplaySubject.NodeList.Node<Object> head()
    {
      return this.list.head;
    }
    
    public boolean isEmpty()
    {
      Object localObject = head().next;
      if (localObject == null) {}
      do
      {
        return true;
        localObject = this.leaveTransform.call(((ReplaySubject.NodeList.Node)localObject).value);
      } while ((this.nl.isError(localObject)) || (this.nl.isCompleted(localObject)));
      return false;
    }
    
    public T latest()
    {
      Object localObject1 = head().next;
      if (localObject1 == null) {}
      Object localObject2;
      do
      {
        return null;
        localObject2 = null;
        while (localObject1 != tail())
        {
          localObject2 = localObject1;
          localObject1 = ((ReplaySubject.NodeList.Node)localObject1).next;
        }
        localObject1 = this.leaveTransform.call(((ReplaySubject.NodeList.Node)localObject1).value);
        if ((!this.nl.isError(localObject1)) && (!this.nl.isCompleted(localObject1))) {
          break;
        }
      } while (localObject2 == null);
      localObject1 = this.leaveTransform.call(((ReplaySubject.NodeList.Node)localObject2).value);
      return (T)this.nl.getValue(localObject1);
      return (T)this.nl.getValue(localObject1);
    }
    
    public void next(T paramT)
    {
      if (!this.terminated)
      {
        this.list.addLast(this.enterTransform.call(this.nl.next(paramT)));
        this.evictionPolicy.evict(this.list);
        this.tail = this.list.tail;
      }
    }
    
    public boolean replayObserver(SubjectSubscriptionManager.SubjectObserver<? super T> paramSubjectObserver)
    {
      try
      {
        paramSubjectObserver.first = false;
        if (paramSubjectObserver.emitting) {
          return false;
        }
        paramSubjectObserver.index(replayObserverFromIndex((ReplaySubject.NodeList.Node)paramSubjectObserver.index(), paramSubjectObserver));
        return true;
      }
      finally {}
    }
    
    public ReplaySubject.NodeList.Node<Object> replayObserverFromIndex(ReplaySubject.NodeList.Node<Object> paramNode, SubjectSubscriptionManager.SubjectObserver<? super T> paramSubjectObserver)
    {
      while (paramNode != tail())
      {
        accept(paramSubjectObserver, paramNode.next);
        paramNode = paramNode.next;
      }
      return paramNode;
    }
    
    public ReplaySubject.NodeList.Node<Object> replayObserverFromIndexTest(ReplaySubject.NodeList.Node<Object> paramNode, SubjectSubscriptionManager.SubjectObserver<? super T> paramSubjectObserver, long paramLong)
    {
      while (paramNode != tail())
      {
        acceptTest(paramSubjectObserver, paramNode.next, paramLong);
        paramNode = paramNode.next;
      }
      return paramNode;
    }
    
    public int size()
    {
      int i = 0;
      Object localObject2 = head();
      for (Object localObject1 = ((ReplaySubject.NodeList.Node)localObject2).next; localObject1 != null; localObject1 = ((ReplaySubject.NodeList.Node)localObject1).next)
      {
        i += 1;
        localObject2 = localObject1;
      }
      int j = i;
      if (((ReplaySubject.NodeList.Node)localObject2).value != null)
      {
        localObject1 = this.leaveTransform.call(((ReplaySubject.NodeList.Node)localObject2).value);
        j = i;
        if (localObject1 != null) {
          if (!this.nl.isError(localObject1))
          {
            j = i;
            if (!this.nl.isCompleted(localObject1)) {}
          }
          else
          {
            j = i - 1;
          }
        }
      }
      return j;
    }
    
    public ReplaySubject.NodeList.Node<Object> tail()
    {
      return this.tail;
    }
    
    public boolean terminated()
    {
      return this.terminated;
    }
    
    public T[] toArray(T[] paramArrayOfT)
    {
      ArrayList localArrayList = new ArrayList();
      for (ReplaySubject.NodeList.Node localNode = head().next;; localNode = localNode.next)
      {
        Object localObject;
        if (localNode != null)
        {
          localObject = this.leaveTransform.call(localNode.value);
          if ((localNode.next != null) || ((!this.nl.isError(localObject)) && (!this.nl.isCompleted(localObject)))) {}
        }
        else
        {
          return localArrayList.toArray(paramArrayOfT);
        }
        localArrayList.add(localObject);
      }
    }
  }
  
  static final class DefaultOnAdd<T>
    implements Action1<SubjectSubscriptionManager.SubjectObserver<T>>
  {
    final ReplaySubject.BoundedState<T> state;
    
    public DefaultOnAdd(ReplaySubject.BoundedState<T> paramBoundedState)
    {
      this.state = paramBoundedState;
    }
    
    public void call(SubjectSubscriptionManager.SubjectObserver<T> paramSubjectObserver)
    {
      paramSubjectObserver.index(this.state.replayObserverFromIndex(this.state.head(), paramSubjectObserver));
    }
  }
  
  static final class EmptyEvictionPolicy
    implements ReplaySubject.EvictionPolicy
  {
    public void evict(ReplaySubject.NodeList<Object> paramNodeList) {}
    
    public void evictFinal(ReplaySubject.NodeList<Object> paramNodeList) {}
    
    public boolean test(Object paramObject, long paramLong)
    {
      return true;
    }
  }
  
  static abstract interface EvictionPolicy
  {
    public abstract void evict(ReplaySubject.NodeList<Object> paramNodeList);
    
    public abstract void evictFinal(ReplaySubject.NodeList<Object> paramNodeList);
    
    public abstract boolean test(Object paramObject, long paramLong);
  }
  
  static final class NodeList<T>
  {
    final Node<T> head = new Node(null);
    int size;
    Node<T> tail = this.head;
    
    public void addLast(T paramT)
    {
      Node localNode = this.tail;
      paramT = new Node(paramT);
      localNode.next = paramT;
      this.tail = paramT;
      this.size += 1;
    }
    
    public void clear()
    {
      this.tail = this.head;
      this.size = 0;
    }
    
    public boolean isEmpty()
    {
      return this.size == 0;
    }
    
    public T removeFirst()
    {
      if (this.head.next == null) {
        throw new IllegalStateException("Empty!");
      }
      Node localNode = this.head.next;
      this.head.next = localNode.next;
      if (this.head.next == null) {
        this.tail = this.head;
      }
      this.size -= 1;
      return (T)localNode.value;
    }
    
    public int size()
    {
      return this.size;
    }
    
    static final class Node<T>
    {
      volatile Node<T> next;
      final T value;
      
      Node(T paramT)
      {
        this.value = paramT;
      }
    }
  }
  
  static final class PairEvictionPolicy
    implements ReplaySubject.EvictionPolicy
  {
    final ReplaySubject.EvictionPolicy first;
    final ReplaySubject.EvictionPolicy second;
    
    public PairEvictionPolicy(ReplaySubject.EvictionPolicy paramEvictionPolicy1, ReplaySubject.EvictionPolicy paramEvictionPolicy2)
    {
      this.first = paramEvictionPolicy1;
      this.second = paramEvictionPolicy2;
    }
    
    public void evict(ReplaySubject.NodeList<Object> paramNodeList)
    {
      this.first.evict(paramNodeList);
      this.second.evict(paramNodeList);
    }
    
    public void evictFinal(ReplaySubject.NodeList<Object> paramNodeList)
    {
      this.first.evictFinal(paramNodeList);
      this.second.evictFinal(paramNodeList);
    }
    
    public boolean test(Object paramObject, long paramLong)
    {
      return (this.first.test(paramObject, paramLong)) || (this.second.test(paramObject, paramLong));
    }
  }
  
  static final class RemoveTimestamped
    implements Func1<Object, Object>
  {
    public Object call(Object paramObject)
    {
      return ((Timestamped)paramObject).getValue();
    }
  }
  
  static abstract interface ReplayState<T, I>
  {
    public abstract void complete();
    
    public abstract void error(Throwable paramThrowable);
    
    public abstract boolean isEmpty();
    
    public abstract T latest();
    
    public abstract void next(T paramT);
    
    public abstract boolean replayObserver(SubjectSubscriptionManager.SubjectObserver<? super T> paramSubjectObserver);
    
    public abstract I replayObserverFromIndex(I paramI, SubjectSubscriptionManager.SubjectObserver<? super T> paramSubjectObserver);
    
    public abstract I replayObserverFromIndexTest(I paramI, SubjectSubscriptionManager.SubjectObserver<? super T> paramSubjectObserver, long paramLong);
    
    public abstract int size();
    
    public abstract boolean terminated();
    
    public abstract T[] toArray(T[] paramArrayOfT);
  }
  
  static final class SizeEvictionPolicy
    implements ReplaySubject.EvictionPolicy
  {
    final int maxSize;
    
    public SizeEvictionPolicy(int paramInt)
    {
      this.maxSize = paramInt;
    }
    
    public void evict(ReplaySubject.NodeList<Object> paramNodeList)
    {
      while (paramNodeList.size() > this.maxSize) {
        paramNodeList.removeFirst();
      }
    }
    
    public void evictFinal(ReplaySubject.NodeList<Object> paramNodeList)
    {
      while (paramNodeList.size() > this.maxSize + 1) {
        paramNodeList.removeFirst();
      }
    }
    
    public boolean test(Object paramObject, long paramLong)
    {
      return false;
    }
  }
  
  static final class TimeEvictionPolicy
    implements ReplaySubject.EvictionPolicy
  {
    final long maxAgeMillis;
    final Scheduler scheduler;
    
    public TimeEvictionPolicy(long paramLong, Scheduler paramScheduler)
    {
      this.maxAgeMillis = paramLong;
      this.scheduler = paramScheduler;
    }
    
    public void evict(ReplaySubject.NodeList<Object> paramNodeList)
    {
      long l = this.scheduler.now();
      while ((!paramNodeList.isEmpty()) && (test(paramNodeList.head.next.value, l))) {
        paramNodeList.removeFirst();
      }
    }
    
    public void evictFinal(ReplaySubject.NodeList<Object> paramNodeList)
    {
      long l = this.scheduler.now();
      while ((paramNodeList.size > 1) && (test(paramNodeList.head.next.value, l))) {
        paramNodeList.removeFirst();
      }
    }
    
    public boolean test(Object paramObject, long paramLong)
    {
      return ((Timestamped)paramObject).getTimestampMillis() <= paramLong - this.maxAgeMillis;
    }
  }
  
  static final class TimedOnAdd<T>
    implements Action1<SubjectSubscriptionManager.SubjectObserver<T>>
  {
    final Scheduler scheduler;
    final ReplaySubject.BoundedState<T> state;
    
    public TimedOnAdd(ReplaySubject.BoundedState<T> paramBoundedState, Scheduler paramScheduler)
    {
      this.state = paramBoundedState;
      this.scheduler = paramScheduler;
    }
    
    public void call(SubjectSubscriptionManager.SubjectObserver<T> paramSubjectObserver)
    {
      if (!this.state.terminated) {}
      for (ReplaySubject.NodeList.Node localNode = this.state.replayObserverFromIndexTest(this.state.head(), paramSubjectObserver, this.scheduler.now());; localNode = this.state.replayObserverFromIndex(this.state.head(), paramSubjectObserver))
      {
        paramSubjectObserver.index(localNode);
        return;
      }
    }
  }
  
  static final class UnboundedReplayState<T>
    extends AtomicInteger
    implements ReplaySubject.ReplayState<T, Integer>
  {
    private final ArrayList<Object> list;
    private final NotificationLite<T> nl = NotificationLite.instance();
    private volatile boolean terminated;
    
    public UnboundedReplayState(int paramInt)
    {
      this.list = new ArrayList(paramInt);
    }
    
    public void accept(Observer<? super T> paramObserver, int paramInt)
    {
      this.nl.accept(paramObserver, this.list.get(paramInt));
    }
    
    public void complete()
    {
      if (!this.terminated)
      {
        this.terminated = true;
        this.list.add(this.nl.completed());
        getAndIncrement();
      }
    }
    
    public void error(Throwable paramThrowable)
    {
      if (!this.terminated)
      {
        this.terminated = true;
        this.list.add(this.nl.error(paramThrowable));
        getAndIncrement();
      }
    }
    
    public boolean isEmpty()
    {
      return size() == 0;
    }
    
    public T latest()
    {
      Object localObject2 = null;
      int i = get();
      Object localObject1 = localObject2;
      if (i > 0)
      {
        localObject1 = this.list.get(i - 1);
        if ((!this.nl.isCompleted(localObject1)) && (!this.nl.isError(localObject1))) {
          break label73;
        }
        localObject1 = localObject2;
        if (i > 1) {
          localObject1 = this.nl.getValue(this.list.get(i - 2));
        }
      }
      return (T)localObject1;
      label73:
      return (T)this.nl.getValue(localObject1);
    }
    
    public void next(T paramT)
    {
      if (!this.terminated)
      {
        this.list.add(this.nl.next(paramT));
        getAndIncrement();
      }
    }
    
    public boolean replayObserver(SubjectSubscriptionManager.SubjectObserver<? super T> paramSubjectObserver)
    {
      try
      {
        paramSubjectObserver.first = false;
        if (paramSubjectObserver.emitting) {
          return false;
        }
        Integer localInteger = (Integer)paramSubjectObserver.index();
        if (localInteger != null)
        {
          paramSubjectObserver.index(Integer.valueOf(replayObserverFromIndex(localInteger, paramSubjectObserver).intValue()));
          return true;
        }
      }
      finally {}
      throw new IllegalStateException("failed to find lastEmittedLink for: " + paramSubjectObserver);
    }
    
    public Integer replayObserverFromIndex(Integer paramInteger, SubjectSubscriptionManager.SubjectObserver<? super T> paramSubjectObserver)
    {
      int i = paramInteger.intValue();
      while (i < get())
      {
        accept(paramSubjectObserver, i);
        i += 1;
      }
      return Integer.valueOf(i);
    }
    
    public Integer replayObserverFromIndexTest(Integer paramInteger, SubjectSubscriptionManager.SubjectObserver<? super T> paramSubjectObserver, long paramLong)
    {
      return replayObserverFromIndex(paramInteger, paramSubjectObserver);
    }
    
    public int size()
    {
      int j = get();
      int i = j;
      if (j > 0)
      {
        Object localObject = this.list.get(j - 1);
        if (!this.nl.isCompleted(localObject))
        {
          i = j;
          if (!this.nl.isError(localObject)) {}
        }
        else
        {
          i = j - 1;
        }
      }
      return i;
    }
    
    public boolean terminated()
    {
      return this.terminated;
    }
    
    public T[] toArray(T[] paramArrayOfT)
    {
      int j = size();
      Object localObject2;
      if (j > 0)
      {
        Object localObject1 = paramArrayOfT;
        if (j > paramArrayOfT.length) {
          localObject1 = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), j);
        }
        int i = 0;
        while (i < j)
        {
          localObject1[i] = this.list.get(i);
          i += 1;
        }
        localObject2 = localObject1;
        if (localObject1.length > j)
        {
          localObject1[j] = null;
          localObject2 = localObject1;
        }
      }
      do
      {
        return (T[])localObject2;
        localObject2 = paramArrayOfT;
      } while (paramArrayOfT.length <= 0);
      paramArrayOfT[0] = null;
      return paramArrayOfT;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/subjects/ReplaySubject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */