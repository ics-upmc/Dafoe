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
package org.dafoe.application.importexportmanager.imp.model;

import org.dafoe.application.importexportmanager.model.ImportExportComponent;

/**
 * The Class ImportManagementModel.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class ImportManagementModel {

	ImportExportComponent selectedPlugin;

	public ImportManagementModel() {
		// TODO Auto-generated constructor stub
	}

	public ImportExportComponent getSelectedPlugin() {
		return selectedPlugin;
	}

	public void setSelectedPlugin(ImportExportComponent selectedPlugin) {
		this.selectedPlugin = selectedPlugin;
	}

	public boolean isValid() {
		boolean isValid= false; 
				
		if(selectedPlugin instanceof PluginComponentImp){
			isValid= true;
		}
		return isValid;
	}

}
