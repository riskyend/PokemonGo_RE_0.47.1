package com.mopub.volley;

import java.util.Collections;
import java.util.Map;

public abstract interface Cache
{
  public abstract void clear();
  
  public abstract Entry get(String paramString);
  
  public abstract void initialize();
  
  public abstract void invalidate(String paramString, boolean paramBoolean);
  
  public abstract void put(String paramString, Entry paramEntry);
  
  public abstract void remove(String paramString);
  
  public static class Entry
  {
    public byte[] data;
    public String etag;
    public Map<String, String> responseHeaders = Collections.emptyMap();
    public long serverDate;
    public long softTtl;
    public long ttl;
    
    public boolean isExpired()
    {
      return this.ttl < System.currentTimeMillis();
    }
    
    public boolean refreshNeeded()
    {
      return this.softTtl < System.currentTimeMillis();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/mopub/volley/Cache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */