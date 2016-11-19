package com.nianticlabs.nia.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AccountPreferences
{
  private static final String KEY_ACCOUNT_NAME = "accountName";
  private static AccountPreferences instance = null;
  private static final Object staticMutex = new Object();
  private final SharedPreferences prefs;
  
  private AccountPreferences(Context paramContext)
  {
    this.prefs = paramContext.getSharedPreferences(paramContext.getPackageName() + ".PREFS", 0);
  }
  
  public static AccountPreferences getInstance(Context paramContext)
  {
    synchronized (staticMutex)
    {
      if (instance == null) {
        instance = new AccountPreferences(paramContext);
      }
      paramContext = instance;
      return paramContext;
    }
  }
  
  public void clearAccount()
  {
    try
    {
      this.prefs.edit().remove("accountName").apply();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String getAccountName()
  {
    try
    {
      String str = this.prefs.getString("accountName", "");
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void setAccountName(String paramString)
  {
    try
    {
      this.prefs.edit().putString("accountName", paramString).apply();
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/account/AccountPreferences.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */