/*******************************************************************************************************************************
 * (c) Copyright 2007, 2010 CRITT Informatique and INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC.
 * All rights reserved.
 * This program has been developed by the CRITT Informatique for the ANR DAFOE4App Project.
 * This program and the accompanying materials are made available under the terms
 * of the CeCILL-C Public License v1 which accompanies this distribution,
 * and is available at http://www.cecill.info/licences/Licence_CeCILL-C_V1-fr.html
 *
 * Contributors:
 *     INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, T�l�com ParisTech, CNRS/UTC and CRITT Informatique - specifications
 *     CRITT Informatique - initial API and implementation
 ********************************************************************************************************************************/
package org.dafoe.framework.core.metadata.model;

// TODO: Auto-generated Javadoc
/**
 * The Interface ISimpleType.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Val�ry TEGUIAK</a>
 */
public interface ISimpleType extends IAttributType {

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
	 * Gets the java type.
	 * 
	 * @return the java type
	 */
	String getJavaType();

	/**
	 * Sets the java type.
	 * 
	 * @param javaType the new java type
	 */
	void setJavaType(String javaType);

	/**
	 * Gets the sql type.
	 * 
	 * @return the sql type
	 */
	String getSqlType();

	/**
	 * Sets the sql type.
	 * 
	 * @param sqlType the new sql type
	 */
	void setSqlType(String sqlType);
}
