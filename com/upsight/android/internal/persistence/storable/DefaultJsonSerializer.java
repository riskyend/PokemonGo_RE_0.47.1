package com.upsight.android.internal.persistence.storable;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.upsight.android.UpsightException;
import com.upsight.android.persistence.UpsightStorableSerializer;

public class DefaultJsonSerializer<T>
  implements UpsightStorableSerializer<T>
{
  private final Class<T> mClass;
  private final Gson mGson;
  
  public DefaultJsonSerializer(Gson paramGson, Class<T> paramClass)
  {
    this.mGson = paramGson;
    this.mClass = paramClass;
  }
  
  public T fromString(String paramString)
    throws UpsightException
  {
    try
    {
      paramString = this.mGson.fromJson(paramString, this.mClass);
      return paramString;
    }
    catch (JsonSyntaxException paramString)
    {
      throw new UpsightException(paramString);
    }
  }
  
  public String toString(T paramT)
  {
    return this.mGson.toJson(paramT);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/storable/DefaultJsonSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */