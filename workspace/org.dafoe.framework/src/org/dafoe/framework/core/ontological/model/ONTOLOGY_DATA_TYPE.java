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
package org.dafoe.framework.core.ontological.model;

/**
 * The ONTOLOGY_DATA_TYPE Enum represents basic datatype according to W3C specification .
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public enum ONTOLOGY_DATA_TYPE {
	SHORT("short"),
	UNSIGNED_SHORT("unsignedShort"),
    INTEGER("integer"), //
    POSITIVE_INTEGER("positiveInteger"),
    NEGATIVE_INTEGER("negativeInteger"),
    NON_POSITIVE_INTEGER("nonPositiveInteger"),
    NON_NEGATIVE_INTEGER("nonNegativeInteger"),
    UNSIGNED_INTEGER("unsignedInteger"),
    LONG("long"),
    UNSIGNED_LONG("unsignedLong"),
    STRING("string"),
    NORMALIZED_STRING("normalisedString"),
    TOKEN("token"),
    FLOAT("float"),
    TIME("time"),
    DATE("date"),
    BOOLEAN("boolean"),
	BYTE("byte"),
	UNSIGNED_BYTE("unsignedByte"),
	ANY_URI("anyUri")
    ; //
	
	/** The label. */
 private String label;
	
	ONTOLOGY_DATA_TYPE(String lb){
		this.label= lb;
	}
	
	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
	
	
	
	
}

