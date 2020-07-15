/**
 * Copyright (C) 2014-2020 Philip Helger (www.helger.com)
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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.string.StringHelper;

/**
 * Represents a single element within a postal code format definition
 *
 * @author Philip Helger
 */
enum EPostalCodeFormatElement
{
  LETTER ("A", "[a-zA-Z]", "A"),
  NUMBER ("N", "[0-9]", "1"),
  COUNTRY_CODE ("CC", null, null),
  SPACE (" ", " ", " "),
  DASH ("-", "\\-", "-");

  private final String m_sToken;
  private final String m_sRegEx;
  private final String m_sExample;

  private EPostalCodeFormatElement (@Nonnull @Nonempty final String sToken, @Nullable final String sRegEx, @Nullable final String sExample)
  {
    m_sToken = sToken;
    m_sRegEx = sRegEx;
    m_sExample = sExample;
  }

  /**
   * @return The token in the format definition string.
   */
  @Nonnull
  @Nonempty
  public String getToken ()
  {
    return m_sToken;
  }

  /**
   * @return The length of the token
   */
  @Nonnegative
  public int getTokenLength ()
  {
    return m_sToken.length ();
  }

  /**
   * @return The regular expression to parse this element. May be
   *         <code>null</code> for the country code!
   */
  @Nullable
  public String getRegEx ()
  {
    return m_sRegEx;
  }

  /**
   * @return The example character for this element. May be <code>null</code>
   *         for the country code!
   */
  @Nullable
  public String getExample ()
  {
    return m_sExample;
  }

  @Nullable
  public static EPostalCodeFormatElement getFromString (@Nullable final String sString, final int nIndex)
  {
    if (StringHelper.hasText (sString))
      for (final EPostalCodeFormatElement eElement : values ())
        if (sString.substring (nIndex, nIndex + eElement.getTokenLength ()).equals (eElement.getToken ()))
          return eElement;
    return null;
  }
}
