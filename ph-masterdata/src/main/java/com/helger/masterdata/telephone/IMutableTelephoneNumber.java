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

import com.helger.commons.lang.ICloneable;
import com.helger.commons.state.EChange;

/**
 * Base interface for a telephone number.
 *
 * @author Philip Helger
 */
public interface IMutableTelephoneNumber extends ITelephoneNumber, ICloneable <IMutableTelephoneNumber>
{
  /**
   * @param aType
   *        The semantic type of this telephone number.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setType (@Nullable ITelephoneType aType);

  /**
   * @param sCountryCode
   *        The country where the number resides.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setCountryCode (@Nullable String sCountryCode);

  /**
   * @param sAreaCode
   *        The area code for the phone number. This is country dependent.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setAreaCode (@Nullable String sAreaCode);

  /**
   * @param sLine
   *        The main telephone number within an area code.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setLine (@Nullable String sLine);

  /**
   * @param sDirectDial
   *        The direct dial for a further specification of a line. Is optional
   *        and may be <code>null</code>.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setDirectDial (@Nullable String sDirectDial);
}
