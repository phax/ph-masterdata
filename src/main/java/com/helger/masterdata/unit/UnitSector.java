/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.id.IHasIntID;
import com.helger.commons.name.IHasDisplayText;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.text.IReadonlyMultiLingualText;

@Immutable
public final class UnitSector implements IHasIntID, IHasDisplayText
{
  private final int m_nGroupNumber;
  private final IReadonlyMultiLingualText m_aName;
  private final EISO31 m_eISO31;

  public UnitSector (@Nonnegative final int nGroupNumber, @Nonnull final IReadonlyMultiLingualText aName)
  {
    ValueEnforcer.isGE0 (nGroupNumber, "GroupNumber");
    ValueEnforcer.notNull (aName, "Name");
    m_nGroupNumber = nGroupNumber;
    m_aName = aName;
    m_eISO31 = EISO31.getFromIDOrNull (nGroupNumber);
  }

  @Nonnegative
  public int getID ()
  {
    return m_nGroupNumber;
  }

  @Nonnull
  public Integer getIDObj ()
  {
    return Integer.valueOf (m_nGroupNumber);
  }

  @Nonnull
  public IReadonlyMultiLingualText getName ()
  {
    return m_aName;
  }

  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return m_aName.getTextWithLocaleFallback (aContentLocale);
  }

  @Nullable
  public EISO31 getISO31 ()
  {
    return m_eISO31;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("groupNumber", m_nGroupNumber)
                                       .append ("name", m_aName)
                                       .append ("iso31", m_eISO31)
                                       .toString ();
  }
}
