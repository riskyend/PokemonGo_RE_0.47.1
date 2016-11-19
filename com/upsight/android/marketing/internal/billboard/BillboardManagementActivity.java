package com.upsight.android.marketing.internal.billboard;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.upsight.android.Upsight;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightCoreComponent;
import com.upsight.android.UpsightMarketingExtension;
import com.upsight.android.analytics.internal.session.ApplicationStatus;
import com.upsight.android.analytics.internal.session.ApplicationStatus.State;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.marketing.R.layout;
import com.upsight.android.marketing.R.style;
import com.upsight.android.marketing.UpsightBillboard.Handler;
import com.upsight.android.marketing.UpsightBillboard.PresentationStyle;
import com.upsight.android.marketing.UpsightContentMediator;
import com.upsight.android.marketing.UpsightMarketingComponent;
import com.upsight.android.marketing.internal.content.MarketingContent;
import com.upsight.android.marketing.internal.content.MarketingContent.PendingDialog;
import com.upsight.android.marketing.internal.content.MarketingContent.ScopelessAvailabilityEvent;
import com.upsight.android.marketing.internal.content.MarketingContent.SubcontentAvailabilityEvent;
import com.upsight.android.marketing.internal.content.MarketingContent.SubdialogAvailabilityEvent;
import com.upsight.android.marketing.internal.content.MarketingContentActions.DestroyEvent;
import com.upsight.android.marketing.internal.content.MarketingContentStore;
import com.upsight.android.persistence.UpsightDataStore;
import com.upsight.android.persistence.UpsightSubscription;
import com.upsight.android.persistence.annotation.Created;
import com.upsight.android.persistence.annotation.Updated;
import java.util.Queue;
import java.util.Set;
import javax.inject.Inject;

