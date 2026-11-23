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

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.atomic.AtomicInteger;

import org.jspecify.annotations.NonNull;
import org.junit.Test;

import com.helger.unittest.property.Generators;
import com.helger.unittest.property.PropertyChecker;

/**
 * Test class for class {@link WeightedModulusCheckDigit}.
 *
 * @author Philip Helger
 */
public final class WeightedModulusCheckDigitTest
{
  private static final int TRIES = 10_000;
  private static final long SEED = 20261123L;

  private static final WeightedModulusCheckDigit LUHN_AS_WEIGHTED = WeightedModulusCheckDigit.builder ()
                                                                                             .id ("luhn-weighted")
                                                                                             .weights (2, 1)
                                                                                             .weightsFromRight (true)
                                                                                             .modulus (10)
                                                                                             .sumDigitsOfProducts (true)
                                                                                             .remainderMapper (IRemainderToCheckChars.complement (10,
                                                                                                                                                  "0",
                                                                                                                                                  null))
                                                                                             .build ();
  private static final WeightedModulusCheckDigit GS1_AS_WEIGHTED = WeightedModulusCheckDigit.builder ()
                                                                                            .id ("gs1-weighted")
                                                                                            .weights (3, 1)
                                                                                            .weightsFromRight (true)
                                                                                            .modulus (10)
                                                                                            .remainderMapper (IRemainderToCheckChars.complement (10,
                                                                                                                                                 "0",
                                                                                                                                                 null))
                                                                                            .build ();
  // ISBN-10: weights 10 to 2 from the left, check = (11 - sum mod 11) mod 11, X for 10
  private static final WeightedModulusCheckDigit ISBN10_AS_WEIGHTED = WeightedModulusCheckDigit.builder ()
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

  @NonNull
  private static PropertyChecker _checker ()
  {
    return new PropertyChecker ().setTries (TRIES).setSeed (SEED);
  }

  @Test
  public void testLuhnEquivalence ()
  {
    // Source: Wikipedia "Luhn algorithm" (79927398713)
    assertTrue (LUHN_AS_WEIGHTED.isValid ("79927398713"));
    assertEquals ("3", LUHN_AS_WEIGHTED.calculateCheckChars ("7992739871"));

    _checker ().forAll (Generators.strings (CheckDigitHelper.DIGITS, 1, 30),
                        sPayload -> assertEquals (sPayload,
                                                  LuhnCheckDigit.INSTANCE.calculateCheckChars (sPayload),
                                                  LUHN_AS_WEIGHTED.calculateCheckChars (sPayload)));
  }

  @Test
  public void testGS1Equivalence ()
  {
    // Source: Wikipedia "International Article Number" (EAN-13 4006381333931)
    assertTrue (GS1_AS_WEIGHTED.isValid ("4006381333931"));

    _checker ().forAll (Generators.strings (CheckDigitHelper.DIGITS, 1, 30),
                        sPayload -> assertEquals (sPayload,
                                                  GS1CheckDigit.INSTANCE.calculateCheckChars (sPayload),
                                                  GS1_AS_WEIGHTED.calculateCheckChars (sPayload)));
  }

  @Test
  public void testISBN10 ()
  {
    // Source: Wikipedia "ISBN" (ISBN-10 0-306-40615-2)
    assertTrue (ISBN10_AS_WEIGHTED.isValid ("0306406152"));
    assertEquals ("2", ISBN10_AS_WEIGHTED.calculateCheckChars ("030640615"));
    assertFalse (ISBN10_AS_WEIGHTED.isValid ("0306406153"));

    // Source: existing com.helger.masterdata.isbn.ISBNTest
    assertTrue (ISBN10_AS_WEIGHTED.isValid ("3860636278"));
    assertTrue (ISBN10_AS_WEIGHTED.isValid ("0789722429"));
    assertTrue (ISBN10_AS_WEIGHTED.isValid ("0596000464"));
    assertFalse (ISBN10_AS_WEIGHTED.isValid ("386063627X"));
  }

  @Test
  public void testRemainderWithoutCheckChar ()
  {
    // Mod 11 where the value 10 is invalid
    final WeightedModulusCheckDigit a = WeightedModulusCheckDigit.builder ()
                                                                 .id ("mod11-no10")
                                                                 .weights (2, 3, 4, 5, 6, 7)
                                                                 .weightsFromRight (true)
                                                                 .modulus (11)
                                                                 .remainderMapper (IRemainderToCheckChars.complement (11,
                                                                                                                      null,
                                                                                                                      "0"))
                                                                 .build ();
    final AtomicInteger aNullCount = new AtomicInteger (0);
    _checker ().forAll (Generators.strings (CheckDigitHelper.DIGITS, 1, 12), sPayload -> {
      final String sCheck = a.calculateCheckChars (sPayload);
      if (sCheck == null)
      {
        // No digit can make this payload valid
        aNullCount.incrementAndGet ();
        for (char c = '0'; c <= '9'; ++c)
          assertFalse (a.isValid (sPayload + c));
        assertNull (a.appendCheckChars (sPayload));
      }
      else
        assertTrue (a.isValid (sPayload + sCheck));
    });
    // About 1/11 of all payloads have no check digit
    final int nNullCount = aNullCount.get ();
    assertTrue (Integer.toString (nNullCount), nNullCount > TRIES / 20 && nNullCount < TRIES / 5);
  }

