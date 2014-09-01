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
package com.helger.masterdata.vat.validation;

import java.util.Locale;

import javax.annotation.Nonnull;

import com.helger.commons.annotations.Translatable;
import com.helger.commons.name.IHasDisplayText;
import com.helger.commons.text.ITextProvider;
import com.helger.commons.text.impl.TextProvider;
import com.helger.commons.text.resolve.DefaultTextResolver;

@Translatable
public enum EVATErrorTexts implements IHasDisplayText
{
  INVALID_VATIN ("Die UID ist ungültig.", "The VATIN is invalid."),
  INVALID_VATITEM ("Der angegebene Steuersatz ist ungültig.", "The specified tax rate is invalid."),
  INVALID_VATIN_WITH_EXAMPLES ("Die UID ist ungültig. Eine UID für {0} kann z.B. so aussehen: {1}", "The VATIN is invalid. A valid UID for {0} can look like this: {1}");

  private final ITextProvider m_aTP;

  private EVATErrorTexts (@Nonnull final String sDE, @Nonnull final String sEN)
  {
    m_aTP = TextProvider.create_DE_EN (sDE, sEN);
  }

  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return DefaultTextResolver.getText (this, m_aTP, aContentLocale);
  }
}
