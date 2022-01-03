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
package com.helger.masterdata.exchangeratio;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.IMicroQName;
import com.helger.xml.microdom.MicroElement;
import com.helger.xml.microdom.MicroQName;
import com.helger.xml.microdom.convert.IMicroTypeConverter;

public final class ExchangeRatioMicroTypeConverter implements IMicroTypeConverter <ExchangeRatio>
{
  private static final IMicroQName ATTR_DATE = new MicroQName ("date");
  private static final IMicroQName ATTR_RATIO = new MicroQName ("ratio");

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final ExchangeRatio aValue,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final IMicroElement aElement = new MicroElement (sNamespaceURI, sTagName);
    aElement.setAttributeWithConversion (ATTR_DATE, aValue.getDate ());
    aElement.setAttributeWithConversion (ATTR_RATIO, aValue.getRatio ());
    return aElement;
  }

  @Nonnull
  public ExchangeRatio convertToNative (@Nonnull final IMicroElement aElement)
  {
    final LocalDate aDate = aElement.getAttributeValueWithConversion (ATTR_DATE, LocalDate.class);
    final BigDecimal aRatio = aElement.getAttributeValueWithConversion (ATTR_RATIO, BigDecimal.class);
    return new ExchangeRatio (aDate, aRatio);
  }
}
