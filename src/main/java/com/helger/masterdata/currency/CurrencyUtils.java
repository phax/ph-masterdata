/**
 * Copyright (C) 2006-2014 phloc systems (www.phloc.com)
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
package com.helger.masterdata.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.collections.ContainerHelper;
import com.helger.commons.locale.LocaleCache;
import com.helger.commons.locale.LocaleFormatter;

/**
 * Some currency helper methods.
 * 
 * @author Philip Helger
 */
@Immutable
public final class CurrencyUtils
{
  // Sorted set of all available currencies
  private static SortedSet <Currency> s_aAllCurrencies = new TreeSet <Currency> (new ComparatorCurrencyCode ());
  private static Map <Locale, Currency> s_aLocaleToCurrency = new HashMap <Locale, Currency> ();

  static
  {
    // For all locales
    for (final Locale aLocale : LocaleCache.getAllLocales ())
    {
      try
      {
        final Currency aCurrency = Currency.getInstance (aLocale);
        if (aCurrency != null)
        {
          s_aAllCurrencies.add (aCurrency);
          s_aLocaleToCurrency.put (aLocale, aCurrency);
        }
      }
      catch (final IllegalArgumentException ex)
      {
        // No currency present for locale
      }
    }
  }

  private CurrencyUtils ()
  {}

  @Nonnull
  @ReturnsMutableCopy
  public static Set <Currency> getAllSupportedCurrencies ()
  {
    return new TreeSet <Currency> (s_aAllCurrencies);
  }

  public static boolean isSupportedCurrency (@Nullable final Currency aCurrency)
  {
    return aCurrency != null && s_aAllCurrencies.contains (aCurrency);
  }

  public static boolean isSupportedCurrency (@Nullable final ECurrency eCurrency)
  {
    return eCurrency != null && isSupportedCurrency (eCurrency.getAsCurrency ());
  }

  public static boolean isSupportedCurrencyCode (@Nonnull final String sCurrencyCode)
  {
    try
    {
      return isSupportedCurrency (Currency.getInstance (sCurrencyCode));
    }
    catch (final IllegalArgumentException ex)
    {
      // No such currency code
      return false;
    }
  }

  /**
   * Check if a currency could be available for the given locale.
   * 
   * @param aLocale
   *        The locale to check.
   * @return <code>true</code> if a currency is available for the given locale,
   *         <code>false</code> otherwise.
   */
  public static boolean localeSupportsCurrencyRetrieval (@Nullable final Locale aLocale)
  {
    return aLocale != null && aLocale.getCountry () != null && aLocale.getCountry ().length () == 2;
  }

  @Nullable
  public static Currency getCurrencyOfLocale (@Nonnull final Locale aContentLocale)
  {
    if (!localeSupportsCurrencyRetrieval (aContentLocale))
      throw new IllegalArgumentException ("Cannot get currency of locale " + aContentLocale);

    return s_aLocaleToCurrency.get (aContentLocale);
  }

  /**
   * @return A map from {@link Locale} to {@link Currency} as offered by the
   *         JDK.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static Map <Locale, Currency> getLocaleToCurrencyMap ()
  {
    return ContainerHelper.newMap (s_aLocaleToCurrency);
  }

  /**
   * Parse a currency value from string using the currency default rounding
   * mode.<br>
   * Source:
   * <code>http://wheelworkshop.blogspot.com/2006/02/parsing-currency-into-bigdecimal.html</code>
   * 
   * @param sStr
   *        The string to be parsed.
   * @param aFormat
   *        The formatting object to be used. May not be <code>null</code>.
   * @param aDefault
   *        The default value to be returned, if parsing failed.
   * @return Either default value or the {@link BigDecimal} value with the
   *         correct scaling.
   */
  @Nullable
  public static BigDecimal parseCurrency (@Nullable final String sStr,
                                          @Nonnull final DecimalFormat aFormat,
                                          @Nullable final BigDecimal aDefault)
  {
    return parseCurrency (sStr, aFormat, aDefault, ECurrency.DEFAULT_ROUNDING_MODE);
  }

  /**
   * Parse a currency value from string using a custom rounding mode.
   * 
   * @param sStr
   *        The string to be parsed.
   * @param aFormat
   *        The formatting object to be used. May not be <code>null</code>.
   * @param aDefault
   *        The default value to be returned, if parsing failed.
   * @param eRoundingMode
   *        The rounding mode to be used. May not be <code>null</code>.
   * @return Either default value or the {@link BigDecimal} value with the
   *         correct scaling.
   */
  @Nullable
  public static BigDecimal parseCurrency (@Nullable final String sStr,
                                          @Nonnull final DecimalFormat aFormat,
                                          @Nullable final BigDecimal aDefault,
                                          @Nonnull final RoundingMode eRoundingMode)
  {
    // So that the call to "parse" returns a BigDecimal
    aFormat.setParseBigDecimal (true);
    aFormat.setRoundingMode (eRoundingMode);

    // Parse as double
    final BigDecimal aNum = LocaleFormatter.parseBigDecimal (sStr, aFormat);
    if (aNum == null)
      return aDefault;

    // And finally do the correct scaling, depending of the decimal format
    // fraction
    return aNum.setScale (aFormat.getMaximumFractionDigits (), eRoundingMode);
  }
}
