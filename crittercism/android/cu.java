package crittercism.android;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class cu
  implements cw
{
  public Map a = new HashMap();
  
  public cu(au paramau)
  {
    this.a.put("app_id", paramau.a());
    this.a.put("hashed_device_id", paramau.c());
    this.a.put("library_version", "5.0.8");
  }
  
  public final cu a(String paramString1, String paramString2)
  {
    this.a.put(paramString1, paramString2);
    return this;
  }
  
  public final cu a(String paramString, JSONArray paramJSONArray)
  {
    this.a.put(paramString, paramJSONArray);
    return this;
  }
  
  public final void a(OutputStream paramOutputStream)
  {
    dx.b();
    paramOutputStream.write(new JSONObject(this.a).toString().getBytes("UTF8"));
  }
  
  public final String toString()
  {
    try
    {
      String str = new JSONObject(this.a).toString(4);
      return str;
    }
    catch (JSONException localJSONException)
    {
      dx.a();
    }
    return null;
  }
  
  public static final class a
    implements cx
  {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/cu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */