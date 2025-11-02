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

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.concurrent.NotThreadSafe;
import com.helger.annotation.style.ReturnsMutableObject;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.hashcode.HashCodeGenerator;
import com.helger.base.hashcode.IHashCodeGenerator;
import com.helger.base.state.EChange;
import com.helger.base.tostring.ToStringGenerator;
import com.helger.typeconvert.collection.StringMap;

/**
 * Abstract base implementation of {@link IBusinessObject} that handles everything except
 * {@link #getObjectType()}.
 *
 * @author Philip Helger
 */
@NotThreadSafe
public abstract class AbstractBusinessObject implements IBusinessObject, Serializable
{
  private final String m_sID;
  private final LocalDateTime m_aCreationDT;
  private final String m_sCreationUserID;
  private LocalDateTime m_aLastModificationDT;
  private String m_sLastModificationUserID;
  private LocalDateTime m_aDeletionDT;
  private String m_sDeletionUserID;
  private final StringMap m_aAttrs = new StringMap ();
  // Status vars
  private transient int m_nHashCode = IHashCodeGenerator.ILLEGAL_HASHCODE;

  public AbstractBusinessObject (@NonNull final IBusinessObject aObject)
  {
    this (aObject.getID (),
          aObject.getCreationDateTime (),
          aObject.getCreationUserID (),
          aObject.getLastModificationDateTime (),
          aObject.getLastModificationUserID (),
          aObject.getDeletionDateTime (),
          aObject.getDeletionUserID (),
          aObject.attrs ());
  }

  public AbstractBusinessObject (@NonNull @Nonempty final String sID,
                                 @Nullable final LocalDateTime aCreationDT,
                                 @Nullable final String sCreationUserID,
                                 @Nullable final LocalDateTime aLastModificationDT,
                                 @Nullable final String sLastModificationUserID,
                                 @Nullable final LocalDateTime aDeletionDT,
                                 @Nullable final String sDeletionUserID,
                                 @Nullable final Map <String, String> aAttrs)
  {
    m_sID = ValueEnforcer.notEmpty (sID, "ID");
    m_aCreationDT = aCreationDT;
    m_sCreationUserID = sCreationUserID;
    m_aLastModificationDT = aLastModificationDT;
    m_sLastModificationUserID = sLastModificationUserID;
    m_aDeletionDT = aDeletionDT;
    m_sDeletionUserID = sDeletionUserID;
    m_aAttrs.putAllIfNotNull (aAttrs);
  }

  @NonNull
  @Nonempty
  public final String getID ()
  {
    return m_sID;
  }

  @Nullable
  public final LocalDateTime getCreationDateTime ()
  {
    return m_aCreationDT;
  }

  @Nullable
  public final String getCreationUserID ()
  {
    return m_sCreationUserID;
  }

  @Nullable
  public final LocalDateTime getLastModificationDateTime ()
  {
    return m_aLastModificationDT;
  }

  @Nullable
  public final String getLastModificationUserID ()
  {
    return m_sLastModificationUserID;
  }

  public final void setLastModification (@NonNull final LocalDateTime aLastModificationDT,
                                         @NonNull @Nonempty final String sLastModificationUserID)
  {
    ValueEnforcer.notNull (aLastModificationDT, "LastModificationDT");
    ValueEnforcer.notEmpty (sLastModificationUserID, "LastModificationUserID");

    if (isDeleted ())
      throw new IllegalStateException ("Object is deleted and can therefore not be modified!");

    m_aLastModificationDT = aLastModificationDT;
    m_sLastModificationUserID = sLastModificationUserID;
  }

  @Nullable
  public final LocalDateTime getDeletionDateTime ()
  {
    return m_aDeletionDT;
  }

  @Nullable
  public final String getDeletionUserID ()
  {
    return m_sDeletionUserID;
  }

  @NonNull
  public final EChange setDeletion (@NonNull final LocalDateTime aDeletionDT,
                                    @NonNull @Nonempty final String sDeletionUserID)
  {
    ValueEnforcer.notNull (aDeletionDT, "DeletionDT");
    ValueEnforcer.notEmpty (sDeletionUserID, "DeletionUserID");

    if (m_aDeletionDT != null)
    {
      // Object is already deleted...
      return EChange.UNCHANGED;
    }

    m_aDeletionDT = aDeletionDT;
    m_sDeletionUserID = sDeletionUserID;
    return EChange.CHANGED;
  }

  @NonNull
  public final EChange setUndeletion (@NonNull final LocalDateTime aUndeletionDT,
                                      @NonNull @Nonempty final String sUndeletionUserID)
  {
    ValueEnforcer.notNull (aUndeletionDT, "UndeletionDT");
    ValueEnforcer.notEmpty (sUndeletionUserID, "UndeletionUserID");

    if (m_aDeletionDT == null)
    {
      // Object is not deleted and can therefore not be undeleted
      return EChange.UNCHANGED;
    }

    m_aDeletionDT = null;
    m_sDeletionUserID = null;

    // Last modification AFTER deletion was reverted
    setLastModification (aUndeletionDT, sUndeletionUserID);
    return EChange.CHANGED;
  }

  @NonNull
  @ReturnsMutableObject
  public final StringMap attrs ()
  {
    return m_aAttrs;
  }

  @Override
  public boolean equals (final Object o)
  {
    if (o == this)
      return true;
    if (o == null || !getClass ().equals (o.getClass ()))
      return false;
    final AbstractBusinessObject rhs = (AbstractBusinessObject) o;
    return getID ().equals (rhs.getID ());
  }

  @Override
  public int hashCode ()
  {
    int ret = m_nHashCode;
    if (ret == IHashCodeGenerator.ILLEGAL_HASHCODE)
      ret = m_nHashCode = new HashCodeGenerator (this).append (getID ()).getHashCode ();
    return ret;
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ID", m_sID)
                                       .appendIfNotNull ("CreationDT", m_aCreationDT)
                                       .appendIfNotNull ("CreationUserID", m_sCreationUserID)
                                       .appendIfNotNull ("LastModificationDT", m_aLastModificationDT)
                                       .appendIfNotNull ("LastModificationUserID", m_sLastModificationUserID)
                                       .appendIfNotNull ("DeletionDT", m_aDeletionDT)
                                       .appendIfNotNull ("DeletionUserID", m_sDeletionUserID)
                                       .append ("Attrs", m_aAttrs)
                                       .getToString ();
  }
}
