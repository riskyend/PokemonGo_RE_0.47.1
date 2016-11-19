package org.apache.commons.io.input;

import java.io.Reader;
import java.io.Serializable;

public class CharSequenceReader
  extends Reader
  implements Serializable
{
  private final CharSequence charSequence;
  private int idx;
  private int mark;
  
  public CharSequenceReader(CharSequence paramCharSequence)
  {
    if (paramCharSequence != null) {}
    for (;;)
    {
      this.charSequence = paramCharSequence;
      return;
      paramCharSequence = "";
    }
  }
  
  public void close()
  {
    this.idx = 0;
    this.mark = 0;
  }
  
  public void mark(int paramInt)
  {
    this.mark = this.idx;
  }
  
  public boolean markSupported()
  {
    return true;
  }
  
  public int read()
  {
    if (this.idx >= this.charSequence.length()) {
      return -1;
    }
    CharSequence localCharSequence = this.charSequence;
    int i = this.idx;
    this.idx = (i + 1);
    return localCharSequence.charAt(i);
  }
  
  public int read(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    int k;
    if (this.idx >= this.charSequence.length())
    {
      k = -1;
      return k;
    }
    if (paramArrayOfChar == null) {
      throw new NullPointerException("Character array is missing");
    }
    if ((paramInt2 < 0) || (paramInt1 < 0) || (paramInt1 + paramInt2 > paramArrayOfChar.length)) {
      throw new IndexOutOfBoundsException("Array Size=" + paramArrayOfChar.length + ", offset=" + paramInt1 + ", length=" + paramInt2);
    }
    int i = 0;
    int j = 0;
    for (;;)
    {
      k = i;
      if (j >= paramInt2) {
        break;
      }
      int m = read();
      k = i;
      if (m == -1) {
        break;
      }
      paramArrayOfChar[(paramInt1 + j)] = ((char)m);
      i += 1;
      j += 1;
    }
  }
  
  public void reset()
  {
    this.idx = this.mark;
  }
  
  public long skip(long paramLong)
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("Number of characters to skip is less than zero: " + paramLong);
    }
    if (this.idx >= this.charSequence.length()) {
      return -1L;
    }
    int i = (int)Math.min(this.charSequence.length(), this.idx + paramLong);
    int j = this.idx;
    this.idx = i;
    return i - j;
  }
  
  public String toString()
  {
    return this.charSequence.toString();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/input/CharSequenceReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */