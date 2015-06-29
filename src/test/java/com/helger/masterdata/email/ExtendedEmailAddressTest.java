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
package com.helger.masterdata.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.mock.AbstractCommonsTestCase;
import com.helger.commons.mock.CommonsTestHelper;

/**
 * Test class for class {@link ExtendedEmailAddress}
 *
 * @author Philip Helger
 */
public final class ExtendedEmailAddressTest extends AbstractCommonsTestCase
{
  @Test
  public void testBasic ()
  {
    final ExtendedEmailAddress a = new ExtendedEmailAddress ();
    assertNull (a.getType ());
    assertNull (a.getAddress ());
    assertNull (a.getPersonal ());

    // Type
    assertTrue (a.setType (EEmailAddressType.OFFICE).isChanged ());
    assertEquals (EEmailAddressType.OFFICE, a.getType ());
    assertFalse (a.setType (EEmailAddressType.OFFICE).isChanged ());
    assertEquals (EEmailAddressType.OFFICE, a.getType ());
    assertTrue (a.setType (EEmailAddressType.PERSONAL).isChanged ());
    assertEquals (EEmailAddressType.PERSONAL, a.getType ());

    // Address
    assertTrue (a.setAddress ("test@example.org").isChanged ());
    assertEquals ("test@example.org", a.getAddress ());
    assertFalse (a.setAddress ("test@example.org").isChanged ());
    assertEquals ("test@example.org", a.getAddress ());
    assertTrue (a.setAddress ("test@example.com").isChanged ());
    assertEquals ("test@example.com", a.getAddress ());

    // Personal
    assertTrue (a.setPersonal ("Ich").isChanged ());
    assertEquals ("Ich", a.getPersonal ());
    assertFalse (a.setPersonal ("Ich").isChanged ());
    assertEquals ("Ich", a.getPersonal ());
    assertTrue (a.setPersonal ("Du").isChanged ());
    assertEquals ("Du", a.getPersonal ());
  }

  @Test
  public void testDefaultImpl ()
  {
    ExtendedEmailAddress a = new ExtendedEmailAddress ();
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new ExtendedEmailAddress ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new ExtendedEmailAddress (a));
    CommonsTestHelper.testGetClone (a);

    a = new ExtendedEmailAddress ();
    a.setType (EEmailAddressType.PERSONAL);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new ExtendedEmailAddress ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new ExtendedEmailAddress (a));
    CommonsTestHelper.testGetClone (a);

    a = new ExtendedEmailAddress ();
    a.setAddress ("test@example.org");
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new ExtendedEmailAddress ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new ExtendedEmailAddress (a));
    CommonsTestHelper.testGetClone (a);

    a = new ExtendedEmailAddress ();
    a.setPersonal ("Ich & Du");
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new ExtendedEmailAddress ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new ExtendedEmailAddress (a));
    CommonsTestHelper.testGetClone (a);
    CommonsTestHelper.testDefaultSerialization (a);
  }
}
