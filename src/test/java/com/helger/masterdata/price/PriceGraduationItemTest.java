/**
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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
package com.helger.masterdata.price;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;

import com.helger.commons.mock.PHAssert;
import com.helger.commons.state.EChange;

/**
 * Test class for class {@link PriceGraduationItem}.
 *
 * @author Philip Helger
 */
public final class PriceGraduationItemTest
{
  @Test
  public void testFunctionality ()
  {
    final BigDecimal aNetAmount = new BigDecimal ("19.9");
    final BigDecimal aNetAmountNew = new BigDecimal ("9.9");
    final PriceGraduationItem aPGI = new PriceGraduationItem (5, aNetAmount);
    assertEquals (5, aPGI.getMinimumQuantity ());
    assertNotNull (aPGI.getUnitNetAmount ());
    PHAssert.assertEquals (19.9, aPGI.getUnitNetAmount ().doubleValue ());

    // Test setter
    assertEquals (EChange.CHANGED, aPGI.setMinimumQuantity (4));
    assertEquals (4, aPGI.getMinimumQuantity ());
    assertEquals (EChange.UNCHANGED, aPGI.setMinimumQuantity (4));
    assertEquals (EChange.CHANGED, aPGI.setMinimumQuantity (5));

    assertEquals (EChange.CHANGED, aPGI.setUnitNetAmount (aNetAmountNew));
    PHAssert.assertEquals (9.9, aPGI.getUnitNetAmount ().doubleValue ());
    assertEquals (EChange.UNCHANGED, aPGI.setUnitNetAmount (aNetAmountNew));
    try
    {
      // Minimum quantity is too low
      new PriceGraduationItem (0, aNetAmount);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Price net amount may not be null
      new PriceGraduationItem (1, null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}

    try
    {
      // Minimum quantity is too low
      aPGI.setMinimumQuantity (0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Price net amount may not be null
      aPGI.setUnitNetAmount (null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}

    // Test equals etc.
    assertTrue (aPGI.equals (aPGI));
    assertFalse (aPGI.equals (null));
    assertFalse (aPGI.equals ("bla"));
    assertTrue (aPGI.equals (new PriceGraduationItem (5, aNetAmountNew)));
    assertFalse (aPGI.equals (new PriceGraduationItem (6, aNetAmountNew)));
    assertFalse (aPGI.equals (new PriceGraduationItem (5, aNetAmountNew.multiply (new BigDecimal ("1.1")))));
    assertEquals (aPGI.hashCode (), new PriceGraduationItem (5, aNetAmountNew).hashCode ());
    assertNotNull (aPGI.toString ());
  }
}
