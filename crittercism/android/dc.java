package crittercism.android;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public final class dc
{
  private static SSLSocketFactory a = null;
  private URL b;
  private Map c = new HashMap();
  private int d = 0;
  private boolean e = true;
  private boolean f = true;
  private String g = "POST";
  private boolean h = false;
  private int i = 2500;
  
  static
  {
    try
    {
      Object localObject = SSLContext.getInstance("TLS");
      ((SSLContext)localObject).init(null, null, null);
      localObject = ((SSLContext)localObject).getSocketFactory();
      if (localObject != null)
      {
        if ((localObject instanceof ab))
        {
          a = ((ab)localObject).a();
          return;
        }
        a = (SSLSocketFactory)localObject;
        return;
      }
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      a = null;
    }
  }
  
  public dc(URL paramURL)
  {
    this.b = paramURL;
    this.c.put("User-Agent", Arrays.asList(new String[] { "5.0.8" }));
    this.c.put("Content-Type", Arrays.asList(new String[] { "application/json" }));
    this.c.put("Accept", Arrays.asList(new String[] { "text/plain", "application/json" }));
  }
  
  public final HttpURLConnection a()
  {
    HttpURLConnection localHttpURLConnection = (HttpURLConnection)this.b.openConnection();
    Object localObject = this.c.entrySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
      Iterator localIterator = ((List)localEntry.getValue()).iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localHttpURLConnection.addRequestProperty((String)localEntry.getKey(), str);
      }
    }
    localHttpURLConnection.setConnectTimeout(this.i);
    localHttpURLConnection.setReadTimeout(this.i);
    localHttpURLConnection.setDoInput(this.e);
    localHttpURLConnection.setDoOutput(this.f);
    if (this.h) {
      localHttpURLConnection.setChunkedStreamingMode(this.d);
    }
    localHttpURLConnection.setRequestMethod(this.g);
    if ((localHttpURLConnection instanceof HttpsURLConnection))
    {
      localObject = (HttpsURLConnection)localHttpURLConnection;
      if (a != null) {
        ((HttpsURLConnection)localObject).setSSLSocketFactory(a);
      }
    }
    else
    {
      return localHttpURLConnection;
    }
    throw new GeneralSecurityException();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/dc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */