package com.upsight.android.analytics.internal.dispatcher.delivery;

import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.internal.session.Clock;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import rx.Scheduler;

public final class DeliveryModule_ProvideQueueBuilderFactory
  implements Factory<QueueBuilder>
{
  private final Provider<Clock> clockProvider;
  private final DeliveryModule module;
  private final Provider<ResponseParser> responseParserProvider;
  private final Provider<Scheduler> retryExecutorProvider;
  private final Provider<Scheduler> sendExecutorProvider;
  private final Provider<SignatureVerifier> signatureVerifierProvider;
  private final Provider<UpsightContext> upsightProvider;
  
  static
  {
    if (!DeliveryModule_ProvideQueueBuilderFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public DeliveryModule_ProvideQueueBuilderFactory(DeliveryModule paramDeliveryModule, Provider<UpsightContext> paramProvider, Provider<Clock> paramProvider1, Provider<Scheduler> paramProvider2, Provider<Scheduler> paramProvider3, Provider<SignatureVerifier> paramProvider4, Provider<ResponseParser> paramProvider5)
  {
    assert (paramDeliveryModule != null);
    this.module = paramDeliveryModule;
    assert (paramProvider != null);
    this.upsightProvider = paramProvider;
    assert (paramProvider1 != null);
    this.clockProvider = paramProvider1;
    assert (paramProvider2 != null);
    this.retryExecutorProvider = paramProvider2;
    assert (paramProvider3 != null);
    this.sendExecutorProvider = paramProvider3;
    assert (paramProvider4 != null);
    this.signatureVerifierProvider = paramProvider4;
    assert (paramProvider5 != null);
    this.responseParserProvider = paramProvider5;
  }
  
  public static Factory<QueueBuilder> create(DeliveryModule paramDeliveryModule, Provider<UpsightContext> paramProvider, Provider<Clock> paramProvider1, Provider<Scheduler> paramProvider2, Provider<Scheduler> paramProvider3, Provider<SignatureVerifier> paramProvider4, Provider<ResponseParser> paramProvider5)
  {
    return new DeliveryModule_ProvideQueueBuilderFactory(paramDeliveryModule, paramProvider, paramProvider1, paramProvider2, paramProvider3, paramProvider4, paramProvider5);
  }
  
  public QueueBuilder get()
  {
    return (QueueBuilder)Preconditions.checkNotNull(this.module.provideQueueBuilder((UpsightContext)this.upsightProvider.get(), (Clock)this.clockProvider.get(), (Scheduler)this.retryExecutorProvider.get(), (Scheduler)this.sendExecutorProvider.get(), (SignatureVerifier)this.signatureVerifierProvider.get(), this.responseParserProvider), "Cannot return null from a non-@Nullable @Provides method");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/delivery/DeliveryModule_ProvideQueueBuilderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */