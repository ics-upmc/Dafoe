/*******************************************************************************************************************************
 * (c) Copyright 2007, 2010 CRITT Informatique and INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, Télécom ParisTech, CNRS/UTC.
 * All rights reserved.
 * This program has been developed by the CRITT Informatique for the ANR DAFOE4App Project.
 * This program and the accompanying materials are made available under the terms
 * of the CeCILL-C Public License v1 which accompanies this distribution,
 * and is available at http://www.cecill.info/licences/Licence_CeCILL-C_V1-fr.html
 *
 * Contributors:
 *     INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, Télécom ParisTech, CNRS/UTC and CRITT Informatique - specifications
 *     CRITT Informatique - initial API and implementation
 ********************************************************************************************************************************/
package org.dafoe.framework.tools;

// TODO: Auto-generated Javadoc
/**
 * The Class StringFormatterTools.
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class StringFormatterTools {

	/**
	 * Gets the sql table name from owl class label.
	 *
	 * @param classLabel the class label
	 * @return the sql table name from class label
	 */
	public static String normalizeSqlTableNameFromClassLabel(String classLabel) {
		String tmp= replaceDatabaseUnAcceptableCharacter(classLabel);
		
		return "m13_" + tmp.toLowerCase();
	}

	/**
	 * Replace database un acceptable character.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String replaceDatabaseUnAcceptableCharacter(String s) {
		String res = "";

		String acceptable = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-1234567890";

		for (char c : (s.trim()).toCharArray()) {
			if (acceptable.indexOf(c) >= 0) {
				res += c;
			} else {
				String accents = "àäâãÂÄéèêëÉÈËÊîïÎÏôöÖÔûüÛÜçÇ -'";
				String sansAccent = "aaaaAAeeeeEEEEiiIIooOOuuUUcC___";

				int idx = accents.indexOf(c);
				if (idx >= 0) {
					res += sansAccent.charAt(idx);
				}

			}
		}
		return res;
	}

	/**
	 * Normalize column name from property label.
	 * 
	 * @param propLabel
	 *            the prop label
	 * 
	 * @return the string
	 */
	public static String normalizeColumnNameFromPropertyLabel(String propLabel) {
		
		String tmp= replaceDatabaseUnAcceptableCharacter(propLabel);
		
		return tmp.toLowerCase();
	}

	/**
	 * Replace rdf un acceptable character.
	 *
	 * @param s the s
	 * @return the string
	 */
	public static String replaceRDFUnAcceptableCharacter(String s) {
		String res = "";

		String acceptable = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-1234567890/:.#";

		for (char c : (s.trim()).toCharArray()) {
			if (acceptable.indexOf(c) >= 0) {
				res += c;
			} else {
				String accents = "àäâãÂÄéèêëÉÈËÊîïÎÏôöÖÔûüÛÜçÇ -'";
				String sansAccent = "aaaaAAeeeeEEEEiiIIooOOuuUUcC___";

				int idx = accents.indexOf(c);
				if (idx >= 0) {
					res += sansAccent.charAt(idx);
				}

			}
		}
		return res;
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		String s="totAot1à";
		
		System.out.println(normalizeSqlTableNameFromClassLabel(s));
		

	}
}
