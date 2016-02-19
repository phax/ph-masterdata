/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;

import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.ext.ICommonsSet;
import com.helger.commons.collection.multimap.IMultiMapSetBased;
import com.helger.commons.collection.multimap.MultiHashMapHashSetBased;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.locale.country.CountryCache;
import com.helger.commons.math.MathHelper;
import com.helger.commons.mock.AbstractCommonsTestCase;
import com.helger.commons.string.StringHelper;
import com.helger.masterdata.locale.EContinent;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public final class ECurrencyTest extends AbstractCommonsTestCase
{
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
  public void testGetCurrencyFormat ()
  {
    for (final ECurrency eCurrency : ECurrency.values ())
      assertNotNull (eCurrency.getCurrencyFormat ());

    assertEquals ("EUR", ECurrency.EUR.getAsCurrency ().getCurrencyCode ());
  }

  @Test
  public void testFormatting ()
  {
    final BigDecimal aBD = new BigDecimal ("1234.56");
    for (final ECurrency eCurrency : ECurrency.values ())
    {
      final int nDefaultFractionDigits = eCurrency.getScale ();
      if (false)
        System.out.println (eCurrency.getID () + " - " + nDefaultFractionDigits);

      // currency format
      assertNotNull (eCurrency.getCurrencyFormat ());
      assertNotNull (eCurrency.getCurrencyFormatted (BigDecimal.TEN));
      assertNotNull (eCurrency.getCurrencyFormatted (BigDecimal.TEN, 3));
      assertEquals (BigDecimal.TEN, eCurrency.parseCurrencyFormat (null, BigDecimal.TEN));
      assertEquals (BigDecimal.TEN, eCurrency.parseCurrencyFormat ("", BigDecimal.TEN));
      assertEquals (BigDecimal.TEN, eCurrency.parseCurrencyFormat ("    ", BigDecimal.TEN));
      {
        final BigDecimal aParsed = eCurrency.parseCurrencyFormat (eCurrency.getCurrencyFormatted (aBD), BigDecimal.TEN);
        // Set the correct scale!
        assertEquals (aBD.setScale (nDefaultFractionDigits, eCurrency.getRoundingMode ()), aParsed);
      }
      {
        final BigDecimal aParsed = eCurrency.parseCurrencyFormatUnchanged (eCurrency.getCurrencyFormatted (aBD),
                                                                           BigDecimal.TEN);
        // Set the correct scale!
        assertEquals (aBD.setScale (nDefaultFractionDigits, eCurrency.getRoundingMode ()), aParsed);
      }

      // value format
      assertNotNull (eCurrency.getValueFormat ());
      assertNotNull (eCurrency.getValueFormatted (BigDecimal.TEN));
      assertNotNull (eCurrency.getValueFormatted (BigDecimal.TEN, 3));
      assertEquals (BigDecimal.TEN, eCurrency.parseValueFormat (null, BigDecimal.TEN));
      assertEquals (BigDecimal.TEN, eCurrency.parseValueFormat ("", BigDecimal.TEN));
      assertEquals (BigDecimal.TEN, eCurrency.parseValueFormat ("    ", BigDecimal.TEN));
      {
        final BigDecimal aParsed2 = eCurrency.parseValueFormatUnchanged (eCurrency.getValueFormatted (aBD),
                                                                         BigDecimal.TEN);
        // Set the correct scale!
        assertEquals (aBD.setScale (nDefaultFractionDigits, eCurrency.getRoundingMode ()), aParsed2);
      }
      {
        final BigDecimal aParsed2 = eCurrency.parseValueFormat (eCurrency.getValueFormatted (aBD), BigDecimal.TEN);
        // Set the correct scale!
        assertEquals (aBD.setScale (nDefaultFractionDigits, eCurrency.getRoundingMode ()), aParsed2);
      }

      // No decimal separator
      final BigDecimal FIVE = new BigDecimal ("5").setScale (nDefaultFractionDigits);
      assertEquals (FIVE, eCurrency.parseValueFormat ("5", BigDecimal.TEN));
      assertEquals (FIVE, eCurrency.parseValueFormat (" 5", BigDecimal.TEN));
      assertEquals (FIVE, eCurrency.parseValueFormat ("5 ", BigDecimal.TEN));
      assertEquals (FIVE, eCurrency.parseValueFormat (" 5 ", BigDecimal.TEN));
      if (false)
      {
        final BigDecimal MFIVE = new BigDecimal ("-5").setScale (nDefaultFractionDigits);
        assertEquals (FIVE, eCurrency.parseValueFormatUnchanged ("+5", BigDecimal.TEN));
        assertEquals (MFIVE, eCurrency.parseValueFormatUnchanged ("-5", BigDecimal.TEN));
      }

      if (false)
      {
        // comma as decimal separator
        assertEquals (FIVE, eCurrency.parseValueFormat ("5,0", BigDecimal.TEN));
        assertEquals (FIVE, eCurrency.parseValueFormat (" 5,0", BigDecimal.TEN));
        assertEquals (FIVE, eCurrency.parseValueFormat ("5,0 ", BigDecimal.TEN));
        assertEquals (FIVE, eCurrency.parseValueFormat (" 5,0 ", BigDecimal.TEN));
        assertEquals (FIVE, eCurrency.parseValueFormat ("5,0000", BigDecimal.TEN));
        assertEquals (FIVE,
                      eCurrency.parseValueFormat ("5," +
                                                  StringHelper.getRepeated ('0', nDefaultFractionDigits + 1) +
                                                  "9",
                                                  BigDecimal.TEN));

        // dot as decimal separator
        assertEquals (FIVE, eCurrency.parseValueFormat ("5.0", BigDecimal.TEN));
        assertEquals (FIVE, eCurrency.parseValueFormat (" 5.0", BigDecimal.TEN));
        assertEquals (FIVE, eCurrency.parseValueFormat ("5.0 ", BigDecimal.TEN));
        assertEquals (FIVE, eCurrency.parseValueFormat (" 5.0 ", BigDecimal.TEN));
        assertEquals (FIVE, eCurrency.parseValueFormat ("5.0000", BigDecimal.TEN));
        assertEquals (FIVE,
                      eCurrency.parseValueFormat ("5." +
                                                  StringHelper.getRepeated ('0', nDefaultFractionDigits + 1) +
                                                  "9",
                                                  BigDecimal.TEN));
      }

      // symbol
      assertTrue (eCurrency.getValueFormat ().format (5).contains ("5"));
      assertFalse (eCurrency.getValueFormat ().format (5).contains (eCurrency.getCurrencySymbol ()));
      assertTrue (eCurrency.getCurrencyFormat ().format (5).contains ("5"));
      assertTrue (eCurrency.getCurrencyFormat ().format (5).contains (eCurrency.getCurrencySymbol ()));
    }
  }

  @Test
  public void testGetCurrencySymbol ()
  {
    for (final ECurrency eCurrency : ECurrency.values ())
      assertNotNull (eCurrency.getCurrencySymbol ());

    assertEquals ("€", ECurrency.EUR.getCurrencySymbol ());
  }

  @Test
  @SuppressFBWarnings ("TQ_NEVER_VALUE_USED_WHERE_ALWAYS_REQUIRED")
  public void testPLN ()
  {
    final ECurrency e = ECurrency.PLN;
    assertEquals ("PLN", e.getID ());
    assertEquals ("zł", e.getCurrencySymbol ());
    assertEquals (2, e.getAsCurrency ().getDefaultFractionDigits ());
    assertEquals ("5 zł", e.getCurrencyFormat ().format (5));
    assertEquals ("5", e.getValueFormat ().format (5));
    assertEquals ("5,1 zł", e.getCurrencyFormat ().format (5.1));
    assertEquals ("5,1", e.getValueFormat ().format (5.1));
    assertEquals ("5,12 zł", e.getCurrencyFormat ().format (5.123));
    assertEquals ("5,12", e.getValueFormat ().format (5.123));

    assertEquals (0, e.getMinimumFractionDigits ());
    e.setMinimumFractionDigits (2);
    assertEquals (2, e.getMinimumFractionDigits ());

    assertEquals ("5,00 zł", e.getCurrencyFormat ().format (5));
    assertEquals ("5,00", e.getValueFormat ().format (5));
    assertEquals ("5,10 zł", e.getCurrencyFormat ().format (5.1));
    assertEquals ("5,10", e.getValueFormat ().format (5.1));
    assertEquals ("5,12 zł", e.getCurrencyFormat ().format (5.123));
    assertEquals ("5,12", e.getValueFormat ().format (5.123));

    e.setMinimumFractionDigits (0);
    assertEquals (0, e.getMinimumFractionDigits ());

    try
    {
      e.setMinimumFractionDigits (-1);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    assertEquals (0, e.getMinimumFractionDigits ());
  }

  @Test
  public void testForint ()
  {
    final ECurrency e = ECurrency.HUF;
    assertEquals ("HUF", e.getID ());
    assertEquals ("Ft", e.getCurrencySymbol ());
    assertEquals (2, e.getAsCurrency ().getDefaultFractionDigits ());
    assertEquals ("5 Ft", e.getCurrencyFormat ().format (5));
    assertEquals ("5", e.getValueFormat ().format (5));
    assertEquals ("5,1 Ft", e.getCurrencyFormat ().format (5.1));
    assertEquals ("5,1", e.getValueFormat ().format (5.1));
    assertEquals ("5,12 Ft", e.getCurrencyFormat ().format (5.123));
    assertEquals ("5,12", e.getValueFormat ().format (5.123));
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
  @SuppressFBWarnings ("RV_RETURN_VALUE_IGNORED")
  public void testGetDivided ()
  {
    final BigDecimal aBD3 = MathHelper.toBigDecimal (3);
    final BigDecimal aBD = MathHelper.toBigDecimal (2);
    try
    {
      // 2/3 == 0.666666666....
      aBD.divide (aBD3);
      fail ();
    }
    catch (final ArithmeticException ex)
    {}
    // -> uses the correct rounding mode
    assertEquals (new BigDecimal ("0.67"), ECurrency.EUR.getDivided (aBD, aBD3));
  }

  @Test
  public void testGetRounded ()
  {
    final BigDecimal aBD = new BigDecimal ("1.2355352343");
    assertEquals (new BigDecimal ("1.24"), ECurrency.EUR.getRounded (aBD));
    assertEquals (new BigDecimal ("1.24"), ECurrency.PLN.getRounded (aBD));

    assertEquals (new BigDecimal ("1.2"), ECurrency.EUR.getRounded (aBD, 1));
    assertEquals (new BigDecimal ("1.2"), ECurrency.EUR.getRounded (aBD, 1));
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

  @Test
  public void testGetScale ()
  {
    for (final ECurrency e : ECurrency.values ())
      assertTrue (e.name () + " has invalid scale: " + e.getScale (), e.getScale () >= 0);
  }

  @Test
  public void testGetPatterns ()
  {
    for (final ECurrency e : ECurrency.values ())
    {
      assertTrue (StringHelper.hasText (e.getCurrencyPattern ()));
      assertTrue (StringHelper.hasText (e.getValuePattern ()));
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
    final Map <Locale, Currency> aMap = CurrencyHelper.getLocaleToCurrencyMap ();

    final IMultiMapSetBased <Currency, Locale> aAllOfCurrency = new MultiHashMapHashSetBased <Currency, Locale> ();
    for (final Map.Entry <Locale, Currency> aEntry : aMap.entrySet ())
    {
      if (ECurrency.findFirst (ECurrency.filterContainsLocale (aEntry.getKey ())) == null)
      {
        aAllOfCurrency.putSingle (aEntry.getValue (), aEntry.getKey ());
      }
    }
    for (final Map.Entry <Currency, ICommonsSet <Locale>> a : CollectionHelper.getSortedByKey (aAllOfCurrency,
                                                                                               Comparator.comparing (Currency::getCurrencyCode))
                                                                              .entrySet ())
    {
      String sLocale = "";
      for (final Locale aLoc : CollectionHelper.getSorted (a.getValue (), Comparator.comparing (Locale::toString)))
      {
        if (sLocale.length () > 0)
          sLocale += ',';
        sLocale += '"' + aLoc.toString () + '"';
      }
      final String sID = a.getKey ().getCurrencyCode ();
      System.out.println (sID +
                          " (Currency.getInstance (\"" +
                          sID +
                          "\"), ECurrencyName." +
                          sID +
                          ", " +
                          sLocale +
                          "),");
    }
  }
}
