package com.nianticlabs.nia.location;

import android.content.Context;
import android.location.GpsSatellite;
import android.location.Location;
import com.nianticlabs.nia.contextservice.ContextService;
import com.nianticlabs.nia.contextservice.ServiceStatus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NianticLocationManager
  extends ContextService
{
  static final boolean ENABLE_VERBOSE_LOGS = false;
  private static final String FUSED_PROVIDER_NAME = "fused";
  private static final float GPS_UPDATE_DISTANCE_M = 0.0F;
  private static final int GPS_UPDATE_TIME_MSEC = 1000;
  private static final long INITIALIZATION_WAIT_TIME_MS = 2000L;
  private static final float NET_UPDATE_DISTANCE_M = 0.0F;
  private static final int NET_UPDATE_TIME_MSEC = 5000;
  private static final String TAG = "NianticLocationManager";
  private float gpsUpdateDistanceM = 0.0F;
  private int gpsUpdateTimeMs = 1000;
  private float netUpdateDistanceM = 0.0F;
  private int netUpdateTimeMs = 5000;
  private final List<Provider> providers;
  private boolean started = false;
  private final Map<String, ServiceStatus> statusMap = new HashMap();
  
  public NianticLocationManager(Context paramContext, long paramLong)
  {
    super(paramContext, paramLong);
    this.statusMap.put("gps", ServiceStatus.UNDEFINED);
    this.statusMap.put("network", ServiceStatus.UNDEFINED);
    this.statusMap.put("fused", ServiceStatus.UNDEFINED);
    this.providers = new ArrayList(3);
  }
  
  private void addProvider(final String paramString, Provider paramProvider)
  {
    this.providers.add(paramProvider);
    if ((paramProvider instanceof GpsProvider))
    {
      paramProvider.setListener(new GpsProvider.GpsProviderListener()
      {
        public void onGpsStatusUpdate(int paramAnonymousInt, GpsSatellite[] paramAnonymousArrayOfGpsSatellite)
        {
          NianticLocationManager.this.gpsStatusUpdate(paramAnonymousInt, paramAnonymousArrayOfGpsSatellite);
        }
        
        public void onProviderLocation(Location paramAnonymousLocation)
        {
          NianticLocationManager.this.locationUpdate(paramAnonymousLocation, NianticLocationManager.access$200(NianticLocationManager.this));
        }
        
        public void onProviderStatus(ServiceStatus paramAnonymousServiceStatus)
        {
          NianticLocationManager.this.statusMap.put(paramString, paramAnonymousServiceStatus);
          NianticLocationManager.this.locationUpdate(null, NianticLocationManager.access$200(NianticLocationManager.this));
        }
      });
      return;
    }
    paramProvider.setListener(new Provider.ProviderListener()
    {
      public void onProviderLocation(Location paramAnonymousLocation)
      {
        NianticLocationManager.this.locationUpdate(paramAnonymousLocation, NianticLocationManager.access$200(NianticLocationManager.this));
      }
      
      public void onProviderStatus(ServiceStatus paramAnonymousServiceStatus)
      {
        NianticLocationManager.this.statusMap.put(paramString, paramAnonymousServiceStatus);
      }
    });
  }
  
  private void createProviders()
  {
    if (this.providers.size() == 3) {
      return;
    }
    addProvider("fused", new FusedLocationProvider(this.context, this.gpsUpdateTimeMs, this.gpsUpdateDistanceM));
    addProvider("gps", new LocationManagerProvider(this.context, "gps", this.gpsUpdateTimeMs, this.gpsUpdateDistanceM));
    addProvider("network", new LocationManagerProvider(this.context, "network", this.netUpdateTimeMs, this.netUpdateDistanceM));
  }
  
  private void doStart()
  {
    if (!this.started)
    {
      createProviders();
      locationUpdate(null, statusArray());
      Iterator localIterator = this.providers.iterator();
      while (localIterator.hasNext()) {
        ((Provider)localIterator.next()).onStart();
      }
      this.started = true;
    }
  }
  
  private void gpsStatusUpdate(int paramInt, GpsSatellite[] paramArrayOfGpsSatellite)
  {
    synchronized (this.callbackLock)
    {
      nativeGpsStatusUpdate(paramInt, paramArrayOfGpsSatellite);
      return;
    }
  }
  
  private void locationUpdate(Location paramLocation, int[] paramArrayOfInt)
  {
    synchronized (this.callbackLock)
    {
      nativeLocationUpdate(paramLocation, paramArrayOfInt, this.context);
      return;
    }
  }
  
  private native void nativeGpsStatusUpdate(int paramInt, GpsSatellite[] paramArrayOfGpsSatellite);
  
  private native void nativeLocationUpdate(Location paramLocation, int[] paramArrayOfInt, Context paramContext);
  
  private int[] statusArray()
  {
    return new int[] { ((ServiceStatus)this.statusMap.get("gps")).ordinal(), ((ServiceStatus)this.statusMap.get("network")).ordinal(), ((ServiceStatus)this.statusMap.get("fused")).ordinal() };
  }
  
  public void configureLocationParameters(final double paramDouble, final int paramInt1, int paramInt2)
  {
    ContextService.runOnServiceHandler(new Runnable()
    {
      public void run()
      {
        if (NianticLocationManager.this.started) {
          throw new IllegalStateException("Already started.");
        }
        NianticLocationManager.access$502(NianticLocationManager.this, paramInt1);
        NianticLocationManager.access$602(NianticLocationManager.this, (float)paramDouble);
        NianticLocationManager.access$702(NianticLocationManager.this, this.val$net_update_time_ms);
        NianticLocationManager.access$802(NianticLocationManager.this, (float)paramDouble);
        NianticLocationManager.this.doStart();
      }
    });
  }
  
  public void onPause()
  {
    Iterator localIterator = this.providers.iterator();
    while (localIterator.hasNext()) {
      ((Provider)localIterator.next()).onPause();
    }
  }
  
  public void onResume()
  {
    if (!this.started) {
      doStart();
    }
    Iterator localIterator = this.providers.iterator();
    while (localIterator.hasNext()) {
      ((Provider)localIterator.next()).onResume();
    }
  }
  
  public void onStart() {}
  
  public void onStop()
  {
    Iterator localIterator = this.providers.iterator();
    while (localIterator.hasNext()) {
      ((Provider)localIterator.next()).onStop();
    }
    this.started = false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/location/NianticLocationManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */