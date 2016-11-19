package crittercism.android;

import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

public final class o
  extends m
{
  private static final String[] f = { "libcore.net.http.HttpURLConnectionImpl", "org.apache.harmony.luni.internal.net.www.protocol.http.HttpURLConnectionImpl", "org.apache.harmony.luni.internal.net.www.protocol.http.HttpURLConnection" };
  
  public o(e parame, d paramd)
  {
    super(parame, paramd, f);
  }
  
  protected final String a()
  {
    return "http";
  }
  
  protected final int getDefaultPort()
  {
    return 80;
  }
  
  protected final URLConnection openConnection(URL paramURL)
  {
    paramURL = (HttpURLConnection)super.openConnection(paramURL);
    try
    {
      r localr = new r(paramURL, this.c, this.d);
      return localr;
    }
    catch (ThreadDeath paramURL)
    {
      throw paramURL;
    }
    catch (Throwable localThrowable)
    {
      dx.a(localThrowable);
    }
    return paramURL;
  }
  
  protected final URLConnection openConnection(URL paramURL, Proxy paramProxy)
  {
    paramURL = (HttpURLConnection)super.openConnection(paramURL, paramProxy);
    try
    {
      paramProxy = new r(paramURL, this.c, this.d);
      return paramProxy;
    }
    catch (ThreadDeath paramURL)
    {
      throw paramURL;
    }
    catch (Throwable paramProxy)
    {
      dx.a(paramProxy);
    }
    return paramURL;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/o.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */