package com.nianticlabs.nia.javawrap;

public class NianticPluginWrapper
{
  private long nativeHandle;
  
  private native void nativeDispose();
  
  private native long nativeGetApi();
  
  private native void nativeInitialize();
  
  public void dispose()
  {
    nativeDispose();
  }
  
  public long getApi()
  {
    return nativeGetApi();
  }
  
  public void initialize()
  {
    nativeInitialize();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/javawrap/NianticPluginWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */