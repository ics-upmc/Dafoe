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
package org.dafoe.application.importexportmanager.controler;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.dafoe.application.importexportmanager.model.ImportExportComponent;

/**
 * The Class Controler.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class Controler {
	
	private  ImportExportComponent currentSelectedPluginComponent;
	
	
	
	private  PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
	
	public static final String SELECT_PLUGIN_EVENT="selectPluginEvent";
	
	
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
	}
	
	public  ImportExportComponent getCurrentSelectedPluginComponent(){
	 return 	currentSelectedPluginComponent;
	}
	
	public  void setCurrentSelectedPluginComponent(
			ImportExportComponent plugin) {
		ImportExportComponent oldSelectedPlugin= currentSelectedPluginComponent;
		currentSelectedPluginComponent = plugin;
		
		propertyChangeSupport.firePropertyChange(SELECT_PLUGIN_EVENT, oldSelectedPlugin, currentSelectedPluginComponent);	
	}
	
	
	

}
