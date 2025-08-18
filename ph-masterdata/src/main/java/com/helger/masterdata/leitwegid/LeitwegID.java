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
package com.helger.masterdata.leitwegid;

import java.math.BigInteger;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.annotation.Nonempty;
import com.helger.base.CGlobal;
import com.helger.base.numeric.BigHelper;
import com.helger.base.string.StringHelper;
import com.helger.cache.regex.RegExHelper;

/**
 * Handler for Leitweg-ID specific stuff.
 *
 * @author Philip Helger
 */
@Immutable
public final class LeitwegID
{
  public static final int MIN_COARSE_LENGTH = 2;
  public static final int MAX_COARSE_LENGTH = 12;
  public static final int MIN_FINE_LENGTH = 0;
  public static final int MAX_FINE_LENGTH = 30;
  public static final int CHECKSUM_LENGTH = 2;
  public static final char SEPARATOR_CHAR = '-';

  private static final String REGEX_LEITWEGID_CREATE = "[0-9A-Z]{2,12}(\\-[0-9A-Z]{0,30})?";
  public static final String REGEX_LEITWEGID = REGEX_LEITWEGID_CREATE + "\\-[0-9]{2}";

  public static final int MIN_TOTAL_LENGTH = MIN_COARSE_LENGTH + 1 + CHECKSUM_LENGTH;
  public static final int MAX_TOTAL_LENGTH = MAX_COARSE_LENGTH + 1 + MAX_FINE_LENGTH + 1 + CHECKSUM_LENGTH;

  private static final BigInteger BI97 = BigInteger.valueOf (97);

  private LeitwegID ()
  {}

  private static int _calcChecksum (@Nonempty final String s)
  {
    BigInteger aBI = BigInteger.ZERO;
    for (final char c : s.toCharArray ())
      if (c >= '0' && c <= '9')
      {
        // Simple case
        aBI = aBI.multiply (BigInteger.TEN).add (BigHelper.toBigInteger (c - '0'));
      }
      else
      {
        // Must be A-Z
        // 10 <= n <= 35
        final int n = 10 + (c - 'A');
        aBI = aBI.multiply (CGlobal.BIGINT_100).add (BigHelper.toBigInteger (n));
      }

    // Add the trailing "00"
    aBI = aBI.multiply (CGlobal.BIGINT_100);

    final int nMod = aBI.mod (BI97).intValueExact ();
    // Use 98 to avoid that the result can be 0
    return 98 - nMod;
  }

  /**
   * Check if the provided Leitweg ID is valid or not. This method also checks the validity of the
   * checksum. An example code that is valid is <code>04011000-1234512345-06</code>.
   *
   * @param sID
   *        The ID to check. May be <code>null</code>.
   * @return <code>true</code> if the ID is valid, <code>false</code> if it is not.
   */
  public static boolean isLeitwegIDValid (@Nullable final String sID)
  {
    // Length checks first
    final int nLen = StringHelper.getLength (sID);
    if (nLen < MIN_TOTAL_LENGTH || nLen > MAX_TOTAL_LENGTH)
    {
      // Too short or too long
      return false;
    }

    // RegEx does it all
    if (!RegExHelper.stringMatchesPattern (REGEX_LEITWEGID, sID))
      return false;

    final String [] aParts = StringHelper.getExplodedArray (SEPARATOR_CHAR, sID);
    final int nParts = aParts.length;
    if (nParts < 2 || nParts > 3)
    {
      // Too many parts
      throw new IllegalStateException ("Unexpected part count " + nParts + " - bug in the RegEx");
    }

    if (aParts[0].length () < MIN_COARSE_LENGTH || aParts[0].length () > MAX_COARSE_LENGTH)
      return false;

    final String sChecksum;
    final boolean bHasFineAddressing = nParts == 3;
    if (bHasFineAddressing)
    {
      // Check fine addressing
      if (aParts[1].length () < MIN_FINE_LENGTH || aParts[1].length () > MAX_FINE_LENGTH)
        return false;
      sChecksum = aParts[2];
    }
    else
      sChecksum = aParts[1];

    // Check checksum
    if (sChecksum.length () != CHECKSUM_LENGTH)
      return false;

    final int nCheckSum = _calcChecksum (bHasFineAddressing ? aParts[0] + aParts[1] : aParts[0]);
    return sChecksum.equals (StringHelper.getLeadingZero (nCheckSum, CHECKSUM_LENGTH));
  }

  /**
   * Calculate the checksum of the Leitweg ID. When using the example code
   * <code>04011000-1234512345</code> the output is <code>06</code>.
   *
   * @param sID
   *        The Leitweg ID without the checksum part. May be <code>null</code>.
   * @return <code>null</code> if the checksum cannot be created.
   */
  @Nullable
  public static String calcLeitwegIDChecksum (@Nullable final String sID)
  {
    // Length checks first
    final int nLen = StringHelper.getLength (sID);
    if (nLen < MIN_TOTAL_LENGTH - 3 || nLen > MAX_TOTAL_LENGTH - 3)
    {
      // Too short or too long
      return null;
    }

    // RegEx does it all
    if (!RegExHelper.stringMatchesPattern (REGEX_LEITWEGID_CREATE, sID))
      return null;

    final String [] aParts = StringHelper.getExplodedArray (SEPARATOR_CHAR, sID);
    final int nParts = aParts.length;
    if (nParts < 1 || nParts > 2)
    {
      // Too many parts
      throw new IllegalStateException ("Unexpected part count " + nParts + " - bug in the RegEx");
    }

    if (aParts[0].length () < MIN_COARSE_LENGTH || aParts[0].length () > MAX_COARSE_LENGTH)
      return null;

    if (nParts == 2)
    {
      // Check fine addressing
      if (aParts[1].length () < MIN_FINE_LENGTH || aParts[1].length () > MAX_FINE_LENGTH)
        return null;
    }

    final int nCheckSum = _calcChecksum (nParts == 2 ? aParts[0] + aParts[1] : aParts[0]);
    return StringHelper.getLeadingZero (nCheckSum, CHECKSUM_LENGTH);
  }
}
