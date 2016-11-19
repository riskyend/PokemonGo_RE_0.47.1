package crittercism.android;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.Queue;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

public final class aa
  extends SSLSocket
  implements ae
{
  private SSLSocket a;
  private e b;
  private d c;
  private final Queue d = new LinkedList();
  private w e;
  private x f;
  
  public aa(SSLSocket paramSSLSocket, e parame, d paramd)
  {
    if (paramSSLSocket == null) {
      throw new NullPointerException("delegate was null");
    }
    if (parame == null) {
      throw new NullPointerException("dispatch was null");
    }
    this.a = paramSSLSocket;
    this.b = parame;
    this.c = paramd;
  }
  
  private c a(boolean paramBoolean)
  {
    c localc = new c();
    InetAddress localInetAddress = this.a.getInetAddress();
    if (localInetAddress != null) {
      localc.a(localInetAddress);
    }
    if (paramBoolean) {
      localc.a(getPort());
    }
    localc.a(k.a.b);
    if (this.c != null) {
      localc.j = this.c.a();
    }
    if (bc.b()) {
      localc.a(bc.a());
    }
    return localc;
  }
  
  public final c a()
  {
    return a(false);
  }
  
  public final void a(c paramc)
  {
    if (paramc != null) {}
    synchronized (this.d)
    {
      this.d.add(paramc);
      return;
    }
  }
  
  public final void addHandshakeCompletedListener(HandshakeCompletedListener paramHandshakeCompletedListener)
  {
    this.a.addHandshakeCompletedListener(paramHandshakeCompletedListener);
  }
  
  public final c b()
  {
    synchronized (this.d)
    {
      c localc = (c)this.d.poll();
      return localc;
    }
  }
  
  public final void bind(SocketAddress paramSocketAddress)
  {
    this.a.bind(paramSocketAddress);
  }
  
  public final void close()
  {
    this.a.close();
    try
    {
      if (this.f != null) {
        this.f.d();
      }
      return;
    }
    catch (ThreadDeath localThreadDeath)
    {
      throw localThreadDeath;
    }
    catch (Throwable localThrowable)
    {
      dx.a(localThrowable);
    }
  }
  
  public final void connect(SocketAddress paramSocketAddress)
  {
    this.a.connect(paramSocketAddress);
  }
  
  public final void connect(SocketAddress paramSocketAddress, int paramInt)
  {
    this.a.connect(paramSocketAddress, paramInt);
  }
  
  public final boolean equals(Object paramObject)
  {
    return this.a.equals(paramObject);
  }
  
  public final SocketChannel getChannel()
  {
    return this.a.getChannel();
  }
  
  public final boolean getEnableSessionCreation()
  {
    return this.a.getEnableSessionCreation();
  }
  
  public final String[] getEnabledCipherSuites()
  {
    return this.a.getEnabledCipherSuites();
  }
  
  public final String[] getEnabledProtocols()
  {
    return this.a.getEnabledProtocols();
  }
  
  public final InetAddress getInetAddress()
  {
    return this.a.getInetAddress();
  }
  
  public final InputStream getInputStream()
  {
    InputStream localInputStream = this.a.getInputStream();
    if (localInputStream != null) {
      try
      {
        if ((this.f != null) && (this.f.a(localInputStream))) {
          return this.f;
        }
        this.f = new x(this, localInputStream, this.b);
        x localx = this.f;
        return localx;
      }
      catch (ThreadDeath localThreadDeath)
      {
        throw localThreadDeath;
      }
      catch (Throwable localThrowable)
      {
        dx.a(localThrowable);
      }
    }
    return localThreadDeath;
  }
  
  public final boolean getKeepAlive()
  {
    return this.a.getKeepAlive();
  }
  
  public final InetAddress getLocalAddress()
  {
    return this.a.getLocalAddress();
  }
  
  public final int getLocalPort()
  {
    return this.a.getLocalPort();
  }
  
  public final SocketAddress getLocalSocketAddress()
  {
    return this.a.getLocalSocketAddress();
  }
  
  public final boolean getNeedClientAuth()
  {
    return this.a.getNeedClientAuth();
  }
  
  public final boolean getOOBInline()
  {
    return this.a.getOOBInline();
  }
  
  public final OutputStream getOutputStream()
  {
    OutputStream localOutputStream = this.a.getOutputStream();
    if (localOutputStream != null) {
      try
      {
        if ((this.e != null) && (this.e.a(localOutputStream))) {
          return this.e;
        }
        w localw = this.e;
        this.e = new w(this, localOutputStream);
        localw = this.e;
        return localw;
      }
      catch (ThreadDeath localThreadDeath)
      {
        throw localThreadDeath;
      }
      catch (Throwable localThrowable)
      {
        dx.a(localThrowable);
      }
    }
    return localThreadDeath;
  }
  
  public final int getPort()
  {
    return this.a.getPort();
  }
  
  public final int getReceiveBufferSize()
  {
    return this.a.getReceiveBufferSize();
  }
  
  public final SocketAddress getRemoteSocketAddress()
  {
    return this.a.getRemoteSocketAddress();
  }
  
  public final boolean getReuseAddress()
  {
    return this.a.getReuseAddress();
  }
  
  public final int getSendBufferSize()
  {
    return this.a.getSendBufferSize();
  }
  
  public final SSLSession getSession()
  {
    return this.a.getSession();
  }
  
  public final int getSoLinger()
  {
    return this.a.getSoLinger();
  }
  
  public final int getSoTimeout()
  {
    return this.a.getSoTimeout();
  }
  
  public final String[] getSupportedCipherSuites()
  {
    return this.a.getSupportedCipherSuites();
  }
  
  public final String[] getSupportedProtocols()
  {
    return this.a.getSupportedProtocols();
  }
  
  public final boolean getTcpNoDelay()
  {
    return this.a.getTcpNoDelay();
  }
  
  public final int getTrafficClass()
  {
    return this.a.getTrafficClass();
  }
  
  public final boolean getUseClientMode()
  {
    return this.a.getUseClientMode();
  }
  
  public final boolean getWantClientAuth()
  {
    return this.a.getWantClientAuth();
  }
  
  public final int hashCode()
  {
    return this.a.hashCode();
  }
  
  public final boolean isBound()
  {
    return this.a.isBound();
  }
  
  public final boolean isClosed()
  {
    return this.a.isClosed();
  }
  
  public final boolean isConnected()
  {
    return this.a.isConnected();
  }
  
  public final boolean isInputShutdown()
  {
    return this.a.isInputShutdown();
  }
  
  public final boolean isOutputShutdown()
  {
    return this.a.isOutputShutdown();
  }
  
  public final void removeHandshakeCompletedListener(HandshakeCompletedListener paramHandshakeCompletedListener)
  {
    this.a.removeHandshakeCompletedListener(paramHandshakeCompletedListener);
  }
  
  public final void sendUrgentData(int paramInt)
  {
    this.a.sendUrgentData(paramInt);
  }
  
  public final void setEnableSessionCreation(boolean paramBoolean)
  {
    this.a.setEnableSessionCreation(paramBoolean);
  }
  
  public final void setEnabledCipherSuites(String[] paramArrayOfString)
  {
    this.a.setEnabledCipherSuites(paramArrayOfString);
  }
  
  public final void setEnabledProtocols(String[] paramArrayOfString)
  {
    this.a.setEnabledProtocols(paramArrayOfString);
  }
  
  public final void setKeepAlive(boolean paramBoolean)
  {
    this.a.setKeepAlive(paramBoolean);
  }
  
  public final void setNeedClientAuth(boolean paramBoolean)
  {
    this.a.setNeedClientAuth(paramBoolean);
  }
  
  public final void setOOBInline(boolean paramBoolean)
  {
    this.a.setOOBInline(paramBoolean);
  }
  
  public final void setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3)
  {
    this.a.setPerformancePreferences(paramInt1, paramInt2, paramInt3);
  }
  
  public final void setReceiveBufferSize(int paramInt)
  {
    this.a.setReceiveBufferSize(paramInt);
  }
  
  public final void setReuseAddress(boolean paramBoolean)
  {
    this.a.setReuseAddress(paramBoolean);
  }
  
  public final void setSendBufferSize(int paramInt)
  {
    this.a.setSendBufferSize(paramInt);
  }
  
  public final void setSoLinger(boolean paramBoolean, int paramInt)
  {
    this.a.setSoLinger(paramBoolean, paramInt);
  }
  
  public final void setSoTimeout(int paramInt)
  {
    this.a.setSoTimeout(paramInt);
  }
  
  public final void setTcpNoDelay(boolean paramBoolean)
  {
    this.a.setTcpNoDelay(paramBoolean);
  }
  
  public final void setTrafficClass(int paramInt)
  {
    this.a.setTrafficClass(paramInt);
  }
  
  public final void setUseClientMode(boolean paramBoolean)
  {
    this.a.setUseClientMode(paramBoolean);
  }
  
  public final void setWantClientAuth(boolean paramBoolean)
  {
    this.a.setWantClientAuth(paramBoolean);
  }
  
  public final void shutdownInput()
  {
    this.a.shutdownInput();
  }
  
  public final void shutdownOutput()
  {
    this.a.shutdownOutput();
  }
  
  public final void startHandshake()
  {
    try
    {
      this.a.startHandshake();
      return;
    }
    catch (IOException localIOException) {}
    try
    {
      c localc = a(true);
      localc.b();
      localc.c();
      localc.f();
      localc.a(localIOException);
      this.b.a(localc, c.a.j);
      throw localIOException;
    }
    catch (ThreadDeath localThreadDeath)
    {
      throw localThreadDeath;
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        dx.a(localThrowable);
      }
    }
  }
  
  public final String toString()
  {
    return this.a.toString();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/crittercism/android/aa.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */