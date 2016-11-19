package rx.exceptions;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import rx.annotations.Experimental;

public final class CompositeException
  extends RuntimeException
{
  private static final long serialVersionUID = 3026362227162912146L;
  private Throwable cause = null;
  private final List<Throwable> exceptions;
  private final String message;
  
  @Deprecated
  public CompositeException(String paramString, Collection<? extends Throwable> paramCollection)
  {
    paramString = new LinkedHashSet();
    ArrayList localArrayList = new ArrayList();
    if (paramCollection != null)
    {
      paramCollection = paramCollection.iterator();
      while (paramCollection.hasNext())
      {
        Throwable localThrowable = (Throwable)paramCollection.next();
        if ((localThrowable instanceof CompositeException)) {
          paramString.addAll(((CompositeException)localThrowable).getExceptions());
        } else if (localThrowable != null) {
          paramString.add(localThrowable);
        } else {
          paramString.add(new NullPointerException());
        }
      }
    }
    paramString.add(new NullPointerException());
    localArrayList.addAll(paramString);
    this.exceptions = Collections.unmodifiableList(localArrayList);
    this.message = (this.exceptions.size() + " exceptions occurred. ");
  }
  
  public CompositeException(Collection<? extends Throwable> paramCollection)
  {
    this(null, paramCollection);
  }
  
  @Experimental
  public CompositeException(Throwable... paramVarArgs)
  {
    LinkedHashSet localLinkedHashSet = new LinkedHashSet();
    ArrayList localArrayList = new ArrayList();
    if (paramVarArgs != null)
    {
      int j = paramVarArgs.length;
      int i = 0;
      if (i < j)
      {
        Throwable localThrowable = paramVarArgs[i];
        if ((localThrowable instanceof CompositeException)) {
          localLinkedHashSet.addAll(((CompositeException)localThrowable).getExceptions());
        }
        for (;;)
        {
          i += 1;
          break;
          if (localThrowable != null) {
            localLinkedHashSet.add(localThrowable);
          } else {
            localLinkedHashSet.add(new NullPointerException());
          }
        }
      }
    }
    else
    {
      localLinkedHashSet.add(new NullPointerException());
    }
    localArrayList.addAll(localLinkedHashSet);
    this.exceptions = Collections.unmodifiableList(localArrayList);
    this.message = (this.exceptions.size() + " exceptions occurred. ");
  }
  
  private void appendStackTrace(StringBuilder paramStringBuilder, Throwable paramThrowable, String paramString)
  {
    paramStringBuilder.append(paramString).append(paramThrowable).append("\n");
    paramString = paramThrowable.getStackTrace();
    int j = paramString.length;
    int i = 0;
    while (i < j)
    {
      Object localObject = paramString[i];
      paramStringBuilder.append("\t\tat ").append(localObject).append("\n");
      i += 1;
    }
    if (paramThrowable.getCause() != null)
    {
      paramStringBuilder.append("\tCaused by: ");
      appendStackTrace(paramStringBuilder, paramThrowable.getCause(), "");
    }
  }
  
  private List<Throwable> getListOfCauses(Throwable paramThrowable)
  {
    ArrayList localArrayList = new ArrayList();
    Throwable localThrowable = paramThrowable.getCause();
    paramThrowable = localThrowable;
    if (localThrowable == null) {
      return localArrayList;
    }
    do
    {
      paramThrowable = paramThrowable.getCause();
      localArrayList.add(paramThrowable);
    } while (paramThrowable.getCause() != null);
    return localArrayList;
  }
  
  private void printStackTrace(PrintStreamOrWriter paramPrintStreamOrWriter)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this).append("\n");
    ??? = getStackTrace();
    int j = ???.length;
    int i = 0;
    Object localObject2;
    while (i < j)
    {
      localObject2 = ???[i];
      localStringBuilder.append("\tat ").append(localObject2).append("\n");
      i += 1;
    }
    i = 1;
    ??? = this.exceptions.iterator();
    while (((Iterator)???).hasNext())
    {
      localObject2 = (Throwable)((Iterator)???).next();
      localStringBuilder.append("  ComposedException ").append(i).append(" :").append("\n");
      appendStackTrace(localStringBuilder, (Throwable)localObject2, "\t");
      i += 1;
    }
    synchronized (paramPrintStreamOrWriter.lock())
    {
      paramPrintStreamOrWriter.println(localStringBuilder.toString());
      return;
    }
  }
  
  public Throwable getCause()
  {
    CompositeExceptionCausalChain localCompositeExceptionCausalChain2;
    for (;;)
    {
      try
      {
        if (this.cause != null) {
          break label182;
        }
        localCompositeExceptionCausalChain2 = new CompositeExceptionCausalChain();
        HashSet localHashSet = new HashSet();
        CompositeExceptionCausalChain localCompositeExceptionCausalChain1 = localCompositeExceptionCausalChain2;
        Iterator localIterator1 = this.exceptions.iterator();
        if (!localIterator1.hasNext()) {
          break;
        }
        Object localObject3 = (Throwable)localIterator1.next();
        if (!localHashSet.contains(localObject3))
        {
          localHashSet.add(localObject3);
          Iterator localIterator2 = getListOfCauses((Throwable)localObject3).iterator();
          if (localIterator2.hasNext())
          {
            Throwable localThrowable3 = (Throwable)localIterator2.next();
            if (localHashSet.contains(localThrowable3)) {
              localObject3 = new RuntimeException("Duplicate found in causal chain so cropping to prevent loop ...");
            } else {
              localHashSet.add(localThrowable3);
            }
          }
          else
          {
            try
            {
              ((Throwable)localObject1).initCause((Throwable)localObject3);
              Throwable localThrowable1 = ((Throwable)localObject1).getCause();
            }
            catch (Throwable localThrowable2)
            {
              localObject2 = localObject3;
            }
          }
        }
      }
      finally {}
    }
    this.cause = localCompositeExceptionCausalChain2;
    label182:
    Object localObject2 = this.cause;
    return (Throwable)localObject2;
  }
  
  public List<Throwable> getExceptions()
  {
    return this.exceptions;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void printStackTrace()
  {
    printStackTrace(System.err);
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    printStackTrace(new WrappedPrintStream(paramPrintStream));
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    printStackTrace(new WrappedPrintWriter(paramPrintWriter));
  }
  
  static final class CompositeExceptionCausalChain
    extends RuntimeException
  {
    static String MESSAGE = "Chain of Causes for CompositeException In Order Received =>";
    private static final long serialVersionUID = 3875212506787802066L;
    
    public String getMessage()
    {
      return MESSAGE;
    }
  }
  
  private static abstract class PrintStreamOrWriter
  {
    abstract Object lock();
    
    abstract void println(Object paramObject);
  }
  
  private static class WrappedPrintStream
    extends CompositeException.PrintStreamOrWriter
  {
    private final PrintStream printStream;
    
    WrappedPrintStream(PrintStream paramPrintStream)
    {
      super();
      this.printStream = paramPrintStream;
    }
    
    Object lock()
    {
      return this.printStream;
    }
    
    void println(Object paramObject)
    {
      this.printStream.println(paramObject);
    }
  }
  
  private static class WrappedPrintWriter
    extends CompositeException.PrintStreamOrWriter
  {
    private final PrintWriter printWriter;
    
    WrappedPrintWriter(PrintWriter paramPrintWriter)
    {
      super();
      this.printWriter = paramPrintWriter;
    }
    
    Object lock()
    {
      return this.printWriter;
    }
    
    void println(Object paramObject)
    {
      this.printWriter.println(paramObject);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/rx/exceptions/CompositeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */