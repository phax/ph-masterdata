/**
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
package com.helger.masterdata.person;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.ICloneable;

/**
 * The name of a person.
 * 
 * @author Philip Helger
 */
public interface IReadonlyPersonName extends ICloneable <IReadonlyPersonName>, Serializable
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
  String getSalutationID ();

  @Nullable
  String getSalutationDisplayName (@Nonnull Locale aContentLocale);

  @Nullable
  String getGreeting (@Nonnull Locale aContentLocale);

  @Nullable
  String getGreetingComplete (@Nonnull Locale aContentLocale);

  /**
   * @return An optional title that is written before the name. E.g. "Dr." in
   *         Germany.
   */
  @Nullable
  String getPrefixTitle ();

  /**
   * @return The first name.
   */
  @Nullable
  String getFirstName ();

  /**
   * @return The optional middle name.
   */
  @Nullable
  String getMiddleName ();

  /**
   * @return The last name. May not be <code>null</code>.
   */
  @Nullable
  String getLastName ();

  /**
   * @return An optional title that is written after the name. E.g. "MBA" in
   *         Austria.
   */
  @Nullable
  String getSuffixTitle ();
}
