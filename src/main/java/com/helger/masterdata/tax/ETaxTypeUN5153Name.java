/**
 * Copyright (C) 2006-2014 phloc systems
 * http://www.phloc.com
 * office[at]phloc[dot]com
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
package com.helger.masterdata.tax;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotations.Translatable;
import com.helger.commons.name.IHasDisplayText;
import com.helger.commons.text.ITextProvider;
import com.helger.commons.text.impl.TextProvider;
import com.helger.commons.text.resolve.DefaultTextResolver;

/**
 * Texts for {@link ETaxTypeUN5153}.
 * 
 * @author Philip Helger
 */
@Translatable
public enum ETaxTypeUN5153Name implements IHasDisplayText
{
  /**
   * A tax levied on the volume of petroleum being transacted.
   */
  AAA ("Erdölsteuer", "Petroleum tax"),
  /**
   * Countervailing duty paid in cash prior to a formal finding of subsidization
   * by Customs.
   */
  AAB ("Provisorische Ausgleichsabgabe", "Provisional countervailing duty cash"),
  /**
   * Countervailing duty paid by posting a bond during an investigation period
   * prior to a formal decision on subsidization by Customs.
   */
  AAC ("Provisorische Ausgleichsabgabe Schuld", "Provisional countervailing duty bond"),
  /** A tax levied on tobacco products. */
  AAD ("Tabaksteuer", "Tobacco tax"),
  /** General fee or tax for the use of energy. */
  AAE ("Energieabgabe", "Energy fee"),
  /** A tax levied specifically on coffee products. */
  AAF ("Kaffeesteuer", "Coffee tax"),
  /**
   * A harmonized sales tax consisting of a goods and service tax, a Canadian
   * provincial sales tax and, as applicable, a Quebec sales tax which is
   * recoverable.
   */
  AAG ("Harmonisierte Umsatzsteuer, Kanada", "Harmonised sales tax, Canadian"),
  /**
   * A sales tax charged within the Canadian province of Quebec which is
   * recoverable.
   */
  AAH ("Quebecs' Umsatzsteuer", "Quebec sales tax"),
  /**
   * A sales tax charged within Canadian provinces which is non-recoverable.
   */
  AAI ("Kanadische Regionale Umsatzsteuer", "Canadian provincial sales tax"),
  /**
   * A tax levied on a replacement part, where the original part is returned.
   */
  AAJ ("Tax on replacement part", "Tax on replacement part"),
  /**
   * Tax that is levied specifically on products containing mineral oil.
   */
  AAK ("Mineralölsteuer", "Mineral oil tax"),
  /** To indicate a special type of tax. */
  AAL ("Spezialsteuer", "Special tax"),
  /**
   * Duty applied to goods ruled to have been dumped in an import market at a
   * price lower than that in the exporter's domestic market.
   */
  ADD ("Anti-dumping Abgabe", "Anti-dumping duty"),
  /**
   * Tax required in Italy, which may be fixed or graduated in various
   * circumstances (e.g. VAT exempt documents or bank receipts).
   */
  BOL ("Stempelgebühr (Italien)", "Stamp duty (Imposta di Bollo)"),
  /**
   * Levy imposed on agricultural products where there is a difference between
   * the selling price between trading countries.
   */
  CAP ("Agrarabgabe", "Agricultural levy"),
  /** A tax that is levied on the value of the automobile. */
  CAR ("Kraftfahrzeugssteuer", "Car tax"),
  /** Italian Paper consortium tax. */
  COC ("Paper consortium tax (Italien)", "Paper consortium tax (Italy)"),
  /**
   * Tax related to a specified commodity, e.g. illuminants, salts.
   */
  CST ("Spezielle Zollabgaben", "Commodity specific tax"),
  /**
   * Duties laid down in the Customs tariff, to which goods are liable on
   * entering or leaving the Customs territory (CCC).
   */
  CUD ("Zollabgaben", "Customs duty"),
  /**
   * A duty on imported goods applied for compensate for subsidies granted to
   * those goods in the exporting country.
   */
  CVD ("Ausgleichsabgabe", "Countervailing duty"),
  /**
   * Tax assessed for funding or assuring environmental protection or clean-up.
   */
  ENV ("Umweltsteuer", "Environmental tax"),
  /**
   * Customs or fiscal authorities code to identify a specific or ad valorem
   * levy on a specific commodity, applied either domestically or at time of
   * importation.
   */
  EXC ("Verbrauchssteuer", "Excise duty"),
  /**
   * Monetary rebate given to the seller in certain circumstances when
   * agricultural products are exported.
   */
  EXP ("Agrar-Ausfuhrvergütung", "Agricultural export rebate"),
  /**
   * Tax levied by the federal government on the manufacture of specific items.
   */
  FET ("Bundesverbrauchssteuer", "Federal excise tax"),
  /** No tax levied. */
  FRE ("Frei", "Free"),
  /** General tax for construction. */
  GCN ("Allgemeine Bausteuer", "General construction tax"),
  /**
   * Tax levied on the final consumption of goods and services throughout the
   * production and distribution chain.
   */
  GST ("Waren- und Dienstleistungssteuer", "Goods and services tax"),
  /** Tax of illuminants. */
  ILL ("Leuchtmittelsteuer", "Illuminants tax"),
  /** Tax assessed on imports. */
  IMP ("Importsteuer", "Import tax"),
  /** A tax levied based on an individual's ability to pay. */
  IND ("Individual-Steuer", "Individual tax"),
  /** Government assessed charge for permit to do business. */
  LAC ("Gewerbesteuer", "Business license fee"),
  /** Local tax for construction. */
  LCN ("Regionale Bausteuer", "Local construction tax"),
  /**
   * Fee levied on a vessel to pay for port navigation lights.
   */
  LDP ("Leuchtturmabgaben", "Light dues payable"),
  /**
   * Assessment charges on sale of goods or services by city, borough country or
   * other taxing authorities below state or provincial level.
   */
  LOC ("Lokale Umsatzsteuer", "Local sales tax"),
  /**
   * Tax imposed for clean-up of leaky underground storage tanks.
   */
  LST ("LUST Steuer", "Lust tax"),
  /**
   * Levy on Common Agricultural Policy (European Union) goods used to
   * compensate for fluctuating currencies between member states.
   */
  MCA ("Monetäre Ausgleichssumme", "Monetary compensatory amount"),
  /**
   * Duty paid and held on deposit, by Customs, during an investigation period
   * prior to a final decision being made on any aspect related to imported
   * goods (except valuation) by Customs.
   */
  MCD ("Diverse Bareinlage", "Miscellaneous cash deposit"),
  /** Unspecified, miscellaneous tax charges. */
  OTH ("Sonstige Steuern", "Other taxes"),
  /**
   * Anti-dumping duty paid by posting a bond during an investigation period
   * prior to a formal decision on dumping by Customs.
   */
  PDB ("Provisorische Anleihenschuld", "Provisional duty bond"),
  /**
   * Anti-dumping duty paid in cash prior to a formal finding of dumping by
   * Customs.
   */
  PDC ("Provisorische Abgabenschuld", "Provisional duty cash"),
  /**
   * Duties laid down in the Customs tariff, to which goods are liable on
   * entering or leaving the Customs territory falling under a preferential
   * regime such as Generalised System of Preferences (GSP).
   */
  PRF ("Vorzugsabgabe", "Preference duty"),
  /** Special tax for construction. */
  SCN ("Spezialbausteuer", "Special construction tax"),
  /**
   * Social securities share of the invoice amount to be paid directly to the
   * social securities collector.
   */
  SSS ("Verschiebung der Sozialen Sicherheit", "Shifted social securities"),
  /**
   * All applicable sale taxes by authorities at the state or provincial level,
   * below national level.
   */
  STT ("Regionale Umsatzsteuer", "State/provincial sales tax"),
  /** Duty suspended or deferred from payment. */
  SUP ("Außer Kraft gesetzte Steuer", "Suspended duty"),
  /**
   * A tax or duty applied on and in addition to existing duties and taxes.
   */
  SUR ("Zusatzsteuer/Zuschlag/Ergänzungsabgabe", "Surtax"),
  /**
   * Wage tax share of the invoice amount to be paid directly to the tax
   * collector(s office).
   */
  SWT ("Verschobene Lohnsteuer", "Shifted wage tax"),
  /**
   * A tax levied based on the type of alcohol being obtained.
   */
  TAC ("Alkoholsteuer", "Alcohol mark tax"),
  /** The summary amount of all taxes. */
  TOT ("Summe/Gesamt", "Total"),
  /** Tax levied on the total sales/turnover of a corporation. */
  TOX ("Umsatzsteuer", "Turnover tax"),
  /** Tax levied based on the vessel's net tonnage. */
  TTA ("Tonnagensteuer", "Tonnage taxes"),
  /**
   * Duty paid and held on deposit, by Customs, during an investigation period
   * prior to a formal decision on valuation of the goods being made.
   */
  VAD ("Wertansatz Abgabe", "Valuation deposit"),
  /**
   * A tax on domestic or imported goods applied to the value added at each
   * stage in the production/distribution cycle.
   */
  VAT ("Mehrwertsteuer", "Value added tax");

  private final ITextProvider m_aTP;

  private ETaxTypeUN5153Name (@Nonnull final String sDE, @Nonnull final String sEN)
  {
    m_aTP = TextProvider.create_DE_EN (sDE, sEN);
  }

  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return DefaultTextResolver.getText (this, m_aTP, aContentLocale);
  }
}
