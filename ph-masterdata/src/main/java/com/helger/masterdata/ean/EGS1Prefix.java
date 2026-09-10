/*
 * Copyright (C) 2014-2026 Philip Helger (www.helger.com)
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
package com.helger.masterdata.ean;

import java.util.function.Consumer;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.Nonnegative;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.string.StringHelper;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.CommonsHashMap;
import com.helger.collection.commons.CommonsTreeSet;
import com.helger.collection.commons.ICommonsList;
import com.helger.collection.commons.ICommonsMap;
import com.helger.collection.commons.ICommonsSortedSet;

/**
 * GS1 company prefix, including the countries the respective prefix is assigned to.<br>
 * Source: https://www.gs1.org/standards/id-keys/company-prefix<br>
 * Source: https://en.wikipedia.org/wiki/List_of_GS1_country_codes<br>
 * Note: a GS1 prefix does not identify the country of origin of a product - it only identifies the
 * GS1 Member Organisation that issued the company prefix.
 *
 * @author Philip Helger
 */
public enum EGS1Prefix
{
  X0 ("0000000", null, "Used to issue Restricted Circulation Numbers within a company"),
  X1 ("0000001", "0000099", "Unused to avoid collision with GTIN-8"),
  US_1 ("00001", "00009", "GS1 US", "US"),
  US_2 ("0001", "0009", "GS1 US", "US"),
  US_3 ("001", "019", "GS1 US", "US"),
  X2 ("020", "029", "Used to issue Restricted Circulation Numbers within a geographic region (MO defined)"),
  US_4 ("030", "039", "GS1 US", "US"),
  X3 ("040", "049", "Used to issue GS1 Restricted Circulation Numbers within a company"),
  US_5 ("050", "059", "GS1 US reserved for future use", "US"),
  US_6 ("060", "139", "GS1 US", "US"),
  X4 ("200", "299", "Used to issue GS1 Restricted Circulation Numbers within a geographic region (MO defined)"),
  FR ("300", "379", "GS1 France", "FR", "MC"),
  BG ("380", null, "GS1 Bulgaria", "BG"),
  XK ("381", null, "GS1 Kosovo", "XK"),
  SI ("383", null, "GS1 Slovenija", "SI"),
  HR ("385", null, "GS1 Croatia", "HR"),
  BA ("387", null, "GS1 BIH (Bosnia-Herzegovina)", "BA"),
  ME ("389", null, "GS1 Montenegro", "ME"),
  DE ("400", "440", "GS1 Germany", "DE"),
  JP_1 ("450", "459", "GS1 Japan", "JP"),
  RU ("460", "469", "GS1 Russia", "RU"),
  KG ("470", null, "GS1 Kyrgyzstan", "KG"),
  TW ("471", null, "GS1 Chinese Taipei", "TW"),
  EE ("474", null, "GS1 Estonia", "EE"),
  LV ("475", null, "GS1 Latvia", "LV"),
  AZ ("476", null, "GS1 Azerbaijan", "AZ"),
  LT ("477", null, "GS1 Lithuania", "LT"),
  UZ ("478", null, "GS1 Uzbekistan", "UZ"),
  LK ("479", null, "GS1 Sri Lanka", "LK"),
  PH ("480", null, "GS1 Philippines", "PH"),
  BY ("481", null, "GS1 Belarus", "BY"),
  UA ("482", null, "GS1 Ukraine", "UA"),
  TM ("483", null, "GS1 Turkmenistan", "TM"),
  MD ("484", null, "GS1 Moldova", "MD"),
  AM ("485", null, "GS1 Armenia", "AM"),
  GE ("486", null, "GS1 Georgia", "GE"),
  KZ ("487", null, "GS1 Kazakhstan", "KZ"),
  TJ ("488", null, "GS1 Tajikistan", "TJ"),
  HK ("489", null, "GS1 Hong Kong, China", "HK"),
  JP_2 ("490", "499", "GS1 Japan", "JP"),
  GB ("500", "509", "GS1 UK", "GB"),
  GR ("520", "521", "GS1 Association Greece", "GR"),
  LB ("528", null, "GS1 Lebanon", "LB"),
  CY ("529", null, "GS1 Cyprus", "CY"),
  AL ("530", null, "GS1 Albania", "AL"),
  MK ("531", null, "GS1 Macedonia", "MK"),
  MT ("535", null, "GS1 Malta", "MT"),
  IE ("539", null, "GS1 Ireland", "IE"),
  BE ("540", "549", "GS1 Belgium & Luxembourg", "BE", "LU"),
  PT ("560", null, "GS1 Portugal", "PT"),
  IS ("569", null, "GS1 Iceland", "IS"),
  DK ("570", "579", "GS1 Denmark", "DK", "FO", "GL"),
  PL ("590", null, "GS1 Poland", "PL"),
  RO ("594", null, "GS1 Romania", "RO"),
  HU ("599", null, "GS1 Hungary", "HU"),
  ZA ("600", "601", "GS1 South Africa", "ZA"),
  GH ("603", null, "GS1 Ghana", "GH"),
  SN ("604", null, "GS1 Senegal", "SN"),
  UG ("605", null, "GS1 Uganda", "UG"),
  AO ("606", null, "GS1 Angola", "AO"),
  OM ("607", null, "GS1 Oman", "OM"),
  BH ("608", null, "GS1 Bahrain", "BH"),
  MU ("609", null, "GS1 Mauritius", "MU"),
  X17 ("610", null, "Managed by GS1 Global Office for future MO"),
  MA ("611", null, "GS1 Morocco", "MA"),
  DZ ("613", null, "GS1 Algeria", "DZ"),
  X18 ("614", null, "Managed by GS1 Global Office for future MO"),
  NG ("615", null, "GS1 Nigeria", "NG"),
  KE ("616", null, "GS1 Kenya", "KE"),
  CM ("617", null, "GS1 Cameroon", "CM"),
  CI ("618", null, "GS1 Côte d'Ivoire", "CI"),
  TN ("619", null, "GS1 Tunisia", "TN"),
  TZ ("620", null, "GS1 Tanzania", "TZ"),
  SY ("621", null, "GS1 Syria", "SY"),
  EG ("622", null, "GS1 Egypt", "EG"),
  X13 ("623", null, "Managed by GS1 Global Office for future MO (was GS1 Brunei until 2021-05)"),
  LY ("624", null, "GS1 Libya", "LY"),
  JO ("625", null, "GS1 Jordan", "JO"),
  IR ("626", null, "GS1 Iran", "IR"),
  KW ("627", null, "GS1 Kuwait", "KW"),
  SA ("628", null, "GS1 Saudi Arabia", "SA"),
  AE ("629", null, "GS1 Emirates", "AE"),
  QA ("630", null, "GS1 Qatar", "QA"),
  NA ("631", null, "GS1 Namibia", "NA"),
  RW ("632", null, "GS1 Rwanda", "RW"),
  FI ("640", "649", "GS1 Finland", "FI"),
  CN_1 ("680", "681", "GS1 China", "CN"),
  CN_2 ("690", "699", "GS1 China", "CN"),
  NO ("700", "709", "GS1 Norway", "NO"),
  IL ("729", null, "GS1 Israel", "IL"),
  SE ("730", "739", "GS1 Sweden", "SE"),
  GT ("740", null, "GS1 Guatemala", "GT"),
  SV ("741", null, "GS1 El Salvador", "SV"),
  HN ("742", null, "GS1 Honduras", "HN"),
  NI ("743", null, "GS1 Nicaragua", "NI"),
  CR ("744", null, "GS1 Costa Rica", "CR"),
  PA ("745", null, "GS1 Panama", "PA"),
  DO ("746", null, "GS1 Republica Dominicana", "DO"),
  MX ("750", null, "GS1 Mexico", "MX"),
  CA ("754", "755", "GS1 Canada", "CA"),
  X19 ("758", null, "Managed by GS1 Global Office for future MO"),
  VE ("759", null, "GS1 Venezuela", "VE"),
  CH ("760", "769", "GS1 Switzerland", "CH", "LI"),
  CO ("770", "771", "GS1 Colombia", "CO"),
  UY ("773", null, "GS1 Uruguay", "UY"),
  PE ("775", null, "GS1 Peru", "PE"),
  BO ("777", null, "GS1 Bolivia", "BO"),
  AR ("778", "779", "GS1 Argentina", "AR"),
  CL ("780", null, "GS1 Chile", "CL"),
  PY ("784", null, "GS1 Paraguay", "PY"),
  EC ("786", null, "GS1 Ecuador", "EC"),
  BR ("789", "790", "GS1 Brasil", "BR"),
  IT ("800", "839", "GS1 Italy", "IT", "SM", "VA"),
  ES ("840", "849", "GS1 Spain", "ES", "AD"),
  CU ("850", null, "GS1 Cuba", "CU"),
  SK ("858", null, "GS1 Slovakia", "SK"),
  CZ ("859", null, "GS1 Czech", "CZ"),
  RS ("860", null, "GS1 Serbia", "RS"),
  MN ("865", null, "GS1 Mongolia", "MN"),
  KP ("867", null, "GS1 North Korea", "KP"),
  TR ("868", "869", "GS1 Türkiye", "TR"),
  NL ("870", "879", "GS1 Netherlands", "NL"),
  KR ("880", "881", "GS1 South Korea", "KR"),
  MM ("883", null, "GS1 Myanmar", "MM"),
  KH ("884", null, "GS1 Cambodia", "KH"),
  TH ("885", null, "GS1 Thailand", "TH"),
  LA ("887", null, "GS1 Laos", "LA"),
  SG ("888", null, "GS1 Singapore", "SG"),
  IN ("890", null, "GS1 India", "IN"),
  VN ("893", null, "GS1 Vietnam", "VN"),
  X20 ("894", null, "Managed by GS1 Global Office for future MO"),
  PK ("896", null, "GS1 Pakistan", "PK"),
  ID ("899", null, "GS1 Indonesia", "ID"),
  AT ("900", "919", "GS1 Austria", "AT"),
  AU ("930", "939", "GS1 Australia", "AU"),
  NZ ("940", "949", "GS1 New Zealand", "NZ"),
  X5 ("950",
      null,
      "GS1 Global Office - used to support territories and countries where no GS1 Member Organisation operates"),
  X6 ("951", null, "GS1 Global Office - General Manager Number for the EPC General Identifier (GID) scheme"),
  X14 ("952", null, "Used for demonstrations and examples of the GS1 system"),
  MY ("955", null, "GS1 Malaysia", "MY"),
  MO ("958", null, "GS1 Macau, China", "MO"),
  X7 ("9600", "9624", "GS1 UK Office - GTIN-8 allocations"),
  X15 ("9625", "9626", "GS1 Poland Office - GTIN-8 allocations"),
  X16 ("9627", "9699", "GS1 Global Office - GTIN-8 allocations"),
  X8 ("977", null, "Serial publications (ISSN)"),
  X9 ("978", "979", "Bookland (ISBN)"),
  X10 ("980", null, "Refund receipts"),
  X11 ("981", "983", "GS1 coupon identification for common currency areas"),
  X12 ("990", "999", "GS1 coupon identification");

  private static final ICommonsMap <String, EGS1Prefix> PREFIX_MAP = _createPrefixMap ();
  private static final int [] PREFIX_LENGTHS = _createPrefixLengths ();

  private final String m_sFrom;
  private final String m_sTo;
  private final String m_sDescription;
  private final ICommonsList <String> m_aCountryCodes;

  EGS1Prefix (@NonNull @Nonempty final String sFrom,
              @Nullable final String sTo,
              @NonNull @Nonempty final String sDescription,
              @NonNull final String... aCountryCodes)
  {
    m_sFrom = sFrom;
    m_sTo = sTo;
    m_sDescription = sDescription;
    m_aCountryCodes = new CommonsArrayList <> (aCountryCodes);
  }

  @NonNull
  @Nonempty
  public String getFrom ()
  {
    return m_sFrom;
  }

  @Nonnegative
  public int getPrefixLength ()
  {
    return m_sFrom.length ();
  }

  @Nullable
  public String getTo ()
  {
    return m_sTo;
  }

  public boolean hasTo ()
  {
    return m_sTo != null;
  }

  @NonNull
  @Nonempty
  public String getDescription ()
  {
    return m_sDescription;
  }

  /**
   * Get the primary country code of this prefix. If a prefix is assigned to more than one country
   * (like <code>540-549</code> for Belgium and Luxembourg) the first one is returned.
   *
   * @return <code>null</code> if this prefix is not assigned to a country.
   * @see #getAllCountryCodes()
   */
  @Nullable
  public String getCountryCode ()
  {
    return m_aCountryCodes.getFirstOrNull ();
  }

