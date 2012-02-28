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

package org.dafoe.plugin.exp.owl.main;

import java.beans.PropertyChangeListener;
import java.io.File;

import org.dafoe.application.extensionpoint.ontological.AbstractOntoExportationWidget;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.plugin.exp.owl.controler.ControlerFactory;
import org.dafoe.plugin.exp.owl.dialog.SelectOntoDialog;
import org.dafoe.plugin.exp.owl.model.ExportOWL;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class OwlExporterResource extends AbstractOntoExportationWidget {

	private Text txtFile;
	private Text txtOnto;

	public OwlExporterResource() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean exportData() {
		// Use and UIThread in order to advoid "org.eclipse.swt.SWTException: Invalid thread access"
		PlatformUI.getWorkbench().getDisplay().syncExec(
			        new Runnable() {
			           public void run(){
			        	   String dir= txtFile.getText();
			       		
			     		  if (dir != null) {
			     	          // Set the text box to the new selection
			     	            	
			     	        	File f= new File(dir);
			     	        	
			     	        	IOntology onto= ControlerFactory.getControler().getCurrentSelectedOntology();
			     	        	
			     	        	ExportOWL exp= new ExportOWL();
			     	        	
			     	        	try {
			     					exp.export(f,onto);
			     				} catch (Exception e) {
			     					// TODO Auto-generated catch block
			     					e.printStackTrace();
			     					//return false;
			     				}
			     	        	
			     	        
			     	        }
			           }
			        }
			     );

		
		
		return true;
	}

	@Override
	public boolean isPageComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createUI(Composite parent) {
		// System.out.println("SkosExportResource.createUI()");

		final Composite body = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		
		body.setLayout(layout);

		// new Label(body, SWT.NONE).setText("Export to SKOS");
		
		new Label(body, SWT.NONE).setText(Messages.OwlExporterResource_0);

		// Create the text box extra wide to show long paths
		txtOnto = new Text(body, SWT.BORDER);
		txtOnto.setEnabled(false);
		GridData data0 = new GridData(GridData.FILL_HORIZONTAL);
		data0.horizontalSpan = 1;
		txtOnto.setLayoutData(data0);

		// Clicking the button will allow the user
		// to select a directory
		Button btnBrowseOnto = new Button(body, SWT.PUSH);
		btnBrowseOnto.setText("..."); //$NON-NLS-1$
		btnBrowseOnto.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				// open selection terminoonto dialg
				
				SelectOntoDialog dlg= new SelectOntoDialog(body.getShell(), Messages.OwlExporterResource_1);
				
				dlg.open();

			}
		});
		
		CurrentOntologyPropertyChangeListener toListener = new CurrentOntologyPropertyChangeListener();

		ControlerFactory.getControler().addPropertyChangeListener(ControlerFactory.getControler().SELECT_ONTOLOGY_EVENT, toListener);

		
		
        //
		new Label(body, SWT.NONE).setText(Messages.OwlExporterResource_3);

		// Create the text box extra wide to show long paths
		txtFile = new Text(body, SWT.BORDER);
		txtFile.setEnabled(false);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		txtFile.setLayoutData(data);

		// Clicking the button will allow the user
		// to select a directory
		Button btnBrowse = new Button(body, SWT.PUSH);
		btnBrowse.setText("...");
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				FileDialog fd = new FileDialog(body.getShell(), SWT.SAVE);
				fd.setText(Messages.OwlExporterResource_5);
				fd.setFilterPath(null);
				fd.setFilterExtensions(new String[]{"*.owl"});
				String dir = fd.open();

				txtFile.setText(dir);

			}
		});
		
		// all plugin must explicit notify the change of the content after build it
		setContent(body);

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return Messages.OwlExporterResource_6;
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return Messages.OwlExporterResource_7;
	}
	
	
	class CurrentOntologyPropertyChangeListener implements PropertyChangeListener {
		@Override
		public void propertyChange(java.beans.PropertyChangeEvent arg0) {
			IOntology to= ControlerFactory.getControler().getCurrentSelectedOntology();

			if (to != null) {
				txtOnto.setText(to.getName());
				
			} else {
				
			}
			
			
		}

	}
}