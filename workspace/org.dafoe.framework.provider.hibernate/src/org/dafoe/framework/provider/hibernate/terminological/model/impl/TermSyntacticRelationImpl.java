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
package org.dafoe.framework.provider.hibernate.terminological.model.impl;

import java.util.HashSet;
import java.util.Set;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermSyntacticRelation;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTermSyntacticRelationImpl;
import org.dafoe.framework.provider.hibernate.util.Util;

/**
 *The Class TermSyntacticRelationImpl.
 * 
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TermSyntacticRelationImpl extends BaseTermSyntacticRelationImpl
		implements ITermSyntacticRelation {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TermSyntacticRelationImpl () {
		super();
		super.setHeadTerm(new TermImpl());
		super.setModifierTerm(new TermImpl());
		
		super.setTerminology(new TerminologyImpl());
		//super.setTerminology(SingleObject.getInstanceTerminology());

		setState(TERMINO_OBJECT_STATE.UNKNOWN);
	}


//	@Override
	public void setHeadTerm(ITerm term) {
		super.setHeadTerm((TermImpl) term);

	}

//	@Override
	public void setModifierTerm(ITerm term) {
		super.setModifierTerm((TermImpl) term);

	}

	@Override
	public void setState(TERMINO_OBJECT_STATE status) {
		super.setStatus(status.getValue());
		
	}
	
	@Override
	public TERMINO_OBJECT_STATE getState() {
		return Util.getTerminoObjectStateFromDatabase(super.getStatus());
		//return null;
	}
	

	/////////// TO COMPLETE
	
	@Override
	public boolean addMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public Set<ITerminoOntoObject> getMappedTerminoOntoObjects() {
		// TODO Auto-generated method stub
		return new HashSet<ITerminoOntoObject>();
	}


	@Override
	public boolean removeMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		// TODO Auto-generated method stub
		return false;
	}

	/*@Override
	public void setTerminology(ITerminology terminology) {
		super.setTerminology((TerminologyImpl) terminology);

	}*/

}