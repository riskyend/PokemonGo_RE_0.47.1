package com.upsight.mediation.vast.processor;

import com.upsight.mediation.log.FuseLog;
import com.upsight.mediation.vast.VASTPlayer;
import com.upsight.mediation.vast.model.VASTModel;
import com.upsight.mediation.vast.model.VAST_DOC_ELEMENTS;
import com.upsight.mediation.vast.util.XmlTools;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class VASTProcessor
{
  private static final boolean IS_VALIDATION_ON = false;
  private static final int MAX_VAST_LEVELS = 5;
  private static final String TAG = "VASTProcessor";
  private final VASTPlayer mVastPlayer;
  private VASTMediaPicker mediaPicker;
  private StringBuilder mergedVastDocs = new StringBuilder(500);
  private VASTModel vastModel;
  
  public VASTProcessor(VASTMediaPicker paramVASTMediaPicker, VASTPlayer paramVASTPlayer)
  {
    this.mediaPicker = paramVASTMediaPicker;
    this.mVastPlayer = paramVASTPlayer;
  }
  
  private Document createDoc(InputStream paramInputStream)
  {
    try
    {
      paramInputStream = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(paramInputStream);
      paramInputStream.getDocumentElement().normalize();
      return paramInputStream;
    }
    catch (Exception paramInputStream) {}
    return null;
  }
  
  private void merge(Document paramDocument)
  {
    paramDocument = XmlTools.xmlDocumentToString(paramDocument.getElementsByTagName("VAST").item(0));
    this.mergedVastDocs.append(paramDocument);
  }
  
  private int processUri(InputStream paramInputStream, int paramInt)
  {
    if (paramInt >= 5) {
      return 302;
    }
    Document localDocument = createDoc(paramInputStream);
    if (localDocument == null) {
      return 100;
    }
    if (localDocument.getFirstChild().getNodeName().equals("VAST")) {}
    for (paramInputStream = localDocument.getFirstChild().getAttributes().getNamedItem("version").getNodeValue().toString(); (!paramInputStream.equals("2.0")) && (!paramInputStream.equals("3.0")); paramInputStream = localDocument.getChildNodes().item(1).getAttributes().getNamedItem("version").getNodeValue().toString()) {
      return 102;
    }
    paramInputStream = localDocument.getElementsByTagName(VAST_DOC_ELEMENTS.vastAdTagURI.getValue());
    if ((paramInputStream == null) || (paramInputStream.getLength() == 0))
    {
      merge(localDocument);
      return 0;
    }
    FuseLog.v("VASTProcessor", "Doc is a wrapper. ");
    paramInputStream = XmlTools.getElementValue(paramInputStream.item(0));
    FuseLog.v("VASTProcessor", "Wrapper URL: " + paramInputStream);
    try
    {
      paramInputStream = (HttpURLConnection)new URL(paramInputStream).openConnection();
      paramInputStream.setConnectTimeout(5000);
      paramInputStream.setReadTimeout(5000);
      if (paramInputStream.getResponseCode() != 200) {
        return 303;
      }
      paramInputStream = paramInputStream.getInputStream();
      paramInt = processUri(paramInputStream, paramInt + 1);
      try
      {
        paramInputStream.close();
        return paramInt;
      }
      catch (IOException paramInputStream)
      {
        return paramInt;
      }
      return 300;
    }
    catch (MalformedURLException paramInputStream)
    {
      return 303;
    }
    catch (SocketTimeoutException paramInputStream)
    {
      return 301;
    }
    catch (IOException paramInputStream)
    {
      FuseLog.w("VASTProcessor", paramInputStream.getMessage(), paramInputStream);
    }
  }
  
  private Document wrapMergedVastDocWithVasts()
  {
    return XmlTools.stringToDocument(this.mergedVastDocs.toString());
  }
  
  public VASTModel getModel()
  {
    return this.vastModel;
  }
  
  /* Error */
  public int process(android.content.Context paramContext, String paramString, boolean paramBoolean, int paramInt)
  {
    // Byte code:
    //   0: ldc 14
    //   2: ldc -28
    //   4: invokestatic 231	com/upsight/mediation/log/FuseLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   7: aload_0
    //   8: aconst_null
    //   9: putfield 223	com/upsight/mediation/vast/processor/VASTProcessor:vastModel	Lcom/upsight/mediation/vast/model/VASTModel;
    //   12: new 233	java/io/ByteArrayInputStream
    //   15: dup
    //   16: aload_2
    //   17: invokestatic 239	java/nio/charset/Charset:defaultCharset	()Ljava/nio/charset/Charset;
    //   20: invokevirtual 242	java/nio/charset/Charset:name	()Ljava/lang/String;
    //   23: invokevirtual 246	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   26: invokespecial 249	java/io/ByteArrayInputStream:<init>	([B)V
    //   29: astore_2
    //   30: aload_0
    //   31: aload_2
    //   32: iconst_0
    //   33: invokespecial 201	com/upsight/mediation/vast/processor/VASTProcessor:processUri	(Ljava/io/InputStream;I)I
    //   36: istore 5
    //   38: aload_2
    //   39: invokevirtual 206	java/io/InputStream:close	()V
    //   42: iload 5
    //   44: ifeq +20 -> 64
    //   47: iload 5
    //   49: ireturn
    //   50: astore_1
    //   51: ldc 14
    //   53: aload_1
    //   54: invokevirtual 250	java/io/UnsupportedEncodingException:getMessage	()Ljava/lang/String;
    //   57: aload_1
    //   58: invokestatic 213	com/upsight/mediation/log/FuseLog:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)V
    //   61: bipush 100
    //   63: ireturn
    //   64: aload_0
    //   65: invokespecial 252	com/upsight/mediation/vast/processor/VASTProcessor:wrapMergedVastDocWithVasts	()Lorg/w3c/dom/Document;
    //   68: astore_2
    //   69: aload_0
    //   70: new 254	com/upsight/mediation/vast/model/VASTModel
    //   73: dup
    //   74: aload_2
    //   75: invokespecial 256	com/upsight/mediation/vast/model/VASTModel:<init>	(Lorg/w3c/dom/Document;)V
    //   78: putfield 223	com/upsight/mediation/vast/processor/VASTProcessor:vastModel	Lcom/upsight/mediation/vast/model/VASTModel;
    //   81: aload_2
    //   82: ifnonnull +6 -> 88
    //   85: bipush 100
    //   87: ireturn
    //   88: iload_3
    //   89: ifeq +16 -> 105
    //   92: aload_0
    //   93: getfield 223	com/upsight/mediation/vast/processor/VASTProcessor:vastModel	Lcom/upsight/mediation/vast/model/VASTModel;
    //   96: invokestatic 262	com/upsight/mediation/vast/processor/VASTModelPostValidator:validate	(Lcom/upsight/mediation/vast/model/VASTModel;)Z
    //   99: ifne +6 -> 105
    //   102: bipush 101
    //   104: ireturn
    //   105: aload_0
    //   106: getfield 223	com/upsight/mediation/vast/processor/VASTProcessor:vastModel	Lcom/upsight/mediation/vast/model/VASTModel;
    //   109: aload_0
    //   110: getfield 36	com/upsight/mediation/vast/processor/VASTProcessor:mediaPicker	Lcom/upsight/mediation/vast/processor/VASTMediaPicker;
    //   113: invokestatic 266	com/upsight/mediation/vast/processor/VASTModelPostValidator:pickMediaFile	(Lcom/upsight/mediation/vast/model/VASTModel;Lcom/upsight/mediation/vast/processor/VASTMediaPicker;)Z
    //   116: ifne +7 -> 123
    //   119: sipush 403
    //   122: ireturn
    //   123: aload_0
    //   124: getfield 223	com/upsight/mediation/vast/processor/VASTProcessor:vastModel	Lcom/upsight/mediation/vast/model/VASTModel;
    //   127: invokevirtual 269	com/upsight/mediation/vast/model/VASTModel:getPickedMediaFileDeliveryType	()Ljava/lang/String;
    //   130: ldc_w 271
    //   133: invokevirtual 119	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   136: ifeq +18 -> 154
    //   139: aload_0
    //   140: getfield 223	com/upsight/mediation/vast/processor/VASTProcessor:vastModel	Lcom/upsight/mediation/vast/model/VASTModel;
    //   143: aload_1
    //   144: aload_0
    //   145: getfield 38	com/upsight/mediation/vast/processor/VASTProcessor:mVastPlayer	Lcom/upsight/mediation/vast/VASTPlayer;
    //   148: iload 4
    //   150: invokevirtual 275	com/upsight/mediation/vast/model/VASTModel:cache	(Landroid/content/Context;Lcom/upsight/mediation/vast/VASTPlayer;I)I
    //   153: ireturn
    //   154: aload_0
    //   155: getfield 223	com/upsight/mediation/vast/processor/VASTProcessor:vastModel	Lcom/upsight/mediation/vast/model/VASTModel;
    //   158: invokevirtual 269	com/upsight/mediation/vast/model/VASTModel:getPickedMediaFileDeliveryType	()Ljava/lang/String;
    //   161: ldc_w 277
    //   164: invokevirtual 119	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   167: ifeq +11 -> 178
    //   170: aload_0
    //   171: getfield 38	com/upsight/mediation/vast/processor/VASTProcessor:mVastPlayer	Lcom/upsight/mediation/vast/VASTPlayer;
    //   174: iconst_1
    //   175: invokevirtual 283	com/upsight/mediation/vast/VASTPlayer:setLoaded	(Z)V
    //   178: iconst_0
    //   179: ireturn
    //   180: astore_2
    //   181: goto -139 -> 42
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	184	0	this	VASTProcessor
    //   0	184	1	paramContext	android.content.Context
    //   0	184	2	paramString	String
    //   0	184	3	paramBoolean	boolean
    //   0	184	4	paramInt	int
    //   36	12	5	i	int
    // Exception table:
    //   from	to	target	type
    //   12	30	50	java/io/UnsupportedEncodingException
    //   38	42	180	java/io/IOException
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/processor/VASTProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */