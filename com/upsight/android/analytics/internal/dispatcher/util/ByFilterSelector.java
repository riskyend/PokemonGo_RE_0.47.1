package com.upsight.android.analytics.internal.dispatcher.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ByFilterSelector<D>
  implements Selector<D>
{
  private Map<String, D> mData;
  private D mDefaultValue = null;
  private Set<Filter> mFilters;
  
  public ByFilterSelector(Map<String, D> paramMap)
  {
    this.mData = paramMap;
    this.mFilters = new HashSet();
    paramMap = this.mData.keySet().iterator();
    while (paramMap.hasNext())
    {
      String str = (String)paramMap.next();
      this.mFilters.add(new Filter(str));
    }
  }
  
  public ByFilterSelector(Map<String, D> paramMap, D paramD)
  {
    this(paramMap);
  }
  
  private String getFilterFor(String paramString)
  {
    Filter localFilter = null;
    Iterator localIterator = this.mFilters.iterator();
    while (localIterator.hasNext()) {
      localFilter = ((Filter)localIterator.next()).getFilterIfBetter(paramString, localFilter);
    }
    if (localFilter == null) {
      return null;
    }
    return localFilter.getFilter();
  }
  
  public D select(String paramString)
  {
    paramString = getFilterFor(paramString);
    if (paramString == null) {
      return (D)this.mDefaultValue;
    }
    return (D)this.mData.get(paramString);
  }
  
  static class Filter
  {
    private static final String SEPARATOR = ".";
    private final String mFilter;
    private final boolean mIsWildcard;
    private final String mMatcher;
    
    public Filter(String paramString)
    {
      this.mFilter = paramString;
      this.mIsWildcard = paramString.endsWith("*");
      this.mMatcher = paramString.replaceAll("\\*", "");
    }
    
    public String getFilter()
    {
      return this.mFilter;
    }
    
    public Filter getFilterIfBetter(String paramString, Filter paramFilter)
    {
      if (!this.mMatcher.equals(paramString))
      {
        localFilter = paramFilter;
        if (!paramString.startsWith(this.mMatcher)) {
          return localFilter;
        }
        if ((!this.mMatcher.endsWith(".")) && (!this.mIsWildcard))
        {
          localFilter = paramFilter;
          if (!this.mMatcher.isEmpty()) {
            return localFilter;
          }
        }
        if (paramFilter != null)
        {
          localFilter = paramFilter;
          if (paramFilter.getMatcher().length() >= this.mMatcher.length()) {
            return localFilter;
          }
        }
      }
      Filter localFilter = this;
      return localFilter;
    }
    
    public String getMatcher()
    {
      return this.mMatcher;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/util/ByFilterSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */