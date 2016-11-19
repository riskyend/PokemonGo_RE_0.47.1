package crittercism.android;

import android.content.Context;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;

public final class df
{
  private Context a;
  private List b;
  
  public df(Context paramContext)
  {
    this.a = paramContext;
    this.b = new ArrayList();
  }
  
  public final void a()
  {
    Object localObject = new ArrayList();
    Iterator localIterator = this.b.iterator();
    while (localIterator.hasNext()) {
      ((ArrayList)localObject).add(new Thread((di)localIterator.next()));
    }
    localIterator = ((ArrayList)localObject).iterator();
    while (localIterator.hasNext()) {
      ((Thread)localIterator.next()).start();
    }
    localObject = ((ArrayList)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      ((Thread)((Iterator)localObject).next()).join();
    }
  }
  
  public final void a(bs parambs, cz paramcz, String paramString1, String paramString2, String paramString3, au paramau, cx paramcx)
  {
    try
    {
      if (parambs.b() > 0)
      {
        bs localbs = parambs.a(this.a);
        paramcz = paramcz.a(localbs, parambs, paramString3, this.a, paramau);
        parambs = new dh(localbs, parambs, paramau, new db(paramString1, paramString2).a(), paramcz, paramcx);
        this.b.add(parambs);
      }
      return;
    }
    finally
    {
      parambs = finally;
      throw parambs;
    }
  }
  
  public final void a(dg paramdg, ExecutorService paramExecutorService)
  {
    Iterator localIterator = this.b.iterator();
    while (localIterator.hasNext())
    {
      di localdi = (di)localIterator.next();
      if (!paramdg.a(localdi)) {
        paramExecutorService.execute(localdi);
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/df.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */