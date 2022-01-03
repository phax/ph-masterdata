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
package com.helger.masterdata.address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.convert.MicroTypeConverter;
import com.helger.xml.microdom.serialize.MicroWriter;

/**
 * Test class for class {@link AddressMicroTypeConverterRegistrar}.
 *
 * @author Philip Helger
 */
public final class AddressMicroTypeConverterRegistrarTest
{
  private static final Logger LOGGER = LoggerFactory.getLogger (AddressMicroTypeConverterRegistrarTest.class);

  @Test
  public void testMarshal ()
  {
    final Locale aLocale = Locale.GERMAN;
    final PostalAddress aAddress = new PostalAddress ();
    aAddress.setStreet ("Cumberlandstraße", aLocale);
    aAddress.setPostalCode ("1140");
    aAddress.setCity ("Vienna", aLocale);
    aAddress.setState ("W", aLocale);
    aAddress.setCountry ("AT", aLocale);
    final IMicroElement aElement = MicroTypeConverter.convertToMicroElement (aAddress, "addr");
    assertNotNull (aElement);
    LOGGER.info (MicroWriter.getNodeAsString (aElement));

    final PostalAddress aAddress2 = MicroTypeConverter.convertToNative (aElement, PostalAddress.class);
    assertEquals (aAddress, aAddress2);
  }
}
