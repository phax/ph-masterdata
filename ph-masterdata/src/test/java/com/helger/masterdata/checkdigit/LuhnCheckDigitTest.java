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

import org.junit.Test;

/**
 * Test class for class {@link LuhnCheckDigit}.
 *
 * @author Philip Helger
 */
public final class LuhnCheckDigitTest
{
  @Test
  public void testBasic ()
  {
    final LuhnCheckDigit a = LuhnCheckDigit.INSTANCE;
    assertEquals ("luhn", a.getID ());
    assertEquals (1, a.getCheckCharCount ());

    // Source: Wikipedia "Luhn algorithm" (7992739871 with check digit 3)
    assertTrue (a.isValid ("79927398713"));
    assertEquals ("3", a.calculateCheckChars ("7992739871"));
    assertEquals ("79927398713", a.appendCheckChars ("7992739871"));
    for (final String sWrong : new String [] { "79927398710",
                                               "79927398711",
                                               "79927398712",
                                               "79927398714",
                                               "79927398715",
                                               "79927398716",
                                               "79927398717",
                                               "79927398718",
                                               "79927398719" })
      assertFalse (sWrong, a.isValid (sWrong));

    // Source: python-stdnum 2.2, stdnum/luhn.py
    assertFalse (a.isValid ("7894"));
    assertEquals ("9", a.calculateCheckChars ("7894"));
    assertTrue (a.isValid ("78949"));

    // Only digits
    assertFalse (a.isValidPayload ("12A"));
    assertNull (a.calculateCheckChars ("1 2"));
    assertFalse (a.isValid ("7992739871-3"));
  }

  @Test
  public void testDiffersFromGS1 ()
  {
    // Source: Wikipedia "International Article Number" (EAN-13 4006381333931). Valid in GS1 but not
    // in Luhn
    assertTrue (GS1CheckDigit.INSTANCE.isValid ("4006381333931"));
    assertFalse (LuhnCheckDigit.INSTANCE.isValid ("4006381333931"));
  }
}
