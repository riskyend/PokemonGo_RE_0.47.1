package crittercism.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import org.json.JSONException;
import org.json.JSONObject;

public final class dk
  extends di
{
  private ax a;
  private final boolean b;
  private Context c;
  
  public dk(Context paramContext, ax paramax, boolean paramBoolean)
  {
    this.a = paramax;
    this.b = paramBoolean;
    this.c = paramContext;
  }
  
  public final void a()
  {
    new StringBuilder("Setting opt out status to ").append(this.b).append(".  This will take effect in the next user session.");
    dx.b();
    boolean bool = this.b;
    Object localObject = this.a;
    String str1 = cq.i.a();
    String str2 = cq.i.b();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("optOutStatus", bool).put("optOutStatusSet", true);
      ((ax)localObject).a(str1, str2, localJSONObject.toString());
      if (this.b)
      {
        localObject = this.c.getSharedPreferences("com.crittercism.optmz.config", 0).edit();
        ((SharedPreferences.Editor)localObject).clear();
        ((SharedPreferences.Editor)localObject).commit();
        h.b(this.c);
      }
      return;
    }
    catch (JSONException localJSONException)
    {
      for (;;) {}
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/dk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */