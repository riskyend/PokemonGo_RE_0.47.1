package com.upsight.android.analytics.internal.dispatcher.schema;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class IdentifierConfig
{
  private Map<String, String> mIdentifierFilters;
  private Map<String, Set<String>> mIdentifiers;
  
  private IdentifierConfig(Builder paramBuilder)
  {
    this.mIdentifiers = paramBuilder.identifiers;
    this.mIdentifierFilters = paramBuilder.identifierFilters;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
      paramObject = (IdentifierConfig)paramObject;
      if (this.mIdentifierFilters != null)
      {
        if (this.mIdentifierFilters.equals(((IdentifierConfig)paramObject).mIdentifierFilters)) {}
      }
      else {
        while (((IdentifierConfig)paramObject).mIdentifierFilters != null) {
          return false;
        }
      }
      if (this.mIdentifiers == null) {
        break;
      }
    } while (this.mIdentifiers.equals(((IdentifierConfig)paramObject).mIdentifiers));
    for (;;)
    {
      return false;
      if (((IdentifierConfig)paramObject).mIdentifiers == null) {
        break;
      }
    }
  }
  
  public Map<String, String> getIdentifierFilters()
  {
    return Collections.unmodifiableMap(this.mIdentifierFilters);
  }
  
  public Map<String, Set<String>> getIdentifiers()
  {
    return Collections.unmodifiableMap(this.mIdentifiers);
  }
  
  public int hashCode()
  {
    int j = 0;
    if (this.mIdentifiers != null) {}
    for (int i = this.mIdentifiers.hashCode();; i = 0)
    {
      if (this.mIdentifierFilters != null) {
        j = this.mIdentifierFilters.hashCode();
      }
      return i * 31 + j;
    }
  }
  
  public static class Builder
  {
    private Map<String, String> identifierFilters = new HashMap();
    private Map<String, Set<String>> identifiers = new HashMap();
    
    public Builder addIdentifierFilter(String paramString1, String paramString2)
    {
      if (this.identifierFilters.containsKey(paramString1)) {
        throw new IllegalArgumentException("Identifier filter " + paramString1 + " already exists");
      }
      this.identifierFilters.put(paramString1, paramString2);
      return this;
    }
    
    public Builder addIdentifiers(String paramString, Set<String> paramSet)
    {
      if (this.identifiers.containsKey(paramString)) {
        throw new IllegalArgumentException("Identifiers name " + paramString + " already exists");
      }
      this.identifiers.put(paramString, paramSet);
      return this;
    }
    
    public IdentifierConfig build()
    {
      return new IdentifierConfig(this, null);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/schema/IdentifierConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */