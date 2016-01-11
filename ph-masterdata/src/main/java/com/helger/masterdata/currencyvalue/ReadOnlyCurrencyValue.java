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
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.math.MathHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.masterdata.currency.ECurrency;

/**
 * This class represents a single read only currency value as the combination of
 * a value and a currency.
 *
 * @author Philip Helger
 */
@Immutable
public final class ReadOnlyCurrencyValue implements ICurrencyValue
{
  private final ECurrency m_eCurrency;
  private final BigDecimal m_aValue;

  public ReadOnlyCurrencyValue (@Nonnull final ICurrencyValue aCurrencyValue)
  {
    this (aCurrencyValue.getCurrency (), aCurrencyValue.getValue ());
  }

  public ReadOnlyCurrencyValue (@Nonnull final ECurrency eCurrency, @Nonnull final BigDecimal aValue)
  {
    m_eCurrency = ValueEnforcer.notNull (eCurrency, "Currency");
    m_aValue = ValueEnforcer.notNull (aValue, "Value");
  }

  @Nonnull
  public ECurrency getCurrency ()
  {
    return m_eCurrency;
  }

  @Nonnull
  public BigDecimal getValue ()
  {
    return m_aValue;
  }

  @Nonnull
  @CheckReturnValue
  public ReadOnlyCurrencyValue getAdded (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    if (MathHelper.isEqualToZero (aValue))
      return this;
    return new ReadOnlyCurrencyValue (getCurrency (), getValue ().add (aValue));
  }

  @Nonnull
  @CheckReturnValue
  public ReadOnlyCurrencyValue getSubtracted (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    if (MathHelper.isEqualToZero (aValue))
      return this;
    return new ReadOnlyCurrencyValue (getCurrency (), getValue ().subtract (aValue));
  }

  @Nonnull
  @CheckReturnValue
  public ReadOnlyCurrencyValue getMultiplied (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    if (MathHelper.isEqualToOne (aValue))
      return this;
    return new ReadOnlyCurrencyValue (getCurrency (), getValue ().multiply (aValue));
  }

  @Nonnull
  @CheckReturnValue
  public ReadOnlyCurrencyValue getDivided (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    if (MathHelper.isEqualToOne (aValue))
      return this;
    final ECurrency eCurrency = getCurrency ();
    return new ReadOnlyCurrencyValue (eCurrency, eCurrency.getDivided (getValue (), aValue));
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final ReadOnlyCurrencyValue rhs = (ReadOnlyCurrencyValue) o;
    return m_eCurrency.equals (rhs.m_eCurrency) && EqualsHelper.equals (m_aValue, rhs.m_aValue);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_eCurrency).append (m_aValue).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("currency", m_eCurrency).append ("value", m_aValue).toString ();
  }

  @Nonnull
  public static ICurrencyValue fromCurrencyFormattedString (@Nonnull final String sText,
                                                            @Nonnull final ECurrency eCurrency,
                                                            @Nonnull final BigDecimal aDefaultValue)
  {
    return new ReadOnlyCurrencyValue (eCurrency, eCurrency.parseCurrencyFormat (sText, aDefaultValue));
  }

  @Nonnull
  public static ICurrencyValue fromValueFormattedString (@Nonnull final String sText,
                                                         @Nonnull final ECurrency eCurrency,
                                                         @Nonnull final BigDecimal aDefaultValue)
  {
    return new ReadOnlyCurrencyValue (eCurrency, eCurrency.parseValueFormat (sText, aDefaultValue));
  }
}
