package com.google.android.exoplayer.text.ttml;

import android.text.Layout.Alignment;
import com.google.android.exoplayer.util.Assertions;

final class TtmlStyle
{
  public static final short FONT_SIZE_UNIT_EM = 2;
  public static final short FONT_SIZE_UNIT_PERCENT = 3;
  public static final short FONT_SIZE_UNIT_PIXEL = 1;
  private static final short OFF = 0;
  private static final short ON = 1;
  public static final short STYLE_BOLD = 1;
  public static final short STYLE_BOLD_ITALIC = 3;
  public static final short STYLE_ITALIC = 2;
  public static final short STYLE_NORMAL = 0;
  public static final short UNSPECIFIED = -1;
  private int backgroundColor;
  private boolean backgroundColorSpecified;
  private short bold = -1;
  private int color;
  private boolean colorSpecified;
  private String fontFamily;
  private float fontSize;
  private short fontSizeUnit = -1;
  private String id;
  private TtmlStyle inheritableStyle;
  private short italic = -1;
  private short linethrough = -1;
  private Layout.Alignment textAlign;
  private short underline = -1;
  
  private TtmlStyle inherit(TtmlStyle paramTtmlStyle, boolean paramBoolean)
  {
    if (paramTtmlStyle != null)
    {
      if ((!this.colorSpecified) && (paramTtmlStyle.colorSpecified)) {
        setColor(paramTtmlStyle.color);
      }
      if (this.bold == -1) {
        this.bold = paramTtmlStyle.bold;
      }
      if (this.italic == -1) {
        this.italic = paramTtmlStyle.italic;
      }
      if (this.fontFamily == null) {
        this.fontFamily = paramTtmlStyle.fontFamily;
      }
      if (this.linethrough == -1) {
        this.linethrough = paramTtmlStyle.linethrough;
      }
      if (this.underline == -1) {
        this.underline = paramTtmlStyle.underline;
      }
      if (this.textAlign == null) {
        this.textAlign = paramTtmlStyle.textAlign;
      }
      if (this.fontSizeUnit == -1)
      {
        this.fontSizeUnit = paramTtmlStyle.fontSizeUnit;
        this.fontSize = paramTtmlStyle.fontSize;
      }
      if ((paramBoolean) && (!this.backgroundColorSpecified) && (paramTtmlStyle.backgroundColorSpecified)) {
        setBackgroundColor(paramTtmlStyle.backgroundColor);
      }
    }
    return this;
  }
  
  public TtmlStyle chain(TtmlStyle paramTtmlStyle)
  {
    return inherit(paramTtmlStyle, true);
  }
  
  public int getBackgroundColor()
  {
    return this.backgroundColor;
  }
  
  public int getColor()
  {
    return this.color;
  }
  
  public String getFontFamily()
  {
    return this.fontFamily;
  }
  
  public float getFontSize()
  {
    return this.fontSize;
  }
  
  public short getFontSizeUnit()
  {
    return this.fontSizeUnit;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public short getStyle()
  {
    short s2;
    if ((this.bold == -1) && (this.italic == -1)) {
      s2 = -1;
    }
    short s1;
    do
    {
      return s2;
      s1 = 0;
      if (this.bold != -1) {
        s1 = (short)(this.bold + 0);
      }
      s2 = s1;
    } while (this.italic == -1);
    return (short)(this.italic + s1);
  }
  
  public Layout.Alignment getTextAlign()
  {
    return this.textAlign;
  }
  
  public boolean hasBackgroundColorSpecified()
  {
    return this.backgroundColorSpecified;
  }
  
  public boolean hasColorSpecified()
  {
    return this.colorSpecified;
  }
  
  public TtmlStyle inherit(TtmlStyle paramTtmlStyle)
  {
    return inherit(paramTtmlStyle, false);
  }
  
  public boolean isLinethrough()
  {
    return this.linethrough == 1;
  }
  
  public boolean isUnderline()
  {
    return this.underline == 1;
  }
  
  public TtmlStyle setBackgroundColor(int paramInt)
  {
    this.backgroundColor = paramInt;
    this.backgroundColorSpecified = true;
    return this;
  }
  
  public TtmlStyle setBold(boolean paramBoolean)
  {
    short s = 1;
    boolean bool;
    if (this.inheritableStyle == null)
    {
      bool = true;
      Assertions.checkState(bool);
      if (!paramBoolean) {
        break label31;
      }
    }
    for (;;)
    {
      this.bold = s;
      return this;
      bool = false;
      break;
      label31:
      s = 0;
    }
  }
  
  public TtmlStyle setColor(int paramInt)
  {
    if (this.inheritableStyle == null) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkState(bool);
      this.color = paramInt;
      this.colorSpecified = true;
      return this;
    }
  }
  
  public TtmlStyle setFontFamily(String paramString)
  {
    if (this.inheritableStyle == null) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkState(bool);
      this.fontFamily = paramString;
      return this;
    }
  }
  
  public TtmlStyle setFontSize(float paramFloat)
  {
    this.fontSize = paramFloat;
    return this;
  }
  
  public TtmlStyle setFontSizeUnit(short paramShort)
  {
    this.fontSizeUnit = paramShort;
    return this;
  }
  
  public TtmlStyle setId(String paramString)
  {
    this.id = paramString;
    return this;
  }
  
  public TtmlStyle setItalic(boolean paramBoolean)
  {
    short s = 0;
    if (this.inheritableStyle == null) {}
    for (boolean bool = true;; bool = false)
    {
      Assertions.checkState(bool);
      if (paramBoolean) {
        s = 2;
      }
      this.italic = s;
      return this;
    }
  }
  
  public TtmlStyle setLinethrough(boolean paramBoolean)
  {
    short s = 1;
    boolean bool;
    if (this.inheritableStyle == null)
    {
      bool = true;
      Assertions.checkState(bool);
      if (!paramBoolean) {
        break label31;
      }
    }
    for (;;)
    {
      this.linethrough = s;
      return this;
      bool = false;
      break;
      label31:
      s = 0;
    }
  }
  
  public TtmlStyle setTextAlign(Layout.Alignment paramAlignment)
  {
    this.textAlign = paramAlignment;
    return this;
  }
  
  public TtmlStyle setUnderline(boolean paramBoolean)
  {
    short s = 1;
    boolean bool;
    if (this.inheritableStyle == null)
    {
      bool = true;
      Assertions.checkState(bool);
      if (!paramBoolean) {
        break label31;
      }
    }
    for (;;)
    {
      this.underline = s;
      return this;
      bool = false;
      break;
      label31:
      s = 0;
    }
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/text/ttml/TtmlStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */