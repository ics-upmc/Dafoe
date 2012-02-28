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

package org.dafoe.plugin.imp.owl.main;

import java.io.File;

import org.dafoe.application.extensionpoint.ontological.AbstractOntoImportationWidget;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.OntologyImpl;
import org.dafoe.plugin.imp.owl.model.ImportOWL;
import org.eclipse.jface.viewers.TableViewer;
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

public class OwlLoaderResource extends AbstractOntoImportationWidget {
	protected TableViewer fileTableViewer;
	protected static Text txtOntologyName ;
	
	protected static Text txtSelectedFile ;

	
	public OwlLoaderResource() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "This plug-in will import an OWL ontology.";
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Enter a file name";
	}

	@Override
	public boolean importData() {
		// Use and UIThread in order to advoid "org.eclipse.swt.SWTException: Invalid thread access"
		PlatformUI.getWorkbench().getDisplay().syncExec(
			        new Runnable() {
			           public void run(){
			        	   String fileName=txtSelectedFile.getText();
			        	   
			        	   			       		
			        	  //  getSeletedSchemes();
			        	
			     		  if (fileName != null) {
			     	          	
			     			  	IOntology newOnto= new OntologyImpl();
			     			  	newOnto.setName(txtOntologyName.getText());
			     			  	
			     			  	ImportOWL imp= new ImportOWL(new File(fileName), newOnto);
			     			  	
			     			  	imp.run();
			     	        	
			     	            
			     	        }
			           }
			        }
			     );
		return false;
	}

	@Override
	public boolean isPageComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createUI(Composite parent) {
		final Composite body = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		body.setLayout(layout);
		
		new Label(body, SWT.NONE).setText("Ontology name:");
		
		 // Create the text box extra wide to show long paths
	    txtOntologyName = new Text(body, SWT.BORDER);
	    GridData dataRes = new GridData(GridData.FILL_HORIZONTAL);
	    dataRes.horizontalSpan = 2;
	    txtOntologyName.setLayoutData(dataRes);
	    
		
	    new Label(body, SWT.NONE).setText("File:");
	    
	    // Create the text box extra wide to show long paths
	    txtSelectedFile = new Text(body, SWT.BORDER);
	    txtSelectedFile.setEnabled(false);
	    GridData data = new GridData(GridData.FILL_HORIZONTAL);
	    data.horizontalSpan = 1;
	    txtSelectedFile.setLayoutData(data);

	    // Clicking the button will allow the user
	    // to select a directory
	    Button btnBrowse = new Button(body, SWT.PUSH);
	    btnBrowse.setText("Browse...");
	    btnBrowse.addSelectionListener(new SelectionAdapter() {
	      public void widgetSelected(SelectionEvent event) {
	        FileDialog fd = new FileDialog(body.getShell());

	        fd.setText("Select OWL Ontology");
	        
	        fd.setFilterExtensions(new String[]{"*.owl"});
	        
	        String selected = fd.open();
	        
	        txtSelectedFile.setText(selected);
	        
	       }
	    });
	    
	    
	
	   	    
	 // all plugin must explicit notify the change of the content after build it
	    setContent(body);
		
	}
	

	
}
