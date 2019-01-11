/**
 * Copyright (C) 2014-2019 Philip Helger (www.helger.com)
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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Locale;

import org.junit.Test;

import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.locale.country.CountryCache;
import com.helger.commons.math.MathHelper;
import com.helger.commons.mock.CommonsAssert;
import com.helger.commons.state.EChange;
import com.helger.masterdata.currency.CurrencyHelper;
import com.helger.masterdata.currency.ECurrency;
import com.helger.masterdata.currencyvalue.CurrencyValue;
import com.helger.masterdata.currencyvalue.ICurrencyValue;
import com.helger.masterdata.vat.EVATItemType;
import com.helger.masterdata.vat.IVATItem;
import com.helger.masterdata.vat.VATItem;
import com.helger.masterdata.vat.VATManager;

/**
 * Test class for class {@link Price}.
 *
 * @author Philip Helger
 */
public final class PriceTest
{
  @Test
  public void testAll ()
  {
    final Price p = new Price (CurrencyHelper.DEFAULT_CURRENCY,
                               MathHelper.toBigDecimal ("9.9"),
                               VATManager.VATTYPE_NONE);
    assertEquals (CurrencyHelper.DEFAULT_CURRENCY, p.getCurrency ());
    CommonsAssert.assertEquals (9.9, p.getNetAmount ().getValue ().doubleValue ());
    assertEquals (VATManager.VATTYPE_NONE, p.getVATItem ());

    // Setter
    assertEquals (EChange.CHANGED, p.getNetAmount ().setCurrency (ECurrency.AMD));
    assertEquals (ECurrency.AMD, p.getCurrency ());
    assertEquals (EChange.UNCHANGED, p.getNetAmount ().setCurrency (ECurrency.AMD));
    assertEquals (ECurrency.AMD, p.getCurrency ());
    assertEquals (EChange.UNCHANGED,
                  p.setNetAmount (new CurrencyValue (ECurrency.AMD, MathHelper.toBigDecimal ("9.9"))));
    assertEquals (EChange.CHANGED, p.getNetAmount ().setCurrency (CurrencyHelper.DEFAULT_CURRENCY));
    assertEquals (CurrencyHelper.DEFAULT_CURRENCY, p.getCurrency ());

    final IVATItem aVATItem = VATItem.createTestItem (Locale.GERMANY,
                                                      EVATItemType.REGULAR,
                                                      MathHelper.toBigDecimal ("50"));
    assertEquals (EChange.CHANGED, p.setVATItem (aVATItem));
    assertEquals (aVATItem, p.getVATItem ());
    assertEquals (EChange.UNCHANGED, p.setVATItem (aVATItem));
    CommonsAssert.assertEquals (9.9, p.getNetAmount ().getValue ().doubleValue ());
    // 14.85 = 9.9 * 1.5 (50%)
    CommonsAssert.assertEquals (14.85, p.getGrossAmount ().getValue ().doubleValue ());
    assertEquals (EChange.CHANGED, p.setVATItem (VATManager.VATTYPE_NONE));
    CommonsAssert.assertEquals (9.9, p.getNetAmount ().getValue ().doubleValue ());
    CommonsAssert.assertEquals (9.9, p.getGrossAmount ().getValue ().doubleValue ());

    assertEquals (p, p.getMultiplied (BigDecimal.ONE));

    try
    {
      // null not allowed
      p.setNetAmount ((ICurrencyValue) null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}
    try
    {
      // null not allowed
      p.setNetAmount ((CurrencyValue) null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}

    try
    {
      // null not allowed
      p.setVATItem (null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}
  }

  @Test
  public void testFactories ()
  {
    final IVATItem aVATItem = VATManager.getDefaultInstance ().getVATItemOfID ("at.v20");
    assertNotNull (aVATItem);
    Price p = Price.createFromNetAmount (ECurrency.EUR, MathHelper.toBigDecimal ("10"), aVATItem);
    assertTrue (EqualsHelper.equals (MathHelper.toBigDecimal ("10"), p.getNetAmount ().getValue ()));
    assertTrue (EqualsHelper.equals (MathHelper.toBigDecimal ("12"), p.getGrossAmount ().getValue ()));

    p = Price.createFromGrossAmount (ECurrency.EUR, MathHelper.toBigDecimal ("12"), aVATItem);
    assertTrue (EqualsHelper.equals (MathHelper.toBigDecimal ("10"), p.getNetAmount ().getValue ()));
    assertTrue (EqualsHelper.equals (MathHelper.toBigDecimal ("12"), p.getGrossAmount ().getValue ()));
  }

  @Test
  public void testFactoriesArbitrary ()
  {
    final Locale aAT = CountryCache.getInstance ().getCountry ("AT");
    for (final String sNumber : new String [] { "1", "10", "12", "3.66666", "0.000555" })
      for (final IVATItem aVATItem : VATManager.getDefaultInstance ().getAllVATItemsForCountry (aAT).values ())
      {
        Price p = Price.createFromNetAmount (ECurrency.EUR, MathHelper.toBigDecimal (sNumber), aVATItem);
        assertNotNull (p);

        p = Price.createFromGrossAmount (ECurrency.EUR, MathHelper.toBigDecimal (sNumber), aVATItem);
        assertNotNull (p);
      }
  }
}
