package com.google.android.exoplayer;

import android.annotation.TargetApi;
import android.media.MediaCodecInfo;
import android.media.MediaCodecInfo.CodecCapabilities;
import android.media.MediaCodecInfo.CodecProfileLevel;
import android.media.MediaCodecInfo.VideoCapabilities;
import android.media.MediaCodecList;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.google.android.exoplayer.util.Assertions;
import com.google.android.exoplayer.util.Util;
import java.io.IOException;
import java.util.HashMap;

@TargetApi(16)
public final class MediaCodecUtil
{
  private static final String TAG = "MediaCodecUtil";
  private static final HashMap<CodecKey, Pair<String, MediaCodecInfo.CodecCapabilities>> codecs = new HashMap();
  
  private static int avcLevelToMaxFrameSize(int paramInt)
  {
    int i = 25344;
    switch (paramInt)
    {
    default: 
      i = -1;
    case 1: 
    case 2: 
      return i;
    case 8: 
      return 101376;
    case 16: 
      return 101376;
    case 32: 
      return 101376;
    case 64: 
      return 202752;
    case 128: 
      return 414720;
    case 256: 
      return 414720;
    case 512: 
      return 921600;
    case 1024: 
      return 1310720;
    case 2048: 
      return 2097152;
    case 4096: 
      return 2097152;
    case 8192: 
      return 2228224;
    case 16384: 
      return 5652480;
    }
    return 9437184;
  }
  
  public static DecoderInfo getDecoderInfo(String paramString, boolean paramBoolean)
    throws MediaCodecUtil.DecoderQueryException
  {
    paramString = getMediaCodecInfo(paramString, paramBoolean);
    if (paramString == null) {
      return null;
    }
    return new DecoderInfo((String)paramString.first, isAdaptive((MediaCodecInfo.CodecCapabilities)paramString.second));
  }
  
  private static Pair<String, MediaCodecInfo.CodecCapabilities> getMediaCodecInfo(CodecKey paramCodecKey, MediaCodecListCompat paramMediaCodecListCompat)
    throws MediaCodecUtil.DecoderQueryException
  {
    try
    {
      paramCodecKey = getMediaCodecInfoInternal(paramCodecKey, paramMediaCodecListCompat);
      return paramCodecKey;
    }
    catch (Exception paramCodecKey)
    {
      throw new DecoderQueryException(paramCodecKey, null);
    }
  }
  
  public static Pair<String, MediaCodecInfo.CodecCapabilities> getMediaCodecInfo(String paramString, boolean paramBoolean)
    throws MediaCodecUtil.DecoderQueryException
  {
    for (;;)
    {
      try
      {
        CodecKey localCodecKey = new CodecKey(paramString, paramBoolean);
        if (codecs.containsKey(localCodecKey))
        {
          paramString = (Pair)codecs.get(localCodecKey);
          return paramString;
        }
        Object localObject;
        if (Util.SDK_INT >= 21)
        {
          localObject = new MediaCodecListCompatV21(paramBoolean);
          Pair localPair = getMediaCodecInfo(localCodecKey, (MediaCodecListCompat)localObject);
          localObject = localPair;
          if (paramBoolean)
          {
            localObject = localPair;
            if (localPair == null)
            {
              localObject = localPair;
              if (21 <= Util.SDK_INT)
              {
                localObject = localPair;
                if (Util.SDK_INT <= 23)
                {
                  localPair = getMediaCodecInfo(localCodecKey, new MediaCodecListCompatV16(null));
                  localObject = localPair;
                  if (localPair != null)
                  {
                    Log.w("MediaCodecUtil", "MediaCodecList API didn't list secure decoder for: " + paramString + ". Assuming: " + (String)localPair.first);
                    localObject = localPair;
                  }
                }
              }
            }
          }
        }
        else
        {
          localObject = new MediaCodecListCompatV16(null);
          continue;
        }
        paramString = (String)localObject;
      }
      finally {}
    }
  }
  
  private static Pair<String, MediaCodecInfo.CodecCapabilities> getMediaCodecInfoInternal(CodecKey paramCodecKey, MediaCodecListCompat paramMediaCodecListCompat)
  {
    String str1 = paramCodecKey.mimeType;
    int k = paramMediaCodecListCompat.getCodecCount();
    boolean bool1 = paramMediaCodecListCompat.secureDecodersExplicit();
    int i = 0;
    while (i < k)
    {
      MediaCodecInfo localMediaCodecInfo = paramMediaCodecListCompat.getCodecInfoAt(i);
      String str2 = localMediaCodecInfo.getName();
      if (isCodecUsableDecoder(localMediaCodecInfo, str2, bool1))
      {
        String[] arrayOfString = localMediaCodecInfo.getSupportedTypes();
        int j = 0;
        while (j < arrayOfString.length)
        {
          Object localObject = arrayOfString[j];
          if (((String)localObject).equalsIgnoreCase(str1))
          {
            MediaCodecInfo.CodecCapabilities localCodecCapabilities = localMediaCodecInfo.getCapabilitiesForType((String)localObject);
            boolean bool2 = paramMediaCodecListCompat.isSecurePlaybackSupported(paramCodecKey.mimeType, localCodecCapabilities);
            if (!bool1)
            {
              localHashMap = codecs;
              if (paramCodecKey.secure)
              {
                localObject = new CodecKey(str1, false);
                localHashMap.put(localObject, Pair.create(str2, localCodecCapabilities));
                if (bool2)
                {
                  localHashMap = codecs;
                  if (!paramCodecKey.secure) {
                    break label237;
                  }
                }
              }
              label237:
              for (localObject = paramCodecKey;; localObject = new CodecKey(str1, true))
              {
                localHashMap.put(localObject, Pair.create(str2 + ".secure", localCodecCapabilities));
                if (!codecs.containsKey(paramCodecKey)) {
                  break label303;
                }
                return (Pair)codecs.get(paramCodecKey);
                localObject = paramCodecKey;
                break;
              }
            }
            HashMap localHashMap = codecs;
            if (paramCodecKey.secure == bool2) {}
            for (localObject = paramCodecKey;; localObject = new CodecKey(str1, bool2))
            {
              localHashMap.put(localObject, Pair.create(str2, localCodecCapabilities));
              break;
            }
          }
          label303:
          j += 1;
        }
      }
      i += 1;
    }
    return null;
  }
  
  @TargetApi(21)
  private static MediaCodecInfo.VideoCapabilities getVideoCapabilitiesV21(String paramString, boolean paramBoolean)
    throws MediaCodecUtil.DecoderQueryException
  {
    paramString = getMediaCodecInfo(paramString, paramBoolean);
    if (paramString == null) {
      return null;
    }
    return ((MediaCodecInfo.CodecCapabilities)paramString.second).getVideoCapabilities();
  }
  
  private static boolean isAdaptive(MediaCodecInfo.CodecCapabilities paramCodecCapabilities)
  {
    if (Util.SDK_INT >= 19) {
      return isAdaptiveV19(paramCodecCapabilities);
    }
    return false;
  }
  
  @TargetApi(19)
  private static boolean isAdaptiveV19(MediaCodecInfo.CodecCapabilities paramCodecCapabilities)
  {
    return paramCodecCapabilities.isFeatureSupported("adaptive-playback");
  }
  
