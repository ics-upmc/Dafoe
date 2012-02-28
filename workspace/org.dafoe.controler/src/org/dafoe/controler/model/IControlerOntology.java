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
package org.dafoe.controler.model;

import java.beans.PropertyChangeListener;

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IClassInstance;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.ontological.model.IProperty;



/**
 * The Interface IControlerOntology.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 * @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:texier@ensma.fr">Guillaume TEXIER</a>
 */
public interface IControlerOntology {
	
	/**
	 * Gets the current ontology.
	 * 
	 * @return the current ontology
	 */
	IOntology getCurrentOntology();
	
	/**
	 * Sets the current ontology.
	 * 
	 * @param classe the new current ontology
	 */
	void setCurrentOntology(IOntology ontology);
	
	/**
	 * Gets the current class.
	 * 
	 * @return the current class
	 */
	IClass getCurrentClass();
	
	/**
	 * Sets the current class.
	 * 
	 * @param classe the new current class
	 */
	void setCurrentClass(IClass classe);
	
	/**
	 * Gets the current property.
	 * 
	 * @return the current property
	 */
	IProperty getCurrentProperty();
	
	/**
	 * Sets the current property.
	 * 
	 * @param prop the new current property
	 */
	void setCurrentProperty(IProperty prop);

	/**
	 * Gets the current instance.
	 * 
	 * @return the current instance
	 */
	IClassInstance getCurrentInstance();
	
	/**
	 * Sets the current instance.
	 * 
	 * @param instance the new current instance
	 */
	void setCurrentInstance(IClassInstance instance);
	
	/**
	 * Adds the classe parent.
	 * 
	 * @param classe the classe
	 * @param parentClass the parent class
	 */
	public void AddClasseParent (IClass classe,IClass parentClass);
	
	/**
	 * Select no class.
	 */
	public void SelectNoClass();
	
	/**
	 * Update class tree.
	 */
	public void UpdateClassTree();
	
	/**
	 * Update current class.
	 */
	public void UpdateCurrentClass();
	
	/**
	 * Adds the property change listener.
	 * 
	 * @param propertyName the property name
	 * @param listener the listener
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	/**
	 * Removes the property change listener.
	 * 
	 * @param listener the listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Update current property.
	 */
	public void updateCurrentProperty();
	
	/**
	 * Change ontology.
	 */
	public void changeOntology();
	
}
