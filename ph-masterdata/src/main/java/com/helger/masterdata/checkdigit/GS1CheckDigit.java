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
 * The GS1 Mod 10 check digit algorithm as used for GTIN-8, GTIN-12, GTIN-13, GTIN-14, GLN and
 * SSCC: starting with the rightmost payload digit, the digits are multiplied alternately by 3 and
 * 1, the products are summed up and the check digit is <code>(10 - sum mod 10) mod 10</code>. Only
 * the digits <code>0-9</code> are accepted. The payload length is not checked, so this works for
 * all GS1 identification keys.<br>
 * Note: this is not the same as {@link LuhnCheckDigit}. GS1 Mod 10 detects all single digit
 * errors, but no adjacent transposition of two digits with a difference of 5.<br>
 * This class is immutable and thread-safe.
 *
 * @author Philip Helger
 * @since 8.3.0
 */
@Immutable
public final class GS1CheckDigit extends AbstractCheckDigitAlgorithm
{
  /** The ID of this algorithm */
  public static final String ID = "gs1-mod10";

  /** The singleton instance */
  public static final GS1CheckDigit INSTANCE = new GS1CheckDigit ();

  private GS1CheckDigit ()
  {
    super (ID, 1);
  }

  private static int _getCheckValue (@NonNull final String sPayload)
  {
    int nSum = 0;
    boolean bWeight3 = true;
    for (int i = sPayload.length () - 1; i >= 0; --i)
    {
      final int nDigit = sPayload.charAt (i) - '0';
      nSum = (nSum + (bWeight3 ? 3 * nDigit : nDigit)) % 10;
      bWeight3 = !bWeight3;
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
