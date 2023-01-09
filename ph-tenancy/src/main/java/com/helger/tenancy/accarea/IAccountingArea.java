/*
 * Copyright (C) 2014-2023 Philip Helger (www.helger.com)
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
   * @return The company type. E.g. "KEG". May be <code>null</code>.
   */
  @Nullable
  String getCompanyType ();

  /**
   * @return <code>true</code> if a company type is present, <code>false</code>
   *         if not.
   * @see #getCompanyType()
   */
  default boolean hasCompanyType ()
  {
    return StringHelper.hasText (getCompanyType ());
  }

  /**
   * @return The same as {@link #getDisplayNameAndCompanyType(String)} but using
   *         the fixed separator " " (1 blank).
   * @see #getDisplayNameAndCompanyType(String)
   */
  @Nonnull
  default String getDisplayNameAndCompanyType ()
  {
    return getDisplayNameAndCompanyType (" ");
  }

  /**
   * Get display name and company type concatenated using the provided
   * separator. The separator is only used, if both fields are present and not
   * empty.
   *
   * @param sSep
   *        The separator to use. May not be <code>null</code>.
   * @return The non-<code>null</code> merged string.
   * @see #getDisplayName()
   * @see #getCompanyType()
   */
  @Nonnull
  default String getDisplayNameAndCompanyType (@Nonnull final String sSep)
  {
    return StringHelper.getImplodedNonEmpty (sSep, getDisplayName (), getCompanyType ());
  }

  /**
   * @return The company VATID (de: UID). May be <code>null</code>
   */
  @Nullable
  String getCompanyVATIN ();

  /**
   * @return <code>true</code> if a VATIN is present, <code>false</code> if not.
   * @see #getCompanyVATIN()
   */
  default boolean hasCompanyVATIN ()
  {
    return StringHelper.hasText (getCompanyVATIN ());
  }

  /**
   * @return The company number (Interne Betriebsnummer). May be
   *         <code>null</code>.
   */
  @Nullable
  String getCompanyNumber ();

  /**
   * @return <code>true</code> if a company number is present,
   *         <code>false</code> if not.
   * @see #getCompanyNumber()
   */
  default boolean hasCompanyNumber ()
  {
    return StringHelper.hasText (getCompanyNumber ());
  }

  /**
   * @return Customer number. May be <code>null</code>.
   */
  @Nullable
  String getCustomerNumber ();

  /**
   * @return <code>true</code> if a customer number is present,
   *         <code>false</code> if not.
   * @see #getCustomerNumber()
   */
  default boolean hasCustomerNumber ()
  {
    return StringHelper.hasText (getCustomerNumber ());
  }

  /**
   * @return The postal address of the owner. May not be <code>null</code>.
   */
  @Nonnull
  IPostalAddress getAddress ();

  /**
   * @return The telephone number. May be <code>null</code>.
   */
  @Nullable
  String getTelephone ();

  /**
   * @return <code>true</code> if a telephone number is present,
   *         <code>false</code> if not.
   * @see #getTelephone()
   */
  default boolean hasTelephone ()
  {
    return StringHelper.hasText (getTelephone ());
  }

  /**
   * @return The telefax number. May be <code>null</code>.
   */
  @Nullable
  String getFax ();

  /**
   * @return <code>true</code> if a fax number is present, <code>false</code> if
   *         not.
   * @see #getFax()
   */
  default boolean hasFax ()
  {
    return StringHelper.hasText (getFax ());
  }

  /**
   * @return The email address. May be <code>null</code>.
   */
  @Nullable
  String getEmailAddress ();

  /**
   * @return <code>true</code> if an email address is present,
   *         <code>false</code> if not.
   * @see #getEmailAddress()
   */
  default boolean hasEmailAddress ()
  {
    return StringHelper.hasText (getEmailAddress ());
  }

  /**
   * @return The web site. May be <code>null</code>.
   */
  @Nullable
  String getWebSite ();

  /**
   * @return <code>true</code> if a website is present, <code>false</code> if
   *         not.
   * @see #getWebSite()
   */
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
   * @see #getDefaultCurrency()
   */
  @Nullable
  default String getDefaultCurrencyID ()
  {
    final ECurrency eCurrency = getDefaultCurrency ();
    return eCurrency == null ? null : eCurrency.getID ();
  }

  /**
   * @return <code>true</code> if a default currency is present,
   *         <code>false</code> if not.
   * @see #getDefaultCurrency()
   */
  default boolean hasDefaultCurrency ()
  {
    return getDefaultCurrency () != null;
  }

  /**
   * @return Office location (Firmensitz). May be <code>null</code>.
   */
  @Nullable
  String getOfficeLocation ();

  /**
   * @return <code>true</code> if an office location is present,
   *         <code>false</code> if not.
   * @see #getOfficeLocation()
   */
  default boolean hasOfficeLocation ()
  {
    return StringHelper.hasText (getOfficeLocation ());
  }

  /**
   * @return Commercial registration number (e.g. Firmenbuchnummer). May be
   *         <code>null</code>.
   */
  @Nullable
  String getCommercialRegistrationNumber ();

  /**
   * @return <code>true</code> if a commercial register number is present,
   *         <code>false</code> if not.
   * @see #getCommercialRegistrationNumber()
   */
  default boolean hasCommercialRegistrationNumber ()
  {
    return StringHelper.hasText (getCommercialRegistrationNumber ());
  }

  /**
   * @return Commercial court(e.g. Firmenbuchgericht). May be <code>null</code>.
   */
  @Nullable
  String getCommercialCourt ();

  /**
   * @return <code>true</code> if a commercial court is present,
   *         <code>false</code> if not.
   * @see #getCommercialCourt()
   */
  default boolean hasCommercialCourt ()
  {
    return StringHelper.hasText (getCommercialCourt ());
  }
}
