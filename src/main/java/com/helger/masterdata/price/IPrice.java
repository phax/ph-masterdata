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

import com.helger.commons.state.EChange;
import com.helger.masterdata.currency.ECurrency;
import com.helger.masterdata.currency.ICurrencyValue;
import com.helger.masterdata.currency.IReadonlyCurrencyValue;
import com.helger.masterdata.vat.IVATItem;

public interface IPrice extends IReadonlyPrice
{
  @Nonnull
  ICurrencyValue getNetAmount ();

  /**
   * Change the amount of this price.
   * 
   * @param aValue
   *        The new value. May not be <code>null</code>.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setNetAmount (@Nonnull IReadonlyCurrencyValue aValue);

  /**
   * Change the VAT type of this price.
   * 
   * @param aVATType
   *        The new VAT type. May not be <code>null</code>.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setVATItem (@Nonnull IVATItem aVATType);

  /**
   * Set the currency of the price.
   * 
   * @param eCurrency
   *        The new currency to use. May not be <code>null</code>.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setCurrency (@Nonnull ECurrency eCurrency);

  /**
   * Add this price and the given value, keeping currency and VAT type.
   * 
   * @param aValue
   *        The value to add.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IPrice getAdded (@Nonnull BigDecimal aValue);

  /**
   * Add this price and the given value, keeping currency and VAT type.
   * 
   * @param nValue
   *        The value to add.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IPrice getAdded (long nValue);

  /**
   * Subtract the given value from this price, keeping currency and VAT type.
   * 
   * @param aValue
   *        The value to subtract.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IPrice getSubtracted (@Nonnull BigDecimal aValue);

  /**
   * Subtract the given value from this price, keeping currency and VAT type.
   * 
   * @param nValue
   *        The value to subtract.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IPrice getSubtracted (long nValue);

  /**
   * Multiply this price with given value, keeping currency and VAT type.
   * 
   * @param aValue
   *        The value to multiply with.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IPrice getMultiplied (@Nonnull BigDecimal aValue);

  /**
   * Multiply this price with given value, keeping currency and VAT type.
   * 
   * @param nValue
   *        The value to multiply with.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IPrice getMultiplied (long nValue);

  /**
   * Divide this price with given value, keeping currency and VAT type.
   * 
   * @param aValue
   *        The value to divide through.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IPrice getDivided (@Nonnull BigDecimal aValue);

  /**
   * Divide this price with given value, keeping currency and VAT type.
   * 
   * @param nValue
   *        The value to divide through.
   * @return The result value as a new object.
   */
  @Nonnull
  @CheckReturnValue
  IPrice getDivided (long nValue);
}
