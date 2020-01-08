/**
 * Copyright (C) 2014-2020 Philip Helger (www.helger.com)
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Locale;

import org.junit.Test;

import com.helger.commons.CGlobal;
import com.helger.commons.math.MathHelper;
import com.helger.commons.mock.CommonsTestHelper;

/**
 * Test class for class {@link VATItem}.
 *
 * @author Philip Helger
 */
public final class VATItemTest
{
  @Test
  public void testFactors ()
  {
    final VATItem v = VATItem.createTestItem (Locale.US, EVATItemType.REDUCED, MathHelper.toBigDecimal ("20"));
    assertEquals (MathHelper.toBigDecimal ("1.2"), v.getMultiplicationFactorNetToGross ());
    assertEquals (MathHelper.toBigDecimal ("120.0"),
                  CGlobal.BIGDEC_100.multiply (v.getMultiplicationFactorNetToGross ()));
    assertNull (v.getPeriod ().getStart ());
    assertNull (v.getPeriod ().getEnd ());
    CommonsTestHelper.testDefaultSerialization (v);
  }
}
