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
package com.helger.tenancy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;

import org.junit.Test;

import com.helger.base.id.factory.GlobalIDFactory;
import com.helger.base.type.ObjectType;
import com.helger.datetime.helper.PDTFactory;
import com.helger.typeconvert.collection.IStringMap;

/**
 * Test class for class {@link IBusinessObject}
 *
 * @author Philip Helger
 */
public final class IBusinessObjectTest
{
  private static final class MockBO implements IBusinessObject
  {
    private static final ObjectType OT = new ObjectType ("mock-bo");
    private final String m_sID;
    String m_sCreationUserID = null;
    LocalDateTime m_aCreationDT;
    String m_sLastModUserID = null;
    LocalDateTime m_aLastModDT;
    String m_sDeletionUserID = null;
    LocalDateTime m_aDeletionDT;

    public MockBO ()
    {
      m_sID = GlobalIDFactory.getNewStringID ();
    }

    public ObjectType getObjectType ()
    {
      return OT;
    }

    public String getID ()
    {
      return m_sID;
    }

    public String getCreationUserID ()
    {
      return m_sCreationUserID;
    }

    public LocalDateTime getCreationDateTime ()
    {
      return m_aCreationDT;
    }

    public String getLastModificationUserID ()
    {
      return m_sLastModUserID;
    }

    public LocalDateTime getLastModificationDateTime ()
    {
      return m_aLastModDT;
    }

    public String getDeletionUserID ()
    {
      return m_sDeletionUserID;
    }

    public LocalDateTime getDeletionDateTime ()
    {
      return m_aDeletionDT;
    }

    public IStringMap attrs ()
    {
      throw new UnsupportedOperationException ();
    }
  }

  @Test
  public void testGetLastChangeDate ()
  {
    final LocalDateTime aNow = PDTFactory.getCurrentLocalDateTime ();
    final LocalDateTime aT2 = aNow.plusMinutes (1);
    final LocalDateTime aT3 = aT2.plusMinutes (1);

    final MockBO aBO = new MockBO ();
    assertNull (aBO.getLastChangeDateTime ());

    aBO.m_aCreationDT = aNow;
    assertEquals (aNow, aBO.getLastChangeDateTime ());

    aBO.m_aLastModDT = aNow;
    assertEquals (aNow, aBO.getLastChangeDateTime ());

    aBO.m_aDeletionDT = aNow;
    assertEquals (aNow, aBO.getLastChangeDateTime ());

    aBO.m_aDeletionDT = aNow;
    assertEquals (aNow, aBO.getLastChangeDateTime ());

    aBO.m_aCreationDT = aNow;
    aBO.m_aLastModDT = aT2;
    aBO.m_aDeletionDT = aT3;
    assertEquals (aT3, aBO.getLastChangeDateTime ());

    aBO.m_aCreationDT = null;
    aBO.m_aLastModDT = aT2;
    aBO.m_aDeletionDT = aT3;
    assertEquals (aT3, aBO.getLastChangeDateTime ());

    aBO.m_aCreationDT = null;
    aBO.m_aLastModDT = null;
    aBO.m_aDeletionDT = aT3;
    assertEquals (aT3, aBO.getLastChangeDateTime ());

    aBO.m_aCreationDT = null;
    aBO.m_aLastModDT = aT2;
    aBO.m_aDeletionDT = null;
    assertEquals (aT2, aBO.getLastChangeDateTime ());
  }
}
