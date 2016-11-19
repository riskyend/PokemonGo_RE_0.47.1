package spacemadness.com.lunarconsole.ui;

import android.view.MotionEvent;
import spacemadness.com.lunarconsole.debug.Log;

public abstract class GestureRecognizer<T extends GestureRecognizer>
{
  private OnGestureListener<T> listener;
  
  public OnGestureListener<T> getListener()
  {
    return this.listener;
  }
  
  protected void notifyGestureRecognizer()
  {
    if (this.listener != null) {}
    try
    {
      this.listener.onGesture(this);
      return;
    }
    catch (Exception localException)
    {
      Log.e(localException, "Error while notifying gesture listener", new Object[0]);
    }
  }
  
  public abstract boolean onTouchEvent(MotionEvent paramMotionEvent);
  
  public void setListener(OnGestureListener<T> paramOnGestureListener)
  {
    this.listener = paramOnGestureListener;
  }
  
  public static abstract interface OnGestureListener<T extends GestureRecognizer>
  {
    public abstract void onGesture(T paramT);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/ui/GestureRecognizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */