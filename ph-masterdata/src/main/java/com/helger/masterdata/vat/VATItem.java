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
package com.helger.masterdata.vat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.Nonnegative;
import com.helger.annotation.concurrent.Immutable;
import com.helger.base.CGlobal;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.equals.EqualsHelper;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.id.factory.GlobalIDFactory;
import com.helger.base.numeric.BigHelper;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.datetime.period.LocalDatePeriod;
import com.helger.text.locale.country.CountryCache;

/**
 * Default implementation of {@link IVATItem}.
 *
 * @author Philip Helger
 */
@Immutable
public class VATItem implements IVATItem, Serializable
{
  private final String m_sID;
  private final Locale m_aCountry;
  private final EVATItemType m_eType;
  private final BigDecimal m_aPercentage;
  private final BigDecimal m_aPercentageFactor;
  private final BigDecimal m_aMultiplicationFactorNetToGross;
  private final boolean m_bDeprecated;
  private final LocalDatePeriod m_aPeriod;

  public VATItem (@NonNull @Nonempty final String sID,
                  @Nullable final Locale aCountry,
                  @NonNull final EVATItemType eType,
                  @NonNull @Nonnegative final BigDecimal aPercentage,
                  final boolean bDeprecated,
                  @Nullable final LocalDate aValidFrom,
                  @Nullable final LocalDate aValidTo)
  {
    ValueEnforcer.notEmpty (sID, "ID");
    ValueEnforcer.notNull (eType, "Type");
    ValueEnforcer.notNull (aPercentage, "Percentage");
    ValueEnforcer.isBetweenInclusive (aPercentage, "Percentage", BigDecimal.ZERO, CGlobal.BIGDEC_100);
    // Simply not true
    if (false)
      if (BigHelper.isNE0 (aPercentage))
        ValueEnforcer.notNull (aCountry, "If a percentage is present a country must be present!");
    if (aValidFrom != null && aValidTo != null && aValidTo.isBefore (aValidFrom))
      throw new IllegalArgumentException ("ValidFrom date must be <= validTo date");

    m_sID = sID;
    m_aCountry = CountryCache.getInstance ().getCountry (aCountry);
    m_eType = eType;
    m_aPercentage = aPercentage;
    m_aPercentageFactor = m_aPercentage.divide (CGlobal.BIGDEC_100);
    m_aMultiplicationFactorNetToGross = BigDecimal.ONE.add (m_aPercentageFactor);
    m_bDeprecated = bDeprecated;
    m_aPeriod = new LocalDatePeriod (aValidFrom, aValidTo);
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public Locale getCountry ()
  {
    return m_aCountry;
  }

  @NonNull
  public EVATItemType getType ()
  {
    return m_eType;
  }

  @NonNull
  @Nonnegative
  public BigDecimal getPercentage ()
  {
    return m_aPercentage;
  }

  @NonNull
  @Nonnegative
  public BigDecimal getPercentageFactor ()
  {
    return m_aPercentageFactor;
  }

  @NonNull
  @Nonnegative
  public BigDecimal getMultiplicationFactorNetToGross ()
  {
    return m_aMultiplicationFactorNetToGross;
  }

  @Nullable
  public String getDisplayText (@NonNull final Locale aContentLocale)
  {
    return EVATItemText.VAT_PERC.getDisplayTextWithArgs (aContentLocale, m_aPercentage);
  }

  public boolean isDeprecated ()
  {
    return m_bDeprecated;
  }

  @NonNull
  public LocalDatePeriod getPeriod ()
  {
    return m_aPeriod;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final VATItem rhs = (VATItem) o;
    return m_sID.equals (rhs.m_sID) &&
           EqualsHelper.equals (m_aCountry, rhs.m_aCountry) &&
           m_eType.equals (rhs.m_eType) &&
           BigHelper.equalValues (m_aPercentage, rhs.m_aPercentage) &&
           m_bDeprecated == rhs.m_bDeprecated &&
           m_aPeriod.equals (rhs.m_aPeriod);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_sID)
                                       .append (m_aCountry)
                                       .append (m_eType)
                                       .append (m_aPercentage)
                                       .append (m_bDeprecated)
                                       .append (m_aPeriod)
                                       .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ID", m_sID)
                                       .append ("Country", m_aCountry)
                                       .append ("Type", m_eType)
                                       .append ("Percentage", m_aPercentage)
                                       .append ("PercentageFactor", m_aPercentageFactor)
                                       .append ("MultiplicationFactorNetToGross", m_aMultiplicationFactorNetToGross)
                                       .append ("Deprecated", m_bDeprecated)
                                       .append ("Period", m_aPeriod)
                                       .getToString ();
  }

  @NonNull
  public static VATItem createTestItem (@Nullable final Locale aCountry,
                                        @NonNull final EVATItemType eType,
                                        @NonNull @Nonnegative final BigDecimal aPercentage)
  {
    final String sID = (aCountry != null ? aCountry.getCountry ().toLowerCase (Locale.US) : "zero") +
                       "." +
                       aPercentage.intValue () +
                       "/" +
                       GlobalIDFactory.getNewStringID ();
    return new VATItem (sID, aCountry, eType, aPercentage, false, null, null);
  }
}
