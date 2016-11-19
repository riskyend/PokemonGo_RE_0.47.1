package com.google.android.exoplayer.upstream;

import android.net.Uri;
import com.google.android.exoplayer.ParserException;
import java.io.IOException;
import java.io.InputStream;

public final class UriLoadable<T>
  implements Loader.Loadable
{
  private final DataSpec dataSpec;
  private volatile boolean isCanceled;
  private final Parser<T> parser;
  private volatile T result;
  private final UriDataSource uriDataSource;
  
  public UriLoadable(String paramString, UriDataSource paramUriDataSource, Parser<T> paramParser)
  {
    this.uriDataSource = paramUriDataSource;
    this.parser = paramParser;
    this.dataSpec = new DataSpec(Uri.parse(paramString), 1);
  }
  
  public final void cancelLoad()
  {
    this.isCanceled = true;
  }
  
  public final T getResult()
  {
    return (T)this.result;
  }
  
  public final boolean isLoadCanceled()
  {
    return this.isCanceled;
  }
  
  public final void load()
    throws IOException, InterruptedException
  {
    DataSourceInputStream localDataSourceInputStream = new DataSourceInputStream(this.uriDataSource, this.dataSpec);
    try
    {
      localDataSourceInputStream.open();
      this.result = this.parser.parse(this.uriDataSource.getUri(), localDataSourceInputStream);
      return;
    }
    finally
    {
      localDataSourceInputStream.close();
    }
  }
  
  public static abstract interface Parser<T>
  {
    public abstract T parse(String paramString, InputStream paramInputStream)
      throws ParserException, IOException;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/upstream/UriLoadable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */