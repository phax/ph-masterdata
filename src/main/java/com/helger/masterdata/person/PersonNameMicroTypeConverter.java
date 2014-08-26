/**
 * Copyright (C) 2006-2014 phloc systems
 * http://www.phloc.com
 * office[at]phloc[dot]com
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
package com.helger.masterdata.person;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.convert.IMicroTypeConverter;
import com.helger.commons.microdom.impl.MicroElement;
import com.helger.commons.string.StringHelper;
import com.helger.commons.system.SystemHelper;

public final class PersonNameMicroTypeConverter implements IMicroTypeConverter
{
  protected static final String ATTR_SALUTATION = "salutation";
  protected static final String ATTR_PREFIXTITLE = "prefixtitle";
  protected static final String ATTR_FIRSTNAME = "firstname";
  protected static final String ATTR_MIDDLENAME = "middlename";
  protected static final String ATTR_LASTNAME = "lastname";
  protected static final String ATTR_SUFFIXTITLE = "suffixtitle";

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final Object aObject,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final PersonName aAddress = (PersonName) aObject;
    final IMicroElement eName = new MicroElement (sNamespaceURI, sTagName);
    if (aAddress.getSalutation () != null)
      eName.setAttribute (ATTR_SALUTATION, aAddress.getSalutationID ());
    if (StringHelper.hasText (aAddress.getPrefixTitle ()))
      eName.setAttribute (ATTR_PREFIXTITLE, aAddress.getPrefixTitle ());
    if (StringHelper.hasText (aAddress.getFirstName ()))
      eName.setAttribute (ATTR_FIRSTNAME, aAddress.getFirstName ());
    if (StringHelper.hasText (aAddress.getMiddleName ()))
      eName.setAttribute (ATTR_MIDDLENAME, aAddress.getMiddleName ());
    if (StringHelper.hasText (aAddress.getLastName ()))
      eName.setAttribute (ATTR_LASTNAME, aAddress.getLastName ());
    if (StringHelper.hasText (aAddress.getSuffixTitle ()))
      eName.setAttribute (ATTR_SUFFIXTITLE, aAddress.getSuffixTitle ());
    return eName;
  }

  @Nonnull
  public PersonName convertToNative (@Nonnull final IMicroElement eAddress)
  {
    final Locale aLocale = SystemHelper.getSystemLocale ();
    final PersonName aName = new PersonName ();
    aName.setSalutation (ESalutation.getFromIDOrNull (eAddress.getAttribute (ATTR_SALUTATION)));
    aName.setPrefixTitle (eAddress.getAttribute (ATTR_PREFIXTITLE));
    aName.setFirstName (eAddress.getAttribute (ATTR_FIRSTNAME), aLocale);
    aName.setMiddleName (eAddress.getAttribute (ATTR_MIDDLENAME), aLocale);
    aName.setLastName (eAddress.getAttribute (ATTR_LASTNAME), aLocale);
    aName.setSuffixTitle (eAddress.getAttribute (ATTR_SUFFIXTITLE));
    return aName;
  }
}
