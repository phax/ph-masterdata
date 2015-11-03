package com.helger.masterdata.email;

import java.io.Serializable;

import com.helger.commons.annotation.MustImplementEqualsAndHashcode;
import com.helger.commons.id.IHasID;
import com.helger.commons.text.display.IHasDisplayText;

/**
 * Base interface for email address type.
 *
 * @author Philip Helger
 */
@MustImplementEqualsAndHashcode
public interface IEmailAddressType extends IHasID <String>, IHasDisplayText, Serializable
{
  /* empty */
}
