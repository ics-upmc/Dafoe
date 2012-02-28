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

import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelationMember;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRole;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTerminoConceptRelationMemberImpl;

/**
 * The Class TerminoConceptRelationMemberImpl.
 * 
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TerminoConceptRelationMemberImpl extends
		BaseTerminoConceptRelationMemberImpl implements
		ITerminoConceptRelationMember {

	
	public TerminoConceptRelationMemberImpl() {
		super();
		

	}

	

//	@Override
	public void setTerminoConcept(ITerminoConcept tc) {
		super.setTerminoConcept((TerminoConceptImpl) tc);

	}

//	@Override
	public void setTerminoConceptRelation(ITerminoConceptRelation tcRelation) {
		super.setTerminoConceptRelation((TerminoConceptRelationImpl) tcRelation);

	}

//	@Override
	public void setTerminoConceptRole(ITerminoConceptRole tcRole) {
		super.setTerminoConceptRole((TerminoConceptRoleImpl) tcRole);

	}
}