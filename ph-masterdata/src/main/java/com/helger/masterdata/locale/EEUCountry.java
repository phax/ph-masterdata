/**
 * Copyright (C) 2014-2018 Philip Helger (www.helger.com)
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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.functional.IPredicate;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.locale.country.CountryCache;

public enum EEUCountry implements IHasID <String>
{
  BELGIUM ("BE", LocalDate.of (1952, Month.JANUARY, 1)),
  BULGARIA ("BG", LocalDate.of (2007, Month.JANUARY, 1)),
  DENMARK ("DK", LocalDate.of (1973, Month.JANUARY, 1)),
  GERMANY ("DE", LocalDate.of (1952, Month.JANUARY, 1)),
  ESTONIA ("EE", LocalDate.of (2004, Month.JANUARY, 1)),
  FINLAND ("FI", LocalDate.of (1995, Month.JANUARY, 1)),
  FRANCE ("FR", LocalDate.of (1952, Month.JANUARY, 1)),
  GREECE ("GR", LocalDate.of (1981, Month.JANUARY, 1)),
  IRELAND ("IE", LocalDate.of (1973, Month.JANUARY, 1)),
  ITALY ("IT", LocalDate.of (1952, Month.JANUARY, 1)),
  CROATIA ("HR", LocalDate.of (2013, Month.JANUARY, 1)),
  LATVIA ("LV", LocalDate.of (2004, Month.JANUARY, 1)),
  LITHUANIA ("LT", LocalDate.of (2004, Month.JANUARY, 1)),
  LUXEMBOURG ("LU", LocalDate.of (1952, Month.JANUARY, 1)),
  MALTA ("MT", LocalDate.of (2004, Month.JANUARY, 1)),
  NETHERLANDS ("NL", LocalDate.of (1952, Month.JANUARY, 1)),
  AUSTRIA ("AT", LocalDate.of (1995, Month.JANUARY, 1)),
  POLAND ("PL", LocalDate.of (2004, Month.JANUARY, 1)),
  PORTUGAL ("PT", LocalDate.of (1986, Month.JANUARY, 1)),
  ROMANIA ("RO", LocalDate.of (2007, Month.JANUARY, 1)),
  SWEDEN ("SE", LocalDate.of (1995, Month.JANUARY, 1)),
  SLOVAKIA ("SK", LocalDate.of (2004, Month.JANUARY, 1)),
  SLOVENIA ("SI", LocalDate.of (2004, Month.JANUARY, 1)),
  SPAIN ("ES", LocalDate.of (1986, Month.JANUARY, 1)),
  CZECH_REPUBLIC ("CZ", LocalDate.of (2004, Month.JANUARY, 1)),
  HUNGARY ("HU", LocalDate.of (2004, Month.JANUARY, 1)),
  UNITED_KINGDOM ("GB", LocalDate.of (1973, Month.JANUARY, 1)),
  CYPRUS ("CY", LocalDate.of (2004, Month.JANUARY, 1));

  private final String m_sCountryCode;
  private final Locale m_aCountry;
  private final LocalDate m_aJoinDate;

  /**
   * @param sCountryCode
   *        Country code
   * @param aJoinDate
   *        Join date
   */
  private EEUCountry (@Nonnull @Nonempty final String sCountryCode, @Nonnull final LocalDate aJoinDate)
  {
    m_sCountryCode = sCountryCode;
    m_aCountry = CountryCache.getInstance ().getCountry (sCountryCode);
    if (m_aCountry == null)
      throw new IllegalStateException ("Failed to resolve country '" + sCountryCode + "'");
    m_aJoinDate = aJoinDate;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sCountryCode;
  }

  @Nonnull
  @Nonempty
  public String getCountryCode ()
  {
    return m_sCountryCode;
  }

  @Nonnull
  public Locale getCountry ()
  {
    return m_aCountry;
  }

  @Nonnull
  public LocalDate getJoinDate ()
  {
    return m_aJoinDate;
  }

  @Nonnegative
  public int getJoinYear ()
  {
    return m_aJoinDate.getYear ();
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

  @Nonnull
  public static IPredicate <Locale> filterLocaleCountryIsEUCountry ()
  {
    return aLocale -> EEUCountry.isEUCountry (aLocale);
  }
}
