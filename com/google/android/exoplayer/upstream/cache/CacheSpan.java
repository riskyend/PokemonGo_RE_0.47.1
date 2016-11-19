package com.google.android.exoplayer.upstream.cache;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CacheSpan
  implements Comparable<CacheSpan>
{
  private static final String SUFFIX = ".v1.exo";
  private static final String SUFFIX_ESCAPED = "\\.v1\\.exo";
  private static final Pattern cacheFilePattern = Pattern.compile("^(.+)\\.(\\d+)\\.(\\d+)(\\.v1\\.exo)$");
  public final File file;
  public final boolean isCached;
  public final String key;
  public final long lastAccessTimestamp;
  public final long length;
  public final long position;
  
  CacheSpan(String paramString, long paramLong1, long paramLong2, boolean paramBoolean, long paramLong3, File paramFile)
  {
    this.key = paramString;
    this.position = paramLong1;
    this.length = paramLong2;
    this.isCached = paramBoolean;
    this.file = paramFile;
    this.lastAccessTimestamp = paramLong3;
  }
  
  public static CacheSpan createCacheEntry(File paramFile)
  {
    Matcher localMatcher = cacheFilePattern.matcher(paramFile.getName());
    if (!localMatcher.matches()) {
      return null;
    }
    return createCacheEntry(localMatcher.group(1), Long.parseLong(localMatcher.group(2)), Long.parseLong(localMatcher.group(3)), paramFile);
  }
  
  private static CacheSpan createCacheEntry(String paramString, long paramLong1, long paramLong2, File paramFile)
  {
    return new CacheSpan(paramString, paramLong1, paramFile.length(), true, paramLong2, paramFile);
  }
  
  public static CacheSpan createClosedHole(String paramString, long paramLong1, long paramLong2)
  {
    return new CacheSpan(paramString, paramLong1, paramLong2, false, -1L, null);
  }
  
  public static CacheSpan createLookup(String paramString, long paramLong)
  {
    return new CacheSpan(paramString, paramLong, -1L, false, -1L, null);
  }
  
  public static CacheSpan createOpenHole(String paramString, long paramLong)
  {
    return new CacheSpan(paramString, paramLong, -1L, false, -1L, null);
  }
  
  public static File getCacheFileName(File paramFile, String paramString, long paramLong1, long paramLong2)
  {
    return new File(paramFile, paramString + "." + paramLong1 + "." + paramLong2 + ".v1.exo");
  }
  
  public int compareTo(CacheSpan paramCacheSpan)
  {
    if (!this.key.equals(paramCacheSpan.key)) {
      return this.key.compareTo(paramCacheSpan.key);
    }
    long l = this.position - paramCacheSpan.position;
    if (l == 0L) {
      return 0;
    }
    if (l < 0L) {
      return -1;
    }
    return 1;
  }
  
  public boolean isOpenEnded()
  {
    return this.length == -1L;
  }
  
  public CacheSpan touch()
  {
    long l = System.currentTimeMillis();
    File localFile = getCacheFileName(this.file.getParentFile(), this.key, this.position, l);
    this.file.renameTo(localFile);
    return createCacheEntry(this.key, this.position, l, localFile);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/upstream/cache/CacheSpan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */