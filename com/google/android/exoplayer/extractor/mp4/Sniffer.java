package com.google.android.exoplayer.extractor.mp4;

import com.google.android.exoplayer.extractor.ExtractorInput;
import com.google.android.exoplayer.util.ParsableByteArray;
import com.google.android.exoplayer.util.Util;
import java.io.IOException;

final class Sniffer
{
  private static final int[] COMPATIBLE_BRANDS = { Util.getIntegerCodeForString("isom"), Util.getIntegerCodeForString("iso2"), Util.getIntegerCodeForString("avc1"), Util.getIntegerCodeForString("hvc1"), Util.getIntegerCodeForString("hev1"), Util.getIntegerCodeForString("mp41"), Util.getIntegerCodeForString("mp42"), Util.getIntegerCodeForString("3g2a"), Util.getIntegerCodeForString("3g2b"), Util.getIntegerCodeForString("3gr6"), Util.getIntegerCodeForString("3gs6"), Util.getIntegerCodeForString("3ge6"), Util.getIntegerCodeForString("3gg6"), Util.getIntegerCodeForString("M4V "), Util.getIntegerCodeForString("M4A "), Util.getIntegerCodeForString("f4v "), Util.getIntegerCodeForString("kddi"), Util.getIntegerCodeForString("M4VP"), Util.getIntegerCodeForString("qt  "), Util.getIntegerCodeForString("MSNV") };
  
  private static boolean isCompatibleBrand(int paramInt)
  {
    if (paramInt >>> 8 == Util.getIntegerCodeForString("3gp")) {
      return true;
    }
    int[] arrayOfInt = COMPATIBLE_BRANDS;
    int j = arrayOfInt.length;
    int i = 0;
    for (;;)
    {
      if (i >= j) {
        break label42;
      }
      if (arrayOfInt[i] == paramInt) {
        break;
      }
      i += 1;
    }
    label42:
    return false;
  }
  
  public static boolean sniffFragmented(ExtractorInput paramExtractorInput)
    throws IOException, InterruptedException
  {
    return sniffInternal(paramExtractorInput, 4096, true);
  }
  
  private static boolean sniffInternal(ExtractorInput paramExtractorInput, int paramInt, boolean paramBoolean)
    throws IOException, InterruptedException
  {
    long l2 = paramExtractorInput.getLength();
    long l1;
    if (l2 != -1L)
    {
      l1 = l2;
      if (l2 <= paramInt) {}
    }
    else
    {
      l1 = paramInt;
    }
    int m = (int)l1;
    ParsableByteArray localParsableByteArray = new ParsableByteArray(64);
    int j = 0;
    paramInt = 0;
    boolean bool2 = false;
    for (;;)
    {
      boolean bool1 = bool2;
      int i;
      if (j < m)
      {
        i = 8;
        paramExtractorInput.peekFully(localParsableByteArray.data, 0, 8);
        localParsableByteArray.setPosition(0);
        l2 = localParsableByteArray.readUnsignedInt();
        k = localParsableByteArray.readInt();
        l1 = l2;
        if (l2 == 1L)
        {
          paramExtractorInput.peekFully(localParsableByteArray.data, 8, 8);
          i = 16;
          l1 = localParsableByteArray.readLong();
        }
        if (l1 < i) {
          return false;
        }
        i = (int)l1 - i;
        if (k == Atom.TYPE_ftyp)
        {
          if (i < 8) {
            return false;
          }
          int n = (i - 8) / 4;
          paramExtractorInput.peekFully(localParsableByteArray.data, 0, (n + 2) * 4);
          k = 0;
          i = paramInt;
          if (k < n + 2)
          {
            if (k == 1) {}
            while (!isCompatibleBrand(localParsableByteArray.readInt()))
            {
              k += 1;
              break;
            }
            i = 1;
          }
          k = i;
          if (i != 0) {
            break label307;
          }
          return false;
        }
        if (k != Atom.TYPE_moof) {
          break label273;
        }
        bool1 = true;
      }
      label273:
      do
      {
        if ((paramInt == 0) || (paramBoolean != bool1)) {
          break label322;
        }
        return true;
        k = paramInt;
        if (i == 0) {
          break;
        }
        bool1 = bool2;
      } while (j + l1 >= m);
      paramExtractorInput.advancePeekPosition(i);
      int k = paramInt;
      label307:
      j = (int)(j + l1);
      paramInt = k;
    }
    label322:
    return false;
  }
  
  public static boolean sniffUnfragmented(ExtractorInput paramExtractorInput)
    throws IOException, InterruptedException
  {
    return sniffInternal(paramExtractorInput, 128, false);
  }
}


/* Location:              /Users/mohamedtajjiou/Downloads/Rerverse Engeneering/dex2jar-2.0/com.nianticlabs.pokemongo_0.47.1-2016111700_minAPI19(armeabi-v7a)(nodpi)_apkmirror.com (1)-dex2jar.jar!/com/google/android/exoplayer/extractor/mp4/Sniffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */