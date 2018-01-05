/**
 * Copyright (C) 2014-2018 Philip Helger (www.helger.com)
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.IMicroQName;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.MicroQName;
import com.helger.xml.microdom.convert.IMicroTypeConverter;

public final class TelephoneNumberMicroTypeConverter implements IMicroTypeConverter <TelephoneNumber>
{
  private static final IMicroQName ATTR_TYPE = new MicroQName ("type");
  private static final IMicroQName ATTR_COUNTRYCODE = new MicroQName ("countrycode");
  private static final IMicroQName ATTR_AREACODE = new MicroQName ("areacode");
  private static final IMicroQName ATTR_LINE = new MicroQName ("line");
  private static final IMicroQName ATTR_DIRECTDIAL = new MicroQName ("directdial");

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final TelephoneNumber aTelNo,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final IMicroElement eTelNo = new MicroElement (sNamespaceURI, sTagName);
    if (aTelNo.getType () != null)
      eTelNo.setAttribute (ATTR_TYPE, aTelNo.getType ().getID ());
    eTelNo.setAttribute (ATTR_COUNTRYCODE, aTelNo.getCountryCode ());
    eTelNo.setAttribute (ATTR_AREACODE, aTelNo.getAreaCode ());
    eTelNo.setAttribute (ATTR_LINE, aTelNo.getLine ());
    eTelNo.setAttribute (ATTR_DIRECTDIAL, aTelNo.getDirectDial ());
    return eTelNo;
  }

  @Nonnull
  public TelephoneNumber convertToNative (@Nonnull final IMicroElement eTelNo)
  {
    final ETelephoneType eType = ETelephoneType.getFromIDOrNull (eTelNo.getAttributeValue (ATTR_TYPE));
    final String sCountryCode = eTelNo.getAttributeValue (ATTR_COUNTRYCODE);
    final String sAreaCode = eTelNo.getAttributeValue (ATTR_AREACODE);
    final String sLine = eTelNo.getAttributeValue (ATTR_LINE);
    final String sDirectDial = eTelNo.getAttributeValue (ATTR_DIRECTDIAL);
    return new TelephoneNumber (eType, sCountryCode, sAreaCode, sLine, sDirectDial);
  }
}
