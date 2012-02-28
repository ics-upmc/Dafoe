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
package org.dafoe.contextlevel.internal;

import java.util.List;

import org.dafoe.contextlevel.ILevel;


public class Level implements ILevel {
	private String levelName;

	private String themeId;
	
	private List<String> perspectiveIds;
	
	public Level(String name, String pThemeId, List<String> pPerspectiveIds) {
		this.levelName = name;
		this.themeId = pThemeId;
		this.perspectiveIds = pPerspectiveIds;
	}
	
	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	
	public List<String> getPerspectiveIds() {
		return perspectiveIds;
	}
}
