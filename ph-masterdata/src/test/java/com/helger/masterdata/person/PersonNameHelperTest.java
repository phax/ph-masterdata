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
package com.helger.masterdata.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import org.junit.Test;

/**
 * Test class for class {@link PersonNameHelper}.
 *
 * @author Philip Helger
 */
public final class PersonNameHelperTest
{
  private static final Locale L_DE = new Locale ("de");

  @Test
  public void testDefaults ()
  {
    assertTrue (PersonNameHelper.DEFAULT_COMPLEX_NAME_HANDLING == PersonNameHelper.isComplexNameHandlingEnabled ());
    assertTrue (PersonNameHelper.DEFAULT_FIRST_NAME_FIRST == PersonNameHelper.isFirstNameFirst ());
  }

  @Test
  public void testUnifyName ()
  {
    PersonNameHelper.setComplexNameHandlingEnabled (true);
    assertNull (PersonNameHelper.unifyName (null, L_DE));
    assertNull (PersonNameHelper.unifyName ("", L_DE));
    assertNull (PersonNameHelper.unifyName ("        ", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName ("Hans", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName (" Hans", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName ("Hans ", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName (" Hans ", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName ("hans", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName (" hans", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName ("hans ", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName (" hans ", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName ("HANS", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName (" HANS", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName ("HANS ", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName (" HANS ", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName ("HanS", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName (" HanS", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName ("HanS ", L_DE));
    assertEquals ("Hans", PersonNameHelper.unifyName (" HanS ", L_DE));
    assertEquals ("Päter", PersonNameHelper.unifyName (" PÄTER ", L_DE));
    assertEquals ("Haêk", PersonNameHelper.unifyName (" HAÊK ", L_DE));
    assertEquals ("Hans Peter", PersonNameHelper.unifyName ("Hans Peter", L_DE));
    assertEquals ("Hans Peter", PersonNameHelper.unifyName ("hans peter", L_DE));
    assertEquals ("Hans Peter", PersonNameHelper.unifyName ("  hans   peter   ", L_DE));
    assertEquals ("Hans Peter", PersonNameHelper.unifyName ("  HANS   PETER   ", L_DE));
    assertEquals ("Hans-Peter", PersonNameHelper.unifyName ("Hans-Peter", L_DE));
    assertEquals ("Hans-Peter", PersonNameHelper.unifyName ("hans-peter", L_DE));
    assertEquals ("Hans-Peter", PersonNameHelper.unifyName (" hans-peter ", L_DE));
    assertEquals ("Hans-Peter", PersonNameHelper.unifyName ("  hans---peter   ", L_DE));
    assertEquals ("Hans-Peter", PersonNameHelper.unifyName ("  HANS---PETER   ", L_DE));
    PersonNameHelper.setComplexNameHandlingEnabled (PersonNameHelper.DEFAULT_COMPLEX_NAME_HANDLING);
  }
}
