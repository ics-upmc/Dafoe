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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoObject;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptOccurrence;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelationMember;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotation;
import org.dafoe.framework.core.terminoontological.model.ITerminoProperty;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.DatatypePropertyImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTerminoConceptImpl;
import org.dafoe.framework.provider.hibernate.util.Util;

/**
 * The Class TerminoConceptImpl.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TerminoConceptImpl extends BaseTerminoConceptImpl implements
		ITerminoConcept {

	
	public TerminoConceptImpl () {
		super();
		super.setChildren(new HashSet<ITerminoConcept>());
		super.setParents(new HashSet<ITerminoConcept>());
		super.setMappedClasses(new HashSet<IClass>());
		super.setMappedDatatypeProperties(new HashSet<IDatatypeProperty>());
		super.setMappedObjectProperties(new HashSet<IObjectProperty>());
		super.setMappedTerms(new HashSet<ITerm>());
		super.setRelationsMember(new HashSet<ITerminoConceptRelationMember>());
		super.setTerminoConceptOccurrences(new HashSet<ITerminoConceptOccurrence>());
		super.setAnnotations(new HashSet<ITerminoOntoAnnotation>());
		
	
		
		super.setStatus(TERMINO_ONTO_OBJECT_STATE.UNKNOWN.getValue());
		// a tc is not simple
		super.setSimple(false);
		
	}


		
	public TerminoConceptImpl(Integer _id) {
		// TODO Auto-generated constructor stub
	}



	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminoontological.model.ITerminoConcept#addTerminoProperty(org.dafoe.framework.core.terminoontological.model.ITerminoProperty)
	 */
//	@Override
	public void addTerminoProperty(ITerminoProperty tProp) {
		
			super.addToTerminoProperties(tProp);
			((TerminoPropertyImpl)tProp).addToTerminoConcepts(this);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminoontological.model.ITerminoConcept#addTerminoConceptOccurrence(org.dafoe.framework.core.terminoontological.model.ITerminoConceptOccurrence)
	 */
//	@Override
	public void addTerminoConceptOccurrence(ITerminoConceptOccurrence tcOccur) {
		super.addToTerminoConceptOccurrences(tcOccur);
		((TerminoConceptOccurrenceImpl)tcOccur).setTerminoConcept(this);

	}



	

	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject#setTerminoOntology(org.dafoe.framework.core.terminoontological.model.ITerminoOntology)
	 */
	/*@Override
	public void setTerminoOntology(ITerminoOntology terminoOntology) {
		super.setTerminoOntology((TerminoOntologyImpl) terminoOntology);
		
	}*/

	


	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminoontological.model.ITerminoConcept#getTerminoConceptRelationsMember()
	 */
	@SuppressWarnings("unchecked")
//	@Override
	public Set<ITerminoConceptRelationMember> getTerminoConceptRelationsMember() {
		
		return super.getRelationsMember();
	}

//	@Override
	public boolean addChild(ITerminoConcept tc) {
		//super.addToChildren(tc);
		//((TerminoConceptImpl)tc).addToParents(this);
		
		 boolean ok1= super.getChildren().add(tc);
         boolean ok2= ((TerminoConceptImpl) tc).getParents().add(this);
		return  ok1 && ok2;
		
	}

//	@Override
	public boolean addParent(ITerminoConcept tc) {
		//super.addToParents(tc);
		//((TerminoConceptImpl)tc).addToChildren(this);
		
		boolean ok1= super.getParents().add(tc);
        boolean ok2= ((TerminoConceptImpl) tc).getChildren().add(this);
		return  ok1 && ok2;
		
	}

	
	// mapping with term
//	@Override
	public boolean addMappedTerm(ITerm term) {
		//super.addToMappedTerms(term);
		//((TermImpl)term).addMappedTerminoConcept(this);
		
		boolean ok1= false;
		boolean ok2= false;
		
			
			ok1=  super.getMappedTerms().add(term);
			ok2= ((TermImpl)term).getMappedTerminoConcepts().add(this);
			
			return ok1 && ok2;
		
		
	}

	//services
//	@Override
	public boolean addMappedOntoObject(IOntoObject ontoObject) {
		boolean ok1= false;
		boolean ok2= false;
		
		if (ontoObject instanceof IClass){
			IClass cls= (IClass)ontoObject;
			
			//super.addToMappedClasses(cls);
			//((ClassImpl)cls).addToMappedTerminoConcepts(this);
			ok1=  super.getMappedClasses().add(cls);
			ok2= ((ClassImpl)cls).getMappedTerminoConcepts().add(this);
			
			return ok1 && ok2;
			
		}
		if(ontoObject instanceof IDatatypeProperty){
			IDatatypeProperty dProp= (IDatatypeProperty)ontoObject;
			
			//super.addToMappedDatatypeProperties(dProp);
			//((DatatypePropertyImpl)dProp).addToMappedTerminoConcepts(this);
			ok1= super.getMappedDatatypeProperties().add(dProp);
			ok2= ((DatatypePropertyImpl)dProp).getMappedTerminoConcepts().add(this);
			return ok1 && ok2;
		}
		if(ontoObject instanceof IObjectProperty){
			IObjectProperty oProp=(IObjectProperty)ontoObject;
			
			//super.addToMappedObjectProperties(oProp);
			//((ObjectPropertyImpl)oProp).addToMappedTerminoConcepts(this);
			
			ok1= super.getMappedObjectProperties().add(oProp);
			ok2= ((ObjectPropertyImpl)oProp).getMappedTerminoConcepts().add(this);
			
			
			return ok1 && ok2;
		}
		return false;
	}

//	@Override
	public Set<IOntoObject> getMappedOntoObjects() {
		Set<IOntoObject> objs= new HashSet<IOntoObject>();
		
		objs.addAll(super.getMappedClasses());
		objs.addAll(super.getMappedDatatypeProperties());
		objs.addAll(super.getMappedObjectProperties());
		
		
		return objs;
	}

//	@Override
	public boolean removeMappedOntoObject(IOntoObject ontoObject) {
        
		boolean isRemoved1= false;
        boolean isRemoved2= false;
        
		if (ontoObject instanceof IClass){
			IClass cls= (IClass)ontoObject;
			
			isRemoved1= super.getMappedClasses().remove(cls);
			isRemoved2= ((ClassImpl)cls).getMappedTerminoConcepts().remove(this);
		}
		if(ontoObject instanceof IDatatypeProperty){
			IDatatypeProperty dProp= (IDatatypeProperty)ontoObject;
			
			isRemoved1= super.getMappedDatatypeProperties().remove(dProp);
			isRemoved2= ((DatatypePropertyImpl)dProp).getMappedTerminoConcepts().remove(this);
		}
		if(ontoObject instanceof IObjectProperty){
			IObjectProperty oProp=(IObjectProperty)ontoObject;
			
			isRemoved1= super.getMappedObjectProperties().remove(oProp);
			isRemoved2= ((ObjectPropertyImpl)oProp).getMappedTerminoConcepts().remove(this);
		}
		return isRemoved1 && isRemoved2;
		
		
	}

//	@Override
	public boolean removeTerminoConceptOccurence(
			ITerminoConceptOccurrence tcOccur) {
		boolean isRemoved= false;
		isRemoved= super.getTerminoConceptOccurrences().remove(tcOccur);
		((TerminoConceptOccurrenceImpl)tcOccur).setTerminoConcept(null);

		// tcOccur must be delete
		
		return false;
	}



//	@Override
	public boolean removeMappedTerm(ITerm term) {
		boolean isRemoved1= false;
		boolean isRemoved2= false;
		
		isRemoved1= super.getMappedTerms().remove(term);
		isRemoved2=((TermImpl)term).getMappedTerminoConcepts().remove(this);
		
		return isRemoved1 && isRemoved2;
		
	}



//	@Override
	public Set<ITerminoConcept> getAncestors() {
		Set<ITerminoConcept> result = new HashSet<ITerminoConcept>();
		List<ITerminoConcept> totreat = new ArrayList<ITerminoConcept>();
		
		totreat.add(this);
		while(totreat.size()>0) {
			ITerminoConcept currentTc = totreat.get(0);
			Iterator<ITerminoConcept> iter = currentTc.getParents().iterator();
			while (iter.hasNext()) {
				ITerminoConcept parent = iter.next();
				if (!result.contains(parent)) {
					totreat.add(parent);
					result.add(parent);
				}
			}
			totreat.remove(0);
			
		}
		
		
		
		return result;
	}



//	@Override
	public Set<ITerminoConcept> getDescendants() {
		Set<ITerminoConcept> result = new HashSet<ITerminoConcept>();
		List<ITerminoConcept> totreat = new ArrayList<ITerminoConcept>();
		
		totreat.add(this);
		while(totreat.size()>0) {
			ITerminoConcept currentTc = totreat.get(0);
			Iterator<ITerminoConcept> iter = currentTc.getChildren().iterator();
			while (iter.hasNext()) {
				ITerminoConcept child = iter.next();
				if (!result.contains(child)) {
					totreat.add(child);
					result.add(child);
				}
			}
			totreat.remove(0);
			
		}
		
		
		
		return result;
	}



//	@Override
	public boolean removeChild(ITerminoConcept tc) {
		boolean isRemoved1= false;
		boolean isRemoved2= false;
		
		isRemoved1= super.getChildren().remove(tc);
		isRemoved2=((TerminoConceptImpl)tc).getParents().remove(this);
		
		return isRemoved1 && isRemoved2;
	}



//	@Override
	public boolean removeParent(ITerminoConcept tc) {
		boolean isRemoved1= false;
		boolean isRemoved2= false;
		
		isRemoved1= super.getParents().remove(tc);
		isRemoved2=((TerminoConceptImpl)tc).getChildren().remove(this);
		
		return isRemoved1 && isRemoved2;
	}



//	@Override
	public boolean addAnnotation(ITerminoOntoAnnotation annot) {
		//super.addToAnnotations(annot);
		((TerminoConceptAnnotationImpl) annot).setItsTerminoConcept(this);
		return super.getAnnotations().add(annot);
	}



//	@Override
	public boolean removeAnnotation(ITerminoOntoAnnotation annot) {
		((TerminoConceptAnnotationImpl) annot).setItsTerminoConcept(null);
		return super.getAnnotations().remove(annot);
		
	}



	@Override
	public TERMINO_ONTO_OBJECT_STATE getState() {
		
		return Util.getTerminoOntoObjectStateFromDatabase(super.getStatus());
	}



	@Override
	public void setState(TERMINO_ONTO_OBJECT_STATE state) {
		super.setStatus(state.getValue());
		
	}



	
}