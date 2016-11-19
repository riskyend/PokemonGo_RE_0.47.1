package com.upsight.android.analytics.internal;

import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.analytics.UpsightGooglePlayHelper;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.internal.util.Opt;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public final class BaseAnalyticsModule
{
  public static final String OPT_UNCAUGHT_EXCEPTION_HANDLER = "optUncaughtExceptionHandler";
  private final UpsightContext mUpsight;
  
  public BaseAnalyticsModule(UpsightContext paramUpsightContext)
  {
    this.mUpsight = paramUpsightContext;
  }
  
  @Provides
  @Singleton
  public Clock provideClock()
  {
    new Clock()
    {
      public long currentTimeMillis()
      {
        return System.currentTimeMillis();
      }
      
      public long currentTimeSeconds()
      {
        return TimeUnit.SECONDS.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
      }
    };
  }
  
  @Provides
  @Singleton
  public UpsightGooglePlayHelper provideGooglePlayHelper(UpsightContext paramUpsightContext)
  {
    return new GooglePlayHelper(paramUpsightContext, paramUpsightContext.getCoreComponent().gson());
  }
  
  @Provides
  @Named("optUncaughtExceptionHandler")
  @Singleton
  public Opt<Thread.UncaughtExceptionHandler> provideUncaughtExceptionHandler()
  {
    return Opt.absent();
  }
  
  @Provides
  @Singleton
  public UpsightContext provideUpsightContext()
  {
    return this.mUpsight;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/BaseAnalyticsModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */