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
package org.dafoe.framework.builder.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.framework.core.mock.MockBinaryTCRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelationMember;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRole;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoCollection;
import org.dafoe.framework.provider.hibernate.mock.BinaryTCRelation;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TcHashSetImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptRelationImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * A factory for creating Serializable objects from a non serializable one.
 *  @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 *  @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a> 
 *  
 */
public class SerializableObjectFactory {

	
	/**
	 * Creates a new serializable ITerminoConceptRelation from a non-serializable BinaryTCRelation.
	 *
	 * @param bTCR1 a non-serializable binary terminoconceptual relation
	 * @param hSession an hibernate session
	 * @return the new serializable terminoconceptual relation
	 */
	@SuppressWarnings("unchecked")
	public static ITerminoConceptRelation createTCR(MockBinaryTCRelation bTCR1, Session hSession) {
		Transaction dTx;
		ITerminoConceptRelation newTCR = null;
		List<ITerminoConceptRole> tcrs = null;
		Iterator<ITerminoConceptRole> itTcrs = null;
		ITerminoConceptRole tcr = null;

		System.out.println("binary RTC TC1 = " + bTCR1.getTc1().getLabel());

		try {
			dTx = hSession.beginTransaction();

			newTCR = new TerminoConceptRelationImpl();

			tcrs = hSession.createCriteria(ITerminoConceptRole.class)
					.list();
			itTcrs = tcrs.iterator();

			while (itTcrs.hasNext()) {
				tcr = itTcrs.next();

				if (tcr.getLabel().compareTo("arg1") == 0) {

					newTCR.addTerminoConcept(bTCR1.getTc1(), tcr);

				} else if (tcr.getLabel().compareTo("arg2") == 0) {

					newTCR.addTerminoConcept(bTCR1.getTc2(), tcr);

				}
			}

			newTCR.setTypeRelation(bTCR1.getType());

			newTCR.setState(bTCR1.getState());

			hSession.save(newTCR);

			dTx.commit();

			System.out.println("newRTC.getId() = " + newTCR.getId());

			// ES: pour mettre à jour les dépendances ... le framework devrait
			// le faire !!
			hSession.refresh(newTCR);

			hSession.refresh(bTCR1.getTc1());

			hSession.refresh(bTCR1.getTc2());

			System.out.println("Associated members: "
					+ newTCR.getTerminoConceptRelationMembers().size());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return newTCR;
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
			Iterator<ITerminoConceptRelation> itcrs = tcrs.iterator();

			while (itcrs.hasNext()) {
				ITerminoConceptRelation tcr = (ITerminoConceptRelation) (itcrs
						.next());

				MockBinaryTCRelation rel = new BinaryTCRelation();
				rel.setOrigin(tcr);
				rel.setType(tcr.getTypeRelation());
				rel.setState(tcr.getState());

				Set<ITerminoConceptRelationMember> tcrms = tcr
						.getTerminoConceptRelationMembers();
				Iterator<ITerminoConceptRelationMember> itcrms = tcrms
						.iterator();

				while (itcrms.hasNext()) {

					ITerminoConceptRelationMember itcrm = itcrms.next();

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

	public static ITerminoOntoCollection createHashSet(){
		return new TcHashSetImpl();
	}
	
	public static ITerminoOntoCollection createArrayList(){
		return null;
	}
}
