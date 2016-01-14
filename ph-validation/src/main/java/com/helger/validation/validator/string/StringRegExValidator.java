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
package com.helger.validation.validator.string;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.RegEx;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.text.display.IHasDisplayText;
import com.helger.validation.EStandardValidationErrorTexts;
import com.helger.validation.result.IValidationResult;
import com.helger.validation.result.ValidationResultError;
import com.helger.validation.result.ValidationResultSuccess;

/**
 * Checks string values whether they match a certain regular expression.
 *
 * @author Philip Helger
 */
@Immutable
public class StringRegExValidator extends AbstractStringValidator
{
  private final String m_sRegEx;
  private final IHasDisplayText m_aErrorText;

  public StringRegExValidator (@Nonnull @Nonempty @RegEx final String sRegEx)
  {
    this (sRegEx, null);
  }

  /**
   * Constructor with custom error message.
   *
   * @param sRegEx
   *        Regular expression to use. May neither be <code>null</code> nor
   *        empty.
   * @param aErrorText
   *        Optional error text. May be <code>null</code>.
   */
  public StringRegExValidator (@Nonnull @Nonempty @RegEx final String sRegEx,
                               @Nullable final IHasDisplayText aErrorText)
  {
    ValueEnforcer.notEmpty (sRegEx, "RegEx");
    m_sRegEx = sRegEx;
    m_aErrorText = aErrorText;
  }

  @Nonnull
  @Nonempty
  @RegEx
  public String getRegEx ()
  {
    return m_sRegEx;
  }

  @Nonnull
  public IValidationResult validate (@Nullable final String sValue)
  {
    if (StringHelper.hasText (sValue) && RegExHelper.stringMatchesPattern (m_sRegEx, sValue))
      return ValidationResultSuccess.getInstance ();
    return m_aErrorText != null ? new ValidationResultError (m_aErrorText, m_sRegEx)
                                : new ValidationResultError (EStandardValidationErrorTexts.INVALID_REGEX, m_sRegEx);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!super.equals (o))
      return false;
    final StringRegExValidator rhs = (StringRegExValidator) o;
    return m_sRegEx.equals (rhs.m_sRegEx) && EqualsHelper.equals (m_aErrorText, rhs.m_aErrorText);
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ()).append (m_sRegEx).append (m_aErrorText).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("regEx", m_sRegEx)
                            .appendIfNotNull ("errorText", m_aErrorText)
                            .toString ();
  }
}
