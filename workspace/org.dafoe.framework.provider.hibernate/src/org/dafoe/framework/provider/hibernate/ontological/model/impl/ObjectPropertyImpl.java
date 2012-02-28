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
package org.dafoe.framework.provider.hibernate.ontological.model.impl;

import java.util.HashSet;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IConstraintAllValueFrom;
import org.dafoe.framework.core.ontological.model.IConstraintMinMax;
import org.dafoe.framework.core.ontological.model.IConstraintSomeValueFrom;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoAnnotation;
import org.dafoe.framework.core.ontological.model.ONTO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseObjectPropertyImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptRelationImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TypeRelationTcImpl;
import org.dafoe.framework.provider.hibernate.util.Util;

/**
 * This is the object class that relates to the m23_object_property table.
 * Any customizations belong here.
 * 
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class ObjectPropertyImpl extends BaseObjectPropertyImpl implements IObjectProperty {
	
	

	

	public ObjectPropertyImpl () {
		super();
		super.setOntology(new OntologyImpl());
		super.setRange(null);
		//super.setInverseOf(new ObjectPropertyImpl());
		
		super.setAnnotations(new HashSet<IOntoAnnotation>());
		super.setChildren(new HashSet<IObjectProperty>());
		super.setMappedTerminoConceptRelations(new HashSet<ITerminoConceptRelation>());
		super.setMappedTerminoConcepts(new HashSet<ITerminoConcept>());
		super.setMappedTerminoConceptRelationTypes(new HashSet<ITypeRelationTc>());
		super.setDomains(new HashSet<IClass>());
		super.setMaximalCardinality(1);
		super.setMinimalCardinality(1);
		setState(ONTO_OBJECT_STATE.UNKNOWN);
		super.setConstraintAllValueFrom(new HashSet<IConstraintAllValueFrom>());
		super.setConstraintSomeValueFrom(new HashSet<IConstraintSomeValueFrom>());
		super.setConstraintMinMax(new HashSet<IConstraintMinMax>());
		
	}


	

//@Override
	public void setInverseOf(IObjectProperty inverseOf) {
		super.setInverseOf((ObjectPropertyImpl) inverseOf);
		
	}

	

	
//	@Override
	public void setRange(IClass range) {
		super.setRange((ClassImpl) range);
		
	}


	
//	@Override
	public boolean addDomain(IClass domain) {
		
		boolean ok1= super.getDomains().add(domain);
		boolean ok2= domain.getObjectProperties().add(this);
		return ok1 && ok2;
	}

	
	@Override
	public Set<IClass> getDomains() {
		return super.getDomains();
	}


//	@Override
	public boolean removeDomain(IClass domain) {
		boolean ok1= this.getDomains().remove(domain);
		boolean ok2= domain.getObjectProperties().remove(this);
		return ok1 && ok2;
	}

	
//	@Override
	public boolean addChild(IObjectProperty objProp) {
		//super.addToChildren(objProp);
		((ObjectPropertyImpl)objProp).setParent(this);
		return super.getChildren().add(objProp);
	}


	@Override
	public boolean removeChild(IObjectProperty objProp) {
		
		((ObjectPropertyImpl) objProp).setParent(null);
		
		return super.getChildren().remove(objProp);
	}

//	@Override
	public boolean addAnnotation(IOntoAnnotation ontoAnnotation) {
		
		//super.addToAnnotations(ontoAnnotation);
		((ObjectPropertyAnnotationImpl)ontoAnnotation).setItsProperty(this);
		
		return super.getAnnotations().add(this);
	}

	@Override
	public Set<IOntoAnnotation> getAnnotations() {
		
		return super.getAnnotations();
	}

//	@Override
	public boolean removeAnnotation(IOntoAnnotation ontoAnnotation) {
		((ObjectPropertyAnnotationImpl)ontoAnnotation).setItsProperty(null);
		return super.getAnnotations().remove(ontoAnnotation);
		// a delete(annotation) must be done
	}

//	@Override
	public boolean addMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		boolean ok1= false;
		boolean ok2= false;
		
		if(toObject instanceof ITerminoConcept){
			ITerminoConcept tc= (ITerminoConcept)toObject;
			
			ok1= super.getMappedTerminoConcepts().add(tc);
			ok2= ((TerminoConceptImpl)tc).getMappedObjectProperties().add(this);
			return ok1 && ok2;
		}
		if(toObject instanceof ITerminoConceptRelation){
			ITerminoConceptRelation tcr= (ITerminoConceptRelation)toObject;
			
			ok1= super.getMappedTerminoConceptRelations().add(tcr);
			ok2= ((TerminoConceptRelationImpl)tcr).getMappedObjectProperties().add(this);
			return ok1 && ok2;
		}
		if(toObject instanceof ITypeRelationTc){
			ITypeRelationTc tcrType= (ITypeRelationTc) toObject;
			
			ok1= super.getMappedTerminoConceptRelationTypes().add(tcrType);
			ok2= ((TypeRelationTcImpl)tcrType).getMappedObjectProperties().add(this);
			return ok1 && ok2;
	    	   
			}
		
		return false;
	}

//	@Override
	public Set<ITerminoOntoObject> getMappedTerminoOntoObjects() {
		HashSet<ITerminoOntoObject> toObjs= new HashSet<ITerminoOntoObject>();
		
		toObjs.addAll(super.getMappedTerminoConcepts());
		toObjs.addAll(super.getMappedTerminoConceptRelations());
		toObjs.addAll(super.getMappedTerminoConceptRelationTypes());
		
		return toObjs;
	}

//	@Override
	public boolean removeMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		boolean isRemove1=false;
		boolean isRemove2=false;
		
		if(toObject instanceof ITerminoConcept){
			ITerminoConcept tc= (ITerminoConcept)toObject;
			
			isRemove1= super.getMappedTerminoConcepts().remove(tc);
			isRemove2= ((TerminoConceptImpl)tc).getMappedObjectProperties().remove(this); 
			return isRemove1 && isRemove2;
		}
		if(toObject instanceof ITerminoConceptRelation){
			ITerminoConceptRelation tcr= (ITerminoConceptRelation)toObject;
			
			isRemove1= super.getMappedTerminoConceptRelations().remove(tcr);
			isRemove2= ((TerminoConceptRelationImpl)tcr).getMappedObjectProperties().remove(this);
			return isRemove1 && isRemove2;
		}
		
		if(toObject instanceof ITypeRelationTc){
			ITypeRelationTc tcrType= (ITypeRelationTc) toObject;
			isRemove1 = super.getMappedTerminoConceptRelations().remove(tcrType);
			isRemove2 = ((TerminoConceptRelationImpl) tcrType).getMappedClasses()
					.remove(this);
			return isRemove1 && isRemove2;
			}
		return false;
	}

	@Override
	public ONTO_OBJECT_STATE getState() {
		
		return Util.getOntoObjectStateFromDatabase(super.getStatus());
	}

	@Override
	public void setState(ONTO_OBJECT_STATE state) {
		super.setStatus(state.getValue());
		
	}
}
