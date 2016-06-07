/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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

import com.helger.commons.system.SystemHelper;
import com.helger.xml.microdom.IMicroElement;

public class AddressMicroTypeConverter extends AbstractAddressMicroTypeConverter
{
  @Nonnull
  public Address convertToNative (@Nonnull final IMicroElement eAddress)
  {
    final Locale aLocale = SystemHelper.getSystemLocale ();
    final EAddressType eType = EAddressType.getFromIDOrNull (eAddress.getAttributeValue (ATTR_TYPE));
    final String sCountry = eAddress.getAttributeValue (ATTR_COUNTRY);
    final String sState = eAddress.getAttributeValue (ATTR_STATE);
    final String sPostalCode = eAddress.getAttributeValue (ATTR_POSTALCODE);
    final String sCity = eAddress.getAttributeValue (ATTR_CITY);
    final String sStreet = eAddress.getAttributeValue (ATTR_STREET);
    final String sBuildingNumber = eAddress.getAttributeValue (ATTR_BUILDINGNUMBER);
    final String sPostOfficeBox = eAddress.getAttributeValue (ATTR_POBOX);
    final String sCareOf = eAddress.getAttributeValue (ATTR_CARE_OF);
    return new Address (eType,
                        sCountry,
                        sState,
                        sPostalCode,
                        sCity,
                        sStreet,
                        sBuildingNumber,
                        sPostOfficeBox,
                        sCareOf,
                        aLocale);
  }
}
