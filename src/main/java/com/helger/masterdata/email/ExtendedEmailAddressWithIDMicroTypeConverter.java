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
package com.helger.masterdata.email;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.string.StringParser;

public final class ExtendedEmailAddressWithIDMicroTypeConverter extends AbstractExtendedEmailAddressMicroTypeConverter
{
  protected static final String ATTR_ID = "type";

  @Override
  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final Object aObject,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final ExtendedEmailAddressWithID aEmail = (ExtendedEmailAddressWithID) aObject;
    final IMicroElement ret = super.convertToMicroElement (aObject, sNamespaceURI, sTagName);
    ret.setAttribute (ATTR_ID, aEmail.getID ());
    return ret;
  }

  @Nonnull
  public ExtendedEmailAddressWithID convertToNative (@Nonnull final IMicroElement eEmail)
  {
    final int nID = StringParser.parseInt (eEmail.getAttribute (ATTR_ID), -1);
    final EEmailAddressType eType = EEmailAddressType.getFromIDOrNull (eEmail.getAttribute (ATTR_TYPE));
    final String sAddress = eEmail.getAttribute (ATTR_ADDRESS);
    final String sPersonal = eEmail.getAttribute (ATTR_PERSONAL);
    return new ExtendedEmailAddressWithID (nID, eType, sAddress, sPersonal);
  }
}
