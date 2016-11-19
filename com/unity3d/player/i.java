package com.unity3d.player;

import android.os.Build.VERSION;

public final class i
{
  static final boolean a;
  static final boolean b;
  static final boolean c;
  static final boolean d;
  static final boolean e;
  static final c f;
  static final d g;
  static final e h;
  
  static
  {
    Object localObject2 = null;
    boolean bool2 = true;
    boolean bool1;
    if (Build.VERSION.SDK_INT >= 11)
    {
      bool1 = true;
      a = bool1;
      if (Build.VERSION.SDK_INT < 17) {
        break label136;
      }
      bool1 = true;
      label28:
      b = bool1;
      if (Build.VERSION.SDK_INT < 19) {
        break label141;
      }
      bool1 = true;
      label42:
      c = bool1;
      if (Build.VERSION.SDK_INT < 21) {
        break label146;
      }
      bool1 = true;
      label56:
      d = bool1;
      if (Build.VERSION.SDK_INT < 23) {
        break label151;
      }
      bool1 = bool2;
      label70:
      e = bool1;
      if (!a) {
        break label156;
      }
      localObject1 = new b();
      label88:
      f = (c)localObject1;
      if (!b) {
        break label161;
      }
    }
    label136:
    label141:
    label146:
    label151:
    label156:
    label161:
    for (Object localObject1 = new f();; localObject1 = null)
    {
      g = (d)localObject1;
      localObject1 = localObject2;
      if (e) {
        localObject1 = new h();
      }
      h = (e)localObject1;
      return;
      bool1 = false;
      break;
      bool1 = false;
      break label28;
      bool1 = false;
      break label42;
      bool1 = false;
      break label56;
      bool1 = false;
      break label70;
      localObject1 = null;
      break label88;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/unity3d/player/i.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */