package crittercism.android;

import java.io.OutputStream;
import org.json.JSONException;
import org.json.JSONObject;

public final class bm
  implements ch
{
  private JSONObject a;
  private String b = cg.a.a();
  
  public bm(au paramau)
  {
    try
    {
      this.a = new JSONObject().put("appID", paramau.a()).put("deviceID", paramau.c()).put("crPlatform", "android").put("crVersion", paramau.d()).put("deviceModel", paramau.j()).put("osName", "android").put("osVersion", paramau.k()).put("carrier", paramau.f()).put("mobileCountryCode", paramau.g()).put("mobileNetworkCode", paramau.h()).put("appVersion", paramau.b()).put("locale", new bx.k().a);
      return;
    }
    catch (JSONException paramau) {}
  }
  
  public final void a(OutputStream paramOutputStream)
  {
    paramOutputStream.write(this.a.toString().getBytes());
  }
  
  public final String e()
  {
    return this.b;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/bm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */