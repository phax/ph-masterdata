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
package com.helger.masterdata.ean;

import java.util.function.Consumer;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.impl.CommonsHashSet;
import com.helger.commons.collection.impl.ICommonsSet;
import com.helger.commons.string.StringHelper;

/**
 * GS1 company prefix<br>
 * Source: https://www.gs1.org/standards/id-keys/company-prefix
 *
 * @author Philip Helger
 */
public enum EGS1Prefix
{
  X0 ("0000000", null, "Used to issue Restricted Ciruculation Numbers within a company", null),
  X1 ("0000001", "0000099", "Unused to avoid collision with GTIN-8", null),
  US_1 ("00001", "00009", "GS1 US", "US"),
  US_2 ("0001", "0009", "GS1 US", "US"),
  US_3 ("001", "019", "GS1 US", "US"),
  X2 ("020", "029", "Used to issue Restricted Circulation Numbers within a geographic region (MO defined)", null),
  US_4 ("030", "039", "GS1 US", "US"),
  X3 ("040", "049", "Used to issue GS1 Restricted Circulation Numbers within a company", null),
  US_5 ("050", "059", "GS1 US reserved for future use", "US"),
  US_6 ("060", "139", "GS1 US", "US"),
  X4 ("200", "299", "Used to issue GS1 Restricted Circulation Numbers within a geographic region (MO defined)", null),
  FR ("300", "379", "GS1 France", "FR"),
  BG ("380", null, "GS1 Bulgaria", "BG"),
  SI ("383", null, "GS1 Slovenija", "SI"),
  HR ("385", null, "GS1 Croatia", "HR"),
  BA ("387", null, "GS1 BIH (Bosnia-Herzegovina)", "BA"),
  ME ("389", null, "GS1 Montenegro", "ME"),
  DE ("400", "440", "GS1 Germany", "DE"),
  JP_1 ("450", "459", "GS1 Japan", "JP"),
  JP_2 ("490", "499", "GS1 Japan", "JP"),
  RU ("460", "469", "GS1 Russia", "RU"),
  KG ("470", null, "GS1 Kyrgyzstan", "KG"),
  CN_TP ("471", null, "GS1 Chinese Taipei", "CN_TP"),
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
  KZ ("487", null, "GS1 Kazakstan", "KZ"),
  TJ ("488", null, "GS1 Tajikistan", "TJ"),
  CN_HK ("489", null, "GS1 Hong Kong, China", "CN_HK"),
  GB ("500", "509", "GS1 UK", "GB"),
  GR ("520", "521", "GS1 Association Greece", "GR"),
  LB ("528", null, "GS1 Lebanon", "LB"),
  CY ("529", null, "GS1 Cyprus", "CY"),
  AL ("530", null, "GS1 Albania", "AL"),
  MK ("531", null, "GS1 Macedonia", "MK"),
  MT ("535", null, "GS1 Malta", "MT"),
  IE ("539", null, "GS1 Ireland", "IE"),
  BE ("540", "549", "GS1 Belgium & Luxembourg", "BE"),
  PT ("560", null, "GS1 Portugal", "PT"),
  IS ("569", null, "GS1 Iceland", "IS"),
  DK ("570", "579", "GS1 Denmark", "DK"),
  PL ("590", null, "GS1 Poland", "PL"),
  RO ("594", null, "GS1 Romania", "RO"),
  HU ("599", null, "GS1 Hungary", "HU"),
  ZA ("600", "601", "GS1 South Africa", "ZA"),
  GH ("603", null, "GS1 Ghana", "GH"),
  SN ("604", null, "GS1 Senegal", "SN"),
  BH ("608", null, "GS1 Bahrain", "BH"),
  MU ("609", null, "GS1 Mauritius", "MU"),
  MA ("611", null, "GS1 Morocco", "MA"),
  DZ ("613", null, "GS1 Algeria", "DZ"),
  NG ("615", null, "GS1 Nigeria", "NG"),
  KE ("616", null, "GS1 Kenya", "KE"),
  CI ("618", null, "GS1 Côte d'Ivoire", "CI"),
  TN ("619", null, "GS1 Tunisia", "TN"),
  TZ ("620", null, "GS1 Tanzania", "TZ"),
  SY ("621", null, "GS1 Syria", "SY"),
  EG ("622", null, "GS1 Egypt", "EG"),
  BN ("623", null, "GS1 Brunei", "BN"),
  LY ("624", null, "GS1 Libya", "LY"),
  JO ("625", null, "GS1 Jordan", "JO"),
  IR ("626", null, "GS1 Iran", "IR"),
  KW ("627", null, "GS1 Kuwait", "KW"),
  SA ("628", null, "GS1 Saudi Arabia", "SA"),
  AE ("629", null, "GS1 Emirates", "AE"),
  FI ("640", "649", "GS1 Finland", "FI"),
  CN ("690", "699", "GS1 China", "CN"),
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
  VE ("759", null, "GS1 Venezuela", "VE"),
  CH ("760", "769", "GS1 Schweiz, Suisse, Svizzera", "CH"),
  CO ("770", "771", "GS1 Colombia", "CO"),
  UY ("773", null, "GS1 Uruguay", "UY"),
  PE ("775", null, "GS1 Peru", "PE"),
  BO ("777", null, "GS1 Bolivia", "BO"),
  AR ("778", "779", "GS1 Argentina", "AR"),
  CL ("780", null, "GS1 Chile", "CL"),
  PY ("784", null, "GS1 Paraguay", "PY"),
  EC ("786", null, "GS1 Ecuador", "EC"),
  BR ("789", "790", "GS1 Brasil", "BR"),
  IT ("800", "839", "GS1 Italy", "IT"),
  ES ("840", "849", "GS1 Spain", "ES"),
  CU ("850", null, "GS1 Cuba", "CU"),
  SK ("858", null, "GS1 Slovakia", "SK"),
  CZ ("859", null, "GS1 Czech", "CZ"),
  RS ("860", null, " GS1 Serbia", "RS"),
  MN ("865", null, "GS1 Mongolia", "MN"),
  KP ("867", null, "GS1 North Korea", "KP"),
  TK ("868", "869", "GS1 Turkey", "TK"),
  NL ("870", "879", "GS1 Netherlands", "NL"),
  KR ("880", null, "GS1 South Korea", "KR"),
  KH ("884", null, "GS1 Cambodia", "KH"),
  TH ("885", null, "GS1 Thailand", "TH"),
  SG ("888", null, "GS1 Singapore", "SG"),
  IN ("890", null, "GS1 India", "IN"),
  VN ("893", null, "GS1 Vietnam", "VN"),
  PK ("896", null, "GS1 Pakistan", "PK"),
  ID ("899", null, "GS1 Indonesia", "ID"),
  AT ("900", "919", "GS1 Austria", "AT"),
  AU ("930", "939", "GS1 Australia", "AU"),
  NZ ("940", "949", "GS1 New Zealand", "NZ"),
  X5 ("950", null, "GS1 Global Office", null),
  X6 ("951", null, "Global Office - General Manager Number, see Note 2", null),
  MY ("955", null, "GS1 Malaysia", "MY"),
  CN_MO ("958", null, "GS1 Macau, China", "CN_MO"),
  X7 ("960", "969", "Global Office - GTIN-8, see note 3", null),
  X8 ("977", null, "Serial publications (ISSN)", null),
  X9 ("978", "979", "Bookland (ISBN)", null),
  X10 ("980", null, "Refund receipts", null),
  X11 ("981", "984", "GS1 coupon identification for common currency areas", null),
  X12 ("99", null, "GS1 coupon identification", null);

