package com.upsight.mediation.vast.model;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import com.upsight.mediation.log.FuseLog;
import com.upsight.mediation.vast.VASTPlayer;
import com.upsight.mediation.vast.VASTPlayer.VASTPlayerListener;
import com.upsight.mediation.vast.util.XmlTools;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class VASTModel
  implements Serializable
{
  public static final long DOWNLOAD_TIMEOUT_LIMIT = 30000L;
  private static String TAG = "VASTModel";
  private static final String adSystemXPATH = "/VAST/Ad/InLine/AdSystem";
  private static final String adTitleXPATH = "/VAST/Ad/InLine/AdTitle";
  private static final String combinedTrackingXPATH = "/VAST/Ad/InLine/Creatives/Creative/Linear/TrackingEvents/Tracking|/VAST/Ad/InLine/Creatives/Creative/NonLinearAds/TrackingEvents/Tracking|/VAST/Ad/Wrapper/Creatives/Creative/Linear/TrackingEvents/Tracking|/VAST/Ad/Wrapper/Creatives/Creative/NonLinearAds/TrackingEvents/Tracking";
  private static final String durationXPATH = "/VAST/Ad/InLine/Creatives/Creative/Linear/Duration";
  private static final String errorUrlXPATH = "//Error";
  private static final String impressionXPATH = "/VAST/Ad/InLine/Impression";
  private static final String inlineLinearTrackingXPATH = "/VAST/Ad/InLine/Creatives/Creative/Linear/TrackingEvents/Tracking";
  private static final String inlineNonLinearTrackingXPATH = "/VAST/Ad/InLine/Creatives/Creative/NonLinearAds/TrackingEvents/Tracking";
  private static final String mediaFileXPATH = "/VAST/Ad/InLine/Creatives/Creative/Linear/MediaFiles/MediaFile";
  private static final long serialVersionUID = 4318368258447283733L;
  private static final String skipOffsetXPATH = "/VAST/Ad/InLine/Creatives/Creative/Linear[@skipoffset]";
  private static final String vastXPATH = "//VAST";
  private static final String videoClicksXPATH = "//VideoClicks";
  private static final String wrapperLinearTrackingXPATH = "/VAST/Ad/Wrapper/Creatives/Creative/Linear/TrackingEvents/Tracking";
  private static final String wrapperNonLinearTrackingXPATH = "/VAST/Ad/Wrapper/Creatives/Creative/NonLinearAds/TrackingEvents/Tracking";
  private String mediaFileDeliveryType = null;
  private String mediaFileLocation = null;
  private transient Document vastsDocument;
  
  public VASTModel(Document paramDocument)
  {
    this.vastsDocument = paramDocument;
  }
  
  private List<String> getListFromXPath(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    Object localObject = XPathFactory.newInstance().newXPath();
    try
    {
      localObject = (NodeList)((XPath)localObject).evaluate(paramString, this.vastsDocument, XPathConstants.NODESET);
      paramString = localArrayList;
      if (localObject != null)
      {
        int i = 0;
        for (;;)
        {
          paramString = localArrayList;
          if (i >= ((NodeList)localObject).getLength()) {
            break;
          }
          paramString = XmlTools.getElementValue(((NodeList)localObject).item(i));
          if ((paramString != null) || (!paramString.equals(""))) {
            localArrayList.add(paramString);
          }
          i += 1;
        }
      }
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString = null;
    }
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws ClassNotFoundException, IOException
  {
    paramObjectInputStream.defaultReadObject();
    this.vastsDocument = XmlTools.stringToDocument((String)paramObjectInputStream.readObject());
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
    paramObjectOutputStream.writeObject(XmlTools.xmlDocumentToString(this.vastsDocument));
  }
  
  public int cache(final Context paramContext, final VASTPlayer paramVASTPlayer, final int paramInt)
  {
    ((Activity)paramContext).runOnUiThread(new Runnable()
    {
      public void run()
      {
        new VASTModel.DownloadTask(VASTModel.this, paramVASTPlayer, paramContext, paramInt).execute(new String[] { VASTModel.this.mediaFileLocation });
      }
    });
    return 0;
  }
  
  public boolean evaluateAdSystem()
  {
    Object localObject = XPathFactory.newInstance().newXPath();
    try
    {
      localObject = (Node)((XPath)localObject).evaluate("/VAST/Ad/InLine/AdSystem", this.vastsDocument, XPathConstants.NODE);
      return true;
    }
    catch (XPathExpressionException localXPathExpressionException) {}
    return false;
  }
  
  public boolean evaluateAdTitle()
  {
    Object localObject = XPathFactory.newInstance().newXPath();
    try
    {
      localObject = (Node)((XPath)localObject).evaluate("/VAST/Ad/InLine/AdTitle", this.vastsDocument, XPathConstants.NODE);
      return true;
    }
    catch (XPathExpressionException localXPathExpressionException) {}
    return false;
  }
  
  public String getDuration()
  {
    FuseLog.d(TAG, "getDuration");
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = XPathFactory.newInstance().newXPath();
    try
    {
      localObject3 = (NodeList)((XPath)localObject3).evaluate("/VAST/Ad/InLine/Creatives/Creative/Linear/Duration", this.vastsDocument, XPathConstants.NODESET);
      if (localObject3 != null)
      {
        int i = 0;
        localObject1 = localObject2;
        while (i < ((NodeList)localObject3).getLength())
        {
          localObject1 = XmlTools.getElementValue(((NodeList)localObject3).item(i));
          i += 1;
        }
      }
      return localException;
    }
    catch (Exception localException)
    {
      return null;
    }
  }
  
  public List<String> getErrorUrl()
  {
    return getListFromXPath("//Error");
  }
  
  public List<String> getImpressions()
  {
    return getListFromXPath("/VAST/Ad/InLine/Impression");
  }
  
  /* Error */
  public List<VASTMediaFile> getMediaFiles()
  {
    // Byte code:
    //   0: new 95	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 96	java/util/ArrayList:<init>	()V
    //   7: astore_3
    //   8: invokestatic 102	javax/xml/xpath/XPathFactory:newInstance	()Ljavax/xml/xpath/XPathFactory;
    //   11: invokevirtual 106	javax/xml/xpath/XPathFactory:newXPath	()Ljavax/xml/xpath/XPath;
    //   14: astore_2
    //   15: aload_2
    //   16: ldc 44
    //   18: aload_0
    //   19: getfield 83	com/upsight/mediation/vast/model/VASTModel:vastsDocument	Lorg/w3c/dom/Document;
    //   22: getstatic 112	javax/xml/xpath/XPathConstants:NODESET	Ljavax/xml/namespace/QName;
    //   25: invokeinterface 118 4 0
    //   30: checkcast 120	org/w3c/dom/NodeList
    //   33: astore 4
    //   35: aload_3
    //   36: astore_2
    //   37: aload 4
    //   39: ifnull +392 -> 431
    //   42: iconst_0
    //   43: istore_1
    //   44: aload_3
    //   45: astore_2
    //   46: iload_1
    //   47: aload 4
    //   49: invokeinterface 124 1 0
    //   54: if_icmpge +377 -> 431
    //   57: new 218	com/upsight/mediation/vast/model/VASTMediaFile
    //   60: dup
    //   61: invokespecial 219	com/upsight/mediation/vast/model/VASTMediaFile:<init>	()V
    //   64: astore 5
    //   66: aload 4
    //   68: iload_1
    //   69: invokeinterface 128 2 0
    //   74: astore 6
    //   76: aload 6
    //   78: invokeinterface 223 1 0
    //   83: astore 7
    //   85: aload 7
    //   87: ldc -31
    //   89: invokeinterface 231 2 0
    //   94: astore_2
    //   95: aload_2
    //   96: ifnonnull +215 -> 311
    //   99: aconst_null
    //   100: astore_2
    //   101: aload 5
    //   103: aload_2
    //   104: invokevirtual 235	com/upsight/mediation/vast/model/VASTMediaFile:setApiFramework	(Ljava/lang/String;)V
    //   107: aload 7
    //   109: ldc -19
    //   111: invokeinterface 231 2 0
    //   116: astore_2
    //   117: aload_2
    //   118: ifnonnull +203 -> 321
    //   121: aconst_null
    //   122: astore_2
    //   123: aload 5
    //   125: aload_2
    //   126: invokevirtual 241	com/upsight/mediation/vast/model/VASTMediaFile:setBitrate	(Ljava/math/BigInteger;)V
    //   129: aload 7
    //   131: ldc -13
    //   133: invokeinterface 231 2 0
    //   138: astore_2
    //   139: aload_2
    //   140: ifnonnull +198 -> 338
    //   143: aconst_null
    //   144: astore_2
    //   145: aload 5
    //   147: aload_2
    //   148: invokevirtual 246	com/upsight/mediation/vast/model/VASTMediaFile:setDelivery	(Ljava/lang/String;)V
    //   151: aload 7
    //   153: ldc -8
    //   155: invokeinterface 231 2 0
    //   160: astore_2
    //   161: aload_2
    //   162: ifnonnull +186 -> 348
    //   165: aconst_null
    //   166: astore_2
    //   167: aload 5
    //   169: aload_2
    //   170: invokevirtual 251	com/upsight/mediation/vast/model/VASTMediaFile:setHeight	(Ljava/math/BigInteger;)V
    //   173: aload 7
    //   175: ldc -3
    //   177: invokeinterface 231 2 0
    //   182: astore_2
    //   183: aload_2
    //   184: ifnonnull +181 -> 365
    //   187: aconst_null
    //   188: astore_2
    //   189: aload 5
    //   191: aload_2
    //   192: invokevirtual 256	com/upsight/mediation/vast/model/VASTMediaFile:setId	(Ljava/lang/String;)V
    //   195: aload 7
    //   197: ldc_w 258
    //   200: invokeinterface 231 2 0
    //   205: astore_2
    //   206: aload_2
    //   207: ifnonnull +168 -> 375
    //   210: aconst_null
    //   211: astore_2
    //   212: aload 5
    //   214: aload_2
    //   215: invokevirtual 262	com/upsight/mediation/vast/model/VASTMediaFile:setMaintainAspectRatio	(Ljava/lang/Boolean;)V
    //   218: aload 7
    //   220: ldc_w 264
    //   223: invokeinterface 231 2 0
    //   228: astore_2
    //   229: aload_2
    //   230: ifnonnull +158 -> 388
    //   233: aconst_null
    //   234: astore_2
    //   235: aload 5
    //   237: aload_2
    //   238: invokevirtual 267	com/upsight/mediation/vast/model/VASTMediaFile:setScalable	(Ljava/lang/Boolean;)V
    //   241: aload 7
    //   243: ldc_w 269
    //   246: invokeinterface 231 2 0
    //   251: astore_2
    //   252: aload_2
    //   253: ifnonnull +148 -> 401
    //   256: aconst_null
    //   257: astore_2
    //   258: aload 5
    //   260: aload_2
    //   261: invokevirtual 272	com/upsight/mediation/vast/model/VASTMediaFile:setType	(Ljava/lang/String;)V
    //   264: aload 7
    //   266: ldc_w 274
    //   269: invokeinterface 231 2 0
    //   274: astore_2
    //   275: aload_2
    //   276: ifnonnull +135 -> 411
    //   279: aconst_null
    //   280: astore_2
    //   281: aload 5
    //   283: aload_2
    //   284: invokevirtual 277	com/upsight/mediation/vast/model/VASTMediaFile:setWidth	(Ljava/math/BigInteger;)V
    //   287: aload 5
    //   289: aload 6
    //   291: invokestatic 134	com/upsight/mediation/vast/util/XmlTools:getElementValue	(Lorg/w3c/dom/Node;)Ljava/lang/String;
    //   294: invokevirtual 280	com/upsight/mediation/vast/model/VASTMediaFile:setValue	(Ljava/lang/String;)V
    //   297: aload_3
    //   298: aload 5
    //   300: invokevirtual 145	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   303: pop
    //   304: iload_1
    //   305: iconst_1
    //   306: iadd
    //   307: istore_1
    //   308: goto -264 -> 44
    //   311: aload_2
    //   312: invokeinterface 283 1 0
    //   317: astore_2
    //   318: goto -217 -> 101
    //   321: new 285	java/math/BigInteger
    //   324: dup
    //   325: aload_2
    //   326: invokeinterface 283 1 0
    //   331: invokespecial 287	java/math/BigInteger:<init>	(Ljava/lang/String;)V
    //   334: astore_2
    //   335: goto -212 -> 123
    //   338: aload_2
    //   339: invokeinterface 283 1 0
    //   344: astore_2
    //   345: goto -200 -> 145
    //   348: new 285	java/math/BigInteger
    //   351: dup
    //   352: aload_2
    //   353: invokeinterface 283 1 0
    //   358: invokespecial 287	java/math/BigInteger:<init>	(Ljava/lang/String;)V
    //   361: astore_2
    //   362: goto -195 -> 167
    //   365: aload_2
    //   366: invokeinterface 283 1 0
    //   371: astore_2
    //   372: goto -183 -> 189
    //   375: aload_2
    //   376: invokeinterface 283 1 0
    //   381: invokestatic 293	java/lang/Boolean:valueOf	(Ljava/lang/String;)Ljava/lang/Boolean;
    //   384: astore_2
    //   385: goto -173 -> 212
    //   388: aload_2
    //   389: invokeinterface 283 1 0
    //   394: invokestatic 293	java/lang/Boolean:valueOf	(Ljava/lang/String;)Ljava/lang/Boolean;
    //   397: astore_2
    //   398: goto -163 -> 235
    //   401: aload_2
    //   402: invokeinterface 283 1 0
    //   407: astore_2
    //   408: goto -150 -> 258
    //   411: new 285	java/math/BigInteger
    //   414: dup
    //   415: aload_2
    //   416: invokeinterface 283 1 0
    //   421: invokespecial 287	java/math/BigInteger:<init>	(Ljava/lang/String;)V
    //   424: astore_2
    //   425: goto -144 -> 281
    //   428: astore_2
    //   429: aconst_null
    //   430: astore_2
    //   431: aload_2
    //   432: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	433	0	this	VASTModel
    //   43	265	1	i	int
    //   14	411	2	localObject	Object
    //   428	1	2	localException	Exception
    //   430	2	2	localList	List<VASTMediaFile>
    //   7	291	3	localArrayList	ArrayList
    //   33	34	4	localNodeList	NodeList
    //   64	235	5	localVASTMediaFile	VASTMediaFile
    //   74	216	6	localNode	Node
    //   83	182	7	localNamedNodeMap	NamedNodeMap
    // Exception table:
    //   from	to	target	type
    //   15	35	428	java/lang/Exception
    //   46	95	428	java/lang/Exception
    //   101	117	428	java/lang/Exception
    //   123	139	428	java/lang/Exception
    //   145	161	428	java/lang/Exception
    //   167	183	428	java/lang/Exception
    //   189	206	428	java/lang/Exception
    //   212	229	428	java/lang/Exception
    //   235	252	428	java/lang/Exception
    //   258	275	428	java/lang/Exception
    //   281	304	428	java/lang/Exception
    //   311	318	428	java/lang/Exception
    //   321	335	428	java/lang/Exception
    //   338	345	428	java/lang/Exception
    //   348	362	428	java/lang/Exception
    //   365	372	428	java/lang/Exception
    //   375	385	428	java/lang/Exception
    //   388	398	428	java/lang/Exception
    //   401	408	428	java/lang/Exception
    //   411	425	428	java/lang/Exception
  }
  
  public String getPickedMediaFileDeliveryType()
  {
    return this.mediaFileDeliveryType;
  }
  
  public String getPickedMediaFileLocation()
  {
    return this.mediaFileLocation;
  }
  
  public String getSkipOffset()
  {
    Object localObject = XPathFactory.newInstance().newXPath();
    try
    {
      localObject = ((Node)((XPath)localObject).evaluate("/VAST/Ad/InLine/Creatives/Creative/Linear[@skipoffset]", this.vastsDocument, XPathConstants.NODE)).getAttributes().getNamedItem("skipoffset").getNodeValue().toString();
      return (String)localObject;
    }
    catch (Exception localException) {}
    return null;
  }
  
  /* Error */
  public java.util.HashMap<TRACKING_EVENTS_TYPE, List<VASTTracking>> getTrackingEvents()
  {
    // Byte code:
    //   0: new 310	java/util/HashMap
    //   3: dup
    //   4: invokespecial 311	java/util/HashMap:<init>	()V
    //   7: astore 5
    //   9: invokestatic 102	javax/xml/xpath/XPathFactory:newInstance	()Ljavax/xml/xpath/XPathFactory;
    //   12: invokevirtual 106	javax/xml/xpath/XPathFactory:newXPath	()Ljavax/xml/xpath/XPath;
    //   15: astore_3
    //   16: aload_3
    //   17: ldc 26
    //   19: aload_0
    //   20: getfield 83	com/upsight/mediation/vast/model/VASTModel:vastsDocument	Lorg/w3c/dom/Document;
    //   23: getstatic 112	javax/xml/xpath/XPathConstants:NODESET	Ljavax/xml/namespace/QName;
    //   26: invokeinterface 118 4 0
    //   31: checkcast 120	org/w3c/dom/NodeList
    //   34: astore 6
    //   36: ldc -120
    //   38: astore_3
    //   39: aload 5
    //   41: astore 4
    //   43: aload 6
    //   45: ifnull +210 -> 255
    //   48: iconst_0
    //   49: istore_1
    //   50: aload 5
    //   52: astore 4
    //   54: iload_1
    //   55: aload 6
    //   57: invokeinterface 124 1 0
    //   62: if_icmpge +193 -> 255
    //   65: aload 6
    //   67: iload_1
    //   68: invokeinterface 128 2 0
    //   73: astore 4
    //   75: aload 4
    //   77: invokeinterface 223 1 0
    //   82: astore 8
    //   84: aload 8
    //   86: ldc_w 313
    //   89: invokeinterface 231 2 0
    //   94: invokeinterface 283 1 0
    //   99: astore 7
    //   101: aload 7
    //   103: ldc_w 315
    //   106: invokevirtual 142	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   109: istore_2
    //   110: iload_2
    //   111: ifeq +19 -> 130
    //   114: aload 8
    //   116: ldc_w 317
    //   119: invokeinterface 231 2 0
    //   124: invokeinterface 283 1 0
    //   129: astore_3
    //   130: aload 7
    //   132: invokestatic 322	com/upsight/mediation/vast/model/TRACKING_EVENTS_TYPE:valueOf	(Ljava/lang/String;)Lcom/upsight/mediation/vast/model/TRACKING_EVENTS_TYPE;
    //   135: astore 7
    //   137: aload 4
    //   139: invokestatic 134	com/upsight/mediation/vast/util/XmlTools:getElementValue	(Lorg/w3c/dom/Node;)Ljava/lang/String;
    //   142: astore 8
    //   144: new 324	com/upsight/mediation/vast/model/VASTTracking
    //   147: dup
    //   148: invokespecial 325	com/upsight/mediation/vast/model/VASTTracking:<init>	()V
    //   151: astore 4
    //   153: aload 4
    //   155: aload 7
    //   157: invokevirtual 329	com/upsight/mediation/vast/model/VASTTracking:setEvent	(Lcom/upsight/mediation/vast/model/TRACKING_EVENTS_TYPE;)V
    //   160: aload 4
    //   162: aload 8
    //   164: invokevirtual 330	com/upsight/mediation/vast/model/VASTTracking:setValue	(Ljava/lang/String;)V
    //   167: aload 7
    //   169: getstatic 333	com/upsight/mediation/vast/model/TRACKING_EVENTS_TYPE:progress	Lcom/upsight/mediation/vast/model/TRACKING_EVENTS_TYPE;
    //   172: invokevirtual 334	com/upsight/mediation/vast/model/TRACKING_EVENTS_TYPE:equals	(Ljava/lang/Object;)Z
    //   175: ifeq +13 -> 188
    //   178: aload_3
    //   179: ifnull +9 -> 188
    //   182: aload 4
    //   184: aload_3
    //   185: invokevirtual 337	com/upsight/mediation/vast/model/VASTTracking:setOffset	(Ljava/lang/String;)V
    //   188: aload 5
    //   190: aload 7
    //   192: invokevirtual 340	java/util/HashMap:containsKey	(Ljava/lang/Object;)Z
    //   195: ifeq +24 -> 219
    //   198: aload 5
    //   200: aload 7
    //   202: invokevirtual 344	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
    //   205: checkcast 346	java/util/List
    //   208: aload 4
    //   210: invokeinterface 347 2 0
    //   215: pop
    //   216: goto +42 -> 258
    //   219: new 95	java/util/ArrayList
    //   222: dup
    //   223: invokespecial 96	java/util/ArrayList:<init>	()V
    //   226: astore 8
    //   228: aload 8
    //   230: aload 4
    //   232: invokeinterface 347 2 0
    //   237: pop
    //   238: aload 5
    //   240: aload 7
    //   242: aload 8
    //   244: invokevirtual 351	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   247: pop
    //   248: goto +10 -> 258
    //   251: astore_3
    //   252: aconst_null
    //   253: astore 4
    //   255: aload 4
    //   257: areturn
    //   258: iload_1
    //   259: iconst_1
    //   260: iadd
    //   261: istore_1
    //   262: goto -212 -> 50
    //   265: astore_3
    //   266: aconst_null
    //   267: astore_3
    //   268: goto -138 -> 130
    //   271: astore 4
    //   273: goto -15 -> 258
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	276	0	this	VASTModel
    //   49	213	1	i	int
    //   109	2	2	bool	boolean
    //   15	170	3	localObject1	Object
    //   251	1	3	localException	Exception
    //   265	1	3	localNullPointerException	NullPointerException
    //   267	1	3	localObject2	Object
    //   41	215	4	localObject3	Object
    //   271	1	4	localIllegalArgumentException	IllegalArgumentException
    //   7	232	5	localHashMap	java.util.HashMap
    //   34	32	6	localNodeList	NodeList
    //   99	142	7	localObject4	Object
    //   82	161	8	localObject5	Object
    // Exception table:
    //   from	to	target	type
    //   16	36	251	java/lang/Exception
    //   54	110	251	java/lang/Exception
    //   114	130	251	java/lang/Exception
    //   130	137	251	java/lang/Exception
    //   137	178	251	java/lang/Exception
    //   182	188	251	java/lang/Exception
    //   188	216	251	java/lang/Exception
    //   219	248	251	java/lang/Exception
    //   114	130	265	java/lang/NullPointerException
    //   130	137	271	java/lang/IllegalArgumentException
  }
  
  public String getVastVersion()
  {
    Object localObject = XPathFactory.newInstance().newXPath();
    try
    {
      localObject = ((Node)((XPath)localObject).evaluate("//VAST", this.vastsDocument, XPathConstants.NODE)).getAttributes().getNamedItem("version").getNodeValue().toString();
      return (String)localObject;
    }
    catch (Exception localException) {}
    return null;
  }
  
  /* Error */
  public VideoClicks getVideoClicks()
  {
    // Byte code:
    //   0: new 359	com/upsight/mediation/vast/model/VideoClicks
    //   3: dup
    //   4: invokespecial 360	com/upsight/mediation/vast/model/VideoClicks:<init>	()V
    //   7: astore 4
    //   9: invokestatic 102	javax/xml/xpath/XPathFactory:newInstance	()Ljavax/xml/xpath/XPathFactory;
    //   12: invokevirtual 106	javax/xml/xpath/XPathFactory:newXPath	()Ljavax/xml/xpath/XPath;
    //   15: astore_3
    //   16: aload_3
    //   17: ldc 56
    //   19: aload_0
    //   20: getfield 83	com/upsight/mediation/vast/model/VASTModel:vastsDocument	Lorg/w3c/dom/Document;
    //   23: getstatic 112	javax/xml/xpath/XPathConstants:NODESET	Ljavax/xml/namespace/QName;
    //   26: invokeinterface 118 4 0
    //   31: checkcast 120	org/w3c/dom/NodeList
    //   34: astore 5
    //   36: aload 4
    //   38: astore_3
    //   39: aload 5
    //   41: ifnull +172 -> 213
    //   44: iconst_0
    //   45: istore_1
    //   46: aload 4
    //   48: astore_3
    //   49: iload_1
    //   50: aload 5
    //   52: invokeinterface 124 1 0
    //   57: if_icmpge +156 -> 213
    //   60: aload 5
    //   62: iload_1
    //   63: invokeinterface 128 2 0
    //   68: invokeinterface 364 1 0
    //   73: astore_3
    //   74: iconst_0
    //   75: istore_2
    //   76: iload_2
    //   77: aload_3
    //   78: invokeinterface 124 1 0
    //   83: if_icmpge +113 -> 196
    //   86: aload_3
    //   87: iload_2
    //   88: invokeinterface 128 2 0
    //   93: astore 6
    //   95: aload 6
    //   97: invokeinterface 367 1 0
    //   102: astore 7
    //   104: aload 7
    //   106: ldc_w 369
    //   109: invokevirtual 373	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   112: ifeq +26 -> 138
    //   115: aload 6
    //   117: invokestatic 134	com/upsight/mediation/vast/util/XmlTools:getElementValue	(Lorg/w3c/dom/Node;)Ljava/lang/String;
    //   120: astore 6
    //   122: aload 4
    //   124: invokevirtual 376	com/upsight/mediation/vast/model/VideoClicks:getClickTracking	()Ljava/util/List;
    //   127: aload 6
    //   129: invokeinterface 347 2 0
    //   134: pop
    //   135: goto +68 -> 203
    //   138: aload 7
    //   140: ldc_w 378
    //   143: invokevirtual 373	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   146: ifeq +16 -> 162
    //   149: aload 4
    //   151: aload 6
    //   153: invokestatic 134	com/upsight/mediation/vast/util/XmlTools:getElementValue	(Lorg/w3c/dom/Node;)Ljava/lang/String;
    //   156: invokevirtual 381	com/upsight/mediation/vast/model/VideoClicks:setClickThrough	(Ljava/lang/String;)V
    //   159: goto +44 -> 203
    //   162: aload 7
    //   164: ldc_w 383
    //   167: invokevirtual 373	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   170: ifeq +33 -> 203
    //   173: aload 6
    //   175: invokestatic 134	com/upsight/mediation/vast/util/XmlTools:getElementValue	(Lorg/w3c/dom/Node;)Ljava/lang/String;
    //   178: astore 6
    //   180: aload 4
    //   182: invokevirtual 386	com/upsight/mediation/vast/model/VideoClicks:getCustomClick	()Ljava/util/List;
    //   185: aload 6
    //   187: invokeinterface 347 2 0
    //   192: pop
    //   193: goto +10 -> 203
    //   196: iload_1
    //   197: iconst_1
    //   198: iadd
    //   199: istore_1
    //   200: goto -154 -> 46
    //   203: iload_2
    //   204: iconst_1
    //   205: iadd
    //   206: istore_2
    //   207: goto -131 -> 76
    //   210: astore_3
    //   211: aconst_null
    //   212: astore_3
    //   213: aload_3
    //   214: areturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	215	0	this	VASTModel
    //   45	155	1	i	int
    //   75	132	2	j	int
    //   15	72	3	localObject1	Object
    //   210	1	3	localException	Exception
    //   212	2	3	localVideoClicks1	VideoClicks
    //   7	174	4	localVideoClicks2	VideoClicks
    //   34	27	5	localNodeList	NodeList
    //   93	93	6	localObject2	Object
    //   102	61	7	str	String
    // Exception table:
    //   from	to	target	type
    //   16	36	210	java/lang/Exception
    //   49	74	210	java/lang/Exception
    //   76	135	210	java/lang/Exception
    //   138	159	210	java/lang/Exception
    //   162	193	210	java/lang/Exception
  }
  
  public void setPickedMediaFileDeliveryType(String paramString)
  {
    this.mediaFileDeliveryType = paramString;
  }
  
  public void setPickedMediaFileLocation(String paramString)
  {
    this.mediaFileLocation = paramString;
  }
  
  private class DownloadTask
    extends AsyncTask<String, Void, Integer>
  {
    private final Context context;
    private final long downloadTimeout;
    private final VASTPlayer mVastPlayer;
    
    public DownloadTask(VASTPlayer paramVASTPlayer, Context paramContext, int paramInt)
    {
      this.mVastPlayer = paramVASTPlayer;
      this.context = paramContext;
      if (paramInt > 0) {}
      for (long l = paramInt;; l = 30000L)
      {
        this.downloadTimeout = l;
        return;
      }
    }
    
    /* Error */
    protected Integer doInBackground(String... paramVarArgs)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore 11
      //   3: aconst_null
      //   4: astore 21
      //   6: aconst_null
      //   7: astore 20
      //   9: aconst_null
      //   10: astore 15
      //   12: aconst_null
      //   13: astore 19
      //   15: aconst_null
      //   16: astore 14
      //   18: aconst_null
      //   19: astore 16
      //   21: aconst_null
      //   22: astore 22
      //   24: aconst_null
      //   25: astore 18
      //   27: aconst_null
      //   28: astore 17
      //   30: aconst_null
      //   31: astore 23
      //   33: aconst_null
      //   34: astore 24
      //   36: aconst_null
      //   37: astore 25
      //   39: aload 25
      //   41: astore 9
      //   43: aload 23
      //   45: astore 8
      //   47: aload 21
      //   49: astore 12
      //   51: aload 22
      //   53: astore 13
      //   55: aload 24
      //   57: astore 10
      //   59: invokestatic 46	java/lang/System:nanoTime	()J
      //   62: lstore 4
      //   64: aload 25
      //   66: astore 9
      //   68: aload 23
      //   70: astore 8
      //   72: aload 21
      //   74: astore 12
      //   76: aload 22
      //   78: astore 13
      //   80: aload 24
      //   82: astore 10
      //   84: new 48	java/net/URL
      //   87: dup
      //   88: aload_1
      //   89: iconst_0
      //   90: aaload
      //   91: invokespecial 51	java/net/URL:<init>	(Ljava/lang/String;)V
      //   94: astore 26
      //   96: aload 25
      //   98: astore 9
      //   100: aload 23
      //   102: astore 8
      //   104: aload 21
      //   106: astore 12
      //   108: aload 22
      //   110: astore 13
      //   112: aload 24
      //   114: astore 10
      //   116: aload_1
      //   117: iconst_0
      //   118: aaload
      //   119: aload_1
      //   120: iconst_0
      //   121: aaload
      //   122: bipush 47
      //   124: invokevirtual 57	java/lang/String:lastIndexOf	(I)I
      //   127: iconst_1
      //   128: iadd
      //   129: aload_1
      //   130: iconst_0
      //   131: aaload
      //   132: invokevirtual 61	java/lang/String:length	()I
      //   135: invokevirtual 65	java/lang/String:substring	(II)Ljava/lang/String;
      //   138: astore 27
      //   140: aload 25
      //   142: astore 9
      //   144: aload 23
      //   146: astore 8
      //   148: aload 21
      //   150: astore 12
      //   152: aload 22
      //   154: astore 13
      //   156: aload 24
      //   158: astore 10
      //   160: aload 26
      //   162: invokevirtual 69	java/net/URL:openConnection	()Ljava/net/URLConnection;
      //   165: checkcast 71	java/net/HttpURLConnection
      //   168: astore_1
      //   169: aload_1
      //   170: astore 9
      //   172: aload_1
      //   173: astore 8
      //   175: aload 21
      //   177: astore 12
      //   179: aload 22
      //   181: astore 13
      //   183: aload_1
      //   184: astore 10
      //   186: aload_1
      //   187: ldc 73
      //   189: invokevirtual 76	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
      //   192: aload_1
      //   193: astore 9
      //   195: aload_1
      //   196: astore 8
      //   198: aload 21
      //   200: astore 12
      //   202: aload 22
      //   204: astore 13
      //   206: aload_1
      //   207: astore 10
      //   209: aload_1
      //   210: sipush 5000
      //   213: invokevirtual 80	java/net/HttpURLConnection:setConnectTimeout	(I)V
      //   216: aload_1
      //   217: astore 9
      //   219: aload_1
      //   220: astore 8
      //   222: aload 21
      //   224: astore 12
      //   226: aload 22
      //   228: astore 13
      //   230: aload_1
      //   231: astore 10
      //   233: aload_1
      //   234: invokevirtual 83	java/net/HttpURLConnection:connect	()V
      //   237: aload_1
      //   238: astore 9
      //   240: aload_1
      //   241: astore 8
      //   243: aload 21
      //   245: astore 12
      //   247: aload 22
      //   249: astore 13
      //   251: aload_1
      //   252: astore 10
      //   254: aload_1
      //   255: invokevirtual 86	java/net/HttpURLConnection:getContentLength	()I
      //   258: istore_2
      //   259: aload_1
      //   260: astore 9
      //   262: aload_1
      //   263: astore 8
      //   265: aload 21
      //   267: astore 12
      //   269: aload 22
      //   271: astore 13
      //   273: aload_1
      //   274: astore 10
      //   276: aload_1
      //   277: invokevirtual 89	java/net/HttpURLConnection:getResponseCode	()I
      //   280: sipush 200
      //   283: if_icmpeq +111 -> 394
      //   286: aload_1
      //   287: astore 9
      //   289: aload_1
      //   290: astore 8
      //   292: aload 21
      //   294: astore 12
      //   296: aload 22
      //   298: astore 13
      //   300: aload_1
      //   301: astore 10
      //   303: invokestatic 93	com/upsight/mediation/vast/model/VASTModel:access$100	()Ljava/lang/String;
      //   306: new 95	java/lang/StringBuilder
      //   309: dup
      //   310: invokespecial 96	java/lang/StringBuilder:<init>	()V
      //   313: ldc 98
      //   315: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   318: aload_1
      //   319: invokevirtual 89	java/net/HttpURLConnection:getResponseCode	()I
      //   322: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   325: ldc 107
      //   327: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   330: aload_1
      //   331: invokevirtual 110	java/net/HttpURLConnection:getResponseMessage	()Ljava/lang/String;
      //   334: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   337: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   340: invokestatic 119	com/upsight/mediation/log/FuseLog:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   343: sipush 401
      //   346: invokestatic 125	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   349: astore 9
      //   351: iconst_0
      //   352: ifeq +11 -> 363
      //   355: new 127	java/lang/NullPointerException
      //   358: dup
      //   359: invokespecial 128	java/lang/NullPointerException:<init>	()V
      //   362: athrow
      //   363: iconst_0
      //   364: ifeq +11 -> 375
      //   367: new 127	java/lang/NullPointerException
      //   370: dup
      //   371: invokespecial 128	java/lang/NullPointerException:<init>	()V
      //   374: athrow
      //   375: aload 9
      //   377: astore 8
      //   379: aload_1
      //   380: ifnull +11 -> 391
      //   383: aload_1
      //   384: invokevirtual 131	java/net/HttpURLConnection:disconnect	()V
      //   387: aload 9
      //   389: astore 8
      //   391: aload 8
      //   393: areturn
      //   394: aload_1
      //   395: astore 9
      //   397: aload_1
      //   398: astore 8
      //   400: aload 21
      //   402: astore 12
      //   404: aload 22
      //   406: astore 13
      //   408: aload_1
      //   409: astore 10
      //   411: aload_1
      //   412: invokevirtual 131	java/net/HttpURLConnection:disconnect	()V
      //   415: aload_1
      //   416: astore 9
      //   418: aload_1
      //   419: astore 8
      //   421: aload 21
      //   423: astore 12
      //   425: aload 22
      //   427: astore 13
      //   429: aload_1
      //   430: astore 10
      //   432: new 133	java/io/File
      //   435: dup
      //   436: aload_0
      //   437: getfield 27	com/upsight/mediation/vast/model/VASTModel$DownloadTask:context	Landroid/content/Context;
      //   440: invokevirtual 139	android/content/Context:getCacheDir	()Ljava/io/File;
      //   443: ldc -115
      //   445: invokespecial 144	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
      //   448: astore 23
      //   450: aload_1
      //   451: astore 9
      //   453: aload_1
      //   454: astore 8
      //   456: aload 21
      //   458: astore 12
      //   460: aload 22
      //   462: astore 13
      //   464: aload_1
      //   465: astore 10
      //   467: aload 23
      //   469: invokevirtual 148	java/io/File:exists	()Z
      //   472: ifne +75 -> 547
      //   475: aload_1
      //   476: astore 9
      //   478: aload_1
      //   479: astore 8
      //   481: aload 21
      //   483: astore 12
      //   485: aload 22
      //   487: astore 13
      //   489: aload_1
      //   490: astore 10
      //   492: aload 23
      //   494: invokevirtual 151	java/io/File:mkdir	()Z
      //   497: ifne +50 -> 547
      //   500: sipush 401
      //   503: invokestatic 125	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   506: astore 9
      //   508: iconst_0
      //   509: ifeq +11 -> 520
      //   512: new 127	java/lang/NullPointerException
      //   515: dup
      //   516: invokespecial 128	java/lang/NullPointerException:<init>	()V
      //   519: athrow
      //   520: iconst_0
      //   521: ifeq +11 -> 532
      //   524: new 127	java/lang/NullPointerException
      //   527: dup
      //   528: invokespecial 128	java/lang/NullPointerException:<init>	()V
      //   531: athrow
      //   532: aload 9
      //   534: astore 8
      //   536: aload_1
      //   537: ifnull -146 -> 391
      //   540: aload_1
      //   541: invokevirtual 131	java/net/HttpURLConnection:disconnect	()V
      //   544: aload 9
      //   546: areturn
      //   547: aload_1
      //   548: astore 9
      //   550: aload_1
      //   551: astore 8
      //   553: aload 21
      //   555: astore 12
      //   557: aload 22
      //   559: astore 13
      //   561: aload_1
      //   562: astore 10
      //   564: new 133	java/io/File
      //   567: dup
      //   568: aload 23
      //   570: aload 27
      //   572: invokespecial 144	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
      //   575: astore 21
      //   577: aload_1
      //   578: astore 14
      //   580: aload 20
      //   582: astore 10
      //   584: aload_1
      //   585: astore 12
      //   587: aload 15
      //   589: astore 11
      //   591: aload_1
      //   592: astore 13
      //   594: aload 19
      //   596: astore 9
      //   598: aload 21
      //   600: invokevirtual 148	java/io/File:exists	()Z
      //   603: ifeq +106 -> 709
      //   606: aload_1
      //   607: astore 14
      //   609: aload 20
      //   611: astore 10
      //   613: aload_1
      //   614: astore 12
      //   616: aload 15
      //   618: astore 11
      //   620: aload_1
      //   621: astore 13
      //   623: aload 19
      //   625: astore 9
      //   627: aload 21
      //   629: invokevirtual 153	java/io/File:length	()J
      //   632: iload_2
      //   633: i2l
      //   634: lcmp
      //   635: ifne +74 -> 709
      //   638: aload_1
      //   639: astore 14
      //   641: aload 20
      //   643: astore 10
      //   645: aload_1
      //   646: astore 12
      //   648: aload 15
      //   650: astore 11
      //   652: aload_1
      //   653: astore 13
      //   655: aload 19
      //   657: astore 9
      //   659: aload_0
      //   660: getfield 20	com/upsight/mediation/vast/model/VASTModel$DownloadTask:this$0	Lcom/upsight/mediation/vast/model/VASTModel;
      //   663: aload 21
      //   665: invokevirtual 156	java/io/File:getAbsolutePath	()Ljava/lang/String;
      //   668: invokestatic 160	com/upsight/mediation/vast/model/VASTModel:access$002	(Lcom/upsight/mediation/vast/model/VASTModel;Ljava/lang/String;)Ljava/lang/String;
      //   671: pop
      //   672: iconst_0
      //   673: ifeq +11 -> 684
      //   676: new 127	java/lang/NullPointerException
      //   679: dup
      //   680: invokespecial 128	java/lang/NullPointerException:<init>	()V
      //   683: athrow
      //   684: iconst_0
      //   685: ifeq +11 -> 696
      //   688: new 127	java/lang/NullPointerException
      //   691: dup
      //   692: invokespecial 128	java/lang/NullPointerException:<init>	()V
      //   695: athrow
      //   696: aload_1
      //   697: ifnull +7 -> 704
      //   700: aload_1
      //   701: invokevirtual 131	java/net/HttpURLConnection:disconnect	()V
      //   704: iconst_0
      //   705: invokestatic 125	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   708: areturn
      //   709: iload_2
      //   710: i2l
      //   711: lstore 6
      //   713: aload_1
      //   714: astore 14
      //   716: aload 20
      //   718: astore 10
      //   720: aload_1
      //   721: astore 12
      //   723: aload 15
      //   725: astore 11
      //   727: aload_1
      //   728: astore 13
      //   730: aload 19
      //   732: astore 9
      //   734: lload 6
      //   736: aload_0
      //   737: getfield 25	com/upsight/mediation/vast/model/VASTModel$DownloadTask:mVastPlayer	Lcom/upsight/mediation/vast/VASTPlayer;
      //   740: invokevirtual 165	com/upsight/mediation/vast/VASTPlayer:getMaxFileSize	()J
      //   743: lcmp
      //   744: ifle +42 -> 786
      //   747: iconst_0
      //   748: ifeq +11 -> 759
      //   751: new 127	java/lang/NullPointerException
      //   754: dup
      //   755: invokespecial 128	java/lang/NullPointerException:<init>	()V
      //   758: athrow
      //   759: iconst_0
      //   760: ifeq +11 -> 771
      //   763: new 127	java/lang/NullPointerException
      //   766: dup
      //   767: invokespecial 128	java/lang/NullPointerException:<init>	()V
      //   770: athrow
      //   771: aload_1
      //   772: ifnull +7 -> 779
      //   775: aload_1
      //   776: invokevirtual 131	java/net/HttpURLConnection:disconnect	()V
      //   779: sipush 400
      //   782: invokestatic 125	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   785: areturn
      //   786: aload_1
      //   787: astore 14
      //   789: aload 20
      //   791: astore 10
      //   793: aload_1
      //   794: astore 12
      //   796: aload 15
      //   798: astore 11
      //   800: aload_1
      //   801: astore 13
      //   803: aload 19
      //   805: astore 9
      //   807: aload 26
      //   809: invokevirtual 69	java/net/URL:openConnection	()Ljava/net/URLConnection;
      //   812: checkcast 71	java/net/HttpURLConnection
      //   815: astore_1
      //   816: aload_1
      //   817: astore 14
      //   819: aload 20
      //   821: astore 10
      //   823: aload_1
      //   824: astore 12
      //   826: aload 15
      //   828: astore 11
      //   830: aload_1
      //   831: astore 13
      //   833: aload 19
      //   835: astore 9
      //   837: aload_1
      //   838: sipush 5000
      //   841: invokevirtual 80	java/net/HttpURLConnection:setConnectTimeout	(I)V
      //   844: aload_1
      //   845: astore 14
      //   847: aload 20
      //   849: astore 10
      //   851: aload_1
      //   852: astore 12
      //   854: aload 15
      //   856: astore 11
      //   858: aload_1
      //   859: astore 13
      //   861: aload 19
      //   863: astore 9
      //   865: aload_1
      //   866: invokevirtual 83	java/net/HttpURLConnection:connect	()V
      //   869: aload_1
      //   870: astore 14
      //   872: aload 20
      //   874: astore 10
      //   876: aload_1
      //   877: astore 12
      //   879: aload 15
      //   881: astore 11
      //   883: aload_1
      //   884: astore 13
      //   886: aload 19
      //   888: astore 9
      //   890: aload_1
      //   891: invokevirtual 89	java/net/HttpURLConnection:getResponseCode	()I
      //   894: sipush 200
      //   897: if_icmpeq +103 -> 1000
      //   900: aload_1
      //   901: astore 14
      //   903: aload 20
      //   905: astore 10
      //   907: aload_1
      //   908: astore 12
      //   910: aload 15
      //   912: astore 11
      //   914: aload_1
      //   915: astore 13
      //   917: aload 19
      //   919: astore 9
      //   921: invokestatic 93	com/upsight/mediation/vast/model/VASTModel:access$100	()Ljava/lang/String;
      //   924: new 95	java/lang/StringBuilder
      //   927: dup
      //   928: invokespecial 96	java/lang/StringBuilder:<init>	()V
      //   931: ldc 98
      //   933: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   936: aload_1
      //   937: invokevirtual 89	java/net/HttpURLConnection:getResponseCode	()I
      //   940: invokevirtual 105	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
      //   943: ldc 107
      //   945: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   948: aload_1
      //   949: invokevirtual 110	java/net/HttpURLConnection:getResponseMessage	()Ljava/lang/String;
      //   952: invokevirtual 102	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   955: invokevirtual 113	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   958: invokestatic 119	com/upsight/mediation/log/FuseLog:d	(Ljava/lang/String;Ljava/lang/String;)V
      //   961: iconst_0
      //   962: ifeq +11 -> 973
      //   965: new 127	java/lang/NullPointerException
      //   968: dup
      //   969: invokespecial 128	java/lang/NullPointerException:<init>	()V
      //   972: athrow
      //   973: iconst_0
      //   974: ifeq +11 -> 985
      //   977: new 127	java/lang/NullPointerException
      //   980: dup
      //   981: invokespecial 128	java/lang/NullPointerException:<init>	()V
      //   984: athrow
      //   985: aload_1
      //   986: ifnull +7 -> 993
      //   989: aload_1
      //   990: invokevirtual 131	java/net/HttpURLConnection:disconnect	()V
      //   993: sipush 401
      //   996: invokestatic 125	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   999: areturn
      //   1000: aload_1
      //   1001: astore 14
      //   1003: aload 20
      //   1005: astore 10
      //   1007: aload_1
      //   1008: astore 12
      //   1010: aload 15
      //   1012: astore 11
      //   1014: aload_1
      //   1015: astore 13
      //   1017: aload 19
      //   1019: astore 9
      //   1021: aload_1
      //   1022: invokevirtual 169	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
      //   1025: astore 8
      //   1027: aload_1
      //   1028: astore 14
      //   1030: aload 8
      //   1032: astore 10
      //   1034: aload_1
      //   1035: astore 12
      //   1037: aload 8
      //   1039: astore 11
      //   1041: aload_1
      //   1042: astore 13
      //   1044: aload 8
      //   1046: astore 9
      //   1048: new 171	java/io/FileOutputStream
      //   1051: dup
      //   1052: aload 21
      //   1054: invokespecial 174	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
      //   1057: astore 15
      //   1059: sipush 4096
      //   1062: newarray <illegal type>
      //   1064: astore 9
      //   1066: aload 8
      //   1068: aload 9
      //   1070: invokevirtual 180	java/io/InputStream:read	([B)I
      //   1073: istore_3
      //   1074: iload_3
      //   1075: iconst_m1
      //   1076: if_icmpeq +135 -> 1211
      //   1079: invokestatic 46	java/lang/System:nanoTime	()J
      //   1082: lload 4
      //   1084: lsub
      //   1085: ldc2_w 181
      //   1088: ldiv
      //   1089: aload_0
      //   1090: getfield 29	com/upsight/mediation/vast/model/VASTModel$DownloadTask:downloadTimeout	J
      //   1093: lcmp
      //   1094: iflt +9 -> 1103
      //   1097: aload_0
      //   1098: iconst_1
      //   1099: invokevirtual 186	com/upsight/mediation/vast/model/VASTModel$DownloadTask:cancel	(Z)Z
      //   1102: pop
      //   1103: aload_0
      //   1104: invokevirtual 189	com/upsight/mediation/vast/model/VASTModel$DownloadTask:isCancelled	()Z
      //   1107: ifeq +43 -> 1150
      //   1110: aload 8
      //   1112: invokevirtual 192	java/io/InputStream:close	()V
      //   1115: aload 15
      //   1117: ifnull +8 -> 1125
      //   1120: aload 15
      //   1122: invokevirtual 195	java/io/OutputStream:close	()V
      //   1125: aload 8
      //   1127: ifnull +8 -> 1135
      //   1130: aload 8
      //   1132: invokevirtual 192	java/io/InputStream:close	()V
      //   1135: aload_1
      //   1136: ifnull +7 -> 1143
      //   1139: aload_1
      //   1140: invokevirtual 131	java/net/HttpURLConnection:disconnect	()V
      //   1143: sipush 402
      //   1146: invokestatic 125	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   1149: areturn
      //   1150: aload 15
      //   1152: aload 9
      //   1154: iconst_0
      //   1155: iload_3
      //   1156: invokevirtual 199	java/io/OutputStream:write	([BII)V
      //   1159: goto -93 -> 1066
      //   1162: astore 9
      //   1164: aload 15
      //   1166: astore 9
      //   1168: sipush 402
      //   1171: invokestatic 125	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   1174: astore 10
      //   1176: aload 9
      //   1178: ifnull +8 -> 1186
      //   1181: aload 9
      //   1183: invokevirtual 195	java/io/OutputStream:close	()V
      //   1186: aload 8
      //   1188: ifnull +8 -> 1196
      //   1191: aload 8
      //   1193: invokevirtual 192	java/io/InputStream:close	()V
      //   1196: aload 10
      //   1198: astore 8
      //   1200: aload_1
      //   1201: ifnull -810 -> 391
      //   1204: aload_1
      //   1205: invokevirtual 131	java/net/HttpURLConnection:disconnect	()V
      //   1208: aload 10
      //   1210: areturn
      //   1211: aload 21
      //   1213: invokevirtual 148	java/io/File:exists	()Z
      //   1216: ifeq +14 -> 1230
      //   1219: aload 21
      //   1221: invokevirtual 153	java/io/File:length	()J
      //   1224: iload_2
      //   1225: i2l
      //   1226: lcmp
      //   1227: ifeq +38 -> 1265
      //   1230: aload 15
      //   1232: ifnull +8 -> 1240
      //   1235: aload 15
      //   1237: invokevirtual 195	java/io/OutputStream:close	()V
      //   1240: aload 8
      //   1242: ifnull +8 -> 1250
      //   1245: aload 8
      //   1247: invokevirtual 192	java/io/InputStream:close	()V
      //   1250: aload_1
      //   1251: ifnull +7 -> 1258
      //   1254: aload_1
      //   1255: invokevirtual 131	java/net/HttpURLConnection:disconnect	()V
      //   1258: sipush 400
      //   1261: invokestatic 125	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   1264: areturn
      //   1265: aload 15
      //   1267: ifnull +8 -> 1275
      //   1270: aload 15
      //   1272: invokevirtual 195	java/io/OutputStream:close	()V
      //   1275: aload 8
      //   1277: ifnull +8 -> 1285
      //   1280: aload 8
      //   1282: invokevirtual 192	java/io/InputStream:close	()V
      //   1285: aload_1
      //   1286: ifnull +7 -> 1293
      //   1289: aload_1
      //   1290: invokevirtual 131	java/net/HttpURLConnection:disconnect	()V
      //   1293: aload_0
      //   1294: getfield 20	com/upsight/mediation/vast/model/VASTModel$DownloadTask:this$0	Lcom/upsight/mediation/vast/model/VASTModel;
      //   1297: aload 21
      //   1299: invokevirtual 156	java/io/File:getAbsolutePath	()Ljava/lang/String;
      //   1302: invokestatic 160	com/upsight/mediation/vast/model/VASTModel:access$002	(Lcom/upsight/mediation/vast/model/VASTModel;Ljava/lang/String;)Ljava/lang/String;
      //   1305: pop
      //   1306: iconst_0
      //   1307: invokestatic 125	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   1310: areturn
      //   1311: astore_1
      //   1312: aload 16
      //   1314: astore 10
      //   1316: aload 11
      //   1318: astore 8
      //   1320: aload 9
      //   1322: astore_1
      //   1323: sipush 400
      //   1326: invokestatic 125	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
      //   1329: astore 9
      //   1331: aload 10
      //   1333: ifnull +8 -> 1341
      //   1336: aload 10
      //   1338: invokevirtual 195	java/io/OutputStream:close	()V
      //   1341: aload 8
      //   1343: ifnull +8 -> 1351
      //   1346: aload 8
      //   1348: invokevirtual 192	java/io/InputStream:close	()V
      //   1351: aload 9
      //   1353: astore 8
      //   1355: aload_1
      //   1356: ifnull -965 -> 391
      //   1359: aload_1
      //   1360: invokevirtual 131	java/net/HttpURLConnection:disconnect	()V
      //   1363: aload 9
      //   1365: areturn
      //   1366: astore 11
      //   1368: aload 13
      //   1370: astore 9
      //   1372: aload 12
      //   1374: astore 10
      //   1376: aload 8
      //   1378: astore_1
      //   1379: aload 9
      //   1381: ifnull +8 -> 1389
      //   1384: aload 9
      //   1386: invokevirtual 195	java/io/OutputStream:close	()V
      //   1389: aload 10
      //   1391: ifnull +8 -> 1399
      //   1394: aload 10
      //   1396: invokevirtual 192	java/io/InputStream:close	()V
      //   1399: aload_1
      //   1400: ifnull +7 -> 1407
      //   1403: aload_1
      //   1404: invokevirtual 131	java/net/HttpURLConnection:disconnect	()V
      //   1407: aload 11
      //   1409: athrow
      //   1410: astore 8
      //   1412: goto -13 -> 1399
      //   1415: astore 11
      //   1417: aload 14
      //   1419: astore_1
      //   1420: aload 18
      //   1422: astore 9
      //   1424: goto -45 -> 1379
      //   1427: astore 11
      //   1429: aload 15
      //   1431: astore 9
      //   1433: aload 8
      //   1435: astore 10
      //   1437: goto -58 -> 1379
      //   1440: astore 8
      //   1442: goto -91 -> 1351
      //   1445: astore_1
      //   1446: aload 12
      //   1448: astore_1
      //   1449: aload 11
      //   1451: astore 8
      //   1453: aload 16
      //   1455: astore 10
      //   1457: goto -134 -> 1323
      //   1460: astore 9
      //   1462: aload 15
      //   1464: astore 10
      //   1466: goto -143 -> 1323
      //   1469: astore 8
      //   1471: goto -275 -> 1196
      //   1474: astore_1
      //   1475: aload 10
      //   1477: astore_1
      //   1478: aload 14
      //   1480: astore 8
      //   1482: aload 17
      //   1484: astore 9
      //   1486: goto -318 -> 1168
      //   1489: astore_1
      //   1490: aload 13
      //   1492: astore_1
      //   1493: aload 9
      //   1495: astore 8
      //   1497: aload 17
      //   1499: astore 9
      //   1501: goto -333 -> 1168
      //   1504: astore 8
      //   1506: goto -221 -> 1285
      //   1509: astore 8
      //   1511: goto -261 -> 1250
      //   1514: astore 8
      //   1516: goto -381 -> 1135
      //   1519: astore 8
      //   1521: goto -536 -> 985
      //   1524: astore 8
      //   1526: goto -755 -> 771
      //   1529: astore 8
      //   1531: goto -835 -> 696
      //   1534: astore 8
      //   1536: goto -1004 -> 532
      //   1539: astore 8
      //   1541: goto -1166 -> 375
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	1544	0	this	DownloadTask
      //   0	1544	1	paramVarArgs	String[]
      //   258	967	2	i	int
      //   1073	83	3	j	int
      //   62	1021	4	l1	long
      //   711	24	6	l2	long
      //   45	1332	8	localObject1	Object
      //   1410	24	8	localIOException1	IOException
      //   1440	1	8	localIOException2	IOException
      //   1451	1	8	localObject2	Object
      //   1469	1	8	localIOException3	IOException
      //   1480	16	8	localObject3	Object
      //   1504	1	8	localIOException4	IOException
      //   1509	1	8	localIOException5	IOException
      //   1514	1	8	localIOException6	IOException
      //   1519	1	8	localIOException7	IOException
      //   1524	1	8	localIOException8	IOException
      //   1529	1	8	localIOException9	IOException
      //   1534	1	8	localIOException10	IOException
      //   1539	1	8	localIOException11	IOException
      //   41	1112	9	localObject4	Object
      //   1162	1	9	localSocketTimeoutException	java.net.SocketTimeoutException
      //   1166	266	9	localObject5	Object
      //   1460	1	9	localException	Exception
      //   1484	16	9	localObject6	Object
      //   57	1419	10	localObject7	Object
      //   1	1316	11	localObject8	Object
      //   1366	42	11	localObject9	Object
      //   1415	1	11	localObject10	Object
      //   1427	23	11	localObject11	Object
      //   49	1398	12	localObject12	Object
      //   53	1438	13	localObject13	Object
      //   16	1463	14	arrayOfString	String[]
      //   10	1453	15	localFileOutputStream	java.io.FileOutputStream
      //   19	1435	16	localObject14	Object
      //   28	1470	17	localObject15	Object
      //   25	1396	18	localObject16	Object
      //   13	1005	19	localObject17	Object
      //   7	997	20	localObject18	Object
      //   4	1294	21	localFile1	java.io.File
      //   22	536	22	localObject19	Object
      //   31	538	23	localFile2	java.io.File
      //   34	123	24	localObject20	Object
      //   37	104	25	localObject21	Object
      //   94	714	26	localURL	java.net.URL
      //   138	433	27	str	String
      // Exception table:
      //   from	to	target	type
      //   1059	1066	1162	java/net/SocketTimeoutException
      //   1066	1074	1162	java/net/SocketTimeoutException
      //   1079	1103	1162	java/net/SocketTimeoutException
      //   1103	1115	1162	java/net/SocketTimeoutException
      //   1150	1159	1162	java/net/SocketTimeoutException
      //   1211	1230	1162	java/net/SocketTimeoutException
      //   59	64	1311	java/lang/Exception
      //   84	96	1311	java/lang/Exception
      //   116	140	1311	java/lang/Exception
      //   160	169	1311	java/lang/Exception
      //   186	192	1311	java/lang/Exception
      //   209	216	1311	java/lang/Exception
      //   233	237	1311	java/lang/Exception
      //   254	259	1311	java/lang/Exception
      //   276	286	1311	java/lang/Exception
      //   303	343	1311	java/lang/Exception
      //   411	415	1311	java/lang/Exception
      //   432	450	1311	java/lang/Exception
      //   467	475	1311	java/lang/Exception
      //   492	500	1311	java/lang/Exception
      //   564	577	1311	java/lang/Exception
      //   59	64	1366	finally
      //   84	96	1366	finally
      //   116	140	1366	finally
      //   160	169	1366	finally
      //   186	192	1366	finally
      //   209	216	1366	finally
      //   233	237	1366	finally
      //   254	259	1366	finally
      //   276	286	1366	finally
      //   303	343	1366	finally
      //   411	415	1366	finally
      //   432	450	1366	finally
      //   467	475	1366	finally
      //   492	500	1366	finally
      //   564	577	1366	finally
      //   1384	1389	1410	java/io/IOException
      //   1394	1399	1410	java/io/IOException
      //   598	606	1415	finally
      //   627	638	1415	finally
      //   659	672	1415	finally
      //   734	747	1415	finally
      //   807	816	1415	finally
      //   837	844	1415	finally
      //   865	869	1415	finally
      //   890	900	1415	finally
      //   921	961	1415	finally
      //   1021	1027	1415	finally
      //   1048	1059	1415	finally
      //   1059	1066	1427	finally
      //   1066	1074	1427	finally
      //   1079	1103	1427	finally
      //   1103	1115	1427	finally
      //   1150	1159	1427	finally
      //   1211	1230	1427	finally
      //   1336	1341	1440	java/io/IOException
      //   1346	1351	1440	java/io/IOException
      //   598	606	1445	java/lang/Exception
      //   627	638	1445	java/lang/Exception
      //   659	672	1445	java/lang/Exception
      //   734	747	1445	java/lang/Exception
      //   807	816	1445	java/lang/Exception
      //   837	844	1445	java/lang/Exception
      //   865	869	1445	java/lang/Exception
      //   890	900	1445	java/lang/Exception
      //   921	961	1445	java/lang/Exception
      //   1021	1027	1445	java/lang/Exception
      //   1048	1059	1445	java/lang/Exception
      //   1059	1066	1460	java/lang/Exception
      //   1066	1074	1460	java/lang/Exception
      //   1079	1103	1460	java/lang/Exception
      //   1103	1115	1460	java/lang/Exception
      //   1150	1159	1460	java/lang/Exception
      //   1211	1230	1460	java/lang/Exception
      //   1181	1186	1469	java/io/IOException
      //   1191	1196	1469	java/io/IOException
      //   59	64	1474	java/net/SocketTimeoutException
      //   84	96	1474	java/net/SocketTimeoutException
      //   116	140	1474	java/net/SocketTimeoutException
      //   160	169	1474	java/net/SocketTimeoutException
      //   186	192	1474	java/net/SocketTimeoutException
      //   209	216	1474	java/net/SocketTimeoutException
      //   233	237	1474	java/net/SocketTimeoutException
      //   254	259	1474	java/net/SocketTimeoutException
      //   276	286	1474	java/net/SocketTimeoutException
      //   303	343	1474	java/net/SocketTimeoutException
      //   411	415	1474	java/net/SocketTimeoutException
      //   432	450	1474	java/net/SocketTimeoutException
      //   467	475	1474	java/net/SocketTimeoutException
      //   492	500	1474	java/net/SocketTimeoutException
      //   564	577	1474	java/net/SocketTimeoutException
      //   598	606	1489	java/net/SocketTimeoutException
      //   627	638	1489	java/net/SocketTimeoutException
      //   659	672	1489	java/net/SocketTimeoutException
      //   734	747	1489	java/net/SocketTimeoutException
      //   807	816	1489	java/net/SocketTimeoutException
      //   837	844	1489	java/net/SocketTimeoutException
      //   865	869	1489	java/net/SocketTimeoutException
      //   890	900	1489	java/net/SocketTimeoutException
      //   921	961	1489	java/net/SocketTimeoutException
      //   1021	1027	1489	java/net/SocketTimeoutException
      //   1048	1059	1489	java/net/SocketTimeoutException
      //   1270	1275	1504	java/io/IOException
      //   1280	1285	1504	java/io/IOException
      //   1235	1240	1509	java/io/IOException
      //   1245	1250	1509	java/io/IOException
      //   1120	1125	1514	java/io/IOException
      //   1130	1135	1514	java/io/IOException
      //   965	973	1519	java/io/IOException
      //   977	985	1519	java/io/IOException
      //   751	759	1524	java/io/IOException
      //   763	771	1524	java/io/IOException
      //   676	684	1529	java/io/IOException
      //   688	696	1529	java/io/IOException
      //   512	520	1534	java/io/IOException
      //   524	532	1534	java/io/IOException
      //   355	363	1539	java/io/IOException
      //   367	375	1539	java/io/IOException
    }
    
    protected void onCancelled()
    {
      this.mVastPlayer.listener.vastError(402);
    }
    
    protected void onPostExecute(Integer paramInteger)
    {
      if (paramInteger.intValue() != 0)
      {
        this.mVastPlayer.listener.vastError(paramInteger.intValue());
        return;
      }
      FuseLog.v(VASTModel.TAG, "on execute complete");
      this.mVastPlayer.setLoaded(true);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/model/VASTModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */