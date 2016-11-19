package crittercism.android;

import java.lang.reflect.Constructor;

public final class l
{
  public static Constructor a(String paramString, String[] paramArrayOfString)
  {
    paramString = Class.forName(paramString).getDeclaredConstructors();
    int j = 0;
    while (j < paramString.length)
    {
      Class[] arrayOfClass = paramString[j].getParameterTypes();
      int i;
      if (arrayOfClass.length != paramArrayOfString.length) {
        i = 0;
      }
      while (i != 0)
      {
        return paramString[j];
        i = 0;
        for (;;)
        {
          if (i >= arrayOfClass.length) {
            break label79;
          }
          if (!arrayOfClass[i].getName().equals(paramArrayOfString[i]))
          {
            i = 0;
            break;
          }
          i += 1;
        }
        label79:
        i = 1;
      }
      j += 1;
    }
    return null;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */