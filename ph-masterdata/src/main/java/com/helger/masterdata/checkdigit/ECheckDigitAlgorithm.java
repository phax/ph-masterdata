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
import com.helger.base.lang.EnumHelper;

/**
 * All predefined check digit algorithms with stable IDs. Each enum constant delegates to the
 * underlying algorithm instance, which is available via {@link #getAlgorithm()}.
 *
 * @author Philip Helger
 * @since 8.3.0
 */
public enum ECheckDigitAlgorithm implements ICheckDigitAlgorithm
{
  /** ISO 7064 MOD 11-2 - see {@link ISO7064PureSystem#MOD_11_2} */
  ISO7064_MOD_11_2 (ISO7064PureSystem.MOD_11_2),
  /** ISO 7064 MOD 37-2 - see {@link ISO7064PureSystem#MOD_37_2} */
  ISO7064_MOD_37_2 (ISO7064PureSystem.MOD_37_2),
  /** ISO 7064 MOD 97-10 - see {@link ISO7064PureSystem#MOD_97_10} */
  ISO7064_MOD_97_10 (ISO7064PureSystem.MOD_97_10),
  /** ISO 7064 MOD 97-10 with ISO 13616 letter conversion - see {@link ISO7064PureSystem#MOD_97_10_ALPHA} */
  ISO7064_MOD_97_10_ALPHA (ISO7064PureSystem.MOD_97_10_ALPHA),
  /** ISO 7064 MOD 11,10 - see {@link ISO7064HybridSystem#MOD_11_10} */
  ISO7064_MOD_11_10 (ISO7064HybridSystem.MOD_11_10),
  /** ISO 7064 MOD 37,36 - see {@link ISO7064HybridSystem#MOD_37_36} */
  ISO7064_MOD_37_36 (ISO7064HybridSystem.MOD_37_36),
  /** Luhn - see {@link LuhnCheckDigit} */
  LUHN (LuhnCheckDigit.INSTANCE),
  /** GS1 Mod 10 - see {@link GS1CheckDigit} */
  GS1_MOD_10 (GS1CheckDigit.INSTANCE);

  private final ICheckDigitAlgorithm m_aAlgorithm;

  ECheckDigitAlgorithm (@NonNull final ICheckDigitAlgorithm aAlgorithm)
  {
    m_aAlgorithm = aAlgorithm;
  }

  /**
   * @return The underlying algorithm instance. Never <code>null</code>.
   */
  @NonNull
  public ICheckDigitAlgorithm getAlgorithm ()
  {
    return m_aAlgorithm;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_aAlgorithm.getID ();
  }

  @Nonnegative
  public int getCheckCharCount ()
  {
    return m_aAlgorithm.getCheckCharCount ();
  }

  public boolean isValidPayload (@Nullable final String sPayload)
  {
    return m_aAlgorithm.isValidPayload (sPayload);
  }

  @Nullable
  public String calculateCheckChars (@Nullable final String sPayload)
  {
    return m_aAlgorithm.calculateCheckChars (sPayload);
  }

  public boolean isValid (@Nullable final String sValue)
  {
    return m_aAlgorithm.isValid (sValue);
  }

  /**
   * Find the algorithm with the provided ID.
   *
   * @param sID
   *        The ID to search. May be <code>null</code>.
   * @return <code>null</code> if no such algorithm exists.
   */
  @Nullable
  public static ECheckDigitAlgorithm getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (ECheckDigitAlgorithm.class, sID);
  }
}
