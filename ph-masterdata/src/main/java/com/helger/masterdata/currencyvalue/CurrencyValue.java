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

import java.io.Serializable;
import java.math.BigDecimal;

import com.helger.annotation.CheckReturnValue;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.numeric.BigHelper;
import com.helger.base.state.EChange;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.masterdata.currency.CurrencyHelper;
import com.helger.masterdata.currency.ECurrency;

import jakarta.annotation.Nonnull;

/**
 * This class represents a single currency value as the combination of a value and a currency.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class CurrencyValue implements IMutableCurrencyValue, Serializable
{
  private ECurrency m_eCurrency;
  private BigDecimal m_aValue;

  public CurrencyValue (@Nonnull final ICurrencyValue aCurrencyValue)
  {
    this (aCurrencyValue.getCurrency (), aCurrencyValue.getValue ());
  }

  public CurrencyValue (@Nonnull final ECurrency eCurrency)
  {
    this (eCurrency, BigDecimal.ZERO);
  }

  public CurrencyValue (@Nonnull final ECurrency eCurrency, @Nonnull final BigDecimal aValue)
  {
    setCurrency (eCurrency);
    setValue (aValue);
  }

  @Nonnull
  public ECurrency getCurrency ()
  {
    return m_eCurrency;
  }

  @Nonnull
  public EChange setCurrency (@Nonnull final ECurrency eCurrency)
  {
    ValueEnforcer.notNull (eCurrency, "Currency");

    if (eCurrency.equals (m_eCurrency))
      return EChange.UNCHANGED;
    m_eCurrency = eCurrency;
    return EChange.CHANGED;
  }

  @Nonnull
  public BigDecimal getValue ()
  {
    return m_aValue;
  }

  @Nonnull
  public EChange setValue (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");

    if (EqualsHelper.equals (aValue, m_aValue))
      return EChange.UNCHANGED;
    m_aValue = aValue;
    return EChange.CHANGED;
  }

  public void addValue (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    m_aValue = m_aValue.add (aValue);
  }

  @Nonnull
  @CheckReturnValue
  public CurrencyValue getAdded (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    if (BigHelper.isEQ0 (aValue))
      return this;
    return new CurrencyValue (getCurrency (), getValue ().add (aValue));
  }

  @Nonnull
  @CheckReturnValue
  public CurrencyValue getSubtracted (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    if (BigHelper.isEQ0 (aValue))
      return this;
    return new CurrencyValue (getCurrency (), getValue ().subtract (aValue));
  }

  @Nonnull
  @CheckReturnValue
  public CurrencyValue getMultiplied (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    if (BigHelper.isEQ1 (aValue))
      return this;
    return new CurrencyValue (getCurrency (), getValue ().multiply (aValue));
  }

  @Nonnull
  @CheckReturnValue
  public CurrencyValue getDivided (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    if (BigHelper.isEQ1 (aValue))
      return this;
    final ECurrency eCurrency = getCurrency ();
    return new CurrencyValue (eCurrency, CurrencyHelper.getDivided (eCurrency, getValue (), aValue));
  }

  @Nonnull
  public CurrencyValue getClone ()
  {
    return new CurrencyValue (this);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final CurrencyValue rhs = (CurrencyValue) o;
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
    return new ToStringGenerator (this).append ("currency", m_eCurrency).append ("value", m_aValue).getToString ();
  }

  @Nonnull
  public static IMutableCurrencyValue fromCurrencyFormattedString (@Nonnull final String sText,
                                                                   @Nonnull final ECurrency eCurrency,
                                                                   @Nonnull final BigDecimal aDefaultValue)
  {
    return new CurrencyValue (eCurrency, CurrencyHelper.parseCurrencyFormat (eCurrency, sText, aDefaultValue));
  }

  @Nonnull
  public static IMutableCurrencyValue fromValueFormattedString (@Nonnull final String sText,
                                                                @Nonnull final ECurrency eCurrency,
                                                                @Nonnull final BigDecimal aDefaultValue)
  {
    return new CurrencyValue (eCurrency, CurrencyHelper.parseValueFormat (eCurrency, sText, aDefaultValue));
  }
}
