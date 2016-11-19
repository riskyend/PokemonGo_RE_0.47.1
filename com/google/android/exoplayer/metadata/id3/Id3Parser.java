package com.google.android.exoplayer.metadata.id3;

import com.google.android.exoplayer.ParserException;
import com.google.android.exoplayer.metadata.MetadataParser;
import com.google.android.exoplayer.util.ParsableByteArray;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class Id3Parser
  implements MetadataParser<List<Id3Frame>>
{
  private static final int ID3_TEXT_ENCODING_ISO_8859_1 = 0;
  private static final int ID3_TEXT_ENCODING_UTF_16 = 1;
  private static final int ID3_TEXT_ENCODING_UTF_16BE = 2;
  private static final int ID3_TEXT_ENCODING_UTF_8 = 3;
  
  private static int delimiterLength(int paramInt)
  {
    if ((paramInt == 0) || (paramInt == 3)) {
      return 1;
    }
    return 2;
  }
  
  private static String getCharsetName(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "ISO-8859-1";
    case 0: 
      return "ISO-8859-1";
    case 1: 
      return "UTF-16";
    case 2: 
      return "UTF-16BE";
    }
    return "UTF-8";
  }
  
  private static int indexOf(byte[] paramArrayOfByte, int paramInt, byte paramByte)
  {
    while (paramInt < paramArrayOfByte.length)
    {
      if (paramArrayOfByte[paramInt] == paramByte) {
        return paramInt;
      }
      paramInt += 1;
    }
    return paramArrayOfByte.length;
  }
  
  private static int indexOfEOS(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = indexOf(paramArrayOfByte, paramInt1, (byte)0);
    if (paramInt2 != 0)
    {
      paramInt1 = i;
      if (paramInt2 != 3) {}
    }
    else
    {
      return i;
    }
    do
    {
      paramInt1 = indexOf(paramArrayOfByte, paramInt1 + 1, (byte)0);
      if (paramInt1 >= paramArrayOfByte.length - 1) {
        break;
      }
    } while (paramArrayOfByte[(paramInt1 + 1)] != 0);
    return paramInt1;
    return paramArrayOfByte.length;
  }
  
  private static int parseId3Header(ParsableByteArray paramParsableByteArray)
    throws ParserException
  {
    int i = paramParsableByteArray.readUnsignedByte();
    int j = paramParsableByteArray.readUnsignedByte();
    int k = paramParsableByteArray.readUnsignedByte();
    if ((i != 73) || (j != 68) || (k != 51)) {
      throw new ParserException(String.format(Locale.US, "Unexpected ID3 file identifier, expected \"ID3\", actual \"%c%c%c\".", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) }));
    }
    paramParsableByteArray.skipBytes(2);
    k = paramParsableByteArray.readUnsignedByte();
    j = paramParsableByteArray.readSynchSafeInt();
    i = j;
    if ((k & 0x2) != 0)
    {
      i = paramParsableByteArray.readSynchSafeInt();
      if (i > 4) {
        paramParsableByteArray.skipBytes(i - 4);
      }
      i = j - i;
    }
    j = i;
    if ((k & 0x8) != 0) {
      j = i - 10;
    }
    return j;
  }
  
  public boolean canParse(String paramString)
  {
    return paramString.equals("application/id3");
  }
  
  public List<Id3Frame> parse(byte[] paramArrayOfByte, int paramInt)
    throws UnsupportedEncodingException, ParserException
  {
    ArrayList localArrayList = new ArrayList();
    paramArrayOfByte = new ParsableByteArray(paramArrayOfByte, paramInt);
    paramInt = parseId3Header(paramArrayOfByte);
    int j;
    int k;
    int m;
    int n;
    int i;
    if (paramInt > 0)
    {
      j = paramArrayOfByte.readUnsignedByte();
      k = paramArrayOfByte.readUnsignedByte();
      m = paramArrayOfByte.readUnsignedByte();
      n = paramArrayOfByte.readUnsignedByte();
      i = paramArrayOfByte.readSynchSafeInt();
      if (i > 1) {}
    }
    else
    {
      return Collections.unmodifiableList(localArrayList);
    }
    paramArrayOfByte.skipBytes(2);
    Object localObject1;
    Object localObject2;
    Object localObject3;
    if ((j == 84) && (k == 88) && (m == 88) && (n == 88))
    {
      j = paramArrayOfByte.readUnsignedByte();
      localObject1 = getCharsetName(j);
      localObject2 = new byte[i - 1];
      paramArrayOfByte.readBytes((byte[])localObject2, 0, i - 1);
      k = indexOfEOS((byte[])localObject2, 0, j);
      localObject3 = new String((byte[])localObject2, 0, k, (String)localObject1);
      k += delimiterLength(j);
      localArrayList.add(new TxxxFrame((String)localObject3, new String((byte[])localObject2, k, indexOfEOS((byte[])localObject2, k, j) - k, (String)localObject1)));
    }
    for (;;)
    {
      paramInt -= i + 10;
      break;
      if ((j == 80) && (k == 82) && (m == 73) && (n == 86))
      {
        localObject1 = new byte[i];
        paramArrayOfByte.readBytes((byte[])localObject1, 0, i);
        j = indexOf((byte[])localObject1, 0, (byte)0);
        localObject2 = new String((byte[])localObject1, 0, j, "ISO-8859-1");
        localObject3 = new byte[i - j - 1];
        System.arraycopy(localObject1, j + 1, localObject3, 0, i - j - 1);
        localArrayList.add(new PrivFrame((String)localObject2, (byte[])localObject3));
      }
      else if ((j == 71) && (k == 69) && (m == 79) && (n == 66))
      {
        j = paramArrayOfByte.readUnsignedByte();
        String str = getCharsetName(j);
        localObject1 = new byte[i - 1];
        paramArrayOfByte.readBytes((byte[])localObject1, 0, i - 1);
        k = indexOf((byte[])localObject1, 0, (byte)0);
        localObject2 = new String((byte[])localObject1, 0, k, "ISO-8859-1");
        k += 1;
        m = indexOfEOS((byte[])localObject1, k, j);
        localObject3 = new String((byte[])localObject1, k, m - k, str);
        m += delimiterLength(j);
        k = indexOfEOS((byte[])localObject1, m, j);
        str = new String((byte[])localObject1, m, k - m, str);
        m = i - 1 - k - delimiterLength(j);
        byte[] arrayOfByte = new byte[m];
        System.arraycopy(localObject1, delimiterLength(j) + k, arrayOfByte, 0, m);
        localArrayList.add(new GeobFrame((String)localObject2, (String)localObject3, str, arrayOfByte));
      }
      else
      {
        localObject1 = String.format(Locale.US, "%c%c%c%c", new Object[] { Integer.valueOf(j), Integer.valueOf(k), Integer.valueOf(m), Integer.valueOf(n) });
        localObject2 = new byte[i];
        paramArrayOfByte.readBytes((byte[])localObject2, 0, i);
        localArrayList.add(new BinaryFrame((String)localObject1, (byte[])localObject2));
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/metadata/id3/Id3Parser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */