package com.upsight.mediation.vast.model;

import java.math.BigInteger;

public class VASTMediaFile
{
  private String apiFramework;
  private BigInteger bitrate;
  private String delivery;
  private BigInteger height;
  private String id;
  private Boolean maintainAspectRatio;
  private Boolean scalable;
  private String type;
  private String value;
  private BigInteger width;
  
  public String getApiFramework()
  {
    return this.apiFramework;
  }
  
  public BigInteger getBitrate()
  {
    return this.bitrate;
  }
  
  public String getDelivery()
  {
    return this.delivery;
  }
  
  public BigInteger getHeight()
  {
    return this.height;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public BigInteger getWidth()
  {
    return this.width;
  }
  
  public Boolean isMaintainAspectRatio()
  {
    return this.maintainAspectRatio;
  }
  
  public Boolean isScalable()
  {
    return this.scalable;
  }
  
  public void setApiFramework(String paramString)
  {
    this.apiFramework = paramString;
  }
  
  public void setBitrate(BigInteger paramBigInteger)
  {
    this.bitrate = paramBigInteger;
  }
  
  public void setDelivery(String paramString)
  {
    this.delivery = paramString;
  }
  
  public void setHeight(BigInteger paramBigInteger)
  {
    this.height = paramBigInteger;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public void setMaintainAspectRatio(Boolean paramBoolean)
  {
    this.maintainAspectRatio = paramBoolean;
  }
  
  public void setScalable(Boolean paramBoolean)
  {
    this.scalable = paramBoolean;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public void setValue(String paramString)
  {
    this.value = paramString;
  }
  
  public void setWidth(BigInteger paramBigInteger)
  {
    this.width = paramBigInteger;
  }
  
  public String toString()
  {
    return "MediaFile [value=" + this.value + ", id=" + this.id + ", delivery=" + this.delivery + ", type=" + this.type + ", bitrate=" + this.bitrate + ", width=" + this.width + ", height=" + this.height + ", scalable=" + this.scalable + ", maintainAspectRatio=" + this.maintainAspectRatio + ", apiFramework=" + this.apiFramework + "]";
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/model/VASTMediaFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */