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
 * Test class for class {@link DoubleMinMaxValidator}.
 *
 * @author Philip Helger
 */
public final class DoubleMinMaxValidatorTest
{
  @Test
  public void testAll ()
  {
    DoubleMinMaxValidator v = new DoubleMinMaxValidator (0d, 10d);
    assertFalse (v.validate (null).isValid ());
    assertFalse (v.validate ("").isValid ());
    assertFalse (v.validate ("TRUE").isValid ());
    assertFalse (v.validate ("-1").isValid ());
    assertFalse (v.validate ("-0.00001").isValid ());
    assertTrue (v.validate ("0").isValid ());
    assertTrue (v.validate ("1").isValid ());
    assertTrue (v.validate ("10").isValid ());

    v = new DoubleMinMaxValidator (Double.NEGATIVE_INFINITY, 0d);
    assertTrue (v.validate ("-1").isValid ());
    assertTrue (v.validate ("0").isValid ());
    assertFalse (v.validate ("0.001").isValid ());
    assertFalse (v.validate ("1").isValid ());

    v = new DoubleMinMaxValidator (0d, Double.POSITIVE_INFINITY);
    assertFalse (v.validate ("-1").isValid ());
    assertFalse (v.validate ("-0.001").isValid ());
    assertTrue (v.validate ("0").isValid ());
    assertTrue (v.validate ("1").isValid ());

    try
    {
      // Both null not okay
      new DoubleMinMaxValidator (Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}

    try
    {
      // Min > Max
      new DoubleMinMaxValidator (1d, 0d);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testStandard ()
  {
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (new DoubleMinMaxValidator (0d, 10d),
                                                                       new DoubleMinMaxValidator (0d, 10d));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (new DoubleMinMaxValidator (Double.NEGATIVE_INFINITY,
                                                                                                  10d),
                                                                       new DoubleMinMaxValidator (Double.NEGATIVE_INFINITY,
                                                                                                  10d));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (new DoubleMinMaxValidator (0d,
                                                                                                  Double.POSITIVE_INFINITY),
                                                                       new DoubleMinMaxValidator (0d,
                                                                                                  Double.POSITIVE_INFINITY));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (new DoubleMinMaxValidator (0d, 1d),
                                                                           new DoubleMinMaxValidator (0d, 10d));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (new DoubleMinMaxValidator (0d, 1d),
                                                                           new DoubleMinMaxValidator (0d,
                                                                                                      Double.POSITIVE_INFINITY));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (new DoubleMinMaxValidator (0d, 1d),
                                                                           new DoubleMinMaxValidator (Double.NEGATIVE_INFINITY,
                                                                                                      1d));
  }
}
