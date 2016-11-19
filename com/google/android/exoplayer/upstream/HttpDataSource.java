package com.google.android.exoplayer.upstream;

import android.text.TextUtils;
import com.google.android.exoplayer.util.Predicate;
import com.google.android.exoplayer.util.Util;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract interface HttpDataSource
  extends UriDataSource
{
  public static final Predicate<String> REJECT_PAYWALL_TYPES = new Predicate()
  {
    public boolean evaluate(String paramAnonymousString)
    {
      paramAnonymousString = Util.toLowerInvariant(paramAnonymousString);
      return (!TextUtils.isEmpty(paramAnonymousString)) && ((!paramAnonymousString.contains("text")) || (paramAnonymousString.contains("text/vtt"))) && (!paramAnonymousString.contains("html")) && (!paramAnonymousString.contains("xml"));
    }
  };
  
  public abstract void clearAllRequestProperties();
  
  public abstract void clearRequestProperty(String paramString);
  
  public abstract void close()
    throws HttpDataSource.HttpDataSourceException;
  
  public abstract Map<String, List<String>> getResponseHeaders();
  
  public abstract long open(DataSpec paramDataSpec)
    throws HttpDataSource.HttpDataSourceException;
  
  public abstract int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws HttpDataSource.HttpDataSourceException;
  
  public abstract void setRequestProperty(String paramString1, String paramString2);
  
  public static class HttpDataSourceException
    extends IOException
  {
    public final DataSpec dataSpec;
    
    public HttpDataSourceException(DataSpec paramDataSpec)
    {
      this.dataSpec = paramDataSpec;
    }
    
    public HttpDataSourceException(IOException paramIOException, DataSpec paramDataSpec)
    {
      super();
      this.dataSpec = paramDataSpec;
    }
    
    public HttpDataSourceException(String paramString, DataSpec paramDataSpec)
    {
      super();
      this.dataSpec = paramDataSpec;
    }
    
    public HttpDataSourceException(String paramString, IOException paramIOException, DataSpec paramDataSpec)
    {
      super(paramIOException);
      this.dataSpec = paramDataSpec;
    }
  }
  
  public static final class InvalidContentTypeException
    extends HttpDataSource.HttpDataSourceException
  {
    public final String contentType;
    
    public InvalidContentTypeException(String paramString, DataSpec paramDataSpec)
    {
      super(paramDataSpec);
      this.contentType = paramString;
    }
  }
  
  public static final class InvalidResponseCodeException
    extends HttpDataSource.HttpDataSourceException
  {
    public final Map<String, List<String>> headerFields;
    public final int responseCode;
    
    public InvalidResponseCodeException(int paramInt, Map<String, List<String>> paramMap, DataSpec paramDataSpec)
    {
      super(paramDataSpec);
      this.responseCode = paramInt;
      this.headerFields = paramMap;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/upstream/HttpDataSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */