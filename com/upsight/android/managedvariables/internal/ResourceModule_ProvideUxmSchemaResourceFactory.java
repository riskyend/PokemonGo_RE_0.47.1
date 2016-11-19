package com.upsight.android.managedvariables.internal;

import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ResourceModule_ProvideUxmSchemaResourceFactory
  implements Factory<Integer>
{
  private final ResourceModule module;
  
  static
  {
    if (!ResourceModule_ProvideUxmSchemaResourceFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ResourceModule_ProvideUxmSchemaResourceFactory(ResourceModule paramResourceModule)
  {
    assert (paramResourceModule != null);
    this.module = paramResourceModule;
  }
  
  public static Factory<Integer> create(ResourceModule paramResourceModule)
  {
    return new ResourceModule_ProvideUxmSchemaResourceFactory(paramResourceModule);
  }
  
  public Integer get()
  {
    return (Integer)Preconditions.checkNotNull(this.module.provideUxmSchemaResource(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/ResourceModule_ProvideUxmSchemaResourceFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */