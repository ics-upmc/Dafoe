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
 * The ITermSaillance Interface represents saliency criteria of a term.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public interface ITermSaillance {

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	Integer getId();

	/**
	 * Gets the frequency.
	 * 
	 * @return the frequency
	 */
	Integer getFrequency();

	/**
	 * Sets the frequency.
	 * 
	 * @param frequency the new frequency
	 */
	void setFrequency(Integer frequency);

	/**
	 * Gets the head productivity.
	 * 
	 * @return the head productivity
	 */
	Integer getHeadProductivity();

	/**
	 * Sets the head productivity.
	 * 
	 * @param headProductivity the new head productivity
	 */
	void setHeadProductivity(Integer headProductivity);

	/**
	 * Gets the modifier productivity.
	 * 
	 * @return the modifier productivity
	 */
	Integer getModifierProductivity();

	/**
	 * Sets the modifier productivity.
	 * 
	 * @param modifierProductivity the new modifier productivity
	 */
	void setModifierProductivity(Integer modifierProductivity);

	/**
	 * Gets the term.
	 * 
	 * @return the term
	 */
	public ITerm getTerm();

	/**
	 * Sets the term.
	 * 
	 * @param term the new term
	 */
	public void setTerm(ITerm term);
	
	
	
	/**
	 * Gets the tf idf.
	 * 
	 * @return the tf idf
	 */
	Integer getTfIdf ();

	
	/**
	 * Sets the tf idf.
	 * 
	 * @param tfIdf the new tf idf
	 */
	public void setTfIdf (Integer tfIdf);

	
	/**
	 * Gets the nb var.
	 * 
	 * @return the nb var
	 */
	Integer getNbVar ();

	
	/**
	 * Sets the nb var.
	 * 
	 * @param nbVar the new nb var
	 */
	void setNbVar (Integer nbVar);


	
	/**
	 * Gets the typographical weight.
	 * 
	 * @return the typographical weight
	 */
	Integer getTypographicalWeight ();

	
	/**
	 * Sets the typographical weight.
	 * 
	 * @param typographicalWeight the new typographical weight
	 */
	void setTypographicalWeight (Integer typographicalWeight);


	
	/**
	 * Gets the position weight.
	 * 
	 * @return the position weight
	 */
	Integer getPositionWeight ();

	
	/**
	 * Sets the position weight.
	 * 
	 * @param positionWeight the new position weight
	 */
	void setPositionWeight (Integer positionWeight);

	/**
	 * Gets the head neighbor size.
	 * 
	 * @return the head neighbor size
	 */
	Integer getHeadNeighborSize () ;
	
	/**
	 * Sets the head neighbor size.
	 * 
	 * @param size the new head neighbor size
	 */
	void setHeadNeighborSize (Integer size);
	
	/**
	 * Gets the modifier neighbor size.
	 * 
	 * @return the modifier neighbor size
	 */
	Integer getModifierNeighborSize ();
	
	/**
	 * Sets the modifier neighbor size.
	 * 
	 * @param size the new modifier neighbor size
	 */
	void setModifierNeighborSize (Integer size);
}
