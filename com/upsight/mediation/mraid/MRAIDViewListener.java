package com.upsight.mediation.mraid;

public abstract interface MRAIDViewListener
{
  public abstract void mraidReplayVideoPressed(MRAIDView paramMRAIDView);
  
  public abstract void mraidViewAcceptPressed(MRAIDView paramMRAIDView);
  
  public abstract void mraidViewClose(MRAIDView paramMRAIDView);
  
  public abstract void mraidViewExpand(MRAIDView paramMRAIDView);
  
  public abstract void mraidViewFailedToLoad(MRAIDView paramMRAIDView);
  
  public abstract void mraidViewLoaded(MRAIDView paramMRAIDView);
  
  public abstract void mraidViewRejectPressed(MRAIDView paramMRAIDView);
  
  public abstract boolean mraidViewResize(MRAIDView paramMRAIDView, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/mraid/MRAIDViewListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */