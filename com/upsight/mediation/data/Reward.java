package com.upsight.mediation.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.upsight.mediation.RewardedInfo;
import com.upsight.mediation.log.FuseLog;
import com.upsight.mediation.util.HashMapCaster;
import com.upsight.mediation.util.StringUtil;
import java.util.HashMap;

public class Reward
{
  private static final String TAG = "Reward";
  public final int itemId;
  public final int offerId;
  @NonNull
  public final String postRollMessage;
  @NonNull
  public final String preRollMessage;
  public final int rewardAmount;
  @NonNull
  public final String rewardItem;
  @Nullable
  public String richMediaPostrollScript;
  @Nullable
  public String richMediaPrerollScript;
  public final int zoneId;
  @NonNull
  public final String zoneName;
  
  public Reward(@Nullable String paramString1, @Nullable String paramString2, @Nullable String paramString3, @Nullable String paramString4, @NonNull String paramString5, int paramInt1, int paramInt2, int paramInt3, int paramInt4, @NonNull String paramString6)
  {
    String str = paramString1;
    if (paramString1 == null) {
      str = "";
    }
    this.preRollMessage = str;
    paramString1 = paramString2;
    if (paramString2 == null) {
      paramString1 = "";
    }
    this.postRollMessage = paramString1;
    this.richMediaPrerollScript = paramString3;
    this.richMediaPostrollScript = paramString4;
    this.rewardItem = paramString5;
    this.rewardAmount = paramInt1;
    this.itemId = paramInt2;
    this.offerId = paramInt3;
    this.zoneId = paramInt4;
    this.zoneName = paramString6;
  }
  
  @Nullable
  public static Reward createFromValues(HashMap<String, String> paramHashMap)
  {
    paramHashMap = new HashMapCaster(paramHashMap);
    String str1 = paramHashMap.get("reward");
    String str2 = paramHashMap.get("zone_id");
    if ((str1 == null) || (str2 == null))
    {
      FuseLog.public_w("Reward", "Reward ignored due to missing values: " + paramHashMap.toString());
      return null;
    }
    return new Reward(paramHashMap.get("pre_roll"), paramHashMap.get("post_roll"), paramHashMap.get("pre_roll_script"), paramHashMap.get("post_roll_script"), str1, paramHashMap.getInt("amount", 0), paramHashMap.getInt("item_id"), paramHashMap.getInt("offer_id"), paramHashMap.getInt("id"), str2);
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = true;
    boolean bool3 = false;
    if (this == paramObject) {
      bool1 = true;
    }
    label144:
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
                  paramObject = (Reward)paramObject;
                  bool1 = bool3;
                } while (this.rewardAmount != ((Reward)paramObject).rewardAmount);
                bool1 = bool3;
              } while (this.itemId != ((Reward)paramObject).itemId);
              bool1 = bool3;
            } while (this.offerId != ((Reward)paramObject).offerId);
            bool1 = bool3;
          } while (this.zoneId != ((Reward)paramObject).zoneId);
          if (this.preRollMessage == null) {
            break;
          }
          bool1 = bool3;
        } while (!this.preRollMessage.equals(((Reward)paramObject).preRollMessage));
        if (this.postRollMessage == null) {
          break label204;
        }
        bool1 = bool3;
      } while (!this.postRollMessage.equals(((Reward)paramObject).postRollMessage));
      if (this.rewardItem == null) {
        break label213;
      }
      bool1 = bool3;
    } while (!this.rewardItem.equals(((Reward)paramObject).rewardItem));
    label168:
    if (this.zoneName != null)
    {
      bool1 = bool2;
      if (this.zoneName.equals(((Reward)paramObject).zoneName)) {}
    }
    label191:
    for (boolean bool1 = false;; bool1 = bool2)
    {
      return bool1;
      if (((Reward)paramObject).preRollMessage == null) {
        break;
      }
      return false;
      label204:
      if (((Reward)paramObject).postRollMessage == null) {
        break label144;
      }
      return false;
      label213:
      if (((Reward)paramObject).rewardItem == null) {
        break label168;
      }
      return false;
      if (((Reward)paramObject).zoneName != null) {
        break label191;
      }
    }
  }
  
  @NonNull
  public RewardedInfo getInfo()
  {
    return new RewardedInfo(this.preRollMessage, this.postRollMessage, this.rewardItem, this.rewardAmount, this.itemId);
  }
  
  public boolean hasPostRollMessage()
  {
    return !StringUtil.isNullOrEmpty(this.postRollMessage);
  }
  
  public boolean hasPreRollMessage()
  {
    return !StringUtil.isNullOrEmpty(this.preRollMessage);
  }
  
  public boolean hasRichMediaPostroll()
  {
    return !StringUtil.isNullOrEmpty(this.richMediaPostrollScript);
  }
  
  public boolean hasRichMediaPreroll()
  {
    return !StringUtil.isNullOrEmpty(this.richMediaPrerollScript);
  }
  
  public int hashCode()
  {
    int m = 0;
    int i;
    int j;
    if (this.preRollMessage != null)
    {
      i = this.preRollMessage.hashCode();
      if (this.postRollMessage == null) {
        break label135;
      }
      j = this.postRollMessage.hashCode();
      label33:
      if (this.rewardItem == null) {
        break label140;
      }
    }
    label135:
    label140:
    for (int k = this.rewardItem.hashCode();; k = 0)
    {
      int n = this.rewardAmount;
      int i1 = this.itemId;
      int i2 = this.offerId;
      int i3 = this.zoneId;
      if (this.zoneName != null) {
        m = this.zoneName.hashCode();
      }
      return ((((((i * 31 + j) * 31 + k) * 31 + n) * 31 + i1) * 31 + i2) * 31 + i3) * 31 + m;
      i = 0;
      break;
      j = 0;
      break label33;
    }
  }
  
  public String toString()
  {
    return "RewardObject {zoneId:" + this.zoneId + ", offerId: " + this.offerId + ", itemId: " + this.itemId + ", rewardItem: " + this.rewardItem + ", rewardAmount: " + this.rewardAmount + ", preRollMessage: " + this.preRollMessage + ", rewardMessage: " + this.postRollMessage + "}";
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/data/Reward.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */