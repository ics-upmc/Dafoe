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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dafoe.contextlevel.IDafoeContextLevel;
import org.dafoe.contextlevel.ILevel;

public class ContextLevel implements IDafoeContextLevel {

	private Map<String, ILevel> allContextLevel = new HashMap<String, ILevel>();

	protected String perspectiveId;

	protected String contextLevel;
	

	

	public ContextLevel() {
		perspectiveId = "org.dafoe.welcomelevel.perspectiveId"; //$NON-NLS-1$
		contextLevel = "welcome";
	}

	public Collection<String> getAllContextLevelNames() {
		Collection<String> currentLevelNames = new ArrayList<String>();
		for (ILevel currentContextLevel : allContextLevel.values()) {
			currentLevelNames.add(currentContextLevel.getLevelName());
		}
		return currentLevelNames;
	}

	public String getCurrentContextLevelName() {
		if (perspectiveId != null) {
			return allContextLevel.get(contextLevel).getLevelName();
		}
		return null;
	}

	public void setCurrentPerspectiveId(String currentContext) {
		this.perspectiveId = currentContext;

		Set<String> keySet = allContextLevel.keySet();
		for (String currentKey : keySet) {
			ILevel level = allContextLevel.get(currentKey);
			Collection<String> perspectiveIds = level.getPerspectiveIds();

			for (String currentPerspectiveId : perspectiveIds) {
				if (currentContext.equals(currentPerspectiveId)) {
					this.contextLevel = currentKey;
					return;
				}
			}

		}
	}

	public String getCurrentContextLevelTheme() {
		if (perspectiveId != null) {
			return allContextLevel.get(contextLevel).getThemeId();
		}
		return null;
	}

	public void setCurrentContextLevel(String pContextLevel) {
		this.contextLevel = pContextLevel;
	}

	public String getCurrentContextLevel() {
		return this.contextLevel;
	}

	public String getPerspectiveIdFromContextLevel(String contextLevel) {
		ILevel level = allContextLevel.get(contextLevel);
		List<String> perspectiveIds = level.getPerspectiveIds();
		return perspectiveIds.get(0);
	}

	public List<String> getPerpsectiveIdsFromContextLevel(String contextLevel) {
		ILevel level = allContextLevel.get(contextLevel);
		List<String> perspectiveIds = level.getPerspectiveIds();
		return perspectiveIds;
	}

	public void addLevel(String contextLevel, ILevel newLevel) {
		this.allContextLevel.put(contextLevel, newLevel);
		
	}

	
}
