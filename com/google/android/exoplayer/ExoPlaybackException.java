package com.google.android.exoplayer;

public final class ExoPlaybackException
  extends Exception
{
  public final boolean caughtAtTopLevel;
  
  public ExoPlaybackException(String paramString)
  {
    super(paramString);
    this.caughtAtTopLevel = false;
  }
  
  public ExoPlaybackException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
    this.caughtAtTopLevel = false;
  }
  
  public ExoPlaybackException(Throwable paramThrowable)
  {
    super(paramThrowable);
    this.caughtAtTopLevel = false;
  }
  
  ExoPlaybackException(Throwable paramThrowable, boolean paramBoolean)
  {
    super(paramThrowable);
    this.caughtAtTopLevel = paramBoolean;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/ExoPlaybackException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */