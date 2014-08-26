/**
 * Copyright (C) 2006-2014 phloc systems
 * http://www.phloc.com
 * office[at]phloc[dot]com
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

import org.junit.Test;

/**
 * Test class for class {@link EAN13}.
 * 
 * @author Philip Helger
 */
public final class EAN13Test
{
  @Test
  public void testBasic ()
  {
    EAN13 e = new EAN13 ("1234567890128");
    assertEquals ("1234567890128", e.getMessage ());
    assertEquals (EEANChecksumMode.AUTO, e.getChecksumMode ());
    assertTrue (e.validate ().isValid ());

    // Too short
    e = new EAN13 ("12345678901", EEANChecksumMode.CHECK);
    assertEquals ("12345678901", e.getMessage ());
    assertEquals (EEANChecksumMode.CHECK, e.getChecksumMode ());
    assertFalse (e.validate ().isValid ());

    // X not allowed
    e = new EAN13 ("12345678901X");
    assertEquals ("12345678901X", e.getMessage ());
    assertEquals (EEANChecksumMode.AUTO, e.getChecksumMode ());
    assertFalse (e.validate ().isValid ());
  }

  @Test
  public void testGetWithChecksum ()
  {
    EAN13 e = new EAN13 ("123456789012");
    assertEquals ("1234567890128", e.getWithCorrectChecksum ());

    e = new EAN13 ("123456789013");
    assertEquals ("1234567890135", e.getWithCorrectChecksum ());
  }
}
