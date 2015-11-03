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
package com.helger.masterdata.currencyvalue;

import java.math.BigDecimal;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import com.helger.commons.lang.ICloneable;
import com.helger.commons.state.EChange;
import com.helger.masterdata.currency.ECurrency;

/**
 * Writable version of a currency value. The reading methods are inherited from
 * {@link ICurrencyValue}.
 * 
 * @author Philip Helger
 */
public interface IMutableCurrencyValue extends ICurrencyValue, ICloneable <IMutableCurrencyValue>
{
  @Nonnull
  EChange setCurrency (@Nonnull ECurrency eCurrency);

  @Nonnull
  @CheckReturnValue
  IMutableCurrencyValue getAdded (@Nonnull BigDecimal aValue);

  @Nonnull
  @CheckReturnValue
  IMutableCurrencyValue getAdded (long nValue);

  @Nonnull
  @CheckReturnValue
  IMutableCurrencyValue getSubtracted (@Nonnull BigDecimal aValue);

  @Nonnull
  @CheckReturnValue
  IMutableCurrencyValue getSubtracted (long nValue);

  @Nonnull
  @CheckReturnValue
  IMutableCurrencyValue getMultiplied (@Nonnull BigDecimal aValue);

  @Nonnull
  @CheckReturnValue
  IMutableCurrencyValue getMultiplied (long nValue);

  @Nonnull
  @CheckReturnValue
  IMutableCurrencyValue getDivided (@Nonnull BigDecimal aValue);

  @Nonnull
  @CheckReturnValue
  IMutableCurrencyValue getDivided (long nValue);

  @Nonnull
  EChange setValue (@Nonnull BigDecimal aValue);

  void addValue (@Nonnull BigDecimal aValue);
}
