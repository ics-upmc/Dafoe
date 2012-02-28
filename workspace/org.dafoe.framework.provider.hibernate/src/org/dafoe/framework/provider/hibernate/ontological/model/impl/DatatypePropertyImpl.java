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

import org.dafoe.framework.core.ontological.model.IBasicDatatype;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IConstraintHasValue;
import org.dafoe.framework.core.ontological.model.IConstraintHighThan;
import org.dafoe.framework.core.ontological.model.IConstraintLessThan;
import org.dafoe.framework.core.ontological.model.IConstraintLike;
import org.dafoe.framework.core.ontological.model.IConstraintOneOf;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IOntoAnnotation;
import org.dafoe.framework.core.ontological.model.ONTO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseDatatypePropertyImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptRelationImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TypeRelationTcImpl;
import org.dafoe.framework.provider.hibernate.util.Util;

/**
 * This is the object class that relates to the m23_datatype_property table.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class DatatypePropertyImpl extends BaseDatatypePropertyImpl implements IDatatypeProperty{

	
	
	
	public DatatypePropertyImpl () {
		super();
		super.setOntology(new OntologyImpl());
		super.setRange(null);
		//super.setParent(new DatatypePropertyImpl());
		super.setAnnotations(new HashSet<IOntoAnnotation>());
		super.setChildren(new HashSet<IDatatypeProperty>());
		super.setMappedTerminoConceptRelations(new HashSet<ITerminoConceptRelation>());
		super.setMappedTerminoConcepts(new HashSet<ITerminoConcept>());
		super.setMappedTerminoConceptRelationTypes(new HashSet<ITypeRelationTc>());
		super.setDomains(new HashSet<IClass>());
		super.setMaximalCardinality(1);
		super.setMinimalCardinality(1);
		setState(ONTO_OBJECT_STATE.UNKNOWN);
		
		super.setConstraintHasValue(new HashSet<IConstraintHasValue>());
		super.setConstraintHighThan(new HashSet<IConstraintHighThan>());
		super.setConstraintLessThan(new HashSet<IConstraintLessThan>());
		super.setConstraintOneOf(new HashSet<IConstraintOneOf>());
		super.setConstraintLike(new HashSet<IConstraintLike>());
		
	}

	


	/* (non-Javadoc)
 * @see org.dafoe.framework.core.ontological.model.IProperty#addDomain(org.dafoe.framework.core.ontological.model.IClass)
 */
//@Override
	public boolean addDomain(IClass domain) {
		boolean ok1= super.getDomains().add(domain);
		boolean ok2= domain.getDatatypeProperties().add(this);
		return ok1 && ok2;
		
	}

	
	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.ontological.model.IDatatypeProperty#setRange(org.dafoe.framework.core.ontological.model.IBasicDatatype)
	 */
//	@Override
	public void setRange(IBasicDatatype range) {
		super.setRange((BasicDatatypeImpl) range);
		
	}

		
	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.ontological.model.IProperty#removeDomain(org.dafoe.framework.core.ontological.model.IClass)
	 */
//	@Override
	public boolean removeDomain(IClass domain) {
		boolean ok1= this.getDomains().remove(domain);
		boolean ok2= domain.getDatatypeProperties().remove(this);
		return ok1 && ok2;
	}

	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.ontological.model.IDatatypeProperty#addChildren(org.dafoe.framework.core.ontological.model.IDatatypeProperty)
	 */
//	@Override
	public boolean addChild(IDatatypeProperty dProp) {
		
		((DatatypePropertyImpl)dProp).setParent(this);
		return super.getChildren().add(dProp);
	}


	@Override
	public void setStatus(Integer status) {
		super.setStatus( status);
		
	}

		
	
//	@Override
	public boolean removeChild(IDatatypeProperty prop) {
		((DatatypePropertyImpl)prop).setParent(null);
		return super.getChildren().remove(prop);
		
	}

//	@Override
	public boolean addAnnotation(IOntoAnnotation ontoAnnotation) {
		
		((DataTypePropertyAnnotationImpl)ontoAnnotation).setItsProperty(this);
		
		return super.getAnnotations().add(ontoAnnotation);
	}

	@Override
	public Set<IOntoAnnotation> getAnnotations() {
		
		return super.getAnnotations();
	}

//	@Override
	public boolean removeAnnotation(IOntoAnnotation ontoAnnotation) {
		((DataTypePropertyAnnotationImpl)ontoAnnotation).setItsProperty(null);
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
			ok2= ((TerminoConceptImpl)tc).getMappedDatatypeProperties().add(this);
			return ok1 && ok2;
			
		}
		if(toObject instanceof ITerminoConceptRelation){
			ITerminoConceptRelation tcr= (ITerminoConceptRelation)toObject;
					
			ok1= super.getMappedTerminoConceptRelations().add(tcr);
			ok2= ((TerminoConceptRelationImpl)tcr).getMappedDatatypeProperties().add(this);
			return ok1 && ok2;
		}
		
		if(toObject instanceof ITypeRelationTc){
			ITypeRelationTc tcrType= (ITypeRelationTc) toObject;
						
			ok1= super.getMappedTerminoConceptRelationTypes().add(tcrType);
			ok2= ((TypeRelationTcImpl)tcrType).getMappedDatatypeProperties().add(this);
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
			isRemove2= ((TerminoConceptImpl)tc).getMappedDatatypeProperties().remove(this); 
			return isRemove1 && isRemove2;
		}
		if(toObject instanceof ITerminoConceptRelation){
			ITerminoConceptRelation tcr= (ITerminoConceptRelation)toObject;
			
			isRemove1= super.getMappedTerminoConceptRelations().remove(tcr);
			isRemove2= ((TerminoConceptRelationImpl)tcr).getMappedDatatypeProperties().remove(this);
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



/*
	@Override
	public ONTOLOGY_DATA_TYPE getRange1() {
		
		return Util.getOntologyDataTypeFromDatabase(super.getBasicRange());
	}




	@Override
	public void setRange(ONTOLOGY_DATA_TYPE range) {
		super.setBasicRange(range.getLabel());
		
	}
	*/

}