package dagger.internal;

import dagger.MembersInjector;

public final class MembersInjectors
{
  public static <T> MembersInjector<T> delegatingTo(MembersInjector<? super T> paramMembersInjector)
  {
    return (MembersInjector)Preconditions.checkNotNull(paramMembersInjector);
  }
  
  public static <T> T injectMembers(MembersInjector<T> paramMembersInjector, T paramT)
  {
    paramMembersInjector.injectMembers(paramT);
    return paramT;
  }
  
  public static <T> MembersInjector<T> noOp()
  {
    return NoOpMembersInjector.INSTANCE;
  }
  
  private static enum NoOpMembersInjector
    implements MembersInjector<Object>
  {
    INSTANCE;
    
    private NoOpMembersInjector() {}
    
    public void injectMembers(Object paramObject)
    {
      Preconditions.checkNotNull(paramObject);
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/dagger/internal/MembersInjectors.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */