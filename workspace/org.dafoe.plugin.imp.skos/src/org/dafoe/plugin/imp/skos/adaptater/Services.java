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

package org.dafoe.plugin.imp.skos.adaptater;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dafoe.api.skos.vocabulary.SKOSConcept;
import org.dafoe.api.skos.vocabulary.SKOSConceptScheme;
import org.dafoe.api.skos.vocabulary.SKOSModel;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotationType;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_ANNOTATION_TYPE_ENUM;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntoAnnotationTypeImpl;
import org.dafoe.plugin.imp.skos.util.SKOSAnnotationTypeENUM;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class Services {

	
	
	public static Session getDafoeSession() {
		//initDataSource(); // uncomment for test
		// VT: load a dynamic session per database
		return SessionFactoryImpl.getCurrentDynamicSession();
	}
	

	
	// we assume that the file may contains only one scheme
	// in case of many scheme, de first one is selected by default
	public static SKOSConceptScheme getSkosConceptSchemeFromSkosFile(File skosFile){
		SKOSModel skosModel= SKOSModel.newInstance();
		skosModel.read(skosFile);
		SKOSConceptScheme[] schemes = skosModel.getConceptSchemes();
		if(schemes!= null){
			//System.out.println(schemes.length);
			//System.out.println("size= "+(schemes[0].getTopConcepts()).length);
			return schemes[0];
		}
		return null;
	}
	
	// we assume that the file may contains only one scheme
	// in case of many scheme, de first one is selected by default
	public static SKOSConceptScheme getSkosConceptSchemeFromSkosFile(SKOSModel skosModel,File skosFile){
		skosModel.read(skosFile);
		SKOSConceptScheme[] schemes = skosModel.getConceptSchemes();
		if(schemes!= null){
			return schemes[0];
		}
		return null;
	}
	
	
	
	public static  List<File> getAllFiles(File directory){
		List<File> files= new ArrayList<File>();
		
		if(directory.isDirectory()){
			for (File f: directory.listFiles()){
				if(f.isFile()){
					files.add(f);
				}
			}
		}
		
		//System.out.println("size files= "+files.size()); //$NON-NLS-1$
		return files;
	}
	
	private static Map<SKOSAnnotationTypeENUM,TERMINO_ONTO_ANNOTATION_TYPE_ENUM> mappedAnnotationType= new HashMap<SKOSAnnotationTypeENUM, TERMINO_ONTO_ANNOTATION_TYPE_ENUM>();
	
	
	private static TERMINO_ONTO_ANNOTATION_TYPE_ENUM[] dafoeToAnnotTypes;
	private static SKOSAnnotationTypeENUM[] skosAnnoTypes;
	//public static void initAnnotMapping()
	static{
		
		// init toAnnotTypes
	  
		dafoeToAnnotTypes= TERMINO_ONTO_ANNOTATION_TYPE_ENUM.values();
	 
		//init skos annotaTypes
		skosAnnoTypes= SKOSAnnotationTypeENUM.values();
		
	  //init mapping
	 
	  for(int i=0; i<dafoeToAnnotTypes.length;i++ ){
		  mappedAnnotationType.put(skosAnnoTypes[i],dafoeToAnnotTypes[i] );
		}

	}
	
	public static ITerminoOntoAnnotationType createAnnotType(String label, String dataType){
		ITerminoOntoAnnotationType annotType= new TerminoOntoAnnotationTypeImpl();
		annotType.setLabel(label);
		annotType.setType(dataType);
		
		return annotType;
	}
	
   public static Map<SKOSAnnotationTypeENUM, TERMINO_ONTO_ANNOTATION_TYPE_ENUM> getMappedAnnotationType() {
	return mappedAnnotationType;
}
   
   
   public static  SKOSConcept[] getTopConcepts(SKOSConceptScheme scheme){
	   List<SKOSConcept> topTcs= new ArrayList<SKOSConcept>();
	   
	   SKOSConcept[] allConcepts= SKOSModel.getConcepts();
	   for(SKOSConcept c: allConcepts){
		   if(c.getBroaders().length<=0){
			   topTcs.add(c);
		   }
	   }
	  // System.out.println("size ="+topTcs.size());
	   
	   return topTcs.toArray(new SKOSConcept[0]);
   }
   
   public static  SKOSConcept[] getTopConcepts1(SKOSConceptScheme scheme){
	   SKOSConcept[] topTcs = getTopConcepts(scheme);
	   
	 
	   System.out.println("size ="+topTcs.length);
	   
	   return topTcs;
   }
   
   //retrieve equivalent toAnnotType form database
   public static ITerminoOntoAnnotationType getTerminoOntoAnnotationTypeFromDatabase(TERMINO_ONTO_ANNOTATION_TYPE_ENUM toAnnotTypeEnum){
	   Session hSession= SessionFactoryImpl.getCurrentDynamicSession();
	   
	   Transaction tx= hSession.beginTransaction();
	  
	   List<ITerminoOntoAnnotationType> types= hSession.createCriteria(TerminoOntoAnnotationTypeImpl.class).add(Restrictions.eq("Label", toAnnotTypeEnum.getLabel())).list();
	   tx.commit();
	   
	   if(types!= null){
		   return types.get(0);
	   }
	   return null;
   }
}
