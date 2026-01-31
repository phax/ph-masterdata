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
package com.helger.masterdata.nuts;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link NutsManager}.
 *
 * @author Philip Helger
 */
public final class NutsManagerTest
{
  @Test
  public void testBasic ()
  {
    final INutsManager nm = NutsManager.INSTANCE_2021;
    assertNotNull (nm);
    assertEquals (1844, nm.nutsItems ().size ());

    final NutsItem aItem = nm.getItemOfID ("AT130");
    assertNotNull (aItem);

    assertEquals ("AT130", aItem.getID ());
    assertEquals ("Wien", aItem.getDisplayName ());
    assertEquals ("Wien", aItem.getLatinDisplayName ());
    assertSame (ENutsLevel.NUTS3, aItem.getLevel ());
    assertEquals (20, aItem.getCountryOrdinal ());
    assertEquals (1273, aItem.getRegionOrdinal ());

    assertTrue (nm.isIDValid ("AT130"));
    assertFalse (nm.isIDValid ("AT131"));
  }

  @Test
  public void testClone ()
  {
    final NutsManager nm = NutsManager.INSTANCE_2021;

    // Clone
    final NutsManager nm2 = nm.getClone ();
    assertEquals (nm.nutsItems (), nm2.nutsItems ());

    // Alter clone
    nm2.nutsItems ().clear ();
    assertEquals (1844, nm.nutsItems ().size ());
    assertEquals (0, nm2.nutsItems ().size ());
    assertNotEquals (nm.nutsItems (), nm2.nutsItems ());
  }
}