  private static boolean isCodecUsableDecoder(MediaCodecInfo paramMediaCodecInfo, String paramString, boolean paramBoolean)
  {
    if ((paramMediaCodecInfo.isEncoder()) || ((!paramBoolean) && (paramString.endsWith(".secure")))) {}
    while (((Util.SDK_INT < 21) && ("CIPAACDecoder".equals(paramString))) || ("CIPMP3Decoder".equals(paramString)) || ("CIPVorbisDecoder".equals(paramString)) || ("AACDecoder".equals(paramString)) || ("MP3Decoder".equals(paramString)) || ((Util.SDK_INT == 16) && ("OMX.SEC.MP3.Decoder".equals(paramString))) || ((Util.SDK_INT == 16) && ("OMX.qcom.audio.decoder.mp3".equals(paramString)) && (("dlxu".equals(Util.DEVICE)) || ("protou".equals(Util.DEVICE)) || ("C6602".equals(Util.DEVICE)) || ("C6603".equals(Util.DEVICE)) || ("C6606".equals(Util.DEVICE)) || ("C6616".equals(Util.DEVICE)) || ("L36h".equals(Util.DEVICE)) || ("SO-02E".equals(Util.DEVICE)))) || ((Util.SDK_INT == 16) && ("OMX.qcom.audio.decoder.aac".equals(paramString)) && (("C1504".equals(Util.DEVICE)) || ("C1505".equals(Util.DEVICE)) || ("C1604".equals(Util.DEVICE)) || ("C1605".equals(Util.DEVICE)))) || ((Util.SDK_INT <= 19) && (Util.DEVICE != null) && ((Util.DEVICE.startsWith("d2")) || (Util.DEVICE.startsWith("serrano"))) && ("samsung".equals(Util.MANUFACTURER)) && (paramString.equals("OMX.SEC.vp8.dec")))) {
      return false;
    }
    return true;
  }
  
  public static boolean isH264ProfileSupported(int paramInt1, int paramInt2)
    throws MediaCodecUtil.DecoderQueryException
  {
    Object localObject = getMediaCodecInfo("video/avc", false);
    if (localObject == null) {}
    for (;;)
    {
      return false;
      localObject = (MediaCodecInfo.CodecCapabilities)((Pair)localObject).second;
      int i = 0;
      while (i < ((MediaCodecInfo.CodecCapabilities)localObject).profileLevels.length)
      {
        MediaCodecInfo.CodecProfileLevel localCodecProfileLevel = localObject.profileLevels[i];
        if ((localCodecProfileLevel.profile == paramInt1) && (localCodecProfileLevel.level >= paramInt2)) {
          return true;
        }
        i += 1;
      }
    }
  }
  
  @TargetApi(21)
  public static boolean isSizeAndRateSupportedV21(String paramString, boolean paramBoolean, int paramInt1, int paramInt2, double paramDouble)
    throws MediaCodecUtil.DecoderQueryException
  {
    if (Util.SDK_INT >= 21) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkState(bool);
      paramString = getVideoCapabilitiesV21(paramString, paramBoolean);
      if ((paramString == null) || (!paramString.areSizeAndRateSupported(paramInt1, paramInt2, paramDouble))) {
        break;
      }
      return true;
    }
    return false;
  }
  
  @TargetApi(21)
  public static boolean isSizeSupportedV21(String paramString, boolean paramBoolean, int paramInt1, int paramInt2)
    throws MediaCodecUtil.DecoderQueryException
  {
    if (Util.SDK_INT >= 21) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkState(bool);
      paramString = getVideoCapabilitiesV21(paramString, paramBoolean);
      if ((paramString == null) || (!paramString.isSizeSupported(paramInt1, paramInt2))) {
        break;
      }
      return true;
    }
    return false;
  }
  
  public static int maxH264DecodableFrameSize()
    throws MediaCodecUtil.DecoderQueryException
  {
    int k = 0;
    Object localObject = getMediaCodecInfo("video/avc", false);
    if (localObject == null) {
      return k;
    }
    int i = 0;
    localObject = (MediaCodecInfo.CodecCapabilities)((Pair)localObject).second;
    int j = 0;
    for (;;)
    {
      k = i;
      if (j >= ((MediaCodecInfo.CodecCapabilities)localObject).profileLevels.length) {
        break;
      }
      i = Math.max(avcLevelToMaxFrameSize(localObject.profileLevels[j].level), i);
      j += 1;
    }
  }
  
  public static void warmCodec(String paramString, boolean paramBoolean)
  {
    try
    {
      getMediaCodecInfo(paramString, paramBoolean);
      return;
    }
    catch (DecoderQueryException paramString)
    {
      for (;;)
      {
        Log.e("MediaCodecUtil", "Codec warming failed", paramString);
      }
    }
    finally {}
  }
  
  private static final class CodecKey
  {
    public final String mimeType;
    public final boolean secure;
    
    public CodecKey(String paramString, boolean paramBoolean)
    {
      this.mimeType = paramString;
      this.secure = paramBoolean;
    }
    
    public boolean equals(Object paramObject)
    {
      if (this == paramObject) {}
      do
      {
        return true;
        if ((paramObject == null) || (paramObject.getClass() != CodecKey.class)) {
          return false;
        }
        paramObject = (CodecKey)paramObject;
      } while ((TextUtils.equals(this.mimeType, ((CodecKey)paramObject).mimeType)) && (this.secure == ((CodecKey)paramObject).secure));
      return false;
    }
    
    public int hashCode()
    {
      int i;
      if (this.mimeType == null)
      {
        i = 0;
        if (!this.secure) {
          break label41;
        }
      }
      label41:
      for (int j = 1231;; j = 1237)
      {
        return (i + 31) * 31 + j;
        i = this.mimeType.hashCode();
        break;
      }
    }
  }
  
  public static class DecoderQueryException
    extends IOException
  {
    private DecoderQueryException(Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
  
  private static abstract interface MediaCodecListCompat
  {
    public abstract int getCodecCount();
    
    public abstract MediaCodecInfo getCodecInfoAt(int paramInt);
    
    public abstract boolean isSecurePlaybackSupported(String paramString, MediaCodecInfo.CodecCapabilities paramCodecCapabilities);
    
    public abstract boolean secureDecodersExplicit();
  }
  
  private static final class MediaCodecListCompatV16
    implements MediaCodecUtil.MediaCodecListCompat
  {
    public int getCodecCount()
    {
      return MediaCodecList.getCodecCount();
    }
    
    public MediaCodecInfo getCodecInfoAt(int paramInt)
    {
      return MediaCodecList.getCodecInfoAt(paramInt);
    }
    
    public boolean isSecurePlaybackSupported(String paramString, MediaCodecInfo.CodecCapabilities paramCodecCapabilities)
    {
      return "video/avc".equals(paramString);
    }
    
    public boolean secureDecodersExplicit()
    {
      return false;
    }
  }
  
  @TargetApi(21)
  private static final class MediaCodecListCompatV21
    implements MediaCodecUtil.MediaCodecListCompat
  {
    private final int codecKind;
    private MediaCodecInfo[] mediaCodecInfos;
    
    public MediaCodecListCompatV21(boolean paramBoolean)
    {
      if (paramBoolean) {}
      for (int i = 1;; i = 0)
      {
        this.codecKind = i;
        return;
      }
    }
    
    private void ensureMediaCodecInfosInitialized()
    {
      if (this.mediaCodecInfos == null) {
        this.mediaCodecInfos = new MediaCodecList(this.codecKind).getCodecInfos();
      }
    }
    
    public int getCodecCount()
    {
      ensureMediaCodecInfosInitialized();
      return this.mediaCodecInfos.length;
    }
    
    public MediaCodecInfo getCodecInfoAt(int paramInt)
    {
      ensureMediaCodecInfosInitialized();
      return this.mediaCodecInfos[paramInt];
    }
    
    public boolean isSecurePlaybackSupported(String paramString, MediaCodecInfo.CodecCapabilities paramCodecCapabilities)
    {
      return paramCodecCapabilities.isFeatureSupported("secure-playback");
    }
    
    public boolean secureDecodersExplicit()
    {
      return true;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/MediaCodecUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */