package com.upsight.android.internal;

import com.google.gson.JsonParser;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class JsonModule_ProvideJsonParserFactory
  implements Factory<JsonParser>
{
  private final JsonModule module;
  
  static
  {
    if (!JsonModule_ProvideJsonParserFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public JsonModule_ProvideJsonParserFactory(JsonModule paramJsonModule)
  {
    assert (paramJsonModule != null);
    this.module = paramJsonModule;
  }
  
  public static Factory<JsonParser> create(JsonModule paramJsonModule)
  {
    return new JsonModule_ProvideJsonParserFactory(paramJsonModule);
  }
  
  public JsonParser get()
  {
    return (JsonParser)Preconditions.checkNotNull(this.module.provideJsonParser(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/JsonModule_ProvideJsonParserFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */