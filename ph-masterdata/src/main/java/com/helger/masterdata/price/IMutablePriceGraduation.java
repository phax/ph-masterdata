/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ReturnsImmutableObject;
import com.helger.commons.state.EChange;
import com.helger.commons.state.IClearable;
import com.helger.masterdata.vat.IVATItem;

/**
 * The writable interface for a single price graduation.
 *
 * @author Philip Helger
 */
public interface IMutablePriceGraduation extends IPriceGraduation, IClearable
{
  @Nullable
  IMutablePriceGraduationItem getSmallestMinimumQuantityItem ();

  @Nullable
  IMutablePriceGraduationItem getLargestMinimumQuantityItem ();

  @Nonnull
  @ReturnsImmutableObject
  List <? extends IMutablePriceGraduationItem> getAllItems ();

  @Nonnull
  IMutablePrice getSinglePriceOfQuantity (@Nonnegative int nQuantity, @Nonnull IVATItem aVAT);

  @Nonnull
  IMutablePrice getTotalPriceOfQuantity (@Nonnegative int nQuantity, @Nonnull IVATItem aVAT);

  /**
   * Add a new item based on the default currency and VAT type.
   *
   * @param nMinimumQuantity
   *        The minimum quantity to use. Must be &ge; 1.
   * @param aNetAmount
   *        The net amount of a single piece for the given quantity.
   * @return {@link EChange#CHANGED} if the value changed,
   *         {@link EChange#UNCHANGED} otherwise.
   */
  @Nonnull
  EChange addItem (@Nonnegative int nMinimumQuantity, BigDecimal aNetAmount);

  /**
   * Add a new item. The item should be added to the correct position, based on
   * the minimum quantity, in ascending order.
   *
   * @param aItem
   *        The price graduation item to use. May not be <code>null</code>.
   * @return {@link EChange#CHANGED} if the value changed,
   *         {@link EChange#UNCHANGED} otherwise.
   * @throws IllegalArgumentException
   *         If another item with the same minimum quantity is already
   *         contained. Use the setter methods of
   *         {@link IMutablePriceGraduationItem} instead.
   */
  @Nonnull
  EChange addItem (@Nonnull IMutablePriceGraduationItem aItem);

  /**
   * Delivers a writable price object based on the net amount of the passed item
   * and the currency and VAT set for the price graduation
   *
   * @param aItem
   *        the price graduation item for which to retrieve the price
   * @param aVAT
   *        the VAT to be used for the price (depending on the concrete context)
   * @return the resulting {@link IMutablePrice}
   */
  IMutablePrice getPrice (@Nonnull IPriceGraduationItem aItem, IVATItem aVAT);
}
