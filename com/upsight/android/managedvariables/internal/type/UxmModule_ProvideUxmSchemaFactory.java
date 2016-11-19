package com.upsight.android.managedvariables.internal.type;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class UxmModule_ProvideUxmSchemaFactory
  implements Factory<UxmSchema>
{
  private final UxmModule module;
  private final Provider<UpsightContext> upsightProvider;
  private final Provider<Gson> uxmSchemaGsonProvider;
  private final Provider<JsonParser> uxmSchemaJsonParserProvider;
  private final Provider<String> uxmSchemaStringProvider;
  
  static
  {
    if (!UxmModule_ProvideUxmSchemaFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public UxmModule_ProvideUxmSchemaFactory(UxmModule paramUxmModule, Provider<UpsightContext> paramProvider, Provider<Gson> paramProvider1, Provider<JsonParser> paramProvider2, Provider<String> paramProvider3)
  {
    assert (paramUxmModule != null);
    this.module = paramUxmModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
    assert (paramProvider1 != null);
    this.uxmSchemaGsonProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.uxmSchemaJsonParserProvider = paramProvider2;
    assert (paramProvider3 != null);
    this.uxmSchemaStringProvider = paramProvider3;
  }
  
  public static Factory<UxmSchema> create(UxmModule paramUxmModule, Provider<UpsightContext> paramProvider, Provider<Gson> paramProvider1, Provider<JsonParser> paramProvider2, Provider<String> paramProvider3)
  {
    return new UxmModule_ProvideUxmSchemaFactory(paramUxmModule, paramProvider, paramProvider1, paramProvider2, paramProvider3);
  }
  
  public UxmSchema get()
  {
    return (UxmSchema)Preconditions.checkNotNull(this.module.provideUxmSchema((UpsightContext)this.upsightProvider.get(), (Gson)this.uxmSchemaGsonProvider.get(), (JsonParser)this.uxmSchemaJsonParserProvider.get(), (String)this.uxmSchemaStringProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/UxmModule_ProvideUxmSchemaFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */