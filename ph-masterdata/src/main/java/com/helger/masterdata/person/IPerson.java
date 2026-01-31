/*
 * Copyright (C) 2014-2026 Philip Helger (www.helger.com)
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

import java.time.LocalDate;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.masterdata.address.IPostalAddress;
import com.helger.masterdata.email.IExtendedEmailAddress;
import com.helger.masterdata.telephone.ITelephoneNumber;

/**
 * Read only person interface
 *
 * @author Philip Helger
 */
public interface IPerson
{
  /**
   * @return The gender of the person. May be <code>null</code>.
   */
  @Nullable
  EGender getGender ();

  default boolean hasGender ()
  {
    return getGender () != null;
  }

  /**
   * @return The name of the person. May not be <code>null</code>.
   */
  @NonNull
  IPersonName getName ();

  /**
   * @return The optional birthday of the person.
   */
  @Nullable
  LocalDate getBirthday ();

  default boolean hasBirthday ()
  {
    return getBirthday () != null;
  }

  /**
   * @return An optional telephone number.
   */
  @Nullable
  ITelephoneNumber getTelephoneNumber ();

  default boolean hasTelephoneNumber ()
  {
    return getTelephoneNumber () != null;
  }

  /**
   * @return An optional email address for this person.
   */
  @Nullable
  IExtendedEmailAddress getEmailAddress ();

  default boolean hasEmailAddress ()
  {
    return getEmailAddress () != null;
  }

  /**
   * @return An optional real address for this person.
   */
  @Nullable
  IPostalAddress getAddress ();

  default boolean hasAddress ()
  {
    return getAddress () != null;
  }
}
