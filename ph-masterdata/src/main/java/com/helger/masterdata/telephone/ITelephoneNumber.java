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
package com.helger.masterdata.telephone;

import java.io.Serializable;

import javax.annotation.Nullable;

/**
 * Read-only interface for a telephone number.
 *
 * @author Philip Helger
 */
public interface ITelephoneNumber extends Serializable
{
  /**
   * @return The semantic type of this telephone number. May be
   *         <code>null</code>.
   */
  @Nullable
  ITelephoneType getType ();

  /**
   * @return The country where the number resides. May be <code>null</code>.
   */
  @Nullable
  String getCountryCode ();

  /**
   * @return The area code for the phone number. This is country dependent. May
   *         be <code>null</code>.
   */
  @Nullable
  String getAreaCode ();

  /**
   * @return The main telephone number within an area code. May be
   *         <code>null</code>.
   */
  @Nullable
  String getLine ();

  /**
   * @return The direct dial for a further specification of a line. Is optional
   *         and may be <code>null</code>.
   */
  @Nullable
  String getDirectDial ();
}
