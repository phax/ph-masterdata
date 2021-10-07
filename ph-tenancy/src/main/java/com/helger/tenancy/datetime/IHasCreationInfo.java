/*
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
package com.helger.tenancy.datetime;

import javax.annotation.Nullable;

import com.helger.commons.string.StringHelper;
import com.helger.datetime.domain.IHasCreationDateTime;

/**
 * Base interface for an object that has a date time and a user ID.
 *
 * @author Philip Helger
 */
public interface IHasCreationInfo extends IHasCreationDateTime
{
  /**
   * @return The user ID who created the object. May be <code>null</code>.
   */
  @Nullable
  String getCreationUserID ();

  /**
   * @return <code>true</code> if a creation user is present, <code>false</code>
   *         if not.
   * @since 6.1.1
   */
  default boolean hasCreationUserID ()
  {
    return StringHelper.hasText (getCreationUserID ());
  }
}
