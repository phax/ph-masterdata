/*
 * Copyright (C) 2014-2026 Philip Helger (www.helger.com)
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

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonnegative;
import com.helger.annotation.style.ReturnsImmutableObject;
import com.helger.base.state.EChange;
import com.helger.base.state.IClearable;
import com.helger.collection.commons.ICommonsList;
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

  @NonNull
  @ReturnsImmutableObject
  ICommonsList <? extends IMutablePriceGraduationItem> getAllItems ();

  @NonNull
  IMutablePrice getSinglePriceOfQuantity (@Nonnegative int nQuantity, @NonNull IVATItem aVAT);

  @NonNull
  IMutablePrice getTotalPriceOfQuantity (@Nonnegative int nQuantity, @NonNull IVATItem aVAT);

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
  @NonNull
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
  @NonNull
  EChange addItem (@NonNull IMutablePriceGraduationItem aItem);

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
  IMutablePrice getPrice (@NonNull IPriceGraduationItem aItem, IVATItem aVAT);
}
