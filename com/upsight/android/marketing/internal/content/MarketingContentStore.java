package com.upsight.android.marketing.internal.content;

import com.upsight.android.analytics.internal.action.ActionableStore;
import java.util.Set;

public abstract interface MarketingContentStore
  extends ActionableStore<MarketingContent>
{
  public abstract Set<String> getIdsForScope(String paramString);
  
  public abstract boolean presentScopedContent(String paramString, String[] paramArrayOfString);
  
  public abstract boolean presentScopelessContent(String paramString1, String paramString2);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/MarketingContentStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */