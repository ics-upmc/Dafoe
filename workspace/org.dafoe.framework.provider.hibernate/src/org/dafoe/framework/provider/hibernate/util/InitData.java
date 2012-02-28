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
package org.dafoe.framework.provider.hibernate.util;


import org.dafoe.framework.core.ontological.model.IBasicDatatype;
import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSource;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.BasicDatatypeImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class InitData {

	static {
		IDataSource con = new DataSource("jdbc:postgresql://localhost:5432/DAFOE2", "postgres",
				"postgres");

		SessionFactoryImpl.openDynamicSession(con);
	}

	private String[] basicDataTypeArray = { "short", "unsignedShort",
			"integer", "positiveInteger", "negativeInteger",
			"nonPositiveInteger", "nonNegativeInteger", "int", "unsignedInt",
			"long", "unsignedLong", "decimal", "float", "double", "string",
			"normalizedString", "token", "language", "NMTOKEN", "Name",
			"NCName", "time", "date", "datetime", "gYearMonth", "gMonthDay",
			"gDay", "gMonth", "boolean", "byte", "unsignedByte", "hexBinary",
			"anyURI" };

	/**
	 * Gets the dafoe session.
	 * 
	 * @return the dafoe session
	 */
	static Session getDafoeSession() {

		// VT: load a dynamic session per database
		return SessionFactoryImpl.getCurrentDynamicSession();
	}

	public static void initBasicDataType(String[] allBasicDataType) {

		Session ontoSession = getDafoeSession();

		

		Transaction tx= ontoSession.beginTransaction();
		
		for (String bTypeLabel : allBasicDataType) {
			IBasicDatatype basic = new BasicDatatypeImpl();
			basic.setLabel(bTypeLabel);
			
			ontoSession.saveOrUpdate(basic);
			
		}
		tx.commit();
	}



}
