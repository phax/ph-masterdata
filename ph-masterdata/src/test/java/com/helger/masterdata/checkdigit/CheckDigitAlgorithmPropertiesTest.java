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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.jspecify.annotations.NonNull;
import org.junit.Test;

import com.helger.unittest.property.Generators;
import com.helger.unittest.property.IGenerator;
import com.helger.unittest.property.PropertyAssume;
import com.helger.unittest.property.PropertyChecker;

/**
 * Property based tests for all {@link ECheckDigitAlgorithm} algorithms, using the
 * ph-unittest-property {@link PropertyChecker} with a fixed seed. Failing samples are shrunk to a
 * minimal example.
 *
 * @author Philip Helger
 */
public final class CheckDigitAlgorithmPropertiesTest
{
  private static final int TRIES = 10_000;
  private static final int MAX_PAYLOAD_LEN = 30;
  private static final long SEED = 20261123L;
  /** Characters that are not part of any alphabet */
  private static final String ILLEGAL_CHARS = "#-+ abä€\t";

  @NonNull
  private static PropertyChecker _checker ()
  {
    return new PropertyChecker ().setTries (TRIES).setSeed (SEED);
  }

  /**
   * @return The alphabet to generate payloads from, for which all error detection guarantees hold.
   *         For MOD 97-10 alpha only digits are used, because the error detection guarantees of ISO
   *         7064 do not hold for the ISO 13616 letter conversion.
   */
  @NonNull
  private static String _getPayloadAlphabet (@NonNull final ECheckDigitAlgorithm e)
  {
    switch (e)
    {
      case ISO7064_MOD_37_2:
      case ISO7064_MOD_37_36:
        return CheckDigitHelper.DIGITS_AND_UPPERCASE_LETTERS;
      default:
        return CheckDigitHelper.DIGITS;
    }
  }

  /**
   * @return The complete payload alphabet of the algorithm.
   */
  @NonNull
  private static String _getFullPayloadAlphabet (@NonNull final ECheckDigitAlgorithm e)
  {
    if (e == ECheckDigitAlgorithm.ISO7064_MOD_97_10_ALPHA)
      return CheckDigitHelper.DIGITS_AND_UPPERCASE_LETTERS;
    return _getPayloadAlphabet (e);
  }

  /**
   * @return All characters that may occur anywhere in a value, used as replacement characters.
   */
  @NonNull
  private static String _getValueAlphabet (@NonNull final ECheckDigitAlgorithm e)
  {
    switch (e)
    {
      case ISO7064_MOD_11_2:
        return CheckDigitHelper.DIGITS + "X";
      case ISO7064_MOD_37_2:
        return CheckDigitHelper.DIGITS_AND_UPPERCASE_LETTERS + "*";
      case ISO7064_MOD_37_36:
        return CheckDigitHelper.DIGITS_AND_UPPERCASE_LETTERS;
      default:
        return CheckDigitHelper.DIGITS;
    }
  }

  /**
   * @return <code>true</code> if the algorithm detects all adjacent transpositions. This holds for
   *         the ISO 7064 pure systems only. The hybrid systems, Luhn (09/90) and GS1 (difference of
   *         5) do not detect all of them.
   */
  private static boolean _detectsAllTranspositions (@NonNull final ECheckDigitAlgorithm e)
  {
    return e.getAlgorithm () instanceof ISO7064PureSystem;
  }

  /**
   * @return A generator for valid values (payload + check characters) of the algorithm.
   */
  @NonNull
  private static IGenerator <String> _validValues (@NonNull final ECheckDigitAlgorithm e)
  {
    return Generators.strings (_getPayloadAlphabet (e), 1, MAX_PAYLOAD_LEN).map (e::appendCheckChars);
  }

  @Test
  public void testRoundTrip ()
  {
    for (final ECheckDigitAlgorithm e : ECheckDigitAlgorithm.values ())
      _checker ().forAll (Generators.strings (_getFullPayloadAlphabet (e), 1, MAX_PAYLOAD_LEN), sPayload -> {
        assertTrue (e + " " + sPayload, e.isValidPayload (sPayload));
        final String sCheckChars = e.calculateCheckChars (sPayload);
        // All predefined algorithms always produce check characters for a valid payload
        assertNotNull (e + " " + sPayload, sCheckChars);
        assertEquals (e + " " + sPayload, e.getCheckCharCount (), sCheckChars.length ());
        assertEquals (e + " " + sPayload, sPayload + sCheckChars, e.appendCheckChars (sPayload));
        assertTrue (e + " " + sPayload + sCheckChars, e.isValid (sPayload + sCheckChars));
      });
  }

  @Test
  public void testSingleSubstitutionDetected ()
  {
    // All predefined algorithms detect every single character substitution
    for (final ECheckDigitAlgorithm e : ECheckDigitAlgorithm.values ())
    {
      final String sValueAlphabet = _getValueAlphabet (e);
      _checker ().forAll (_validValues (e),
                          Generators.ints (0, MAX_PAYLOAD_LEN + e.getCheckCharCount () - 1),
                          Generators.ints (1, sValueAlphabet.length () - 1),
                          (sValue, aPos, aOffset) -> {
                            final int nPos = aPos.intValue () % sValue.length ();
                            final char cOld = sValue.charAt (nPos);
                            // The offset is never 0, so the new character always differs
                            final int nNewIndex = (sValueAlphabet.indexOf (cOld) + aOffset.intValue ()) %
                                                  sValueAlphabet.length ();
                            final String sChanged = sValue.substring (0, nPos) +
                                                    sValueAlphabet.charAt (nNewIndex) +
                                                    sValue.substring (nPos + 1);
                            assertFalse (e + " " + sValue + " -> " + sChanged, e.isValid (sChanged));
                          });
    }
  }

