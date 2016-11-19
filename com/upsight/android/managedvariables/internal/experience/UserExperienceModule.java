package com.upsight.android.managedvariables.internal.experience;

import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.managedvariables.experience.UpsightUserExperience;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class UserExperienceModule
{
  @Provides
  @Singleton
  UpsightUserExperience provideUserExperience(UpsightContext paramUpsightContext)
  {
    return new UserExperience(paramUpsightContext.getCoreComponent().bus());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/experience/UserExperienceModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */