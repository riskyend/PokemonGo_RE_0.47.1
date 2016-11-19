package com.nianticlabs.nia.useractivity;

import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionApi;
import com.google.android.gms.location.ActivityRecognitionResult;

public class ActivityRecognitionService
  extends IntentService
{
  private static final String TAG = "NianticActivityManager";
  private GoogleApiClient googleApiClient = null;
  
  public ActivityRecognitionService()
  {
    super("ActivityRecognitionService");
  }
  
  private void unregisterIntent()
  {
    this.googleApiClient = new GoogleApiClient.Builder(this).addApi(ActivityRecognition.API).addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks()
    {
      public void onConnected(Bundle arg1)
      {
        synchronized ()
        {
          if (NianticActivityManager.getInstance() == null)
          {
            PendingIntent localPendingIntent = NianticActivityManager.createPendingIntent(ActivityRecognitionService.this);
            ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(ActivityRecognitionService.this.googleApiClient, localPendingIntent);
            ActivityRecognitionService.this.googleApiClient.disconnect();
          }
          return;
        }
      }
      
      public void onConnectionSuspended(int paramAnonymousInt) {}
    }).build();
    this.googleApiClient.connect();
  }
  
  protected void onHandleIntent(Intent paramIntent)
  {
    paramIntent = ActivityRecognitionResult.extractResult(paramIntent);
    Log.d("NianticActivityManager", "Got activity result" + paramIntent.getMostProbableActivity());
    NianticActivityManager localNianticActivityManager = NianticActivityManager.getInstance();
    if (localNianticActivityManager != null)
    {
      localNianticActivityManager.receiveUpdateActivity(paramIntent);
      return;
    }
    Log.e("NianticActivityManager", "The app has closed while the ActivityRecognitionService is still receiving updates and draining the phone's battery");
    unregisterIntent();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/useractivity/ActivityRecognitionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */