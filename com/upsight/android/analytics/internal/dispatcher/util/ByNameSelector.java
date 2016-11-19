package com.upsight.android.analytics.internal.dispatcher.util;

import java.util.Map;

public class ByNameSelector<D>
  implements Selector<D>
{
  private Map<String, D> mData;
  private D mDefaultValue = null;
  
  public ByNameSelector(Map<String, D> paramMap)
  {
    this.mData = paramMap;
  }
  
  public ByNameSelector(Map<String, D> paramMap, D paramD)
  {
    this(paramMap);
  }
  
  public D select(String paramString)
  {
    paramString = this.mData.get(paramString);
    if (paramString != null) {
      return paramString;
    }
    return (D)this.mDefaultValue;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/util/ByNameSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */