package com.upsight.android.managedvariables.internal.type;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.text.TextUtils;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.internal.dispatcher.schema.AbstractUxmBlockProvider;
import com.upsight.android.internal.util.PreferencesHelper;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.managedvariables.type.UpsightManagedBoolean;
import com.upsight.android.managedvariables.type.UpsightManagedFloat;
import com.upsight.android.managedvariables.type.UpsightManagedInt;
import com.upsight.android.managedvariables.type.UpsightManagedString;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import javax.inject.Named;

public class UxmBlockProvider
  extends AbstractUxmBlockProvider
  implements SharedPreferences.OnSharedPreferenceChangeListener
{
  private static final String HASH_ALGORITHM = "SHA-1";
  private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
  private Observer mBundleHashObserver = new Observer()
  {
    public void update(Observable paramAnonymousObservable, Object paramAnonymousObject)
    {
      UxmBlockProvider.this.put("bundle.hash", UxmBlockProvider.this.getBundleHash());
    }
  };
  private MessageDigest mDigest;
  private UpsightContext mUpsight;
  private UxmSchema mUxmSchema;
  private String mUxmSchemaRawString;
  
  UxmBlockProvider(UpsightContext paramUpsightContext, @Named("stringRawUxmSchema") String paramString, UxmSchema paramUxmSchema)
  {
    this.mUpsight = paramUpsightContext;
    this.mUxmSchemaRawString = paramString;
    this.mUxmSchema = paramUxmSchema;
    try
    {
      this.mDigest = MessageDigest.getInstance("SHA-1");
      PreferencesHelper.registerListener(paramUpsightContext, this);
      subscribeManagedVariables();
      put("bundle.schema_hash", getBundleSchemaHash());
      put("bundle.id", getBundleId());
      put("bundle.hash", getBundleHash());
      return;
    }
    catch (NoSuchAlgorithmException paramString)
    {
      for (;;)
      {
        paramUpsightContext.getLogger().e("Upsight", paramString, "Failed to generate UXM hashes because SHA-1 is unavailable on this device", new Object[0]);
      }
    }
  }
  
  private static String bytesToHex(byte[] paramArrayOfByte)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (paramArrayOfByte != null)
    {
      localObject1 = localObject2;
      if (paramArrayOfByte.length > 0)
      {
        localObject1 = new char[paramArrayOfByte.length * 2];
        int i = 0;
        while (i < paramArrayOfByte.length)
        {
          int j = paramArrayOfByte[i] & 0xFF;
          localObject1[(i * 2)] = HEX_ARRAY[(j >>> 4)];
          localObject1[(i * 2 + 1)] = HEX_ARRAY[(j & 0xF)];
          i += 1;
        }
        localObject1 = new String((char[])localObject1);
      }
    }
    return (String)localObject1;
  }
  
  private String generateHash(String paramString)
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    try
    {
      if (this.mDigest != null)
      {
        localObject1 = localObject2;
        if (!TextUtils.isEmpty(paramString))
        {
          paramString = paramString.getBytes();
          this.mDigest.update(paramString, 0, paramString.length);
          localObject1 = bytesToHex(this.mDigest.digest());
        }
      }
      return (String)localObject1;
    }
    finally {}
  }
  
  private void subscribeManagedVariables()
  {
    Iterator localIterator = this.mUxmSchema.getAllOrdered().iterator();
    while (localIterator.hasNext())
    {
      UxmSchema.BaseSchema localBaseSchema = (UxmSchema.BaseSchema)localIterator.next();
      if ("com.upsight.uxm.string".equals(localBaseSchema.type)) {
        UpsightManagedString.fetch(this.mUpsight, localBaseSchema.tag).addObserver(this.mBundleHashObserver);
      } else if ("com.upsight.uxm.boolean".equals(localBaseSchema.type)) {
        UpsightManagedBoolean.fetch(this.mUpsight, localBaseSchema.tag).addObserver(this.mBundleHashObserver);
      } else if ("com.upsight.uxm.integer".equals(localBaseSchema.type)) {
        UpsightManagedInt.fetch(this.mUpsight, localBaseSchema.tag).addObserver(this.mBundleHashObserver);
      } else if ("com.upsight.uxm.float".equals(localBaseSchema.type)) {
        UpsightManagedFloat.fetch(this.mUpsight, localBaseSchema.tag).addObserver(this.mBundleHashObserver);
      }
    }
  }
  
  public String getBundleHash()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = this.mUxmSchema.getAllOrdered().iterator();
    if (localIterator.hasNext())
    {
      UxmSchema.BaseSchema localBaseSchema = (UxmSchema.BaseSchema)localIterator.next();
      Object localObject = null;
      if ("com.upsight.uxm.string".equals(localBaseSchema.type)) {
        localObject = UpsightManagedString.fetch(this.mUpsight, localBaseSchema.tag).get();
      }
      for (;;)
      {
        localStringBuilder.append(localBaseSchema.tag).append(localObject).append(localBaseSchema.type);
        break;
        if ("com.upsight.uxm.boolean".equals(localBaseSchema.type)) {
          localObject = UpsightManagedBoolean.fetch(this.mUpsight, localBaseSchema.tag).get();
        } else if ("com.upsight.uxm.integer".equals(localBaseSchema.type)) {
          localObject = UpsightManagedInt.fetch(this.mUpsight, localBaseSchema.tag).get();
        } else if ("com.upsight.uxm.float".equals(localBaseSchema.type)) {
          localObject = UpsightManagedFloat.fetch(this.mUpsight, localBaseSchema.tag).get();
        }
      }
    }
    return generateHash(localStringBuilder.toString());
  }
  
  public String getBundleId()
  {
    return PreferencesHelper.getString(this.mUpsight, "uxmBundleId", null);
  }
  
  public String getBundleSchemaHash()
  {
    return generateHash(this.mUxmSchemaRawString);
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    if ("uxmBundleId".equals(paramString)) {
      put("bundle.id", getBundleId());
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/managedvariables/internal/type/UxmBlockProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */