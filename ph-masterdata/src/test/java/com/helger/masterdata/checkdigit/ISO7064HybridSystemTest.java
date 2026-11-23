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

import org.junit.Test;

/**
 * Test class for class {@link ISO7064HybridSystem}.
 *
 * @author Philip Helger
 */
public final class ISO7064HybridSystemTest
{
  @Test
  public void testMod11_10 ()
  {
    final ISO7064HybridSystem a = ISO7064HybridSystem.MOD_11_10;
    assertEquals ("iso7064-mod11-10", a.getID ());
    assertEquals (1, a.getCheckCharCount ());
    assertEquals (10, a.getModulus ());

    // Source: python-stdnum 2.2, stdnum/iso7064/mod_11_10.py
    assertEquals ("3", a.calculateCheckChars ("79462"));
    assertTrue (a.isValid ("794623"));
    assertEquals ("5", a.calculateCheckChars ("00200667308"));
    assertTrue (a.isValid ("002006673085"));

    // Source: python-stdnum 2.2, tests/test_iso7064.doctest
    assertTrue (a.isValid ("12323"));
    assertTrue (a.isValid ("546794"));
    assertEquals ("5", a.calculateCheckChars ("0794"));
    assertTrue (a.isValid ("07945"));
    assertFalse (a.isValid ("002006673084"));
    assertEquals ("3", a.calculateCheckChars ("00200667309"));
    assertEquals ("8", a.calculateCheckChars ("00200667310"));
    assertEquals ("6", a.calculateCheckChars ("00200667311"));
    assertEquals ("4", a.calculateCheckChars ("00200667312"));

    // German VAT number (USt-IdNr.) - Source: python-stdnum 2.2, stdnum/de/vat.py
    assertTrue (a.isValid ("136695976"));
    assertFalse (a.isValid ("136695978"));

    // Croatian OIB - Source: python-stdnum 2.2, stdnum/hr/oib.py
    assertTrue (a.isValid ("33392005961"));
    assertFalse (a.isValid ("33392005962"));

    assertFalse (a.isValidPayload ("12A"));
    assertNull (a.calculateCheckChars ("12A"));
  }

  @Test
  public void testMod37_36 ()
  {
    final ISO7064HybridSystem a = ISO7064HybridSystem.MOD_37_36;
    assertEquals ("iso7064-mod37-36", a.getID ());
    assertEquals (36, a.getModulus ());

    // Source: python-stdnum 2.2, stdnum/iso7064/mod_37_36.py
    assertEquals ("M", a.calculateCheckChars ("A12425GABC1234002"));
    assertTrue (a.isValid ("A12425GABC1234002M"));

    // Case-sensitive
    assertFalse (a.isValid ("a12425GABC1234002M"));
    assertFalse (a.isValid ("A12425GABC1234002m"));
  }

  @Test
  public void testCustomAlphabet ()
  {
    // Source: python-stdnum 2.2, stdnum/iso7064/mod_37_36.py - with the alphabet 0-9 it is Mod 11,10
    final ISO7064HybridSystem a = new ISO7064HybridSystem ("custom", "0123456789");
    assertEquals ("5", a.calculateCheckChars ("00200667308"));
    assertTrue (a.isValid ("002006673085"));
  }

  @Test
  public void testInvalidConstructorArguments ()
  {
    try
    {
      new ISO7064HybridSystem ("x", "0");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
    try
    {
      new ISO7064HybridSystem ("x", "001");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
  }
}
