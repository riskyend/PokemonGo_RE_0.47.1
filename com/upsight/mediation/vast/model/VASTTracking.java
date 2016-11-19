package com.upsight.mediation.vast.model;

import com.upsight.mediation.vast.util.Assets;

public class VASTTracking
{
  private boolean consumed;
  private TRACKING_EVENTS_TYPE event;
  private String offset;
  private boolean offsetRelative;
  private long parsedOffset;
  private String value;
  
  public TRACKING_EVENTS_TYPE getEvent()
  {
    return this.event;
  }
  
  public long getParsedOffset()
  {
    return this.parsedOffset;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public boolean isConsumed()
  {
    return this.consumed;
  }
  
  public boolean isOffsetRelative()
  {
    return this.offsetRelative;
  }
  
  public void setConsumed(boolean paramBoolean)
  {
    this.consumed = paramBoolean;
  }
  
  public void setEvent(TRACKING_EVENTS_TYPE paramTRACKING_EVENTS_TYPE)
  {
    this.event = paramTRACKING_EVENTS_TYPE;
  }
  
  public void setOffset(String paramString)
  {
    this.offset = paramString;
    if (paramString.endsWith("%"))
    {
      this.parsedOffset = Long.parseLong(paramString.substring(0, paramString.indexOf("%")));
      this.offsetRelative = true;
      return;
    }
    this.parsedOffset = Assets.parseOffset(paramString);
    this.offsetRelative = false;
  }
  
  public void setValue(String paramString)
  {
    this.value = paramString;
  }
  
  public String toString()
  {
    return "Tracking [event=" + this.event + ", value=" + this.value + " offset=" + this.offset + "]";
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/model/VASTTracking.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */