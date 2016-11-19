package crittercism.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public final class dt
{
  public SharedPreferences a;
  
  protected dt() {}
  
  public dt(Context paramContext)
  {
    this.a = paramContext.getSharedPreferences("com.crittercism.ratemyapp", 0);
  }
  
  public final int a()
  {
    return this.a.getInt("numAppLoads", 0);
  }
  
  public final void a(boolean paramBoolean)
  {
    this.a.edit().putBoolean("rateMyAppEnabled", paramBoolean).commit();
  }
  
  public final String b()
  {
    return this.a.getString("rateAppMessage", "Would you mind taking a second to rate my app?  I would really appreciate it!");
  }
  
  public final String c()
  {
    return this.a.getString("rateAppTitle", "Rate My App");
  }
  
  public final void d()
  {
    this.a.edit().putBoolean("hasRatedApp", true).commit();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/dt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */