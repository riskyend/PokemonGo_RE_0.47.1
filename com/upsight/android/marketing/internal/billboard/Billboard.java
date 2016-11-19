package com.upsight.android.marketing.internal.billboard;

import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightMarketingExtension;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.marketing.UpsightBillboard;
import com.upsight.android.marketing.UpsightBillboard.Handler;
import com.upsight.android.marketing.UpsightBillboardManager;

public class Billboard
  extends UpsightBillboard
{
  private UpsightBillboardManager mBillboardManager;
  protected final UpsightBillboard.Handler mHandler;
  protected final String mScope;
  
  public Billboard(String paramString, UpsightBillboard.Handler paramHandler)
  {
    this.mScope = paramString;
    this.mHandler = paramHandler;
  }
  
  public final void destroy()
  {
    UpsightBillboardManager localUpsightBillboardManager = this.mBillboardManager;
    if (localUpsightBillboardManager != null)
    {
      localUpsightBillboardManager.unregisterBillboard(this);
      this.mBillboardManager = null;
    }
  }
  
  UpsightBillboard.Handler getHandler()
  {
    return this.mHandler;
  }
  
  String getScope()
  {
    return this.mScope;
  }
  
  public final UpsightBillboard setUp(UpsightContext paramUpsightContext)
    throws IllegalStateException
  {
    Object localObject = null;
    UpsightMarketingExtension localUpsightMarketingExtension = (UpsightMarketingExtension)paramUpsightContext.getUpsightExtension("com.upsight.extension.marketing");
    if (localUpsightMarketingExtension != null) {}
    for (paramUpsightContext = localUpsightMarketingExtension.getApi(); paramUpsightContext != null; paramUpsightContext = (UpsightContext)localObject)
    {
      this.mBillboardManager = paramUpsightContext;
      if (this.mBillboardManager.registerBillboard(this)) {
        break;
      }
      paramUpsightContext = UpsightBillboard.class.getSimpleName();
      throw new IllegalStateException("An active " + paramUpsightContext + " with the same scope already exists. A billboard remains active until either a content view is attached, or " + paramUpsightContext + "#destroy() is called.");
      paramUpsightContext.getLogger().e("Upsight", "com.upsight.extension.marketing must be registered in your Android Manifest", new Object[0]);
    }
    return this;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/billboard/Billboard.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */