package org.apache.commons.io.output;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.io.input.ClosedInputStream;

public class ByteArrayOutputStream
  extends OutputStream
{
  private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  private final List<byte[]> buffers = new ArrayList();
  private int count;
  private byte[] currentBuffer;
  private int currentBufferIndex;
  private int filledBufferSum;
  
  public ByteArrayOutputStream()
  {
    this(1024);
  }
  
  public ByteArrayOutputStream(int paramInt)
  {
    if (paramInt < 0) {
      throw new IllegalArgumentException("Negative initial size: " + paramInt);
    }
    try
    {
      needNewBuffer(paramInt);
      return;
    }
    finally {}
  }
  
  private void needNewBuffer(int paramInt)
  {
    if (this.currentBufferIndex < this.buffers.size() - 1)
    {
      this.filledBufferSum += this.currentBuffer.length;
      this.currentBufferIndex += 1;
      this.currentBuffer = ((byte[])this.buffers.get(this.currentBufferIndex));
      return;
    }
    if (this.currentBuffer == null) {}
    for (this.filledBufferSum = 0;; this.filledBufferSum += this.currentBuffer.length)
    {
      this.currentBufferIndex += 1;
      this.currentBuffer = new byte[paramInt];
      this.buffers.add(this.currentBuffer);
      return;
      paramInt = Math.max(this.currentBuffer.length << 1, paramInt - this.filledBufferSum);
    }
  }
  
  private InputStream toBufferedInputStream()
  {
    int i = this.count;
    if (i == 0) {
      return new ClosedInputStream();
    }
    ArrayList localArrayList = new ArrayList(this.buffers.size());
    Iterator localIterator = this.buffers.iterator();
    int j;
    do
    {
      if (!localIterator.hasNext()) {
        break;
      }
      byte[] arrayOfByte = (byte[])localIterator.next();
      j = Math.min(arrayOfByte.length, i);
      localArrayList.add(new ByteArrayInputStream(arrayOfByte, 0, j));
      j = i - j;
      i = j;
    } while (j != 0);
    return new SequenceInputStream(Collections.enumeration(localArrayList));
  }
  
  public static InputStream toBufferedInputStream(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    localByteArrayOutputStream.write(paramInputStream);
    return localByteArrayOutputStream.toBufferedInputStream();
  }
  
  public void close()
    throws IOException
  {}
  
  public void reset()
  {
    try
    {
      this.count = 0;
      this.filledBufferSum = 0;
      this.currentBufferIndex = 0;
      this.currentBuffer = ((byte[])this.buffers.get(this.currentBufferIndex));
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int size()
  {
    try
    {
      int i = this.count;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  /* Error */
  public byte[] toByteArray()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 85	org/apache/commons/io/output/ByteArrayOutputStream:count	I
    //   6: istore_1
    //   7: iload_1
    //   8: ifne +13 -> 21
    //   11: getstatic 18	org/apache/commons/io/output/ByteArrayOutputStream:EMPTY_BYTE_ARRAY	[B
    //   14: astore 4
    //   16: aload_0
    //   17: monitorexit
    //   18: aload 4
    //   20: areturn
    //   21: iload_1
    //   22: newarray <illegal type>
    //   24: astore 5
    //   26: iconst_0
    //   27: istore_2
    //   28: aload_0
    //   29: getfield 30	org/apache/commons/io/output/ByteArrayOutputStream:buffers	Ljava/util/List;
    //   32: invokeinterface 93 1 0
    //   37: astore 6
    //   39: aload 5
    //   41: astore 4
    //   43: aload 6
    //   45: invokeinterface 99 1 0
    //   50: ifeq -34 -> 16
    //   53: aload 6
    //   55: invokeinterface 103 1 0
    //   60: checkcast 71	[B
    //   63: astore 4
    //   65: aload 4
    //   67: arraylength
    //   68: iload_1
    //   69: invokestatic 106	java/lang/Math:min	(II)I
    //   72: istore_3
    //   73: aload 4
    //   75: iconst_0
    //   76: aload 5
    //   78: iload_2
    //   79: iload_3
    //   80: invokestatic 143	java/lang/System:arraycopy	(Ljava/lang/Object;ILjava/lang/Object;II)V
    //   83: iload_2
    //   84: iload_3
    //   85: iadd
    //   86: istore_2
    //   87: iload_1
    //   88: iload_3
    //   89: isub
    //   90: istore_3
    //   91: iload_3
    //   92: istore_1
    //   93: iload_3
    //   94: ifne -55 -> 39
    //   97: aload 5
    //   99: astore 4
    //   101: goto -85 -> 16
    //   104: astore 4
    //   106: aload_0
    //   107: monitorexit
    //   108: aload 4
    //   110: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	111	0	this	ByteArrayOutputStream
    //   6	87	1	i	int
    //   27	60	2	j	int
    //   72	22	3	k	int
    //   14	86	4	localObject1	Object
    //   104	5	4	localObject2	Object
    //   24	74	5	arrayOfByte	byte[]
    //   37	17	6	localIterator	Iterator
    // Exception table:
    //   from	to	target	type
    //   2	7	104	finally
    //   11	16	104	finally
    //   21	26	104	finally
    //   28	39	104	finally
    //   43	83	104	finally
  }
  
  public String toString()
  {
    return new String(toByteArray());
  }
  
  public String toString(String paramString)
    throws UnsupportedEncodingException
  {
    return new String(toByteArray(), paramString);
  }
  
  public int write(InputStream paramInputStream)
    throws IOException
  {
    int k = 0;
    try
    {
      int j = this.count - this.filledBufferSum;
      int i = paramInputStream.read(this.currentBuffer, j, this.currentBuffer.length - j);
      while (i != -1)
      {
        int m = k + i;
        j += i;
        this.count += i;
        i = j;
        if (j == this.currentBuffer.length)
        {
          needNewBuffer(this.currentBuffer.length);
          i = 0;
        }
        k = paramInputStream.read(this.currentBuffer, i, this.currentBuffer.length - i);
        j = i;
        i = k;
        k = m;
      }
      return k;
    }
    finally {}
  }
  
  public void write(int paramInt)
  {
    try
    {
      int j = this.count - this.filledBufferSum;
      int i = j;
      if (j == this.currentBuffer.length)
      {
        needNewBuffer(this.count + 1);
        i = 0;
      }
      this.currentBuffer[i] = ((byte)paramInt);
      this.count += 1;
      return;
    }
    finally {}
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 > paramArrayOfByte.length) || (paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfByte.length) || (paramInt1 + paramInt2 < 0)) {
      throw new IndexOutOfBoundsException();
    }
    if (paramInt2 == 0) {
      return;
    }
    try
    {
      int m = this.count + paramInt2;
      int i = paramInt2;
      int j = this.count - this.filledBufferSum;
      while (i > 0)
      {
        int k = Math.min(i, this.currentBuffer.length - j);
        System.arraycopy(paramArrayOfByte, paramInt1 + paramInt2 - i, this.currentBuffer, j, k);
        k = i - k;
        i = k;
        if (k > 0)
        {
          needNewBuffer(m);
          j = 0;
          i = k;
        }
      }
      this.count = m;
      return;
    }
    finally {}
  }
  
  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    try
    {
      int i = this.count;
      Iterator localIterator = this.buffers.iterator();
      int j;
      do
      {
        if (!localIterator.hasNext()) {
          break;
        }
        byte[] arrayOfByte = (byte[])localIterator.next();
        j = Math.min(arrayOfByte.length, i);
        paramOutputStream.write(arrayOfByte, 0, j);
        j = i - j;
        i = j;
      } while (j != 0);
      return;
    }
    finally {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/output/ByteArrayOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */