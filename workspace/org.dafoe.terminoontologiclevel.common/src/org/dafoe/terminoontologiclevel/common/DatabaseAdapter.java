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

package org.dafoe.terminoontologiclevel.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.controler.utility.UtilTools;
import org.dafoe.framework.core.ontological.model.IClass;
import org.dafoe.framework.core.ontological.model.IDatatypeProperty;
import org.dafoe.framework.core.ontological.model.IObjectProperty;
import org.dafoe.framework.core.ontological.model.IOntoObject;
import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptOccurrence;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelation;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRelationMember;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptRole;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotation;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntoAnnotationType;
import org.dafoe.framework.core.terminoontological.model.ITerminoOntology;
import org.dafoe.framework.core.terminoontological.model.ITypeRelationTc;
import org.dafoe.framework.core.terminoontological.model.TERMINO_ONTO_OBJECT_STATE;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ClassImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.DatatypePropertyImpl;
import org.dafoe.framework.provider.hibernate.ontological.model.impl.ObjectPropertyImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.SentenceImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptAnnotationImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptOccurrenceImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptRelationImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntoAnnotationTypeImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoOntologyImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TypeRelationTcImpl;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * The DatabaseAdapter Class provide services for querying database.
 * @author <a href="mailto:sardet@ensma.fr">Eric SARDET</a>
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 * @author <a href="mailto:texier@ensma.fr">Guillaume TEXIER</a>
 */
public class DatabaseAdapter {

	
	public static String STAR_TERM_ANNOTATION = "preferred_label";

	
	public static String LABEL_SP_ANNOTATION = "label_sp";

	
	public static String LABEL_EN_ANNOTATION = "label_en";

	
	private static List<ITerminoConcept> terminoConcepts;

	
	private static List<ITerminoConceptRelation> tcrs;

	
	private static List<ITypeRelationTc> rtcTypes;

	
	private static ITerminoOntology currentTerminoOntology;

	/**
	 * Gets the current created dafoe session.
	 *
	 * @return the dafoe session
	 */
	@SuppressWarnings("static-access")
	public static Session getDafoeSession() {
		return org.dafoe.terminoontologiclevel.common.Activator.getDefault().dafoeSession;
	}

	//

	/**
	 * Load all termino concepts from database.
	 */
	@SuppressWarnings("unchecked")
	public static void loadTerminoConcepts() {

		try {

			terminoConcepts = getDafoeSession().createCriteria(
					TerminoConceptImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Load all termino concepts relations from database.
	 */
	@SuppressWarnings("unchecked")
	public static void loadTerminoConceptsRelations() {

		try {

			tcrs = getDafoeSession().createCriteria(
					ITerminoConceptRelation.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Gets the termino concepts.
	 *
	 * @return the termino concepts
	 */
	public static List<ITerminoConcept> getTerminoConcepts() {
		return terminoConcepts;
	}

	//

	/**
	 * Load all  termino conceptual relation types from database.
	 */
	@SuppressWarnings("unchecked")
	public static void loadRTCTypes() {

		try {

			rtcTypes = getDafoeSession().createCriteria(ITypeRelationTc.class)
					.list();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Gets the rTC types.
	 *
	 * @return the rTC types
	 */
	public static List<ITypeRelationTc> getRTCTypes() {
		loadRTCTypes();
		return rtcTypes;
	}

	//

	/**
	 * Creates the termino concept.
	 *
	 * @param label the label
	 * @param term the term
	 * @return the i termino concept
	 */
	public static ITerminoConcept createTerminoConcept(String label, ITerm term) {
		ITerminoConcept tc = new TerminoConceptImpl();
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			tc.setLabel(label);

			tc.setState(TERMINO_ONTO_OBJECT_STATE.VALIDATED);

			getDafoeSession().save(tc);

			currentTerminoOntology.addTerminoOntoObject(tc);

			if (term != null) {

				term.addMappedTerminoConcept(tc);

				Set<ITerm> variants = term.getVariantes();
				Iterator<ITerm> it = variants.iterator();

				while (it.hasNext()) {
					it.next().addMappedTerminoConcept(tc);
				}

			}

			dTx.commit();

			getDafoeSession().refresh(tc);

		} catch (Exception e) {

			e.printStackTrace();

		}

		return tc;
	}

	//

	/**
	 * Remove mapping between tc and term.
	 *
	 * @param terminoConcepts the termino concepts
	 * @param term the term
	 */
	public static void unlinkTerm(List<ITerminoConcept> terminoConcepts,
			ITerm term) {
		Transaction dTx;
		ITerminoConcept tc = null;
		Iterator<ITerminoConcept> it = terminoConcepts.iterator();

		try {
			dTx = getDafoeSession().beginTransaction();

			while (it.hasNext()) {

				tc = it.next();

				term.getMappedTerminoConcepts().remove(tc);

				List<ITerm> variants = UtilTools.setToList(term.getVariantes());
				Iterator<ITerm> itVars = variants.iterator();

				while (itVars.hasNext()) {
					itVars.next().getMappedTerminoConcepts().remove(tc);
				}

			}

			dTx.commit();

			getDafoeSession().refresh(tc);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Remove mapping between terms and their related termino-onto object.
	 *
	 * @param terms the terms
	 */
	public static void unlinkTerms(List<ITerm> terms) {
		Transaction dTx;
		ITerm term = null;
		Set<ITerminoConcept> tcs;
		Iterator<ITerm> it;
		try {

			if (terms != null) {

				dTx = getDafoeSession().beginTransaction();

				it = terms.iterator();

				while (it.hasNext()) {

					term = it.next();

					tcs = term.getMappedTerminoConcepts();
					Iterator<ITerminoConcept> it2 = tcs.iterator();
					ITerminoConcept tc;

					while (it2.hasNext()) {

						tc = it2.next();

						tc.getMappedTerms().remove(term);
					}
				
				}

				dTx.commit();

				it = terms.iterator();

				while (it.hasNext()) {
					getDafoeSession().refresh(it.next());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 

	/**
	 * Gets the termino concept by label.
	 *
	 * @param label the label
	 * @return the termino concept by label
	 */
	@SuppressWarnings("unchecked")
	public static ITerminoConcept getTerminoConceptByLabel(String label) {
		List<ITerminoConcept> queryResult;
		ITerminoConcept tc = null;

		try {

			SQLQuery query = getDafoeSession().createSQLQuery(
					"SELECT * FROM m22_terminoconcept WHERE label = '" + label
							+ "'").addEntity(TerminoConceptImpl.class);

			if (query != null) {
				queryResult = query.list();

				if (queryResult.size() != 0) {
					tc = queryResult.get(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tc;
	}

	// 

	/**
	 * Gets the termino conceptual relations.
	 *
	 * @return the termino conceptual relations
	 */
	public static List<BinaryTCRelation> getTerminoConceptualRelations() {
		List<BinaryTCRelation> rels;

		loadTerminoConceptsRelations();

		rels = buildBinaryTCRelations(tcrs);
		return rels;

	}

	// 

	/**
	 * Gets the binary tc relation.
	 *
	 * @param rtc the rtc
	 * @return the binary tc relation
	 */
	public static BinaryTCRelation getBinaryTCRelation(
			ITerminoConceptRelation rtc) {

		List<BinaryTCRelation> rels;

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

	/**
	 * Gets termino conceptual relation from tc.
	 *
	 * @param tc the tc
	 * @return the termino conceptual relation from tc
	 */
	public static List<BinaryTCRelation> getRTCsFromTC(ITerminoConcept tc) {

		List<BinaryTCRelation> rels = new ArrayList<BinaryTCRelation>();
		List<ITerminoConceptRelation> tcrs = new ArrayList<ITerminoConceptRelation>();

		if (tc != null) {

			Set<ITerminoConceptRelationMember> tcrms = tc
					.getTerminoConceptRelationsMember();
			Iterator<ITerminoConceptRelationMember> itTcrms = tcrms.iterator();

			while (itTcrms.hasNext()) {
				ITerminoConceptRelationMember tcrm = itTcrms.next();

				tcrs.add(tcrm.getTerminoConceptRelation());

			}

			rels = buildBinaryTCRelations(tcrs);
		}

		return rels;
	}

	// 

	/**
	 * Builds the binary tc relations.
	 *
	 * @param tcrs the tcrs
	 * @return the list
	 */
	private static List<BinaryTCRelation> buildBinaryTCRelations(
			List<ITerminoConceptRelation> tcrs) {
		List<BinaryTCRelation> rels = new ArrayList<BinaryTCRelation>();

		if (tcrs != null) {
			Iterator<ITerminoConceptRelation> itcrs = tcrs.iterator();

			while (itcrs.hasNext()) {
				ITerminoConceptRelation tcr = (ITerminoConceptRelation) (itcrs
						.next());

				BinaryTCRelation rel = new BinaryTCRelation();
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

	//

	/**
	 * Creates the termino conceptual relation from binary relation.
	 *
	 * @param rtc the rtc
	 * @return the termino concept relation
	 */
	@SuppressWarnings("unchecked")
	public static ITerminoConceptRelation createRTC(BinaryTCRelation rtc) {
		Transaction dTx;
		ITerminoConceptRelation newRTC = null;
		List<ITerminoConceptRole> tcrs = null;
		Iterator<ITerminoConceptRole> itTcrs = null;
		ITerminoConceptRole tcr = null;

		System.out.println("binary RTC TC1 = " + rtc.getTc1().getLabel());

		try {
			dTx = getDafoeSession().beginTransaction();

			newRTC = new TerminoConceptRelationImpl();

			tcrs = getDafoeSession().createCriteria(ITerminoConceptRole.class)
					.list();
			itTcrs = tcrs.iterator();

			while (itTcrs.hasNext()) {
				tcr = itTcrs.next();

				if (tcr.getLabel().compareTo("arg1") == 0) {

					newRTC.addTerminoConcept(rtc.getTc1(), tcr);

				} else if (tcr.getLabel().compareTo("arg2") == 0) {

					newRTC.addTerminoConcept(rtc.getTc2(), tcr);

				}
			}

			newRTC.setTypeRelation(rtc.getType());

			newRTC.setState(TERMINO_ONTO_OBJECT_STATE.VALIDATED);

			getDafoeSession().save(newRTC);

			dTx.commit();

			System.out.println("newRTC.getId() = " + newRTC.getId());

			// ES: pour mettre à jour les dépendances ... le framework devrait
			// le faire !!
			getDafoeSession().refresh(newRTC);

			getDafoeSession().refresh(rtc.getTc1());

			getDafoeSession().refresh(rtc.getTc2());

			System.out.println("Associated members: "
					+ newRTC.getTerminoConceptRelationMembers().size());

		} catch (Exception e) {
			e.printStackTrace();
		}

		return newRTC;
	}

	//

	/**
	 * Create mapping between termino conceptual relation and term relation.
	 *
	 * @param rtc the termino conceptual relation
	 * @param rt the term relation
	 */
	public static void linkRTCWithRT(BinaryTCRelation rtc, ITermRelation rt) {
		Transaction dTx;

		try {

			if (!rtc.getTc1().getMappedTerms().contains(rt.getTerm1())) {

				linkTCWithTerm(rtc.getTc1(), rt.getTerm1());

			}

			if (!rtc.getType().getMappedTermRelationTypes().contains(
					rt.getTypeRel())) {

				linkRTCTypeWithRTType(rtc.getType(), rt.getTypeRel());

			}

			if (!rtc.getTc2().getMappedTerms().contains(rt.getTerm2())) {

				linkTCWithTerm(rtc.getTc2(), rt.getTerm2());

			}

			dTx = getDafoeSession().beginTransaction();

			rtc.getOrigin().addMappedTermRelation(rt);

			dTx.commit();

			// getDafoeSession().refresh(rtc.getOrigin());

			getDafoeSession().refresh(rt);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Create mapping between termino concept and term.
	 *
	 * @param tc the tc
	 * @param t the t
	 */
	public static void linkTCWithTerm(ITerminoConcept tc, ITerm t) {
		Transaction dTx;

		try {
			dTx = getDafoeSession().beginTransaction();

			tc.addMappedTerm(t);

			Set<ITerm> vars = t.getVariantes();
			Iterator<ITerm> it = vars.iterator();

			while (it.hasNext()) {
				tc.addMappedTerm(it.next());
			}

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Create mapping between termino concept relation type and term relation type.
	 *
	 * @param rtcType the termino concept relation type
	 * @param rtType the term relation type
	 */
	public static void linkRTCTypeWithRTType(ITypeRelationTc rtcType,
			ITypeRelationTermino rtType) {
		Transaction dTx;

		try {
			dTx = getDafoeSession().beginTransaction();

			rtType.addMappedTcRelationType(rtcType);

			dTx.commit();

			getDafoeSession().refresh(rtcType);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Remove mapping between binary termino conceptual relation and term relation.
	 *
	 * @param rtc the binary termino conceptual relation
	 * @param rt the term relation
	 */
	public static void unlinkRT(BinaryTCRelation rtc, ITermRelation rt) {
		Transaction dTx;

		try {
			dTx = getDafoeSession().beginTransaction();

			rtc.getOrigin().addMappedTermRelation(rt);
			// rt.removeRelatedTerminoConceptRelation(rtc.getOrigin());

			dTx.commit();

			getDafoeSession().refresh(rt);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Remove mapping between termino conceptual relation type and term relation type.
	 *
	 * @param rtcType the termino conceptual relation type
	 * @param rtType the term relation type
	 */
	public static void unlinkRTType(ITypeRelationTc rtcType,
			ITypeRelationTermino rtType) {
		Transaction dTx;

		try {
			dTx = getDafoeSession().beginTransaction();

			rtType.getMappedTcRelationTypes().remove(rtcType);

			dTx.commit();

			getDafoeSession().refresh(rtcType);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Gets the termino conceptual relation type by id.
	 *
	 * @param id the id
	 * @return the termino conceptual relation type
	 */
	public static ITypeRelationTc getRTCTypeById(int id) {

		Object o = getDafoeSession().load(TypeRelationTcImpl.class, id);

		return (ITypeRelationTc) o;
	}

	// 

	/**
	 * Gets the termino conceptual relation type by label.
	 *
	 * @param label the label
	 * @return the termino conceptual relation type
	 */
	@SuppressWarnings("unchecked")
	public static TypeRelationTcImpl getRTCTypeByLabel(String label) {
		List<TypeRelationTcImpl> queryResult;
		TypeRelationTcImpl rtcType = null;

		try {

			SQLQuery query = getDafoeSession().createSQLQuery(
					"SELECT * FROM m22_type_relation_tc WHERE name = '" + label
							+ "'").addEntity(TypeRelationTcImpl.class);

			if (query != null) {
				queryResult = query.list();

				if (queryResult.size() != 0) {
					rtcType = queryResult.get(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rtcType;
	}

	//

	/**
	 * Creates the termino conceptual relation type.
	 *
	 * @param label the label
	 * @param parent the parent
	 * @return the i type relation tc
	 */
	public static ITypeRelationTc createRTCType(String label,
			ITypeRelationTc parent) {
		ITypeRelationTc rtcType = new TypeRelationTcImpl();
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			rtcType.setName(label);

			if (parent != null) {
				parent.addChild(rtcType);
			}
			getDafoeSession().save(rtcType);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rtcType;
	}

	//

	/**
	 * Change parent hierarchy of a ITypeRelationTc.
	 *
	 * @param rtcType the rtc type
	 * @param parent the parent
	 */
	public static void changeParent(ITypeRelationTc rtcType,
			ITypeRelationTc parent) {
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			if (rtcType.getParent() != null) {
				rtcType.removeChild(rtcType);
			}

			if (parent != null) {
				parent.addChild(rtcType);
			}

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// VT:

	/**
	 * Gets the top termino concepts.
	 *
	 * @param to the to
	 * @return the top termino concepts
	 */
	@SuppressWarnings("unchecked")
	public static List<ITerminoConcept> getTopTerminoConcepts(
			ITerminoOntology to) {
		List<ITerminoConcept> tcs = new ArrayList<ITerminoConcept>();

		// HQL;

		try {
			
			String query = "SELECT m22_terminoconcept.* FROM m22_terminoconcept "
					+ "left join m22_tc_2parent2_tc as lien on lien.tc_child_id=m22_terminoconcept.id "
					+ "WHERE ((lien.tc_child_id is null and m22_terminoconcept.termino_ontology_id= "
					+ to.getId()
					+ ") AND NOT (m22_terminoconcept.is_datatype))"
					+ " ORDER BY label";
			tcs = getDafoeSession().createSQLQuery(query).addEntity(
					"m22_terminoconcept", TerminoConceptImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return tcs;

	}

	//

	/**
	 * Delete tc and all its sub tc.
	 *
	 * @param tc the tc
	 */
	public static void deleteRecursiveTC(ITerminoConcept tc) {
		
		List<ITerminoConcept> delTCs = new ArrayList<ITerminoConcept>();
		try {

			retrieveSubTCTree(tc, delTCs);

			Iterator<ITerminoConcept> iter = delTCs.iterator();

			while (iter.hasNext()) {
				ITerminoConcept delTC = iter.next();
				deleteTC(delTC);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieve sub tc tree.
	 *
	 * @param tc the tc
	 * @param delTCs the del t cs
	 */
	private static void retrieveSubTCTree(ITerminoConcept tc,
			List<ITerminoConcept> delTCs) {

		Set<ITerminoConcept> children = tc.getChildren();

		Iterator<ITerminoConcept> iter = children.iterator();

		while (iter.hasNext()) {

			retrieveSubTCTree(iter.next(), delTCs);

		}

		delTCs.add(tc);

	}

	//

	/**
	 * Delete tc.
	 *
	 * @param tc the tc
	 */
	public static void deleteTC(ITerminoConcept tc) {
		Transaction dTx;
		try {

			deleteAllTCAnnotations(tc);

			dTx = getDafoeSession().beginTransaction();

			Iterator<ITerminoConcept> iter = tc.getChildren().iterator();

			while (iter.hasNext()) {
				ITerminoConcept enfant = iter.next();
				enfant.getParents().remove(tc);
				getDafoeSession().save(enfant);
			}

			iter = tc.getParents().iterator();
			while (iter.hasNext()) {
				ITerminoConcept parent = iter.next();
				parent.getChildren().remove(tc);
				getDafoeSession().save(parent);
			}

			dTx.commit();

			getDafoeSession().refresh(tc);

			dTx = getDafoeSession().beginTransaction();

			currentTerminoOntology.removeTerminoOntoObject(tc);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Save termino concept.
	 *
	 * @param tc the tc
	 */
	public static void saveTerminoConcept(ITerminoConcept tc) {

		try {

			Transaction dTx = getDafoeSession().beginTransaction();

			dTx.begin();

			getDafoeSession().save(tc);

			dTx.commit();

			getDafoeSession().refresh(currentTerminoOntology);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Change parent hierarchy of the tc.
	 *
	 * @param tc the tc
	 * @param newParent the new parent
	 */
	public static void ChangeParent(ITerminoConcept tc,
			ITerminoConcept newParent) {
		Transaction dTx;
		try {

			if (tc == newParent) {
				return;
			}

			dTx = getDafoeSession().beginTransaction();

			dTx.begin();

			Iterator<ITerminoConcept> iter = tc.getParents().iterator();

			while (iter.hasNext()) {
				ITerminoConcept parent =  iter.next();
				parent.removeChild(tc);
				getDafoeSession().save(parent);
			}

			tc.addParent(newParent);

			getDafoeSession().save(tc);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Gets the default termino ontology language.
	 *
	 * @return the default termino ontology
	 */
	public static ITerminoOntology getDefaultTerminoOntology() {

		currentTerminoOntology = (ITerminoOntology) getDafoeSession()
				.createCriteria(TerminoOntologyImpl.class).list().get(0);
		return currentTerminoOntology;

	}

	//

	/**
	 * Refresh a terminoconcept object.
	 *
	 * @param tc the tc
	 */
	public static void refresh(ITerminoConcept tc) {
		getDafoeSession().refresh(tc);
	}

	//

	/**
	 * Update termino concept differential.
	 *
	 * @param tc the tc
	 */
	public static void updateTerminoConceptDifferential(ITerminoConcept tc) {
		Transaction dTx;

		try {
			dTx = getDafoeSession().beginTransaction();

			getDafoeSession().update(tc);

			dTx.commit();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	//

	/**
	 * Update label and status of a terminoconcept.
	 *
	 * @param tc the tc
	 * @param label the label
	 * @param status the status
	 */
	public static void updateTC(ITerminoConcept tc, String label, TERMINO_ONTO_OBJECT_STATE status) {
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			if (label != null) {
				tc.setLabel(label);
			}

			if (status != TERMINO_ONTO_OBJECT_STATE.UNKNOWN) {
				tc.setState(status);
			}

			getDafoeSession().update(tc);

			dTx.commit();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	//

	/**
	 * Update the name of a termino conceptual relation type.
	 *
	 * @param rtcType the rtc type
	 * @param label the label
	 */
	public static void updateRTCType(ITypeRelationTc rtcType, String label) {
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			if (label != null) {
				rtcType.setName(label);
			}

			getDafoeSession().update(rtcType);

			dTx.commit();

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	//

	/**
	 * Update termino conceptual relation.
	 *
	 * @param rtc the binary termino conceptual relation
	 * @param tc1 the tc1
	 * @param tc2 the tc2
	 * @param rtcType the termino conceptual relation type
	 * @param status the status
	 */
	@SuppressWarnings("unchecked")
	public static void updateRTC(BinaryTCRelation rtc, ITerminoConcept tc1,
			ITerminoConcept tc2, ITypeRelationTc rtcType, TERMINO_ONTO_OBJECT_STATE status) {
		Transaction dTx;
		ITerminoConcept oldTc1, oldTc2;
		ITypeRelationTc oldRTCType;
		ITerminoConceptRole roleArg1, roleArg2;

		try {

			dTx = getDafoeSession().beginTransaction();

			roleArg1 = roleArg2 = null;

			List<ITerminoConceptRole> roles = getDafoeSession().createCriteria(
					ITerminoConceptRole.class).list();
			Iterator<ITerminoConceptRole> it = roles.iterator();
			while (it.hasNext()) {
				ITerminoConceptRole role = it.next();

				if (role.getLabel().compareTo("arg1") == 0) {

					roleArg1 = role;

				} else if (role.getLabel().compareTo("arg2") == 0) {

					roleArg2 = role;
				}
			}

			oldTc1 = rtc.getTc1();
			oldTc2 = rtc.getTc2();
			oldRTCType = rtc.getType();

			if ((tc1 != null) && (tc1 != oldTc1)) {
				rtc.getOrigin().addTerminoConcept(tc1, roleArg1);
			}

			if ((tc2 != null) && (tc2 != oldTc2)) {
				rtc.getOrigin().addTerminoConcept(tc2, roleArg2);
			}

			if ((rtcType != null) && (oldRTCType != rtcType)) {
				rtcType.addTerminoConceptRelation(rtc.getOrigin());
			}

			if (status != TERMINO_ONTO_OBJECT_STATE.UNKNOWN) {
				rtc.getOrigin().setState(status);
			}

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Update the status of a termino conceptual relation.
	 *
	 * @param rtc the rtc
	 * @param status the status
	 */
	public static void updateRTCStatus(ITerminoConceptRelation rtc, TERMINO_ONTO_OBJECT_STATE status) {
		Transaction dTx;
		try {

			dTx = getDafoeSession().beginTransaction();

			rtc.setState(status);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Delete a given ITerminoConceptRelation.
	 *
	 * @param rtc the termino conceptual relation
	 */
	public static void deleteRTC(ITerminoConceptRelation rtc) {
		Transaction dTx;
		try {

			dTx = getDafoeSession().beginTransaction();

			configureDeleteRTC(rtc);

			Object o = getDafoeSession().load(TerminoConceptRelationImpl.class,
					rtc.getId());

			getDafoeSession().delete(o);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Configure delete rtc.
	 *
	 * @param rtc the rtc
	 */
	private static void configureDeleteRTC(ITerminoConceptRelation rtc) {
		ITerminoConcept tc1, tc2;
		ITypeRelationTc rtcType;

		Set<ITerminoConceptRelationMember> members = rtc
				.getTerminoConceptRelationMembers();
		Iterator<ITerminoConceptRelationMember> itMembers = members.iterator();

		while (itMembers.hasNext()) {
			ITerminoConceptRelationMember member = itMembers.next();
			String role = member.getTerminoConceptRole().getLabel();

			if (role.compareTo("arg1") == 0) {
				// remove link from TC1
				tc1 = member.getTerminoConcept();
				tc1.getTerminoConceptRelationsMember().remove(member);

			} else if (role.compareTo("arg2") == 0) {
				// remove link from TC2
				tc2 = member.getTerminoConcept();
				tc2.getTerminoConceptRelationsMember().remove(member);
				// remove member
			}
			// remove member
			itMembers.remove();
		}

		// remove link from RTCType
		rtcType = rtc.getTypeRelation();
		rtcType.getTcRelation().remove(rtc);

		// remove link from RTs
		Set<ITermRelation> relatedRTs = rtc.getMappedTermRelations();
		Iterator<ITermRelation> itRTs = relatedRTs.iterator();

		while (itRTs.hasNext()) {
			ITermRelation rt = itRTs.next();
			rt.getMappedTerminoConceptRelations().remove(rtc);
		}

	}

	//

	/**
	 * Gets the default language for terminoonlogy.
	 *
	 * @return the default language
	 */
	@SuppressWarnings("unchecked")
	public static String getDefaultLanguage() {
		String defaultLanguage;

		try {

			List<ITerminoOntology> tos = getDafoeSession().createCriteria(
					ITerminoOntology.class).list();

			if (tos.size() > 0) {

				ITerminoOntology to = tos.get(0);
				defaultLanguage = to.getLanguage();

				return defaultLanguage;

			} else {

				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 

	/**
	 * Gets the sentences from tc.
	 *
	 * @param tc the tc
	 * @return the sentences from tc
	 */
	@SuppressWarnings("unchecked")
	public static List<ISentence> getSentencesFromTC(ITerminoConcept tc) {
		List<ISentence> res = new ArrayList<ISentence>();
		//List<ISentence> tmp = null;

		res = new ArrayList<ISentence>();

		if (tc != null) {

			res = getDafoeSession()
					.createSQLQuery(
							""
									+ "SELECT DISTINCT m11_sentence.* FROM m11_sentence "
									+ "	INNER JOIN "
									+ "		m11_termoccurrence ON m11_termoccurrence.sentence_id = m11_sentence.id "
									+ "WHERE"
									+ "	EXISTS ("
									+ "		SELECT  m12_terminoconceptoccurrence.terminoconcept_id FROM m12_terminoconceptoccurrence"
									+ "		WHERE ((m12_terminoconceptoccurrence.termoccur_id = m11_termoccurrence.id)"
									+ "			AND"
									+ "			(m12_terminoconceptoccurrence.terminoconcept_id = "
									+ tc.getId() + "))" + "	) "
									+ "ORDER BY num_order" + "").addEntity(
							SentenceImpl.class).list();

			/*
			 * tmp = getDafoeSession().createSQLQuery("SELECT m11_sentence.* " +
			 * "FROM m11_sentence inner join m11_termoccurrence on m11_termoccurrence.sentence_id = m11_sentence.id "
			 * + "WHERE m11_termoccurrence.term_id = " +
			 * itTerm.next().getId().toString() + " " +
			 * "ORDER BY num_order").addEntity(SentenceImpl.class).list();
			 */

		}

		return res;
	}

	// To be improved with multiple sentences

	/**
	 * Delete sentences from tc.
	 *
	 * @param tc the tc
	 * @param sentence the sentence
	 */
	@SuppressWarnings("unchecked")
	public static void deleteSentencesFromTC(ITerminoConcept tc,
			List<ISentence> sentence) {
		Transaction dTx;
		List<ITerminoConceptOccurrence> res = new ArrayList<ITerminoConceptOccurrence>();

		if ((tc != null) && (sentence != null)) {

			// to be improved with multiple sentences
			if (sentence.get(0) != null) {
				res = getDafoeSession()
						.createSQLQuery(
								""
										+ "SELECT m12_terminoconceptoccurrence.* FROM m12_terminoconceptoccurrence "
										+ "INNER JOIN m11_termoccurrence ON m11_termoccurrence.id = m12_terminoconceptoccurrence.termoccur_id "
										+ "WHERE "
										+ "((m11_termoccurrence.sentence_id = "
										+ sentence.get(0).getId()
										+ ")"
										+ " AND "
										+ "(m12_terminoconceptoccurrence.terminoconcept_id = "
										+ tc.getId() + "))").addEntity(
								TerminoConceptOccurrenceImpl.class).list();

			}
		}

		if (res != null) {

			try {

				dTx = getDafoeSession().beginTransaction();

				Iterator<ITerminoConceptOccurrence> it = res.iterator();
				ITerminoConceptOccurrence tcOcc;

				while (it.hasNext()) {
					tcOcc = it.next();

					getDafoeSession().delete(tcOcc);

				}

				dTx.commit();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//

	/**
	 * Creates annotation for a tc.
	 *
	 * @param tc the tc
	 * @param annotType the annot type
	 * @param value the value
	 */
	public static void createTCAnnotation(ITerminoConcept tc, String annotType,
			String value) {
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			ITerminoOntoAnnotationType toAnnotationType = getAnnotationType(annotType);

			ITerminoOntoAnnotation annot = new TerminoConceptAnnotationImpl();

			annot.setTerminoOntoAnnotationType(toAnnotationType);

			annot.setValue(value);

			tc.addAnnotation(annot);

			getDafoeSession().save(annot);

			dTx.commit();

			getDafoeSession().refresh(tc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Gets annotation type object by label.
	 *
	 * @param annotType the annot type
	 * @return the annotation type
	 */
	@SuppressWarnings("unchecked")
	private static ITerminoOntoAnnotationType getAnnotationType(String annotType) {
		//Transaction dTx;
		List<ITerminoOntoAnnotationType> tmp = new ArrayList<ITerminoOntoAnnotationType>();
		ITerminoOntoAnnotationType res = null;
		try {

			tmp = getDafoeSession()
					.createSQLQuery(
							""
									+ "SELECT m22_termino_onto_annotation_type.* FROM m22_termino_onto_annotation_type "
									+ "WHERE "
									+ "m22_termino_onto_annotation_type.label = '"
									+ annotType + "'").addEntity(
							TerminoOntoAnnotationTypeImpl.class).list();

			if (tmp != null) {

				if (tmp.size() == 1) {

					res = tmp.get(0);
				}

			}

			return res;

		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}

	//

	/**
	 * Update annotation.
	 *
	 * @param annot the annot
	 * @param value the value
	 */
	public static void updateAnnotation(ITerminoOntoAnnotation annot,
			String value) {
		Transaction dTx;
		try {

			dTx = getDafoeSession().beginTransaction();

			annot.setValue(value);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Delete tc annotation.
	 *
	 * @param tc the tc
	 * @param annot the annot
	 */
	public static void deleteTCAnnotation(ITerminoConcept tc,
			ITerminoOntoAnnotation annot) {
		Transaction dTx;

		try {

			tc.removeAnnotation(annot);

			dTx = getDafoeSession().beginTransaction();

			getDafoeSession().delete(annot);

			dTx.commit();

			getDafoeSession().refresh(tc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Delete all tc annotations.
	 *
	 * @param tc the tc
	 */
	public static void deleteAllTCAnnotations(ITerminoConcept tc) {
		Transaction dTx;
		Set<ITerminoOntoAnnotation> annotations;

		try {

			dTx = getDafoeSession().beginTransaction();

			annotations = tc.getAnnotations();

			Iterator<ITerminoOntoAnnotation> it = annotations.iterator();

			while (it.hasNext()) {

				ITerminoOntoAnnotation annotation = it.next();

				it.remove();

				tc.getAnnotations().remove(annotation);

				getDafoeSession().delete(annotation);

			}

			dTx.commit();

			getDafoeSession().refresh(tc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Created mapping between tc and  class.
	 *
	 * @param tc the tc
	 * @param cl the cl
	 */
	public static void linkTCtoClass(ITerminoConcept tc, IClass cl) {
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			tc.addMappedOntoObject(cl);

			dTx.commit();

			getDafoeSession().refresh(cl);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 

	/**
	 * Gets the class by id.
	 *
	 * @param id the id
	 * @return the class by id
	 */
	public static IClass getClassById(int id) {

		IClass cl;

		cl = (IClass) getDafoeSession().load(ClassImpl.class, id);

		return cl;
	}

	//

	/**
	 * Created mapping between tc and object property.
	 *
	 * @param tc the tc
	 * @param prop the prop
	 */
	public static void linkTCtoObjectProperty(ITerminoConcept tc,
			IObjectProperty prop) {
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			tc.addMappedOntoObject(prop);

			dTx.commit();

			getDafoeSession().refresh(prop);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 

	/**
	 * Gets the object property by id.
	 *
	 * @param id the id
	 * @return the object property by id
	 */
	public static IObjectProperty getObjectPropertyById(int id) {

		IObjectProperty prop;

		prop = (IObjectProperty) getDafoeSession().load(
				ObjectPropertyImpl.class, id);

		return prop;
	}

	//

	/**
	 * Created mapping between tc and datatype property.
	 *
	 * @param tc the tc
	 * @param prop the prop
	 */
	public static void linkTCtoDatatypeProperty(ITerminoConcept tc,
			IDatatypeProperty prop) {
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			tc.addMappedOntoObject(prop);

			dTx.commit();

			getDafoeSession().refresh(prop);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 

	/**
	 * Gets the data type property by id.
	 *
	 * @param id the id
	 * @return the data type property by id
	 */
	public static IDatatypeProperty getDataTypePropertyById(int id) {

		IDatatypeProperty prop;

		prop = (IDatatypeProperty) getDafoeSession().load(
				DatatypePropertyImpl.class, id);

		return prop;
	}

	//

	/**
	 * Delete mapping between a termino-concept a an OntoObject.
	 *
	 * @param tc the tc
	 * @param ontoObject the onto object
	 */
	public static void deleteTCtoOntoObject(ITerminoConcept tc,
			IOntoObject ontoObject) {
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			tc.removeMappedOntoObject(ontoObject);

			dTx.commit();

			getDafoeSession().refresh(tc);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Gets all termino ontologies from database.
	 *
	 * @return the termino ontologies
	 */
	@SuppressWarnings("unchecked")
	public static List<ITerminoOntology> getTerminoOntologies() {
		try {
			return (List<ITerminoOntology>)getDafoeSession().createCriteria(TerminoOntologyImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ITerminoOntology>();
		}
	}
}
