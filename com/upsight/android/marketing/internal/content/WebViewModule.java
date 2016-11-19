package com.upsight.android.marketing.internal.content;

import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public final class WebViewModule
{
  @Provides
  @Singleton
  ContentTemplateWebViewClientFactory provideContentTemplateWebViewClientFactory(UpsightContext paramUpsightContext)
  {
    UpsightCoreComponent localUpsightCoreComponent = paramUpsightContext.getCoreComponent();
    return new ContentTemplateWebViewClientFactory(localUpsightCoreComponent.bus(), localUpsightCoreComponent.gson(), localUpsightCoreComponent.jsonParser(), paramUpsightContext.getLogger());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/WebViewModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */