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
package com.helger.masterdata.austria;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasIntID;
import com.helger.commons.lang.EnumHelper;

/**
 * All Austrian States
 *
 * @author Philip Helger
 */
public enum EAustriaState implements IHasIntID
{
  BURGENLAND (1, "B"),
  KAERNTEN (2, "K"),
  NIEDEROESTERREICH (3, "N"),
  OBEROESTERREICH (4, "O"),
  SALZBURG (5, "Sa"),
  STEIERMARK (6, "St"),
  TIROL (7, "T"),
  VORARLBERG (8, "V"),
  WIEN (9, "W");

  private final int m_nID;
  private final String m_sPostID;

  EAustriaState (@Nonnegative final int nID, @Nonnull @Nonempty final String sPostID)
  {
    m_nID = nID;
    m_sPostID = sPostID;
  }

  @Nonnegative
  public int getID ()
  {
    return m_nID;
  }

  /**
   * @return Estimated postal code prefix (inexact).
   */
  @Nonnull
  @Nonempty
  public String getPostID ()
  {
    return m_sPostID;
  }

  /**
   * @return Verwaltungskennzeichen (VKZ) Prefix (L1 to L9).
   */
  @Nonnull
  @Nonempty
  public String getVKZPrefix ()
  {
    return "L" + m_nID;
  }

  @Nullable
  public static EAustriaState getFromIDOrNull (final int nID)
  {
    return EnumHelper.getFromIDOrNull (EAustriaState.class, nID);
  }

  @Nonnull
  public static EAustriaState getFromIDOrThrow (final int nID)
  {
    return EnumHelper.getFromIDOrThrow (EAustriaState.class, nID);
  }
}
