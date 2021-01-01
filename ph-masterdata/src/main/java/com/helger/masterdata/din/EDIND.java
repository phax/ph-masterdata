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
package com.helger.masterdata.din;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.lang.EnumHelper;

/**
 * DIN D. Width and height are in portrait mode.<br>
 * <a href=
 * "http://www.din-formate.de/reihe-d-din-groessen-aufstellung-groessentabelle-blattmasse-werte-in-mm-dpi-pixel.html"
 * >Source</a>
 *
 * @author Philip Helger
 */
public enum EDIND implements IDINSize
{
  D0 ("d0", 771, 1090),
  D1 ("d1", 545, 771),
  D2 ("d2", 385, 545),
  D3 ("d3", 272, 285),
  D4 ("d4", 192, 272),
  D5 ("d5", 136, 192),
  D6 ("d6", 96, 136),
  D7 ("d7", 68, 96),
  D8 ("d8", 48, 68),
  D9 ("d9", 34, 48),
  D10 ("d10", 24, 34);

  private final String m_sID;
  private final int m_nWidthMM;
  private final int m_nHeightMM;

  private EDIND (@Nonnull @Nonempty final String sID, @Nonnegative final int nWidthMM, @Nonnegative final int nHeightMM)
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
  public static EDIND getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EDIND.class, sID);
  }

  @Nullable
  public static EDIND getFromIDOrDefault (@Nullable final String sID, @Nullable final EDIND eDefault)
  {
    return EnumHelper.getFromIDOrDefault (EDIND.class, sID, eDefault);
  }
}
