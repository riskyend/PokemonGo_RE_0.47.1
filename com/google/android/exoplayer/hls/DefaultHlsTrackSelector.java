package com.google.android.exoplayer.hls;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.exoplayer.chunk.Format;
import com.google.android.exoplayer.chunk.VideoFormatSelectorUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class DefaultHlsTrackSelector
  implements HlsTrackSelector
{
  private static final int TYPE_DEFAULT = 0;
  private static final int TYPE_VTT = 1;
  private final Context context;
  private final int type;
  
  private DefaultHlsTrackSelector(Context paramContext, int paramInt)
  {
    this.context = paramContext;
    this.type = paramInt;
  }
  
  public static DefaultHlsTrackSelector newDefaultInstance(Context paramContext)
  {
    return new DefaultHlsTrackSelector(paramContext, 0);
  }
  
  public static DefaultHlsTrackSelector newVttInstance()
  {
    return new DefaultHlsTrackSelector(null, 1);
  }
  
  private static boolean variantHasExplicitCodecWithPrefix(Variant paramVariant, String paramString)
  {
    paramVariant = paramVariant.format.codecs;
    if (TextUtils.isEmpty(paramVariant)) {}
    for (;;)
    {
      return false;
      paramVariant = paramVariant.split("(\\s*,\\s*)|(\\s*$)");
      int i = 0;
      while (i < paramVariant.length)
      {
        if (paramVariant[i].startsWith(paramString)) {
          return true;
        }
        i += 1;
      }
    }
  }
  
  public void selectTracks(HlsMasterPlaylist paramHlsMasterPlaylist, HlsTrackSelector.Output paramOutput)
    throws IOException
  {
    Object localObject1;
    int i;
    if (this.type == 1)
    {
      localObject1 = paramHlsMasterPlaylist.subtitles;
      if ((localObject1 != null) && (!((List)localObject1).isEmpty()))
      {
        i = 0;
        while (i < ((List)localObject1).size())
        {
          paramOutput.fixedTrack(paramHlsMasterPlaylist, (Variant)((List)localObject1).get(i));
          i += 1;
        }
      }
    }
    else
    {
      Object localObject2 = new ArrayList();
      localObject1 = VideoFormatSelectorUtil.selectVideoFormatsForDefaultDisplay(this.context, paramHlsMasterPlaylist.variants, null, false);
      i = 0;
      while (i < localObject1.length)
      {
        ((ArrayList)localObject2).add(paramHlsMasterPlaylist.variants.get(localObject1[i]));
        i += 1;
      }
      localObject1 = new ArrayList();
      ArrayList localArrayList = new ArrayList();
      i = 0;
      if (i < ((ArrayList)localObject2).size())
      {
        Variant localVariant = (Variant)((ArrayList)localObject2).get(i);
        if ((localVariant.format.height > 0) || (variantHasExplicitCodecWithPrefix(localVariant, "avc"))) {
          ((ArrayList)localObject1).add(localVariant);
        }
        for (;;)
        {
          i += 1;
          break;
          if (variantHasExplicitCodecWithPrefix(localVariant, "mp4a")) {
            localArrayList.add(localVariant);
          }
        }
      }
      if (!((ArrayList)localObject1).isEmpty()) {}
      for (;;)
      {
        if (((ArrayList)localObject1).size() > 1)
        {
          localObject2 = new Variant[((ArrayList)localObject1).size()];
          ((ArrayList)localObject1).toArray((Object[])localObject2);
          paramOutput.adaptiveTrack(paramHlsMasterPlaylist, (Variant[])localObject2);
        }
        i = 0;
        while (i < ((ArrayList)localObject1).size())
        {
          paramOutput.fixedTrack(paramHlsMasterPlaylist, (Variant)((ArrayList)localObject1).get(i));
          i += 1;
        }
        localObject1 = localObject2;
        if (localArrayList.size() < ((ArrayList)localObject2).size())
        {
          ((ArrayList)localObject2).removeAll(localArrayList);
          localObject1 = localObject2;
        }
      }
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/hls/DefaultHlsTrackSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */