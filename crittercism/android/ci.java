package crittercism.android;

import java.io.OutputStream;
import org.json.JSONArray;

public abstract class ci
  extends bp
{
  public abstract JSONArray a();
  
  public final void a(OutputStream paramOutputStream)
  {
    String str = a().toString();
    new StringBuilder("BREADCRUMB WRITING ").append(str);
    dx.b();
    paramOutputStream.write(str.getBytes());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/ci.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */