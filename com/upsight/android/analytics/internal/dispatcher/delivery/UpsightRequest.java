package com.upsight.android.analytics.internal.dispatcher.delivery;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.upsight.android.UpsightContext;
import com.upsight.android.analytics.internal.DataStoreRecord;
import com.upsight.android.analytics.internal.dispatcher.routing.Packet;
import com.upsight.android.analytics.internal.dispatcher.schema.Schema;
import com.upsight.android.analytics.internal.session.Clock;
import com.upsight.android.analytics.provider.UpsightOptOutStatus;
import com.upsight.android.internal.util.PreferencesHelper;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@JsonAdapter(DefaultTypeAdapter.class)
class UpsightRequest
{
  private long mInstallTs;
  private final JsonParser mJsonParser;
  private boolean mOptOut;
  private long mRequestTs;
  private Schema mSchema;
  private Session[] mSessions;
  private UpsightContext mUpsight;
  
  public UpsightRequest(UpsightContext paramUpsightContext, BatchSender.Request paramRequest, JsonParser paramJsonParser, Clock paramClock)
  {
    this.mUpsight = paramUpsightContext;
    this.mJsonParser = paramJsonParser;
    this.mInstallTs = PreferencesHelper.getLong(paramUpsightContext, "install_ts", 0L);
    this.mSessions = getSessions(paramRequest.batch);
    this.mOptOut = UpsightOptOutStatus.get(this.mUpsight);
    this.mRequestTs = paramClock.currentTimeSeconds();
    this.mSchema = paramRequest.schema;
  }
  
  private Session[] getSessions(Batch paramBatch)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramBatch.getPackets().iterator();
    while (localIterator.hasNext())
    {
      DataStoreRecord localDataStoreRecord = ((Packet)localIterator.next()).getRecord();
      Session localSession = (Session)localHashMap.get(localDataStoreRecord.getSessionID());
      paramBatch = localSession;
      if (localSession == null)
      {
        paramBatch = new Session(localDataStoreRecord, this.mInstallTs);
        localHashMap.put(localDataStoreRecord.getSessionID(), paramBatch);
      }
      paramBatch.addEvent(localDataStoreRecord, this.mJsonParser);
    }
    return (Session[])localHashMap.values().toArray(new Session[localHashMap.values().size()]);
  }
  
  public static final class DefaultTypeAdapter
    extends TypeAdapter<UpsightRequest>
  {
    private static final Gson GSON = new Gson();
    private static final String IDENTIFIERS_KEY = "identifiers";
    private static final String LOCALE_KEY = "locale";
    private static final String OPT_OUT_KEY = "opt_out";
    private static final String REQUEST_TS_KEY = "request_ts";
    private static final String SESSIONS_KEY = "sessions";
    private static final TypeAdapter<Session> SESSION_TYPE_ADAPTER = new Session.DefaultTypeAdapter();
    
    public UpsightRequest read(JsonReader paramJsonReader)
      throws IOException
    {
      throw new IOException(UpsightRequest.class.getSimpleName() + " cannot be deserialized");
    }
    
    public void write(JsonWriter paramJsonWriter, UpsightRequest paramUpsightRequest)
      throws IOException
    {
      paramJsonWriter.beginObject();
      Object localObject1 = paramUpsightRequest.mSchema.availableKeys().iterator();
      while (((Iterator)localObject1).hasNext())
      {
        String str = (String)((Iterator)localObject1).next();
        Object localObject2 = paramUpsightRequest.mSchema.getValueFor(str);
        if (localObject2 != null)
        {
          paramJsonWriter.name(str);
          Streams.write(GSON.toJsonTree(localObject2), paramJsonWriter);
        }
      }
      paramJsonWriter.name("request_ts");
      paramJsonWriter.value(paramUpsightRequest.mRequestTs);
      paramJsonWriter.name("opt_out");
      paramJsonWriter.value(paramUpsightRequest.mOptOut);
      localObject1 = paramUpsightRequest.mSchema;
      if (localObject1 != null)
      {
        localObject1 = ((Schema)localObject1).getName();
        if (!TextUtils.isEmpty((CharSequence)localObject1))
        {
          paramJsonWriter.name("identifiers");
          paramJsonWriter.value((String)localObject1);
        }
      }
      localObject1 = Locale.getDefault();
      if (localObject1 != null)
      {
        localObject1 = ((Locale)localObject1).toString();
        if (!TextUtils.isEmpty((CharSequence)localObject1))
        {
          paramJsonWriter.name("locale");
          paramJsonWriter.value((String)localObject1);
        }
      }
      paramJsonWriter.name("sessions");
      paramJsonWriter.beginArray();
      paramUpsightRequest = paramUpsightRequest.mSessions;
      int j = paramUpsightRequest.length;
      int i = 0;
      while (i < j)
      {
        localObject1 = paramUpsightRequest[i];
        SESSION_TYPE_ADAPTER.write(paramJsonWriter, localObject1);
        i += 1;
      }
      paramJsonWriter.endArray();
      paramJsonWriter.endObject();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/delivery/UpsightRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */