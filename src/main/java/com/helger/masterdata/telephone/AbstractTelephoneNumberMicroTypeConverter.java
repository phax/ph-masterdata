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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.convert.IMicroTypeConverter;
import com.helger.commons.microdom.impl.MicroElement;

public abstract class AbstractTelephoneNumberMicroTypeConverter implements IMicroTypeConverter
{
  protected static final String ATTR_TYPE = "type";
  protected static final String ATTR_COUNTRYCODE = "countrycode";
  protected static final String ATTR_AREACODE = "areacode";
  protected static final String ATTR_LINE = "line";
  protected static final String ATTR_DIRECTDIAL = "directdial";

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final Object aObject,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final IReadonlyTelephoneNumber aTelNo = (IReadonlyTelephoneNumber) aObject;
    final IMicroElement eTelNo = new MicroElement (sNamespaceURI, sTagName);
    if (aTelNo.getType () != null)
      eTelNo.setAttribute (ATTR_TYPE, aTelNo.getType ().getID ());
    eTelNo.setAttribute (ATTR_COUNTRYCODE, aTelNo.getCountryCode ());
    eTelNo.setAttribute (ATTR_AREACODE, aTelNo.getAreaCode ());
    eTelNo.setAttribute (ATTR_LINE, aTelNo.getLine ());
    eTelNo.setAttribute (ATTR_DIRECTDIAL, aTelNo.getDirectDial ());
    return eTelNo;
  }
}