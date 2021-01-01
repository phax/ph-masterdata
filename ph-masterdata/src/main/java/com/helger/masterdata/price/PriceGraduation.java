/**
 * Copyright (C) 2014-2021 Philip Helger (www.helger.com)
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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;
import com.helger.masterdata.currency.ECurrency;
import com.helger.masterdata.vat.IVATItem;

/**
 * Default implementation of the {@link IMutablePriceGraduation} and
 * {@link IPriceGraduation} interfaces.
 *
 * @author Philip Helger
 */
public class PriceGraduation implements IMutablePriceGraduation
{
  private final ECurrency m_eCurrency;

  // All items sorted ascending by the minimum quantity
  private final ICommonsList <IMutablePriceGraduationItem> m_aItems = new CommonsArrayList <> ();

  /**
   * Create a new price graduation valid only for the given currency and VAT
   * type.
   *
   * @param eCurrency
   *        The currency to use. May not be <code>null</code>.
   */
  public PriceGraduation (@Nonnull final ECurrency eCurrency)
  {
    m_eCurrency = ValueEnforcer.notNull (eCurrency, "Currency");
  }

  @Nonnull
  public final ECurrency getCurrency ()
  {
    return m_eCurrency;
  }

  @Nullable
  public IMutablePriceGraduationItem getSmallestMinimumQuantityItem ()
  {
    return m_aItems.getFirst ();
  }

  @Nullable
  public IMutablePriceGraduationItem getLargestMinimumQuantityItem ()
  {
    return m_aItems.getLast ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <? extends IMutablePriceGraduationItem> getAllItems ()
  {
    return m_aItems.getClone ();
  }

  @Nullable
  public IMutablePriceGraduationItem getItemOfIndex (@Nonnegative final int nIndex)
  {
    return m_aItems.getAtIndex (nIndex);
  }

  @Nonnull
  private IMutablePriceGraduationItem _getItemOfQuantity (@Nonnegative final int nQuantity)
  {
    ValueEnforcer.isGT0 (nQuantity, "Quantity");

    IMutablePriceGraduationItem ret = null;
    for (final IMutablePriceGraduationItem aItem : m_aItems)
    {
      if (aItem.getMinimumQuantity () > nQuantity)
        break;
      ret = aItem;
    }
    if (ret == null)
      throw new IllegalStateException ("Failed to resolve item of quantity " + nQuantity + " in " + toString ());
    return ret;
  }

  @Nonnull
  private IMutablePrice _createPrice (@Nonnull final BigDecimal aNetAmount, @Nonnull final IVATItem aVAT)
  {
    return new Price (m_eCurrency, aNetAmount, aVAT);
  }

  @Nonnull
  public IMutablePrice getPrice (@Nonnull final IPriceGraduationItem aItem, @Nonnull final IVATItem aVATItem)
  {
    ValueEnforcer.notNull (aItem, "Item");
    ValueEnforcer.notNull (aVATItem, "VATItem");

    if (!m_aItems.contains (aItem))
      throw new IllegalArgumentException ("passed item is not contained in this price graduation: " + aItem);
    return _createPrice (aItem.getUnitNetAmount (), aVATItem);
  }

  @Nonnull
  public IMutablePrice getSinglePriceOfQuantity (@Nonnegative final int nQuantity, @Nonnull final IVATItem aVATItem)
  {
    ValueEnforcer.notNull (aVATItem, "VATItem");
    return _createPrice (_getItemOfQuantity (nQuantity).getUnitNetAmount (), aVATItem);
  }

  @Nonnull
  public IMutablePrice getTotalPriceOfQuantity (@Nonnegative final int nQuantity, @Nonnull final IVATItem aVAT)
  {
    return getSinglePriceOfQuantity (nQuantity, aVAT).getMultiplied (nQuantity);
  }

  @Nonnull
  public EChange addItem (@Nonnegative final int nMinimumQuantity, @Nonnull final BigDecimal aUnitNetAmount)
  {
    return addItem (new PriceGraduationItem (nMinimumQuantity, aUnitNetAmount));
  }

  @Nonnull
  public EChange addItem (@Nonnull final IMutablePriceGraduationItem aItem)
  {
    ValueEnforcer.notNull (aItem, "Item");

    // Check if an item with the exact same minimum quantity is already
    // contained.
    int nInsertIndex = 0;
    final int nNewItemQuantity = aItem.getMinimumQuantity ();
    for (final IMutablePriceGraduationItem aExistingItem : m_aItems)
    {
      final int nExistingMinQuantity = aExistingItem.getMinimumQuantity ();
      if (nExistingMinQuantity == nNewItemQuantity)
        throw new IllegalArgumentException ("Another item with the exact same quantity is already contained: " + nExistingMinQuantity);

      // Find the insertion index
      if (nNewItemQuantity > nExistingMinQuantity)
        ++nInsertIndex;
    }

    m_aItems.add (nInsertIndex, aItem);
    return EChange.CHANGED;
  }

  @Nonnull
  public EChange removeAll ()
  {
    return m_aItems.removeAll ();
  }

  public boolean isEmpty ()
  {
    return m_aItems.isEmpty ();
  }

  @Nonnegative
  public int size ()
  {
    return m_aItems.size ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final PriceGraduation rhs = (PriceGraduation) o;
    return EqualsHelper.equals (m_eCurrency, rhs.m_eCurrency) && m_aItems.equals (rhs.m_aItems);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_eCurrency).append (m_aItems).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("currency", m_eCurrency).append ("items", m_aItems).getToString ();
  }

  /**
   * Create a simple price graduation that contains one item with the minimum
   * quantity of 1.
   *
   * @param aPrice
   *        The price to use. May not be <code>null</code>.
   * @return Never <code>null</code>.
   */
  @Nonnull
  public static IMutablePriceGraduation createSimple (@Nonnull final IMutablePrice aPrice)
  {
    final PriceGraduation ret = new PriceGraduation (aPrice.getCurrency ());
    ret.addItem (new PriceGraduationItem (1, aPrice.getNetAmount ().getValue ()));
    return ret;
  }
}
