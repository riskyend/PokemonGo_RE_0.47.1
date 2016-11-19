package com.upsight.mediation.vast.util;

import android.text.TextUtils;
import com.upsight.mediation.log.FuseLog;
import com.upsight.mediation.vast.VASTPlayer;
import com.upsight.mediation.vast.VASTPlayer.VASTPlayerListener;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTools
{
  private static final String TAG = HttpTools.class.getName();
  
  public static void httpGetURL(final String paramString1, String paramString2, final VASTPlayer paramVASTPlayer)
  {
    if (!TextUtils.isEmpty(paramString2)) {
      new Thread()
      {
        public void run()
        {
          Object localObject3 = null;
          HttpURLConnection localHttpURLConnection3 = null;
          HttpURLConnection localHttpURLConnection2 = localHttpURLConnection3;
          Object localObject1 = localObject3;
          for (;;)
          {
            try
            {
              FuseLog.v(HttpTools.TAG, "connection to URL:" + this.val$url);
              localHttpURLConnection2 = localHttpURLConnection3;
              localObject1 = localObject3;
              URL localURL = new URL(this.val$url);
              localHttpURLConnection2 = localHttpURLConnection3;
              localObject1 = localObject3;
              HttpURLConnection.setFollowRedirects(true);
              localHttpURLConnection2 = localHttpURLConnection3;
              localObject1 = localObject3;
              localHttpURLConnection3 = (HttpURLConnection)localURL.openConnection();
              localHttpURLConnection2 = localHttpURLConnection3;
              localObject1 = localHttpURLConnection3;
              localHttpURLConnection3.setConnectTimeout(5000);
              localHttpURLConnection2 = localHttpURLConnection3;
              localObject1 = localHttpURLConnection3;
              localHttpURLConnection3.setRequestProperty("Connection", "close");
              localHttpURLConnection2 = localHttpURLConnection3;
              localObject1 = localHttpURLConnection3;
              localHttpURLConnection3.setRequestMethod("GET");
              localHttpURLConnection2 = localHttpURLConnection3;
              localObject1 = localHttpURLConnection3;
              int i = localHttpURLConnection3.getResponseCode();
              localHttpURLConnection2 = localHttpURLConnection3;
              localObject1 = localHttpURLConnection3;
              FuseLog.v(HttpTools.TAG, "response code:" + i + ", for URL:" + this.val$url);
              if ((i < 200) && (i > 226))
              {
                localHttpURLConnection2 = localHttpURLConnection3;
                localObject1 = localHttpURLConnection3;
                if (paramString1.equals("impression"))
                {
                  localHttpURLConnection2 = localHttpURLConnection3;
                  localObject1 = localHttpURLConnection3;
                  paramVASTPlayer.listener.vastError(900);
                }
              }
            }
            catch (Exception localException4)
            {
              HttpURLConnection localHttpURLConnection1 = localHttpURLConnection2;
              FuseLog.w(HttpTools.TAG, this.val$url + ": " + localException4.getMessage() + ":" + localException4.toString());
              localHttpURLConnection1 = localHttpURLConnection2;
              if (!paramString1.equals("impression")) {
                continue;
              }
              localHttpURLConnection1 = localHttpURLConnection2;
              paramVASTPlayer.listener.vastError(900);
              if (localHttpURLConnection2 == null) {
                continue;
              }
              try
              {
                localHttpURLConnection2.disconnect();
                return;
              }
              catch (Exception localException2)
              {
                FuseLog.w(HttpTools.TAG, localException2.toString());
                return;
              }
            }
            finally
            {
              if (localException2 == null) {
                break label372;
              }
            }
            try
            {
              localHttpURLConnection3.disconnect();
              return;
            }
            catch (Exception localException1)
            {
              FuseLog.w(HttpTools.TAG, localException1.toString());
              return;
            }
          }
          try
          {
            localException2.disconnect();
            label372:
            throw ((Throwable)localObject2);
          }
          catch (Exception localException3)
          {
            for (;;)
            {
              FuseLog.w(HttpTools.TAG, localException3.toString());
            }
          }
        }
      }.start();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/util/HttpTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */