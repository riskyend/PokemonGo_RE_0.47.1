package com.upsight.android.managedvariables.internal.type;

import com.google.gson.JsonParser;
import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class UxmModule_ProvideUxmSchemaJsonParserFactory
  implements Factory<JsonParser>
{
  private final UxmModule module;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!UxmModule_ProvideUxmSchemaJsonParserFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public UxmModule_ProvideUxmSchemaJsonParserFactory(UxmModule paramUxmModule, Provider<UpsightContext> paramProvider)
  {
    assert (paramUxmModule != null);
    this.module = paramUxmModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
  }
  
  public static Factory<JsonParser> create(UxmModule paramUxmModule, Provider<UpsightContext> paramProvider)
  {
    return new UxmModule_ProvideUxmSchemaJsonParserFactory(paramUxmModule, paramProvider);
  }
  
  public JsonParser get()
  {
    return (JsonParser)Preconditions.checkNotNull(this.module.provideUxmSchemaJsonParser((UpsightContext)this.upsightProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/UxmModule_ProvideUxmSchemaJsonParserFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */