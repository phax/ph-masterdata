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
package com.helger.masterdata.vehiclesigns;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.collections.ContainerHelper;
import com.helger.commons.collections.multimap.IMultiMapSetBased;
import com.helger.commons.collections.multimap.MultiHashMapHashSetBased;
import com.helger.commons.exceptions.InitializationException;
import com.helger.commons.locale.country.CountryCache;

/**
 * Source: http://www.unece.org/trans/main/wp1/wp1fdoc/disting-signs-5-2001.pdf
 *
 * @author Philip Helger
 */
@Immutable
public final class VehicleSigns
{
  private static Map <Locale, String> s_aCountryToSign = new HashMap <Locale, String> ();
  private static IMultiMapSetBased <String, Locale> s_aSignToCountry = new MultiHashMapHashSetBased <String, Locale> ();

  static
  {
    _add ("AL", "AL");
    _add ("DZ", "DZ");
    _add ("AD", "AND");
    _add ("AR", "RA");
    _add ("AU", "AUS");
    _add ("AT", "A");
    _add ("BS", "BS");
    _add ("BH", "BRN");
    _add ("BD", "BD");
    _add ("BB", "BDS");
    _add ("BY", "SU");
    _add ("BE", "B");
    _add ("BZ", "BH");
    _add ("BJ", "DY");
    _add ("BA", "BIH");
    _add ("BW", "RB");
    _add ("BR", "BR");
    _add ("BN", "BRU");
    _add ("BG", "BG");
    _add ("KH", "K");
    _add ("CA", "CDN");
    _add ("CF", "RCA");
    _add ("CL", "RCH");
    _add ("CN", "RC");
    _add ("CG", "RCB");
    _add ("CR", "CR");
    _add ("HR", "HR");
    _add ("CU", "CU");
    _add ("CY", "CY");
    _add ("CZ", "CZ");
    _add ("CI", "CI");
    _add ("CD", "ZRE");
    _add ("DK", "DK");
    _add ("DO", "DOM");
    _add ("EC", "EC");
    _add ("EG", "ET");
    _add ("EE", "EST");
    _add ("FO", "FO");
    _add ("FJ", "FJI");
    _add ("FI", "FIN");
    _add ("FR", "F");
    _add ("GM", "WAG");
    _add ("GE", "GE");
    _add ("DE", "D");
    _add ("GH", "GH");
    _add ("GI", "GBZ");
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
    _add ("KW", "KWT");
    _add ("KG", "KS");
    _add ("LA", "LAO");
    _add ("LV", "LV");
    _add ("LB", "RL");
    _add ("LS", "LS");
    _add ("LT", "LT");
    _add ("LU", "L");
    _add ("MK", "MK");
    _add ("MG", "RM");
    _add ("MW", "MW");
    _add ("MY", "MAL");
    _add ("ML", "RMM");
    _add ("MT", "M");
    _add ("MU", "MS");
    _add ("MX", "MEX");
    _add ("MC", "MC");
    _add ("MN", "MGL");
    _add ("MA", "MA");
    _add ("MM", "BUR");
    _add ("NA", "NAM");
    _add ("AN", "NA");
    _add ("NL", "NL");
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
    _add ("MD", "MD");
    _add ("RO", "RO");
    _add ("RU", "RUS");
    _add ("RW", "RWA");
    _add ("WS", "WS");
    _add ("SM", "RSM");
    _add ("SN", "SN");
    _add ("SC", "SY");
    _add ("SL", "WAL");
    _add ("SG", "SGP");
    _add ("SK", "SK");
    _add ("SI", "SLO");
    _add ("ZA", "ZA");
    _add ("ES", "E");
    _add ("LK", "CL");
    _add ("LC", "WL");
    _add ("VC", "WV");
    _add ("SR", "SME");
    _add ("SZ", "SD");
    _add ("SE", "S");
    _add ("CH", "CH");
    _add ("SY", "SYR");
    _add ("TJ", "TJ");
    _add ("TH", "T");
    _add ("TG", "TG");
    _add ("TT", "TT");
    _add ("TN", "TN");
    _add ("TR", "TR");
    _add ("TM", "TM");
    _add ("UG", "EAU");
    _add ("UA", "UA");
    _add ("GB", "GB");
    _add ("US", "USA");
    _add ("UY", "ROU");
    _add ("UZ", "UZ");
    _add ("VE", "YV");
    _add ("YE", "AND");
    _add ("ZM", "RNR");
    _add ("ZW", "ZW");
  }

  private static void _add (@Nonnull final String sCountryCode, @Nonnull final String sSign)
  {
    final Locale aCountry = CountryCache.getInstance ().getCountry (sCountryCode);
    if (s_aCountryToSign.containsKey (aCountry))
      throw new InitializationException ("Locale " + aCountry + " is already contained!");
    s_aCountryToSign.put (aCountry, sSign);
    s_aSignToCountry.putSingle (sSign, aCountry);
  }

  private VehicleSigns ()
  {}

  @Nullable
  public static String getVehicleSign (@Nonnull final Locale aCountry)
  {
    ValueEnforcer.notNull (aCountry, "Country");
    return getVehicleSign (aCountry.getCountry ());
  }

  @Nullable
  public static String getVehicleSignOrNull (@Nullable final String sCountry)
  {
    return s_aCountryToSign.get (CountryCache.getInstance ().getCountry (sCountry));
  }

  @Nullable
  public static String getVehicleSign (@Nullable final String sCountry)
  {
    final String ret = getVehicleSignOrNull (sCountry);
    return ret == null ? sCountry : ret;
  }

  @Nullable
  public static Locale getCountryFromVehicleSign (@Nullable final String sSign)
  {
    final Set <Locale> aCountries = s_aSignToCountry.get (sSign);
    return ContainerHelper.isEmpty (aCountries) ? null : aCountries.iterator ().next ();
  }
}
