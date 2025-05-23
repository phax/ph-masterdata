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
package com.helger.masterdata.leitwegid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link LeitwegID}.
 *
 * @author Philip Helger
 */
public final class LeitwegIDTest
{
  @Test
  public void testBasic ()
  {
    assertEquals (5, LeitwegID.MIN_TOTAL_LENGTH);
    assertEquals (46, LeitwegID.MAX_TOTAL_LENGTH);
  }

  @Test
  public void testIsLeitwegIDValid ()
  {
    assertFalse (LeitwegID.isLeitwegIDValid (null));
    assertFalse (LeitwegID.isLeitwegIDValid (""));

    assertFalse (LeitwegID.isLeitwegIDValid ("04011000-1234512345-05"));
    assertTrue (LeitwegID.isLeitwegIDValid ("04011000-1234512345-06"));
    assertFalse (LeitwegID.isLeitwegIDValid ("04011000-1234512345-07"));
    assertTrue (LeitwegID.isLeitwegIDValid ("991-99012-32"));
    assertTrue (LeitwegID.isLeitwegIDValid ("991-33333TEST-33"));
    assertTrue (LeitwegID.isLeitwegIDValid ("99661-WEBSERVICEOZG-28"));
  }

  @Test
  public void testCalcLeitwegIDChecksum ()
  {
    assertNull (LeitwegID.calcLeitwegIDChecksum (null));
    assertNull (LeitwegID.calcLeitwegIDChecksum (""));

    assertEquals ("06", LeitwegID.calcLeitwegIDChecksum ("04011000-1234512345"));
    assertEquals ("32", LeitwegID.calcLeitwegIDChecksum ("991-99012"));
  }
}
