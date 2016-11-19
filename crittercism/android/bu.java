package crittercism.android;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public final class bu
  implements bv
{
  private Map a = new HashMap();
  
  public final bu a(bw parambw)
  {
    if (parambw.b() != null) {
      this.a.put(parambw.a(), parambw.b());
    }
    return this;
  }
  
  public final JSONObject a()
  {
    return new JSONObject(this.a);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/bu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */