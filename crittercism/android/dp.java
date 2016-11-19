package crittercism.android;

import java.util.HashMap;
import java.util.Map;

public final class dp
{
  private static Map a;
  
  static
  {
    HashMap localHashMap = new HashMap();
    a = localHashMap;
    localHashMap.put("com.amazon.venezia", new do.a.a());
    a.put("com.android.vending", new do.b.a());
  }
  
  public static dn a(String paramString)
  {
    if ((paramString != null) && (a.containsKey(paramString))) {
      return (dn)a.get(paramString);
    }
    return null;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/dp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */