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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.junit.Test;

import com.helger.annotation.Nonempty;
import com.helger.base.CGlobal;
import com.helger.base.type.ObjectType;
import com.helger.collection.commons.CommonsLinkedHashMap;
import com.helger.unittest.support.TestHelper;

/**
 * Test class for class {@link AbstractBusinessObject}
 *
 * @author Philip Helger
 */
public final class AbstractBusinessObjectTest
{
  private static final LocalDateTime CREATION_DT = LocalDateTime.of (2020, Month.JANUARY, 1, 12, 0, 0);
  private static final LocalDateTime MODIFICATION_DT = LocalDateTime.of (2021, Month.FEBRUARY, 2, 13, 0, 0);
  private static final LocalDateTime DELETION_DT = LocalDateTime.of (2022, Month.MARCH, 3, 14, 0, 0);
  private static final LocalDateTime UNDELETION_DT = LocalDateTime.of (2023, Month.APRIL, 4, 15, 0, 0);

  private static final class MockBusinessObject extends AbstractBusinessObject
  {
    private static final ObjectType OT = new ObjectType ("mock-business-object");

    MockBusinessObject (@NonNull final IBusinessObject aObject)
    {
      super (aObject);
    }

    MockBusinessObject (@NonNull @Nonempty final String sID)
    {
      this (sID, null);
    }

    MockBusinessObject (@NonNull @Nonempty final String sID, @Nullable final Map <String, String> aAttrs)
    {
      super (sID, CREATION_DT, "creator", null, null, null, null, aAttrs);
    }

    @NonNull
    public ObjectType getObjectType ()
    {
      return OT;
    }
  }

  @Test
  public void testBasic ()
  {
    final MockBusinessObject aObj = new MockBusinessObject ("id1");
    assertEquals ("id1", aObj.getID ());
    assertEquals (MockBusinessObject.OT, aObj.getObjectType ());

    assertEquals (CREATION_DT, aObj.getCreationDateTime ());
    assertTrue (aObj.hasCreationDateTime ());
    assertEquals ("creator", aObj.getCreationUserID ());
    assertTrue (aObj.hasCreationUserID ());

    assertNull (aObj.getLastModificationDateTime ());
    assertFalse (aObj.hasLastModificationDateTime ());
    assertNull (aObj.getLastModificationUserID ());
    assertFalse (aObj.hasLastModificationUserID ());

    assertNull (aObj.getDeletionDateTime ());
    assertFalse (aObj.hasDeletionDateTime ());
    assertNull (aObj.getDeletionUserID ());
    assertFalse (aObj.hasDeletionUserID ());

    assertFalse (aObj.isDeleted ());
    assertTrue (aObj.isNotDeleted ());
    assertTrue (aObj.attrs ().isEmpty ());
  }

  @Test
  public void testInvalidID ()
  {
    try
    {
      new MockBusinessObject ("");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }

    try
    {
      new MockBusinessObject ((String) null);
      fail ();
    }
    catch (final NullPointerException ex)
    {
      // expected
    }
  }

  @Test
  public void testSetLastModification ()
  {
    final MockBusinessObject aObj = new MockBusinessObject ("id1");
    assertEquals (CREATION_DT, aObj.getLastChangeDateTime ());

    aObj.setLastModification (MODIFICATION_DT, "modifier");
    assertEquals (MODIFICATION_DT, aObj.getLastModificationDateTime ());
    assertEquals ("modifier", aObj.getLastModificationUserID ());
    assertTrue (aObj.hasLastModificationUserID ());
    assertEquals (MODIFICATION_DT, aObj.getLastChangeDateTime ());

    try
    {
      aObj.setLastModification (null, "modifier");
      fail ();
    }
    catch (final NullPointerException ex)
    {
      // expected
    }

    try
    {
      aObj.setLastModification (MODIFICATION_DT, "");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {
      // expected
    }
  }

  @Test
  public void testSetDeletion ()
  {
    final MockBusinessObject aObj = new MockBusinessObject ("id1");
    assertTrue (aObj.setDeletion (DELETION_DT, "deleter").isChanged ());
    assertEquals (DELETION_DT, aObj.getDeletionDateTime ());
    assertEquals ("deleter", aObj.getDeletionUserID ());
    assertTrue (aObj.hasDeletionUserID ());
    assertTrue (aObj.isDeleted ());
    assertFalse (aObj.isNotDeleted ());
    assertEquals (DELETION_DT, aObj.getLastChangeDateTime ());

    // Second deletion changes nothing
    assertTrue (aObj.setDeletion (UNDELETION_DT, "deleter2").isUnchanged ());
    assertEquals (DELETION_DT, aObj.getDeletionDateTime ());
    assertEquals ("deleter", aObj.getDeletionUserID ());

    // A deleted object may not be modified
    try
    {
      aObj.setLastModification (MODIFICATION_DT, "modifier");
      fail ();
    }
    catch (final IllegalStateException ex)
    {
      // expected
    }
  }

  @Test
  public void testSetUndeletion ()
  {
    final MockBusinessObject aObj = new MockBusinessObject ("id1");

    // Not deleted - nothing to undelete
    assertTrue (aObj.setUndeletion (UNDELETION_DT, "undeleter").isUnchanged ());
    assertNull (aObj.getLastModificationDateTime ());

    assertTrue (aObj.setDeletion (DELETION_DT, "deleter").isChanged ());
    assertTrue (aObj.setUndeletion (UNDELETION_DT, "undeleter").isChanged ());

    assertNull (aObj.getDeletionDateTime ());
    assertNull (aObj.getDeletionUserID ());
    assertFalse (aObj.isDeleted ());
    assertTrue (aObj.isNotDeleted ());

    // The undeletion is recorded as last modification
    assertEquals (UNDELETION_DT, aObj.getLastModificationDateTime ());
    assertEquals ("undeleter", aObj.getLastModificationUserID ());
    assertEquals (UNDELETION_DT, aObj.getLastChangeDateTime ());
  }

  @Test
  public void testAttrs ()
  {
    final MockBusinessObject aObj = new MockBusinessObject ("id1");
    assertTrue (aObj.attrs ().putIn ("key1", "value1").isChanged ());
    assertTrue (aObj.attrs ().putIn ("key2", 42).isChanged ());
    assertEquals (2, aObj.attrs ().size ());
    assertEquals ("value1", aObj.attrs ().getValue ("key1"));
    assertEquals (42, aObj.attrs ().getAsInt ("key2"));
    assertEquals (CGlobal.ILLEGAL_UINT, aObj.attrs ().getAsInt ("key-none"));

    // The attributes are copied in the constructor
    final Map <String, String> aAttrs = new CommonsLinkedHashMap <> ();
    aAttrs.put ("key1", "value1");
    final MockBusinessObject aObj2 = new MockBusinessObject ("id2", aAttrs);
    assertEquals (1, aObj2.attrs ().size ());
    aAttrs.put ("key2", "value2");
    assertEquals (1, aObj2.attrs ().size ());
  }

  @Test
  public void testCopyConstructor ()
  {
    final MockBusinessObject aObj = new MockBusinessObject ("id1");
    aObj.setLastModification (MODIFICATION_DT, "modifier");
    aObj.attrs ().putIn ("key1", "value1");

    final MockBusinessObject aCopy = new MockBusinessObject (aObj);
    assertEquals (aObj.getID (), aCopy.getID ());
    assertEquals (aObj.getCreationDateTime (), aCopy.getCreationDateTime ());
    assertEquals (aObj.getCreationUserID (), aCopy.getCreationUserID ());
    assertEquals (aObj.getLastModificationDateTime (), aCopy.getLastModificationDateTime ());
    assertEquals (aObj.getLastModificationUserID (), aCopy.getLastModificationUserID ());
    assertEquals (aObj.getDeletionDateTime (), aCopy.getDeletionDateTime ());
    assertEquals (aObj.getDeletionUserID (), aCopy.getDeletionUserID ());
    assertEquals (aObj.attrs (), aCopy.attrs ());

    TestHelper.testDefaultImplementationWithEqualContentObject (aObj, aCopy);
  }

  @Test
  public void testEqualsHashCodeToString ()
  {
    TestHelper.testDefaultImplementationWithEqualContentObject (new MockBusinessObject ("id1"),
                                                                new MockBusinessObject ("id1"));
    TestHelper.testDefaultImplementationWithDifferentContentObject (new MockBusinessObject ("id1"),
                                                                    new MockBusinessObject ("id2"));
  }
}
