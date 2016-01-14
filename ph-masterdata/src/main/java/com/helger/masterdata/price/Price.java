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
import java.math.RoundingMode;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.math.MathHelper;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;
import com.helger.masterdata.currency.ECurrency;
import com.helger.masterdata.currencyvalue.CurrencyValue;
import com.helger.masterdata.currencyvalue.ICurrencyValue;
import com.helger.masterdata.currencyvalue.ReadOnlyCurrencyValue;
import com.helger.masterdata.vat.IVATItem;

/**
 * Default implementation of the {@link IMutablePrice} interface.
 *
 * @author Philip Helger
 */
public class Price implements IMutablePrice
{
  public static final String FIELD_VATITEM = "vatitem";

  private CurrencyValue m_aNetAmount;
  private IVATItem m_aVATItem;

  public Price ()
  {}

  public Price (@Nonnull final IPrice aPrice)
  {
    this (aPrice.getNetAmount (), aPrice.getVATItem ());
  }

  /**
   * Constructor
   *
   * @param eCurrency
   *        Currency to use. May not be <code>null</code>.
   * @param aNetAmount
   *        The net amount to use. May not be <code>null</code>.
   * @param aVATItem
   *        The VAT item to use. May not be <code>null</code>.
   */
  public Price (@Nonnull final ECurrency eCurrency,
                @Nonnull final BigDecimal aNetAmount,
                @Nonnull final IVATItem aVATItem)
  {
    this (new ReadOnlyCurrencyValue (eCurrency, aNetAmount), aVATItem);
  }

  /**
   * Constructor
   *
   * @param aNetAmount
   *        The net amount incl. currency to use. May not be <code>null</code>.
   * @param aVATItem
   *        The VAT item to use. May not be <code>null</code>.
   */
  public Price (@Nonnull final ICurrencyValue aNetAmount, @Nonnull final IVATItem aVATItem)
  {
    setNetAmount (aNetAmount);
    setVATItem (aVATItem);
  }

  @Nonnull
  public CurrencyValue getNetAmount ()
  {
    return m_aNetAmount;
  }

  @Nonnull
  public EChange setNetAmount (@Nonnull final ICurrencyValue aNetAmount)
  {
    ValueEnforcer.notNull (aNetAmount, "NetAmount");

    final CurrencyValue aRealNetAmount = new CurrencyValue (aNetAmount);
    if (EqualsHelper.equals (m_aNetAmount, aRealNetAmount))
      return EChange.UNCHANGED;
    m_aNetAmount = aRealNetAmount;
    return EChange.CHANGED;
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
  public EChange setVATItem (@Nonnull final IVATItem aVATItem)
  {
    ValueEnforcer.notNull (aVATItem, "VATItem");

    if (aVATItem.equals (m_aVATItem))
      return EChange.UNCHANGED;
    m_aVATItem = aVATItem;
    return EChange.CHANGED;
  }

  @Nonnull
  public ICurrencyValue getTaxAmount ()
  {
    return m_aNetAmount.getMultiplied (m_aVATItem.getPercentageFactor ());
  }

  /*
   * Helper method to access the currency of the included "currencyValue".
   * Marked as @Transient to avoid EclipseLink automatically using it a s column
   * (because the corresponding setter method is also present!)
   */
  @Nonnull
  public ECurrency getCurrency ()
  {
    return m_aNetAmount.getCurrency ();
  }

  @Nonnull
  public EChange setCurrency (@Nonnull final ECurrency eCurrency)
  {
    return m_aNetAmount.setCurrency (eCurrency);
  }

  @Nonnull
  public ICurrencyValue getGrossAmount ()
  {
    final BigDecimal aMultiplicationFactor = m_aVATItem.getMultiplicationFactorNetToGross ();
    return m_aNetAmount.getMultiplied (aMultiplicationFactor);
  }

  @Nonnull
  @CheckReturnValue
  public Price getAdded (@Nonnull final BigDecimal aValue)
  {
    return new Price (m_aNetAmount.getAdded (aValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public Price getAdded (final long nValue)
  {
    return new Price (m_aNetAmount.getAdded (nValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public Price getSubtracted (@Nonnull final BigDecimal aValue)
  {
    return new Price (m_aNetAmount.getSubtracted (aValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public Price getSubtracted (final long nValue)
  {
    return new Price (m_aNetAmount.getSubtracted (nValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public Price getMultiplied (@Nonnull final BigDecimal aValue)
  {
    return new Price (m_aNetAmount.getMultiplied (aValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public Price getMultiplied (final long nValue)
  {
    return new Price (m_aNetAmount.getMultiplied (nValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public Price getDivided (@Nonnull final BigDecimal aValue)
  {
    return new Price (m_aNetAmount.getDivided (aValue), m_aVATItem);
  }

  @Nonnull
  @CheckReturnValue
  public Price getDivided (final long nValue)
  {
    return new Price (m_aNetAmount.getDivided (nValue), m_aVATItem);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final Price rhs = (Price) o;
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
    return new ToStringGenerator (this).append ("netAmount", m_aNetAmount).append ("VATtype", m_aVATItem).toString ();
  }

  /**
   * Create a price from a net amount.
   *
   * @param eCurrency
   *        Currency to use. May not be <code>null</code>.
   * @param aNetAmount
   *        The net amount to use. May not be <code>null</code>.
   * @param aVATItem
   *        The VAT item to use. May not be <code>null</code>.
   * @return The created {@link Price}
   */
  @Nonnull
  public static Price createFromNetAmount (@Nonnull final ECurrency eCurrency,
                                           @Nonnull final BigDecimal aNetAmount,
                                           @Nonnull final IVATItem aVATItem)
  {
    return new Price (eCurrency, aNetAmount, aVATItem);
  }

  /**
   * Create a price from a net amount.
   *
   * @param aNetAmount
   *        The net amount to use. May not be <code>null</code>.
   * @param aVATItem
   *        The VAT item to use. May not be <code>null</code>.
   * @return The created {@link Price}
   */
  @Nonnull
  public static Price createFromNetAmount (@Nonnull final ICurrencyValue aNetAmount, @Nonnull final IVATItem aVATItem)
  {
    return new Price (aNetAmount, aVATItem);
  }

  /**
   * Create a price from a gross amount using the scale and rounding mode from
   * the currency.
   *
   * @param eCurrency
   *        Currency to use. May not be <code>null</code>.
   * @param aGrossAmount
   *        The gross amount to use. May not be <code>null</code>.
   * @param aVATItem
   *        The VAT item to use. May not be <code>null</code>.
   * @return The created {@link Price}
   */
  @Nonnull
  public static Price createFromGrossAmount (@Nonnull final ECurrency eCurrency,
                                             @Nonnull final BigDecimal aGrossAmount,
                                             @Nonnull final IVATItem aVATItem)
  {
    return createFromGrossAmount (eCurrency,
                                  aGrossAmount,
                                  aVATItem,
                                  eCurrency.getScale (),
                                  eCurrency.getRoundingMode ());
  }

  /**
   * Create a price from a gross amount.
   *
   * @param eCurrency
   *        Currency to use. May not be <code>null</code>.
   * @param aGrossAmount
   *        The gross amount to use. May not be <code>null</code>.
   * @param aVATItem
   *        The VAT item to use. May not be <code>null</code>.
   * @param nScale
   *        The scaling to be used for the resulting amount, in case
   *        <code>grossAmount / (1 + perc/100)</code> delivery an inexact
   *        result.
   * @param eRoundingMode
   *        The rounding mode to be used to create a valid result.
   * @return The created {@link Price}
   */
  @Nonnull
  public static Price createFromGrossAmount (@Nonnull final ECurrency eCurrency,
                                             @Nonnull final BigDecimal aGrossAmount,
                                             @Nonnull final IVATItem aVATItem,
                                             @Nonnegative final int nScale,
                                             @Nonnull final RoundingMode eRoundingMode)
  {
    ValueEnforcer.notNull (aVATItem, "VATItem");

    final BigDecimal aFactor = aVATItem.getMultiplicationFactorNetToGross ();
    if (MathHelper.isEqualToOne (aFactor))
    {
      // Shortcut for no VAT (net == gross)
      return new Price (eCurrency, aGrossAmount, aVATItem);
    }
    return new Price (eCurrency, aGrossAmount.divide (aFactor, nScale, eRoundingMode), aVATItem);
  }

  /**
   * Create a price from a gross amount using the scale and rounding mode from
   * the currency.
   *
   * @param aGrossAmount
   *        The gross amount to use. May not be <code>null</code>.
   * @param aVATItem
   *        The VAT item to use. May not be <code>null</code>.
   * @return The created {@link Price}
   */
  @Nonnull
  public static Price createFromGrossAmount (@Nonnull final ICurrencyValue aGrossAmount,
                                             @Nonnull final IVATItem aVATItem)
  {
    ValueEnforcer.notNull (aGrossAmount, "GrossAmount");

    final ECurrency eCurrency = aGrossAmount.getCurrency ();
    return createFromGrossAmount (aGrossAmount, aVATItem, eCurrency.getScale (), eCurrency.getRoundingMode ());
  }

  /**
   * Create a price from a gross amount.
   *
   * @param aGrossAmount
   *        The gross amount to use. May not be <code>null</code>.
   * @param aVATItem
   *        The VAT item to use. May not be <code>null</code>.
   * @param nScale
   *        The scaling to be used for the resulting amount, in case
   *        <code>grossAmount / (1 + perc/100)</code> delivery an inexact
   *        result.
   * @param eRoundingMode
   *        The rounding mode to be used to create a valid result.
   * @return The created {@link Price}
   */
  @Nonnull
  public static Price createFromGrossAmount (@Nonnull final ICurrencyValue aGrossAmount,
                                             @Nonnull final IVATItem aVATItem,
                                             @Nonnegative final int nScale,
                                             @Nonnull final RoundingMode eRoundingMode)
  {
    ValueEnforcer.notNull (aVATItem, "VATItem");

    final BigDecimal aFactor = aVATItem.getMultiplicationFactorNetToGross ();
    if (MathHelper.isEqualToOne (aFactor))
    {
      // Shortcut for no VAT (net == gross)
      return new Price (aGrossAmount, aVATItem);
    }

    return new Price (aGrossAmount.getCurrency (),
                      aGrossAmount.getValue ().divide (aFactor, nScale, eRoundingMode),
                      aVATItem);
  }
}
