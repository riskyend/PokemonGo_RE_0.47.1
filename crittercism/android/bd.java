package crittercism.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public final class bd
  extends BroadcastReceiver
{
  private az a;
  private String b;
  private b c;
  
  public bd(Context paramContext, az paramaz)
  {
    this.a = paramaz;
    paramContext = new d(paramContext);
    this.b = paramContext.b();
    this.c = paramContext.a();
  }
  
  public final void onReceive(Context paramContext, Intent paramIntent)
  {
    new StringBuilder("CrittercismReceiver: INTENT ACTION = ").append(paramIntent.getAction());
    dx.b();
    paramContext = new d(paramContext);
    paramIntent = paramContext.a();
    if ((this.c != paramIntent) && (paramIntent != b.c))
    {
      if (paramIntent == b.d)
      {
        this.a.a(new ce(ce.a.b));
        this.c = paramIntent;
      }
    }
    else
    {
      paramContext = paramContext.b();
      if (!paramContext.equals(this.b))
      {
        if ((!this.b.equals("unknown")) && (!this.b.equals("disconnected"))) {
          break label200;
        }
        if ((!paramContext.equals("unknown")) && (!paramContext.equals("disconnected"))) {
          this.a.a(new ce(ce.a.c, paramContext));
        }
      }
    }
    for (;;)
    {
      this.b = paramContext;
      return;
      if ((this.c != b.d) && (this.c != b.c)) {
        break;
      }
      this.a.a(new ce(ce.a.a));
      break;
      label200:
      if (paramContext.equals("disconnected")) {
        this.a.a(new ce(ce.a.d, this.b));
      } else if (!paramContext.equals("unknown")) {
        this.a.a(new ce(ce.a.e, this.b, paramContext));
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/bd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */