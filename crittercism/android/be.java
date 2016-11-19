package crittercism.android;

import com.crittercism.app.Transaction;

public final class be
  extends Transaction
{
  public be()
  {
    dx.c("Creating no-op transaction");
  }
  
  public final void a()
  {
    dx.b("No-op transaction. Ignoring Transaction.start() call.", new IllegalStateException("No-op transaction"));
  }
  
  public final void a(int paramInt)
  {
    dx.b("No-op transaction. Ignoring Transaction.setValue(double) call.", new IllegalStateException("No-op transaction"));
  }
  
  public final void b()
  {
    dx.b("No-op transaction. Ignoring Transaction.stop() call.", new IllegalStateException("No-op transaction"));
  }
  
  public final void c()
  {
    dx.b("No-op transaction. Ignoring Transaction.fail() call.", new IllegalStateException("No-op transaction"));
  }
  
  public final int d()
  {
    dx.b("No-op transaction. Ignoring Transaction.getValue() call.", new IllegalStateException("No-op transaction"));
    return -1;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/be.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */