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
import org.dafoe.controler.model.IControlerOntology;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IClassInstance;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.ontological.model.IProperty;

/**
 * The Class ControlerOnlotogyImpl.
 * 
 * @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 * @author <a href="mailto:texier@ensma.fr">Guillaume TEXIER</a>
 */
public class ControlerOnlotogyApiImpl implements IControlerOntology {

	/** The current classe. */
	private IClass currentClasse=null;
	
	/** The current property. */
	private IProperty currentProperty = null;
	
	/** The current instance. */
	private IClassInstance currentInstance = null; 
	
	/** The current ontology. */
	private IOntology currentOntology = null;
	
	/** The property change support. */
	private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);
	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#addPropertyChangeListener(java.lang.String, java.beans.PropertyChangeListener)
	 */
	@Override
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#getCurrentClass()
	 */
	@Override
	public IClass getCurrentClass() {
		return currentClasse;
	}
	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#getCurrentOntology()
	 */
	public IOntology getCurrentOntology() {
		return currentOntology;
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#getCurrentInstance()
	 */
	@Override
	public IClassInstance getCurrentInstance() {
		return currentInstance;
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#getCurrentProperty()
	 */
	@Override
	public IProperty getCurrentProperty() {
		return currentProperty;
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#removePropertyChangeListener(java.beans.PropertyChangeListener)
	 */
	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#setCurrentClass(org.dafoe.framework.core.ontological.model.IClass)
	 */
	@Override
	public void setCurrentClass(IClass classe) {
		IClass oldClass = currentClasse; 
		currentClasse= classe;
		if (classe==null) {
			
			propertyChangeSupport.firePropertyChange(
					ControlerFactoryImpl.selectNoClassEvent, 
					true, false);
		} else {
			
			propertyChangeSupport.firePropertyChange(
					ControlerFactoryImpl.currentClassEvent, 
					oldClass, currentClasse);
		}
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#setCurrentOntology(org.dafoe.framework.core.ontological.model.IOntology)
	 */
	public void setCurrentOntology(IOntology ontology) {
		IOntology oldOntology = currentOntology; 
		currentOntology= ontology;
		if (ontology==null) {
			System.out.println("ControlerOnlotogyImpl.setCurrentOntology()= null");
			propertyChangeSupport.firePropertyChange(
					ControlerFactoryImpl.selectNoOntology, 
					true, false);
		} else {
			System.out.println("ControlerOnlotogyImpl.setCurrentOntology() != null");
			propertyChangeSupport.firePropertyChange(
					ControlerFactoryImpl.currentOntologyEvent, 
					oldOntology, currentOntology);
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#setCurrentInstance(org.dafoe.framework.core.ontological.model.IClassInstance)
	 */
	@Override
	public void setCurrentInstance(IClassInstance instance) {
		IClassInstance oldinstance = currentInstance; 
		currentInstance= instance;
		propertyChangeSupport.firePropertyChange(
				ControlerFactoryImpl.currentInstanceEvent, 
				oldinstance, currentInstance);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#setCurrentProperty(org.dafoe.framework.core.ontological.model.IProperty)
	 */
	@Override
	public void setCurrentProperty(IProperty variant) {
		currentProperty = variant;
		propertyChangeSupport.firePropertyChange(ControlerFactoryImpl.currentPropertyEvent,	true, false);
	}

	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#updateCurrentProperty()
	 */
	@Override
	public void updateCurrentProperty() {		
		propertyChangeSupport.firePropertyChange(
				ControlerFactoryImpl.updatePropertyEvent, 
				true, false);
	}
	
	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#AddClasseParent(org.dafoe.framework.core.ontological.model.IClass, org.dafoe.framework.core.ontological.model.IClass)
	 */
	@Override
	public void AddClasseParent(IClass classe, IClass parentClass) {
		propertyChangeSupport.firePropertyChange(
				ControlerFactoryImpl.addClassParentEvent, 
				classe, parentClass);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#SelectNoClass()
	 */
	@Override
	public void SelectNoClass() {
		propertyChangeSupport.firePropertyChange(
				ControlerFactoryImpl.selectNoClassEvent, 
				true, false);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#UpdateClassTree()
	 */
	@Override
	public void UpdateClassTree() {
		propertyChangeSupport.firePropertyChange(
				ControlerFactoryImpl.updateClasseTreeEvent, 
				true, false);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#UpdateCurrentClass()
	 */
	@Override
	public void UpdateCurrentClass() {
		propertyChangeSupport.firePropertyChange(
				ControlerFactoryImpl.updateClasseEvent, 
				false, true);
	}

	/* (non-Javadoc)
	 * @see org.dafoe.controler.model.IControlerOntology#changeOntology()
	 */
	@Override
	public void changeOntology() {
		propertyChangeSupport.firePropertyChange(
				ControlerFactoryImpl.changeOntologyEvent, 
				false, true);
	}
	
	

}
