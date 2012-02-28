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

package org.dafoe.plugin.imp.skos.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dafoe.api.skos.vocabulary.SKOSConceptScheme;
import org.dafoe.api.skos.vocabulary.SKOSModel;
import org.dafoe.application.extensionpoint.terminoontological.AbstractTerminoOntoImportationWidget;
import org.dafoe.plugin.imp.skos.model.ImportSKOS;
import org.dafoe.plugin.imp.skos.util.Util;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

public class SkosLoaderResource extends AbstractTerminoOntoImportationWidget {

	protected TableViewer fileTableViewer;
	protected static Text txtTerminoOntoName ;
	
	protected static Text txtSelectedFile ;
	protected CheckboxTableViewer fileCheckBoxTableViewer;
	
	private File currentSkosFile=null;
	
	private SKOSModel skosModel=null;
	
	private List<SKOSConceptScheme> skosConceptSchemesInFile= new ArrayList<SKOSConceptScheme>();
	
	private List<SKOSConceptScheme> selectedSkosConceptScheme= new ArrayList<SKOSConceptScheme>();
	
	public SkosLoaderResource() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "This plug-in will import data from SKOS.";
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "Select directory";
	}

	@Override
	public boolean importData() {
		// Use and UIThread in order to advoid "org.eclipse.swt.SWTException: Invalid thread access"
		PlatformUI.getWorkbench().getDisplay().syncExec(
			        new Runnable() {
			           public void run(){
			        	   String fileName=txtSelectedFile.getText();
			        	   
			        	   			       		
			        	  //  getSeletedSchemes();
			        	   
			        	   for(Object o: fileCheckBoxTableViewer.getCheckedElements()){
			        		   if(o instanceof SKOSConceptScheme){
			        			   selectedSkosConceptScheme.add(((SKOSConceptScheme)o));
			        		   }
			        	   }
			     		  if (fileName != null) {
			     	          			     	        	
			     	        	for(SKOSConceptScheme sch: selectedSkosConceptScheme){
			     	        		ImportSKOS imp= new ImportSKOS(sch,txtTerminoOntoName.getText());
			     	        		imp.run();
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
		final Composite body = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 0;
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		body.setLayout(layout);
		
		new Label(body, SWT.NONE).setText("TerminoOntology Name:");
		
		 // Create the text box extra wide to show long paths
	    txtTerminoOntoName = new Text(body, SWT.BORDER);
	    GridData dataRes = new GridData(GridData.FILL_HORIZONTAL);
	    dataRes.horizontalSpan = 2;
	    txtTerminoOntoName.setLayoutData(dataRes);
	    
		
	    new Label(body, SWT.NONE).setText("File :");
	    
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
	        FileDialog dlg = new FileDialog(body.getShell());

	        // Set the initial filter path according
	        // to anything they've selected or typed in
	        //dlg.setFilterPath(txtDirectory.getText());

	        // Change the title bar text
	        dlg.setText(" Dialog");

	        

	        // Calling open() will open and run the dialog.
	        // It will return the selected directory, or
	        // null if user cancels
	        String seletedFilePath = dlg.open();
	        currentSkosFile= new File(seletedFilePath);
	        
	        if (currentSkosFile != null) {
	          // Set the text box to the new selection
	        	txtSelectedFile.setText(seletedFilePath);
	          
	          //remove all already loaded files
	          fileCheckBoxTableViewer.getTable().removeAll();
	          
	          // load files of the selected dir in fileTableviewer
	          skosModel= SKOSModel.newInstance();
	          
	          skosModel.read(currentSkosFile);
	          //SKOSConceptScheme[]
	          
	          skosConceptSchemesInFile= Util.toList(skosModel.getConceptSchemes());
	          
	          fileCheckBoxTableViewer.setInput(skosConceptSchemesInFile);
	          
	          fileCheckBoxTableViewer.refresh();
	        }
	      }
	    });
	    
	    //final Composite areaFile= new Composite(body, SWT.NONE);
		GridData gdAreaFile = new GridData(GridData.FILL_HORIZONTAL);
		//areaFile.setLayoutData(GDCOMP);
		//GridLayout GL2 = new GridLayout(3, false);
		//areaFile.setLayout(GL2);
		gdAreaFile.horizontalSpan=2;
		gdAreaFile.verticalSpan=1;
	    Label lbFile= new Label(body, SWT.NONE);
	    lbFile.setText("Selected ConceptScheme(s):");
	    //lbFile.setLayoutData(gdAreaFile);
	    // create blank ui
	    Label lbBlank= new Label(body, SWT.NONE);
	    lbBlank.setLayoutData(gdAreaFile);
	    // create blank ui
	    //new Label(body, SWT.NONE);
	    
	    //final Composite areaViewer= new Composite(body, SWT.NONE);
		GridData gdAreaViewer = new GridData(GridData.FILL_HORIZONTAL);
		gdAreaViewer.horizontalSpan=2;
		gdAreaViewer.verticalSpan=10;
	
	    // Create the CheckboxTableViewer to display the files in the source dir
		fileCheckBoxTableViewer = CheckboxTableViewer.newCheckList(
	        body, SWT.BORDER);
				fileCheckBoxTableViewer.getTable().setLayoutData(gdAreaViewer);
				fileCheckBoxTableViewer.setContentProvider(new SkosConceptSchemeContenProvider());
				fileCheckBoxTableViewer.setLabelProvider(new SkosConceptSchemeLabelProvider());
	    

	    Button btnSelectAll = new Button(body, SWT.PUSH);
	    btnSelectAll.setText("Select all");
	    btnSelectAll.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent event) {
			        
			        
			      }
			    });
	    btnSelectAll.setEnabled(false);
	    
	    Button btnDeselectAll = new Button(body, SWT.PUSH);
	    btnDeselectAll.setText("Deselect all");
	    btnDeselectAll.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent event) {			        
		      }
		    });
	    btnDeselectAll.setEnabled(false);
	    
	    Button btnRefresh = new Button(body, SWT.PUSH);
	    btnRefresh.setText("Refresh");
	    btnRefresh.addSelectionListener(new SelectionAdapter() {
		      public void widgetSelected(SelectionEvent event) {
			        
			        
		      }
		    });
	    btnRefresh.setEnabled(false);
		
	 // all plugin must explicit notify the change of the content after build it
	    setContent(body);
	}
	
	

	
	
	class SkosConceptSchemeContenProvider implements IStructuredContentProvider {
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}
		public void dispose() {
		}
		public Object[] getElements(Object parent) {
			return  ((List<SKOSConceptScheme>) parent).toArray();
		}
	}

	class SkosConceptSchemeLabelProvider extends LabelProvider  {
		public String getText(Object obj) {
			  if (obj instanceof SKOSConceptScheme){
				 return  ((SKOSConceptScheme) obj).getUri();
			}
			  return null;
		}
		public Image getColumnImage(Object obj, int index) {
			return null;
		}
		public Image getImage(Object obj) {
			return null;
		}

}

}