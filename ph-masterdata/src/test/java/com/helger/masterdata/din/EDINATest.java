/*
 * Copyright (C) 2014-2021 Philip Helger (www.helger.com)
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
 * Unit test class for class {@link EDINA}
 *
 * @author Philip Helger
 */
public final class EDINATest
{
  @Test
  public void testBasic ()
  {
    for (final EDINA e : EDINA.values ())
    {
      assertTrue (StringHelper.hasText (e.getID ()));
      assertSame (e, EDINA.getFromIDOrNull (e.getID ()));
      assertSame (e, EDINA.getFromIDOrDefault (e.getID (), EDINA.A0));
    }
  }

  @Test
  public void testConversion ()
  {
    assertEquals (9933, Math.round (EDINA.A0.getWidthPixel (300)));
    assertEquals (14043, Math.round (EDINA.A0.getHeightPixel (300)));
    assertEquals (4967, Math.round (EDINA.A0.getWidthPixel (150)));
    assertEquals (7022, Math.round (EDINA.A0.getHeightPixel (150)));
    assertEquals (2384, Math.round (EDINA.A0.getWidthPixel (72)));
    assertEquals (3370, Math.round (EDINA.A0.getHeightPixel (72)));

    assertEquals (9933, EDINA.A0.getWidthPixelLong (300));
    assertEquals (14043, EDINA.A0.getHeightPixelLong (300));
    assertEquals (4967, EDINA.A0.getWidthPixelLong (150));
    assertEquals (7022, EDINA.A0.getHeightPixelLong (150));
    assertEquals (2384, EDINA.A0.getWidthPixelLong (72));
    assertEquals (3370, EDINA.A0.getHeightPixelLong (72));
  }
}
