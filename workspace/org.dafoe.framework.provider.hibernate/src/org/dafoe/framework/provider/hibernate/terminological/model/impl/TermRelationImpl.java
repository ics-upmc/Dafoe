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

import org.dafoe.framework.core.terminological.model.IOriginRelation;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTermRelationImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptRelationImpl;
import org.dafoe.framework.provider.hibernate.util.Util;

/**
 * The Class TermRelationImpl.
 * 
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TermRelationImpl extends BaseTermRelationImpl implements
		ITermRelation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new term relation impl.
	 */
	public TermRelationImpl() {
		super();
		super.setTerm1(new TermImpl());
		super.setTerm2(new TermImpl());
		super.setTypeRel(new TypeRelationTerminoImpl());
		setState(TERMINO_OBJECT_STATE.UNKNOWN);
		super.setOriginRelation(new HashSet<IOriginRelation>());
		
		super.setTerminology(new TerminologyImpl());		
		//super.setTerminology(SingleObject.getInstanceTerminology());		
		
		

	}

//	@Override
	public void setTerm1(ITerm term1) {
		super.setTerm1((TermImpl) term1);
	}

//	@Override
	public void setTerm2(ITerm term2) {
		super.setTerm2((TermImpl) term2);

	}

//	@Override
	public void setTypeRel(ITypeRelationTermino typeRel) {
		super.setTypeRel((TypeRelationTerminoImpl) typeRel);

	}
	/*
	@Override
	public void setTerminology(ITerminology terminology) {
		super.setTerminology((TerminologyImpl) terminology);

	}*/

//	@Override
	public boolean addOriginRelation(IOriginRelation originRel) {
		
		((OriginRelationImpl)originRel).setTermRelation(this);
		
		return super.getOriginRelation().add(originRel);

	}

	

//	@Override
	public boolean addMappedTerminoConceptRelation(ITerminoConceptRelation itc) {
		
		
		boolean ok1= super.getMappedTerminoConceptRelations().add(itc);
		boolean ok2= ((TerminoConceptRelationImpl)itc).getMappedTermRelations().add(this);
		return ok1 && ok2;
		
	}

//	@Override
	public Set<ITerminoConceptRelation> getMappedTerminoConceptRelations() {
		
		return super.getMappedTerminoConceptRelations();
	}

//	@Override
	public boolean removeMappedTerminoConceptRelation(ITerminoConceptRelation itc) {
		boolean ok1= ((TerminoConceptRelationImpl)itc).getMappedTermRelations().remove(this);
		boolean ok2= super.getMappedTerminoConceptRelations().remove(itc);
		return ok1 && ok2;
		
	}

	@Override
	public boolean removeOriginRelation(IOriginRelation originRel) {
		
		return super.getOriginRelation().remove(originRel);
	}
	
	@Override
	public void setState(TERMINO_OBJECT_STATE status) {
		super.setStatus(status.getValue());
		
	}

	@Override
	public TERMINO_OBJECT_STATE getState() {
		return Util.getTerminoObjectStateFromDatabase(super.getStatus());
		
	}
	
	
	@Override
	public boolean addMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		
		boolean ok1= false;
		boolean ok2= false;
		
		if(toObject instanceof ITerminoConceptRelation){
			TerminoConceptRelationImpl tcr= (TerminoConceptRelationImpl)toObject;
			ok1= super.getMappedTerminoConceptRelations().add(tcr);
			ok2= ((TerminoConceptRelationImpl)tcr).getMappedTermRelations().add(this);
			
			return ok1 && ok2;
		}
		return false;
	}

	@Override
	public Set<ITerminoOntoObject> getMappedTerminoOntoObjects() {
		Set<ITerminoOntoObject> toObjs= new HashSet<ITerminoOntoObject>();
		toObjs.addAll(this.getMappedTerminoConceptRelations());
		
		return toObjs;
	}

	@Override
	public boolean removeMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		boolean ok1= false;
		boolean ok2= false;
		
		if(toObject instanceof ITerminoConceptRelation){
			TerminoConceptRelationImpl tcr= (TerminoConceptRelationImpl)toObject;
			ok1= super.getMappedTerminoConceptRelations().remove(tcr);
			ok2= ((TerminoConceptRelationImpl)tcr).getMappedTermRelations().remove(this);
			
			return ok1 && ok2;
		}
		return false;
	}


}
