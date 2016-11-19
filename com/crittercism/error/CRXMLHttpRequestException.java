package com.crittercism.error;

public class CRXMLHttpRequestException
  extends Exception
{
  private static final long serialVersionUID = 1515011187293165939L;
  
  public CRXMLHttpRequestException(String paramString)
  {
    this(paramString, null);
  }
  
  public CRXMLHttpRequestException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
  
  public CRXMLHttpRequestException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/crittercism/error/CRXMLHttpRequestException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */