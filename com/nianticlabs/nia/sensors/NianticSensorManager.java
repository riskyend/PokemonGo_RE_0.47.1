package com.nianticlabs.nia.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.WindowManager;
import com.nianticlabs.nia.contextservice.ContextService;
import com.nianticlabs.nia.contextservice.ServiceStatus;

public class NianticSensorManager
  extends ContextService
  implements SensorEventListener
{
  private static final float ANGLE_CHANGE_THRESHOLD_DEGREES = 1.0F;
  private static final int DECLINATION_UPDATE_INTERVAL_MSEC = 600000;
  private static final boolean ENABLE_VERBOSE_LOGS = false;
  private static final int MAX_SENSOR_UPDATE_DIFF_MSEC = 5000;
  private static final int MIN_SENSOR_UPDATE_INTERVAL_MSEC = 50;
  private static final float SINE_OF_45_DEGREES = (float)Math.sqrt(2.0D) / 2.0F;
  private static final String TAG = "NianticSensorManager";
  private Sensor accelerometer;
  private float[] accelerometerData = new float[3];
  private long accelerometerReadingMs;
  private float declination;
  private long declinationUpdateTimeMs;
  private final Display display;
  private Sensor gravity;
  private Sensor gyroscope;
  private float lastAzimuthUpdate;
  private float lastPitchUpdate;
  private long lastUpdateTimeMs;
  private Sensor linearAcceleration;
  private Sensor magnetic;
  private float[] magneticData = new float[3];
  private long magnetometerReadingMs;
  private final AngleFilter orientationFilter = new AngleFilter(true);
  private Sensor rotation;
  private float[] rotationData = new float[5];
  private final SensorManager sensorManager;
  private ServiceStatus status = ServiceStatus.UNDEFINED;
  private final float[] tmpMatrix1 = new float[9];
  private final float[] tmpMatrix2 = new float[9];
  private final float[] tmpMatrix3 = new float[9];
  private final float[] tmpOrientationAngles = new float[3];
  
  public NianticSensorManager(Context paramContext, long paramLong)
  {
    super(paramContext, paramLong);
    this.display = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
    this.sensorManager = ((SensorManager)paramContext.getSystemService("sensor"));
    this.gravity = this.sensorManager.getDefaultSensor(9);
    this.gyroscope = this.sensorManager.getDefaultSensor(4);
    this.accelerometer = this.sensorManager.getDefaultSensor(1);
    this.magnetic = this.sensorManager.getDefaultSensor(2);
    this.rotation = this.sensorManager.getDefaultSensor(11);
    this.linearAcceleration = this.sensorManager.getDefaultSensor(10);
  }
  
  private void calcMatrixFromRotationVector(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    float f8 = paramArrayOfFloat1[3];
    float f9 = paramArrayOfFloat1[0];
    float f10 = paramArrayOfFloat1[1];
    float f11 = paramArrayOfFloat1[2];
    float f1 = 2.0F * f9 * f9;
    float f2 = 2.0F * f10 * f10;
    float f3 = 2.0F * f11 * f11;
    float f4 = 2.0F * f9 * f10;
    float f5 = 2.0F * f11 * f8;
    float f6 = 2.0F * f9 * f11;
    float f7 = 2.0F * f10 * f8;
    f10 = 2.0F * f10 * f11;
    f8 = 2.0F * f9 * f8;
    paramArrayOfFloat2[0] = (1.0F - f2 - f3);
    paramArrayOfFloat2[1] = (f4 - f5);
    paramArrayOfFloat2[2] = (f6 + f7);
    paramArrayOfFloat2[3] = (f4 + f5);
    paramArrayOfFloat2[4] = (1.0F - f1 - f3);
    paramArrayOfFloat2[5] = (f10 - f8);
    paramArrayOfFloat2[6] = (f6 - f7);
    paramArrayOfFloat2[7] = (f10 + f8);
    paramArrayOfFloat2[8] = (1.0F - f1 - f2);
  }
  
  private float computeRotationVectorW(float[] paramArrayOfFloat)
  {
    float f1 = 0.0F;
    int j = paramArrayOfFloat.length;
    int i = 0;
    while (i < j)
    {
      float f2 = paramArrayOfFloat[i];
      f1 += f2 * f2;
      i += 1;
    }
    return (float)Math.sqrt(1.0F - Math.min(f1, 1.0F));
  }
  
  private float getDeclination()
  {
    long l = System.currentTimeMillis();
    if ((this.declinationUpdateTimeMs + 600000L > l) && (0 != 0))
    {
      this.declinationUpdateTimeMs = l;
      throw new NullPointerException();
    }
    return this.declination;
  }
  
  private native void nativeCompassUpdate(long paramLong, float paramFloat);
  
  private native void nativeSensorUpdate(int paramInt, long paramLong, float[] paramArrayOfFloat);
  
  private void safeCompassUpdate(long paramLong, float paramFloat)
  {
    synchronized (this.callbackLock)
    {
      nativeCompassUpdate(paramLong, paramFloat);
      return;
    }
  }
  
  private void safeSensorUpdate(int paramInt, long paramLong, float[] paramArrayOfFloat)
  {
    synchronized (this.callbackLock)
    {
      nativeSensorUpdate(paramInt, paramLong, paramArrayOfFloat);
      return;
    }
  }
  
  private void startSensorManager()
  {
    if (this.gravity != null) {
      this.sensorManager.registerListener(this, this.gravity, 3, ContextService.getServiceHandler());
    }
    if (this.gyroscope != null) {
      this.sensorManager.registerListener(this, this.gyroscope, 3, ContextService.getServiceHandler());
    }
    if (this.accelerometer != null) {
      this.sensorManager.registerListener(this, this.accelerometer, 2, ContextService.getServiceHandler());
    }
    if (this.magnetic != null) {
      this.sensorManager.registerListener(this, this.magnetic, 2, ContextService.getServiceHandler());
    }
    if (this.rotation != null) {
      this.sensorManager.registerListener(this, this.rotation, 2, ContextService.getServiceHandler());
    }
    if (this.linearAcceleration != null) {
      this.sensorManager.registerListener(this, this.linearAcceleration, 3, ContextService.getServiceHandler());
    }
    this.status = ServiceStatus.INITIALIZED;
  }
  
  private void stopSensorManager()
  {
    this.sensorManager.unregisterListener(this);
    this.status = ServiceStatus.STOPPED;
  }
  
  private boolean updateOrientation(long paramLong, float[] paramArrayOfFloat)
  {
    int i;
    int j;
    float[] arrayOfFloat;
    switch (this.display.getRotation())
    {
    default: 
      i = 1;
      j = 2;
      arrayOfFloat = this.tmpOrientationAngles;
      if (SensorManager.remapCoordinateSystem(paramArrayOfFloat, i, j, this.tmpMatrix2)) {
        break;
      }
    }
    do
    {
      return false;
      i = 2;
      j = 129;
      break;
      i = 130;
      j = 1;
      break;
      i = 129;
      j = 130;
      break;
      if (this.tmpMatrix2[7] <= SINE_OF_45_DEGREES) {
        break label231;
      }
    } while (!SensorManager.remapCoordinateSystem(this.tmpMatrix2, 1, 3, this.tmpMatrix3));
    SensorManager.getOrientation(this.tmpMatrix3, arrayOfFloat);
    for (float f1 = (float)Math.toDegrees(arrayOfFloat[1]) - 90.0F;; f1 = (float)Math.toDegrees(arrayOfFloat[1]))
    {
      float f2 = MathUtil.wrapAngle(arrayOfFloat[0] + 0.017453292F * getDeclination());
      f2 = this.orientationFilter.filter(paramLong, 57.29578F * f2);
      if ((Math.abs(f2 - this.lastAzimuthUpdate) < 1.0F) && (Math.abs(f1 - this.lastPitchUpdate) < 1.0F)) {
        break;
      }
      this.lastAzimuthUpdate = f2;
      this.lastPitchUpdate = f1;
      this.lastUpdateTimeMs = paramLong;
      return true;
      label231:
      SensorManager.getOrientation(this.tmpMatrix2, arrayOfFloat);
    }
  }
  
  private boolean updateOrientationFromRaw(long paramLong)
  {
    if (this.lastUpdateTimeMs + 50L > paramLong) {}
    float[] arrayOfFloat;
    do
    {
      do
      {
        return false;
      } while (Math.abs(this.accelerometerReadingMs - this.magnetometerReadingMs) > 5000L);
      arrayOfFloat = this.tmpMatrix1;
    } while (!SensorManager.getRotationMatrix(arrayOfFloat, null, this.accelerometerData, this.magneticData));
    return updateOrientation(paramLong, arrayOfFloat);
  }
  
  private boolean updateOrientationFromRotation(long paramLong)
  {
    if (this.lastUpdateTimeMs + 50L > paramLong) {
      return false;
    }
    float[] arrayOfFloat = this.tmpMatrix1;
    calcMatrixFromRotationVector(this.rotationData, arrayOfFloat);
    return updateOrientation(paramLong, arrayOfFloat);
  }
  
  public void onAccuracyChanged(Sensor paramSensor, int paramInt) {}
  
  public void onPause()
  {
    stopSensorManager();
  }
  
  public void onResume()
  {
    startSensorManager();
  }
  
  public void onSensorChanged(SensorEvent paramSensorEvent)
  {
    this.status = ServiceStatus.RUNNING;
    long l = System.currentTimeMillis();
    switch (paramSensorEvent.sensor.getType())
    {
    }
    for (;;)
    {
      safeSensorUpdate(paramSensorEvent.sensor.getType(), l, paramSensorEvent.values);
      return;
      this.accelerometerReadingMs = l;
      System.arraycopy(paramSensorEvent.values, 0, this.accelerometerData, 0, this.accelerometerData.length);
      if (updateOrientationFromRaw(l))
      {
        safeCompassUpdate(this.lastUpdateTimeMs, this.lastAzimuthUpdate);
        continue;
        this.magnetometerReadingMs = l;
        System.arraycopy(paramSensorEvent.values, 0, this.magneticData, 0, this.magneticData.length);
        if (updateOrientationFromRaw(l))
        {
          safeCompassUpdate(this.lastUpdateTimeMs, this.lastAzimuthUpdate);
          continue;
          System.arraycopy(paramSensorEvent.values, 0, this.rotationData, 0, Math.min(paramSensorEvent.values.length, this.rotationData.length));
          if (paramSensorEvent.values.length == 3) {
            this.rotationData[3] = computeRotationVectorW(this.rotationData);
          }
          if (updateOrientationFromRotation(l)) {
            safeCompassUpdate(this.lastUpdateTimeMs, this.lastAzimuthUpdate);
          }
        }
      }
    }
  }
  
  public void onStart() {}
  
  public void onStop() {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/sensors/NianticSensorManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */