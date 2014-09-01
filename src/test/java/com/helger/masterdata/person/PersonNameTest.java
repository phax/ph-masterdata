/**
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
package com.helger.masterdata.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.mock.AbstractPHTestCase;
import com.helger.commons.string.StringHelper;

/**
 * Test class for class {@link PersonName}.
 *
 * @author Philip Helger
 */
public final class PersonNameTest extends AbstractPHTestCase
{
  @Test
  public void testMaxLength ()
  {
    final PersonName aPN = new PersonName ();
    assertNull (aPN.getPrefixTitle ());
    assertTrue (aPN.setPrefixTitle ("prefix").isChanged ());
    assertEquals ("prefix", aPN.getPrefixTitle ());

    final String sTooLong = StringHelper.getRepeated ('a', PersonName.LENGTH_PREFIXTITLE + 1);
    assertTrue (aPN.setPrefixTitle (sTooLong).isChanged ());
    assertEquals (StringHelper.getRepeated ('a', PersonName.LENGTH_PREFIXTITLE), aPN.getPrefixTitle ());
    assertFalse (aPN.setPrefixTitle (sTooLong).isChanged ());
  }
}
