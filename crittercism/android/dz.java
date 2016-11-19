package crittercism.android;

import java.util.concurrent.ThreadFactory;

public final class dz
  implements ThreadFactory
{
  public final Thread newThread(Runnable paramRunnable)
  {
    return new dy(paramRunnable);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/dz.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */