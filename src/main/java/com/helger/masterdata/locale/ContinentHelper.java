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
package com.helger.masterdata.locale;

import java.util.Locale;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.collection.multimap.IMultiMapSetBased;
import com.helger.commons.collection.multimap.MultiHashMapTreeSetBased;
import com.helger.commons.locale.country.CountryCache;

/**
 * Contains the country to continent assignment
 *
 * @author Philip Helger
 */
public final class ContinentHelper
{
  private static final IMultiMapSetBased <Locale, EContinent> s_aMap = new MultiHashMapTreeSetBased <Locale, EContinent> ();

  static
  {
    _register ("AD", EContinent.EUROPE);
    _register ("AE", EContinent.ASIA);
    _register ("AF", EContinent.ASIA);
    _register ("AG", EContinent.NORTH_AMERICA);
    _register ("AI", EContinent.NORTH_AMERICA);
    _register ("AL", EContinent.EUROPE);
    _register ("AM", EContinent.ASIA);
    _register ("AN", EContinent.NORTH_AMERICA);
    _register ("AO", EContinent.AFRICA);
    _register ("AQ", EContinent.ANTARCTICA);
    _register ("AR", EContinent.SOUTH_AMERICA);
    _register ("AS", EContinent.OCEANIA);
    _register ("AT", EContinent.EUROPE);
    _register ("AU", EContinent.OCEANIA);
    _register ("AW", EContinent.NORTH_AMERICA);
    _register ("AX", EContinent.EUROPE);
    _register ("AZ", EContinent.EUROPE, EContinent.ASIA);
    _register ("BA", EContinent.EUROPE);
    _register ("BB", EContinent.NORTH_AMERICA);
    _register ("BD", EContinent.ASIA);
    _register ("BE", EContinent.EUROPE);
    _register ("BF", EContinent.AFRICA);
    _register ("BG", EContinent.EUROPE);
    _register ("BH", EContinent.ASIA);
    _register ("BI", EContinent.AFRICA);
    _register ("BJ", EContinent.AFRICA);
    _register ("BL", EContinent.NORTH_AMERICA);
    _register ("BM", EContinent.NORTH_AMERICA);
    _register ("BN", EContinent.ASIA);
    _register ("BO", EContinent.SOUTH_AMERICA);
    _register ("BQ", EContinent.NORTH_AMERICA);
    _register ("BR", EContinent.SOUTH_AMERICA);
    _register ("BS", EContinent.NORTH_AMERICA);
    _register ("BT", EContinent.ASIA);
    // Bouvet-Insel
    _register ("BV", EContinent.UNDEFINED);
    _register ("BW", EContinent.AFRICA);
    _register ("BY", EContinent.EUROPE);
    _register ("BZ", EContinent.NORTH_AMERICA);
    _register ("CA", EContinent.NORTH_AMERICA);
    _register ("CC", EContinent.ASIA);
    _register ("CD", EContinent.AFRICA);
    _register ("CF", EContinent.AFRICA);
    _register ("CG", EContinent.AFRICA);
    _register ("CH", EContinent.EUROPE);
    _register ("CI", EContinent.AFRICA);
    _register ("CK", EContinent.OCEANIA);
    _register ("CL", EContinent.SOUTH_AMERICA);
    _register ("CM", EContinent.AFRICA);
    _register ("CN", EContinent.ASIA);
    _register ("CO", EContinent.SOUTH_AMERICA);
    _register ("CR", EContinent.NORTH_AMERICA);
    _register ("CS", EContinent.EUROPE);
    _register ("CU", EContinent.NORTH_AMERICA);
    _register ("CV", EContinent.AFRICA);
    _register ("CW", EContinent.NORTH_AMERICA);
    _register ("CX", EContinent.ASIA);
    _register ("CY", EContinent.ASIA, EContinent.EUROPE);
    _register ("CZ", EContinent.EUROPE);
    _register ("DE", EContinent.EUROPE);
    _register ("DJ", EContinent.AFRICA);
    _register ("DK", EContinent.EUROPE);
    _register ("DM", EContinent.NORTH_AMERICA);
    _register ("DO", EContinent.NORTH_AMERICA);
    _register ("DZ", EContinent.AFRICA);
    _register ("EC", EContinent.SOUTH_AMERICA);
    _register ("EE", EContinent.EUROPE);
    _register ("EG", EContinent.AFRICA);
    _register ("EH", EContinent.AFRICA);
    _register ("ER", EContinent.AFRICA);
    _register ("ES", EContinent.EUROPE);
    _register ("ET", EContinent.AFRICA);
    _register ("FI", EContinent.EUROPE);
    _register ("FJ", EContinent.OCEANIA);
    _register ("FK", EContinent.SOUTH_AMERICA);
    _register ("FM", EContinent.OCEANIA);
    _register ("FO", EContinent.EUROPE);
    _register ("FR", EContinent.EUROPE);
    _register ("GA", EContinent.AFRICA);
    _register ("GB", EContinent.EUROPE);
    _register ("GD", EContinent.NORTH_AMERICA);
    _register ("GE", EContinent.ASIA);
    _register ("GF", EContinent.SOUTH_AMERICA);
    _register ("GG", EContinent.EUROPE);
    _register ("GH", EContinent.AFRICA);
    _register ("GI", EContinent.EUROPE);
    _register ("GL", EContinent.NORTH_AMERICA);
    _register ("GM", EContinent.AFRICA);
    _register ("GN", EContinent.AFRICA);
    _register ("GP", EContinent.NORTH_AMERICA);
    _register ("GQ", EContinent.AFRICA);
    _register ("GR", EContinent.EUROPE);
    _register ("GS", EContinent.SOUTH_AMERICA);
    _register ("GT", EContinent.NORTH_AMERICA);
    _register ("GU", EContinent.OCEANIA);
    _register ("GW", EContinent.AFRICA);
    _register ("GY", EContinent.SOUTH_AMERICA);
    _register ("HK", EContinent.ASIA);
    _register ("HM", EContinent.OCEANIA);
    _register ("HN", EContinent.NORTH_AMERICA);
    _register ("HR", EContinent.EUROPE);
    _register ("HT", EContinent.NORTH_AMERICA);
    _register ("HU", EContinent.EUROPE);
    _register ("ID", EContinent.ASIA);
    _register ("IE", EContinent.EUROPE);
    _register ("IL", EContinent.ASIA);
    _register ("IM", EContinent.EUROPE);
    _register ("IN", EContinent.ASIA);
    _register ("IO", EContinent.ASIA);
    _register ("IQ", EContinent.ASIA);
    _register ("IR", EContinent.ASIA);
    _register ("IS", EContinent.EUROPE);
    _register ("IT", EContinent.EUROPE);
    _register ("JE", EContinent.EUROPE);
    _register ("JM", EContinent.NORTH_AMERICA);
    _register ("JO", EContinent.ASIA);
    _register ("JP", EContinent.ASIA);
    _register ("KE", EContinent.AFRICA);
    _register ("KG", EContinent.ASIA);
    _register ("KH", EContinent.ASIA);
    _register ("KI", EContinent.OCEANIA);
    _register ("KM", EContinent.AFRICA);
    _register ("KN", EContinent.NORTH_AMERICA);
    _register ("KP", EContinent.ASIA);
    _register ("KR", EContinent.ASIA);
    _register ("KW", EContinent.ASIA);
    _register ("KY", EContinent.NORTH_AMERICA);
    _register ("KZ", EContinent.ASIA, EContinent.EUROPE);
    _register ("LA", EContinent.ASIA);
    _register ("LB", EContinent.ASIA);
    _register ("LC", EContinent.NORTH_AMERICA);
    _register ("LI", EContinent.EUROPE);
    _register ("LK", EContinent.ASIA);
    _register ("LR", EContinent.AFRICA);
    _register ("LS", EContinent.AFRICA);
    _register ("LT", EContinent.EUROPE);
    _register ("LU", EContinent.EUROPE);
    _register ("LV", EContinent.EUROPE);
    _register ("LY", EContinent.AFRICA);
    _register ("MA", EContinent.AFRICA);
    _register ("MC", EContinent.EUROPE);
    _register ("MD", EContinent.EUROPE);
    _register ("ME", EContinent.EUROPE);
    _register ("MF", EContinent.NORTH_AMERICA);
    _register ("MG", EContinent.AFRICA);
    _register ("MH", EContinent.OCEANIA);
    _register ("MK", EContinent.EUROPE);
    _register ("ML", EContinent.AFRICA);
    _register ("MM", EContinent.ASIA);
    _register ("MN", EContinent.ASIA);
    _register ("MO", EContinent.ASIA);
    _register ("MP", EContinent.OCEANIA);
    _register ("MQ", EContinent.NORTH_AMERICA);
    _register ("MR", EContinent.AFRICA);
    _register ("MS", EContinent.NORTH_AMERICA);
    _register ("MT", EContinent.EUROPE);
    _register ("MU", EContinent.AFRICA);
    _register ("MV", EContinent.ASIA);
    _register ("MW", EContinent.AFRICA);
    _register ("MX", EContinent.NORTH_AMERICA);
    _register ("MY", EContinent.ASIA);
    _register ("MZ", EContinent.AFRICA);
    _register ("NA", EContinent.AFRICA);
    _register ("NC", EContinent.OCEANIA);
    _register ("NE", EContinent.AFRICA);
    _register ("NF", EContinent.OCEANIA);
    _register ("NG", EContinent.AFRICA);
    _register ("NI", EContinent.NORTH_AMERICA);
    _register ("NL", EContinent.EUROPE);
    _register ("NO", EContinent.EUROPE);
    _register ("NP", EContinent.ASIA);
    _register ("NR", EContinent.OCEANIA);
    _register ("NU", EContinent.OCEANIA);
    _register ("NZ", EContinent.OCEANIA);
    _register ("OM", EContinent.ASIA);
    _register ("PA", EContinent.NORTH_AMERICA);
    _register ("PE", EContinent.SOUTH_AMERICA);
    _register ("PF", EContinent.OCEANIA);
    _register ("PG", EContinent.OCEANIA);
    _register ("PH", EContinent.ASIA);
    _register ("PK", EContinent.ASIA);
    _register ("PL", EContinent.EUROPE);
    _register ("PM", EContinent.NORTH_AMERICA);
    _register ("PN", EContinent.OCEANIA);
    _register ("PR", EContinent.NORTH_AMERICA);
    _register ("PS", EContinent.ASIA);
    _register ("PT", EContinent.EUROPE);
    _register ("PW", EContinent.OCEANIA);
    _register ("PY", EContinent.SOUTH_AMERICA);
    _register ("QA", EContinent.ASIA);
    _register ("RE", EContinent.AFRICA);
    _register ("RO", EContinent.EUROPE);
    _register ("RS", EContinent.EUROPE);
    _register ("RU", EContinent.EUROPE, EContinent.ASIA);
    _register ("RW", EContinent.AFRICA);
    _register ("SA", EContinent.ASIA);
    _register ("SB", EContinent.OCEANIA);
    _register ("SC", EContinent.AFRICA);
    _register ("SD", EContinent.AFRICA);
    _register ("SE", EContinent.EUROPE);
    _register ("SG", EContinent.ASIA);
    // St. Helena
    _register ("SH", EContinent.UNDEFINED);
    _register ("SI", EContinent.EUROPE);
    // Svalbard und Jan Mayen
    _register ("SJ", EContinent.UNDEFINED);
    _register ("SK", EContinent.EUROPE);
    _register ("SL", EContinent.AFRICA);
    _register ("SM", EContinent.EUROPE);
    _register ("SN", EContinent.AFRICA);
    _register ("SO", EContinent.AFRICA);
    _register ("SR", EContinent.SOUTH_AMERICA);
    _register ("ST", EContinent.AFRICA);
    _register ("SV", EContinent.NORTH_AMERICA);
    _register ("SX", EContinent.NORTH_AMERICA);
    _register ("SY", EContinent.ASIA);
    _register ("SZ", EContinent.AFRICA);
    _register ("TC", EContinent.NORTH_AMERICA);
    _register ("TD", EContinent.AFRICA);
    _register ("TF", EContinent.AFRICA);
    _register ("TG", EContinent.AFRICA);
    _register ("TH", EContinent.ASIA);
    _register ("TJ", EContinent.ASIA);
    _register ("TK", EContinent.OCEANIA);
    _register ("TL", EContinent.ASIA);
    _register ("TM", EContinent.ASIA);
    _register ("TN", EContinent.AFRICA);
    _register ("TO", EContinent.OCEANIA);
    _register ("TR", EContinent.EUROPE, EContinent.ASIA);
    _register ("TT", EContinent.NORTH_AMERICA);
    _register ("TV", EContinent.OCEANIA);
    _register ("TW", EContinent.ASIA);
    _register ("TZ", EContinent.AFRICA);
    _register ("UA", EContinent.EUROPE);
    _register ("UG", EContinent.AFRICA);
    _register ("UM", EContinent.OCEANIA);
    _register ("US", EContinent.NORTH_AMERICA);
    _register ("UY", EContinent.SOUTH_AMERICA);
    _register ("UZ", EContinent.ASIA);
    _register ("VA", EContinent.EUROPE);
    _register ("VC", EContinent.NORTH_AMERICA);
    _register ("VE", EContinent.SOUTH_AMERICA);
    _register ("VG", EContinent.NORTH_AMERICA);
    _register ("VI", EContinent.NORTH_AMERICA);
    _register ("VN", EContinent.ASIA);
    _register ("VU", EContinent.OCEANIA);
    _register ("WF", EContinent.OCEANIA);
    _register ("WS", EContinent.OCEANIA);
    _register ("YE", EContinent.ASIA);
    _register ("YT", EContinent.AFRICA);
    _register ("ZA", EContinent.AFRICA);
    _register ("ZM", EContinent.AFRICA);
    _register ("ZW", EContinent.AFRICA);
  }

  /**
   * Register assignment
   *
   * @param sCountryCode
   *        Country code to be used. May not be <code>null</code> nor empty
   * @param aContinents
   *        The enum to be used. May not be <code>null</code> but may contain a
   *        single <code>null</code> element.
   */
  private static void _register (@Nonnull @Nonempty final String sCountryCode, @Nonnull final EContinent... aContinents)
  {
    final Locale aCountry = CountryCache.getInstance ().getCountry (sCountryCode);
    if (s_aMap.containsKey (aCountry))
      throw new IllegalArgumentException ("Country code '" + sCountryCode + "' is already registered!");
    for (final EContinent eContinent : aContinents)
      s_aMap.putSingle (aCountry, eContinent);
  }

  /**
   * Get all continents for the specified country ID
   *
   * @param aLocale
   *        The locale to be used. May be <code>null</code>.
   * @return <code>null</code> if no continent data is defined. Otherwise a non-
   *         <code>null</code> Set with all continents, without
   *         <code>null</code> elements.
   */
  @Nullable
  @ReturnsMutableCopy
  public static Set <EContinent> getContinentsOfCountry (@Nullable final Locale aLocale)
  {
    final Locale aCountry = CountryCache.getInstance ().getCountry (aLocale);
    if (aCountry != null)
    {
      final Set <EContinent> ret = s_aMap.get (aCountry);
      if (ret != null)
        return CollectionHelper.newSortedSet (ret);
    }
    return null;
  }

  /**
   * Get all continents for the specified country ID
   *
   * @param sCountryID
   *        The country ID to be used. May be <code>null</code>.
   * @return <code>null</code> if no continent data is defined. Otherwise a non-
   *         <code>null</code> Set with all continents, without
   *         <code>null</code> elements.
   */
  @Nullable
  @ReturnsMutableCopy
  public static Set <EContinent> getContinentsOfCountry (@Nullable final String sCountryID)
  {
    final Locale aCountry = CountryCache.getInstance ().getCountry (sCountryID);
    if (aCountry != null)
    {
      final Set <EContinent> ret = s_aMap.get (aCountry);
      if (ret != null)
        return CollectionHelper.newSortedSet (ret);
    }
    return null;
  }
}
