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

import org.jspecify.annotations.NonNull;

import com.helger.annotation.CheckReturnValue;
import com.helger.base.clone.ICloneable;
import com.helger.base.numeric.BigHelper;
import com.helger.base.state.EChange;
import com.helger.masterdata.currency.ECurrency;

/**
 * Writable version of a currency value. The reading methods are inherited from
 * {@link ICurrencyValue}.
 *
 * @author Philip Helger
 */
public interface IMutableCurrencyValue extends ICurrencyValue, ICloneable <IMutableCurrencyValue>
{
  @NonNull
  EChange setCurrency (@NonNull ECurrency eCurrency);

  @NonNull
  @CheckReturnValue
  IMutableCurrencyValue getAdded (@NonNull BigDecimal aValue);

  @NonNull
  @Override
  @CheckReturnValue
  default IMutableCurrencyValue getAdded (final long nValue)
  {
    if (nValue == 0)
      return this;
    return getAdded (BigHelper.toBigDecimal (nValue));
  }

  @NonNull
  @CheckReturnValue
  IMutableCurrencyValue getSubtracted (@NonNull BigDecimal aValue);

  @NonNull
  @Override
  @CheckReturnValue
  default IMutableCurrencyValue getSubtracted (final long nValue)
  {
    if (nValue == 0)
      return this;
    return getSubtracted (BigHelper.toBigDecimal (nValue));
  }

  @NonNull
  @CheckReturnValue
  IMutableCurrencyValue getMultiplied (@NonNull BigDecimal aValue);

  @NonNull
  @Override
  @CheckReturnValue
  default IMutableCurrencyValue getMultiplied (final long nValue)
  {
    if (nValue == 1)
      return this;
    return getMultiplied (BigHelper.toBigDecimal (nValue));
  }

  @NonNull
  @CheckReturnValue
  IMutableCurrencyValue getDivided (@NonNull BigDecimal aValue);

  @NonNull
  @Override
  @CheckReturnValue
  default IMutableCurrencyValue getDivided (final long nValue)
  {
    if (nValue == 1)
      return this;
    return getDivided (BigHelper.toBigDecimal (nValue));
  }

  @NonNull
  EChange setValue (@NonNull BigDecimal aValue);

  void addValue (@NonNull BigDecimal aValue);
}
