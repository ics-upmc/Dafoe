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
package org.dafoe.framework.databasemodeling.factory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.framework.databasemodeling.model.IColumn;
import org.dafoe.framework.databasemodeling.model.ISchema;
import org.dafoe.framework.databasemodeling.model.ITable;
import org.dafoe.framework.databasemodeling.model.impl.ConstraintForeignKeyImpl;
import org.dafoe.framework.databasemodeling.model.impl.ConstraintImpl;
import org.dafoe.framework.tools.DataBaseTools;



/**
 * The DatabaseManagerImpl Class is used to create physically a database
 * schema in the database manager system.
 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
 
public class DatabaseManagerImpl {

	/**
	 * Gets the creates the table and column query.
	 * 
	 * @param table
	 *            the table
	 * 
	 * @return the creates the table and column query
	 */
	public static String getCreateTableAndColumnQuery(ITable table) {
		String query = null;
		Set<IColumn> cols = table.getColumns();
		// if (cols.size() > 0) {// create table query only for table with at
		// least one column
		query = "CREATE TABLE " + table.getName();
		String queryColumn = " ( id INTEGER PRIMARY KEY ";
		for (IColumn c : cols) {
			queryColumn = queryColumn + " , " + c.getName() + " "
					+ c.getSqlType();
		}
		queryColumn = queryColumn + " );";

		query = query + queryColumn;
		// }

		return query;
	}

	public static List<String> getCreateForeignKeyColumnQuery(ITable table) {
		List<String> fkQuery = new ArrayList<String>();
		//Set<ConstraintImpl> cons = table.getConstraints();

		String query;
		for (ConstraintImpl currentConstraint : table.getConstraints()) {
			
			if (currentConstraint instanceof ConstraintForeignKeyImpl) {
				IColumn currentColumn= currentConstraint.getRelatedColumn();
				query = "ALTER TABLE " + table.getName() + " ADD COLUMN "
						+ currentColumn.getName()+"_id" + " "
						+ currentColumn.getSqlType() + ";";

				fkQuery.add(query);
			}

		}

		return fkQuery;
	}

	/**
	 * Gets the creates the constraint query.
	 * 
	 * @param table
	 *            the table
	 * 
	 * @return the creates the constraint query
	 */
	public static List<String> getCreateConstraintQuery(ITable table) {
		List<String> consQuery = new ArrayList<String>();
		Set<ConstraintImpl> cons = table.getConstraints();

		Iterator<ConstraintImpl> iter = cons.iterator();
		ConstraintImpl currentConstraint;

		String query;

		while (iter.hasNext()) {
			currentConstraint = iter.next();
			query = "ALTER TABLE " + table.getName() + " ADD CONSTRAINT "
					+ currentConstraint.getName() + " "
					+ currentConstraint.getExpression() + ";";

			consQuery.add(query);

		}

		return consQuery;
	}

	/**
	 * Creates the physical schema.
	 * 
	 * @param schema
	 *            the schema
	 * @throws SQLException
	 */
	public static void createPhysicalSchema(ISchema schema) throws SQLException {

		
		// direct jdbc connection
		// VT: See next with hibernate
		
		Connection con = DataBaseTools.getCurrentJDBCConnection();
		Statement stmt;
		
		stmt = con.createStatement();

		Set<ITable> tables = schema.getTables();

		// String query = null;
		// create table and column
		System.out.println("==> CREATE TABLE AND COLUMN ");
		for (ITable tab : tables) {
			System.out.println("============> CreateTableAndColumnQuery for:  "
					+ tab.getName());
			String query = getCreateTableAndColumnQuery(tab);
			if (null != query) {
				System.out.println("Query= " + query);
				try {
					// direct jdbc connection
					// VT: See next with hibernate

					 //ResultSet res;

					stmt.execute(query);


				} catch (Exception e) {
					e.printStackTrace();
					con.close();
				}

			}

		}
		
		
	/*	// add foreign key column
		System.out.println("==> ADD FK COLUMN ");
		
		for (ITable tab : tables) {
			System.out.println("==========>  CreateForeignKeyColumnQuery for:  "
					+ tab.getName());

			for (String queryFk : getCreateForeignKeyColumnQuery(tab)) {
				System.out.println("Query= " + queryFk);

				try {

					//stmt.execute(queryFk);

					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}*/
		
		

		// add constraint
		System.out.println("==>ADD CONSTRAINT ");
		for (ITable tab : tables) {
			System.out.println("==========>  CreateConstraintQuery for:  "
					+ tab.getName());

			for (String queryCons : getCreateConstraintQuery(tab)) {
				System.out.println("QueryCons= " + queryCons);

				try {

					stmt.execute(queryCons);

				} catch (Exception e) {
					e.printStackTrace();
					con.close();
				}
			}

		}
		
		
		
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

	}
	
	
	


}
