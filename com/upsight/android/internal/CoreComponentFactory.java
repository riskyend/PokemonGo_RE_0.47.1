package com.upsight.android.internal;

import android.content.Context;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.UpsightCoreComponent.Factory;

public class CoreComponentFactory
  implements UpsightCoreComponent.Factory
{
  public UpsightCoreComponent create(Context paramContext)
  {
    return DaggerCoreComponent.builder().contextModule(new ContextModule(paramContext)).build();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/CoreComponentFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */