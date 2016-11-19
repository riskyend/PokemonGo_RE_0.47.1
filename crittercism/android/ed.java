package crittercism.android;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class ed
{
  public static final ed a = new ed();
  private ee b = new a((byte)0);
  private ThreadLocal c = new ThreadLocal();
  
  private SimpleDateFormat b()
  {
    SimpleDateFormat localSimpleDateFormat2 = (SimpleDateFormat)this.c.get();
    SimpleDateFormat localSimpleDateFormat1 = localSimpleDateFormat2;
    if (localSimpleDateFormat2 == null)
    {
      localSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US);
      localSimpleDateFormat1.setTimeZone(TimeZone.getTimeZone("GMT"));
      localSimpleDateFormat1.setLenient(false);
      this.c.set(localSimpleDateFormat1);
    }
    return localSimpleDateFormat1;
  }
  
  public final long a(String paramString)
  {
    return b().parse(paramString).getTime();
  }
  
  public final String a()
  {
    return a(this.b.a());
  }
  
  public final String a(Date paramDate)
  {
    return b().format(paramDate);
  }
  
  final class a
    implements ee
  {
    private a() {}
    
    public final Date a()
    {
      return new Date();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/ed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */