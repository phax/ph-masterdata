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
package com.helger.tenancy.tenant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.tenancy.mock.MockTenant;
import com.helger.tenancy.mock.MockTenantObject;
import com.helger.unittest.support.TestHelper;

/**
 * Test class for class {@link AbstractTenantObject}
 *
 * @author Philip Helger
 */
public final class AbstractTenantObjectTest
{
  @Test
  public void testBasic ()
  {
    final ITenant aTenant = new MockTenant ("tenant1", "Tenant 1");
    final MockTenantObject aObj = new MockTenantObject (aTenant, "obj1");
    assertSame (aTenant, aObj.getTenant ());
    assertTrue (aObj.hasTenant ());
    assertEquals ("tenant1", aObj.getTenantID ());
    assertTrue (aObj.hasTenantID ());
    assertEquals ("obj1", aObj.getID ());
    assertEquals (MockTenantObject.CREATION_DT, aObj.getCreationDateTime ());
    assertEquals (MockTenantObject.CREATION_USER_ID, aObj.getCreationUserID ());
    assertTrue (aObj.isNotDeleted ());

    assertTrue (aObj.hasSameTenant (aTenant));
    assertTrue (aObj.hasSameTenantID (aObj));
    assertTrue (aObj.hasSameTenantID (new MockTenantObject (aTenant, "obj2")));
    assertFalse (aObj.hasSameTenantID (new MockTenantObject (new MockTenant ("tenant2", "Tenant 2"), "obj2")));
  }

  @Test
  public void testNullTenant ()
  {
    try
    {
      new MockTenantObject (null, "obj1");
      fail ();
    }
    catch (final NullPointerException ex)
    {
      // expected
    }
  }

  @Test
  public void testCopyConstructor ()
  {
    final ITenant aTenant = new MockTenant ("tenant1", "Tenant 1");
    final MockTenantObject aObj = new MockTenantObject (aTenant, "obj1");
    aObj.attrs ().putIn ("key1", "value1");

    final MockTenantObject aCopy = new MockTenantObject (aObj);
    assertSame (aTenant, aCopy.getTenant ());
    assertEquals (aObj.getID (), aCopy.getID ());
    assertEquals (aObj.getCreationDateTime (), aCopy.getCreationDateTime ());
    assertEquals (aObj.attrs (), aCopy.attrs ());
    TestHelper.testDefaultImplementationWithEqualContentObject (aObj, aCopy);
  }

  @Test
  public void testEqualsHashCodeToString ()
  {
    final ITenant aTenant1 = new MockTenant ("tenant1", "Tenant 1");
    final ITenant aTenant2 = new MockTenant ("tenant2", "Tenant 2");

    // Same tenant, same ID
    TestHelper.testDefaultImplementationWithEqualContentObject (new MockTenantObject (aTenant1, "obj1"),
                                                                new MockTenantObject (aTenant1, "obj1"));
    // Same tenant, different ID
    TestHelper.testDefaultImplementationWithDifferentContentObject (new MockTenantObject (aTenant1, "obj1"),
                                                                    new MockTenantObject (aTenant1, "obj2"));
    // Different tenant, same ID
    TestHelper.testDefaultImplementationWithDifferentContentObject (new MockTenantObject (aTenant1, "obj1"),
                                                                    new MockTenantObject (aTenant2, "obj1"));
  }
}