  @Test
  public void testAdjacentTranspositionDetected ()
  {
    for (final ECheckDigitAlgorithm e : ECheckDigitAlgorithm.values ())
      if (_detectsAllTranspositions (e))
        _checker ().forAll (_validValues (e),
                            Generators.ints (0, MAX_PAYLOAD_LEN + e.getCheckCharCount () - 2),
                            (sValue, aPos) -> {
                              final int nPos = aPos.intValue () % (sValue.length () - 1);
                              final char c1 = sValue.charAt (nPos);
                              final char c2 = sValue.charAt (nPos + 1);
                              PropertyAssume.that (c1 != c2);
                              final String sChanged = sValue.substring (0, nPos) +
                                                      c2 +
                                                      c1 +
                                                      sValue.substring (nPos + 2);
                              assertFalse (e + " " + sValue + " -> " + sChanged, e.isValid (sChanged));
                            });
  }

  @Test
  public void testIllegalCharacters ()
  {
    for (final ECheckDigitAlgorithm e : ECheckDigitAlgorithm.values ())
      _checker ().forAll (_validValues (e),
                          Generators.strings (ILLEGAL_CHARS, 1, 1),
                          Generators.ints (0, MAX_PAYLOAD_LEN + e.getCheckCharCount ()),
                          Generators.booleans (),
                          (sValue, sIllegal, aPos, aReplace) -> {
                            final String sPayload = sValue.substring (0, sValue.length () - e.getCheckCharCount ());

                            // Insert into the payload
                            final int nPayloadPos = aPos.intValue () % (sPayload.length () + 1);
                            final String sIllegalPayload = sPayload.substring (0, nPayloadPos) +
                                                           sIllegal +
                                                           sPayload.substring (nPayloadPos);
                            assertFalse (e + " '" + sIllegalPayload + "'", e.isValidPayload (sIllegalPayload));
                            assertNull (e + " '" + sIllegalPayload + "'", e.calculateCheckChars (sIllegalPayload));
                            assertNull (e + " '" + sIllegalPayload + "'", e.appendCheckChars (sIllegalPayload));

                            // Replace in or insert into the value
                            final String sIllegalValue;
                            if (aReplace.booleanValue ())
                            {
                              final int nValuePos = aPos.intValue () % sValue.length ();
                              sIllegalValue = sValue.substring (0, nValuePos) + sIllegal + sValue.substring (nValuePos + 1);
                            }
                            else
                            {
                              final int nValuePos = aPos.intValue () % (sValue.length () + 1);
                              sIllegalValue = sValue.substring (0, nValuePos) + sIllegal + sValue.substring (nValuePos);
                            }
                            assertFalse (e + " '" + sIllegalValue + "'", e.isValid (sIllegalValue));
                          });
  }

  @Test
  public void testArbitraryInputIsConsistent ()
  {
    // Arbitrary input never throws and the methods are consistent with each other
    for (final ECheckDigitAlgorithm e : ECheckDigitAlgorithm.values ())
      _checker ().forAll (Generators.oneOf (Generators.unicodeStrings (0, 40),
                                            Generators.strings (_getValueAlphabet (e), 0, 40)),
                          s -> {
                            final String sCheckChars = e.calculateCheckChars (s);
                            // All predefined algorithms produce check characters exactly for valid
                            // payloads
                            assertEquals (e + " '" + s + "'",
                                          Boolean.valueOf (e.isValidPayload (s)),
                                          Boolean.valueOf (sCheckChars != null));
                            assertEquals (e + " '" + s + "'",
                                          sCheckChars == null ? null : s + sCheckChars,
                                          e.appendCheckChars (s));
                            if (e.isValid (s))
                            {
                              // A valid value is a valid payload followed by the check characters
                              final int nPayloadLen = s.length () - e.getCheckCharCount ();
                              assertTrue (e + " '" + s + "'", nPayloadLen > 0);
                              assertTrue (e + " '" + s + "'", e.isValidPayload (s.substring (0, nPayloadLen)));
                            }
                          });
  }

  @Test
  public void testNullAndEmpty ()
  {
    for (final ECheckDigitAlgorithm e : ECheckDigitAlgorithm.values ())
    {
      assertFalse (e.isValidPayload (null));
      assertFalse (e.isValidPayload (""));
      assertNull (e.calculateCheckChars (null));
      assertNull (e.calculateCheckChars (""));
      assertNull (e.appendCheckChars (null));
      assertNull (e.appendCheckChars (""));
      assertFalse (e.isValid (null));
      assertFalse (e.isValid (""));

      // Values consisting of the check characters only are never valid
      _checker ().forAll (_validValues (e),
                          sValue -> assertFalse (e.isValid (sValue.substring (sValue.length () -
                                                                              e.getCheckCharCount ()))));
    }
  }
}
