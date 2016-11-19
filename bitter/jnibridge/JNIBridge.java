package bitter.jnibridge;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JNIBridge
{
  static native void delete(long paramLong);
  
  static void disableInterfaceProxy(Object paramObject)
  {
    ((a)Proxy.getInvocationHandler(paramObject)).a();
  }
  
  static native Object invoke(long paramLong, Class paramClass, Method paramMethod, Object[] paramArrayOfObject);
  
  static Object newInterfaceProxy(long paramLong, Class[] paramArrayOfClass)
  {
    return Proxy.newProxyInstance(JNIBridge.class.getClassLoader(), paramArrayOfClass, new a(paramLong));
  }
  
  private static final class a
    implements InvocationHandler
  {
    private Object a = new Object[0];
    private long b;
    
    public a(long paramLong)
    {
      this.b = paramLong;
    }
    
    public final void a()
    {
      synchronized (this.a)
      {
        this.b = 0L;
        return;
      }
    }
    
    public final void finalize()
    {
      synchronized (this.a)
      {
        if (this.b == 0L) {
          return;
        }
        JNIBridge.delete(this.b);
        return;
      }
    }
    
    public final Object invoke(Object arg1, Method paramMethod, Object[] paramArrayOfObject)
    {
      synchronized (this.a)
      {
        if (this.b == 0L) {
          return null;
        }
        paramMethod = JNIBridge.invoke(this.b, paramMethod.getDeclaringClass(), paramMethod, paramArrayOfObject);
        return paramMethod;
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/bitter/jnibridge/JNIBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */