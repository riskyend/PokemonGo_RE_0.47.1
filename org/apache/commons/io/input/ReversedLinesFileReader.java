package org.apache.commons.io.input;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import org.apache.commons.io.Charsets;

public class ReversedLinesFileReader
  implements Closeable
{
  private final int avoidNewlineSplitBufferSize;
  private final int blockSize;
  private final int byteDecrement;
  private FilePart currentFilePart;
  private final Charset encoding;
  private final byte[][] newLineSequences;
  private final RandomAccessFile randomAccessFile;
  private final long totalBlockCount;
  private final long totalByteLength;
  private boolean trailingNewlineOfFileSkipped = false;
  
  public ReversedLinesFileReader(File paramFile)
    throws IOException
  {
    this(paramFile, 4096, Charset.defaultCharset().toString());
  }
  
  public ReversedLinesFileReader(File paramFile, int paramInt, String paramString)
    throws IOException
  {
    this(paramFile, paramInt, Charsets.toCharset(paramString));
  }
  
  public ReversedLinesFileReader(File paramFile, int paramInt, Charset paramCharset)
    throws IOException
  {
    this.blockSize = paramInt;
    this.encoding = paramCharset;
    this.randomAccessFile = new RandomAccessFile(paramFile, "r");
    this.totalByteLength = this.randomAccessFile.length();
    int i = (int)(this.totalByteLength % paramInt);
    if (i > 0)
    {
      this.totalBlockCount = (this.totalByteLength / paramInt + 1L);
      this.currentFilePart = new FilePart(this.totalBlockCount, i, null, null);
      paramFile = Charsets.toCharset(paramCharset);
      if (paramFile.newEncoder().maxBytesPerChar() != 1.0F) {
        break label187;
      }
      this.byteDecrement = 1;
    }
    for (;;)
    {
      this.newLineSequences = new byte[][] { "\r\n".getBytes(paramCharset), "\n".getBytes(paramCharset), "\r".getBytes(paramCharset) };
      this.avoidNewlineSplitBufferSize = this.newLineSequences[0].length;
      return;
      this.totalBlockCount = (this.totalByteLength / paramInt);
      if (this.totalByteLength <= 0L) {
        break;
      }
      i = paramInt;
      break;
      label187:
      if (paramFile == Charset.forName("UTF-8"))
      {
        this.byteDecrement = 1;
      }
      else if (paramFile == Charset.forName("Shift_JIS"))
      {
        this.byteDecrement = 1;
      }
      else
      {
        if ((paramFile != Charset.forName("UTF-16BE")) && (paramFile != Charset.forName("UTF-16LE"))) {
          break label247;
        }
        this.byteDecrement = 2;
      }
    }
    label247:
    if (paramFile == Charset.forName("UTF-16")) {
      throw new UnsupportedEncodingException("For UTF-16, you need to specify the byte order (use UTF-16BE or UTF-16LE)");
    }
    throw new UnsupportedEncodingException("Encoding " + paramCharset + " is not supported yet (feel free to submit a patch)");
  }
  
  public void close()
    throws IOException
  {
    this.randomAccessFile.close();
  }
  
  public String readLine()
    throws IOException
  {
    for (String str1 = this.currentFilePart.readLine(); str1 == null; str1 = this.currentFilePart.readLine())
    {
      this.currentFilePart = this.currentFilePart.rollOver();
      if (this.currentFilePart == null) {
        break;
      }
    }
    String str2 = str1;
    if ("".equals(str1))
    {
      str2 = str1;
      if (!this.trailingNewlineOfFileSkipped)
      {
        this.trailingNewlineOfFileSkipped = true;
        str2 = readLine();
      }
    }
    return str2;
  }
  
  private class FilePart
  {
    private int currentLastBytePos;
    private final byte[] data;
    private byte[] leftOver;
    private final long no;
    
    private FilePart(long paramLong, int paramInt, byte[] paramArrayOfByte)
      throws IOException
    {
      this.no = paramLong;
      if (paramArrayOfByte != null) {}
      for (int i = paramArrayOfByte.length;; i = 0)
      {
        this.data = new byte[paramInt + i];
        long l = ReversedLinesFileReader.this.blockSize;
        if (paramLong <= 0L) {
          break;
        }
        ReversedLinesFileReader.this.randomAccessFile.seek((paramLong - 1L) * l);
        if (ReversedLinesFileReader.this.randomAccessFile.read(this.data, 0, paramInt) == paramInt) {
          break;
        }
        throw new IllegalStateException("Count of requested bytes and actually read bytes don't match");
      }
      if (paramArrayOfByte != null) {
        System.arraycopy(paramArrayOfByte, 0, this.data, paramInt, paramArrayOfByte.length);
      }
      this.currentLastBytePos = (this.data.length - 1);
      this.leftOver = null;
    }
    
    private void createLeftOver()
    {
      int i = this.currentLastBytePos + 1;
      if (i > 0)
      {
        this.leftOver = new byte[i];
        System.arraycopy(this.data, 0, this.leftOver, 0, i);
      }
      for (;;)
      {
        this.currentLastBytePos = -1;
        return;
        this.leftOver = null;
      }
    }
    
    private int getNewLineMatchByteCount(byte[] paramArrayOfByte, int paramInt)
    {
      int n = 0;
      byte[][] arrayOfByte = ReversedLinesFileReader.this.newLineSequences;
      int i1 = arrayOfByte.length;
      int i = 0;
      for (;;)
      {
        int j = n;
        if (i < i1)
        {
          byte[] arrayOfByte1 = arrayOfByte[i];
          int k = 1;
          j = arrayOfByte1.length - 1;
          if (j >= 0)
          {
            int m = paramInt + j - (arrayOfByte1.length - 1);
            if ((m >= 0) && (paramArrayOfByte[m] == arrayOfByte1[j])) {}
            for (m = 1;; m = 0)
            {
              k &= m;
              j -= 1;
              break;
            }
          }
          if (k != 0) {
            j = arrayOfByte1.length;
          }
        }
        else
        {
          return j;
        }
        i += 1;
      }
    }
    
    private String readLine()
      throws IOException
    {
      Object localObject2 = null;
      int i;
      int j;
      label19:
      Object localObject1;
      if (this.no == 1L)
      {
        i = 1;
        j = this.currentLastBytePos;
        localObject1 = localObject2;
        if (j > -1)
        {
          if ((i != 0) || (j >= ReversedLinesFileReader.this.avoidNewlineSplitBufferSize)) {
            break label103;
          }
          createLeftOver();
          localObject1 = localObject2;
        }
      }
      for (;;)
      {
        localObject2 = localObject1;
        if (i != 0)
        {
          localObject2 = localObject1;
          if (this.leftOver != null)
          {
            localObject2 = new String(this.leftOver, ReversedLinesFileReader.this.encoding);
            this.leftOver = null;
          }
        }
        return (String)localObject2;
        i = 0;
        break;
        label103:
        int k = getNewLineMatchByteCount(this.data, j);
        if (k > 0)
        {
          int m = j + 1;
          int n = this.currentLastBytePos - m + 1;
          if (n < 0) {
            throw new IllegalStateException("Unexpected negative line length=" + n);
          }
          localObject1 = new byte[n];
          System.arraycopy(this.data, m, localObject1, 0, n);
          localObject1 = new String((byte[])localObject1, ReversedLinesFileReader.this.encoding);
          this.currentLastBytePos = (j - k);
        }
        else
        {
          k = j - ReversedLinesFileReader.this.byteDecrement;
          j = k;
          if (k >= 0) {
            break label19;
          }
          createLeftOver();
          localObject1 = localObject2;
        }
      }
    }
    
    private FilePart rollOver()
      throws IOException
    {
      if (this.currentLastBytePos > -1) {
        throw new IllegalStateException("Current currentLastCharPos unexpectedly positive... last readLine() should have returned something! currentLastCharPos=" + this.currentLastBytePos);
      }
      if (this.no > 1L) {
        return new FilePart(ReversedLinesFileReader.this, this.no - 1L, ReversedLinesFileReader.this.blockSize, this.leftOver);
      }
      if (this.leftOver != null) {
        throw new IllegalStateException("Unexpected leftover of the last block: leftOverOfThisFilePart=" + new String(this.leftOver, ReversedLinesFileReader.this.encoding));
      }
      return null;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/input/ReversedLinesFileReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */