package com.google.android.exoplayer;

public final class CodecCounters
{
  public int codecInitCount;
  public int codecReleaseCount;
  public int droppedOutputBufferCount;
  public int maxConsecutiveDroppedOutputBufferCount;
  public int outputBuffersChangedCount;
  public int outputFormatChangedCount;
  public int renderedOutputBufferCount;
  public int skippedOutputBufferCount;
  
  public void ensureUpdated() {}
  
  public String getDebugString()
  {
    ensureUpdated();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("cic:").append(this.codecInitCount);
    localStringBuilder.append(" crc:").append(this.codecReleaseCount);
    localStringBuilder.append(" ofc:").append(this.outputFormatChangedCount);
    localStringBuilder.append(" obc:").append(this.outputBuffersChangedCount);
    localStringBuilder.append(" ren:").append(this.renderedOutputBufferCount);
    localStringBuilder.append(" sob:").append(this.skippedOutputBufferCount);
    localStringBuilder.append(" dob:").append(this.droppedOutputBufferCount);
    localStringBuilder.append(" mcdob:").append(this.maxConsecutiveDroppedOutputBufferCount);
    return localStringBuilder.toString();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/CodecCounters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */