/**
 * Copyright (C) 2014-2020 Philip Helger (www.helger.com)
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
import java.util.Locale;

import org.junit.Test;

import com.helger.commons.math.MathHelper;
import com.helger.commons.mock.CommonsAssert;
import com.helger.commons.mock.CommonsTestHelper;
import com.helger.masterdata.currency.CurrencyHelper;
import com.helger.masterdata.currency.ECurrency;
import com.helger.masterdata.vat.EVATItemType;
import com.helger.masterdata.vat.IVATItem;
import com.helger.masterdata.vat.VATItem;
import com.helger.masterdata.vat.VATManager;

/**
 * Test class for class {@link PriceGraduation}.
 *
 * @author Philip Helger
 */
public final class PriceGraduationTest
{
  @Test
  public void testCtor ()
  {
    // ECurrency eCurrency = , aNetAmount, VATManager.VATTYPE_NONE

    final PriceGraduation pg = new PriceGraduation (CurrencyHelper.DEFAULT_CURRENCY);
    assertEquals (CurrencyHelper.DEFAULT_CURRENCY, pg.getCurrency ());
    assertNotNull (pg.getAllItems ());
    assertTrue (pg.getAllItems ().isEmpty ());
    try
    {
      // No item present
      pg.getSinglePriceOfQuantity (1, VATManager.VATTYPE_NONE);
      fail ();
    }
    catch (final IllegalStateException ex)
    {}

    try
    {
      // No item present
      pg.getTotalPriceOfQuantity (1, VATManager.VATTYPE_NONE);
      fail ();
    }
    catch (final IllegalStateException ex)
    {}

    try
    {
      // quantity too low
      pg.getSinglePriceOfQuantity (0, VATManager.VATTYPE_NONE);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // quantity too low
      pg.getTotalPriceOfQuantity (0, VATManager.VATTYPE_NONE);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testAdd ()
  {
    final IVATItem aVATItem = VATItem.createTestItem (Locale.GERMANY, EVATItemType.REGULAR, MathHelper.toBigDecimal ("20"));
    final PriceGraduation pg = new PriceGraduation (CurrencyHelper.DEFAULT_CURRENCY);
    assertTrue (pg.isEmpty ());

    // add first item
    final BigDecimal aNetAmount1 = MathHelper.toBigDecimal ("19.9");
    final Price p1 = new Price (CurrencyHelper.DEFAULT_CURRENCY, aNetAmount1, aVATItem);
    pg.addItem (new PriceGraduationItem (1, aNetAmount1));
    assertEquals (1, pg.getAllItems ().size ());
    assertEquals (1, pg.getSmallestMinimumQuantityItem ().getMinimumQuantity ());
    assertEquals (1, pg.getLargestMinimumQuantityItem ().getMinimumQuantity ());

    assertEquals (p1, pg.getSinglePriceOfQuantity (1, aVATItem));
    assertEquals (p1, pg.getSinglePriceOfQuantity (7000000, aVATItem));

    assertEquals (p1, pg.getTotalPriceOfQuantity (1, aVATItem));
    assertEquals (p1.getMultiplied (MathHelper.toBigDecimal ("5")), pg.getTotalPriceOfQuantity (5, aVATItem));

    // add second item
    final BigDecimal aNetAmount5 = MathHelper.toBigDecimal ("18.9");
    final Price p5 = new Price (CurrencyHelper.DEFAULT_CURRENCY, aNetAmount5, aVATItem);
    pg.addItem (new PriceGraduationItem (5, aNetAmount5));
    assertEquals (2, pg.getAllItems ().size ());
    assertEquals (1, pg.getSmallestMinimumQuantityItem ().getMinimumQuantity ());
    assertEquals (5, pg.getLargestMinimumQuantityItem ().getMinimumQuantity ());

    assertEquals (p1, pg.getSinglePriceOfQuantity (1, aVATItem));
    assertEquals (p1, pg.getSinglePriceOfQuantity (4, aVATItem));
    assertEquals (p5, pg.getSinglePriceOfQuantity (5, aVATItem));
    assertEquals (p5, pg.getSinglePriceOfQuantity (70000, aVATItem));

    assertEquals (p1, pg.getTotalPriceOfQuantity (1, aVATItem));
    assertEquals (p1.getMultiplied (MathHelper.toBigDecimal ("4")), pg.getTotalPriceOfQuantity (4, aVATItem));
    assertEquals (p5.getMultiplied (MathHelper.toBigDecimal ("5")), pg.getTotalPriceOfQuantity (5, aVATItem));
    assertEquals (p5.getMultiplied (MathHelper.toBigDecimal ("15")), pg.getTotalPriceOfQuantity (15, aVATItem));

    // add third item
    final BigDecimal aNetAmount3 = MathHelper.toBigDecimal ("19.4");
    final Price p3 = new Price (CurrencyHelper.DEFAULT_CURRENCY, aNetAmount3, aVATItem);
    pg.addItem (new PriceGraduationItem (3, aNetAmount3));
    assertEquals (3, pg.getAllItems ().size ());
    assertEquals (1, pg.getSmallestMinimumQuantityItem ().getMinimumQuantity ());
    assertEquals (5, pg.getLargestMinimumQuantityItem ().getMinimumQuantity ());

    // check ordering
    assertEquals (aNetAmount1, pg.getAllItems ().get (0).getUnitNetAmount ());
    assertEquals (aNetAmount3, pg.getAllItems ().get (1).getUnitNetAmount ());
    assertEquals (aNetAmount5, pg.getAllItems ().get (2).getUnitNetAmount ());

    assertEquals (p1, pg.getSinglePriceOfQuantity (1, aVATItem));
    assertEquals (p1, pg.getSinglePriceOfQuantity (2, aVATItem));
    assertEquals (p3, pg.getSinglePriceOfQuantity (3, aVATItem));
    assertEquals (p3, pg.getSinglePriceOfQuantity (4, aVATItem));
    assertEquals (p5, pg.getSinglePriceOfQuantity (5, aVATItem));
    assertEquals (p5, pg.getSinglePriceOfQuantity (70000, aVATItem));

    assertEquals (p1, pg.getTotalPriceOfQuantity (1, aVATItem));
    assertEquals (p1.getMultiplied (MathHelper.toBigDecimal ("2")), pg.getTotalPriceOfQuantity (2, aVATItem));
    assertEquals (p3.getMultiplied (MathHelper.toBigDecimal ("3")), pg.getTotalPriceOfQuantity (3, aVATItem));
    assertEquals (p3.getMultiplied (MathHelper.toBigDecimal ("4")), pg.getTotalPriceOfQuantity (4, aVATItem));
    assertEquals (p5.getMultiplied (MathHelper.toBigDecimal ("5")), pg.getTotalPriceOfQuantity (5, aVATItem));
    assertEquals (p5.getMultiplied (MathHelper.toBigDecimal ("15")), pg.getTotalPriceOfQuantity (15, aVATItem));

    // try invalid
    try
    {
      // Cannot add null
      pg.addItem (null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}
    assertEquals (3, pg.getAllItems ().size ());

    try
    {
      // An item for the same minimum quantity already contained
      pg.addItem (new PriceGraduationItem (1, aNetAmount1));
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    assertEquals (3, pg.getAllItems ().size ());
  }

  @Test
  public void testAddSimple ()
  {
    final IVATItem aVATItem = VATItem.createTestItem (Locale.GERMANY, EVATItemType.REGULAR, MathHelper.toBigDecimal ("20"));
    final IMutablePriceGraduation pg = new PriceGraduation (ECurrency.GBP);
    assertEquals (ECurrency.GBP, pg.getCurrency ());
    assertTrue (pg.isEmpty ());
    assertTrue (pg.addItem (1, MathHelper.toBigDecimal ("19.9")).isChanged ());
    assertTrue (pg.addItem (5, MathHelper.toBigDecimal ("14.9")).isChanged ());
    CommonsAssert.assertEquals (19.9, pg.getSinglePriceOfQuantity (1, aVATItem).getNetAmount ().getValue ().doubleValue ());
    assertEquals (ECurrency.GBP, pg.getSinglePriceOfQuantity (1, aVATItem).getCurrency ());
    assertEquals (aVATItem, pg.getSinglePriceOfQuantity (1, aVATItem).getVATItem ());
  }

  @Test
  public void testStdMethods ()
  {
    final IMutablePriceGraduation pg1 = new PriceGraduation (CurrencyHelper.DEFAULT_CURRENCY);
    final IMutablePriceGraduation pg2 = new PriceGraduation (CurrencyHelper.DEFAULT_CURRENCY);
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (pg1, pg2);

    final BigDecimal aNetAmount1 = MathHelper.toBigDecimal ("19.9");
    pg1.addItem (new PriceGraduationItem (1, aNetAmount1));
    pg2.addItem (new PriceGraduationItem (1, aNetAmount1));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (pg1, pg2);

    for (int i = 2; i <= 100; ++i)
    {
      final BigDecimal aNetAmount = MathHelper.toBigDecimal ("19.9").add (MathHelper.toBigDecimal (i));
      pg1.addItem (new PriceGraduationItem (i, aNetAmount));
      pg2.addItem (new PriceGraduationItem (i, aNetAmount));
      CommonsTestHelper.testDefaultImplementationWithEqualContentObject (pg1, pg2);
    }
  }

  @Test
  public void testStatic ()
  {
    final IMutablePriceGraduation pg = PriceGraduation.createSimple (new Price (ECurrency.AMD,
                                                                                MathHelper.toBigDecimal ("4.9"),
                                                                                VATManager.VATTYPE_NONE));
    assertNotNull (pg);
    assertFalse (pg.isEmpty ());
    assertEquals (1, pg.getAllItems ().size ());
  }
}
