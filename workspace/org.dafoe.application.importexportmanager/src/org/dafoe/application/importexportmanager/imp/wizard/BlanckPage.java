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
package org.dafoe.application.importexportmanager.imp.wizard;


import org.dafoe.application.extensionpoint.generic.AbstractResourceImportationWidget;
import org.dafoe.application.extensionpoint.ontological.AbstractOntoImportationWidget;
import org.dafoe.application.extensionpoint.terminological.AbstractTerminoImportationWidget;
import org.dafoe.application.extensionpoint.terminoontological.AbstractTerminoOntoImportationWidget;
import org.dafoe.application.importexportmanager.controler.ControlerFactory;
import org.dafoe.application.importexportmanager.imp.actions.ImportResourceOntoJob;
import org.dafoe.application.importexportmanager.imp.actions.ImportResourceTerminoJob;
import org.dafoe.application.importexportmanager.imp.actions.ImportResourceTerminoOntoJob;
import org.dafoe.application.importexportmanager.imp.model.PluginComponentImp;
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

	private AbstractResourceImportationWidget pluginBodyPage = null;
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
		
		return false;
	}

	private Composite body;
	private Composite parent;
	
	public void createControl(Composite parent) {
		this.parent = parent;
		body = new Composite(parent, SWT.NONE);

		this.setControl(body);
	}

	public void handleEvent(Event event) {
		saveDataToModel();
		this.setPageComplete(isPageComplete());
	}

	private void saveDataToModel() {
		// ImportManagementWizard wizard = (ImportManagementWizard) getWizard();
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

	private AbstractResourceImportationWidget getUIBodyPageForSelectedPlugin() {
		ImportExportComponent currentPlugin= ControlerFactory.getControler().getCurrentSelectedPluginComponent();
		
		if(currentPlugin instanceof PluginComponentImp){
			PluginComponentImp expPlugin= (PluginComponentImp)currentPlugin;
			return expPlugin.getUIBodyPart();
		}
			
		
		return new BlanckBody();
	}
	
	
	private static  void runInJob(AbstractResourceImportationWidget resource){
  System.out.println("BlanckPage.runInJob() resource= "+resource.toString());
  
			if(resource!= null){// a resource was corretly instantiated for this plugin component
				System.out.println("BlanckPage.runInJob() not null");
				// the selected plugin belong to the terminological level
				if(resource instanceof AbstractTerminoImportationWidget){
					System.out.println("BlanckPage.runInJob() t");
					ImportResourceTerminoJob job= new ImportResourceTerminoJob(resource);
					
					job.run();
				}
				
				// the selected plugin belong to the terminoontological level
				if(resource instanceof AbstractTerminoOntoImportationWidget){
					System.out.println("BlanckPage.runInJob() to");
					ImportResourceTerminoOntoJob job= new ImportResourceTerminoOntoJob(resource);
					
					job.run();
				}
				
				// the selected plugin belong to the ontological level
				if(resource instanceof AbstractOntoImportationWidget){
					System.out.println("BlanckPage.runInJob() o");
					ImportResourceOntoJob job= new ImportResourceOntoJob(resource);
					
					job.run();
				}
				
				
			}
		}
		
	
	
	class BlanckBody extends AbstractResourceImportationWidget {

		public BlanckBody() {
			// TODO Auto-generated constructor stub
		}

		@Override
		public boolean importData() {
			// TODO Auto-generated method stub
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
