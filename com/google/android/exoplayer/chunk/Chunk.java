package com.google.android.exoplayer.chunk;

import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DataSpec;
import com.google.android.exoplayer.upstream.Loader.Loadable;
import com.google.android.exoplayer.util.Assertions;

public abstract class Chunk
  implements Loader.Loadable
{
  public static final int NO_PARENT_ID = -1;
  public static final int TRIGGER_ADAPTIVE = 3;
  public static final int TRIGGER_CUSTOM_BASE = 10000;
  public static final int TRIGGER_INITIAL = 1;
  public static final int TRIGGER_MANUAL = 2;
  public static final int TRIGGER_TRICK_PLAY = 4;
  public static final int TRIGGER_UNSPECIFIED = 0;
  public static final int TYPE_CUSTOM_BASE = 10000;
  public static final int TYPE_DRM = 3;
  public static final int TYPE_MANIFEST = 4;
  public static final int TYPE_MEDIA = 1;
  public static final int TYPE_MEDIA_INITIALIZATION = 2;
  public static final int TYPE_UNSPECIFIED = 0;
  protected final DataSource dataSource;
  public final DataSpec dataSpec;
  public final Format format;
  public final int parentId;
  public final int trigger;
  public final int type;
  
  public Chunk(DataSource paramDataSource, DataSpec paramDataSpec, int paramInt1, int paramInt2, Format paramFormat, int paramInt3)
  {
    this.dataSource = ((DataSource)Assertions.checkNotNull(paramDataSource));
    this.dataSpec = ((DataSpec)Assertions.checkNotNull(paramDataSpec));
    this.type = paramInt1;
    this.trigger = paramInt2;
    this.format = paramFormat;
    this.parentId = paramInt3;
  }
  
  public abstract long bytesLoaded();
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/chunk/Chunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */