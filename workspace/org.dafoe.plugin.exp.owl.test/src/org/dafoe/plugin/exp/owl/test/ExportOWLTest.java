package org.dafoe.plugin.exp.owl.test;

import java.io.File;
import java.util.List;

import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSource;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.OntologyImpl;
import org.dafoe.plugin.exp.owl.model.ExportOWL;
import org.hibernate.Session;

public class ExportOWLTest {
	
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
	
	public void testImportOWL() throws Exception {				
		ExportOWL myOwlWriter2 = new ExportOWL();
		myOwlWriter2.export(new File("D:/Mes documents/Projet Dafoe/Import OWL/NewOntolUrgences_20091207.owl"), this.getAllOntologies().get(0));
	}
	
	public static void main(String[] args) throws Exception {
		final ExportOWLTest importOWLTest = new ExportOWLTest();
		importOWLTest.testImportOWL();
	}
}
