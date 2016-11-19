package com.nianticlabs.nia.iap;

class PurchasableItemDetails
{
  private String description;
  private String itemId;
  private String price;
  private String title;
  
  PurchasableItemDetails(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.itemId = paramString1;
    this.title = paramString2;
    this.description = paramString3;
    this.price = paramString4;
  }
  
  String getDescription()
  {
    return this.description;
  }
  
  String getItemId()
  {
    return this.itemId;
  }
  
  String getPrice()
  {
    return this.price;
  }
  
  String getTitle()
  {
    return this.title;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/iap/PurchasableItemDetails.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */