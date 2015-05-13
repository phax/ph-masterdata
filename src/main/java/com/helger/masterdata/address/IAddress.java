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
package com.helger.masterdata.address;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ICloneable;
import com.helger.commons.state.EChange;

/**
 * The writable version of the address interface.
 *
 * @author Philip Helger
 */
public interface IAddress extends IReadonlyAddress, ICloneable <IAddress>
{
  /**
   * @param eAddressType
   *        The semantic type of this address.
   * @return {@link EChange#CHANGED} if something changed,
   *         {@link EChange#UNCHANGED} otherwise.
   */
  @Nonnull
  EChange setType (@Nullable EAddressType eAddressType);

  /**
   * @param aCountry
   *        The country the address resides in.
   * @param aSortLocale
   *        The locale for processing. May not be <code>null</code>.
   * @return {@link EChange#CHANGED} if something changed,
   *         {@link EChange#UNCHANGED} otherwise.
   */
  @Nonnull
  EChange setCountry (@Nullable Locale aCountry, @Nonnull Locale aSortLocale);

  /**
   * @param sCountry
   *        The country the address resides in. The uppercase two-letter
   *        ISO-3166 code as used by java.util.Locale.
   * @param aSortLocale
   *        The locale for processing. May not be <code>null</code>.
   * @return {@link EChange#CHANGED} if something changed,
   *         {@link EChange#UNCHANGED} otherwise.
   */
  @Nonnull
  EChange setCountry (@Nullable String sCountry, @Nonnull Locale aSortLocale);

  /**
   * @param sState
   *        The optional state within the country. May be <code>null</code>.
   * @param aSortLocale
   *        The locale for processing. May not be <code>null</code>.
   * @return {@link EChange#CHANGED} if something changed,
   *         {@link EChange#UNCHANGED} otherwise.
   */
  @Nonnull
  EChange setState (@Nullable String sState, @Nonnull Locale aSortLocale);

  /**
   * @param sZipCode
   *        The ZIP code representing the area within a state/country.
   * @return {@link EChange#CHANGED} if something changed,
   *         {@link EChange#UNCHANGED} otherwise.
   */
  @Nonnull
  EChange setPostalCode (@Nullable String sZipCode);

  /**
   * @param sCity
   *        The name of the city the address resides in.
   * @param aSortLocale
   *        The locale for processing. May not be <code>null</code>.
   * @return {@link EChange#CHANGED} if something changed,
   *         {@link EChange#UNCHANGED} otherwise.
   */
  @Nonnull
  EChange setCity (@Nullable String sCity, @Nonnull Locale aSortLocale);

  /**
   * @param sStreet
   *        The street (including the number) of the address.
   * @param aSortLocale
   *        The locale for processing. May not be <code>null</code>.
   * @return {@link EChange#CHANGED} if something changed,
   *         {@link EChange#UNCHANGED} otherwise.
   */
  @Nonnull
  EChange setStreet (@Nullable String sStreet, @Nonnull Locale aSortLocale);

  /**
   * @param sBuildingNumber
   *        The number of the building in the street (if it is not contained in
   *        the street).
   * @return {@link EChange#CHANGED} if something changed,
   *         {@link EChange#UNCHANGED} otherwise.
   */
  @Nonnull
  EChange setBuildingNumber (@Nullable String sBuildingNumber);

  /**
   * @param sPOBox
   *        An optional post office box that should be used instead the street.
   *        May be <code>null</code>.
   * @param aSortLocale
   *        The locale for processing. May not be <code>null</code>.
   * @return {@link EChange#CHANGED} if something changed,
   *         {@link EChange#UNCHANGED} otherwise.
   */
  @Nonnull
  EChange setPostOfficeBox (@Nullable String sPOBox, @Nonnull Locale aSortLocale);
}
