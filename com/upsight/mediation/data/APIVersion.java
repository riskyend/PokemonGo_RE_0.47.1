package com.upsight.mediation.data;

import com.upsight.mediation.util.StringUtil;
import java.util.Arrays;

public class APIVersion
  implements Comparable
{
  public final int[] versionComponents;
  
  public APIVersion(String paramString)
  {
    this.versionComponents = StringUtil.toIntArray(paramString, "\\.");
  }
  
  public APIVersion(int... paramVarArgs)
  {
    if ((paramVarArgs == null) || (paramVarArgs.length == 0)) {
      throw new IllegalArgumentException("At least one version component must be specified");
    }
    this.versionComponents = paramVarArgs;
  }
  
  public int compareTo(Object paramObject)
  {
    APIVersion localAPIVersion = (APIVersion)paramObject;
    int k = 0;
    int j = k;
    int i;
    if (!equals(localAPIVersion)) {
      i = 0;
    }
    for (;;)
    {
      j = k;
      int m;
      if (i < this.versionComponents.length)
      {
        j = getVersionComponent(i);
        m = ((APIVersion)paramObject).getVersionComponent(i);
        if (j > m) {
          j = i;
        }
      }
      else
      {
        return j;
      }
      if (j < m) {
        return -i;
      }
      i += 1;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    paramObject = (APIVersion)paramObject;
    return Arrays.equals(this.versionComponents, ((APIVersion)paramObject).versionComponents);
  }
  
  public int getVersionComponent(int paramInt)
  {
    if (this.versionComponents.length > paramInt) {
      return this.versionComponents[paramInt];
    }
    return 0;
  }
  
  public String toString()
  {
    return StringUtil.join(this.versionComponents, ".");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/data/APIVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */