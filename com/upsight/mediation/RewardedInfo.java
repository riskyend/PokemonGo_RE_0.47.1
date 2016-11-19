package com.upsight.mediation;

import org.json.JSONException;
import org.json.JSONStringer;

public class RewardedInfo
{
  public final String preRollMessage;
  public final int rewardAmount;
  public final String rewardItem;
  public final int rewardItemId;
  public final String rewardMessage;
  
  public RewardedInfo(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2)
  {
    this.preRollMessage = paramString1;
    this.rewardMessage = paramString2;
    this.rewardItem = paramString3;
    this.rewardAmount = paramInt1;
    this.rewardItemId = paramInt2;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = true;
    boolean bool3 = false;
    if (this == paramObject) {
      bool1 = true;
    }
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                return bool1;
                bool1 = bool3;
              } while (paramObject == null);
              bool1 = bool3;
            } while (getClass() != paramObject.getClass());
            paramObject = (RewardedInfo)paramObject;
            bool1 = bool3;
          } while (this.rewardAmount != ((RewardedInfo)paramObject).rewardAmount);
          bool1 = bool3;
        } while (this.rewardItemId != ((RewardedInfo)paramObject).rewardItemId);
        if (this.preRollMessage == null) {
          break;
        }
        bool1 = bool3;
      } while (!this.preRollMessage.equals(((RewardedInfo)paramObject).preRollMessage));
      if (this.rewardMessage == null) {
        break label152;
      }
      bool1 = bool3;
    } while (!this.rewardMessage.equals(((RewardedInfo)paramObject).rewardMessage));
    label116:
    if (this.rewardItem != null)
    {
      bool1 = bool2;
      if (this.rewardItem.equals(((RewardedInfo)paramObject).rewardItem)) {}
    }
    label139:
    for (boolean bool1 = false;; bool1 = bool2)
    {
      return bool1;
      if (((RewardedInfo)paramObject).preRollMessage == null) {
        break;
      }
      return false;
      label152:
      if (((RewardedInfo)paramObject).rewardMessage == null) {
        break label116;
      }
      return false;
      if (((RewardedInfo)paramObject).rewardItem != null) {
        break label139;
      }
    }
  }
  
  public int hashCode()
  {
    int k = 0;
    int i;
    if (this.preRollMessage != null)
    {
      i = this.preRollMessage.hashCode();
      if (this.rewardMessage == null) {
        break label80;
      }
    }
    label80:
    for (int j = this.rewardMessage.hashCode();; j = 0)
    {
      if (this.rewardItem != null) {
        k = this.rewardItem.hashCode();
      }
      return (((i * 31 + j) * 31 + k) * 31 + this.rewardAmount) * 31 + this.rewardItemId;
      i = 0;
      break;
    }
  }
  
  public String toJSONString()
  {
    try
    {
      String str = new JSONStringer().object().key("RewardedInfo").object().key("preRollMessage").value(this.preRollMessage).key("rewardMessage").value(this.rewardMessage).key("rewardItem").value(this.rewardItem).key("rewardAmount").value(this.rewardAmount).key("rewardItemId").value(this.rewardItemId).endObject().endObject().toString();
      return str;
    }
    catch (JSONException localJSONException) {}
    return "{ \"RewardedInfo\" : \"\" }";
  }
  
  public String toString()
  {
    return "RewardedInfo{preRollMessage='" + this.preRollMessage + '\'' + ", rewardMessage='" + this.rewardMessage + '\'' + ", rewardItem='" + this.rewardItem + '\'' + ", rewardAmount=" + this.rewardAmount + ", rewardItemId=" + this.rewardItemId + '}';
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/RewardedInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */