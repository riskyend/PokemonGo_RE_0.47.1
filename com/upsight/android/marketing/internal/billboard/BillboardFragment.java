package com.upsight.android.marketing.internal.billboard;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import com.upsight.android.marketing.R.layout;
import com.upsight.android.marketing.UpsightBillboard.Dimensions;
import java.util.Iterator;
import java.util.Set;

public final class BillboardFragment
  extends DialogFragment
{
  private static final String BUNDLE_KEY_LANDSCAPE_HEIGHT = "landscapeHeight";
  private static final String BUNDLE_KEY_LANDSCAPE_WIDTH = "landscapeWidth";
  private static final String BUNDLE_KEY_PORTRAIT_HEIGHT = "portraitHeight";
  private static final String BUNDLE_KEY_PORTRAIT_WIDTH = "portraitWidth";
  private BackPressHandler mBackPressHandler = null;
  private ViewGroup mContentViewContainer = null;
  private ViewGroup mRootView = null;
  
  public static BillboardFragment newInstance(Context paramContext, Set<UpsightBillboard.Dimensions> paramSet)
  {
    BillboardFragment localBillboardFragment = new BillboardFragment();
    Bundle localBundle = new Bundle();
    if (paramSet != null)
    {
      paramSet = paramSet.iterator();
      while (paramSet.hasNext())
      {
        UpsightBillboard.Dimensions localDimensions = (UpsightBillboard.Dimensions)paramSet.next();
        if ((localDimensions.width > 0) && (localDimensions.height > 0)) {
          switch (localDimensions.layout)
          {
          default: 
            break;
          case ???: 
            localBundle.putInt("portraitWidth", localDimensions.width);
            localBundle.putInt("portraitHeight", localDimensions.height);
            break;
          case ???: 
            localBundle.putInt("landscapeWidth", localDimensions.width);
            localBundle.putInt("landscapeHeight", localDimensions.height);
          }
        }
      }
    }
    localBillboardFragment.setArguments(localBundle);
    localBillboardFragment.setRetainInstance(true);
    localBillboardFragment.mContentViewContainer = new LinearLayout(paramContext.getApplicationContext());
    return localBillboardFragment;
  }
  
  private void setDialogSize(int paramInt1, int paramInt2)
  {
    Object localObject = getDialog();
    if (localObject != null)
    {
      localObject = ((Dialog)localObject).getWindow();
      WindowManager.LayoutParams localLayoutParams = ((Window)localObject).getAttributes();
      localLayoutParams.width = paramInt1;
      localLayoutParams.height = paramInt2;
      ((Window)localObject).setAttributes(localLayoutParams);
    }
  }
  
  public ViewGroup getContentViewContainer()
  {
    return this.mContentViewContainer;
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    getFragmentManager().beginTransaction().detach(this).attach(this).commit();
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    paramBundle = super.onCreateDialog(paramBundle);
    paramBundle.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        boolean bool2 = false;
        boolean bool1 = bool2;
        if (paramAnonymousInt == 4)
        {
          bool1 = bool2;
          if (paramAnonymousKeyEvent.getAction() == 0)
          {
            paramAnonymousDialogInterface = BillboardFragment.this.mBackPressHandler;
            bool1 = bool2;
            if (paramAnonymousDialogInterface != null)
            {
              bool1 = bool2;
              if (paramAnonymousDialogInterface.onBackPress()) {
                bool1 = true;
              }
            }
          }
        }
        return bool1;
      }
    });
    return paramBundle;
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    if (this.mContentViewContainer != null)
    {
      this.mRootView = ((ViewGroup)paramLayoutInflater.inflate(R.layout.upsight_fragment_billboard, paramViewGroup, false));
      this.mRootView.addView(this.mContentViewContainer, new ViewGroup.LayoutParams(-1, -1));
    }
    return this.mRootView;
  }
  
  public void onDestroyView()
  {
    if (this.mContentViewContainer != null) {
      this.mRootView.removeView(this.mContentViewContainer);
    }
    Dialog localDialog = getDialog();
    if ((localDialog != null) && (getRetainInstance())) {
      localDialog.setDismissMessage(null);
    }
    super.onDestroyView();
  }
  
  public void onResume()
  {
    int i = getResources().getConfiguration().orientation;
    Bundle localBundle = getArguments();
    if ((i == 1) && (localBundle.containsKey("portraitWidth"))) {
      setDialogSize(localBundle.getInt("portraitWidth"), localBundle.getInt("portraitHeight"));
    }
    for (;;)
    {
      super.onResume();
      return;
      if ((i == 2) && (localBundle.containsKey("landscapeWidth"))) {
        setDialogSize(localBundle.getInt("landscapeWidth"), localBundle.getInt("landscapeHeight"));
      }
    }
  }
  
  public void onStart()
  {
    super.onStart();
    if (this.mContentViewContainer == null) {
      dismiss();
    }
  }
  
  public void setBackPressHandler(BackPressHandler paramBackPressHandler)
  {
    this.mBackPressHandler = paramBackPressHandler;
  }
  
  public static abstract interface BackPressHandler
  {
    public abstract boolean onBackPress();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/marketing/internal/billboard/BillboardFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */