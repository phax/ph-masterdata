package com.helger.masterdata.telephone;

import java.io.Serializable;

import com.helger.commons.annotation.MustImplementEqualsAndHashcode;
import com.helger.commons.id.IHasID;
import com.helger.commons.text.display.IHasDisplayText;

/**
 * Base interface for telephone type.
 *
 * @author Philip Helger
 */
@MustImplementEqualsAndHashcode
public interface ITelephoneType extends IHasID <String>, IHasDisplayText, Serializable
{
  /* empty */
}
