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
package com.helger.masterdata.currency.exchangeratio;

import java.math.BigDecimal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.joda.time.LocalDate;

import com.helger.commons.microdom.IMicroElement;
import com.helger.commons.microdom.MicroElement;
import com.helger.commons.microdom.convert.IMicroTypeConverter;

public final class ExchangeRatioMicroTypeConverter implements IMicroTypeConverter
{
  private static final String ATTR_DATE = "date";
  private static final String ATTR_RATIO = "ratio";

  @Nonnull
  public IMicroElement convertToMicroElement (@Nonnull final Object aObject,
                                              @Nullable final String sNamespaceURI,
                                              @Nonnull final String sTagName)
  {
    final ExchangeRatio aValue = (ExchangeRatio) aObject;
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
