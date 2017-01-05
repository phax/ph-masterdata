/**
 * Copyright (C) 2014-2017 Philip Helger (www.helger.com)
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
package com.helger.validation.validator.string;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;

/**
 * Test class for class {@link LongUnsignedValidator}.
 *
 * @author Philip Helger
 */
public final class LongUnsignedValidatorTest
{
  @Test
  public void testAll ()
  {
    LongUnsignedValidator v = new LongUnsignedValidator (true);
    assertFalse (v.validate (null).isValid ());
    assertFalse (v.validate ("").isValid ());
    assertFalse (v.validate ("TRUE").isValid ());
    assertTrue (v.validate ("1").isValid ());
    assertTrue (v.validate ("0").isValid ());
    assertFalse (v.validate ("-1").isValid ());

    v = new LongUnsignedValidator (false);
    assertTrue (v.validate ("1").isValid ());
    assertFalse (v.validate ("0").isValid ());
    assertFalse (v.validate ("-1").isValid ());
  }

  @Test
  public void testStandard ()
  {
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (new LongUnsignedValidator (true),
                                                                       new LongUnsignedValidator (true));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (new LongUnsignedValidator (false),
                                                                       new LongUnsignedValidator (false));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (new LongUnsignedValidator (true),
                                                                           new LongUnsignedValidator (false));
  }
}
