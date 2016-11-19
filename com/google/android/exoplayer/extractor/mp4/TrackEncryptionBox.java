package com.google.android.exoplayer.extractor.mp4;

public final class TrackEncryptionBox
{
  public final int initializationVectorSize;
  public final boolean isEncrypted;
  public final byte[] keyId;
  
  public TrackEncryptionBox(boolean paramBoolean, int paramInt, byte[] paramArrayOfByte)
  {
    this.isEncrypted = paramBoolean;
    this.initializationVectorSize = paramInt;
    this.keyId = paramArrayOfByte;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/extractor/mp4/TrackEncryptionBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */