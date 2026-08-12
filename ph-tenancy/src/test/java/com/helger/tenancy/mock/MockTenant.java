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
import java.util.Locale;

import org.jspecify.annotations.NonNull;

import com.helger.annotation.Nonempty;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.base.type.ObjectType;
import com.helger.tenancy.AbstractBusinessObject;
import com.helger.tenancy.tenant.ITenant;

/**
 * Mock implementation of {@link ITenant} for testing purposes.
 *
 * @author Philip Helger
 */
public class MockTenant extends AbstractBusinessObject implements ITenant
{
  public static final ObjectType OT = new ObjectType ("mock-tenant");
  /** A fixed creation date time, so that all derived texts are deterministic */
  public static final LocalDateTime CREATION_DT = LocalDateTime.of (2020, Month.JANUARY, 1, 12, 0, 0);
  public static final String CREATION_USER_ID = "mock-creator";

  private final String m_sDisplayName;

  public MockTenant (@NonNull @Nonempty final String sID, @NonNull @Nonempty final String sDisplayName)
  {
    super (sID, CREATION_DT, CREATION_USER_ID, null, null, null, null, null);
    m_sDisplayName = ValueEnforcer.notEmpty (sDisplayName, "DisplayName");
  }

  @NonNull
  public ObjectType getObjectType ()
  {
    return OT;
  }

  @NonNull
  @Nonempty
  public String getDisplayName ()
  {
    return m_sDisplayName;
  }

  @NonNull
  @Nonempty
  public String getAsUIText (@NonNull final Locale aDisplayLocale)
  {
    return m_sDisplayName;
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("DisplayName", m_sDisplayName).getToString ();
  }
}
