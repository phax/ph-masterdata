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
package com.helger.tenancy.tenant;

import com.helger.base.name.IHasDisplayName;
import com.helger.tenancy.IBusinessObject;
import com.helger.tenancy.uitext.IHasUIText;

/**
 * Represents a single tenant (Mandant)
 *
 * @author Philip Helger
 */
public interface ITenant extends IBusinessObject, IHasDisplayName, IHasUIText
{
  /**
   * @return <code>true</code> if this is the system global tenant,
   *         <code>false</code> otherwise
   */
  default boolean isGlobalTenant ()
  {
    return CTenant.GLOBAL_TENANT_ID.equals (getID ());
  }
}
