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
package org.dafoe.contextlevel;

import java.util.Collection;
import java.util.List;

public interface IDafoeContextLevel {	
	/**
	 * Retrieve all allowed context level names.
	 * 
	 * @return
	 */
	Collection<String> getAllContextLevelNames();
	
	/**
	 * Retrieve the current context level name.
	 * 
	 * @return current context level.
	 */
	String getCurrentContextLevelName();
		
	/**
	 * Retrieve the current context level theme.
	 * 
	 * @return
	 */
	String getCurrentContextLevelTheme();
		
	/**
	 * Set the perspective Id associated with the current context.
	 *  
	 * @param pCurrentContext new perspective Id
	 */
	void setCurrentPerspectiveId(String pCurrentContext);
	
	void setCurrentContextLevel(String pContextLevel);
	
	
	
	
	
	String getCurrentContextLevel();
	
	String getPerspectiveIdFromContextLevel(String pContextLevel);
	
	List<String> getPerpsectiveIdsFromContextLevel(String pContextLevel);
	
	
	void addLevel(String contextLevel, ILevel newLevel);
}
