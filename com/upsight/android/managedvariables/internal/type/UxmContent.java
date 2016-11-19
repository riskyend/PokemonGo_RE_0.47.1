package com.upsight.android.managedvariables.internal.type;

import com.upsight.android.analytics.internal.action.ActionMap;
import com.upsight.android.analytics.internal.action.Actionable;

public class UxmContent
  extends Actionable
{
  public static final String PREFERENCES_KEY_UXM_BUNDLE_ID = "uxmBundleId";
  public static final String TRIGGER_CONTENT_RECEIVED = "content_received";
  private boolean mShouldApplyBundle;
  
  private UxmContent(String paramString, ActionMap<UxmContent, UxmContentActions.UxmContentActionContext> paramActionMap, boolean paramBoolean)
  {
    super(paramString, paramActionMap);
    this.mShouldApplyBundle = paramBoolean;
  }
  
  public static UxmContent create(String paramString, ActionMap<UxmContent, UxmContentActions.UxmContentActionContext> paramActionMap, boolean paramBoolean)
  {
    return new UxmContent(paramString, paramActionMap, paramBoolean);
  }
  
  public boolean shouldApplyBundle()
  {
    return this.mShouldApplyBundle;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/UxmContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */