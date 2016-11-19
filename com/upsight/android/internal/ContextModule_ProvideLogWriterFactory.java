package com.upsight.android.internal;

import com.upsight.android.internal.logger.LogWriter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

public final class ContextModule_ProvideLogWriterFactory
  implements Factory<LogWriter>
{
  private final ContextModule module;
  
  static
  {
    if (!ContextModule_ProvideLogWriterFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public ContextModule_ProvideLogWriterFactory(ContextModule paramContextModule)
  {
    assert (paramContextModule != null);
    this.module = paramContextModule;
  }
  
  public static Factory<LogWriter> create(ContextModule paramContextModule)
  {
    return new ContextModule_ProvideLogWriterFactory(paramContextModule);
  }
  
  public LogWriter get()
  {
    return (LogWriter)Preconditions.checkNotNull(this.module.provideLogWriter(), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/ContextModule_ProvideLogWriterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */