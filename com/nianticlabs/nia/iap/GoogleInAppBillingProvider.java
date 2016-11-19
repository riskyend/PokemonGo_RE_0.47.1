package com.nianticlabs.nia.iap;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import com.android.vending.billing.IInAppBillingService;
import com.android.vending.billing.IInAppBillingService.Stub;
import com.nianticlabs.nia.contextservice.ContextService;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GoogleInAppBillingProvider
  implements InAppBillingProvider
{
  private static final int BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = 3;
  private static final int BILLING_RESPONSE_RESULT_DEVELOPER_ERROR = 5;
  private static final int BILLING_RESPONSE_RESULT_ERROR = 6;
  private static final int BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED = 7;
  private static final int BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED = 8;
  private static final int BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE = 4;
  private static final int BILLING_RESPONSE_RESULT_NOT_FOUND = 1000;
  private static final int BILLING_RESPONSE_RESULT_OK = 0;
  private static final int BILLING_RESPONSE_RESULT_USER_CANCELED = 1;
  private static final int DEFAULT_BILLING_SERVICE_VERSION = 3;
  private static final int DESIRED_BILLING_SERVICE_VERSION = 6;
  static final boolean ENABLE_VERBOSE_LOGS = false;
  private static final String GET_SKU_DETAILS_ITEM_LIST = "ITEM_ID_LIST";
  private static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";
  private static final String ITEM_TYPE_INAPP = "inapp";
  private static final String PACKAGE_NAME_BASE = "com.niantic";
  private static final String RESPONSE_BUY_INTENT = "BUY_INTENT";
  private static final String RESPONSE_CODE = "RESPONSE_CODE";
  private static final String RESPONSE_GET_SKU_DETAILS_LIST = "DETAILS_LIST";
  private static final String RESPONSE_INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
  private static final String RESPONSE_INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";
  private static final String RESPONSE_INAPP_SIGNATURE = "INAPP_DATA_SIGNATURE";
  private static final String RESPONSE_INAPP_SIGNATURE_LIST = "INAPP_DATA_SIGNATURE_LIST";
  private static final String UNKNOWN_CURRENCY_STRING = "UNKNOWN";
  private static WeakReference<GoogleInAppBillingProvider> instance = null;
  private static final Logger log = new Logger(GoogleInAppBillingProvider.class);
  private IInAppBillingService billingService = null;
  private int billingServiceVersion = 3;
  private boolean clientConnected = false;
  private boolean connectionInProgress = false;
  private final Context context;
  private Map<String, GetSkuDetailsResponseItem> currentPurchasableItems;
  private InAppBillingProvider.Delegate delegate;
  private String itemBeingPurchased = null;
  private final String packageName;
  private PendingIntent pendingIntent;
  private boolean purchaseSupported = false;
  private ServiceConnection serviceConnection = null;
  private int transactionsInProgress = 0;
  
  public GoogleInAppBillingProvider(Context paramContext)
  {
    String str = paramContext.getPackageName();
    if (!str.startsWith("com.niantic")) {}
    for (this.packageName = "ERROR";; this.packageName = str)
    {
      this.context = paramContext;
      this.currentPurchasableItems = new HashMap();
      instance = new WeakReference(this);
      connectToBillingService();
      return;
    }
  }
  
  private void connectToBillingService()
  {
    if (this.connectionInProgress) {
      return;
    }
    if (this.billingService != null)
    {
      finalizeConnectionResult();
      return;
    }
    this.connectionInProgress = true;
    this.serviceConnection = new ServiceConnection()
    {
      public void onServiceConnected(ComponentName paramAnonymousComponentName, final IBinder paramAnonymousIBinder)
      {
        ContextService.runOnServiceHandler(new Runnable()
        {
          public void run()
          {
            if (GoogleInAppBillingProvider.this.serviceConnection == null)
            {
              GoogleInAppBillingProvider.this.finalizeConnectionResult();
              return;
            }
            GoogleInAppBillingProvider.access$002(GoogleInAppBillingProvider.this, IInAppBillingService.Stub.asInterface(paramAnonymousIBinder));
            for (;;)
            {
              try
              {
                GoogleInAppBillingProvider.access$102(GoogleInAppBillingProvider.this, 6);
                int j = GoogleInAppBillingProvider.this.billingService.isBillingSupported(GoogleInAppBillingProvider.this.billingServiceVersion, GoogleInAppBillingProvider.this.packageName, "inapp");
                int i = j;
                if (j != 0)
                {
                  GoogleInAppBillingProvider.access$102(GoogleInAppBillingProvider.this, 3);
                  i = GoogleInAppBillingProvider.this.billingService.isBillingSupported(GoogleInAppBillingProvider.this.billingServiceVersion, GoogleInAppBillingProvider.this.packageName, "inapp");
                }
                GoogleInAppBillingProvider localGoogleInAppBillingProvider = GoogleInAppBillingProvider.this;
                if (i != 0) {
                  continue;
                }
                bool = true;
                GoogleInAppBillingProvider.access$1202(localGoogleInAppBillingProvider, bool);
              }
              catch (RemoteException localRemoteException)
              {
                boolean bool;
                GoogleInAppBillingProvider.access$1202(GoogleInAppBillingProvider.this, false);
                continue;
                GoogleInAppBillingProvider.this.finalizeConnectionResult();
              }
              if (GoogleInAppBillingProvider.this.currentPurchasableItems.size() <= 0) {
                continue;
              }
              new GoogleInAppBillingProvider.ProcessPurchasedItemsTask(GoogleInAppBillingProvider.this).execute(new Void[0]);
              return;
              bool = false;
            }
          }
        });
      }
      
      public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
      {
        ContextService.runOnServiceHandler(new Runnable()
        {
          public void run()
          {
            GoogleInAppBillingProvider.access$002(GoogleInAppBillingProvider.this, null);
            GoogleInAppBillingProvider.this.finalizeConnectionResult();
          }
        });
      }
    };
    Intent localIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
    localIntent.setPackage("com.android.vending");
    List localList = this.context.getPackageManager().queryIntentServices(localIntent, 0);
    if ((localList == null) || (localList.isEmpty())) {
      finalizeConnectionResult();
    }
    this.context.bindService(localIntent, this.serviceConnection, 1);
  }
  
  private void finalizeConnectionResult()
  {
    boolean bool = false;
    this.connectionInProgress = false;
    if (this.delegate != null)
    {
      InAppBillingProvider.Delegate localDelegate = this.delegate;
      if (this.billingService != null) {
        bool = true;
      }
      localDelegate.onConnectionStateChanged(bool);
    }
  }
  
  private void finalizePurchaseResult(PurchaseResult paramPurchaseResult)
  {
    this.transactionsInProgress -= 1;
    maybeDisconnectBillingService();
    if (this.delegate != null) {
      this.delegate.purchaseResult(paramPurchaseResult);
    }
  }
  
  public static WeakReference<GoogleInAppBillingProvider> getInstance()
  {
    return instance;
  }
  
  private static int getResponseCodeFromBundle(Bundle paramBundle)
  {
    return getResponseCodeFromObject(paramBundle.get("RESPONSE_CODE"));
  }
  
  private static int getResponseCodeFromIntent(Intent paramIntent)
  {
    return getResponseCodeFromObject(paramIntent.getExtras().get("RESPONSE_CODE"));
  }
  
  private static int getResponseCodeFromObject(Object paramObject)
  {
    if (paramObject == null) {
      return 0;
    }
    if ((paramObject instanceof Integer)) {
      return ((Integer)paramObject).intValue();
    }
    if ((paramObject instanceof Long)) {
      return (int)((Long)paramObject).longValue();
    }
    return 6;
  }
  
  private boolean handlePurchaseErrorResult(int paramInt)
  {
    boolean bool = false;
    switch (paramInt)
    {
    case 2: 
    case 5: 
    case 6: 
    default: 
      finalizePurchaseResult(PurchaseResult.FAILURE);
    }
    for (;;)
    {
      bool = true;
      return bool;
      finalizePurchaseResult(PurchaseResult.USER_CANCELLED);
      continue;
      finalizePurchaseResult(PurchaseResult.BILLING_UNAVAILABLE);
      continue;
      finalizePurchaseResult(PurchaseResult.SKU_NOT_AVAILABLE);
      continue;
      new ProcessPurchasedItemsTask().execute(new Void[0]);
      finalizePurchaseResult(PurchaseResult.FAILURE);
    }
  }
  
  private void launchPurchaseActivity(PendingIntent paramPendingIntent)
  {
    this.pendingIntent = paramPendingIntent;
    ContextService.runOnUiThread(new Runnable()
    {
      public void run()
      {
        Intent localIntent = new Intent(GoogleInAppBillingProvider.this.context, PurchaseActivity.class);
        GoogleInAppBillingProvider.this.context.startActivity(localIntent);
      }
    });
  }
  
  private void maybeDisconnectBillingService()
  {
    if ((this.transactionsInProgress > 0) || (this.connectionInProgress) || (this.clientConnected)) {
      return;
    }
    if (this.serviceConnection != null) {
      this.context.unbindService(this.serviceConnection);
    }
    this.serviceConnection = null;
    this.billingService = null;
    this.transactionsInProgress = 0;
  }
  
  private void notifyPurchasableItemsResult(Collection<PurchasableItemDetails> paramCollection)
  {
    if (this.delegate != null) {
      this.delegate.purchasableItemsResult(paramCollection);
    }
  }
  
  private void processPurchaseResult(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    Object localObject5 = this.itemBeingPurchased;
    this.itemBeingPurchased = null;
    if (this.billingService == null) {}
    while ((paramInt2 != 1000) && (handlePurchaseErrorResult(paramInt2))) {
      return;
    }
    if (paramInt1 == 0)
    {
      finalizePurchaseResult(PurchaseResult.USER_CANCELLED);
      return;
    }
    if (paramInt1 != -1)
    {
      finalizePurchaseResult(PurchaseResult.FAILURE);
      return;
    }
    if ((paramInt2 == 1000) || (paramString1 == null) || (paramString2 == null))
    {
      finalizePurchaseResult(PurchaseResult.FAILURE);
      return;
    }
    Object localObject3 = "UNKNOWN";
    Object localObject4 = null;
    paramInt2 = 0;
    Object localObject2 = localObject3;
    paramInt1 = paramInt2;
    Object localObject1 = localObject4;
    if (localObject5 != null)
    {
      localObject5 = (GetSkuDetailsResponseItem)this.currentPurchasableItems.get(localObject5);
      localObject2 = localObject3;
      paramInt1 = paramInt2;
      localObject1 = localObject4;
      if (localObject5 != null)
      {
        localObject1 = ((GetSkuDetailsResponseItem)localObject5).getProductId();
        localObject2 = ((GetSkuDetailsResponseItem)localObject5).getCurrencyCode();
        paramInt1 = ((GetSkuDetailsResponseItem)localObject5).getPriceE6();
      }
    }
    if (localObject1 == null)
    {
      localObject3 = GoogleInAppPurchaseData.fromJson(paramString1);
      if (localObject3 != null) {
        localObject1 = ((GoogleInAppPurchaseData)localObject3).getProductId();
      }
      if (localObject1 != null) {}
    }
    this.delegate.ProcessReceipt(paramString1, paramString2, (String)localObject2, paramInt1);
  }
  
  public void forwardedOnActivityResult(final int paramInt, final Intent paramIntent)
  {
    final int i;
    final String str1;
    if (paramIntent != null)
    {
      i = getResponseCodeFromIntent(paramIntent);
      String str2 = paramIntent.getStringExtra("INAPP_PURCHASE_DATA");
      str1 = paramIntent.getStringExtra("INAPP_DATA_SIGNATURE");
      paramIntent = str2;
    }
    for (;;)
    {
      ContextService.runOnServiceHandler(new Runnable()
      {
        public void run()
        {
          GoogleInAppBillingProvider.this.processPurchaseResult(paramInt, i, paramIntent, str1);
        }
      });
      return;
      i = 1000;
      paramIntent = null;
      str1 = null;
    }
  }
  
  public void getPurchasableItems(ArrayList<String> paramArrayList)
  {
    if (!isBillingAvailable())
    {
      notifyPurchasableItemsResult(Collections.emptyList());
      return;
    }
    new GetSkuDetailsTask(paramArrayList).execute(new Void[0]);
  }
  
  public boolean isBillingAvailable()
  {
    return (this.billingService != null) && (this.purchaseSupported);
  }
  
  public boolean isTransactionInProgress()
  {
    return this.transactionsInProgress > 0;
  }
  
  public void onPause()
  {
    this.clientConnected = false;
    maybeDisconnectBillingService();
  }
  
  public void onProcessedGoogleBillingTransaction(boolean paramBoolean, String paramString)
  {
    if (!paramBoolean)
    {
      finalizePurchaseResult(PurchaseResult.FAILURE);
      return;
    }
    if (this.billingService == null)
    {
      finalizePurchaseResult(PurchaseResult.FAILURE);
      return;
    }
    if (paramString == null)
    {
      finalizePurchaseResult(PurchaseResult.FAILURE);
      return;
    }
    new ConsumeItemTask(paramString).execute(new Void[0]);
  }
  
  public void onResume()
  {
    this.clientConnected = true;
    connectToBillingService();
  }
  
  public void purchaseItem(String paramString1, String paramString2)
  {
    this.transactionsInProgress += 1;
    if (!isBillingAvailable()) {
      finalizePurchaseResult(PurchaseResult.BILLING_UNAVAILABLE);
    }
    Object localObject;
    for (;;)
    {
      return;
      if (!this.currentPurchasableItems.keySet().contains(paramString1))
      {
        finalizePurchaseResult(PurchaseResult.SKU_NOT_AVAILABLE);
        return;
      }
      try
      {
        if (this.billingServiceVersion == 6)
        {
          localObject = new Bundle();
          ((Bundle)localObject).putString("accountId", paramString2);
        }
        for (paramString2 = this.billingService.getBuyIntentExtraParams(this.billingServiceVersion, this.packageName, paramString1, "inapp", paramString2, (Bundle)localObject);; paramString2 = this.billingService.getBuyIntent(this.billingServiceVersion, this.packageName, paramString1, "inapp", paramString2))
        {
          localObject = (PendingIntent)paramString2.getParcelable("BUY_INTENT");
          if (handlePurchaseErrorResult(getResponseCodeFromBundle(paramString2))) {
            break;
          }
          if (localObject != null) {
            break label166;
          }
          finalizePurchaseResult(PurchaseResult.FAILURE);
          return;
        }
        if (this.transactionsInProgress != 1) {}
      }
      catch (RemoteException paramString1)
      {
        finalizePurchaseResult(PurchaseResult.FAILURE);
        return;
      }
    }
    label166:
    for (this.itemBeingPurchased = paramString1;; this.itemBeingPurchased = null)
    {
      launchPurchaseActivity((PendingIntent)localObject);
      return;
    }
  }
  
  public void setDelegate(InAppBillingProvider.Delegate paramDelegate)
  {
    this.delegate = paramDelegate;
  }
  
  public void startBuyIntent(Activity paramActivity)
  {
    try
    {
      paramActivity.startIntentSenderForResult(this.pendingIntent.getIntentSender(), 10009, new Intent(), 0, 0, 0);
      return;
    }
    catch (IntentSender.SendIntentException paramActivity)
    {
      this.itemBeingPurchased = null;
      this.pendingIntent = null;
      finalizePurchaseResult(PurchaseResult.FAILURE);
    }
  }
  
  private class ConsumeItemTask
    extends AsyncTask<Void, Void, Integer>
  {
    private final IInAppBillingService billingService;
    private final String purchaseToken;
    
    public ConsumeItemTask(String paramString)
    {
      this.purchaseToken = paramString;
      this.billingService = GoogleInAppBillingProvider.this.billingService;
    }
    
    protected Integer doInBackground(Void... paramVarArgs)
    {
      if (this.billingService == null) {
        return null;
      }
      try
      {
        int i = this.billingService.consumePurchase(GoogleInAppBillingProvider.this.billingServiceVersion, GoogleInAppBillingProvider.this.packageName, this.purchaseToken);
        return Integer.valueOf(i);
      }
      catch (RemoteException paramVarArgs) {}
      return null;
    }
    
    protected void onPostExecute(Integer paramInteger)
    {
      if ((paramInteger == null) || (paramInteger.intValue() != 0))
      {
        GoogleInAppBillingProvider.this.finalizePurchaseResult(PurchaseResult.FAILURE);
        return;
      }
      GoogleInAppBillingProvider.this.finalizePurchaseResult(PurchaseResult.SUCCESS);
    }
  }
  
  private class GetSkuDetailsTask
    extends AsyncTask<Void, Void, Bundle>
  {
    private final IInAppBillingService billingService = GoogleInAppBillingProvider.this.billingService;
    private final Bundle requestBundle = new Bundle();
    
    public GetSkuDetailsTask()
    {
      ArrayList localArrayList;
      this.requestBundle.putStringArrayList("ITEM_ID_LIST", localArrayList);
    }
    
    protected Bundle doInBackground(Void... paramVarArgs)
    {
      if (this.billingService == null) {
        return null;
      }
      try
      {
        paramVarArgs = this.billingService.getSkuDetails(GoogleInAppBillingProvider.this.billingServiceVersion, GoogleInAppBillingProvider.this.packageName, "inapp", this.requestBundle);
        return paramVarArgs;
      }
      catch (RemoteException paramVarArgs) {}
      return null;
    }
    
    protected void onPostExecute(Bundle paramBundle)
    {
      ArrayList localArrayList = new ArrayList();
      GoogleInAppBillingProvider.this.currentPurchasableItems.clear();
      if ((paramBundle != null) && (paramBundle.containsKey("DETAILS_LIST")))
      {
        paramBundle = paramBundle.getStringArrayList("DETAILS_LIST").iterator();
        while (paramBundle.hasNext())
        {
          GetSkuDetailsResponseItem localGetSkuDetailsResponseItem = GetSkuDetailsResponseItem.fromJson((String)paramBundle.next());
          if (localGetSkuDetailsResponseItem != null)
          {
            PurchasableItemDetails localPurchasableItemDetails = GetSkuDetailsResponseItem.toPurchasableItemDetails(localGetSkuDetailsResponseItem);
            localArrayList.add(localPurchasableItemDetails);
            GoogleInAppBillingProvider.this.currentPurchasableItems.put(localPurchasableItemDetails.getItemId(), localGetSkuDetailsResponseItem);
          }
        }
      }
      GoogleInAppBillingProvider.this.notifyPurchasableItemsResult(localArrayList);
      new GoogleInAppBillingProvider.ProcessPurchasedItemsTask(GoogleInAppBillingProvider.this).execute(new Void[0]);
    }
  }
  
  static class Logger
  {
    private final String tag;
    
    public Logger(Class paramClass)
    {
      this.tag = paramClass.toString();
    }
    
    void assertOnServiceThread(String paramString)
    {
      if (!ContextService.onServiceThread()) {
        severe(this.tag + ": Must be on the service thread: " + paramString, new Object[0]);
      }
    }
    
    void dev(String paramString, Object... paramVarArgs) {}
    
    void error(String paramString, Object... paramVarArgs) {}
    
    void severe(String paramString, Object... paramVarArgs) {}
    
    void warning(String paramString, Object... paramVarArgs) {}
  }
  
  private class ProcessPurchasedItemsTask
    extends AsyncTask<Void, Void, Bundle>
  {
    private final IInAppBillingService billingService = GoogleInAppBillingProvider.this.billingService;
    
    public ProcessPurchasedItemsTask() {}
    
    protected Bundle doInBackground(Void... paramVarArgs)
    {
      if (this.billingService == null) {
        return null;
      }
      Object localObject1 = null;
      paramVarArgs = null;
      localObject6 = null;
      localObject4 = localObject1;
      localObject5 = paramVarArgs;
      label104:
      label135:
      do
      {
        do
        {
          do
          {
            do
            {
              try
              {
                localObject8 = this.billingService.getPurchases(GoogleInAppBillingProvider.this.billingServiceVersion, GoogleInAppBillingProvider.this.packageName, "inapp", (String)localObject6);
                localObject4 = localObject1;
                localObject5 = paramVarArgs;
                i = GoogleInAppBillingProvider.getResponseCodeFromBundle((Bundle)localObject8);
                localObject4 = localObject1;
                localObject5 = paramVarArgs;
                localObject3 = ((Bundle)localObject8).getStringArrayList("INAPP_PURCHASE_DATA_LIST");
                localObject4 = localObject1;
                localObject5 = paramVarArgs;
                localObject2 = ((Bundle)localObject8).getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
                if (i != 5) {
                  break label135;
                }
                localObject7 = paramVarArgs;
                localObject6 = localObject1;
              }
              catch (RemoteException paramVarArgs)
              {
                for (;;)
                {
                  Object localObject8;
                  int i;
                  Object localObject3;
                  Object localObject2;
                  localObject6 = localObject4;
                  Object localObject7 = localObject5;
                }
              }
              if (localObject6 == null) {
                break;
              }
              paramVarArgs = new Bundle();
              paramVarArgs.putStringArrayList("INAPP_PURCHASE_DATA_LIST", (ArrayList)localObject6);
              paramVarArgs.putStringArrayList("INAPP_DATA_SIGNATURE_LIST", (ArrayList)localObject7);
              return paramVarArgs;
              localObject6 = localObject1;
              localObject7 = paramVarArgs;
            } while (i != 0);
            localObject6 = localObject1;
            localObject7 = paramVarArgs;
            localObject4 = localObject1;
            localObject5 = paramVarArgs;
          } while (!((Bundle)localObject8).containsKey("INAPP_PURCHASE_DATA_LIST"));
          localObject6 = localObject1;
          localObject7 = paramVarArgs;
          localObject4 = localObject1;
          localObject5 = paramVarArgs;
        } while (!((Bundle)localObject8).containsKey("INAPP_DATA_SIGNATURE_LIST"));
        localObject6 = localObject1;
        localObject7 = paramVarArgs;
        localObject4 = localObject1;
        localObject5 = paramVarArgs;
      } while (((ArrayList)localObject3).size() != ((ArrayList)localObject2).size());
      if (localObject1 == null) {}
      for (;;)
      {
        localObject4 = localObject3;
        localObject5 = localObject2;
        localObject8 = ((Bundle)localObject8).getString("INAPP_CONTINUATION_TOKEN");
        localObject6 = localObject3;
        localObject7 = localObject2;
        if (localObject8 == null) {
          break label104;
        }
        localObject1 = localObject3;
        paramVarArgs = (Void[])localObject2;
        localObject6 = localObject8;
        localObject4 = localObject3;
        localObject5 = localObject2;
        if (((String)localObject8).length() != 0) {
          break;
        }
        localObject6 = localObject3;
        localObject7 = localObject2;
        break label104;
        localObject4 = localObject1;
        localObject5 = paramVarArgs;
        ((ArrayList)localObject1).addAll((Collection)localObject3);
        localObject4 = localObject1;
        localObject5 = paramVarArgs;
        paramVarArgs.addAll((Collection)localObject2);
        localObject3 = localObject1;
        localObject2 = paramVarArgs;
      }
    }
    
    protected void onPostExecute(final Bundle paramBundle)
    {
      if (paramBundle != null)
      {
        ContextService.runOnServiceHandler(new Runnable()
        {
          public void run()
          {
            ArrayList localArrayList1 = paramBundle.getStringArrayList("INAPP_PURCHASE_DATA_LIST");
            ArrayList localArrayList2 = paramBundle.getStringArrayList("INAPP_DATA_SIGNATURE_LIST");
            int i = 0;
            while (i < localArrayList1.size())
            {
              GoogleInAppBillingProvider.access$708(GoogleInAppBillingProvider.this);
              GoogleInAppBillingProvider.this.processPurchaseResult(-1, 0, (String)localArrayList1.get(i), (String)localArrayList2.get(i));
              i += 1;
            }
            GoogleInAppBillingProvider.this.finalizeConnectionResult();
            GoogleInAppBillingProvider.this.maybeDisconnectBillingService();
          }
        });
        return;
      }
      GoogleInAppBillingProvider.this.finalizeConnectionResult();
      GoogleInAppBillingProvider.this.maybeDisconnectBillingService();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/iap/GoogleInAppBillingProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */