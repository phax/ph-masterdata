/*
 * Copyright (C) 2014-2022 Philip Helger (www.helger.com)
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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.math.MathHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.system.EJavaVersion;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link CurrencyHelper}.
 *
 * @author Philip Helger
 */
public final class CurrencyHelperTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (CurrencyHelperTest.class);
  private static final char CURRENCY_SPACE = EJavaVersion.JDK_9.isSupportedVersion () ? '\u00a0' : ' ';

  @Test
  public void testGetAll ()
  {
    assertNotNull (CurrencyHelper.getAllSupportedCurrencies ());
    assertTrue (CurrencyHelper.isSupportedCurrencyCode ("EUR"));
    assertTrue (CurrencyHelper.isSupportedCurrency (ECurrency.EUR));
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
    assertEquals (new BigDecimal ("0.67"), CurrencyHelper.getDivided (ECurrency.EUR, aBD, aBD3));
  }

  @Test
  public void testGetRounded ()
  {
    final BigDecimal aBD = new BigDecimal ("1.2355352343");
    assertEquals (new BigDecimal ("1.24"), CurrencyHelper.getRounded (ECurrency.EUR, aBD));
    assertEquals (new BigDecimal ("1.24"), CurrencyHelper.getRounded (ECurrency.PLN, aBD));

    assertEquals (new BigDecimal ("1.2"), CurrencyHelper.getRounded (ECurrency.EUR, aBD, 1));
    assertEquals (new BigDecimal ("1.2"), CurrencyHelper.getRounded (ECurrency.PLN, aBD, 1));
  }

  @Test
  public void testGetScale ()
  {
    for (final ECurrency e : ECurrency.values ())
    {
      final int nScale = CurrencyHelper.getScale (e);
      assertTrue (e.name () + " has invalid scale: " + nScale, nScale >= 0);
    }
  }

  @Test
  public void testGetPatterns ()
  {
    for (final ECurrency e : ECurrency.values ())
    {
      assertTrue (StringHelper.hasText (CurrencyHelper.getCurrencyPattern (e)));
      assertTrue (StringHelper.hasText (CurrencyHelper.getValuePattern (e)));
      assertNotNull (CurrencyHelper.getCurrencyFormat (e));
      assertNotNull (CurrencyHelper.getValueFormat (e));
    }
  }

  @Test
  public void testFormatting ()
  {
    final BigDecimal aBD = new BigDecimal ("1234.56");
    for (final ECurrency e : ECurrency.values ())
    {
      final PerCurrencySettings aPCS = CurrencyHelper.getSettings (e);
      assertNotNull (aPCS);
      if (EJavaVersion.JDK_1_8.isCurrentVersion ())
      {
        assertNotNull ("No decimal separator for " + e, aPCS.getDecimalSeparator ());
        assertNotNull ("No grouping separator for " + e, aPCS.getGroupingSeparator ());
      }
      final int nDefaultFractionDigits = aPCS.getScale ();
      if (false)
        if (LOGGER.isInfoEnabled ())
          LOGGER.info (e.getID () + " - " + nDefaultFractionDigits);

      // currency format
      assertNotNull (aPCS.getCurrencyFormat ());
      assertNotNull (aPCS.getCurrencyFormatted (BigDecimal.TEN));
      assertNotNull (aPCS.getCurrencyFormatted (BigDecimal.TEN, 3));
      assertEquals (BigDecimal.TEN, CurrencyHelper.parseCurrencyFormat (e, null, BigDecimal.TEN));
      assertEquals (BigDecimal.TEN, CurrencyHelper.parseCurrencyFormat (e, "", BigDecimal.TEN));
      assertEquals (BigDecimal.TEN, CurrencyHelper.parseCurrencyFormat (e, "    ", BigDecimal.TEN));
      {
        final BigDecimal aExpected = aBD.setScale (nDefaultFractionDigits, aPCS.getRoundingMode ());
        final BigDecimal aParsed = CurrencyHelper.parseCurrencyFormat (e,
                                                                       aPCS.getCurrencyFormatted (aBD),
                                                                       BigDecimal.TEN);
        // Set the correct scale!
        assertEquals ("Difference for " + aPCS + " (based on: " + aPCS.getCurrencyFormatted (aBD) + ")",
                      aExpected,
                      aParsed);
      }
      {
        final BigDecimal aParsed = CurrencyHelper.parseCurrencyFormatUnchanged (e,
                                                                                aPCS.getCurrencyFormatted (aBD),
                                                                                BigDecimal.TEN);
        // Set the correct scale!
        assertEquals (aBD.setScale (nDefaultFractionDigits, aPCS.getRoundingMode ()), aParsed);
      }

      // value format
      assertNotNull (aPCS.getValueFormat ());
      assertNotNull (aPCS.getValueFormatted (BigDecimal.TEN));
      assertNotNull (aPCS.getValueFormatted (BigDecimal.TEN, 3));
      assertEquals (BigDecimal.TEN, CurrencyHelper.parseValueFormat (e, null, BigDecimal.TEN));
      assertEquals (BigDecimal.TEN, CurrencyHelper.parseValueFormat (e, "", BigDecimal.TEN));
      assertEquals (BigDecimal.TEN, CurrencyHelper.parseValueFormat (e, "    ", BigDecimal.TEN));
      {
        final BigDecimal aParsed2 = CurrencyHelper.parseValueFormatUnchanged (e,
                                                                              aPCS.getValueFormatted (aBD),
                                                                              BigDecimal.TEN);
        // Set the correct scale!
        assertEquals (aBD.setScale (nDefaultFractionDigits, aPCS.getRoundingMode ()), aParsed2);
      }
      {
        final BigDecimal aParsed2 = CurrencyHelper.parseValueFormat (e, aPCS.getValueFormatted (aBD), BigDecimal.TEN);
        // Set the correct scale!
        assertEquals (aBD.setScale (nDefaultFractionDigits, aPCS.getRoundingMode ()), aParsed2);
      }

      // No decimal separator
      final BigDecimal FIVE = new BigDecimal ("5").setScale (nDefaultFractionDigits);
      if (EJavaVersion.JDK_1_8.isCurrentVersion ())
      {
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, "5", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, " 5", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, "5 ", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, " 5 ", BigDecimal.TEN));
      }
      if (false)
      {
        final BigDecimal MFIVE = new BigDecimal ("-5").setScale (nDefaultFractionDigits);
        assertEquals (FIVE, CurrencyHelper.parseValueFormatUnchanged (e, "+5", BigDecimal.TEN));
        assertEquals (MFIVE, CurrencyHelper.parseValueFormatUnchanged (e, "-5", BigDecimal.TEN));
      }

      if (false)
      {
        // comma as decimal separator
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, "5,0", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, " 5,0", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, "5,0 ", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, " 5,0 ", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, "5,0000", BigDecimal.TEN));
        assertEquals (FIVE,
                      CurrencyHelper.parseValueFormat (e,
                                                       "5," +
                                                          StringHelper.getRepeated ('0', nDefaultFractionDigits + 1) +
                                                          "9",
                                                       BigDecimal.TEN));

        // dot as decimal separator
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, "5.0", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, " 5.0", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, "5.0 ", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, " 5.0 ", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyHelper.parseValueFormat (e, "5.0000", BigDecimal.TEN));
        assertEquals (FIVE,
                      CurrencyHelper.parseValueFormat (e,
                                                       "5." +
                                                          StringHelper.getRepeated ('0', nDefaultFractionDigits + 1) +
                                                          "9",
                                                       BigDecimal.TEN));
      }

      // symbol
      // Later versions print also non-arabic chars
      if (EJavaVersion.JDK_1_8.isCurrentVersion ())
      {
        final String sVF = aPCS.getValueFormat ().format (5);
        assertTrue ("Searching in '" + sVF + "'", sVF.contains ("5"));
        assertFalse ("Searching in '" + sVF + "'", sVF.contains (aPCS.getCurrencySymbol ()));
        final String sCF = aPCS.getCurrencyFormat ().format (5);
        assertTrue ("Searching in '" + sCF + "'", sCF.contains ("5"));
        assertTrue ("Searching in '" + sCF + "'", sCF.contains (aPCS.getCurrencySymbol ()));
      }
    }

  }

  @Test
  public void testGetCurrencySymbol ()
  {
    for (final ECurrency e : ECurrency.values ())
      assertNotNull (CurrencyHelper.getCurrencySymbol (e));

    assertEquals ("€", CurrencyHelper.getCurrencySymbol (ECurrency.EUR));
  }

  @Test
  @SuppressFBWarnings ("TQ_NEVER_VALUE_USED_WHERE_ALWAYS_REQUIRED")
  public void testPLN ()
  {
    final ECurrency e = ECurrency.PLN;
    assertEquals ("PLN", e.getID ());
    assertEquals ("zł", CurrencyHelper.getCurrencySymbol (e));
    assertEquals (2, e.getAsCurrency ().getDefaultFractionDigits ());
    if (EJavaVersion.JDK_9.isSupportedVersion ())
    {
      assertEquals (2, CurrencyHelper.getMinimumFractionDigits (e));
      assertEquals ("5,00" + CURRENCY_SPACE + "zł", CurrencyHelper.getCurrencyFormat (e).format (5));
      assertEquals ("5,00", CurrencyHelper.getValueFormat (e).format (5));
      assertEquals ("5,10" + CURRENCY_SPACE + "zł", CurrencyHelper.getCurrencyFormat (e).format (5.1));
      assertEquals ("5,10", CurrencyHelper.getValueFormat (e).format (5.1));
    }
    else
    {
      assertEquals (0, CurrencyHelper.getMinimumFractionDigits (e));
      assertEquals ("5" + CURRENCY_SPACE + "zł", CurrencyHelper.getCurrencyFormat (e).format (5));
      assertEquals ("5", CurrencyHelper.getValueFormat (e).format (5));
      assertEquals ("5,1" + CURRENCY_SPACE + "zł", CurrencyHelper.getCurrencyFormat (e).format (5.1));
      assertEquals ("5,1", CurrencyHelper.getValueFormat (e).format (5.1));
    }
    assertEquals ("5,12" + CURRENCY_SPACE + "zł", CurrencyHelper.getCurrencyFormat (e).format (5.123));
    assertEquals ("5,12", CurrencyHelper.getValueFormat (e).format (5.123));

    CurrencyHelper.setMinimumFractionDigits (e, 2);
    assertEquals (2, CurrencyHelper.getMinimumFractionDigits (e));

    assertEquals ("5,00" + CURRENCY_SPACE + "zł", CurrencyHelper.getCurrencyFormat (e).format (5));
    assertEquals ("5,00", CurrencyHelper.getValueFormat (e).format (5));
    assertEquals ("5,10" + CURRENCY_SPACE + "zł", CurrencyHelper.getCurrencyFormat (e).format (5.1));
    assertEquals ("5,10", CurrencyHelper.getValueFormat (e).format (5.1));
    assertEquals ("5,12" + CURRENCY_SPACE + "zł", CurrencyHelper.getCurrencyFormat (e).format (5.123));
    assertEquals ("5,12", CurrencyHelper.getValueFormat (e).format (5.123));

    CurrencyHelper.setMinimumFractionDigits (e, 0);
    assertEquals (0, CurrencyHelper.getMinimumFractionDigits (e));

    try
    {
      CurrencyHelper.setMinimumFractionDigits (e, -1);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    assertEquals (0, CurrencyHelper.getMinimumFractionDigits (e));
  }

  @Test
  public void testForint ()
  {
    final ECurrency e = ECurrency.HUF;
    assertEquals ("HUF", e.getID ());

    final PerCurrencySettings aPCS = CurrencyHelper.getSettings (e);
    assertNotNull (aPCS);
    assertNotNull (aPCS.getDecimalSeparator ());
    assertNotNull (aPCS.getGroupingSeparator ());

    assertEquals ("Ft", CurrencyHelper.getCurrencySymbol (e));
    assertEquals (2, e.getAsCurrency ().getDefaultFractionDigits ());
    if (EJavaVersion.JDK_9.isSupportedVersion ())
    {
      assertEquals ("5,00" + CURRENCY_SPACE + "Ft", CurrencyHelper.getCurrencyFormat (e).format (5));
      assertEquals ("5,00", CurrencyHelper.getValueFormat (e).format (5));
      assertEquals ("5,10" + CURRENCY_SPACE + "Ft", CurrencyHelper.getCurrencyFormat (e).format (5.1));
      assertEquals ("5,10", CurrencyHelper.getValueFormat (e).format (5.1));
    }
    else
    {
      assertEquals ("5" + CURRENCY_SPACE + "Ft", CurrencyHelper.getCurrencyFormat (e).format (5));
      assertEquals ("5", CurrencyHelper.getValueFormat (e).format (5));
      assertEquals ("5,1" + CURRENCY_SPACE + "Ft", CurrencyHelper.getCurrencyFormat (e).format (5.1));
      assertEquals ("5,1", CurrencyHelper.getValueFormat (e).format (5.1));
    }
    assertEquals ("5,12" + CURRENCY_SPACE + "Ft", CurrencyHelper.getCurrencyFormat (e).format (5.123));
    assertEquals ("5,12", CurrencyHelper.getValueFormat (e).format (5.123));
  }
}
