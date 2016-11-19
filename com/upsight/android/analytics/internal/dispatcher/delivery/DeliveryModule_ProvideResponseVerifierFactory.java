package com.upsight.android.analytics.internal.dispatcher.delivery;

import com.upsight.android.UpsightContext;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class DeliveryModule_ProvideResponseVerifierFactory
  implements Factory<SignatureVerifier>
{
  private final DeliveryModule module;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!DeliveryModule_ProvideResponseVerifierFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public DeliveryModule_ProvideResponseVerifierFactory(DeliveryModule paramDeliveryModule, Provider<UpsightContext> paramProvider)
  {
    assert (paramDeliveryModule != null);
    this.module = paramDeliveryModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
  }
  
  public static Factory<SignatureVerifier> create(DeliveryModule paramDeliveryModule, Provider<UpsightContext> paramProvider)
  {
    return new DeliveryModule_ProvideResponseVerifierFactory(paramDeliveryModule, paramProvider);
  }
  
  public SignatureVerifier get()
  {
    return (SignatureVerifier)Preconditions.checkNotNull(this.module.provideResponseVerifier((UpsightContext)this.upsightProvider.get()), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/delivery/DeliveryModule_ProvideResponseVerifierFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */