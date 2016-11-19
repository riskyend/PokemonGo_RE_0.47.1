package com.upsight.android;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import com.upsight.android.internal.persistence.Content;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Nullable;
import javax.inject.Named;

public class UpsightContext
  extends ContextWrapper
{
  private final String mAppToken;
  private UpsightCoreComponent mCoreComponent;
  private final UpsightDataStore mDataStore;
  private final Map<String, UpsightExtension> mExtensionsMap = new ConcurrentHashMap();
  private final UpsightLogger mLogger;
  private final String mPublicKey;
  private final String mSdkPlugin;
  private final String mSid;
  
  public UpsightContext(Context paramContext, @Named("com.upsight.sdk_plugin") String paramString1, @Named("com.upsight.app_token") String paramString2, @Named("com.upsight.public_key") String paramString3, String paramString4, UpsightDataStore paramUpsightDataStore, UpsightLogger paramUpsightLogger)
  {
    super(paramContext);
    this.mSdkPlugin = paramString1;
    this.mAppToken = paramString2;
    this.mPublicKey = paramString3;
    this.mSid = paramString4;
    this.mDataStore = paramUpsightDataStore;
    this.mLogger = paramUpsightLogger;
  }
  
  public String getApplicationToken()
  {
    return this.mAppToken;
  }
  
  @Nullable
  public UpsightCoreComponent getCoreComponent()
  {
    return this.mCoreComponent;
  }
  
  public UpsightDataStore getDataStore()
  {
    return this.mDataStore;
  }
  
  public UpsightLogger getLogger()
  {
    return this.mLogger;
  }
  
  public String getPublicKey()
  {
    return this.mPublicKey;
  }
  
  public String getSdkBuild()
  {
    return getString(R.string.upsight_sdk_build);
  }
  
  public String getSdkPlugin()
  {
    return this.mSdkPlugin;
  }
  
  public String getSdkVersion()
  {
    return getString(R.string.upsight_sdk_version);
  }
  
  public String getSid()
  {
    return this.mSid;
  }
  
  @Nullable
  public UpsightExtension<?, ?> getUpsightExtension(String paramString)
  {
    return (UpsightExtension)this.mExtensionsMap.get(paramString);
  }
  
  void onCreate(UpsightCoreComponent paramUpsightCoreComponent, Map<String, UpsightExtension> paramMap)
  {
    this.mCoreComponent = paramUpsightCoreComponent;
    Object localObject = getContentResolver().acquireContentProviderClient(Content.getAuthoritytUri(this));
    if (localObject == null) {
      throw new IllegalStateException("Verify that the Upsight content provider is configured correctly in the Android Manifest:\n        <provider\n            android:name=\"com.upsight.android.internal.persistence.ContentProvider\"\n            android:authorities=\"" + getPackageName() + ".upsight\"\n" + "            android:enabled=\"true\"\n" + "            android:exported=\"false\" />");
    }
    ((ContentProviderClient)localObject).release();
    localObject = paramMap.entrySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
      UpsightExtension localUpsightExtension = (UpsightExtension)localEntry.getValue();
      localUpsightExtension.setComponent(localUpsightExtension.onResolve(paramUpsightCoreComponent.upsightContext()));
      this.mExtensionsMap.put(localEntry.getKey(), localUpsightExtension);
    }
    paramUpsightCoreComponent = paramMap.values().iterator();
    while (paramUpsightCoreComponent.hasNext())
    {
      localObject = (UpsightExtension)paramUpsightCoreComponent.next();
      ((UpsightExtension)localObject).getComponent().inject((UpsightExtension)localObject);
    }
    paramUpsightCoreComponent = paramMap.values().iterator();
    while (paramUpsightCoreComponent.hasNext()) {
      ((UpsightExtension)paramUpsightCoreComponent.next()).onCreate(this);
    }
    paramUpsightCoreComponent = paramMap.values().iterator();
    while (paramUpsightCoreComponent.hasNext()) {
      ((UpsightExtension)paramUpsightCoreComponent.next()).onPostCreate(this);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/UpsightContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */