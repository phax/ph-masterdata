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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.MicroElement;
import com.helger.commons.microdom.convert.IMicroTypeConverter;
import com.helger.commons.string.StringHelper;

public abstract class AbstractAddressMicroTypeConverter implements IMicroTypeConverter
{
  protected static final String ATTR_TYPE = "type";
  protected static final String ATTR_COUNTRY = "country";
  protected static final String ATTR_STATE = "state";
  protected static final String ATTR_POSTALCODE = "zipcode";
  protected static final String ATTR_CITY = "city";
  protected static final String ATTR_STREET = "street";
  protected static final String ATTR_BUILDINGNUMBER = "buildingno";
  protected static final String ATTR_POBOX = "pobox";
  protected static final String ATTR_CARE_OF = "careof";

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final Object aObject,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final IAddress aAddress = (IAddress) aObject;
    final IMicroElement eAddress = new MicroElement (sNamespaceURI, sTagName);
    if (aAddress.getType () != null)
      eAddress.setAttribute (ATTR_TYPE, aAddress.getType ().getID ());
    if (StringHelper.hasText (aAddress.getCountry ()))
      eAddress.setAttribute (ATTR_COUNTRY, aAddress.getCountry ());
    if (StringHelper.hasText (aAddress.getState ()))
      eAddress.setAttribute (ATTR_STATE, aAddress.getState ());
    if (StringHelper.hasText (aAddress.getPostalCode ()))
      eAddress.setAttribute (ATTR_POSTALCODE, aAddress.getPostalCode ());
    if (StringHelper.hasText (aAddress.getCity ()))
      eAddress.setAttribute (ATTR_CITY, aAddress.getCity ());
    if (StringHelper.hasText (aAddress.getStreet ()))
      eAddress.setAttribute (ATTR_STREET, aAddress.getStreet ());
    if (StringHelper.hasText (aAddress.getBuildingNumber ()))
      eAddress.setAttribute (ATTR_BUILDINGNUMBER, aAddress.getBuildingNumber ());
    if (StringHelper.hasText (aAddress.getPostOfficeBox ()))
      eAddress.setAttribute (ATTR_POBOX, aAddress.getPostOfficeBox ());
    if (StringHelper.hasText (aAddress.getCareOf ()))
      eAddress.setAttribute (ATTR_CARE_OF, aAddress.getCareOf ());
    return eAddress;
  }
}
