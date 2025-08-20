/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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

import java.io.Serializable;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.RegEx;
import javax.annotation.concurrent.Immutable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.style.ReturnsMutableCopy;
import com.helger.base.debug.GlobalDebug;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.string.StringHelper;
import com.helger.base.string.StringRemove;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.cache.regex.RegExCache;
import com.helger.collection.commons.CommonsArrayList;
import com.helger.collection.commons.ICommonsList;
import com.helger.text.locale.country.CountryCache;
import com.helger.text.locale.country.IHasCountry;

/**
 * Represents a VATIN structure for a single country.
 *
 * @author Philip Helger
 */
@Immutable
public class VATINStructure implements IHasCountry, Serializable
{
  private final Locale m_aCountry;
  private final String m_sPattern;
  private final Pattern m_aPattern;
  private final ICommonsList <String> m_aExamples;

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
    m_aPattern = RegExCache.getPattern (sRegEx);
    m_aExamples = new CommonsArrayList <> (aExamples);

    if (GlobalDebug.isDebugMode ())
      for (final String s : m_aExamples)
        if (!isValid (s))
          throw new IllegalArgumentException ("Example VATIN " + s + " does not match " + sRegEx);
  }

  public boolean isValid (@Nullable final String sVATIN)
  {
    if (StringHelper.isEmpty (sVATIN))
      return false;
    final String sRealVATIN = StringRemove.removeAll (sVATIN, " ").toUpperCase (Locale.US);
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
   * @return A non-<code>null</code> list with example VAT numbers. This list contains at least a
   *         single item.
   */
  @Nonnull
  @Nonempty
  @ReturnsMutableCopy
  public ICommonsList <String> getExamples ()
  {
    return m_aExamples.getClone ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
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
                                       .getToString ();
  }
}
