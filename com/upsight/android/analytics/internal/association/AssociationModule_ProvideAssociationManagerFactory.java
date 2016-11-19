package com.upsight.android.analytics.internal.association;

import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.internal.session.Clock;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class AssociationModule_ProvideAssociationManagerFactory
  implements Factory<AssociationManager>
{
  private final Provider<Clock> clockProvider;
  private final AssociationModule module;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!AssociationModule_ProvideAssociationManagerFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public AssociationModule_ProvideAssociationManagerFactory(AssociationModule paramAssociationModule, Provider<UpsightContext> paramProvider, Provider<Clock> paramProvider1)
  {
    assert (paramAssociationModule != null);
    this.module = paramAssociationModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
    assert (paramProvider1 != null);
    this.clockProvider = paramProvider1;
  }
  
  public static Factory<AssociationManager> create(AssociationModule paramAssociationModule, Provider<UpsightContext> paramProvider, Provider<Clock> paramProvider1)
  {
    return new AssociationModule_ProvideAssociationManagerFactory(paramAssociationModule, paramProvider, paramProvider1);
  }
  
  public AssociationManager get()
  {
    return (AssociationManager)Preconditions.checkNotNull(this.module.provideAssociationManager((UpsightContext)this.upsightProvider.get(), (Clock)this.clockProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/association/AssociationModule_ProvideAssociationManagerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */