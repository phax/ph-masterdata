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
package com.helger.masterdata.tax;

import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.misc.Translatable;
import com.helger.text.IMultilingualText;
import com.helger.text.display.IHasDisplayText;
import com.helger.text.resolve.DefaultTextResolver;
import com.helger.text.util.TextHelper;

/**
 * Texts for {@link ETaxCategoryUN5305}.
 *
 * @author Philip Helger
 */
@Translatable
public enum ETaxCategoryUN5305Name implements IHasDisplayText
{
  /** Code specifying that the rate is based on mixed tax. */
  A ("Gemischter Steuersatz", "Mixed tax rate"),
  /** Tax rate is lower than standard rate. */
  AA ("Niedrigerer Steuersatz", "Lower rate"),
  /**
   * A tax category code indicating the item is tax exempt when the item is
   * bought for future resale.
   */
  AB ("Ausnahme für Wiederverkauf", "Exempt for resale"),
  /**
   * A code to indicate that the Value Added Tax (VAT) amount which is due on
   * the current invoice is to be paid on receipt of a separate VAT payment
   * request.
   */
  AC ("MwSt. ist noch nicht zu zahlen", "Value Added Tax (VAT) not now due for payment"),
  /**
   * A code to indicate that the Value Added Tax (VAT) amount of a previous
   * invoice is to be paid.
   */
  AD ("MwSt. von einer vorherigen Rechnung offen", "Value Added Tax (VAT) due from a previous invoice"),
  /**
   * Code specifying that the standard VAT rate is levied from the invoicee.
   */
  AE ("Umkehr der Steuerschuld", "VAT Reverse Charge"),
  /**
   * VAT not to be paid to the issuer of the invoice but directly to relevant
   * tax authority.
   */
  B ("Steuer direkt ans Finanzamt abzuführen", "Transferred (VAT)"),
  /**
   * Duty associated with shipment of goods is paid by the supplier; customer
   * receives goods with duty paid.
   */
  C ("Lieferant zahlt Schuld", "Duty paid by supplier"),
  /**
   * Indication that the VAT margin scheme for travel agents is applied.
   */
  D ("Mehrwertsteuer-Margenregelung - Reisebüros", "Value Added Tax (VAT) margin scheme - travel agents"),
  /** Code specifying that taxes are not applicable. */
  E ("Steuerausnahme", "Exempt from tax"),
  /**
   * Code specifying that the item is free export and taxes are not charged.
   */
  G ("Freier Export-Artikel, keine Steuerschuld", "Free export item, tax not charged"),
  /** Code specifying a higher rate of duty or tax or fee. */
  H ("Höherer Steuersatz", "Higher rate"),
  /**
   * Indication that the VAT margin scheme for works of art is applied.
   */
  I ("Mehrwertsteuer-Margenregelung - Kunstwerke Margenregelung - Kunstwerke",
     "Value Added Tax (VAT) margin scheme - works of art Margin scheme - Works of art"),
  /**
   * Indication that the VAT margin scheme for collector’s items and antiques is
   * applied.
   */
  J ("Mehrwertsteuer-Margenregelung - Sammlerstücke und Antiquitäten",
     "Value Added Tax (VAT) margin scheme - collector’s items and antiques"),
  /**
   * A tax category code indicating the item is VAT exempt due to an
   * intra-community supply in the European Economic Area.
   */
  K ("Mehrwertsteuerbefreiung für innergemeinschaftliche Lieferungen von Gegenständen und Dienstleistungen im EWR",
     "VAT exempt for EEA intra-community supply of goods and services"),
  /**
   * Impuesto General Indirecto Canario (IGIC) is an indirect tax levied on
   * goods and services supplied in the Canary Islands (Spain) by traders and
   * professionals, as well as on import of goods.
   */
  L ("Allgemeine indirekte Steuern der Kanarischen Inseln", "Canary Islands general indirect tax"),
  /**
   * Impuesto sobre la Producción, los Servicios y la Importación (IPSI) is an
   * indirect municipal tax, levied on the production, processing and import of
   * all kinds of movable tangible property, the supply of services and the
   * transfer of immovable property located in the cities of Ceuta and Melilla.
   */
  M ("Steuer für Produktion, Dienstleistungen und Import in Ceuta und Melilla",
     "Tax for production, services and importation in Ceuta and Melilla"),
  /**
   * Code specifying that taxes are not applicable to the services.
   */
  O ("Dienstleistung nicht steuerrelevant", "Services outside scope of tax"),
  /** Code specifying the standard rate. */
  S ("Standard Steuersatz", "Standard rate"),
  /** Code specifying that the goods are at a zero rate. */
  Z ("Steuersatz 0%", "Zero rated goods");

  private final IMultilingualText m_aTP;

  ETaxCategoryUN5305Name (@NonNull final String sDE, @NonNull final String sEN)
  {
    m_aTP = TextHelper.create_DE_EN (sDE, sEN);
  }

  @Nullable
  public String getDisplayText (@NonNull final Locale aContentLocale)
  {
    return DefaultTextResolver.getTextStatic (this, m_aTP, aContentLocale);
  }
}
