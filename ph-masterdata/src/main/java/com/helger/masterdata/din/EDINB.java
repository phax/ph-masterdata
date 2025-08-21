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
package com.helger.masterdata.din;

import com.helger.annotation.Nonempty;
import com.helger.annotation.Nonnegative;
import com.helger.base.lang.EnumHelper;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * DIN B. Width and height are in portrait mode.<br>
 * <a href=
 * "http://www.din-formate.de/reihe-b-din-groessen-uebersicht-auflistung-blattgroesse-masse-in-pixel-mm-dpi.html"
 * >Source</a>
 *
 * @author Philip Helger
 */
public enum EDINB implements IDINSize
{
  B0 ("b0", 1000, 1414),
  B1 ("b1", 707, 1000),
  B2 ("b2", 500, 707),
  B3 ("b3", 353, 500),
  B4 ("b4", 250, 353),
  B5 ("b5", 176, 250),
  B6 ("b6", 125, 176),
  B7 ("b7", 88, 125),
  B8 ("b8", 62, 88),
  B9 ("b9", 44, 62),
  B10 ("b10", 31, 44);

  private final String m_sID;
  private final int m_nWidthMM;
  private final int m_nHeightMM;

  EDINB (@Nonnull @Nonempty final String sID, @Nonnegative final int nWidthMM, @Nonnegative final int nHeightMM)
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
  public static EDINB getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EDINB.class, sID);
  }

  @Nullable
  public static EDINB getFromIDOrDefault (@Nullable final String sID, @Nullable final EDINB eDefault)
  {
    return EnumHelper.getFromIDOrDefault (EDINB.class, sID, eDefault);
  }
}
