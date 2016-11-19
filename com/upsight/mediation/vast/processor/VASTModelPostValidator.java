package com.upsight.mediation.vast.processor;

import android.text.TextUtils;
import com.upsight.mediation.vast.model.VASTMediaFile;
import com.upsight.mediation.vast.model.VASTModel;
import java.util.List;

public class VASTModelPostValidator
{
  private static final String TAG = "VASTModelPostValidator";
  
  public static boolean pickMediaFile(VASTModel paramVASTModel, VASTMediaPicker paramVASTMediaPicker)
  {
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (paramVASTMediaPicker != null)
    {
      Object localObject = paramVASTMediaPicker.pickVideo(paramVASTModel.getMediaFiles());
      bool1 = bool2;
      if (localObject != null)
      {
        paramVASTMediaPicker = ((VASTMediaFile)localObject).getValue();
        localObject = ((VASTMediaFile)localObject).getDelivery();
        bool1 = bool2;
        if (!TextUtils.isEmpty(paramVASTMediaPicker))
        {
          bool1 = true;
          paramVASTModel.setPickedMediaFileLocation(paramVASTMediaPicker);
          paramVASTModel.setPickedMediaFileDeliveryType((String)localObject);
        }
      }
    }
    return bool1;
  }
  
  public static boolean validate(VASTModel paramVASTModel)
  {
    return validateModel(paramVASTModel);
  }
  
  private static boolean validateModel(VASTModel paramVASTModel)
  {
    boolean bool = true;
    if (!paramVASTModel.evaluateAdTitle()) {
      bool = false;
    }
    do
    {
      return bool;
      if (!paramVASTModel.evaluateAdSystem()) {
        return false;
      }
      List localList = paramVASTModel.getImpressions();
      if ((localList == null) || (localList.size() == 0)) {
        return false;
      }
      paramVASTModel = paramVASTModel.getMediaFiles();
    } while ((paramVASTModel != null) && (paramVASTModel.size() != 0));
    return false;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/processor/VASTModelPostValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */