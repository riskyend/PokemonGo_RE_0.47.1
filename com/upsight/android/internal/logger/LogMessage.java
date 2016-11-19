package com.upsight.android.internal.logger;

import com.google.gson.annotations.Expose;
import com.upsight.android.logger.UpsightLogger.Level;
import com.upsight.android.persistence.annotation.UpsightStorableIdentifier;
import com.upsight.android.persistence.annotation.UpsightStorableType;

@UpsightStorableType("com.upsight.message.log")
public final class LogMessage
{
  @UpsightStorableIdentifier
  public String id;
  @Expose
  private UpsightLogger.Level level;
  @Expose
  private String message;
  @Expose
  private String tag;
  @Expose
  private String throwableString;
  
  LogMessage() {}
  
  LogMessage(LogMessage paramLogMessage)
  {
    this(paramLogMessage.tag, paramLogMessage.level, paramLogMessage.message, paramLogMessage.throwableString);
  }
  
  LogMessage(String paramString1, UpsightLogger.Level paramLevel, String paramString2, String paramString3)
  {
    this.tag = paramString1;
    this.level = paramLevel;
    this.message = paramString2;
    this.throwableString = paramString3;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    do
    {
      return true;
      if ((paramObject == null) || (getClass() != paramObject.getClass())) {
        return false;
      }
      paramObject = (LogMessage)paramObject;
      if (this.id == null) {
        break;
      }
    } while (this.id.equals(((LogMessage)paramObject).id));
    for (;;)
    {
      return false;
      if (((LogMessage)paramObject).id == null) {
        break;
      }
    }
  }
  
  public UpsightLogger.Level getLevel()
  {
    return this.level;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public String getTag()
  {
    return this.tag;
  }
  
  public String getThrowableString()
  {
    return this.throwableString;
  }
  
  public int hashCode()
  {
    if (this.id != null) {
      return this.id.hashCode();
    }
    return 0;
  }
  
  public void setLevel(UpsightLogger.Level paramLevel)
  {
    this.level = paramLevel;
  }
  
  public void setMessage(String paramString)
  {
    this.message = paramString;
  }
  
  public void setTag(String paramString)
  {
    this.tag = paramString;
  }
  
  public void setThrowableString(String paramString)
  {
    this.throwableString = paramString;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/internal/logger/LogMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */