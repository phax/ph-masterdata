package com.helger.masterdata.leitwegid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link LeitwegID}.
 *
 * @author Philip Helger
 */
public final class LeitwegIDTest
{
  @Test
  public void testBasic ()
  {
    assertEquals (5, LeitwegID.MIN_TOTAL_LENGTH);
    assertEquals (46, LeitwegID.MAX_TOTAL_LENGTH);
  }

  @Test
  public void testIsLeitwegIDValid ()
  {
    assertFalse (LeitwegID.isLeitwegIDValid (null));
    assertFalse (LeitwegID.isLeitwegIDValid (""));

    assertFalse (LeitwegID.isLeitwegIDValid ("04011000-1234512345-05"));
    assertTrue (LeitwegID.isLeitwegIDValid ("04011000-1234512345-06"));
    assertFalse (LeitwegID.isLeitwegIDValid ("04011000-1234512345-07"));
    assertTrue (LeitwegID.isLeitwegIDValid ("991-99012-32"));
    assertTrue (LeitwegID.isLeitwegIDValid ("991-33333TEST-33"));
    assertTrue (LeitwegID.isLeitwegIDValid ("99661-WEBSERVICEOZG-28"));
  }

  @Test
  public void testCalcLeitwegIDChecksum ()
  {
    assertNull (LeitwegID.calcLeitwegIDChecksum (null));
    assertNull (LeitwegID.calcLeitwegIDChecksum (""));

    assertEquals ("06", LeitwegID.calcLeitwegIDChecksum ("04011000-1234512345"));
    assertEquals ("32", LeitwegID.calcLeitwegIDChecksum ("991-99012"));
  }
}
