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

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Read-only interface of an address.
 * 
 * @author Philip Helger
 */
public interface IReadonlyAddress extends Serializable
{
  /**
   * @return The semantic type of this address.
   */
  @Nullable
  EAddressType getType ();

  /**
   * @return The country the address resides in. The uppercase two-letter
   *         ISO-3166 code as used by java.util.Locale.
   */
  @Nullable
  String getCountry ();

  /**
   * @return The locale representing the country. May be <code>null</code>.
   */
  @Nullable
  Locale getCountryLocale ();

  /**
   * Get the display name of the country using the passed display locale.
   * 
   * @param aDisplayLocale
   *        The locale in which the countries name should be retrieved.
   * @return <code>null</code> if no country is set
   */
  @Nullable
  String getCountryDisplayName (@Nonnull Locale aDisplayLocale);

  /**
   * @return The optional state within the country. May be <code>null</code>.
   */
  @Nullable
  String getState ();

  /**
   * @return The ZIP code representing the area within a state/country.
   */
  @Nullable
  String getPostalCode ();

  /**
   * @return The name of the city the address resides in.
   */
  @Nullable
  String getCity ();

  /**
   * @return The street (including the number) of the address.
   */
  @Nullable
  String getStreet ();

  /**
   * @return The number of the building in the street (if it is not contained in
   *         the street).
   */
  @Nullable
  String getBuildingNumber ();

  /**
   * @return An optional post office box that should be used instead the street.
   *         May be <code>null</code>.
   */
  @Nullable
  String getPostOfficeBox ();
}
