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
package com.helger.masterdata.postal;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.regex.RegExCache;
import com.helger.commons.string.ToStringGenerator;

/**
 * This class contains a single postal code format for a certain country.
 *
 * @author Philip Helger
 */
@Immutable
public class PostalCodeFormat implements Serializable
{
  private final String m_sISO;
  private final String m_sFormat;
  private final Pattern m_aPattern;
  private final String m_sExample;

  /**
   * Create a new postal code format
   *
   * @param sISO
   *        The owning countries ISO code. May neither be <code>null</code> nor
   *        empty.
   * @param aElements
   *        The elements this postal code format is made up
   */
  public PostalCodeFormat (@Nonnull @Nonempty final String sISO, @Nonnull @Nonempty final List <EPostalCodeFormatElement> aElements)
  {
    ValueEnforcer.notEmpty (sISO, "ISO");
    ValueEnforcer.notEmpty (aElements, "Elements");

    m_sISO = sISO;

    final StringBuilder aSBFormat = new StringBuilder ();
    final StringBuilder aSBRegEx = new StringBuilder ("^");
    final StringBuilder aSBExample = new StringBuilder ();
    for (final EPostalCodeFormatElement eElement : aElements)
    {
      aSBFormat.append (eElement.getToken ());

      final String sRegEx = eElement.getRegEx ();
      aSBRegEx.append (sRegEx != null ? sRegEx : sISO);

      final String sExample = eElement.getExample ();
      aSBExample.append (sExample != null ? sExample : sISO);
    }
    aSBRegEx.append ("$");

    m_sFormat = aSBFormat.toString ();
    m_aPattern = RegExCache.getPattern (aSBRegEx.toString ());
    m_sExample = aSBExample.toString ();
  }

  /**
   * @return The format definition string. Never <code>null</code>.
   */
  @Nonnull
  @Nonempty
  public String getFormatDefinitionString ()
  {
    return m_sFormat;
  }

  /**
   * @return The non-<code>null</code> ISO country string, to which this format
   *         belongs.
   */
  @Nonnull
  @Nonempty
  public String getISO ()
  {
    return m_sISO;
  }

  /**
   * @return The regular expression pattern used to parse postal codes. Never
   *         <code>null</code>.
   */
  @Nonnull
  @Nonempty
  public String getRegExPattern ()
  {
    return m_aPattern.pattern ();
  }

  /**
   * @return The example string that would match this format.
   */
  @Nonnull
  @Nonempty
  public String getExample ()
  {
    return m_sExample;
  }

  /**
   * Check whether the passed postal code is valid for this format using the
   * build in regular expression.
   *
   * @param sPostalCode
   *        The postal code to check. May be <code>null</code>.
   * @return <code>true</code> if the passed postal code matches this format,
   *         <code>false</code> otherwise.
   */
  public boolean isValidPostalCode (final String sPostalCode)
  {
    return m_aPattern.matcher (sPostalCode).matches ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("iso", m_sISO)
                                       .append ("format", m_sFormat)
                                       .append ("pattern", m_aPattern)
                                       .append ("example", m_sExample)
                                       .getToString ();
  }
}
