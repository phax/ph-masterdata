/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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
package com.helger.tenancy.accarea;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.name.IHasDisplayName;
import com.helger.commons.string.StringHelper;
import com.helger.masterdata.address.IPostalAddress;
import com.helger.masterdata.currency.ECurrency;
import com.helger.tenancy.tenant.ITenantObject;
import com.helger.tenancy.uitext.IHasUIText;

/**
 * This interface represents a single accounting area.
 *
 * @author Philip Helger
 */
public interface IAccountingArea extends ITenantObject, IHasDisplayName, IHasUIText
{
  /**
   * @return The company type. E.g. "KEG"
   */
  @Nullable
  String getCompanyType ();

  default boolean hasCompanyType ()
  {
    return StringHelper.hasText (getCompanyType ());
  }

  @Nonnull
  default String getDisplayNameAndCompanyType ()
  {
    return getDisplayNameAndCompanyType (" ");
  }

  @Nonnull
  default String getDisplayNameAndCompanyType (@Nonnull final String sSep)
  {
    return StringHelper.getImplodedNonEmpty (sSep, getDisplayName (), getCompanyType ());
  }

  /**
   * @return The company UID.
   */
  @Nullable
  String getCompanyVATIN ();

  default boolean hasCompanyVATIN ()
  {
    return StringHelper.hasText (getCompanyVATIN ());
  }

  /**
   * @return The company number (Interne Betriebsnummer). May not be
   *         <code>null</code>.
   */
  @Nullable
  String getCompanyNumber ();

  default boolean hasCompanyNumber ()
  {
    return StringHelper.hasText (getCompanyNumber ());
  }

  /**
   * @return Optional customer number. May be <code>null</code>.
   */
  @Nullable
  String getCustomerNumber ();

  default boolean hasCustomerNumber ()
  {
    return StringHelper.hasText (getCustomerNumber ());
  }

  /**
   * @return The address of the owner. May not be <code>null</code>.
   */
  @Nonnull
  IPostalAddress getAddress ();

  /**
   * @return The telephone number. May be <code>null</code>.
   */
  @Nullable
  String getTelephone ();

  default boolean hasTelephone ()
  {
    return StringHelper.hasText (getTelephone ());
  }

  /**
   * @return The telefax number. May be <code>null</code>.
   */
  @Nullable
  String getFax ();

  default boolean hasFax ()
  {
    return StringHelper.hasText (getFax ());
  }

  /**
   * @return The email address. May be <code>null</code>.
   */
  @Nullable
  String getEmailAddress ();

  default boolean hasEmailAddress ()
  {
    return StringHelper.hasText (getEmailAddress ());
  }

  /**
   * @return The web site. May be <code>null</code>.
   */
  @Nullable
  String getWebSite ();

  default boolean hasWebSite ()
  {
    return StringHelper.hasText (getWebSite ());
  }

  /**
   * @return The default currency. May be <code>null</code>.
   */
  @Nullable
  ECurrency getDefaultCurrency ();

  /**
   * @return The default currency ID. May be <code>null</code>.
   */
  @Nullable
  String getDefaultCurrencyID ();

  default boolean hasDefaultCurrency ()
  {
    return getDefaultCurrency () != null;
  }

  /**
   * @return Office location (Firmensitz)
   */
  @Nullable
  String getOfficeLocation ();

  default boolean hasOfficeLocation ()
  {
    return StringHelper.hasText (getOfficeLocation ());
  }

  /**
   * @return Commercial registration number (e.g. Firmenbuchnummer)
   */
  @Nullable
  String getCommercialRegistrationNumber ();

  default boolean hasCommercialRegistrationNumber ()
  {
    return StringHelper.hasText (getCommercialRegistrationNumber ());
  }

  /**
   * @return Commercial court(e.g. Firmenbuchgericht)
   */
  @Nullable
  String getCommercialCourt ();

  default boolean hasCommercialCourt ()
  {
    return StringHelper.hasText (getCommercialCourt ());
  }
}
