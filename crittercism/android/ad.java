package crittercism.android;

import java.net.SocketImpl;
import java.net.SocketImplFactory;

public final class ad
  implements SocketImplFactory
{
  private Class a;
  private SocketImplFactory b;
  private e c;
  private d d;
  
  public ad(Class paramClass, e parame, d paramd)
  {
    this.c = parame;
    this.d = paramd;
    this.a = paramClass;
    paramClass = this.a;
    if (paramClass == null) {
      throw new cl("Class was null");
    }
    try
    {
      paramClass.newInstance();
      return;
    }
    catch (Throwable paramClass)
    {
      throw new cl("Unable to create new instance", paramClass);
    }
  }
  
  public ad(SocketImplFactory paramSocketImplFactory, e parame, d paramd)
  {
    this.c = parame;
    this.d = paramd;
    this.b = paramSocketImplFactory;
    paramSocketImplFactory = this.b;
    if (paramSocketImplFactory == null) {
      throw new cl("Factory was null");
    }
    try
    {
      if (paramSocketImplFactory.createSocketImpl() == null) {
        throw new cl("Factory does not work");
      }
    }
    catch (Throwable paramSocketImplFactory)
    {
      throw new cl("Factory does not work", paramSocketImplFactory);
    }
  }
  
  public final SocketImpl createSocketImpl()
  {
    Object localObject1 = null;
    if (this.b != null) {
      localObject1 = this.b.createSocketImpl();
    }
    while (localObject1 != null)
    {
      return new ac(this.c, this.d, (SocketImpl)localObject1);
      Object localObject2 = this.a;
      try
      {
        localObject2 = (SocketImpl)this.a.newInstance();
        localObject1 = localObject2;
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        localIllegalAccessException.printStackTrace();
      }
      catch (InstantiationException localInstantiationException)
      {
        localInstantiationException.printStackTrace();
      }
    }
    return (SocketImpl)localObject1;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/ad.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */