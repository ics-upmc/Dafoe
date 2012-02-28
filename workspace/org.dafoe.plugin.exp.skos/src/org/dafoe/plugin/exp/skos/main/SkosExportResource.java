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

package org.dafoe.plugin.exp.skos.main;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dafoe.application.extensionpoint.terminoontological.AbstractTerminoOntoExportationWidget;
import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntologyImpl;
import org.dafoe.plugin.exp.skos.adaptater.Services;
import org.dafoe.plugin.exp.skos.controler.ControlerFactory;
import org.dafoe.plugin.exp.skos.dialog.SelectOntoDialog;
import org.dafoe.plugin.exp.skos.dialog.SelectTerminoOntoDialog;
import org.dafoe.plugin.exp.skos.model.SKOSExporter;
import org.dafoe.plugin.exp.skos.model.SKOSOutputFormat;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class SkosExportResource extends AbstractTerminoOntoExportationWidget {

	private Text txtFile;
	private Text txtTerminoOnto;
	private Button rdfXmlFormat;
	private Button rdfXmlAbbrevFormat;

	private boolean isPageComplete= false;
	
	public SkosExportResource() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean exportData() {
		// Use and UIThread in order to advoid "org.eclipse.swt.SWTException: Invalid thread access"
		System.out.println("SkosExportResource.exportData()");
		 boolean success= true;	
		 
		 
		
		 PlatformUI.getWorkbench().getDisplay().syncExec(
			        new Runnable() {
			           public void run(){
			              
			        	    String dir= txtFile.getText();
			        	    
			        	    
			        	    if (dir != null) {
			      	          // Set the text box to the new selection
			      	            	
			      	        	File f= new File(dir);
			      	        	
			      	        	ITerminoOntology to= ControlerFactory.getControler().getCurrentSelectedTerminoOntology();
			      	        	
			      	        	SKOSExporter exp= new SKOSExporter(to);
			      	        	try {
			      	        		
			      	        		exp.run();
			      	        		
			      	        		
			      	        		if(rdfXmlFormat.getSelection()){
			      		        		exp.writeFile(f, SKOSOutputFormat.RDF_XML);
			      		        	}
			      		        	if(rdfXmlAbbrevFormat.getSelection()){
			      		        		exp.writeFile(f, SKOSOutputFormat.RDF_XML_ABBREV);
			      		        	}
			      		        	
			      					
			      				} catch (Exception e) {
			      					e.printStackTrace();
			      					//return false;
			      					//success= false;
			      				}
			        	    			        	    
			           
			           }
			           }// run
			        }
			     );

				        
		return success;
	}

	@Override
	public boolean isPageComplete() {
		// TODO Auto-generated method stub
		return isPageComplete;
	}

	@Override
	public void createUI(Composite parent) {
		// System.out.println("SkosExportResource.createUI()");
       final Shell shell= parent.getShell();
		final Composite body = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		
		body.setLayout(layout);

		// new Label(body, SWT.NONE).setText("Export to SKOS");
		
		new Label(body, SWT.NONE).setText("Termino-Ontology :");

		// Create the text box extra wide to show long paths
		txtTerminoOnto = new Text(body, SWT.BORDER);
		txtTerminoOnto.setEnabled(false);
		GridData data0 = new GridData(GridData.FILL_HORIZONTAL);
		data0.horizontalSpan = 1;
		txtTerminoOnto.setLayoutData(data0);
		
		/*
		txtTerminoOnto.addModifyListener(new ModifyListener() {
		      public void modifyText(ModifyEvent event) {
		          
			         isPageComplete= (txtFile.getText().length() > 0) && (txtTerminoOnto.getText().length()>0);
			        }
			      });
			      */

		// Clicking the button will allow the user
		// to select a directory
		final List<ITerminoOntology> tos= org.dafoe.plugin.exp.skos.Activator.getAllTerminoOntologies();
		
		Button btnBrowseTerminoOnto = new Button(body, SWT.PUSH);
		btnBrowseTerminoOnto.setText("...");
		btnBrowseTerminoOnto.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent event) {

				// open selection terminoonto dialg

				if(tos!= null ){
					SelectTerminoOntoDialog dlg0= new SelectTerminoOntoDialog(shell, "Termino-ontology selection",tos);
					dlg0.open();
				}
				
				
			
				
				
				

			}
		});
		
		CurrentTerminoOntologyPropertyChangeListener toListener = new CurrentTerminoOntologyPropertyChangeListener();

		ControlerFactory.getControler().addPropertyChangeListener(ControlerFactory.getControler().SELECT_TERMINO_ONTO_EVENT, toListener);

		
		
        //
		new Label(body, SWT.NONE).setText("File :");

		// Create the text box extra wide to show long paths
		txtFile = new Text(body, SWT.BORDER);
		txtFile.setEnabled(false);
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		txtFile.setLayoutData(data);
		/*
		txtFile.addModifyListener(new ModifyListener() {
		      public void modifyText(ModifyEvent event) {
		          
		         isPageComplete= (txtFile.getText().length() > 0) && (txtTerminoOnto.getText().length()>0);
		        }
		      });
		*/

		// Clicking the button will allow the user
		// to select a directory
		Button btnBrowse = new Button(body, SWT.PUSH);
		btnBrowse.setText("...");
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				FileDialog fd = new FileDialog(body.getShell(), SWT.SAVE);
				fd.setText("Save");
				fd.setFilterPath(null);
				fd.setFilterExtensions(new String[]{"*.rdf"});

				String dir = fd.open();

				txtFile.setText(dir);

			}
		});
		
		// select output format
		
		new Label(body, SWT.NONE).setText("Output format :");

		rdfXmlFormat = new Button(body, SWT.RADIO);
		rdfXmlFormat.setText("RDF/XML");
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		rdfXmlFormat.setLayoutData(gd);
		rdfXmlFormat.setSelection(true);
		
		
		rdfXmlAbbrevFormat = new Button(body, SWT.RADIO);
		rdfXmlAbbrevFormat.setText("RDF/XML-ABBREV");
		GridData gd1 = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 1;
		rdfXmlAbbrevFormat.setLayoutData(gd1);
		
		// all plugin must explicit notify the change of the content after build it
		setContent(body);

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "This plug-in will export a dafoe termino-ontology according to SKOS";
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Enter a file name";
	}
	
	
	class CurrentTerminoOntologyPropertyChangeListener implements PropertyChangeListener {
		@Override
		public void propertyChange(java.beans.PropertyChangeEvent arg0) {
			ITerminoOntology to= ControlerFactory.getControler().getCurrentSelectedTerminoOntology();

			if (to != null) {
				txtTerminoOnto.setText(to.getName());
				
			} else {
				
			}
			
			
		}

	}
}
