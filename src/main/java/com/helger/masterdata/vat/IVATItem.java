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
package com.helger.masterdata.vat;

import java.math.BigDecimal;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.helger.commons.annotations.MustImplementEqualsAndHashcode;
import com.helger.commons.id.IHasID;
import com.helger.commons.name.IHasDisplayText;
import com.helger.datetime.period.ILocalDatePeriod;

/**
 * Defines a single VAT item valid within a country.
 * 
 * @author Philip Helger
 */
@MustImplementEqualsAndHashcode
public interface IVATItem extends IHasDisplayText, IHasID <String>, ILocalDatePeriod
{
  /**
   * @return The non-<code>null</code> type of this item.
   */
  @Nonnull
  EVATType getType ();

  /**
   * @return The percentage of this VAT type. Must be between 0 and 100.
   */
  @Nonnull
  @Nonnegative
  BigDecimal getPercentage ();

  /**
   * @return The factor (e.g. 0.2 for 20% or 0.5 for 50%). Always &ge; 0 (for 0%
   *         VAT) and &le; 1 (for 100% VAT).
   */
  @Nonnull
  @Nonnegative
  BigDecimal getPercentageFactor ();

  /**
   * @return The multiplication factor (e.g. 1.2 for 20% or 1.5 for 50%). Always
   *         &ge; 1 (for 0% VAT) and &le; 2 (for 100% VAT). It can also be used
   *         to calculate the net from the gross price by calling
   *         <code>gross.divide (<i>factor</i>)</code>
   */
  @Nonnull
  @Nonnegative
  BigDecimal getMultiplicationFactorNetToGross ();

  /**
   * @return <code>true</code> if this item is deprecated and a new VAT item
   *         applies now.
   */
  boolean isDeprecated ();
}
