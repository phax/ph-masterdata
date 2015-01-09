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
package com.helger.masterdata.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.mock.AbstractPHTestCase;

/**
 * Test class for class {@link PersonNameUtils}.
 *
 * @author Philip Helger
 */
public final class PersonNameUtilsTest extends AbstractPHTestCase
{
  @Test
  public void testDefaults ()
  {
    assertTrue (PersonNameUtils.DEFAULT_COMPLEX_NAME_HANDLING == PersonNameUtils.isComplexNameHandlingEnabled ());
    assertTrue (PersonNameUtils.DEFAULT_FIRST_NAME_FIRST == PersonNameUtils.isFirstNameFirst ());
  }

  @Test
  public void testUnifyName ()
  {
    PersonNameUtils.setComplexNameHandlingEnabled (true);
    assertNull (PersonNameUtils.unifyName (null, L_DE));
    assertNull (PersonNameUtils.unifyName ("", L_DE));
    assertNull (PersonNameUtils.unifyName ("        ", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName ("Hans", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName (" Hans", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName ("Hans ", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName (" Hans ", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName ("hans", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName (" hans", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName ("hans ", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName (" hans ", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName ("HANS", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName (" HANS", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName ("HANS ", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName (" HANS ", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName ("HanS", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName (" HanS", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName ("HanS ", L_DE));
    assertEquals ("Hans", PersonNameUtils.unifyName (" HanS ", L_DE));
    assertEquals ("Päter", PersonNameUtils.unifyName (" PÄTER ", L_DE));
    assertEquals ("Haêk", PersonNameUtils.unifyName (" HAÊK ", L_DE));
    assertEquals ("Hans Peter", PersonNameUtils.unifyName ("Hans Peter", L_DE));
    assertEquals ("Hans Peter", PersonNameUtils.unifyName ("hans peter", L_DE));
    assertEquals ("Hans Peter", PersonNameUtils.unifyName ("  hans   peter   ", L_DE));
    assertEquals ("Hans Peter", PersonNameUtils.unifyName ("  HANS   PETER   ", L_DE));
    assertEquals ("Hans-Peter", PersonNameUtils.unifyName ("Hans-Peter", L_DE));
    assertEquals ("Hans-Peter", PersonNameUtils.unifyName ("hans-peter", L_DE));
    assertEquals ("Hans-Peter", PersonNameUtils.unifyName (" hans-peter ", L_DE));
    assertEquals ("Hans-Peter", PersonNameUtils.unifyName ("  hans---peter   ", L_DE));
    assertEquals ("Hans-Peter", PersonNameUtils.unifyName ("  HANS---PETER   ", L_DE));
    PersonNameUtils.setComplexNameHandlingEnabled (PersonNameUtils.DEFAULT_COMPLEX_NAME_HANDLING);
  }
}