public class BillboardManagementActivity
  extends Activity
{
  static final String INTENT_EXTRA_MARKETING_CONTENT_DIALOG_THEME = "marketingContentDialogTheme";
  static final String INTENT_EXTRA_MARKETING_CONTENT_ID = "marketingContentId";
  static final String INTENT_EXTRA_MARKETING_CONTENT_PREFERRED_STYLE = "marketingContentPreferredStyle";
  private static final String LOG_TAG = "BillboardActivity";
  private static final int STYLE_DIALOG = R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_UpsightDialog;
  private static final int STYLE_FULLSCREEN = 16974122;
  private Billboard mBillboard = null;
  private MarketingContent mContent = null;
  @Inject
  MarketingContentStore mContentStore;
  private UpsightSubscription mDataStoreSubscription = null;
  private BillboardFragment mFragment = null;
  private boolean mIsForeground = false;
  private boolean mShouldAttachOnResume = false;
  @Inject
  UpsightContext mUpsight;
  
  private void handle(MarketingContent.ScopelessAvailabilityEvent paramScopelessAvailabilityEvent)
  {
    MarketingContent localMarketingContent = this.mContent;
    if ((localMarketingContent != null) && (localMarketingContent.getId().equals(paramScopelessAvailabilityEvent.getParentId())))
    {
      Object localObject = this.mBillboard;
      if (localObject != null)
      {
        paramScopelessAvailabilityEvent = (MarketingContent)this.mContentStore.get(paramScopelessAvailabilityEvent.getId());
        if ((paramScopelessAvailabilityEvent != null) && (paramScopelessAvailabilityEvent.isAvailable()))
        {
          UpsightContentMediator localUpsightContentMediator1 = localMarketingContent.getContentMediator();
          UpsightContentMediator localUpsightContentMediator2 = paramScopelessAvailabilityEvent.getContentMediator();
          if ((localUpsightContentMediator1 != null) && (localUpsightContentMediator2 != null))
          {
            this.mContent = paramScopelessAvailabilityEvent;
            paramScopelessAvailabilityEvent.bindBillboard((Billboard)localObject);
            ((Billboard)localObject).getHandler().onNextView();
            localObject = getFragmentManager();
            localUpsightContentMediator1.hideContent(localMarketingContent, (FragmentManager)localObject, this.mFragment);
            localUpsightContentMediator2.displayContent(paramScopelessAvailabilityEvent, (FragmentManager)localObject, this.mFragment);
          }
        }
      }
    }
  }
  
  private void handle(MarketingContent.SubcontentAvailabilityEvent paramSubcontentAvailabilityEvent)
  {
    MarketingContent localMarketingContent = this.mContent;
    if ((localMarketingContent != null) && (paramSubcontentAvailabilityEvent.getId().equals(localMarketingContent.getId()))) {
      attachBillboard(localMarketingContent);
    }
  }
  
  private void handle(MarketingContent.SubdialogAvailabilityEvent paramSubdialogAvailabilityEvent)
  {
    MarketingContent localMarketingContent = this.mContent;
    if ((localMarketingContent != null) && (paramSubdialogAvailabilityEvent.getId().equals(localMarketingContent.getId()))) {
      attachDialog(paramSubdialogAvailabilityEvent.getPendingDialog());
    }
  }
  
  private void handle(MarketingContentActions.DestroyEvent paramDestroyEvent)
  {
    MarketingContent localMarketingContent = this.mContent;
    if ((localMarketingContent != null) && (localMarketingContent.getId().equals(paramDestroyEvent.mId))) {
      detachBillboard();
    }
  }
  
  void attachBillboard(MarketingContent paramMarketingContent)
  {
    this.mUpsight.getLogger().d("BillboardActivity", "Attach billboard activity=" + this + " marketingContentId=" + paramMarketingContent.getId(), new Object[0]);
    UpsightContentMediator localUpsightContentMediator = paramMarketingContent.getContentMediator();
    UpsightBillboard.PresentationStyle localPresentationStyle;
    int i;
    if (localUpsightContentMediator != null)
    {
      localPresentationStyle = (UpsightBillboard.PresentationStyle)getIntent().getSerializableExtra("marketingContentPreferredStyle");
      if (localPresentationStyle != null)
      {
        localObject = localPresentationStyle;
        if (!localPresentationStyle.equals(UpsightBillboard.PresentationStyle.None)) {}
      }
      else
      {
        localObject = localUpsightContentMediator.getPresentationStyle(paramMarketingContent);
      }
      localPresentationStyle = null;
      switch (localObject)
      {
      default: 
        i = 16974122;
      }
    }
    for (Object localObject = localPresentationStyle;; localObject = localUpsightContentMediator.getDimensions(paramMarketingContent))
    {
      this.mFragment = BillboardFragment.newInstance(this, (Set)localObject);
      this.mFragment.setStyle(1, i);
      this.mFragment.setCancelable(false);
      localUpsightContentMediator.displayContent(paramMarketingContent, getFragmentManager(), this.mFragment);
      return;
      i = STYLE_DIALOG;
    }
  }
  
  void attachDialog(MarketingContent.PendingDialog paramPendingDialog)
  {
    this.mUpsight.getLogger().d("BillboardActivity", "Attach dialog activity=" + this + " marketingContentId=" + paramPendingDialog.mId, new Object[0]);
    Intent localIntent = getIntent();
    if (localIntent.hasExtra("marketingContentDialogTheme"))
    {
      BillboardDialogFragment.newInstance(paramPendingDialog, localIntent.getIntExtra("marketingContentDialogTheme", Integer.MIN_VALUE)).show(getFragmentManager(), null);
      return;
    }
    BillboardDialogFragment.newInstance(paramPendingDialog).show(getFragmentManager(), null);
  }
  
  void detachBillboard()
  {
    Billboard localBillboard = this.mBillboard;
    if (localBillboard != null)
    {
      this.mUpsight.getLogger().d("BillboardActivity", "Detach billboard activity=" + this + " scope=" + localBillboard.getScope(), new Object[0]);
      BillboardFragment localBillboardFragment = this.mFragment;
      if ((localBillboardFragment != null) && (localBillboardFragment.isAdded())) {
        localBillboardFragment.dismiss();
      }
      this.mContent = null;
      this.mBillboard = null;
      this.mFragment = null;
      finish();
      localBillboard.getHandler().onDetach();
    }
  }
  
  @Subscribe
  public void handleActionEvent(MarketingContentActions.DestroyEvent paramDestroyEvent)
  {
    this.mUpsight.getLogger().d("BillboardActivity", "Received DestroyEvent activity=" + this + " marketingContentId=" + paramDestroyEvent.mId, new Object[0]);
    if (this.mIsForeground) {
      handle(paramDestroyEvent);
    }
    MarketingContent localMarketingContent;
    do
    {
      return;
      localMarketingContent = this.mContent;
    } while (localMarketingContent == null);
    localMarketingContent.getEventQueue().add(paramDestroyEvent);
  }
  
  @Subscribe
  public void handleAvailabilityEvent(MarketingContent.ScopelessAvailabilityEvent paramScopelessAvailabilityEvent)
  {
    this.mUpsight.getLogger().d("BillboardActivity", "Received ScopelessAvailabilityEvent activity=" + this + " marketingContentId=" + paramScopelessAvailabilityEvent.getId(), new Object[0]);
    if (this.mIsForeground) {
      handle(paramScopelessAvailabilityEvent);
    }
    MarketingContent localMarketingContent;
    do
    {
      return;
      localMarketingContent = this.mContent;
    } while (localMarketingContent == null);
    localMarketingContent.getEventQueue().add(paramScopelessAvailabilityEvent);
  }
  
  @Subscribe
  public void handleAvailabilityEvent(MarketingContent.SubcontentAvailabilityEvent paramSubcontentAvailabilityEvent)
  {
    this.mUpsight.getLogger().d("BillboardActivity", "Received SubcontentAvailabilityEvent activity=" + this + " marketingContentId=" + paramSubcontentAvailabilityEvent.getId(), new Object[0]);
    if (this.mIsForeground) {
      handle(paramSubcontentAvailabilityEvent);
    }
    MarketingContent localMarketingContent;
    do
    {
      return;
      localMarketingContent = this.mContent;
    } while (localMarketingContent == null);
    localMarketingContent.getEventQueue().add(paramSubcontentAvailabilityEvent);
  }
  
  @Subscribe
  public void handleAvailabilityEvent(MarketingContent.SubdialogAvailabilityEvent paramSubdialogAvailabilityEvent)
  {
    this.mUpsight.getLogger().d("BillboardActivity", "Received SubdialogAvailabilityEvent activity=" + this + " marketingContentId=" + paramSubdialogAvailabilityEvent.getId(), new Object[0]);
    if (this.mIsForeground) {
      handle(paramSubdialogAvailabilityEvent);
    }
    MarketingContent localMarketingContent;
    do
    {
      return;
      localMarketingContent = this.mContent;
    } while (localMarketingContent == null);
    localMarketingContent.getEventQueue().add(paramSubdialogAvailabilityEvent);
  }
  
  @Created
  @Updated
  public void onApplicationStatus(ApplicationStatus paramApplicationStatus)
  {
    if (paramApplicationStatus.getState() == ApplicationStatus.State.BACKGROUND)
    {
      this.mUpsight.getLogger().d("BillboardActivity", "Received application background event activity=" + this, new Object[0]);
      paramApplicationStatus = this.mContent;
      if (paramApplicationStatus != null) {
        paramApplicationStatus.executeActions("app_backgrounded");
      }
    }
  }
  
  public void onAttachFragment(Fragment paramFragment)
  {
    super.onAttachFragment(paramFragment);
    if ((paramFragment instanceof BillboardFragment)) {
      this.mFragment = ((BillboardFragment)paramFragment);
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Object localObject = (UpsightMarketingExtension)Upsight.createContext(this).getUpsightExtension("com.upsight.extension.marketing");
    if (localObject == null) {
      return;
    }
    ((UpsightMarketingComponent)((UpsightMarketingExtension)localObject).getComponent()).inject(this);
    setContentView(R.layout.upsight_activity_billboard_management);
    localObject = getIntent().getStringExtra("marketingContentId");
    this.mUpsight.getLogger().d("BillboardActivity", "onCreate activity=" + this + " marketingContentId=" + (String)localObject, new Object[0]);
    if (TextUtils.isEmpty((CharSequence)localObject))
    {
      finish();
      return;
    }
    localObject = (MarketingContent)this.mContentStore.get((String)localObject);
    boolean bool;
    if (localObject != null)
    {
      this.mContent = ((MarketingContent)localObject);
      this.mBillboard = ((MarketingContent)localObject).getBoundBillboard();
      if (paramBundle == null)
      {
        bool = true;
        this.mShouldAttachOnResume = bool;
      }
    }
    for (;;)
    {
      this.mDataStoreSubscription = this.mUpsight.getDataStore().subscribe(this);
      this.mUpsight.getCoreComponent().bus().register(this);
      return;
      bool = false;
      break;
      finish();
    }
  }
  
  protected void onDestroy()
  {
    this.mDataStoreSubscription.unsubscribe();
    this.mUpsight.getCoreComponent().bus().unregister(this);
    this.mUpsight.getLogger().d("BillboardActivity", "onDestroy activity=" + this, new Object[0]);
    super.onDestroy();
  }
  
  protected void onPause()
  {
    this.mIsForeground = false;
    this.mUpsight.getLogger().d("BillboardActivity", "onPause activity=" + this, new Object[0]);
    super.onPause();
  }
  
  protected void onResume()
  {
    super.onResume();
    this.mIsForeground = true;
    Object localObject1 = this.mContent;
    if (localObject1 == null) {}
    label198:
    for (;;)
    {
      return;
      if (this.mShouldAttachOnResume)
      {
        this.mShouldAttachOnResume = false;
        if (((MarketingContent)localObject1).hasPendingDialog()) {
          attachDialog(((MarketingContent)localObject1).popPendingDialog());
        }
      }
      else
      {
        localObject1 = ((MarketingContent)localObject1).getEventQueue();
        this.mUpsight.getLogger().d("BillboardActivity", "onResume activity=" + this + " eventQueueSize=" + ((Queue)localObject1).size(), new Object[0]);
      }
      for (;;)
      {
        if (((Queue)localObject1).isEmpty()) {
          break label198;
        }
        Object localObject2 = ((Queue)localObject1).poll();
        if ((localObject2 instanceof MarketingContent.SubcontentAvailabilityEvent))
        {
          handle((MarketingContent.SubcontentAvailabilityEvent)localObject2);
          continue;
          attachBillboard((MarketingContent)localObject1);
          break;
        }
        if ((localObject2 instanceof MarketingContent.SubdialogAvailabilityEvent)) {
          handle((MarketingContent.SubdialogAvailabilityEvent)localObject2);
        } else if ((localObject2 instanceof MarketingContent.ScopelessAvailabilityEvent)) {
          handle((MarketingContent.ScopelessAvailabilityEvent)localObject2);
        } else if ((localObject2 instanceof MarketingContentActions.DestroyEvent)) {
          handle((MarketingContentActions.DestroyEvent)localObject2);
        }
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/billboard/BillboardManagementActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */