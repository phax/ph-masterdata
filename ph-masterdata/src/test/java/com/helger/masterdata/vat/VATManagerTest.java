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
package com.helger.masterdata.vat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Locale;
import java.util.Map;

import org.junit.Test;

import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.locale.country.CountryCache;

/**
 * Test class for class {@link VATManager}.
 *
 * @author Philip Helger
 */
public final class VATManagerTest
{
  @Test
  public void testInit ()
  {
    final VATManager aVATMgr = VATManager.getDefaultInstance ();
    assertNotNull (aVATMgr);
    assertFalse (aVATMgr.getAllAvailableCountries ().isEmpty ());
    final Locale aFirstCountry = aVATMgr.getAllAvailableCountries ().iterator ().next ();
    assertNotNull (aFirstCountry);
    assertNotNull (aVATMgr.getAllVATItemsForCountry (aFirstCountry));
  }

  @Test
  public void testExport ()
  {
    final VATManager aVATMgr = VATManager.getDefaultInstance ();
    for (final Locale aCountry : aVATMgr.getAllAvailableCountries ())
    {
      for (final Map.Entry <String, IVATItem> aEntry : aVATMgr.getAllVATItemsForCountry (aCountry).entrySet ())
      {
        assertNotNull (aEntry.getKey ());
        assertNotNull (aEntry.getValue ());
      }
    }
  }

  @Test
  public void testHU ()
  {
    final VATManager aVATMgr = VATManager.getDefaultInstance ();
    final Map <String, IVATItem> aData = aVATMgr.getAllVATItemsForCountry (CountryCache.getInstance ().getCountry ("hu"));
    assertNotNull (aData);

    IVATItem aItem = aData.get ("hu.v25");
    assertNotNull (aItem);
    assertEquals (BigDecimal.valueOf (25), aItem.getPercentage ());
    assertNull (aItem.getPeriod ().getStart ());
    assertEquals (PDTFactory.createLocalDate (2011, Month.DECEMBER, 31), aItem.getPeriod ().getEnd ());

    aItem = aData.get ("hu.v27");
    assertNotNull (aItem);
    assertEquals (PDTFactory.createLocalDate (2012, Month.JANUARY, 1), aItem.getPeriod ().getStart ());
    assertNull (aItem.getPeriod ().getEnd ());
  }

  @Test
  public void testDE ()
  {
    final VATManager aVATMgr = VATManager.getDefaultInstance ();
    final Map <String, IVATItem> aData = aVATMgr.getAllVATItemsForCountry (CountryCache.getInstance ().getCountry ("de"));
    assertNotNull (aData);

    IVATItem aItem = aData.get ("de.v16");
    assertNotNull (aItem);
    assertEquals (BigDecimal.valueOf (16), aItem.getPercentage ());
    assertEquals (PDTFactory.createLocalDate (2020, Month.JULY, 1), aItem.getPeriod ().getStart ());
    assertEquals (PDTFactory.createLocalDate (2020, Month.DECEMBER, 31), aItem.getPeriod ().getEnd ());

    aItem = aData.get ("de.v19");
    assertNotNull (aItem);
    assertNull (aItem.getPeriod ().getStart ());
    assertNull (aItem.getPeriod ().getEnd ());
  }
}
