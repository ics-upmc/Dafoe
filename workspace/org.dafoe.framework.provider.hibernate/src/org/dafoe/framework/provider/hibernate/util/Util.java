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
package org.dafoe.framework.provider.hibernate.util;

import org.dafoe.framework.core.ontological.model.ONTOLOGY_DATA_TYPE;
import org.dafoe.framework.core.ontological.model.ONTO_OBJECT_STATE;
import org.dafoe.framework.core.terminological.model.LINGUISTIC_STATUS;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;

// TODO: Auto-generated Javadoc
/**
 * The Class Util.
 */
public class Util {

////////////////////	
	/**
 * Gets the termino object state from database.
 *
 * @param state the state
 * @return the termino object state from database
 */
public static TERMINO_OBJECT_STATE getTerminoObjectStateFromDatabase(Integer state){
		if(state== TERMINO_OBJECT_STATE.STUDIED.getValue()){
			return TERMINO_OBJECT_STATE.STUDIED;
		}
		
		if(state== TERMINO_OBJECT_STATE.VALIDATED.getValue()){
			return TERMINO_OBJECT_STATE.VALIDATED;
		}
		
		if(state== TERMINO_OBJECT_STATE.REJECTED.getValue()){
			return TERMINO_OBJECT_STATE.REJECTED;
		}
		
		if(state== TERMINO_OBJECT_STATE.CONCEPTUALIZED.getValue()){
			return TERMINO_OBJECT_STATE.CONCEPTUALIZED;
		}
		
		if(state== TERMINO_OBJECT_STATE.DELETED.getValue()){
			return TERMINO_OBJECT_STATE.DELETED;
		}
			
		return TERMINO_OBJECT_STATE.UNKNOWN;
	}
	
	////////////////////
	/**
	 * Gets the linguistic status from database.
	 *
	 * @param status the status
	 * @return the linguistic status from database
	 */
	public static LINGUISTIC_STATUS getLinguisticStatusFromDatabase(Integer status){
		if(status== LINGUISTIC_STATUS.NAMED_ENTITY.getValue()){
			return LINGUISTIC_STATUS.NAMED_ENTITY;
		}
		
		if(status== LINGUISTIC_STATUS.TERM.getValue()){
			return LINGUISTIC_STATUS.TERM;
		}
		
		if(status== LINGUISTIC_STATUS.INDIFFERENTIATED.getValue()){
			return LINGUISTIC_STATUS.INDIFFERENTIATED;
		}
		
		
	return LINGUISTIC_STATUS.UNKNOWN;
	}
	
	/**
	 * Gets the termino onto object state from database.
	 *
	 * @param state the state
	 * @return the termino onto object state from database
	 */
	public static TERMINO_ONTO_OBJECT_STATE getTerminoOntoObjectStateFromDatabase(Integer state){
		if(state== TERMINO_ONTO_OBJECT_STATE.VALIDATED.getValue()){
			return TERMINO_ONTO_OBJECT_STATE.VALIDATED;
		}
		
		if(state== TERMINO_ONTO_OBJECT_STATE.FORMALIZED.getValue()){
			return TERMINO_ONTO_OBJECT_STATE.FORMALIZED;
		}
		
		if(state== TERMINO_ONTO_OBJECT_STATE.REJECTED.getValue()){
			return TERMINO_ONTO_OBJECT_STATE.REJECTED;
		}
		
		if(state== TERMINO_ONTO_OBJECT_STATE.STUDIED.getValue()){
			return TERMINO_ONTO_OBJECT_STATE.STUDIED;
		}
					
		return TERMINO_ONTO_OBJECT_STATE.UNKNOWN;
	}
	
	/**
	 * Gets the onto object state from database.
	 *
	 * @param state the state
	 * @return the onto object state from database
	 */
	public static ONTO_OBJECT_STATE getOntoObjectStateFromDatabase(Integer state){
		if(state== ONTO_OBJECT_STATE.VALIDATED.getValue()){
			return ONTO_OBJECT_STATE.VALIDATED;
		}
		
		if(state== ONTO_OBJECT_STATE.REJECTED.getValue()){
			return ONTO_OBJECT_STATE.REJECTED;
		}

					
		return ONTO_OBJECT_STATE.UNKNOWN;
	}
	
	
	/**
	 * Gets the ontology data type from database.
	 *
	 * @param dt the dt
	 * @return the ontology data type from database
	 */
	public static ONTOLOGY_DATA_TYPE getOntologyDataTypeFromDatabase(String dt){
		
		if(dt.equals(ONTOLOGY_DATA_TYPE.SHORT.getLabel())){
			return ONTOLOGY_DATA_TYPE.SHORT;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.UNSIGNED_SHORT.getLabel())){
			return ONTOLOGY_DATA_TYPE.UNSIGNED_SHORT;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.INTEGER.getLabel())){
			return ONTOLOGY_DATA_TYPE.INTEGER;
		}

		
		if(dt.equals(ONTOLOGY_DATA_TYPE.NEGATIVE_INTEGER.getLabel())){
			return ONTOLOGY_DATA_TYPE.NEGATIVE_INTEGER;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.POSITIVE_INTEGER.getLabel())){
			return ONTOLOGY_DATA_TYPE.POSITIVE_INTEGER;
		}
		
		if(dt.equals(ONTOLOGY_DATA_TYPE.NON_NEGATIVE_INTEGER.getLabel())){
			return ONTOLOGY_DATA_TYPE.NON_NEGATIVE_INTEGER;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.NON_POSITIVE_INTEGER.getLabel())){
			return ONTOLOGY_DATA_TYPE.NON_POSITIVE_INTEGER;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.LONG.getLabel())){
			return ONTOLOGY_DATA_TYPE.LONG;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.UNSIGNED_LONG.getLabel())){
			return ONTOLOGY_DATA_TYPE.UNSIGNED_LONG;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.UNSIGNED_INTEGER.getLabel())){
			return ONTOLOGY_DATA_TYPE.UNSIGNED_INTEGER;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.STRING.getLabel())){
			return ONTOLOGY_DATA_TYPE.STRING;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.NORMALIZED_STRING.getLabel())){
			return ONTOLOGY_DATA_TYPE.NORMALIZED_STRING;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.TOKEN.getLabel())){
			return ONTOLOGY_DATA_TYPE.TOKEN;
		}
		
		if(dt.equals(ONTOLOGY_DATA_TYPE.FLOAT.getLabel())){
			return ONTOLOGY_DATA_TYPE.FLOAT;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.TIME.getLabel())){
			return ONTOLOGY_DATA_TYPE.TIME;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.DATE.getLabel())){
			return ONTOLOGY_DATA_TYPE.DATE;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.BOOLEAN.getLabel())){
			return ONTOLOGY_DATA_TYPE.BOOLEAN;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.BYTE.getLabel())){
			return ONTOLOGY_DATA_TYPE.BYTE;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.UNSIGNED_BYTE.getLabel())){
			return ONTOLOGY_DATA_TYPE.UNSIGNED_BYTE;
		}

		if(dt.equals(ONTOLOGY_DATA_TYPE.ANY_URI.getLabel())){
			return ONTOLOGY_DATA_TYPE.ANY_URI;
		}
		
		return null;
	}
}
