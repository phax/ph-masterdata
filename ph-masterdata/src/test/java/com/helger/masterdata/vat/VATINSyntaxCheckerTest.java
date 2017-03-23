package com.helger.masterdata.vat;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.helger.xml.microdom.IMicroDocument;
import com.helger.xml.microdom.IMicroElement;
import com.helger.xml.microdom.serialize.MicroReader;
import com.helger.xml.microdom.util.MicroHelper;

/**
 * Test class for class {@link VATINSyntaxChecker}.
 *
 * @author Philip Helger
 */
public final class VATINSyntaxCheckerTest
{
  @Test
  public void testBasic ()
  {
    assertTrue (VATINSyntaxChecker.isValidVATIN ("ATU10223006"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("BE0776091951"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("DE111111125"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("DK88146328"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("EL999999999"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("ESX9999999X"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("FI09853608"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("FRXX999999999"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("GBGD123"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("GBHA500"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("GB434031494"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("IE8Z49289F"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("IE3628739L"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("IE3628739UA"));
    assertTrue (VATINSyntaxChecker.isValidVATIN ("IT00000010215"));
  }

  public static void main (final String [] args)
  {
    final IMicroDocument aDoc = MicroReader.readMicroXML (new File ("c:\\test.xml"));
    final Map <String, String> x = new HashMap<> ();
    for (final IMicroElement e : aDoc.getDocumentElement ()
                                     .getFirstChildElement ("ERKLAERUNG")
                                     .getAllChildElements ("ZM"))
    {
      final String sUID = MicroHelper.getChildTextContent (e, "UID_MS");
      final String sSum = MicroHelper.getChildTextContent (e, "SUM_BGL");

      if (!VATINSyntaxChecker.isValidVATIN (sUID))
        System.err.println ("Invalid UID " + sUID);

      final String sOldSum = x.put (sUID, sSum);
      if (sOldSum != null)
        System.err.println (sUID + " (old: " + sOldSum + "; new: " + sSum + ")");
    }
  }
}
