/**
 * Copyright (C) 2014-2021 Philip Helger (www.helger.com)
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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.annotation.ReturnsMutableCopy;
import com.helger.commons.collection.impl.CommonsArrayList;
import com.helger.commons.collection.impl.ICommonsList;
import com.helger.commons.string.StringHelper;
import com.helger.commons.string.ToStringGenerator;

/**
 * Contains postal code information for a single country.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public class PostalCodeCountry implements IPostalCodeCountry, Serializable
{
  private final String m_sISO;
  private final ICommonsList <PostalCodeFormat> m_aFormats = new CommonsArrayList <> ();
  private final ICommonsList <String> m_aSpecificPostalCodes = new CommonsArrayList <> ();
  private String m_sNote;

  public PostalCodeCountry (@Nonnull @Nonempty final String sISO)
  {
    m_sISO = ValueEnforcer.notEmpty (sISO, "ISO");
  }

  @Nonnull
  @Nonempty
  public String getISO ()
  {
    return m_sISO;
  }

  void addFormat (@Nonnull final PostalCodeFormat aFormat)
  {
    ValueEnforcer.notNull (aFormat, "Format");
    m_aFormats.add (aFormat);
  }

  @Nonnegative
  public int getFormatCount ()
  {
    return m_aFormats.size ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <PostalCodeFormat> getAllFormats ()
  {
    return m_aFormats.getClone ();
  }

  @Nullable
  public PostalCodeFormat getFormatOfIndex (final int nIndex)
  {
    return m_aFormats.getAtIndex (nIndex);
  }

  void addSpecificPostalCode (@Nonnull @Nonempty final String sSpecificPostalCode)
  {
    ValueEnforcer.notEmpty (sSpecificPostalCode, "SpecificPostalCode");
    ValueEnforcer.isTrue (isValidPostalCode (sSpecificPostalCode),
                          () -> "The passed code '" + sSpecificPostalCode + "' is not valid according to the rules!");
    m_aSpecificPostalCodes.add (sSpecificPostalCode);
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <String> getAllSpecificPostalCodes ()
  {
    return m_aSpecificPostalCodes.getClone ();
  }

  @Nonnegative
  public int getSpecificPostalCodeCount ()
  {
    return m_aSpecificPostalCodes.size ();
  }

  void setNote (@Nonnull @Nonempty final String sNote)
  {
    ValueEnforcer.notEmpty (sNote, "Note");
    m_sNote = sNote;
  }

  @Nullable
  public String getNote ()
  {
    return m_sNote;
  }

  public boolean isValidPostalCode (@Nullable final String sPostalCode)
  {
    if (StringHelper.hasText (sPostalCode))
      for (final PostalCodeFormat aFormat : m_aFormats)
        if (aFormat.isValidPostalCode (sPostalCode))
          return true;
    // No format - all are valid
    return m_aFormats.isEmpty ();
  }

  @Nonnull
  @ReturnsMutableCopy
  public ICommonsList <String> getAllExamples ()
  {
    return m_aFormats.getAllMapped (PostalCodeFormat::getExample);
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ISO", m_sISO)
                                       .append ("formats", m_aFormats)
                                       .append ("specificPostalCodes", m_aSpecificPostalCodes)
                                       .appendIfNotNull ("note", m_sNote)
                                       .getToString ();
  }
}
