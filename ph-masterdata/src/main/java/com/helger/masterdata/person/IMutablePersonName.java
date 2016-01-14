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

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.state.EChange;

/**
 * The name of a person.
 *
 * @author Philip Helger
 */
public interface IMutablePersonName extends IPersonName
{
  /**
   * @param eSalutation
   *        The salutation of this person. May be <code>null</code>.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setSalutation (@Nullable ESalutation eSalutation);

  /**
   * @param sPrefixTitle
   *        An optional title that is written before the name. E.g. "Dr." in
   *        Germany.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setPrefixTitle (@Nullable String sPrefixTitle);

  /**
   * @param sFirstName
   *        The first name.
   * @param aSortLocale
   *        locale to use.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setFirstName (@Nullable String sFirstName, @Nonnull Locale aSortLocale);

  /**
   * @param sMiddleName
   *        The optional middle name.
   * @param aSortLocale
   *        locale to use.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setMiddleName (@Nullable String sMiddleName, @Nonnull Locale aSortLocale);

  /**
   * @param sLastName
   *        The last name. May not be <code>null</code>.
   * @param aSortLocale
   *        locale to use.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setLastName (@Nullable String sLastName, @Nonnull Locale aSortLocale);

  /**
   * @param sSuffixTitle
   *        An optional title that is written after the name. E.g. "MBA" in
   *        Austria.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setSuffixTitle (@Nullable String sSuffixTitle);

  /**
   * {@inheritDoc}
   */
  @Nonnull
  IMutablePersonName getClone ();
}
