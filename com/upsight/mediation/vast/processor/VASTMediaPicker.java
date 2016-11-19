package com.upsight.mediation.vast.processor;

import com.upsight.mediation.vast.model.VASTMediaFile;
import java.util.List;

public abstract interface VASTMediaPicker
{
  public abstract VASTMediaFile pickVideo(List<VASTMediaFile> paramList);
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/processor/VASTMediaPicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */