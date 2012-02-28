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
package org.dafoe.framework.core.mock;

import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;

public interface MockBinaryTCRelation {

	
    void setTc1(ITerminoConcept tc);
	ITerminoConcept getTc1();

	void setTc2(ITerminoConcept tc);

	ITerminoConcept getTc2();
	
	void setType(ITypeRelationTc type);

	ITypeRelationTc getType() ;
	
	void setState(TERMINO_ONTO_OBJECT_STATE status) ;

	TERMINO_ONTO_OBJECT_STATE getState();

	//
    void setOrigin(ITerminoConceptRelation tcr) ;	
    
    ITerminoConceptRelation getOrigin() ;
}
