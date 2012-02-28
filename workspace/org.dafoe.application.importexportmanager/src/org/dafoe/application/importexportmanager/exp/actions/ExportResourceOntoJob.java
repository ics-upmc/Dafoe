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
package org.dafoe.application.importexportmanager.exp.actions;

import org.dafoe.application.extensionpoint.generic.AbstractResourceExportationWidget;
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
 * The Class ImportResourceOntoAction.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class ExportResourceOntoJob extends Action implements
		IWorkbenchWindowActionDelegate {

	
	private final static int PAUSE_TIMER = 1000;
	
	@SuppressWarnings("unused")
	private IWorkbenchWindow window;

	private AbstractResourceExportationWidget resource;
	
	public ExportResourceOntoJob() {
		this(null);
	}
	
	public ExportResourceOntoJob(AbstractResourceExportationWidget resource) {

		this.resource = resource;
		
		init(PlatformUI.getWorkbench().getActiveWorkbenchWindow());
	}

	
	public void run() {
		// Job
		Job job = new Job("Export") {
			protected org.eclipse.core.runtime.IStatus run(
					final IProgressMonitor monitor) {
				
				try {
					
			            	   monitor.beginTask("export", IProgressMonitor.UNKNOWN);
			            	   
			            	   monitor.subTask("Init Connection ...");
							   ExportResourceOntoJob.this.sleep(PAUSE_TIMER);
			            	   //resource.exportData();
			            	   
								monitor.subTask("Exportation ...");
								ExportResourceOntoJob.this.sleep(PAUSE_TIMER);
								resource.exportData();
								
								monitor.subTask("Finishing ...");
								ExportResourceOntoJob.this.sleep(PAUSE_TIMER);
							
											
					
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