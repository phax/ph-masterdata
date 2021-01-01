/**
 * Copyright (C) 2014-2021 Philip Helger (www.helger.com)
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
import java.util.function.BiFunction;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Enum to easily access parts of an address
 * 
 * @author Philip Helger
 */
public enum EPostalAddressField
{
  CARE_OF ( (a, dl) -> a.hasCareOf () ? PostalAddressHelper.getCareOfPrefix () + a.getCareOf () : null),
  STREET ( (a, dl) -> a.hasStreet () ? a.getStreet () : null),
  BUILDING_NUMBER ( (a, dl) -> a.hasBuildingNumber () ? a.getBuildingNumber () : null),
  STREET_AND_BUILDING_NUMBER ( (a, dl) -> PostalAddressHelper.getStreetAndBuildingNumber (a)),
  POSTAL_CODE ( (a, dl) -> a.hasPostalCode () ? a.getPostalCode () : null),
  CITY ( (a, dl) -> a.hasCity () ? a.getCity () : null),
  POSTAL_CODE_AND_CITY ( (a, dl) -> PostalAddressHelper.getPostalCodeAndCity (a)),
  POST_OFFICE_BOX ( (a, dl) -> a.hasPostOfficeBox () ? a.getPostOfficeBox () : null),
  STATE ( (a, dl) -> a.hasState () ? a.getState () : null),
  COUNTRY ( (a, dl) -> a.hasCountry () ? a.getCountryDisplayName (dl) : null);

  private final BiFunction <IPostalAddress, Locale, String> m_aGetter;

  private EPostalAddressField (@Nonnull final BiFunction <IPostalAddress, Locale, String> aGetter)
  {
    m_aGetter = aGetter;
  }

  @Nullable
  public String get (@Nonnull final IPostalAddress aAddress, @Nonnull final Locale aDisplayLocale)
  {
    return m_aGetter.apply (aAddress, aDisplayLocale);
  }
}
