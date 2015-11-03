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
package com.helger.validation.validator.string;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;

/**
 * Test class for class {@link LongMinMaxValidator}.
 *
 * @author Philip Helger
 */
public final class LongMinMaxValidatorTest
{
  @Test
  public void testAll ()
  {
    LongMinMaxValidator v = new LongMinMaxValidator (0, 10);
    assertFalse (v.validate (null).isValid ());
    assertFalse (v.validate ("").isValid ());
    assertFalse (v.validate ("TRUE").isValid ());
    assertFalse (v.validate ("-1").isValid ());
    assertFalse (v.validate ("-0.00001").isValid ());
    assertTrue (v.validate ("0").isValid ());
    assertTrue (v.validate ("1").isValid ());
    assertTrue (v.validate ("10").isValid ());

    v = new LongMinMaxValidator (Long.MIN_VALUE, 0);
    assertTrue (v.validate ("-1").isValid ());
    assertTrue (v.validate ("0").isValid ());
    assertFalse (v.validate ("0.001").isValid ());
    assertFalse (v.validate ("1").isValid ());

    v = new LongMinMaxValidator (0, Long.MAX_VALUE);
    assertFalse (v.validate ("-1").isValid ());
    assertFalse (v.validate ("-0.001").isValid ());
    assertTrue (v.validate ("0").isValid ());
    assertTrue (v.validate ("1").isValid ());

    try
    {
      // Both null not okay
      new LongMinMaxValidator (Long.MIN_VALUE, Long.MAX_VALUE);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Min > Max
      new LongMinMaxValidator (1, 0);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testStandard ()
  {
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (new LongMinMaxValidator (0, 10),
                                                                       new LongMinMaxValidator (0, 10));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (new LongMinMaxValidator (Long.MIN_VALUE, 10),
                                                                       new LongMinMaxValidator (Long.MIN_VALUE, 10));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (new LongMinMaxValidator (0, Long.MAX_VALUE),
                                                                       new LongMinMaxValidator (0, Long.MAX_VALUE));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (new LongMinMaxValidator (0, 1),
                                                                           new LongMinMaxValidator (0, 10));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (new LongMinMaxValidator (0, 1),
                                                                           new LongMinMaxValidator (0, Long.MAX_VALUE));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (new LongMinMaxValidator (0, 1),
                                                                           new LongMinMaxValidator (Long.MIN_VALUE, 1));
  }
}
