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
package org.dafoe.application.importexportmanager.exp.wizard;

import org.dafoe.application.extensionpoint.generic.AbstractResourceExportationWidget;
import org.dafoe.application.extensionpoint.ontological.AbstractOntoExportationWidget;
import org.dafoe.application.extensionpoint.terminological.AbstractTerminoExportationWidget;
import org.dafoe.application.extensionpoint.terminoontological.AbstractTerminoOntoExportationWidget;
import org.dafoe.application.importexportmanager.controler.ControlerFactory;
import org.dafoe.application.importexportmanager.exp.actions.ExportResourceOntoJob;
import org.dafoe.application.importexportmanager.exp.actions.ExportResourceTerminoJob;
import org.dafoe.application.importexportmanager.exp.actions.ExportResourceTerminoOntoJob;
import org.dafoe.application.importexportmanager.exp.model.PluginComponentExp;
import org.dafoe.application.importexportmanager.model.ImportExportComponent;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

/**
 * The Class BlanckPage.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class BlanckPage extends WizardPage implements
		Listener {

	private AbstractResourceExportationWidget pluginBodyPage = null;
	public BlanckPage() {
		super("blanckPage"); //$NON-NLS-1$
	
		//this.setTitle("Export .");
		//this.setDescription("Please, complete fields ");

	}

	@Override
	public boolean canFlipToNextPage() {
		
		return false;
	}

	
	public boolean performFinish() {
		
		try {
			
			runInJob(this.pluginBodyPage);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		//return this.pluginBodyPage.exportData();
		return true;
	}

	@Override
	public boolean isPageComplete() {
		// TODO Auto-generated method stub
		return false;//pluginBodyPage.isPageComplete();
	}

	private Composite body;
	private Composite parent;
	
	public void createControl(Composite parent) {
		this.parent = parent;
		body = new Composite(parent, SWT.NONE);

		setControl(body);
	}

	public void handleEvent(Event event) {
		saveDataToModel();
		this.setPageComplete(this.pluginBodyPage.isPageComplete());
	}

	private void saveDataToModel() {
		// ImportManagementWizard wizard = (ImportManagementWizard) getWizard();
		System.out.println("BlanckPage.saveDataToModel()");
	}

	
	public void initPage() {
		//System.out.println(this.parent.getChildren().length);
		body.dispose();		
		//System.out.println(this.parent.getChildren().length);
			
		this.pluginBodyPage= getUIBodyPageForSelectedPlugin();
		
		this.setTitle(this.pluginBodyPage.getTitle());
		this.setDescription(this.pluginBodyPage.getDescription());
		
		this.pluginBodyPage.createUI(parent);
		
		//bodyComp.setParent(parent);
		setControl(this.pluginBodyPage.getContent());
		
		this.parent.layout();
	}

	private AbstractResourceExportationWidget getUIBodyPageForSelectedPlugin() {
		ImportExportComponent currentPlugin= ControlerFactory.getControler().getCurrentSelectedPluginComponent();
		
		if(currentPlugin instanceof PluginComponentExp){
			PluginComponentExp expPlugin= (PluginComponentExp)currentPlugin;
			return expPlugin.getUIBodyPart();
		}
			
		
		return new BlanckBody();
	}
	
	
	private static  void runInJob(AbstractResourceExportationWidget resource){

			if(resource!= null){// a resource was corretly instantiated for this plugin component
				
				// the selected plugin belong to the terminological level
				if(resource instanceof AbstractTerminoExportationWidget){
					ExportResourceTerminoJob job= new ExportResourceTerminoJob(resource);
					
					job.run();
				}
				
				// the selected plugin belong to the terminoontological level
				if(resource instanceof AbstractTerminoOntoExportationWidget){
					ExportResourceTerminoOntoJob job= new ExportResourceTerminoOntoJob(resource);
					
					job.run();
				}
				
				// the selected plugin belong to the ontological level
				if(resource instanceof AbstractOntoExportationWidget){
					ExportResourceOntoJob job= new ExportResourceOntoJob(resource);
					
					job.run();
				}
				
				
			}
		}
		
	
	
	class BlanckBody extends AbstractTerminoOntoExportationWidget {

		public BlanckBody() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean exportData() {
			//super.s
		    //System.out.println("BlanckBody.exportData()");
			return false;
		}

		@Override
		public boolean isPageComplete() {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public void createUI(Composite parent) {
					
			//System.out.println("BlanckBody.createUI()");
			
			final Composite body = new Composite(parent, SWT.NONE);
			GridLayout layout = new GridLayout(3, false);
			layout.horizontalSpacing = 0;
			layout.verticalSpacing = 0;
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			body.setLayout(layout);
			
			new Label(body, SWT.NONE).setText("No Plugin has been selected !");

			setContent(body);
			
		}

		@Override
		public String getDescription() {
			// TODO Auto-generated method stub
			return "";
		}

		@Override
		public String getTitle() {
			// TODO Auto-generated method stub
			return "";
		}

	}
	
	
}
