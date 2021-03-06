package com.google.android.exoplayer.upstream.cache;

import java.io.File;
import java.util.NavigableSet;
import java.util.Set;

public abstract interface Cache
{
  public abstract NavigableSet<CacheSpan> addListener(String paramString, Listener paramListener);
  
  public abstract void commitFile(File paramFile);
  
  public abstract long getCacheSpace();
  
  public abstract NavigableSet<CacheSpan> getCachedSpans(String paramString);
  
  public abstract Set<String> getKeys();
  
  public abstract boolean isCached(String paramString, long paramLong1, long paramLong2);
  
  public abstract void releaseHoleSpan(CacheSpan paramCacheSpan);
  
  public abstract void removeListener(String paramString, Listener paramListener);
  
  public abstract void removeSpan(CacheSpan paramCacheSpan);
  
  public abstract File startFile(String paramString, long paramLong1, long paramLong2);
  
  public abstract CacheSpan startReadWrite(String paramString, long paramLong)
    throws InterruptedException;
  
  public abstract CacheSpan startReadWriteNonBlocking(String paramString, long paramLong);
  
  public static abstract interface Listener
  {
    public abstract void onSpanAdded(Cache paramCache, CacheSpan paramCacheSpan);
    
    public abstract void onSpanRemoved(Cache paramCache, CacheSpan paramCacheSpan);
    
    public abstract void onSpanTouched(Cache paramCache, CacheSpan paramCacheSpan1, CacheSpan paramCacheSpan2);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/upstream/cache/Cache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */