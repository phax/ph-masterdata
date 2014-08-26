/**
 * Copyright (C) 2006-2014 phloc systems (www.phloc.com)
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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
package com.helger.masterdata.ean;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotations.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;

public enum EEANChecksumMode implements IHasID <String>
{
  /** "auto" chooses the default checksum behaviour */
  AUTO ("auto"),
  /** "ignore" doesn't check nor add a checksum */
  IGNORE ("ignore"),
  /** "add" adds the necessary checksum anyway */
  ADD ("add"),
  /**
   * "check" requires the check character to be present in the message. It will
   * be checked.
   */
  CHECK ("check");

  private final String m_sID;

  /**
   * Creates a new ChecksumMode instance.
   * 
   * @param sID
   *        the name of the ChecksumMode
   */
  private EEANChecksumMode (@Nonnull @Nonempty final String sID)
  {
    m_sID = sID;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  /**
   * Returns a ChecksumMode instance by ID.
   * 
   * @param sID
   *        the ID of the ChecksumMode
   * @return the requested instance
   */
  @Nullable
  public static EEANChecksumMode getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EEANChecksumMode.class, sID);
  }
}
