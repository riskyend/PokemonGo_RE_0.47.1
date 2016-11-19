package com.unity3d.player;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Handler;
import android.view.View;
import android.view.View.OnSystemUiVisibilityChangeListener;

public final class b
  implements c
{
  private static final SurfaceTexture a = new SurfaceTexture(-1);
  private static final int b;
  private volatile boolean c;
  
  static
  {
    if (i.c) {}
    for (int i = 5894;; i = 1)
    {
      b = i;
      return;
    }
  }
  
  private void a(final View paramView, int paramInt)
  {
    Handler localHandler = paramView.getHandler();
    if (localHandler == null)
    {
      a(paramView, this.c);
      return;
    }
    localHandler.postDelayed(new Runnable()
    {
      public final void run()
      {
        b.this.a(paramView, b.a(b.this));
      }
    }, 1000L);
  }
  
  public final void a(final View paramView)
  {
    if (i.d) {
      return;
    }
    paramView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
    {
      public final void onSystemUiVisibilityChange(int paramAnonymousInt)
      {
        b.a(b.this, paramView);
      }
    });
  }
  
  public final void a(View paramView, boolean paramBoolean)
  {
    this.c = paramBoolean;
    if (this.c) {}
    for (int i = paramView.getSystemUiVisibility() | b;; i = paramView.getSystemUiVisibility() & (b ^ 0xFFFFFFFF))
    {
      paramView.setSystemUiVisibility(i);
      return;
    }
  }
  
  public final boolean a(Camera paramCamera)
  {
    try
    {
      paramCamera.setPreviewTexture(a);
      return true;
    }
    catch (Exception paramCamera) {}
    return false;
  }
  
  public final void b(View paramView)
  {
    if ((!i.c) && (this.c))
    {
      a(paramView, false);
      this.c = true;
    }
    a(paramView, 1000);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/unity3d/player/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */