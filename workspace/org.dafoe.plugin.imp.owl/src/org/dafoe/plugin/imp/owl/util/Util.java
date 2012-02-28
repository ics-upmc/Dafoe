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

package org.dafoe.plugin.imp.owl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.dafoe.framework.core.ontological.model.IBasicDatatype;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.BasicDatatypeImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.semanticweb.owl.model.OWLDataType;


public class Util {

	
	@SuppressWarnings("unchecked")
	public static IBasicDatatype getBasicDatatypeFromOWLRangeInDatabase(OWLDataType range){
		IBasicDatatype basic;
		Session hSession= SessionFactoryImpl.getCurrentDynamicSession();
		List<IBasicDatatype> types= hSession.createCriteria(BasicDatatypeImpl.class).add(Restrictions.eq("label", range.getURI().toString())).list();
		
		if(types!= null){
			return types.get(0);
		}
		
		else{
			basic= new BasicDatatypeImpl();
			basic.setLabel(range.getURI().toString());
			
			Transaction tx = hSession.beginTransaction();
			hSession.save(basic);
			tx.commit();
		}
						
		return basic;
	}
	
	  public static <T> List<T> toList(Set<T> tcsArray){
			List<T> tcsList= new ArrayList<T>();
			
			for(T tc: tcsArray){
				tcsList.add(tc);
			}
			
			return tcsList;
		}

}
