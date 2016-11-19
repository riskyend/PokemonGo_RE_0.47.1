package crittercism.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.ConditionVariable;
import com.crittercism.app.CrittercismNDK;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class dg
  extends di
{
  public ConditionVariable a = new ConditionVariable();
  public bm b = null;
  private ConditionVariable c = new ConditionVariable();
  private bb d;
  private Context e;
  private aw f;
  private ax g;
  private au h;
  private List i = new ArrayList();
  private boolean j = false;
  private boolean k;
  private Exception l = null;
  
  public dg(bb parambb, Context paramContext, aw paramaw, ax paramax, au paramau)
  {
    this.d = parambb;
    this.e = paramContext;
    this.f = paramaw;
    this.g = paramax;
    this.h = paramau;
    this.k = false;
  }
  
  private void a(File paramFile)
  {
    boolean bool = this.k;
    az localaz = az.A();
    if (localaz.t) {
      return;
    }
    if ((paramFile != null) && (paramFile.exists())) {
      paramFile.isFile();
    }
    Object localObject = this.f;
    localObject = this.f.s();
    bs localbs1 = this.f.t();
    bs localbs2 = this.f.u();
    bs localbs3 = this.f.v();
    bs localbs4 = this.f.q();
    if (paramFile != null)
    {
      dq.a = true;
      localaz.e.open();
      localbs4.a(new cd(paramFile, (bs)localObject, localbs2, localbs1, localbs3));
      paramFile.delete();
      this.f.w().a();
    }
    for (;;)
    {
      localbs2.a();
      localbs1.a();
      localbs3.a();
      ((bs)localObject).a(localbs2);
      return;
      localaz.e.open();
      bg.a(this.f);
    }
  }
  
  private void c()
  {
    try
    {
      this.j = true;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private boolean d()
  {
    try
    {
      boolean bool = this.j;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private File e()
  {
    int m = 0;
    Object localObject1 = new File(this.e.getFilesDir().getAbsolutePath() + "/" + this.d.g());
    if ((!((File)localObject1).exists()) || (!((File)localObject1).isDirectory())) {}
    for (;;)
    {
      return null;
      localObject1 = ((File)localObject1).listFiles();
      if (localObject1 != null) {
        if (localObject1.length == 1)
        {
          localObject1 = localObject1[0];
          ((File)localObject1).isFile();
          if (((File)localObject1).isFile()) {
            return (File)localObject1;
          }
        }
        else if (localObject1.length > 1)
        {
          int n = localObject1.length;
          while (m < n)
          {
            Object localObject2 = localObject1[m];
            ((File)localObject2).isFile();
            ((File)localObject2).delete();
            m += 1;
          }
        }
      }
    }
  }
  
  private void f()
  {
    if (az.A().t) {}
    dv localdv;
    do
    {
      return;
      boolean bool = this.k;
      bs localbs1 = this.f.n();
      bs localbs2 = this.f.o();
      bs localbs3 = this.f.p();
      bs localbs4 = this.f.q();
      bs localbs5 = this.f.r();
      localdv = this.f.y();
      this.d.b();
      this.b = new bm(this.h);
      if (!this.d.delaySendingAppLoad())
      {
        localbs1.a(this.b);
        df localdf = new df(this.e);
        localdf.a(localbs1, new ct.a(), this.d.e(), "/v0/appload", this.d.b(), this.h, new cs.b());
        localdf.a(localbs2, new da.a(), this.d.b(), "/android_v2/handle_exceptions", null, this.h, new cu.a());
        localdf.a(localbs4, new da.a(), this.d.b(), "/android_v2/handle_ndk_crashes", null, this.h, new cu.a());
        localdf.a(localbs5, new da.a(), this.d.b(), "/android_v2/handle_crashes", null, this.h, new cu.a());
        localdf.a(localbs3, new da.a(), this.d.b(), "/android_v2/handle_exceptions", null, new ba(this.h, this.d), new cu.a());
        localdf.a();
      }
    } while (!localdv.b());
    az.A().E();
  }
  
  public final void a()
  {
    Object localObject5;
    boolean bool;
    try
    {
      dx.b();
      Object localObject1 = new File(this.e.getFilesDir().getAbsolutePath() + "/com.crittercism/pending");
      if ((((File)localObject1).exists()) && (!((File)localObject1).isDirectory()))
      {
        dx.b();
        localObject5 = az.A();
        ((az)localObject5).w.a();
        localObject1 = this.h.l();
        ((az)localObject5).d.open();
        localObject5 = this.g;
        localObject6 = this.e;
        ((dw)localObject1).a((ax)localObject5);
        dq.a = dq.a(this.e).booleanValue();
        dq.a(this.e, false);
        if (!((dw)localObject1).b())
        {
          localObject5 = new dt(this.e);
          int m = ((dt)localObject5).a();
          ((dt)localObject5).a.edit().putInt("numAppLoads", m + 1).commit();
          az.A().A = ((dt)localObject5);
          ((dw)localObject1).a().a(this.g, cq.j.a(), cq.j.b());
        }
        this.k = ((dw)localObject1).b();
        localObject5 = e();
        if (!this.k) {
          break label531;
        }
        bool = this.k;
        if (!az.A().t)
        {
          if ((localObject5 != null) && (((File)localObject5).exists())) {
            ((File)localObject5).isFile();
          }
          new bs(this.e, br.a).a();
          new bs(this.e, br.b).a();
          new bs(this.e, br.c).a();
          new bs(this.e, br.d).a();
          new bs(this.e, br.e).a();
          new bs(this.e, br.f).a();
          new bs(this.e, br.h).a();
          new bs(this.e, br.g).a();
          new bs(this.e, br.k).a();
          if (localObject5 != null) {
            ((File)localObject5).delete();
          }
        }
        h.b(this.e);
        c();
        localObject1 = this.i.iterator();
        while (((Iterator)localObject1).hasNext()) {
          ((Runnable)((Iterator)localObject1).next()).run();
        }
      }
    }
    catch (Exception localException)
    {
      for (;;)
      {
        new StringBuilder("Exception in run(): ").append(localException.getMessage());
        dx.b();
        dx.c();
        this.l = localException;
        return;
        eb.a(localException);
      }
    }
    finally
    {
      this.c.open();
    }
    label531:
    Object localObject6 = this.e;
    Object localObject3 = new h((Context)localObject6);
    localObject6 = ((Context)localObject6).getSharedPreferences("com.crittercism.optmz.config", 0);
    if (((SharedPreferences)localObject6).contains("interval"))
    {
      ((h)localObject3).d = ((SharedPreferences)localObject6).getInt("interval", 10);
      if (!((SharedPreferences)localObject6).contains("kill")) {
        break label972;
      }
      ((h)localObject3).c = ((SharedPreferences)localObject6).getBoolean("kill", false);
      if (!((SharedPreferences)localObject6).contains("persist")) {
        break label977;
      }
      ((h)localObject3).b = ((SharedPreferences)localObject6).getBoolean("persist", false);
      if (!((SharedPreferences)localObject6).contains("enabled")) {
        break label982;
      }
      ((h)localObject3).a = ((SharedPreferences)localObject6).getBoolean("enabled", false);
    }
    for (;;)
    {
      if (localObject3 != null) {
        az.A().a((h)localObject3);
      }
      bool = this.k;
      this.f.z();
      Object localObject7;
      bs localbs1;
      bs localbs2;
      if (!az.A().t)
      {
        localObject3 = bh.a(this.e);
        localObject6 = this.f.x();
        localObject7 = this.f.s();
        localbs1 = this.f.t();
        localbs2 = this.f.v();
      }
      try
      {
        URL localURL = new URL(this.d.d() + "/api/v1/transactions");
        localObject6 = new bi(this.e, this.h, (bs)localObject6, (bs)localObject7, localbs1, localbs2, localURL);
        localObject7 = az.A();
        ((az)localObject7).y = ((bi)localObject6);
        new dy((Runnable)localObject6, "TXN Thread").start();
        ((az)localObject7).a((bh)localObject3);
        a((File)localObject5);
        this.a.open();
        this.f.s().a(cf.a);
        if ((!az.A().t) && (this.d.isNdkCrashReportingEnabled())) {
          dx.b();
        }
      }
      catch (MalformedURLException localMalformedURLException)
      {
        try
        {
          CrittercismNDK.installNdkLib(this.e, this.d.g());
          f();
          break;
          localMalformedURLException = localMalformedURLException;
          dx.a();
        }
        catch (Throwable localThrowable)
        {
          for (;;)
          {
            new StringBuilder("Exception installing ndk library: ").append(localThrowable.getClass().getName());
            dx.b();
          }
        }
      }
      this.c.open();
      return;
      Object localObject4 = null;
      continue;
      label972:
      localObject4 = null;
      continue;
      label977:
      localObject4 = null;
      continue;
      label982:
      localObject4 = null;
    }
  }
  
  /* Error */
  public final boolean a(Runnable paramRunnable)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial 501	crittercism/android/dg:d	()Z
    //   6: ifne +20 -> 26
    //   9: aload_0
    //   10: getfield 43	crittercism/android/dg:i	Ljava/util/List;
    //   13: aload_1
    //   14: invokeinterface 505 2 0
    //   19: pop
    //   20: iconst_1
    //   21: istore_2
    //   22: aload_0
    //   23: monitorexit
    //   24: iload_2
    //   25: ireturn
    //   26: iconst_0
    //   27: istore_2
    //   28: goto -6 -> 22
    //   31: astore_1
    //   32: aload_0
    //   33: monitorexit
    //   34: aload_1
    //   35: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	36	0	this	dg
    //   0	36	1	paramRunnable	Runnable
    //   21	7	2	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	20	31	finally
  }
  
  public final void b()
  {
    this.c.block();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/dg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */