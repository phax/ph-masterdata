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
package com.helger.masterdata.unit;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotations.Translatable;
import com.helger.commons.name.IHasDisplayText;
import com.helger.commons.text.ISimpleMultiLingualText;
import com.helger.commons.text.impl.TextProvider;
import com.helger.commons.text.resolve.DefaultTextResolver;

@Translatable
public enum EISO31Name implements IHasDisplayText
{
  ISO31_0 ("Grundprinzipien", "General principles"),
  ISO31_1 ("Raum und Zeit", "Space and time"),
  ISO31_2 ("Periodizität und verwandte Phänomene", "Periodic and related phenomena"),
  ISO31_3 ("Mechanik", "Mechanics"),
  ISO31_4 ("Wärme", "Heat"),
  ISO31_5 ("Elektrizität und Magnetismus", "Electricity and magnetism"),
  ISO31_6 ("Licht und verwandte elektromagnetische Strahlung", "Light and related electromagnetic radiations"),
  ISO31_7 ("Akustik", "Acoustics"),
  ISO31_8 ("Physikalische Chemie und Molekularphysik", "Physical chemistry and molecular physics"),
  ISO31_9 ("Atomphysik und Kernphysik", "Atomic and nuclear physics"),
  ISO31_10 ("Kernreaktionen und ionisierende Strahlungen", "Nuclear reactions and ionizing radiations"),
  ISO31_11 ("Mathematische Zeichen und Symbole", "Mathematical signs and symbols for use in the physical sciences and technology"),
  ISO31_12 ("Kennzahlen", "Characteristic numbers"),
  ISO31_13 ("Festkörperphysik", "Solid state physics");

  private final ISimpleMultiLingualText m_aTP;

  private EISO31Name (@Nonnull final String sDE, @Nonnull final String sEN)
  {
    m_aTP = TextProvider.create_DE_EN (sDE, sEN);
  }

  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return DefaultTextResolver.getText (this, m_aTP, aContentLocale);
  }
}
