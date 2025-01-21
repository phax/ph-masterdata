/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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

import org.junit.Test;

/**
 * Test class for class {@link EAN8}.
 *
 * @author Philip Helger
 */
public final class EAN8Test
{
  @Test
  public void testBasic ()
  {
    EAN8 e = new EAN8 ("12345678");
    assertEquals ("12345678", e.getMessage ());
    assertEquals (EEANChecksumMode.AUTO, e.getChecksumMode ());
    assertTrue (e.validate ().isValid ());

    // Too short
    e = new EAN8 ("123456", EEANChecksumMode.CHECK);
    assertEquals ("123456", e.getMessage ());
    assertEquals (EEANChecksumMode.CHECK, e.getChecksumMode ());
    assertFalse (e.validate ().isValid ());

    // X not allowed
    e = new EAN8 ("123456X");
    assertEquals ("123456X", e.getMessage ());
    assertEquals (EEANChecksumMode.AUTO, e.getChecksumMode ());
    assertFalse (e.validate ().isValid ());
  }

  @Test
  public void testGetWithChecksum ()
  {
    EAN8 e = new EAN8 ("1234567");
    assertEquals ("12345670", e.getWithCorrectChecksum ());

    e = new EAN8 ("12345670", EEANChecksumMode.AUTO);
    assertEquals ("12345670", e.getWithCorrectChecksum ());
  }
}
