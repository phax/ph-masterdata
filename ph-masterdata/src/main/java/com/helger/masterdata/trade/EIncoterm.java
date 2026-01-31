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
package com.helger.masterdata.trade;

import java.util.Locale;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.helger.annotation.Nonempty;
import com.helger.base.id.IHasID;
import com.helger.base.lang.EnumHelper;
import com.helger.text.display.IHasDisplayText;

/**
 * Possible delivery terms based on Incoterms.<br>
 * See Technical documentation 6.5<br>
 * http://de.wikipedia.org/wiki/Incoterms
 *
 * @author Philip Helger
 */
public enum EIncoterm implements IHasID <String>, IHasDisplayText
{
  // Order as in in Incoterms 2000
  EXW ("EXW", EIncotermName.EXW),
  FCA ("FCA", EIncotermName.FCA),
  FAS ("FAS", EIncotermName.FAS),
  FOB ("FOB", EIncotermName.FOB),
  CFR ("CFR", EIncotermName.CFR),
  CIF ("CIF", EIncotermName.CIF),
  CPT ("CPT", EIncotermName.CPT),
  CIP ("CIP", EIncotermName.CIP),
  DAF ("DAF", EIncotermName.DAF),
  DES ("DES", EIncotermName.DES),
  DEQ ("DEQ", EIncotermName.DEQ),
  DDU ("DDU", EIncotermName.DDU),
  DDP ("DDP", EIncotermName.DDP);

  private final String m_sID;
  private final IHasDisplayText m_aName;

  EIncoterm (@NonNull @Nonempty final String sID, @NonNull final EIncotermName eDeliveryTermName)
  {
    m_sID = sID;
    m_aName = eDeliveryTermName;
  }

  @NonNull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  @NonNull
  public String getDisplayText (@NonNull final Locale aContentLocale)
  {
    return m_aName.getDisplayText (aContentLocale) + " (" + m_sID + ')';
  }

  @Nullable
  public static EIncoterm getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EIncoterm.class, sID);
  }
}
