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
 * Test class for class {@link GS1CheckDigit}.
 *
 * @author Philip Helger
 */
public final class GS1CheckDigitTest
{
  @Test
  public void testBasic ()
  {
    final GS1CheckDigit a = GS1CheckDigit.INSTANCE;
    assertEquals ("gs1-mod10", a.getID ());
    assertEquals (1, a.getCheckCharCount ());

    // Source: Wikipedia "International Article Number" (EAN-13 4006381333931)
    assertTrue (a.isValid ("4006381333931"));
    assertEquals ("1", a.calculateCheckChars ("400638133393"));
    assertFalse (a.isValid ("4006381333932"));

    // Source: python-stdnum 2.2, stdnum/ean.py (GTIN-8, EAN-13 and GTIN-14)
    assertTrue (a.isValid ("73513537"));
    assertTrue (a.isValid ("9780471117094"));
    assertTrue (a.isValid ("98412345678908"));

    // Source: existing com.helger.masterdata.ean.EAN13Test
    assertTrue (a.isValid ("1234567890128"));
    assertTrue (a.isValid ("7300010000001"));
    assertTrue (a.isValid ("1548079098355"));

    // Only digits
    assertFalse (a.isValidPayload ("12345678901X"));
    assertNull (a.calculateCheckChars ("12345678901X"));
    assertFalse (a.isValid ("12345678901X"));
  }
}
