package crittercism.android;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;

public final class av
  implements Application.ActivityLifecycleCallbacks
{
  private int a = 0;
  private boolean b = false;
  private boolean c = false;
  private boolean d = false;
  private Context e;
  private az f;
  private bd g;
  
  public av(Context paramContext, az paramaz)
  {
    this.e = paramContext;
    this.f = paramaz;
    this.g = new bd(paramContext, paramaz);
  }
  
  public final void onActivityCreated(Activity paramActivity, Bundle paramBundle) {}
  
  public final void onActivityDestroyed(Activity paramActivity) {}
  
  public final void onActivityPaused(Activity paramActivity)
  {
    if (paramActivity != null) {}
    try
    {
      if (this.c)
      {
        paramActivity.unregisterReceiver(this.g);
        this.c = false;
      }
      return;
    }
    catch (ThreadDeath paramActivity)
    {
      throw paramActivity;
    }
    catch (Throwable paramActivity)
    {
      dx.a(paramActivity);
    }
  }
  
  public final void onActivityResumed(Activity paramActivity)
  {
    if (paramActivity != null) {
      for (;;)
      {
        try
        {
          Object localObject;
          if (this.b)
          {
            dx.b();
            this.b = false;
            this.a += 1;
            localObject = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
            ((IntentFilter)localObject).addAction("android.net.wifi.WIFI_STATE_CHANGED");
            paramActivity.registerReceiver(this.g, (IntentFilter)localObject);
            this.c = true;
            return;
          }
          if (this.a == 0)
          {
            this.f.a(new bl(bl.a.a));
            bg.f();
            if (this.d) {
              continue;
            }
            this.d = true;
            localObject = new d(this.e).a();
            if (localObject == b.c) {
              continue;
            }
            if (localObject == b.d)
            {
              this.f.a(new ce(ce.a.b));
              continue;
            }
          }
        }
        catch (ThreadDeath paramActivity)
        {
          throw paramActivity;
          this.f.a(new ce(ce.a.a));
          continue;
        }
        catch (Throwable paramActivity)
        {
          dx.a(paramActivity);
          return;
        }
        this.f.a(new bj(bj.a.a, paramActivity.getClass().getName()));
      }
    }
  }
  
  public final void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle) {}
  
  public final void onActivityStarted(Activity paramActivity) {}
  
  public final void onActivityStopped(Activity paramActivity)
  {
    if (paramActivity != null) {
      try
      {
        this.a -= 1;
        if (paramActivity.isChangingConfigurations())
        {
          dx.b();
          this.b = true;
          return;
        }
        if (this.a == 0)
        {
          this.f.a(new bl(bl.a.b));
          bg.a(this.f);
          return;
        }
      }
      catch (ThreadDeath paramActivity)
      {
        throw paramActivity;
        this.f.a(new bj(bj.a.b, paramActivity.getClass().getName()));
        return;
      }
      catch (Throwable paramActivity)
      {
        dx.a(paramActivity);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/av.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */