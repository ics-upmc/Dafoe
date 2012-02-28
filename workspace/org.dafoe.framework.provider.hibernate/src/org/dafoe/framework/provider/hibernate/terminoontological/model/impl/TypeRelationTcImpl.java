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
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoObject;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotation;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.DatatypePropertyImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TypeRelationTerminoImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTypeRelationTcImpl;
import org.dafoe.framework.provider.hibernate.util.Util;

/**
 * The TypeRelationTcImpl.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TypeRelationTcImpl extends BaseTypeRelationTcImpl implements ITypeRelationTc {


	public TypeRelationTcImpl () {
		super();
		super.setChildren(new HashSet<ITypeRelationTc>());
		super.setMappedTermRelationTypes(new HashSet<ITermRelation>());
		super.setTcRelation(new HashSet<ITerminoConceptRelation>());
		super.setMappedClasses(new HashSet<IClass>());
		super.setMappedDatatypeProperties(new HashSet<IDatatypeProperty>());
		super.setMappedObjectProperties(new HashSet<IObjectProperty>());
		
		super.setStatus(TERMINO_ONTO_OBJECT_STATE.UNKNOWN.getValue());
	}

	

	/* (non-Javadoc)
 * @see org.dafoe.framework.core.terminoontological.model.ITypeRelationTc#addTerminoConceptRelation(org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation)
 */
//@Override
	public boolean addTerminoConceptRelation(ITerminoConceptRelation tcRelation) {
		
		((TerminoConceptRelationImpl)tcRelation).setTypeRelation(this);
		return super.getTcRelation().add(tcRelation);
		
	}

	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminoontological.model.ITypeRelationTc#setParent(org.dafoe.framework.core.terminoontological.model.ITypeRelationTc)
	 */
	/*@Override
	public void setParent(ITypeRelationTc parent) {
		super.setParent((TypeRelationTcImpl) parent);
		
		if(parent!= null){
			parent.addChildren(this);
		}
	}
*/

	


	@Override
	public Set<ITypeRelationTermino> getMappedTermRelationTypes() {
		// TODO Auto-generated method stub
		return super.getMappedTermRelationTypes();
	}

//	@Override
	public boolean addMappedTermRelationTypes(ITypeRelationTermino typeRelTermino) {
		
		boolean ok1= super.getMappedTermRelationTypes().add(typeRelTermino);
		boolean ok2= ((TypeRelationTerminoImpl)typeRelTermino).getMappedTcRelationTypes().add(typeRelTermino);
		
		return ok1 && ok2;
		
	}

//	@Override
	public boolean addChild(ITypeRelationTc typeRelTc) {
		
		((TypeRelationTcImpl)typeRelTc).setParent(this);
		return super.getChildren().add(typeRelTc);
	}

//	@Override
	public boolean removeChild(ITypeRelationTc typeRelTc) {
		boolean isRemoved=false;
		
		isRemoved= super.getChildren().remove(typeRelTc);
		
		((TypeRelationTcImpl)typeRelTc).setParent(null);
		
		return isRemoved;
	}

//	@Override
	public boolean removeMappedTermRelationTypes(ITypeRelationTermino typeRelTermino) {
		boolean isRemoved1= false;
		boolean isRemoved2= false;
		
		isRemoved1= super.getMappedTermRelationTypes().remove(typeRelTermino);
		isRemoved2= ((TypeRelationTerminoImpl)typeRelTermino).getMappedTcRelationTypes().remove(this);
		
		return isRemoved1 && isRemoved2;
	}

	
//	@Override
	public boolean addMappedOntoObject(IOntoObject ontoObject) {
		boolean ok1= false;
		boolean ok2= false;
		
		if (ontoObject instanceof IClass){
			IClass cls= (IClass)ontoObject;

			ok1= super.getMappedClasses().add(cls);
			ok2= ((ClassImpl)cls).getMappedTerminoConceptRelationTypes().add(this);
			return ok1 && ok2;
		}
		if(ontoObject instanceof IDatatypeProperty){
			IDatatypeProperty dProp= (IDatatypeProperty)ontoObject;
			
			ok1= super.getMappedDatatypeProperties().add(dProp);
			ok2= ((DatatypePropertyImpl)dProp).getMappedTerminoConceptRelationTypes().add(this);
			
			return ok1 && ok2;
		}
		if(ontoObject instanceof IObjectProperty){
			IObjectProperty oProp=(IObjectProperty)ontoObject;
			
			ok1= super.getMappedObjectProperties().add(oProp);
			ok2= ((ObjectPropertyImpl)oProp).getMappedTerminoConceptRelationTypes().add(this);
			
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
	public boolean addAnnotation(ITerminoOntoAnnotation annot) {
		
		return super.getAnnotations().add(annot);
		
	}

//	@Override
	public boolean removeAnnotation(ITerminoOntoAnnotation annot) {
		
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



	@Override
	public String getLabel() {
		
		return super.getName();
	}
}

	
