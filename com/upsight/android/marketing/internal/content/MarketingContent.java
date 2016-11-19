package com.upsight.android.marketing.internal.content;

import android.view.View;
import com.squareup.otto.Bus;
import com.upsight.android.analytics.internal.action.ActionMap;
import com.upsight.android.analytics.internal.action.Actionable;
import com.upsight.android.marketing.UpsightContentMediator;
import com.upsight.android.marketing.internal.billboard.Billboard;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class MarketingContent<T>
  extends Actionable
{
  public static final String TRIGGER_APP_BACKGROUNDED = "app_backgrounded";
  public static final String TRIGGER_CONTENT_DISMISSED = "content_dismissed";
  public static final String TRIGGER_CONTENT_DISMISSED_WITH_REWARD = "content_dismissed_with_reward";
  public static final String TRIGGER_CONTENT_DISPLAYED = "content_displayed";
  public static final String TRIGGER_CONTENT_RECEIVED = "content_received";
  private AvailabilityEvent mAvailabilityEvent;
  private Billboard mBillboard = null;
  private UpsightContentMediator mContentMediator = null;
  private T mContentModel = null;
  private View mContentView = null;
  private Queue<Object> mEventQueue = new LinkedBlockingQueue();
  private Map<String, String> mExtras = new HashMap();
  private boolean mIsLoaded = false;
  private boolean mIsRewardGranted = false;
  private PendingDialog mPendingDialog;
  
  private MarketingContent(String paramString, ActionMap<MarketingContent, MarketingContentActions.MarketingContentActionContext> paramActionMap)
  {
    super(paramString, paramActionMap);
  }
  
  public static MarketingContent create(String paramString, ActionMap<MarketingContent, MarketingContentActions.MarketingContentActionContext> paramActionMap)
  {
    return new MarketingContent(paramString, paramActionMap);
  }
  
  private void notifyAvailability(Bus paramBus)
  {
    if (isAvailable()) {
      paramBus.post(this.mAvailabilityEvent);
    }
  }
  
  public void addPendingDialog(PendingDialog paramPendingDialog)
  {
    this.mPendingDialog = paramPendingDialog;
  }
  
  public void bindBillboard(Billboard paramBillboard)
  {
    this.mBillboard = paramBillboard;
  }
  
  public Billboard getBoundBillboard()
  {
    return this.mBillboard;
  }
  
  public UpsightContentMediator getContentMediator()
  {
    return this.mContentMediator;
  }
  
  public T getContentModel()
  {
    return (T)this.mContentModel;
  }
  
  public View getContentView()
  {
    return this.mContentView;
  }
  
  public Queue<Object> getEventQueue()
  {
    return this.mEventQueue;
  }
  
  public String getExtra(String paramString)
  {
    return (String)this.mExtras.get(paramString);
  }
  
  public boolean hasPendingDialog()
  {
    return this.mPendingDialog != null;
  }
  
  public boolean isAvailable()
  {
    return (isLoaded()) && (this.mAvailabilityEvent != null) && (getBoundBillboard() == null);
  }
  
  boolean isLoaded()
  {
    return (this.mContentModel != null) && (this.mIsLoaded);
  }
  
  public boolean isRewardGranted()
  {
    return this.mIsRewardGranted;
  }
  
  public void markLoaded(Bus paramBus)
  {
    this.mIsLoaded = true;
    paramBus.post(new ContentLoadedEvent(getId(), null));
    notifyAvailability(paramBus);
  }
  
  public void markPresentable(AvailabilityEvent paramAvailabilityEvent, Bus paramBus)
  {
    this.mAvailabilityEvent = paramAvailabilityEvent;
    notifyAvailability(paramBus);
  }
  
  public void markRewardGranted()
  {
    this.mIsRewardGranted = true;
  }
  
  public PendingDialog popPendingDialog()
  {
    PendingDialog localPendingDialog = this.mPendingDialog;
    this.mPendingDialog = null;
    return localPendingDialog;
  }
  
  public void putExtra(String paramString1, String paramString2)
  {
    this.mExtras.put(paramString1, paramString2);
  }
  
  public void setContentMediator(UpsightContentMediator paramUpsightContentMediator)
  {
    this.mContentMediator = paramUpsightContentMediator;
  }
  
  public void setContentModel(T paramT)
  {
    this.mContentModel = paramT;
  }
  
  public void setContentView(View paramView)
  {
    this.mContentView = paramView;
  }
  
  public static abstract class AvailabilityEvent
  {
    private final String mId;
    
    private AvailabilityEvent(String paramString)
    {
      this.mId = paramString;
    }
    
    public String getId()
    {
      return this.mId;
    }
  }
  
  public static class ContentLoadedEvent
  {
    private final String mId;
    
    private ContentLoadedEvent(String paramString)
    {
      this.mId = paramString;
    }
    
    public String getId()
    {
      return this.mId;
    }
  }
  
  public static class PendingDialog
  {
    public static final String TEXT = "text";
    public static final String TRIGGER = "trigger";
    public final String mButtons;
    public final String mDismissTrigger;
    public final String mId;
    public final String mMessage;
    public final String mTitle;
    
    public PendingDialog(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    {
      this.mId = paramString1;
      this.mTitle = paramString2;
      this.mMessage = paramString3;
      this.mButtons = paramString4;
      this.mDismissTrigger = paramString5;
    }
  }
  
  public static class ScopedAvailabilityEvent
    extends MarketingContent.AvailabilityEvent
  {
    private final String[] mScopes;
    
    public ScopedAvailabilityEvent(String paramString, String[] paramArrayOfString)
    {
      super(null);
      this.mScopes = paramArrayOfString;
    }
    
    public List<String> getScopes()
    {
      return Arrays.asList(this.mScopes);
    }
  }
  
  public static class ScopelessAvailabilityEvent
    extends MarketingContent.AvailabilityEvent
  {
    private final String mParentId;
    
    public ScopelessAvailabilityEvent(String paramString1, String paramString2)
    {
      super(null);
      this.mParentId = paramString2;
    }
    
    public String getParentId()
    {
      return this.mParentId;
    }
  }
  
  public static class SubcontentAvailabilityEvent
    extends MarketingContent.AvailabilityEvent
  {
    public SubcontentAvailabilityEvent(String paramString)
    {
      super(null);
    }
  }
  
  public static class SubdialogAvailabilityEvent
    extends MarketingContent.AvailabilityEvent
  {
    private final MarketingContent.PendingDialog mPendingDialog;
    
    public SubdialogAvailabilityEvent(String paramString, MarketingContent.PendingDialog paramPendingDialog)
    {
      super(null);
      this.mPendingDialog = paramPendingDialog;
    }
    
    public MarketingContent.PendingDialog getPendingDialog()
    {
      return this.mPendingDialog;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/content/MarketingContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */