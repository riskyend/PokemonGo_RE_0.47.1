package com.upsight.mediation;

import android.util.Base64;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONStringer;

public class VGOfferInfo
{
  public int currencyID;
  public Date endTime;
  public int itemAmount;
  public String itemName;
  public String metadata;
  public String purchaseCurrency;
  public float purchasePrice;
  public Date startTime;
  public int virtualGoodID;
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = true;
    boolean bool3 = false;
    if (this == paramObject) {
      bool1 = true;
    }
    label147:
    label171:
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
                      do
                      {
                        return bool1;
                        bool1 = bool3;
                      } while (paramObject == null);
                      bool1 = bool3;
                    } while (getClass() != paramObject.getClass());
                    paramObject = (VGOfferInfo)paramObject;
                    bool1 = bool3;
                  } while (Float.compare(((VGOfferInfo)paramObject).purchasePrice, this.purchasePrice) != 0);
                  bool1 = bool3;
                } while (this.itemAmount != ((VGOfferInfo)paramObject).itemAmount);
                bool1 = bool3;
              } while (this.virtualGoodID != ((VGOfferInfo)paramObject).virtualGoodID);
              bool1 = bool3;
            } while (this.currencyID != ((VGOfferInfo)paramObject).currencyID);
            if (this.purchaseCurrency == null) {
              break;
            }
            bool1 = bool3;
          } while (!this.purchaseCurrency.equals(((VGOfferInfo)paramObject).purchaseCurrency));
          if (this.itemName == null) {
            break label231;
          }
          bool1 = bool3;
        } while (!this.itemName.equals(((VGOfferInfo)paramObject).itemName));
        if (this.startTime == null) {
          break label240;
        }
        bool1 = bool3;
      } while (!this.startTime.equals(((VGOfferInfo)paramObject).startTime));
      if (this.endTime == null) {
        break label249;
      }
      bool1 = bool3;
    } while (!this.endTime.equals(((VGOfferInfo)paramObject).endTime));
    label195:
    if (this.metadata != null)
    {
      bool1 = bool2;
      if (this.metadata.equals(((VGOfferInfo)paramObject).metadata)) {}
    }
    label218:
    for (boolean bool1 = false;; bool1 = bool2)
    {
      return bool1;
      if (((VGOfferInfo)paramObject).purchaseCurrency == null) {
        break;
      }
      return false;
      label231:
      if (((VGOfferInfo)paramObject).itemName == null) {
        break label147;
      }
      return false;
      label240:
      if (((VGOfferInfo)paramObject).startTime == null) {
        break label171;
      }
      return false;
      label249:
      if (((VGOfferInfo)paramObject).endTime == null) {
        break label195;
      }
      return false;
      if (((VGOfferInfo)paramObject).metadata != null) {
        break label218;
      }
    }
  }
  
  public int hashCode()
  {
    int i1 = 0;
    int i;
    int j;
    label35:
    int k;
    label50:
    int i2;
    int i3;
    int i4;
    int m;
    if (this.purchaseCurrency != null)
    {
      i = this.purchaseCurrency.hashCode();
      if (this.purchasePrice == 0.0F) {
        break label169;
      }
      j = Float.floatToIntBits(this.purchasePrice);
      if (this.itemName == null) {
        break label174;
      }
      k = this.itemName.hashCode();
      i2 = this.itemAmount;
      i3 = this.virtualGoodID;
      i4 = this.currencyID;
      if (this.startTime == null) {
        break label179;
      }
      m = this.startTime.hashCode();
      label84:
      if (this.endTime == null) {
        break label185;
      }
    }
    label169:
    label174:
    label179:
    label185:
    for (int n = this.endTime.hashCode();; n = 0)
    {
      if (this.metadata != null) {
        i1 = this.metadata.hashCode();
      }
      return (((((((i * 31 + j) * 31 + k) * 31 + i2) * 31 + i3) * 31 + i4) * 31 + m) * 31 + n) * 31 + i1;
      i = 0;
      break;
      j = 0;
      break label35;
      k = 0;
      break label50;
      m = 0;
      break label84;
    }
  }
  
  public String toJSONString()
  {
    long l2 = 0L;
    try
    {
      Object localObject = new JSONStringer().object().key("VGOfferInfo").object().key("purchaseCurrency").value(this.purchaseCurrency).key("purchasePrice").value(this.purchasePrice).key("itemName").value(this.itemName).key("itemAmount").value(this.itemAmount).key("virtualGoodID").value(this.virtualGoodID).key("currencyID").value(this.currencyID).key("startTime");
      long l1;
      label134:
      JSONStringer localJSONStringer;
      if (this.startTime == null)
      {
        l1 = 0L;
        localObject = ((JSONStringer)localObject).value(l1).key("endTime");
        if (this.endTime != null) {
          break label186;
        }
        l1 = l2;
        localJSONStringer = ((JSONStringer)localObject).value(l1).key("metadata");
        if (this.metadata != null) {
          break label197;
        }
      }
      label186:
      label197:
      for (localObject = "";; localObject = Base64.encodeToString(this.metadata.getBytes(), 2))
      {
        return localJSONStringer.value(localObject).endObject().endObject().toString();
        l1 = this.startTime.getTime();
        break;
        l1 = this.endTime.getTime();
        break label134;
      }
      return "{ \"VGOfferInfo\" : \"\" }";
    }
    catch (JSONException localJSONException) {}
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer("VGOfferInfo{");
    localStringBuffer.append("purchaseCurrency='").append(this.purchaseCurrency).append('\'');
    localStringBuffer.append(", purchasePrice=").append(this.purchasePrice);
    localStringBuffer.append(", itemName='").append(this.itemName).append('\'');
    localStringBuffer.append(", itemAmount=").append(this.itemAmount);
    localStringBuffer.append(", virtualGoodID=").append(this.virtualGoodID);
    localStringBuffer.append(", currencyID=").append(this.currencyID);
    localStringBuffer.append(", startTime=").append(this.startTime);
    localStringBuffer.append(", endTime=").append(this.endTime);
    localStringBuffer.append(", metadata='").append(this.metadata).append('\'');
    localStringBuffer.append('}');
    return localStringBuffer.toString();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/VGOfferInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */