package crittercism.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.io.File;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public final class ct
  extends da
{
  private au a;
  private Context b;
  private String c;
  private JSONObject d;
  private JSONObject e;
  private boolean f;
  
  public ct(bs parambs1, bs parambs2, String paramString, Context paramContext, au paramau)
  {
    super(parambs1, parambs2);
    this.c = paramString;
    this.b = paramContext;
    this.a = paramau;
  }
  
  public final void a(boolean paramBoolean, int paramInt, JSONObject paramJSONObject)
  {
    super.a(paramBoolean, paramInt, paramJSONObject);
    if (paramJSONObject != null)
    {
      if (!paramJSONObject.optBoolean("internalExceptionReporting", false)) {
        break label421;
      }
      dx.a = dx.a.b;
      i.d();
    }
    for (;;)
    {
      Object localObject1 = this.a.m();
      Object localObject2;
      if (localObject1 != null)
      {
        localObject2 = paramJSONObject.optJSONObject("rateMyApp");
        if (localObject2 == null) {
          ((dt)localObject1).a(false);
        }
      }
      else if (paramJSONObject.optInt("needPkg", 0) != 1) {}
      try
      {
        new dj(new cu(this.a).a("device_name", this.a.i()).a("pkg", this.b.getPackageName()), new dc(new db(this.c, "/android_v2/update_package_name").a()), null).run();
        this.f = true;
        this.d = paramJSONObject.optJSONObject("apm");
        if (this.d != null)
        {
          localObject1 = new h(this.d);
          localObject2 = this.b;
          if (((h)localObject1).c)
          {
            h.b((Context)localObject2);
            localObject2 = ((Context)localObject2).getSharedPreferences("com.crittercism.optmz.config", 0).edit();
            if (!((h)localObject1).b) {
              break label665;
            }
            ((SharedPreferences.Editor)localObject2).putBoolean("enabled", ((h)localObject1).a);
            ((SharedPreferences.Editor)localObject2).putBoolean("kill", ((h)localObject1).c);
            ((SharedPreferences.Editor)localObject2).putBoolean("persist", ((h)localObject1).b);
            ((SharedPreferences.Editor)localObject2).putInt("interval", ((h)localObject1).d);
            ((SharedPreferences.Editor)localObject2).commit();
            az.A().a((h)localObject1);
          }
        }
        else
        {
          this.e = paramJSONObject.optJSONObject("txnConfig");
          if (this.e != null)
          {
            paramJSONObject = new bh(this.e);
            localObject1 = this.b.getSharedPreferences("com.crittercism.txn.config", 0).edit();
            ((SharedPreferences.Editor)localObject1).putBoolean("enabled", paramJSONObject.a);
            ((SharedPreferences.Editor)localObject1).putInt("interval", paramJSONObject.b);
            ((SharedPreferences.Editor)localObject1).putInt("defaultTimeout", paramJSONObject.c);
            ((SharedPreferences.Editor)localObject1).putString("transactions", paramJSONObject.d.toString());
            ((SharedPreferences.Editor)localObject1).commit();
            az.A().a(paramJSONObject);
          }
          return;
          label421:
          dx.a = dx.a.c;
          continue;
          try
          {
            int i = ((JSONObject)localObject2).getInt("rateAfterLoadNum");
            paramInt = i;
            if (i < 0) {
              paramInt = 0;
            }
            ((dt)localObject1).a.edit().putInt("rateAfterNumLoads", paramInt).commit();
            i = ((JSONObject)localObject2).getInt("remindAfterLoadNum");
            paramInt = i;
            if (i <= 0) {
              paramInt = 1;
            }
            ((dt)localObject1).a.edit().putInt("remindAfterNumLoads", paramInt).commit();
            localObject3 = ((JSONObject)localObject2).getString("message");
            ((dt)localObject1).a.edit().putString("rateAppMessage", (String)localObject3).commit();
            localObject2 = ((JSONObject)localObject2).getString("title");
            ((dt)localObject1).a.edit().putString("rateAppTitle", (String)localObject2).commit();
            ((dt)localObject1).a(true);
          }
          catch (JSONException localJSONException)
          {
            ((dt)localObject1).a(false);
          }
        }
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          new StringBuilder("IOException in handleResponse(): ").append(localIOException.getMessage());
          dx.b();
          dx.c();
          continue;
          Object localObject3 = h.a(localJSONException);
          if ((!((File)localObject3).delete()) && (((File)localObject3).exists()))
          {
            dx.b("Unable to reenable OPTMZ instrumentation");
            continue;
            label665:
            localJSONException.clear();
          }
        }
      }
    }
  }
  
  public static final class a
    implements cz
  {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/ct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */