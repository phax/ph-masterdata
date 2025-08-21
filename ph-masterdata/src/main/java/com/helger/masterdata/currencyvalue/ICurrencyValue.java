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
package com.helger.masterdata.currencyvalue;

import java.math.BigDecimal;

import com.helger.annotation.CheckReturnValue;
import com.helger.annotation.Nonnegative;
import com.helger.annotation.style.MustImplementEqualsAndHashcode;
import com.helger.base.numeric.BigHelper;
import com.helger.masterdata.currency.CurrencyHelper;
import com.helger.masterdata.currency.IHasCurrency;

import jakarta.annotation.Nonnull;

/**
 * Read only interface of a currency value.
 *
 * @author Philip Helger
 */
@MustImplementEqualsAndHashcode
public interface ICurrencyValue extends IHasCurrency
{
  /**
   * @return The contained numeric currency value.
   */
  @Nonnull
  BigDecimal getValue ();

  /**
   * @return <code>true</code> if the value is &lt; 0, <code>false</code> if it is &ge; 0.
   * @since 6.1.1
   */
  default boolean isLT0 ()
  {
    return BigHelper.isLT0 (getValue ());
  }

  /**
   * @return <code>true</code> if the value is &le; 0, <code>false</code> if it is &gt; 0.
   * @since 6.1.1
   */
  default boolean isLE0 ()
  {
    return BigHelper.isLE0 (getValue ());
  }

  /**
   * @return <code>true</code> if the value is 0, <code>false</code> if it is not 0.
   * @since 6.1.1
   */
  default boolean isEQ0 ()
  {
    return BigHelper.isEQ0 (getValue ());
  }

  /**
   * @return <code>true</code> if the value is not 0, <code>false</code> if it is 0.
   * @since 6.1.1
   */
  default boolean isNE0 ()
  {
    return BigHelper.isNE0 (getValue ());
  }

  /**
   * @return <code>true</code> if the value is &gt; 0, <code>false</code> if it is &le; 0.
   * @since 6.1.1
   */
  default boolean isGT0 ()
  {
    return BigHelper.isGT0 (getValue ());
  }

  /**
   * @return <code>true</code> if the value is &ge; 0, <code>false</code> if it is &lt; 0.
   * @since 6.1.1
   */
  default boolean isGE0 ()
  {
    return BigHelper.isGE0 (getValue ());
  }

  /**
   * Sum up this currency value with the passed scalar value.
   *
   * @param aValue
   *        The value to add.
   * @return The added value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  ICurrencyValue getAdded (@Nonnull BigDecimal aValue);

  /**
   * Sum up this currency value with the passed scalar value.
   *
   * @param nValue
   *        The value to add.
   * @return The added value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  default ICurrencyValue getAdded (final long nValue)
  {
    if (nValue == 0)
      return this;
    return getAdded (BigHelper.toBigDecimal (nValue));
  }

  /**
   * Subtract this currency value with the passed scalar value.
   *
   * @param aValue
   *        The value to subtract.
   * @return The subtracted value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  ICurrencyValue getSubtracted (@Nonnull BigDecimal aValue);

  /**
   * Subtract this currency value with the passed scalar value.
   *
   * @param nValue
   *        The value to subtract.
   * @return The subtracted value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  default ICurrencyValue getSubtracted (final long nValue)
  {
    if (nValue == 0)
      return this;
    return getSubtracted (BigHelper.toBigDecimal (nValue));
  }

  /**
   * Multiply this currency value with the passed scalar value.
   *
   * @param aValue
   *        The multiplicator to use.
   * @return The multiplied value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  ICurrencyValue getMultiplied (@Nonnull BigDecimal aValue);

  /**
   * Multiply this currency value with the passed scalar value.
   *
   * @param nValue
   *        The multiplicator to use.
   * @return The multiplied value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  default ICurrencyValue getMultiplied (final long nValue)
  {
    if (nValue == 1)
      return this;
    return getMultiplied (BigHelper.toBigDecimal (nValue));
  }

  /**
   * Divide this currency value with the passed scalar value.
   *
   * @param aValue
   *        The divisor to use.
   * @return The divided value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  ICurrencyValue getDivided (@Nonnull BigDecimal aValue);

  /**
   * Divide this currency value with the passed scalar value.
   *
   * @param nValue
   *        The divisor to use.
   * @return The divided value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  default ICurrencyValue getDivided (final long nValue)
  {
    if (nValue == 1)
      return this;
    return getDivided (BigHelper.toBigDecimal (nValue));
  }

  /**
   * @return The value as a formatted currency including the currency sign. The scale is defined by
   *         the currency.
   */
  @Nonnull
  default String getCurrencyFormatted ()
  {
    return CurrencyHelper.getCurrencyFormatted (getCurrency (), getValue ());
  }

  /**
   * @param nFractionDigits
   *        The number of fraction digits to be used.
   * @return The value as a formatted currency including the currency sign.
   */
  @Nonnull
  default String getCurrencyFormatted (@Nonnegative final int nFractionDigits)
  {
    return CurrencyHelper.getCurrencyFormatted (getCurrency (), getValue (), nFractionDigits);
  }

  /**
   * @return The value as a formatted currency excluding the currency sign. The scale is defined by
   *         the currency.
   */
  @Nonnull
  default String getValueFormatted ()
  {
    return CurrencyHelper.getValueFormatted (getCurrency (), getValue ());
  }

  /**
   * @param nFractionDigits
   *        The number of fraction digits to be used.
   * @return The value as a formatted currency excluding the currency sign.
   */
  @Nonnull
  default String getValueFormatted (@Nonnegative final int nFractionDigits)
  {
    return CurrencyHelper.getValueFormatted (getCurrency (), getValue (), nFractionDigits);
  }
}
