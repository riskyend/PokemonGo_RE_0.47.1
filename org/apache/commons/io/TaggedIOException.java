package org.apache.commons.io;

import java.io.IOException;
import java.io.Serializable;

public class TaggedIOException
  extends IOExceptionWithCause
{
  private static final long serialVersionUID = -6994123481142850163L;
  private final Serializable tag;
  
  public TaggedIOException(IOException paramIOException, Serializable paramSerializable)
  {
    super(paramIOException.getMessage(), paramIOException);
    this.tag = paramSerializable;
  }
  
  public static boolean isTaggedWith(Throwable paramThrowable, Object paramObject)
  {
    return (paramObject != null) && ((paramThrowable instanceof TaggedIOException)) && (paramObject.equals(((TaggedIOException)paramThrowable).tag));
  }
  
  public static void throwCauseIfTaggedWith(Throwable paramThrowable, Object paramObject)
    throws IOException
  {
    if (isTaggedWith(paramThrowable, paramObject)) {
      throw ((TaggedIOException)paramThrowable).getCause();
    }
  }
  
  public IOException getCause()
  {
    return (IOException)super.getCause();
  }
  
  public Serializable getTag()
  {
    return this.tag;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/TaggedIOException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */