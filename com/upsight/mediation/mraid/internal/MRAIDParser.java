package com.upsight.mediation.mraid.internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MRAIDParser
{
  private static final String TAG = "MRAIDParser";
  
  private boolean checkParamsForCommand(String paramString, Map<String, String> paramMap)
  {
    boolean bool = true;
    if (paramString.equals("createCalendarEvent")) {
      bool = paramMap.containsKey("eventJSON");
    }
    do
    {
      do
      {
        do
        {
          return bool;
          if ((paramString.equals("open")) || (paramString.equals("playVideo")) || (paramString.equals("storePicture"))) {
            return paramMap.containsKey("url");
          }
          if (!paramString.equals("setOrientationProperties")) {
            break;
          }
        } while ((paramMap.containsKey("allowOrientationChange")) && (paramMap.containsKey("forceOrientation")));
        return false;
        if (!paramString.equals("setResizeProperties")) {
          break;
        }
      } while ((paramMap.containsKey("width")) && (paramMap.containsKey("height")) && (paramMap.containsKey("offsetX")) && (paramMap.containsKey("offsetY")) && (paramMap.containsKey("customClosePosition")) && (paramMap.containsKey("allowOffscreen")));
      return false;
    } while (!paramString.equals("useCustomClose"));
    return paramMap.containsKey("useCustomClose");
  }
  
  private boolean isValidCommand(String paramString)
  {
    return Arrays.asList(new String[] { "replay", "success", "close", "createCalendarEvent", "expand", "open", "playVideo", "resize", "rewardComplete", "setOrientationProperties", "setResizeProperties", "storePicture", "useCustomClose" }).contains(paramString);
  }
  
  public Map<String, String> parseCommandUrl(String paramString)
  {
    MRAIDLog.v("MRAIDParser", "parseCommandUrl " + paramString);
    Object localObject = paramString.substring(8);
    HashMap localHashMap = new HashMap();
    int i = ((String)localObject).indexOf('?');
    if (i != -1)
    {
      String str = ((String)localObject).substring(0, i);
      String[] arrayOfString = ((String)localObject).substring(i + 1).split("&");
      int j = arrayOfString.length;
      i = 0;
      for (;;)
      {
        localObject = str;
        if (i >= j) {
          break;
        }
        localObject = arrayOfString[i];
        int k = ((String)localObject).indexOf('=');
        localHashMap.put(((String)localObject).substring(0, k), ((String)localObject).substring(k + 1));
        i += 1;
      }
    }
    if (!isValidCommand((String)localObject))
    {
      MRAIDLog.i("command " + (String)localObject + " is unknown");
      return null;
    }
    if (!checkParamsForCommand((String)localObject, localHashMap))
    {
      MRAIDLog.i("command URL " + paramString + " is missing parameters");
      return null;
    }
    paramString = new HashMap();
    paramString.put("command", localObject);
    paramString.putAll(localHashMap);
    return paramString;
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/upsight/mediation/mraid/internal/MRAIDParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */