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

// TODO: Auto-generated Javadoc
/**
 * The Interface IAttribut.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IAttribut {

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
	 * @param _name the new name
	 */
	void setName(String name);

	/**
	 * Checks if is dynamic.
	 * 
	 * @return true, if is dynamic
	 */
	boolean isDynamic();

	/**
	 * Sets the dynamic.
	 * 
	 * @param dynamic the new dynamic
	 */
	void setDynamic(boolean dynamic);

	/**
	 * Gets the entity.
	 * 
	 * @return the entity
	 */
	IEntity getEntity();

	/**
	 * Sets the entity.
	 * 
	 * @param e the new entity
	 */
	void setEntity(IEntity e);
}
