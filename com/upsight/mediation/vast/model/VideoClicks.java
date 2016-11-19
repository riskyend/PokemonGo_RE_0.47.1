package com.upsight.mediation.vast.model;

import java.util.ArrayList;
import java.util.List;

public class VideoClicks
{
  private String clickThrough;
  private List<String> clickTracking;
  private List<String> customClick;
  
  private String listToString(List<String> paramList)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramList == null) {
      return "";
    }
    int i = 0;
    while (i < paramList.size())
    {
      localStringBuffer.append(((String)paramList.get(i)).toString());
      i += 1;
    }
    return localStringBuffer.toString();
  }
  
  public String getClickThrough()
  {
    return this.clickThrough;
  }
  
  public List<String> getClickTracking()
  {
    if (this.clickTracking == null) {
      this.clickTracking = new ArrayList();
    }
    return this.clickTracking;
  }
  
  public List<String> getCustomClick()
  {
    if (this.customClick == null) {
      this.customClick = new ArrayList();
    }
    return this.customClick;
  }
  
  public void setClickThrough(String paramString)
  {
    this.clickThrough = paramString;
  }
  
  public String toString()
  {
    return "VideoClicks [clickThrough=" + this.clickThrough + ", clickTracking=[" + listToString(this.clickTracking) + "], customClick=[" + listToString(this.customClick) + "] ]";
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/model/VideoClicks.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */