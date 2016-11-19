package crittercism.android;

import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;

public final class bo
{
  public JSONArray a = new JSONArray();
  
  public bo(bs parambs)
  {
    parambs = parambs.c().iterator();
    while (parambs.hasNext())
    {
      Object localObject = ((bq)parambs.next()).a();
      if (localObject != null) {
        this.a.put(localObject);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/bo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */