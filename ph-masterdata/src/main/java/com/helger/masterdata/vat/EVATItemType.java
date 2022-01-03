/*
 * Copyright (C) 2014-2022 Philip Helger (www.helger.com)
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
package com.helger.masterdata.vat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;
import com.helger.masterdata.tax.ETaxCategoryUN5305;

/**
 * Determines the different VAT types.<br>
 * Source: http://de.wikipedia.org/wiki/Umsatzsteuer<br>
 * Source:
 * http://ec.europa.eu/taxation_customs/taxation/vat/how_vat_works/rates/
 *
 * @author Philip Helger
 */
public enum EVATItemType implements IHasID <String>
{
  /* EC: Standard rate */
  REGULAR ("regular", ETaxCategoryUN5305.S),
  /* EC: Parking rate */
  INBETWEEN ("inbetween", ETaxCategoryUN5305.A),
  /* EC: Reduced rate */
  REDUCED ("reduced", ETaxCategoryUN5305.AA),
  /* EC: Super Reduced Rate */
  REDUCED_HEAVILY ("reduced_heavily", ETaxCategoryUN5305.AA),
  /* Other rate */
  OTHER ("other", null);

  private final String m_sID;
  private final ETaxCategoryUN5305 m_eTaxCategory;

  EVATItemType (@Nonnull @Nonempty final String sID, @Nullable final ETaxCategoryUN5305 eTaxCategory)
  {
    m_sID = sID;
    m_eTaxCategory = eTaxCategory;
  }

  @Nonnull
  @Nonempty
  public String getID ()
  {
    return m_sID;
  }

  /**
   * @return Tax category code. <code>null</code> only for
   *         {@link EVATItemType#OTHER}
   */
  @Nullable
  public ETaxCategoryUN5305 getTaxCategory ()
  {
    return m_eTaxCategory;
  }

  @Nullable
  public static EVATItemType getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (EVATItemType.class, sID);
  }
}