  @Test
  public void testTwoDigits ()
  {
    // 97 - (n mod 97) with two digits
    final WeightedModulusCheckDigit a = WeightedModulusCheckDigit.builder ()
                                                                 .id ("mod97")
                                                                 .weights (1)
                                                                 .modulus (97)
                                                                 .checkCharCount (2)
                                                                 .remainderMapper (IRemainderToCheckChars.complementTwoDigits (97))
                                                                 .build ();
    assertEquals (2, a.getCheckCharCount ());
    // Weights 1 sum up the digits: 1+2+3 = 6, 97 - 6 = 91
    assertEquals ("91", a.calculateCheckChars ("123"));
    assertTrue (a.isValid ("12391"));
    // Sum 0 -> 97
    assertEquals ("97", a.calculateCheckChars ("000"));
  }

  @Test
  public void testFromLeftAndCyclic ()
  {
    final WeightedModulusCheckDigit a = WeightedModulusCheckDigit.builder ()
                                                                 .id ("test")
                                                                 .weights (1, 2)
                                                                 .modulus (10)
                                                                 .remainderMapper (IRemainderToCheckChars.remainderAsDigit ())
                                                                 .build ();
    assertArrayEquals (new int [] { 1, 2 }, a.getAllWeights ());
    assertFalse (a.isWeightsFromRight ());
    assertEquals (10, a.getModulus ());
    assertFalse (a.isSumDigitsOfProducts ());
    // Weights repeated: 1*1 + 2*2 + 1*3 + 2*4 + 1*5 = 21 -> 1
    assertEquals ("1", a.calculateCheckChars ("12345"));
    // From left: 1*1 + 2*2 + 1*3 + 2*4 = 16 -> 6
    assertEquals ("6", a.calculateCheckChars ("1234"));
    final WeightedModulusCheckDigit aRight = WeightedModulusCheckDigit.builder ()
                                                                      .id ("test")
                                                                      .weights (1, 2)
                                                                      .weightsFromRight (true)
                                                                      .modulus (10)
                                                                      .remainderMapper (IRemainderToCheckChars.remainderAsDigit ())
                                                                      .build ();
    // From right: 1*4 + 2*3 + 1*2 + 2*1 = 14 -> 4
    assertEquals ("4", aRight.calculateCheckChars ("1234"));
  }

  @Test
  public void testMapperReturningWrongLength ()
  {
    final WeightedModulusCheckDigit a = WeightedModulusCheckDigit.builder ()
                                                                 .id ("test")
                                                                 .weights (1)
                                                                 .modulus (10)
                                                                 .remainderMapper (n -> "12")
                                                                 .build ();
    assertNull (a.calculateCheckChars ("1"));
    assertFalse (a.isValid ("112"));
    assertFalse (a.isValid ("11"));
  }

  @Test
  public void testInvalidBuilder ()
  {
    final IRemainderToCheckChars aMapper = IRemainderToCheckChars.remainderAsDigit ();
    assertNotNull (WeightedModulusCheckDigit.builder ().id ("x").weights (1).modulus (2).remainderMapper (aMapper).build ());
    try
    {
      WeightedModulusCheckDigit.builder ().weights (1).modulus (10).remainderMapper (aMapper).build ();
      fail ();
    }
    catch (final NullPointerException ex)
    {
      // expected - no ID
    }
    try
    {
      WeightedModulusCheckDigit.builder ().id ("x").modulus (10).remainderMapper (aMapper).build ();
      fail ();
    }
    catch (final NullPointerException ex)
    {
      // expected - no weights
    }
    try
    {
      WeightedModulusCheckDigit.builder ().id ("x").weights ().modulus (10).remainderMapper (aMapper).build ();
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected - empty weights
    }
    try
    {
      WeightedModulusCheckDigit.builder ().id ("x").weights (1, -1).modulus (10).remainderMapper (aMapper).build ();
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected - negative weight
    }
    try
    {
      WeightedModulusCheckDigit.builder ().id ("x").weights (1).modulus (1).remainderMapper (aMapper).build ();
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected - modulus too small
    }
    try
    {
      WeightedModulusCheckDigit.builder ().id ("x").weights (1).modulus (10).build ();
      fail ();
    }
    catch (final NullPointerException ex)
    {
      // expected - no mapper
    }
  }
}
