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
package org.dafoe.framework.databasemodeling.samples;

import java.util.List;

import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.databasemodeling.Ontology2DatabaseTranslator;
import org.dafoe.framework.databasemodeling.model.ISchema;
import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSource;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.OntologyImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * 
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TestOntology2DbTranslator {

	
	/**
	 * Inits the data source.
	 */
	static void initDataSource() {
		IDataSource con = new DataSource("jdbc:postgresql://localhost:5432/DAFOE2", "postgres",
				"postgres");

		SessionFactoryImpl.openDynamicSession(con);
	}
	

	/**
	 * Gets the dafoe session.
	 * 
	 * @return the dafoe session
	 */
	static Session getDafoeSession() {

		// VT: load a dynamic session per database
		return SessionFactoryImpl.getCurrentDynamicSession();
	}
	
	/**
	 * TestMappingManager map schema.
	 */
	@SuppressWarnings("unchecked")
	private static void testMapSchema(){
		
		initDataSource();

		Session ontoSession = getDafoeSession();

		List<IOntology> ontos = ontoSession.createCriteria(OntologyImpl.class)
				.list();

		System.out.println("SIZE= " + ontos.size());

		ISchema schema = Ontology2DatabaseTranslator.mapping(ontos.get(0));

		ontoSession.close();

		Session schemaSession = org.dafoe.framework.databasemodeling.factory.SessionFactoryImpl
				.openSession();
		Transaction tx = schemaSession.beginTransaction();

		schemaSession.saveOrUpdate(schema);

		tx.commit();
		schemaSession.close();
	}

	/**
	 * The main method.
	 * 
	 * @param args the args
	 */
	public static void main(String[] args) {
		

		testMapSchema();
	}
}
