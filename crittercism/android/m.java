package crittercism.android;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

public abstract class m
  extends URLStreamHandler
{
  public static final String[] a = { "java.net.URL", "int", "java.net.Proxy" };
  public static final String[] b = { "java.net.URL", "int" };
  e c;
  d d;
  boolean e;
  private Constructor f = null;
  private Constructor g = null;
  
  public m(e parame, d paramd, String[] paramArrayOfString)
  {
    this(parame, paramd, paramArrayOfString, a, b);
  }
  
  private m(e parame, d paramd, String[] paramArrayOfString1, String[] paramArrayOfString2, String[] paramArrayOfString3)
  {
    this.c = parame;
    this.d = paramd;
    this.e = true;
    int i = 0;
    for (;;)
    {
      if (i < paramArrayOfString1.length) {}
      try
      {
        this.f = l.a(paramArrayOfString1[i], paramArrayOfString3);
        this.g = l.a(paramArrayOfString1[i], paramArrayOfString2);
        this.f.setAccessible(true);
        this.g.setAccessible(true);
        if ((this.f != null) && (this.g != null)) {
          break;
        }
        throw new ClassNotFoundException("Couldn't find suitable connection implementations");
      }
      catch (ClassNotFoundException parame)
      {
        this.f = null;
        this.f = null;
        i += 1;
      }
    }
    if (!b()) {
      throw new ClassNotFoundException("Unable to open test connections");
    }
  }
  
  private URLConnection a(URL paramURL, Proxy paramProxy)
  {
    Proxy localProxy = null;
    String str = "Unable to setup network statistics on a " + a() + " connection due to ";
    try
    {
      ea localea = ea.e;
      if (paramProxy != null) {
        break label125;
      }
      paramProxy = (URLConnection)this.f.newInstance(new Object[] { paramURL, Integer.valueOf(getDefaultPort()) });
      localProxy = paramProxy;
      paramProxy = null;
    }
    catch (IllegalArgumentException paramProxy)
    {
      for (;;)
      {
        new StringBuilder().append(str).append("bad arguments");
        dx.b();
        paramProxy = new IOException(paramProxy.getMessage());
      }
    }
    catch (InstantiationException paramProxy)
    {
      for (;;)
      {
        new StringBuilder().append(str).append("an instantiation problem");
        dx.b();
        paramProxy = new IOException(paramProxy.getMessage());
      }
    }
    catch (IllegalAccessException paramProxy)
    {
      for (;;)
      {
        new StringBuilder().append(str).append("security restrictions");
        dx.b();
        paramProxy = new IOException(paramProxy.getMessage());
      }
    }
    catch (InvocationTargetException paramProxy)
    {
      for (;;)
      {
        label125:
        new StringBuilder().append(str).append("an invocation problem");
        dx.b();
        paramProxy = new IOException(paramProxy.getMessage());
      }
    }
    if (paramProxy != null) {
      if (this.e)
      {
        this.e = false;
        paramProxy = v.a();
        if (paramProxy == null) {
          break label319;
        }
      }
    }
    label319:
    for (boolean bool = paramProxy.c();; bool = false)
    {
      dx.b("Stopping network statistics monitoring");
      if (bool)
      {
        return new URL(paramURL.toExternalForm()).openConnection();
        paramProxy = (URLConnection)this.g.newInstance(new Object[] { paramURL, Integer.valueOf(getDefaultPort()), paramProxy });
        localProxy = paramProxy;
        paramProxy = null;
        break;
        throw paramProxy;
      }
      return localProxy;
    }
  }
  
  private boolean b()
  {
    this.e = false;
    try
    {
      openConnection(new URL("http://www.google.com"));
      this.e = true;
      return true;
    }
    catch (IOException localIOException)
    {
      localIOException = localIOException;
      this.e = true;
      return false;
    }
    finally
    {
      localObject = finally;
      this.e = true;
      throw ((Throwable)localObject);
    }
  }
  
  protected abstract String a();
  
  protected abstract int getDefaultPort();
  
  protected URLConnection openConnection(URL paramURL)
  {
    return a(paramURL, null);
  }
  
  protected URLConnection openConnection(URL paramURL, Proxy paramProxy)
  {
    if ((paramURL == null) || (paramProxy == null)) {
      throw new IllegalArgumentException("url == null || proxy == null");
    }
    return a(paramURL, paramProxy);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/m.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */