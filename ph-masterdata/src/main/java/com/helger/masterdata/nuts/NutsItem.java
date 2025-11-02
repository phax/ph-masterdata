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
package com.helger.masterdata.nuts;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.Nonnegative;
import com.helger.annotation.concurrent.Immutable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.id.IHasID;
import com.helger.base.name.IHasDisplayName;
import com.helger.base.string.StringHelper;
import com.helger.base.tostring.ToStringGenerator;

/**
 * Represents a single NUTS item from the list.
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
  private final String m_sLatinDisplayName;
  private final ENutsLevel m_eNutsLevel;
  private final int m_nCountryOrdinal;
  private final int m_nRegionOrdinal;

  public NutsItem (@NonNull @Nonempty final String sID,
                   @NonNull @Nonempty final String sDisplayName,
                   @Nullable final String sLatinDisplayName,
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
    m_sLatinDisplayName = StringHelper.isNotEmpty (sLatinDisplayName) ? sLatinDisplayName : sDisplayName;
    m_eNutsLevel = ENutsLevel.getFromLengthOrThrow (sID.length ());
    m_nCountryOrdinal = nCountryOrdinal;
    m_nRegionOrdinal = nRegionOrdinal;
  }

  /**
   * @return The NUTS code. As in <code>BE</code> or <code>AT130</code>.
   */
  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  /**
   * @return The display name of the NUTS item in local language.
   * @see #getLatinDisplayName() for the Latin version
   */
  @NonNull
  @Nonempty
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  /**
   * @return The Latin display name of the NUTS item. If no specific latin name is provided, it's
   *         identical to {@link #getDisplayName()}.
   */
  @NonNull
  @Nonempty
  public String getLatinDisplayName ()
  {
    return m_sLatinDisplayName;
  }

  /**
   * @return The NUTS level of the item. Never <code>null</code>.
   */
  @NonNull
  public ENutsLevel getLevel ()
  {
    return m_eNutsLevel;
  }

  /**
   * @return The ordinal number of the country, to which the NUTS item belongs to. Always &gt; 0.
   */
  @Nonnegative
  public int getCountryOrdinal ()
  {
    return m_nCountryOrdinal;
  }

  /**
   * @return The ordinal number of the region, which the NUTS item represents. Always &gt; 0.
   */
  @Nonnegative
  public int getRegionOrdinal ()
  {
    return m_nRegionOrdinal;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("ID", m_sID)
                                       .append ("DisplayName", m_sDisplayName)
                                       .append ("LatinDisplayName", m_sLatinDisplayName)
                                       .append ("Level", m_eNutsLevel)
                                       .append ("CountryOrdinal", m_nCountryOrdinal)
                                       .append ("RegionOrdinal", m_nRegionOrdinal)
                                       .getToString ();
  }
}
