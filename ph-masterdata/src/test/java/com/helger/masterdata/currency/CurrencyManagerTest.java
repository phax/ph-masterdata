/**
 * Copyright (C) 2014-2018 Philip Helger (www.helger.com)
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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link CurrencyManager}
 *
 * @author Philip Helger
 */
public final class CurrencyManagerTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (CurrencyManagerTest.class);

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
    assertEquals (new BigDecimal ("0.67"), CurrencyManager.getDivided (ECurrency.EUR, aBD, aBD3));
  }

  @Test
  public void testGetRounded ()
  {
    final BigDecimal aBD = new BigDecimal ("1.2355352343");
    assertEquals (new BigDecimal ("1.24"), CurrencyManager.getRounded (ECurrency.EUR, aBD));
    assertEquals (new BigDecimal ("1.24"), CurrencyManager.getRounded (ECurrency.PLN, aBD));

    assertEquals (new BigDecimal ("1.2"), CurrencyManager.getRounded (ECurrency.EUR, aBD, 1));
    assertEquals (new BigDecimal ("1.2"), CurrencyManager.getRounded (ECurrency.PLN, aBD, 1));
  }

  @Test
  public void testGetScale ()
  {
    for (final ECurrency e : ECurrency.values ())
    {
      final int nScale = CurrencyManager.getScale (e);
      assertTrue (e.name () + " has invalid scale: " + nScale, nScale >= 0);
    }
  }

  @Test
  public void testGetPatterns ()
  {
    for (final ECurrency e : ECurrency.values ())
    {
      assertTrue (StringHelper.hasText (CurrencyManager.getCurrencyPattern (e)));
      assertTrue (StringHelper.hasText (CurrencyManager.getValuePattern (e)));
      assertNotNull (CurrencyManager.getCurrencyFormat (e));
      assertNotNull (CurrencyManager.getValueFormat (e));
    }
  }

  @Test
  public void testFormatting ()
  {
    final BigDecimal aBD = new BigDecimal ("1234.56");
    for (final ECurrency e : ECurrency.values ())
    {
      final PerCurrencySettings aPCS = CurrencyManager.get (e);
      final int nDefaultFractionDigits = aPCS.getScale ();
      if (false)
        if (LOGGER.isInfoEnabled ())
          LOGGER.info (e.getID () + " - " + nDefaultFractionDigits);

      // currency format
      assertNotNull (aPCS.getCurrencyFormat ());
      assertNotNull (aPCS.getCurrencyFormatted (BigDecimal.TEN));
      assertNotNull (aPCS.getCurrencyFormatted (BigDecimal.TEN, 3));
      assertEquals (BigDecimal.TEN, CurrencyManager.parseCurrencyFormat (e, null, BigDecimal.TEN));
      assertEquals (BigDecimal.TEN, CurrencyManager.parseCurrencyFormat (e, "", BigDecimal.TEN));
      assertEquals (BigDecimal.TEN, CurrencyManager.parseCurrencyFormat (e, "    ", BigDecimal.TEN));
      {
        final BigDecimal aExpected = aBD.setScale (nDefaultFractionDigits, aPCS.getRoundingMode ());
        final BigDecimal aParsed = CurrencyManager.parseCurrencyFormat (e,
                                                                        aPCS.getCurrencyFormatted (aBD),
                                                                        BigDecimal.TEN);
        // Set the correct scale!
        assertEquals ("Difference for " + aPCS + " (based on: " + aPCS.getCurrencyFormatted (aBD) + ")", aExpected, aParsed);
      }
      {
        final BigDecimal aParsed = CurrencyManager.parseCurrencyFormatUnchanged (e,
                                                                                 aPCS.getCurrencyFormatted (aBD),
                                                                                 BigDecimal.TEN);
        // Set the correct scale!
        assertEquals (aBD.setScale (nDefaultFractionDigits, aPCS.getRoundingMode ()), aParsed);
      }

      // value format
      assertNotNull (aPCS.getValueFormat ());
      assertNotNull (aPCS.getValueFormatted (BigDecimal.TEN));
      assertNotNull (aPCS.getValueFormatted (BigDecimal.TEN, 3));
      assertEquals (BigDecimal.TEN, CurrencyManager.parseValueFormat (e, null, BigDecimal.TEN));
      assertEquals (BigDecimal.TEN, CurrencyManager.parseValueFormat (e, "", BigDecimal.TEN));
      assertEquals (BigDecimal.TEN, CurrencyManager.parseValueFormat (e, "    ", BigDecimal.TEN));
      {
        final BigDecimal aParsed2 = CurrencyManager.parseValueFormatUnchanged (e,
                                                                               aPCS.getValueFormatted (aBD),
                                                                               BigDecimal.TEN);
        // Set the correct scale!
        assertEquals (aBD.setScale (nDefaultFractionDigits, aPCS.getRoundingMode ()), aParsed2);
      }
      {
        final BigDecimal aParsed2 = CurrencyManager.parseValueFormat (e,
                                                                      aPCS.getValueFormatted (aBD),
                                                                      BigDecimal.TEN);
        // Set the correct scale!
        assertEquals (aBD.setScale (nDefaultFractionDigits, aPCS.getRoundingMode ()), aParsed2);
      }

      // No decimal separator
      final BigDecimal FIVE = new BigDecimal ("5").setScale (nDefaultFractionDigits);
      assertEquals (FIVE, CurrencyManager.parseValueFormat (e, "5", BigDecimal.TEN));
      assertEquals (FIVE, CurrencyManager.parseValueFormat (e, " 5", BigDecimal.TEN));
      assertEquals (FIVE, CurrencyManager.parseValueFormat (e, "5 ", BigDecimal.TEN));
      assertEquals (FIVE, CurrencyManager.parseValueFormat (e, " 5 ", BigDecimal.TEN));
      if (false)
      {
        final BigDecimal MFIVE = new BigDecimal ("-5").setScale (nDefaultFractionDigits);
        assertEquals (FIVE, CurrencyManager.parseValueFormatUnchanged (e, "+5", BigDecimal.TEN));
        assertEquals (MFIVE, CurrencyManager.parseValueFormatUnchanged (e, "-5", BigDecimal.TEN));
      }

      if (false)
      {
        // comma as decimal separator
        assertEquals (FIVE, CurrencyManager.parseValueFormat (e, "5,0", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyManager.parseValueFormat (e, " 5,0", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyManager.parseValueFormat (e, "5,0 ", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyManager.parseValueFormat (e, " 5,0 ", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyManager.parseValueFormat (e, "5,0000", BigDecimal.TEN));
        assertEquals (FIVE,
                      CurrencyManager.parseValueFormat (e,
                                                        "5," +
                                                                   StringHelper.getRepeated ('0',
                                                                                             nDefaultFractionDigits +
                                                                                                  1) +
                                                                   "9",
                                                        BigDecimal.TEN));

        // dot as decimal separator
        assertEquals (FIVE, CurrencyManager.parseValueFormat (e, "5.0", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyManager.parseValueFormat (e, " 5.0", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyManager.parseValueFormat (e, "5.0 ", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyManager.parseValueFormat (e, " 5.0 ", BigDecimal.TEN));
        assertEquals (FIVE, CurrencyManager.parseValueFormat (e, "5.0000", BigDecimal.TEN));
        assertEquals (FIVE,
                      CurrencyManager.parseValueFormat (e,
                                                        "5." +
                                                                   StringHelper.getRepeated ('0',
                                                                                             nDefaultFractionDigits +
                                                                                                  1) +
                                                                   "9",
                                                        BigDecimal.TEN));
      }

      // symbol
      assertTrue (aPCS.getValueFormat ().format (5).contains ("5"));
      assertFalse (aPCS.getValueFormat ().format (5).contains (aPCS.getCurrencySymbol ()));
      assertTrue (aPCS.getCurrencyFormat ().format (5).contains ("5"));
      assertTrue (aPCS.getCurrencyFormat ().format (5).contains (aPCS.getCurrencySymbol ()));
    }
  }

  @Test
  public void testGetCurrencySymbol ()
  {
    for (final ECurrency e : ECurrency.values ())
      assertNotNull (CurrencyManager.getCurrencySymbol (e));

    assertEquals ("€", CurrencyManager.getCurrencySymbol (ECurrency.EUR));
  }

  @Test
  @SuppressFBWarnings ("TQ_NEVER_VALUE_USED_WHERE_ALWAYS_REQUIRED")
  public void testPLN ()
  {
    final ECurrency e = ECurrency.PLN;
    assertEquals ("PLN", e.getID ());
    assertEquals ("zł", CurrencyManager.getCurrencySymbol (e));
    assertEquals (2, e.getAsCurrency ().getDefaultFractionDigits ());
    assertEquals ("5 zł", CurrencyManager.getCurrencyFormat (e).format (5));
    assertEquals ("5", CurrencyManager.getValueFormat (e).format (5));
    assertEquals ("5,1 zł", CurrencyManager.getCurrencyFormat (e).format (5.1));
    assertEquals ("5,1", CurrencyManager.getValueFormat (e).format (5.1));
    assertEquals ("5,12 zł", CurrencyManager.getCurrencyFormat (e).format (5.123));
    assertEquals ("5,12", CurrencyManager.getValueFormat (e).format (5.123));

    assertEquals (0, CurrencyManager.getMinimumFractionDigits (e));
    CurrencyManager.setMinimumFractionDigits (e, 2);
    assertEquals (2, CurrencyManager.getMinimumFractionDigits (e));

    assertEquals ("5,00 zł", CurrencyManager.getCurrencyFormat (e).format (5));
    assertEquals ("5,00", CurrencyManager.getValueFormat (e).format (5));
    assertEquals ("5,10 zł", CurrencyManager.getCurrencyFormat (e).format (5.1));
    assertEquals ("5,10", CurrencyManager.getValueFormat (e).format (5.1));
    assertEquals ("5,12 zł", CurrencyManager.getCurrencyFormat (e).format (5.123));
    assertEquals ("5,12", CurrencyManager.getValueFormat (e).format (5.123));

    CurrencyManager.setMinimumFractionDigits (e, 0);
    assertEquals (0, CurrencyManager.getMinimumFractionDigits (e));

    try
    {
      CurrencyManager.setMinimumFractionDigits (e, -1);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
    assertEquals (0, CurrencyManager.getMinimumFractionDigits (e));
  }

  @Test
  public void testForint ()
  {
    final ECurrency e = ECurrency.HUF;
    assertEquals ("HUF", e.getID ());
    assertEquals ("Ft", CurrencyManager.getCurrencySymbol (e));
    assertEquals (2, e.getAsCurrency ().getDefaultFractionDigits ());
    assertEquals ("5 Ft", CurrencyManager.getCurrencyFormat (e).format (5));
    assertEquals ("5", CurrencyManager.getValueFormat (e).format (5));
    assertEquals ("5,1 Ft", CurrencyManager.getCurrencyFormat (e).format (5.1));
    assertEquals ("5,1", CurrencyManager.getValueFormat (e).format (5.1));
    assertEquals ("5,12 Ft", CurrencyManager.getCurrencyFormat (e).format (5.123));
    assertEquals ("5,12", CurrencyManager.getValueFormat (e).format (5.123));
  }
}
