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
import java.util.Locale;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.joda.time.LocalDate;

import com.helger.commons.CGlobal;
import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotations.Nonempty;
import com.helger.commons.equals.EqualsUtils;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.idfactory.GlobalIDFactory;
import com.helger.commons.string.ToStringGenerator;
import com.helger.datetime.period.LocalDatePeriod;

/**
 * Default implementation of {@link IVATItem}.
 * 
 * @author Philip Helger
 */
@Immutable
public class VATItem extends LocalDatePeriod implements IVATItem
{
  private final String m_sID;
  private final EVATType m_eType;
  private final BigDecimal m_aPercentage;
  private final BigDecimal m_aPercentageFactor;
  private final BigDecimal m_aMultiplicationFactorNetToGross;
  private final boolean m_bDeprecated;

  public VATItem (@Nonnull @Nonempty final String sID,
                  @Nonnull final EVATType eType,
                  @Nonnull @Nonnegative final BigDecimal aPercentage,
                  final boolean bDeprecated)
  {
    this (sID, eType, aPercentage, bDeprecated, null, null);
  }

  public VATItem (@Nonnull @Nonempty final String sID,
                  @Nonnull final EVATType eType,
                  @Nonnull @Nonnegative final BigDecimal aPercentage,
                  final boolean bDeprecated,
                  @Nullable final LocalDate aValidFrom,
                  @Nullable final LocalDate aValidTo)
  {
    ValueEnforcer.notEmpty (sID, "ID");
    ValueEnforcer.notNull (eType, "Type");
    ValueEnforcer.notNull (aPercentage, "Percentage");
    ValueEnforcer.isBetweenInclusive (aPercentage, "Percentage", BigDecimal.ZERO, CGlobal.BIGDEC_100);
    if (aValidFrom != null && aValidTo != null && aValidTo.isBefore (aValidFrom))
      throw new IllegalArgumentException ("ValidFrom date must be <= validTo date");

    m_sID = sID;
    m_eType = eType;
    m_aPercentage = aPercentage;
    m_aPercentageFactor = m_aPercentage.divide (CGlobal.BIGDEC_100);
    m_aMultiplicationFactorNetToGross = BigDecimal.ONE.add (m_aPercentageFactor);
    m_bDeprecated = bDeprecated;
    setStart (aValidFrom);
    setEnd (aValidTo);
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  public EVATType getType ()
  {
    return m_eType;
  }

  @Nonnull
  @Nonnegative
  public BigDecimal getPercentage ()
  {
    return m_aPercentage;
  }

  @Nonnull
  @Nonnegative
  public BigDecimal getPercentageFactor ()
  {
    return m_aPercentageFactor;
  }

  @Nonnull
  @Nonnegative
  public BigDecimal getMultiplicationFactorNetToGross ()
  {
    return m_aMultiplicationFactorNetToGross;
  }

  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return EVATItemText.VAT_PERC.getDisplayTextWithArgs (aContentLocale, m_aPercentage);
  }

  public boolean isDeprecated ()
  {
    return m_bDeprecated;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!super.equals (o))
      return false;
    final VATItem rhs = (VATItem) o;
    return m_sID.equals (rhs.m_sID) &&
           m_eType.equals (rhs.m_eType) &&
           EqualsUtils.equals (m_aPercentage, rhs.m_aPercentage) &&
           m_bDeprecated == rhs.m_bDeprecated;
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ())
                            .append (m_sID)
                            .append (m_eType)
                            .append (m_aPercentage)
                            .append (m_bDeprecated)
                            .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("id", m_sID)
                            .append ("type", m_eType)
                            .append ("percentage", m_aPercentage)
                            .append ("percentageFactor", m_aPercentageFactor)
                            .append ("multiplicationFactorNetToGross", m_aMultiplicationFactorNetToGross)
                            .append ("deprecated", m_bDeprecated)
                            .toString ();
  }

  @Nonnull
  public static VATItem createNewItem (@Nonnull final EVATType eType, @Nonnull @Nonnegative final BigDecimal aPercentage)
  {
    return createNewItem (eType, aPercentage, (LocalDate) null, (LocalDate) null);
  }

  @Nonnull
  public static VATItem createNewItem (@Nonnull final EVATType eType,
                                       @Nonnull @Nonnegative final BigDecimal aPercentage,
                                       @Nullable final LocalDate aValidFrom,
                                       @Nullable final LocalDate aValidTo)
  {
    return new VATItem (GlobalIDFactory.getNewPersistentStringID (), eType, aPercentage, false, aValidFrom, aValidTo);
  }
}
