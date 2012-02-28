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

package org.dafoe.plugin.imp.skos.samples;

import java.io.File;

import org.dafoe.api.skos.vocabulary.SKOSConceptScheme;
import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSource;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.plugin.imp.skos.adaptater.Services;
import org.dafoe.plugin.imp.skos.model.ImportSKOS;

public class Test {

	
	static{
		initDataSource();
	}
	
	public static void initDataSource() {
		IDataSource con = new DataSource("jdbc:postgresql://localhost:5432/daf63", "postgres",
				"postgres");

		
		SessionFactoryImpl.openDynamicSession(con);
	}

	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//SKOSConceptScheme scheme= Services.getSkosConceptSchemeFromSkosFile(new File("d:/skos/ex1.rdf"));
		
		//SKOSConceptScheme scheme= Services.getSkosConceptSchemeFromSkosFile(new File("C:/Documents and Settings/teguiakh/Bureau/DAFOE_v05_test_Mondeca/DAFOE_garnier_champ5_skos.rdf"));
		
		
		SKOSConceptScheme scheme= Services.getSkosConceptSchemeFromSkosFile(new File("d:/skos/DAFOE_garnier_champ5_skos.rdf"));
		
		//System.out.println(scheme.getTopConcepts().length);
		System.out.println(Services.getTopConcepts(scheme).length);
		ImportSKOS imp= new ImportSKOS(scheme,"target_to");
		
		imp.run();
		
		System.out.println("end");

	}

}