  /**
   * Get all country codes of this prefix. The first entry is the primary country code.
   *
   * @return Never <code>null</code> but maybe empty.
   * @see #getCountryCode()
   */
  @NonNull
  @ReturnsMutableCopy
  public ICommonsList <String> getAllCountryCodes ()
  {
    return m_aCountryCodes.getClone ();
  }

  public boolean hasCountryCode ()
  {
    return m_aCountryCodes.isNotEmpty ();
  }

  /**
   * Iterate all valid prefixes for this prefix
   *
   * @param aConsumer
   *        The consumer to be invoked for all prefixes. May not be <code>null</code>.
   */
  public void iterateAllPrefixes (@NonNull final Consumer <String> aConsumer)
  {
    if (m_sTo == null)
      aConsumer.accept (m_sFrom);
    else
    {
      final int nCharCount = m_sFrom.length ();
      final int nStart = Integer.parseInt (m_sFrom);
      final int nEnd = Integer.parseInt (m_sTo);
      for (int i = nStart; i <= nEnd; ++i)
      {
        final String sValue = StringHelper.getLeadingZero (i, nCharCount);
        aConsumer.accept (sValue);
      }
    }
  }

  @NonNull
  @ReturnsMutableCopy
  private static ICommonsMap <String, EGS1Prefix> _createPrefixMap ()
  {
    final ICommonsMap <String, EGS1Prefix> ret = new CommonsHashMap <> ();
    for (final EGS1Prefix e : values ())
      e.iterateAllPrefixes (sPrefix -> ret.put (sPrefix, e));
    return ret;
  }

  @NonNull
  @ReturnsMutableCopy
  private static int [] _createPrefixLengths ()
  {
    // All distinct prefix lengths, longest first, so that the most specific prefix wins
    final ICommonsSortedSet <Integer> aLengths = new CommonsTreeSet <> ();
    for (final EGS1Prefix e : values ())
      aLengths.add (Integer.valueOf (e.getPrefixLength ()));

    final int [] ret = new int [aLengths.size ()];
    int nIndex = ret.length;
    for (final Integer aLength : aLengths)
      ret[--nIndex] = aLength.intValue ();
    return ret;
  }

  @Nullable
  public static EGS1Prefix getPrefixFromCode (@Nullable final String sCode)
  {
    // Input code length
    final int nCodeLen = StringHelper.getLength (sCode);
    if (nCodeLen > 0)
      for (final int nPrefixLen : PREFIX_LENGTHS)
        if (nCodeLen >= nPrefixLen)
        {
          final EGS1Prefix ret = PREFIX_MAP.get (sCode.substring (0, nPrefixLen));
          if (ret != null)
            return ret;
        }

    return null;
  }

  /**
   * Get the primary country code of the GS1 prefix contained in the provided code.
   *
   * @param sCode
   *        The GS1 code (like a GLN or a GTIN) to be evaluated. May be <code>null</code>.
   * @return <code>null</code> if no prefix was found, or if the found prefix is not assigned to a
   *         country.
   * @see #getPrefixFromCode(String)
   * @see #getCountryCode()
   */
  @Nullable
  public static String getCountryCodeFromCode (@Nullable final String sCode)
  {
    final EGS1Prefix ePrefix = getPrefixFromCode (sCode);
    return ePrefix == null ? null : ePrefix.getCountryCode ();
  }
}
