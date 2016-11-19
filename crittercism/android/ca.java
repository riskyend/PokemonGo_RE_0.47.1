package crittercism.android;

import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

public final class ca
  extends bq
{
  private ca(File paramFile)
  {
    super(paramFile);
  }
  
  public final Object a()
  {
    try
    {
      JSONObject localJSONObject = new JSONObject((String)super.a());
      return localJSONObject;
    }
    catch (JSONException localJSONException) {}
    return null;
  }
  
  public static final class a
    extends cj
  {
    public final bq a(File paramFile)
    {
      return new ca(paramFile, (byte)0);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/ca.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */