package com.upsight.android.internal;

import com.upsight.android.internal.persistence.storable.StorableIdFactory;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ContextModule_ProvideTypeIdGeneratorFactory
  implements Factory<StorableIdFactory>
{
  private final ContextModule module;
  
  static
  {
    if (!ContextModule_ProvideTypeIdGeneratorFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ContextModule_ProvideTypeIdGeneratorFactory(ContextModule paramContextModule)
  {
    assert (paramContextModule != null);
    this.module = paramContextModule;
  }
  
  public static Factory<StorableIdFactory> create(ContextModule paramContextModule)
  {
    return new ContextModule_ProvideTypeIdGeneratorFactory(paramContextModule);
  }
  
  public StorableIdFactory get()
  {
    return (StorableIdFactory)Preconditions.checkNotNull(this.module.provideTypeIdGenerator(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/ContextModule_ProvideTypeIdGeneratorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */