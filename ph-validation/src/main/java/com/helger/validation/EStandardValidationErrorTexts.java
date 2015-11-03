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
package com.helger.validation;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Translatable;
import com.helger.commons.text.IMultilingualText;
import com.helger.commons.text.display.IHasDisplayText;
import com.helger.commons.text.display.IHasDisplayTextWithArgs;
import com.helger.commons.text.resolve.DefaultTextResolver;
import com.helger.commons.text.util.TextHelper;

/**
 * Default error texts used in this component.
 *
 * @author Philip Helger
 */
@Translatable
public enum EStandardValidationErrorTexts implements IHasDisplayText, IHasDisplayTextWithArgs
{
  INVALID_EMPTY ("Dieses Feld darf nicht leer sein.", "This field must not be empty."),
  INVALID_LIST_EMPTY ("Mindestens ein Eintrag wird benötigt.", "At least one entry is required."),
  INVALID_FILE_EMPTY ("Es wurde keine Datei zum Hochladen ausgewählt.", "No file was selected for upload."),
  INVALID_COUNTRY_CODE ("Das Land ist ungültig.", "The country is invalid."),
  INVALID_DATE ("Das Datum ist ungültig. Geben Sie den Wert in der Form [{0}] an.", "The date is invalid. Specify the value in the form [{0}]."),
  INVALID_DATETIME ("Das Datum mit Uhrzeit ist ungültig. Geben Sie den Wert in der Form [{0}] an.", "The date and time is invalid. Specify the value in the form [{0}]."),
  INVALID_DOUBLE ("Die Gleitkommazahl ist ungültig.", "The floating point number is invalid."),
  INVALID_DOUBLE_RANGE ("Die Gleitkommazahl ist nicht im gültigen Bereich {0}-{1}.", "The floating point number is not in the valid range {0}-{1}."),
  INVALID_DOUBLE_UNSIGNED_EXCL0 ("Die Gleitkommazahl muss > 0 sein.", "The floating point number must be > 0."),
  INVALID_DOUBLE_UNSIGNED_INCL0 ("Die Gleitkommazahl muss >= 0 sein.", "The floating point number must be >= 0."),
  INVALID_EMAIL_ADDRESS ("Die E-Mail-Adresse ist ungültig.", "The e-mail address is invalid."),
  INVALID_INT ("Die Ganzzahl ist ungültig.", "The integer number is invalid."),
  INVALID_INT_RANGE ("Die Ganzzahl ist nicht im gültigen Bereich {0}-{1}.", "The integer number is not in the valid range {0}-{1}."),
  INVALID_INT_UNSIGNED_EXCL0 ("Die Ganzzahl muss > 0 sein.", "The integer number must be > 0."),
  INVALID_INT_UNSIGNED_INCL0 ("Die Ganzzahl muss >= 0 sein.", "The integer number must be >= 0."),
  INVALID_MINLENGTH ("Der Wert ist zu kurz. Er muss mindestens {0} Zeichen enthalten.", "The value is too short. The minimum required length is {0}."),
  INVALID_MAXLENGTH ("Der Wert ist zu lang. Er darf maximal {0} Zeichen enthalten.", "The value is too long. The maximum allowed length is {0}."),
  INVALID_REGEX ("Der Wert entspricht nicht dem regulären Ausdruck: {0}", "The value does not match the regular expression: {0}"),
  INVALID_TIME ("Die Uhrzeit ist ungültig. Geben Sie den Wert in der Form [{0}] an.", "The time is invalid. Specify the value in the form [{0}]."),
  INVALID_MIN_LENGTH ("Es müssen mindestens {0} Einträge vorhanden sein.", "At least {0} entries must be present."),
  INVALID_MAX_LENGTH ("Es dürfen maximal {0} Einträge vorhanden sein.", "At last {0} entries must be present."),
  INVALID_URL ("Die URL ist ungültig.", "The URL is invalid.");

  private final IMultilingualText m_aTP;

  private EStandardValidationErrorTexts (@Nonnull final String sDE, @Nonnull final String sEN)
  {
    m_aTP = TextHelper.create_DE_EN (sDE, sEN);
  }

  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return DefaultTextResolver.getTextStatic (this, m_aTP, aContentLocale);
  }

  @Nullable
  public String getDisplayTextWithArgs (@Nonnull final Locale aContentLocale, @Nullable final Object... aArgs)
  {
    return DefaultTextResolver.getTextWithArgsStatic (this, m_aTP, aContentLocale, aArgs);
  }

  @Nonnull
  public IMultilingualText getAsMLT ()
  {
    return m_aTP;
  }
}
