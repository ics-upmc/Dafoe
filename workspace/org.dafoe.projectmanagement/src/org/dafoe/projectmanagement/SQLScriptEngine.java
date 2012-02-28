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

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

public class SQLScriptEngine {
	
	private ProjectManagementModel refModel;
	
	private Connection conn;
	
	public SQLScriptEngine(ProjectManagementModel pModel) {
		this.refModel = pModel;		
	}
	
	public void openProject(final IProgressMonitor monitor) throws SQLScriptEngineException {
		monitor.setTaskName(Messages.SQLScriptEngine_0 + this.refModel.getSidName() + Messages.SQLScriptEngine_1);
		try {
			connectToDataBase(this.refModel.getSidName());
		} catch (SQLScriptEngineException e) {
			monitor.done();
			throw e;
		} finally {
			this.disconnectToDataBase();
		}
	}
	
	/**
	 * Process to execute in order to create a new DAFOE database.
	 * 
	 * @param monitor
	 */
	public void newProject(final IProgressMonitor monitor) throws SQLScriptEngineException {
		monitor.setTaskName(Messages.SQLScriptEngine_2);
		try {
			connectToDataBase("postgres"); //$NON-NLS-1$
			monitor.setTaskName(Messages.SQLScriptEngine_4 + refModel.getSidName() + Messages.SQLScriptEngine_5);
			initializeDataBase();
			monitor.setTaskName(Messages.SQLScriptEngine_6);
			disconnectToDataBase();
			monitor.setTaskName(Messages.SQLScriptEngine_7 + this.refModel.getSidName() + Messages.SQLScriptEngine_8);
			connectToDataBase(this.refModel.getSidName());
			monitor.setTaskName(Messages.SQLScriptEngine_9);			
			executeScript("schema.sql"); //$NON-NLS-1$
			monitor.setTaskName(Messages.SQLScriptEngine_11);			
			executeScript("systemdata.sql"); //$NON-NLS-1$
		} catch (SQLScriptEngineException e) {
			monitor.done();
			throw e;
		} finally {
			this.disconnectToDataBase();
		}
	}
	
	/**
	 * Execute script file located into this bundle.
	 * 
	 * @param file
	 * @throws SQLScriptEngineException
	 */
	private void executeScript(String file) throws SQLScriptEngineException {
		try {
			InputStream inputStream = Activator.getDefault().getBundle().getResource("sql/" + file).openStream(); //$NON-NLS-1$
			BufferedReader reader = new BufferedReader ( new InputStreamReader ( inputStream ) );
			
			SQLScriptRunner scriptRunner = new SQLScriptRunner(conn, true, true);
			scriptRunner.runScript(reader);
		} catch (Exception e) {
			MessageDialog.openError(Display.getDefault().getActiveShell(), Messages.SQLScriptEngine_14 + " (" + e.getMessage() + ")",Messages.SQLScriptEngine_17 + file); //$NON-NLS-2$ //$NON-NLS-3$
			throw new SQLScriptEngineException(e);
		}
	}
	
	/**
	 * Create DataBase.
	 * 
	 * @return
	 */
	private boolean initializeDataBase() throws SQLScriptEngineException {		
		if (isSIDExists()) {
			// Dropped DataBase ?
			return false;
		} else {
			// Create DataBase.
			createDataBase(this.refModel.getSidName());
			return true;
		}
	}
	
	/**
	 * Check if SID is existing.
	 * 
	 * @return
	 */
	private boolean isSIDExists() {
		return false;
	}
	
	/**
	 * Close the current JDBC connection.
	 * 
	 * @throws SQLScriptEngineException
	 */
	private void disconnectToDataBase() throws SQLScriptEngineException {
		if (conn != null) {
			try {
				if (!conn.isClosed()) {
					conn.close();
				} else {
					conn = null;
				}
			} catch (SQLException e) {
				throw new SQLScriptEngineException(e);
			}
		}
	}
	
	/**
	 * Used to connect to database.
	 * 
	 * @param pDatabaseName
	 * @throws SQLScriptEngineException
	 */
	private void connectToDataBase(String pDatabaseName) throws SQLScriptEngineException {
		try {
			Class.forName("org.postgresql.Driver"); //$NON-NLS-1$
			String databaseName = "jdbc:postgresql://" + this.refModel.getServerName() + ":" + this.refModel.getPortName() + "/" + pDatabaseName; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			conn = DriverManager.getConnection(databaseName, this.refModel.getUserName(), this.refModel.getPasswordName());
		} catch (ClassNotFoundException e) {
			throw new SQLScriptEngineException(e);
		} catch (SQLException e) {
			MessageDialog.openError(Display.getDefault().getActiveShell(),  " (" + e.getMessage() + ")",Messages.SQLScriptEngine_25); //$NON-NLS-2$ //$NON-NLS-3$
			throw new SQLScriptEngineException(e);
		}
	}	
	
	/**
	 * Used to create new database.
	 * 
	 * @param name
	 * @throws SQLScriptEngineException
	 */
	private void createDataBase(String name) throws SQLScriptEngineException {
		try {
			Statement createStatement = this.conn.createStatement();
			String sql = "create database " + name; //$NON-NLS-1$
			createStatement.execute(sql);
		} catch (SQLException e) {
			MessageDialog.openError(Display.getDefault().getActiveShell(), Messages.SQLScriptEngine_27 + " (" + e.getMessage() + ")",Messages.SQLScriptEngine_30 + name); //$NON-NLS-2$ //$NON-NLS-3$
			throw new SQLScriptEngineException(e);
		}
	}
}
