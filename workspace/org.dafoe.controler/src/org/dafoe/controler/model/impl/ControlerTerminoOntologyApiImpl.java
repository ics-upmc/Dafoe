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

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.model.IControlerTerminoOntology;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;

// TODO: Auto-generated Javadoc
/**
 * The Class ControlerTerminoOntologyApiImpl.
 * 
 * @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 * @author <a href="mailto:texier@ensma.fr">Guillaume TEXIER</a>
 */
public class ControlerTerminoOntologyApiImpl implements
		IControlerTerminoOntology {

	/** The current termino concept. */
	private ITerminoConcept currentTerminoConcept;
	
	/** The current termino ontology. */
	private ITerminoOntology currentTerminoOntology;
	
	/** The current rtc. */
	private ITerminoConceptRelation currentRTC;
	
	/** The property change support. */
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	//

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	//
	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#getCurrentTerminoConcept()
	 */
	@Override
	public ITerminoConcept getCurrentTerminoConcept() {
		return currentTerminoConcept;
	}

	//
	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#setCurrentTerminoConcept(org.dafoe.framework.core.terminoontological.model.ITerminoConcept)
	 */
	@Override
	public void setCurrentTerminoConcept(ITerminoConcept tc) {
		ITerminoConcept oldTc = this.currentTerminoConcept; 
		this.currentTerminoConcept = tc;
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.currentTerminoConceptEvent, oldTc, this.currentTerminoConcept);
	}

	//
	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#getCurrentTerminoOntology()
	 */
	@Override
	public ITerminoOntology getCurrentTerminoOntology() {
		return this.currentTerminoOntology;
	}

	//
	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#setCurrentTerminoOntology(org.dafoe.framework.core.terminoontological.model.ITerminoOntology)
	 */
	@Override
	public void setCurrentTerminoOntology(ITerminoOntology to) {
		ITerminoOntology oldTo = this.currentTerminoOntology;
		this.currentTerminoOntology = to;
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.currentTerminoOntologyEvent, oldTo, this.currentTerminoOntology);
		
	}

	//
	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#UpdateTCsTree()
	 */
	@Override
	public void UpdateTCsTree() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.updateTCsTreeEvent, true, false);
	}

	//
	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#getCurrentRTC()
	 */
	@Override
	public ITerminoConceptRelation getCurrentRTC() {
		return this.currentRTC;
	}

	//
	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#setCurrentRTC(org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation)
	 */
	@Override
	public void setCurrentRTC(ITerminoConceptRelation rtc) {
		ITerminoConceptRelation oldRTC = this.currentRTC;
		this.currentRTC = rtc;
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.currentRTCEvent, oldRTC, this.currentRTC);
		
	}

	//
	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#setNewRTCStatus()
	 */
	@Override
	public void setNewRTCStatus() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.newRTCStatusEvent, true, false);
		
	}

	//
	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#setDeleteRTC()
	 */
	@Override
	public void setDeleteRTC() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.deleteRTCEvent, true, false);
		
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#setRenameTC()
	 */
	@Override
	public void setRenameTC() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.renameTCEvent, true, false);
		
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#setNewTCToOntoObjectMapping()
	 */
	@Override
	public void setNewTCToOntoObjectMapping() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.newTCToOntoObjectMappingEvent, true, false);
		
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#setUpdateRTCTypee()
	 */
	@Override
	public void setUpdateRTCTypee() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.updateRTCTypeEvent, true, false);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerTerminoOntology#setExternalObjectToTCSourceEvent()
	 */
	@Override
	public void setExternalObjectToTCSourceEvent() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.externalObjectToTCSourceEvent, true, false);
	}

	@Override
	public void setNewTerminoConceptEvent() {
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.NEW_TERMINOCONCEPT_EVENT, true, false);
		
	}

	

}
