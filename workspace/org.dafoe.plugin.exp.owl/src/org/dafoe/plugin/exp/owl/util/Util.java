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

package org.dafoe.plugin.exp.owl.util;

import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl;


public class Util {
	public static String ONTO_ANNOTATION_PREFIX= "http://www.dafoe.org/";
	//returns the local name of the class of an object with the suffix "Impl"
	public static String normaliseAnnotationFromClass(Object o){
		String cls= o.getClass().getName();
		
		return ONTO_ANNOTATION_PREFIX+cls.substring(cls.lastIndexOf('.')+1, cls.lastIndexOf('I'));
		
	}
	
	
	public static String   getLanguageFromLocalizedText(String localizedText){
		
		
		int idx= localizedText.indexOf("@");
		String label= localizedText.substring(0, idx);
		String language= localizedText.substring(idx+1);
		
		return null;
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ITerminoConcept tc= new TerminoConceptImpl();
		System.out.println("norm= "+normaliseAnnotationFromClass(tc));

	}
}
