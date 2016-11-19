package com.upsight.android.analytics.internal.dispatcher.delivery;

import com.upsight.android.analytics.internal.dispatcher.routing.Packet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class Batch
{
  private int mCapacityLeft;
  private Set<Packet> mPackets;
  
  public Batch(int paramInt)
  {
    this.mCapacityLeft = paramInt;
    this.mPackets = new HashSet();
  }
  
  public void addPacket(Packet paramPacket)
  {
    this.mPackets.add(paramPacket);
    this.mCapacityLeft -= 1;
  }
  
  public int capacityLeft()
  {
    return this.mCapacityLeft;
  }
  
  public Set<Packet> getPackets()
  {
    return Collections.unmodifiableSet(this.mPackets);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/android/analytics/internal/dispatcher/delivery/Batch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */