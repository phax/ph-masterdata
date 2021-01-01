/**
 * Copyright (C) 2014-2021 Philip Helger (www.helger.com)
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
package com.helger.tenancy.accarea;

import javax.annotation.Nullable;

import com.helger.tenancy.tenant.IHasTenant;

/**
 * Base interface for objects that have an accounting area.
 *
 * @author Philip Helger
 */
public interface IHasAccountingArea extends IHasTenant, IHasAccountingAreaID
{
  /**
   * @return The accounting area or <code>null</code>.
   */
  @Nullable
  IAccountingArea getAccountingArea ();

  /**
   * @return The accounting area ID to which the object is assigned to. May be
   *         <code>null</code>.
   * @see #getAccountingArea()
   */
  @Nullable
  default String getAccountingAreaID ()
  {
    final IAccountingArea aAccountingArea = getAccountingArea ();
    return aAccountingArea == null ? null : aAccountingArea.getID ();
  }

  /**
   * Check if the passed object has the same tenant ID and the same accounting
   * area ID as this object
   *
   * @param aAccountingArea
   *        The object to check. May be <code>null</code>.
   * @return <code>true</code> if this object and the passed object have the
   *         same tenant ID <b>and</b> the same accounting area ID
   */
  boolean hasSameTenantAndAccountingAreaID (@Nullable IAccountingArea aAccountingArea);

  /**
   * Check if the passed object has the same tenant ID and the same accounting
   * area ID as this object
   *
   * @param aAccountingAreaObject
   *        The object to check. May be <code>null</code>.
   * @return <code>true</code> if this object and the passed object have the
   *         same tenant ID <b>and</b> the same accounting area ID
   */
  boolean hasSameTenantAndAccountingAreaID (@Nullable IAccountingAreaObject aAccountingAreaObject);
}
