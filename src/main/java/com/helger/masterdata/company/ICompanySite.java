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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.state.EChange;
import com.helger.masterdata.address.IAddress;
import com.helger.masterdata.email.IExtendedEmailAddress;
import com.helger.masterdata.telephone.ITelephoneNumber;

/**
 * Represents a single location of a company.
 * 
 * @author Philip Helger
 */
public interface ICompanySite extends IReadonlyCompanySite
{
  /**
   * {@inheritDoc}
   */
  @Nonnull
  ICompany getCompany ();

  @Nonnull
  EChange setDisplayName (@Nullable String sDisplayName);

  @Nonnull
  EChange setLongName (@Nullable String sLongName);

  @Nonnull
  EChange setDeletable (boolean bIsDeletable);

  @Nonnull
  EChange setVirtualSite (boolean bIsVirtualSite);

  /**
   * {@inheritDoc}
   */
  @Nonnull
  IAddress getAddress ();

  @Nonnull
  EChange setAddress (@Nonnull IAddress aAddress);

  /**
   * {@inheritDoc}
   */
  @Nonnull
  ITelephoneNumber getDefaultTelNo ();

  @Nonnull
  EChange setDefaultTelNo (@Nonnull ITelephoneNumber aTelNo);

  /**
   * {@inheritDoc}
   */
  @Nonnull
  ITelephoneNumber getDefaultFaxNo ();

  @Nonnull
  EChange setDefaultFaxNo (@Nonnull ITelephoneNumber aFaxNo);

  /**
   * {@inheritDoc}
   */
  @Nonnull
  IExtendedEmailAddress getDefaultEmailAddress ();

  @Nonnull
  EChange setDefaultEmailAddress (@Nonnull IExtendedEmailAddress aEmailAddress);
}
