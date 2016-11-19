package com.nianticlabs.nia.network;

import android.content.Context;
import com.nianticlabs.nia.contextservice.ContextService;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class NianticTrustManager
  extends ContextService
  implements X509TrustManager
{
  public NianticTrustManager(Context paramContext, long paramLong)
  {
    super(paramContext, paramLong);
  }
  
  /* Error */
  public static X509TrustManager getTrustManager(String paramString, KeyStore paramKeyStore)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 23	javax/net/ssl/TrustManagerFactory:getInstance	(Ljava/lang/String;)Ljavax/net/ssl/TrustManagerFactory;
    //   4: astore_0
    //   5: aload_0
    //   6: aload_1
    //   7: invokevirtual 27	javax/net/ssl/TrustManagerFactory:init	(Ljava/security/KeyStore;)V
    //   10: aload_0
    //   11: invokevirtual 31	javax/net/ssl/TrustManagerFactory:getTrustManagers	()[Ljavax/net/ssl/TrustManager;
    //   14: astore_0
    //   15: aload_0
    //   16: arraylength
    //   17: istore_3
    //   18: iconst_0
    //   19: istore_2
    //   20: iload_2
    //   21: iload_3
    //   22: if_icmpge +33 -> 55
    //   25: aload_0
    //   26: iload_2
    //   27: aaload
    //   28: astore_1
    //   29: aload_1
    //   30: ifnull +17 -> 47
    //   33: aload_1
    //   34: instanceof 6
    //   37: ifeq +10 -> 47
    //   40: aload_1
    //   41: checkcast 6	javax/net/ssl/X509TrustManager
    //   44: astore_0
    //   45: aload_0
    //   46: areturn
    //   47: iload_2
    //   48: iconst_1
    //   49: iadd
    //   50: istore_2
    //   51: goto -31 -> 20
    //   54: astore_0
    //   55: aconst_null
    //   56: areturn
    //   57: astore_0
    //   58: goto -3 -> 55
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	61	0	paramString	String
    //   0	61	1	paramKeyStore	KeyStore
    //   19	32	2	i	int
    //   17	6	3	j	int
    // Exception table:
    //   from	to	target	type
    //   0	18	54	java/security/KeyStoreException
    //   33	45	54	java/security/KeyStoreException
    //   0	18	57	java/security/NoSuchAlgorithmException
    //   33	45	57	java/security/NoSuchAlgorithmException
  }
  
  public static X509TrustManager getTrustManager(KeyStore paramKeyStore)
  {
    return getTrustManager(TrustManagerFactory.getDefaultAlgorithm(), paramKeyStore);
  }
  
  private native void nativeCheckClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
    throws CertificateException;
  
  private native void nativeCheckServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
    throws CertificateException;
  
  private native X509Certificate[] nativeGetAcceptedIssuers();
  
  public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
    throws CertificateException
  {
    synchronized (this.callbackLock)
    {
      nativeCheckServerTrusted(paramArrayOfX509Certificate, paramString);
      return;
    }
  }
  
  public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
    throws CertificateException
  {
    synchronized (this.callbackLock)
    {
      nativeCheckServerTrusted(paramArrayOfX509Certificate, paramString);
      return;
    }
  }
  
  public X509Certificate[] getAcceptedIssuers()
  {
    synchronized (this.callbackLock)
    {
      X509Certificate[] arrayOfX509Certificate = nativeGetAcceptedIssuers();
      return arrayOfX509Certificate;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/network/NianticTrustManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */