package com.upsight.android.unity;

import android.util.Log;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.provider.UpsightSessionContext;
import com.upsight.android.analytics.session.UpsightSessionCallbacks;
import com.upsight.android.analytics.session.UpsightSessionInfo;
import com.upsight.android.managedvariables.experience.UpsightUserExperience;
import com.upsight.android.managedvariables.experience.UpsightUserExperience.Handler;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;

public class UnitySessionCallbacks
  implements UpsightSessionCallbacks
{
  protected static final String TAG = "UnitySessionCallbacks";
  private static boolean mShouldSynchronizeManagedVariables = true;
  
  public static void setShouldSynchronizeManagedVariables(boolean paramBoolean)
  {
    mShouldSynchronizeManagedVariables = paramBoolean;
  }
  
  public void onResume(UpsightSessionContext paramUpsightSessionContext, UpsightSessionInfo paramUpsightSessionInfo) {}
  
  public void onResumed(UpsightContext paramUpsightContext)
  {
    UnityBridge.UnitySendMessage("sessionDidResume");
  }
  
  public void onStart(UpsightSessionContext paramUpsightSessionContext, UpsightSessionInfo paramUpsightSessionInfo)
  {
    UpsightUserExperience.registerHandler(paramUpsightSessionContext, new UpsightUserExperience.Handler()
    {
      public boolean onReceive()
      {
        return UnitySessionCallbacks.mShouldSynchronizeManagedVariables;
      }
      
      public void onSynchronize(List<String> paramAnonymousList)
      {
        Log.i("UnitySessionCallbacks", "onSynchronize");
        JSONArray localJSONArray = new JSONArray();
        paramAnonymousList = paramAnonymousList.iterator();
        while (paramAnonymousList.hasNext()) {
          localJSONArray.put((String)paramAnonymousList.next());
        }
        UnityBridge.UnitySendMessage("managedVariablesDidSynchronize", localJSONArray.toString());
      }
    });
  }
  
  public void onStarted(UpsightContext paramUpsightContext)
  {
    UnityBridge.UnitySendMessage("sessionDidStart");
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/unity/UnitySessionCallbacks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */