/**
 * Copyright (C) 2014-2019 Philip Helger (www.helger.com)
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
package com.helger.masterdata.tax;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.commons.text.display.IHasDisplayText;

/**
 * Tax category.<br>
 * Based on "5305 Duty or tax or fee category code".<br>
 * Source: https://www.unece.org/trade/untdid/d16b/tred/tred5305.htm
 *
 * @author Philip Helger
 */
public enum ETaxCategoryUN5305 implements IHasID <String>, IHasDisplayText
{
  A ("A", ETaxCategoryUN5305Name.A),
  AA ("AA", ETaxCategoryUN5305Name.AA),
  AB ("AB", ETaxCategoryUN5305Name.AB),
  AC ("AC", ETaxCategoryUN5305Name.AC),
  AD ("AD", ETaxCategoryUN5305Name.AD),
  AE ("AE", ETaxCategoryUN5305Name.AE),
  B ("B", ETaxCategoryUN5305Name.B),
  C ("C", ETaxCategoryUN5305Name.C),
  D ("D", ETaxCategoryUN5305Name.D),
  E ("E", ETaxCategoryUN5305Name.E),
  G ("G", ETaxCategoryUN5305Name.G),
  H ("H", ETaxCategoryUN5305Name.H),
  I ("I", ETaxCategoryUN5305Name.I),
  J ("J", ETaxCategoryUN5305Name.J),
  K ("K", ETaxCategoryUN5305Name.K),
  L ("L", ETaxCategoryUN5305Name.L),
  M ("M", ETaxCategoryUN5305Name.M),
  O ("O", ETaxCategoryUN5305Name.O),
  S ("S", ETaxCategoryUN5305Name.S),
  Z ("Z", ETaxCategoryUN5305Name.Z);

  private final String m_sID;
  private final IHasDisplayText m_aName;

  private ETaxCategoryUN5305 (@Nonnull @Nonempty final String sID,
                              @Nonnull final ETaxCategoryUN5305Name eResponseCodeName)
  {
    m_sID = sID;
    m_aName = eResponseCodeName;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public String getDisplayText (@Nonnull final Locale aContentLocale)
  {
    return m_aName.getDisplayText (aContentLocale);
  }

  @Nullable
  public static ETaxCategoryUN5305 getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (ETaxCategoryUN5305.class, sID);
  }
}
