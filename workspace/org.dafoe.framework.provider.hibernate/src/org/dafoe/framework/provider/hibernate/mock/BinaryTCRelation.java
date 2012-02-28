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
package org.dafoe.framework.provider.hibernate.mock;

import org.dafoe.framework.core.mock.MockBinaryTCRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;

// TODO: Auto-generated Javadoc
/**
 * The BinaryTCRelation Class is a non-serializable class only use as modeling ressource.
 *  @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a> 
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class BinaryTCRelation implements MockBinaryTCRelation{
	
	/** The tc1. */
	ITerminoConcept tc1;
	
	/** The tc2. */
	ITerminoConcept tc2;
	
	/** The type. */
	ITypeRelationTc type;
	
	/** The status. */
	TERMINO_ONTO_OBJECT_STATE status;

	/** The origin. */
	ITerminoConceptRelation origin;

	/**
	 * Instantiates a new binary tc relation.
	 */
	public BinaryTCRelation() {
	}

	/**
	 * Sets the tc1.
	 *
	 * @param tc the new tc1
	 */
	public void setTc1(ITerminoConcept tc) {
		
		this.tc1 = tc;

	}

	/**
	 * Gets the tc1.
	 *
	 * @return the tc1
	 */
	public ITerminoConcept getTc1() {
		return this.tc1;
	}

	/**
	 * Sets the tc2.
	 *
	 * @param tc the new tc2
	 */
	public void setTc2(ITerminoConcept tc) {
		
		this.tc2 = tc;
	}

	/**
	 * Gets the tc2.
	 *
	 * @return the tc2
	 */
	public ITerminoConcept getTc2() {
		return this.tc2;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(ITypeRelationTc type) {
	
		this.type = type;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public ITypeRelationTc getType() {
		return this.type;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(TERMINO_ONTO_OBJECT_STATE state) {
		
		this.status = state;
		
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public TERMINO_ONTO_OBJECT_STATE getState() {
		return this.status;
	}

	//

	/**
	 * Sets the origin.
	 *
	 * @param tcr the new origin
	 */
	public void setOrigin(ITerminoConceptRelation tcr) {
		this.origin = tcr;
	}

	//

	/**
	 * Gets the origin.
	 *
	 * @return the origin
	 */
	public ITerminoConceptRelation getOrigin() {
		return this.origin;
	}


}
