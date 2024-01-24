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
package com.helger.masterdata.nuts;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;

/**
 * NUTS level
 *
 * @author Philip Helger
 * @since 6.2.4
 */
public enum ENutsLevel implements IHasID <String>
{
  COUNTRY ("c", 2),
  NUTS1 ("nuts1", 3),
  NUTS2 ("nuts2", 4),
  NUTS3 ("nuts3", 5);

  private final String m_sID;
  private final int m_nCharCount;

  ENutsLevel (@Nonnull final String sID, @Nonnegative final int nCharCount)
  {
    m_sID = sID;
    m_nCharCount = nCharCount;
  }

  @Nonnull
  public String getID ()
  {
    return m_sID;
  }

  @Nonnegative
  public int getCharCount ()
  {
    return m_nCharCount;
  }

  @Nullable
  public static ENutsLevel getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (ENutsLevel.class, sID);
  }

  @Nullable
  public static ENutsLevel getFromLengthOrNull (final int nLength)
  {
    for (final ENutsLevel e : values ())
      if (nLength == e.m_nCharCount)
        return e;
    return null;
  }

  @Nonnull
  public static ENutsLevel getFromLengthOrThrow (final int nLength)
  {
    final ENutsLevel ret = getFromLengthOrNull (nLength);
    if (ret == null)
      throw new IllegalArgumentException ("Invalid length " + nLength + " for a NUTS entry");
    return ret;
  }
}
