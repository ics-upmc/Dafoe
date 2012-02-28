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
package org.dafoe.controler.model.impl;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.model.IControlerTerminology;
import org.dafoe.framework.core.terminological.model.ICorpus;
import org.dafoe.framework.core.terminological.model.IDocument;
import org.dafoe.framework.core.terminological.model.IMethod;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;


/**
 * The Class ControlerTerminologyApiImpl.
 * 
 * @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 * @author <a href="mailto:texier@ensma.fr">Guillaume TEXIER</a>
 */
public class ControlerTerminologyApiImpl implements IControlerTerminology  {

	/** The current term. */
	private ITerm currentTerm;
	
	/** The current terms. */
	private List<ITerm> currentTerms;
	
	/** The current variants. */
	private List<ITerm> currentVariants;
	
	/** The current variant. */
	private ITerm currentVariant;
	
	/** The current corpus. */
	private ICorpus currentCorpus;
	
	/** The current doc. */
	private IDocument currentDoc = null;
	
	/** The current relation. */
	private ITermRelation currentRelation;
		
	/** The current relation type. */
	private ITypeRelationTermino currentRelationType;
	
	/** The current method. */
	private IMethod currentMethod;
	
	private List<ICorpus> currentLoadedCorpus;
	
	/** The property change support. */
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

	/**
	 * Instantiates a new controler terminology api impl.
	 */
	public ControlerTerminologyApiImpl(){
		
	}
		
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#getCurrentTerm()
	 */
	@Override
	public ITerm getCurrentTerm() {
		
		return currentTerm;
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setCurrentTerm(org.dafoe.framework.core.terminological.model.ITerm)
	 */
	@Override
	public void setCurrentTerm(ITerm term) {
		ITerm oldTerm = this.currentTerm; 
		this.currentTerm = term;
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.currentTermEvent, oldTerm, this.currentTerm);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#getcurrentCorpus()
	 */
	@Override
	public ICorpus getcurrentCorpus() {
		
		return currentCorpus;
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setCurrentCorpus(org.dafoe.framework.core.terminological.model.ICorpus)
	 */
	@Override
	public void setCurrentCorpus(ICorpus corpus) {
		ICorpus oldCorpus = this.currentCorpus;
		this.currentCorpus = corpus;
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.currentCorpusEvent, oldCorpus, this.currentCorpus);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#getNewVariant()
	 */
	@Override
	public ITerm getNewVariant() {
		return currentVariant;
	}



	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setNewVariant(org.dafoe.framework.core.terminological.model.ITerm)
	 */
	@Override
	public void setNewVariant(ITerm variant) {
		ITerm oldVariant = this.currentVariant; 
		this.currentVariant = variant;
		//propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.newVariantEvent, oldVariant, this.currentTerm);
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.newVariantEvent, oldVariant, this.currentVariant);
	}


	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setNewStatus()
	 */
	@Override
	public void setNewStatus() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.newStatusEvent, true, false);
		
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#getCurrentDocument()
	 */
	@Override
	public IDocument getCurrentDocument() {
		return currentDoc;
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setCurrentDocument(org.dafoe.framework.core.terminological.model.IDocument)
	 */
	@Override
	public void setCurrentDocument(IDocument document) {
		IDocument oldDoc = this.currentDoc;
		this.currentDoc = document;
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.currentDocumentEvent, oldDoc, this.currentDoc);
	}




	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#getCurrentTerms()
	 */
	@Override
	public List<ITerm> getCurrentTerms() {
		return this.currentTerms;
	}




	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setCurrentTerms(java.util.List)
	 */
	@Override
	public void setCurrentTerms(List<ITerm> terms) {
		List<ITerm> oldTerms = this.currentTerms;
		this.currentTerms = terms;
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.currentTermsEvent, oldTerms, this.currentTerms);
	}




	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#getCurrentVariants()
	 */
	@Override
	public List<ITerm> getCurrentVariants() {
		return this.currentVariants;
	}




	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setCurrentVariants(java.util.List)
	 */
	@Override
	public void setCurrentVariants(List<ITerm> variants) {
		List<ITerm> oldVariants = this.currentVariants;
		this.currentVariants = variants;
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.currentVariantsEvent, oldVariants, this.currentVariants);
	}


	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setVariantDetached()
	 */
	@Override
	public void setVariantDetached() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.newVariantDetachedEvent, true, false);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setTermLabelModified()
	 */
	@Override
	public void setTermLabelModified() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.termLabelModifiedEvent, true, false);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#getTermRelation()
	 */
	@Override
	public ITermRelation getTermRelation() {
		return this.currentRelation;
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setTermRelation(org.dafoe.framework.core.terminological.model.ITermRelation)
	 */
	@Override
	public void setTermRelation(ITermRelation relation) {
		ITermRelation oldRelation = this.currentRelation;
		this.currentRelation = relation;
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.currentRelationEvent, oldRelation, this.currentRelation);
		
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setNewRelationStatus()
	 */
	@Override
	public void setNewRelationStatus() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.newRelationStatusEvent, true, false);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setTermsDeleted()
	 */
	@Override
	public void setTermsDeleted() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.termsDeletedEvent, true, false);
		
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setTermsImported()
	 */
	@Override
	public void setTermsImported() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.termsImportedEvent, true, false);
		
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setCurrentRelationType(org.dafoe.framework.core.terminological.model.ITypeRelationTermino)
	 */
	@Override
	public void setCurrentRelationType(ITypeRelationTermino relationType) {
		ITypeRelationTermino oldRelationType = this.currentRelationType;
		this.currentRelationType = relationType;
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.currentRelationTypeEvent, oldRelationType, this.currentRelationType);		
		
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#getCurrentRelationType()
	 */
	@Override
	public ITypeRelationTermino getCurrentRelationType() {
		return currentRelationType;
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#getMethod()
	 */
	@Override
	public IMethod getMethod() {
		return this.currentMethod;
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setMethod(org.dafoe.framework.core.terminological.model.IMethod)
	 */
	@Override
	public void setMethod(IMethod method) {
		IMethod oldMethod = this.currentMethod;
		this.currentMethod = method;
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.currentMethodEvent, oldMethod, this.currentMethod);		
		
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminology#setRelationDeleted()
	 */
	@Override
	public void setRelationDeleted() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.relationDeletedEvent, true, false);
		
	}

	@Override
	public List<ICorpus> getCurrentLoadedCorpus() {
		// TODO Auto-generated method stub
		return currentLoadedCorpus;
	}

	@Override
	public void setCurrentLoadedCorpus(List<ICorpus> loadedCorpus) {
		List<ICorpus> oldLoadedCorpus= this.currentLoadedCorpus;
		this.currentLoadedCorpus= loadedCorpus;
		
		System.out.println("ControlerTerminologyApiImpl.setCurrentLoadedCorpus()");
		
		
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.CURRENT_LOADED_CORPUS_EVENT, oldLoadedCorpus, this.currentLoadedCorpus);	
		
	}
	
}
