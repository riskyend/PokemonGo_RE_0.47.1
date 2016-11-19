package rx.plugins;

import rx.annotations.Beta;
import rx.exceptions.Exceptions;

public abstract class RxJavaErrorHandler
{
  protected static final String ERROR_IN_RENDERING_SUFFIX = ".errorRendering";
  
  public void handleError(Throwable paramThrowable) {}
  
  @Beta
  public final String handleOnNextValueRendering(Object paramObject)
  {
    try
    {
      String str = render(paramObject);
      return str;
    }
    catch (InterruptedException localInterruptedException)
    {
      Thread.currentThread().interrupt();
      return paramObject.getClass().getName() + ".errorRendering";
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        Exceptions.throwIfFatal(localThrowable);
      }
    }
  }
  
  @Beta
  protected String render(Object paramObject)
    throws InterruptedException
  {
    return null;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/plugins/RxJavaErrorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */