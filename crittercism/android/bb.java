package crittercism.android;

import com.crittercism.app.CrittercismConfig;
import java.util.List;

public final class bb
  extends CrittercismConfig
{
  private String b = "524c99a04002057fcd000001";
  private bn c;
  
  public bb(bn parambn, CrittercismConfig paramCrittercismConfig)
  {
    super(paramCrittercismConfig);
    this.c = parambn;
  }
  
  public final List a()
  {
    List localList = super.a();
    localList.add(this.c.b());
    return localList;
  }
  
  public final String b()
  {
    return this.c.a();
  }
  
  public final String c()
  {
    return this.c.b();
  }
  
  public final String d()
  {
    return this.c.d();
  }
  
  public final String e()
  {
    return this.c.c();
  }
  
  public final boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof bb)) {
      return false;
    }
    bb localbb = (bb)paramObject;
    return (super.equals(paramObject)) && (a(this.c.a(), localbb.c.a())) && (a(this.c.b(), localbb.c.b())) && (a(this.c.d(), localbb.c.d())) && (a(this.c.c(), localbb.c.c())) && (a(this.b, localbb.b));
  }
  
  public final String f()
  {
    return this.b;
  }
  
  public final String g()
  {
    return this.a;
  }
  
  public final int hashCode()
  {
    return ((((super.hashCode() * 31 + this.c.a().hashCode()) * 31 + this.c.b().hashCode()) * 31 + this.c.d().hashCode()) * 31 + this.c.c().hashCode()) * 31 + this.b.hashCode();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/bb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */