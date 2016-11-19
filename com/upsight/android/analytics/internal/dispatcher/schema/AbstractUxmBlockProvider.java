package com.upsight.android.analytics.internal.dispatcher.schema;

import com.upsight.android.analytics.provider.UpsightDataProvider;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractUxmBlockProvider
  extends UpsightDataProvider
{
  public static final String BUNDLE_HASH = "bundle.hash";
  public static final String BUNDLE_ID = "bundle.id";
  public static final String BUNDLE_SCHEMA_HASH = "bundle.schema_hash";
  
  public Set<String> availableKeys()
  {
    return new HashSet(Arrays.asList(new String[] { "bundle.schema_hash", "bundle.id", "bundle.hash" }));
  }
  
  public abstract String getBundleHash();
  
  public abstract String getBundleId();
  
  public abstract String getBundleSchemaHash();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/schema/AbstractUxmBlockProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */