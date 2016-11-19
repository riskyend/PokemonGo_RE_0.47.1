package com.google.android.exoplayer.extractor;

public abstract interface SeekMap
{
  public static final SeekMap UNSEEKABLE = new SeekMap()
  {
    public long getPosition(long paramAnonymousLong)
    {
      return 0L;
    }
    
    public boolean isSeekable()
    {
      return false;
    }
  };
  
  public abstract long getPosition(long paramLong);
  
  public abstract boolean isSeekable();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/extractor/SeekMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */