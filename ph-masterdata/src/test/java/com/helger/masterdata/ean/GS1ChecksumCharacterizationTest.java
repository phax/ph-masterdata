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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Supplier;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.junit.Test;

import com.helger.unittest.property.Generators;
import com.helger.unittest.property.IGenerator;
import com.helger.unittest.property.PropertyChecker;

/**
 * Characterization test for the GS1 checksum handling of {@link AbstractUPCEAN}, the EAN/UPC
 * classes and {@link GTINValidator}. It captures the behaviour of the implementation of v8.2.2, so
 * that it stays unchanged when delegating to the check digit package. The only intended change is
 * that since v8.3.0 <code>AbstractUPCEAN.calcChecksum</code> is strict: other characters than the
 * ASCII digits and an invalid length lead to an {@link IllegalArgumentException} (v8.2.2 counted
 * letters as -1, accepted other Unicode digits and returned negative values for very long input).
 *
 * @author Philip Helger
 */
public final class GS1ChecksumCharacterizationTest
{
  /** ASCII digits, letters, other Unicode digits (Arabic-Indic 3, fullwidth 1) and junk */
  private static final String LENIENT_ALPHABET = "0123456789AZaz -٣１";

  /**
   * Verbatim copy of <code>AbstractUPCEAN.calcChecksum</code> of v8.2.2, used as the oracle.
   */
  private static int _legacyCalcChecksum (final char @NonNull [] aChars, final int nLen)
  {
    int nChecksumBase = 0;
    int nFactor = (nLen % 2) == 0 ? 1 : 3;
    for (int i = 0; i < nLen; ++i)
    {
      nChecksumBase += Character.digit (aChars[i], 10) * nFactor;
      nFactor = 4 - nFactor;
    }
    return (1000 - nChecksumBase) % 10;
  }

  /**
   * The expected strict calculation since v8.3.0, used as the oracle.
   */
  private static int _expectedCalcChecksum (final char @NonNull [] aChars, final int nLen)
  {
    if (nLen < 1 || nLen > aChars.length)
      throw new IllegalArgumentException ("Invalid length");
    int nSum = 0;
    boolean bWeight3 = true;
    for (int i = nLen - 1; i >= 0; --i)
    {
      final char c = aChars[i];
      if (c < '0' || c > '9')
        throw new IllegalArgumentException ("Invalid character");
      nSum += (c - '0') * (bWeight3 ? 3 : 1);
      bWeight3 = !bWeight3;
    }
    return (10 - nSum % 10) % 10;
  }

  /**
   * Copy of <code>GTINValidator._isValidGTIN</code> of v8.2.2, used as the oracle.
   */
  private static boolean _legacyIsValidGTIN (@Nullable final String sGTIN, final int nExpectedLength)
  {
    if (sGTIN == null || sGTIN.length () != nExpectedLength)
      return false;
    final char [] aChars = sGTIN.toCharArray ();
    for (final char c : aChars)
      if (c < '0' || c > '9')
        return false;
    return _legacyCalcChecksum (aChars, aChars.length - 1) == Character.digit (aChars[aChars.length - 1], 10);
  }

  /**
   * Copy of <code>EAN13.getWithCorrectChecksum</code> of v8.2.2 (without the null checks), with the
   * strict checksum calculation of v8.3.0, used as the oracle.
   */
  @NonNull
  private static String _expectedEAN13GetWithCorrectChecksum (@NonNull final String sMsg,
                                                           @NonNull final EEANChecksumMode eMode)
  {
    EEANChecksumMode eRealMode = eMode;
    if (eRealMode == EEANChecksumMode.AUTO)
    {
      if (sMsg.length () == 12)
        eRealMode = EEANChecksumMode.ADD;
      else
        if (sMsg.length () == 13)
          eRealMode = EEANChecksumMode.CHECK;
        else
          throw new IllegalArgumentException ("Failed to automatically detect the checksum mode");
    }
    switch (eRealMode)
    {
      case ADD:
        if (sMsg.length () != 12)
          throw new IllegalArgumentException ("Message must be 12 characters long");
        return sMsg + Character.forDigit (_expectedCalcChecksum (sMsg.toCharArray (), 12), 10);
      case CHECK:
        if (sMsg.length () != 13)
          throw new IllegalArgumentException ("Message must be 13 characters long");
        if (sMsg.charAt (12) != Character.forDigit (_expectedCalcChecksum (sMsg.toCharArray (), 12), 10))
          throw new IllegalArgumentException ("Checksum is bad");
        return sMsg;
      case IGNORE:
        return sMsg;
      default:
        throw new IllegalArgumentException ("Unknown checksum mode: " + eRealMode);
    }
  }

