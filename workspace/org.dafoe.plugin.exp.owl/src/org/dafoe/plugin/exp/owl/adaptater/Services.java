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

package org.dafoe.plugin.exp.owl.adaptater;

import java.util.List;

import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.OntologyImpl;
import org.hibernate.Session;

public class Services {

	
	public static List<IOntology> getAllOntologies(){
		List<IOntology> ontos= getDafoeSession().createCriteria(OntologyImpl.class).list();
		
		return ontos;
	}
	
	
	static Session getDafoeSession() {
		//initDataSource();
		// VT: load a dynamic session per database
		return SessionFactoryImpl.getCurrentDynamicSession();
	}
}
