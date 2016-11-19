package crittercism.android;

import android.location.Location;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.Date;
import org.json.JSONArray;

public final class c
  extends bp
{
  public long a = Long.MAX_VALUE;
  public boolean b = false;
  a c = a.a;
  public long d = 0L;
  public int e = 0;
  public String f = "";
  public cn g = new cn(null);
  public k h = new k();
  public String i;
  public b j = b.a;
  private long k = Long.MAX_VALUE;
  private boolean l = false;
  private boolean m = false;
  private String n = cg.a.a();
  private long o = 0L;
  private boolean p = false;
  private boolean q = false;
  private double[] r;
  
  public c() {}
  
  public c(String paramString)
  {
    if (paramString != null) {
      this.i = paramString;
    }
  }
  
  public c(URL paramURL)
  {
    if (paramURL != null) {
      this.i = paramURL.toExternalForm();
    }
  }
  
  private long g()
  {
    long l2 = Long.MAX_VALUE;
    long l1 = l2;
    if (this.a != Long.MAX_VALUE)
    {
      l1 = l2;
      if (this.k != Long.MAX_VALUE) {
        l1 = this.k - this.a;
      }
    }
    return l1;
  }
  
  public final String a()
  {
    int i2 = 1;
    String str1 = this.i;
    Object localObject1 = str1;
    Object localObject2;
    if (str1 == null)
    {
      localObject2 = this.h;
      if (((k)localObject2).b == null) {
        break label117;
      }
      str1 = ((k)localObject2).b;
    }
    for (;;)
    {
      int i1;
      String str2;
      if (((k)localObject2).f)
      {
        i1 = ((k)localObject2).e;
        localObject1 = str1;
        if (i1 > 0)
        {
          str2 = ":" + i1;
          localObject1 = str1;
          if (!str1.endsWith(str2)) {
            localObject1 = str1 + str2;
          }
        }
        this.i = ((String)localObject1);
        return (String)localObject1;
        label117:
        if (((k)localObject2).a != null) {
          str1 = ((k)localObject2).a.getHostName();
        }
      }
      else
      {
        str2 = ((k)localObject2).c;
        if (str2 != null)
        {
          i1 = i2;
          if (!str2.regionMatches(true, 0, "http:", 0, 5)) {
            if (!str2.regionMatches(true, 0, "https:", 0, 6)) {
              break label193;
            }
          }
        }
        label193:
        for (i1 = i2;; i1 = 0)
        {
          if (i1 == 0) {
            break label198;
          }
          localObject1 = str2;
          break;
        }
        label198:
        if (((k)localObject2).d != null) {}
        for (localObject1 = "" + k.a.a(((k)localObject2).d) + ":";; localObject1 = "")
        {
          if (str2.startsWith("//"))
          {
            localObject1 = (String)localObject1 + str2;
            break;
          }
          String str4 = (String)localObject1 + "//";
          if (str2.startsWith(str1))
          {
            localObject1 = str4 + str2;
            break;
          }
          String str3 = "";
          localObject1 = str3;
          if (((k)localObject2).e > 0) {
            if (((k)localObject2).d != null)
            {
              localObject1 = str3;
              if (k.a.b(((k)localObject2).d) == ((k)localObject2).e) {}
            }
            else
            {
              localObject2 = ":" + ((k)localObject2).e;
              localObject1 = str3;
              if (!str1.endsWith((String)localObject2)) {
                localObject1 = localObject2;
              }
            }
          }
          localObject1 = str4 + str1 + (String)localObject1 + str2;
          break;
        }
      }
      str1 = "unknown-host";
    }
  }
  
  public final void a(int paramInt)
  {
    k localk = this.h;
    if (paramInt > 0) {
      localk.e = paramInt;
    }
  }
  
  public final void a(long paramLong)
  {
    if (!this.p) {
      this.o += paramLong;
    }
  }
  
  public final void a(Location paramLocation)
  {
    this.r = new double[] { paramLocation.getLatitude(), paramLocation.getLongitude() };
  }
  
  public final void a(k.a parama)
  {
    this.h.d = parama;
  }
  
  public final void a(OutputStream paramOutputStream)
  {
    paramOutputStream.write(d().toString().getBytes());
  }
  
  public final void a(String paramString)
  {
    if (paramString == null) {
      throw new NullPointerException();
    }
    this.i = paramString;
  }
  
  public final void a(Throwable paramThrowable)
  {
    this.g = new cn(paramThrowable);
  }
  
  public final void a(InetAddress paramInetAddress)
  {
    this.i = null;
    this.h.a = paramInetAddress;
  }
  
  public final void b()
  {
    if ((!this.l) && (this.a == Long.MAX_VALUE)) {
      this.a = System.currentTimeMillis();
    }
  }
  
  public final void b(long paramLong)
  {
    this.p = true;
    this.o = paramLong;
  }
  
  public final void b(String paramString)
  {
    this.i = null;
    this.h.b = paramString;
  }
  
  public final void c()
  {
    if ((!this.m) && (this.k == Long.MAX_VALUE)) {
      this.k = System.currentTimeMillis();
    }
  }
  
  public final void c(long paramLong)
  {
    if (!this.q) {
      this.d += paramLong;
    }
  }
  
  public final JSONArray d()
  {
    JSONArray localJSONArray1 = new JSONArray();
    try
    {
      localJSONArray1.put(this.f);
      localJSONArray1.put(a());
      localJSONArray1.put(ed.a.a(new Date(this.a)));
      localJSONArray1.put(g());
      localJSONArray1.put(this.j.a());
      localJSONArray1.put(this.o);
      localJSONArray1.put(this.d);
      localJSONArray1.put(this.e);
      localJSONArray1.put(this.g.a);
      localJSONArray1.put(this.g.b);
      if (this.r != null)
      {
        JSONArray localJSONArray2 = new JSONArray();
        localJSONArray2.put(this.r[0]);
        localJSONArray2.put(this.r[1]);
        localJSONArray1.put(localJSONArray2);
      }
      return localJSONArray1;
    }
    catch (Exception localException)
    {
      System.out.println("Failed to create statsArray");
      localException.printStackTrace();
    }
    return null;
  }
  
  public final void d(long paramLong)
  {
    this.q = true;
    this.d = paramLong;
  }
  
  public final String e()
  {
    return this.n;
  }
  
  public final void e(long paramLong)
  {
    this.a = paramLong;
    this.l = true;
  }
  
  public final void f()
  {
    this.h.f = true;
  }
  
  public final void f(long paramLong)
  {
    this.k = paramLong;
    this.m = true;
  }
  
  public final String toString()
  {
    Object localObject = "" + "URI            : " + this.i + "\n";
    localObject = (String)localObject + "URI Builder    : " + this.h.toString() + "\n";
    localObject = (String)localObject + "\n";
    localObject = (String)localObject + "Logged by      : " + this.c.toString() + "\n";
    localObject = (String)localObject + "Error type:         : " + this.g.a + "\n";
    localObject = (String)localObject + "Error code:         : " + this.g.b + "\n";
    localObject = (String)localObject + "\n";
    localObject = (String)localObject + "Response time  : " + g() + "\n";
    localObject = (String)localObject + "Start time     : " + this.a + "\n";
    localObject = (String)localObject + "End time       : " + this.k + "\n";
    localObject = (String)localObject + "\n";
    localObject = (String)localObject + "Bytes out    : " + this.d + "\n";
    localObject = (String)localObject + "Bytes in     : " + this.o + "\n";
    localObject = (String)localObject + "\n";
    localObject = (String)localObject + "Response code  : " + this.e + "\n";
    String str = (String)localObject + "Request method : " + this.f + "\n";
    localObject = str;
    if (this.r != null) {
      localObject = str + "Location       : " + Arrays.toString(this.r) + "\n";
    }
    return (String)localObject;
  }
  
  public static enum a
  {
    private String m;
    
    private a(String paramString1)
    {
      this.m = paramString1;
    }
    
    public final String toString()
    {
      return this.m;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */