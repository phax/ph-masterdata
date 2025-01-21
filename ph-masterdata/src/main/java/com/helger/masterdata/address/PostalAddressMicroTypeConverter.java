/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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
import javax.annotation.Nullable;

import com.helger.commons.string.StringHelper;
import com.helger.commons.system.SystemHelper;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.IMicroQName;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.MicroQName;
import com.helger.xml.microdom.convert.IMicroTypeConverter;

/**
 * MicroType converter for {@link PostalAddress}.
 * 
 * @author Philip Helger
 */
public class PostalAddressMicroTypeConverter implements IMicroTypeConverter <PostalAddress>
{
  private static final IMicroQName ATTR_TYPE = new MicroQName ("type");
  private static final IMicroQName ATTR_COUNTRY = new MicroQName ("country");
  private static final IMicroQName ATTR_STATE = new MicroQName ("state");
  private static final IMicroQName ATTR_POSTALCODE = new MicroQName ("zipcode");
  private static final IMicroQName ATTR_CITY = new MicroQName ("city");
  private static final IMicroQName ATTR_STREET = new MicroQName ("street");
  private static final IMicroQName ATTR_BUILDINGNUMBER = new MicroQName ("buildingno");
  private static final IMicroQName ATTR_POBOX = new MicroQName ("pobox");
  private static final IMicroQName ATTR_CARE_OF = new MicroQName ("careof");

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final PostalAddress aAddress,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
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

  @Nonnull
  public PostalAddress convertToNative (@Nonnull final IMicroElement eAddress)
  {
    final Locale aLocale = SystemHelper.getSystemLocale ();
    final EPostalAddressType eType = EPostalAddressType.getFromIDOrNull (eAddress.getAttributeValue (ATTR_TYPE));
    final String sCountry = eAddress.getAttributeValue (ATTR_COUNTRY);
    final String sState = eAddress.getAttributeValue (ATTR_STATE);
    final String sPostalCode = eAddress.getAttributeValue (ATTR_POSTALCODE);
    final String sCity = eAddress.getAttributeValue (ATTR_CITY);
    final String sStreet = eAddress.getAttributeValue (ATTR_STREET);
    final String sBuildingNumber = eAddress.getAttributeValue (ATTR_BUILDINGNUMBER);
    final String sPostOfficeBox = eAddress.getAttributeValue (ATTR_POBOX);
    final String sCareOf = eAddress.getAttributeValue (ATTR_CARE_OF);
    return new PostalAddress (eType, sCountry, sState, sPostalCode, sCity, sStreet, sBuildingNumber, sPostOfficeBox, sCareOf, aLocale);
  }
}
