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

package org.dafoe.plugin.imp.skos.util;

public enum SKOSAnnotationTypeENUM {

	SKOS_PREF_LABEL("skos:prefLabel"), 
	SKOS_ALT_LABEL("skos:altLabel"), 
	SKOS_HIDDEN_LABEL("skos:hiddenLabel"), 
	SKOS_NOTE("skos:note"),
	SKOS_DEFINITION("skos:definition"), 
	SKOS_EXAMPLE("skos:example"), 
	SKOS_HISTORY_NOTE("skos:historyNote"), 
	SKOS_EDITORIAL_NOTE("skos:editorialNote"), 
	SKOS_CHANGE_NOTE("skos:changeNote"); 
	
	private String value;
	
	SKOSAnnotationTypeENUM(String v){
		this.value= v;
	}
	
	public String getValue() {
		return value;
	}
}