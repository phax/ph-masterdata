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
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

import com.helger.tenancy.mock.MockTenant;

/**
 * Test class for class {@link ITenant}
 *
 * @author Philip Helger
 */
public final class ITenantTest
{
  @Test
  public void testIsGlobalTenant ()
  {
    final ITenant aGlobal = new MockTenant (CTenant.GLOBAL_TENANT_ID, CTenant.GLOBAL_TENANT_NAME);
    assertTrue (aGlobal.isGlobalTenant ());

    final ITenant aOther = new MockTenant ("tenant1", "Tenant 1");
    assertFalse (aOther.isGlobalTenant ());
  }

  @Test
  public void testBasic ()
  {
    final ITenant aTenant = new MockTenant ("tenant1", "Tenant 1");
    assertEquals ("tenant1", aTenant.getID ());
    assertEquals ("Tenant 1", aTenant.getDisplayName ());
    assertEquals ("Tenant 1", aTenant.getAsUIText (Locale.GERMANY));
    assertTrue (aTenant.isNotDeleted ());
  }
}
