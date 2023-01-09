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
package com.helger.masterdata.currencyvalue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.math.MathHelper;
import com.helger.commons.mock.CommonsTestHelper;
import com.helger.commons.system.EJavaVersion;
import com.helger.masterdata.currency.CurrencyHelper;
import com.helger.masterdata.currency.ECurrency;

public final class CurrencyValueTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (CurrencyValueTest.class);
  private static final char CURRENCY_SPACE = EJavaVersion.JDK_9.isSupportedVersion () ? '\u00a0' : ' ';

  @Test
  public void testGetFormatted ()
  {
    IMutableCurrencyValue aCV = new CurrencyValue (ECurrency.EUR, MathHelper.toBigDecimal (5));
    if (EJavaVersion.JDK_9.isSupportedVersion ())
      assertEquals ("5,00" + CURRENCY_SPACE + "€", aCV.getCurrencyFormatted ());
    else
      assertEquals ("€" + CURRENCY_SPACE + "5,00", aCV.getCurrencyFormatted ());
    aCV = new CurrencyValue (ECurrency.EUR, new BigDecimal ("5.12"));
    if (EJavaVersion.JDK_9.isSupportedVersion ())
      assertEquals ("5,12" + CURRENCY_SPACE + "€", aCV.getCurrencyFormatted ());
    else
      assertEquals ("€" + CURRENCY_SPACE + "5,12", aCV.getCurrencyFormatted ());
    aCV = new CurrencyValue (ECurrency.USD, new BigDecimal ("5.12"));
    assertEquals ("$5.12", aCV.getCurrencyFormatted ());

    for (final ECurrency eCurrency : ECurrency.values ())
    {
      aCV = new CurrencyValue (eCurrency, new BigDecimal ("5.12"));
      final String sCurrencyFormatted = aCV.getCurrencyFormatted ();
      assertNotNull (sCurrencyFormatted);
      final String sValueFormatted = aCV.getValueFormatted ();
      assertNotNull (sValueFormatted);
      assertTrue (sValueFormatted, sValueFormatted.indexOf (CurrencyHelper.getCurrencySymbol (eCurrency)) < 0);
      CommonsTestHelper.testGetClone (aCV);

      // There seems to be a bug in the optimizer of 1.6.0_45 so that the output
      // values are sometimes reordered - dunno why :(
      LOGGER.info ("[" + sCurrencyFormatted + "][" + sValueFormatted + "]");
    }
  }
}
