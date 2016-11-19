package com.upsight.android.analytics.internal.dispatcher.delivery;

import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Base64;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.upsight.android.internal.util.GzipHelper;
import com.upsight.android.logger.UpsightLogger;
import java.io.IOException;
import java.net.HttpURLConnection;
import org.apache.commons.io.IOUtils;

class UpsightEndpoint
{
  private static final String CONNECTION_CLOSE = "close";
  private static final int CONNECTION_TIMEOUT_MS = 30000;
  private static final String CONTENT_ENCODING_GZIP = "gzip";
  private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";
  private static final String EMPTY_STRING = "";
  static final String HTTP_HEADER_REF_ID = "X-US-Ref-Id";
  static final String HTTP_HEADER_US_DIGEST = "X-US-DIGEST";
  public static final String LOG_TEXT_POSTING = "POSTING:       ";
  public static final String LOG_TEXT_RECEIVING = "RECEIVING:     ";
  public static final String LOG_TEXT_REQUEST_BODY = "\nREQUEST BODY:  ";
  public static final String LOG_TEXT_RESPONSE_BODY = "\nRESPONSE BODY: ";
  public static final String LOG_TEXT_RESPONSE_BODY_NONE = "[NONE]";
  public static final String LOG_TEXT_STATUS_CODE = "\nSTATUS CODE:   ";
  public static final String LOG_TEXT_TO = "\nTO:            ";
  private static final String POST_METHOD_NAME = "POST";
  public static final String SIGNED_MESSAGE_SEPARATOR = ":";
  private static final String USER_AGENT_ANDROID = "Android-" + Build.VERSION.SDK_INT;
  private static final boolean USE_GZIP = false;
  private String mEndpointAddress;
  private Gson mGson;
  private JsonParser mJsonParser;
  private UpsightLogger mLogger;
  private Gson mRequestLoggingGson;
  private SignatureVerifier mSignatureVerifier;
  
  public UpsightEndpoint(String paramString, SignatureVerifier paramSignatureVerifier, Gson paramGson1, JsonParser paramJsonParser, Gson paramGson2, UpsightLogger paramUpsightLogger)
  {
    this.mEndpointAddress = paramString;
    this.mSignatureVerifier = paramSignatureVerifier;
    this.mGson = paramGson1;
    this.mJsonParser = paramJsonParser;
    this.mRequestLoggingGson = paramGson2;
    this.mLogger = paramUpsightLogger;
  }
  
