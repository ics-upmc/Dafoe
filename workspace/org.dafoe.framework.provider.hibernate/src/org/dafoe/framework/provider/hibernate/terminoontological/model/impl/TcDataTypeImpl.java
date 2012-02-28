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
package org.dafoe.framework.provider.hibernate.terminoontological.model.impl;

import java.util.HashSet;

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptOccurrence;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelationMember;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTcDataTypeImpl;

/**
 * The Class TcDataTypeImpl.
 * 
 *  * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TcDataTypeImpl extends BaseTcDataTypeImpl {

	public TcDataTypeImpl () {
		super();
		super.setChildren(new HashSet<ITerminoConcept>());
		super.setParents(new HashSet<ITerminoConcept>());
		super.setMappedClasses(new HashSet<IClass>());
		super.setMappedDatatypeProperties(new HashSet<IDatatypeProperty>());
		super.setMappedObjectProperties(new HashSet<IObjectProperty>());
		super.setMappedTerms(new HashSet<ITerm>());
		super.setRelationsMember(new HashSet<ITerminoConceptRelationMember>());
		super.setTerminoConceptOccurrences(new HashSet<ITerminoConceptOccurrence>());
		
		super.setStatus(TERMINO_ONTO_OBJECT_STATE.STUDIED.getValue());
		
		// a tcData is  simple
		super.setSimple(true);
	}

	
}