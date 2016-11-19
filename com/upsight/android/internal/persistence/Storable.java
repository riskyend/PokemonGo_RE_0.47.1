package com.upsight.android.internal.persistence;

public final class Storable
{
  String id;
  String type;
  String value;
  
  Storable(String paramString1, String paramString2, String paramString3)
  {
    this.id = paramString1;
    this.type = paramString2;
    this.value = paramString3;
  }
  
  public static Storable create(String paramString1, String paramString2, String paramString3)
  {
    return new Storable(paramString1, paramString2, paramString3);
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public String getValue()
  {
    return this.value;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/persistence/Storable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */