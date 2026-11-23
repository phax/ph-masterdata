/*
 * Copyright (C) 2014-2026 Philip Helger (www.helger.com)
 * philip[at]helger[dot]com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.helger.masterdata.checkdigit;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.Immutable;

/**
 * The Luhn algorithm (Mod 10, ISO/IEC 7812-1 Annex B): starting with the rightmost payload digit,
 * every second digit is doubled (subtracting 9 if the result is greater than 9), all digits are
 * summed up and the check digit is <code>(10 - sum mod 10) mod 10</code>. Only the digits
 * <code>0-9</code> are accepted.<br>
 * Note: this is not the same as {@link GS1CheckDigit}. Luhn detects all single digit errors and
 * all adjacent transpositions except <code>09</code> &harr; <code>90</code>.<br>
 * This class is immutable and thread-safe.
 *
 * @author Philip Helger
 * @since 8.3.0
 */
@Immutable
public final class LuhnCheckDigit extends AbstractCheckDigitAlgorithm
{
  /** The ID of this algorithm */
  public static final String ID = "luhn";

  /** The singleton instance */
  public static final LuhnCheckDigit INSTANCE = new LuhnCheckDigit ();

  private LuhnCheckDigit ()
  {
    super (ID, 1);
  }

  private static int _getCheckValue (@NonNull final String sPayload)
  {
    int nSum = 0;
    boolean bDouble = true;
    for (int i = sPayload.length () - 1; i >= 0; --i)
    {
      int nDigit = sPayload.charAt (i) - '0';
      if (bDouble)
      {
        nDigit *= 2;
        if (nDigit > 9)
          nDigit -= 9;
      }
      nSum = (nSum + nDigit) % 10;
      bDouble = !bDouble;
    }
    return (10 - nSum) % 10;
  }

  public boolean isValidPayload (@Nullable final String sPayload)
  {
    return CheckDigitHelper.isAllDigits (sPayload);
  }

  @Nullable
  public String calculateCheckChars (@Nullable final String sPayload)
  {
    if (!isValidPayload (sPayload))
      return null;

    return Character.toString ((char) ('0' + _getCheckValue (sPayload)));
  }
}
