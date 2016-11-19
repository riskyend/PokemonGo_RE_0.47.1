package com.google.android.exoplayer.text.eia608;

abstract class ClosedCaption
{
  public static final int TYPE_CTRL = 0;
  public static final int TYPE_TEXT = 1;
  public final int type;
  
  protected ClosedCaption(int paramInt)
  {
    this.type = paramInt;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/text/eia608/ClosedCaption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */