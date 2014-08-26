/**
 * Copyright (C) 2006-2014 phloc systems (www.phloc.com)
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
package com.helger.masterdata.address;

import java.util.Locale;

import javax.annotation.Nonnull;

import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.system.SystemHelper;

public class ReadonlyAddressMicroTypeConverter extends AbstractAddressMicroTypeConverter
{
  @Nonnull
  public ReadonlyAddress convertToNative (@Nonnull final IMicroElement eAddress)
  {
    final Locale aLocale = SystemHelper.getSystemLocale ();
    final EAddressType eType = EAddressType.getFromIDOrNull (eAddress.getAttribute (ATTR_TYPE));
    final String sCountry = eAddress.getAttribute (ATTR_COUNTRY);
    final String sState = eAddress.getAttribute (ATTR_STATE);
    final String sPostalCode = eAddress.getAttribute (ATTR_POSTALCODE);
    final String sCity = eAddress.getAttribute (ATTR_CITY);
    final String sStreet = eAddress.getAttribute (ATTR_STREET);
    final String sBuildingNumber = eAddress.getAttribute (ATTR_BUILDINGNUMBER);
    final String sPostOfficeBox = eAddress.getAttribute (ATTR_POBOX);
    return new ReadonlyAddress (eType,
                                sCountry,
                                sState,
                                sPostalCode,
                                sCity,
                                sStreet,
                                sBuildingNumber,
                                sPostOfficeBox,
                                aLocale);
  }
}
