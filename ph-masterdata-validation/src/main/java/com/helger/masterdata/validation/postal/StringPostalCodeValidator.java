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
package com.helger.masterdata.validation.postal;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.string.StringHelper;
import com.helger.masterdata.postal.IPostalCodeCountry;
import com.helger.masterdata.postal.PostalCodeManager;
import com.helger.validation.result.IValidationResult;
import com.helger.validation.result.ValidationResultError;
import com.helger.validation.result.ValidationResultSuccess;
import com.helger.validation.validator.string.AbstractStringValidator;

/**
 * Postal code validator
 *
 * @author Philip Helger
 */
@Immutable
public class StringPostalCodeValidator extends AbstractStringValidator
{
  private static final Logger s_aLogger = LoggerFactory.getLogger (StringPostalCodeValidator.class);

  private final IPostalCodeCountry m_aPostalCountry;

  public StringPostalCodeValidator (@Nonnull final Locale aCountry)
  {
    this (PostalCodeManager.DEFAULT_MGR, aCountry);
  }

  public StringPostalCodeValidator (@Nonnull final PostalCodeManager aMgr, @Nonnull final Locale aCountry)
  {
    ValueEnforcer.notNull (aMgr, "Mgr");
    ValueEnforcer.notNull (aCountry, "Country");
    m_aPostalCountry = aMgr.getPostalCountryOfCountry (aCountry);
    if (m_aPostalCountry == null)
      s_aLogger.error ("No postal country information available for the passed country " + aCountry);
  }

  @Nonnull
  public IValidationResult validate (@Nullable final String sValue)
  {
    if (m_aPostalCountry == null)
    {
      // If no country information is available, it is considered to be valid
      return ValidationResultSuccess.getInstance ();
    }

    if (m_aPostalCountry.isValidPostalCode (sValue))
      return ValidationResultSuccess.getInstance ();

    // Error
    final String sValidExamples = StringHelper.getImploded (", ", m_aPostalCountry.getAllExamples ());
    if (StringHelper.hasText (sValidExamples))
    {
      final Object [] aArgs = { sValidExamples };
      return new ValidationResultError (EPostalCodeErrorTexts.INVALID_POSTAL_CODE_WITH_EXAMPLES, aArgs);
    }
    return new ValidationResultError (EPostalCodeErrorTexts.INVALID_POSTAL_CODE);
  }

  /**
   * Simple validation code.
   *
   * @param aCountry
   *        The code for which a postal code should be validated. May not be
   *        <code>null</code>.
   * @param sPostalCode
   *        The postal code to be validated. May be <code>null</code>.
   * @return Never <code>null</code>.
   */
  @Nonnull
  public static IValidationResult defaultValidate (@Nonnull final Locale aCountry, @Nullable final String sPostalCode)
  {
    return new StringPostalCodeValidator (aCountry).validate (sPostalCode);
  }
}
