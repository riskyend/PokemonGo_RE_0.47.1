package com.upsight.android.marketing.internal.content;

import com.upsight.android.marketing.UpsightContentMediator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MarketingContentMediatorManager
{
  private DefaultContentMediator mDefaultContentMediator;
  private Map<String, UpsightContentMediator> mMediators = new HashMap();
  
  MarketingContentMediatorManager(DefaultContentMediator paramDefaultContentMediator)
  {
    this.mDefaultContentMediator = paramDefaultContentMediator;
  }
  
  UpsightContentMediator getContentMediator(String paramString)
  {
    return (UpsightContentMediator)this.mMediators.get(paramString);
  }
  
  Set<String> getContentProviders()
  {
    return new HashSet(this.mMediators.keySet());
  }
  
  UpsightContentMediator getDefaultContentMediator()
  {
    return this.mDefaultContentMediator;
  }
  
  public void register(UpsightContentMediator paramUpsightContentMediator)
  {
    this.mMediators.put(paramUpsightContentMediator.getContentProvider(), paramUpsightContentMediator);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/MarketingContentMediatorManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */