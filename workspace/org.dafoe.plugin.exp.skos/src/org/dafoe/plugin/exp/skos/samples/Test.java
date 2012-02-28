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

package org.dafoe.plugin.exp.skos.samples;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.dafoe.framework.builder.model.SerializableObjectFactory;
import org.dafoe.framework.core.mock.MockBinaryTCRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotationType;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSource;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.mock.BinaryTCRelation;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptAnnotationImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntoAnnotationTypeImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntologyImpl;
import org.dafoe.plugin.exp.skos.model.SKOSExporter;
import org.dafoe.plugin.exp.skos.model.SKOSOutputFormat;
import org.hibernate.Session;

public class Test {

	static {
		initDataSource();
	}
	
	
	public static void initDataSource() {
		IDataSource con = new DataSource("jdbc:postgresql://localhost:5432/daf64", "postgres",
				"postgres");

		SessionFactoryImpl.openDynamicSession(con);
	}

	public static Session getDafoeSession() {
		//initDataSource();
		// VT: load a dynamic session per database
		return SessionFactoryImpl.getCurrentDynamicSession();
	}

	
	public static  void initTerminoOntoAnnotationType(Session hSession){
		 
		/*
		 * Transaction tx= hSession.beginTransaction();
		
		ITerminoOntoAnnotationType t1= Util.createAnnotType(SKOSAnnotationTypeENUM.SKOS_PREF_LABEL.getLabel(), "STRING");
		  
		ITerminoOntoAnnotationType t2= Util.createAnnotType(SKOSAnnotationTypeENUM.SKOS_ALT_LABEL.getLabel(), "STRING");

		ITerminoOntoAnnotationType t3= Util.createAnnotType(SKOSAnnotationTypeENUM.SKOS_HIDDEN_LABEL.getLabel(), "STRING");
		
		hSession.saveOrUpdate(t1);
		hSession.saveOrUpdate(t2);
		hSession.saveOrUpdate(t3);
		
		 tx.commit();
		*/
	}
	
	
	  // create a mock TerminoOntology
	   
