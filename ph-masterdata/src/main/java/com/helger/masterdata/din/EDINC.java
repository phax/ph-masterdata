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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.lang.EnumHelper;

/**
 * DIN C. Width and height are in portrait mode.<br>
 * <a href=
 * "http://www.din-formate.de/reihe-c-din-groessen-liste-papierformate-seitengroesse-masseinheiten-in-dpi-mm-pixel.html"
 * >Source</a>
 *
 * @author Philip Helger
 */
public enum EDINC implements IDINSize
{
  C0 ("c0", 917, 1297),
  C1 ("c1", 648, 917),
  C2 ("c2", 458, 648),
  C3 ("c3", 324, 458),
  C4 ("c4", 229, 324),
  C5 ("c5", 162, 229),
  C6 ("c6", 114, 162),
  C7 ("c7", 81, 114),
  C8 ("c8", 57, 81),
  C9 ("c9", 40, 57),
  C10 ("c10", 28, 40);

  private final String m_sID;
  private final int m_nWidthMM;
  private final int m_nHeightMM;

  EDINC (@Nonnull @Nonempty final String sID, @Nonnegative final int nWidthMM, @Nonnegative final int nHeightMM)
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
  public static EDINC getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EDINC.class, sID);
  }

  @Nullable
  public static EDINC getFromIDOrDefault (@Nullable final String sID, @Nullable final EDINC eDefault)
  {
    return EnumHelper.getFromIDOrDefault (EDINC.class, sID, eDefault);
  }
}
