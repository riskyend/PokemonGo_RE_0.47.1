package com.nianticlabs.nia.platform;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.safetynet.SafetyNetApi.AttestationResult;
import com.nianticlabs.nia.contextservice.ContextService;
import com.nianticlabs.nia.contextservice.GoogleApiManager;
import com.nianticlabs.nia.contextservice.GoogleApiManager.Listener;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class SafetyNetService
  extends ContextService
{
  private GoogleApiManager.Listener googleApiListener = new GoogleApiManager.Listener()
  {
    public void onConnected()
    {
      SafetyNetService.access$002(SafetyNetService.this, SafetyNetService.GoogleApiState.STARTED);
      synchronized (SafetyNetService.this.lock)
      {
        if (SafetyNetService.this.queuedNonce != null)
        {
          byte[] arrayOfByte = SafetyNetService.this.queuedNonce;
          SafetyNetService.access$202(SafetyNetService.this, null);
          SafetyNetService.this.attest(arrayOfByte);
        }
        return;
      }
    }
    
    public void onConnectionFailed(ConnectionResult paramAnonymousConnectionResult)
    {
      SafetyNetService.access$002(SafetyNetService.this, SafetyNetService.GoogleApiState.STOPPED);
    }
    
    public void onDisconnected()
    {
      SafetyNetService.access$002(SafetyNetService.this, SafetyNetService.GoogleApiState.STOPPED);
    }
  };
  private final GoogleApiManager googleApiManager;
  private GoogleApiState googleApiState = GoogleApiState.STOPPED;
  private final Object lock = new Object();
  private byte[] queuedNonce = null;
  private final Map<ByteBuffer, PendingResult<SafetyNetApi.AttestationResult>> requestMap = new HashMap();
  
  public SafetyNetService(Context paramContext, long paramLong)
  {
    super(paramContext, paramLong);
    this.googleApiManager = new GoogleApiManager(paramContext);
    this.googleApiManager.setListener(this.googleApiListener);
    this.googleApiManager.builder().addApi(SafetyNet.API);
    this.googleApiManager.build();
  }
  
  private void attestResponse(byte[] paramArrayOfByte, String paramString)
  {
    synchronized (this.callbackLock)
    {
      nativeAttestResponse(paramArrayOfByte, paramString);
      return;
    }
  }
  
  private static boolean checkResult(String paramString)
  {
    try
    {
      paramString = paramString.split("\\.");
      if (paramString.length == 3)
      {
        paramString = new JSONObject(new String(Base64.decode(paramString[1], 0)));
        if (!paramString.has("error")) {
          return true;
        }
        Log.e("SafetyNetService", "Got error from SafetyNet " + paramString.getString("error"));
        return false;
      }
    }
    catch (Throwable paramString) {}
    return false;
  }
  
  private native void nativeAttestResponse(byte[] paramArrayOfByte, String paramString);
  
  public void attest(final byte[] paramArrayOfByte)
  {
    runOnServiceHandler(new Runnable()
    {
      public void run()
      {
        if (SafetyNetService.this.googleApiState == SafetyNetService.GoogleApiState.STARTED)
        {
          final ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
          synchronized (SafetyNetService.this.lock)
          {
            if ((PendingResult)SafetyNetService.this.requestMap.get(localByteBuffer) == null)
            {
              PendingResult localPendingResult = SafetyNet.SafetyNetApi.attest(SafetyNetService.this.googleApiManager.getClient(), paramArrayOfByte);
              SafetyNetService.this.requestMap.put(localByteBuffer, localPendingResult);
              localPendingResult.setResultCallback(new ResultCallback()
              {
                public void onResult(SafetyNetApi.AttestationResult paramAnonymous2AttestationResult)
                {
                  synchronized (SafetyNetService.this.lock)
                  {
                    SafetyNetService.this.requestMap.remove(localByteBuffer);
                    if (!paramAnonymous2AttestationResult.getStatus().isSuccess()) {
                      break label104;
                    }
                    paramAnonymous2AttestationResult = paramAnonymous2AttestationResult.getJwsResult();
                    if (SafetyNetService.checkResult(paramAnonymous2AttestationResult))
                    {
                      SafetyNetService.this.attestResponse(SafetyNetService.2.this.val$nonce, paramAnonymous2AttestationResult);
                      return;
                    }
                  }
                  SafetyNetService.this.attestResponse(SafetyNetService.2.this.val$nonce, null);
                  return;
                  label104:
                  SafetyNetService.this.attestResponse(SafetyNetService.2.this.val$nonce, null);
                }
              });
            }
            return;
          }
        }
        int i = 0;
        synchronized (SafetyNetService.this.lock)
        {
          if (SafetyNetService.this.queuedNonce == null)
          {
            SafetyNetService.access$202(SafetyNetService.this, paramArrayOfByte);
            if (i != 0) {
              SafetyNetService.this.attestResponse(paramArrayOfByte, null);
            }
          }
          else
          {
            i = 1;
          }
        }
      }
    });
  }
  
  public void cancel(final byte[] paramArrayOfByte)
  {
    runOnServiceHandler(new Runnable()
    {
      public void run()
      {
        if (SafetyNetService.this.googleApiState == SafetyNetService.GoogleApiState.STARTED)
        {
          ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
          ??? = SafetyNetService.this.lock;
          int j = 1;
          int i = j;
          if (1 != 0) {
            i = j;
          }
          try
          {
            if (SafetyNetService.this.queuedNonce != null)
            {
              i = j;
              if (Arrays.equals(SafetyNetService.this.queuedNonce, paramArrayOfByte))
              {
                SafetyNetService.access$202(SafetyNetService.this, null);
                i = 0;
              }
            }
            if (i != 0)
            {
              PendingResult localPendingResult = (PendingResult)SafetyNetService.this.requestMap.get(localByteBuffer);
              if (localPendingResult != null)
              {
                SafetyNetService.this.requestMap.remove(localByteBuffer);
                localPendingResult.cancel();
              }
            }
            return;
          }
          finally {}
        }
        synchronized (SafetyNetService.this.lock)
        {
          if ((SafetyNetService.this.queuedNonce != null) && (Arrays.equals(SafetyNetService.this.queuedNonce, paramArrayOfByte))) {
            SafetyNetService.access$202(SafetyNetService.this, null);
          }
          return;
        }
      }
    });
  }
  
  public void onPause()
  {
    this.googleApiManager.onPause();
  }
  
  public void onResume()
  {
    this.googleApiManager.onResume();
  }
  
  public void onStart()
  {
    this.googleApiManager.onStart();
  }
  
  public void onStop()
  {
    this.googleApiManager.onStop();
  }
  
  private static enum GoogleApiState
  {
    STARTED,  STOPPED;
    
    private GoogleApiState() {}
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/platform/SafetyNetService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */