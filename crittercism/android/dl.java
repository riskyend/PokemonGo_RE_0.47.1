package crittercism.android;

import android.content.SharedPreferences;
import java.util.HashMap;
import java.util.Map;

public final class dl
  extends di
{
  public Map a = new HashMap();
  private dw b;
  private au c;
  private boolean d = false;
  private boolean e = false;
  private boolean f = false;
  private boolean g = false;
  
  public dl(au paramau)
  {
    this.c = paramau;
    this.b = paramau.l();
  }
  
  private void a(String paramString, Object paramObject)
  {
    try
    {
      this.a.put(paramString, paramObject);
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public final void a()
  {
    boolean bool2 = false;
    boolean bool1 = this.b.b();
    if (this.d) {
      a("optOutStatus", Boolean.valueOf(bool1));
    }
    if (!bool1)
    {
      if (this.e) {
        a("crashedOnLastLoad", Boolean.valueOf(dq.a));
      }
      if (this.f) {
        a("userUUID", this.c.c());
      }
      if (this.g)
      {
        dt localdt = az.A().A;
        if (localdt != null)
        {
          bool1 = bool2;
          if (localdt.a.getBoolean("rateMyAppEnabled", false))
          {
            bool1 = bool2;
            if (!localdt.a.getBoolean("hasRatedApp", false))
            {
              int i = localdt.a();
              int j = localdt.a.getInt("rateAfterNumLoads", 5);
              bool1 = bool2;
              if (i >= j)
              {
                bool1 = bool2;
                if ((i - j) % localdt.a.getInt("remindAfterNumLoads", 5) == 0) {
                  bool1 = true;
                }
              }
            }
          }
          a("shouldShowRateAppAlert", Boolean.valueOf(bool1));
          a("message", localdt.b());
          a("title", localdt.c());
        }
      }
    }
  }
  
  public final void b()
  {
    this.d = true;
  }
  
  public final void c()
  {
    this.e = true;
  }
  
  public final void d()
  {
    this.f = true;
  }
  
  public final void e()
  {
    this.g = true;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/dl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */