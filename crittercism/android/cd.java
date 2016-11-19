package crittercism.android;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public final class cd
  implements ch
{
  private JSONObject a;
  private JSONObject b;
  private JSONArray c;
  private JSONArray d;
  private File e;
  private String f;
  
  public cd(File paramFile, bs parambs1, bs parambs2, bs parambs3, bs parambs4)
  {
    paramFile.exists();
    this.f = cg.a.a();
    this.e = paramFile;
    this.a = new bu().a(new bx.c()).a(new bx.b()).a(new bx.d()).a(new bx.f()).a(new bx.o()).a(new bx.p()).a(new bx.j()).a(new bx.h()).a(new bx.z()).a(new bx.aa()).a(new bx.k()).a(new bx.r()).a(new bx.m()).a(new bx.s()).a(new bx.w()).a(new bx.x()).a();
    paramFile = new HashMap();
    paramFile.put("crashed_session", new bo(parambs1).a);
    if (parambs2.b() > 0) {
      paramFile.put("previous_session", new bo(parambs2).a);
    }
    this.b = new JSONObject(paramFile);
    this.c = new bo(parambs3).a;
    this.d = new bo(parambs4).a;
  }
  
  public final void a(OutputStream paramOutputStream)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("app_state", this.a);
    localHashMap.put("breadcrumbs", this.b);
    localHashMap.put("endpoints", this.c);
    localHashMap.put("systemBreadcrumbs", this.d);
    Object localObject1 = new byte[0];
    byte[] arrayOfByte = new byte[' '];
    FileInputStream localFileInputStream = new FileInputStream(this.e);
    for (;;)
    {
      int i = localFileInputStream.read(arrayOfByte);
      if (i == -1) {
        break;
      }
      localObject2 = new byte[localObject1.length + i];
      System.arraycopy(localObject1, 0, localObject2, 0, localObject1.length);
      System.arraycopy(arrayOfByte, 0, localObject2, localObject1.length, i);
      localObject1 = localObject2;
    }
    localFileInputStream.close();
    Object localObject2 = new HashMap();
    ((Map)localObject2).put("dmp_name", this.e.getName());
    ((Map)localObject2).put("dmp_file", cr.a((byte[])localObject1));
    localHashMap.put("ndk_dmp_info", new JSONObject((Map)localObject2));
    paramOutputStream.write(new JSONObject(localHashMap).toString().getBytes());
  }
  
  public final String e()
  {
    return this.f;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/cd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */