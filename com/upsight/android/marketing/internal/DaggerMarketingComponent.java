package com.upsight.android.marketing.internal;

import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightMarketingExtension;
import com.upsight.android.UpsightMarketingExtension_MembersInjector;
import com.upsight.android.marketing.UpsightBillboardManager;
import com.upsight.android.marketing.UpsightMarketingApi;
import com.upsight.android.marketing.UpsightMarketingContentStore;
import com.upsight.android.marketing.internal.billboard.BillboardDialogFragment;
import com.upsight.android.marketing.internal.billboard.BillboardDialogFragment_MembersInjector;
import com.upsight.android.marketing.internal.billboard.BillboardManagementActivity;
import com.upsight.android.marketing.internal.billboard.BillboardManagementActivity_MembersInjector;
import com.upsight.android.marketing.internal.billboard.BillboardModule;
import com.upsight.android.marketing.internal.billboard.BillboardModule_ProvideBillboardManagerFactory;
import com.upsight.android.marketing.internal.content.ContentModule;
import com.upsight.android.marketing.internal.content.ContentModule_ProvideDefaultContentMediatorFactory;
import com.upsight.android.marketing.internal.content.ContentModule_ProvideMarketingContentFactoryFactory;
import com.upsight.android.marketing.internal.content.ContentModule_ProvideMarketingContentMediatorManagerFactory;
import com.upsight.android.marketing.internal.content.ContentModule_ProvideMarketingContentStoreFactory;
import com.upsight.android.marketing.internal.content.ContentModule_ProvideMarketingContentStoreImplFactory;
import com.upsight.android.marketing.internal.content.ContentModule_ProvideUpsightMarketingContentStoreFactory;
import com.upsight.android.marketing.internal.content.ContentTemplateWebViewClientFactory;
import com.upsight.android.marketing.internal.content.DefaultContentMediator;
import com.upsight.android.marketing.internal.content.MarketingContentFactory;
import com.upsight.android.marketing.internal.content.MarketingContentMediatorManager;
import com.upsight.android.marketing.internal.content.MarketingContentStore;
import com.upsight.android.marketing.internal.content.WebViewModule;
import com.upsight.android.marketing.internal.content.WebViewModule_ProvideContentTemplateWebViewClientFactoryFactory;
import dagger.MembersInjector;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerMarketingComponent
  implements MarketingComponent
{
  private MembersInjector<BillboardDialogFragment> billboardDialogFragmentMembersInjector;
  private MembersInjector<BillboardManagementActivity> billboardManagementActivityMembersInjector;
  private Provider<UpsightBillboardManager> provideBillboardManagerProvider;
  private Provider<ContentTemplateWebViewClientFactory> provideContentTemplateWebViewClientFactoryProvider;
  private Provider<DefaultContentMediator> provideDefaultContentMediatorProvider;
  private Provider<Scheduler> provideMainSchedulerProvider;
  private Provider<UpsightMarketingApi> provideMarketingApiProvider;
  private Provider<MarketingContentFactory> provideMarketingContentFactoryProvider;
  private Provider<MarketingContentMediatorManager> provideMarketingContentMediatorManagerProvider;
  private Provider provideMarketingContentStoreImplProvider;
  private Provider<MarketingContentStore> provideMarketingContentStoreProvider;
  private Provider<UpsightContext> provideUpsightContextProvider;
  private Provider<UpsightMarketingContentStore> provideUpsightMarketingContentStoreProvider;
  private MembersInjector<UpsightMarketingExtension> upsightMarketingExtensionMembersInjector;
  
  static
  {
    if (!DaggerMarketingComponent.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  private DaggerMarketingComponent(Builder paramBuilder)
  {
    assert (paramBuilder != null);
    initialize(paramBuilder);
  }
  
  public static Builder builder()
  {
    return new Builder(null);
  }
  
  private void initialize(Builder paramBuilder)
  {
    this.provideUpsightContextProvider = DoubleCheck.provider(BaseMarketingModule_ProvideUpsightContextFactory.create(paramBuilder.baseMarketingModule));
    this.provideMarketingContentStoreImplProvider = DoubleCheck.provider(ContentModule_ProvideMarketingContentStoreImplFactory.create(paramBuilder.contentModule, this.provideUpsightContextProvider));
    this.provideMarketingContentStoreProvider = DoubleCheck.provider(ContentModule_ProvideMarketingContentStoreFactory.create(paramBuilder.contentModule, this.provideMarketingContentStoreImplProvider));
    this.provideBillboardManagerProvider = DoubleCheck.provider(BillboardModule_ProvideBillboardManagerFactory.create(paramBuilder.billboardModule, this.provideUpsightContextProvider, this.provideMarketingContentStoreProvider));
    this.provideUpsightMarketingContentStoreProvider = DoubleCheck.provider(ContentModule_ProvideUpsightMarketingContentStoreFactory.create(paramBuilder.contentModule, this.provideMarketingContentStoreImplProvider));
    this.provideDefaultContentMediatorProvider = DoubleCheck.provider(ContentModule_ProvideDefaultContentMediatorFactory.create(paramBuilder.contentModule));
    this.provideMarketingContentMediatorManagerProvider = DoubleCheck.provider(ContentModule_ProvideMarketingContentMediatorManagerFactory.create(paramBuilder.contentModule, this.provideDefaultContentMediatorProvider));
    this.provideMarketingApiProvider = DoubleCheck.provider(BaseMarketingModule_ProvideMarketingApiFactory.create(paramBuilder.baseMarketingModule, this.provideBillboardManagerProvider, this.provideUpsightMarketingContentStoreProvider, this.provideMarketingContentMediatorManagerProvider));
    this.provideMainSchedulerProvider = DoubleCheck.provider(BaseMarketingModule_ProvideMainSchedulerFactory.create(paramBuilder.baseMarketingModule));
    this.provideContentTemplateWebViewClientFactoryProvider = DoubleCheck.provider(WebViewModule_ProvideContentTemplateWebViewClientFactoryFactory.create(paramBuilder.webViewModule, this.provideUpsightContextProvider));
    this.provideMarketingContentFactoryProvider = DoubleCheck.provider(ContentModule_ProvideMarketingContentFactoryFactory.create(paramBuilder.contentModule, this.provideUpsightContextProvider, this.provideMainSchedulerProvider, this.provideMarketingContentMediatorManagerProvider, this.provideMarketingContentStoreProvider, this.provideContentTemplateWebViewClientFactoryProvider));
    this.upsightMarketingExtensionMembersInjector = UpsightMarketingExtension_MembersInjector.create(this.provideMarketingApiProvider, this.provideMarketingContentFactoryProvider, this.provideDefaultContentMediatorProvider);
    this.billboardManagementActivityMembersInjector = BillboardManagementActivity_MembersInjector.create(this.provideUpsightContextProvider, this.provideMarketingContentStoreProvider);
    this.billboardDialogFragmentMembersInjector = BillboardDialogFragment_MembersInjector.create(this.provideUpsightContextProvider, this.provideMarketingContentStoreProvider);
  }
  
  public void inject(UpsightMarketingExtension paramUpsightMarketingExtension)
  {
    this.upsightMarketingExtensionMembersInjector.injectMembers(paramUpsightMarketingExtension);
  }
  
  public void inject(BillboardDialogFragment paramBillboardDialogFragment)
  {
    this.billboardDialogFragmentMembersInjector.injectMembers(paramBillboardDialogFragment);
  }
  
  public void inject(BillboardManagementActivity paramBillboardManagementActivity)
  {
    this.billboardManagementActivityMembersInjector.injectMembers(paramBillboardManagementActivity);
  }
  
  public static final class Builder
  {
    private BaseMarketingModule baseMarketingModule;
    private BillboardModule billboardModule;
    private ContentModule contentModule;
    private WebViewModule webViewModule;
    
    public Builder baseMarketingModule(BaseMarketingModule paramBaseMarketingModule)
    {
      this.baseMarketingModule = ((BaseMarketingModule)Preconditions.checkNotNull(paramBaseMarketingModule));
      return this;
    }
    
    public Builder billboardModule(BillboardModule paramBillboardModule)
    {
      this.billboardModule = ((BillboardModule)Preconditions.checkNotNull(paramBillboardModule));
      return this;
    }
    
    public MarketingComponent build()
    {
      if (this.baseMarketingModule == null) {
        throw new IllegalStateException(BaseMarketingModule.class.getCanonicalName() + " must be set");
      }
      if (this.contentModule == null) {
        this.contentModule = new ContentModule();
      }
      if (this.billboardModule == null) {
        this.billboardModule = new BillboardModule();
      }
      if (this.webViewModule == null) {
        this.webViewModule = new WebViewModule();
      }
      return new DaggerMarketingComponent(this, null);
    }
    
    public Builder contentModule(ContentModule paramContentModule)
    {
      this.contentModule = ((ContentModule)Preconditions.checkNotNull(paramContentModule));
      return this;
    }
    
    @Deprecated
    public Builder marketingModule(MarketingModule paramMarketingModule)
    {
      Preconditions.checkNotNull(paramMarketingModule);
      return this;
    }
    
    public Builder webViewModule(WebViewModule paramWebViewModule)
    {
      this.webViewModule = ((WebViewModule)Preconditions.checkNotNull(paramWebViewModule));
      return this;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/DaggerMarketingComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */