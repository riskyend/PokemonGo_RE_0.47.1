package org.apache.commons.io.comparator;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

abstract class AbstractFileComparator
  implements Comparator<File>
{
  public List<File> sort(List<File> paramList)
  {
    if (paramList != null) {
      Collections.sort(paramList, this);
    }
    return paramList;
  }
  
  public File[] sort(File... paramVarArgs)
  {
    if (paramVarArgs != null) {
      Arrays.sort(paramVarArgs, this);
    }
    return paramVarArgs;
  }
  
  public String toString()
  {
    return getClass().getSimpleName();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/comparator/AbstractFileComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */