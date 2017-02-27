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

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.collection.ext.CommonsArrayList;
import com.helger.commons.collection.ext.ICommonsList;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;
import com.helger.validation.result.IValidationResult;
import com.helger.validation.result.ValidationResultSuccess;
import com.helger.validation.validator.IBaseValidator;
import com.helger.validation.validator.IStringValidator;

/**
 * A collection of simple {@link IBaseValidator} objects that are executed
 * together. Only if all validators indicate success, the overall validation
 * succeeds. In case of a validation error, the first error is returned.
 *
 * @author Philip Helger
 */
public class StringValidatorChainAND extends AbstractStringValidator
{
  private final ICommonsList <IStringValidator> m_aValidators;

  public StringValidatorChainAND (@Nonnull @Nonempty final IStringValidator... aValidators)
  {
    ValueEnforcer.notEmptyNoNullValue (aValidators, "Validators");
    m_aValidators = new CommonsArrayList<> (aValidators);
  }

  @Nonnull
  public IValidationResult validate (@Nullable final String aValue)
  {
    for (final IStringValidator aValidator : m_aValidators)
    {
      final IValidationResult aRes = aValidator.validate (aValue);
      if (aRes.isInvalid ())
        return aRes;
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
    final StringValidatorChainAND rhs = (StringValidatorChainAND) o;
    return m_aValidators.equals (rhs.m_aValidators);
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ()).append (m_aValidators).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("validators", m_aValidators).getToString ();
  }
}
