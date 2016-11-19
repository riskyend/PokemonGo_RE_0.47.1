package crittercism.android;

import org.json.JSONException;
import org.json.JSONObject;

public final class ds
{
  private boolean a;
  private boolean b;
  
  public ds(boolean paramBoolean)
  {
    this.a = paramBoolean;
    this.b = true;
  }
  
  public final boolean a()
  {
    try
    {
      boolean bool = this.a;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static final class a
  {
    public static ds a(ax paramax)
    {
      Object localObject1 = null;
      Object localObject2 = paramax.a(cq.i.a(), cq.i.b());
      if (localObject2 != null) {}
      for (;;)
      {
        try
        {
          localObject2 = new JSONObject((String)localObject2);
          localObject1 = localObject2;
        }
        catch (JSONException localJSONException)
        {
          dx.b();
          continue;
          boolean bool = paramax.c(cq.l.a(), cq.l.b());
          continue;
          bool = false;
          continue;
        }
        if (localObject1 != null)
        {
          bool = ((JSONObject)localObject1).optBoolean("optOutStatusSet", false);
          if (bool)
          {
            bool = ((JSONObject)localObject1).optBoolean("optOutStatus", false);
            return new ds(bool);
          }
        }
        localObject1 = null;
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/ds.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */