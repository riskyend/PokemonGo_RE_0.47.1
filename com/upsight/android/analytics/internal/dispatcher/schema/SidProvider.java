package com.upsight.android.analytics.internal.dispatcher.schema;

import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.provider.UpsightDataProvider;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SidProvider
  extends UpsightDataProvider
{
  public static final String SID_KEY = "sid";
  
  SidProvider(UpsightContext paramUpsightContext)
  {
    put("sid", paramUpsightContext.getSid());
  }
  
  public Set<String> availableKeys()
  {
    return new HashSet(Arrays.asList(new String[] { "sid" }));
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/schema/SidProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */