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
package com.helger.masterdata.din;

import javax.annotation.Nonnegative;

import com.helger.commons.CGlobal;
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
  default double getWidthCM ()
  {
    return getWidthMM () / 10.0;
  }

  /**
   * Calculate the width in pixel for the specified DPI count
   *
   * @param nDPI
   *        Dots per inch count. Must be &gt; 0.
   * @return The pixel width.
   */
  @Nonnegative
  default double getWidthPixel (@Nonnegative final int nDPI)
  {
    return nDPI * getWidthMM () / (double) CGlobal.MM_PER_INCH;
  }

  /**
   * Calculate the width in pixel for the specified DPI count
   *
   * @param nDPI
   *        Dots per inch count. Must be &gt; 0.
   * @return The rounded pixel width.
   */
  @Nonnegative
  default long getWidthPixelLong (@Nonnegative final int nDPI)
  {
    return Math.round (getWidthPixel (nDPI));
  }

  /**
   * Calculate the required DPI count for the specified pixel count
   *
   * @param nPixel
   *        The available pixel size.
   * @return The necessary dots per inch for the specified pixel count
   */
  @Nonnegative
  default double getWidthDPI (@Nonnegative final int nPixel)
  {
    return nPixel * (double) CGlobal.MM_PER_INCH / getWidthMM ();
  }

  /**
   * Calculate the required DPI count for the specified pixel count
   *
   * @param nPixel
   *        The available pixel size.
   * @return The necessary dots per inch for the specified pixel count
   */
  @Nonnegative
  default long getWidthDPILong (@Nonnegative final int nPixel)
  {
    return Math.round (getWidthDPI (nPixel));
  }

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
  default double getHeightCM ()
  {
    return getHeightMM () / 10.0;
  }

  /**
   * Calculate the height in pixel for the specified DPI count
   *
   * @param nDPI
   *        Dots per inch count. Must be &gt; 0.
   * @return The pixel height.
   */
  @Nonnegative
  default double getHeightPixel (@Nonnegative final int nDPI)
  {
    return nDPI * getHeightMM () / (double) CGlobal.MM_PER_INCH;
  }

  /**
   * Calculate the height in pixel for the specified DPI count
   *
   * @param nDPI
   *        Dots per inch count. Must be &gt; 0.
   * @return The rounded pixel height.
   */
  @Nonnegative
  default long getHeightPixelLong (@Nonnegative final int nDPI)
  {
    return Math.round (getHeightPixel (nDPI));
  }

  /**
   * Calculate the required DPI count for the specified pixel count
   *
   * @param nPixel
   *        The available pixel size.
   * @return The necessary dots per inch for the specified pixel count
   */
  @Nonnegative
  default double getHeightDPI (@Nonnegative final int nPixel)
  {
    return nPixel * (double) CGlobal.MM_PER_INCH / getHeightMM ();
  }

  /**
   * Calculate the required DPI count for the specified pixel count
   *
   * @param nPixel
   *        The available pixel size.
   * @return The necessary dots per inch for the specified pixel count
   */
  @Nonnegative
  default long getHeightDPILong (@Nonnegative final int nPixel)
  {
    return Math.round (getHeightDPI (nPixel));
  }
}
