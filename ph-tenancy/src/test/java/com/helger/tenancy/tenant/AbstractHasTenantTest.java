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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.jspecify.annotations.NonNull;
import org.junit.Test;

import com.helger.tenancy.mock.MockTenant;
import com.helger.unittest.support.TestHelper;

/**
 * Test class for class {@link AbstractHasTenant}
 *
 * @author Philip Helger
 */
public final class AbstractHasTenantTest
{
  private static final class MockHasTenant extends AbstractHasTenant
  {
    MockHasTenant (@NonNull final ITenant aTenant)
    {
      super (aTenant);
    }
  }

  @Test
  public void testBasic ()
  {
    final ITenant aTenant = new MockTenant ("tenant1", "Tenant 1");
    final MockHasTenant aObj = new MockHasTenant (aTenant);
    assertSame (aTenant, aObj.getTenant ());
    assertTrue (aObj.hasTenant ());
    assertEquals ("tenant1", aObj.getTenantID ());
    assertTrue (aObj.hasTenantID ());
    assertTrue (aObj.hasSameTenant (aTenant));
    assertTrue (aObj.hasSameTenantID ("tenant1"));
  }

  @Test
  public void testNullTenant ()
  {
    try
    {
      new MockHasTenant (null);
      fail ();
    }
    catch (final NullPointerException ex)
    {
      // expected
    }
  }

  @Test
  public void testEqualsHashCodeToString ()
  {
    TestHelper.testDefaultImplementationWithEqualContentObject (new MockHasTenant (new MockTenant ("tenant1",
                                                                                                   "Tenant 1")),
                                                                new MockHasTenant (new MockTenant ("tenant1",
                                                                                                   "Tenant 1")));
    TestHelper.testDefaultImplementationWithDifferentContentObject (new MockHasTenant (new MockTenant ("tenant1",
                                                                                                       "Tenant 1")),
                                                                    new MockHasTenant (new MockTenant ("tenant2",
                                                                                                       "Tenant 2")));
  }
}
