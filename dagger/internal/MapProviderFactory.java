package dagger.internal;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.Provider;

public final class MapProviderFactory<K, V>
  implements Factory<Map<K, Provider<V>>>
{
  private static final MapProviderFactory<Object, Object> EMPTY = new MapProviderFactory(java.util.Collections.emptyMap());
  private final Map<K, Provider<V>> contributingMap;
  
  private MapProviderFactory(Map<K, Provider<V>> paramMap)
  {
    this.contributingMap = java.util.Collections.unmodifiableMap(paramMap);
  }
  
  public static <K, V> Builder<K, V> builder(int paramInt)
  {
    return new Builder(paramInt, null);
  }
  
  public static <K, V> MapProviderFactory<K, V> empty()
  {
    return EMPTY;
  }
  
  public Map<K, Provider<V>> get()
  {
    return this.contributingMap;
  }
  
  public static final class Builder<K, V>
  {
    private final LinkedHashMap<K, Provider<V>> mapBuilder;
    
    private Builder(int paramInt)
    {
      this.mapBuilder = Collections.newLinkedHashMapWithExpectedSize(paramInt);
    }
    
    public MapProviderFactory<K, V> build()
    {
      return new MapProviderFactory(this.mapBuilder, null);
    }
    
    public Builder<K, V> put(K paramK, Provider<V> paramProvider)
    {
      if (paramK == null) {
        throw new NullPointerException("The key is null");
      }
      if (paramProvider == null) {
        throw new NullPointerException("The provider of the value is null");
      }
      this.mapBuilder.put(paramK, paramProvider);
      return this;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/dagger/internal/MapProviderFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */