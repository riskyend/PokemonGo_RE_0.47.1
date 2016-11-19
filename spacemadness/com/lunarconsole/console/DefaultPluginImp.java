package spacemadness.com.lunarconsole.console;

import android.app.Activity;
import android.view.View;
import java.lang.ref.WeakReference;
import spacemadness.com.lunarconsole.core.LunarConsoleException;
import spacemadness.com.lunarconsole.utils.UIUtils;

class DefaultPluginImp
  implements ConsolePluginImp
{
  private final WeakReference<View> rootViewRef;
  
  public DefaultPluginImp(Activity paramActivity)
  {
    paramActivity = UIUtils.getRootViewGroup(paramActivity);
    if (paramActivity == null) {
      throw new LunarConsoleException("Can't initialize plugin: root view not found");
    }
    this.rootViewRef = new WeakReference(paramActivity);
  }
  
  public View getTouchRecepientView()
  {
    return (View)this.rootViewRef.get();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/console/DefaultPluginImp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */