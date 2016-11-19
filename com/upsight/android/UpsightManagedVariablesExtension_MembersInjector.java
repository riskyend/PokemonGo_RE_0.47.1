package com.upsight.android;

import com.upsight.android.managedvariables.UpsightManagedVariablesApi;
import com.upsight.android.managedvariables.internal.type.UxmBlockProvider;
import com.upsight.android.managedvariables.internal.type.UxmContentFactory;
import dagger.MembersInjector;
import javax.inject.Provider;

public final class UpsightManagedVariablesExtension_MembersInjector
  implements MembersInjector<UpsightManagedVariablesExtension>
{
  private final Provider<UpsightManagedVariablesApi> mManagedVariablesProvider;
  private final Provider<UxmBlockProvider> mUxmBlockProvider;
  private final Provider<UxmContentFactory> mUxmContentFactoryProvider;
  
  static
  {
    if (!UpsightManagedVariablesExtension_MembersInjector.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public UpsightManagedVariablesExtension_MembersInjector(Provider<UpsightManagedVariablesApi> paramProvider, Provider<UxmContentFactory> paramProvider1, Provider<UxmBlockProvider> paramProvider2)
  {
    assert (paramProvider != null);
    this.mManagedVariablesProvider = paramProvider;
    assert (paramProvider1 != null);
    this.mUxmContentFactoryProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.mUxmBlockProvider = paramProvider2;
  }
  
  public static MembersInjector<UpsightManagedVariablesExtension> create(Provider<UpsightManagedVariablesApi> paramProvider, Provider<UxmContentFactory> paramProvider1, Provider<UxmBlockProvider> paramProvider2)
  {
    return new UpsightManagedVariablesExtension_MembersInjector(paramProvider, paramProvider1, paramProvider2);
  }
  
  public static void injectMManagedVariables(UpsightManagedVariablesExtension paramUpsightManagedVariablesExtension, Provider<UpsightManagedVariablesApi> paramProvider)
  {
    paramUpsightManagedVariablesExtension.mManagedVariables = ((UpsightManagedVariablesApi)paramProvider.get());
  }
  
  public static void injectMUxmBlockProvider(UpsightManagedVariablesExtension paramUpsightManagedVariablesExtension, Provider<UxmBlockProvider> paramProvider)
  {
    paramUpsightManagedVariablesExtension.mUxmBlockProvider = ((UxmBlockProvider)paramProvider.get());
  }
  
  public static void injectMUxmContentFactory(UpsightManagedVariablesExtension paramUpsightManagedVariablesExtension, Provider<UxmContentFactory> paramProvider)
  {
    paramUpsightManagedVariablesExtension.mUxmContentFactory = ((UxmContentFactory)paramProvider.get());
  }
  
  public void injectMembers(UpsightManagedVariablesExtension paramUpsightManagedVariablesExtension)
  {
    if (paramUpsightManagedVariablesExtension == null) {
      throw new NullPointerException("Cannot inject members into a null reference");
    }
    paramUpsightManagedVariablesExtension.mManagedVariables = ((UpsightManagedVariablesApi)this.mManagedVariablesProvider.get());
    paramUpsightManagedVariablesExtension.mUxmContentFactory = ((UxmContentFactory)this.mUxmContentFactoryProvider.get());
    paramUpsightManagedVariablesExtension.mUxmBlockProvider = ((UxmBlockProvider)this.mUxmBlockProvider.get());
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/UpsightManagedVariablesExtension_MembersInjector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */