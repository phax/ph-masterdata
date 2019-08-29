package com.helger.masterdata.ean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

import com.helger.commons.locale.country.CountryCache;
import com.helger.commons.string.StringHelper;

/**
 * Test class for class {@link EGS1Prefix}.
 *
 * @author Philip Helger
 */
public class EGS1PrefixTest
{
  @Test
  public void testBasic ()
  {
    for (final EGS1Prefix e : EGS1Prefix.values ())
    {
      final String sFrom = e.getFrom ();
      final String sTo = e.getTo ();
      final String sDesc = e.getDescription ();
      final String sCC = e.getCountryCode ();

      assertTrue (StringHelper.hasText (sFrom));
      if (sTo != null)
        assertEquals (sFrom.length (), sTo.length ());
      assertTrue (StringHelper.hasText (sDesc));
      if (sCC != null)
      {
        final Locale aC = CountryCache.getInstance ().getCountry (sCC);
        assertNotNull ("No such country '" + sCC + "'", aC);
        assertNotEquals (sCC, aC.getDisplayCountry ());
      }
    }
  }
}
