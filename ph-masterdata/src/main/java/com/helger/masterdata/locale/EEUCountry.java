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
package com.helger.masterdata.locale;

import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.Nonnegative;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;
import com.helger.text.locale.country.CountryCache;

/**
 * Enumeration with all countries that are part of the EU.
 *
 * @author Philip Helger
 */
public enum EEUCountry implements IHasID <String>
{
  AUSTRIA ("AT", LocalDate.of (1995, Month.JANUARY, 1), null),
  BELGIUM ("BE", LocalDate.of (1952, Month.JANUARY, 1), null),
  BULGARIA ("BG", LocalDate.of (2007, Month.JANUARY, 1), null),
  CROATIA ("HR", LocalDate.of (2013, Month.JANUARY, 1), null),
  CYPRUS ("CY", LocalDate.of (2004, Month.JANUARY, 1), null),
  CZECH_REPUBLIC ("CZ", LocalDate.of (2004, Month.JANUARY, 1), null),
  DENMARK ("DK", LocalDate.of (1973, Month.JANUARY, 1), null),
  ESTONIA ("EE", LocalDate.of (2004, Month.JANUARY, 1), null),
  FINLAND ("FI", LocalDate.of (1995, Month.JANUARY, 1), null),
  FRANCE ("FR", LocalDate.of (1952, Month.JANUARY, 1), null),
  GERMANY ("DE", LocalDate.of (1952, Month.JANUARY, 1), null),
  GREECE ("GR", LocalDate.of (1981, Month.JANUARY, 1), null),
  HUNGARY ("HU", LocalDate.of (2004, Month.JANUARY, 1), null),
  IRELAND ("IE", LocalDate.of (1973, Month.JANUARY, 1), null),
  ITALY ("IT", LocalDate.of (1952, Month.JANUARY, 1), null),
  LATVIA ("LV", LocalDate.of (2004, Month.JANUARY, 1), null),
  LITHUANIA ("LT", LocalDate.of (2004, Month.JANUARY, 1), null),
  LUXEMBOURG ("LU", LocalDate.of (1952, Month.JANUARY, 1), null),
  MALTA ("MT", LocalDate.of (2004, Month.JANUARY, 1), null),
  NETHERLANDS ("NL", LocalDate.of (1952, Month.JANUARY, 1), null),
  POLAND ("PL", LocalDate.of (2004, Month.JANUARY, 1), null),
  PORTUGAL ("PT", LocalDate.of (1986, Month.JANUARY, 1), null),
  ROMANIA ("RO", LocalDate.of (2007, Month.JANUARY, 1), null),
  SWEDEN ("SE", LocalDate.of (1995, Month.JANUARY, 1), null),
  SLOVAKIA ("SK", LocalDate.of (2004, Month.JANUARY, 1), null),
  SLOVENIA ("SI", LocalDate.of (2004, Month.JANUARY, 1), null),
  SPAIN ("ES", LocalDate.of (1986, Month.JANUARY, 1), null),
  UNITED_KINGDOM ("GB", LocalDate.of (1973, Month.JANUARY, 1), LocalDate.of (2020, Month.DECEMBER, 31));

  private final String m_sCountryCode;
  private final Locale m_aCountry;
  private final LocalDate m_aJoinDate;
  private final LocalDate m_aLeaveDate;

  /**
   * @param sCountryCode
   *        Country code
   * @param aJoinDate
   *        Join date. Never <code>null</code>.
   * @param aLeaveDate
   *        The date when the country left the EU. May be <code>null</code>.
   */
  EEUCountry (@NonNull @Nonempty final String sCountryCode, @NonNull final LocalDate aJoinDate, @Nullable final LocalDate aLeaveDate)
  {
    m_sCountryCode = sCountryCode;
    m_aCountry = CountryCache.getInstance ().getCountry (sCountryCode);
    if (m_aCountry == null)
      throw new IllegalStateException ("Failed to resolve country '" + sCountryCode + "'");
    m_aJoinDate = aJoinDate;
    m_aLeaveDate = aLeaveDate;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sCountryCode;
  }

  @NonNull
  @Nonempty
  public String getCountryCode ()
  {
    return m_sCountryCode;
  }

  @NonNull
  public Locale getCountry ()
  {
    return m_aCountry;
  }

  @NonNull
  public LocalDate getJoinDate ()
  {
    return m_aJoinDate;
  }

  @Nonnegative
  public int getJoinYear ()
  {
    return m_aJoinDate.getYear ();
  }

  /**
   * @return Get the leave date of the country. May be <code>null</code> if the
   *         country is still in the EU.
   * @since 6.1.9
   */
  @Nullable
  public LocalDate getLeaveDate ()
  {
    return m_aLeaveDate;
  }

  /**
   * @return <code>true</code> if the country has left the EU,
   *         <code>false</code> if not. Introduced for EU leave 2020.
   * @since 6.1.9
   */
  public boolean hasLeaveDate ()
  {
    return m_aLeaveDate != null;
  }

  /**
   * Check if this country was in the EU at the provided date.
   *
   * @param aDate
   *        The date to check. May not be <code>null</code>.
   * @return <code>true</code> if this country was in the EU at the specified
   *         time, <code>false</code> if not.
   * @since 6.1.9
   */
  public boolean isInEUAt (@NonNull final LocalDate aDate)
  {
    ValueEnforcer.notNull (aDate, "Date");
    if (aDate.isBefore (m_aJoinDate))
      return false;
    if (m_aLeaveDate != null && aDate.isAfter (m_aLeaveDate))
      return false;
    return true;
  }

  @Nullable
  public static EEUCountry getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EEUCountry.class, sID);
  }

  @Nullable
  public static EEUCountry getFromLocaleOrNull (@Nullable final Locale aLocale)
  {
    return aLocale == null ? null : getFromIDOrNull (aLocale.getCountry ());
  }

  public static boolean isEUCountry (@Nullable final Locale aLocale)
  {
    return getFromLocaleOrNull (aLocale) != null;
  }

  /**
   * Check if the provided Locale is an EU country at the provided point in
   * time.
   *
   * @param aLocale
   *        The country locale to check. May be <code>null</code>.
   * @param aDate
   *        The date to check at. May not be <code>null</code>.
   * @return <code>true</code> if the provided country is an EU member at the
   *         provided point in time.
   * @since 6.1.9
   */
  public static boolean isEUCountryAt (@Nullable final Locale aLocale, @NonNull final LocalDate aDate)
  {
    final EEUCountry eCountry = getFromLocaleOrNull (aLocale);
    return eCountry != null && eCountry.isInEUAt (aDate);
  }
}
