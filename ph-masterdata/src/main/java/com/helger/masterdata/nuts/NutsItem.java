/*
 * Copyright (C) 2014-2022 Philip Helger (www.helger.com)
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
package com.helger.masterdata.nuts;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.name.IHasDisplayName;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;

/**
 * Represents a single NUTS item from the code list.
 *
 * @author Philip Helger
 * @since 6.2.4
 */
@Immutable
public class NutsItem implements IHasID <String>, IHasDisplayName
{
  public static final int ID_MIN_LENGTH = 2;
  public static final int ID_MAX_LENGTH = 5;

  private final String m_sID;
  private final String m_sDisplayName;
  private final String m_sLatinName;
  private final ENutsLevel m_eNutsLevel;
  private final int m_nCountryOrdinal;
  private final int m_nRegionOrdinal;

  public NutsItem (@Nonnull @Nonempty final String sID,
                   @Nonnull @Nonempty final String sDisplayName,
                   @Nullable final String sLatinName,
                   @Nonnegative final int nCountryOrdinal,
                   @Nonnegative final int nRegionOrdinal)
  {
    ValueEnforcer.notEmpty (sID, "ID");
    ValueEnforcer.isTrue ( () -> sID.length () >= ID_MIN_LENGTH && sID.length () <= ID_MAX_LENGTH, "Odd ID length");
    ValueEnforcer.notEmpty (sDisplayName, "Name");
    ValueEnforcer.isGT0 (nCountryOrdinal, "CountryOrdinal");
    ValueEnforcer.isGT0 (nRegionOrdinal, "RegionOrdinal");

    m_sID = sID;
    m_sDisplayName = sDisplayName;
    m_sLatinName = StringHelper.hasText (sLatinName) ? sLatinName : sDisplayName;
    m_eNutsLevel = ENutsLevel.getFromLengthOrThrow (sID.length ());
    m_nCountryOrdinal = nCountryOrdinal;
    m_nRegionOrdinal = nRegionOrdinal;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nonnull
  @Nonempty
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  /**
   * @return The Latin display name. If no specific latin name is provided, it's
   *         identical to {@link #getDisplayName()}.
   */
  @Nonnull
  @Nonempty
  public String getLatinDisplayName ()
  {
    return m_sLatinName;
  }

  @Nonnull
  public ENutsLevel getLevel ()
  {
    return m_eNutsLevel;
  }

  @Nonnull
  public int getCountryOrdinal ()
  {
    return m_nCountryOrdinal;
  }

  @Nonnull
  public int getRegionOrdinal ()
  {
    return m_nRegionOrdinal;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("ID", m_sID)
                                       .append ("DisplayName", m_sDisplayName)
                                       .append ("LatinDisplayName", m_sLatinName)
                                       .append ("Level", m_eNutsLevel)
                                       .append ("CountryOrdinal", m_nCountryOrdinal)
                                       .append ("RegionOrdinal", m_nRegionOrdinal)
                                       .getToString ();
  }
}
