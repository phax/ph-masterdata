/*
 * Copyright (C) 2014-2024 Philip Helger (www.helger.com)
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

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;

/**
 * Default implementation class of {@link IMutablePriceGraduationItem} and
 * {@link IPriceGraduationItem}.
 *
 * @author Philip Helger
 */
public class PriceGraduationItem implements IMutablePriceGraduationItem, Serializable
{
  private int m_nMinimumQuantity;
  private BigDecimal m_aNetAmount;

  public PriceGraduationItem (@Nonnegative final int nMinimumQuantity, @Nonnull final BigDecimal aUnitNetAmount)
  {
    setMinimumQuantity (nMinimumQuantity);
    setUnitNetAmount (aUnitNetAmount);
  }

  @Nonnegative
  public final int getMinimumQuantity ()
  {
    return m_nMinimumQuantity;
  }

  @Nonnull
  public final EChange setMinimumQuantity (@Nonnegative final int nMinimumQuantity)
  {
    ValueEnforcer.isGT0 (nMinimumQuantity, "MinimumQuantity");

    if (nMinimumQuantity == m_nMinimumQuantity)
      return EChange.UNCHANGED;
    m_nMinimumQuantity = nMinimumQuantity;
    return EChange.CHANGED;
  }

  @Nonnull
  public final BigDecimal getUnitNetAmount ()
  {
    return m_aNetAmount;
  }

  @Nonnull
  public final EChange setUnitNetAmount (@Nonnull final BigDecimal aNetAmount)
  {
    ValueEnforcer.notNull (aNetAmount, "NetAmount");

    if (EqualsHelper.equals (aNetAmount, m_aNetAmount))
      return EChange.UNCHANGED;
    m_aNetAmount = aNetAmount;
    return EChange.CHANGED;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final PriceGraduationItem rhs = (PriceGraduationItem) o;
    return m_nMinimumQuantity == rhs.m_nMinimumQuantity && EqualsHelper.equals (m_aNetAmount, rhs.m_aNetAmount);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_nMinimumQuantity).append (m_aNetAmount).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("minQuantity", m_nMinimumQuantity).append ("priceamount", m_aNetAmount).getToString ();
  }
}
