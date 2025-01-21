/*
 * Copyright (C) 2014-2025 Philip Helger (www.helger.com)
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
package com.helger.masterdata.isbn;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test class for class {@link ISBN}.
 *
 * @author Philip Helger
 */
public final class ISBNTest
{
  @Test
  public void testISBN10 ()
  {
    // check valid
    assertTrue (ISBN.isValidISBN10Number ("3860636278"));
    assertTrue (ISBN.isValidISBN10Number ("0789722429"));
    assertTrue (ISBN.isValidISBN10Number ("0596000464"));

    // check invalid checksum (last char)
    assertFalse (ISBN.isValidISBN10Number ("386063627X"));

    // check invalid input length
    assertFalse (ISBN.isValidISBN10Number ("38606362788"));

    // check not numeric
    assertFalse (ISBN.isValidISBN10Number ("38606362XX"));
    assertFalse (ISBN.isValidISBN10Number (null));
    assertFalse (ISBN.isValidISBN10Number (""));
  }

  @Test
  public void testISBN13 ()
  {
    // check valid
    assertTrue (ISBN.isValidISBN13Number ("9780321312556"));
    assertTrue (ISBN.isValidISBN13Number ("9780201612448"));

    // check invalid checksum (last char)
    assertFalse (ISBN.isValidISBN13Number ("9780321312555"));

    // check invalid input length
    assertFalse (ISBN.isValidISBN13Number ("97803213125564"));

    // check not numeric
    assertFalse (ISBN.isValidISBN13Number ("978032131255X"));
    assertFalse (ISBN.isValidISBN13Number (null));
    assertFalse (ISBN.isValidISBN13Number (""));
  }
}
