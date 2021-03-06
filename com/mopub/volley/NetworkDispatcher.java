package com.mopub.volley;

import android.annotation.TargetApi;
import android.net.TrafficStats;
import android.os.Build.VERSION;
import java.util.concurrent.BlockingQueue;

public class NetworkDispatcher
  extends Thread
{
  private final Cache mCache;
  private final ResponseDelivery mDelivery;
  private final Network mNetwork;
  private final BlockingQueue<Request<?>> mQueue;
  private volatile boolean mQuit = false;
  
  public NetworkDispatcher(BlockingQueue<Request<?>> paramBlockingQueue, Network paramNetwork, Cache paramCache, ResponseDelivery paramResponseDelivery)
  {
    this.mQueue = paramBlockingQueue;
    this.mNetwork = paramNetwork;
    this.mCache = paramCache;
    this.mDelivery = paramResponseDelivery;
  }
  
  @TargetApi(14)
  private void addTrafficStatsTag(Request<?> paramRequest)
  {
    if (Build.VERSION.SDK_INT >= 14) {
      TrafficStats.setThreadStatsTag(paramRequest.getTrafficStatsTag());
    }
  }
  
  private void parseAndDeliverNetworkError(Request<?> paramRequest, VolleyError paramVolleyError)
  {
    paramVolleyError = paramRequest.parseNetworkError(paramVolleyError);
    this.mDelivery.postError(paramRequest, paramVolleyError);
  }
  
  public void quit()
  {
    this.mQuit = true;
    interrupt();
  }
  
  /* Error */
  public void run()
  {
    // Byte code:
    //   0: bipush 10
    //   2: invokestatic 86	android/os/Process:setThreadPriority	(I)V
    //   5: invokestatic 92	android/os/SystemClock:elapsedRealtime	()J
    //   8: lstore_1
    //   9: aload_0
    //   10: getfield 24	com/mopub/volley/NetworkDispatcher:mQueue	Ljava/util/concurrent/BlockingQueue;
    //   13: invokeinterface 98 1 0
    //   18: checkcast 46	com/mopub/volley/Request
    //   21: astore_3
    //   22: aload_3
    //   23: ldc 100
    //   25: invokevirtual 104	com/mopub/volley/Request:addMarker	(Ljava/lang/String;)V
    //   28: aload_3
    //   29: invokevirtual 108	com/mopub/volley/Request:isCanceled	()Z
    //   32: ifeq +43 -> 75
    //   35: aload_3
    //   36: ldc 110
    //   38: invokevirtual 113	com/mopub/volley/Request:finish	(Ljava/lang/String;)V
    //   41: goto -36 -> 5
    //   44: astore 4
    //   46: aload 4
    //   48: invokestatic 92	android/os/SystemClock:elapsedRealtime	()J
    //   51: lload_1
    //   52: lsub
    //   53: invokevirtual 117	com/mopub/volley/VolleyError:setNetworkTimeMs	(J)V
    //   56: aload_0
    //   57: aload_3
    //   58: aload 4
    //   60: invokespecial 119	com/mopub/volley/NetworkDispatcher:parseAndDeliverNetworkError	(Lcom/mopub/volley/Request;Lcom/mopub/volley/VolleyError;)V
    //   63: goto -58 -> 5
    //   66: astore_3
    //   67: aload_0
    //   68: getfield 22	com/mopub/volley/NetworkDispatcher:mQuit	Z
    //   71: ifeq -66 -> 5
    //   74: return
    //   75: aload_0
    //   76: aload_3
    //   77: invokespecial 121	com/mopub/volley/NetworkDispatcher:addTrafficStatsTag	(Lcom/mopub/volley/Request;)V
    //   80: aload_0
    //   81: getfield 26	com/mopub/volley/NetworkDispatcher:mNetwork	Lcom/mopub/volley/Network;
    //   84: aload_3
    //   85: invokeinterface 127 2 0
    //   90: astore 4
    //   92: aload_3
    //   93: ldc -127
    //   95: invokevirtual 104	com/mopub/volley/Request:addMarker	(Ljava/lang/String;)V
    //   98: aload 4
    //   100: getfield 134	com/mopub/volley/NetworkResponse:notModified	Z
    //   103: ifeq +76 -> 179
    //   106: aload_3
    //   107: invokevirtual 137	com/mopub/volley/Request:hasHadResponseDelivered	()Z
    //   110: ifeq +69 -> 179
    //   113: aload_3
    //   114: ldc -117
    //   116: invokevirtual 113	com/mopub/volley/Request:finish	(Ljava/lang/String;)V
    //   119: goto -114 -> 5
    //   122: astore 4
    //   124: aload 4
    //   126: ldc -115
    //   128: iconst_1
    //   129: anewarray 143	java/lang/Object
    //   132: dup
    //   133: iconst_0
    //   134: aload 4
    //   136: invokevirtual 147	java/lang/Exception:toString	()Ljava/lang/String;
    //   139: aastore
    //   140: invokestatic 153	com/mopub/volley/VolleyLog:e	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   143: new 79	com/mopub/volley/VolleyError
    //   146: dup
    //   147: aload 4
    //   149: invokespecial 156	com/mopub/volley/VolleyError:<init>	(Ljava/lang/Throwable;)V
    //   152: astore 4
    //   154: aload 4
    //   156: invokestatic 92	android/os/SystemClock:elapsedRealtime	()J
    //   159: lload_1
    //   160: lsub
    //   161: invokevirtual 117	com/mopub/volley/VolleyError:setNetworkTimeMs	(J)V
    //   164: aload_0
    //   165: getfield 30	com/mopub/volley/NetworkDispatcher:mDelivery	Lcom/mopub/volley/ResponseDelivery;
    //   168: aload_3
    //   169: aload 4
    //   171: invokeinterface 69 3 0
    //   176: goto -171 -> 5
    //   179: aload_3
    //   180: aload 4
    //   182: invokevirtual 160	com/mopub/volley/Request:parseNetworkResponse	(Lcom/mopub/volley/NetworkResponse;)Lcom/mopub/volley/Response;
    //   185: astore 4
    //   187: aload_3
    //   188: ldc -94
    //   190: invokevirtual 104	com/mopub/volley/Request:addMarker	(Ljava/lang/String;)V
    //   193: aload_3
    //   194: invokevirtual 165	com/mopub/volley/Request:shouldCache	()Z
    //   197: ifeq +35 -> 232
    //   200: aload 4
    //   202: getfield 171	com/mopub/volley/Response:cacheEntry	Lcom/mopub/volley/Cache$Entry;
    //   205: ifnull +27 -> 232
    //   208: aload_0
    //   209: getfield 28	com/mopub/volley/NetworkDispatcher:mCache	Lcom/mopub/volley/Cache;
    //   212: aload_3
    //   213: invokevirtual 174	com/mopub/volley/Request:getCacheKey	()Ljava/lang/String;
    //   216: aload 4
    //   218: getfield 171	com/mopub/volley/Response:cacheEntry	Lcom/mopub/volley/Cache$Entry;
    //   221: invokeinterface 180 3 0
    //   226: aload_3
    //   227: ldc -74
    //   229: invokevirtual 104	com/mopub/volley/Request:addMarker	(Ljava/lang/String;)V
    //   232: aload_3
    //   233: invokevirtual 185	com/mopub/volley/Request:markDelivered	()V
    //   236: aload_0
    //   237: getfield 30	com/mopub/volley/NetworkDispatcher:mDelivery	Lcom/mopub/volley/ResponseDelivery;
    //   240: aload_3
    //   241: aload 4
    //   243: invokeinterface 189 3 0
    //   248: goto -243 -> 5
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	251	0	this	NetworkDispatcher
    //   8	152	1	l	long
    //   21	37	3	localRequest	Request
    //   66	175	3	localInterruptedException	InterruptedException
    //   44	15	4	localVolleyError	VolleyError
    //   90	9	4	localNetworkResponse	NetworkResponse
    //   122	26	4	localException	Exception
    //   152	90	4	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   22	41	44	com/mopub/volley/VolleyError
    //   75	119	44	com/mopub/volley/VolleyError
    //   179	232	44	com/mopub/volley/VolleyError
    //   232	248	44	com/mopub/volley/VolleyError
    //   9	22	66	java/lang/InterruptedException
    //   22	41	122	java/lang/Exception
    //   75	119	122	java/lang/Exception
    //   179	232	122	java/lang/Exception
    //   232	248	122	java/lang/Exception
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/mopub/volley/NetworkDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */