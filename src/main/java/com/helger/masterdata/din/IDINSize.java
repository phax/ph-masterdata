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
package com.helger.masterdata.din;

import javax.annotation.Nonnegative;

import com.helger.commons.id.IHasID;

public interface IDINSize extends IHasID <String>
{
  /**
   * @return The width in millimeter. Always &gt; 0.
   */
  @Nonnegative
  int getWidthMM ();

  /**
   * @return The width in centimeter. Always &gt; 0. This is identical to
   *         <code>getWidthMM() / 10.0</code>
   */
  @Nonnegative
  double getWidthCM ();

  /**
   * Calculate the width in pixel for the specified DPI count
   * 
   * @param nDPI
   *        Dots per inch count. Must be &gt; 0.
   * @return The pixel width.
   */
  @Nonnegative
  double getWidthPixel (@Nonnegative int nDPI);

  /**
   * Calculate the width in pixel for the specified DPI count
   * 
   * @param nDPI
   *        Dots per inch count. Must be &gt; 0.
   * @return The rounded pixel width.
   */
  @Nonnegative
  long getWidthPixelLong (@Nonnegative int nDPI);

  /**
   * Calculate the required DPI count for the specified pixel count
   * 
   * @param nPixel
   *        The available pixel size.
   * @return The necessary dots per inch for the specified pixel count
   */
  @Nonnegative
  double getWidthDPI (@Nonnegative int nPixel);

  /**
   * Calculate the required DPI count for the specified pixel count
   * 
   * @param nPixel
   *        The available pixel size.
   * @return The necessary dots per inch for the specified pixel count
   */
  @Nonnegative
  long getWidthDPILong (@Nonnegative int nPixel);

  /**
   * @return The height in millimeter. Always &gt; 0.
   */
  @Nonnegative
  int getHeightMM ();

  /**
   * @return The height in centimeter. Always &gt; 0. This is identical to
   *         <code>getHeightMM() / 10.0</code>
   */
  @Nonnegative
  double getHeightCM ();

  /**
   * Calculate the height in pixel for the specified DPI count
   * 
   * @param nDPI
   *        Dots per inch count. Must be &gt; 0.
   * @return The pixel height.
   */
  @Nonnegative
  double getHeightPixel (@Nonnegative int nDPI);

  /**
   * Calculate the height in pixel for the specified DPI count
   * 
   * @param nDPI
   *        Dots per inch count. Must be &gt; 0.
   * @return The rounded pixel height.
   */
  @Nonnegative
  long getHeightPixelLong (@Nonnegative int nDPI);

  /**
   * Calculate the required DPI count for the specified pixel count
   * 
   * @param nPixel
   *        The available pixel size.
   * @return The necessary dots per inch for the specified pixel count
   */
  @Nonnegative
  double getHeightDPI (@Nonnegative int nPixel);

  /**
   * Calculate the required DPI count for the specified pixel count
   * 
   * @param nPixel
   *        The available pixel size.
   * @return The necessary dots per inch for the specified pixel count
   */
  @Nonnegative
  long getHeightDPILong (@Nonnegative int nPixel);
}
