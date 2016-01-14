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

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

/**
 * Validating date and time values with a fixed locale.
 *
 * @author Philip Helger
 */
@Immutable
public class DateTimeValidatorConstantLocale extends AbstractStringDateTimeValidator
{
  private final Locale m_aParseLocale;

  public DateTimeValidatorConstantLocale (@Nonnull final Locale aParseLocale)
  {
    m_aParseLocale = ValueEnforcer.notNull (aParseLocale, "ParseLocale");
  }

  @Override
  @Nonnull
  protected final Locale getParseLocale ()
  {
    return m_aParseLocale;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!super.equals (o))
      return false;
    final DateTimeValidatorConstantLocale rhs = (DateTimeValidatorConstantLocale) o;
    return m_aParseLocale.equals (rhs.m_aParseLocale);
  }

  @Override
  public int hashCode ()
  {
    return HashCodeGenerator.getDerived (super.hashCode ()).append (m_aParseLocale).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("parseLocale", m_aParseLocale).toString ();
  }
}
