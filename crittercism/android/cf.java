package crittercism.android;

import java.io.OutputStream;
import org.json.JSONArray;

public final class cf
  extends bp
{
  public static final cf a = new cf("session_start", a.a);
  private String b;
  private String c;
  private String d = cg.a.a();
  private a e;
  
  public cf(String paramString, a parama)
  {
    this(paramString, ed.a.a(), parama);
  }
  
  private cf(String paramString1, String paramString2, a parama)
  {
    String str = paramString1;
    if (paramString1.length() > 140) {
      str = paramString1.substring(0, 140);
    }
    this.b = str;
    this.c = paramString2;
    this.e = parama;
  }
  
  public final void a(OutputStream paramOutputStream)
  {
    Object localObject = new JSONArray();
    ((JSONArray)localObject).put(this.b);
    ((JSONArray)localObject).put(this.c);
    localObject = ((JSONArray)localObject).toString();
    new StringBuilder("BREADCRUMB WRITING ").append((String)localObject);
    dx.b();
    paramOutputStream.write(((String)localObject).getBytes());
  }
  
  public final String e()
  {
    return this.d;
  }
  
  public static enum a {}
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/cf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */