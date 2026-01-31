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
package com.helger.masterdata.tax;

import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;
import com.helger.text.display.IHasDisplayText;

/**
 * Tax type.<br>
 * Based on "5153 Duty or tax or fee type name code".<br>
 * Source: http://www.unece.org/trade/untdid/d08b/tred/tred5153.htm
 *
 * @author Philip Helger
 */
public enum ETaxTypeUN5153 implements IHasID <String>, IHasDisplayText
{
  AAA ("AAA", ETaxTypeUN5153Name.AAA),
  AAB ("AAB", ETaxTypeUN5153Name.AAB),
  AAC ("AAC", ETaxTypeUN5153Name.AAC),
  AAD ("AAD", ETaxTypeUN5153Name.AAD),
  AAE ("AAE", ETaxTypeUN5153Name.AAE),
  AAF ("AAF", ETaxTypeUN5153Name.AAF),
  AAG ("AAG", ETaxTypeUN5153Name.AAG),
  AAH ("AAH", ETaxTypeUN5153Name.AAH),
  AAI ("AAI", ETaxTypeUN5153Name.AAI),
  AAJ ("AAJ", ETaxTypeUN5153Name.AAJ),
  AAK ("AAK", ETaxTypeUN5153Name.AAK),
  AAL ("AAL", ETaxTypeUN5153Name.AAL),
  ADD ("ADD", ETaxTypeUN5153Name.ADD),
  BOL ("BOL", ETaxTypeUN5153Name.BOL),
  CAP ("CAP", ETaxTypeUN5153Name.CAP),
  CAR ("CAR", ETaxTypeUN5153Name.CAR),
  COC ("COC", ETaxTypeUN5153Name.COC),
  CST ("CST", ETaxTypeUN5153Name.CST),
  CUD ("CUD", ETaxTypeUN5153Name.CUD),
  CVD ("CVD", ETaxTypeUN5153Name.CVD),
  ENV ("ENV", ETaxTypeUN5153Name.ENV),
  EXC ("EXC", ETaxTypeUN5153Name.EXC),
  EXP ("EXP", ETaxTypeUN5153Name.EXP),
  FET ("FET", ETaxTypeUN5153Name.FET),
  FRE ("FRE", ETaxTypeUN5153Name.FRE),
  GCN ("GCN", ETaxTypeUN5153Name.GCN),
  GST ("GST", ETaxTypeUN5153Name.GST),
  ILL ("ILL", ETaxTypeUN5153Name.ILL),
  IMP ("IMP", ETaxTypeUN5153Name.IMP),
  IND ("IND", ETaxTypeUN5153Name.IND),
  LAC ("LAC", ETaxTypeUN5153Name.LAC),
  LCN ("LCN", ETaxTypeUN5153Name.LCN),
  LDP ("LDP", ETaxTypeUN5153Name.LDP),
  LOC ("LOC", ETaxTypeUN5153Name.LOC),
  LST ("LST", ETaxTypeUN5153Name.LST),
  MCA ("MCA", ETaxTypeUN5153Name.MCA),
  MCD ("MCD", ETaxTypeUN5153Name.MCD),
  OTH ("OTH", ETaxTypeUN5153Name.OTH),
  PDB ("PDB", ETaxTypeUN5153Name.PDB),
  PDC ("PDC", ETaxTypeUN5153Name.PDC),
  PRF ("PRF", ETaxTypeUN5153Name.PRF),
  SCN ("SCN", ETaxTypeUN5153Name.SCN),
  SSS ("SSS", ETaxTypeUN5153Name.SSS),
  STT ("STT", ETaxTypeUN5153Name.STT),
  SUP ("SUP", ETaxTypeUN5153Name.SUP),
  SUR ("SUR", ETaxTypeUN5153Name.SUR),
  SWT ("SWT", ETaxTypeUN5153Name.SWT),
  TAC ("TAC", ETaxTypeUN5153Name.TAC),
  TOT ("TOT", ETaxTypeUN5153Name.TOT),
  TOX ("TOX", ETaxTypeUN5153Name.TOX),
  TTA ("TTA", ETaxTypeUN5153Name.TTA),
  VAD ("VAD", ETaxTypeUN5153Name.VAD),
  VAT ("VAT", ETaxTypeUN5153Name.VAT);

  private final String m_sID;
  private final IHasDisplayText m_aName;

  ETaxTypeUN5153 (@NonNull @Nonempty final String sID, @NonNull final ETaxTypeUN5153Name eResponseCodeName)
  {
    m_sID = sID;
    m_aName = eResponseCodeName;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @Nullable
  public String getDisplayText (@NonNull final Locale aContentLocale)
  {
    return m_aName.getDisplayText (aContentLocale);
  }

  @Nullable
  public static ETaxTypeUN5153 getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (ETaxTypeUN5153.class, sID);
  }
}
