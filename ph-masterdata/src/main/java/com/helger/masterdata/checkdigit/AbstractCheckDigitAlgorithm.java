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
package com.helger.masterdata.checkdigit;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.annotation.Nonnegative;
import com.helger.annotation.concurrent.Immutable;
import com.helger.base.enforce.ValueEnforcer;
import com.helger.base.tostring.ToStringGenerator;

/**
 * Abstract base class for {@link ICheckDigitAlgorithm} implementations, holding the ID and the
 * number of check characters. The default implementation of {@link #isValid(String)} splits the
 * value into payload and check characters and compares the check characters with the result of
 * {@link #calculateCheckChars(String)}.
 *
 * @author Philip Helger
 * @since 8.3.0
 */
@Immutable
public abstract class AbstractCheckDigitAlgorithm implements ICheckDigitAlgorithm
{
  private final String m_sID;
  private final int m_nCheckCharCount;

  protected AbstractCheckDigitAlgorithm (@NonNull @Nonempty final String sID, @Nonnegative final int nCheckCharCount)
  {
    ValueEnforcer.notEmpty (sID, "ID");
    ValueEnforcer.isGT0 (nCheckCharCount, "CheckCharCount");
    m_sID = sID;
    m_nCheckCharCount = nCheckCharCount;
  }

  @NonNull
  @Nonempty
  public final String getID ()
  {
    return m_sID;
  }

  @Nonnegative
  public final int getCheckCharCount ()
  {
    return m_nCheckCharCount;
  }

  public boolean isValid (@Nullable final String sValue)
  {
    if (sValue == null || sValue.length () <= m_nCheckCharCount)
      return false;

    final int nPayloadLen = sValue.length () - m_nCheckCharCount;
    final String sExpected = calculateCheckChars (sValue.substring (0, nPayloadLen));
    return sExpected != null && sValue.regionMatches (nPayloadLen, sExpected, 0, m_nCheckCharCount);
  }

  @Override
  public String toString ()
  {
    return new ToStringGenerator (this).append ("ID", m_sID)
                                       .append ("CheckCharCount", m_nCheckCharCount)
                                       .getToString ();
  }
}
