package crittercism.android;

import java.net.MalformedURLException;
import java.net.URL;

public final class db
{
  private String a;
  private String b;
  
  public db(String paramString1, String paramString2)
  {
    paramString1.endsWith("/");
    paramString2.startsWith("/");
    this.a = paramString1;
    this.b = paramString2;
  }
  
  public final URL a()
  {
    try
    {
      URL localURL = new URL(this.a + this.b);
      return localURL;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      new StringBuilder("Invalid url: ").append(this.a).append(this.b);
      dx.b();
    }
    return null;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/db.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */