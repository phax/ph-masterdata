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
package com.helger.masterdata.address;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.text.display.IHasDisplayText;

public enum EAddressType implements IAddressType, IHasDisplayText
{
  PERSONAL ("pers", EAddressText.MSG_TYPE_PERSONAL),
  PERSONAL2 ("pers2", EAddressText.MSG_TYPE_PERSONAL2),
  OFFICE ("off", EAddressText.MSG_TYPE_OFFICE),
  OFFICE2 ("off2", EAddressText.MSG_TYPE_OFFICE2),
  OTHER ("oth", EAddressText.MSG_TYPE_OTHER);

  private final String m_sID;
  private final IHasDisplayText m_aText;

  private EAddressType (@Nonnull @Nonempty final String sID, @Nonnull final EAddressText eText)
  {
    m_sID = sID;
    m_aText = eText;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return m_aText.getDisplayText (aContentLocale);
  }

  @Nullable
  public static EAddressType getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EAddressType.class, sID);
  }

  @Nullable
  public static EAddressType getFromIDOrDefault (@Nullable final String sID, @Nullable final EAddressType eDefault)
  {
    return EnumHelper.getFromIDOrDefault (EAddressType.class, sID, eDefault);
  }
}
