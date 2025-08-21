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
package com.helger.masterdata.person;

import java.util.Locale;

import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;
import com.helger.text.display.IHasDisplayText;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Represents the gender of a person.
 *
 * @author Philip Helger
 */
public enum EGender implements IHasDisplayText, IHasID <String>
{
  MALE ("m", EGenderName.MALE),
  FEMALE ("f", EGenderName.FEMALE),
  OTHER ("o", EGenderName.OTHER);

  private final String m_sID;
  private final EGenderName m_aText;

  EGender (@Nonnull final String sID, @Nonnull final EGenderName eText)
  {
    m_sID = sID;
    m_aText = eText;
  }

  @Nonnull
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  public EGenderName getName ()
  {
    return m_aText;
  }

  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return m_aText.getDisplayText (aContentLocale);
  }

  @Nullable
  public static EGender getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EGender.class, sID);
  }
}
