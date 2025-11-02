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
package com.helger.tenancy;

import java.time.LocalDateTime;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.style.ReturnsMutableObject;
import com.helger.base.type.ITypedObject;
import com.helger.datetime.util.PDTHelper;
import com.helger.tenancy.datetime.IHasCreationInfo;
import com.helger.tenancy.datetime.IHasDeletionInfo;
import com.helger.tenancy.datetime.IHasLastModificationInfo;
import com.helger.typeconvert.collection.IStringMap;

/**
 * Base interface for all business objects. Has a creation, last modification
 * and deletion user ID and date time.
 *
 * @author Philip Helger
 */
public interface IBusinessObject extends
                                 ITypedObject <String>,
                                 IHasCreationInfo,
                                 IHasLastModificationInfo,
                                 IHasDeletionInfo
{
  /**
   * @return The latest date time that something changed. This is the latest
   *         date time from {@link #getCreationDateTime()},
   *         {@link #getLastModificationDateTime()} and
   *         {@link #getDeletionDateTime()}. It may be <code>null</code> if no
   *         time is defined at all.
   */
  @Nullable
  default LocalDateTime getLastChangeDateTime ()
  {
    final LocalDateTime aCreationDT = getCreationDateTime ();
    LocalDateTime ret = aCreationDT;

    final LocalDateTime aLastModDT = getLastModificationDateTime ();
    if (aLastModDT != null)
    {
      if (ret != null)
        ret = PDTHelper.getMax (ret, aLastModDT);
      else
        ret = aLastModDT;
    }

    final LocalDateTime aDeletionDT = getDeletionDateTime ();
    if (aDeletionDT != null)
    {
      if (ret != null)
        ret = PDTHelper.getMax (ret, aDeletionDT);
      else
        ret = aDeletionDT;
    }
    return ret;
  }

  /**
   * Check if the last change date is after the provider date time.
   *
   * @param aDT
   *        The date time to check against. May not be <code>null</code>.
   * @return <code>true</code> if a last change date time is present and is
   *         after the provided date time.
   * @see #getLastChangeDateTime()
   */
  default boolean isLastChangeAfter (@NonNull final LocalDateTime aDT)
  {
    final LocalDateTime aLastChangeDT = getLastChangeDateTime ();
    return aLastChangeDT != null && aLastChangeDT.isAfter (aDT);
  }

  /**
   * @return <code>true</code> if this object is deleted, <code>false</code> if
   *         not.
   * @see #isNotDeleted()
   */
  default boolean isDeleted ()
  {
    return hasDeletionDateTime ();
  }

  /**
   * @return <code>true</code> if this object is not deleted, <code>false</code>
   *         if it is deleted.
   * @see #isDeleted()
   * @since 6.1.1
   */
  default boolean isNotDeleted ()
  {
    return !hasDeletionDateTime ();
  }

  /**
   * @return Custom attributes. Never <code>null</code>.
   */
  @NonNull
  @ReturnsMutableObject
  IStringMap attrs ();
}