  private byte[] getRequestBodyBytes(String paramString, boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean) {
      return GzipHelper.compress(paramString);
    }
    return paramString.getBytes();
  }
  
  private String getVerifiedResponse(HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    String str1 = "";
    Object localObject2 = paramHttpURLConnection.getRequestProperty("X-US-Ref-Id");
    String str2 = paramHttpURLConnection.getHeaderField("X-US-DIGEST");
    Object localObject1 = str1;
    if (!TextUtils.isEmpty((CharSequence)localObject2))
    {
      localObject1 = str1;
      if (!TextUtils.isEmpty(str2))
      {
        paramHttpURLConnection = paramHttpURLConnection.getInputStream();
        localObject1 = str1;
        if (paramHttpURLConnection != null)
        {
          paramHttpURLConnection = IOUtils.toString(paramHttpURLConnection);
          localObject1 = str1;
          if (!TextUtils.isEmpty(paramHttpURLConnection)) {
            localObject1 = (paramHttpURLConnection + ":" + (String)localObject2).getBytes();
          }
        }
      }
    }
    try
    {
      localObject2 = Base64.decode(str2, 8);
      boolean bool = this.mSignatureVerifier.verify((byte[])localObject1, (byte[])localObject2);
      localObject1 = str1;
      if (bool) {
        localObject1 = paramHttpURLConnection;
      }
      return (String)localObject1;
    }
    catch (IllegalArgumentException paramHttpURLConnection)
    {
      this.mLogger.e("Upsight", paramHttpURLConnection, "Message signature is not valid Base64. X-US-DIGEST: " + str2, new Object[0]);
    }
    return "";
  }
  
  /* Error */
  public Response send(UpsightRequest paramUpsightRequest)
    throws IOException
  {
    // Byte code:
    //   0: invokestatic 187	java/util/UUID:randomUUID	()Ljava/util/UUID;
    //   3: invokevirtual 188	java/util/UUID:toString	()Ljava/lang/String;
    //   6: astore 5
    //   8: aconst_null
    //   9: astore 4
    //   11: aload 4
    //   13: astore_3
    //   14: aload_0
    //   15: getfield 106	com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint:mGson	Lcom/google/gson/Gson;
    //   18: aload_1
    //   19: invokevirtual 194	com/google/gson/Gson:toJson	(Ljava/lang/Object;)Ljava/lang/String;
    //   22: astore 6
    //   24: aload 4
    //   26: astore_3
    //   27: aload_0
    //   28: getfield 110	com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint:mRequestLoggingGson	Lcom/google/gson/Gson;
    //   31: aload_1
    //   32: invokevirtual 194	com/google/gson/Gson:toJson	(Ljava/lang/Object;)Ljava/lang/String;
    //   35: astore_1
    //   36: aload 4
    //   38: astore_3
    //   39: new 74	java/lang/StringBuilder
    //   42: dup
    //   43: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   46: ldc 32
    //   48: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: aload 5
    //   53: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   56: ldc 50
    //   58: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: aload_0
    //   62: getfield 102	com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint:mEndpointAddress	Ljava/lang/String;
    //   65: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   68: ldc 38
    //   70: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   73: aload_1
    //   74: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: astore_1
    //   78: aload 4
    //   80: astore_3
    //   81: aload_0
    //   82: getfield 112	com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint:mLogger	Lcom/upsight/android/logger/UpsightLogger;
    //   85: ldc -85
    //   87: aload_1
    //   88: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   91: iconst_0
    //   92: anewarray 4	java/lang/Object
    //   95: invokeinterface 198 4 0
    //   100: aload 4
    //   102: astore_3
    //   103: aload_0
    //   104: aload 6
    //   106: iconst_0
    //   107: invokespecial 200	com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint:getRequestBodyBytes	(Ljava/lang/String;Z)[B
    //   110: astore_1
    //   111: aload 4
    //   113: astore_3
    //   114: new 202	java/net/URL
    //   117: dup
    //   118: aload_0
    //   119: getfield 102	com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint:mEndpointAddress	Ljava/lang/String;
    //   122: invokespecial 205	java/net/URL:<init>	(Ljava/lang/String;)V
    //   125: invokevirtual 209	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   128: checkcast 135	java/net/HttpURLConnection
    //   131: astore 4
    //   133: aload 4
    //   135: astore_3
    //   136: aload 4
    //   138: ldc 53
    //   140: invokevirtual 212	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   143: aload 4
    //   145: astore_3
    //   146: aload 4
    //   148: ldc 26
    //   150: aload 5
    //   152: invokevirtual 216	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   155: aload 4
    //   157: astore_3
    //   158: aload 4
    //   160: ldc -38
    //   162: ldc 20
    //   164: invokevirtual 216	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   167: aload 4
    //   169: astore_3
    //   170: aload 4
    //   172: ldc -36
    //   174: getstatic 97	com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint:USER_AGENT_ANDROID	Ljava/lang/String;
    //   177: invokevirtual 216	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   180: aload 4
    //   182: astore_3
    //   183: aload 4
    //   185: ldc -34
    //   187: ldc 11
    //   189: invokevirtual 216	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   192: aload 4
    //   194: astore_3
    //   195: aload 4
    //   197: sipush 30000
    //   200: invokevirtual 226	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   203: aload 4
    //   205: astore_3
    //   206: aload 4
    //   208: sipush 30000
    //   211: invokevirtual 229	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   214: aload 4
    //   216: astore_3
    //   217: aload 4
    //   219: iconst_1
    //   220: invokevirtual 233	java/net/HttpURLConnection:setDoInput	(Z)V
    //   223: aload 4
    //   225: astore_3
    //   226: aload 4
    //   228: iconst_1
    //   229: invokevirtual 236	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   232: aload 4
    //   234: astore_3
    //   235: aload 4
    //   237: aload_1
    //   238: arraylength
    //   239: invokevirtual 239	java/net/HttpURLConnection:setFixedLengthStreamingMode	(I)V
    //   242: aload 4
    //   244: astore_3
    //   245: aload_1
    //   246: aload 4
    //   248: invokevirtual 243	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   251: invokestatic 247	org/apache/commons/io/IOUtils:write	([BLjava/io/OutputStream;)V
    //   254: aconst_null
    //   255: astore_1
    //   256: aload 4
    //   258: astore_3
    //   259: aload 4
    //   261: invokevirtual 251	java/net/HttpURLConnection:getResponseCode	()I
    //   264: istore_2
    //   265: aload 4
    //   267: astore_3
    //   268: new 74	java/lang/StringBuilder
    //   271: dup
    //   272: invokespecial 77	java/lang/StringBuilder:<init>	()V
    //   275: ldc 35
    //   277: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   280: aload 5
    //   282: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   285: ldc 47
    //   287: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   290: iload_2
    //   291: invokevirtual 91	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   294: astore 6
    //   296: iload_2
    //   297: sipush 200
    //   300: if_icmpne +46 -> 346
    //   303: aload 4
    //   305: astore_3
    //   306: aload_0
    //   307: aload 4
    //   309: invokespecial 253	com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint:getVerifiedResponse	(Ljava/net/HttpURLConnection;)Ljava/lang/String;
    //   312: astore 5
    //   314: aload 4
    //   316: astore_3
    //   317: aload 5
    //   319: invokestatic 148	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   322: ifeq +72 -> 394
    //   325: ldc 44
    //   327: astore_1
    //   328: aload 4
    //   330: astore_3
    //   331: aload 6
    //   333: ldc 41
    //   335: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   338: aload_1
    //   339: invokevirtual 83	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   342: pop
    //   343: aload 5
    //   345: astore_1
    //   346: aload 4
    //   348: astore_3
    //   349: aload_0
    //   350: getfield 112	com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint:mLogger	Lcom/upsight/android/logger/UpsightLogger;
    //   353: ldc -85
    //   355: aload 6
    //   357: invokevirtual 95	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   360: iconst_0
    //   361: anewarray 4	java/lang/Object
    //   364: invokeinterface 198 4 0
    //   369: aload 4
    //   371: astore_3
    //   372: new 6	com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint$Response
    //   375: dup
    //   376: iload_2
    //   377: aload_1
    //   378: invokespecial 256	com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint$Response:<init>	(ILjava/lang/String;)V
    //   381: astore_1
    //   382: aload 4
    //   384: ifnull +8 -> 392
    //   387: aload 4
    //   389: invokevirtual 259	java/net/HttpURLConnection:disconnect	()V
    //   392: aload_1
    //   393: areturn
    //   394: aload 4
    //   396: astore_3
    //   397: aload_0
    //   398: getfield 110	com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint:mRequestLoggingGson	Lcom/google/gson/Gson;
    //   401: aload_0
    //   402: getfield 108	com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint:mJsonParser	Lcom/google/gson/JsonParser;
    //   405: aload 5
    //   407: invokevirtual 265	com/google/gson/JsonParser:parse	(Ljava/lang/String;)Lcom/google/gson/JsonElement;
    //   410: invokevirtual 268	com/google/gson/Gson:toJson	(Lcom/google/gson/JsonElement;)Ljava/lang/String;
    //   413: astore_1
    //   414: goto -86 -> 328
    //   417: astore_1
    //   418: aload_3
    //   419: ifnull +7 -> 426
    //   422: aload_3
    //   423: invokevirtual 259	java/net/HttpURLConnection:disconnect	()V
    //   426: aload_1
    //   427: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	428	0	this	UpsightEndpoint
    //   0	428	1	paramUpsightRequest	UpsightRequest
    //   264	113	2	i	int
    //   13	410	3	localHttpURLConnection1	HttpURLConnection
    //   9	386	4	localHttpURLConnection2	HttpURLConnection
    //   6	400	5	str	String
    //   22	334	6	localObject	Object
    // Exception table:
    //   from	to	target	type
    //   14	24	417	finally
    //   27	36	417	finally
    //   39	78	417	finally
    //   81	100	417	finally
    //   103	111	417	finally
    //   114	133	417	finally
    //   136	143	417	finally
    //   146	155	417	finally
    //   158	167	417	finally
    //   170	180	417	finally
    //   183	192	417	finally
    //   195	203	417	finally
    //   206	214	417	finally
    //   217	223	417	finally
    //   226	232	417	finally
    //   235	242	417	finally
    //   245	254	417	finally
    //   259	265	417	finally
    //   268	296	417	finally
    //   306	314	417	finally
    //   317	325	417	finally
    //   331	343	417	finally
    //   349	369	417	finally
    //   372	382	417	finally
    //   397	414	417	finally
  }
  
  public static class Response
  {
    public final String body;
    public final int statusCode;
    
    public Response(int paramInt, String paramString)
    {
      this.statusCode = paramInt;
      this.body = paramString;
    }
    
    public boolean isOk()
    {
      return this.statusCode == 200;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/delivery/UpsightEndpoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */