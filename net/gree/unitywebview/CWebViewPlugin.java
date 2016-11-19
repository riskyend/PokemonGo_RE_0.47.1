package net.gree.unitywebview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.unity3d.player.UnityPlayer;
import java.io.File;

public class CWebViewPlugin
{
  private static FrameLayout layout = null;
  private boolean canGoBack;
  private boolean canGoForward;
  private WebView mWebView;
  private CWebViewPluginInterface mWebViewPlugin;
  
  public void Destroy()
  {
    UnityPlayer.currentActivity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (CWebViewPlugin.this.mWebView == null) {
          return;
        }
        CWebViewPlugin.layout.removeView(CWebViewPlugin.this.mWebView);
        CWebViewPlugin.access$002(CWebViewPlugin.this, null);
      }
    });
  }
  
  public void EvaluateJS(final String paramString)
  {
    UnityPlayer.currentActivity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (CWebViewPlugin.this.mWebView == null) {
          return;
        }
        CWebViewPlugin.this.mWebView.loadUrl("javascript:" + paramString);
      }
    });
  }
  
  public void GoBack()
  {
    UnityPlayer.currentActivity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (CWebViewPlugin.this.mWebView == null) {
          return;
        }
        CWebViewPlugin.this.mWebView.goBack();
      }
    });
  }
  
  public void GoForward()
  {
    UnityPlayer.currentActivity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (CWebViewPlugin.this.mWebView == null) {
          return;
        }
        CWebViewPlugin.this.mWebView.goForward();
      }
    });
  }
  
  public void Init(final String paramString, final boolean paramBoolean)
  {
    final Activity localActivity = UnityPlayer.currentActivity;
    localActivity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (CWebViewPlugin.this.mWebView != null) {
          return;
        }
        final WebView localWebView = new WebView(localActivity);
        localWebView.setVisibility(8);
        localWebView.setFocusable(true);
        localWebView.setFocusableInTouchMode(true);
        localWebView.setWebChromeClient(new WebChromeClient());
        CWebViewPlugin.access$102(CWebViewPlugin.this, new CWebViewPluginInterface(jdField_this, paramString));
        localWebView.setWebViewClient(new WebViewClient()
        {
          public void onPageFinished(WebView paramAnonymous2WebView, String paramAnonymous2String)
          {
            CWebViewPlugin.access$202(CWebViewPlugin.this, localWebView.canGoBack());
            CWebViewPlugin.access$302(CWebViewPlugin.this, localWebView.canGoForward());
            CWebViewPlugin.this.mWebViewPlugin.call("CallOnLoaded", paramAnonymous2String);
          }
          
          public void onPageStarted(WebView paramAnonymous2WebView, String paramAnonymous2String, Bitmap paramAnonymous2Bitmap)
          {
            CWebViewPlugin.access$202(CWebViewPlugin.this, localWebView.canGoBack());
            CWebViewPlugin.access$302(CWebViewPlugin.this, localWebView.canGoForward());
          }
          
          public void onReceivedError(WebView paramAnonymous2WebView, int paramAnonymous2Int, String paramAnonymous2String1, String paramAnonymous2String2)
          {
            localWebView.loadUrl("about:blank");
            CWebViewPlugin.access$202(CWebViewPlugin.this, localWebView.canGoBack());
            CWebViewPlugin.access$302(CWebViewPlugin.this, localWebView.canGoForward());
            CWebViewPlugin.this.mWebViewPlugin.call("CallOnError", paramAnonymous2Int + "\t" + paramAnonymous2String1 + "\t" + paramAnonymous2String2);
          }
          
          public boolean shouldOverrideUrlLoading(WebView paramAnonymous2WebView, String paramAnonymous2String)
          {
            if ((paramAnonymous2String.startsWith("http://")) || (paramAnonymous2String.startsWith("https://")) || (paramAnonymous2String.startsWith("file://")) || (paramAnonymous2String.startsWith("javascript:"))) {
              return false;
            }
            if (paramAnonymous2String.startsWith("unity:"))
            {
              paramAnonymous2WebView = paramAnonymous2String.substring(6);
              CWebViewPlugin.this.mWebViewPlugin.call("CallFromJS", paramAnonymous2WebView);
              return true;
            }
            paramAnonymous2String = new Intent("android.intent.action.VIEW", Uri.parse(paramAnonymous2String));
            paramAnonymous2WebView.getContext().startActivity(paramAnonymous2String);
            return true;
          }
        });
        localWebView.addJavascriptInterface(CWebViewPlugin.this.mWebViewPlugin, "Unity");
        WebSettings localWebSettings = localWebView.getSettings();
        localWebSettings.setSupportZoom(false);
        localWebSettings.setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 16) {
          localWebSettings.setAllowUniversalAccessFromFileURLs(true);
        }
        localWebSettings.setDatabaseEnabled(true);
        localWebSettings.setDomStorageEnabled(true);
        localWebSettings.setDatabasePath(localWebView.getContext().getDir("databases", 0).getPath());
        localWebSettings.setUseWideViewPort(true);
        if (paramBoolean) {
          localWebView.setBackgroundColor(0);
        }
        if (CWebViewPlugin.layout == null)
        {
          CWebViewPlugin.access$402(new FrameLayout(localActivity));
          localActivity.addContentView(CWebViewPlugin.layout, new ViewGroup.LayoutParams(-1, -1));
          CWebViewPlugin.layout.setFocusable(true);
          CWebViewPlugin.layout.setFocusableInTouchMode(true);
        }
        CWebViewPlugin.layout.addView(localWebView, new FrameLayout.LayoutParams(-1, -1, 0));
        CWebViewPlugin.access$002(CWebViewPlugin.this, localWebView);
      }
    });
    final View localView = localActivity.getWindow().getDecorView().getRootView();
    localView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
    {
      public void onGlobalLayout()
      {
        Rect localRect = new Rect();
        localView.getWindowVisibleDisplayFrame(localRect);
        Display localDisplay = localActivity.getWindowManager().getDefaultDisplay();
        Point localPoint = new Point();
        localDisplay.getSize(localPoint);
        if (localView.getRootView().getHeight() - (localRect.bottom - localRect.top) > localPoint.y / 3)
        {
          UnityPlayer.UnitySendMessage(paramString, "SetKeyboardVisible", "true");
          return;
        }
        UnityPlayer.UnitySendMessage(paramString, "SetKeyboardVisible", "false");
      }
    });
  }
  
  public boolean IsInitialized()
  {
    return this.mWebView != null;
  }
  
  public void LoadURL(final String paramString)
  {
    UnityPlayer.currentActivity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (CWebViewPlugin.this.mWebView == null) {
          return;
        }
        CWebViewPlugin.this.mWebView.loadUrl(paramString);
      }
    });
  }
  
  public void SetMargins(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    final FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, -1, 0);
    localLayoutParams.setMargins(paramInt1, paramInt2, paramInt3, paramInt4);
    UnityPlayer.currentActivity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (CWebViewPlugin.this.mWebView == null) {
          return;
        }
        CWebViewPlugin.this.mWebView.setLayoutParams(localLayoutParams);
      }
    });
  }
  
  public void SetVisibility(final boolean paramBoolean)
  {
    UnityPlayer.currentActivity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (CWebViewPlugin.this.mWebView == null) {
          return;
        }
        if (paramBoolean)
        {
          CWebViewPlugin.this.mWebView.setVisibility(0);
          CWebViewPlugin.layout.requestFocus();
          CWebViewPlugin.this.mWebView.requestFocus();
          return;
        }
        CWebViewPlugin.this.mWebView.setVisibility(8);
      }
    });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/net/gree/unitywebview/CWebViewPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */