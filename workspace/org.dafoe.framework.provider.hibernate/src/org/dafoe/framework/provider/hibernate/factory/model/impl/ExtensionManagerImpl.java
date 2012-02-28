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
package org.dafoe.framework.provider.hibernate.factory.model.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IClassExtension;
import org.dafoe.framework.core.ontological.model.IProperty;

import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassExtension;
import org.dafoe.framework.tools.DataBaseTools;
import org.dafoe.framework.tools.StringFormatterTools;

// TODO: Auto-generated Javadoc
/**
 * The Class ExtensionManagerImpl.
 * 
 * 
 */
public class ExtensionManagerImpl {

	/**
	 * Gets the all class extension.
	 * 
	 * @return the all class extension
	 */
	public static Set<IClassExtension> getAllClassExtension() {

		return null;
	}

	public static Connection getJdbcConnection() {

		return DataBaseTools.getCurrentJDBCConnection();
	}

	public static IClassExtension loadClassExtension(IClass cls,
			Integer classExtId) {
		String query = " SELECT * FROM "
				+ StringFormatterTools.normalizeSqlTableNameFromClassLabel(cls
						.getLabel())
				+ " WHERE "
				+ StringFormatterTools.normalizeSqlTableNameFromClassLabel(cls
						.getLabel()) + ".id = " + classExtId + " ;";

		Connection con = getJdbcConnection();

		Statement stmt;
		ResultSet rs;

		IClassExtension clsExt = null;

		try {
			// stmt = con.createStatement();

			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			System.out.println(query);
			stmt.execute(query);
			rs = stmt.getResultSet();
			int row = 1; // resultset most have no more than one result

			rs.next();// absolute(row);

			clsExt = new ClassExtension(rs, row, cls.getProperties(), cls);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return clsExt;

	}


	public static Set<IClassExtension> getAllClassExtension(IClass cls,
			Set<IProperty> props) {
		Set<IClassExtension> exts = new HashSet<IClassExtension>();

		Connection con = getJdbcConnection();

		Statement stmt;

		ResultSet rs;
		// default instance query, for primitive classes
		String sqlInstanceQuery = "SELECT * FROM "
				+ StringFormatterTools.normalizeSqlTableNameFromClassLabel(cls
						.getLabel()) + " ;";

		if (cls.getLogicalDefinition() != null) {// cls is a defined class
			sqlInstanceQuery = cls.getSqlInstanceQuery();
		}
		try {
			// stmt = con.createStatement();

			stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			stmt.execute(sqlInstanceQuery);
			rs = stmt.getResultSet();
			int idx = 1;

			while (rs.next()) {

				exts.add(new ClassExtension(rs, idx, props, cls));

				idx++;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return exts;
	}

	/**
	 * Delete class extension.
	 * 
	 * @param clsExt
	 *            the cls ext
	 * 
	 * @return true, if successful
	 */
	public static boolean deleteClassExtension(IClassExtension clsExt) {
		boolean isDeleted = false;

		Connection con = getJdbcConnection();

		Statement stmt;

		ResultSet rs;

		try {
			stmt = con.createStatement();

			stmt.execute("DELETE FROM "
					+ StringFormatterTools
							.normalizeSqlTableNameFromClassLabel(clsExt
									.getPreferedClass().getLabel())
					+ " WHERE "
					+ StringFormatterTools
							.normalizeSqlTableNameFromClassLabel(clsExt
									.getPreferedClass().getLabel()) + ".id= "
					+ clsExt.getId());

			rs = stmt.getResultSet();
			isDeleted = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return isDeleted;
	}

	public static boolean deleteClassExtension(IClass cls, Integer clsExtId) {
		boolean isDeleted = false;

		Connection con = getJdbcConnection();

		Statement stmt;

		ResultSet rs;

		try {
			stmt = con.createStatement();

			stmt
					.execute("DELETE FROM "
							+ StringFormatterTools
									.normalizeSqlTableNameFromClassLabel(cls
											.getLabel())
							+ " WHERE "
							+ StringFormatterTools
									.normalizeSqlTableNameFromClassLabel(cls
											.getLabel()) + ".id= " + clsExtId);

			rs = stmt.getResultSet();

			isDeleted = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return isDeleted;
	}

	/**
	 * Save class extension.
	 * 
	 * @param clsExt
	 *            the cls ext
	 * 
	 * @return true, if successful
	 */
	public static boolean saveClassExtension(IClassExtension clsExt) {

		boolean save = false;

		Connection con = getJdbcConnection();

		Statement stmt;

		ResultSet rs;

		try {
			stmt = con.createStatement();

			save = stmt.execute(clsExt.getInsertionQuery());

			rs = stmt.getResultSet();

			save = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return save;
	}

	/**
	 * Update class extension.
	 * 
	 * @param clsExt
	 *            the cls ext
	 * 
	 * @return true, if successful
	 */
	/*
	 * public static boolean updateClassExtension(IClassExtension clsExt) {
	 * 
	 * boolean update = false;
	 * 
	 * return update; }
	 */

}
