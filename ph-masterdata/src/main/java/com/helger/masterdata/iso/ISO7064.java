/*
 * Copyright (C) 2014-2023 Philip Helger (www.helger.com)
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
package com.helger.masterdata.iso;

import javax.annotation.Nonnull;

import com.helger.commons.CGlobal;

/**
 * ISO 7064 checksum algorithms.
 *
 * @author Philip Helger
 * @since 6.1.4
 */
public final class ISO7064
{
  /**
   * Mod 97 algorithm. Used e.g. for IBAN.
   *
   * @author Philip Helger
   */
  public static final class Mod97
  {
    public static final int ILLEGAL_CHECKSUM = CGlobal.ILLEGAL_UINT;
    public static final int EXPECTED_CHECKSUM = 1;

    private Mod97 ()
    {}

    public static int getChecksum (@Nonnull final String s)
    {
      int nChecksum = 0;
      for (final char c : s.toCharArray ())
      {
        final int nCurChecksumValue;
        if (c >= '0' && c <= '9')
          nCurChecksumValue = c - '0';
        else
          if (c >= 'A' && c <= 'Z')
          {
            // Same as "- '7'" but more clear
            nCurChecksumValue = c - 'A' + 10;
          }
          else
            return ILLEGAL_CHECKSUM;

        if (nCurChecksumValue > 9)
          nChecksum = (100 * nChecksum + nCurChecksumValue) % 97;
        else
          nChecksum = (10 * nChecksum + nCurChecksumValue) % 97;
      }
      return nChecksum;
    }

    public static boolean isValid (@Nonnull final String s)
    {
      return getChecksum (s) == EXPECTED_CHECKSUM;
    }
  }

  private ISO7064 ()
  {}

}
