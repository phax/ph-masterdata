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
package com.helger.masterdata.person;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.lang.ICloneable;
import com.helger.commons.string.StringHelper;

/**
 * The name of a person.
 *
 * @author Philip Helger
 */
public interface IPersonName extends ICloneable <IPersonName>, Serializable
{
  /**
   * @return The salutation of this name. May be <code>null</code>.
   */
  @Nullable
  ESalutation getSalutation ();

  /**
   * @return The ID of the salutation of this name. May be <code>null</code>.
   */
  @Nullable
  default String getSalutationID ()
  {
    final ESalutation e = getSalutation ();
    return e == null ? null : e.getID ();
  }

  @Nullable
  default String getSalutationDisplayName (@Nonnull final Locale aContentLocale)
  {
    final ESalutation e = getSalutation ();
    return e == null ? null : e.getDisplayText (aContentLocale);
  }

  @Nullable
  default String getGreeting (@Nonnull final Locale aContentLocale)
  {
    final ESalutation e = getSalutation ();
    return e == null ? null : e.getGreeting (aContentLocale);
  }

  @Nullable
  default String getGreetingComplete (@Nonnull final Locale aContentLocale)
  {
    final ESalutation e = getSalutation ();
    return e == null ? null : e.getGreetingComplete (aContentLocale);
  }

  /**
   * @return An optional title that is written before the name. E.g. "Dr." in
   *         Germany.
   */
  @Nullable
  String getPrefixTitle ();

  default boolean hasPrefixTitle ()
  {
    return StringHelper.hasText (getPrefixTitle ());
  }

  /**
   * @return The first name.
   */
  @Nullable
  String getFirstName ();

  default boolean hasFirstName ()
  {
    return StringHelper.hasText (getFirstName ());
  }

  /**
   * @return The optional middle name.
   */
  @Nullable
  String getMiddleName ();

  default boolean hasMiddleName ()
  {
    return StringHelper.hasText (getMiddleName ());
  }

  /**
   * @return The last name. May not be <code>null</code>.
   */
  @Nullable
  String getLastName ();

  default boolean hasLastName ()
  {
    return StringHelper.hasText (getLastName ());
  }

  /**
   * @return An optional title that is written after the name. E.g. "MBA" in
   *         Austria.
   */
  @Nullable
  String getSuffixTitle ();

  default boolean hasSuffixTitle ()
  {
    return StringHelper.hasText (getSuffixTitle ());
  }
}