  private final String m_sFrom;
  private final String m_sTo;
  private final String m_sDescription;
  private final String m_sCountryCode;

  EGS1Prefix (@Nonnull @Nonempty final String sFrom,
              @Nullable final String sTo,
              @Nonnull @Nonempty final String sDescription,
              @Nullable final String sCountryCode)
  {
    m_sFrom = sFrom;
    m_sTo = sTo;
    m_sDescription = sDescription;
    m_sCountryCode = sCountryCode;
  }

  @Nonnull
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

  @Nonnull
  @Nonempty
  public String getDescription ()
  {
    return m_sDescription;
  }

  @Nullable
  public String getCountryCode ()
  {
    return m_sCountryCode;
  }

  public boolean hasCountryCode ()
  {
    return m_sCountryCode != null;
  }

  /**
   * Iterate all valid prefixes for this prefix
   *
   * @param aConsumer
   *        The consumer to be invoked for all prefixes. May not be
   *        <code>null</code>.
   */
  public void iterateAllPrefixes (@Nonnull final Consumer <String> aConsumer)
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

  @Nullable
  public static EGS1Prefix getPrefixFromCode (@Nullable final String sCode)
  {
    // Input code length
    final int nCodeLen = StringHelper.getLength (sCode);
    if (nCodeLen > 0)
    {
      // Outside of the loop for performance
      final ICommonsSet <String> aPrefixes = new CommonsHashSet <> (100);
      for (final EGS1Prefix e : values ())
        if (nCodeLen >= e.getPrefixLength ())
        {
          final String sUsablePrefix = sCode.substring (0, e.getPrefixLength ());

          aPrefixes.clear ();
          e.iterateAllPrefixes (aPrefixes::add);
          if (aPrefixes.contains (sUsablePrefix))
            return e;
        }
    }

    return null;
  }
}
