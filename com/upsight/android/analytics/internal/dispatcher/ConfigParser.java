package com.upsight.android.analytics.internal.dispatcher;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.internal.dispatcher.delivery.BatchSender.Config;
import com.upsight.android.analytics.internal.dispatcher.delivery.Batcher.Config;
import com.upsight.android.analytics.internal.dispatcher.delivery.QueueConfig;
import com.upsight.android.analytics.internal.dispatcher.delivery.QueueConfig.Builder;
import com.upsight.android.analytics.internal.dispatcher.routing.RoutingConfig;
import com.upsight.android.analytics.internal.dispatcher.routing.RoutingConfig.Builder;
import com.upsight.android.analytics.internal.dispatcher.schema.IdentifierConfig;
import com.upsight.android.analytics.internal.dispatcher.schema.IdentifierConfig.Builder;
import com.upsight.android.logger.UpsightLogger;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

@Singleton
class ConfigParser
{
  private static final String LOG_TAG = "Dispatcher";
  private Gson mGson;
  private UpsightLogger mLogger;
  
  @Inject
  public ConfigParser(UpsightContext paramUpsightContext, @Named("config-gson") Gson paramGson)
  {
    this.mGson = paramGson;
    this.mLogger = paramUpsightContext.getLogger();
  }
  
  private IdentifierConfig parseIdentifierConfig(Config paramConfig)
  {
    IdentifierConfig.Builder localBuilder = new IdentifierConfig.Builder();
    if (paramConfig.identifiers != null)
    {
      Object localObject = paramConfig.identifiers.iterator();
      while (((Iterator)localObject).hasNext())
      {
        Identifier localIdentifier = (Identifier)((Iterator)localObject).next();
        localBuilder.addIdentifiers(localIdentifier.name, new HashSet(localIdentifier.ids));
      }
      paramConfig = paramConfig.identifierFilters.iterator();
      while (paramConfig.hasNext())
      {
        localObject = (IdentifierFilter)paramConfig.next();
        localBuilder.addIdentifierFilter(((IdentifierFilter)localObject).filter, ((IdentifierFilter)localObject).identifiers);
      }
    }
    return localBuilder.build();
  }
  
  private QueueConfig parseQueueConfig(Queue paramQueue)
  {
    return new QueueConfig.Builder().setEndpointAddress(paramQueue.formatEndpointAddress()).setBatchSenderConfig(new BatchSender.Config(paramQueue.countNetworkFail, paramQueue.retryInterval, paramQueue.retryCount)).setBatcherConfig(new Batcher.Config(paramQueue.maxSize, paramQueue.maxAge)).build();
  }
  
  private RoutingConfig parseRoutingConfig(Config paramConfig)
  {
    RoutingConfig.Builder localBuilder = new RoutingConfig.Builder();
    if (paramConfig.queues != null)
    {
      ArrayList localArrayList = new ArrayList();
      Object localObject1 = paramConfig.queues.iterator();
      Object localObject2;
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Queue)((Iterator)localObject1).next();
        localBuilder.addQueueConfig(((Queue)localObject2).name, parseQueueConfig((Queue)localObject2));
        localArrayList.add(((Queue)localObject2).name);
      }
      paramConfig = paramConfig.routeFilters.iterator();
      while (paramConfig.hasNext())
      {
        localObject1 = (RouteFilter)paramConfig.next();
        localObject2 = new ArrayList();
        Iterator localIterator = ((RouteFilter)localObject1).queues.iterator();
        while (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          if ((localArrayList.contains(str)) || ("trash".equals(str))) {
            ((List)localObject2).add(str);
          } else {
            this.mLogger.w("Dispatcher", "Dispatcher ignored a route to a non-existent queue: " + str + " in the dispatcher configuration", new Object[0]);
          }
        }
        if (((List)localObject2).size() > 0) {
          localBuilder.addRoute(((RouteFilter)localObject1).filter, (List)localObject2);
        }
      }
    }
    return localBuilder.build();
  }
  
  public Config parseConfig(String paramString)
    throws IOException
  {
    try
    {
      Object localObject = (Config)this.mGson.fromJson(paramString, Config.class);
      paramString = parseIdentifierConfig((Config)localObject);
      localObject = parseRoutingConfig((Config)localObject);
      return new Config.Builder().setRoutingConfig((RoutingConfig)localObject).setIdentifierConfig(paramString).build();
    }
    catch (JsonSyntaxException paramString)
    {
      throw new IOException(paramString);
    }
  }
  
  public static class Config
  {
    @Expose
    @SerializedName("identifier_filters")
    public List<ConfigParser.IdentifierFilter> identifierFilters;
    @Expose
    @SerializedName("identifiers")
    public List<ConfigParser.Identifier> identifiers;
    @Expose
    @SerializedName("queues")
    public List<ConfigParser.Queue> queues;
    @Expose
    @SerializedName("route_filters")
    public List<ConfigParser.RouteFilter> routeFilters;
  }
  
  public static class Identifier
  {
    @Expose
    @SerializedName("ids")
    public List<String> ids;
    @Expose
    @SerializedName("name")
    public String name;
  }
  
  public static class IdentifierFilter
  {
    @Expose
    @SerializedName("filter")
    public String filter;
    @Expose
    @SerializedName("identifiers")
    public String identifiers;
  }
  
  public static class Queue
  {
    @Expose
    @SerializedName("count_network_fail_retries")
    public boolean countNetworkFail;
    @Expose
    @SerializedName("host")
    public String host;
    @Expose
    @SerializedName("max_age")
    public int maxAge;
    @Expose
    @SerializedName("max_size")
    public int maxSize;
    @Expose
    @SerializedName("name")
    public String name;
    @Expose
    @SerializedName("protocol")
    public String protocol;
    @Expose
    @SerializedName("max_retry_count")
    public int retryCount;
    @Expose
    @SerializedName("retry_interval")
    public int retryInterval;
    @Expose
    @SerializedName("url_fmt")
    public String urlFormat;
    
    public String formatEndpointAddress()
    {
      return this.urlFormat.replace("{protocol}", this.protocol).replace("{host}", this.host);
    }
  }
  
  public static class RouteFilter
  {
    @Expose
    @SerializedName("filter")
    public String filter;
    @Expose
    @SerializedName("queues")
    public List<String> queues;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/ConfigParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */