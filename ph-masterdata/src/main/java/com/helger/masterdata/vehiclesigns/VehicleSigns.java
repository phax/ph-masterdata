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
package com.helger.masterdata.vehiclesigns;

import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.concurrent.Immutable;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.exception.InitializationException;
import com.helger.collection.commons.CommonsHashMap;
import com.helger.collection.commons.CommonsLinkedHashSet;
import com.helger.collection.commons.ICommonsMap;
import com.helger.collection.commons.ICommonsOrderedSet;
import com.helger.text.locale.country.CountryCache;

/**
 * Source: http://www.unece.org/fileadmin/DAM/trans/conventn/Distsigns.pdf Old
 * Source: http://www.unece.org/trans/main/wp1/wp1fdoc/disting-signs-5-2001.pdf
 *
 * @author Philip Helger
 */
@Immutable
public final class VehicleSigns
{
  private static final ICommonsMap <Locale, ICommonsOrderedSet <String>> COUNTRY_TO_SIGN = new CommonsHashMap <> ();
  private static final ICommonsMap <String, ICommonsOrderedSet <Locale>> SIGN_TO_COUNTRY = new CommonsHashMap <> ();

  static
  {
    _add ("AL", "AL");
    _add ("DZ", "DZ");
    _add ("AD", "AND");
    _add ("AR", "RA");
    _add ("AM", "AM");
    _add ("AU", "AUS");
    _add ("AT", "A");
    _add ("AZ", "AZ");

    _add ("BS", "BS");
    _add ("BH", "BRN");
    _add ("BD", "BD");
    _add ("BB", "BDS");
    // old: _add ("BY", "SU");
    _add ("BY", "BY");
    _add ("BE", "B");
    _add ("BZ", "BH");
    _add ("BJ", "DY");
    _add ("BA", "BIH");
    // old: _add ("BW", "RB");
    _add ("BW", "BW");
    _add ("BR", "BR");
    _add ("BN", "BRU");
    _add ("BG", "BG");

    // old: _add ("KH", "K");
    _add ("KH", "KH");
    _add ("CA", "CDN");
    _add ("CF", "RCA");
    _add ("CL", "RCH");
    _add ("CN", "RC");
    _add ("CG", "RCB");
    _add ("CR", "CR");
    _add ("CI", "CI");
    _add ("HR", "HR");
    _add ("CU", "CU");
    _add ("CY", "CY");
    _add ("CZ", "CZ");

    _add ("CD", "ZRE");
    _add ("DK", "DK");
    _add ("FO", "FO");
    _add ("DO", "DOM");

    _add ("EC", "EC");
    _add ("EG", "ET");
    _add ("EE", "EST");

    _add ("FJ", "FJI");
    _add ("FI", "FIN");
    _add ("FR", "F");

    _add ("GM", "WAG");
    _add ("GE", "GE");
    _add ("DE", "D");
    _add ("GH", "GH");
    // deleted: _add ("GI", "GBZ");
    _add ("GR", "GR");
    _add ("GD", "WG");
    _add ("GT", "GCA");
    _add ("GY", "GUY");

    _add ("HT", "RH");
    _add ("VA", "V");
    _add ("HU", "H");

    _add ("IS", "IS");
    _add ("IN", "IND");
    _add ("ID", "RI");
    _add ("IR", "IR");
    _add ("IE", "IRL");
    _add ("IL", "IL");
    _add ("IT", "I");

    _add ("JM", "JA");
    _add ("JP", "J");
    _add ("JO", "HKJ");

    _add ("KZ", "KZ");
    _add ("KE", "EAK");
    _add ("KE", "E.A.K.");
    _add ("KW", "KWT");
    _add ("KG", "KS");

    _add ("LA", "LAO");
    _add ("LV", "LV");
    _add ("LB", "RL");
    _add ("LS", "LS");
    _add ("LT", "LT");
    _add ("LU", "L");

    _add ("MG", "RM");
    _add ("MW", "MW");
    _add ("MY", "MAL");
    _add ("ML", "RMM");
    _add ("MT", "M");
    _add ("MU", "MS");
    _add ("MX", "MEX");
    _add ("MD", "MD");
    _add ("MC", "MC");
    _add ("MN", "MGL");
    _add ("ME", "MNE");
    _add ("MA", "MA");
    _add ("MM", "BUR");

    _add ("NA", "NAM");
    _add ("NL", "NL");
    _add ("AN", "NA");
    _add ("NZ", "NZ");
    _add ("NI", "NIC");
    _add ("NE", "RN");
    _add ("NG", "WAN");
    _add ("NO", "N");

    _add ("PK", "PK");
    _add ("PG", "PNG");
    _add ("PY", "PY");
    _add ("PE", "PE");
    _add ("PH", "RP");
    _add ("PL", "PL");
    _add ("PT", "P");

    _add ("KP", "ROK");
    _add ("KR", "ROK");
    _add ("RU", "RUS");
    _add ("RW", "RWA");

    _add ("LC", "WL");
    _add ("VC", "WV");
    _add ("WS", "WS");
    _add ("SM", "RSM");
    _add ("SN", "SN");
    _add ("RS", "SRB");
    _add ("SC", "SY");
    _add ("SL", "WAL");
    _add ("SG", "SGP");
    _add ("SK", "SK");
    _add ("SI", "SLO");
    _add ("ZA", "ZA");
    _add ("ES", "E");
    _add ("LK", "CL");
    _add ("SR", "SME");
    _add ("SZ", "SD");
    _add ("SE", "S");
    _add ("CH", "CH");
    _add ("SY", "SYR");

    _add ("TJ", "TJ");
    _add ("TH", "T");
    _add ("MK", "MK");
    _add ("TG", "TG");
    _add ("TT", "TT");
    _add ("TN", "TN");
    _add ("TR", "TR");
    _add ("TM", "TM");

    _add ("UG", "EAU");
    _add ("UA", "UA");
    _add ("GB", "GB");
    // Alderney
    _add ("GB", "GBA");
    _add ("GG", "GBG");
    _add ("IM", "GBM");
    _add ("JE", "GBJ");
    // Tanganyika
    _add ("TZ", "EAT");
    // Zanzibar
    _add ("TZ", "EAZ");
    _add ("US", "USA");
    _add ("UY", "ROU");
    _add ("UZ", "UZ");

    _add ("VE", "YV");

    // deleted: _add ("YE", "YAR");

    _add ("ZM", "RNR");
    _add ("ZW", "ZW");
  }

  private static void _add (@NonNull final String sCountryCode, @NonNull final String sSign)
  {
    final Locale aCountry = CountryCache.getInstance ().getCountry (sCountryCode);
    if (!COUNTRY_TO_SIGN.computeIfAbsent (aCountry, k -> new CommonsLinkedHashSet <> ()).add (sSign))
      throw new InitializationException ("Locale " + aCountry + " already contains sign '" + sSign + "'");
    if (!SIGN_TO_COUNTRY.computeIfAbsent (sSign, k -> new CommonsLinkedHashSet <> ()).add (aCountry))
      throw new InitializationException ("Sign '" + sSign + "' already contains country " + aCountry);
  }

  private VehicleSigns ()
  {}

  @NonNull
  @ReturnsMutableCopy
  public static ICommonsOrderedSet <String> getAllVehicleSigns (@Nullable final String sCountry)
  {
    final Locale aRealCountry = CountryCache.getInstance ().getCountry (sCountry);
    final ICommonsOrderedSet <String> aSigns = COUNTRY_TO_SIGN.get (aRealCountry);
    return new CommonsLinkedHashSet <> (aSigns);
  }

  @NonNull
  @ReturnsMutableCopy
  public static ICommonsOrderedSet <String> getAllVehicleSigns (@Nullable final Locale aCountry)
  {
    return getAllVehicleSigns (aCountry == null ? null : aCountry.getCountry ());
  }

  @Nullable
  public static String getSingleVehicleSign (@Nullable final String sCountry)
  {
    final Locale aRealCountry = CountryCache.getInstance ().getCountry (sCountry);
    final ICommonsOrderedSet <String> aSigns = COUNTRY_TO_SIGN.get (aRealCountry);
    if (aSigns != null)
    {
      if (aSigns.size () == 1)
        return aSigns.getFirst ();
      throw new IllegalArgumentException ("Multiple vehicle signs are assigned to the country locale '" +
                                          sCountry +
                                          "': " +
                                          aSigns);
    }
    return null;
  }

  @Nullable
  public static String getSingleVehicleSign (@Nullable final Locale aCountry)
  {
    return getSingleVehicleSign (aCountry == null ? null : aCountry.getCountry ());
  }

  /**
   * @return The complete map from country locale to all vehicle signs. Never
   *         <code>null</code> nor empty.
   */
  @NonNull
  @ReturnsMutableCopy
  public static ICommonsMap <Locale, ICommonsOrderedSet <String>> getCountryToVehicleSignMap ()
  {
    return COUNTRY_TO_SIGN.getClone ();
  }

  @NonNull
  @ReturnsMutableCopy
  public static ICommonsOrderedSet <Locale> getAllCountriesFromVehicleSign (@Nullable final String sSign)
  {
    final ICommonsOrderedSet <Locale> aCountries = SIGN_TO_COUNTRY.get (sSign);
    return new CommonsLinkedHashSet <> (aCountries);
  }

  @Nullable
  public static Locale getSingleCountryFromVehicleSign (@Nullable final String sSign)
  {
    final ICommonsOrderedSet <Locale> aCountries = SIGN_TO_COUNTRY.get (sSign);
    if (aCountries != null)
    {
      if (aCountries.size () == 1)
        return aCountries.getFirst ();
      throw new IllegalArgumentException ("Multiple country locales are assigned to the vehicle sign '" +
                                          sSign +
                                          "': " +
                                          aCountries);
    }
    return null;
  }

  /**
   * @return A copy of the full map from vehicle sign to country locales. Never
   *         <code>null</code>.
   * @since 5.0.6
   */
  @NonNull
  @ReturnsMutableCopy
  public static ICommonsMap <String, ICommonsOrderedSet <Locale>> getVehicleSignToCountryMap ()
  {
    return SIGN_TO_COUNTRY.getClone ();
  }
}
