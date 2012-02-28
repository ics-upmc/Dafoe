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
package org.dafoe.application.importexportmanager.imp.actions;

import org.dafoe.application.extensionpoint.generic.AbstractResourceImportationWidget;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

/**
 * The Class ImportResourceTerminoAction.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class ImportResourceTerminoJob extends Action implements
		IWorkbenchWindowActionDelegate {

	@SuppressWarnings("unused")
	private IWorkbenchWindow window;

	private AbstractResourceImportationWidget resource;
	
	

	private final static int PAUSE_TIMER = 1000;

	public ImportResourceTerminoJob() {
		this(null);
	}


	public ImportResourceTerminoJob(AbstractResourceImportationWidget resource) {

		this.resource = resource;
		
		init(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	}

	
	public void run() {
		// Job
		Job job = new Job("Import") {
			protected org.eclipse.core.runtime.IStatus run(
					final IProgressMonitor monitor) {
				
				try {
					
			            	   monitor.beginTask("import", IProgressMonitor.UNKNOWN);
			            	   
			            	   monitor.subTask("Init Connection ...");
							   ImportResourceTerminoJob.this.sleep(PAUSE_TIMER);
			            	   //resource.initConnection(filesDirectory);
			            	   
								monitor.subTask("Importation ...");
								ImportResourceTerminoJob.this.sleep(PAUSE_TIMER);
								resource.importData();
								
								monitor.subTask("Finishing ...");
								ImportResourceTerminoJob.this.sleep(PAUSE_TIMER);
												
					
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}			
					
				monitor.done();
				
				return Status.OK_STATUS;
			} // end run job 
			
			
		}; // end new job 
													
		job.setUser(true);
		job.schedule();
	}

	private void sleep(long mills) {
		try {
			Thread.sleep(mills);
		} catch (InterruptedException e) {
		}
	}

	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run(IAction arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectionChanged(IAction arg0, ISelection arg1) {
		// TODO Auto-generated method stub
		
	}

}