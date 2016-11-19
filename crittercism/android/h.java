package crittercism.android;

import android.content.Context;
import java.io.File;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public final class h
{
  public boolean a = false;
  public boolean b = false;
  public boolean c = false;
  public int d = 10;
  
  public h(Context paramContext)
  {
    if (a(paramContext).exists()) {
      this.c = true;
    }
  }
  
  public h(JSONObject paramJSONObject)
  {
    if (!paramJSONObject.has("net")) {
      return;
    }
    try
    {
      paramJSONObject = paramJSONObject.getJSONObject("net");
      this.a = paramJSONObject.optBoolean("enabled", false);
      this.b = paramJSONObject.optBoolean("persist", false);
      this.c = paramJSONObject.optBoolean("kill", false);
      this.d = paramJSONObject.optInt("interval", 10);
      return;
    }
    catch (JSONException paramJSONObject) {}
  }
  
  public static File a(Context paramContext)
  {
    return new File(paramContext.getFilesDir().getAbsolutePath() + "/.crittercism.apm.disabled.");
  }
  
  public static void b(Context paramContext)
  {
    try
    {
      a(paramContext).createNewFile();
      return;
    }
    catch (IOException paramContext)
    {
      dx.b("Unable to kill APM: " + paramContext.getMessage());
    }
  }
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (!(paramObject instanceof h)) {
        return false;
      }
      paramObject = (h)paramObject;
      if (this.c != ((h)paramObject).c) {
        return false;
      }
      if (this.a != ((h)paramObject).a) {
        return false;
      }
      if (this.b != ((h)paramObject).b) {
        return false;
      }
    } while (this.d == ((h)paramObject).d);
    return false;
  }
  
  public final int hashCode()
  {
    int k = 1231;
    int i;
    int j;
    if (this.c)
    {
      i = 1231;
      if (!this.a) {
        break label63;
      }
      j = 1231;
      label26:
      if (!this.b) {
        break label70;
      }
    }
    for (;;)
    {
      return ((j + (i + 31) * 31) * 31 + k) * 31 + this.d;
      i = 1237;
      break;
      label63:
      j = 1237;
      break label26;
      label70:
      k = 1237;
    }
  }
  
  public final String toString()
  {
    return "OptmzConfiguration [\nisSendTaskEnabled=" + this.a + "\n, shouldPersist=" + this.b + "\n, isKilled=" + this.c + "\n, statisticsSendInterval=" + this.d + "]";
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */