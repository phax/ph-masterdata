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
package com.helger.masterdata.din;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.base.string.StringHelper;

/**
 * Unit test class for class {@link EDINC}
 *
 * @author Philip Helger
 */
public final class EDINCTest
{
  @Test
  public void testBasic ()
  {
    for (final EDINC e : EDINC.values ())
    {
      assertTrue (StringHelper.isNotEmpty (e.getID ()));
      assertSame (e, EDINC.getFromIDOrNull (e.getID ()));
      assertSame (e, EDINC.getFromIDOrDefault (e.getID (), EDINC.C0));
    }
  }

  @Test
  public void testConversion ()
  {
    assertEquals (10831, Math.round (EDINC.C0.getWidthPixel (300)));
    assertEquals (15319, Math.round (EDINC.C0.getHeightPixel (300)));
    assertEquals (5415, Math.round (EDINC.C0.getWidthPixel (150)));
    assertEquals (7659, Math.round (EDINC.C0.getHeightPixel (150)));
    assertEquals (2599, Math.round (EDINC.C0.getWidthPixel (72)));
    assertEquals (3677, Math.round (EDINC.C0.getHeightPixel (72)));

    assertEquals (10831, EDINC.C0.getWidthPixelLong (300));
    assertEquals (15319, EDINC.C0.getHeightPixelLong (300));
    assertEquals (5415, EDINC.C0.getWidthPixelLong (150));
    assertEquals (7659, EDINC.C0.getHeightPixelLong (150));
    assertEquals (2599, EDINC.C0.getWidthPixelLong (72));
    assertEquals (3677, EDINC.C0.getHeightPixelLong (72));
  }
}