  /**
   * @return The result or the exception class name, to compare both.
   */
  @NonNull
  private static String _outcome (@NonNull final Supplier <?> aSupplier)
  {
    try
    {
      return "result:" + aSupplier.get ();
    }
    catch (final RuntimeException ex)
    {
      return "exception:" + ex.getClass ().getName ();
    }
  }

  @NonNull
  private static PropertyChecker _checker ()
  {
    return new PropertyChecker ().setTries (10_000).setSeed (822);
  }

  @Test
  public void testCalcChecksumEdgeCases ()
  {
    // Source: Wikipedia "International Article Number" (EAN-13 4006381333931)
    assertEquals (1, AbstractUPCEAN.calcChecksum ("400638133393".toCharArray (), 12));
    // Only the first nLen characters are used
    assertEquals (1, AbstractUPCEAN.calcChecksum ("4006381333931XYZ".toCharArray (), 12));
    // Since v8.3.0: empty, letters (counted as -1 before), other Unicode digits (accepted before)
    // and an out of bounds length (ArrayIndexOutOfBoundsException before) are rejected
    final String sIAE = "exception:java.lang.IllegalArgumentException";
    assertEquals (sIAE, _outcome ( () -> Integer.valueOf (AbstractUPCEAN.calcChecksum (new char [0], 0))));
    assertEquals (sIAE, _outcome ( () -> Integer.valueOf (AbstractUPCEAN.calcChecksum ("ABCDEFGHIJKL".toCharArray (), 12))));
    assertEquals (sIAE,
                  _outcome ( () -> Integer.valueOf (AbstractUPCEAN.calcChecksum ("\uff1400638133393".toCharArray (), 12))));
    assertEquals (sIAE, _outcome ( () -> Integer.valueOf (AbstractUPCEAN.calcChecksum ("123".toCharArray (), 4))));
    assertEquals (sIAE, _outcome ( () -> Integer.valueOf (AbstractUPCEAN.calcChecksum ("123".toCharArray (), -1))));
    // Since v8.3.0: very long input is calculated correctly (56 nines: sum 1008 - before: -8)
    final char [] aNines = "9".repeat (56).toCharArray ();
    assertEquals (2, AbstractUPCEAN.calcChecksum (aNines, 56));
  }

  @Test
  public void testCalcChecksumAsExpected ()
  {
    final IGenerator <String> aStrings = Generators.oneOf (Generators.strings ("0123456789", 0, 20),
                                                           Generators.strings ("0123456789", 30, 80),
                                                           Generators.strings (LENIENT_ALPHABET, 0, 20));
    _checker ().forAll (aStrings, Generators.ints (0, 82), (s, aLen) -> {
      final char [] aChars = s.toCharArray ();
      final int nLen = aLen.intValue ();
      final String sExpected = _outcome ( () -> Integer.valueOf (_expectedCalcChecksum (aChars, nLen)));
      assertEquals (s + "/" + nLen, sExpected, _outcome ( () -> Integer.valueOf (AbstractUPCEAN.calcChecksum (aChars, nLen))));
      // For valid input up to 55 digits (sum <= 999) the result is the same as in v8.2.2
      if (nLen <= 55 && sExpected.startsWith ("result:"))
        assertEquals (s + "/" + nLen,
                      "result:" + _legacyCalcChecksum (aChars, nLen),
                      sExpected);
    });
  }

  @Test
  public void testEANUPCAsExpected ()
  {
    // getWithCorrectChecksum does not validate the characters itself - since v8.3.0 the checksum
    // calculation rejects them
    assertEquals ("exception:java.lang.IllegalArgumentException",
                  _outcome ( () -> EAN13.getWithCorrectChecksum ("ABCDEFGHIJKL", EEANChecksumMode.ADD)));

    _checker ().forAll (Generators.oneOf (Generators.strings ("0123456789", 7, 13),
                                          Generators.strings (LENIENT_ALPHABET, 7, 13)),
                        s -> {
                          final char [] aChars = s.toCharArray ();
                          for (final EEANChecksumMode eMode : EEANChecksumMode.values ())
                          {
                            assertEquals (s + " " + eMode,
                                          _outcome ( () -> _expectedEAN13GetWithCorrectChecksum (s, eMode)),
                                          _outcome ( () -> EAN13.getWithCorrectChecksum (s, eMode)));
                          }
                          // EAN-8 and UPC-A in ADD mode
                          if (s.length () == 7)
                            assertEquals (_outcome ( () -> s + Character.forDigit (_expectedCalcChecksum (aChars, 7), 10)),
                                          _outcome ( () -> EAN8.getWithCorrectChecksum (s, EEANChecksumMode.ADD)));
                          if (s.length () == 11)
                            assertEquals (_outcome ( () -> s + Character.forDigit (_expectedCalcChecksum (aChars, 11), 10)),
                                          _outcome ( () -> UPCA.handleChecksum (s, EEANChecksumMode.ADD)));
                        });
  }

  @Test
  public void testGTINValidatorSameAsLegacy ()
  {
    // Source: Wikipedia "International Article Number" (EAN-13 4006381333931)
    assertTrue (GTINValidator.isValidGTIN13 ("4006381333931"));
    assertTrue (GTINValidator.isValidGLN ("4006381333931"));
    assertFalse (GTINValidator.isValidGTIN13 ("4006381333932"));
    // Other Unicode digits are rejected, because the characters are checked first
    assertFalse (GTINValidator.isValidGTIN13 ("４006381333931"));
    assertFalse (GTINValidator.isValidGTIN13 (null));
    assertFalse (GTINValidator.isValidGTIN13 (""));

    _checker ().forAll (Generators.oneOf (Generators.strings ("0123456789", 7, 19),
                                          Generators.strings (LENIENT_ALPHABET, 7, 19)),
                        s -> {
                          assertEquals (s,
                                        Boolean.valueOf (_legacyIsValidGTIN (s, 8)),
                                        Boolean.valueOf (GTINValidator.isValidGTIN8 (s)));
                          assertEquals (s,
                                        Boolean.valueOf (_legacyIsValidGTIN (s, 12)),
                                        Boolean.valueOf (GTINValidator.isValidGTIN12 (s)));
                          assertEquals (s,
                                        Boolean.valueOf (_legacyIsValidGTIN (s, 13)),
                                        Boolean.valueOf (GTINValidator.isValidGTIN13 (s)));
                          assertEquals (s,
                                        Boolean.valueOf (_legacyIsValidGTIN (s, 13)),
                                        Boolean.valueOf (GTINValidator.isValidGLN (s)));
                          assertEquals (s,
                                        Boolean.valueOf (_legacyIsValidGTIN (s, 14)),
                                        Boolean.valueOf (GTINValidator.isValidGTIN14 (s)));
                          assertEquals (s,
                                        Boolean.valueOf (_legacyIsValidGTIN (s, 18)),
                                        Boolean.valueOf (GTINValidator.isValidSSCC (s)));
                        });
    // Make sure valid values are hit: append the correct check digit
    _checker ().forAll (Generators.oneOf (Generators.strings ("0123456789", 7, 7),
                                          Generators.strings ("0123456789", 11, 13),
                                          Generators.strings ("0123456789", 17, 17)),
                        s -> {
                          final String sValid = s + Character.forDigit (_legacyCalcChecksum (s.toCharArray (), s.length ()), 10);
                          assertTrue (sValid,
                                      GTINValidator.isValidGTIN8 (sValid) ||
                                              GTINValidator.isValidGTIN12 (sValid) ||
                                              GTINValidator.isValidGTIN13 (sValid) ||
                                              GTINValidator.isValidGTIN14 (sValid) ||
                                              GTINValidator.isValidSSCC (sValid));
                        });
  }
}
