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
package com.helger.masterdata.telephone;

import javax.annotation.Nonnull;

import com.helger.commons.microdom.IMicroElement;

public final class TelephoneNumberMicroTypeConverter extends AbstractTelephoneNumberMicroTypeConverter
{
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
