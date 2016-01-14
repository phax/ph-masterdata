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
package com.helger.validation.validator.map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.helger.commons.collection.CollectionHelper;
import com.helger.commons.mock.CommonsTestHelper;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Test class for class MinLengthMapValidator
 *
 * @author Philip Helger
 */
public final class MinLengthMapValidatorTest
{
  @Test
  @SuppressFBWarnings ("TQ_NEVER_VALUE_USED_WHERE_ALWAYS_REQUIRED")
  public void testAll ()
  {
    final MinLengthMapValidator v = new MinLengthMapValidator (2);
    assertFalse (v.validate (null).isValid ());
    assertFalse (v.validate (CollectionHelper.newMap ("a", "a")).isValid ());
    assertTrue (v.validate (CollectionHelper.newMap ("a", "a", "b", "b")).isValid ());
    assertTrue (v.validate (CollectionHelper.newMap ("a", "a", "b", "b", "c", "c")).isValid ());

    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (v, new MinLengthMapValidator (2));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (v, new MinLengthMapValidator (1));

    try
    {
      new MinLengthMapValidator (-1);
      fail ();
    }
    catch (final IllegalArgumentException ex)
    {}
  }
}
