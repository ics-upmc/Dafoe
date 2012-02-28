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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IClassExtension;
import org.dafoe.framework.core.ontological.model.IClassInstance;
import org.dafoe.framework.core.ontological.model.IConstraintAllValueFrom;
import org.dafoe.framework.core.ontological.model.IConstraintHasValue;
import org.dafoe.framework.core.ontological.model.IConstraintHighThan;
import org.dafoe.framework.core.ontological.model.IConstraintLessThan;
import org.dafoe.framework.core.ontological.model.IConstraintLike;
import org.dafoe.framework.core.ontological.model.IConstraintMinMax;
import org.dafoe.framework.core.ontological.model.IConstraintOneOf;
import org.dafoe.framework.core.ontological.model.IConstraintSomeValueFrom;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoAnnotation;
import org.dafoe.framework.core.ontological.model.IProperty;
import org.dafoe.framework.core.ontological.model.ONTO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.provider.hibernate.factory.model.impl.ExtensionManagerImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.base.BaseClassImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptRelationImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TypeRelationTcImpl;
import org.dafoe.framework.provider.hibernate.util.Util;

/**
 * ClassImpl.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */

public class ClassImpl extends BaseClassImpl implements IClass {

	Set<IClassExtension> classExtensions = null;

	public ClassImpl() {
		super();
		super.setOntology(new OntologyImpl());

		super.setAnnotations(new HashSet<IOntoAnnotation>());
		super.setChildren(new HashSet<IClass>());
		super.setClassInstances(new HashSet<IClassInstance>());
		super.setDatatypeProperties(new HashSet<IDatatypeProperty>());
		super.setObjectProperties(new HashSet<IObjectProperty>());
		super.setParents(new HashSet<IClass>());
		super.setMappedTerminoConceptRelations(new HashSet<ITerminoConceptRelation>());
		super.setMappedTerminoConcepts(new HashSet<ITerminoConcept>());
		super.setMappedTerminoConceptRelationTypes(new HashSet<ITypeRelationTc>());
		super.setConstraintAllValueFrom(new HashSet<IConstraintAllValueFrom>());
		setState(ONTO_OBJECT_STATE.UNKNOWN);
		super
				.setConstraintSomeValueFrom(new HashSet<IConstraintSomeValueFrom>());
		super.setConstraintMinMax(new HashSet<IConstraintMinMax>());

		super.setConstraintHasValue(new HashSet<IConstraintHasValue>());
		super.setConstraintHighThan(new HashSet<IConstraintHighThan>());
		super.setConstraintLessThan(new HashSet<IConstraintLessThan>());
		super.setConstraintOneOf(new HashSet<IConstraintOneOf>());
		super.setConstraintLike(new HashSet<IConstraintLike>());

	}

//	@Override
	public boolean addClassInstance(IClassInstance classInst) {
		
		// classInst.setItsClass(this);
		((ClassInstanceImpl) classInst).setItsClass(this);
        return super.getClassInstances().add(classInst);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.IClass#addDatatypeProperty
	 * (org.dafoe.framework.core.ontological.model.IDatatypeProperty)
	 */
//	@Override
	public boolean addDatatypeProperty(IDatatypeProperty dProp) {
		//super.addToDatatypeProperties(dProp);
		//dProp.addDomain(this);
		
		boolean ok1= super.getDatatypeProperties().add(dProp);
		boolean ok2= dProp.getDomains().add(this);
		return ok1 && ok2;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.IClass#addObjectProperty(org
	 * .dafoe.framework.core.ontological.model.IObjectProperty)
	 */
//	@Override
	public boolean addObjectProperty(IObjectProperty objProp) {
		//super.addToObjectProperties(objProp);
		//objProp.addDomain(this);
		
		boolean ok1= super.getObjectProperties().add(objProp);
		boolean ok2= objProp.getDomains().add(this);
		return ok1 && ok2;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.IClass#addProperty(org.dafoe
	 * .framework.core.ontological.model.IProperty)
	 */
//	@Override
	public boolean addProperty(IProperty prop) {
		if (prop instanceof IDatatypeProperty) {
			return this.addDatatypeProperty((IDatatypeProperty) prop);

		}

		if (prop instanceof IObjectProperty) {
			return this.addObjectProperty((IObjectProperty) prop);
		}
         return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.IClass#addChild(org.dafoe.
	 * framework.core.ontological.model.IClass)
	 */
//	@Override
	public boolean addChild(IClass child) {
        //VT: change the spec to return boolean
		//super.addToChildren(child);
		((ClassImpl) child).addToParents(this);
		return super.getChildren().add(child);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.IClass#addParent(org.dafoe
	 * .framework.core.ontological.model.IClass)
	 */
//	@Override
	public boolean addParent(IClass parent) {
		//super.addToParents(parent);
		//((ClassImpl) parent).addToChildren(this);
         boolean ok1= super.getParents().add(parent);
         boolean ok2= ((ClassImpl) parent).getParents().add(this);
		return  ok1 && ok2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.ontological.model.impl.base.BaseClassImpl#
	 * getDatatypeProperties()
	 */
	@Override
	public Set<IDatatypeProperty> getDatatypeProperties() {
		return super.getDatatypeProperties();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.ontological.model.impl.base.BaseClassImpl#
	 * getObjectProperties()
	 */
	@Override
	public Set<IObjectProperty> getObjectProperties() {

		return super.getObjectProperties();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.ontological.model.impl.base.BaseClassImpl#
	 * getChildren()
	 */
	@Override
	public Set<IClass> getChildren() {
		return super.getChildren();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.impl.base.BaseClassImpl#getParents
	 * ()
	 */
	@Override
	public Set<IClass> getParents() {
		return super.getParents();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.IClass#removeChild(org.dafoe
	 * .framework.core.ontological.model.IClass)
	 */
//	@Override
	public boolean removeChild(IClass child) {
		boolean ok1= super.getChildren().remove(child);
		boolean ok2= ((ClassImpl) child).getParents().remove(this);
		
		return ok1 && ok2;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.IClass#removeClassInstance
	 * (org.dafoe.framework.core.ontological.model.IClassInstance)
	 */
//	@Override
	public boolean removeClassInstance(IClassInstance classInst) {
		// instance must be delete
		// classInst.setItsClass(null);
		((ClassInstanceImpl) classInst).setItsClass(null);

		return super.getClassInstances().remove(classInst);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.IClass#removeDatatypeProperty
	 * (org.dafoe.framework.core.ontological.model.IDatatypeProperty)
	 */
//	@Override
	public boolean removeDatatypeProperty(IDatatypeProperty dProp) {
		boolean ok1= super.getDatatypeProperties().remove(dProp);
		boolean ok2= ((DatatypePropertyImpl) dProp).getDomains().remove(this);

		return ok1 && ok2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.IClass#removeObjectProperty
	 * (org.dafoe.framework.core.ontological.model.IObjectProperty)
	 */
//	@Override
	public boolean removeObjectProperty(IObjectProperty objProp) {
		boolean ok1= super.getObjectProperties().remove(objProp);
		boolean ok2= ((ObjectPropertyImpl) objProp).getDomains().remove(this);
		return ok1 && ok2; 
	}

//	@Override
	public boolean removeProperty(IProperty prop) {

		if (prop instanceof IDatatypeProperty) {
			IDatatypeProperty dProp = (IDatatypeProperty) prop;
			return this.removeDatatypeProperty(dProp);

		}

		if (prop instanceof IObjectProperty) {
			IObjectProperty oProp = (IObjectProperty) prop;
			return this.removeObjectProperty(oProp);

		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.IClass#removeParent(org.dafoe
	 * .framework.core.ontological.model.IClass)
	 */
//	@Override
	public boolean removeParent(IClass parent) {
		boolean ok1= super.getParents().remove(parent);
		boolean ok2= ((ClassImpl) parent).getChildren().remove(this);
        return ok1 && ok2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.IClass#setOntology(org.dafoe
	 * .framework.core.ontological.model.IOntology)
	 */
	/*
	 * @Override public void setOntology(IOntology ontology) {
	 * super.setOntology((OntologyImpl) ontology); if(ontology !=null){
	 * ontology.addOntoObject(this); }
	 * 
	 * }
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.IClass#setStatus(org.dafoe
	 * .framework.core.ontological.model.IOntoObjectStatus)
	 */
	

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.ontological.model.impl.base.BaseClassImpl#
	 * getClassInstances()
	 */
	@Override
	public Set<IClassInstance> getClassInstances() {

		return super.getClassInstances();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.ontological.model.IClass#
	 * getInheritedDatatypeProperties()
	 */
//	@Override
	public Set<IDatatypeProperty> getInheritedDatatypeProperties() {
		Set<IDatatypeProperty> props = new HashSet<IDatatypeProperty>();

		Set<IClass> parents = this.getAncestors(); // corrigé par GT

		Iterator<IClass> iter = parents.iterator();

		while (iter.hasNext()) {
			props.addAll(iter.next().getDatatypeProperties());
		}

		return props;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.ontological.model.IClass#
	 * getInheritedObjectProperties()
	 */
//	@Override
	public Set<IObjectProperty> getInheritedObjectProperties() {
		Set<IObjectProperty> props = new HashSet<IObjectProperty>();

		Set<IClass> parents = this.getAncestors();// corrigé par GT

		Iterator<IClass> iter = parents.iterator();

		while (iter.hasNext()) {

			props.addAll(iter.next().getObjectProperties());
		}

		return props;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.ontological.model.IClass#getInheritedProperties
	 * ()
	 */
//	@Override
	public Set<IProperty> getInheritedProperties() {
		Set<IProperty> props = new HashSet<IProperty>();

		props.addAll(this.getInheritedDatatypeProperties());
		props.addAll(this.getInheritedObjectProperties());

		return props;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dafoe.framework.core.ontological.model.IClass#getProperties()
	 */
//	@Override
	public Set<IProperty> getProperties() {
		Set<IProperty> props = new HashSet<IProperty>();

		try {
			props.addAll(this.getDatatypeProperties());
		} catch (Exception excep) {

		}
		try {
			props.addAll(this.getObjectProperties());
		} catch (Exception excep) {

		}
		return props;
	}

	// /*********************** IMPLEMENTED BY GT
//	@Override
	public Set<IClass> getAncestors() {

		Set<IClass> result = new HashSet<IClass>();
		List<IClass> totreat = new ArrayList<IClass>();

		totreat.add(this);
		while (totreat.size() > 0) {
			IClass curclass = totreat.get(0);
			Iterator<IClass> iter = curclass.getParents().iterator();
			while (iter.hasNext()) {
				IClass parent = iter.next();
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
	public Set<IClass> getDescendants() {
		Set<IClass> result = new HashSet<IClass>();
		List<IClass> totreat = new ArrayList<IClass>();

		totreat.add(this);
		while (totreat.size() > 0) {
			IClass curclass = totreat.get(0);
			Iterator<IClass> iter = curclass.getChildren().iterator();
			while (iter.hasNext()) {
				IClass child = iter.next();
				if (!result.contains(child)) {
					totreat.add(child);
					result.add(child);
				}
			}
			totreat.remove(0);

		}

		return result;
	}

	// FIN GT

//	@Override
	public boolean addAnnotation(IOntoAnnotation ontoAnnotation) {
		//super.addToAnnotations(ontoAnnotation);
		
		((ClassAnnotationImpl) ontoAnnotation).setItsClass(this);
		return super.getAnnotations().add(ontoAnnotation);

	}

	@Override
	public Set<IOntoAnnotation> getAnnotations() {

		return super.getAnnotations();
	}

//	@Override
	public boolean removeAnnotation(IOntoAnnotation ontoAnnotation) {
		((ClassAnnotationImpl) ontoAnnotation).setItsClass(null);
		return super.getAnnotations().remove(ontoAnnotation);
		// a delete(annotation) must be done
	}



	// *************** run with a direct jdbc access
//	@Override
	public boolean addClassExtension(IClassExtension classExt) {
		boolean isAdded = false;
		if (classExtensions == null) {// lazy loading of extension

			classExtensions = ExtensionManagerImpl.getAllClassExtension(this,
					this.getProperties());
		}

		// try to persit change in database
		isAdded = ExtensionManagerImpl.saveClassExtension(classExt);

		if (isAdded) {// if persit successful;
			classExtensions.add(classExt);
		}
		return isAdded;
	}

//	@Override
	public boolean removeClassExtension(IClassExtension classExt) {
		boolean isRemoved = false;

		if (classExtensions == null) {// lazy loading of extension

			classExtensions = ExtensionManagerImpl.getAllClassExtension(this,
					this.getProperties());
		}

		// try to persit change in database
		isRemoved = ExtensionManagerImpl.deleteClassExtension(classExt);

		if (isRemoved) { // if persit successful then remove in CPU Unit;
			classExtensions.remove(classExt);
		}
		return isRemoved;
	}

//	@Override
	// ok
	public Set<IClassExtension> getClassExtensions() {
		if (classExtensions == null) {// lazy loading of extension

			classExtensions = ExtensionManagerImpl.getAllClassExtension(this,
					this.getProperties());
		}
		return classExtensions;
	}

	@Override
	public boolean addMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		boolean ok1= false;
		boolean ok2= false;
		if (toObject instanceof ITerminoConcept) {
			ITerminoConcept tc = (ITerminoConcept) toObject;

			ok1= super.getMappedTerminoConcepts().add(tc);
			ok2= ((TerminoConceptImpl) tc).getMappedClasses().add(this);
			
			return ok1 && ok2;
		}
		if (toObject instanceof ITerminoConceptRelation) {
			ITerminoConceptRelation tcr = (ITerminoConceptRelation) toObject;
			
			ok1= super.getMappedTerminoConceptRelations().add(tcr);
			ok2= ((TerminoConceptRelationImpl) tcr).getMappedClasses().add(this); 
			return ok1 && ok2;
		}

		if(toObject instanceof ITypeRelationTc){
			ITypeRelationTc tcrType= (ITypeRelationTc) toObject;
			
			ok1= super.getMappedTerminoConceptRelationTypes().add(tcrType);
			ok2= ((TypeRelationTcImpl)tcrType).getMappedClasses().add(this);
			return ok1 && ok2;
			}
		
		return false;
	}

	@Override
	public Set<ITerminoOntoObject> getMappedTerminoOntoObjects() {
		HashSet<ITerminoOntoObject> toObjs = new HashSet<ITerminoOntoObject>();
		//
		toObjs.addAll(super.getMappedTerminoConcepts());
		toObjs.addAll(super.getMappedTerminoConceptRelations());
		toObjs.addAll(super.getMappedTerminoConceptRelationTypes());

		return toObjs;
	}

	@Override
	public boolean removeMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		boolean isRemove1 = false;
		boolean isRemove2 = false;

		if (toObject instanceof ITerminoConcept) {
			ITerminoConcept tc = (ITerminoConcept) toObject;

			isRemove1 = super.getMappedTerminoConcepts().remove(tc);
			isRemove2 = ((TerminoConceptImpl) tc).getMappedClasses().remove(
					this);
			return isRemove1 && isRemove2;

		}
		if (toObject instanceof ITerminoConceptRelation) {
			ITerminoConceptRelation tcr = (ITerminoConceptRelation) toObject;

			isRemove1 = super.getMappedTerminoConceptRelations().remove(tcr);
			isRemove2 = ((TerminoConceptRelationImpl) tcr).getMappedClasses()
					.remove(this);
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

	/*@Override
	public boolean removeClassExtension(Integer classExtId) {
		boolean isRemoved = false;

		if (classExtensions == null) {// lazy loading of extension

			classExtensions = ExtensionManagerImpl.getAllClassExtension(this,
					this.getProperties());
		}

		// try to persit change in database
		isRemoved = ExtensionManagerImpl.deleteClassExtension(this, classExtId);

		if (isRemoved) { // if persit successful the remove in the CPU Unit;
			
			IClassExtension clsExt= ExtensionManagerImpl.loadClassExtension(this, classExtId);
			classExtensions.remove(clsExt);
		}
		return isRemoved;
	}
	*/

}