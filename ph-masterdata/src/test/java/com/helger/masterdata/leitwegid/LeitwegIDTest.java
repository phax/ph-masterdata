package com.helger.masterdata.leitwegid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
  }
}
