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

import javax.annotation.Nullable;

import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.string.StringHelper;

/**
 * Base interface for objects that have a tenant ID.
 *
 * @author Philip Helger
 */
public interface IHasTenantID
{
  /**
   * @return The tenant ID or <code>null</code>.
   */
  @Nullable
  String getTenantID ();

  /**
   * @return <code>true</code> if a tenant ID is present, <code>false</code> if
   *         not.
   * @since 6.1.1
   */
  default boolean hasTenantID ()
  {
    return StringHelper.hasText (getTenantID ());
  }

  /**
   * Check if the passed tenant ID has the same ID as this object
   *
   * @param sTenantID
   *        The tenant ID to check. May be <code>null</code>.
   * @return <code>true</code> if this object and the passed object have the
   *         same tenant ID
   */
  default boolean hasSameTenantID (@Nullable final String sTenantID)
  {
    return EqualsHelper.equals (getTenantID (), sTenantID);
  }
}
