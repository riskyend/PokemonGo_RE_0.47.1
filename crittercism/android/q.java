package crittercism.android;

import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

public final class q
  extends m
{
  private static final String[] f = { "libcore.net.http.HttpsURLConnectionImpl", "org.apache.harmony.luni.internal.net.www.protocol.https.HttpsURLConnectionImpl", "org.apache.harmony.luni.internal.net.www.protocol.https.HttpsURLConnection" };
  
  public q(e parame, d paramd)
  {
    super(parame, paramd, f);
  }
  
  protected final String a()
  {
    return "https";
  }
  
  protected final int getDefaultPort()
  {
    return 443;
  }
  
  protected final URLConnection openConnection(URL paramURL)
  {
    paramURL = (HttpsURLConnection)super.openConnection(paramURL);
    try
    {
      s locals = new s(paramURL, this.c, this.d);
      return locals;
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
    paramURL = (HttpsURLConnection)super.openConnection(paramURL, paramProxy);
    try
    {
      paramProxy = new s(paramURL, this.c, this.d);
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


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/q.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */