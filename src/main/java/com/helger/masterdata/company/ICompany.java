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
package com.helger.masterdata.company;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.state.EChange;

public interface ICompany extends IReadonlyCompany
{
  /**
   * Set the name of the company and how it is known. E.g. "IBM".
   * 
   * @param sPublicName
   *        The new name
   * @return {@link EChange}
   */
  @Nonnull
  EChange setPublicName (@Nullable String sPublicName);

  /**
   * Set the official name of the company like
   * "International Business Machines Corp.".
   * 
   * @param sOfficialName
   *        The new name
   * @return {@link EChange}
   */
  @Nonnull
  EChange setOfficialName (@Nullable String sOfficialName);

  /**
   * {@inheritDoc}
   */
  @Nonnull
  Collection <ICompanySite> getAllSites ();

  @Nonnull
  EChange addSite (@Nonnull ICompanySite aSite);

  @Nonnull
  EChange removeSite (@Nonnull ICompanySite aSite);

  /**
   * {@inheritDoc}
   */
  @Nullable
  ICompanySite getSiteOfID (@Nullable String sSiteID);

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @ReturnsMutableCopy
  Collection <? extends ICompanySite> getAllNonVirtualSites ();

  /**
   * {@inheritDoc}
   */
  @Nonnull
  @ReturnsMutableCopy
  Collection <? extends ICompanySite> getAllVirtualSites ();

  /**
   * {@inheritDoc}
   */
  @Nullable
  ICompanySite getHeadQuarterSite ();

  /**
   * Set the head quarter site. The passed site must already be part of the
   * overall site list.
   * 
   * @param aSite
   *        The site to be marked as head quarter.
   * @return {@link EChange}
   */
  @Nonnull
  EChange setHeadQuarterSite (@Nonnull ICompanySite aSite);
}
