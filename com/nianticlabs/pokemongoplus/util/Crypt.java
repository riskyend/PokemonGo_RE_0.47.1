package com.nianticlabs.pokemongoplus.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import com.nianticlabs.pokemongoplus.bridge.BackgroundBridge;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypt
{
  private static final String ALGORITHM = "AES/CTR/NoPadding";
  private static final SecureRandom secureRandom = new SecureRandom();
  
  public static byte[] encryptNonce(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    try
    {
      byte[] arrayOfByte = new byte[16];
      secureRandom.nextBytes(arrayOfByte);
      paramArrayOfByte1 = new SecretKeySpec(paramArrayOfByte1, "AES");
      Cipher localCipher = Cipher.getInstance("AES/CTR/NoPadding");
      localCipher.init(1, paramArrayOfByte1, new IvParameterSpec(arrayOfByte));
      paramArrayOfByte1 = localCipher.doFinal(paramArrayOfByte2);
      paramArrayOfByte2 = new byte[arrayOfByte.length + paramArrayOfByte1.length];
      System.arraycopy(arrayOfByte, 0, paramArrayOfByte2, 0, arrayOfByte.length);
      System.arraycopy(paramArrayOfByte1, 0, paramArrayOfByte2, arrayOfByte.length, paramArrayOfByte1.length);
      return paramArrayOfByte2;
    }
    catch (GeneralSecurityException paramArrayOfByte1)
    {
      paramArrayOfByte1.printStackTrace();
    }
    return new byte[0];
  }
  
  public static byte[] generateNonce()
  {
    byte[] arrayOfByte = new byte[16];
    secureRandom.nextBytes(arrayOfByte);
    return arrayOfByte;
  }
  
  public static byte[] getPersistedByteArray(String paramString)
  {
    paramString = getPreferences().getString(paramString, null);
    if (paramString != null) {
      return Base64.decode(paramString, 0);
    }
    return new byte[0];
  }
  
  private static SharedPreferences getPreferences()
  {
    return BackgroundBridge.currentContext.getSharedPreferences("pgp", 0);
  }
  
  public static void persistByteArray(String paramString, byte[] paramArrayOfByte)
  {
    paramArrayOfByte = Base64.encodeToString(paramArrayOfByte, 0);
    getPreferences().edit().putString(paramString, paramArrayOfByte).commit();
  }
  
  public static byte[] unencryptNonce(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    if (paramArrayOfByte2.length == 32) {
      try
      {
        paramArrayOfByte1 = new SecretKeySpec(paramArrayOfByte1, "AES");
        byte[] arrayOfByte1 = new byte[16];
        byte[] arrayOfByte2 = new byte[16];
        System.arraycopy(paramArrayOfByte2, 0, arrayOfByte1, 0, 16);
        System.arraycopy(paramArrayOfByte2, 16, arrayOfByte2, 0, 16);
        paramArrayOfByte2 = Cipher.getInstance("AES/CTR/NoPadding");
        paramArrayOfByte2.init(2, paramArrayOfByte1, new IvParameterSpec(arrayOfByte1));
        paramArrayOfByte1 = paramArrayOfByte2.doFinal(arrayOfByte2);
        return paramArrayOfByte1;
      }
      catch (GeneralSecurityException paramArrayOfByte1)
      {
        paramArrayOfByte1.printStackTrace();
      }
    }
    return new byte[0];
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/pokemongoplus/util/Crypt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */