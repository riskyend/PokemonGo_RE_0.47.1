package com.upsight.android.analytics.internal.provider;

import com.upsight.android.UpsightContext;
import com.upsight.android.UpsightException;
import com.upsight.android.analytics.provider.UpsightLocationTracker;
import com.upsight.android.analytics.provider.UpsightLocationTracker.Data;
import com.upsight.android.logger.UpsightLogger;
import com.upsight.android.persistence.UpsightDataStore;
import com.upsight.android.persistence.UpsightDataStoreListener;
import java.util.Iterator;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
final class LocationTracker
  extends UpsightLocationTracker
{
  private static final String LOG_TAG = LocationTracker.class.getSimpleName();
  private UpsightDataStore mDataStore;
  private UpsightLogger mLogger;
  
  @Inject
  LocationTracker(UpsightContext paramUpsightContext)
  {
    this.mDataStore = paramUpsightContext.getDataStore();
    this.mLogger = paramUpsightContext.getLogger();
  }
  
  public void purge()
  {
    this.mDataStore.fetch(UpsightLocationTracker.Data.class, new UpsightDataStoreListener()
    {
      public void onFailure(UpsightException paramAnonymousUpsightException)
      {
        LocationTracker.this.mLogger.e("Upsight", "Failed to remove stale location data.", new Object[] { paramAnonymousUpsightException });
      }
      
      public void onSuccess(Set<UpsightLocationTracker.Data> paramAnonymousSet)
      {
        paramAnonymousSet = paramAnonymousSet.iterator();
        while (paramAnonymousSet.hasNext())
        {
          UpsightLocationTracker.Data localData = (UpsightLocationTracker.Data)paramAnonymousSet.next();
          LocationTracker.this.mDataStore.remove(localData);
        }
      }
    });
  }
  
  public void track(final UpsightLocationTracker.Data paramData)
  {
    this.mDataStore.fetch(UpsightLocationTracker.Data.class, new UpsightDataStoreListener()
    {
      public void onFailure(UpsightException paramAnonymousUpsightException)
      {
        LocationTracker.this.mLogger.e(LocationTracker.LOG_TAG, paramAnonymousUpsightException, "Failed to fetch location data.", new Object[0]);
      }
      
      public void onSuccess(Set<UpsightLocationTracker.Data> paramAnonymousSet)
      {
        Object localObject = null;
        Iterator localIterator = paramAnonymousSet.iterator();
        paramAnonymousSet = (Set<UpsightLocationTracker.Data>)localObject;
        if (localIterator.hasNext())
        {
          paramAnonymousSet = (UpsightLocationTracker.Data)localIterator.next();
          paramAnonymousSet.setLatitude(paramData.getLatitude());
          paramAnonymousSet.setLongitude(paramData.getLongitude());
        }
        localObject = paramAnonymousSet;
        if (paramAnonymousSet == null) {
          localObject = paramData;
        }
        LocationTracker.this.mDataStore.store(localObject);
      }
    });
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/provider/LocationTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */