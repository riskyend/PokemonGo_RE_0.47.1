package com.android.volley.toolbox;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.android.volley.AuthFailureError;

public class AndroidAuthenticator
  implements Authenticator
{
  private final Account mAccount;
  private final AccountManager mAccountManager;
  private final String mAuthTokenType;
  private final boolean mNotifyAuthFailure;
  
  AndroidAuthenticator(AccountManager paramAccountManager, Account paramAccount, String paramString, boolean paramBoolean)
  {
    this.mAccountManager = paramAccountManager;
    this.mAccount = paramAccount;
    this.mAuthTokenType = paramString;
    this.mNotifyAuthFailure = paramBoolean;
  }
  
  public AndroidAuthenticator(Context paramContext, Account paramAccount, String paramString)
  {
    this(paramContext, paramAccount, paramString, false);
  }
  
  public AndroidAuthenticator(Context paramContext, Account paramAccount, String paramString, boolean paramBoolean)
  {
    this(AccountManager.get(paramContext), paramAccount, paramString, paramBoolean);
  }
  
  public Account getAccount()
  {
    return this.mAccount;
  }
  
  public String getAuthToken()
    throws AuthFailureError
  {
    AccountManagerFuture localAccountManagerFuture = this.mAccountManager.getAuthToken(this.mAccount, this.mAuthTokenType, this.mNotifyAuthFailure, null, null);
    Bundle localBundle;
    try
    {
      localBundle = (Bundle)localAccountManagerFuture.getResult();
      Object localObject2 = null;
      Object localObject1 = localObject2;
      if (!localAccountManagerFuture.isDone()) {
        break label105;
      }
      localObject1 = localObject2;
      if (localAccountManagerFuture.isCancelled()) {
        break label105;
      }
      if (localBundle.containsKey("intent")) {
        throw new AuthFailureError((Intent)localBundle.getParcelable("intent"));
      }
    }
    catch (Exception localException)
    {
      throw new AuthFailureError("Error while retrieving auth token", localException);
    }
    String str = localBundle.getString("authtoken");
    label105:
    if (str == null)
    {
      str = String.valueOf(this.mAuthTokenType);
      if (str.length() != 0) {}
      for (str = "Got null auth token for type: ".concat(str);; str = new String("Got null auth token for type: ")) {
        throw new AuthFailureError(str);
      }
    }
    return str;
  }
  
  public String getAuthTokenType()
  {
    return this.mAuthTokenType;
  }
  
  public void invalidateAuthToken(String paramString)
  {
    this.mAccountManager.invalidateAuthToken(this.mAccount.type, paramString);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/android/volley/toolbox/AndroidAuthenticator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */