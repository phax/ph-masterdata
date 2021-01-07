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
package com.helger.masterdata.unit;

import javax.annotation.Nonnegative;
import javax.annotation.Nullable;

import com.helger.commons.id.IHasIntID;
import com.helger.commons.lang.EnumHelper;

public enum EUnitLevel implements IHasIntID
{
  NORMATIVE (1),
  NORMATIVE_EQUIVALENT (2),
  INFORMATIVE (3);

  private final int m_nID;

  EUnitLevel (@Nonnegative final int nID)
  {
    m_nID = nID;
  }

  @Nonnegative
  public int getID ()
  {
    return m_nID;
  }

  @Nullable
  public static EUnitLevel getFromIDOrNull (final int nID)
  {
    return EnumHelper.getFromIDOrNull (EUnitLevel.class, nID);
  }
}
