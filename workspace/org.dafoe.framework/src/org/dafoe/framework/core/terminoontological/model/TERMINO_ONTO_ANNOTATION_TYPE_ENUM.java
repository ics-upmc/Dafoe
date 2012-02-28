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
package org.dafoe.framework.core.terminoontological.model;

import org.dafoe.framework.core.util.DATA_TYPE;


/**
 * The TERMINO_ONTO_ANNOTATION_TYPE_ENUM Enum represents available annotation type for termino-ontology's object..
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public enum TERMINO_ONTO_ANNOTATION_TYPE_ENUM {
	
	
	DAFOE_TERMINO_ONTO_PREF_LABEL("prefLabel",DATA_TYPE.STRING.getLabel()), 
	
	
	DAFOE_TERMINO_ONTO_ALT_LABEL("altLabel",DATA_TYPE.STRING.getLabel()), 
	
	
	DAFOE_TERMINO_ONTO_HIDDEN_LABEL("hiddenLabel",DATA_TYPE.STRING.getLabel()), 
	
	
	DAFOE_TERMINO_ONTO_NOTE("note",DATA_TYPE.STRING.getLabel()), 
	
	
	DAFOE_TERMINO_ONTO_DEFINITION("definition",DATA_TYPE.STRING.getLabel()), 
	

	DAFOE_TERMINO_ONTO_EXAMPLE("example",DATA_TYPE.STRING.getLabel()), 
	
	
	DAFOE_TERMINO_ONTO_HISTORY_NOTE("historyNote",DATA_TYPE.STRING.getLabel()), 
	
	
	DAFOE_TERMINO_ONTO_EDITORIAL_NOTE("editorialNote",DATA_TYPE.STRING.getLabel()), 
	
	
	DAFOE_TERMINO_ONTO_CHANGE_NOTE("changeNote",DATA_TYPE.STRING.getLabel()); 
	
	/** The label. */
	private String label;
	
	/** The type. */
	private String type;
	
	/**
	 * Instantiates a new tERMIN o_ ont o_ annotatio n_ typ e_ enum.
	 *
	 * @param lb the lb
	 * @param type the type
	 */
	TERMINO_ONTO_ANNOTATION_TYPE_ENUM(String lb, String type){
		this.label= lb;
		this.type= type;
	}
	
	/**
	 * Gets the label.
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
}
