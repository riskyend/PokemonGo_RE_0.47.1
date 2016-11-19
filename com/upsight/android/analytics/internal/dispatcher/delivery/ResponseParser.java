package com.upsight.android.analytics.internal.dispatcher.delivery;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.analytics.dispatcher.EndpointResponse;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

class ResponseParser
{
  private Gson mGson;
  
  @Inject
  public ResponseParser(@Named("config-gson") Gson paramGson)
  {
    this.mGson = paramGson;
  }
  
  /* Error */
  public Response parse(String paramString)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 26	com/upsight/android/analytics/internal/dispatcher/delivery/ResponseParser:mGson	Lcom/google/gson/Gson;
    //   6: aload_1
    //   7: ldc 12
    //   9: invokevirtual 41	com/google/gson/Gson:fromJson	(Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   12: checkcast 12	com/upsight/android/analytics/internal/dispatcher/delivery/ResponseParser$ResponseJson
    //   15: astore_1
    //   16: new 43	java/util/LinkedList
    //   19: dup
    //   20: invokespecial 44	java/util/LinkedList:<init>	()V
    //   23: astore_2
    //   24: aload_1
    //   25: getfield 48	com/upsight/android/analytics/internal/dispatcher/delivery/ResponseParser$ResponseJson:response	Ljava/util/List;
    //   28: ifnull +74 -> 102
    //   31: aload_1
    //   32: getfield 48	com/upsight/android/analytics/internal/dispatcher/delivery/ResponseParser$ResponseJson:response	Ljava/util/List;
    //   35: invokeinterface 54 1 0
    //   40: astore_3
    //   41: aload_3
    //   42: invokeinterface 60 1 0
    //   47: ifeq +55 -> 102
    //   50: aload_3
    //   51: invokeinterface 64 1 0
    //   56: checkcast 9	com/upsight/android/analytics/internal/dispatcher/delivery/ResponseParser$ResponseElementJson
    //   59: astore 4
    //   61: aload_2
    //   62: aload 4
    //   64: getfield 68	com/upsight/android/analytics/internal/dispatcher/delivery/ResponseParser$ResponseElementJson:type	Ljava/lang/String;
    //   67: aload 4
    //   69: getfield 72	com/upsight/android/analytics/internal/dispatcher/delivery/ResponseParser$ResponseElementJson:content	Lcom/google/gson/JsonElement;
    //   72: invokevirtual 78	com/google/gson/JsonElement:toString	()Ljava/lang/String;
    //   75: invokestatic 84	com/upsight/android/analytics/dispatcher/EndpointResponse:create	(Ljava/lang/String;Ljava/lang/String;)Lcom/upsight/android/analytics/dispatcher/EndpointResponse;
    //   78: invokeinterface 90 2 0
    //   83: pop
    //   84: goto -43 -> 41
    //   87: astore_1
    //   88: aload_0
    //   89: monitorexit
    //   90: aload_1
    //   91: athrow
    //   92: astore_1
    //   93: new 33	java/io/IOException
    //   96: dup
    //   97: aload_1
    //   98: invokespecial 93	java/io/IOException:<init>	(Ljava/lang/Throwable;)V
    //   101: athrow
    //   102: new 6	com/upsight/android/analytics/internal/dispatcher/delivery/ResponseParser$Response
    //   105: dup
    //   106: aload_2
    //   107: aload_1
    //   108: getfield 96	com/upsight/android/analytics/internal/dispatcher/delivery/ResponseParser$ResponseJson:error	Ljava/lang/String;
    //   111: invokespecial 99	com/upsight/android/analytics/internal/dispatcher/delivery/ResponseParser$Response:<init>	(Ljava/util/Collection;Ljava/lang/String;)V
    //   114: astore_1
    //   115: aload_0
    //   116: monitorexit
    //   117: aload_1
    //   118: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	119	0	this	ResponseParser
    //   0	119	1	paramString	String
    //   23	84	2	localLinkedList	java.util.LinkedList
    //   40	11	3	localIterator	java.util.Iterator
    //   59	9	4	localResponseElementJson	ResponseElementJson
    // Exception table:
    //   from	to	target	type
    //   2	16	87	finally
    //   16	41	87	finally
    //   41	84	87	finally
    //   93	102	87	finally
    //   102	115	87	finally
    //   2	16	92	com/google/gson/JsonSyntaxException
  }
  
  public static class Response
  {
    public final String error;
    public final Collection<EndpointResponse> responses;
    
    public Response(Collection<EndpointResponse> paramCollection, String paramString)
    {
      this.responses = paramCollection;
      this.error = paramString;
    }
  }
  
  public static class ResponseElementJson
  {
    @Expose
    @SerializedName("content")
    public JsonElement content;
    @Expose
    @SerializedName("type")
    public String type;
  }
  
  public static class ResponseJson
  {
    @Expose
    @SerializedName("error")
    public String error;
    @Expose
    @SerializedName("response")
    public List<ResponseParser.ResponseElementJson> response;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/delivery/ResponseParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */