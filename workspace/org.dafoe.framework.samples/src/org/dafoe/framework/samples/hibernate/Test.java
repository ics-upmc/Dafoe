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
package org.dafoe.framework.samples.hibernate;



import java.util.ArrayList;
import java.util.List;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSource;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TerminologyImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * The Class Test.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class Test {

	
	
	
	@SuppressWarnings("unchecked")
	public static List<ITerm> getTermsFromLabel(String label){
		List<ITerm> terms= new ArrayList<ITerm>();
		
		try {
			terms = SessionFactoryImpl.getCurrentDynamicSession().createSQLQuery("SELECT m21_term.* "+
					" FROM m21_term WHERE m21_term.label = "+label+" ORDER BY label").addEntity(TermImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Terms size= "+terms.size());
		return terms;
	}
	/**
	 * Switch database.
	 * 
	 * @param dbConParam the db con param
	 */
	public static void switchDatabase(IDataSource dbConParam){
		Session hSession= SessionFactoryImpl.openDynamicSession(dbConParam);
		
		Transaction tx= hSession.beginTransaction();
		
		ITerminology t= new TerminologyImpl();
		t.setName("myTermino");
		t.setName("myNamespace");
		t.setLanguage("myLanguage");
		
		hSession.saveOrUpdate(t);
		tx.commit();
		
	}
	
	public static void switchSessionRunning(){
		
		IDataSource con= new DataSource("jdbc:postgresql://localhost:5432/DAFOE2","postgres","postgres");
		
		//DataSourceManagerImpl dtsMgr= new DataSourceManagerImpl(con);
		
         SessionFactoryImpl.openDynamicSession(con);
         
         Session hSession= SessionFactoryImpl.getCurrentDynamicSession();
	
         Transaction tx= hSession.beginTransaction();
		
         ITerminology t= new TerminologyImpl();
 		t.setName("myTermino");
 		t.setName("myNamespace");
 		t.setLanguage("myLanguage");
 		
 		hSession.saveOrUpdate(t);
		
		tx.commit();
		
		//new datasource
		
		IDataSource con1= new DataSource("jdbc:postgresql://localhost:5432/DAFOE","postgres","postgres");

		//dtsMgr.setCurrentDataSource(con1);
		
		SessionFactoryImpl.setCurrentDataSource(con1);
		
		hSession.clear();
		
		hSession= SessionFactoryImpl.getCurrentDynamicSession();
		
        Transaction tx1= hSession.beginTransaction();
		
        ITerminology termi= new TerminologyImpl();
        termi.setName("myTermino");
        termi.setName("myNamespace");
        termi.setLanguage("myLanguage");
		
		hSession.saveOrUpdate(termi);
		
		tx1.commit();
	}
	
	/**
	 * The main method.
	 * 
	 * @param args the args
	 */
	public static void main(String[] args) {
		//DataSource con= new DataSource("jdbc:postgresql://localhost:5432/DAFOE","postgres","postgres");
		//switchDatabase("DAFOE2");
		//switchDatabase(con);
		
		switchSessionRunning();
		

	}

}
