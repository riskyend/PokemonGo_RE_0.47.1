package com.upsight.android.marketing.internal.content;

import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class WebViewModule_ProvideContentTemplateWebViewClientFactoryFactory
  implements Factory<ContentTemplateWebViewClientFactory>
{
  private final WebViewModule module;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!WebViewModule_ProvideContentTemplateWebViewClientFactoryFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public WebViewModule_ProvideContentTemplateWebViewClientFactoryFactory(WebViewModule paramWebViewModule, Provider<UpsightContext> paramProvider)
  {
    assert (paramWebViewModule != null);
    this.module = paramWebViewModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
  }
  
  public static Factory<ContentTemplateWebViewClientFactory> create(WebViewModule paramWebViewModule, Provider<UpsightContext> paramProvider)
  {
    return new WebViewModule_ProvideContentTemplateWebViewClientFactoryFactory(paramWebViewModule, paramProvider);
  }
  
  public ContentTemplateWebViewClientFactory get()
  {
    return (ContentTemplateWebViewClientFactory)Preconditions.checkNotNull(this.module.provideContentTemplateWebViewClientFactory((UpsightContext)this.upsightProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/WebViewModule_ProvideContentTemplateWebViewClientFactoryFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */