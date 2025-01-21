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
package com.helger.masterdata.unit;

import java.util.Locale;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.id.IHasIntID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.text.display.IHasDisplayText;

public enum EISO31 implements IHasIntID, IHasDisplayText
{
  ISO31_0 (0, EISO31Name.ISO31_0),
  ISO31_1 (1, EISO31Name.ISO31_1),
  ISO31_2 (2, EISO31Name.ISO31_2),
  ISO31_3 (3, EISO31Name.ISO31_3),
  ISO31_4 (4, EISO31Name.ISO31_4),
  ISO31_5 (5, EISO31Name.ISO31_5),
  ISO31_6 (6, EISO31Name.ISO31_6),
  ISO31_7 (7, EISO31Name.ISO31_7),
  ISO31_8 (8, EISO31Name.ISO31_8),
  ISO31_9 (9, EISO31Name.ISO31_9),
  ISO31_10 (10, EISO31Name.ISO31_10),
  ISO31_11 (11, EISO31Name.ISO31_11),
  ISO31_12 (12, EISO31Name.ISO31_12),
  ISO31_13 (13, EISO31Name.ISO31_13);

  private final int m_nID;
  private final IHasDisplayText m_aName;

  EISO31 (@Nonnegative final int nNumber, @Nonnull final EISO31Name aName)
  {
    m_nID = nNumber;
    m_aName = aName;
  }

  @Nonnegative
  public int getID ()
  {
    return m_nID;
  }

  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return m_aName.getDisplayText (aContentLocale);
  }

  @Nullable
  public static EISO31 getFromIDOrNull (final int nID)
  {
    return EnumHelper.getFromIDOrNull (EISO31.class, nID);
  }
}
