/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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
package com.helger.masterdata.vat;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.RegEx;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.GlobalDebug;
import com.helger.commons.IHasCountry;
import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotations.Nonempty;
import com.helger.commons.annotations.ReturnsMutableCopy;
import com.helger.commons.collections.ContainerHelper;
import com.helger.commons.hash.HashCodeGenerator;
import com.helger.commons.locale.country.CountryCache;
import com.helger.commons.regex.RegExPool;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;

/**
 * Represents a VATIN structure for a single country.
 *
 * @author Philip Helger
 */
@Immutable
public final class VATINStructure implements IHasCountry
{
  private final Locale m_aCountry;
  private final String m_sPattern;
  private final Pattern m_aPattern;
  private final List <String> m_aExamples;

  public VATINStructure (@Nonnull final String sCountry,
                         @Nonnull @RegEx final String sRegEx,
                         @Nonnull final Collection <String> aExamples)
  {
    ValueEnforcer.notNull (sCountry, "Country");
    ValueEnforcer.notNull (sRegEx, "RegEx");
    ValueEnforcer.notEmpty (aExamples, "Example");

    m_aCountry = CountryCache.getInstance ().getCountry (sCountry);
    if (m_aCountry == null)
      throw new IllegalArgumentException ("country");
    m_sPattern = sRegEx;
    m_aPattern = RegExPool.getPattern (sRegEx);
    m_aExamples = ContainerHelper.newList (aExamples);

    if (GlobalDebug.isDebugMode ())
      for (final String s : m_aExamples)
        if (!isValid (s))
          throw new IllegalArgumentException ("Example VATIN " + s + " does not match " + sRegEx);
  }

  public boolean isValid (@Nullable final String sVATIN)
  {
    if (StringHelper.hasNoText (sVATIN))
      return false;
    final String sRealVATIN = StringHelper.replaceAll (sVATIN, " ", "").toUpperCase ();
    return m_aPattern.matcher (sRealVATIN).matches ();
  }

  /**
   * @return The regular expression pattern used for validation.
   */
  @Nonnull
  public String getPattern ()
  {
    return m_sPattern;
  }

  /**
   * @return The country for which the structure applies.
   */
  @Nonnull
  public Locale getCountry ()
  {
    return m_aCountry;
  }

  /**
   * @return A non-<code>null</code> list with example VAT numbers. This list
   *         contains at least a single item.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public List <String> getExamples ()
  {
    return ContainerHelper.newList (m_aExamples);
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (!(o instanceof VATINStructure))
      return false;
    final VATINStructure rhs = (VATINStructure) o;
    return m_aCountry.equals (rhs.m_aCountry) &&
           m_sPattern.equals (rhs.m_sPattern) &&
           m_aExamples.equals (rhs.m_aExamples);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aCountry).append (m_sPattern).append (m_aExamples).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("country", m_aCountry)
                                       .append ("pattern", m_sPattern)
                                       .append ("examples", m_aExamples)
                                       .toString ();
  }
}
