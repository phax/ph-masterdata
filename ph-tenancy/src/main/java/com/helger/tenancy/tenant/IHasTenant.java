/*
 * Copyright (C) 2014-2023 Philip Helger (www.helger.com)
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

import javax.annotation.Nullable;

import com.helger.commons.equals.EqualsHelper;

/**
 * Base interface for objects that have a tenant.
 *
 * @author Philip Helger
 */
public interface IHasTenant extends IHasTenantID
{
  /**
   * @return The tenant or <code>null</code>.
   */
  @Nullable
  ITenant getTenant ();

  /**
   * @return The tenant or <code>null</code>.
   * @since 6.1.1
   */
  default boolean hasTenant ()
  {
    return getTenant () != null;
  }

  @Nullable
  default String getTenantID ()
  {
    final ITenant aTenant = getTenant ();
    return aTenant == null ? null : aTenant.getID ();
  }

  default boolean hasTenantID ()
  {
    return getTenant () != null;
  }

  /**
   * Check if the passed object has the same tenant ID as this object
   *
   * @param aObj
   *        The object to check. May be <code>null</code>.
   * @return <code>true</code> if this object and the passed object (if not
   *         <code>null</code>) have the same tenant ID
   */
  default boolean hasSameTenantID (@Nullable final ITenantObject aObj)
  {
    return aObj != null && hasSameTenantID (aObj.getTenantID ());
  }

  /**
   * Check if the passed tenant has the same ID as this object
   *
   * @param aTenant
   *        The tenant to check. May be <code>null</code>.
   * @return <code>true</code> if this object and the passed object have the
   *         same tenant.
   */
  default boolean hasSameTenant (@Nullable final ITenant aTenant)
  {
    return EqualsHelper.equals (getTenant (), aTenant);
  }
}
