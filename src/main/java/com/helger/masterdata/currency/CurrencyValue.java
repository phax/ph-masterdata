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
import javax.annotation.Nonnull;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.math.MathHelper;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;

/**
 * This class represents a single currency value as the combination of a value
 * and a currency.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public final class CurrencyValue extends AbstractCurrencyValue implements IMutableCurrencyValue
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

  public boolean isLowerThanZero ()
  {
    return MathHelper.isLowerThanZero (m_aValue);
  }

  public boolean isGreaterThanZero ()
  {
    return MathHelper.isGreaterThanZero (m_aValue);
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
  public IMutableCurrencyValue getAdded (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    if (MathHelper.isEqualToZero (aValue))
      return this;
    return new CurrencyValue (getCurrency (), getValue ().add (aValue));
  }

  @Nonnull
  @CheckReturnValue
  public IMutableCurrencyValue getAdded (final long nValue)
  {
    if (nValue == 0)
      return this;
    return getAdded (new BigDecimal (nValue));
  }

  @Nonnull
  @CheckReturnValue
  public IMutableCurrencyValue getSubtracted (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    if (MathHelper.isEqualToZero (aValue))
      return this;
    return new CurrencyValue (getCurrency (), getValue ().subtract (aValue));
  }

  @Nonnull
  @CheckReturnValue
  public IMutableCurrencyValue getSubtracted (final long nValue)
  {
    if (nValue == 0)
      return this;
    return getSubtracted (new BigDecimal (nValue));
  }

  @Nonnull
  @CheckReturnValue
  public IMutableCurrencyValue getMultiplied (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    if (MathHelper.isEqualToOne (aValue))
      return this;
    return new CurrencyValue (getCurrency (), getValue ().multiply (aValue));
  }

  @Nonnull
  @CheckReturnValue
  public IMutableCurrencyValue getMultiplied (final long nValue)
  {
    if (nValue == 1)
      return this;
    return getMultiplied (new BigDecimal (nValue));
  }

  @Nonnull
  @CheckReturnValue
  public IMutableCurrencyValue getDivided (@Nonnull final BigDecimal aValue)
  {
    ValueEnforcer.notNull (aValue, "Value");
    if (MathHelper.isEqualToOne (aValue))
      return this;
    final ECurrency eCurrency = getCurrency ();
    return new CurrencyValue (eCurrency, eCurrency.getDivided (getValue (), aValue));
  }

  @Nonnull
  @CheckReturnValue
  public IMutableCurrencyValue getDivided (final long nValue)
  {
    if (nValue == 1)
      return this;
    return getDivided (new BigDecimal (nValue));
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
    return new ToStringGenerator (this).append ("currency", m_eCurrency).append ("value", m_aValue).toString ();
  }

  @Nonnull
  public static IMutableCurrencyValue fromCurrencyFormattedString (@Nonnull final String sText,
                                                            @Nonnull final ECurrency eCurrency,
                                                            @Nonnull final BigDecimal aDefaultValue)
  {
    return new CurrencyValue (eCurrency, eCurrency.parseCurrencyFormat (sText, aDefaultValue));
  }

  @Nonnull
  public static IMutableCurrencyValue fromValueFormattedString (@Nonnull final String sText,
                                                         @Nonnull final ECurrency eCurrency,
                                                         @Nonnull final BigDecimal aDefaultValue)
  {
    return new CurrencyValue (eCurrency, eCurrency.parseValueFormat (sText, aDefaultValue));
  }
}
