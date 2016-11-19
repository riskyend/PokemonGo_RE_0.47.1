package com.google.unity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnGenericMotionListener;
import android.view.View.OnKeyListener;
import android.view.View.OnSystemUiVisibilityChangeListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.PopupWindow;
import com.unity3d.player.UnityPlayer;

public class GoogleUnityActivity
  extends Activity
{
  private static final int NAVIGATION_BAR_TIMEOUT_MS = 2000;
  static final String TAG = GoogleUnityActivity.class.getSimpleName();
  protected AndroidInputListener mAndroidInputListener;
  protected AndroidLifecycleListener mAndroidLifecycleListener;
  private View mAndroidView;
  private PopupWindow mPopupWindow;
  protected UnityPlayer mUnityPlayer;
  private boolean shouldUseImmersiveMode;
  
  @TargetApi(19)
  private void setImmersiveMode()
  {
    getWindow().getDecorView().setSystemUiVisibility(5894);
  }
  
  private void startImmersiveMode()
  {
    final Handler localHandler = new Handler();
    getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
    {
      public void onSystemUiVisibilityChange(int paramAnonymousInt)
      {
        if ((paramAnonymousInt & 0x2) == 0) {
          localHandler.postDelayed(new Runnable()
          {
            public void run()
            {
              GoogleUnityActivity.this.setImmersiveMode();
            }
          }, 2000L);
        }
        if (GoogleUnityActivity.this.mAndroidInputListener != null) {
          GoogleUnityActivity.this.mAndroidInputListener.onSystemUiVisibilityChange(paramAnonymousInt);
        }
      }
    });
  }
  
  public void attachInputListener(AndroidInputListener paramAndroidInputListener)
  {
    this.mAndroidInputListener = paramAndroidInputListener;
  }
  
  public void attachLifecycleListener(AndroidLifecycleListener paramAndroidLifecycleListener)
  {
    this.mAndroidLifecycleListener = paramAndroidLifecycleListener;
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 2) {
      return injectUnityEvent(paramKeyEvent);
    }
    return super.dispatchKeyEvent(paramKeyEvent);
  }
  
  public View getAndroidViewLayer()
  {
    return this.mAndroidView;
  }
  
  public UnityPlayer getUnityPlayer()
  {
    return this.mUnityPlayer;
  }
  
  public boolean injectUnityEvent(InputEvent paramInputEvent)
  {
    return this.mUnityPlayer.injectEvent(paramInputEvent);
  }
  
  public void launchIntent(String paramString1, String paramString2, String[] paramArrayOfString, int paramInt)
  {
    Intent localIntent = new Intent();
    localIntent.setClassName(paramString1, paramString2);
    if (paramArrayOfString != null)
    {
      int i = 0;
      while (i < paramArrayOfString.length)
      {
        paramString1 = paramArrayOfString[i].split(":");
        if (paramString1.length == 2) {
          localIntent.putExtra(paramString1[0], paramString1[1]);
        }
        i += 1;
      }
    }
    startActivityForResult(localIntent, paramInt);
  }
  
  public void logAndroidErrorMessage(String paramString)
  {
    Log.e(getPackageName(), paramString);
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (this.mAndroidLifecycleListener != null) {
      this.mAndroidLifecycleListener.onActivityResult(paramInt1, paramInt2, paramIntent);
    }
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    this.mUnityPlayer.configurationChanged(paramConfiguration);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    getWindow().takeSurface(null);
    getWindow().setFormat(4);
    this.mUnityPlayer = new UnityPlayer(this);
    if (this.mUnityPlayer.getSettings().getBoolean("hide_status_bar", true)) {
      getWindow().setFlags(1024, 1024);
    }
    this.mUnityPlayer.setOnTouchListener(new View.OnTouchListener()
    {
      public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        return GoogleUnityActivity.this.onTouchEvent(paramAnonymousMotionEvent);
      }
    });
    this.mUnityPlayer.setOnGenericMotionListener(new View.OnGenericMotionListener()
    {
      public boolean onGenericMotion(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
      {
        return GoogleUnityActivity.this.onGenericMotionEvent(paramAnonymousMotionEvent);
      }
    });
    this.mUnityPlayer.setOnKeyListener(new View.OnKeyListener()
    {
      public boolean onKey(View paramAnonymousView, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        switch (paramAnonymousKeyEvent.getAction())
        {
        default: 
          return GoogleUnityActivity.this.injectUnityEvent(paramAnonymousKeyEvent);
        case 0: 
          return GoogleUnityActivity.this.onKeyDown(paramAnonymousInt, paramAnonymousKeyEvent);
        }
        return GoogleUnityActivity.this.onKeyUp(paramAnonymousInt, paramAnonymousKeyEvent);
      }
    });
    setContentView(this.mUnityPlayer);
    this.mUnityPlayer.requestFocus();
    this.shouldUseImmersiveMode = false;
    try
    {
      this.shouldUseImmersiveMode = getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.getBoolean("IMMERSIVE_MODE");
      if ((this.shouldUseImmersiveMode) && (Build.VERSION.SDK_INT < 19)) {
        startImmersiveMode();
      }
      return;
    }
    catch (NullPointerException paramBundle)
    {
      for (;;)
      {
        Log.e(TAG, "Failed to load meta-data, NullPointer: " + paramBundle.getMessage());
      }
    }
    catch (PackageManager.NameNotFoundException paramBundle)
    {
      for (;;) {}
    }
  }
  
  protected void onDestroy()
  {
    this.mUnityPlayer.quit();
    super.onDestroy();
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    if ((this.mAndroidInputListener != null) && (this.mAndroidInputListener.onGenericMotionEvent(paramMotionEvent))) {
      return true;
    }
    return injectUnityEvent(paramMotionEvent);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((this.mAndroidInputListener != null) && (this.mAndroidInputListener.onKeyDown(paramInt, paramKeyEvent))) {
      return true;
    }
    return injectUnityEvent(paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((this.mAndroidInputListener != null) && (this.mAndroidInputListener.onKeyUp(paramInt, paramKeyEvent))) {
      return true;
    }
    return injectUnityEvent(paramKeyEvent);
  }
  
  protected void onPause()
  {
    super.onPause();
    if (this.mAndroidLifecycleListener != null) {
      this.mAndroidLifecycleListener.onPause();
    }
    this.mUnityPlayer.pause();
  }
  
  protected void onResume()
  {
    super.onResume();
    if (this.mAndroidLifecycleListener != null) {
      this.mAndroidLifecycleListener.onResume();
    }
    this.mUnityPlayer.resume();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((this.mAndroidInputListener != null) && (this.mAndroidInputListener.onTouchEvent(paramMotionEvent))) {
      return true;
    }
    return injectUnityEvent(paramMotionEvent);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    super.onWindowFocusChanged(paramBoolean);
    this.mUnityPlayer.windowFocusChanged(paramBoolean);
    if ((paramBoolean) && (this.shouldUseImmersiveMode)) {
      setImmersiveMode();
    }
  }
  
  public void showAndroidViewLayer(final int paramInt)
  {
    runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (GoogleUnityActivity.this.mPopupWindow != null)
        {
          GoogleUnityActivity.this.mPopupWindow.dismiss();
          GoogleUnityActivity.access$002(GoogleUnityActivity.this, null);
        }
        GoogleUnityActivity.access$002(GoogleUnityActivity.this, new PopupWindow(jdField_this));
        GoogleUnityActivity.this.mPopupWindow.setWindowLayoutMode(-1, -1);
        GoogleUnityActivity.this.mPopupWindow.setClippingEnabled(false);
        GoogleUnityActivity.this.mPopupWindow.setBackgroundDrawable(null);
        LayoutInflater localLayoutInflater = LayoutInflater.from(jdField_this);
        GoogleUnityActivity.access$102(GoogleUnityActivity.this, localLayoutInflater.inflate(paramInt, null));
        GoogleUnityActivity.this.mPopupWindow.setContentView(GoogleUnityActivity.this.mAndroidView);
        GoogleUnityActivity.this.mPopupWindow.setTouchable(false);
        GoogleUnityActivity.this.mPopupWindow.showAtLocation(jdField_this.getWindow().getDecorView(), 80, 0, 0);
      }
    });
  }
  
  public static abstract interface AndroidInputListener
  {
    public abstract boolean onGenericMotionEvent(MotionEvent paramMotionEvent);
    
    public abstract boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent);
    
    public abstract boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent);
    
    public abstract void onSystemUiVisibilityChange(int paramInt);
    
    public abstract boolean onTouchEvent(MotionEvent paramMotionEvent);
  }
  
  public static abstract interface AndroidLifecycleListener
  {
    public abstract void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent);
    
    public abstract void onPause();
    
    public abstract void onResume();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/unity/GoogleUnityActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */