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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jspecify.annotations.Nullable;
import org.junit.Test;

import com.helger.cache.regex.RegExHelper;
import com.helger.unittest.property.Generators;
import com.helger.unittest.property.PropertyChecker;

/**
 * Characterization test for {@link ISBN}. It captures the behaviour of the implementation of
 * v8.2.2, so that it stays unchanged when delegating to the check digit package.
 *
 * @author Philip Helger
 */
public final class ISBNCharacterizationTest
{
  /**
   * Verbatim copy of <code>ISBN.isValidISBN10Number</code> of v8.2.2, used as the oracle.
   */
  private static boolean _legacyIsValidISBN10Number (@Nullable final String sValue)
  {
    if (sValue == null || sValue.length () != 10)
      return false;
    if (!RegExHelper.stringMatchesPattern ("^[0-9]+[0-9X]?$", sValue))
      return false;
    int nRes = 0;
    for (int i = 0; i < 9; i++)
    {
      final int j = Character.digit (sValue.charAt (i), 10);
      nRes += ((j * (10 - i)) % 11);
    }
    nRes = 11 - (nRes % 11);
    final char cChkSum = (nRes == 10 ? 'X' : Character.forDigit (nRes, 10));
    return cChkSum == sValue.charAt (9);
  }

  /**
   * Verbatim copy of <code>ISBN.isValidISBN13Number</code> of v8.2.2, used as the oracle.
   */
  private static boolean _legacyIsValidISBN13Number (@Nullable final String sValue)
  {
    if (sValue == null || sValue.length () != 13)
      return false;
    if (!RegExHelper.stringMatchesPattern ("^[0-9]+$", sValue))
      return false;
    int nRes = 0;
    for (int i = 0; i < 12; i++)
    {
      final int j = Character.digit (sValue.charAt (i), 10);
      final int k = 1 + ((i % 2) * 2);
      nRes += ((j * k) % 10);
    }
    nRes = (10 - (nRes % 10)) % 10;
    return Character.forDigit (nRes, 10) == sValue.charAt (12);
  }

  @Test
  public void testEdgeCases ()
  {
    // Source: Wikipedia "ISBN" (ISBN-10 0-306-40615-2 and ISBN-13 978-0-306-40615-7)
    assertTrue (ISBN.isValidISBN10Number ("0306406152"));
    assertTrue (ISBN.isValidISBN13Number ("9780306406157"));

    // Known limitation of v8.2.2: an ISBN-10 with the check digit 0 (weighted sum divisible by 11)
    // is never valid, because "11 - 0" is not mapped to 0
    assertFalse (ISBN.isValidISBN10Number ("0000000000"));
    assertFalse (ISBN.isValidISBNNumber ("0000000000"));

    // X only as the last character, uppercase only
    assertFalse (ISBN.isValidISBN10Number ("X000000000"));
    assertFalse (ISBN.isValidISBN10Number ("000000001x"));

    // Other Unicode digits are rejected by the regular expression
    assertFalse (ISBN.isValidISBN10Number ("٣306406152"));
    assertFalse (ISBN.isValidISBN13Number ("٩780306406157"));

    assertFalse (ISBN.isValidISBN10Number (null));
    assertFalse (ISBN.isValidISBN13Number (null));
    assertFalse (ISBN.isValidISBNNumber (null));
    assertFalse (ISBN.isValidISBNNumber (""));
  }

  @Test
  public void testSameAsLegacy ()
  {
    final PropertyChecker aChecker = new PropertyChecker ().setTries (10_000).setSeed (822);
    // Arbitrary strings
    aChecker.forAll (Generators.oneOf (Generators.strings ("0123456789X", 9, 14),
                                       Generators.strings ("0123456789Xx -٣", 9, 14)),
                     s -> {
                       assertEquals (s,
                                     Boolean.valueOf (_legacyIsValidISBN10Number (s)),
                                     Boolean.valueOf (ISBN.isValidISBN10Number (s)));
                       assertEquals (s,
                                     Boolean.valueOf (_legacyIsValidISBN13Number (s)),
                                     Boolean.valueOf (ISBN.isValidISBN13Number (s)));
                     });
    // Every possible check character for a numeric payload, so that the valid values are hit
    aChecker.forAll (Generators.strings ("0123456789", 9, 9), Generators.strings ("0123456789X", 1, 1), (s, c) -> {
      assertEquals (s + c,
                    Boolean.valueOf (_legacyIsValidISBN10Number (s + c)),
                    Boolean.valueOf (ISBN.isValidISBN10Number (s + c)));
    });
    aChecker.forAll (Generators.strings ("0123456789", 12, 12), Generators.strings ("0123456789", 1, 1), (s, c) -> {
      assertEquals (s + c,
                    Boolean.valueOf (_legacyIsValidISBN13Number (s + c)),
                    Boolean.valueOf (ISBN.isValidISBN13Number (s + c)));
    });
  }
}
