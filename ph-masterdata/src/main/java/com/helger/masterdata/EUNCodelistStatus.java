/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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
package com.helger.masterdata;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.id.IHasID;
import com.helger.commons.string.StringHelper;

/**
 * Represents the status of single elements within UN code lists.
 *
 * @author Philip Helger
 */
public enum EUNCodelistStatus implements IHasID <String>
{
  UNCHANGED ("unchanged", '\0'),
  ADDED ("added", '+'),
  CHANGEDNAME ("changedname", '#'),
  CHANGEDCHARACTERISTICS ("changedcharacteristics", '|'),
  DEPRECATED ("deprecated", 'D'),
  MARKEDDELETED ("markeddeleted", 'X'),
  REINSTATED ("reinstated", '=');

  private final String m_sID;
  private final char m_cText;

  private EUNCodelistStatus (@Nonnull @Nonempty final String sID, final char cText)
  {
    m_sID = sID;
    m_cText = cText;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  /**
   * Convert the passed status string into an array of {@link EUNCodelistStatus}
   * elements.
   *
   * @param sStatus
   *        The status string to be parsed. May be <code>null</code> or empty.
   * @return An array with one {@link #UNCHANGED} element if the passed input
   *         string is empty.
   */
  @Nonnull
  @Nonempty
  public static EUNCodelistStatus [] getFromTextOrUnchanged (@Nullable final String sStatus)
  {
    if (StringHelper.hasText (sStatus))
    {
      final ICommonsList <EUNCodelistStatus> ret = new CommonsArrayList<> ();
      for (final EUNCodelistStatus eStatus : values ())
        if (sStatus.indexOf (eStatus.m_cText) != -1)
          ret.add (eStatus);
      if (ret.isEmpty ())
        throw new IllegalArgumentException ("Illegal status: " + sStatus);
      return ret.toArray (new EUNCodelistStatus [ret.size ()]);
    }
    return new EUNCodelistStatus [] { UNCHANGED };
  }

  /**
   * Get the passed code list status array as one big string, separated by a
   * comma.
   *
   * @param aStatus
   *        The status array. May not be <code>null</code>.
   * @return The non-<code>null</code> String of all passed status elements.
   */
  @Nonnull
  public static String getAsString (@Nonnull final EUNCodelistStatus [] aStatus)
  {
    return StringHelper.getImplodedMapped (",", aStatus, EUNCodelistStatus::getID);
  }
}
