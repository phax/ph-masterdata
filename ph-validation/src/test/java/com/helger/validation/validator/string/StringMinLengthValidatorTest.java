/**
 * Copyright (C) 2014-2016 Philip Helger (www.helger.com)
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
import com.helger.commons.text.display.ConstantHasDisplayText;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link StringMinLengthValidator}.
 *
 * @author Philip Helger
 */
public final class StringMinLengthValidatorTest
{
  @Test
  @SuppressFBWarnings ("TQ_NEVER_VALUE_USED_WHERE_ALWAYS_REQUIRED")
  public void testAll ()
  {
    StringMinLengthValidator v = new StringMinLengthValidator (2);
    assertFalse (v.validate (null).isValid ());
    assertFalse (v.validate ("").isValid ());
    assertTrue (v.validate ("TRUE").isValid ());
    assertTrue (v.validate ("-1").isValid ());
    assertTrue (v.validate ("-0.00001").isValid ());
    assertFalse (v.validate ("0").isValid ());
    assertFalse (v.validate ("1").isValid ());
    assertTrue (v.validate ("10").isValid ());

    v = new StringMinLengthValidator (2, new ConstantHasDisplayText ("any"));
    assertFalse (v.validate (null).isValid ());
    assertFalse (v.validate ("").isValid ());
    assertTrue (v.validate ("TRUE").isValid ());
    assertTrue (v.validate ("-1").isValid ());
    assertTrue (v.validate ("-0.00001").isValid ());
    assertFalse (v.validate ("0").isValid ());
    assertFalse (v.validate ("1").isValid ());
    assertTrue (v.validate ("10").isValid ());

    try
    {
      // Min length invalid
      new StringMinLengthValidator (-1);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testStandard ()
  {
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (new StringMinLengthValidator (10),
                                                                       new StringMinLengthValidator (10));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (new StringMinLengthValidator (1),
                                                                       new StringMinLengthValidator (1));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (new StringMinLengthValidator (1),
                                                                           new StringMinLengthValidator (10));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (new StringMinLengthValidator (1),
                                                                           new StringMinLengthValidator (Integer.MAX_VALUE));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (new StringMinLengthValidator (1),
                                                                           new StringMinLengthValidator (2));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (new StringMinLengthValidator (1),
                                                                           new StringMinLengthValidator (1,
                                                                                                         new ConstantHasDisplayText ("any")));
  }
}
