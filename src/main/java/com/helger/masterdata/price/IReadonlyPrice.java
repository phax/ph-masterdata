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
package com.helger.masterdata.price;

import java.math.BigDecimal;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import com.helger.masterdata.currency.IHasCurrency;
import com.helger.masterdata.currency.ICurrencyValue;
import com.helger.masterdata.vat.IHasVATItem;

/**
 * Base interface for a price that has a value, a currency and a VAT type.
 * 
 * @author Philip Helger
 */
public interface IReadonlyPrice extends IHasCurrency, IHasVATItem
{
  /**
   * @return The net amount of this price (without VAT).
   */
  @Nonnull
  ICurrencyValue getNetAmount ();

  /**
   * @return The gross amount of this price (with VAT).
   */
  @Nonnull
  ICurrencyValue getGrossAmount ();

  /**
   * @return The tax amount of this price (=net amount * percentage / 100).
   */
  @Nonnull
  ICurrencyValue getTaxAmount ();

  /**
   * Add this price and the given value, keeping currency and VAT type.
   * 
   * @param aValue
   *        The value to add.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyPrice getAdded (@Nonnull BigDecimal aValue);

  /**
   * Add this price and the given value, keeping currency and VAT type.
   * 
   * @param nValue
   *        The value to add.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyPrice getAdded (long nValue);

  /**
   * Subtract the given value from this price, keeping currency and VAT type.
   * 
   * @param aValue
   *        The value to subtract.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyPrice getSubtracted (@Nonnull BigDecimal aValue);

  /**
   * Subtract the given value from this price, keeping currency and VAT type.
   * 
   * @param nValue
   *        The value to subtract.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyPrice getSubtracted (long nValue);

  /**
   * Multiply this price with given value, keeping currency and VAT type.
   * 
   * @param aValue
   *        The value to multiply with.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyPrice getMultiplied (@Nonnull BigDecimal aValue);

  /**
   * Multiply this price with given value, keeping currency and VAT type.
   * 
   * @param nValue
   *        The value to multiply with.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyPrice getMultiplied (long nValue);

  /**
   * Divide this price with given value, keeping currency and VAT type.
   * 
   * @param aValue
   *        The value to divide through.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyPrice getDivided (@Nonnull BigDecimal aValue);

  /**
   * Divide this price with given value, keeping currency and VAT type.
   * 
   * @param nValue
   *        The value to divide through.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IReadonlyPrice getDivided (long nValue);
}
