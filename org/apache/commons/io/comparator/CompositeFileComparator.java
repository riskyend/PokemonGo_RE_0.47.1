package org.apache.commons.io.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class CompositeFileComparator
  extends AbstractFileComparator
  implements Serializable
{
  private static final Comparator<?>[] NO_COMPARATORS = new Comparator[0];
  private final Comparator<File>[] delegates;
  
  public CompositeFileComparator(Iterable<Comparator<File>> paramIterable)
  {
    if (paramIterable == null)
    {
      this.delegates = ((Comparator[])NO_COMPARATORS);
      return;
    }
    ArrayList localArrayList = new ArrayList();
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext()) {
      localArrayList.add((Comparator)paramIterable.next());
    }
    this.delegates = ((Comparator[])localArrayList.toArray(new Comparator[localArrayList.size()]));
  }
  
  public CompositeFileComparator(Comparator<File>... paramVarArgs)
  {
    if (paramVarArgs == null)
    {
      this.delegates = ((Comparator[])NO_COMPARATORS);
      return;
    }
    this.delegates = ((Comparator[])new Comparator[paramVarArgs.length]);
    System.arraycopy(paramVarArgs, 0, this.delegates, 0, paramVarArgs.length);
  }
  
  public int compare(File paramFile1, File paramFile2)
  {
    int i = 0;
    Comparator[] arrayOfComparator = this.delegates;
    int k = arrayOfComparator.length;
    int j = 0;
    for (;;)
    {
      if (j < k)
      {
        i = arrayOfComparator[j].compare(paramFile1, paramFile2);
        if (i == 0) {}
      }
      else
      {
        return i;
      }
      j += 1;
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(super.toString());
    localStringBuilder.append('{');
    int i = 0;
    while (i < this.delegates.length)
    {
      if (i > 0) {
        localStringBuilder.append(',');
      }
      localStringBuilder.append(this.delegates[i]);
      i += 1;
    }
    localStringBuilder.append('}');
    return localStringBuilder.toString();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/comparator/CompositeFileComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */