/**
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.locale.LocaleCache;
import com.helger.commons.system.SystemHelper;

/**
 * Source: http://www.unece.org/trans/main/wp1/wp1fdoc/disting-signs-5-2001.pdf
 * 
 * @author Philip Helger
 */
public final class FuncTestVehicleSigns
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (FuncTestVehicleSigns.class);

  @Ignore
  @Test
  public void testFindVehicleCountries ()
  {
    if (false)
      for (final Locale aLocale : LocaleCache.getAllLocales ())
        if (aLocale.getDisplayCountry (Locale.UK).contains ("zi"))
          s_aLogger.info (aLocale + " -- " + aLocale.getDisplayCountry (Locale.UK));
    final Map <String, String> map = new LinkedHashMap <String, String> ();
    map.put ("Albania", "AL");
    if (false)
      map.put ("Alderney", "GBA");
    map.put ("Algeria", "DZ");
    map.put ("Andorra", "AND");
    map.put ("Argentina", "RA");
    map.put ("Australia", "AUS");
    map.put ("Austria", "A");
    map.put ("Bahamas", "BS");
    map.put ("Bahrain", "BRN");
    map.put ("Bangladesh", "BD");
    map.put ("Barbados", "BDS");
    map.put ("Belarus", "SU");
    map.put ("Belgium", "B");
    map.put ("Belize", "BH");
    map.put ("Benin", "DY");
    map.put ("Bosnia and Herzegovina", "BIH");
    map.put ("Botswana", "RB");
    map.put ("Brazil", "BR");
    map.put ("Brunei", "BRU");
    map.put ("Bulgaria", "BG");
    map.put ("Cambodia", "K");
    map.put ("Canada", "CDN");
    map.put ("Central African Republic", "RCA");
    map.put ("Chile", "RCH");
    map.put ("China", "RC");
    map.put ("Congo", "RCB");
    map.put ("Costa Rica", "CR");
    map.put ("Croatia", "HR");
    map.put ("Cuba", "CU");
    map.put ("Cyprus", "CY");
    map.put ("Czech Republic", "CZ");
    map.put ("Côte d'Ivoire", "CI");
    map.put ("The Democratic Republic Of Congo", "ZRE");
    map.put ("Denmark", "DK");
    map.put ("Dominican Republic", "DOM");
    map.put ("Ecuador", "EC");
    map.put ("Egypt", "ET");
    map.put ("Estonia", "EST");
    map.put ("Faroe Islands", "FO");
    map.put ("Fiji", "FJI");
    map.put ("Finland", "FIN");
    map.put ("France", "F");
    map.put ("Gambia", "WAG");
    map.put ("Georgia", "GE");
    map.put ("Germany", "D");
    map.put ("Ghana", "GH");
    map.put ("Gibraltar", "GBZ");
    map.put ("Greece", "GR");
    map.put ("Grenada", "WG");
    map.put ("Guatemala", "GCA");
    if (false)
      map.put ("Guernsey", "GBG");
    map.put ("Guyana", "GUY");
    map.put ("Haiti", "RH");
    map.put ("Vatican", "V");
    map.put ("Hungary", "H");
    map.put ("Iceland", "IS");
    map.put ("India", "IND");
    map.put ("Indonesia", "RI");
    map.put ("Iran", "IR");
    map.put ("Ireland", "IRL");
    if (false)
      map.put ("Isle of Man", "GBM");
    map.put ("Israel", "IL");
    map.put ("Italy", "I");
    map.put ("Jamaica", "JA");
    map.put ("Japan", "J");
    if (false)
      map.put ("Jersey", "GBJ");
    map.put ("Jordan", "HKJ");
    map.put ("Kazakhstan", "KZ");
    map.put ("Kenya", "EAK");
    map.put ("Kuwait", "KWT");
    map.put ("Kyrgyzstan", "KS");
    map.put ("Laos", "LAO");
    map.put ("Latvia", "LV");
    map.put ("Lebanon", "RL");
    map.put ("Lesotho", "LS");
    map.put ("Lithuania", "LT");
    map.put ("Luxembourg", "L");
    map.put ("Macedonia", "MK");
    map.put ("Madagascar", "RM");
    map.put ("Malawi", "MW");
    map.put ("Malaysia", "MAL");
    map.put ("Mali", "RMM");
    map.put ("Malta", "M");
    map.put ("Mauritius", "MS");
    map.put ("Mexico", "MEX");
    map.put ("Monaco", "MC");
    map.put ("Mongolia", "MGL");
    map.put ("Morocco", "MA");
    map.put ("Myanmar", "BUR");
    map.put ("Namibia", "NAM");
    map.put ("Netherlands Antilles", "NA");
    map.put ("Netherlands", "NL");
    map.put ("New Zealand", "NZ");
    map.put ("Nicaragua", "NIC");
    map.put ("Niger", "RN");
    map.put ("Nigeria", "WAN");
    map.put ("Norway", "N");
    map.put ("Pakistan", "PK");
    map.put ("Papua New Guinea", "PNG");
    map.put ("Paraguay", "PY");
    map.put ("Peru", "PE");
    map.put ("Philippines", "RP");
    map.put ("Poland", "PL");
    map.put ("Portugal", "P");
    if (false)
      map.put ("Republic of Korea", "ROK");
    else
    {
      map.put ("North Korea", "ROK");
      map.put ("South Korea", "ROK");
    }
    map.put ("Moldova", "MD");
    map.put ("Romania", "RO");
    map.put ("Russia", "RUS");
    map.put ("Rwanda", "RWA");
    map.put ("Samoa", "WS");
    map.put ("San Marino", "RSM");
    map.put ("Senegal", "SN");
    map.put ("Seychelles", "SY");
    map.put ("Sierra Leone", "WAL");
    map.put ("Singapore", "SGP");
    map.put ("Slovakia", "SK");
    map.put ("Slovenia", "SLO");
    map.put ("South Africa", "ZA");
    map.put ("Spain", "E");
    map.put ("Sri Lanka", "CL");
    map.put ("Saint Lucia", "WL");
    map.put ("Saint Vincent And The Grenadines", "WV");
    map.put ("Suriname", "SME");
    map.put ("Swaziland", "SD");
    map.put ("Sweden", "S");
    map.put ("Switzerland", "CH");
    map.put ("Syria", "SYR");
    map.put ("Tajikistan", "TJ");
    if (false)
      map.put ("Tanganyika", "EAT");
    map.put ("Thailand", "T");
    map.put ("Togo", "TG");
    map.put ("Trinidad and Tobago", "TT");
    map.put ("Tunisia", "TN");
    map.put ("Turkey", "TR");
    map.put ("Turkmenistan", "TM");
    map.put ("Uganda", "EAU");
    map.put ("Ukraine", "UA");
    map.put ("United Kingdom", "GB");
    map.put ("United States", "USA");
    map.put ("Uruguay", "ROU");
    map.put ("Uzbekistan", "UZ");
    map.put ("Venezuela", "YV");
    map.put ("Yemen", "AND");
    if (false)
      map.put ("Yugoslavia", "YU");
    map.put ("Zambia", "RNR");
    if (false)
      map.put ("Zanzibar", "EAZ");
    map.put ("Zimbabwe", "ZW");

    for (final String sCountry : map.keySet ())
    {
      Locale aFound = null;
      outer: for (final Locale aLocale : LocaleCache.getAllLocales ())
      {
        if (aLocale.getCountry ().length () == 0)
          continue;
        final Locale aSearchLocale = new Locale ("", aLocale.getCountry ());
        for (final Locale aDisplayLocale : new Locale [] { Locale.US,
                                                          Locale.UK,
                                                          aLocale,
                                                          SystemHelper.getSystemLocale () })
        {
          final String sLocaleCountry = aSearchLocale.getDisplayCountry (aDisplayLocale);
          if (sCountry.equals (sLocaleCountry))
          {
            aFound = aSearchLocale;
            break outer;
          }
        }
      }

      if (aFound == null)
        throw new IllegalStateException ("Failed to resolve country " + sCountry);
      if (false)
        s_aLogger.info ("map.put (LocaleCache.get (\"\",\"" +
                        aFound.getCountry () +
                        "\"), \"" +
                        map.get (sCountry) +
                        "\");");
    }
  }

  @Ignore
  @Test
  public void testGetAllCountries ()
  {
    final Set <String> aSet = new TreeSet <String> ();
    for (final Locale aLocale : LocaleCache.getAllLocales ())
      for (final Locale aLocale2 : LocaleCache.getAllLocales ())
        aSet.add (aLocale.getDisplayCountry (aLocale2));
    for (final String sUnique : aSet)
      s_aLogger.info (sUnique);
  }
}
