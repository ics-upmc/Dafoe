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
package org.dafoe.application.importexportmanager.adaptater;

import java.util.ArrayList;
import java.util.List;

import org.dafoe.application.importexportmanager.exp.model.PluginComponentExp;
import org.dafoe.application.importexportmanager.exp.model.PluginLevelExp;
import org.dafoe.application.importexportmanager.imp.model.PluginComponentImp;
import org.dafoe.application.importexportmanager.imp.model.PluginLevelImp;
import org.dafoe.application.importexportmanager.model.ImportExportComponent;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;


/**
 * The Class Services.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class Services {

	private static final String TERMINO_EXTENSION_POINT_ID = "org.dafoe.application.extensionpoint.ImportTermino"; //$NON-NLS-1$
	
	private static final String TERMINO_ONTO_EXTENSION_POINT_ID = "org.dafoe.application.extensionpoint.ImportTerminoOnto"; //$NON-NLS-1$
	
	private static final String ONTO_EXTENSION_POINT_ID = "org.dafoe.application.extensionpoint.ImportOnto"; //$NON-NLS-1$
	
	
	private static final String EXPORT_TERMINO_EXTENSION_POINT_ID = "org.dafoe.application.extensionpoint.ExportTermino"; //$NON-NLS-1$
	
	private static final String EXPORT_TERMINO_ONTO_EXTENSION_POINT_ID = "org.dafoe.application.extensionpoint.ExportTerminoOnto"; //$NON-NLS-1$
	
	private static final String EXPORT_ONTO_EXTENSION_POINT_ID = "org.dafoe.application.extensionpoint.ExportOnto"; //$NON-NLS-1$

	
	
	
	public static List<ImportExportComponent> getAllImportContributors(){
		List<ImportExportComponent> comps= new ArrayList<ImportExportComponent>();
		
		// termino level
		PluginLevelImp terminoLevel= new PluginLevelImp(Messages.Services_3);
		terminoLevel.setPlugins(getImpContributors(TERMINO_EXTENSION_POINT_ID));
		
		comps.add(terminoLevel);
		
		// termino_onto level
		PluginLevelImp terminoOntoLevel= new PluginLevelImp(Messages.Services_4);
		terminoOntoLevel.setPlugins(getImpContributors(TERMINO_ONTO_EXTENSION_POINT_ID));
		
		comps.add(terminoOntoLevel);
		
		// onto level
		PluginLevelImp OntoLevel= new PluginLevelImp(Messages.Services_5);
		OntoLevel.setPlugins(getImpContributors(ONTO_EXTENSION_POINT_ID));
		
		comps.add(OntoLevel);
		
		// other level
		PluginLevelImp otherLevel= new PluginLevelImp(Messages.Services_6);
		otherLevel.setPlugins(new ArrayList<PluginComponentImp>());
		
		comps.add(otherLevel);
		
		
		return comps;
	}
	
	
	public static List<ImportExportComponent> getAllExportContributors(){
		List<ImportExportComponent> comps= new ArrayList<ImportExportComponent>();
		
		// termino level
		PluginLevelExp terminoLevel= new PluginLevelExp(Messages.Services_3);
		terminoLevel.setPlugins(getExportContributors(EXPORT_TERMINO_EXTENSION_POINT_ID));
		
		comps.add(terminoLevel);
		
		// termino_onto level
		PluginLevelExp terminoOntoLevel= new PluginLevelExp(Messages.Services_4);
		terminoOntoLevel.setPlugins(getExportContributors(EXPORT_TERMINO_ONTO_EXTENSION_POINT_ID));
		
		comps.add(terminoOntoLevel);
		
		// onto level
		PluginLevelExp OntoLevel= new PluginLevelExp(Messages.Services_5);
		OntoLevel.setPlugins(getExportContributors(EXPORT_ONTO_EXTENSION_POINT_ID));
		
		comps.add(OntoLevel);
		
		// other level
		PluginLevelExp otherLevel= new PluginLevelExp(Messages.Services_6);
		otherLevel.setPlugins(new ArrayList<PluginComponentExp>());
		
		comps.add(otherLevel);
		
		
		return comps;
	}
	
	public static List<PluginComponentImp> getImpContributors(String extensionId){
		List<PluginComponentImp> comps= new ArrayList<PluginComponentImp>();
		IConfigurationElement[] contributorPlugins = Platform.getExtensionRegistry()
		.getConfigurationElementsFor(extensionId);
		
		for(IConfigurationElement contrib: contributorPlugins){
			PluginComponentImp plug= new PluginComponentImp(contrib);
			comps.add(plug);
		}
		return comps;
	}
	
	public static List<PluginComponentExp> getExportContributors(String extensionId){
		List<PluginComponentExp> comps= new ArrayList<PluginComponentExp>();
		IConfigurationElement[] contributorPlugins = Platform.getExtensionRegistry()
		.getConfigurationElementsFor(extensionId);
		
		for(IConfigurationElement contrib: contributorPlugins){
			PluginComponentExp plug= new PluginComponentExp(contrib);
			comps.add(plug);
		}
		return comps;
	}



}
