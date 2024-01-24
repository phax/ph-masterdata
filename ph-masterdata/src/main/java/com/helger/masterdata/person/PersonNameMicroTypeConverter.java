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
package com.helger.masterdata.person;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.string.StringHelper;
import com.helger.commons.system.SystemHelper;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.IMicroQName;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.MicroQName;
import com.helger.xml.microdom.convert.IMicroTypeConverter;

public final class PersonNameMicroTypeConverter implements IMicroTypeConverter <PersonName>
{
  protected static final IMicroQName ATTR_SALUTATION = new MicroQName ("salutation");
  protected static final IMicroQName ATTR_PREFIXTITLE = new MicroQName ("prefixtitle");
  protected static final IMicroQName ATTR_FIRSTNAME = new MicroQName ("firstname");
  protected static final IMicroQName ATTR_MIDDLENAME = new MicroQName ("middlename");
  protected static final IMicroQName ATTR_LASTNAME = new MicroQName ("lastname");
  protected static final IMicroQName ATTR_SUFFIXTITLE = new MicroQName ("suffixtitle");

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final PersonName aValue,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final IMicroElement eName = new MicroElement (sNamespaceURI, sTagName);
    if (aValue.getSalutation () != null)
      eName.setAttribute (ATTR_SALUTATION, aValue.getSalutationID ());
    if (StringHelper.hasText (aValue.getPrefixTitle ()))
      eName.setAttribute (ATTR_PREFIXTITLE, aValue.getPrefixTitle ());
    if (StringHelper.hasText (aValue.getFirstName ()))
      eName.setAttribute (ATTR_FIRSTNAME, aValue.getFirstName ());
    if (StringHelper.hasText (aValue.getMiddleName ()))
      eName.setAttribute (ATTR_MIDDLENAME, aValue.getMiddleName ());
    if (StringHelper.hasText (aValue.getLastName ()))
      eName.setAttribute (ATTR_LASTNAME, aValue.getLastName ());
    if (StringHelper.hasText (aValue.getSuffixTitle ()))
      eName.setAttribute (ATTR_SUFFIXTITLE, aValue.getSuffixTitle ());
    return eName;
  }

  @Nonnull
  public PersonName convertToNative (@Nonnull final IMicroElement eAddress)
  {
    final Locale aLocale = SystemHelper.getSystemLocale ();
    final PersonName aName = new PersonName ();
    aName.setSalutation (ESalutation.getFromIDOrNull (eAddress.getAttributeValue (ATTR_SALUTATION)));
    aName.setPrefixTitle (eAddress.getAttributeValue (ATTR_PREFIXTITLE));
    aName.setFirstName (eAddress.getAttributeValue (ATTR_FIRSTNAME), aLocale);
    aName.setMiddleName (eAddress.getAttributeValue (ATTR_MIDDLENAME), aLocale);
    aName.setLastName (eAddress.getAttributeValue (ATTR_LASTNAME), aLocale);
    aName.setSuffixTitle (eAddress.getAttributeValue (ATTR_SUFFIXTITLE));
    return aName;
  }
}
