package com.upsight.android.internal;

import com.squareup.otto.Bus;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ContextModule_ProvideBusFactory
  implements Factory<Bus>
{
  private final ContextModule module;
  
  static
  {
    if (!ContextModule_ProvideBusFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ContextModule_ProvideBusFactory(ContextModule paramContextModule)
  {
    assert (paramContextModule != null);
    this.module = paramContextModule;
  }
  
  public static Factory<Bus> create(ContextModule paramContextModule)
  {
    return new ContextModule_ProvideBusFactory(paramContextModule);
  }
  
  public Bus get()
  {
    return (Bus)Preconditions.checkNotNull(this.module.provideBus(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/ContextModule_ProvideBusFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */