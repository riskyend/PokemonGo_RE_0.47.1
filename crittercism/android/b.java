package crittercism.android;

import android.util.SparseArray;

public enum b
{
  private static SparseArray e;
  private int f;
  
  static
  {
    SparseArray localSparseArray = new SparseArray();
    e = localSparseArray;
    localSparseArray.put(0, a);
    e.put(1, b);
  }
  
  private b(int paramInt1)
  {
    this.f = paramInt1;
  }
  
  public static b a(int paramInt)
  {
    b localb2 = (b)e.get(paramInt);
    b localb1 = localb2;
    if (localb2 == null) {
      localb1 = c;
    }
    return localb1;
  }
  
  public final int a()
  {
    return this.f;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */