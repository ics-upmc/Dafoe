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
package org.dafoe.framework.core.terminological.model;

import java.util.Set;


/**
 * The IMethod Interface .
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface IMethod {

	/**
	 * Gets the id of this IMethod.
	 * 
	 * @return the id
	 */
	Integer getId();

	/**
	 * Gets the pattern.
	 * 
	 * @return the pattern
	 */
	String getPattern();

	/**
	 * Sets the pattern.
	 * 
	 * @param motif the new pattern
	 */
	void setPattern(String motif);

	/**
	 * Gets the tool.
	 * 
	 * @return the tool
	 */
	String getTool();

	/**
	 * Sets a new tool.
	 * 
	 * @param tool the new tool
	 */
	void setTool(String tool);

	/**
	 * Gets relation type.
	 * 
	 * @return the type relation
	 */
	Set<ITypeRelationTermino> getTypeRelation();
	
	/**
	 * Gets the origin relations.
	 * 
	 * @return the origin relations
	 */
	Set<IOriginRelation> getOriginRelations();
	
	/**
	 * Adds a new origin of termino relation .
	 *
	 * @param originRel the origin rel
	 * @return true, if successful
	 */
	boolean addOriginRelationTermino(IOriginRelation originRel);

	/**
	 * Adds a new termino relation type.
	 *
	 * @param typeRel the type rel
	 * @return true, if successful
	 */
	boolean addTypeRelationTermino(ITypeRelationTermino typeRel);

}
