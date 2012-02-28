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
import org.dafoe.framework.core.terminological.model.ITermOccurrence;
import org.dafoe.framework.core.terminological.model.ITermProperty;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITermSaillance;
import org.dafoe.framework.core.terminological.model.ITermSyntacticRelation;
import org.dafoe.framework.core.terminological.model.ITerminoAnnotation;
import org.dafoe.framework.core.terminological.model.LINGUISTIC_STATUS;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.base.BaseTermImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl;
import org.dafoe.framework.provider.hibernate.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * The Class TermImpl.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TermImpl extends BaseTermImpl implements ITerm {

	
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new term impl.
	 */
	public TermImpl(){
		super();
		super.setIsNamedEntity(LINGUISTIC_STATUS.INDIFFERENTIATED.getValue());
		super.setVariantes(new HashSet<ITerm>());
		super.setHeadInSyntacticRelation(new HashSet<ITermSyntacticRelation>());
		super.setModifierInSyntacticRelation(new HashSet<ITermSyntacticRelation>());
		super.setRelationsWhereInTerm1(new HashSet<ITermRelation>());
		super.setRelationsWhereInTerm2(new HashSet<ITermRelation>());
		super.setTermOccurrences(new HashSet<ITermOccurrence>());
		super.setTermProperties(new HashSet<ITermProperty>());
		super.setMappedTerminoConcepts(new HashSet<ITerminoConcept>());
		setState(TERMINO_OBJECT_STATE.UNKNOWN);
	
		//super.setTerminology(SingleObject.getInstanceTerminology());
		
	}
	
	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminological.model.ITerm#setSaillanceCriteria(org.dafoe.framework.core.terminological.model.ITermSaillance)
	 */
	
	
//	@Override
	public void setSaillanceCriteria(ITermSaillance saillance) {
		
     
	try {
		//hSession = SessionFactoryImpl.getCurrentDynamicSession();
		
        saillance.setTerm(this);
		
		super.setSaillance((TermSaillanceImpl) saillance);
		
		SessionFactoryImpl.getCurrentDynamicSession().saveOrUpdate(saillance); 
	} catch (HibernateException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		//throw new SessionException(e.getMessage());
	}
		
				
	}
	
	// due to the OneToOne association between term and saillance,
	// we must explicitly call this method before deleting a term.
//	@Override
	public  void deleteSaillanceCriteria() {

		Session hSession;

		ITermSaillance termSaillance = this.getSaillanceCriteria();

		try {
			hSession = SessionFactoryImpl.getCurrentDynamicSession();
		
			if (termSaillance != null) {

				//tx = hSession.beginTransaction();

				int termSaillanceId = termSaillance.getId();

				termSaillance.setTerm(null);
				

				Object o= hSession.load(TermSaillanceImpl.class, termSaillanceId);
				hSession.delete(o);
				
				//tx.commit();

			}

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//throw new SessionException(e.getMessage());
		}

		System.out.println("*************************** end ");
	}
	

	
	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminological.model.ITerm#addTermProperty(org.dafoe.framework.core.terminological.model.ITermProperty)
	 */
//	@Override
	public boolean addTermProperty(ITermProperty termProp) {
		boolean ok1= super.getTermProperties().add(termProp);
		boolean ok2= ((TermPropertyImpl)termProp).getTerms().add(this);
		 return ok1 && ok2;
		//super.addToTermProperties(termProp);
		//((TermPropertyImpl)termProp).addToTerms(this);
		
	}

	
	
	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminological.model.ITerm#addVariante(org.dafoe.framework.core.terminological.model.ITerm)
	 */
//	@Override
	public boolean addVariante(ITerm term) {
		//super.addToVariantes(term);
		
		((TermImpl)term).setStarTerm(this);
		return super.getVariantes().add(term);
	}

	
	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminological.model.ITerm#addTermOccurrence(org.dafoe.framework.core.terminological.model.ITermOccurrence)
	 */
//	@Override
	public boolean addTermOccurrence(ITermOccurrence termOccurrence) {
		//super.addToTermOccurrences(termOccurrence);
		((TermOccurrenceImpl)termOccurrence).setRelatedTerm(this);
		return super.getTermOccurrences().add(termOccurrence);
	}

	
	
	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminological.model.ITerm#getProperties()
	 */
//	@Override
	public Set<ITermProperty> getProperties() {
		
		return  super.getTermProperties();
	}

	
	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminological.model.impl.base.BaseTermImpl#getVariantes()
	 */
	@Override
	public Set<ITerm> getVariantes() {
				
		return super.getVariantes();
	}

	
	/*@Override
	public void setStarTerm(ITerm star) {
		super.setStarTerm((TermImpl) star);
		
		if (star != null)
			((TermImpl)star).addToVariantes(this);

	}
*/

	/*@Override
	public void addTermOccurrence(ITermOccurrence termOcc, ISentence sentence) {
		
		termOcc.setRelatedSentence(sentence);
		termOcc.setRelatedTerm(this);
		
		super.addToTermOccurrences(termOcc);
		
	}*/

	
	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminological.model.ITerm#getSaillanceCriteria()
	 */
//	@Override
	public ITermSaillance getSaillanceCriteria() {
		
		return super.getSaillance();
	}

	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminological.model.ITerm#isNamedEntity()
	 */
//	@Override
	public Integer isNamedEntity() {
		return super.getIsNamedEntity();
	}

	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminological.model.ITerm#setNamedEntity(int)
	 */
//	@Override
	public void setNamedEntity(Integer eboolean) {
	
		super.setIsNamedEntity(eboolean);
	}


	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminological.model.ITerm#removeVariante(org.dafoe.framework.core.terminological.model.ITerm)
	 */
//	@Override
	public boolean removeVariante(ITerm term) {
		boolean isRemoved= false;
		
		super.getVariantes().remove(term);
		((TermImpl)term).setStarTerm(null);
		
		return isRemoved;
	}

	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminological.model.ITerm#addAnnotation(org.dafoe.framework.core.terminological.model.ITerminoAnnotation)
	 */
//	@Override
	public boolean addAnnotation(ITerminoAnnotation annot) {
		//super.addToAnnotations(annot);
		((TermAnnotationImpl)annot).setItsTerm(this);
		return super.getAnnotations().add(annot);
		
	}

	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminological.model.impl.base.BaseTermImpl#getAnnotations()
	 */
	@Override
	public Set<ITerminoAnnotation> getAnnotations() {
		
		return super.getAnnotations();
	}

	/* (non-Javadoc)
	 * @see org.dafoe.framework.core.terminological.model.ITerm#removeAnnotation(org.dafoe.framework.core.terminological.model.ITerminoAnnotation)
	 */
//	@Override
	public boolean removeAnnotation(ITerminoAnnotation annot) {
		((TermAnnotationImpl)annot).setItsTerm(null);
		return super.getAnnotations().remove(annot);
		
		// a delete(annotation) must be done
	}

	@Override
	public boolean removeMappedTerminoConcept(ITerminoConcept tc) {
		boolean ok1= super.getMappedTerminoConcepts().remove(tc);
		boolean ok2= ((TerminoConceptImpl)tc).getMappedTerms().remove(this);
		return ok1 && ok2;
	}

	// mapping TC
	/* (non-Javadoc)
 * @see org.dafoe.framework.core.terminological.model.ITerm#addRelatedTerminoConcept(org.dafoe.framework.core.terminoontological.model.ITerminoConcept)
 */
//@Override
	public boolean addMappedTerminoConcept(ITerminoConcept tc) {
		boolean ok1= super.getMappedTerminoConcepts().add(tc);
		boolean ok2= ((TerminoConceptImpl)tc).getMappedTerms().add(this);
		
		return ok1 && ok2;
		
	}

	@Override
	public boolean addMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		boolean ok1= false;
		boolean ok2= false;
		
		if(toObject instanceof ITerminoConcept){
			TerminoConceptImpl tc= (TerminoConceptImpl)toObject;
			ok1= super.getMappedTerminoConcepts().add(tc);
			ok2= ((TerminoConceptImpl)tc).getMappedTerms().add(this);
			
			return ok1 && ok2;
		}
		return false;
	}

	@Override
	public Set<ITerminoOntoObject> getMappedTerminoOntoObjects() {
		Set<ITerminoOntoObject> toObjects= new HashSet<ITerminoOntoObject>();
		
		toObjects.addAll(super.getMappedTerminoConcepts());
		
		return toObjects;
	}

	@Override
	public boolean removeMappedTerminoOntoObject(ITerminoOntoObject toObject) {
		
		boolean ok1= false;
		boolean ok2= false;
		
		if(toObject instanceof ITerminoConcept){
			TerminoConceptImpl tc= (TerminoConceptImpl)toObject;
			ok1= super.getMappedTerminoConcepts().remove(tc);
			ok2= ((TerminoConceptImpl)tc).getMappedTerms().remove(this);
			
			return ok1 && ok2;
		}
		return false;
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
	public LINGUISTIC_STATUS getLinguisticStatus() {
		
		return   Util.getLinguisticStatusFromDatabase(super.getIsNamedEntity());
	}

	@Override
	public void setLinguisticStatus(LINGUISTIC_STATUS ls) {
		super.setIsNamedEntity(ls.getValue());
		
	}
	
	
	
}
