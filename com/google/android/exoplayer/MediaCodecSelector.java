package com.google.android.exoplayer;

public abstract interface MediaCodecSelector
{
  public static final MediaCodecSelector DEFAULT = new MediaCodecSelector()
  {
    private static final String RAW_DECODER_NAME = "OMX.google.raw.decoder";
    
    public DecoderInfo getDecoderInfo(String paramAnonymousString, boolean paramAnonymousBoolean)
      throws MediaCodecUtil.DecoderQueryException
    {
      return MediaCodecUtil.getDecoderInfo(paramAnonymousString, paramAnonymousBoolean);
    }
    
    public String getPassthroughDecoderName()
      throws MediaCodecUtil.DecoderQueryException
    {
      return "OMX.google.raw.decoder";
    }
  };
  
  public abstract DecoderInfo getDecoderInfo(String paramString, boolean paramBoolean)
    throws MediaCodecUtil.DecoderQueryException;
  
  public abstract String getPassthroughDecoderName()
    throws MediaCodecUtil.DecoderQueryException;
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/MediaCodecSelector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */