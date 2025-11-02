/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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

import java.time.LocalDateTime;
import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.Immutable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.hashcode.IHashCodeGenerator;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.tenancy.AbstractBusinessObject;
import com.helger.tenancy.IBusinessObject;

/**
 * Abstract base implementation of {@link ITenantObject}.
 * 
 * @author Philip Helger
 */
@Immutable
public abstract class AbstractTenantObject extends AbstractBusinessObject implements ITenantObject
{
  private final ITenant m_aTenant;
  // Status vars
  private transient int m_nHashCode = IHashCodeGenerator.ILLEGAL_HASHCODE;

  protected AbstractTenantObject (@NonNull final ITenantObject aBase)
  {
    super (aBase);
    m_aTenant = aBase.getTenant ();
    // Recalculate hash code, if this implementation class is different from the
    // passed implementation class
  }

  public AbstractTenantObject (@NonNull final ITenant aTenant, @NonNull final IBusinessObject aObject)
  {
    super (aObject);
    m_aTenant = ValueEnforcer.notNull (aTenant, "Tenant");
  }

  public AbstractTenantObject (@NonNull final ITenant aTenant,
                               @NonNull @Nonempty final String sID,
                               @NonNull final LocalDateTime aCreationDT,
                               @Nullable final String sCreationUserID,
                               @Nullable final LocalDateTime aLastModificationDT,
                               @Nullable final String sLastModificationUserID,
                               @Nullable final LocalDateTime aDeletionDT,
                               @Nullable final String sDeletionUserID)
  {
    super (sID,
           aCreationDT,
           sCreationUserID,
           aLastModificationDT,
           sLastModificationUserID,
           aDeletionDT,
           sDeletionUserID,
           (Map <String, String>) null);
    m_aTenant = ValueEnforcer.notNull (aTenant, "Tenant");
  }

  @NonNull
  public final ITenant getTenant ()
  {
    return m_aTenant;
  }

  @Override
  public final boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final AbstractTenantObject rhs = (AbstractTenantObject) o;
    return m_aTenant.equals (rhs.m_aTenant) && getID ().equals (rhs.getID ());
  }

  @Override
  public final int hashCode ()
  {
    int ret = m_nHashCode;
    if (ret == IHashCodeGenerator.ILLEGAL_HASHCODE)
      ret = m_nHashCode = new HashCodeGenerator (this).append (m_aTenant).append (getID ()).getHashCode ();
    return ret;
  }

  @Override
  public String toString ()
  {
    return ToStringGenerator.getDerived (super.toString ()).append ("Tenant", m_aTenant).getToString ();
  }
}
