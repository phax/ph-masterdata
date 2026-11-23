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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Random;

import org.jspecify.annotations.NonNull;
import org.junit.Test;

/**
 * Property based tests for all {@link ECheckDigitAlgorithm} algorithms, using random payloads with a
 * fixed seed.
 *
 * @author Philip Helger
 */
public final class CheckDigitAlgorithmPropertiesTest
{
  private static final int RUNS = 10_000;
  private static final int MAX_PAYLOAD_LEN = 30;
  private static final long SEED = 20261123L;
  /** Characters that are not part of any alphabet */
  private static final String ILLEGAL_CHARS = "#-+ abä€\t";

  /**
   * @return The alphabet to generate payloads from. For MOD 97-10 alpha only digits are used, because
   *         the error detection guarantees of ISO 7064 do not hold for the ISO 13616 letter
   *         conversion.
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
   *         the ISO 7064 pure systems only. The hybrid systems, Luhn (09/90) and GS1 (difference of 5)
   *         do not detect all of them.
   */
  private static boolean _detectsAllTranspositions (@NonNull final ECheckDigitAlgorithm e)
  {
    return e.getAlgorithm () instanceof ISO7064PureSystem;
  }

  @NonNull
  private static String _createRandom (@NonNull final Random aRandom,
                                       @NonNull final String sAlphabet,
                                       final int nLen)
  {
    final StringBuilder aSB = new StringBuilder (nLen);
    for (int i = 0; i < nLen; ++i)
      aSB.append (sAlphabet.charAt (aRandom.nextInt (sAlphabet.length ())));
    return aSB.toString ();
  }

  @Test
  public void testRoundTrip ()
  {
    for (final ECheckDigitAlgorithm e : ECheckDigitAlgorithm.values ())
    {
      final Random aRandom = new Random (SEED);
      // Use the full payload alphabet here, including the letters for MOD 97-10 alpha
      final String sAlphabet = e == ECheckDigitAlgorithm.ISO7064_MOD_97_10_ALPHA ? CheckDigitHelper.DIGITS_AND_UPPERCASE_LETTERS
                                                                           : _getPayloadAlphabet (e);
      for (int i = 0; i < RUNS; ++i)
      {
        final String sPayload = _createRandom (aRandom, sAlphabet, 1 + aRandom.nextInt (MAX_PAYLOAD_LEN));
        assertTrue (e + " " + sPayload, e.isValidPayload (sPayload));
        final String sValue = e.appendCheckChars (sPayload);
        // All predefined algorithms always produce a check character for a valid payload
        assertNotNull (e + " " + sPayload, sValue);
        assertTrue (e + " " + sValue, sValue.length () == sPayload.length () + e.getCheckCharCount ());
        assertTrue (e + " " + sValue, e.isValid (sValue));
      }
    }
  }

  @Test
  public void testSingleSubstitutionDetected ()
  {
    // All predefined algorithms detect every single character substitution
    for (final ECheckDigitAlgorithm e : ECheckDigitAlgorithm.values ())
    {
      final Random aRandom = new Random (SEED);
      final String sPayloadAlphabet = _getPayloadAlphabet (e);
      final String sValueAlphabet = _getValueAlphabet (e);
      for (int i = 0; i < RUNS; ++i)
      {
        final String sValue = e.appendCheckChars (_createRandom (aRandom,
                                                                 sPayloadAlphabet,
                                                                 1 + aRandom.nextInt (MAX_PAYLOAD_LEN)));
        final char [] aChars = sValue.toCharArray ();
        for (int nPos = 0; nPos < aChars.length; ++nPos)
        {
          final char cOld = aChars[nPos];
          char cNew;
          do
          {
            cNew = sValueAlphabet.charAt (aRandom.nextInt (sValueAlphabet.length ()));
          } while (cNew == cOld);
          aChars[nPos] = cNew;
          final String sChanged = new String (aChars);
          assertFalse (e + " " + sValue + " -> " + sChanged, e.isValid (sChanged));
          aChars[nPos] = cOld;
        }
      }
    }
  }

  @Test
  public void testAdjacentTranspositionDetected ()
  {
    for (final ECheckDigitAlgorithm e : ECheckDigitAlgorithm.values ())
      if (_detectsAllTranspositions (e))
      {
        final Random aRandom = new Random (SEED);
        final String sPayloadAlphabet = _getPayloadAlphabet (e);
        for (int i = 0; i < RUNS; ++i)
        {
          final String sValue = e.appendCheckChars (_createRandom (aRandom,
                                                                   sPayloadAlphabet,
                                                                   1 + aRandom.nextInt (MAX_PAYLOAD_LEN)));
          final char [] aChars = sValue.toCharArray ();
          for (int nPos = 0; nPos < aChars.length - 1; ++nPos)
          {
            final char c1 = aChars[nPos];
            final char c2 = aChars[nPos + 1];
            if (c1 != c2)
            {
              aChars[nPos] = c2;
              aChars[nPos + 1] = c1;
              final String sChanged = new String (aChars);
              assertFalse (e + " " + sValue + " -> " + sChanged, e.isValid (sChanged));
              aChars[nPos] = c1;
              aChars[nPos + 1] = c2;
            }
          }
        }
      }
  }

  @Test
  public void testInvalidInput ()
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

      final Random aRandom = new Random (SEED);
      final String sPayloadAlphabet = _getPayloadAlphabet (e);
      for (int i = 0; i < RUNS; ++i)
      {
        final String sPayload = _createRandom (aRandom, sPayloadAlphabet, 1 + aRandom.nextInt (MAX_PAYLOAD_LEN));
        final String sValue = e.appendCheckChars (sPayload);

        // Only the check characters (or less) are never valid
        assertFalse (e.isValid (sValue.substring (sValue.length () - e.getCheckCharCount ())));

        // Insert an illegal character at a random position
        final char cIllegal = ILLEGAL_CHARS.charAt (aRandom.nextInt (ILLEGAL_CHARS.length ()));
        final int nPayloadPos = aRandom.nextInt (sPayload.length () + 1);
        final String sIllegalPayload = sPayload.substring (0, nPayloadPos) + cIllegal + sPayload.substring (nPayloadPos);
        assertFalse (e + " '" + sIllegalPayload + "'", e.isValidPayload (sIllegalPayload));
        assertNull (e + " '" + sIllegalPayload + "'", e.calculateCheckChars (sIllegalPayload));
        assertNull (e + " '" + sIllegalPayload + "'", e.appendCheckChars (sIllegalPayload));

        final int nValuePos = aRandom.nextInt (sValue.length () + 1);
        final String sIllegalValue = sValue.substring (0, nValuePos) + cIllegal + sValue.substring (nValuePos);
        assertFalse (e + " '" + sIllegalValue + "'", e.isValid (sIllegalValue));

        // Replace a character with an illegal one
        final int nReplacePos = aRandom.nextInt (sValue.length ());
        final String sReplacedValue = sValue.substring (0, nReplacePos) + cIllegal + sValue.substring (nReplacePos + 1);
        assertFalse (e + " '" + sReplacedValue + "'", e.isValid (sReplacedValue));
      }
    }
  }
}
