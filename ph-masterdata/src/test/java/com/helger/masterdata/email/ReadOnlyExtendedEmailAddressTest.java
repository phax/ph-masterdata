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
package com.helger.masterdata.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.helger.commons.mock.AbstractCommonsTestCase;
import com.helger.commons.mock.CommonsTestHelper;

/**
 * Test class for class {@link ReadOnlyExtendedEmailAddress}
 *
 * @author Philip Helger
 */
public final class ReadOnlyExtendedEmailAddressTest extends AbstractCommonsTestCase
{
  @Test
  public void testBasic ()
  {
    ReadOnlyExtendedEmailAddress a = new ReadOnlyExtendedEmailAddress (null, null, null);
    assertNull (a.getType ());
    assertNull (a.getAddress ());
    assertNull (a.getPersonal ());

    a = new ReadOnlyExtendedEmailAddress (EEmailAddressType.OFFICE, "test@example.org", "Ich");
    assertEquals (EEmailAddressType.OFFICE, a.getType ());
    assertEquals ("test@example.org", a.getAddress ());
    assertEquals ("Ich", a.getPersonal ());
  }

  @Test
  public void testDefaultImpl ()
  {
    final ReadOnlyExtendedEmailAddress a = new ReadOnlyExtendedEmailAddress (EEmailAddressType.OFFICE,
                                                                             "test@example.org",
                                                                             "Ich");
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a,
                                                                           new ReadOnlyExtendedEmailAddress (EEmailAddressType.OFFICE2,
                                                                                                             "test@example.org",
                                                                                                             "Ich"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a,
                                                                           new ReadOnlyExtendedEmailAddress (EEmailAddressType.OFFICE,
                                                                                                             "test@example.com",
                                                                                                             "Ich"));
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a,
                                                                           new ReadOnlyExtendedEmailAddress (EEmailAddressType.OFFICE,
                                                                                                             "test@example.org",
                                                                                                             "Du"));
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new ReadOnlyExtendedEmailAddress (a));
    CommonsTestHelper.testDefaultSerialization (a);
  }
}
