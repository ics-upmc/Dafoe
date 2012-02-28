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

package org.dafoe.terminologiclevel.terminologycard.dialogs.models;

import java.util.Hashtable;

import org.dafoe.terminologiclevel.terminologycard.Messages;

public class TerminologyInformationModel {
	private static final String PROD_T = Messages.getString("TerminologyInformationModel.0"); //$NON-NLS-1$
	private static final String PROD_E = Messages.getString("TerminologyInformationModel.1"); //$NON-NLS-1$
	private static final String TF_IDF = Messages.getString("TerminologyInformationModel.2"); //$NON-NLS-1$
	private static final String NB_VAR = Messages.getString("TerminologyInformationModel.3"); //$NON-NLS-1$
	private static final String FREQ = Messages.getString("TerminologyInformationModel.4"); //$NON-NLS-1$
	private static final String POIDS_TYPO = Messages.getString("TerminologyInformationModel.5"); //$NON-NLS-1$
	private static final String POIDS_POS = Messages.getString("TerminologyInformationModel.6"); //$NON-NLS-1$

	public static final String[] NAMES = new String[]{PROD_T, PROD_E, TF_IDF, NB_VAR, FREQ, POIDS_TYPO, POIDS_POS};
	
	private Hashtable<String, Boolean> termInfoHashtable = new Hashtable<String, Boolean>();
	
	public TerminologyInformationModel(){
		init();
	}
	
	public void init(){
		termInfoHashtable.put(PROD_T, new Boolean(true));
		termInfoHashtable.put(PROD_E, new Boolean(true));
		termInfoHashtable.put(TF_IDF, new Boolean(true));
		termInfoHashtable.put(NB_VAR, new Boolean(true));
		termInfoHashtable.put(FREQ, new Boolean(true));
		termInfoHashtable.put(POIDS_TYPO, new Boolean(true));
		termInfoHashtable.put(POIDS_POS, new Boolean(true));
	}
	
	public void set(String key){
		termInfoHashtable.put(key, new Boolean(true));
	}

	public void unSet(String key){
		termInfoHashtable.put(key, new Boolean(false));
	}
	
	public boolean get(String key){
		return ((Boolean)termInfoHashtable.get(key)).booleanValue();
	}
	
	public int getSize(){
		return termInfoHashtable.size();
	}
}
