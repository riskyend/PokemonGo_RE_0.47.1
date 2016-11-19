package dagger.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.inject.Provider;

public final class SetFactory<T>
  implements Factory<Set<T>>
{
  private static final String ARGUMENTS_MUST_BE_NON_NULL = "SetFactory.create() requires its arguments to be non-null";
  private static final Factory<Set<Object>> EMPTY_FACTORY;
  private final List<Provider<Set<T>>> contributingProviders;
  
  static
  {
    if (!SetFactory.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      EMPTY_FACTORY = new Factory()
      {
        public Set<Object> get()
        {
          return java.util.Collections.emptySet();
        }
      };
      return;
    }
  }
  
  private SetFactory(List<Provider<Set<T>>> paramList)
  {
    this.contributingProviders = paramList;
  }
  
  public static <T> Factory<Set<T>> create()
  {
    return EMPTY_FACTORY;
  }
  
  public static <T> Factory<Set<T>> create(Factory<Set<T>> paramFactory)
  {
    assert (paramFactory != null) : "SetFactory.create() requires its arguments to be non-null";
    return paramFactory;
  }
  
  public static <T> Factory<Set<T>> create(Provider<Set<T>>... paramVarArgs)
  {
    assert (paramVarArgs != null) : "SetFactory.create() requires its arguments to be non-null";
    paramVarArgs = Arrays.asList(paramVarArgs);
    assert (!paramVarArgs.contains(null)) : "Codegen error?  Null within provider list.";
    assert (!hasDuplicates(paramVarArgs)) : "Codegen error?  Duplicates in the provider list";
    return new SetFactory(paramVarArgs);
  }
  
  private static boolean hasDuplicates(List<? extends Object> paramList)
  {
    HashSet localHashSet = new HashSet(paramList);
    return paramList.size() != localHashSet.size();
  }
  
  public Set<T> get()
  {
    int j = 0;
    Object localObject1 = new ArrayList(this.contributingProviders.size());
    int i = 0;
    int k = this.contributingProviders.size();
    Object localObject3;
    while (i < k)
    {
      localObject2 = (Provider)this.contributingProviders.get(i);
      localObject3 = (Set)((Provider)localObject2).get();
      if (localObject3 == null)
      {
        localObject1 = String.valueOf(localObject2);
        throw new NullPointerException(String.valueOf(localObject1).length() + 14 + (String)localObject1 + " returned null");
      }
      ((List)localObject1).add(localObject3);
      j += ((Set)localObject3).size();
      i += 1;
    }
    Object localObject2 = Collections.newLinkedHashSetWithExpectedSize(j);
    i = 0;
    j = ((List)localObject1).size();
    while (i < j)
    {
      localObject3 = ((Set)((List)localObject1).get(i)).iterator();
      while (((Iterator)localObject3).hasNext())
      {
        Object localObject4 = ((Iterator)localObject3).next();
        if (localObject4 == null) {
          throw new NullPointerException("a null element was provided");
        }
        ((Set)localObject2).add(localObject4);
      }
      i += 1;
    }
    return java.util.Collections.unmodifiableSet((Set)localObject2);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/dagger/internal/SetFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */