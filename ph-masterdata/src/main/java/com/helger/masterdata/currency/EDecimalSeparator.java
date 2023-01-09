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
package com.helger.masterdata.currency;

import javax.annotation.Nullable;

import com.helger.commons.lang.EnumHelper;

/**
 * Represents the different possible decimal separators.
 *
 * @author Philip Helger
 */
public enum EDecimalSeparator
{
  COMMA (','),
  POINT ('.');

  private final char m_cChar;

  EDecimalSeparator (final char c)
  {
    m_cChar = c;
  }

  public char getChar ()
  {
    return m_cChar;
  }

  @Nullable
  public static EDecimalSeparator getFromCharOrNull (final char cSep)
  {
    return getFromCharOrDefault (cSep, null);
  }

  @Nullable
  public static EDecimalSeparator getFromCharOrDefault (final char cSep, @Nullable final EDecimalSeparator eDefault)
  {
    return EnumHelper.findFirst (EDecimalSeparator.class, eSep -> eSep.getChar () == cSep, eDefault);
  }
}
