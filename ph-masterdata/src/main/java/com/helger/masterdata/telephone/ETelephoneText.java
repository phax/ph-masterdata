/**
 * Copyright (C) 2014-2021 Philip Helger (www.helger.com)
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
package com.helger.masterdata.telephone;

import java.util.Locale;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Translatable;
import com.helger.commons.text.IMultilingualText;
import com.helger.commons.text.display.IHasDisplayText;
import com.helger.commons.text.resolve.DefaultTextResolver;
import com.helger.commons.text.util.TextHelper;

@Translatable
public enum ETelephoneText implements IHasDisplayText
{
  MSG_TELEPHONE ("Telefon", "Telephone"),
  MSG_TELEPHONE_COUNTRYCODE ("Telefon Ländervorwahl", "Telephone country code"),
  MSG_TELEPHONE_AREACODE ("Telefon Vorwahl", "Telephone area code"),
  MSG_TELEPHONE_LINE ("Telefonnummer", "Telephone line"),
  MSG_TELEPHONE_DIRECTDIAL ("Telefondurchwahl", "Telephone direct dial"),
  MSG_FAX ("Fax", "Telefax"),
  MSG_FAX_COUNTRYCODE ("Fax Ländervorwahl", "Telefax country code"),
  MSG_FAX_AREACODE ("Fax Vorwahl", "Telefax area code"),
  MSG_FAX_LINE ("Faxnummer", "Telefax line"),
  MSG_FAX_DIRECTDIAL ("Faxdurchwahl", "Telefax direct dial"),
  MSG_TYPE_ASSISTANT ("Assistent", "Assistant"),
  MSG_TYPE_BUSINESS ("Geschäftlich", "Business"),
  MSG_TYPE_BUSINESS2 ("Geschäftlich (2)", "Business (2)"),
  MSG_TYPE_BUSINESS_FAX ("Fax geschäftlich", "Business fax"),
  MSG_TYPE_OFFICE ("Büro", "Office"),
  MSG_TYPE_OFFICE_FAX ("Fax Büro", "Office fax"),
  MSG_TYPE_PERSONAL ("Persönlich", "Personal"),
  MSG_TYPE_PERSONAL2 ("Persönlich (2)", "Personal (2)"),
  MSG_TYPE_PERSONAL_FAX ("Fax persönlich", "Personal fax"),
  MSG_TYPE_ISDN ("ISDN", "ISDN"),
  MSG_TYPE_MOBILE ("Mobil", "Mobile"),
  MSG_TYPE_OTHER ("Sonstige", "Other"),
  MSG_TYPE_OTHER_FAX ("Fax sonstige", "Other fax");

  private final IMultilingualText m_aTP;

  private ETelephoneText (@Nonnull final String sDE, @Nonnull final String sEN)
  {
    m_aTP = TextHelper.create_DE_EN (sDE, sEN);
  }

  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return DefaultTextResolver.getTextStatic (this, m_aTP, aContentLocale);
  }
}
