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
package org.dafoe.framework.core.ontological.model;

import java.util.Map;
import java.util.Set;


/**
 * The IClassExtension Interface Interface represent instance of class, coded as one table per class.
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IClassExtension {

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId();

	/**
	 * Sets the id.
	 * 
	 * @param id the new id
	 */
	void setId(Integer id);

	/**
	 * Gets the value of.
	 * 
	 * @param prop the prop
	 * 
	 * @return the value of
	 */
	Object getValueOf(IProperty prop);

	/**
	 * Sets the value of.
	 * 
	 * @param prop the prop
	 * @param value the value
	 */
	void setValueOf(IProperty prop, Object value);

	/**
	 * Gets the prefered class.
	 * 
	 * @return the prefered class
	 */
	IClass getPreferedClass();

	/**
	 * Sets the prefered class.
	 * 
	 * @param cls the new prefered class
	 */
	void setPreferedClass(IClass cls);

	/**
	 * Instance of.
	 * 
	 * @return the set< i class>
	 */
	Set<IClass> instanceOf();

		
	String getInsertionQuery();
	
	String getUpdateQuery();

	Map<IProperty, Object> getTuple();
}
