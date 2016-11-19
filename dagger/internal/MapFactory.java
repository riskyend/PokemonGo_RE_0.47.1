package dagger.internal;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.inject.Provider;

public final class MapFactory<K, V>
  implements Factory<Map<K, V>>
{
  private final Map<K, Provider<V>> contributingMap;
  
  private MapFactory(Map<K, Provider<V>> paramMap)
  {
    this.contributingMap = java.util.Collections.unmodifiableMap(paramMap);
  }
  
  public static <K, V> MapFactory<K, V> create(Provider<Map<K, Provider<V>>> paramProvider)
  {
    return new MapFactory((Map)paramProvider.get());
  }
  
  public Map<K, V> get()
  {
    LinkedHashMap localLinkedHashMap = Collections.newLinkedHashMapWithExpectedSize(this.contributingMap.size());
    Iterator localIterator = this.contributingMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localLinkedHashMap.put(localEntry.getKey(), ((Provider)localEntry.getValue()).get());
    }
    return java.util.Collections.unmodifiableMap(localLinkedHashMap);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/dagger/internal/MapFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */