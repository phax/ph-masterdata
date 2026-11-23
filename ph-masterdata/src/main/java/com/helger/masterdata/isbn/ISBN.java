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
package com.helger.masterdata.isbn;

import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.Immutable;
import com.helger.cache.regex.RegExHelper;
import com.helger.masterdata.checkdigit.GS1CheckDigit;
import com.helger.masterdata.checkdigit.IRemainderToCheckChars;
import com.helger.masterdata.checkdigit.WeightedModulusCheckDigit;

/**
 * Helper class for validating ISBN numbers.
 *
 * @author Philip Helger
 */
@Immutable
public final class ISBN
{
  /**
   * ISBN-10: weights 10 to 2 from the left, check character <code>(11 - (sum mod 11)) mod
   * 11</code>, with <code>X</code> for 10.
   */
  private static final WeightedModulusCheckDigit ISBN10_CHECK_DIGIT = WeightedModulusCheckDigit.builder ()
                                                                                              .id ("isbn10")
                                                                                              .weights (10,
                                                                                                        9,
                                                                                                        8,
                                                                                                        7,
                                                                                                        6,
                                                                                                        5,
                                                                                                        4,
                                                                                                        3,
                                                                                                        2)
                                                                                              .modulus (11)
                                                                                              .remainderMapper (IRemainderToCheckChars.complement (11,
                                                                                                                                                   "X",
                                                                                                                                                   "0"))
                                                                                              .build ();

  private ISBN ()
  {}

  public static boolean isValidISBN10Number (@Nullable final String sValue)
  {
    if (sValue == null || sValue.length () != 10)
      return false;

    // Value needs to be fully numeric with a trailing 'X'
    if (!RegExHelper.stringMatchesPattern ("^[0-9]+[0-9X]?$", sValue))
      return false;

    return ISBN10_CHECK_DIGIT.isValid (sValue);
  }

  public static boolean isValidISBN13Number (@Nullable final String sValue)
  {
    if (sValue == null || sValue.length () != 13)
      return false;
    // Value needs to be fully numeric
    if (!RegExHelper.stringMatchesPattern ("^[0-9]+$", sValue))
      return false;

    return GS1CheckDigit.INSTANCE.isValid (sValue);
  }

  public static boolean isValidISBNNumber (@Nullable final String sValue)
  {
    return isValidISBN10Number (sValue) || isValidISBN13Number (sValue);
  }
}