	   public static ITerminoOntology createMockTerminoOnto(Session hSession){
		   		   
		   //Transaction tx= hSession.beginTransaction();
		   
		   List<ITerminoOntoAnnotationType> toAnnotTypes= hSession.createCriteria(TerminoOntoAnnotationTypeImpl.class).list();
		   
		   ITerminoOntology to= new TerminoOntologyImpl();
		   to.setLanguage("French");
		   to.setName("Urgence");
		   to.setNameSpace("http://dafoe/to/Urgence");
		   
		   //******************create tc *******************
		   ITerminoConcept tc1= new TerminoConceptImpl();
		   tc1.setLabel("TC1");
		   tc1.setState(TERMINO_ONTO_OBJECT_STATE.STUDIED);
		   tc1.setDefinition("tc1 def");
		   
		   ITerminoOntoAnnotation annot1= new TerminoConceptAnnotationImpl();
		   annot1.setTerminoOntoAnnotationType(toAnnotTypes.get(0));
		   annot1.setValue("annot1");
		   
		   hSession.saveOrUpdate(annot1);
		   
		   tc1.addAnnotation(annot1);
		   		   
		   //children
		   //
		   ITerminoConcept tc11= new TerminoConceptImpl();
		   tc11.setLabel("TC11");
		   tc11.setState(TERMINO_ONTO_OBJECT_STATE.STUDIED);
		   tc11.setDefinition("tc11 def");
		   //
		   ITerminoConcept tc12= new TerminoConceptImpl();
		   tc12.setLabel("TC12");
		   tc12.setState(TERMINO_ONTO_OBJECT_STATE.STUDIED);
		   tc12.setDefinition("tc12 def");
		    
		   tc1.addChild(tc11);
		   tc1.addChild(tc12);
		   
		   to.addTerminoOntoObject(tc1);
		   to.addTerminoOntoObject(tc11);
		   to.addTerminoOntoObject(tc12);
		   
		   //to.addTopTerminoConcept(tc1);
		   
		 //******************create tc *******************
		   ITerminoConcept tc2= new TerminoConceptImpl();
		   tc2.setLabel("TC2");
		   tc2.setState(TERMINO_ONTO_OBJECT_STATE.STUDIED);
		   tc2.setDefinition("tc2 def");
		   
		   to.addTerminoOntoObject(tc2);
		   		   
		   //to.addTopTerminoConcept(tc2);
		   
		   
		 //******************create tc *******************
		   ITerminoConcept tc3= new TerminoConceptImpl();
		   tc3.setLabel("TC3");
		   tc3.setState(TERMINO_ONTO_OBJECT_STATE.STUDIED);
		   tc3.setDefinition("tc3 def");
		   
		   
		 //children
		   //
		   ITerminoConcept tc31= new TerminoConceptImpl();
		   tc31.setLabel("TC31");
		   tc31.setState(TERMINO_ONTO_OBJECT_STATE.STUDIED);
		   tc31.setDefinition("tc31 def");
		   //
		   ITerminoConcept tc32= new TerminoConceptImpl();
		   tc32.setLabel("TC32");
		   tc32.setState(TERMINO_ONTO_OBJECT_STATE.STUDIED);
		   tc32.setDefinition("tc32 def");
		   
		   // child child 
		   ITerminoConcept tc321= new TerminoConceptImpl();
		   tc321.setLabel("TC321");
		   tc321.setState(TERMINO_ONTO_OBJECT_STATE.STUDIED);
		   tc321.setDefinition("tc321 def");
		   
		   
		   tc3.addChild(tc31);
		   tc3.addChild(tc32);
		   
		   tc31.addChild(tc321);
		   
		   		   		   
		   to.addTerminoOntoObject(tc3);
		   to.addTerminoOntoObject(tc31);
		   to.addTerminoOntoObject(tc32);
		   to.addTerminoOntoObject(tc321);
		   		   
		   hSession.saveOrUpdate(to);
		   
		   //to.addTopTerminoConcept(tc3); 
		   
		   //**************** create binary tcr **********************
		   /*
		   MockBinaryTCRelation bTCR1= new BinaryTCRelation();
		   bTCR1.setStatus(ITerminoOntoObjectStatus.STUDIED);
		   bTCR1.setTc1(tc1);
		   bTCR1.setTc2(tc2);
		   
		   ITerminoConceptRelation r1= SerializableObjectFactory.createTCR(bTCR1, hSession);
		   
		   to.addTerminoOntoObject(r1);
		   */
		   //to.addBinaryTerminoConceptRelation(bTCR1);
		  
		 //**************** create binary tcr **********************
		   
		  
		   MockBinaryTCRelation bTCR2= new BinaryTCRelation();
		   bTCR2.setState(TERMINO_ONTO_OBJECT_STATE.STUDIED);
		   bTCR2.setTc1(tc31);
		   bTCR2.setTc2(tc32);
		   
		   ITerminoConceptRelation r2= SerializableObjectFactory.createTCR(bTCR2, hSession);
		   
		   to.addTerminoOntoObject(r2);
		   
		
		   //to.addTerminoOntoObject(SerializableObjectFactory.createTCR(bTCR2, hSession));
		   //to.addBinaryTerminoConceptRelation(bTCR2);
		   	  
		   hSession.saveOrUpdate(to);
		   
		   //tx.commit();
		   
		   return to;
	   }
	   
	   
	   public static void exportInSKOS(Session hSession){
		   //ITerminoOntology mockTO= createMockTerminoOnto(hSession);
		  
		   List<ITerminoOntology> tos= hSession.createCriteria(TerminoOntologyImpl.class).list();
		   
		   //for (ITerminoOntology mockTO: tos){
		   ITerminoOntology mockTO= tos.get(1);
		   System.out.println("******************* "+mockTO.getName()+ "********************");
		   
           SKOSExporter skosLoader= new SKOSExporter(mockTO);
           
           skosLoader.run();
		
		    //VT
		    File f = new File("d:/skos/garnier_skos3.rdf");
		    skosLoader.writeFile(f, SKOSOutputFormat.RDF_XML);
		    		    
		    //skosLoader.writeOutput(System.out, SKOSOutputFormat.RDF_XML);
		    
		  // } 
	   }
	   
	   

		public static List<ITerminoOntology> getAllTerminoOntologies(){
			List<ITerminoOntology> tos= getDafoeSession().createCriteria(TerminoOntologyImpl.class).list();
			
			if(tos == null){
				tos= new ArrayList<ITerminoOntology>();
			}
			
			System.out.println("size= "+tos.size());
			return tos;
		}
		
		
	   public static void main(String[] args) {
        
         
         //initTerminoOntoAnnotationType(getDafoeSession());
		   
		 exportInSKOS(getDafoeSession());//
		   
		   //getAllTerminoOntologies(); OK
		   
		  
		}
	   
}
