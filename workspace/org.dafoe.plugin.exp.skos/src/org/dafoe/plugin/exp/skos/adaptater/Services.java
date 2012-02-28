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

package org.dafoe.plugin.exp.skos.adaptater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotationType;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_ANNOTATION_TYPE_ENUM;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntoAnnotationTypeImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntologyImpl;
import org.dafoe.plugin.exp.skos.util.SKOS_ANNOTATION_TYPE_ENUM;
import org.hibernate.Session;

public class Services {

	
	@SuppressWarnings("unchecked")
	public static List<ITerminoOntology> getAllTerminoOntologies(){
		List<ITerminoOntology> tos= getDafoeSession().createCriteria(TerminoOntologyImpl.class).list();
		
		if(tos == null){
			return new ArrayList<ITerminoOntology>();
		}
		return tos;
	}
	
	
	public static Session getDafoeSession() {
		//initDataSource();
		// VT: load a dynamic session per database
		return SessionFactoryImpl.getCurrentDynamicSession();
	}
	
	private static Map<TERMINO_ONTO_ANNOTATION_TYPE_ENUM, SKOS_ANNOTATION_TYPE_ENUM> mappedAnnotationType= new HashMap<TERMINO_ONTO_ANNOTATION_TYPE_ENUM, SKOS_ANNOTATION_TYPE_ENUM>();
	
	private static TERMINO_ONTO_ANNOTATION_TYPE_ENUM[] dafoeToAnnotTypes;
	private static SKOS_ANNOTATION_TYPE_ENUM[] skosAnnoTypes;
	//public static void initAnnotMapping()
	static{
		
		// init toAnnotTypes
	  
		dafoeToAnnotTypes= TERMINO_ONTO_ANNOTATION_TYPE_ENUM.values();
	 
		//init skos annotaTypes
		skosAnnoTypes= SKOS_ANNOTATION_TYPE_ENUM.values();
		
	  //init mapping
	 
	  for(int i=0; i<skosAnnoTypes.length;i++ ){
		  mappedAnnotationType.put(dafoeToAnnotTypes[i],skosAnnoTypes[i] );
		}

	}
	
	public static ITerminoOntoAnnotationType createAnnotType(String label, String dataType){
		ITerminoOntoAnnotationType annotType= new TerminoOntoAnnotationTypeImpl();
		annotType.setLabel(label);
		annotType.setType(dataType);
		
		return annotType;
	}
	
   public static Map<TERMINO_ONTO_ANNOTATION_TYPE_ENUM, SKOS_ANNOTATION_TYPE_ENUM> getMappedAnnotationType() {
	return mappedAnnotationType;
}
   
   //retrieve equivalent toAnnotType form database
   public static TERMINO_ONTO_ANNOTATION_TYPE_ENUM getTerminoOntoAnnotationTypeFromDatabase(ITerminoOntoAnnotationType toAnnotType){
	  
	   boolean isOther= true;
	   
	   if(toAnnotType.getLabel().equals(TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_PREF_LABEL.getLabel())){
		   isOther= false;
		   return TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_PREF_LABEL;
	   }
	   
	   if(toAnnotType.getLabel().equals(TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_CHANGE_NOTE.getLabel())){
		   isOther= false;
		   return TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_CHANGE_NOTE;
	   }
	   
	   if(toAnnotType.getLabel().equals(TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_ALT_LABEL.getLabel())){
		   isOther= false;
		   return TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_ALT_LABEL;
	   }
	   
	   if(toAnnotType.getLabel().equals(TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_DEFINITION.getLabel())){
		   isOther= false;
		   return TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_DEFINITION;
	   }
	   
	   if(toAnnotType.getLabel().equals(TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_EDITORIAL_NOTE.getLabel())){
		   return TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_EDITORIAL_NOTE;
	   }
	   
	   if(toAnnotType.getLabel().equals(TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_EXAMPLE.getLabel())){
		   isOther= false;
		   return TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_EXAMPLE;
	   }
	   
	   if(toAnnotType.getLabel().equals(TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_HIDDEN_LABEL.getLabel())){
		   isOther= false;
		   return TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_HIDDEN_LABEL;
	   }
	   if(toAnnotType.getLabel().equals(TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_HISTORY_NOTE.getLabel())){
		   isOther= false;
		   return TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_HISTORY_NOTE;
	   }
	   if(toAnnotType.getLabel().equals(TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_NOTE.getLabel())){
		   isOther= false;
		   return TERMINO_ONTO_ANNOTATION_TYPE_ENUM.DAFOE_TERMINO_ONTO_NOTE;
	   }
	   if(isOther){
		   // TO COMPLETE
	   }
	   return null;
   }
}
