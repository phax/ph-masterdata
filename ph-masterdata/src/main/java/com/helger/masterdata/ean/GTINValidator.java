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
package com.helger.masterdata.ean;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonnegative;
import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.PresentForCodeCoverage;
import com.helger.base.string.StringHelper;

/**
 * Utility class that makes verification of GTIN numbers a bit simpler :)
 *
 * @author Philip Helger
 */
@Immutable
public final class GTINValidator
{
  @PresentForCodeCoverage
  private static final GTINValidator INSTANCE = new GTINValidator ();

  private GTINValidator ()
  {}

  private static boolean _containsValidChecksum (@NonNull final char [] aChars)
  {
    final int nCalcedChecksum = AbstractUPCEAN.calcChecksum (aChars, aChars.length - 1);
    final int nChecksum = AbstractUPCEAN.asInt (aChars[aChars.length - 1]);
    return nCalcedChecksum == nChecksum;
  }

  private static boolean _isValidGTIN (@Nullable final String sGTIN, @Nonnegative final int nExpectedLength)
  {
    if (StringHelper.getLength (sGTIN) != nExpectedLength)
      return false;
    final char [] aChars = sGTIN.toCharArray ();
    if (AbstractUPCEAN.validateMessage (aChars).isInvalid ())
      return false;
    return _containsValidChecksum (aChars);
  }

  public static boolean isValidGTIN8 (@Nullable final String sGTIN8)
  {
    return _isValidGTIN (sGTIN8, 8);
  }

  public static boolean isValidGTIN12 (@Nullable final String sGTIN12)
  {
    return _isValidGTIN (sGTIN12, 12);
  }

  public static boolean isValidGTIN13 (@Nullable final String sGTIN13)
  {
    return _isValidGTIN (sGTIN13, 13);
  }

  public static boolean isValidGLN (@Nullable final String sGLN)
  {
    return isValidGTIN13 (sGLN);
  }

  public static boolean isValidGTIN14 (@Nullable final String sGTIN14)
  {
    return _isValidGTIN (sGTIN14, 14);
  }

  public static boolean isValidSSCC (@Nullable final String sSSCC)
  {
    return _isValidGTIN (sSSCC, 18);
  }
}
