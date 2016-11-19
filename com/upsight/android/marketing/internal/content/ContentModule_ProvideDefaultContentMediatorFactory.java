package com.upsight.android.marketing.internal.content;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ContentModule_ProvideDefaultContentMediatorFactory
  implements Factory<DefaultContentMediator>
{
  private final ContentModule module;
  
  static
  {
    if (!ContentModule_ProvideDefaultContentMediatorFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ContentModule_ProvideDefaultContentMediatorFactory(ContentModule paramContentModule)
  {
    assert (paramContentModule != null);
    this.module = paramContentModule;
  }
  
  public static Factory<DefaultContentMediator> create(ContentModule paramContentModule)
  {
    return new ContentModule_ProvideDefaultContentMediatorFactory(paramContentModule);
  }
  
  public DefaultContentMediator get()
  {
    return (DefaultContentMediator)Preconditions.checkNotNull(this.module.provideDefaultContentMediator(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/ContentModule_ProvideDefaultContentMediatorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */