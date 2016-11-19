package com.nianticlabs.nia.iap;

import java.util.ArrayList;
import java.util.Collection;

public abstract interface InAppBillingProvider
{
  public abstract void getPurchasableItems(ArrayList<String> paramArrayList);
  
  public abstract boolean isBillingAvailable();
  
  public abstract boolean isTransactionInProgress();
  
  public abstract void onPause();
  
  public abstract void onProcessedGoogleBillingTransaction(boolean paramBoolean, String paramString);
  
  public abstract void onResume();
  
  public abstract void purchaseItem(String paramString1, String paramString2);
  
  public abstract void setDelegate(Delegate paramDelegate);
  
  public static abstract interface Delegate
  {
    public abstract void ProcessReceipt(String paramString1, String paramString2, String paramString3, int paramInt);
    
    public abstract void onConnectionStateChanged(boolean paramBoolean);
    
    public abstract void purchasableItemsResult(Collection<PurchasableItemDetails> paramCollection);
    
    public abstract void purchaseResult(PurchaseResult paramPurchaseResult);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/iap/InAppBillingProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */