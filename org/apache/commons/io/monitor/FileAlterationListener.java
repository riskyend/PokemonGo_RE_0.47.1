package org.apache.commons.io.monitor;

import java.io.File;

public abstract interface FileAlterationListener
{
  public abstract void onDirectoryChange(File paramFile);
  
  public abstract void onDirectoryCreate(File paramFile);
  
  public abstract void onDirectoryDelete(File paramFile);
  
  public abstract void onFileChange(File paramFile);
  
  public abstract void onFileCreate(File paramFile);
  
  public abstract void onFileDelete(File paramFile);
  
  public abstract void onStart(FileAlterationObserver paramFileAlterationObserver);
  
  public abstract void onStop(FileAlterationObserver paramFileAlterationObserver);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/monitor/FileAlterationListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */