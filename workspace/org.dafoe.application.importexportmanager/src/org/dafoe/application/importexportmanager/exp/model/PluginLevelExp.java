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
package org.dafoe.application.importexportmanager.exp.model;

import java.util.ArrayList;
import java.util.List;

import org.dafoe.application.importexportmanager.model.ImportExportComponent;

/**
 * The Class PluginLevel.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class PluginLevelExp extends ImportExportComponent{

	private String name;
	
	private List<PluginComponentExp> plugins= new ArrayList<PluginComponentExp>();
	
	
	public PluginLevelExp() {
		// TODO Auto-generated constructor stub
	}
	
	public PluginLevelExp(String name) {
		this.name= name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public void setPlugins(List<PluginComponentExp> plugins) {
		this.plugins = plugins;
	}
	
	public List<PluginComponentExp> getPlugins() {
		return plugins;
	}
	
	public boolean addPlugin(PluginComponentExp p){
		
		return this.plugins.add(p);
	}
}
