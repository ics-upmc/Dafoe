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

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermOccurrence;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptOccurrence;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelationMember;
import org.dafoe.framework.core.terminoontological.model.ITerminoProperty;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_ANNOTATION_TYPE_ENUM;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSource;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermOccurrenceImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TcDataTypeImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptOccurrenceImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoPropertyImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TypeRelationTcImpl;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;



// TODO: Auto-generated Javadoc
/**
 * The Class TerminoOntologicalSamples provides examples of how to manage data in the terminoontological layer.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TerminoOntologicalSamples {

	
	static Session getDafoeSession()  {
		initDataSource();		
		//VT: load a dynamic session per database
		return SessionFactoryImpl.getCurrentDynamicSession();
	}
	
	
	/**
	 * Creates the termino concept sample.
	 */
	public void createTerminoConceptSample(){
		
	}
	
	public static void initDataSource(){
		IDataSource con= new DataSource("jdbc:postgresql://localhost:5432/daf2","postgres","postgres");
		
        SessionFactoryImpl.openDynamicSession(con);
	}
	
	
		
	
	/**
	 * Delete termino concept sample.
	 */
	public void deleteTerminoConceptSample(){
		
	}
	
	
	/**
	 * Load termino concept sample.
	 * 
	 * @return the i termino concept
	 */
	public ITerminoConcept loadTerminoConceptSample(){
		
		return null;
	}
	
	
	/**
	 * Find all termino concept.
	 * 
	 * @return the list< i termino concept>
	 */
	@SuppressWarnings("unchecked")
	public static List<ITerminoConcept> findAllTerminoConceptSample()throws HibernateException{
		
		Session hSession = getDafoeSession();
				
		List<ITerminoConcept> tcs = hSession.createCriteria(TerminoConceptImpl.class).list();
        
		
		for (ITerminoConcept tc : tcs) {
            
			System.out.println("tc id= " + tc.getId() + " || form= "+tc.getLabel()+"|| relation member size="+tc.getTerminoConceptRelationsMember().size()+"is_simple= "+tc.isSimple());
			testGetAllRelationSample(tc);
		}
		return tcs;
	}
	
	
