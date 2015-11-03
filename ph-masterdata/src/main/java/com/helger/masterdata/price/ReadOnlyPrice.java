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

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;
import com.helger.masterdata.currency.ECurrency;
import com.helger.masterdata.currencyvalue.ICurrencyValue;
import com.helger.masterdata.currencyvalue.ReadOnlyCurrencyValue;
import com.helger.masterdata.vat.IVATItem;

/**
 * Default implementation of the {@link IPrice} interface.
 *
 * @author Philip Helger
 */
public class ReadOnlyPrice implements IPrice
{
  private final ICurrencyValue m_aNetAmount;
  private final IVATItem m_aVATItem;

  public ReadOnlyPrice (@Nonnull final IPrice aOtherPrice)
  {
    this (aOtherPrice.getNetAmount (), aOtherPrice.getVATItem ());
  }

  public ReadOnlyPrice (@Nonnull final ECurrency eCurrency,
                        @Nonnull final BigDecimal aNetAmount,
                        @Nonnull final IVATItem aVATItem)
  {
    m_aNetAmount = new ReadOnlyCurrencyValue (eCurrency, aNetAmount);
    m_aVATItem = ValueEnforcer.notNull (aVATItem, "VATItem");
  }

  public ReadOnlyPrice (@Nonnull final ICurrencyValue aNetAmount, @Nonnull final IVATItem aVATItem)
  {
    ValueEnforcer.notNull (aNetAmount, "NetAmount");
    // Make a copy of the net amount!
    m_aNetAmount = new ReadOnlyCurrencyValue (aNetAmount);
    m_aVATItem = ValueEnforcer.notNull (aVATItem, "VATItem");
  }

  @Nonnull
  public ECurrency getCurrency ()
  {
    return m_aNetAmount.getCurrency ();
  }

  @Nonnull
  public ICurrencyValue getNetAmount ()
  {
    return m_aNetAmount;
  }

  @Nonnull
  public ICurrencyValue getGrossAmount ()
  {
    final BigDecimal aMultiplicationFactor = m_aVATItem.getMultiplicationFactorNetToGross ();
    return m_aNetAmount.getMultiplied (aMultiplicationFactor);
  }

  @Nonnull
  public IVATItem getVATItem ()
  {
    return m_aVATItem;
  }

  @Nonnull
  @Nonempty
  public String getVATItemID ()
  {
    return m_aVATItem.getID ();
  }

  @Nonnull
  public ICurrencyValue getTaxAmount ()
  {
    return m_aNetAmount.getMultiplied (m_aVATItem.getPercentageFactor ());
  }

  @Nonnull
  @CheckReturnValue
  public ReadOnlyPrice getAdded (@Nonnull final BigDecimal aValue)
  {
    return new ReadOnlyPrice (m_aNetAmount.getAdded (aValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public ReadOnlyPrice getAdded (final long nValue)
  {
    return new ReadOnlyPrice (m_aNetAmount.getAdded (nValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public ReadOnlyPrice getSubtracted (@Nonnull final BigDecimal aValue)
  {
    return new ReadOnlyPrice (m_aNetAmount.getSubtracted (aValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public ReadOnlyPrice getSubtracted (final long nValue)
  {
    return new ReadOnlyPrice (m_aNetAmount.getSubtracted (nValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public ReadOnlyPrice getMultiplied (@Nonnull final BigDecimal aValue)
  {
    return new ReadOnlyPrice (m_aNetAmount.getMultiplied (aValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public ReadOnlyPrice getMultiplied (final long nValue)
  {
    return new ReadOnlyPrice (m_aNetAmount.getMultiplied (nValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public ReadOnlyPrice getDivided (@Nonnull final BigDecimal aValue)
  {
    return new ReadOnlyPrice (m_aNetAmount.getDivided (aValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public ReadOnlyPrice getDivided (final long nValue)
  {
    return new ReadOnlyPrice (m_aNetAmount.getDivided (nValue), m_aVATItem);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final ReadOnlyPrice rhs = (ReadOnlyPrice) o;
    return m_aNetAmount.equals (rhs.m_aNetAmount) && m_aVATItem.equals (rhs.m_aVATItem);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aNetAmount).append (m_aVATItem).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("netAmount", m_aNetAmount).append ("VATItem", m_aVATItem).toString ();
  }
}
