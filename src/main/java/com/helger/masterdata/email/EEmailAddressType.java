/**
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
package com.helger.masterdata.email;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.name.IHasDisplayText;

public enum EEmailAddressType implements IHasID <String>, IHasDisplayText
{
  PERSONAL ("pers", EEmailAddressText.MSG_TYPE_PERSONAL),
  PERSONAL2 ("pers2", EEmailAddressText.MSG_TYPE_PERSONAL2),
  OFFICE ("off", EEmailAddressText.MSG_TYPE_OFFICE),
  OFFICE2 ("off2", EEmailAddressText.MSG_TYPE_OFFICE2),
  OTHER ("oth", EEmailAddressText.MSG_TYPE_OTHER);

  private final String m_sID;
  private final IHasDisplayText m_aText;

  private EEmailAddressType (@Nonnull final String sID, @Nonnull final EEmailAddressText eText)
  {
    m_sID = sID;
    m_aText = eText;
  }

  @Nonnull
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
  public static EEmailAddressType getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EEmailAddressType.class, sID);
  }

  @Nullable
  public static EEmailAddressType getFromIDOrDefault (@Nullable final String sID,
                                                      @Nullable final EEmailAddressType eDefault)
  {
    return EnumHelper.getFromIDOrDefault (EEmailAddressType.class, sID, eDefault);
  }
}
