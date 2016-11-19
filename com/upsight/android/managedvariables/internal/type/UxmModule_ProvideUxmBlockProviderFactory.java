package com.upsight.android.managedvariables.internal.type;

import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class UxmModule_ProvideUxmBlockProviderFactory
  implements Factory<UxmBlockProvider>
{
  private final UxmModule module;
  private final Provider<UpsightContext> upsightProvider;
  private final Provider<UxmSchema> uxmSchemaProvider;
  private final Provider<String> uxmSchemaRawStringProvider;
  
  static
  {
    if (!UxmModule_ProvideUxmBlockProviderFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public UxmModule_ProvideUxmBlockProviderFactory(UxmModule paramUxmModule, Provider<UpsightContext> paramProvider, Provider<String> paramProvider1, Provider<UxmSchema> paramProvider2)
  {
    assert (paramUxmModule != null);
    this.module = paramUxmModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
    assert (paramProvider1 != null);
    this.uxmSchemaRawStringProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.uxmSchemaProvider = paramProvider2;
  }
  
  public static Factory<UxmBlockProvider> create(UxmModule paramUxmModule, Provider<UpsightContext> paramProvider, Provider<String> paramProvider1, Provider<UxmSchema> paramProvider2)
  {
    return new UxmModule_ProvideUxmBlockProviderFactory(paramUxmModule, paramProvider, paramProvider1, paramProvider2);
  }
  
  public UxmBlockProvider get()
  {
    return (UxmBlockProvider)Preconditions.checkNotNull(this.module.provideUxmBlockProvider((UpsightContext)this.upsightProvider.get(), (String)this.uxmSchemaRawStringProvider.get(), (UxmSchema)this.uxmSchemaProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/UxmModule_ProvideUxmBlockProviderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */