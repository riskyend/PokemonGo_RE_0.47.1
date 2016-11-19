package com.google.android.exoplayer.drm;

public final class UnsupportedDrmException
  extends Exception
{
  public static final int REASON_INSTANTIATION_ERROR = 2;
  public static final int REASON_UNSUPPORTED_SCHEME = 1;
  public final int reason;
  
  public UnsupportedDrmException(int paramInt)
  {
    this.reason = paramInt;
  }
  
  public UnsupportedDrmException(int paramInt, Exception paramException)
  {
    super(paramException);
    this.reason = paramInt;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/drm/UnsupportedDrmException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */