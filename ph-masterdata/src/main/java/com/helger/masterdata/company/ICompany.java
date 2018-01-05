/**
 * Copyright (C) 2014-2018 Philip Helger (www.helger.com)
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
package com.helger.masterdata.company;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.ICommonsCollection;
import com.helger.commons.type.ITypedObject;

/**
 * Base interface representing a read-only company.
 *
 * @author Philip Helger
 */
public interface ICompany extends ITypedObject <String>
{
  /**
   * @return The name of the company and how it is known. E.g. "IBM".
   */
  @Nullable
  String getPublicName ();

  /**
   * @return The official name of the company like
   *         "International Business Machines Corp.".
   */
  @Nullable
  String getOfficialName ();

  /**
   * @return The number of all (virtual and non-virtual) sites.
   */
  @Nonnegative
  int getSiteCount ();

  /**
   * @return A collection of all sites belonging to this company. Includes both
   *         virtual and non-virtual sites.
   */
  @Nonnull
  @ReturnsMutableCopy
  ICommonsCollection <? extends ICompanySite> getAllSites ();

  /**
   * Get the site with the given ID.
   *
   * @param sSiteID
   *        The site ID to search. May be <code>null</code>.
   * @return <code>null</code> if no such site exists.
   */
  @Nullable
  ICompanySite getSiteOfID (@Nullable String sSiteID);

  /**
   * @return A collection of all non-virtual sites belonging to this company.
   */
  @Nonnull
  @ReturnsMutableCopy
  ICommonsCollection <? extends ICompanySite> getAllNonVirtualSites ();

  /**
   * @return A collection of all virtual sites belonging to this company.
   */
  @Nonnull
  @ReturnsMutableCopy
  ICommonsCollection <? extends ICompanySite> getAllVirtualSites ();

  /**
   * @return The head quarter site of this company. May be <code>null</code> if
   *         not a single head quarter is available.
   */
  @Nullable
  ICompanySite getHeadQuarterSite ();

  /**
   * @return <code>true</code> if at least one site has the "deletable" flag set
   *         to false
   */
  boolean containsAtLeastOneNotDeletableSite ();
}
