package crittercism.android;

import android.content.Context;
import android.os.ConditionVariable;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class bi
  extends di
  implements bt
{
  private long a = System.currentTimeMillis();
  private volatile long b = 10000L;
  private ConditionVariable c = new ConditionVariable(false);
  private ConditionVariable d = new ConditionVariable(false);
  private au e;
  private bs f;
  private bs g;
  private bs h;
  private bs i;
  private URL j;
  private Context k;
  private volatile boolean l = false;
  
  public bi(Context arg1, au paramau, bs parambs1, bs parambs2, bs parambs3, bs parambs4, URL paramURL)
  {
    this.k = ???;
    this.f = parambs1;
    this.g = parambs2;
    this.h = parambs3;
    this.i = parambs4;
    this.e = paramau;
    this.j = paramURL;
    paramau = this.f;
    if (this != null) {}
    synchronized (paramau.c)
    {
      paramau.c.add(this);
      return;
    }
  }
  
  private JSONObject a(JSONArray paramJSONArray)
  {
    JSONObject localJSONObject1 = new JSONObject();
    try
    {
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject2.put("appID", this.e.a());
      localJSONObject2.put("deviceID", this.e.c());
      localJSONObject2.put("crPlatform", "android");
      localJSONObject2.put("crVersion", this.e.d());
      localJSONObject2.put("deviceModel", this.e.j());
      localJSONObject2.put("osName", "android");
      localJSONObject2.put("osVersion", this.e.k());
      localJSONObject2.put("carrier", this.e.f());
      localJSONObject2.put("mobileCountryCode", this.e.g());
      localJSONObject2.put("mobileNetworkCode", this.e.h());
      localJSONObject2.put("appVersion", this.e.b());
      localJSONObject2.put("locale", new bx.k().a);
      localJSONObject1.put("appState", localJSONObject2);
      localJSONObject1.put("transactions", paramJSONArray);
      if (b(paramJSONArray))
      {
        localJSONObject1.put("breadcrumbs", new bo(this.g).a);
        localJSONObject1.put("endpoints", new bo(this.h).a);
        localJSONObject1.put("systemBreadcrumbs", new bo(this.i).a);
      }
      return localJSONObject1;
    }
    catch (JSONException paramJSONArray) {}
    return null;
  }
  
  private static boolean b(JSONArray paramJSONArray)
  {
    boolean bool2 = false;
    int m = 0;
    for (;;)
    {
      boolean bool1 = bool2;
      Object localObject;
      if (m < paramJSONArray.length())
      {
        localObject = paramJSONArray.optJSONArray(m);
        if (localObject == null) {
          break label79;
        }
      }
      try
      {
        localObject = new bg((JSONArray)localObject).k();
        if ((localObject != bg.a.c) && (localObject != bg.a.i))
        {
          bg.a locala = bg.a.h;
          if (localObject != locala)
          {
            bool1 = true;
            return bool1;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        dx.a(localJSONException);
        m += 1;
      }
      catch (ParseException localParseException)
      {
        for (;;)
        {
          label79:
          dx.a(localParseException);
        }
      }
    }
  }
  
  public final void a()
  {
    for (;;)
    {
      if (!this.l)
      {
        this.c.block();
        this.d.block();
        if (!this.l) {}
      }
      else
      {
        return;
      }
      long l1 = this.b - (System.currentTimeMillis() - this.a);
      if (l1 > 0L) {}
      try
      {
        Thread.sleep(l1);
        this.a = System.currentTimeMillis();
        Object localObject1 = this.f.a(this.k);
        this.f.a((bs)localObject1);
        Object localObject2 = new bo((bs)localObject1).a;
        eb.a(((bs)localObject1).a);
        if ((((JSONArray)localObject2).length() <= 0) || (a((JSONArray)localObject2) == null)) {
          continue;
        }
        localObject1 = a((JSONArray)localObject2);
        try
        {
          localObject2 = new dc(this.j).a();
          OutputStream localOutputStream = ((HttpURLConnection)localObject2).getOutputStream();
          localOutputStream.write(((JSONObject)localObject1).toString().getBytes("UTF8"));
          localOutputStream.close();
          ((HttpURLConnection)localObject2).getResponseCode();
          ((HttpURLConnection)localObject2).disconnect();
        }
        catch (IOException localIOException)
        {
          new StringBuilder("Request failed for ").append(this.j);
          dx.a();
        }
        catch (GeneralSecurityException localGeneralSecurityException)
        {
          new StringBuilder("Request failed for ").append(this.j);
          dx.a();
          dx.a(localGeneralSecurityException);
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;) {}
      }
    }
  }
  
  public final void a(int paramInt, TimeUnit paramTimeUnit)
  {
    this.b = paramTimeUnit.toMillis(paramInt);
  }
  
  public final void b()
  {
    this.c.open();
  }
  
  public final void c()
  {
    bs localbs = this.f;
    this.d.open();
  }
  
  public final void d()
  {
    bs localbs = this.f;
    this.d.close();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/bi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */