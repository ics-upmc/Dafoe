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

package org.dafoe.plugin.imp.owl.test;

import java.io.File;
import java.util.List;

import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSource;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.OntologyImpl;
import org.dafoe.plugin.imp.owl.model.ImportOWL;
import org.hibernate.Session;

public class ImportOWLTest {
	
	public static Session getDafoeSession() {
		IDataSource con = new DataSource("jdbc:postgresql://localhost:5432/import", "postgres", "psql");

		SessionFactoryImpl.openDynamicSession(con);

		return SessionFactoryImpl.getCurrentDynamicSession();
	}
	
	public static List<IOntology> getAllOntologies() {
		Session hSession = getDafoeSession();
		List<IOntology> ontos = hSession.createCriteria(OntologyImpl.class).list();		
		return ontos;
	}
	
	public void testImportOWL() {				
		File ontoFile = new File("D:/Mes documents/Projet Dafoe/Import OWL/OntolUrgences_20091207.owl");
		final IOntology ontology = this.getAllOntologies().get(0);
		ImportOWL newOwlReader2 = new ImportOWL(ontoFile, ontology);
		newOwlReader2.run();
	}
	
	public static void main(String[] args) {
		final ImportOWLTest importOWLTest = new ImportOWLTest();
		importOWLTest.testImportOWL();
	}
}
