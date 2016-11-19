package android.support.v4.text;

import android.os.Build.VERSION;
import java.util.Locale;

public class ICUCompat
{
  private static final ICUCompatImpl IMPL = new ICUCompatImplBase();
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 21)
    {
      IMPL = new ICUCompatImplLollipop();
      return;
    }
    if (i >= 14)
    {
      IMPL = new ICUCompatImplIcs();
      return;
    }
  }
  
  public static String maximizeAndGetScript(Locale paramLocale)
  {
    return IMPL.maximizeAndGetScript(paramLocale);
  }
  
  static abstract interface ICUCompatImpl
  {
    public abstract String maximizeAndGetScript(Locale paramLocale);
  }
  
  static class ICUCompatImplBase
    implements ICUCompat.ICUCompatImpl
  {
    public String maximizeAndGetScript(Locale paramLocale)
    {
      return null;
    }
  }
  
  static class ICUCompatImplIcs
    implements ICUCompat.ICUCompatImpl
  {
    public String maximizeAndGetScript(Locale paramLocale)
    {
      return ICUCompatIcs.maximizeAndGetScript(paramLocale);
    }
  }
  
  static class ICUCompatImplLollipop
    implements ICUCompat.ICUCompatImpl
  {
    public String maximizeAndGetScript(Locale paramLocale)
    {
      return ICUCompatApi23.maximizeAndGetScript(paramLocale);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/android/support/v4/text/ICUCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */