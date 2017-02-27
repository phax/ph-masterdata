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
package com.helger.validation.validator.string;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.string.ToStringGenerator;
import com.helger.commons.text.display.IHasDisplayText;
import com.helger.validation.EStandardValidationErrorTexts;
import com.helger.validation.result.IValidationResult;
import com.helger.validation.result.ValidationResultError;
import com.helger.validation.result.ValidationResultSuccess;

/**
 * Check that a string is part of an enumeration that implements
 * IHasID&lt;String&gt;.
 *
 * @author Philip Helger
 * @param <ENUMTYPE>
 *        Enum type to validate
 */
@Immutable
public class StringEnumValidator <ENUMTYPE extends Enum <ENUMTYPE> & IHasID <String>> extends AbstractStringValidator
{
  private Class <ENUMTYPE> m_aEnumClass;
  private final IHasDisplayText m_aErrorText;

  /**
   * Constructor with a default error message.
   *
   * @param aClass
   *        The enum class to be used. May not be <code>null</code>.
   */
  public StringEnumValidator (@Nonnull final Class <ENUMTYPE> aClass)
  {
    this (aClass, null);
  }

  /**
   * Constructor with custom error message.
   *
   * @param aClass
   *        The enum class to be used. May not be <code>null</code>.
   * @param aErrorText
   *        Optional error text. May be <code>null</code>.
   */
  public StringEnumValidator (@Nonnull final Class <ENUMTYPE> aClass, @Nullable final IHasDisplayText aErrorText)
  {
    m_aEnumClass = ValueEnforcer.notNull (aClass, "Class");
    m_aErrorText = aErrorText;
  }

  @Nonnull
  public Class <ENUMTYPE> getEnumClass ()
  {
    return m_aEnumClass;
  }

  @Nonnull
  public IValidationResult validate (@Nullable final String sValue)
  {
    if (EnumHelper.getFromIDOrNull (m_aEnumClass, sValue) != null)
      return ValidationResultSuccess.getInstance ();
    return new ValidationResultError (m_aErrorText != null ? m_aErrorText
                                                           : EStandardValidationErrorTexts.INVALID_EMPTY);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!super.equals (o))
      return false;
    final StringEnumValidator <?> rhs = (StringEnumValidator <?>) o;
    return m_aEnumClass.equals (rhs.m_aEnumClass) && EqualsHelper.equals (m_aErrorText, rhs.m_aErrorText);
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ()).append (m_aEnumClass).append (m_aErrorText).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("class", m_aEnumClass)
                            .appendIfNotNull ("errorText", m_aErrorText)
                            .getToString ();
  }

  @Nonnull
  public static <ENUMTYPE extends Enum <ENUMTYPE> & IHasID <String>> StringEnumValidator <ENUMTYPE> create (@Nonnull final Class <ENUMTYPE> aClass)
  {
    return new StringEnumValidator<> (aClass);
  }

  @Nonnull
  public static <ENUMTYPE extends Enum <ENUMTYPE> & IHasID <String>> StringEnumValidator <ENUMTYPE> create (@Nonnull final Class <ENUMTYPE> aClass,
                                                                                                            @Nullable final IHasDisplayText aErrorText)
  {
    return new StringEnumValidator<> (aClass, aErrorText);
  }
}
