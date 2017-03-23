package com.helger.masterdata.vat;

import javax.annotation.Nonnull;

import com.helger.commons.ValueEnforcer;

/**
 * Check the syntax of VATINs based on the published rules.
 *
 * @author Philip Helger
 * @since 5.0.4
 */
public class VATINSyntaxChecker
{
  private VATINSyntaxChecker ()
  {}

  private static boolean _isNum (final char c)
  {
    return c >= '0' && c <= '9';
  }

  private static boolean _isNum1to9 (final char c)
  {
    return c >= '1' && c <= '9';
  }

  private static boolean _isLetter (final char c)
  {
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
  }

  private static boolean _isLetterOrNum (final char c)
  {
    return _isLetter (c) || _isNum (c);
  }

  private static int _toInt (final char c)
  {
    return c - '0';
  }

  private static int _toInt (final char c0, final char c1)
  {
    return _toInt (c0) * 10 + _toInt (c1);
  }

  private static int _toInt (final char c0, final char c1, final char c2)
  {
    return _toInt (c0) * 100 + _toInt (c1) * 10 + _toInt (c2);
  }

  private static int _toInt (final char c0,
                             final char c1,
                             final char c2,
                             final char c3,
                             final char c4,
                             final char c5,
                             final char c6)
  {
    return _toInt (c0) * 1_000_000 +
           _toInt (c1) * 100_000 +
           _toInt (c2) * 10_000 +
           _toInt (c3) * 1_000 +
           _toInt (c4) * 100 +
           _toInt (c5) * 10 +
           _toInt (c6);
  }

  private static int _toInt (final char c0,
                             final char c1,
                             final char c2,
                             final char c3,
                             final char c4,
                             final char c5,
                             final char c6,
                             final char c7)
  {
    return _toInt (c0) * 10_000_000 +
           _toInt (c1) * 1_000_000 +
           _toInt (c2) * 100_000 +
           _toInt (c3) * 10_000 +
           _toInt (c4) * 1_000 +
           _toInt (c5) * 100 +
           _toInt (c6) * 10 +
           _toInt (c7);
  }

  private static int _toInt (final char c0,
                             final char c1,
                             final char c2,
                             final char c3,
                             final char c4,
                             final char c5,
                             final char c6,
                             final char c7,
                             final char c8)
  {
    return _toInt (c0) * 100_000_000 +
           _toInt (c1) * 10_000_000 +
           _toInt (c2) * 1_000_000 +
           _toInt (c3) * 100_000 +
           _toInt (c4) * 10_000 +
           _toInt (c5) * 1_000 +
           _toInt (c6) * 100 +
           _toInt (c7) * 10 +
           _toInt (c8);
  }

  public static boolean isValidVATIN (@Nonnull final String sVATIN)
  {
    ValueEnforcer.notNull (sVATIN, "VATIN");
    if (sVATIN.startsWith ("AT"))
      return isValidVATIN_AT (sVATIN.substring (2));
    if (sVATIN.startsWith ("BE"))
      return isValidVATIN_BE (sVATIN.substring (2));
    if (sVATIN.startsWith ("DE"))
      return isValidVATIN_DE (sVATIN.substring (2));
    if (sVATIN.startsWith ("DK"))
      return isValidVATIN_DK (sVATIN.substring (2));
    if (sVATIN.startsWith ("EL"))
      return isValidVATIN_EL (sVATIN.substring (2));
    if (sVATIN.startsWith ("ES"))
      return isValidVATIN_ES (sVATIN.substring (2));
    if (sVATIN.startsWith ("FI"))
      return isValidVATIN_FI (sVATIN.substring (2));
    if (sVATIN.startsWith ("FR"))
      return isValidVATIN_FR (sVATIN.substring (2));
    if (sVATIN.startsWith ("GB"))
      return isValidVATIN_GB (sVATIN.substring (2));
    if (sVATIN.startsWith ("IE"))
      return isValidVATIN_IE (sVATIN.substring (2));
    if (sVATIN.startsWith ("IT"))
      return isValidVATIN_IT (sVATIN.substring (2));

    System.out.println ("Unsupported country " + sVATIN);
    return true;
  }

  private static int _at_s (final char c)
  {
    final int n = _toInt (c);
    return n / 5 + (n * 2) % 10;
  }

  public static boolean isValidVATIN_AT (@Nonnull final String sVATIN)
  {
    ValueEnforcer.notNull (sVATIN, "VATIN");
    final char [] c = sVATIN.toCharArray ();
    if (c.length != 9)
      return false;
    if (c[0] != 'U')
      return false;
    for (int i = 1; i <= 8; ++i)
      if (!_isNum (c[i]))
        return false;
    // Si = INT(Ci / 5) + (Ci * 2) modulo10
    final int s3 = _at_s (c[2]);
    final int s5 = _at_s (c[4]);
    final int s7 = _at_s (c[6]);
    final int r = s3 + s5 + s7;
    final int n9 = (10 - (r + _toInt (c[1]) + _toInt (c[3]) + _toInt (c[5]) + _toInt (c[7]) + 4) % 10) % 10;
    return _toInt (c[8]) == n9;
  }

  public static boolean isValidVATIN_BE (@Nonnull final String sVATIN)
  {
    ValueEnforcer.notNull (sVATIN, "VATIN");
    final char [] c = sVATIN.toCharArray ();
    if (c.length != 10)
      return false;
    if (c[0] != '0')
      return false;
    if (!_isNum1to9 (c[1]))
      return false;
    for (int i = 2; i <= 9; ++i)
      if (!_isNum (c[i]))
        return false;
    final int nExpected = _toInt (c[8], c[9]);
    final int nMod = 97 - (_toInt (c[0], c[1], c[2], c[3], c[4], c[5], c[6], c[7]) % 97);
    return nExpected == nMod;
  }

  public static boolean isValidVATIN_DE (@Nonnull final String sVATIN)
  {
    ValueEnforcer.notNull (sVATIN, "VATIN");
    final char [] c = sVATIN.toCharArray ();
    if (c.length != 9)
      return false;
    if (!_isNum1to9 (c[0]))
      return false;
    for (int i = 1; i <= 8; ++i)
      if (!_isNum (c[i]))
        return false;

    int p = 10;
    for (int n = 0; n <= 7; ++n)
    {
      int m = (_toInt (c[n]) + p) % 10;
      if (m == 0)
        m = 10;
      p = (2 * m) % 11;
    }
    final int r = 11 - p;
    final int nExpected = r == 10 ? 0 : r;
    return nExpected == _toInt (c[8]);
  }

  public static boolean isValidVATIN_DK (@Nonnull final String sVATIN)
  {
    ValueEnforcer.notNull (sVATIN, "VATIN");
    final char [] c = sVATIN.toCharArray ();
    if (c.length != 8)
      return false;
    if (!_isNum1to9 (c[0]))
      return false;
    for (int i = 1; i <= 7; ++i)
      if (!_isNum (c[i]))
        return false;

    final int r = (2 * _toInt (c[0]) +
                   7 * _toInt (c[1]) +
                   6 * _toInt (c[2]) +
                   5 * _toInt (c[3]) +
                   4 * _toInt (c[4]) +
                   3 * _toInt (c[5]) +
                   2 * _toInt (c[6]) +
                   1 * _toInt (c[7]));
    return (r % 11) == 0;
  }

  public static boolean isValidVATIN_EL (@Nonnull final String sVATIN)
  {
    ValueEnforcer.notNull (sVATIN, "VATIN");
    final char [] c = sVATIN.toCharArray ();
    if (c.length != 9)
      return false;
    for (int i = 0; i <= 8; ++i)
      if (!_isNum (c[i]))
        return false;

    return true;
  }

  public static boolean isValidVATIN_ES (@Nonnull final String sVATIN)
  {
    ValueEnforcer.notNull (sVATIN, "VATIN");
    final char [] c = sVATIN.toCharArray ();
    if (c.length != 9)
      return false;
    if (!_isLetterOrNum (c[0]))
      return false;
    for (int i = 1; i <= 7; ++i)
      if (!_isNum (c[i]))
        return false;
    if (!_isLetterOrNum (c[8]))
      return false;

    return true;
  }

  public static boolean isValidVATIN_FI (@Nonnull final String sVATIN)
  {
    ValueEnforcer.notNull (sVATIN, "VATIN");
    final char [] c = sVATIN.toCharArray ();
    if (c.length != 8)
      return false;
    for (int i = 0; i <= 7; ++i)
      if (!_isNum (c[i]))
        return false;

    final int r = 11 -
                  (7 * _toInt (c[0]) +
                   9 * _toInt (c[1]) +
                   10 * _toInt (c[2]) +
                   5 * _toInt (c[3]) +
                   8 * _toInt (c[4]) +
                   4 * _toInt (c[5]) +
                   2 * _toInt (c[6])) % 11;
    if (r == 10)
      return false;

    final int nExpected = r == 11 ? 0 : r;
    return _toInt (c[7]) == nExpected;
  }

  public static boolean isValidVATIN_FR (@Nonnull final String sVATIN)
  {
    ValueEnforcer.notNull (sVATIN, "VATIN");
    final char [] c = sVATIN.toCharArray ();
    if (c.length != 11)
      return false;
    if (!_isLetterOrNum (c[0]))
      return false;
    if (!_isLetterOrNum (c[1]))
      return false;
    for (int i = 2; i <= 10; ++i)
      if (!_isNum (c[i]))
        return false;

    return true;
  }

  public static boolean isValidVATIN_GB (@Nonnull final String sVATIN)
  {
    ValueEnforcer.notNull (sVATIN, "VATIN");
    final char [] c = sVATIN.toCharArray ();
    if (c.length == 5)
    {
      // This format applies to Government departments and Health authorities
      if (c[0] == 'G' && c[1] == 'D')
      {
        final int n = _toInt (c[2], c[3], c[4]);
        return n >= 0 && n <= 499;
      }
      if (c[0] == 'H' && c[1] == 'A')
      {
        final int n = _toInt (c[2], c[3], c[4]);
        return n >= 500 && n <= 999;
      }
      return false;
    }

    // This format applies to all others
    if (c.length != 9 && c.length != 12)
      return false;
    for (int i = 0; i < c.length; ++i)
      if (!_isNum (c[i]))
        return false;

    final int v1 = _toInt (c[0], c[1], c[2], c[3], c[4], c[5], c[6]);
    if (v1 >= 100_000 && v1 <= 999_999)
      return false;
    if (v1 >= 9_490_001 && v1 <= 9_700_000)
      return false;
    if (v1 >= 9_990_001 && v1 <= 9_999_999)
      return false;
    if (c.length == 12)
    {
      final int v2b = _toInt (c[9], c[10], c[11]);
      if (v2b <= 0)
        return false;
    }

    final int v2 = _toInt (c[0], c[1], c[2], c[3], c[4], c[5], c[6], c[7], c[8]);
    if (v2 <= 0)
      return false;

    final int tmp = 8 * _toInt (c[0]) +
                    7 * _toInt (c[1]) +
                    6 * _toInt (c[2]) +
                    5 * _toInt (c[3]) +
                    4 * _toInt (c[4]) +
                    3 * _toInt (c[5]) +
                    2 * _toInt (c[6]) +
                    1 * _toInt (c[7], c[8]);
    final int r1 = tmp % 97;
    final int r2 = (tmp + 55) % 97;
    return r1 == 0 || r2 == 0;
  }

  private static boolean _ie_is2 (final char c)
  {
    return (c >= 'A' && c <= 'Z') || c == '+' || c == '*';
  }

  private static boolean _ie_is8 (final char c)
  {
    return (c >= 'A' && c <= 'W');
  }

  private static boolean _ie_is9 (final char c)
  {
    return (c >= 'A' && c <= 'I');
  }

  private static char _ie_checkChar (final int r)
  {
    return r == 0 ? 'W' : (char) ('A' + r - 1);
  }

  private static int _ie_toNum (final char c)
  {
    return c - 'A' + 1;
  }

  private static boolean _ie_isV1 (@Nonnull final char [] c)
  {
    if (c.length != 8)
      return false;
    if (!_isNum (c[0]))
      return false;
    if (!_ie_is2 (c[1]))
      return false;
    for (int i = 2; i <= 6; ++i)
      if (!_isNum (c[i]))
        return false;
    if (!_ie_is8 (c[7]))
      return false;
    final int r = (0 * 8 +
                   _toInt (c[2]) * 7 +
                   _toInt (c[3]) * 6 +
                   _toInt (c[4]) * 5 +
                   _toInt (c[5]) * 4 +
                   _toInt (c[6]) * 3 +
                   _toInt (c[0]) * 2) %
                  23;

    final char cCheck = _ie_checkChar (r);
    return c[7] == cCheck;
  }

  private static boolean _ie_isV2 (@Nonnull final char [] c)
  {
    if (c.length != 8)
      return false;
    for (int i = 0; i <= 6; ++i)
      if (!_isNum (c[i]))
        return false;
    if (!_ie_is8 (c[7]))
      return false;
    final int r = (_toInt (c[0]) * 8 +
                   _toInt (c[1]) * 7 +
                   _toInt (c[2]) * 6 +
                   _toInt (c[3]) * 5 +
                   _toInt (c[4]) * 4 +
                   _toInt (c[5]) * 3 +
                   _toInt (c[6]) * 2) %
                  23;

    final char cCheck = _ie_checkChar (r);
    return c[7] == cCheck;
  }

  public static boolean isValidVATIN_IE (@Nonnull final String sVATIN)
  {
    ValueEnforcer.notNull (sVATIN, "VATIN");
    final char [] c = sVATIN.toCharArray ();

    // Version 1 (old Style)
    if (_ie_isV1 (c))
      return true;

    // Version 2 (new Style 8 characters)
    if (_ie_isV2 (c))
      return true;

    // Version 3 (new Style 9 characters)
    if (c.length != 9)
      return false;
    for (int i = 0; i <= 6; ++i)
      if (!_isNum (c[i]))
        return false;
    if (!_ie_is8 (c[7]))
      return false;
    if (!_ie_is9 (c[8]))
      return false;
    final int r = (_toInt (c[0]) * 8 +
                   _toInt (c[1]) * 7 +
                   _toInt (c[2]) * 6 +
                   _toInt (c[3]) * 5 +
                   _toInt (c[4]) * 4 +
                   _toInt (c[5]) * 3 +
                   _toInt (c[6]) * 2 +
                   _ie_toNum (c[8]) * 9) %
                  23;

    final char cCheck = _ie_checkChar (r);
    return c[7] == cCheck;
  }

  private static int _it_d (final char c)
  {
    final int n = _toInt (c);
    return n / 5 + (2 * n) % 10;
  }

  public static boolean isValidVATIN_IT (@Nonnull final String sVATIN)
  {
    ValueEnforcer.notNull (sVATIN, "VATIN");
    final char [] c = sVATIN.toCharArray ();

    if (c.length != 11)
      return false;
    for (int i = 0; i <= 10; ++i)
      if (!_isNum (c[i]))
        return false;

    final int v = _toInt (c[7], c[8], c[9]);
    if (!((v > 0 && v < 101) || v == 120 || v == 121 || v == 999 || v == 888))
      return false;

    final int s1 = _toInt (c[0]) + _toInt (c[2]) + _toInt (c[4]) + _toInt (c[6]) + _toInt (c[8]);
    final int s2 = _it_d (c[1]) + _it_d (c[3]) + _it_d (c[5]) + _it_d (c[7]) + _it_d (c[9]);
    final int nExpected = (10 - (s1 + s2) % 10) % 10;
    return _toInt (c[10]) == nExpected;
  }
}