@SuppressWarnings("unchecked")
public static List<ITerminoConcept> findAllTcDataTypeSample()throws HibernateException{
		
		Session hSession = getDafoeSession();
				
		List<ITerminoConcept> tcs = hSession.createCriteria(TcDataTypeImpl.class).list();
        
		
		for (ITerminoConcept tc : tcs) {
            
			System.out.println("tc id= " + tc.getId() + " || form= "+tc.getLabel()+"|| relation member size="+tc.getTerminoConceptRelationsMember().size());
			testGetAllRelationSample(tc);
		}
		return tcs;
	}
	
	public static void testGetAllRelationSample(ITerminoConcept tc){
		System.out.println(" 	TC RELATIONS: ");
		
		Set<ITerminoConceptRelationMember> relMembers= tc.getTerminoConceptRelationsMember();
	    Iterator<ITerminoConceptRelationMember> iter= relMembers.iterator();
	    
	    while(iter.hasNext()){
	    	ITerminoConceptRelationMember currrentMember=iter.next();
	    	System.out.println(" 		tc_relation= "+currrentMember.getTerminoConceptRelation().getId());
	    	System.out.println(" 		type_relation= "+currrentMember.getTerminoConceptRelation().getTypeRelation().getName());
	    	System.out.println(" 		tc_role= "+currrentMember.getTerminoConceptRole().getLabel());
	    }
	
	}
	

	
	
	/**
	 * Test save term property transaction.
	 */
	public static void testSaveTerminoPropertyTransactionSample() throws HibernateException{

		System.out.println("*************************** begin");

		Session hSession = getDafoeSession();
		
		Transaction tx = hSession.beginTransaction();
		
		ITerminoProperty prop= new TerminoPropertyImpl();
		prop.setLabel("doc1_tegs");
		prop.setType("String");
		
		
		ITerminoConcept tc= new TerminoConceptImpl();
		tc.setLabel("tc1_tegs");
		tc.addTerminoProperty(prop);
		
		hSession.save(tc);
		
		
		tx.commit();
		
		hSession.close();
		
		System.out.println("*************************** end ");
	}

	
	public static void testSaveTerminoConceptOccur(){
		
		 System.out.println("*************************** begin");

			Session hSession = getDafoeSession();
			
			Transaction tx = hSession.beginTransaction();
			ITerm term= new TermImpl();
			term.setLabel("term_test2");
			term.setState(TERMINO_OBJECT_STATE.STUDIED);
			
			ITermOccurrence toc= new TermOccurrenceImpl();
			toc.setPosition(10000);
			toc.setLength(10009);
			
			term.addTermOccurrence(toc);
			
			
			ITerminoConcept tc= new TerminoConceptImpl();
			tc.setLabel("tc_test2");
			
			tc.setState(TERMINO_ONTO_OBJECT_STATE.STUDIED);
			
			ITerminoConceptOccurrence tcocc= new TerminoConceptOccurrenceImpl();
			
			tcocc.setRelatedTermOccurence(toc);
			
			toc.setRelatedTerminoConceptOccurrence(tcocc);
			
			tc.addTerminoConceptOccurrence(tcocc);
			
			hSession.saveOrUpdate(term);
			
			hSession.saveOrUpdate(tc);
			
			tx.commit();
			
			 System.out.println("*************************** end ");
		
	}
	
	
	
	public static void testSaveTcDatatype(){
		System.out.println("*************************** begin");

		Session hSession = getDafoeSession();

		Transaction tx = hSession.beginTransaction();

		ITerminoConcept tcDat= new TcDataTypeImpl();

		tcDat.setLabel("tc_dat_integer_simple");
		hSession.saveOrUpdate(tcDat);

		tx.commit();

		System.out.println("*************************** end ");
		
	}
	
	
	public static void manageRTCType() {
		
		System.out.println("*************************** begin");
		
		System.out.println("RTC types creation: ");
		Session hSession = getDafoeSession();

		Transaction tx = hSession.beginTransaction();

		ITypeRelationTc test1 = new TypeRelationTcImpl();
		test1.setName("test1");
		hSession.saveOrUpdate(test1);
		
		ITypeRelationTc test2 = new TypeRelationTcImpl();
		test2.setName("test2");
		hSession.saveOrUpdate(test2);
		
		ITypeRelationTc test3 = new TypeRelationTcImpl();
		test3.setName("test3");
		hSession.saveOrUpdate(test3);
		
		tx.commit();

		System.out.println("test2 is_a test1");
		
		tx = hSession.beginTransaction();
		
		test1.addChild(test2);
		
		tx.commit();
		
		System.out.println("test2 is_a null");
		
		tx = hSession.beginTransaction();
		
		//test1.getChildren().remove(test2);
		
		test1.removeChild(test2);
		
		tx.commit();
		
		hSession.refresh(test2);
		
		System.out.println("test 1 parent " + test1.getParent());
		System.out.println("test 2 parent " + test2.getParent());
		System.out.println("test 3 parent " + test3.getParent());
		System.out.println("test 1 children " + test1.getChildren().size());
		System.out.println("test 2 children " + test2.getChildren().size());
		System.out.println("test 3 children " + test3.getChildren().size());
		
		System.out.println("*************************** end ");
	}
	
	public static void testGetAllAnnotatype(){
		System.out.println(TERMINO_OBJECT_STATE.STUDIED!=TERMINO_OBJECT_STATE.UNKNOWN);

		System.out.println("size"+ TERMINO_ONTO_ANNOTATION_TYPE_ENUM.values().length);
				
		for(TERMINO_ONTO_ANNOTATION_TYPE_ENUM toAnnotTEnum:TERMINO_ONTO_ANNOTATION_TYPE_ENUM.values()){
				
			
			System.out.println("label= "+toAnnotTEnum.getLabel()+"||"+"type= "+toAnnotTEnum.getType());
			
		}
	}
	
	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) throws HibernateException{

	    //findAllTerminoConceptSample();//ok
	      
		 // findAllTcDataTypeSample();//ok
		//testSaveTerminoOntoObjectStatusSample();//ok
	     testSaveTerminoConceptOccur();//ok
		
		//testSaveTcDatatype();//ok
	      
	    //manageRTCType();
			
	}
}
