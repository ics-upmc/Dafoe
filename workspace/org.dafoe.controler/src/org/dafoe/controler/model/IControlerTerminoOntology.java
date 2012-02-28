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

import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;

// TODO: Auto-generated Javadoc
/**
 * The Interface IControlerTerminoOntology.
 * 
 * @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 * @author <a href="mailto:texier@ensma.fr">Guillaume TEXIER</a>
 */
public interface IControlerTerminoOntology {

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
	
	//
	
	/**
	 * Gets the current termino concept.
	 * 
	 * @return the current termino concept
	 */
	ITerminoConcept getCurrentTerminoConcept();
	
	/**
	 * Sets the current termino concept.
	 * 
	 * @param tc the new current termino concept
	 */
	void setCurrentTerminoConcept(ITerminoConcept tc);

	/**
	 * Gets the current termino ontology.
	 * 
	 * @return the current termino ontology
	 */
	ITerminoOntology getCurrentTerminoOntology();
	
	/**
	 * Sets the current termino ontology.
	 * 
	 * @param to the new current termino ontology
	 */
	void setCurrentTerminoOntology(ITerminoOntology to);

	/**
	 * Gets the current rtc.
	 * 
	 * @return the current rtc
	 */
	ITerminoConceptRelation getCurrentRTC();
	
	/**
	 * Sets the current rtc.
	 * 
	 * @param rtc the new current rtc
	 */
	void setCurrentRTC(ITerminoConceptRelation rtc);
	
	/**
	 * Update t cs tree.
	 */
	void UpdateTCsTree();

	/**
	 * Sets the new rtc status.
	 */
	void setNewRTCStatus();

	/**
	 * Sets the delete rtc.
	 */
	void setDeleteRTC();

	/**
	 * Sets the rename tc.
	 */
	void setRenameTC();

	/**
	 * Sets the new tc to onto object mapping.
	 */
	void setNewTCToOntoObjectMapping();
	
	/**
	 * Sets the update rtc typee.
	 */
	void setUpdateRTCTypee();
	
	/**
	 * Sets the external object to tc source event.
	 */
	void setExternalObjectToTCSourceEvent();

	/**
	 * Sets the external object to tc source event.
	 */
	
	void setNewTerminoConceptEvent();
	
	

}
