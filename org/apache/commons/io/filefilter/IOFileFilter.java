package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public abstract interface IOFileFilter
  extends FileFilter, FilenameFilter
{
  public abstract boolean accept(File paramFile);
  
  public abstract boolean accept(File paramFile, String paramString);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/filefilter/IOFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */