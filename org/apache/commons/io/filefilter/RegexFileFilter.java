package org.apache.commons.io.filefilter;

import java.io.File;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.IOCase;

public class RegexFileFilter
  extends AbstractFileFilter
  implements Serializable
{
  private final Pattern pattern;
  
  public RegexFileFilter(String paramString)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Pattern is missing");
    }
    this.pattern = Pattern.compile(paramString);
  }
  
  public RegexFileFilter(String paramString, int paramInt)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Pattern is missing");
    }
    this.pattern = Pattern.compile(paramString, paramInt);
  }
  
  public RegexFileFilter(String paramString, IOCase paramIOCase)
  {
    if (paramString == null) {
      throw new IllegalArgumentException("Pattern is missing");
    }
    int j = 0;
    int i = j;
    if (paramIOCase != null)
    {
      i = j;
      if (!paramIOCase.isCaseSensitive()) {
        i = 2;
      }
    }
    this.pattern = Pattern.compile(paramString, i);
  }
  
  public RegexFileFilter(Pattern paramPattern)
  {
    if (paramPattern == null) {
      throw new IllegalArgumentException("Pattern is missing");
    }
    this.pattern = paramPattern;
  }
  
  public boolean accept(File paramFile, String paramString)
  {
    return this.pattern.matcher(paramString).matches();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/org/apache/commons/io/filefilter/RegexFileFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */