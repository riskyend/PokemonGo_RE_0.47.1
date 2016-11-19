package crittercism.android;

import android.content.Context;
import android.content.SharedPreferences;
import org.json.JSONException;
import org.json.JSONObject;

public final class bh
{
  public boolean a = false;
  public int b = 10;
  public int c = 3600000;
  public JSONObject d = new JSONObject();
  
  bh() {}
  
  public bh(JSONObject paramJSONObject)
  {
    this.a = paramJSONObject.optBoolean("enabled", false);
    this.b = paramJSONObject.optInt("interval", 10);
    this.c = paramJSONObject.optInt("defaultTimeout", 3600000);
    this.d = paramJSONObject.optJSONObject("transactions");
    if (this.d == null) {
      this.d = new JSONObject();
    }
  }
  
  public static bh a(Context paramContext)
  {
    Object localObject = paramContext.getSharedPreferences("com.crittercism.txn.config", 0);
    paramContext = new bh();
    paramContext.a = ((SharedPreferences)localObject).getBoolean("enabled", false);
    paramContext.b = ((SharedPreferences)localObject).getInt("interval", 10);
    paramContext.c = ((SharedPreferences)localObject).getInt("defaultTimeout", 3600000);
    localObject = ((SharedPreferences)localObject).getString("transactions", null);
    paramContext.d = new JSONObject();
    if (localObject != null) {}
    try
    {
      paramContext.d = new JSONObject((String)localObject);
      return paramContext;
    }
    catch (JSONException localJSONException) {}
    return paramContext;
  }
  
  public final long a(String paramString)
  {
    paramString = this.d.optJSONObject(paramString);
    if (paramString != null) {
      return paramString.optLong("timeout", this.c);
    }
    return this.c;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/bh.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */