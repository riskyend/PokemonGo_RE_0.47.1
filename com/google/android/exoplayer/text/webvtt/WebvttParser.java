package com.google.android.exoplayer.text.webvtt;

import android.text.TextUtils;
import com.google.android.exoplayer.ParserException;
import com.google.android.exoplayer.text.SubtitleParser;
import com.google.android.exoplayer.util.ParsableByteArray;
import java.util.ArrayList;

public final class WebvttParser
  implements SubtitleParser
{
  private final WebvttCueParser cueParser = new WebvttCueParser();
  private final ParsableByteArray parsableWebvttData = new ParsableByteArray();
  private final WebvttCue.Builder webvttCueBuilder = new WebvttCue.Builder();
  
  public final boolean canParse(String paramString)
  {
    return "text/vtt".equals(paramString);
  }
  
  public final WebvttSubtitle parse(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws ParserException
  {
    this.parsableWebvttData.reset(paramArrayOfByte, paramInt1 + paramInt2);
    this.parsableWebvttData.setPosition(paramInt1);
    this.webvttCueBuilder.reset();
    WebvttParserUtil.validateWebvttHeaderLine(this.parsableWebvttData);
    while (!TextUtils.isEmpty(this.parsableWebvttData.readLine())) {}
    paramArrayOfByte = new ArrayList();
    while (this.cueParser.parseNextValidCue(this.parsableWebvttData, this.webvttCueBuilder))
    {
      paramArrayOfByte.add(this.webvttCueBuilder.build());
      this.webvttCueBuilder.reset();
    }
    return new WebvttSubtitle(paramArrayOfByte);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/text/webvtt/WebvttParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */