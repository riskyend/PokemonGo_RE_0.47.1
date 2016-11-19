package crittercism.android;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

public final class e
{
  List a = new LinkedList();
  final Set b = new HashSet();
  final Set c = new HashSet();
  private Executor d;
  
  public e(Executor paramExecutor)
  {
    this(paramExecutor, new LinkedList(), new LinkedList());
  }
  
  private e(Executor paramExecutor, List paramList1, List paramList2)
  {
    this.d = paramExecutor;
    a(paramList1);
    b(paramList2);
  }
  
  @Deprecated
  public final void a(c paramc)
  {
    a(paramc, c.a.e);
  }
  
  public final void a(c paramc, c.a parama)
  {
    if (paramc.b) {
      return;
    }
    paramc.b = true;
    paramc.c = parama;
    this.d.execute(new a(paramc, (byte)0));
  }
  
  public final void a(f paramf)
  {
    synchronized (this.a)
    {
      this.a.add(paramf);
      return;
    }
  }
  
  public final void a(List paramList)
  {
    synchronized (this.b)
    {
      this.b.addAll(paramList);
      this.b.remove(null);
      return;
    }
  }
  
  public final void b(List paramList)
  {
    synchronized (this.c)
    {
      this.c.addAll(paramList);
      this.c.remove(null);
      return;
    }
  }
  
  final class a
    implements Runnable
  {
    private c b;
    
    private a(c paramc)
    {
      this.b = paramc;
    }
    
    private boolean a(c arg1)
    {
      String str = ???.a();
      synchronized (e.this.b)
      {
        Iterator localIterator = e.this.b.iterator();
        while (localIterator.hasNext()) {
          if (str.contains((String)localIterator.next())) {
            return true;
          }
        }
        return false;
      }
    }
    
    private boolean a(String paramString)
    {
      synchronized (e.this.c)
      {
        Iterator localIterator = e.this.c.iterator();
        while (localIterator.hasNext()) {
          if (paramString.contains((String)localIterator.next())) {
            return false;
          }
        }
        return true;
      }
    }
    
    public final void run()
    {
      if (a(this.b)) {
        return;
      }
      ??? = this.b.a();
      if (a((String)???))
      {
        int i = ((String)???).indexOf("?");
        if (i != -1) {
          this.b.a(((String)???).substring(0, i));
        }
      }
      synchronized (e.this.a)
      {
        Iterator localIterator = e.this.a.iterator();
        if (localIterator.hasNext()) {
          ((f)localIterator.next()).a(this.b);
        }
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */