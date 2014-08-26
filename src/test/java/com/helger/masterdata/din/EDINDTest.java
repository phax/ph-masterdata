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
package com.helger.masterdata.din;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.string.StringHelper;

/**
 * Unit test class for class {@link EDIND}
 * 
 * @author Philip Helger
 */
public class EDINDTest
{
  @Test
  public void testBasic ()
  {
    for (final EDIND e : EDIND.values ())
    {
      assertTrue (StringHelper.hasText (e.getID ()));
      assertSame (e, EDIND.getFromIDOrNull (e.getID ()));
      assertSame (e, EDIND.getFromIDOrDefault (e.getID (), EDIND.D0));
    }
  }

  @Test
  public void testConversion ()
  {
    assertEquals (9106, Math.round (EDIND.D0.getWidthPixel (300)));
    assertEquals (12874, Math.round (EDIND.D0.getHeightPixel (300)));
    assertEquals (4553, Math.round (EDIND.D0.getWidthPixel (150)));
    assertEquals (6437, Math.round (EDIND.D0.getHeightPixel (150)));
    assertEquals (2186, Math.round (EDIND.D0.getWidthPixel (72)));
    assertEquals (3090, Math.round (EDIND.D0.getHeightPixel (72)));

    assertEquals (9106, EDIND.D0.getWidthPixelLong (300));
    assertEquals (12874, EDIND.D0.getHeightPixelLong (300));
    assertEquals (4553, EDIND.D0.getWidthPixelLong (150));
    assertEquals (6437, EDIND.D0.getHeightPixelLong (150));
    assertEquals (2186, EDIND.D0.getWidthPixelLong (72));
    assertEquals (3090, EDIND.D0.getHeightPixelLong (72));
  }
}
