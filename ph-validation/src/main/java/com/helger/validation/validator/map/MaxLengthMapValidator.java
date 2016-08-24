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
package com.helger.validation.validator.map;

import java.util.Map;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;
import com.helger.validation.EStandardValidationErrorTexts;
import com.helger.validation.result.IValidationResult;
import com.helger.validation.result.ValidationResultError;
import com.helger.validation.result.ValidationResultSuccess;

/**
 * A validator that checks for the maximum length of a map.
 *
 * @author Philip Helger
 */
@Immutable
public class MaxLengthMapValidator extends AbstractMapValidator <Object, Object>
{
  private final int m_nMaxLength;

  public MaxLengthMapValidator (@Nonnegative final int nMaxLength)
  {
    ValueEnforcer.isGT0 (nMaxLength, "MaxLength");
    m_nMaxLength = nMaxLength;
  }

  @Nonnegative
  public int getMaxLength ()
  {
    return m_nMaxLength;
  }

  @Nonnull
  public IValidationResult validate (@Nullable final Map <? extends Object, ? extends Object> aRequestValues)
  {
    if (aRequestValues == null || aRequestValues.size () > m_nMaxLength)
    {
      return ValidationResultError.create (EStandardValidationErrorTexts.INVALID_MAX_LENGTH,
                                           Integer.toString (m_nMaxLength));
    }
    return ValidationResultSuccess.getInstance ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!super.equals (o))
      return false;
    final MaxLengthMapValidator rhs = (MaxLengthMapValidator) o;
    return m_nMaxLength == rhs.m_nMaxLength;
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ()).append (m_nMaxLength).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("maxLength", m_nMaxLength).toString ();
  }
}
