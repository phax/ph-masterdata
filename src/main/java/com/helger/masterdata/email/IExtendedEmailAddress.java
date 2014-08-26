/**
 * Copyright (C) 2006-2014 phloc systems
 * http://www.phloc.com
 * office[at]phloc[dot]com
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
package com.helger.masterdata.email;

import javax.annotation.Nonnull;

import com.helger.commons.ICloneable;
import com.helger.commons.state.EChange;

/**
 * Extended interface for an email address with a type.
 * 
 * @author Philip Helger
 */
public interface IExtendedEmailAddress extends IReadonlyExtendedEmailAddress, ICloneable <IExtendedEmailAddress>
{
  /**
   * @param eType
   *        The type of the email address. May be <code>null</code>.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setType (EEmailAddressType eType);

  /**
   * @param sAddress
   *        The main email address. May be <code>null</code>.
   * @return <code>{@link EChange#CHANGED}</code> if the passed email address
   *         was valid and set, <code>{@link EChange#UNCHANGED}</code>
   *         otherwise.
   */
  @Nonnull
  EChange setAddress (String sAddress);

  /**
   * @param sPersonal
   *        The personal name. May be <code>null</code>.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setPersonal (String sPersonal);
}
