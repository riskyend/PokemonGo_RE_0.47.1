package com.upsight.android.internal;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ContextModule_ProvideApplicationContextFactory
  implements Factory<Context>
{
  private final ContextModule module;
  
  static
  {
    if (!ContextModule_ProvideApplicationContextFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ContextModule_ProvideApplicationContextFactory(ContextModule paramContextModule)
  {
    assert (paramContextModule != null);
    this.module = paramContextModule;
  }
  
  public static Factory<Context> create(ContextModule paramContextModule)
  {
    return new ContextModule_ProvideApplicationContextFactory(paramContextModule);
  }
  
  public Context get()
  {
    return (Context)Preconditions.checkNotNull(this.module.provideApplicationContext(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/ContextModule_ProvideApplicationContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */