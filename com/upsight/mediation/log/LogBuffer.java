package com.upsight.mediation.log;

import java.util.ArrayList;

public class LogBuffer
{
  public final String[] buffer;
  public final int bufferSize;
  public int end;
  public final int msgLength;
  
  public LogBuffer(int paramInt1, int paramInt2)
  {
    if (paramInt1 <= 0) {
      throw new IllegalArgumentException("Buffer size must be greater than 0");
    }
    if (paramInt2 <= 0) {
      throw new IllegalArgumentException("Message length must be greater than 0");
    }
    this.bufferSize = paramInt1;
    this.buffer = new String[paramInt1];
    this.msgLength = paramInt2;
    this.end = -1;
  }
  
  public void append(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Value may not be null");
    }
    int i = this.end + 1;
    this.end = i;
    if (i >= this.bufferSize) {
      this.end = 0;
    }
    if (paramString.length() > this.msgLength)
    {
      this.buffer[this.end] = paramString.substring(0, this.msgLength);
      return;
    }
    this.buffer[this.end] = paramString;
  }
  
  public void append(String paramString1, String paramString2, String paramString3)
  {
    append(paramString1 + "," + paramString2 + "," + paramString3);
  }
  
  public String[] getLog()
  {
    int j = getStartIndex();
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    for (;;)
    {
      String str;
      if (i < this.bufferSize)
      {
        int k = this.bufferSize;
        str = this.buffer[((j + i) % k)];
        if (str != null) {}
      }
      else
      {
        return (String[])localArrayList.toArray(new String[localArrayList.size()]);
      }
      localArrayList.add(str);
      i += 1;
    }
  }
  
  public int getStartIndex()
  {
    if (this.buffer[(this.buffer.length - 1)] == null) {}
    for (int i = 0;; i = this.end + 1)
    {
      int j = i;
      if (i >= this.bufferSize) {
        j = 0;
      }
      return j;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/log/LogBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */