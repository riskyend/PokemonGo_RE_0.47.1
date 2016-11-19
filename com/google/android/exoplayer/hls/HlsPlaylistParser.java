package com.google.android.exoplayer.hls;

import com.google.android.exoplayer.ParserException;
import com.google.android.exoplayer.chunk.Format;
import com.google.android.exoplayer.upstream.UriLoadable.Parser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;

public final class HlsPlaylistParser
  implements UriLoadable.Parser<HlsPlaylist>
{
  private static final String AUDIO_TYPE = "AUDIO";
  private static final String BANDWIDTH_ATTR = "BANDWIDTH";
  private static final Pattern BANDWIDTH_ATTR_REGEX = Pattern.compile("BANDWIDTH=(\\d+)\\b");
  private static final Pattern BYTERANGE_REGEX;
  private static final String BYTERANGE_TAG = "#EXT-X-BYTERANGE";
  private static final String CLOSED_CAPTIONS_TYPE = "CLOSED-CAPTIONS";
  private static final String CODECS_ATTR = "CODECS";
  private static final Pattern CODECS_ATTR_REGEX = Pattern.compile("CODECS=\"(.+?)\"");
  private static final String DISCONTINUITY_SEQUENCE_TAG = "#EXT-X-DISCONTINUITY-SEQUENCE";
  private static final String DISCONTINUITY_TAG = "#EXT-X-DISCONTINUITY";
  private static final String ENDLIST_TAG = "#EXT-X-ENDLIST";
  private static final String IV_ATTR = "IV";
  private static final Pattern IV_ATTR_REGEX;
  private static final String KEY_TAG = "#EXT-X-KEY";
  private static final String LANGUAGE_ATTR = "LANGUAGE";
  private static final Pattern LANGUAGE_ATTR_REGEX = Pattern.compile("LANGUAGE=\"(.+?)\"");
  private static final Pattern MEDIA_DURATION_REGEX;
  private static final String MEDIA_DURATION_TAG = "#EXTINF";
  private static final Pattern MEDIA_SEQUENCE_REGEX;
  private static final String MEDIA_SEQUENCE_TAG = "#EXT-X-MEDIA-SEQUENCE";
  private static final String MEDIA_TAG = "#EXT-X-MEDIA";
  private static final String METHOD_AES128 = "AES-128";
  private static final String METHOD_ATTR = "METHOD";
  private static final Pattern METHOD_ATTR_REGEX;
  private static final String METHOD_NONE = "NONE";
  private static final String NAME_ATTR = "NAME";
  private static final Pattern NAME_ATTR_REGEX = Pattern.compile("NAME=\"(.+?)\"");
  private static final String RESOLUTION_ATTR = "RESOLUTION";
  private static final Pattern RESOLUTION_ATTR_REGEX = Pattern.compile("RESOLUTION=(\\d+x\\d+)");
  private static final String STREAM_INF_TAG = "#EXT-X-STREAM-INF";
  private static final String SUBTITLES_TYPE = "SUBTITLES";
  private static final Pattern TARGET_DURATION_REGEX;
  private static final String TARGET_DURATION_TAG = "#EXT-X-TARGETDURATION";
  private static final String TYPE_ATTR = "TYPE";
  private static final Pattern TYPE_ATTR_REGEX;
  private static final String URI_ATTR = "URI";
  private static final Pattern URI_ATTR_REGEX;
  private static final Pattern VERSION_REGEX;
  private static final String VERSION_TAG = "#EXT-X-VERSION";
  private static final String VIDEO_TYPE = "VIDEO";
  
  static
  {
    MEDIA_DURATION_REGEX = Pattern.compile("#EXTINF:([\\d.]+)\\b");
    MEDIA_SEQUENCE_REGEX = Pattern.compile("#EXT-X-MEDIA-SEQUENCE:(\\d+)\\b");
    TARGET_DURATION_REGEX = Pattern.compile("#EXT-X-TARGETDURATION:(\\d+)\\b");
    VERSION_REGEX = Pattern.compile("#EXT-X-VERSION:(\\d+)\\b");
    BYTERANGE_REGEX = Pattern.compile("#EXT-X-BYTERANGE:(\\d+(?:@\\d+)?)\\b");
    METHOD_ATTR_REGEX = Pattern.compile("METHOD=(NONE|AES-128)");
    URI_ATTR_REGEX = Pattern.compile("URI=\"(.+?)\"");
    IV_ATTR_REGEX = Pattern.compile("IV=([^,.*]+)");
    TYPE_ATTR_REGEX = Pattern.compile("TYPE=(AUDIO|VIDEO|SUBTITLES|CLOSED-CAPTIONS)");
  }
  
  private static HlsMasterPlaylist parseMasterPlaylist(LineIterator paramLineIterator, String paramString)
    throws IOException
  {
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    int k = 0;
    String str2 = null;
    int j = -1;
    int i = -1;
    String str1 = null;
    int m = 0;
    while (paramLineIterator.hasNext())
    {
      String str3 = paramLineIterator.next();
      Object localObject;
      if (str3.startsWith("#EXT-X-MEDIA"))
      {
        if ("SUBTITLES".equals(HlsParserUtil.parseStringAttr(str3, TYPE_ATTR_REGEX, "TYPE")))
        {
          localObject = HlsParserUtil.parseStringAttr(str3, NAME_ATTR_REGEX, "NAME");
          localArrayList2.add(new Variant(HlsParserUtil.parseStringAttr(str3, URI_ATTR_REGEX, "URI"), new Format((String)localObject, "application/x-mpegURL", -1, -1, -1.0F, -1, -1, -1, HlsParserUtil.parseOptionalStringAttr(str3, LANGUAGE_ATTR_REGEX), str2)));
        }
      }
      else
      {
        if (str3.startsWith("#EXT-X-STREAM-INF"))
        {
          m = HlsParserUtil.parseIntAttr(str3, BANDWIDTH_ATTR_REGEX, "BANDWIDTH");
          str2 = HlsParserUtil.parseOptionalStringAttr(str3, CODECS_ATTR_REGEX);
          str1 = HlsParserUtil.parseOptionalStringAttr(str3, NAME_ATTR_REGEX);
          localObject = HlsParserUtil.parseOptionalStringAttr(str3, RESOLUTION_ATTR_REGEX);
          int n;
          if (localObject != null)
          {
            localObject = ((String)localObject).split("x");
            i = Integer.parseInt(localObject[0]);
            j = i;
            if (i <= 0) {
              j = -1;
            }
            n = Integer.parseInt(localObject[1]);
            k = j;
            i = n;
            if (n <= 0)
            {
              i = -1;
              k = j;
            }
          }
          for (;;)
          {
            n = 1;
            j = k;
            k = m;
            m = n;
            break;
            k = -1;
            i = -1;
          }
        }
        if ((!str3.startsWith("#")) && (m != 0))
        {
          localObject = str1;
          if (str1 == null) {
            localObject = Integer.toString(localArrayList1.size());
          }
          localArrayList1.add(new Variant(str3, new Format((String)localObject, "application/x-mpegURL", j, i, -1.0F, -1, -1, k, null, str2)));
          k = 0;
          str2 = null;
          str1 = null;
          j = -1;
          i = -1;
          m = 0;
        }
      }
    }
    return new HlsMasterPlaylist(paramString, localArrayList1, localArrayList2);
  }
  
  private static HlsMediaPlaylist parseMediaPlaylist(LineIterator paramLineIterator, String paramString)
    throws IOException
  {
    int i2 = 0;
    int i1 = 0;
    int n = 1;
    boolean bool3 = true;
    ArrayList localArrayList = new ArrayList();
    double d = 0.0D;
    int k = 0;
    long l = 0L;
    int i = 0;
    int j = -1;
    int m = 0;
    boolean bool1 = false;
    String str2 = null;
    String str1 = null;
    String str3;
    do
    {
      Object localObject;
      int i3;
      for (;;)
      {
        bool2 = bool3;
        if (!paramLineIterator.hasNext()) {
          break label492;
        }
        str3 = paramLineIterator.next();
        if (str3.startsWith("#EXT-X-TARGETDURATION"))
        {
          i1 = HlsParserUtil.parseIntAttr(str3, TARGET_DURATION_REGEX, "#EXT-X-TARGETDURATION");
        }
        else if (str3.startsWith("#EXT-X-MEDIA-SEQUENCE"))
        {
          i2 = HlsParserUtil.parseIntAttr(str3, MEDIA_SEQUENCE_REGEX, "#EXT-X-MEDIA-SEQUENCE");
          m = i2;
        }
        else if (str3.startsWith("#EXT-X-VERSION"))
        {
          n = HlsParserUtil.parseIntAttr(str3, VERSION_REGEX, "#EXT-X-VERSION");
        }
        else if (str3.startsWith("#EXTINF"))
        {
          d = HlsParserUtil.parseDoubleAttr(str3, MEDIA_DURATION_REGEX, "#EXTINF");
        }
        else if (str3.startsWith("#EXT-X-KEY"))
        {
          bool1 = "AES-128".equals(HlsParserUtil.parseStringAttr(str3, METHOD_ATTR_REGEX, "METHOD"));
          if (bool1)
          {
            str2 = HlsParserUtil.parseStringAttr(str3, URI_ATTR_REGEX, "URI");
            str1 = HlsParserUtil.parseOptionalStringAttr(str3, IV_ATTR_REGEX);
          }
          else
          {
            str2 = null;
            str1 = null;
          }
        }
        else if (str3.startsWith("#EXT-X-BYTERANGE"))
        {
          localObject = HlsParserUtil.parseStringAttr(str3, BYTERANGE_REGEX, "#EXT-X-BYTERANGE").split("@");
          i3 = Integer.parseInt(localObject[0]);
          j = i3;
          if (localObject.length > 1)
          {
            i = Integer.parseInt(localObject[1]);
            j = i3;
          }
        }
        else if (str3.startsWith("#EXT-X-DISCONTINUITY-SEQUENCE"))
        {
          k = Integer.parseInt(str3.substring(str3.indexOf(':') + 1));
        }
        else
        {
          if (!str3.equals("#EXT-X-DISCONTINUITY")) {
            break;
          }
          k += 1;
        }
      }
      if (!str3.startsWith("#"))
      {
        if (!bool1) {
          localObject = null;
        }
        for (;;)
        {
          i3 = m + 1;
          m = i;
          if (j == -1) {
            m = 0;
          }
          localArrayList.add(new HlsMediaPlaylist.Segment(str3, d, k, l, bool1, str2, (String)localObject, m, j));
          l += (1000000.0D * d);
          d = 0.0D;
          i = m;
          if (j != -1) {
            i = m + j;
          }
          j = -1;
          m = i3;
          break;
          if (str1 != null) {
            localObject = str1;
          } else {
            localObject = Integer.toHexString(m);
          }
        }
      }
    } while (!str3.equals("#EXT-X-ENDLIST"));
    boolean bool2 = false;
    label492:
    return new HlsMediaPlaylist(paramString, i2, i1, n, bool2, Collections.unmodifiableList(localArrayList));
  }
  
  public HlsPlaylist parse(String paramString, InputStream paramInputStream)
    throws IOException, ParserException
  {
    paramInputStream = new BufferedReader(new InputStreamReader(paramInputStream));
    LinkedList localLinkedList = new LinkedList();
    try
    {
      for (;;)
      {
        String str = paramInputStream.readLine();
        if (str == null) {
          break;
        }
        str = str.trim();
        if (!str.isEmpty())
        {
          if (str.startsWith("#EXT-X-STREAM-INF"))
          {
            localLinkedList.add(str);
            paramString = parseMasterPlaylist(new LineIterator(localLinkedList, paramInputStream), paramString);
            return paramString;
          }
          if ((str.startsWith("#EXT-X-TARGETDURATION")) || (str.startsWith("#EXT-X-MEDIA-SEQUENCE")) || (str.startsWith("#EXTINF")) || (str.startsWith("#EXT-X-KEY")) || (str.startsWith("#EXT-X-BYTERANGE")) || (str.equals("#EXT-X-DISCONTINUITY")) || (str.equals("#EXT-X-DISCONTINUITY-SEQUENCE")) || (str.equals("#EXT-X-ENDLIST")))
          {
            localLinkedList.add(str);
            paramString = parseMediaPlaylist(new LineIterator(localLinkedList, paramInputStream), paramString);
            return paramString;
          }
          localLinkedList.add(str);
        }
      }
    }
    finally
    {
      paramInputStream.close();
    }
    throw new ParserException("Failed to parse the playlist, could not identify any tags.");
  }
  
  private static class LineIterator
  {
    private final Queue<String> extraLines;
    private String next;
    private final BufferedReader reader;
    
    public LineIterator(Queue<String> paramQueue, BufferedReader paramBufferedReader)
    {
      this.extraLines = paramQueue;
      this.reader = paramBufferedReader;
    }
    
    public boolean hasNext()
      throws IOException
    {
      if (this.next != null) {
        return true;
      }
      if (!this.extraLines.isEmpty())
      {
        this.next = ((String)this.extraLines.poll());
        return true;
      }
      do
      {
        String str = this.reader.readLine();
        this.next = str;
        if (str == null) {
          break;
        }
        this.next = this.next.trim();
      } while (this.next.isEmpty());
      return true;
      return false;
    }
    
    public String next()
      throws IOException
    {
      String str = null;
      if (hasNext())
      {
        str = this.next;
        this.next = null;
      }
      return str;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/hls/HlsPlaylistParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */