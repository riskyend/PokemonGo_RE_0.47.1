package com.google.android.exoplayer.text;

import com.google.android.exoplayer.ParserException;

public abstract interface SubtitleParser
{
  public abstract boolean canParse(String paramString);
  
  public abstract Subtitle parse(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws ParserException;
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/text/SubtitleParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */