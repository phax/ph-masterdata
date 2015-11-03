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
package com.helger.masterdata.person;

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joda.time.LocalDate;

import com.helger.commons.id.IHasID;
import com.helger.masterdata.address.IAddress;
import com.helger.masterdata.email.IExtendedEmailAddress;
import com.helger.masterdata.telephone.ITelephoneNumber;

/**
 * Read only person interface
 * 
 * @author Philip Helger
 */
public interface IPerson extends IHasID <String>, Serializable
{
  /**
   * @return The gender of the person. May be <code>null</code>.
   */
  @Nullable
  EGender getGender ();

  /**
   * @return The name of the person. May not be <code>null</code>.
   */
  @Nonnull
  IPersonName getName ();

  /**
   * @return The optional birthday of the person.
   */
  @Nullable
  LocalDate getBirthday ();

  /**
   * @return An optional telephone number.
   */
  @Nullable
  ITelephoneNumber getTelephoneNumber ();

  /**
   * @return An optional email address for this person.
   */
  @Nullable
  IExtendedEmailAddress getEmailAddress ();

  /**
   * @return An optional real address for this person.
   */
  @Nullable
  IAddress getAddress ();
}
