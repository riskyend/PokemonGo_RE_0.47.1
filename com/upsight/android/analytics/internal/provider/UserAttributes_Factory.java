package com.upsight.android.analytics.internal.provider;

import com.upsight.android.UpsightContext;
import dagger.MembersInjector;
import dagger.internal.Factory;
import dagger.internal.MembersInjectors;
import javax.inject.Provider;

public final class UserAttributes_Factory
  implements Factory<UserAttributes>
{
  private final Provider<UpsightContext> upsightProvider;
  private final MembersInjector<UserAttributes> userAttributesMembersInjector;
  
  static
  {
    if (!UserAttributes_Factory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public UserAttributes_Factory(MembersInjector<UserAttributes> paramMembersInjector, Provider<UpsightContext> paramProvider)
  {
    assert (paramMembersInjector != null);
    this.userAttributesMembersInjector = paramMembersInjector;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
  }
  
  public static Factory<UserAttributes> create(MembersInjector<UserAttributes> paramMembersInjector, Provider<UpsightContext> paramProvider)
  {
    return new UserAttributes_Factory(paramMembersInjector, paramProvider);
  }
  
  public UserAttributes get()
  {
    return (UserAttributes)MembersInjectors.injectMembers(this.userAttributesMembersInjector, new UserAttributes((UpsightContext)this.upsightProvider.get()));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/provider/UserAttributes_Factory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */