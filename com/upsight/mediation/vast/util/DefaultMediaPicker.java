package com.upsight.mediation.vast.util;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.upsight.mediation.vast.model.VASTMediaFile;
import com.upsight.mediation.vast.processor.VASTMediaPicker;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class DefaultMediaPicker
  implements VASTMediaPicker
{
  private static final String TAG = "DefaultMediaPicker";
  private static final int maxPixels = 5000;
  String SUPPORTED_VIDEO_TYPE_REGEX = "video/.*(?i)(mp4|3gpp|mp2t|webm|matroska)";
  private Context context;
  private int deviceArea;
  private int deviceHeight;
  private int deviceWidth;
  
  public DefaultMediaPicker(int paramInt1, int paramInt2)
  {
    setDeviceWidthHeight(paramInt1, paramInt2);
  }
  
  public DefaultMediaPicker(Context paramContext)
  {
    this.context = paramContext;
    setDeviceWidthHeight();
  }
  
  private VASTMediaFile getBestMatch(List<VASTMediaFile> paramList)
  {
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      VASTMediaFile localVASTMediaFile = (VASTMediaFile)paramList.next();
      if (isMediaFileCompatible(localVASTMediaFile)) {
        return localVASTMediaFile;
      }
    }
    return null;
  }
  
  private boolean isMediaFileCompatible(VASTMediaFile paramVASTMediaFile)
  {
    return paramVASTMediaFile.getType().matches(this.SUPPORTED_VIDEO_TYPE_REGEX);
  }
  
  private int prefilterMediaFiles(List<VASTMediaFile> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      VASTMediaFile localVASTMediaFile = (VASTMediaFile)localIterator.next();
      if (TextUtils.isEmpty(localVASTMediaFile.getType()))
      {
        localIterator.remove();
      }
      else
      {
        BigInteger localBigInteger = localVASTMediaFile.getHeight();
        if (localBigInteger == null)
        {
          localIterator.remove();
        }
        else
        {
          int i = localBigInteger.intValue();
          if ((i <= 0) || (i >= 5000))
          {
            localIterator.remove();
          }
          else
          {
            localBigInteger = localVASTMediaFile.getWidth();
            if (localBigInteger == null)
            {
              localIterator.remove();
            }
            else
            {
              i = localBigInteger.intValue();
              if ((i <= 0) || (i >= 5000)) {
                localIterator.remove();
              } else if (TextUtils.isEmpty(localVASTMediaFile.getValue())) {
                localIterator.remove();
              }
            }
          }
        }
      }
    }
    return paramList.size();
  }
  
  private void setDeviceWidthHeight()
  {
    DisplayMetrics localDisplayMetrics = this.context.getResources().getDisplayMetrics();
    this.deviceWidth = localDisplayMetrics.widthPixels;
    this.deviceHeight = localDisplayMetrics.heightPixels;
    this.deviceArea = (this.deviceWidth * this.deviceHeight);
  }
  
  private void setDeviceWidthHeight(int paramInt1, int paramInt2)
  {
    this.deviceWidth = paramInt1;
    this.deviceHeight = paramInt2;
    this.deviceArea = (this.deviceWidth * this.deviceHeight);
  }
  
  public VASTMediaFile pickVideo(List<VASTMediaFile> paramList)
  {
    if ((paramList == null) || (prefilterMediaFiles(paramList) == 0)) {
      return null;
    }
    Collections.sort(paramList, new AreaComparator(null));
    return getBestMatch(paramList);
  }
  
  private class AreaComparator
    implements Comparator<VASTMediaFile>
  {
    private AreaComparator() {}
    
    public int compare(VASTMediaFile paramVASTMediaFile1, VASTMediaFile paramVASTMediaFile2)
    {
      int k = paramVASTMediaFile1.getWidth().intValue();
      int m = paramVASTMediaFile1.getHeight().intValue();
      int i = paramVASTMediaFile2.getWidth().intValue();
      int j = paramVASTMediaFile2.getHeight().intValue();
      k = Math.abs(k * m - DefaultMediaPicker.this.deviceArea);
      i = Math.abs(i * j - DefaultMediaPicker.this.deviceArea);
      if (k < i) {
        return -1;
      }
      if (k > i) {
        return 1;
      }
      return 0;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/vast/util/DefaultMediaPicker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */