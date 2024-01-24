/*
 * Copyright (C) 2014-2024 Philip Helger (www.helger.com)
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
package com.helger.masterdata.nuts;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.annotation.ReturnsMutableObject;
import com.helger.commons.collection.impl.ICommonsOrderedMap;

/**
 * Read only interface of the NUTS manager
 *
 * @author Philip Helger
 * @since 6.2.4
 */
public interface INutsManager
{
  @Nonnull
  @ReturnsMutableObject
  ICommonsOrderedMap <String, NutsItem> nutsItems ();

  @Nullable
  default NutsItem getItemOfID (@Nullable final String sID)
  {
    return nutsItems ().get (sID);
  }

  default boolean isIDValid (@Nullable final String sID)
  {
    return nutsItems ().containsKey (sID);
  }
}
