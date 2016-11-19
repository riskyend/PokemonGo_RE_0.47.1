package com.upsight.android.googlepushservices;

import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.v4.app.NotificationCompat.BigPictureStyle;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.ImageView.ScaleType;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NoCache;
import com.upsight.android.UpsightContext;
import com.upsight.android.logger.UpsightLogger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.observables.BlockingObservable;

public abstract interface UpsightPushNotificationBuilderFactory
{
  public abstract NotificationCompat.Builder getNotificationBuilder(UpsightContext paramUpsightContext, String paramString1, String paramString2, String paramString3);
  
  public static class Default
    implements UpsightPushNotificationBuilderFactory
  {
    public static final float HTTP_REQUEST_BACKOFF_MULT = 2.0F;
    public static final int HTTP_REQUEST_MAX_RETRIES = 3;
    public static final int HTTP_REQUEST_TIMEOUT_MS = 5000;
    private RequestQueue mRequestQueue = new RequestQueue(new NoCache(), new BasicNetwork(new HurlStack()));
    
    public Default()
    {
      this.mRequestQueue.start();
    }
    
    protected Observable<Bitmap> getImageObservable(final String paramString)
    {
      Observable.create(new Observable.OnSubscribe()
      {
        public void call(final Subscriber<? super Bitmap> paramAnonymousSubscriber)
        {
          UpsightPushNotificationBuilderFactory.Default.this.mRequestQueue.add(new ImageRequest(paramString, new Response.Listener()
          {
            public void onResponse(Bitmap paramAnonymous2Bitmap)
            {
              paramAnonymousSubscriber.onNext(paramAnonymous2Bitmap);
              paramAnonymousSubscriber.onCompleted();
            }
          }, 0, 0, ImageView.ScaleType.CENTER_INSIDE, Bitmap.Config.ARGB_8888, new Response.ErrorListener()
          {
            public void onErrorResponse(VolleyError paramAnonymous2VolleyError)
            {
              paramAnonymousSubscriber.onError(paramAnonymous2VolleyError);
            }
          }).setRetryPolicy(new DefaultRetryPolicy(5000, 3, 2.0F)));
        }
      });
    }
    
    public NotificationCompat.Builder getNotificationBuilder(UpsightContext paramUpsightContext, String paramString1, String paramString2, String paramString3)
    {
      Object localObject2 = null;
      Object localObject1 = localObject2;
      if (!TextUtils.isEmpty(paramString3))
      {
        if (!isImageUrlValid(paramString3))
        {
          paramUpsightContext.getLogger().e("Upsight", "Malformed notification picture URL, displaying notification without", new Object[0]);
          localObject1 = localObject2;
        }
      }
      else {
        if (localObject1 == null) {
          break label154;
        }
      }
      label154:
      for (paramString3 = new NotificationCompat.BigPictureStyle().bigPicture((Bitmap)localObject1).setSummaryText(paramString2);; paramString3 = new NotificationCompat.BigTextStyle().bigText(paramString2))
      {
        return new NotificationCompat.Builder(paramUpsightContext.getApplicationContext()).setSmallIcon(paramUpsightContext.getApplicationInfo().icon).setStyle(paramString3).setContentTitle(paramString1).setContentText(paramString2);
        try
        {
          localObject1 = (Bitmap)getImageObservable(paramString3).toBlocking().first();
        }
        catch (Throwable paramString3)
        {
          paramUpsightContext.getLogger().e("Upsight", "Failed to download notification picture, displaying notification without", new Object[] { paramString3 });
          localObject1 = localObject2;
        }
        break;
      }
    }
    
    protected boolean isImageUrlValid(String paramString)
    {
      return Patterns.WEB_URL.matcher(paramString).matches();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/googlepushservices/UpsightPushNotificationBuilderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */