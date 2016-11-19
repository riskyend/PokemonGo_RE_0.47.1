package com.google.android.exoplayer.upstream;

import android.net.Uri;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

public final class UdpDataSource
  implements UriDataSource
{
  public static final int DEFAULT_MAX_PACKET_SIZE = 2000;
  private InetAddress address;
  private DataSpec dataSpec;
  private final TransferListener listener;
  private MulticastSocket multicastSocket;
  private boolean opened;
  private final DatagramPacket packet;
  private byte[] packetBuffer;
  private int packetRemaining;
  private DatagramSocket socket;
  private InetSocketAddress socketAddress;
  
  public UdpDataSource(TransferListener paramTransferListener)
  {
    this(paramTransferListener, 2000);
  }
  
  public UdpDataSource(TransferListener paramTransferListener, int paramInt)
  {
    this.listener = paramTransferListener;
    this.packetBuffer = new byte[paramInt];
    this.packet = new DatagramPacket(this.packetBuffer, 0, paramInt);
  }
  
  public void close()
  {
    if (this.multicastSocket != null) {}
    try
    {
      this.multicastSocket.leaveGroup(this.address);
      this.multicastSocket = null;
      if (this.socket != null)
      {
        this.socket.close();
        this.socket = null;
      }
      this.address = null;
      this.socketAddress = null;
      this.packetRemaining = 0;
      if (this.opened)
      {
        this.opened = false;
        if (this.listener != null) {
          this.listener.onTransferEnd();
        }
      }
      return;
    }
    catch (IOException localIOException)
    {
      for (;;) {}
    }
  }
  
  public String getUri()
  {
    if (this.dataSpec == null) {
      return null;
    }
    return this.dataSpec.uri.toString();
  }
  
  /* Error */
  public long open(DataSpec paramDataSpec)
    throws UdpDataSource.UdpDataSourceException
  {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: putfield 85	com/google/android/exoplayer/upstream/UdpDataSource:dataSpec	Lcom/google/android/exoplayer/upstream/DataSpec;
    //   5: aload_1
    //   6: getfield 91	com/google/android/exoplayer/upstream/DataSpec:uri	Landroid/net/Uri;
    //   9: invokevirtual 96	android/net/Uri:toString	()Ljava/lang/String;
    //   12: astore_1
    //   13: aload_1
    //   14: iconst_0
    //   15: aload_1
    //   16: bipush 58
    //   18: invokevirtual 104	java/lang/String:indexOf	(I)I
    //   21: invokevirtual 108	java/lang/String:substring	(II)Ljava/lang/String;
    //   24: astore_3
    //   25: aload_1
    //   26: aload_1
    //   27: bipush 58
    //   29: invokevirtual 104	java/lang/String:indexOf	(I)I
    //   32: iconst_1
    //   33: iadd
    //   34: invokevirtual 111	java/lang/String:substring	(I)Ljava/lang/String;
    //   37: invokestatic 117	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   40: istore_2
    //   41: aload_0
    //   42: aload_3
    //   43: invokestatic 123	java/net/InetAddress:getByName	(Ljava/lang/String;)Ljava/net/InetAddress;
    //   46: putfield 58	com/google/android/exoplayer/upstream/UdpDataSource:address	Ljava/net/InetAddress;
    //   49: aload_0
    //   50: new 125	java/net/InetSocketAddress
    //   53: dup
    //   54: aload_0
    //   55: getfield 58	com/google/android/exoplayer/upstream/UdpDataSource:address	Ljava/net/InetAddress;
    //   58: iload_2
    //   59: invokespecial 128	java/net/InetSocketAddress:<init>	(Ljava/net/InetAddress;I)V
    //   62: putfield 72	com/google/android/exoplayer/upstream/UdpDataSource:socketAddress	Ljava/net/InetSocketAddress;
    //   65: aload_0
    //   66: getfield 58	com/google/android/exoplayer/upstream/UdpDataSource:address	Ljava/net/InetAddress;
    //   69: invokevirtual 132	java/net/InetAddress:isMulticastAddress	()Z
    //   72: ifeq +62 -> 134
    //   75: aload_0
    //   76: new 60	java/net/MulticastSocket
    //   79: dup
    //   80: aload_0
    //   81: getfield 72	com/google/android/exoplayer/upstream/UdpDataSource:socketAddress	Ljava/net/InetSocketAddress;
    //   84: invokespecial 135	java/net/MulticastSocket:<init>	(Ljava/net/SocketAddress;)V
    //   87: putfield 56	com/google/android/exoplayer/upstream/UdpDataSource:multicastSocket	Ljava/net/MulticastSocket;
    //   90: aload_0
    //   91: getfield 56	com/google/android/exoplayer/upstream/UdpDataSource:multicastSocket	Ljava/net/MulticastSocket;
    //   94: aload_0
    //   95: getfield 58	com/google/android/exoplayer/upstream/UdpDataSource:address	Ljava/net/InetAddress;
    //   98: invokevirtual 138	java/net/MulticastSocket:joinGroup	(Ljava/net/InetAddress;)V
    //   101: aload_0
    //   102: aload_0
    //   103: getfield 56	com/google/android/exoplayer/upstream/UdpDataSource:multicastSocket	Ljava/net/MulticastSocket;
    //   106: putfield 66	com/google/android/exoplayer/upstream/UdpDataSource:socket	Ljava/net/DatagramSocket;
    //   109: aload_0
    //   110: iconst_1
    //   111: putfield 76	com/google/android/exoplayer/upstream/UdpDataSource:opened	Z
    //   114: aload_0
    //   115: getfield 42	com/google/android/exoplayer/upstream/UdpDataSource:listener	Lcom/google/android/exoplayer/upstream/TransferListener;
    //   118: ifnull +12 -> 130
    //   121: aload_0
    //   122: getfield 42	com/google/android/exoplayer/upstream/UdpDataSource:listener	Lcom/google/android/exoplayer/upstream/TransferListener;
    //   125: invokeinterface 141 1 0
    //   130: ldc2_w 142
    //   133: lreturn
    //   134: aload_0
    //   135: new 68	java/net/DatagramSocket
    //   138: dup
    //   139: aload_0
    //   140: getfield 72	com/google/android/exoplayer/upstream/UdpDataSource:socketAddress	Ljava/net/InetSocketAddress;
    //   143: invokespecial 144	java/net/DatagramSocket:<init>	(Ljava/net/SocketAddress;)V
    //   146: putfield 66	com/google/android/exoplayer/upstream/UdpDataSource:socket	Ljava/net/DatagramSocket;
    //   149: goto -40 -> 109
    //   152: astore_1
    //   153: new 8	com/google/android/exoplayer/upstream/UdpDataSource$UdpDataSourceException
    //   156: dup
    //   157: aload_1
    //   158: invokespecial 147	com/google/android/exoplayer/upstream/UdpDataSource$UdpDataSourceException:<init>	(Ljava/io/IOException;)V
    //   161: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	162	0	this	UdpDataSource
    //   0	162	1	paramDataSpec	DataSpec
    //   40	19	2	i	int
    //   24	19	3	str	String
    // Exception table:
    //   from	to	target	type
    //   41	109	152	java/io/IOException
    //   134	149	152	java/io/IOException
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws UdpDataSource.UdpDataSourceException
  {
    if (this.packetRemaining == 0) {}
    try
    {
      this.socket.receive(this.packet);
      this.packetRemaining = this.packet.getLength();
      if (this.listener != null) {
        this.listener.onBytesTransferred(this.packetRemaining);
      }
      int i = this.packet.getLength();
      int j = this.packetRemaining;
      paramInt2 = Math.min(this.packetRemaining, paramInt2);
      System.arraycopy(this.packetBuffer, i - j, paramArrayOfByte, paramInt1, paramInt2);
      this.packetRemaining -= paramInt2;
      return paramInt2;
    }
    catch (IOException paramArrayOfByte)
    {
      throw new UdpDataSourceException(paramArrayOfByte);
    }
  }
  
  public static final class UdpDataSourceException
    extends IOException
  {
    public UdpDataSourceException(IOException paramIOException)
    {
      super();
    }
    
    public UdpDataSourceException(String paramString)
    {
      super();
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/upstream/UdpDataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */