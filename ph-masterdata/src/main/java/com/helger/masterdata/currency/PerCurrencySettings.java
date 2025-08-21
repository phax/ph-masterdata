/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import com.helger.annotation.Nonempty;
import com.helger.annotation.Nonnegative;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.string.StringRemove;
import com.helger.base.string.StringReplace;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Per currency settings. Not thread safe.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public final class PerCurrencySettings implements Serializable
{
  private final int m_nScale;
  private final String m_sCurrencyPattern;
  private final String m_sValuePattern;
  private final DecimalFormat m_aCurrencyFormat;
  private final DecimalFormat m_aValueFormat;
  private final DecimalFormatSymbols m_aDFS;
  private RoundingMode m_eRoundingMode;
  private final EDecimalSeparator m_eDecimalSep;
  private final EGroupingSeparator m_eGroupingSep;

  public PerCurrencySettings (@Nonnull final ECurrency eCurrency)
  {
    // Use the first locale with a language as the most relevant one
    Locale aRelevantLocale = eCurrency.matchingLocales ().findFirst (x -> x.getLanguage ().length () > 0);
    if (aRelevantLocale == null)
    {
      // Fallback to the first locale
      aRelevantLocale = eCurrency.matchingLocales ().getFirstOrNull ();
    }

    final Currency aCurrency = eCurrency.getAsCurrency ();
    m_nScale = aCurrency == null ? CurrencyHelper.DEFAULT_SCALE : aCurrency.getDefaultFractionDigits ();

    // Note: Locale fr_FR formats locale with a trailing € whereas the locale
    // de_DE formats the € at front!
    m_aCurrencyFormat = (DecimalFormat) NumberFormat.getCurrencyInstance (aRelevantLocale);
    m_aCurrencyFormat.setMaximumFractionDigits (m_nScale);
    m_aDFS = m_aCurrencyFormat.getDecimalFormatSymbols ();

    // Extract value pattern from currency pattern (without currency symbol)
    String sCP = m_aCurrencyFormat.toPattern ();
    if (false)
    {
      // Java 9 may use non-breaking spaces as separator
      // TODO make this configurable to use either blank or 0xa0
      sCP = StringReplace.replaceAll (sCP, '\u00a0', ' ');
    }
    m_sCurrencyPattern = sCP;

    String sVP = sCP;
    sVP = StringRemove.removeAll (sVP, "\u00A4 ");
    sVP = StringRemove.removeAll (sVP, " \u00A4");
    sVP = StringRemove.removeAll (sVP, "\u00A4");
    sVP = StringRemove.removeAll (sVP, '\u00A0');
    m_sValuePattern = sVP;

    // Use the decimal symbols from the currency format
    m_aValueFormat = new DecimalFormat (m_sValuePattern, m_aDFS);

    // By default the default rounding mode should be used
    m_eRoundingMode = null;

    m_eDecimalSep = EDecimalSeparator.getFromCharOrNull (m_aDFS.getDecimalSeparator ());
    m_eGroupingSep = EGroupingSeparator.getFromCharOrNull (m_aDFS.getGroupingSeparator ());
  }

  /**
   * @return The scaling to be used for BigDecimal operations. Always &ge; 0. If no underlying JDK
   *         currency is present, {@value CurrencyHelper#DEFAULT_SCALE} is returned.
   */
  @Nonnegative
  public int getScale ()
  {
    return m_nScale;
  }

  /**
   * @return The pattern to be used in {@link DecimalFormat} to format this currency. This pattern
   *         includes the currency string.
   */
  @Nonnull
  @Nonempty
  public String getCurrencyPattern ()
  {
    return m_sCurrencyPattern;
  }

  /**
   * @return The pattern to be used in {@link DecimalFormat} to format this currency. This pattern
   *         does NOT includes the currency string.
   */
  @Nonnull
  @Nonempty
  public String getValuePattern ()
  {
    return m_sValuePattern;
  }

  /**
   * @return The {@link DecimalFormat} used to format this currency. Always returns a copy of the
   *         contained formatter for thread-safety and modification.
   */
  @Nonnull
  public DecimalFormat getCurrencyFormat ()
  {
    // DecimalFormat is not thread safe - clone!
    return (DecimalFormat) m_aCurrencyFormat.clone ();
  }

  @Nonnull
  public String getCurrencyFormatted (@Nonnull final BigDecimal aValue)
  {
    return getCurrencyFormat ().format (aValue);
  }

  @Nonnull
  public String getCurrencyFormatted (@Nonnull final BigDecimal aValue, @Nonnegative final int nFractionDigits)
  {
    final DecimalFormat aFormat = getCurrencyFormat ();
    aFormat.setMaximumFractionDigits (nFractionDigits);
    return aFormat.format (aValue);
  }

  /**
   * @return The {@link DecimalFormat} object that formats an object like the
   *         {@link #getCurrencyFormat()} but without the currency sign. Always returns a copy of
   *         the contained formatter for thread-safety and modification.
   */
  @Nonnull
  public DecimalFormat getValueFormat ()
  {
    // DecimalFormat is not thread safe - clone!
    return (DecimalFormat) m_aValueFormat.clone ();
  }

  @Nonnull
  public String getValueFormatted (@Nonnull final BigDecimal aValue)
  {
    return getValueFormat ().format (aValue);
  }

  @Nonnull
  public String getValueFormatted (@Nonnull final BigDecimal aValue, @Nonnegative final int nFractionDigits)
  {
    final DecimalFormat aFormat = getValueFormat ();
    aFormat.setMaximumFractionDigits (nFractionDigits);
    return aFormat.format (aValue);
  }

  /**
   * Set the minimum fraction digits to be used for formatting. Applies to the currency-formatting
   * and the value-formatting.
   *
   * @param nDecimals
   *        The new minimum fraction digits. May not be negative.
   */
  public void setMinimumFractionDigits (@Nonnegative final int nDecimals)
  {
    ValueEnforcer.isGE0 (nDecimals, "Decimals");
    m_aCurrencyFormat.setMinimumFractionDigits (nDecimals);
    m_aValueFormat.setMinimumFractionDigits (nDecimals);
  }

  @Nonnull
  public String getCurrencySymbol ()
  {
    return m_aDFS.getCurrencySymbol ();
  }

  /**
   * @return The rounding mode of this currency. If non is specified,
   *         {@link CurrencyHelper#DEFAULT_ROUNDING_MODE} is returned instead. May not be
   *         <code>null</code>.
   */
  @Nonnull
  public RoundingMode getRoundingMode ()
  {
    return m_eRoundingMode != null ? m_eRoundingMode : CurrencyHelper.DEFAULT_ROUNDING_MODE;
  }

  /**
   * Change the rounding mode of this currency.
   *
   * @param eRoundingMode
   *        The rounding mode to be used. May be <code>null</code>.
   */
  public void setRoundingMode (@Nullable final RoundingMode eRoundingMode)
  {
    m_eRoundingMode = eRoundingMode;
  }

  @Nullable
  public EDecimalSeparator getDecimalSeparator ()
  {
    return m_eDecimalSep;
  }

  @Nullable
  public EGroupingSeparator getGroupingSeparator ()
  {
    return m_eGroupingSep;
  }
}
