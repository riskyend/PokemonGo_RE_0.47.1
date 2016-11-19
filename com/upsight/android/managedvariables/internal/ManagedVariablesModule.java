package com.upsight.android.managedvariables.internal;

import com.upsight.android.managedvariables.internal.experience.UserExperienceModule;
import com.upsight.android.managedvariables.internal.type.UxmModule;
import dagger.Module;

@Module(includes={ResourceModule.class, UxmModule.class, UserExperienceModule.class, BaseManagedVariablesModule.class})
public class ManagedVariablesModule {}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/ManagedVariablesModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */