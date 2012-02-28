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
package org.dafoe.framework.core.metadata.model;

import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Interface IEntity.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IEntity {

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId();

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	String getName();

	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	void setName(String name);

	/**
	 * Checks if is abstract.
	 * 
	 * @return true, if is abstract
	 */
	boolean isAbstract();

	/**
	 * Sets the abstract.
	 * 
	 * @param bool the new abstract
	 */
	void setAbstract(boolean bool);

	/**
	 * Gets the related table.
	 * 
	 * @return the related table
	 */
	String getRelatedTable();

	/**
	 * Sets the related table.
	 * 
	 * @param relatedTable the new related table
	 */
	void setRelatedTable(String relatedTable);

	/**
	 * Gets the model.
	 * 
	 * @return the model
	 */
	IModel getModel();

	/**
	 * Sets the model.
	 * 
	 * @param model the new model
	 */
	void setModel(IModel model);

	/**
	 * Gets the super entities.
	 * 
	 * @return the super entities
	 */
	Set<IEntity> getSuperEntities();

	// public void setParents (java.util.Set parents);

	/**
	 * Adds the super entity.
	 * 
	 * @param e the e
	 */
	void addSuperEntity(IEntity e);

	/**
	 * Adds the sub entity.
	 * 
	 * @param e the e
	 */
	void addSubEntity(IEntity e);

	/**
	 * Gets the sub entities.
	 * 
	 * @return the sub entities
	 */
	Set<IEntity> getSubEntities();

}
