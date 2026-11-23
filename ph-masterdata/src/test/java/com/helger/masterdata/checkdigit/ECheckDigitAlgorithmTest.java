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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Test class for class {@link ECheckDigitAlgorithm}.
 *
 * @author Philip Helger
 */
public final class ECheckDigitAlgorithmTest
{
  @Test
  public void testIDs ()
  {
    // The IDs are part of the API and must be stable
    assertEquals ("iso7064-mod11-2", ECheckDigitAlgorithm.ISO7064_MOD_11_2.getID ());
    assertEquals ("iso7064-mod37-2", ECheckDigitAlgorithm.ISO7064_MOD_37_2.getID ());
    assertEquals ("iso7064-mod97-10", ECheckDigitAlgorithm.ISO7064_MOD_97_10.getID ());
    assertEquals ("iso7064-mod97-10-alpha", ECheckDigitAlgorithm.ISO7064_MOD_97_10_ALPHA.getID ());
    assertEquals ("iso7064-mod11-10", ECheckDigitAlgorithm.ISO7064_MOD_11_10.getID ());
    assertEquals ("iso7064-mod37-36", ECheckDigitAlgorithm.ISO7064_MOD_37_36.getID ());
    assertEquals ("luhn", ECheckDigitAlgorithm.LUHN.getID ());
    assertEquals ("gs1-mod10", ECheckDigitAlgorithm.GS1_MOD_10.getID ());

    final Set <String> aIDs = new HashSet <> ();
    for (final ECheckDigitAlgorithm e : ECheckDigitAlgorithm.values ())
    {
      assertTrue (aIDs.add (e.getID ()));
      assertSame (e, ECheckDigitAlgorithm.getFromIDOrNull (e.getID ()));
      assertNotNull (e.getAlgorithm ());
      assertEquals (e.getAlgorithm ().getID (), e.getID ());
      assertEquals (e.getAlgorithm ().getCheckCharCount (), e.getCheckCharCount ());
    }
    assertNull (ECheckDigitAlgorithm.getFromIDOrNull (null));
    assertNull (ECheckDigitAlgorithm.getFromIDOrNull ("bla"));
  }

  @Test
  public void testDelegation ()
  {
    // Source: Wikipedia "Luhn algorithm" (79927398713)
    assertTrue (ECheckDigitAlgorithm.LUHN.isValid ("79927398713"));
    assertTrue (ECheckDigitAlgorithm.LUHN.isValidPayload ("7992739871"));
    assertEquals ("3", ECheckDigitAlgorithm.LUHN.calculateCheckChars ("7992739871"));
    assertEquals ("79927398713", ECheckDigitAlgorithm.LUHN.appendCheckChars ("7992739871"));
  }
}
