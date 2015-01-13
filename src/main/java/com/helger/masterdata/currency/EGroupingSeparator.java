/**
 * Copyright (C) 2014-2015 Philip Helger (www.helger.com)
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

/**
 * Represents the different possible grouping separators.
 *
 * @author Philip Helger
 */
public enum EGroupingSeparator
{
  APOSTROPHE ('\''),
  NO_BREAK_SPACE ('\u00a0'),
  COMMA (','),
  POINT ('.');

  private final char m_cChar;

  private EGroupingSeparator (final char c)
  {
    m_cChar = c;
  }

  public char getChar ()
  {
    return m_cChar;
  }

  @Nullable
  public static EGroupingSeparator getFromCharOrNull (final char cSep)
  {
    return getFromCharOrDefault (cSep, null);
  }

  @Nullable
  public static EGroupingSeparator getFromCharOrDefault (final char cSep, @Nullable final EGroupingSeparator eDefault)
  {
    for (final EGroupingSeparator eDecSep : EGroupingSeparator.values ())
      if (eDecSep.getChar () == cSep)
        return eDecSep;
    return eDefault;
  }
}
