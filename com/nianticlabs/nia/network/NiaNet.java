package com.nianticlabs.nia.network;

import java.nio.ByteBuffer;

public class NiaNet
{
  private static final int CHUNK_SIZE = 32768;
  static ThreadLocal<ByteBuffer> readBuffer = new ThreadLocal()
  {
    protected ByteBuffer initialValue()
    {
      return ByteBuffer.allocateDirect(32768);
    }
  };
  private static final ThreadLocal<byte[]> threadChunk = new ThreadLocal()
  {
    protected byte[] initialValue()
    {
      return new byte[32768];
    }
  };
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/nianticlabs/nia/network/NiaNet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */