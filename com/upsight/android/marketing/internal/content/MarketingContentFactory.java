package com.upsight.android.marketing.internal.content;

import android.text.TextUtils;
import com.upsight.android.analytics.internal.action.ActionMap;
import com.upsight.android.analytics.internal.action.ActionMapResponse;

public final class MarketingContentFactory
{
  private static final MarketingContentActions.MarketingContentActionFactory sMarketingContentActionFactory = new MarketingContentActions.MarketingContentActionFactory();
  private MarketingContentActions.MarketingContentActionContext mActionContext;
  
  public MarketingContentFactory(MarketingContentActions.MarketingContentActionContext paramMarketingContentActionContext)
  {
    this.mActionContext = paramMarketingContentActionContext;
  }
  
  public MarketingContent create(ActionMapResponse paramActionMapResponse)
  {
    Object localObject2 = null;
    String str = paramActionMapResponse.getActionMapId();
    Object localObject1 = localObject2;
    if (!TextUtils.isEmpty(str))
    {
      localObject1 = localObject2;
      if ("marketing_content_factory".equals(paramActionMapResponse.getActionFactory())) {
        localObject1 = MarketingContent.create(str, new ActionMap(sMarketingContentActionFactory, this.mActionContext, paramActionMapResponse.getActionMap()));
      }
    }
    return (MarketingContent)localObject1;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/MarketingContentFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */