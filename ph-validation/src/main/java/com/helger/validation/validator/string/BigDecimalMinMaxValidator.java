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

import java.math.BigDecimal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.StringParser;
import com.helger.commons.string.ToStringGenerator;
import com.helger.validation.EStandardValidationErrorTexts;
import com.helger.validation.result.IValidationResult;
import com.helger.validation.result.ValidationResultError;
import com.helger.validation.result.ValidationResultSuccess;

@Immutable
public class BigDecimalMinMaxValidator extends AbstractStringValidator
{
  private final BigDecimal m_aMinInclusive;
  private final BigDecimal m_aMaxInclusive;

  /**
   * Ctor. Note: only one value may be <code>null</code>. If both values should
   * be infinity, use the {@link BigDecimalValidator} which imposes no limits!
   *
   * @param aMinInclusive
   *        Minimum allowed value. Must be &le; maximum value. Pass in
   *        <code>null</code> to indicate no lower limit.
   * @param aMaxInclusive
   *        Maximum allowed value. Must be &ge; minimum value. Pass in
   *        <code>null</code> to indicate no upper limit.
   */
  public BigDecimalMinMaxValidator (@Nullable final BigDecimal aMinInclusive, @Nullable final BigDecimal aMaxInclusive)
  {
    if (aMinInclusive == null && aMaxInclusive == null)
      throw new IllegalArgumentException ("Both min and max are infinity! Use simple BigDecimalValidator instead");
    if (aMinInclusive != null && aMaxInclusive != null && aMinInclusive.compareTo (aMaxInclusive) > 0)
      throw new IllegalArgumentException ("Minimum value is greater than maximum value!");
    m_aMinInclusive = aMinInclusive;
    m_aMaxInclusive = aMaxInclusive;
  }

  private static String _toString (@Nullable final BigDecimal aValue, final boolean bIsMin)
  {
    return aValue != null ? aValue.toString ()
                          : Double.toString (bIsMin ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY);
  }

  @Nonnull
  public IValidationResult validate (@Nullable final String sValue)
  {
    final BigDecimal aValue = StringParser.parseBigDecimal (sValue);
    if (aValue == null)
      return new ValidationResultError (EStandardValidationErrorTexts.INVALID_DOUBLE);

    if (m_aMinInclusive != null && aValue.compareTo (m_aMinInclusive) < 0)
    {
      return ValidationResultError.create (EStandardValidationErrorTexts.INVALID_DOUBLE_RANGE,
                                           _toString (m_aMinInclusive, true),
                                           _toString (m_aMaxInclusive, false));
    }
    if (m_aMaxInclusive != null && aValue.compareTo (m_aMaxInclusive) > 0)
    {
      return ValidationResultError.create (EStandardValidationErrorTexts.INVALID_DOUBLE_RANGE,
                                           _toString (m_aMinInclusive, true),
                                           _toString (m_aMaxInclusive, false));
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
    final BigDecimalMinMaxValidator rhs = (BigDecimalMinMaxValidator) o;
    return EqualsHelper.equals (m_aMinInclusive, rhs.m_aMinInclusive) &&
           EqualsHelper.equals (m_aMaxInclusive, rhs.m_aMaxInclusive);
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ())
                            .append (m_aMinInclusive)
                            .append (m_aMaxInclusive)
                            .getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ())
                            .append ("minIncl", m_aMinInclusive)
                            .append ("maxIncl", m_aMaxInclusive)
                            .getToString ();
  }
}
