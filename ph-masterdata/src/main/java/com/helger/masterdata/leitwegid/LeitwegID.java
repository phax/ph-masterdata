package com.helger.masterdata.leitwegid;

import java.math.BigInteger;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.CGlobal;
import com.helger.commons.math.MathHelper;
import com.helger.commons.regex.RegExHelper;
import com.helger.commons.string.StringHelper;

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

  public static final String REGEX_LEITWEGID = "[0-9A-Z]{2,12}\\-([0-9A-Z]{0,30}\\-)?[0-9]{2}";

  public static final int MIN_TOTAL_LENGTH = MIN_COARSE_LENGTH + 1 + CHECKSUM_LENGTH;
  public static final int MAX_TOTAL_LENGTH = MAX_COARSE_LENGTH + 1 + MAX_FINE_LENGTH + 1 + CHECKSUM_LENGTH;

  private static final BigInteger BI97 = BigInteger.valueOf (97);

  private LeitwegID ()
  {}

  private static int _getCode (final char c)
  {
    if (c >= '0' && c <= '9')
      return c - '0';
    // Must be A-Z
    return 10 + (c - 'A');
  }

  private static int _calcChecksum (final String s)
  {
    BigInteger aBI = BigInteger.ZERO;
    for (final char c : s.toCharArray ())
    {
      // Shift by one level (*10) than add
      aBI = aBI.multiply (BigInteger.TEN).add (MathHelper.toBigInteger (_getCode (c)));
    }
    // Add the trailing "00"
    aBI = aBI.multiply (CGlobal.BIGINT_100);
    final int nMod = aBI.mod (BI97).intValueExact ();
    return 98 - nMod;
  }

  public static boolean isLeitwegIDValid (@Nullable final String sID)
  {
    // Length checks first
    final int nLen = StringHelper.getLength (sID);
    if (nLen < MIN_TOTAL_LENGTH)
    {
      // Too short
      return false;
    }
    if (nLen > MAX_TOTAL_LENGTH)
    {
      // Too long
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

    if (aParts[0].length () < MIN_COARSE_LENGTH)
      return false;
    if (aParts[0].length () > MAX_COARSE_LENGTH)
      return false;
    final boolean bHasFineAddressing = nParts == 3;
    final String sChecksum = aParts[bHasFineAddressing ? 2 : 1];
    if (bHasFineAddressing)
    {
      // Check fine addressing and checksum
      if (aParts[1].length () < MIN_FINE_LENGTH)
        return false;
      if (aParts[1].length () > MAX_FINE_LENGTH)
        return false;
    }
    // Check checksum only
    if (sChecksum.length () != CHECKSUM_LENGTH)
      return false;

    final int nCheckSum = _calcChecksum (bHasFineAddressing ? aParts[0] + aParts[1] : aParts[0]);
    return sChecksum.equals (StringHelper.getLeadingZero (nCheckSum, 2));
  }
}
