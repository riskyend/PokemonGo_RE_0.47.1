package com.voxelbusters.nativeplugins.features.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.voxelbusters.nativeplugins.NativePluginHelper;
import com.voxelbusters.nativeplugins.utilities.StringUtility;
import java.util.HashMap;

public class UiActivity
  extends Activity
{
  AlertDialog alertDialog;
  Bundle bundleInfo;
  boolean paused;
  
  private void showAlertDialog(Bundle paramBundle)
  {
    int i = 3;
    final String str = paramBundle.getString("tag");
    final String[] arrayOfString = paramBundle.getStringArray("button-list");
    this.alertDialog = getDialogWithDefaultDetails(paramBundle);
    final int j;
    if (arrayOfString.length > 3) {
      j = 0;
    }
    for (;;)
    {
      if (j >= i)
      {
        this.alertDialog.show();
        return;
        i = arrayOfString.length;
        break;
      }
      paramBundle = new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface = arrayOfString[j];
          String str = str;
          HashMap localHashMap = new HashMap();
          localHashMap.put("button-pressed", paramAnonymousDialogInterface);
          localHashMap.put("caller", str);
          NativePluginHelper.sendMessage("AlertDialogClosed", localHashMap);
          UiHandler.getInstance().onFinish(str);
          UiActivity.this.finish();
        }
      };
      this.alertDialog.setButton(-1 - j, arrayOfString[j], paramBundle);
      j += 1;
    }
  }
  
  private void showLoginPrompt(Bundle paramBundle)
  {
    final String[] arrayOfString = paramBundle.getStringArray("button-list");
    setContentView(new LinearLayout(this));
    this.alertDialog = getDialogWithDefaultDetails(paramBundle);
    final EditText localEditText1 = new EditText(this);
    final EditText localEditText2 = new EditText(this);
    Object localObject = new LinearLayout(this);
    ((LinearLayout)localObject).setOrientation(1);
    ((LinearLayout)localObject).addView(localEditText1);
    ((LinearLayout)localObject).addView(localEditText2);
    this.alertDialog.setView((View)localObject);
    localObject = paramBundle.getString("place-holder-text-1");
    paramBundle = paramBundle.getString("place-holder-text-2");
    localEditText2.setTransformationMethod(new PasswordTransformationMethod());
    localEditText1.setHint((CharSequence)localObject);
    localEditText2.setHint(paramBundle);
    int i;
    final int j;
    if (arrayOfString.length > 3)
    {
      i = 3;
      j = 0;
    }
    for (;;)
    {
      if (j >= i)
      {
        this.alertDialog.show();
        return;
        i = arrayOfString.length;
        break;
      }
      paramBundle = new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface = new HashMap();
          paramAnonymousDialogInterface.put("button-pressed", arrayOfString[j]);
          paramAnonymousDialogInterface.put("username", localEditText1.getText().toString());
          paramAnonymousDialogInterface.put("password", localEditText2.getText().toString());
          NativePluginHelper.sendMessage("LoginPromptDialogClosed", paramAnonymousDialogInterface);
          UiActivity.this.finish();
        }
      };
      this.alertDialog.setButton(-1 - j, arrayOfString[j], paramBundle);
      j += 1;
    }
  }
  
  private void showSinglePrompt(Bundle paramBundle)
  {
    int i = 3;
    final String[] arrayOfString = paramBundle.getStringArray("button-list");
    this.alertDialog = getDialogWithDefaultDetails(paramBundle);
    final EditText localEditText = new EditText(this);
    this.alertDialog.setView(localEditText);
    boolean bool = paramBundle.getBoolean("is-secure");
    paramBundle = paramBundle.getString("place-holder-text-1");
    if (bool) {
      localEditText.setTransformationMethod(new PasswordTransformationMethod());
    }
    if (paramBundle != null) {
      localEditText.setHint(paramBundle);
    }
    final int j;
    if (arrayOfString.length > 3) {
      j = 0;
    }
    for (;;)
    {
      if (j >= i)
      {
        this.alertDialog.show();
        return;
        i = arrayOfString.length;
        break;
      }
      paramBundle = new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface = localEditText.getText().toString();
          HashMap localHashMap = new HashMap();
          localHashMap.put("button-pressed", arrayOfString[j]);
          localHashMap.put("input", paramAnonymousDialogInterface);
          NativePluginHelper.sendMessage("SingleFieldPromptDialogClosed", localHashMap);
          UiActivity.this.finish();
        }
      };
      this.alertDialog.setButton(-1 - j, arrayOfString[j], paramBundle);
      j += 1;
    }
  }
  
  AlertDialog getDialogWithDefaultDetails(Bundle paramBundle)
  {
    String str = paramBundle.getString("title");
    paramBundle = paramBundle.getString("message");
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    if (!StringUtility.isNullOrEmpty(str)) {
      localBuilder.setTitle(str);
    }
    if (!StringUtility.isNullOrEmpty(paramBundle)) {
      localBuilder.setMessage(paramBundle);
    }
    localBuilder.setCancelable(false);
    return localBuilder.create();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (this.bundleInfo == null) {
      this.bundleInfo = getIntent().getExtras();
    }
    paramBundle = eUiType.values()[this.bundleInfo.getInt("type")];
    if (paramBundle == eUiType.ALERT_DIALOG) {
      showAlertDialog(this.bundleInfo);
    }
    do
    {
      return;
      if (paramBundle == eUiType.SINGLE_FIELD_PROMPT)
      {
        showSinglePrompt(this.bundleInfo);
        return;
      }
    } while (paramBundle != eUiType.LOGIN_PROMPT);
    showLoginPrompt(this.bundleInfo);
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    if (this.alertDialog != null)
    {
      this.alertDialog.dismiss();
      this.alertDialog = null;
    }
  }
  
  protected void onPause()
  {
    super.onPause();
    this.paused = true;
  }
  
  @SuppressLint({"NewApi"})
  protected void onResume()
  {
    super.onResume();
    if (this.paused)
    {
      finish();
      new Handler().postDelayed(new Runnable()
      {
        public void run()
        {
          UiActivity.this.startActivity(UiActivity.this.getIntent());
        }
      }, 10L);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/voxelbusters/nativeplugins/features/ui/UiActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */