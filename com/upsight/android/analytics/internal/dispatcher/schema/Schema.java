package com.upsight.android.analytics.internal.dispatcher.schema;

import com.upsight.android.analytics.provider.UpsightDataProvider;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Schema
{
  private final Set<String> mAttributes;
  private final Map<String, UpsightDataProvider> mDataProviders;
  private final String mName;
  
  private Schema(String paramString, Set<String> paramSet, Map<String, UpsightDataProvider> paramMap)
  {
    this.mName = paramString;
    this.mAttributes = paramSet;
    this.mDataProviders = paramMap;
  }
  
  static Schema from(String paramString, Schema paramSchema, Set<String> paramSet)
  {
    HashSet localHashSet = new HashSet();
    localHashSet.addAll(paramSet);
    localHashSet.addAll(paramSchema.mAttributes);
    return new Schema(paramString, localHashSet, paramSchema.mDataProviders);
  }
  
  public Set<String> availableKeys()
  {
    return this.mAttributes;
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public Object getValueFor(String paramString)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (this.mAttributes.contains(paramString))
    {
      UpsightDataProvider localUpsightDataProvider = (UpsightDataProvider)this.mDataProviders.get(paramString);
      localObject1 = localObject2;
      if (localUpsightDataProvider != null) {
        localObject1 = localUpsightDataProvider.get(paramString);
      }
    }
    return localObject1;
  }
  
  public static class Default
    extends Schema
  {
    static final Set<String> DEFAULT_REQUEST_ATTRIBUTES = new HashSet() {};
    private static final String PARTNERS_KEY = "partners";
    
    Default(Map<String, UpsightDataProvider> paramMap)
    {
      super(DEFAULT_REQUEST_ATTRIBUTES, paramMap, null);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/schema/Schema.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */