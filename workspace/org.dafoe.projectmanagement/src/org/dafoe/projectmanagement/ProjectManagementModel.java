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

public class ProjectManagementModel {
	
	private String connectionName;
	
	private String serverName;
	
	private String portName;
	
	private String sidName;
	
	private String userName;
	
	private String passwordName;
	
	private boolean isExists;
	
	public ProjectManagementModel(boolean isExists, String connectionName, String serverName, String portName, String sidName, String userName, String passwordName) {
		this.isExists = isExists;
		this.connectionName = connectionName;
		this.serverName = serverName;
		this.portName = portName;
		this.sidName = sidName;
		this.userName = userName;
		this.passwordName = passwordName;
	}
	
	public ProjectManagementModel() {		
	}

	public boolean isExists() {
		return isExists;
	}

	public void setExists(boolean isExists) {
		this.isExists = isExists;
	}

	public String getConnectionName() {
		return connectionName;
	}

	public void setConnectionName(String connectionName) {
		if (connectionName != null) {
			this.connectionName = connectionName.toLowerCase();
		}
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		if (serverName != null) {
			this.serverName = serverName.toLowerCase();
		}
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		if (portName != null) {
			this.portName = portName.toLowerCase();
		}
	}

	public String getSidName() {
		return sidName;
	}

	public void setSidName(String sidName) {
		if (sidName != null) {
			this.sidName = sidName.toLowerCase();
		}
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		if (userName != null) {
			this.userName = userName.toLowerCase();
		}
	}

	public String getPasswordName() {
		return passwordName;
	}

	public void setPasswordName(String passwordName) {
		if (passwordName != null) {
			this.passwordName = passwordName.toLowerCase();
		}
	}
	
	public boolean isValid() {
		return this.connectionName != null && !this.connectionName.equals("") && //$NON-NLS-1$
			this.passwordName != null && !this.passwordName.equals("") && //$NON-NLS-1$
			this.serverName != null && !this.serverName.equals("") && //$NON-NLS-1$
			this.portName != null && !this.portName.equals("") && //$NON-NLS-1$
			this.sidName != null && !this.sidName.equals("") && //$NON-NLS-1$
			this.userName != null && !this.userName.equals("");				 //$NON-NLS-1$
	}
	
	public String getDbUrl() {
		return "jdbc:postgresql://" + this.getServerName() + ":" + this.getPortName() + "/" + this.sidName; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	@Override
	public String toString() {
		return this.connectionName + "(" + this.serverName + "," + this.sidName + "," + this.portName + "," + this.userName + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
	}
}
