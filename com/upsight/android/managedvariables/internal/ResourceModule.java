package com.upsight.android.managedvariables.internal;

import com.upsight.android.managedvariables.R.raw;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class ResourceModule
{
  public static final String RES_UXM_SCHEMA = "resUxmSchema";
  
  @Provides
  @Named("resUxmSchema")
  @Singleton
  Integer provideUxmSchemaResource()
  {
    return Integer.valueOf(R.raw.uxm_schema);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/ResourceModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */