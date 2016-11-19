package com.upsight.android.unity;

import android.util.Log;
import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightException;
import com.upsight.android.googlepushservices.UpsightGooglePushServices;
import com.upsight.android.googlepushservices.UpsightGooglePushServices.OnRegisterListener;
import com.upsight.android.googlepushservices.UpsightGooglePushServices.OnUnregisterListener;
import com.upsight.android.googlepushservices.UpsightPushBillboard;
import com.upsight.android.marketing.UpsightBillboard;

public class UpsightPushManager
  implements IUpsightExtensionManager
{
  protected static final String TAG = "Upsight-UnityPush";
  private UpsightBillboard mPushBillboard;
  private BillboardHandler mPushBillboardHandler;
  private UpsightContext mUpsight;
  
  public void init(UpsightContext paramUpsightContext)
  {
    this.mUpsight = paramUpsightContext;
    paramUpsightContext = UnityBridge.getActivity();
    if (paramUpsightContext == null) {
      return;
    }
    this.mPushBillboardHandler = new BillboardHandler(paramUpsightContext);
    this.mPushBillboard = UpsightPushBillboard.create(this.mUpsight, this.mPushBillboardHandler);
  }
  
  public void onApplicationPaused()
  {
    if (this.mUpsight == null) {}
    for (;;)
    {
      return;
      try
      {
        if (this.mPushBillboard != null)
        {
          this.mPushBillboard.destroy();
          this.mPushBillboard = null;
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  public void onApplicationResumed()
  {
    if (this.mUpsight == null) {}
    for (;;)
    {
      return;
      try
      {
        if (this.mPushBillboard == null)
        {
          this.mPushBillboard = UpsightPushBillboard.create(this.mUpsight, this.mPushBillboardHandler);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
  
  public void registerForPushNotifications()
  {
    if (this.mUpsight == null) {
      return;
    }
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        Log.i("Upsight-UnityPush", "registering for push notifications");
        UpsightGooglePushServices.register(UpsightPushManager.this.mUpsight, new UpsightGooglePushServices.OnRegisterListener()
        {
          public void onFailure(UpsightException paramAnonymous2UpsightException)
          {
            Log.e("Upsight-UnityPush", "registration failed: " + paramAnonymous2UpsightException);
          }
          
          public void onSuccess(String paramAnonymous2String)
          {
            Log.i("Upsight-UnityPush", "registration succeeded");
          }
        });
      }
    });
  }
  
  public void unregisterForPushNotifications()
  {
    if (this.mUpsight == null) {
      return;
    }
    UnityBridge.runSafelyOnUiThread(new Runnable()
    {
      public void run()
      {
        Log.i("Upsight-UnityPush", "unregistering for push notifications");
        UpsightGooglePushServices.unregister(UpsightPushManager.this.mUpsight, new UpsightGooglePushServices.OnUnregisterListener()
        {
          public void onFailure(UpsightException paramAnonymous2UpsightException)
          {
            Log.e("Upsight-UnityPush", "unregistration failed: " + paramAnonymous2UpsightException);
          }
          
          public void onSuccess()
          {
            Log.i("Upsight-UnityPush", "unregistration succeeded");
          }
        });
      }
    });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/unity/UpsightPushManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */