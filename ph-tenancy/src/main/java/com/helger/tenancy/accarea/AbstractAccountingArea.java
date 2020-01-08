/**
 * Copyright (C) 2014-2020 Philip Helger (www.helger.com)
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

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.state.EChange;
import com.helger.commons.string.ToStringGenerator;
import com.helger.masterdata.address.IPostalAddress;
import com.helger.masterdata.address.PostalAddress;
import com.helger.masterdata.currency.ECurrency;
import com.helger.tenancy.IBusinessObject;
import com.helger.tenancy.tenant.AbstractTenantObject;
import com.helger.tenancy.tenant.ITenant;

/**
 * Abstract base implementation of {@link IAccountingArea}
 *
 * @author Philip Helger
 */
@NotThreadSafe
public abstract class AbstractAccountingArea extends AbstractTenantObject implements IAccountingArea
{
  private String m_sDisplayName;
  private String m_sCompanyType;
  private String m_sCompanyVATIN;
  private String m_sCompanyNumber;
  private String m_sCustomerNumber;
  private PostalAddress m_aAddress;
  private String m_sTelephone;
  private String m_sFax;
  private String m_sEmailAddress;
  private String m_sWebSite;
  private ECurrency m_eDefaultCurrency;
  private String m_sOfficeLocation;
  private String m_sCommercialRegistrationNumber;
  private String m_sCommercialCourt;

  protected AbstractAccountingArea (@Nonnull final ITenant aTenant,
                                    @Nonnull final IBusinessObject aStubObject,
                                    @Nonnull @Nonempty final String sDisplayName,
                                    @Nullable final String sCompanyType,
                                    @Nullable final String sCompanyVATIN,
                                    @Nullable final String sCompanyNumber,
                                    @Nullable final String sCustomerNumber,
                                    @Nonnull final IPostalAddress aAddress,
                                    @Nullable final String sTelephone,
                                    @Nullable final String sFax,
                                    @Nullable final String sEmailAddress,
                                    @Nullable final String sWebSite,
                                    @Nullable final ECurrency eDefaultCurrency,
                                    @Nullable final String sOfficeLocation,
                                    @Nullable final String sCommercialRegistrationNumber,
                                    @Nullable final String sCommercialCourt,
                                    @Nonnull final Locale aDisplayLocale)
  {
    super (aTenant, aStubObject);
    setDisplayName (sDisplayName);
    setCompanyType (sCompanyType);
    setCompanyVATIN (sCompanyVATIN);
    setCompanyNumber (sCompanyNumber);
    setCustomerNumber (sCustomerNumber);
    setAddress (aAddress, aDisplayLocale);
    setTelephone (sTelephone);
    setFax (sFax);
    setEmailAddress (sEmailAddress);
    setWebSite (sWebSite);
    setDefaultCurrency (eDefaultCurrency);
    setOfficeLocation (sOfficeLocation);
    setCommercialRegistrationNumber (sCommercialRegistrationNumber);
    setCommercialCourt (sCommercialCourt);
  }

  @Nonnull
  @Nonempty
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  @Nonnull
  public EChange setDisplayName (@Nonnull @Nonempty final String sDisplayName)
  {
    ValueEnforcer.notEmpty (sDisplayName, "DisplayName");

    if (sDisplayName.equals (m_sDisplayName))
      return EChange.UNCHANGED;
    m_sDisplayName = sDisplayName;
    return EChange.CHANGED;
  }

  @Nullable
  public String getCompanyType ()
  {
    return m_sCompanyType;
  }

  @Nonnull
  public EChange setCompanyType (@Nullable final String sCompanyType)
  {
    if (EqualsHelper.equals (sCompanyType, m_sCompanyType))
      return EChange.UNCHANGED;
    m_sCompanyType = sCompanyType;
    return EChange.CHANGED;
  }

  @Nullable
  public String getCompanyVATIN ()
  {
    return m_sCompanyVATIN;
  }

  @Nonnull
  public EChange setCompanyVATIN (@Nullable final String sCompanyVATIN)
  {
    if (EqualsHelper.equals (sCompanyVATIN, m_sCompanyVATIN))
      return EChange.UNCHANGED;
    m_sCompanyVATIN = sCompanyVATIN;
    return EChange.CHANGED;
  }

  @Nullable
  public String getCompanyNumber ()
  {
    return m_sCompanyNumber;
  }

  @Nonnull
  public EChange setCompanyNumber (@Nullable final String sCompanyNumber)
  {
    if (EqualsHelper.equals (sCompanyNumber, m_sCompanyNumber))
      return EChange.UNCHANGED;
    m_sCompanyNumber = sCompanyNumber;
    return EChange.CHANGED;
  }

  @Nullable
  public String getCustomerNumber ()
  {
    return m_sCustomerNumber;
  }

  @Nonnull
  public EChange setCustomerNumber (@Nullable final String sCustomerNumber)
  {
    if (EqualsHelper.equals (sCustomerNumber, m_sCustomerNumber))
      return EChange.UNCHANGED;
    m_sCustomerNumber = sCustomerNumber;
    return EChange.CHANGED;
  }

  @Nonnull
  public IPostalAddress getAddress ()
  {
    return m_aAddress;
  }

  @Nonnull
  public EChange setAddress (@Nonnull final IPostalAddress aAddress, @Nonnull final Locale aDisplayLocale)
  {
    ValueEnforcer.notNull (aAddress, "Address");

    final PostalAddress aNewAddress = new PostalAddress (aAddress, aDisplayLocale);
    if (aNewAddress.equals (m_aAddress))
      return EChange.UNCHANGED;
    m_aAddress = aNewAddress;
    return EChange.CHANGED;
  }

  @Nullable
  public String getTelephone ()
  {
    return m_sTelephone;
  }

  @Nonnull
  public EChange setTelephone (@Nullable final String sTelephone)
  {
    if (EqualsHelper.equals (sTelephone, m_sTelephone))
      return EChange.UNCHANGED;
    m_sTelephone = sTelephone;
    return EChange.CHANGED;
  }

  @Nullable
  public String getFax ()
  {
    return m_sFax;
  }

  @Nonnull
  public EChange setFax (@Nullable final String sFax)
  {
    if (EqualsHelper.equals (sFax, m_sFax))
      return EChange.UNCHANGED;
    m_sFax = sFax;
    return EChange.CHANGED;
  }

  @Nullable
  public String getEmailAddress ()
  {
    return m_sEmailAddress;
  }

  @Nonnull
  public EChange setEmailAddress (@Nullable final String sEmailAddress)
  {
    if (EqualsHelper.equals (sEmailAddress, m_sEmailAddress))
      return EChange.UNCHANGED;
    m_sEmailAddress = sEmailAddress;
    return EChange.CHANGED;
  }

  @Nullable
  public String getWebSite ()
  {
    return m_sWebSite;
  }

  @Nonnull
  public EChange setWebSite (@Nullable final String sWebSite)
  {
    if (EqualsHelper.equals (sWebSite, m_sWebSite))
      return EChange.UNCHANGED;
    m_sWebSite = sWebSite;
    return EChange.CHANGED;
  }

  @Nullable
  public ECurrency getDefaultCurrency ()
  {
    return m_eDefaultCurrency;
  }

  @Nonnull
  public EChange setDefaultCurrency (@Nullable final ECurrency eDefaultCurrency)
  {
    if (EqualsHelper.equals (eDefaultCurrency, m_eDefaultCurrency))
      return EChange.UNCHANGED;
    m_eDefaultCurrency = eDefaultCurrency;
    return EChange.CHANGED;
  }

  @Nullable
  public String getOfficeLocation ()
  {
    return m_sOfficeLocation;
  }

  @Nonnull
  public EChange setOfficeLocation (@Nullable final String sOfficeLocation)
  {
    if (EqualsHelper.equals (sOfficeLocation, m_sOfficeLocation))
      return EChange.UNCHANGED;
    m_sOfficeLocation = sOfficeLocation;
    return EChange.CHANGED;
  }

  @Nullable
  public String getCommercialRegistrationNumber ()
  {
    return m_sCommercialRegistrationNumber;
  }

  @Nonnull
  public EChange setCommercialRegistrationNumber (@Nullable final String sCommercialRegistrationNumber)
  {
    if (EqualsHelper.equals (sCommercialRegistrationNumber, m_sCommercialRegistrationNumber))
      return EChange.UNCHANGED;
    m_sCommercialRegistrationNumber = sCommercialRegistrationNumber;
    return EChange.CHANGED;
  }

  @Nullable
  public String getCommercialCourt ()
  {
    return m_sCommercialCourt;
  }

  @Nonnull
  public EChange setCommercialCourt (@Nullable final String sCommercialCourt)
  {
    if (EqualsHelper.equals (sCommercialCourt, m_sCommercialCourt))
      return EChange.UNCHANGED;
    m_sCommercialCourt = sCommercialCourt;
    return EChange.CHANGED;
  }

  @Nonnull
  @Nonempty
  public String getAsUIText (final Locale aDisplayLocale)
  {
    return getTenant ().getAsUIText (aDisplayLocale) + " - " + getDisplayName ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("displayName", m_sDisplayName)
                            .appendIfNotNull ("companyType", m_sCompanyType)
                            .appendIfNotNull ("companyVATIN", m_sCompanyVATIN)
                            .appendIfNotNull ("companyNumber", m_sCompanyNumber)
                            .appendIfNotNull ("debtorNumber", m_sCustomerNumber)
                            .append ("address", m_aAddress)
                            .appendIfNotNull ("telephone", m_sTelephone)
                            .appendIfNotNull ("fax", m_sFax)
                            .appendIfNotNull ("emailAddress", m_sEmailAddress)
                            .appendIfNotNull ("website", m_sWebSite)
                            .appendIfNotNull ("defaultCurrency", m_eDefaultCurrency)
                            .appendIfNotNull ("officeLocation", m_sOfficeLocation)
                            .appendIfNotNull ("commercialRegistrationNumber", m_sCommercialRegistrationNumber)
                            .appendIfNotNull ("commercialCourt", m_sCommercialCourt)
                            .getToString ();
  }
}
