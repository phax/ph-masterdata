/*
 * Copyright (C) 2014-2024 Philip Helger (www.helger.com)
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
package com.helger.masterdata.din;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.lang.EnumHelper;

/**
 * DIN A. Width and height are in portrait mode.<br>
 * <a href="http://www.din-formate.de/reihe-a-din-groessen-mm-pixel-dpi.html">
 * Source</a>
 *
 * @author Philip Helger
 */
public enum EDINA implements IDINSize
{
  A0 ("a0", 841, 1189),
  A1 ("a1", 594, 841),
  A2 ("a2", 420, 594),
  A3 ("a3", 297, 420),
  A4 ("a4", 210, 297),
  A5 ("a5", 148, 210),
  A6 ("a6", 105, 148),
  A7 ("a7", 74, 105),
  A8 ("a8", 52, 74),
  A9 ("a9", 37, 52),
  A10 ("a10", 26, 37);

  private final String m_sID;
  private final int m_nWidthMM;
  private final int m_nHeightMM;

  EDINA (@Nonnull @Nonempty final String sID, @Nonnegative final int nWidthMM, @Nonnegative final int nHeightMM)
  {
    m_sID = sID;
    m_nWidthMM = nWidthMM;
    m_nHeightMM = nHeightMM;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnegative
  public int getWidthMM ()
  {
    return m_nWidthMM;
  }

  @Nonnegative
  public int getHeightMM ()
  {
    return m_nHeightMM;
  }

  @Nullable
  public static EDINA getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EDINA.class, sID);
  }

  @Nullable
  public static EDINA getFromIDOrDefault (@Nullable final String sID, @Nullable final EDINA eDefault)
  {
    return EnumHelper.getFromIDOrDefault (EDINA.class, sID, eDefault);
  }
}
