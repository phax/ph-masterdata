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
package com.helger.masterdata.swift;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.function.Supplier;

import org.jspecify.annotations.NonNull;
import org.junit.Test;

import com.helger.unittest.property.Generators;
import com.helger.unittest.property.PropertyChecker;

/**
 * Characterization test for the checksum handling of {@link IBANManager}. It captures the behaviour
 * of the implementation of v8.2.2, so that it stays unchanged when delegating to the check digit
 * package. The only intended change is that the check digits 00, 01 and 99 are rejected by
 * {@link IBANManager#isValidIBAN(String)} since v8.3.0.
 *
 * @author Philip Helger
 */
public final class IBANManagerCharacterizationTest
{
  /**
   * Verbatim copy of <code>ISO7064.Mod97.getChecksum</code> of v8.2.2.
   */
  private static int _legacyMod97 (@NonNull final String s)
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
          return -1;

      if (nCurChecksumValue > 9)
        nChecksum = (100 * nChecksum + nCurChecksumValue) % 97;
      else
        nChecksum = (10 * nChecksum + nCurChecksumValue) % 97;
    }
    return nChecksum;
  }

  /**
   * Copy of <code>IBANManager.createChecksumOfNewIBAN</code> of v8.2.2, used as the oracle.
   */
  private static int _legacyCreateChecksumOfNewIBAN (@NonNull final String sCountryCode, @NonNull final String sBBAN)
  {
    if (sCountryCode.length () != 2)
      throw new IllegalArgumentException ("Country code does not have exactly 2 characters!");
    final String sIBAN = IBANManager.unifyIBAN (sCountryCode + "00" + sBBAN);
    if (sIBAN == null)
      throw new IllegalArgumentException ("The passed data does not resemble an IBAN!");
    final int nChecksum = _legacyMod97 (sIBAN.substring (4) + sIBAN.substring (0, 4));
    if (nChecksum == -1)
      throw new IllegalArgumentException ("The passed data does not resemble an IBAN!");
    return 98 - (((nChecksum - 1) % 97) + 1);
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

  @Test
  public void testCreateChecksumEdgeCases ()
  {
    // Source: Wikipedia "International Bank Account Number"
    assertEquals (82, IBANManager.createChecksumOfNewIBAN ("GB", "WEST12345698765432"));
    assertEquals (89, IBANManager.createChecksumOfNewIBAN ("DE", "370400440532013000"));
    // Lowercase and separators are unified
    assertEquals (82, IBANManager.createChecksumOfNewIBAN ("gb", "west 1234 5698 7654 32"));

    // Check digits are always in the range 02-98, never 00, 01 or 99
    assertEquals ("result:98", _outcome ( () -> Integer.valueOf (IBANManager.createChecksumOfNewIBAN ("00", ""))));

    // Errors
    assertEquals ("exception:java.lang.IllegalArgumentException",
                  _outcome ( () -> Integer.valueOf (IBANManager.createChecksumOfNewIBAN ("D", "123"))));
    assertEquals ("exception:java.lang.NullPointerException",
                  _outcome ( () -> Integer.valueOf (IBANManager.createChecksumOfNewIBAN (null, "123"))));
    assertEquals ("exception:java.lang.NullPointerException",
                  _outcome ( () -> Integer.valueOf (IBANManager.createChecksumOfNewIBAN ("DE", null))));
    // A non-alphanumeric country code character is removed, so the "00" placeholder moves
    assertEquals (_outcome ( () -> Integer.valueOf (_legacyCreateChecksumOfNewIBAN ("D-", "A123"))),
                  _outcome ( () -> Integer.valueOf (IBANManager.createChecksumOfNewIBAN ("D-", "A123"))));
  }

  @Test
  public void testIsValidIBANEdgeCases ()
  {
    // Source: Wikipedia "International Bank Account Number"
    assertTrue (IBANManager.isValidIBAN ("GB82WEST12345698765432"));
    assertTrue (IBANManager.isValidIBAN ("DE89370400440532013000"));
    assertTrue (IBANManager.isValidIBAN ("de89 3704 0044 0532 0130 00"));
    assertFalse (IBANManager.isValidIBAN ("DE88370400440532013000"));
    assertFalse (IBANManager.isValidIBAN (null));
    assertFalse (IBANManager.isValidIBAN (""));
    assertFalse (IBANManager.isValidIBAN ("DE8"));

    // Check digits 00, 01 and 99 are never issued (ISO 13616-1:2020, 6.3.3) - accepted before v8.3.0
    // Source: https://github.com/ktsu-dev/Semantics/issues/438
    assertFalse (IBANManager.isValidIBAN ("GB01WEST12340000000012"));
    assertFalse (IBANManager.isValidIBAN ("GB00WEST12340000000030"));
    assertEquals ("GB98WEST12340000000012", IBANManager.createIBANWithValidChecksum ("GB", "WEST12340000000012"));
    assertEquals ("GB97WEST12340000000030", IBANManager.createIBANWithValidChecksum ("GB", "WEST12340000000030"));
    assertTrue (IBANManager.isValidIBAN ("GB98WEST12340000000012"));
    assertTrue (IBANManager.isValidIBAN ("GB97WEST12340000000030"));

  }

  @Test
  public void testCreateChecksumSameAsLegacy ()
  {
    new PropertyChecker ().setTries (10_000)
                          .setSeed (822)
                          .forAll (Generators.oneOf (Generators.strings ("ABDEGZ", 2, 2),
                                                     Generators.strings ("ABDEZaz09-", 0, 3)),
                                   Generators.oneOf (Generators.strings ("0123456789", 0, 34),
                                                     Generators.strings ("0123456789ABCXYZ", 0, 34),
                                                     Generators.strings ("0123456789AZaz -/٣", 0, 34)),
                                   (sCountryCode, sBBAN) -> assertEquals (sCountryCode + "/" + sBBAN,
                                                                          _outcome ( () -> Integer.valueOf (_legacyCreateChecksumOfNewIBAN (sCountryCode,
                                                                                                                                            sBBAN))),
                                                                          _outcome ( () -> Integer.valueOf (IBANManager.createChecksumOfNewIBAN (sCountryCode,
                                                                                                                                                 sBBAN)))));
  }

  @Test
  public void testIsValidIBANCheckDigits ()
  {
    // For all supported countries: the created IBAN is valid if the BBAN matches the country
    // pattern. No other check digits are accepted - since v8.3.0 also not the congruent values 00,
    // 01 and 99 (instead of 97, 98 and 02), because ISO 13616-1 only allows 02 to 98.
    for (final String sCountry : IBANManager.getAllSupportedCountries ())
    {
      final IBANCountryData aData = IBANManager.getCountryData (sCountry);
      // A BBAN consisting of zeros only, which may or may not match the pattern
      final String sBBAN = "0".repeat (aData.getExpectedLength () - 4);
      final int nCheck = IBANManager.createChecksumOfNewIBAN (sCountry, sBBAN);
      final boolean bValid = IBANManager.isValidIBAN (IBANManager.createIBANWithValidChecksum (sCountry, sBBAN));
      for (int i = 0; i <= 99; ++i)
      {
        final String sOther = sCountry + (i < 10 ? "0" : "") + i + sBBAN;
        final boolean bExpected = bValid && i == nCheck;
        assertEquals (sOther, Boolean.valueOf (bExpected), Boolean.valueOf (IBANManager.isValidIBAN (sOther)));
      }
    }
  }
}
