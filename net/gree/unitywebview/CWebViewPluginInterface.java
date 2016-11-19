package net.gree.unitywebview;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import com.unity3d.player.UnityPlayer;

class CWebViewPluginInterface
{
  private String mGameObject;
  private CWebViewPlugin mPlugin;
  
  public CWebViewPluginInterface(CWebViewPlugin paramCWebViewPlugin, String paramString)
  {
    this.mPlugin = paramCWebViewPlugin;
    this.mGameObject = paramString;
  }
  
  @JavascriptInterface
  public void call(String paramString)
  {
    call("CallFromJS", paramString);
  }
  
  public void call(final String paramString1, final String paramString2)
  {
    UnityPlayer.currentActivity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (CWebViewPluginInterface.this.mPlugin.IsInitialized()) {
          UnityPlayer.UnitySendMessage(CWebViewPluginInterface.this.mGameObject, paramString1, paramString2);
        }
      }
    });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/net/gree/unitywebview/CWebViewPluginInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */