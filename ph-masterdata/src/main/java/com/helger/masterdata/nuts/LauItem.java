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

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.Immutable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.id.IHasID;
import com.helger.base.name.IHasDisplayName;
import com.helger.base.string.StringHelper;
import com.helger.base.tostring.ToStringGenerator;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Represents a single LAU item and its relationship to the NUTS codes.
 *
 * @author Philip Helger
 * @since 6.2.4
 */
@Immutable
public class LauItem implements IHasID <String>, IHasDisplayName
{
  // These values have been determined heuristically based on the 2021 data
  public static final int ID_MIN_LENGTH = 1;
  public static final int ID_MAX_LENGTH = 13;

  private final String m_sLau;
  private final String m_sNuts;
  private final String m_sDisplayName;
  private final String m_sLatinDisplayName;

  public LauItem (@Nonnull @Nonempty final String sNuts,
                  @Nonnull @Nonempty final String sLau,
                  @Nonnull @Nonempty final String sDisplayName,
                  @Nullable final String sLatinDisplayName)
  {
    ValueEnforcer.notEmpty (sLau, "LAU");
    ValueEnforcer.isTrue ( () -> sLau.length () >= ID_MIN_LENGTH && sLau.length () <= ID_MAX_LENGTH,
                           () -> "Odd LAU length of '" + sLau + "'");
    ValueEnforcer.notEmpty (sNuts, "NUTS");
    ValueEnforcer.isTrue ( () -> sNuts.length () <= ENutsLevel.NUTS3.getCharCount (),
                           "() ->Odd NUTS length of '" + sNuts + "'");
    ValueEnforcer.notEmpty (sDisplayName, "Name");

    m_sLau = sLau;
    m_sNuts = sNuts;
    m_sDisplayName = sDisplayName;
    m_sLatinDisplayName = StringHelper.isNotEmpty (sLatinDisplayName) ? sLatinDisplayName : sDisplayName;
  }

  /**
   * @return The NUTS + LAU code combined. This one is ensured to be unique. Neither
   *         <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sNuts + m_sLau;
  }

  /**
   * @return The LAU code. Neither <code>null</code> nor empty.
   */
  @Nonnull
  @Nonempty
  public String getLauCode ()
  {
    return m_sLau;
  }

  /**
   * @return The Country code to which the LAU belongs to. This is calculated from the NUTS code.
   *         Never <code>null</code>.
   */
  @Nonnull
  @Nonempty
  public String getCountryCode ()
  {
    return m_sNuts.substring (0, 2);
  }

  /**
   * @return The NUTS code to which the LAU belongs to. Never <code>null</code>.
   */
  @Nonnull
  @Nonempty
  public String getNutsCode ()
  {
    return m_sNuts;
  }

  /**
   * @return The display name of the LAU item in local language.
   * @see #getLatinDisplayName() for the Latin version
   */
  @Nonnull
  @Nonempty
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  /**
   * @return The Latin display name of the LAU item. If no specific latin name is provided, it's
   *         identical to {@link #getDisplayName()}.
   */
  @Nonnull
  @Nonempty
  public String getLatinDisplayName ()
  {
    return m_sLatinDisplayName;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (null).append ("ID", m_sLau)
                                       .append ("Nuts3", m_sNuts)
                                       .append ("DisplayName", m_sDisplayName)
                                       .append ("LatinDisplayName", m_sLatinDisplayName)
                                       .getToString ();
  }
}
