package com.nianticlabs.nia.account;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.io.IOException;
import java.lang.ref.WeakReference;

public class AccountsActivity
  extends Activity
{
  static final String AUTH_TOKEN_SCOPE_PREFIX = "audience:server:client_id:";
  static String EXTRA_OAUTH_CLIENT_ID = "oauthClientId";
  private static final int REQUEST_CHOOSE_ACCOUNT = 1;
  private static final int REQUEST_GET_AUTH = 2;
  private static String TAG = "AccountsActivity";
  private NianticAccountManager accountManager;
  private boolean authInProgress = false;
  private AccountPreferences prefs;
  
  private void askUserToRecover(final UserRecoverableAuthException paramUserRecoverableAuthException)
  {
    runOnUiThread(new Runnable()
    {
      public void run()
      {
        AccountsActivity.this.startActivityForResult(paramUserRecoverableAuthException.getIntent(), 2);
      }
    });
  }
  
  private void failAuth(NianticAccountManager.Status paramStatus, String paramString)
  {
    Log.e(TAG, paramString);
    if (this.accountManager != null) {
      this.accountManager.setAuthToken(paramStatus, "", this.prefs.getAccountName());
    }
    finish();
  }
  
  private void getAuth(final String paramString)
  {
    new AsyncTask()
    {
      protected Void doInBackground(Void... paramAnonymousVarArgs)
      {
        AccountsActivity.getAuthTokenBlocking(AccountsActivity.this, paramString);
        return null;
      }
    }.execute(new Void[0]);
  }
  
  private void getAuthOrAccount()
  {
    String str = this.prefs.getAccountName();
    if ((str != null) && (!str.isEmpty()))
    {
      getAuth(str);
      return;
    }
    startActivityForResult(AccountPicker.newChooseAccountIntent(null, null, new String[] { "com.google" }, false, null, null, null, null), 1);
  }
  
  private static void getAuthTokenBlocking(AccountsActivity paramAccountsActivity, String paramString)
  {
    try
    {
      Log.d(TAG, "Authenticating with account: " + paramString);
      String str = paramAccountsActivity.getIntent().getStringExtra(EXTRA_OAUTH_CLIENT_ID);
      str = GoogleAuthUtil.getToken(paramAccountsActivity, paramString, "audience:server:client_id:" + str);
      if (paramAccountsActivity.accountManager != null) {
        paramAccountsActivity.accountManager.setAuthToken(NianticAccountManager.Status.OK, str, paramString);
      }
      paramAccountsActivity.postFinish();
      return;
    }
    catch (UserRecoverableAuthException paramString)
    {
      paramAccountsActivity.askUserToRecover(paramString);
      return;
    }
    catch (IOException localIOException)
    {
      Log.e(TAG, "Unable to get authToken at this time.", localIOException);
      if (paramAccountsActivity.accountManager != null) {
        paramAccountsActivity.accountManager.setAuthToken(NianticAccountManager.Status.NON_RECOVERABLE_ERROR, "", paramString);
      }
      paramAccountsActivity.postFinish();
      return;
    }
    catch (GoogleAuthException localGoogleAuthException)
    {
      Log.e(TAG, "User cannot be authenticated.", localGoogleAuthException);
      if (paramAccountsActivity.accountManager != null) {
        paramAccountsActivity.accountManager.setAuthToken(NianticAccountManager.Status.NON_RECOVERABLE_ERROR, "", paramString);
      }
      paramAccountsActivity.postFinish();
    }
  }
  
  private void postFinish()
  {
    runOnUiThread(new Runnable()
    {
      public void run()
      {
        AccountsActivity.this.finish();
      }
    });
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    String str = "Unexpected requestCode " + paramInt1;
    switch (paramInt1)
    {
    default: 
      Log.e(TAG, str);
      return;
    case 1: 
      if (paramInt2 == 0)
      {
        failAuth(NianticAccountManager.Status.USER_CANCELED_LOGIN, "User decided to cancel account selection.");
        return;
      }
      if (paramIntent == null)
      {
        failAuth(NianticAccountManager.Status.NON_RECOVERABLE_ERROR, "Attempt to choose null account, resultCode: " + paramInt2);
        return;
      }
      paramIntent = paramIntent.getStringExtra("authAccount");
      if ((paramIntent == null) || (paramIntent.isEmpty()))
      {
        failAuth(NianticAccountManager.Status.NON_RECOVERABLE_ERROR, "Attempt to choose unnamed account, resultCode: " + paramInt2);
        return;
      }
      this.prefs.setAccountName(paramIntent);
      getAuth(paramIntent);
      return;
    }
    getAuthOrAccount();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(getResources().getIdentifier("accounts_activity", "layout", getPackageName()));
    this.accountManager = null;
    paramBundle = NianticAccountManager.getInstance();
    if (paramBundle != null) {
      this.accountManager = ((NianticAccountManager)paramBundle.get());
    }
    this.prefs = AccountPreferences.getInstance(getApplicationContext());
  }
  
  protected void onResume()
  {
    super.onResume();
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
    if (i != 0)
    {
      Log.e(TAG, "Google Play Services not available, need to do something. Error code: " + i);
      if (this.accountManager != null) {
        this.accountManager.setAuthToken(NianticAccountManager.Status.NON_RECOVERABLE_ERROR, "", this.prefs.getAccountName());
      }
      finish();
    }
    while (this.authInProgress) {
      return;
    }
    this.authInProgress = true;
    getAuthOrAccount();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/account/AccountsActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */