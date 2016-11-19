package com.upsight.mediation.mraid.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MRAIDHtmlProcessor
{
  private static final String TAG = "MRAIDHtmlProcessor";
  
  public static String processRawHtml(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    Object localObject = Pattern.compile("<script\\s+[^>]*\\bsrc\\s*=\\s*([\\\"\\'])mraid\\.js\\1[^>]*>\\s*</script>\\n*", 2).matcher(localStringBuffer);
    if (((Matcher)localObject).find()) {
      localStringBuffer.delete(((Matcher)localObject).start(), ((Matcher)localObject).end());
    }
    int j;
    if (paramString.indexOf("<html") != -1)
    {
      i = 1;
      if (paramString.indexOf("<head") == -1) {
        break label105;
      }
      j = 1;
      label71:
      if (paramString.indexOf("<body") == -1) {
        break label110;
      }
    }
    label105:
    label110:
    for (int k = 1;; k = 0)
    {
      if ((i == 0) || (k != 0)) {
        break label115;
      }
      MRAIDLog.i("MRAIDHtmlProcessor", "have html tag but no body tag. can't randomly insert a body tag");
      return null;
      i = 0;
      break;
      j = 0;
      break label71;
    }
    label115:
    paramString = System.getProperty("line.separator");
    if (j == 0) {
      localStringBuffer.insert(0, "<head>" + paramString + "</head>" + paramString);
    }
    if (i == 0)
    {
      localStringBuffer.insert(0, "<html>" + paramString);
      localStringBuffer.append("</html>");
    }
    localObject = "<style>" + paramString + "body { margin:0; padding:0; }" + paramString + "*:not(input) { -webkit-touch-callout:none; -webkit-user-select:none; -webkit-text-size-adjust:none; }" + paramString + "</style>";
    Matcher localMatcher = Pattern.compile("<head[^>]*>", 2).matcher(localStringBuffer);
    for (int i = 0; localMatcher.find(i); i = localMatcher.end()) {
      localStringBuffer.insert(localMatcher.end(), paramString + "<meta name='viewport' content='width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no' />" + paramString + (String)localObject);
    }
    return localStringBuffer.toString();
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/mraid/internal/MRAIDHtmlProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */