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

import org.dafoe.application.extensionpoint.generic.AbstractResourceExportationWidget;
import org.dafoe.application.importexportmanager.model.ImportExportComponent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;

/**
 * The Class PluginComponent.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class PluginComponentExp extends ImportExportComponent{
	private static final String EXTENSION_ATTRIBUT_NAME = "name";
	
	private static final String EXTENSION_ATTRIBUT_CLASS = "class"; //$NON-NLS-1$
	
	private IConfigurationElement configElement;
	private AbstractResourceExportationWidget bodyPart;
	//private AbstractResourceExportationWizardPage page;
	
	public PluginComponentExp() {
		// TODO Auto-generated constructor stub
	}
	
	public PluginComponentExp(IConfigurationElement cfgElt){
		this.configElement=cfgElt;
		//page= AbstractResourceExportationWizardPage.getDefaultExportWizardPage();
		
		try {
			this.bodyPart= (AbstractResourceExportationWidget) cfgElt.createExecutableExtension(EXTENSION_ATTRIBUT_CLASS);
		    
			//page.setBodyPage(rightBodyPage);
		
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String getName(){
		return configElement.getAttribute(EXTENSION_ATTRIBUT_NAME);
	}
	
	
	public IConfigurationElement getConfigElement() {
		return configElement;
	}
	
	public AbstractResourceExportationWidget getUIBodyPart() {
		return bodyPart;
	}
	
	
}
