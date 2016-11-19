package com.upsight.mediation;

import android.util.Base64;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONStringer;

public class IAPOfferInfo
{
  public Date endTime;
  public int itemAmount;
  public String itemName;
  public String metadata;
  public String productID;
  public float productPrice;
  public Date startTime;
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = true;
    boolean bool3 = false;
    if (this == paramObject) {
      bool1 = true;
    }
    label119:
    label143:
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
                paramObject = (IAPOfferInfo)paramObject;
                bool1 = bool3;
              } while (Float.compare(((IAPOfferInfo)paramObject).productPrice, this.productPrice) != 0);
              bool1 = bool3;
            } while (this.itemAmount != ((IAPOfferInfo)paramObject).itemAmount);
            if (this.itemName == null) {
              break;
            }
            bool1 = bool3;
          } while (!this.itemName.equals(((IAPOfferInfo)paramObject).itemName));
          if (this.productID == null) {
            break label203;
          }
          bool1 = bool3;
        } while (!this.productID.equals(((IAPOfferInfo)paramObject).productID));
        if (this.startTime == null) {
          break label212;
        }
        bool1 = bool3;
      } while (!this.startTime.equals(((IAPOfferInfo)paramObject).startTime));
      if (this.endTime == null) {
        break label221;
      }
      bool1 = bool3;
    } while (!this.endTime.equals(((IAPOfferInfo)paramObject).endTime));
    label167:
    if (this.metadata != null)
    {
      bool1 = bool2;
      if (this.metadata.equals(((IAPOfferInfo)paramObject).metadata)) {}
    }
    label190:
    for (boolean bool1 = false;; bool1 = bool2)
    {
      return bool1;
      if (((IAPOfferInfo)paramObject).itemName == null) {
        break;
      }
      return false;
      label203:
      if (((IAPOfferInfo)paramObject).productID == null) {
        break label119;
      }
      return false;
      label212:
      if (((IAPOfferInfo)paramObject).startTime == null) {
        break label143;
      }
      return false;
      label221:
      if (((IAPOfferInfo)paramObject).endTime == null) {
        break label167;
      }
      return false;
      if (((IAPOfferInfo)paramObject).metadata != null) {
        break label190;
      }
    }
  }
  
  public int hashCode()
  {
    int i1 = 0;
    int i;
    int i2;
    int j;
    label41:
    int k;
    label56:
    int m;
    if (this.productPrice != 0.0F)
    {
      i = Float.floatToIntBits(this.productPrice);
      i2 = this.itemAmount;
      if (this.itemName == null) {
        break label145;
      }
      j = this.itemName.hashCode();
      if (this.productID == null) {
        break label150;
      }
      k = this.productID.hashCode();
      if (this.startTime == null) {
        break label155;
      }
      m = this.startTime.hashCode();
      label72:
      if (this.endTime == null) {
        break label161;
      }
    }
    label145:
    label150:
    label155:
    label161:
    for (int n = this.endTime.hashCode();; n = 0)
    {
      if (this.metadata != null) {
        i1 = this.metadata.hashCode();
      }
      return (((((i * 31 + i2) * 31 + j) * 31 + k) * 31 + m) * 31 + n) * 31 + i1;
      i = 0;
      break;
      j = 0;
      break label41;
      k = 0;
      break label56;
      m = 0;
      break label72;
    }
  }
  
  public String toJSONString()
  {
    try
    {
      JSONStringer localJSONStringer = new JSONStringer().object().key("IAPOfferInfo").object().key("productPrice").value(this.productPrice).key("itemAmount").value(this.itemAmount).key("itemName").value(this.itemName).key("productID").value(this.productID).key("startTime");
      if (this.startTime == null)
      {
        localObject = "";
        localJSONStringer = localJSONStringer.value(localObject).key("endTime");
        if (this.endTime != null) {
          break label155;
        }
        localObject = "";
        label105:
        localJSONStringer = localJSONStringer.value(localObject).key("metadata");
        if (this.metadata != null) {
          break label169;
        }
      }
      label155:
      label169:
      for (Object localObject = "";; localObject = Base64.encodeToString(this.metadata.getBytes(), 2))
      {
        return localJSONStringer.value(localObject).endObject().endObject().toString();
        localObject = Long.valueOf(this.startTime.getTime());
        break;
        localObject = Long.valueOf(this.endTime.getTime());
        break label105;
      }
      return "{ \"IAPOfferInfo\" : \"\" }";
    }
    catch (JSONException localJSONException) {}
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer("IAPOfferInfo{");
    localStringBuffer.append("productPrice=").append(this.productPrice);
    localStringBuffer.append(", itemAmount=").append(this.itemAmount);
    localStringBuffer.append(", itemName='").append(this.itemName).append('\'');
    localStringBuffer.append(", productID='").append(this.productID).append('\'');
    localStringBuffer.append(", startTime=").append(this.startTime);
    localStringBuffer.append(", endTime=").append(this.endTime);
    localStringBuffer.append(", metadata='").append(this.metadata).append('\'');
    localStringBuffer.append('}');
    return localStringBuffer.toString();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/IAPOfferInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */