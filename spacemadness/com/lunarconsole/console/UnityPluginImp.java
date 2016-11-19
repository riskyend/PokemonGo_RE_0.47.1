package spacemadness.com.lunarconsole.console;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import com.unity3d.player.UnityPlayer;
import java.lang.ref.WeakReference;
import spacemadness.com.lunarconsole.core.LunarConsoleException;
import spacemadness.com.lunarconsole.utils.UIUtils;

public class UnityPluginImp
  implements ConsolePluginImp
{
  private final WeakReference<UnityPlayer> playerRef;
  
  public UnityPluginImp(Activity paramActivity)
  {
    paramActivity = resolveUnityPlayerInstance(paramActivity);
    if (paramActivity == null) {
      throw new LunarConsoleException("Can't initialize plugin: UnityPlayer instance not resolved");
    }
    this.playerRef = new WeakReference(paramActivity);
  }
  
  private UnityPlayer getPlayer()
  {
    return (UnityPlayer)this.playerRef.get();
  }
  
  private static UnityPlayer resolveUnityPlayerInstance(Activity paramActivity)
  {
    return resolveUnityPlayerInstance(UIUtils.getRootViewGroup(paramActivity));
  }
  
  private static UnityPlayer resolveUnityPlayerInstance(ViewGroup paramViewGroup)
  {
    if ((paramViewGroup instanceof UnityPlayer)) {
      return (UnityPlayer)paramViewGroup;
    }
    int i = 0;
    while (i < paramViewGroup.getChildCount())
    {
      Object localObject = paramViewGroup.getChildAt(i);
      if ((localObject instanceof UnityPlayer)) {
        return (UnityPlayer)localObject;
      }
      if ((localObject instanceof ViewGroup))
      {
        localObject = resolveUnityPlayerInstance((ViewGroup)localObject);
        if (localObject != null) {
          return (UnityPlayer)localObject;
        }
      }
      i += 1;
    }
    return null;
  }
  
  public View getTouchRecepientView()
  {
    return getPlayer();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/console/UnityPluginImp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */