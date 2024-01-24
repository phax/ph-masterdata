/*
 * Copyright (C) 2014-2024 Philip Helger (www.helger.com)
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
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.Currency;
import java.util.Locale;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsEnumMap;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.CommonsTreeSet;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.collection.impl.ICommonsSortedSet;
import com.helger.commons.concurrent.SimpleReadWriteLock;
import com.helger.commons.locale.LocaleCache;
import com.helger.commons.locale.LocaleParser;
import com.helger.commons.string.StringHelper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Currency helper methods. Heavily extended in 6.1.0.
 *
 * @author Philip Helger
 */
@Immutable
@SuppressFBWarnings ("JCIP_FIELD_ISNT_FINAL_IN_IMMUTABLE_CLASS")
public final class CurrencyHelper
{
  /**
   * The default rounding mode to be used for currency values. It may be
   * overridden for each currency individually.
   */
  public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;

  /** The default currency */
  public static final ECurrency DEFAULT_CURRENCY = ECurrency.EUR;

  /** The default scale to be used if no JDK Currency is available */
  public static final int DEFAULT_SCALE = 2;

  // Sorted set of all available currencies
  private static final ICommonsSortedSet <Currency> ALL_CURRENCIES = new CommonsTreeSet <> (Comparator.comparing (Currency::getCurrencyCode));
  private static final ICommonsMap <Locale, Currency> LOCALE_TO_CURRENCY = new CommonsHashMap <> ();
  private static final SimpleReadWriteLock RW_LOCK = new SimpleReadWriteLock ();
  @GuardedBy ("RW_LOCK")
  private static final ICommonsMap <ECurrency, PerCurrencySettings> SETTINGS_MAP = new CommonsEnumMap <> (ECurrency.class);

  static
  {
    // For all locales
    for (final Locale aLocale : LocaleCache.getInstance ().getAllLocales ())
    {
      try
      {
        final Currency aCurrency = Currency.getInstance (aLocale);
        if (aCurrency != null)
        {
          ALL_CURRENCIES.add (aCurrency);
          LOCALE_TO_CURRENCY.put (aLocale, aCurrency);
        }
      }
      catch (final IllegalArgumentException ex)
      {
        // No currency present for locale
      }
    }
    reinitializeCurrencySettings ();
  }

  /**
   * Reinitialize all the {@link PerCurrencySettings} to the original state.
   */
  public static void reinitializeCurrencySettings ()
  {
    RW_LOCK.writeLocked ( () -> {
      SETTINGS_MAP.clear ();
      for (final ECurrency e : ECurrency.values ())
      {
        SETTINGS_MAP.put (e, new PerCurrencySettings (e));

        for (final Locale aLocale : e.matchingLocales ())
          if (!localeSupportsCurrencyRetrieval (aLocale))
            throw new IllegalArgumentException ("Passed locale " + aLocale + " does not support currency retrieval!");
      }
    });
  }

  private CurrencyHelper ()
  {}

  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsSortedSet <Currency> getAllSupportedCurrencies ()
  {
    return ALL_CURRENCIES.getClone ();
  }

  public static boolean isSupportedCurrency (@Nullable final Currency aCurrency)
  {
    return aCurrency != null && ALL_CURRENCIES.contains (aCurrency);
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

    return LOCALE_TO_CURRENCY.get (aContentLocale);
  }

  /**
   * @return A map from {@link Locale} to {@link Currency} as offered by the
   *         JDK.
   */
  @Nonnull
  @ReturnsMutableCopy
  public static ICommonsMap <Locale, Currency> getLocaleToCurrencyMap ()
  {
    return LOCALE_TO_CURRENCY.getClone ();
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
    return parseCurrency (sStr, aFormat, aDefault, DEFAULT_ROUNDING_MODE);
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
    // Shortcut
    if (StringHelper.hasNoText (sStr))
      return aDefault;

    // So that the call to "parse" returns a BigDecimal
    aFormat.setParseBigDecimal (true);
    aFormat.setRoundingMode (eRoundingMode);

    // Parse as double
    final BigDecimal aNum = LocaleParser.parseBigDecimal (sStr, aFormat);
    if (aNum == null)
      return aDefault;

    // And finally do the correct scaling, depending of the decimal format
    // fraction
    return aNum.setScale (aFormat.getMaximumFractionDigits (), eRoundingMode);
  }

  /**
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @return Never <code>null</code>.
   */
  @Nonnull
  public static PerCurrencySettings getSettings (@Nullable final ECurrency eCurrency)
  {
    RW_LOCK.readLock ().lock ();
    try
    {
      return SETTINGS_MAP.get (eCurrency != null ? eCurrency : DEFAULT_CURRENCY);
    }
    finally
    {
      RW_LOCK.readLock ().unlock ();
    }
  }

  @Nonnull
  public static String getCurrencySymbol (@Nullable final ECurrency eCurrency)
  {
    return getSettings (eCurrency).getCurrencySymbol ();
  }

  /**
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @return The pattern to be used in {@link DecimalFormat} to format this
   *         currency. This pattern includes the currency string.
   */
  @Nonnull
  @Nonempty
  public static String getCurrencyPattern (@Nullable final ECurrency eCurrency)
  {
    return getSettings (eCurrency).getCurrencyPattern ();
  }

  /**
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @return The pattern to be used in {@link DecimalFormat} to format this
   *         currency. This pattern does NOT includes the currency string.
   */
  @Nonnull
  @Nonempty
  public static String getValuePattern (@Nullable final ECurrency eCurrency)
  {
    return getSettings (eCurrency).getValuePattern ();
  }

  /**
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @return The {@link DecimalFormat} used to format this currency. Always
   *         returns a copy of the contained formatter for thread-safety and
   *         modification.
   */
  @Nonnull
  public static DecimalFormat getCurrencyFormat (@Nullable final ECurrency eCurrency)
  {
    return getSettings (eCurrency).getCurrencyFormat ();
  }

  @Nonnull
  public static String getCurrencyFormatted (@Nullable final ECurrency eCurrency, @Nonnull final BigDecimal aValue)
  {
    return getSettings (eCurrency).getCurrencyFormatted (aValue);
  }

  @Nonnull
  public static String getCurrencyFormatted (@Nullable final ECurrency eCurrency,
                                             @Nonnull final BigDecimal aValue,
                                             @Nonnegative final int nFractionDigits)
  {
    return getSettings (eCurrency).getCurrencyFormatted (aValue, nFractionDigits);
  }

  /**
   * @return The {@link DecimalFormat} object that formats an object like the
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   *        {@link #getCurrencyFormat(ECurrency)} but without the currency sign.
   *        Always returns a copy of the contained formatter for thread-safety
   *        and modification.
   */
  @Nonnull
  public static DecimalFormat getValueFormat (@Nullable final ECurrency eCurrency)
  {
    return getSettings (eCurrency).getValueFormat ();
  }

  @Nonnull
  public static String getValueFormatted (@Nullable final ECurrency eCurrency, @Nonnull final BigDecimal aValue)
  {
    return getSettings (eCurrency).getValueFormatted (aValue);
  }

  @Nonnull
  public static String getValueFormatted (@Nullable final ECurrency eCurrency,
                                          @Nonnull final BigDecimal aValue,
                                          @Nonnegative final int nFractionDigits)
  {
    return getSettings (eCurrency).getValueFormatted (aValue, nFractionDigits);
  }

  /**
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @return The minimum fraction digits to be used for formatting.
   */
  @Nonnegative
  public static int getMinimumFractionDigits (@Nullable final ECurrency eCurrency)
  {
    return getCurrencyFormat (eCurrency).getMinimumFractionDigits ();
  }

  /**
   * Set the minimum fraction digits to be used for formatting. Applies to the
   * currency-formatting and the value-formatting.
   *
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @param nDecimals
   *        The new minimum fraction digits. May not be negative.
   */
  public static void setMinimumFractionDigits (@Nullable final ECurrency eCurrency, @Nonnegative final int nDecimals)
  {
    getSettings (eCurrency).setMinimumFractionDigits (nDecimals);
  }

  @Nullable
  public static EDecimalSeparator getDecimalSeparator (@Nullable final ECurrency eCurrency)
  {
    return getSettings (eCurrency).getDecimalSeparator ();
  }

  @Nullable
  public static EGroupingSeparator getGroupingSeparator (@Nullable final ECurrency eCurrency)
  {
    return getSettings (eCurrency).getGroupingSeparator ();
  }

  /**
   * Adopt the passed text value according to the requested decimal separator.
   *
   * @param sTextValue
   *        The text to be manipulated. May be <code>null</code>.
   * @param eDecimalSep
   *        The decimal separator that is required. May not be <code>null</code>
   *        .
   * @param eGroupingSep
   *        The grouping separator that is required. May not be
   *        <code>null</code> .
   * @return The manipulated text so that it matches the required decimal
   *         separator or the original text
   */
  @Nullable
  private static String _getTextValueForDecimalSeparator (@Nullable final String sTextValue,
                                                          @Nonnull final EDecimalSeparator eDecimalSep,
                                                          @Nonnull final EGroupingSeparator eGroupingSep)
  {
    ValueEnforcer.notNull (eDecimalSep, "DecimalSeparator");
    ValueEnforcer.notNull (eGroupingSep, "GroupingSeparator");

    final String ret = StringHelper.trim (sTextValue);

    // Replace only, if the desired decimal separator is not present
    if (ret != null && ret.indexOf (eDecimalSep.getChar ()) < 0)
      switch (eDecimalSep)
      {
        case COMMA:
        {
          // Decimal separator is a ","
          if (ret.indexOf ('.') > -1 && eGroupingSep.getChar () != '.')
          {
            // Currency expects "," but user passed "."
            return StringHelper.replaceAll (ret, '.', eDecimalSep.getChar ());
          }
          break;
        }
        case POINT:
        {
          // Decimal separator is a "."
          if (ret.indexOf (',') > -1 && eGroupingSep.getChar () != ',')
          {
            // Pattern contains no "," but value contains ","
            return StringHelper.replaceAll (ret, ',', eDecimalSep.getChar ());
          }
          break;
        }
        default:
          throw new IllegalStateException ("Unexpected decimal separator [" + eDecimalSep + "]");
      }
    return ret;
  }

  /**
   * Try to parse a string value formatted by the {@link NumberFormat} object
   * returned from {@link #getCurrencyFormat(ECurrency)}. E.g.
   * <code>&euro; 5,00</code>
   *
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @param sTextValue
   *        The string value. It will be trimmed, and the decimal separator will
   *        be adopted.
   * @param aDefault
   *        The default value to be used in case parsing fails. May be
   *        <code>null</code>.
   * @return The {@link BigDecimal} value matching the string value or the
   *         passed default value.
   */
  @Nullable
  public static BigDecimal parseCurrencyFormat (@Nullable final ECurrency eCurrency,
                                                @Nullable final String sTextValue,
                                                @Nullable final BigDecimal aDefault)
  {
    final PerCurrencySettings aPCS = getSettings (eCurrency);
    final DecimalFormat aCurrencyFormat = aPCS.getCurrencyFormat ();

    // Adopt the decimal separator
    final String sRealTextValue;
    // In Java 9 onwards, this the separators may be null (e.g. for AED)
    if (aPCS.getDecimalSeparator () != null && aPCS.getGroupingSeparator () != null)
      sRealTextValue = _getTextValueForDecimalSeparator (sTextValue,
                                                         aPCS.getDecimalSeparator (),
                                                         aPCS.getGroupingSeparator ());
    else
      sRealTextValue = sTextValue;
    return parseCurrency (sRealTextValue, aCurrencyFormat, aDefault, aPCS.getRoundingMode ());
  }

  /**
   * Try to parse a string value formatted by the {@link NumberFormat} object
   * returned from {@link #getCurrencyFormat(ECurrency)}. E.g.
   * <code>&euro; 5,00</code>
   *
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @param sTextValue
   *        The string value. It will be parsed unmodified!
   * @param aDefault
   *        The default value to be used in case parsing fails. May be
   *        <code>null</code>.
   * @return The {@link BigDecimal} value matching the string value or the
   *         passed default value.
   */
  @Nullable
  public static BigDecimal parseCurrencyFormatUnchanged (@Nullable final ECurrency eCurrency,
                                                         @Nullable final String sTextValue,
                                                         @Nullable final BigDecimal aDefault)
  {
    final PerCurrencySettings aPCS = getSettings (eCurrency);
    final DecimalFormat aCurrencyFormat = aPCS.getCurrencyFormat ();
    return parseCurrency (sTextValue, aCurrencyFormat, aDefault, aPCS.getRoundingMode ());
  }

  /**
   * Try to parse a string value formatted by the {@link DecimalFormat} object
   * returned from {@link #getValueFormat(ECurrency)}
   *
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @param sTextValue
   *        The string value. It will be trimmed, and the decimal separator will
   *        be adopted.
   * @param aDefault
   *        The default value to be used in case parsing fails. May be
   *        <code>null</code>.
   * @return The {@link BigDecimal} value matching the string value or the
   *         passed default value.
   */
  @Nullable
  public static BigDecimal parseValueFormat (@Nullable final ECurrency eCurrency,
                                             @Nullable final String sTextValue,
                                             @Nullable final BigDecimal aDefault)
  {
    final PerCurrencySettings aPCS = getSettings (eCurrency);
    final DecimalFormat aValueFormat = aPCS.getValueFormat ();

    // Adopt the decimal separator
    final String sRealTextValue;
    // In Java 9 onwards, this the separators may be null (e.g. for AED)
    if (aPCS.getDecimalSeparator () != null && aPCS.getGroupingSeparator () != null)
      sRealTextValue = _getTextValueForDecimalSeparator (sTextValue,
                                                         aPCS.getDecimalSeparator (),
                                                         aPCS.getGroupingSeparator ());
    else
      sRealTextValue = sTextValue;
    return parseCurrency (sRealTextValue, aValueFormat, aDefault, aPCS.getRoundingMode ());
  }

  /**
   * Try to parse a string value formatted by the {@link DecimalFormat} object
   * returned from {@link #getValueFormat(ECurrency)}
   *
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @param sTextValue
   *        The string value. It will be parsed unmodified!
   * @param aDefault
   *        The default value to be used in case parsing fails. May be
   *        <code>null</code>.
   * @return The {@link BigDecimal} value matching the string value or the
   *         passed default value.
   */
  @Nullable
  public static BigDecimal parseValueFormatUnchanged (@Nullable final ECurrency eCurrency,
                                                      @Nullable final String sTextValue,
                                                      @Nullable final BigDecimal aDefault)
  {
    final PerCurrencySettings aPCS = getSettings (eCurrency);
    final DecimalFormat aValueFormat = aPCS.getValueFormat ();

    return parseCurrency (sTextValue, aValueFormat, aDefault, aPCS.getRoundingMode ());
  }

  /**
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @return The scaling to be used for BigDecimal operations. Always &ge; 0. If
   *         no underlying JDK currency is present, {@value #DEFAULT_SCALE} is
   *         returned.
   */
  @Nonnegative
  public static int getScale (@Nullable final ECurrency eCurrency)
  {
    return getSettings (eCurrency).getScale ();
  }

  /**
   * Special currency division method. This method solves the problem of
   * dividing "1/3" as it would result in a never ending series of
   * "0.33333333..." which results in an {@link ArithmeticException} thrown by
   * the divide method!<br>
   * The default scaling of this currency is used.
   *
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @param aDividend
   *        Dividend
   * @param aDivisor
   *        Divisor
   * @return The divided value with the correct scaling
   */
  @Nonnull
  @CheckReturnValue
  public static BigDecimal getDivided (@Nullable final ECurrency eCurrency,
                                       @Nonnull final BigDecimal aDividend,
                                       @Nonnull final BigDecimal aDivisor)
  {
    ValueEnforcer.notNull (aDividend, "Dividend");
    ValueEnforcer.notNull (aDivisor, "Divisor");
    final PerCurrencySettings aPCS = getSettings (eCurrency);
    return aDividend.divide (aDivisor, aPCS.getScale (), aPCS.getRoundingMode ());
  }

  /**
   * Special currency division method. This method solves the problem of
   * dividing "1/3" as it would result in a never ending series of
   * "0.33333333..." which results in an {@link ArithmeticException} thrown by
   * the divide method!<br>
   * This method takes a custom scaling. If the default scaling of this currency
   * should be used, than {@link #getDivided(ECurrency,BigDecimal, BigDecimal)}
   * should be used instead.
   *
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @param aDividend
   *        Dividend
   * @param aDivisor
   *        Divisor
   * @param nFractionDigits
   *        A custom scaling to be used.
   * @return The divided value with the provided scaling
   */
  @Nonnull
  @CheckReturnValue
  public static BigDecimal getDivided (@Nullable final ECurrency eCurrency,
                                       @Nonnull final BigDecimal aDividend,
                                       @Nonnull final BigDecimal aDivisor,
                                       @Nonnegative final int nFractionDigits)
  {
    ValueEnforcer.notNull (aDividend, "Dividend");
    ValueEnforcer.notNull (aDivisor, "Divisor");
    final PerCurrencySettings aPCS = getSettings (eCurrency);
    return aDividend.divide (aDivisor, nFractionDigits, aPCS.getRoundingMode ());
  }

  /**
   * Get the passed value rounded to the appropriate number of fraction digits,
   * based on this currencies default fraction digits.<br>
   * The default scaling of this currency is used.
   *
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @param aValue
   *        The value to be rounded. May not be <code>null</code>.
   * @return The rounded value. Never <code>null</code>.
   */
  @Nonnull
  public static BigDecimal getRounded (@Nullable final ECurrency eCurrency, @Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    final PerCurrencySettings aPCS = getSettings (eCurrency);
    return aValue.setScale (aPCS.getScale (), aPCS.getRoundingMode ());
  }

  /**
   * Get the passed value rounded to the appropriate number of fraction digits,
   * based on this currencies default fraction digits.<br>
   * This method takes a custom scaling. If the default scaling of this currency
   * should be used, than {@link #getRounded(ECurrency,BigDecimal)} should be
   * used instead.
   *
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @param aValue
   *        The value to be rounded. May not be <code>null</code>.
   * @param nFractionDigits
   *        A custom scaling to be used.
   * @return The rounded value. Never <code>null</code>.
   */
  @Nonnull
  public static BigDecimal getRounded (@Nullable final ECurrency eCurrency,
                                       @Nonnull final BigDecimal aValue,
                                       @Nonnegative final int nFractionDigits)
  {
    ValueEnforcer.notNull (aValue, "Value");
    final PerCurrencySettings aPCS = getSettings (eCurrency);
    return aValue.setScale (nFractionDigits, aPCS.getRoundingMode ());
  }

  /**
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @return The rounding mode of this currency. If non is specified,
   *         {@link #DEFAULT_ROUNDING_MODE} is returned instead. May not be
   *         <code>null</code>.
   */
  @Nonnull
  public static RoundingMode getRoundingMode (@Nullable final ECurrency eCurrency)
  {
    return getSettings (eCurrency).getRoundingMode ();
  }

  /**
   * Change the rounding mode of this currency.
   *
   * @param eCurrency
   *        The currency it is about. If <code>null</code> is provided
   *        {@link #DEFAULT_CURRENCY} is used instead.
   * @param eRoundingMode
   *        The rounding mode to be used. May be <code>null</code>.
   */
  public static void setRoundingMode (@Nullable final ECurrency eCurrency, @Nullable final RoundingMode eRoundingMode)
  {
    getSettings (eCurrency).setRoundingMode (eRoundingMode);
  }
}
