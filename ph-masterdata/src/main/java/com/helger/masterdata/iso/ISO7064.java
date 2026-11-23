/*
 * Copyright (C) 2014-2026 Philip Helger (www.helger.com)
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

import org.jspecify.annotations.NonNull;

import com.helger.base.CGlobal;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.masterdata.checkdigit.ISO7064PureSystem;

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

    /**
     * Get the MOD 97-10 remainder of the provided string. Digits are used as is, the uppercase
     * letters <code>A-Z</code> are converted to the two-digit numbers 10-35 (ISO 13616). The
     * calculation is delegated to {@link ISO7064PureSystem#MOD_97_10_ALPHA}.
     *
     * @param s
     *        The string to use. May not be <code>null</code>.
     * @return The remainder in the range 0 to 96 (0 for an empty string), or
     *         {@link #ILLEGAL_CHECKSUM} if the string contains another character.
     */
    public static int getChecksum (@NonNull final String s)
    {
      ValueEnforcer.notNull (s, "String");
      return ISO7064PureSystem.MOD_97_10_ALPHA.getPayloadRemainder (s);
    }

    public static boolean isValid (@NonNull final String s)
    {
      return getChecksum (s) == EXPECTED_CHECKSUM;
    }
  }

  private ISO7064 ()
  {}

}
