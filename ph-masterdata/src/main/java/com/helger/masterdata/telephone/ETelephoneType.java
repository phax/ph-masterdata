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
package com.helger.masterdata.telephone;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.text.display.IHasDisplayText;

/**
 * Contains the possible telephone number types.
 *
 * @author Philip Helger
 */
public enum ETelephoneType implements ITelephoneType
{
  ASSISTANT ("ass", ETelephoneText.MSG_TYPE_ASSISTANT),
  BUSINESS ("bus", ETelephoneText.MSG_TYPE_BUSINESS),
  BUSINESS2 ("bus2", ETelephoneText.MSG_TYPE_BUSINESS2),
  BUSINESS_FAX ("busfax", ETelephoneText.MSG_TYPE_BUSINESS_FAX),
  OFFICE ("off", ETelephoneText.MSG_TYPE_OFFICE),
  OFFICE_FAX ("offfax", ETelephoneText.MSG_TYPE_OFFICE_FAX),
  PERSONAL ("per", ETelephoneText.MSG_TYPE_PERSONAL),
  PERSONAL2 ("per2", ETelephoneText.MSG_TYPE_PERSONAL2),
  PERSONAL_FAX ("perfax", ETelephoneText.MSG_TYPE_PERSONAL_FAX),
  ISDN ("isdn", ETelephoneText.MSG_TYPE_ISDN),
  MOBILE ("mob", ETelephoneText.MSG_TYPE_MOBILE),
  OTHER ("oth", ETelephoneText.MSG_TYPE_OTHER),
  OTHER_FAX ("othfax", ETelephoneText.MSG_TYPE_OTHER_FAX);

  private final String m_sID;
  private final IHasDisplayText m_aText;

  private ETelephoneType (@Nonnull @Nonempty final String sID, @Nonnull final ETelephoneText eText)
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
  public static ETelephoneType getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (ETelephoneType.class, sID);
  }

  @Nullable
  public static ETelephoneType getFromIDOrDefault (@Nullable final String sID, @Nullable final ETelephoneType eDefault)
  {
    return EnumHelper.getFromIDOrDefault (ETelephoneType.class, sID, eDefault);
  }
}
