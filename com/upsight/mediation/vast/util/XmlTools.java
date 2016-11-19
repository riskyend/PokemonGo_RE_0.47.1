package com.upsight.mediation.vast.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XmlTools
{
  private static String TAG = "XmlTools";
  
  public static String getElementValue(Node paramNode)
  {
    NodeList localNodeList = paramNode.getChildNodes();
    paramNode = null;
    int i = 0;
    while (i < localNodeList.getLength())
    {
      paramNode = ((CharacterData)localNodeList.item(i)).getData().trim();
      if (paramNode.length() == 0) {
        i += 1;
      } else {
        return paramNode;
      }
    }
    return paramNode;
  }
  
  public static void logXmlDocument(Document paramDocument)
  {
    try
    {
      Transformer localTransformer = TransformerFactory.newInstance().newTransformer();
      localTransformer.setOutputProperty("omit-xml-declaration", "no");
      localTransformer.setOutputProperty("method", "xml");
      localTransformer.setOutputProperty("indent", "yes");
      localTransformer.setOutputProperty("encoding", "UTF-8");
      localTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
      StringWriter localStringWriter = new StringWriter();
      localTransformer.transform(new DOMSource(paramDocument), new StreamResult(localStringWriter));
      return;
    }
    catch (Exception paramDocument) {}
  }
  
  public static String stringFromStream(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte['Ѐ'];
    for (;;)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i == -1) {
        break;
      }
      localByteArrayOutputStream.write(arrayOfByte, 0, i);
    }
    return new String(localByteArrayOutputStream.toByteArray(), "UTF-8");
  }
  
  public static Document stringToDocument(String paramString)
  {
    try
    {
      DocumentBuilder localDocumentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      InputSource localInputSource = new InputSource();
      localInputSource.setCharacterStream(new StringReader(paramString));
      paramString = localDocumentBuilder.parse(localInputSource);
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
  }
  
  public static String xmlDocumentToString(Document paramDocument)
  {
    try
    {
      Transformer localTransformer = TransformerFactory.newInstance().newTransformer();
      localTransformer.setOutputProperty("omit-xml-declaration", "no");
      localTransformer.setOutputProperty("method", "xml");
      localTransformer.setOutputProperty("indent", "yes");
      localTransformer.setOutputProperty("encoding", "UTF-8");
      localTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
      StringWriter localStringWriter = new StringWriter();
      localTransformer.transform(new DOMSource(paramDocument), new StreamResult(localStringWriter));
      paramDocument = localStringWriter.toString();
      return paramDocument;
    }
    catch (Exception paramDocument) {}
    return null;
  }
  
  public static String xmlDocumentToString(Node paramNode)
  {
    try
    {
      Transformer localTransformer = TransformerFactory.newInstance().newTransformer();
      localTransformer.setOutputProperty("omit-xml-declaration", "yes");
      localTransformer.setOutputProperty("method", "xml");
      localTransformer.setOutputProperty("indent", "yes");
      localTransformer.setOutputProperty("encoding", "UTF-8");
      localTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
      StringWriter localStringWriter = new StringWriter();
      localTransformer.transform(new DOMSource(paramNode), new StreamResult(localStringWriter));
      paramNode = localStringWriter.toString();
      return paramNode;
    }
    catch (Exception paramNode) {}
    return null;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/util/XmlTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */