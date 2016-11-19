package crittercism.android;

import java.util.List;
import java.util.Map;

public abstract class n
{
  Map a;
  
  public n(Map paramMap)
  {
    this.a = paramMap;
  }
  
  private String c(String paramString)
  {
    paramString = (List)this.a.get(paramString);
    if (paramString != null) {
      return (String)paramString.get(paramString.size() - 1);
    }
    return null;
  }
  
  public final long a(String paramString)
  {
    long l = Long.MAX_VALUE;
    paramString = c(paramString);
    if (paramString != null) {}
    try
    {
      l = Long.parseLong(paramString);
      return l;
    }
    catch (NumberFormatException paramString) {}
    return Long.MAX_VALUE;
  }
  
  public final int b(String paramString)
  {
    int i = -1;
    paramString = c(paramString);
    if (paramString != null) {}
    try
    {
      i = Integer.parseInt(paramString);
      return i;
    }
    catch (NumberFormatException paramString) {}
    return -1;
  }
  
  public final String toString()
  {
    return this.a.toString();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/n.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */