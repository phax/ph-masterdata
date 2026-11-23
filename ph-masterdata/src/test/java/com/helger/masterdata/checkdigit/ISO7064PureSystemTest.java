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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

import com.helger.base.CGlobal;

/**
 * Test class for class {@link ISO7064PureSystem}.
 *
 * @author Philip Helger
 */
public final class ISO7064PureSystemTest
{
  private static final BigInteger BI97 = BigInteger.valueOf (97);

  @Test
  public void testMod11_2 ()
  {
    final ISO7064PureSystem a = ISO7064PureSystem.MOD_11_2;
    assertEquals ("iso7064-mod11-2", a.getID ());
    assertEquals (1, a.getCheckCharCount ());
    assertEquals (11, a.getModulus ());
    assertEquals (2, a.getRadix ());

    // Source: ORCID, "Structure of the ORCID Identifier" (ORCID iD 0000-0002-1825-0097)
    assertTrue (a.isValid ("0000000218250097"));
    assertEquals ("7", a.calculateCheckChars ("000000021825009"));

    // Source: python-stdnum 2.2, stdnum/iso7064/mod_11_2.py and tests/test_iso7064.doctest
    assertEquals ("0", a.calculateCheckChars ("0794"));
    assertTrue (a.isValid ("07940"));
    assertEquals ("X", a.calculateCheckChars ("079"));
    assertTrue (a.isValid ("079X"));
    assertEquals (1, a.getChecksum ("079X"));

    // Source: python-stdnum 2.2, stdnum/isni.py (ISNI 0000 0001 2281 955X valid, 0000 0001 1111 955X
    // invalid)
    assertTrue (a.isValid ("000000012281955X"));
    assertFalse (a.isValid ("000000011111955X"));

    // Case-sensitive
    assertFalse (a.isValid ("079x"));
    // X only allowed as check character
    assertFalse (a.isValidPayload ("07X"));
    assertNull (a.calculateCheckChars ("07X"));
  }

  @Test
  public void testMod37_2 ()
  {
    final ISO7064PureSystem a = ISO7064PureSystem.MOD_37_2;
    assertEquals ("iso7064-mod37-2", a.getID ());
    assertEquals (1, a.getCheckCharCount ());

    // Source: python-stdnum 2.2, stdnum/iso7064/mod_37_2.py
    assertEquals ("Y", a.calculateCheckChars ("G123489654321"));
    assertTrue (a.isValid ("G123489654321Y"));
    assertEquals (1, a.getChecksum ("G123489654321Y"));

    // Source: python-stdnum 2.2, tests/test_iso7064.doctest
    assertEquals ("H", a.calculateCheckChars ("G123498654321"));

    // Case-sensitive
    assertFalse (a.isValid ("g123489654321Y"));
    assertFalse (a.isValid ("G123489654321y"));
    // * only allowed as check character
    assertFalse (a.isValidPayload ("G12*"));
  }

  @Test
  public void testMod97_10 ()
  {
    final ISO7064PureSystem a = ISO7064PureSystem.MOD_97_10;
    assertEquals ("iso7064-mod97-10", a.getID ());
    assertEquals (2, a.getCheckCharCount ());

    // Source: task specification (794 mod 97 = 18, 1800 mod 97 = 54, 98 - 54 = 44)
    assertEquals ("44", a.calculateCheckChars ("794"));
    assertTrue (a.isValid ("79444"));

    // Source: python-stdnum 2.2, stdnum/iso7064/mod_97_10.py
    assertEquals ("90", a.calculateCheckChars ("99991234567890121414"));
    assertTrue (a.isValid ("9999123456789012141490"));
    assertEquals ("31", a.calculateCheckChars ("4354111611551114"));
    assertTrue (a.isValid ("08686001256515001121751"));
    assertEquals ("35", a.calculateCheckChars ("22181321402534321446701611"));

    // Source: python-stdnum 2.2, tests/test_iso7064.doctest (check digits in the IBAN range 02-98)
    assertEquals ("02", a.calculateCheckChars ("5367"));
    assertEquals ("97", a.calculateCheckChars ("5303"));
    assertEquals ("98", a.calculateCheckChars ("5335"));
    // ISO 7064 validation also accepts the congruent check digits
    assertTrue (a.isValid ("536702"));
    assertTrue (a.isValid ("536799"));
    assertTrue (a.isValid ("533598"));
    assertTrue (a.isValid ("533501"));
    assertTrue (a.isValid ("530397"));
    assertTrue (a.isValid ("530300"));

    // Letters are not allowed
    assertFalse (a.isValidPayload ("12A"));
    assertNull (a.calculateCheckChars ("12A"));
    assertFalse (a.isValid ("WEST12345698765432GB82"));
    // Only digits as check characters
    assertFalse (a.isValid ("12AB"));
  }

  @Test
  public void testMod97_10Alpha ()
  {
    final ISO7064PureSystem a = ISO7064PureSystem.MOD_97_10_ALPHA;
    assertEquals ("iso7064-mod97-10-alpha", a.getID ());
    assertEquals (2, a.getCheckCharCount ());
    assertTrue (a.isTwoDigitValues ());

    // IBAN (ISO 13616): move the first 4 characters to the end and validate
    // Source: Wikipedia "International Bank Account Number" (GB82WEST12345698765432 and
    // DE89370400440532013000)
    assertTrue (a.isValid ("WEST12345698765432" + "GB82"));
    assertTrue (a.isValid ("370400440532013000" + "DE89"));
    assertFalse (a.isValid ("WEST12345698765432" + "GB83"));
    assertFalse (a.isValid ("370400440532013000" + "DE88"));
    // IBAN check digit creation: append country code and "00" placeholder
    assertEquals ("82", a.calculateCheckChars ("WEST12345698765432GB"));
    assertEquals ("89", a.calculateCheckChars ("370400440532013000DE"));

    // Leitweg-ID: Source: Javadoc of com.helger.masterdata.leitwegid.LeitwegID (04011000-1234512345-06)
    assertEquals ("06", a.calculateCheckChars ("040110001234512345"));

    // Source: python-stdnum 2.2, stdnum/iso7064/mod_97_10.py (numeric payloads behave identical)
    assertEquals ("44", a.calculateCheckChars ("794"));
    assertEquals ("90", a.calculateCheckChars ("99991234567890121414"));

    // Case-sensitive
    assertFalse (a.isValid ("west12345698765432GB82"));
    // Only digits as check characters
    assertFalse (a.isValid ("12AB"));
  }

  @Test
  public void testMod97_10NumericEquivalence ()
  {
    // For numeric payloads both variants must behave identically
    final Random aRandom = new Random (7064);
    for (int nRun = 0; nRun < 10_000; ++nRun)
    {
      final StringBuilder aPayload = new StringBuilder ();
      final int nLen = 1 + aRandom.nextInt (40);
      for (int i = 0; i < nLen; ++i)
        aPayload.append ((char) ('0' + aRandom.nextInt (10)));
      final String sPayload = aPayload.toString ();
      final String sCheck = ISO7064PureSystem.MOD_97_10.calculateCheckChars (sPayload);
      assertEquals (sPayload, sCheck, ISO7064PureSystem.MOD_97_10_ALPHA.calculateCheckChars (sPayload));
      final String sCheck2 = Integer.toString (aRandom.nextInt (100));
      assertEquals (sPayload + sCheck2,
                    ISO7064PureSystem.MOD_97_10.getChecksum (sPayload + sCheck2),
                    ISO7064PureSystem.MOD_97_10_ALPHA.getChecksum (sPayload + sCheck2));
    }
  }

  @Test
  public void testMod97_10LongInput ()
  {
    // Compare the running remainder with a BigInteger reference implementation for long inputs
    final ISO7064PureSystem a = ISO7064PureSystem.MOD_97_10_ALPHA;
    final Random aRandom = new Random (7064);
    for (int nRun = 0; nRun < 200; ++nRun)
    {
      final StringBuilder aPayload = new StringBuilder ();
      final StringBuilder aNumeric = new StringBuilder ();
      final int nLen = 1 + aRandom.nextInt (2000);
      for (int i = 0; i < nLen; ++i)
      {
        final int nValue = aRandom.nextInt (36);
        aPayload.append (CheckDigitHelper.DIGITS_AND_UPPERCASE_LETTERS.charAt (nValue));
        aNumeric.append (nValue);
      }
      final int nExpected = 98 - new BigInteger (aNumeric.append ("00").toString ()).mod (BI97).intValue ();
      assertEquals (nExpected, Integer.parseInt (a.calculateCheckChars (aPayload.toString ())));
    }
  }

  @Test
  public void testGetChecksum ()
  {
    final ISO7064PureSystem a = ISO7064PureSystem.MOD_97_10;
    assertEquals (CGlobal.ILLEGAL_UINT, a.getChecksum (null));
    assertEquals (CGlobal.ILLEGAL_UINT, a.getChecksum (""));
    assertEquals (CGlobal.ILLEGAL_UINT, a.getChecksum ("12"));
    assertEquals (CGlobal.ILLEGAL_UINT, a.getChecksum ("1#2"));
    assertEquals (CGlobal.ILLEGAL_UINT, a.getChecksum ("1a2"));
    assertEquals (1, a.getChecksum ("79444"));
    assertEquals (79445 % 97, a.getChecksum ("79445"));
  }

  @Test
  public void testInvalidConstructorArguments ()
  {
    final String sDigits = CheckDigitHelper.DIGITS;
    try
    {
      // Modulus too small
      new ISO7064PureSystem ("x", 1, 2, 1, sDigits, "0", false);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
    try
    {
      // Check alphabet length must be the modulus
      new ISO7064PureSystem ("x", 11, 2, 1, sDigits, sDigits, false);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
    try
    {
      // 3 check characters are not supported
      new ISO7064PureSystem ("x", 97, 10, 3, sDigits, sDigits, false);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
    try
    {
      // Modulus too large for 2 check characters
      new ISO7064PureSystem ("x", 101, 10, 2, sDigits, sDigits, false);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
    try
    {
      // Duplicate characters
      new ISO7064PureSystem ("x", 11, 2, 1, "00", sDigits + "X", false);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
    try
    {
      // Empty ID
      new ISO7064PureSystem ("", 11, 2, 1, sDigits, sDigits + "X", false);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
  }
}
