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
package com.helger.tenancy.mock;

import java.time.LocalDateTime;
import java.time.Month;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonempty;
import com.helger.base.type.ObjectType;
import com.helger.tenancy.IBusinessObject;
import com.helger.tenancy.tenant.AbstractTenantObject;
import com.helger.tenancy.tenant.ITenant;
import com.helger.tenancy.tenant.ITenantObject;

/**
 * Mock implementation of {@link ITenantObject} for testing purposes.
 *
 * @author Philip Helger
 */
public class MockTenantObject extends AbstractTenantObject
{
  public static final ObjectType OT = new ObjectType ("mock-tenant-object");
  /** A fixed creation date time, so that all derived texts are deterministic */
  public static final LocalDateTime CREATION_DT = LocalDateTime.of (2020, Month.FEBRUARY, 2, 13, 0, 0);
  public static final String CREATION_USER_ID = "mock-creator";

  public MockTenantObject (@NonNull final ITenantObject aBase)
  {
    super (aBase);
  }

  public MockTenantObject (@NonNull final ITenant aTenant, @NonNull final IBusinessObject aObject)
  {
    super (aTenant, aObject);
  }

  public MockTenantObject (@NonNull final ITenant aTenant, @NonNull @Nonempty final String sID)
  {
    super (aTenant, sID, CREATION_DT, CREATION_USER_ID, null, null, null, null);
  }

  @NonNull
  public ObjectType getObjectType ()
  {
    return OT;
  }
}
