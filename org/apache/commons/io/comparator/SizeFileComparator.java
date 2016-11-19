package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import org.apache.commons.io.FileUtils;

public class SizeFileComparator
  extends AbstractFileComparator
  implements Serializable
{
  public static final Comparator<File> SIZE_COMPARATOR = new SizeFileComparator();
  public static final Comparator<File> SIZE_REVERSE = new ReverseComparator(SIZE_COMPARATOR);
  public static final Comparator<File> SIZE_SUMDIR_COMPARATOR = new SizeFileComparator(true);
  public static final Comparator<File> SIZE_SUMDIR_REVERSE = new ReverseComparator(SIZE_SUMDIR_COMPARATOR);
  private final boolean sumDirectoryContents;
  
  public SizeFileComparator()
  {
    this.sumDirectoryContents = false;
  }
  
  public SizeFileComparator(boolean paramBoolean)
  {
    this.sumDirectoryContents = paramBoolean;
  }
  
  public int compare(File paramFile1, File paramFile2)
  {
    long l1;
    long l2;
    if (paramFile1.isDirectory()) {
      if ((this.sumDirectoryContents) && (paramFile1.exists()))
      {
        l1 = FileUtils.sizeOfDirectory(paramFile1);
        if (!paramFile2.isDirectory()) {
          break label85;
        }
        if ((!this.sumDirectoryContents) || (!paramFile2.exists())) {
          break label79;
        }
        l2 = FileUtils.sizeOfDirectory(paramFile2);
      }
    }
    for (;;)
    {
      l1 -= l2;
      if (l1 >= 0L) {
        break label94;
      }
      return -1;
      l1 = 0L;
      break;
      l1 = paramFile1.length();
      break;
      label79:
      l2 = 0L;
      continue;
      label85:
      l2 = paramFile2.length();
    }
    label94:
    if (l1 > 0L) {
      return 1;
    }
    return 0;
  }
  
  public String toString()
  {
    return super.toString() + "[sumDirectoryContents=" + this.sumDirectoryContents + "]";
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/comparator/SizeFileComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */