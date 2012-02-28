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


/**
 * The ITermSyntacticRelation Interface .
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITermSyntacticRelation extends ITerminoObject{

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId ();
	
	/**
	 * Gets the term in modifier position.
	 * 
	 * @return the modifier term
	 */
	ITerm getModifierTerm ();
	
	/**
	 * Sets the term in modifier position.
	 * 
	 * @param term the new modifier term
	 */
	void setModifierTerm (ITerm term);
	
	/**
	 * Gets the term in head position.
	 * 
	 * @return the head term
	 */
	ITerm getHeadTerm ();
	
	/**
	 * Sets the term in head position.
	 * 
	 * @param term the new head term
	 */
	void setHeadTerm (ITerm term);
	
}
