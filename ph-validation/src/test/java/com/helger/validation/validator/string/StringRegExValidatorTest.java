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
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;
import com.helger.commons.text.display.ConstantHasDisplayText;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class {@link StringRegExValidator}.
 *
 * @author Philip Helger
 */
public final class StringRegExValidatorTest
{
  @Test
  @SuppressFBWarnings (value = "NP_NONNULL_PARAM_VIOLATION")
  public void testAll ()
  {
    StringRegExValidator v = new StringRegExValidator ("\\-?[0-9]+");
    assertFalse (v.validate (null).isValid ());
    assertFalse (v.validate ("").isValid ());
    assertFalse (v.validate ("TRUE").isValid ());
    assertTrue (v.validate ("-1").isValid ());
    assertFalse (v.validate ("-0.00001").isValid ());
    assertTrue (v.validate ("0").isValid ());
    assertTrue (v.validate ("1").isValid ());
    assertTrue (v.validate ("10").isValid ());

    v = new StringRegExValidator ("\\-?[0-9]+", new ConstantHasDisplayText ("any"));
    assertFalse (v.validate (null).isValid ());
    assertFalse (v.validate ("").isValid ());
    assertFalse (v.validate ("TRUE").isValid ());
    assertTrue (v.validate ("-1").isValid ());
    assertFalse (v.validate ("-0.00001").isValid ());
    assertTrue (v.validate ("0").isValid ());
    assertTrue (v.validate ("1").isValid ());
    assertTrue (v.validate ("10").isValid ());

    try
    {
      // empty regex
      new StringRegExValidator (null);
      fail ();
    }
    catch (final NullPointerException ex)
    {}

    try
    {
      // empty regex
      new StringRegExValidator ("");
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }

  @Test
  public void testStandard ()
  {
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (new StringRegExValidator ("ab.+"),
                                                                       new StringRegExValidator ("ab.+"));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (new StringRegExValidator ("^any$"),
                                                                       new StringRegExValidator ("^any$"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (new StringRegExValidator ("^any$"),
                                                                           new StringRegExValidator (".*else.*"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (new StringRegExValidator ("^any$"),
                                                                           new StringRegExValidator ("^any$",
                                                                                                     new ConstantHasDisplayText ("any")));
  }
}
