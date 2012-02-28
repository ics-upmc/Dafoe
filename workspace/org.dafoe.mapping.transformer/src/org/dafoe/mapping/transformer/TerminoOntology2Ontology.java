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
package org.dafoe.mapping.transformer;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IClassExtension;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.DatatypePropertyImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyImpl;

public class TerminoOntology2Ontology {

	private static ITerminoOntology currentTerminoOntology;
	private static IOntology currentOntology;
	
	static{
		currentTerminoOntology= ControlerFactoryImpl.getTerminoOntoControler().getCurrentTerminoOntology();
		currentOntology= ControlerFactoryImpl.getOntoControler().getCurrentOntology();
	}
	public TerminoOntology2Ontology() {
		
	}
	
	public static IClass mapTcToClass(ITerminoConcept tc) {
        //1: build class
		IClass cls= new ClassImpl();
        
        cls.setLabel(tc.getLabel());
        
        //2: add mapping 
        cls.addMappedTerminoOntoObject(tc); // or tc.addMappedOntoObject(cls);
        
        //3: insert the created OntoObject in the current ontology
        currentOntology.addOntoObject(cls);
        
      //4: save  the current ontology
        SessionFactoryImpl.getCurrentDynamicSession().saveOrUpdate(currentOntology); 
        
		return cls;
	}

	public static IObjectProperty mapTcToObjectProperty(ITerminoConcept tc) {

		 //1: build object property
		IObjectProperty oProp= new ObjectPropertyImpl();
        
		oProp.setLabel(tc.getLabel());
        
        //2: add mapping 
        oProp.addMappedTerminoOntoObject(tc); // or tc.addMappedOntoObject(oProp);
        
      //3: insert the created OntoObject in the current ontology
        currentOntology.addOntoObject(oProp);
        
      //4: save  the current ontology
        SessionFactoryImpl.getCurrentDynamicSession().saveOrUpdate(currentOntology);  
		return oProp;
	}

	public static IDatatypeProperty mapTcToDataTypeProperty(ITerminoConcept tc) {

		 //1: build datatype property
		IDatatypeProperty dProp= new DatatypePropertyImpl();
        
		dProp.setLabel(tc.getLabel());
        
        //2: add mapping 
		dProp.addMappedTerminoOntoObject(tc); // or tc.addMappedOntoObject(dProp);
        
		//3: insert the created OntoObject in the current ontology
        currentOntology.addOntoObject(dProp);
        
      //4: save  the current ontology
        SessionFactoryImpl.getCurrentDynamicSession().saveOrUpdate(currentOntology); 
        
		return dProp;
	}

	public static IClassExtension mapTcToIndividual(ITerminoConcept tc) {

		return null;
	}
}
