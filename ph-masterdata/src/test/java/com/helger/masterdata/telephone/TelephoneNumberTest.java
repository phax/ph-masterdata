/*
 * Copyright (C) 2014-2022 Philip Helger (www.helger.com)
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
package com.helger.masterdata.telephone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.helger.commons.mock.CommonsTestHelper;

/**
 * Test class for class {@link TelephoneNumber}
 *
 * @author Philip Helger
 */
public final class TelephoneNumberTest
{
  @Test
  public void testBasic ()
  {
    final TelephoneNumber a = new TelephoneNumber ();
    assertNull (a.getType ());
    assertNull (a.getCountryCode ());
    assertNull (a.getAreaCode ());
    assertNull (a.getLine ());
    assertNull (a.getDirectDial ());

    // Type
    assertTrue (a.setType (ETelephoneType.OFFICE).isChanged ());
    assertEquals (ETelephoneType.OFFICE, a.getType ());
    assertFalse (a.setType (ETelephoneType.OFFICE).isChanged ());
    assertEquals (ETelephoneType.OFFICE, a.getType ());
    assertTrue (a.setType (ETelephoneType.PERSONAL).isChanged ());
    assertEquals (ETelephoneType.PERSONAL, a.getType ());

    // CountryCode
    assertTrue (a.setCountryCode ("0043").isChanged ());
    assertEquals ("0043", a.getCountryCode ());
    assertFalse (a.setCountryCode ("0043").isChanged ());
    assertEquals ("0043", a.getCountryCode ());
    assertTrue (a.setCountryCode ("0049").isChanged ());
    assertEquals ("0049", a.getCountryCode ());

    // AreaCode
    assertTrue (a.setAreaCode ("01").isChanged ());
    assertEquals ("01", a.getAreaCode ());
    assertFalse (a.setAreaCode ("01").isChanged ());
    assertEquals ("01", a.getAreaCode ());
    assertTrue (a.setAreaCode ("02953").isChanged ());
    assertEquals ("02953", a.getAreaCode ());

    // Postal code
    assertTrue (a.setLine ("1231234").isChanged ());
    assertEquals ("1231234", a.getLine ());
    assertFalse (a.setLine ("1231234").isChanged ());
    assertEquals ("1231234", a.getLine ());
    assertTrue (a.setLine ("9898987").isChanged ());
    assertEquals ("9898987", a.getLine ());

    // DirectDial
    assertTrue (a.setDirectDial ("47").isChanged ());
    assertEquals ("47", a.getDirectDial ());
    assertFalse (a.setDirectDial ("47").isChanged ());
    assertEquals ("47", a.getDirectDial ());
    assertTrue (a.setDirectDial ("2323").isChanged ());
    assertEquals ("2323", a.getDirectDial ());
  }

  @Test
  public void testDefaultImpl ()
  {
    TelephoneNumber a = new TelephoneNumber ();
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new TelephoneNumber ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new TelephoneNumber (a));
    CommonsTestHelper.testGetClone (a);

    a = new TelephoneNumber ();
    a.setType (ETelephoneType.PERSONAL);
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new TelephoneNumber ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new TelephoneNumber (a));
    CommonsTestHelper.testGetClone (a);

    a = new TelephoneNumber ();
    a.setCountryCode ("0043");
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new TelephoneNumber ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new TelephoneNumber (a));
    CommonsTestHelper.testGetClone (a);

    a = new TelephoneNumber ();
    a.setAreaCode ("01");
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new TelephoneNumber ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new TelephoneNumber (a));
    CommonsTestHelper.testGetClone (a);

    a = new TelephoneNumber ();
    a.setLine ("1231234");
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new TelephoneNumber ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new TelephoneNumber (a));
    CommonsTestHelper.testGetClone (a);

    a = new TelephoneNumber ();
    a.setDirectDial ("47");
    CommonsTestHelper.testDefaultImplementationWithDifferentContentObject (a, new TelephoneNumber ());
    CommonsTestHelper.testDefaultImplementationWithEqualContentObject (a, new TelephoneNumber (a));
    CommonsTestHelper.testGetClone (a);
  }
}
