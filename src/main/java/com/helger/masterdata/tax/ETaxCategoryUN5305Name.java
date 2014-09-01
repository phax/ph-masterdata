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
  /** Code specifying that taxes are not applicable. */
  E ("Steuerausnahme", "Exempt from tax"),
  /**
   * Code specifying that the item is free export and taxes are not charged.
   */
  G ("Freier Export-Artikel, keine Steuerschuld", "Free export item, tax not charged"),
  /** Code specifying a higher rate of duty or tax or fee. */
  H ("Höherer Steuersatz", "Higher rate"),
  /**
   * Code specifying that taxes are not applicable to the services.
   */
  O ("Dienstleistung nicht steuerrelevant", "Services outside scope of tax"),
  /** Code specifying the standard rate. */
  S ("Standard Steuersatz", "Standard rate"),
  /** Code specifying that the goods are at a zero rate. */
  Z ("Steuersatz 0%", "Zero rated goods");

  private final ITextProvider m_aTP;

  private ETaxCategoryUN5305Name (@Nonnull final String sDE, @Nonnull final String sEN)
  {
    m_aTP = TextProvider.create_DE_EN (sDE, sEN);
  }

  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return DefaultTextResolver.getText (this, m_aTP, aContentLocale);
  }
}
