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
package com.helger.masterdata.leitwegid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;

import org.jspecify.annotations.NonNull;
import org.junit.Test;

import com.helger.base.string.StringHelper;
import com.helger.unittest.property.Generators;
import com.helger.unittest.property.IGenerator;
import com.helger.unittest.property.PropertyChecker;

/**
 * Characterization test for the checksum handling of {@link LeitwegID}. It captures the behaviour
 * of the implementation of v8.2.2, so that it stays unchanged when delegating to the check digit
 * package.
 *
 * @author Philip Helger
 */
public final class LeitwegIDCharacterizationTest
{
  private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  private static final BigInteger BI97 = BigInteger.valueOf (97);
  private static final BigInteger BI100 = BigInteger.valueOf (100);

  /**
   * Copy of <code>LeitwegID._calcChecksum</code> of v8.2.2, used as the oracle.
   */
  private static int _legacyCalcChecksum (@NonNull final String s)
  {
    BigInteger aBI = BigInteger.ZERO;
    for (final char c : s.toCharArray ())
      if (c >= '0' && c <= '9')
        aBI = aBI.multiply (BigInteger.TEN).add (BigInteger.valueOf (c - '0'));
      else
        aBI = aBI.multiply (BI100).add (BigInteger.valueOf (10 + (c - 'A')));
    aBI = aBI.multiply (BI100);
    return 98 - aBI.mod (BI97).intValueExact ();
  }

  /**
   * @return A generator for syntactically valid Leitweg-IDs without the checksum, with and without
   *         fine addressing.
   */
  @NonNull
  private static IGenerator <String []> _parts ()
  {
    final IGenerator <Boolean> aHasFine = Generators.booleans ();
    final IGenerator <String> aCoarse = Generators.strings (ALPHABET, 2, 12);
    final IGenerator <String> aFine = Generators.strings (ALPHABET, 0, 30);
    return aChoices -> aHasFine.generate (aChoices).booleanValue () ? new String [] { aCoarse.generate (aChoices),
                                                                                      aFine.generate (aChoices) }
                                                                    : new String [] { aCoarse.generate (aChoices) };
  }

  @Test
  public void testEdgeCases ()
  {
    // Source: Javadoc of LeitwegID
    assertEquals ("06", LeitwegID.calcLeitwegIDChecksum ("04011000-1234512345"));
    assertTrue (LeitwegID.isLeitwegIDValid ("04011000-1234512345-06"));
    assertFalse (LeitwegID.isLeitwegIDValid ("04011000-1234512345-07"));

    // Empty fine addressing
    assertEquals (StringHelper.getLeadingZero (_legacyCalcChecksum ("0401"), 2),
                  LeitwegID.calcLeitwegIDChecksum ("0401-"));

    // Invalid input
    assertNull (LeitwegID.calcLeitwegIDChecksum (null));
    assertNull (LeitwegID.calcLeitwegIDChecksum (""));
    assertNull (LeitwegID.calcLeitwegIDChecksum ("0"));
    assertNull (LeitwegID.calcLeitwegIDChecksum ("04011000-12345a"));
    assertFalse (LeitwegID.isLeitwegIDValid (null));
    assertFalse (LeitwegID.isLeitwegIDValid (""));
    assertFalse (LeitwegID.isLeitwegIDValid ("04011000-1234512345-6"));
  }

  @Test
  public void testSameAsLegacy ()
  {
    new PropertyChecker ().setTries (10_000).setSeed (822).forAll (_parts (), aParts -> {
      final String sID = aParts.length == 1 ? aParts[0] : aParts[0] + "-" + aParts[1];
      final int nLegacy = _legacyCalcChecksum (aParts.length == 1 ? aParts[0] : aParts[0] + aParts[1]);
      final String sExpected = StringHelper.getLeadingZero (nLegacy, 2);
      assertEquals (sID, sExpected, LeitwegID.calcLeitwegIDChecksum (sID));

      // Exactly the calculated checksum is valid - the congruent values 00, 01 and 99 are not
      for (int i = 0; i <= 99; ++i)
      {
        final String sCheck = StringHelper.getLeadingZero (i, 2);
        assertEquals (sID + "-" + sCheck,
                      Boolean.valueOf (sCheck.equals (sExpected)),
                      Boolean.valueOf (LeitwegID.isLeitwegIDValid (sID + "-" + sCheck)));
      }
    });
  }
}
