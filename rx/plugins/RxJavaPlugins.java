package rx.plugins;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class RxJavaPlugins
{
  static final RxJavaErrorHandler DEFAULT_ERROR_HANDLER = new RxJavaErrorHandler() {};
  private static final RxJavaPlugins INSTANCE = new RxJavaPlugins();
  private final AtomicReference<RxJavaErrorHandler> errorHandler = new AtomicReference();
  private final AtomicReference<RxJavaObservableExecutionHook> observableExecutionHook = new AtomicReference();
  private final AtomicReference<RxJavaSchedulersHook> schedulersHook = new AtomicReference();
  private final AtomicReference<RxJavaSingleExecutionHook> singleExecutionHook = new AtomicReference();
  
  public static RxJavaPlugins getInstance()
  {
    return INSTANCE;
  }
  
  static Object getPluginImplementationViaProperty(Class<?> paramClass, Properties paramProperties)
  {
    Properties localProperties = (Properties)paramProperties.clone();
    String str2 = paramClass.getSimpleName();
    String str1 = localProperties.getProperty("rxjava.plugin." + str2 + ".implementation");
    paramProperties = str1;
    if (str1 == null)
    {
      Object localObject = localProperties.entrySet().iterator();
      String str3;
      do
      {
        paramProperties = str1;
        if (!((Iterator)localObject).hasNext()) {
          break;
        }
        paramProperties = (Map.Entry)((Iterator)localObject).next();
        str3 = paramProperties.getKey().toString();
      } while ((!str3.startsWith("rxjava.plugin.")) || (!str3.endsWith(".class")) || (!str2.equals(paramProperties.getValue().toString())));
      paramProperties = str3.substring(0, str3.length() - ".class".length()).substring("rxjava.plugin.".length());
      localObject = "rxjava.plugin." + paramProperties + ".impl";
      str1 = localProperties.getProperty((String)localObject);
      paramProperties = str1;
      if (str1 == null) {
        throw new RuntimeException("Implementing class declaration for " + str2 + " missing: " + (String)localObject);
      }
    }
    if (paramProperties != null) {
      try
      {
        paramClass = Class.forName(paramProperties).asSubclass(paramClass).newInstance();
        return paramClass;
      }
      catch (ClassCastException paramClass)
      {
        throw new RuntimeException(str2 + " implementation is not an instance of " + str2 + ": " + paramProperties);
      }
      catch (ClassNotFoundException paramClass)
      {
        throw new RuntimeException(str2 + " implementation class not found: " + paramProperties, paramClass);
      }
      catch (InstantiationException paramClass)
      {
        throw new RuntimeException(str2 + " implementation not able to be instantiated: " + paramProperties, paramClass);
      }
      catch (IllegalAccessException paramClass)
      {
        throw new RuntimeException(str2 + " implementation not able to be accessed: " + paramProperties, paramClass);
      }
    }
    return null;
  }
  
  public RxJavaErrorHandler getErrorHandler()
  {
    Object localObject;
    if (this.errorHandler.get() == null)
    {
      localObject = getPluginImplementationViaProperty(RxJavaErrorHandler.class, System.getProperties());
      if (localObject != null) {
        break label46;
      }
      this.errorHandler.compareAndSet(null, DEFAULT_ERROR_HANDLER);
    }
    for (;;)
    {
      return (RxJavaErrorHandler)this.errorHandler.get();
      label46:
      this.errorHandler.compareAndSet(null, (RxJavaErrorHandler)localObject);
    }
  }
  
  public RxJavaObservableExecutionHook getObservableExecutionHook()
  {
    Object localObject;
    if (this.observableExecutionHook.get() == null)
    {
      localObject = getPluginImplementationViaProperty(RxJavaObservableExecutionHook.class, System.getProperties());
      if (localObject != null) {
        break label46;
      }
      this.observableExecutionHook.compareAndSet(null, RxJavaObservableExecutionHookDefault.getInstance());
    }
    for (;;)
    {
      return (RxJavaObservableExecutionHook)this.observableExecutionHook.get();
      label46:
      this.observableExecutionHook.compareAndSet(null, (RxJavaObservableExecutionHook)localObject);
    }
  }
  
  public RxJavaSchedulersHook getSchedulersHook()
  {
    Object localObject;
    if (this.schedulersHook.get() == null)
    {
      localObject = getPluginImplementationViaProperty(RxJavaSchedulersHook.class, System.getProperties());
      if (localObject != null) {
        break label46;
      }
      this.schedulersHook.compareAndSet(null, RxJavaSchedulersHook.getDefaultInstance());
    }
    for (;;)
    {
      return (RxJavaSchedulersHook)this.schedulersHook.get();
      label46:
      this.schedulersHook.compareAndSet(null, (RxJavaSchedulersHook)localObject);
    }
  }
  
  public RxJavaSingleExecutionHook getSingleExecutionHook()
  {
    Object localObject;
    if (this.singleExecutionHook.get() == null)
    {
      localObject = getPluginImplementationViaProperty(RxJavaSingleExecutionHook.class, System.getProperties());
      if (localObject != null) {
        break label46;
      }
      this.singleExecutionHook.compareAndSet(null, RxJavaSingleExecutionHookDefault.getInstance());
    }
    for (;;)
    {
      return (RxJavaSingleExecutionHook)this.singleExecutionHook.get();
      label46:
      this.singleExecutionHook.compareAndSet(null, (RxJavaSingleExecutionHook)localObject);
    }
  }
  
  public void registerErrorHandler(RxJavaErrorHandler paramRxJavaErrorHandler)
  {
    if (!this.errorHandler.compareAndSet(null, paramRxJavaErrorHandler)) {
      throw new IllegalStateException("Another strategy was already registered: " + this.errorHandler.get());
    }
  }
  
  public void registerObservableExecutionHook(RxJavaObservableExecutionHook paramRxJavaObservableExecutionHook)
  {
    if (!this.observableExecutionHook.compareAndSet(null, paramRxJavaObservableExecutionHook)) {
      throw new IllegalStateException("Another strategy was already registered: " + this.observableExecutionHook.get());
    }
  }
  
  public void registerSchedulersHook(RxJavaSchedulersHook paramRxJavaSchedulersHook)
  {
    if (!this.schedulersHook.compareAndSet(null, paramRxJavaSchedulersHook)) {
      throw new IllegalStateException("Another strategy was already registered: " + this.schedulersHook.get());
    }
  }
  
  public void registerSingleExecutionHook(RxJavaSingleExecutionHook paramRxJavaSingleExecutionHook)
  {
    if (!this.singleExecutionHook.compareAndSet(null, paramRxJavaSingleExecutionHook)) {
      throw new IllegalStateException("Another strategy was already registered: " + this.singleExecutionHook.get());
    }
  }
  
  void reset()
  {
    INSTANCE.errorHandler.set(null);
    INSTANCE.observableExecutionHook.set(null);
    INSTANCE.singleExecutionHook.set(null);
    INSTANCE.schedulersHook.set(null);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/plugins/RxJavaPlugins.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */