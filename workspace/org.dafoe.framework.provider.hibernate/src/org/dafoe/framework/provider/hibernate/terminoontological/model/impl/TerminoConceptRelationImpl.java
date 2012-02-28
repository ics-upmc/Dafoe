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
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelationMember;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRole;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotation;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.DatatypePropertyImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermRelationImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTerminoConceptRelationImpl;
import org.dafoe.framework.provider.hibernate.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * The Class TerminoConceptRelationImpl.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TerminoConceptRelationImpl extends BaseTerminoConceptRelationImpl implements ITerminoConceptRelation {


	public TerminoConceptRelationImpl () {
		super();
		super.setMappedClasses(new HashSet<IClass>());
		super.setMappedDatatypeProperties(new HashSet<IDatatypeProperty>());
		super.setMappedObjectProperties(new HashSet<IObjectProperty>());
		super.setMappedTermRelations(new HashSet<ITermRelation>());
		super.setRelationMembers(new HashSet<ITerminoConceptRelationMember>());
		
		super.setStatus(TERMINO_ONTO_OBJECT_STATE.UNKNOWN.getValue());
		
	}
	
	/* (non-Javadoc)
 * @see org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation#setTypeRelation(org.dafoe.framework.core.terminoontological.model.ITypeRelationTc)
 */
//@Override
	public void setTypeRelation(ITypeRelationTc typeRelation) {
		super.setTypeRelation((TypeRelationTcImpl) typeRelation);
		
	}

	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation#addTerminoConcept(org.dafoe.framework.core.terminoontological.model.ITerminoConcept, org.dafoe.framework.core.terminoontological.model.IRoleInTcRelation)
	 */
//	@Override
	public void addTerminoConcept(ITerminoConcept tc, ITerminoConceptRole role) {
		//HVT: relationship must be manage birectionnal: to implemented later
        
		 Session hSession;
			try {
				hSession = SessionFactoryImpl.getCurrentDynamicSession();
				
		        ITerminoConceptRelationMember tcMember= new TerminoConceptRelationMemberImpl();
		        ((TerminoConceptRelationMemberImpl)tcMember).setTerminoConcept(tc);
		            
		        
		        ((TerminoConceptRelationMemberImpl)tcMember).setTerminoConceptRelation(this);
		        
		        
		        ((TerminoConceptRelationMemberImpl)tcMember).setTerminoConceptRole(role);
												
				hSession.saveOrUpdate(tcMember); 
			} catch (HibernateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//throw new SessionException(e.getMessage());
			}
		
		
	}

	


	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject#setTerminoOntology(org.dafoe.framework.core.terminoontological.model.ITerminoOntology)
	 */
	/*@Override
	public void setTerminoOntology(ITerminoOntology terminoOntology) {
		super.setTerminoOntology((TerminoOntologyImpl) terminoOntology);
		
	}
*/
	

	@Override
	public Set<ITermRelation> getMappedTermRelations() {
		// TODO Auto-generated method stub
		return super.getMappedTermRelations();
	}

//	@Override
	public Set<ITerminoConceptRelationMember> getTerminoConceptRelationMembers() {
		// TODO Auto-generated method stub
		return super.getRelationMembers();
	}


//	@Override
	public void addMappedTermRelation(ITermRelation termRel) {
		super.addToMappedTermRelations(termRel);
		((TermRelationImpl)termRel).addMappedTerminoConceptRelation(this);
		
	}


//	@Override
	public void removeMappedTermRelation(ITermRelation termRel) {
		super.getMappedTermRelations().remove(termRel);
		((TermRelationImpl)termRel).getMappedTerminoConceptRelations().remove(this);
		
	}

//	@Override
	public boolean addMappedOntoObject(IOntoObject ontoObject) {
		boolean ok1= false;
		boolean ok2= false;
		
		if (ontoObject instanceof IClass){
			IClass cls= (IClass)ontoObject;
			//super.addToMappedClasses(cls);
			//((ClassImpl)cls).addToMappedTerminoConceptRelations(this);
			ok1= super.getMappedClasses().add(cls);
			ok2= ((ClassImpl)cls).getMappedTerminoConceptRelations().add(this);
			
			return ok1 && ok2;
			
		}
		if(ontoObject instanceof IDatatypeProperty){
			IDatatypeProperty dProp= (IDatatypeProperty)ontoObject;
			//super.addToMappedDatatypeProperties(dProp);
			//((DatatypePropertyImpl)dProp).addToMappedTerminoConceptRelations(this);
			
			ok1= super.getMappedDatatypeProperties().add(dProp);
			ok2= ((DatatypePropertyImpl)dProp).getMappedTerminoConceptRelations().add(this);
			
			return ok1 && ok2;
		}
		if(ontoObject instanceof IObjectProperty){
			IObjectProperty oProp=(IObjectProperty)ontoObject;
			//super.addToMappedObjectProperties(oProp);
			//((ObjectPropertyImpl)oProp).addToMappedTerminoConceptRelations(this);
			ok1= super.getMappedObjectProperties().add(oProp);
			ok2= ((ObjectPropertyImpl)oProp).getMappedTerminoConceptRelations().add(this);
			
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
			isRemoved2= ((ClassImpl)cls).getMappedTerminoConceptRelations().remove(this);
		}
		if(ontoObject instanceof IDatatypeProperty){
			IDatatypeProperty dProp= (IDatatypeProperty)ontoObject;
			
			isRemoved1= super.getMappedDatatypeProperties().remove(dProp);
			isRemoved2= ((DatatypePropertyImpl)dProp).getMappedTerminoConceptRelations().remove(this);
		}
		if(ontoObject instanceof IObjectProperty){
			IObjectProperty oProp=(IObjectProperty)ontoObject;
			
			isRemoved1= super.getMappedObjectProperties().remove(oProp);
			isRemoved2= ((ObjectPropertyImpl)oProp).getMappedTerminoConceptRelations().remove(this);
		}
		return isRemoved1 && isRemoved2;
	}

//	@Override
	public boolean addAnnotation(ITerminoOntoAnnotation annot) {
		//super.addToAnnotations(annot);
		((TerminoConceptRelationAnnotationImpl) annot).setItsTerminoConceptRelation(this);
		return super.getAnnotations().add(annot);
		
	}

//	@Override
	public boolean removeAnnotation(ITerminoOntoAnnotation annot) {
		((TerminoConceptRelationAnnotationImpl) annot).setItsTerminoConceptRelation(null);
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
		
		return super.getTypeRelation().getLabel()+"/Relation/"+getId()+":";
	}
	
	
}