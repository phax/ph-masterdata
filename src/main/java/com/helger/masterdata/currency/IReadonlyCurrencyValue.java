/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.helger.commons.annotation.MustImplementEqualsAndHashcode;

/**
 * Read only interface of a currency value.
 *
 * @author Philip Helger
 */
@MustImplementEqualsAndHashcode
public interface IReadonlyCurrencyValue extends IHasCurrency
{
  /**
   * @return <code>true</code> if the value is &lt; 0, <code>false</code> if it
   *         is &ge; 0.
   */
  boolean isLowerThanZero ();

  /**
   * @return <code>true</code> if the value is &lt; 0, <code>false</code> if it
   *         is &le; 0.
   */
  boolean isGreaterThanZero ();

  /**
   * @return The contained numeric currency value.
   */
  @Nonnull
  BigDecimal getValue ();

  /**
   * Sum up this currency value with the passed scalar value.
   *
   * @param aValue
   *        The value to add.
   * @return The added value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyCurrencyValue getAdded (@Nonnull BigDecimal aValue);

  /**
   * Sum up this currency value with the passed scalar value.
   *
   * @param nValue
   *        The value to add.
   * @return The added value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyCurrencyValue getAdded (long nValue);

  /**
   * Subtract this currency value with the passed scalar value.
   *
   * @param aValue
   *        The value to subtract.
   * @return The subtracted value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyCurrencyValue getSubtracted (@Nonnull BigDecimal aValue);

  /**
   * Subtract this currency value with the passed scalar value.
   *
   * @param nValue
   *        The value to subtract.
   * @return The subtracted value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyCurrencyValue getSubtracted (long nValue);

  /**
   * Multiply this currency value with the passed scalar value.
   *
   * @param aValue
   *        The multiplicator to use.
   * @return The multiplied value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyCurrencyValue getMultiplied (@Nonnull BigDecimal aValue);

  /**
   * Multiply this currency value with the passed scalar value.
   *
   * @param nValue
   *        The multiplicator to use.
   * @return The multiplied value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyCurrencyValue getMultiplied (long nValue);

  /**
   * Divide this currency value with the passed scalar value.
   *
   * @param aValue
   *        The divisor to use.
   * @return The divided value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyCurrencyValue getDivided (@Nonnull BigDecimal aValue);

  /**
   * Divide this currency value with the passed scalar value.
   *
   * @param nValue
   *        The divisor to use.
   * @return The divided value with the same currency.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyCurrencyValue getDivided (long nValue);

  /**
   * @return The value as a formatted currency including the currency sign. The
   *         scale is defined by the currency.
   */
  @Nonnull
  String getCurrencyFormatted ();

  /**
   * @param nFractionDigits
   *        The number of fraction digits to be used.
   * @return The value as a formatted currency including the currency sign.
   */
  @Nonnull
  String getCurrencyFormatted (@Nonnegative int nFractionDigits);

  /**
   * @return The value as a formatted currency excluding the currency sign. The
   *         scale is defined by the currency.
   */
  @Nonnull
  String getValueFormatted ();

  /**
   * @param nFractionDigits
   *        The number of fraction digits to be used.
   * @return The value as a formatted currency excluding the currency sign.
   */
  @Nonnull
  String getValueFormatted (@Nonnegative int nFractionDigits);
}
