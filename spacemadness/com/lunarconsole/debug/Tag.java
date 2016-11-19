package spacemadness.com.lunarconsole.debug;

public class Tag
{
  public boolean enabled;
  public final String name;
  
  public Tag(String paramString)
  {
    this(paramString, false);
  }
  
  public Tag(String paramString, boolean paramBoolean)
  {
    this.name = paramString;
    this.enabled = paramBoolean;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/spacemadness/com/lunarconsole/debug/Tag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */