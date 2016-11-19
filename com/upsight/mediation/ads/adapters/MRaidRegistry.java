package com.upsight.mediation.ads.adapters;

import com.upsight.mediation.log.FuseLog;
import java.util.ArrayList;

public class MRaidRegistry
{
  private static final String TAG = "MRaidRegistry";
  public ArrayList<MRaidAdAdapter> registeredProviders = new ArrayList();
  
  public MRaidAdAdapter getProvider(int paramInt)
  {
    return (MRaidAdAdapter)this.registeredProviders.get(paramInt);
  }
  
  public int register(MRaidAdAdapter paramMRaidAdAdapter)
  {
    if (this.registeredProviders.contains(paramMRaidAdAdapter)) {
      FuseLog.w("MRaidRegistry", "Trying to register provider, already registered");
    }
    this.registeredProviders.add(paramMRaidAdAdapter);
    return this.registeredProviders.indexOf(paramMRaidAdAdapter);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/ads/adapters/MRaidRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */