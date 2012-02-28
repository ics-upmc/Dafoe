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
package org.dafoe.framework.provider.hibernate.terminoontological.model.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.framework.core.mock.MockBinaryTCRelation;
import org.dafoe.framework.core.ontological.model.IOntology;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.framework.core.terminoontological.model.ITcList;
import org.dafoe.framework.core.terminoontological.model.ITcSet;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelationMember;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.mock.BinaryTCRelation;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.OntologyImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.base.BaseTerminoOntologyImpl;
import org.hibernate.Session;

/**
 * The Class TerminoOntologyImpl.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TerminoOntologyImpl extends BaseTerminoOntologyImpl implements
		ITerminoOntology {
	
	private List<ITerminoConcept> topTCs= null;
	private List<MockBinaryTCRelation> bTCRs= null;//new ArrayList<MockBinaryTCRelation>();
	
	public TerminoOntologyImpl() {
		super();
		super.setTerminoConcepts(new HashSet<ITerminoConcept>());
		super
				.setTerminoConceptRelations(new HashSet<ITerminoConceptRelation>());

		super.setMappedTerminologies(new HashSet<ITerminology>());

		super.setMappedOntologies(new HashSet<IOntology>());
		
		super.setTcSets(new HashSet<ITcSet>());
		
		super.setTcLists(new HashSet<ITcList>());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.terminoontological.model.ITerminoOntology#
	 * addTerminoOntoObject
	 * (org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject)
	 */
	// @Override
	public boolean addTerminoOntoObject(ITerminoOntoObject toObj) {
		if (toObj instanceof ITerminoConcept) {
			ITerminoConcept tc = (ITerminoConcept) toObj;

			((TerminoConceptImpl) tc).setTerminoOntology(this);
			return super.getTerminoConcepts().add(tc);

		}
		if (toObj instanceof ITerminoConceptRelation) {
			ITerminoConceptRelation tcRel = (ITerminoConceptRelation) toObj;
			
			((TerminoConceptRelationImpl) tcRel).setTerminoOntology(this);
			return super.getTerminoConceptRelations().add(tcRel);
		}

		if (toObj instanceof ITypeRelationTc) {
			 ITypeRelationTc typeRelTc= (ITypeRelationTc)toObj;
			  ((TypeRelationTcImpl)typeRelTc).setTerminoOntology(this);
			  return super.getTypeRelationTcs().add(typeRelTc);
			  
		 }
		
		if (toObj instanceof ITcSet) {
			ITcSet tcHashSet= (ITcSet)toObj;
			  ((TcHashSetImpl)tcHashSet).setTerminoOntology(this);
			  return super.getTcSets().add(tcHashSet);
			  
		 }
		
		if (toObj instanceof ITcList) {
			ITcList tcArrayList= (ITcList)toObj;
			  ((TcArrayListImpl)tcArrayList).setTerminoOntology(this);
			  return super.getTcLists().add(tcArrayList);
			  
		 }

		return false;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.terminoontological.model.ITerminoOntology#
	 * getTerminoOntoObjects()
	 */
	// @Override
	public Set<ITerminoOntoObject> getTerminoOntoObjects() {
		Set<ITerminoOntoObject> toObjects = new HashSet<ITerminoOntoObject>();

		toObjects.addAll(super.getTerminoConcepts());
		toObjects.addAll(super.getTerminoConceptRelations());
        toObjects.addAll(super.getTypeRelationTcs());
        toObjects.addAll(super.getTcSets());
        toObjects.addAll(super.getTcLists());
        
		return toObjects;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.terminoontological.model.ITerminoOntology#
	 * removeTerminoOntoObject
	 * (org.dafoe.framework.core.terminoontological.model.ITerminoOntoObject)
	 */
	// @Override
	public boolean removeTerminoOntoObject(ITerminoOntoObject toObj) {
		if (toObj instanceof ITerminoConcept) {
			ITerminoConcept tc = (ITerminoConcept) toObj;
			
			((TerminoConceptImpl) tc).setTerminoOntology(null);
			return super.getTerminoConcepts().remove(tc);
			// tc must be delete
		}
		if (toObj instanceof ITermRelation) {
			ITerminoConceptRelation tcRel = (ITerminoConceptRelation) toObj;
			
			((TerminoConceptRelationImpl) tcRel).setTerminoOntology(null);
			return super.getTerminoConceptRelations().remove(toObj);
			// tcr must be delete
		}
				  
		if (toObj instanceof ITypeRelationTc) {
		 ITypeRelationTc typeRelTc= (ITypeRelationTc)toObj;
		  ((TypeRelationTcImpl)typeRelTc).setTerminoOntology(null);
		  return super.getTypeRelationTcs().remove(typeRelTc);
		  
		// typeRelTc must be delete
		  }
		 
		if (toObj instanceof ITcSet) {
			ITcSet tcHashSet= (ITcSet)toObj;
			  ((TcHashSetImpl)tcHashSet).setTerminoOntology(null);
			  return super.getTcSets().remove(tcHashSet);
			
			  // tcHashSet must be delete
		 }
		
		if (toObj instanceof ITcList) {
			ITcList tcArrayList= (ITcList)toObj;
			  ((TcArrayListImpl)tcArrayList).setTerminoOntology(null);
			  return super.getTcLists().remove(tcArrayList);
			  
			  //tcList must be delete
		 }

		return false;

	}
	
	@Override
	public Set<ITerminoConcept> getTerminoConcepts() {
		
		return super.getTerminoConcepts();
	}

	@Override
	public Set<ITerminoConceptRelation> getTerminoConceptRelations() {
		
		return super.getTerminoConceptRelations();
	}
	@Override
	public Set<ITypeRelationTc> getTypeRelationTcs() {
		
		return super.getTypeRelationTcs();
	}
	
	@Override
	public Set<IOntology> getMappedOntologies() {
		
		return super.getMappedOntologies();
	}

	@Override
	public Set<ITerminology> getMappedTerminologies() {
		
		return super.getMappedTerminologies();
	}

	@Override
	public boolean addMappedOntology(IOntology onto) {
		boolean ok1= super.getMappedOntologies().add(onto);
		boolean ok2= ((OntologyImpl)onto).getMappedTerminoOntologies().add(this);
		return ok1 && ok2;
	}

	@Override
	public boolean addMappedTerminology(ITerminology termino) {
		
		return false;
	}

	@Override
	public boolean removeMappedOntology(IOntology onto) {
		
		return false;
	}

	@Override
	public boolean removeMappedTerminology(ITerminology termino) {
		
		return false;
	}

	@Override
	public boolean addTopTerminoConcept(ITerminoConcept tc) {
		
		return topTCs.add(tc);
	}
	
	@Override
	public  List<ITerminoConcept> getTopTerminoConcepts() {
       //return topTCs;
		
       if(topTCs== null){
		//List<ITerminoConcept> tcs = new ArrayList<ITerminoConcept>();
         topTCs= new ArrayList<ITerminoConcept>();
		// HQL;

		try {
			// System.out.println(getDafoeSession().toString());
			// Transaction dTx = getDafoeSession().beginTransaction();
			String query = "SELECT m22_terminoconcept.* FROM m22_terminoconcept "
					+ "left join m22_tc_2parent2_tc as lien on lien.tc_child_id=m22_terminoconcept.id "
					+ "WHERE ((lien.tc_child_id is null and m22_terminoconcept.termino_ontology_id= "
					+ this.getId()
					+ ") AND NOT (m22_terminoconcept.is_datatype))"
					+ " ORDER BY label";
			topTCs = getDafoeSession().createSQLQuery(query).addEntity(
					"m22_terminoconcept", TerminoConceptImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}

       }
		//return tcs;
       
       return topTCs;

	}
	
	private static Session getDafoeSession() {
		return SessionFactoryImpl.getCurrentDynamicSession();
	}

	@Override
	public boolean addBinaryTerminoConceptRelation(MockBinaryTCRelation bTCR) {
		
		return bTCRs.add(bTCR);
	}

	@Override
	public List<MockBinaryTCRelation> getBinaryTerminoConceptRelations() {
		if(bTCRs== null){
			bTCRs= new ArrayList<MockBinaryTCRelation>();
			List<ITerminoConceptRelation> tcrs= getDafoeSession().createCriteria(TerminoConceptRelationImpl.class).list();
			
			bTCRs= buildBinaryTCRelations(tcrs);
		}
		return bTCRs;
	}

	
	public static MockBinaryTCRelation getBinaryTCRelation(
			ITerminoConceptRelation rtc) {

		List<MockBinaryTCRelation> rels;

		List<ITerminoConceptRelation> tmp = new ArrayList<ITerminoConceptRelation>();
		tmp.add(rtc);

		rels = buildBinaryTCRelations(tmp);

		if (rels == null) {

			return null;

		} else {

			return rels.get(0);

		}

	}
	
	// 

	private static List<MockBinaryTCRelation> buildBinaryTCRelations(
			List<ITerminoConceptRelation> tcrs) {
		List<MockBinaryTCRelation> rels = new ArrayList<MockBinaryTCRelation>();

		if (tcrs != null) {
			Iterator<ITerminoConceptRelation> iterTCR = tcrs.iterator();

			while (iterTCR.hasNext()) {
				ITerminoConceptRelation tcr = (ITerminoConceptRelation) (iterTCR
						.next());

				MockBinaryTCRelation rel = new BinaryTCRelation();
				rel.setOrigin(tcr);
				rel.setType(tcr.getTypeRelation());
				rel.setState(tcr.getState());
				

				Set<ITerminoConceptRelationMember> tcrms = tcr
						.getTerminoConceptRelationMembers();
				Iterator<ITerminoConceptRelationMember> iterTCRM = tcrms
						.iterator();

				while (iterTCRM.hasNext()) {

					ITerminoConceptRelationMember itcrm = iterTCRM.next();

					String role = itcrm.getTerminoConceptRole().getLabel();

					if (role.compareTo("arg1") == 0) {

						rel.setTc1(itcrm.getTerminoConcept());

					} else if (role.compareTo("arg2") == 0) {

						rel.setTc2(itcrm.getTerminoConcept());

					}
				}

				rels.add(rel);
			}
		}

		return rels;
	}

	
}