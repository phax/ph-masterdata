/**
 * Copyright (C) 2014-2020 Philip Helger (www.helger.com)
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
package com.helger.masterdata.email;

import java.util.Locale;

import javax.annotation.Nonnull;

import com.helger.commons.annotation.Translatable;
import com.helger.commons.text.IMultilingualText;
import com.helger.commons.text.display.IHasDisplayText;
import com.helger.commons.text.resolve.DefaultTextResolver;
import com.helger.commons.text.util.TextHelper;

@Translatable
public enum EEmailAddressText implements IHasDisplayText
{
  MSG_EMAIL ("E-Mail", "E-mail"),
  MSG_EMAIL_ADDRESS ("E-Mail-Adresse", "E-mail address"),
  MSG_EMAIL_PERSONAL ("E-Mail-Kontaktname", "E-mail contact name"),
  MSG_TYPE_PERSONAL ("Privat", "Personal"),
  MSG_TYPE_PERSONAL2 ("Privat (2)", "Personal (2)"),
  MSG_TYPE_OFFICE ("Büro", "Office"),
  MSG_TYPE_OFFICE2 ("Büro (2)", "Office (2)"),
  MSG_TYPE_OTHER ("Sonstige", "Other");

  private final IMultilingualText m_aTP;

  private EEmailAddressText (@Nonnull final String sDE, @Nonnull final String sEN)
  {
    m_aTP = TextHelper.create_DE_EN (sDE, sEN);
  }

  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return DefaultTextResolver.getTextStatic (this, m_aTP, aContentLocale);
  }
}
