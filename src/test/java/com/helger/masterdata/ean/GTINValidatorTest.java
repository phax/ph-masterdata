/**
 * Copyright (C) 2006-2014 phloc systems (www.phloc.com)
 * Copyright (C) 2014 Philip Helger (www.helger.com)
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
package com.helger.masterdata.ean;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link GTINValidator}.
 * 
 * @author Philip Helger
 */
public final class GTINValidatorTest
{
  @Test
  public void testIsValidGTIN8 ()
  {
    assertTrue (GTINValidator.isValidGTIN8 ("11111115"));
    assertFalse (GTINValidator.isValidGTIN8 ("11111114"));
    assertFalse (GTINValidator.isValidGTIN8 ("11111116"));
    assertFalse (GTINValidator.isValidGTIN8 ("1111111"));
    assertFalse (GTINValidator.isValidGTIN8 ("111111159"));
    assertFalse (GTINValidator.isValidGTIN8 (""));
    assertFalse (GTINValidator.isValidGTIN8 (null));
  }

  @Test
  public void testIsValidGTIN12 ()
  {
    assertTrue (GTINValidator.isValidGTIN12 ("111111111117"));
    assertFalse (GTINValidator.isValidGTIN12 ("111111111116"));
    assertFalse (GTINValidator.isValidGTIN12 ("111111111118"));
    assertFalse (GTINValidator.isValidGTIN12 ("11111111111"));
    assertFalse (GTINValidator.isValidGTIN12 ("1111111111177"));
    assertFalse (GTINValidator.isValidGTIN12 (""));
    assertFalse (GTINValidator.isValidGTIN12 (null));
  }

  @Test
  public void testIsValidGTIN13 ()
  {
    assertTrue (GTINValidator.isValidGTIN13 ("6291041500213"));
    assertFalse (GTINValidator.isValidGTIN13 ("6291041500212"));
    assertFalse (GTINValidator.isValidGTIN13 ("6291041500214"));
    assertFalse (GTINValidator.isValidGTIN13 ("629104150021"));
    assertFalse (GTINValidator.isValidGTIN13 ("62910415002133"));
    assertFalse (GTINValidator.isValidGTIN13 (""));
    assertFalse (GTINValidator.isValidGTIN13 (null));
  }

  @Test
  public void testIsValidGTIN14 ()
  {
    assertTrue (GTINValidator.isValidGTIN14 ("11111111111113"));
    assertFalse (GTINValidator.isValidGTIN14 ("11111111111112"));
    assertFalse (GTINValidator.isValidGTIN14 ("11111111111114"));
    assertFalse (GTINValidator.isValidGTIN14 ("1111111111111"));
    assertFalse (GTINValidator.isValidGTIN14 ("111111111111133"));
    assertFalse (GTINValidator.isValidGTIN14 (""));
    assertFalse (GTINValidator.isValidGTIN14 (null));
  }

  @Test
  public void testIsValidSSCC ()
  {
    assertTrue (GTINValidator.isValidSSCC ("111111111111122227"));
    assertFalse (GTINValidator.isValidSSCC ("111111111111122226"));
    assertFalse (GTINValidator.isValidSSCC ("111111111111122228"));
    assertFalse (GTINValidator.isValidSSCC ("11111111111112222"));
    assertFalse (GTINValidator.isValidSSCC ("1111111111111222277"));
    assertFalse (GTINValidator.isValidSSCC (""));
    assertFalse (GTINValidator.isValidSSCC (null));
  }
}
