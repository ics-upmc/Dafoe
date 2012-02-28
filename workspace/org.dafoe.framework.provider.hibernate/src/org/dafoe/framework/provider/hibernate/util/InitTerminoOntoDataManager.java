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

import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotationType;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_ANNOTATION_TYPE_ENUM;
import org.dafoe.framework.core.util.IInitTerminoOntoDataManager;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntoAnnotationTypeImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class InitTerminoOntoDataManager implements IInitTerminoOntoDataManager{

	public InitTerminoOntoDataManager() {
		
	}
	
	
	public void initTerminoOntoAnnotationType() {
		Session hSession= SessionFactoryImpl.getCurrentDynamicSession();
		
		Transaction tx= hSession.beginTransaction();
		
		for(TERMINO_ONTO_ANNOTATION_TYPE_ENUM toAnnotTEnum:TERMINO_ONTO_ANNOTATION_TYPE_ENUM.values()){
			ITerminoOntoAnnotationType toAnnotT= new TerminoOntoAnnotationTypeImpl();
			toAnnotT.setLabel(toAnnotTEnum.getLabel());
			toAnnotT.setType(toAnnotTEnum.getType());
			
			hSession.save(toAnnotT);
			
		}
		
		tx.commit();
		
	}

}
