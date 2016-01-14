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
package com.helger.masterdata.company;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.state.EChange;
import com.helger.masterdata.address.IMutableAddress;
import com.helger.masterdata.email.IMutableExtendedEmailAddress;
import com.helger.masterdata.telephone.IMutableTelephoneNumber;

/**
 * Represents a single location of a company.
 *
 * @author Philip Helger
 */
public interface IMutableCompanySite extends ICompanySite
{
  /**
   * {@inheritDoc}
   */
  @Nonnull
  IMutableCompany getCompany ();

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
  IMutableAddress getAddress ();

  @Nonnull
  EChange setAddress (@Nonnull IMutableAddress aAddress);

  /**
   * {@inheritDoc}
   */
  @Nonnull
  IMutableTelephoneNumber getDefaultTelNo ();

  @Nonnull
  EChange setDefaultTelNo (@Nonnull IMutableTelephoneNumber aTelNo);

  /**
   * {@inheritDoc}
   */
  @Nonnull
  IMutableTelephoneNumber getDefaultFaxNo ();

  @Nonnull
  EChange setDefaultFaxNo (@Nonnull IMutableTelephoneNumber aFaxNo);

  /**
   * {@inheritDoc}
   */
  @Nonnull
  IMutableExtendedEmailAddress getDefaultEmailAddress ();

  @Nonnull
  EChange setDefaultEmailAddress (@Nonnull IMutableExtendedEmailAddress aEmailAddress);
}
