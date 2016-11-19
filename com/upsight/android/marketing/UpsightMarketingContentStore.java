package com.upsight.android.marketing;

import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightMarketingExtension;
import com.upsight.android.logger.UpsightLogger;

public abstract class UpsightMarketingContentStore
{
  public static boolean isContentReady(UpsightContext paramUpsightContext, String paramString)
  {
    UpsightMarketingExtension localUpsightMarketingExtension = (UpsightMarketingExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.marketing");
    if (localUpsightMarketingExtension != null) {
      return localUpsightMarketingExtension.getApi().isContentReady(paramString);
    }
    paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.marketing must be registered in your Android Manifest", new Object[0]);
    return false;
  }
  
  public abstract boolean isContentReady(String paramString);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/UpsightMarketingContentStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */