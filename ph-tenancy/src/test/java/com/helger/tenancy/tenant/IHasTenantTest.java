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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.tenancy.mock.MockTenant;
import com.helger.tenancy.mock.MockTenantObject;

/**
 * Test class for class {@link IHasTenant}
 *
 * @author Philip Helger
 */
public final class IHasTenantTest
{
  private static final class MockHasTenant implements IHasTenant
  {
    private ITenant m_aTenant;

    public ITenant getTenant ()
    {
      return m_aTenant;
    }
  }

  @Test
  public void testNoTenant ()
  {
    final MockHasTenant aObj = new MockHasTenant ();
    assertNull (aObj.getTenant ());
    assertFalse (aObj.hasTenant ());
    assertNull (aObj.getTenantID ());
    assertFalse (aObj.hasTenantID ());
    assertTrue (aObj.hasSameTenant (null));
    assertTrue (aObj.hasSameTenantID ((String) null));
    assertFalse (aObj.hasSameTenantID ((ITenantObject) null));
  }

  @Test
  public void testWithTenant ()
  {
    final ITenant aTenant1 = new MockTenant ("tenant1", "Tenant 1");
    final ITenant aTenant2 = new MockTenant ("tenant2", "Tenant 2");

    final MockHasTenant aObj = new MockHasTenant ();
    aObj.m_aTenant = aTenant1;
    assertTrue (aObj.hasTenant ());
    assertEquals ("tenant1", aObj.getTenantID ());
    assertTrue (aObj.hasTenantID ());

    assertTrue (aObj.hasSameTenant (aTenant1));
    assertTrue (aObj.hasSameTenant (new MockTenant ("tenant1", "Tenant 1")));
    assertFalse (aObj.hasSameTenant (aTenant2));
    assertFalse (aObj.hasSameTenant (null));

    assertTrue (aObj.hasSameTenantID ("tenant1"));
    assertFalse (aObj.hasSameTenantID ("tenant2"));
    assertFalse (aObj.hasSameTenantID ((String) null));

    assertTrue (aObj.hasSameTenantID (new MockTenantObject (aTenant1, "obj1")));
    assertFalse (aObj.hasSameTenantID (new MockTenantObject (aTenant2, "obj1")));
    assertFalse (aObj.hasSameTenantID ((ITenantObject) null));
  }
}
