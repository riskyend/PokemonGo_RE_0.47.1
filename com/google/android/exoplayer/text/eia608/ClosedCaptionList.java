package com.google.android.exoplayer.text.eia608;

final class ClosedCaptionList
  implements Comparable<ClosedCaptionList>
{
  public final ClosedCaption[] captions;
  public final boolean decodeOnly;
  public final long timeUs;
  
  public ClosedCaptionList(long paramLong, boolean paramBoolean, ClosedCaption[] paramArrayOfClosedCaption)
  {
    this.timeUs = paramLong;
    this.decodeOnly = paramBoolean;
    this.captions = paramArrayOfClosedCaption;
  }
  
  public int compareTo(ClosedCaptionList paramClosedCaptionList)
  {
    long l = this.timeUs - paramClosedCaptionList.timeUs;
    if (l == 0L) {
      return 0;
    }
    if (l > 0L) {
      return 1;
    }
    return -1;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/text/eia608/ClosedCaptionList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */