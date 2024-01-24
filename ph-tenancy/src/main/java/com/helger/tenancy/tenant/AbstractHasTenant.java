/*
 * Copyright (C) 2014-2024 Philip Helger (www.helger.com)
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

import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.Nonempty;
import com.helger.commons.hashcode.HashCodeGenerator;
import com.helger.commons.string.ToStringGenerator;

/**
 * Abstract base implementation for {@link IHasTenant}.
 *
 * @author Philip Helger
 */
@Immutable
public abstract class AbstractHasTenant implements IHasTenant, Serializable
{
  @Nonnull
  private final ITenant m_aTenant;

  public AbstractHasTenant (@Nonnull final ITenant aTenant)
  {
    m_aTenant = ValueEnforcer.notNull (aTenant, "Tenant");
  }

  @Nonnull
  public final ITenant getTenant ()
  {
    return m_aTenant;
  }

  @Nonnull
  @Nonempty
  @Override
  public final String getTenantID ()
  {
    return m_aTenant.getID ();
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final AbstractHasTenant rhs = (AbstractHasTenant) o;
    return m_aTenant.equals (rhs.m_aTenant);
  }

  @Override
  public int hashCode ()
  {
    return new HashCodeGenerator (this).append (m_aTenant).getHashCode ();
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("Tenant", m_aTenant).getToString ();
  }
}
