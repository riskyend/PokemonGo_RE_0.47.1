package org.apache.commons.io;

import java.io.File;

@Deprecated
public class FileCleaner
{
  static final FileCleaningTracker theInstance = new FileCleaningTracker();
  
  @Deprecated
  public static void exitWhenFinished()
  {
    try
    {
      theInstance.exitWhenFinished();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static FileCleaningTracker getInstance()
  {
    return theInstance;
  }
  
  @Deprecated
  public static int getTrackCount()
  {
    return theInstance.getTrackCount();
  }
  
  @Deprecated
  public static void track(File paramFile, Object paramObject)
  {
    theInstance.track(paramFile, paramObject);
  }
  
  @Deprecated
  public static void track(File paramFile, Object paramObject, FileDeleteStrategy paramFileDeleteStrategy)
  {
    theInstance.track(paramFile, paramObject, paramFileDeleteStrategy);
  }
  
  @Deprecated
  public static void track(String paramString, Object paramObject)
  {
    theInstance.track(paramString, paramObject);
  }
  
  @Deprecated
  public static void track(String paramString, Object paramObject, FileDeleteStrategy paramFileDeleteStrategy)
  {
    theInstance.track(paramString, paramObject, paramFileDeleteStrategy);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/FileCleaner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */