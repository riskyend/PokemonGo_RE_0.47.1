package android.support.v4.speech.tts;

import android.content.Context;
import android.os.Build.VERSION;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

class TextToSpeechICS
{
  private static final String TAG = "android.support.v4.speech.tts";
  
  static TextToSpeech construct(Context paramContext, TextToSpeech.OnInitListener paramOnInitListener, String paramString)
  {
    if (Build.VERSION.SDK_INT < 14)
    {
      if (paramString == null) {
        return new TextToSpeech(paramContext, paramOnInitListener);
      }
      Log.w("android.support.v4.speech.tts", "Can't specify tts engine on this device");
      return new TextToSpeech(paramContext, paramOnInitListener);
    }
    return new TextToSpeech(paramContext, paramOnInitListener, paramString);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/android/support/v4/speech/tts/TextToSpeechICS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */