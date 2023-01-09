/*
 * Copyright (C) 2014-2023 Philip Helger (www.helger.com)
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

import com.helger.commons.string.StringHelper;

/**
 * Unit test class for class {@link EDINB}
 *
 * @author Philip Helger
 */
public final class EDINBTest
{
  @Test
  public void testBasic ()
  {
    for (final EDINB e : EDINB.values ())
    {
      assertTrue (StringHelper.hasText (e.getID ()));
      assertSame (e, EDINB.getFromIDOrNull (e.getID ()));
      assertSame (e, EDINB.getFromIDOrDefault (e.getID (), EDINB.B0));
    }
  }

  @Test
  public void testConversion ()
  {
    assertEquals (11811, Math.round (EDINB.B0.getWidthPixel (300)));
    assertEquals (16701, Math.round (EDINB.B0.getHeightPixel (300)));
    assertEquals (5906, Math.round (EDINB.B0.getWidthPixel (150)));
    assertEquals (8350, Math.round (EDINB.B0.getHeightPixel (150)));
    assertEquals (2835, Math.round (EDINB.B0.getWidthPixel (72)));
    assertEquals (4008, Math.round (EDINB.B0.getHeightPixel (72)));

    assertEquals (11811, EDINB.B0.getWidthPixelLong (300));
    assertEquals (16701, EDINB.B0.getHeightPixelLong (300));
    assertEquals (5906, EDINB.B0.getWidthPixelLong (150));
    assertEquals (8350, EDINB.B0.getHeightPixelLong (150));
    assertEquals (2835, EDINB.B0.getWidthPixelLong (72));
    assertEquals (4008, EDINB.B0.getHeightPixelLong (72));
  }
}
