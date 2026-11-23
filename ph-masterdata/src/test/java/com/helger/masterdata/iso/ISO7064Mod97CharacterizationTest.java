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
package com.helger.masterdata.iso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jspecify.annotations.NonNull;
import org.junit.Test;

import com.helger.unittest.property.Generators;
import com.helger.unittest.property.PropertyChecker;

/**
 * Characterization test for {@link ISO7064.Mod97}. It captures the behaviour of the implementation
 * of v8.2.2, so that it stays unchanged when delegating to the check digit package.
 *
 * @author Philip Helger
 */
public final class ISO7064Mod97CharacterizationTest
{
  /**
   * Verbatim copy of <code>ISO7064.Mod97.getChecksum</code> of v8.2.2, used as the oracle.
   */
  private static int _legacyGetChecksum (@NonNull final String s)
  {
    int nChecksum = 0;
    for (final char c : s.toCharArray ())
    {
      final int nCurChecksumValue;
      if (c >= '0' && c <= '9')
        nCurChecksumValue = c - '0';
      else
        if (c >= 'A' && c <= 'Z')
          nCurChecksumValue = c - 'A' + 10;
        else
          return ISO7064.Mod97.ILLEGAL_CHECKSUM;

      if (nCurChecksumValue > 9)
        nChecksum = (100 * nChecksum + nCurChecksumValue) % 97;
      else
        nChecksum = (10 * nChecksum + nCurChecksumValue) % 97;
    }
    return nChecksum;
  }

  @Test
  public void testConstants ()
  {
    assertEquals (-1, ISO7064.Mod97.ILLEGAL_CHECKSUM);
    assertEquals (1, ISO7064.Mod97.EXPECTED_CHECKSUM);
  }

  @Test
  public void testEdgeCases ()
  {
    // Empty string has the checksum 0 and is not valid
    assertEquals (0, ISO7064.Mod97.getChecksum (""));
    assertFalse (ISO7064.Mod97.isValid (""));

    // A single character, letters converted to two digits
    assertEquals (1, ISO7064.Mod97.getChecksum ("1"));
    assertTrue (ISO7064.Mod97.isValid ("1"));
    assertEquals (10, ISO7064.Mod97.getChecksum ("A"));
    assertEquals (35, ISO7064.Mod97.getChecksum ("Z"));
    // 1011 mod 97 = 41
    assertEquals (41, ISO7064.Mod97.getChecksum ("AB"));

    // Letters may occur anywhere, also in the last two positions
    assertEquals (_legacyGetChecksum ("12AB"), ISO7064.Mod97.getChecksum ("12AB"));

    // 79444 = 97 * 819 + 1
    assertEquals (1, ISO7064.Mod97.getChecksum ("79444"));
    assertTrue (ISO7064.Mod97.isValid ("79444"));

    // Illegal characters: lowercase, other Unicode digits, whitespace
    assertEquals (ISO7064.Mod97.ILLEGAL_CHECKSUM, ISO7064.Mod97.getChecksum ("1a"));
    assertEquals (ISO7064.Mod97.ILLEGAL_CHECKSUM, ISO7064.Mod97.getChecksum ("٣"));
    assertEquals (ISO7064.Mod97.ILLEGAL_CHECKSUM, ISO7064.Mod97.getChecksum ("12 3"));
    assertFalse (ISO7064.Mod97.isValid ("1a"));

    // IBAN rearranged - Source: Wikipedia "International Bank Account Number"
    assertTrue (ISO7064.Mod97.isValid ("WEST12345698765432GB82"));
  }

  @Test
  public void testNull ()
  {
    try
    {
      ISO7064.Mod97.getChecksum (null);
      fail ();
    }
    catch (final NullPointerException ex)
    {
      // expected
    }
    try
    {
      ISO7064.Mod97.isValid (null);
      fail ();
    }
    catch (final NullPointerException ex)
    {
      // expected
    }
  }

  @Test
  public void testSameAsLegacy ()
  {
    final PropertyChecker aChecker = new PropertyChecker ().setTries (10_000).setSeed (822);
    aChecker.forAll (Generators.oneOf (Generators.strings ("0123456789", 0, 60),
                                       Generators.strings ("0123456789ABXYZ", 0, 60),
                                       Generators.strings ("0123456789AZaz #٣１", 0, 20),
                                       Generators.unicodeStrings (0, 20)),
                     s -> {
                       assertEquals (s, _legacyGetChecksum (s), ISO7064.Mod97.getChecksum (s));
                       assertEquals (s,
                                     Boolean.valueOf (_legacyGetChecksum (s) == 1),
                                     Boolean.valueOf (ISO7064.Mod97.isValid (s)));
                     });
  }
}
