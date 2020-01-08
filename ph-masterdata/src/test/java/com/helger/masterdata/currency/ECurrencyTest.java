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
package com.helger.masterdata.currency;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.collection.multimap.MultiHashMapHashSetBased;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.locale.country.CountryCache;
import com.helger.masterdata.locale.EContinent;

public final class ECurrencyTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (ECurrencyTest.class);
  private static final Locale L_DE = new Locale ("de");
  private static final Locale L_EN = new Locale ("en");

  @Test
  public void testGetFromIDOrNull ()
  {
    for (final ECurrency eCurrency : ECurrency.values ())
      assertSame (eCurrency, ECurrency.getFromIDOrNull (eCurrency.getID ()));
    assertNull (ECurrency.getFromIDOrNull (null));
    assertNull (ECurrency.getFromIDOrNull ("Does not exist!"));
  }

  @Test
  public void testGetFromIDOrDefault ()
  {
    for (final ECurrency eCurrency : ECurrency.values ())
      assertSame (eCurrency, ECurrency.getFromIDOrDefault (eCurrency.getID (), ECurrency.ALL));
    assertSame (ECurrency.ALL, ECurrency.getFromIDOrDefault (null, ECurrency.ALL));
    assertSame (ECurrency.ALL, ECurrency.getFromIDOrDefault ("Does not exist!", ECurrency.ALL));
  }

  @Test
  public void testAsCurrency ()
  {
    for (final ECurrency eCurrency : ECurrency.values ())
      if (eCurrency.getAsCurrency () != null)
        assertEquals (eCurrency.getID (), eCurrency.getAsCurrency ().getCurrencyCode ());
    assertEquals ("EUR", ECurrency.EUR.getAsCurrency ().getCurrencyCode ());
  }

  @Test
  public void testGetDisplayText ()
  {
    for (final ECurrency eCurrency : ECurrency.values ())
    {
      assertNotNull (eCurrency.getDisplayText (L_DE));
      assertNotNull (eCurrency.getDisplayText (L_EN));
    }
  }

  @Test
  public void testGetAllMatchingCountries ()
  {
    for (final ECurrency eCurrency : ECurrency.values ())
    {
      assertFalse (eCurrency.getAllMatchingLocales ().isEmpty ());
      for (final Locale aCountry : eCurrency.getAllMatchingLocales ())
        assertNotNull (aCountry);
    }
  }

  @Test
  public void testGetDisplayName ()
  {
    for (final ECurrencyName e : ECurrencyName.values ())
    {
      assertSame (e, ECurrencyName.valueOf (e.name ()));
      assertTrue (e.ordinal () >= 0);
    }
  }

  @SuppressWarnings ("deprecation")
  @Test
  public void testIsDeprecated ()
  {
    assertTrue (ECurrency.EEK.isDeprecated ());
    assertFalse (ECurrency.EUR.isDeprecated ());
    assertTrue (ECurrency.getAllCurrencies (ECurrency.filterDeprecated ()).contains (ECurrency.EEK));
    assertFalse (ECurrency.getAllCurrencies ().contains (ECurrency.EEK));
  }

  @Test
  public void testGetFromCountry ()
  {
    // The following countries have multiple currencies:
    final Locale aLocCuba = CountryCache.getInstance ().getCountry ("CU");

    for (final ECurrency e : ECurrency.values ())
      if (!e.isDeprecated ())
        for (final Locale aLocale : e.getAllMatchingLocales ())
          if (!EqualsHelper.equals (aLocale, aLocCuba))
            assertSame (e, ECurrency.getFromLocaleOrNull (aLocale));
  }

  @Test
  public void testGetAllCurrenciesWithLocaleFilter ()
  {
    List <ECurrency> aSelected = ECurrency.getAllCurrencies (ECurrency.filterLocaleAny (EContinent.filterLocaleCountryOnContinent (EContinent.EUROPE)));
    assertNotNull (aSelected);
    assertTrue (aSelected.contains (ECurrency.EUR));
    assertTrue (aSelected.contains (ECurrency.CHF));
    assertFalse (aSelected.contains (ECurrency.USD));
    assertFalse (aSelected.contains (ECurrency.CNY));

    aSelected = ECurrency.getAllCurrencies (ECurrency.filterLocaleAny (EContinent.filterLocaleCountryOnContinent (EContinent.EUROPE)));
    assertNotNull (aSelected);
    assertTrue (aSelected.contains (ECurrency.EUR));
    assertTrue (aSelected.contains (ECurrency.CHF));
    assertFalse (aSelected.contains (ECurrency.USD));
    assertFalse (aSelected.contains (ECurrency.CNY));

    aSelected = ECurrency.getAllCurrencies (ECurrency.filterLocaleAny (EContinent.filterLocaleCountryOnContinent (EContinent.NORTH_AMERICA)));
    assertNotNull (aSelected);
    // Used in french over sea areas :)
    assertTrue (aSelected.contains (ECurrency.EUR));
    assertFalse (aSelected.contains (ECurrency.CHF));
    assertTrue (aSelected.contains (ECurrency.USD));
    assertFalse (aSelected.contains (ECurrency.CNY));

    aSelected = ECurrency.getAllCurrencies (ECurrency.filterLocaleAny (EContinent.filterLocaleCountryOnAnyContinent (EContinent.EUROPE,
                                                                                                                     EContinent.NORTH_AMERICA)));
    assertNotNull (aSelected);
    assertTrue (aSelected.contains (ECurrency.EUR));
    assertTrue (aSelected.contains (ECurrency.USD));
    assertFalse (aSelected.contains (ECurrency.CNY));

    aSelected = ECurrency.getAllCurrencies (ECurrency.filterLocaleAny (EContinent.filterLocaleCountryOnAnyContinent (EContinent.EUROPE,
                                                                                                                     EContinent.ASIA,
                                                                                                                     EContinent.NORTH_AMERICA)));
    assertNotNull (aSelected);
    assertTrue (aSelected.contains (ECurrency.EUR));
    assertTrue (aSelected.contains (ECurrency.USD));
    assertTrue (aSelected.contains (ECurrency.CNY));
  }

  @Test
  @Ignore
  public void testGetMissingCurrencies ()
  {
    final ICommonsMap <Locale, Currency> aMap = CurrencyHelper.getLocaleToCurrencyMap ();

    final MultiHashMapHashSetBased <Currency, Locale> aAllOfCurrency = new MultiHashMapHashSetBased <> ();
    for (final Map.Entry <Locale, Currency> aEntry : aMap.entrySet ())
    {
      if (ECurrency.findFirst (ECurrency.filterContainsLocale (aEntry.getKey ())) == null)
      {
        aAllOfCurrency.putSingle (aEntry.getValue (), aEntry.getKey ());
      }
    }
    final StringBuilder aSB = new StringBuilder ();
    for (final Map.Entry <Currency, ICommonsSet <Locale>> a : aAllOfCurrency.getSortedByKey (Comparator.comparing (Currency::getCurrencyCode))
                                                                            .entrySet ())
    {
      final StringBuilder aLocale = new StringBuilder ();
      for (final Locale aLoc : a.getValue ().getSorted (Comparator.comparing (Locale::toString)))
      {
        if (aLocale.length () > 0)
          aLocale.append (',');
        aLocale.append ('"').append (aLoc.toString ()).append ('"');
      }
      final String sID = a.getKey ().getCurrencyCode ();
      aSB.append (sID +
                  " (Currency.getInstance (\"" +
                  sID +
                  "\"), ECurrencyName." +
                  sID +
                  ", " +
                  aLocale.toString () +
                  "),");
    }
    LOGGER.info (aSB.toString ());
  }
}
