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
package com.helger.masterdata.company;

import java.util.Collection;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.type.ITypedObject;

/**
 * Base interface representing a read-only company.
 * 
 * @author Philip Helger
 */
public interface IReadonlyCompany extends ITypedObject <String>
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
  Collection <? extends IReadonlyCompanySite> getAllSites ();

  /**
   * Get the site with the given ID.
   * 
   * @param sSiteID
   *        The site ID to search. May be <code>null</code>.
   * @return <code>null</code> if no such site exists.
   */
  @Nullable
  IReadonlyCompanySite getSiteOfID (@Nullable String sSiteID);

  /**
   * @return A collection of all non-virtual sites belonging to this company.
   */
  @Nonnull
  @ReturnsMutableCopy
  Collection <? extends IReadonlyCompanySite> getAllNonVirtualSites ();

  /**
   * @return A collection of all virtual sites belonging to this company.
   */
  @Nonnull
  @ReturnsMutableCopy
  Collection <? extends IReadonlyCompanySite> getAllVirtualSites ();

  /**
   * @return The head quarter site of this company. May be <code>null</code> if
   *         not a single head quarter is available.
   */
  @Nullable
  IReadonlyCompanySite getHeadQuarterSite ();

  /**
   * @return <code>true</code> if at least one site has the "deletable" flag set
   *         to false
   */
  boolean containsAtLeastOneNotDeletableSite ();
}
