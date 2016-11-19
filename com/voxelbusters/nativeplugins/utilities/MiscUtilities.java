package com.voxelbusters.nativeplugins.utilities;

import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import com.voxelbusters.nativeplugins.features.sharing.SharingHelper;
import java.util.Comparator;

public class MiscUtilities
{
  public static Comparator<ResolveInfo> resolveInfoComparator = new Comparator()
  {
    public int compare(ResolveInfo paramAnonymousResolveInfo1, ResolveInfo paramAnonymousResolveInfo2)
    {
      boolean bool1 = SharingHelper.isSocialNetwork(paramAnonymousResolveInfo1.activityInfo.packageName);
      boolean bool2 = SharingHelper.isSocialNetwork(paramAnonymousResolveInfo2.activityInfo.packageName);
      if (((bool1) && (bool2)) || (bool1)) {
        return -1;
      }
      if (bool2) {
        return 1;
      }
      return 0;
    }
  };
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/voxelbusters/nativeplugins/utilities/MiscUtilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */