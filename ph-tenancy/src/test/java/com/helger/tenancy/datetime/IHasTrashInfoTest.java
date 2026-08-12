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
package com.helger.tenancy.datetime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

/**
 * Test class for class {@link IHasTrashInfo}
 *
 * @author Philip Helger
 */
public final class IHasTrashInfoTest
{
  private static final LocalDateTime TRASH_DT = LocalDateTime.of (2021, Month.JUNE, 15, 10, 30, 0);

  private static final class MockHasTrashInfo implements IHasTrashInfo
  {
    private LocalDateTime m_aTrashDT;
    private String m_sTrashUserID;

    public LocalDateTime getTrashDateTime ()
    {
      return m_aTrashDT;
    }

    public String getTrashUserID ()
    {
      return m_sTrashUserID;
    }
  }

  @Test
  public void testNotTrashed ()
  {
    final MockHasTrashInfo aObj = new MockHasTrashInfo ();
    assertNull (aObj.getTrashDateTime ());
    assertFalse (aObj.hasTrashDateTime ());
    assertNull (aObj.getTrashDate ());
    assertNull (aObj.getTrashTime ());
    assertNull (aObj.getTrashUserID ());
    assertFalse (aObj.hasTrashUserID ());
    assertFalse (aObj.isTrashedAt (TRASH_DT));
  }

  @Test
  public void testTrashed ()
  {
    final MockHasTrashInfo aObj = new MockHasTrashInfo ();
    aObj.m_aTrashDT = TRASH_DT;
    aObj.m_sTrashUserID = "trasher";

    assertEquals (TRASH_DT, aObj.getTrashDateTime ());
    assertTrue (aObj.hasTrashDateTime ());
    assertEquals (TRASH_DT.toLocalDate (), aObj.getTrashDate ());
    assertEquals (TRASH_DT.toLocalTime (), aObj.getTrashTime ());
    assertEquals ("trasher", aObj.getTrashUserID ());
    assertTrue (aObj.hasTrashUserID ());

    assertTrue (aObj.isTrashedAt (TRASH_DT));
    assertTrue (aObj.isTrashedAt (TRASH_DT.plusSeconds (1)));
    assertFalse (aObj.isTrashedAt (TRASH_DT.minusSeconds (1)));

    // An empty user ID is not a user ID
    aObj.m_sTrashUserID = "";
    assertFalse (aObj.hasTrashUserID ());
  }
}
