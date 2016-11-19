package com.upsight.android.analytics.internal.dispatcher.schema;

import android.location.Location;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.provider.UpsightDataProvider;
import com.upsight.android.analytics.provider.UpsightLocationTracker.Data;
import com.upsight.android.persistence.UpsightDataStore;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import rx.Observable;
import rx.observables.BlockingObservable;

public class LocationBlockProvider
  extends UpsightDataProvider
{
  private static final String DATETIME_FORMAT_ISO_8601 = "yyyy-MM-dd HH:mm:ss.SSSZ";
  public static final String LATITUDE_KEY = "location.lat";
  public static final String LONGITUDE_KEY = "location.lon";
  public static final String TIME_ZONE_KEY = "location.tz";
  private static final int TIME_ZONE_OFFSET_LENGTH = 5;
  private static final Pattern TIME_ZONE_OFFSET_PATTERN = Pattern.compile("[+-][0-9]{4}");
  private TimeZone mCurrentTimeZone = null;
  private String mTimeZoneOffset = null;
  private UpsightContext mUpsight;
  
  LocationBlockProvider(UpsightContext paramUpsightContext)
  {
    this.mUpsight = paramUpsightContext;
  }
  
  private String fetchCurrentTimeZone()
  {
    TimeZone localTimeZone = TimeZone.getDefault();
    if ((localTimeZone != null) && (!localTimeZone.equals(this.mCurrentTimeZone)))
    {
      Object localObject = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ", Locale.US);
      ((SimpleDateFormat)localObject).setTimeZone(localTimeZone);
      localObject = ((SimpleDateFormat)localObject).format(new Date());
      if (localObject != null)
      {
        int i = ((String)localObject).length();
        if (i > 5)
        {
          localObject = ((String)localObject).substring(i - 5, i);
          if (isTimeZoneOffsetValid((String)localObject))
          {
            this.mCurrentTimeZone = localTimeZone;
            this.mTimeZoneOffset = ((String)localObject);
          }
        }
      }
    }
    return this.mTimeZoneOffset;
  }
  
  private UpsightLocationTracker.Data fetchLatestLocation()
  {
    return (UpsightLocationTracker.Data)this.mUpsight.getDataStore().fetchObservable(UpsightLocationTracker.Data.class).lastOrDefault(null).toBlocking().first();
  }
  
  public Set<String> availableKeys()
  {
    return new HashSet(Arrays.asList(new String[] { "location.tz", "location.lat", "location.lon" }));
  }
  
  public Object get(String paramString)
  {
    Object localObject = null;
    int i = 0;
    for (;;)
    {
      try
      {
        switch (paramString.hashCode())
        {
        case 552272735: 
          paramString = super.get(paramString);
          return paramString;
        }
      }
      finally {}
      if (paramString.equals("location.tz"))
      {
        break label161;
        if (paramString.equals("location.lat"))
        {
          i = 1;
          break label161;
          if (paramString.equals("location.lon"))
          {
            i = 2;
            break label161;
            paramString = fetchCurrentTimeZone();
            continue;
            UpsightLocationTracker.Data localData = fetchLatestLocation();
            paramString = (String)localObject;
            if (localData == null) {
              continue;
            }
            paramString = Location.convert(localData.getLatitude(), 0);
            continue;
            localData = fetchLatestLocation();
            paramString = (String)localObject;
            if (localData == null) {
              continue;
            }
            paramString = Location.convert(localData.getLongitude(), 0);
            continue;
          }
        }
      }
      i = -1;
      label161:
      switch (i)
      {
      }
    }
  }
  
  boolean isTimeZoneOffsetValid(String paramString)
  {
    return TIME_ZONE_OFFSET_PATTERN.matcher(paramString).matches();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/schema/LocationBlockProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */