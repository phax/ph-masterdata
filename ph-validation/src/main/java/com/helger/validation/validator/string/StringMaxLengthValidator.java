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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.text.display.IHasDisplayText;
import com.helger.validation.EStandardValidationErrorTexts;
import com.helger.validation.result.IValidationResult;
import com.helger.validation.result.ValidationResultError;
import com.helger.validation.result.ValidationResultSuccess;

/**
 * Check whether a string fulfills a maximum length restriction.
 *
 * @author Philip Helger
 */
@Immutable
public class StringMaxLengthValidator extends AbstractStringValidator
{
  private final int m_nMaxLength;
  private final IHasDisplayText m_aErrorText;

  public StringMaxLengthValidator (@Nonnegative final int nMaxLength)
  {
    this (nMaxLength, null);
  }

  /**
   * Constructor with custom error message.
   *
   * @param nMaxLength
   *        The maximum allowed string length. Must be &gt; 0.
   * @param aErrorText
   *        Optional error text. May be <code>null</code>.
   */
  public StringMaxLengthValidator (@Nonnegative final int nMaxLength, @Nullable final IHasDisplayText aErrorText)
  {
    ValueEnforcer.isGT0 (nMaxLength, "MaxLength");
    m_nMaxLength = nMaxLength;
    m_aErrorText = aErrorText;
  }

  @Nonnull
  public IValidationResult validate (@Nullable final String sValue)
  {
    if (StringHelper.getLength (sValue) <= m_nMaxLength)
      return ValidationResultSuccess.getInstance ();
    return ValidationResultError.create (m_aErrorText != null ? m_aErrorText
                                                              : EStandardValidationErrorTexts.INVALID_MAXLENGTH,
                                         Integer.toString (m_nMaxLength));
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!super.equals (o))
      return false;
    final StringMaxLengthValidator rhs = (StringMaxLengthValidator) o;
    return m_nMaxLength == rhs.m_nMaxLength && EqualsHelper.equals (m_aErrorText, rhs.m_aErrorText);
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ()).append (m_nMaxLength).append (m_aErrorText).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("maxLength", m_nMaxLength)
                            .appendIfNotNull ("errorText", m_aErrorText)
                            .toString ();
  }
}
