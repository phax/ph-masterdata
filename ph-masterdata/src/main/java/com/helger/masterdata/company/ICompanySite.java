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

import com.helger.commons.name.IHasDisplayName;
import com.helger.commons.type.ITypedObject;
import com.helger.masterdata.address.IAddress;
import com.helger.masterdata.email.IExtendedEmailAddress;
import com.helger.masterdata.telephone.ITelephoneNumber;

/**
 * Represents a single location of a company.
 *
 * @author Philip Helger
 */
public interface ICompanySite extends IHasDisplayName, ITypedObject <String>
{
  /**
   * @return The company to which the site belongs
   */
  @Nonnull
  ICompany getCompany ();

  /**
   * @return The long name of the company site, e.g. containing the city or
   *         similar stuff.
   */
  @Nullable
  String getLongName ();

  /**
   * @return <code>false</code> if this site is undeletable, because it is
   *         required by business logic!
   */
  boolean isDeletable ();

  /**
   * @return <code>true</code> if it is a virtual site (e.g. a WebShop) or
   *         <code>false</code> if it is a real site.
   */
  boolean isVirtualSite ();

  /**
   * @return The address of this company site.
   */
  @Nonnull
  IAddress getAddress ();

  /**
   * @return The default telephone number of the company site.
   */
  @Nonnull
  ITelephoneNumber getDefaultTelNo ();

  /**
   * @return The default fax number of the company site.
   */
  @Nonnull
  ITelephoneNumber getDefaultFaxNo ();

  /**
   * @return The default email address of the company site.
   */
  @Nonnull
  IExtendedEmailAddress getDefaultEmailAddress ();
}
