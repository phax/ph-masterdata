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
package com.helger.tenancy.accarea;

import javax.annotation.Nullable;

import com.helger.commons.equals.EqualsHelper;
import com.helger.commons.string.StringHelper;

/**
 * Base interface for objects that have an accounting area ID.
 *
 * @author Philip Helger
 */
public interface IHasAccountingAreaID
{
  /**
   * @return The accounting area ID or <code>null</code>.
   */
  @Nullable
  String getAccountingAreaID ();

  /**
   * @return <code>true</code> if an accounting area ID is present,
   *         <code>false</code> if not.
   * @since 6.1.1
   */
  default boolean hasAccountingAreaID ()
  {
    return StringHelper.hasText (getAccountingAreaID ());
  }

  /**
   * Check if the passed accounting area ID has the same ID as this object
   *
   * @param sAccountingAreaID
   *        The accounting area ID to check. May be <code>null</code>.
   * @return <code>true</code> if this object and the passed object have the
   *         same accounting area ID
   * @since 6.1.1
   */
  default boolean hasSameAccountingAreaID (@Nullable final String sAccountingAreaID)
  {
    return EqualsHelper.equals (getAccountingAreaID (), sAccountingAreaID);
  }
}
