package com.helger.masterdata.nuts;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.helger.commons.id.IHasID;
import com.helger.commons.lang.EnumHelper;

/**
 * NUTS level
 *
 * @author Philip Helger
 */
public enum ENutsLevel implements IHasID <String>
{
  COUNTRY ("c", 2),
  NUTS1 ("nuts1", 3),
  NUTS2 ("nuts2", 4),
  NUTS3 ("nuts3", 5);

  private final String m_sID;
  private final int m_nCharCount;

  ENutsLevel (@Nonnull final String sID, @Nonnegative final int nCharCount)
  {
    m_sID = sID;
    m_nCharCount = nCharCount;
  }

  @Nonnull
  public String getID ()
  {
    return m_sID;
  }

  @Nonnegative
  public int getCharCount ()
  {
    return m_nCharCount;
  }

  @Nullable
  public static ENutsLevel getFromIDOrNull (@Nullable final String sID)
  {
    return EnumHelper.getFromIDOrNull (ENutsLevel.class, sID);
  }
}
