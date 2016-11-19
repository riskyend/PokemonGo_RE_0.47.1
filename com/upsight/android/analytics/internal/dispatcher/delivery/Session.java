package com.upsight.android.analytics.internal.dispatcher.delivery;

import android.text.TextUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.upsight.android.analytics.internal.DataStoreRecord;
import java.io.IOException;

class Session
{
  private static final String EVENTS = "events";
  private static final String INSTALL_TS = "install_ts";
  private static final String MSG_CAMPAIGN_ID = "msg_campaign_id";
  private static final String MSG_ID = "msg_id";
  private static final String PAST_SESSION_TIME = "past_session_time";
  private static final String SESSION_NUM = "session_num";
  private static final String SESSION_START = "session_start";
  @Expose
  @SerializedName("events")
  private JsonArray mEvents = new JsonArray();
  @Expose
  @SerializedName("install_ts")
  private Long mInstallTs;
  @Expose
  @SerializedName("msg_campaign_id")
  private Integer mMsgCampaignId;
  @Expose
  @SerializedName("msg_id")
  private Integer mMsgId;
  @Expose
  @SerializedName("past_session_time")
  private Long mPastSessionTime;
  @Expose
  @SerializedName("session_num")
  private Integer mSessionNum;
  @Expose
  @SerializedName("session_start")
  private Long mSessionStart;
  
  public Session(DataStoreRecord paramDataStoreRecord, long paramLong)
  {
    this.mSessionStart = paramDataStoreRecord.getSessionID();
    this.mInstallTs = Long.valueOf(paramLong);
    this.mMsgId = paramDataStoreRecord.getMessageID();
    this.mMsgCampaignId = paramDataStoreRecord.getCampaignID();
    this.mPastSessionTime = paramDataStoreRecord.getPastSessionTime();
    this.mSessionNum = paramDataStoreRecord.getSessionNumber();
  }
  
  void addEvent(DataStoreRecord paramDataStoreRecord, JsonParser paramJsonParser)
  {
    paramDataStoreRecord = paramDataStoreRecord.getSource();
    if (!TextUtils.isEmpty(paramDataStoreRecord))
    {
      paramDataStoreRecord = paramJsonParser.parse(paramDataStoreRecord);
      if ((paramDataStoreRecord != null) && (paramDataStoreRecord.isJsonObject())) {
        this.mEvents.add(paramDataStoreRecord.getAsJsonObject());
      }
    }
  }
  
  static final class DefaultTypeAdapter
    extends TypeAdapter<Session>
  {
    public Session read(JsonReader paramJsonReader)
      throws IOException
    {
      throw new IllegalStateException(getClass().getSimpleName() + " does not implement read().");
    }
    
    public void write(JsonWriter paramJsonWriter, Session paramSession)
      throws IOException
    {
      paramJsonWriter.beginObject();
      paramJsonWriter.name("session_num").value(paramSession.mSessionNum);
      paramJsonWriter.name("session_start").value(paramSession.mSessionStart);
      paramJsonWriter.name("past_session_time").value(paramSession.mPastSessionTime);
      paramJsonWriter.name("msg_id").value(paramSession.mMsgId);
      paramJsonWriter.name("msg_campaign_id").value(paramSession.mMsgCampaignId);
      paramJsonWriter.name("install_ts").value(paramSession.mInstallTs);
      paramJsonWriter.name("events");
      paramJsonWriter.setSerializeNulls(true);
      Streams.write(paramSession.mEvents, paramJsonWriter);
      paramJsonWriter.setSerializeNulls(false);
      paramJsonWriter.endObject();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/delivery/Session.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */