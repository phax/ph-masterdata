package com.helger.tenancy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.LocalDateTime;

import org.junit.Test;

import com.helger.commons.collection.attr.IStringMap;
import com.helger.commons.datetime.PDTFactory;
import com.helger.commons.id.factory.GlobalIDFactory;
import com.helger.commons.type.ObjectType;

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
    String m_sCreationUserID;
    LocalDateTime m_aCreationDT;
    String m_sLastModUserID;
    LocalDateTime m_aLastModDT;
    String m_sDeletionUserID;
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
