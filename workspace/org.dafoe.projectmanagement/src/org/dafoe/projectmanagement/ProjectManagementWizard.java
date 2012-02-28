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

package org.dafoe.projectmanagement;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Display;

public class ProjectManagementWizard extends Wizard {

	NewProjectPage newProjectPage;
	
	OpenProjectPage openProjectPage;
	
	MenuProjectPage menuProjectPage;
	
	ProjectManagementModel refModel;
	
	boolean performState;

	private static final String IDENT_DELIMITER = "_"; //$NON-NLS-1$
	
	private static final String DAFOE_IDENT = "dafoe"; //$NON-NLS-1$

	private static final String CONNECTION_NAME_KEY = "connectionname"; //$NON-NLS-1$
	
	private static final String SERVER_NAME_KEY = "servername"; //$NON-NLS-1$
	
	private static final String PORT_NAME_KEY = "portname"; //$NON-NLS-1$
	
	private static final String SID_NAME_KEY = "sidname"; //$NON-NLS-1$
	
	private static final String USER_NAME_KEY = "username"; //$NON-NLS-1$
	
	private static final String PASSWORD_KEY = "password"; //$NON-NLS-1$
	
	public ProjectManagementWizard() {
		this.setWindowTitle(Messages.ProjectManagementWizard_8);
		
		refModel = new ProjectManagementModel();
	}
	
	@Override
	public void addPages() {
		menuProjectPage = new MenuProjectPage();
		this.addPage(menuProjectPage);		
		newProjectPage = new NewProjectPage();
		this.addPage(newProjectPage);
		openProjectPage = new OpenProjectPage();
		this.addPage(openProjectPage);
	}
	
	/**
	 * Extract from workspace location DAFOE database information.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ProjectManagementModel[] getRecentDatabases() { 
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		try {
			Set<QualifiedName> keySet = root.getPersistentProperties().keySet();
			Map<String, ProjectManagementModel> myHash = new HashMap<String, ProjectManagementModel>();
			for (QualifiedName qualifiedName : keySet) {
				String qualifier = qualifiedName.getQualifier();
				if (qualifier.startsWith(DAFOE_IDENT + IDENT_DELIMITER)) {
					int start = qualifier.indexOf(IDENT_DELIMITER);
					int finish = qualifier.indexOf(IDENT_DELIMITER, start + 1);
					String identDbName = qualifier.substring(start + 1, finish);

					ProjectManagementModel currentModel;
					if (myHash.get(identDbName) == null) {
						currentModel = new ProjectManagementModel();
						myHash.put(identDbName, currentModel);
					} else {
						currentModel = myHash.get(identDbName);
					}
					
					start = finish + 1;
					String optionName = qualifier.substring(start, qualifier.length());
					if (SERVER_NAME_KEY.equals(optionName)) {
						currentModel.setServerName((String)root.getPersistentProperties().get(qualifiedName));
					} else if (PORT_NAME_KEY.equals(optionName)) {
						currentModel.setPortName((String)root.getPersistentProperties().get(qualifiedName));						
					} else if (SID_NAME_KEY.equals(optionName)) {
						currentModel.setSidName((String)root.getPersistentProperties().get(qualifiedName));
					} else if (USER_NAME_KEY.equals(optionName)) {
						currentModel.setUserName((String)root.getPersistentProperties().get(qualifiedName));
					} else if (PASSWORD_KEY.equals(optionName)) {
						currentModel.setPasswordName((String)root.getPersistentProperties().get(qualifiedName));
					} else if (CONNECTION_NAME_KEY.equals(optionName)) {
						currentModel.setConnectionName((String)root.getPersistentProperties().get(qualifiedName));
					}
				}
			}
			
			return myHash.values().toArray(new ProjectManagementModel[0]);
		} catch (CoreException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Persist into the workspace location DAFOE database information. 
	 */
	private void persistNewDataBase() {
		try {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			String identDbName = this.refModel.getConnectionName();
			identDbName = identDbName.replaceAll(IDENT_DELIMITER, ""); //$NON-NLS-1$
			root.setPersistentProperty(new QualifiedName(DAFOE_IDENT
					+ IDENT_DELIMITER + identDbName + IDENT_DELIMITER
					+ CONNECTION_NAME_KEY, "en"), this.refModel //$NON-NLS-1$
					.getConnectionName());
			root.setPersistentProperty(new QualifiedName(DAFOE_IDENT
					+ IDENT_DELIMITER + identDbName + IDENT_DELIMITER
					+ SERVER_NAME_KEY, "en"), this.refModel.getServerName()); //$NON-NLS-1$
			root.setPersistentProperty(new QualifiedName(DAFOE_IDENT
					+ IDENT_DELIMITER + identDbName + IDENT_DELIMITER
					+ PORT_NAME_KEY, "en"), this.refModel.getPortName()); //$NON-NLS-1$
			root.setPersistentProperty(new QualifiedName(DAFOE_IDENT
					+ IDENT_DELIMITER + identDbName + IDENT_DELIMITER
					+ SID_NAME_KEY, "en"), this.refModel.getSidName()); //$NON-NLS-1$
			root.setPersistentProperty(new QualifiedName(DAFOE_IDENT
					+ IDENT_DELIMITER + identDbName + IDENT_DELIMITER
					+ USER_NAME_KEY, "en"), this.refModel.getUserName()); //$NON-NLS-1$
			root.setPersistentProperty(new QualifiedName(DAFOE_IDENT
					+ IDENT_DELIMITER + identDbName + IDENT_DELIMITER
					+ PASSWORD_KEY, "en"), this.refModel.getPasswordName()); //$NON-NLS-1$
		} catch (CoreException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean performFinish() {	
		performState = false;
		if (this.refModel.isExists()) {
			ProgressMonitorDialog pd = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
			try {
				pd.run(true, true, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						SQLScriptEngine currentSQLScriptEngine = new SQLScriptEngine(refModel);
						try {
							currentSQLScriptEngine.openProject(monitor);
							persistNewDataBase();
							performState = true;
						} catch (SQLScriptEngineException e) {
							e.printStackTrace();
							performState = false;
						}
					}
				});
				} catch (Exception e) {
					e.printStackTrace();
				}
		} else {
			ProgressMonitorDialog pd = new ProgressMonitorDialog(Display.getDefault().getActiveShell());
			try {
				pd.run(true, true, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						SQLScriptEngine currentSQLScriptEngine = new SQLScriptEngine(refModel);
						try {
							currentSQLScriptEngine.newProject(monitor);
							persistNewDataBase();
							performState = true;						
						} catch (SQLScriptEngineException e) {
							e.printStackTrace();
							performState = false;
						}					
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		return performState;			
	}

	@Override
	public boolean performCancel() {
		boolean questionStatus = MessageDialog.openQuestion(Display.getDefault().getActiveShell(), "Cancelling opening DAFOE project", "Do you want to stop opening DAFOE project ?");
		return questionStatus; 
	}
	
	public String getDbUrl() {
		return this.refModel.getDbUrl();
	}
	
	public String getDbUser() {
		return this.refModel.getUserName();
	}
	
	public String getDbPassword() {
		return this.refModel.getPasswordName();
	}
	
	//VT
	public void removeProject(){
		
	}
}
