package com.mopub.volley.toolbox;

import android.os.SystemClock;
import com.mopub.volley.Cache;
import com.mopub.volley.Cache.Entry;
import com.mopub.volley.VolleyLog;
import java.io.EOFException;
import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DiskBasedCache
  implements Cache
{
  private static final int CACHE_MAGIC = 538183203;
  private static final int DEFAULT_DISK_USAGE_BYTES = 5242880;
  private static final float HYSTERESIS_FACTOR = 0.9F;
  private final Map<String, CacheHeader> mEntries = new LinkedHashMap(16, 0.75F, true);
  private final int mMaxCacheSizeInBytes;
  private final File mRootDirectory;
  private long mTotalSize = 0L;
  
  public DiskBasedCache(File paramFile)
  {
    this(paramFile, 5242880);
  }
  
  public DiskBasedCache(File paramFile, int paramInt)
  {
    this.mRootDirectory = paramFile;
    this.mMaxCacheSizeInBytes = paramInt;
  }
  
  private String getFilenameForKey(String paramString)
  {
    int i = paramString.length() / 2;
    int j = paramString.substring(0, i).hashCode();
    return String.valueOf(j) + String.valueOf(paramString.substring(i).hashCode());
  }
  
  private void pruneIfNeeded(int paramInt)
  {
    if (this.mTotalSize + paramInt < this.mMaxCacheSizeInBytes) {
      return;
    }
    if (VolleyLog.DEBUG) {
      VolleyLog.v("Pruning old cache entries.", new Object[0]);
    }
    long l1 = this.mTotalSize;
    int i = 0;
    long l2 = SystemClock.elapsedRealtime();
    Iterator localIterator = this.mEntries.entrySet().iterator();
    label61:
    int j = i;
    CacheHeader localCacheHeader;
    if (localIterator.hasNext())
    {
      localCacheHeader = (CacheHeader)((Map.Entry)localIterator.next()).getValue();
      if (!getFileForKey(localCacheHeader.key).delete()) {
        break label203;
      }
      this.mTotalSize -= localCacheHeader.size;
    }
    for (;;)
    {
      localIterator.remove();
      j = i + 1;
      i = j;
      if ((float)(this.mTotalSize + paramInt) >= this.mMaxCacheSizeInBytes * 0.9F) {
        break label61;
      }
      if (!VolleyLog.DEBUG) {
        break;
      }
      VolleyLog.v("pruned %d files, %d bytes, %d ms", new Object[] { Integer.valueOf(j), Long.valueOf(this.mTotalSize - l1), Long.valueOf(SystemClock.elapsedRealtime() - l2) });
      return;
      label203:
      VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", new Object[] { localCacheHeader.key, getFilenameForKey(localCacheHeader.key) });
    }
  }
  
  private void putEntry(String paramString, CacheHeader paramCacheHeader)
  {
    if (!this.mEntries.containsKey(paramString)) {}
    CacheHeader localCacheHeader;
    for (this.mTotalSize += paramCacheHeader.size;; this.mTotalSize += paramCacheHeader.size - localCacheHeader.size)
    {
      this.mEntries.put(paramString, paramCacheHeader);
      return;
      localCacheHeader = (CacheHeader)this.mEntries.get(paramString);
    }
  }
  
  private static int read(InputStream paramInputStream)
    throws IOException
  {
    int i = paramInputStream.read();
    if (i == -1) {
      throw new EOFException();
    }
    return i;
  }
  
  static int readInt(InputStream paramInputStream)
    throws IOException
  {
    return 0x0 | read(paramInputStream) << 0 | read(paramInputStream) << 8 | read(paramInputStream) << 16 | read(paramInputStream) << 24;
  }
  
  static long readLong(InputStream paramInputStream)
    throws IOException
  {
    return 0L | (read(paramInputStream) & 0xFF) << 0 | (read(paramInputStream) & 0xFF) << 8 | (read(paramInputStream) & 0xFF) << 16 | (read(paramInputStream) & 0xFF) << 24 | (read(paramInputStream) & 0xFF) << 32 | (read(paramInputStream) & 0xFF) << 40 | (read(paramInputStream) & 0xFF) << 48 | (read(paramInputStream) & 0xFF) << 56;
  }
  
  static String readString(InputStream paramInputStream)
    throws IOException
  {
    return new String(streamToBytes(paramInputStream, (int)readLong(paramInputStream)), "UTF-8");
  }
  
  static Map<String, String> readStringStringMap(InputStream paramInputStream)
    throws IOException
  {
    int j = readInt(paramInputStream);
    if (j == 0) {}
    for (Object localObject = Collections.emptyMap();; localObject = new HashMap(j))
    {
      int i = 0;
      while (i < j)
      {
        ((Map)localObject).put(readString(paramInputStream).intern(), readString(paramInputStream).intern());
        i += 1;
      }
    }
    return (Map<String, String>)localObject;
  }
  
  private void removeEntry(String paramString)
  {
    CacheHeader localCacheHeader = (CacheHeader)this.mEntries.get(paramString);
    if (localCacheHeader != null)
    {
      this.mTotalSize -= localCacheHeader.size;
      this.mEntries.remove(paramString);
    }
  }
  
  private static byte[] streamToBytes(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    int i = 0;
    while (i < paramInt)
    {
      int j = paramInputStream.read(arrayOfByte, i, paramInt - i);
      if (j == -1) {
        break;
      }
      i += j;
    }
    if (i != paramInt) {
      throw new IOException("Expected " + paramInt + " bytes, read " + i + " bytes");
    }
    return arrayOfByte;
  }
  
  static void writeInt(OutputStream paramOutputStream, int paramInt)
    throws IOException
  {
    paramOutputStream.write(paramInt >> 0 & 0xFF);
    paramOutputStream.write(paramInt >> 8 & 0xFF);
    paramOutputStream.write(paramInt >> 16 & 0xFF);
    paramOutputStream.write(paramInt >> 24 & 0xFF);
  }
  
  static void writeLong(OutputStream paramOutputStream, long paramLong)
    throws IOException
  {
    paramOutputStream.write((byte)(int)(paramLong >>> 0));
    paramOutputStream.write((byte)(int)(paramLong >>> 8));
    paramOutputStream.write((byte)(int)(paramLong >>> 16));
    paramOutputStream.write((byte)(int)(paramLong >>> 24));
    paramOutputStream.write((byte)(int)(paramLong >>> 32));
    paramOutputStream.write((byte)(int)(paramLong >>> 40));
    paramOutputStream.write((byte)(int)(paramLong >>> 48));
    paramOutputStream.write((byte)(int)(paramLong >>> 56));
  }
  
  static void writeString(OutputStream paramOutputStream, String paramString)
    throws IOException
  {
    paramString = paramString.getBytes("UTF-8");
    writeLong(paramOutputStream, paramString.length);
    paramOutputStream.write(paramString, 0, paramString.length);
  }
  
  static void writeStringStringMap(Map<String, String> paramMap, OutputStream paramOutputStream)
    throws IOException
  {
    if (paramMap != null)
    {
      writeInt(paramOutputStream, paramMap.size());
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        writeString(paramOutputStream, (String)localEntry.getKey());
        writeString(paramOutputStream, (String)localEntry.getValue());
      }
    }
    writeInt(paramOutputStream, 0);
  }
  
  public void clear()
  {
    try
    {
      File[] arrayOfFile = this.mRootDirectory.listFiles();
      if (arrayOfFile != null)
      {
        int j = arrayOfFile.length;
        int i = 0;
        while (i < j)
        {
          arrayOfFile[i].delete();
          i += 1;
        }
      }
      this.mEntries.clear();
      this.mTotalSize = 0L;
      VolleyLog.d("Cache cleared.", new Object[0]);
      return;
    }
    finally {}
  }
  
  /* Error */
  public Cache.Entry get(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aload_0
    //   4: monitorenter
    //   5: aload_0
    //   6: getfield 47	com/mopub/volley/toolbox/DiskBasedCache:mEntries	Ljava/util/Map;
    //   9: aload_1
    //   10: invokeinterface 184 2 0
    //   15: checkcast 10	com/mopub/volley/toolbox/DiskBasedCache$CacheHeader
    //   18: astore 4
    //   20: aload 4
    //   22: ifnonnull +10 -> 32
    //   25: aload 5
    //   27: astore_1
    //   28: aload_0
    //   29: monitorexit
    //   30: aload_1
    //   31: areturn
    //   32: aload_0
    //   33: aload_1
    //   34: invokevirtual 140	com/mopub/volley/toolbox/DiskBasedCache:getFileForKey	(Ljava/lang/String;)Ljava/io/File;
    //   37: astore 7
    //   39: aconst_null
    //   40: astore_2
    //   41: aconst_null
    //   42: astore 6
    //   44: new 13	com/mopub/volley/toolbox/DiskBasedCache$CountingInputStream
    //   47: dup
    //   48: new 299	java/io/FileInputStream
    //   51: dup
    //   52: aload 7
    //   54: invokespecial 301	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   57: aconst_null
    //   58: invokespecial 304	com/mopub/volley/toolbox/DiskBasedCache$CountingInputStream:<init>	(Ljava/io/InputStream;Lcom/mopub/volley/toolbox/DiskBasedCache$1;)V
    //   61: astore_3
    //   62: aload_3
    //   63: invokestatic 308	com/mopub/volley/toolbox/DiskBasedCache$CacheHeader:readHeader	(Ljava/io/InputStream;)Lcom/mopub/volley/toolbox/DiskBasedCache$CacheHeader;
    //   66: pop
    //   67: aload 4
    //   69: aload_3
    //   70: aload 7
    //   72: invokevirtual 310	java/io/File:length	()J
    //   75: aload_3
    //   76: invokestatic 314	com/mopub/volley/toolbox/DiskBasedCache$CountingInputStream:access$100	(Lcom/mopub/volley/toolbox/DiskBasedCache$CountingInputStream;)I
    //   79: i2l
    //   80: lsub
    //   81: l2i
    //   82: invokestatic 211	com/mopub/volley/toolbox/DiskBasedCache:streamToBytes	(Ljava/io/InputStream;I)[B
    //   85: invokevirtual 318	com/mopub/volley/toolbox/DiskBasedCache$CacheHeader:toCacheEntry	([B)Lcom/mopub/volley/Cache$Entry;
    //   88: astore_2
    //   89: aload_3
    //   90: ifnull +7 -> 97
    //   93: aload_3
    //   94: invokevirtual 321	com/mopub/volley/toolbox/DiskBasedCache$CountingInputStream:close	()V
    //   97: aload_2
    //   98: astore_1
    //   99: goto -71 -> 28
    //   102: astore_1
    //   103: aload 5
    //   105: astore_1
    //   106: goto -78 -> 28
    //   109: astore 4
    //   111: aload 6
    //   113: astore_3
    //   114: aload_3
    //   115: astore_2
    //   116: ldc_w 323
    //   119: iconst_2
    //   120: anewarray 4	java/lang/Object
    //   123: dup
    //   124: iconst_0
    //   125: aload 7
    //   127: invokevirtual 326	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   130: aastore
    //   131: dup
    //   132: iconst_1
    //   133: aload 4
    //   135: invokevirtual 327	java/io/IOException:toString	()Ljava/lang/String;
    //   138: aastore
    //   139: invokestatic 170	com/mopub/volley/VolleyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   142: aload_3
    //   143: astore_2
    //   144: aload_0
    //   145: aload_1
    //   146: invokevirtual 329	com/mopub/volley/toolbox/DiskBasedCache:remove	(Ljava/lang/String;)V
    //   149: aload 5
    //   151: astore_1
    //   152: aload_3
    //   153: ifnull -125 -> 28
    //   156: aload_3
    //   157: invokevirtual 321	com/mopub/volley/toolbox/DiskBasedCache$CountingInputStream:close	()V
    //   160: aload 5
    //   162: astore_1
    //   163: goto -135 -> 28
    //   166: astore_1
    //   167: aload 5
    //   169: astore_1
    //   170: goto -142 -> 28
    //   173: astore_1
    //   174: aload_2
    //   175: ifnull +7 -> 182
    //   178: aload_2
    //   179: invokevirtual 321	com/mopub/volley/toolbox/DiskBasedCache$CountingInputStream:close	()V
    //   182: aload_1
    //   183: athrow
    //   184: astore_1
    //   185: aload_0
    //   186: monitorexit
    //   187: aload_1
    //   188: athrow
    //   189: astore_1
    //   190: aload 5
    //   192: astore_1
    //   193: goto -165 -> 28
    //   196: astore_1
    //   197: aload_3
    //   198: astore_2
    //   199: goto -25 -> 174
    //   202: astore 4
    //   204: goto -90 -> 114
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	207	0	this	DiskBasedCache
    //   0	207	1	paramString	String
    //   40	159	2	localObject1	Object
    //   61	137	3	localObject2	Object
    //   18	50	4	localCacheHeader	CacheHeader
    //   109	25	4	localIOException1	IOException
    //   202	1	4	localIOException2	IOException
    //   1	190	5	localObject3	Object
    //   42	70	6	localObject4	Object
    //   37	89	7	localFile	File
    // Exception table:
    //   from	to	target	type
    //   93	97	102	java/io/IOException
    //   44	62	109	java/io/IOException
    //   156	160	166	java/io/IOException
    //   44	62	173	finally
    //   116	142	173	finally
    //   144	149	173	finally
    //   5	20	184	finally
    //   32	39	184	finally
    //   93	97	184	finally
    //   156	160	184	finally
    //   178	182	184	finally
    //   182	184	184	finally
    //   178	182	189	java/io/IOException
    //   62	89	196	finally
    //   62	89	202	java/io/IOException
  }
  
  public File getFileForKey(String paramString)
  {
    return new File(this.mRootDirectory, getFilenameForKey(paramString));
  }
  
  /* Error */
  public void initialize()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 51	com/mopub/volley/toolbox/DiskBasedCache:mRootDirectory	Ljava/io/File;
    //   6: invokevirtual 336	java/io/File:exists	()Z
    //   9: ifne +36 -> 45
    //   12: aload_0
    //   13: getfield 51	com/mopub/volley/toolbox/DiskBasedCache:mRootDirectory	Ljava/io/File;
    //   16: invokevirtual 339	java/io/File:mkdirs	()Z
    //   19: ifne +23 -> 42
    //   22: ldc_w 341
    //   25: iconst_1
    //   26: anewarray 4	java/lang/Object
    //   29: dup
    //   30: iconst_0
    //   31: aload_0
    //   32: getfield 51	com/mopub/volley/toolbox/DiskBasedCache:mRootDirectory	Ljava/io/File;
    //   35: invokevirtual 326	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   38: aastore
    //   39: invokestatic 344	com/mopub/volley/VolleyLog:e	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   42: aload_0
    //   43: monitorexit
    //   44: return
    //   45: aload_0
    //   46: getfield 51	com/mopub/volley/toolbox/DiskBasedCache:mRootDirectory	Ljava/io/File;
    //   49: invokevirtual 292	java/io/File:listFiles	()[Ljava/io/File;
    //   52: astore 6
    //   54: aload 6
    //   56: ifnull -14 -> 42
    //   59: aload 6
    //   61: arraylength
    //   62: istore_2
    //   63: iconst_0
    //   64: istore_1
    //   65: iload_1
    //   66: iload_2
    //   67: if_icmpge -25 -> 42
    //   70: aload 6
    //   72: iload_1
    //   73: aaload
    //   74: astore 7
    //   76: aconst_null
    //   77: astore_3
    //   78: aconst_null
    //   79: astore 5
    //   81: new 346	java/io/BufferedInputStream
    //   84: dup
    //   85: new 299	java/io/FileInputStream
    //   88: dup
    //   89: aload 7
    //   91: invokespecial 301	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   94: invokespecial 349	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   97: astore 4
    //   99: aload 4
    //   101: invokestatic 308	com/mopub/volley/toolbox/DiskBasedCache$CacheHeader:readHeader	(Ljava/io/InputStream;)Lcom/mopub/volley/toolbox/DiskBasedCache$CacheHeader;
    //   104: astore_3
    //   105: aload_3
    //   106: aload 7
    //   108: invokevirtual 310	java/io/File:length	()J
    //   111: putfield 148	com/mopub/volley/toolbox/DiskBasedCache$CacheHeader:size	J
    //   114: aload_0
    //   115: aload_3
    //   116: getfield 136	com/mopub/volley/toolbox/DiskBasedCache$CacheHeader:key	Ljava/lang/String;
    //   119: aload_3
    //   120: invokespecial 351	com/mopub/volley/toolbox/DiskBasedCache:putEntry	(Ljava/lang/String;Lcom/mopub/volley/toolbox/DiskBasedCache$CacheHeader;)V
    //   123: aload 4
    //   125: ifnull +8 -> 133
    //   128: aload 4
    //   130: invokevirtual 352	java/io/BufferedInputStream:close	()V
    //   133: iload_1
    //   134: iconst_1
    //   135: iadd
    //   136: istore_1
    //   137: goto -72 -> 65
    //   140: astore_3
    //   141: goto -8 -> 133
    //   144: astore_3
    //   145: aload 5
    //   147: astore 4
    //   149: aload 7
    //   151: ifnull +12 -> 163
    //   154: aload 4
    //   156: astore_3
    //   157: aload 7
    //   159: invokevirtual 145	java/io/File:delete	()Z
    //   162: pop
    //   163: aload 4
    //   165: ifnull -32 -> 133
    //   168: aload 4
    //   170: invokevirtual 352	java/io/BufferedInputStream:close	()V
    //   173: goto -40 -> 133
    //   176: astore_3
    //   177: goto -44 -> 133
    //   180: astore 4
    //   182: aload_3
    //   183: ifnull +7 -> 190
    //   186: aload_3
    //   187: invokevirtual 352	java/io/BufferedInputStream:close	()V
    //   190: aload 4
    //   192: athrow
    //   193: astore_3
    //   194: aload_0
    //   195: monitorexit
    //   196: aload_3
    //   197: athrow
    //   198: astore_3
    //   199: goto -9 -> 190
    //   202: astore 5
    //   204: aload 4
    //   206: astore_3
    //   207: aload 5
    //   209: astore 4
    //   211: goto -29 -> 182
    //   214: astore_3
    //   215: goto -66 -> 149
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	218	0	this	DiskBasedCache
    //   64	73	1	i	int
    //   62	6	2	j	int
    //   77	43	3	localCacheHeader	CacheHeader
    //   140	1	3	localIOException1	IOException
    //   144	1	3	localIOException2	IOException
    //   156	1	3	localObject1	Object
    //   176	11	3	localIOException3	IOException
    //   193	4	3	localObject2	Object
    //   198	1	3	localIOException4	IOException
    //   206	1	3	localObject3	Object
    //   214	1	3	localIOException5	IOException
    //   97	72	4	localObject4	Object
    //   180	25	4	localObject5	Object
    //   209	1	4	localObject6	Object
    //   79	67	5	localObject7	Object
    //   202	6	5	localObject8	Object
    //   52	19	6	arrayOfFile	File[]
    //   74	84	7	localFile	File
    // Exception table:
    //   from	to	target	type
    //   128	133	140	java/io/IOException
    //   81	99	144	java/io/IOException
    //   168	173	176	java/io/IOException
    //   81	99	180	finally
    //   157	163	180	finally
    //   2	42	193	finally
    //   45	54	193	finally
    //   59	63	193	finally
    //   128	133	193	finally
    //   168	173	193	finally
    //   186	190	193	finally
    //   190	193	193	finally
    //   186	190	198	java/io/IOException
    //   99	123	202	finally
    //   99	123	214	java/io/IOException
  }
  
  public void invalidate(String paramString, boolean paramBoolean)
  {
    try
    {
      Cache.Entry localEntry = get(paramString);
      if (localEntry != null)
      {
        localEntry.softTtl = 0L;
        if (paramBoolean) {
          localEntry.ttl = 0L;
        }
        put(paramString, localEntry);
      }
      return;
    }
    finally {}
  }
  
  /* Error */
  public void put(String paramString, Cache.Entry paramEntry)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: aload_2
    //   4: getfield 371	com/mopub/volley/Cache$Entry:data	[B
    //   7: arraylength
    //   8: invokespecial 373	com/mopub/volley/toolbox/DiskBasedCache:pruneIfNeeded	(I)V
    //   11: aload_0
    //   12: aload_1
    //   13: invokevirtual 140	com/mopub/volley/toolbox/DiskBasedCache:getFileForKey	(Ljava/lang/String;)Ljava/io/File;
    //   16: astore_3
    //   17: new 375	java/io/FileOutputStream
    //   20: dup
    //   21: aload_3
    //   22: invokespecial 376	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   25: astore 4
    //   27: new 10	com/mopub/volley/toolbox/DiskBasedCache$CacheHeader
    //   30: dup
    //   31: aload_1
    //   32: aload_2
    //   33: invokespecial 378	com/mopub/volley/toolbox/DiskBasedCache$CacheHeader:<init>	(Ljava/lang/String;Lcom/mopub/volley/Cache$Entry;)V
    //   36: astore 5
    //   38: aload 5
    //   40: aload 4
    //   42: invokevirtual 382	com/mopub/volley/toolbox/DiskBasedCache$CacheHeader:writeHeader	(Ljava/io/OutputStream;)Z
    //   45: ifne +61 -> 106
    //   48: aload 4
    //   50: invokevirtual 383	java/io/FileOutputStream:close	()V
    //   53: ldc_w 385
    //   56: iconst_1
    //   57: anewarray 4	java/lang/Object
    //   60: dup
    //   61: iconst_0
    //   62: aload_3
    //   63: invokevirtual 326	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   66: aastore
    //   67: invokestatic 170	com/mopub/volley/VolleyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   70: new 188	java/io/IOException
    //   73: dup
    //   74: invokespecial 386	java/io/IOException:<init>	()V
    //   77: athrow
    //   78: astore_1
    //   79: aload_3
    //   80: invokevirtual 145	java/io/File:delete	()Z
    //   83: ifne +20 -> 103
    //   86: ldc_w 388
    //   89: iconst_1
    //   90: anewarray 4	java/lang/Object
    //   93: dup
    //   94: iconst_0
    //   95: aload_3
    //   96: invokevirtual 326	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   99: aastore
    //   100: invokestatic 170	com/mopub/volley/VolleyLog:d	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   103: aload_0
    //   104: monitorexit
    //   105: return
    //   106: aload 4
    //   108: aload_2
    //   109: getfield 371	com/mopub/volley/Cache$Entry:data	[B
    //   112: invokevirtual 391	java/io/FileOutputStream:write	([B)V
    //   115: aload 4
    //   117: invokevirtual 383	java/io/FileOutputStream:close	()V
    //   120: aload_0
    //   121: aload_1
    //   122: aload 5
    //   124: invokespecial 351	com/mopub/volley/toolbox/DiskBasedCache:putEntry	(Ljava/lang/String;Lcom/mopub/volley/toolbox/DiskBasedCache$CacheHeader;)V
    //   127: goto -24 -> 103
    //   130: astore_1
    //   131: aload_0
    //   132: monitorexit
    //   133: aload_1
    //   134: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	135	0	this	DiskBasedCache
    //   0	135	1	paramString	String
    //   0	135	2	paramEntry	Cache.Entry
    //   16	80	3	localFile	File
    //   25	91	4	localFileOutputStream	java.io.FileOutputStream
    //   36	87	5	localCacheHeader	CacheHeader
    // Exception table:
    //   from	to	target	type
    //   17	78	78	java/io/IOException
    //   106	127	78	java/io/IOException
    //   2	17	130	finally
    //   17	78	130	finally
    //   79	103	130	finally
    //   106	127	130	finally
  }
  
  public void remove(String paramString)
  {
    try
    {
      boolean bool = getFileForKey(paramString).delete();
      removeEntry(paramString);
      if (!bool) {
        VolleyLog.d("Could not delete cache entry for key=%s, filename=%s", new Object[] { paramString, getFilenameForKey(paramString) });
      }
      return;
    }
    finally {}
  }
  
  static class CacheHeader
  {
    public String etag;
    public String key;
    public Map<String, String> responseHeaders;
    public long serverDate;
    public long size;
    public long softTtl;
    public long ttl;
    
    private CacheHeader() {}
    
    public CacheHeader(String paramString, Cache.Entry paramEntry)
    {
      this.key = paramString;
      this.size = paramEntry.data.length;
      this.etag = paramEntry.etag;
      this.serverDate = paramEntry.serverDate;
      this.ttl = paramEntry.ttl;
      this.softTtl = paramEntry.softTtl;
      this.responseHeaders = paramEntry.responseHeaders;
    }
    
    public static CacheHeader readHeader(InputStream paramInputStream)
      throws IOException
    {
      CacheHeader localCacheHeader = new CacheHeader();
      if (DiskBasedCache.readInt(paramInputStream) != 538183203) {
        throw new IOException();
      }
      localCacheHeader.key = DiskBasedCache.readString(paramInputStream);
      localCacheHeader.etag = DiskBasedCache.readString(paramInputStream);
      if (localCacheHeader.etag.equals("")) {
        localCacheHeader.etag = null;
      }
      localCacheHeader.serverDate = DiskBasedCache.readLong(paramInputStream);
      localCacheHeader.ttl = DiskBasedCache.readLong(paramInputStream);
      localCacheHeader.softTtl = DiskBasedCache.readLong(paramInputStream);
      localCacheHeader.responseHeaders = DiskBasedCache.readStringStringMap(paramInputStream);
      return localCacheHeader;
    }
    
    public Cache.Entry toCacheEntry(byte[] paramArrayOfByte)
    {
      Cache.Entry localEntry = new Cache.Entry();
      localEntry.data = paramArrayOfByte;
      localEntry.etag = this.etag;
      localEntry.serverDate = this.serverDate;
      localEntry.ttl = this.ttl;
      localEntry.softTtl = this.softTtl;
      localEntry.responseHeaders = this.responseHeaders;
      return localEntry;
    }
    
    public boolean writeHeader(OutputStream paramOutputStream)
    {
      try
      {
        DiskBasedCache.writeInt(paramOutputStream, 538183203);
        DiskBasedCache.writeString(paramOutputStream, this.key);
        if (this.etag == null) {}
        for (String str = "";; str = this.etag)
        {
          DiskBasedCache.writeString(paramOutputStream, str);
          DiskBasedCache.writeLong(paramOutputStream, this.serverDate);
          DiskBasedCache.writeLong(paramOutputStream, this.ttl);
          DiskBasedCache.writeLong(paramOutputStream, this.softTtl);
          DiskBasedCache.writeStringStringMap(this.responseHeaders, paramOutputStream);
          paramOutputStream.flush();
          return true;
        }
        return false;
      }
      catch (IOException paramOutputStream)
      {
        VolleyLog.d("%s", new Object[] { paramOutputStream.toString() });
      }
    }
  }
  
  private static class CountingInputStream
    extends FilterInputStream
  {
    private int bytesRead = 0;
    
    private CountingInputStream(InputStream paramInputStream)
    {
      super();
    }
    
    public int read()
      throws IOException
    {
      int i = super.read();
      if (i != -1) {
        this.bytesRead += 1;
      }
      return i;
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      paramInt1 = super.read(paramArrayOfByte, paramInt1, paramInt2);
      if (paramInt1 != -1) {
        this.bytesRead += paramInt1;
      }
      return paramInt1;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/mopub/volley/toolbox/DiskBasedCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */