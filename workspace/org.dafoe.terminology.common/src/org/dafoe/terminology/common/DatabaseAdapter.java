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

package org.dafoe.terminology.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.controler.factory.ControlerFactoryImpl;
import org.dafoe.controler.utility.UtilTools;
import org.dafoe.framework.core.terminological.model.ICorpus;
import org.dafoe.framework.core.terminological.model.IMethod;
import org.dafoe.framework.core.terminological.model.IOriginRelation;
import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermOccurrence;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITermSaillance;
import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.framework.core.terminological.model.LINGUISTIC_STATUS;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.core.terminoontological.model.ITerminoConcept;
import org.dafoe.framework.core.terminoontological.model.ITerminoConceptOccurrence;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.MethodImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermRelationImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermSaillanceImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TerminologyImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TypeRelationTerminoImpl;
import org.dafoe.framework.provider.hibernate.terminoontological.model.impl.TerminoConceptOccurrenceImpl;
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

	
	private static List<ITerm> terms = new ArrayList<ITerm>();

	private static ITerminology terminology = null;


	//

	/**
	 * Gets the current created dafoe session.
	 *
	 * @return the dafoe session
	 */
	@SuppressWarnings("static-access")
	public static Session getDafoeSession() {
		return org.dafoe.terminology.common.Activator.getDefault().dafoeSession;
	}
	
	//

	/**
	 * Inits the terminology.
	 */
	public static void initTerminology(){
		ITerminology termino = null;
		
		Transaction dTx;
		
		try {
			
			dTx = getDafoeSession().beginTransaction();
			
			termino = TerminologyImpl.getInstance();
			termino.setName("Default Terminology");
			//ES: definir un namespace par defaut
			termino.setNameSpace("");
			termino.setLanguage("");

			dTx.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		terminology = termino; 
		
	}
	
	//

	/**
	 * Load all terms from database.
	 */
	@SuppressWarnings("unchecked")
	public static void loadTerms() {

		terms = new ArrayList<ITerm>();

		try {

			ICorpus currentCorpus = ControlerFactoryImpl
					.getTerminologyControler().getcurrentCorpus();

			System.out.println("currentCorpus = " + currentCorpus);

			if (currentCorpus != null) {

				int corpusId = currentCorpus.getId();
				System.out.println("corpusId = " + corpusId);

				SQLQuery query = getDafoeSession().createSQLQuery(
						"SELECT * FROM m21_term WHERE terminology_id = '"
								+ corpusId + "'").addEntity(TermImpl.class);

				if (query != null) {

					terms = query.list();

				}

				query = getDafoeSession().createSQLQuery(
						"SELECT * FROM m21_terminology WHERE id = '" + corpusId
								+ "'").addEntity(TerminologyImpl.class);

			} else {

				terms = getDafoeSession().createCriteria(TermImpl.class).list();
			}

			System.out.println("# terms = " + terms.size());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Gets the terms from CPU memory.
	 *
	 * @return the terms
	 */
	public static List<ITerm> getTerms() {
		return terms;
	}

	//

	/**
	 * Creates a term from a label and persist it in the database.
	 *
	 * @param label the label
	 * @param status the status
	 * @param linguisticStatus the linguistic status
	 * @return the created term term
	 */
	public static ITerm createTermFromLabel(String label, TERMINO_OBJECT_STATE status,
			LINGUISTIC_STATUS linguisticStatus) {
		ITerm newTerm = new TermImpl();
		Transaction dTx;

		System.out.println(terminology.getName());
		
		try {

			dTx = getDafoeSession().beginTransaction();

			newTerm.setLabel(label);

			newTerm.setState(status);

			newTerm.setLinguisticStatus(linguisticStatus);

			System.out.println("====================" + terminology.getName());
			
			terminology.addTerminoObject(newTerm);

			// getDafoeSession().save(newTerm);

			getDafoeSession().save(terminology);

			dTx.commit();

			getDafoeSession().refresh(terminology);

			terms.add(newTerm);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return newTerm;
	}

	//

	/**
	 * Creates terms from a list of label and save them in the database.
	 *
	 * @param label the label
	 * @param status the status
	 * @param linguisticStatus the linguistic status
	 * @return the i term
	 */
	public static ITerm createTermsFromLabel(String[] label, TERMINO_OBJECT_STATE status,
			LINGUISTIC_STATUS linguisticStatus) {
		ITerm newTerm = null;
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			for (int i = 0; i < label.length; i++) {

				newTerm = new TermImpl();

				newTerm.setLabel(label[i]);

				newTerm.setState(status);

				newTerm.setLinguisticStatus(linguisticStatus);

				getDafoeSession().save(newTerm);

				terms.add(newTerm);

			}

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return newTerm;
	}

	//

	/**
	 * Creates the variant from label.
	 *
	 * @param starTerm the star term
	 * @param label the label
	 * @param linguisticStatus the linguistic status
	 * @return the i term
	 */
	public static ITerm createVariantFromLabel(ITerm starTerm, String label,
			LINGUISTIC_STATUS linguisticStatus) {
		//VT: use type-safe approach
		Transaction dTx;
		ITerm variant = null;

		try {

			variant = createTermFromLabel(label, starTerm.getState(),
					linguisticStatus);

			dTx = getDafoeSession().beginTransaction();

			starTerm.addVariante(variant);

			Set<ITerminoConcept> tcs = starTerm.getMappedTerminoConcepts();

			Iterator<ITerminoConcept> it = tcs.iterator();

			while (it.hasNext()) {
				// variant.addRelatedTerminoConcept(it.next());
				it.next().addMappedTerm(variant);
			}

			dTx.commit();

			// Allow to update the inverse relationship => to be improved in the
			// framework directly
			getDafoeSession().refresh(variant);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return variant;
	}

	//

	/**
	 * Detach variants from star term.
	 *
	 * @param variants the variants
	 */
	public static void detachVariants(List<ITerm> variants) {
		Transaction dTx;
		ITerm variant;

		try {

			dTx = getDafoeSession().beginTransaction();

			Iterator<ITerm> it = variants.iterator();
			ITerm starTerm = null;

			while (it.hasNext()) {
				variant = it.next();
				starTerm = variant.getStarTerm();
				starTerm.removeVariante(variant);

			}

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 

	/**
	 * Delete all terms with deleted status.
	 */
	public static void deleteDeletedTerms() {
		List<ITerm> deletedTerms = new ArrayList<ITerm>();
		Iterator<ITerm> it = terms.iterator();
		ITerm tmp;

		// retrieve all the terms for which the status is DELETED
		while (it.hasNext()) {
			tmp = it.next();

			if ((tmp.getState() == TERMINO_OBJECT_STATE.DELETED)) {
				deletedTerms.add(tmp);
			}
		}

		deleteTerms(deletedTerms);

	}

	//

	/**
	 * Gets the terms with deleted status from database.
	 *
	 * @return the terms to be deleted
	 */
	@SuppressWarnings("unchecked")
	public static List<ITerm> getTermsToBedeleted() {
		List<ITerm> termsToBeDeleted = new ArrayList<ITerm>();

		try {

			SQLQuery query = getDafoeSession().createSQLQuery(
					"SELECT * FROM m21_term WHERE status = '"
							+ TERMINO_OBJECT_STATE.DELETED.getValue() + "'").addEntity(
					TermImpl.class);

			if (query != null) {
				termsToBeDeleted = query.list();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return termsToBeDeleted;
	}

	//

	/**
	 * Creates terms from a list of labels and persist them in the database.
	 *
	 * @param labels the labels
	 */
	public static void createTermsFromLabels(String[] labels) {
		//List<ITerm> terms = new ArrayList<ITerm>();
		//ITerm newTerm = null;

		try {
			for (int i = 0; i < labels.length; i++) {

				//newTerm =
					createTermFromLabel(labels[i],
						TERMINO_OBJECT_STATE.STUDIED, LINGUISTIC_STATUS.TERM);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Configure delete term.
	 *
	 * @param term the term
	 */
	private static void configureDeleteTerm(ITerm term) {

		deleteRelations(UtilTools.setToList(term.getRelationsWhereInTerm1()));
		deleteRelations(UtilTools.setToList(term.getRelationsWhereInTerm2()));

		Set<ITerm> variants = term.getVariantes();
		Iterator<ITerm> it = variants.iterator();

		while (it.hasNext()) {

			ITerm var = it.next();

			it.remove();

			term.removeVariante(var);

		}

		ITermSaillance saillance = term.getSaillanceCriteria();

		deleteSaillanceCriteria(saillance);

	}

	// 

	/**
	 * Delete a list terms from database.
	 *
	 * @param deletedTerms the deleted terms
	 */
	public static void deleteTerms(List<ITerm> deletedTerms) {

		Transaction dTx;
		ITerm term;

		if (deletedTerms != null) {
			try {

				Iterator<ITerm> it = deletedTerms.iterator();

				while (it.hasNext()) {

					term = it.next();

					configureDeleteTerm(term);

				}

				dTx = getDafoeSession().beginTransaction();

				it = deletedTerms.iterator();

				while (it.hasNext()) {

					term = it.next();

					terminology.removeTerminoObject(term);

					terms.remove(term);
				}

				dTx.commit();

			} catch (Exception e) {

				e.printStackTrace();

			}
		}

	}

	// 

	/**
	 * Delete saillance criteria of a term.
	 *
	 * @param saillance the saillance
	 */
	public static void deleteSaillanceCriteria(ITermSaillance saillance) {

		Transaction dTx;

		try {

			if (saillance != null) {

				dTx = getDafoeSession().beginTransaction();

				Object o = getDafoeSession().load(TermSaillanceImpl.class,
						saillance.getId());

				getDafoeSession().delete(o);

				dTx.commit();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 

	/**
	 * Make star term.
	 *
	 * @param oldVariant the old variant
	 */
	public static void makeStarTerm(ITerm oldVariant) {
		Transaction dTx;
		ITerm oldStarTerm = null;
		Iterator<ITerm> it;

		try {

			dTx = getDafoeSession().beginTransaction();

			oldStarTerm = oldVariant.getStarTerm();

			Set<ITerm> variants = oldStarTerm.getVariantes();
			oldStarTerm.removeVariante(oldVariant);

			it = variants.iterator();

			while (it.hasNext()) {
				ITerm term = it.next();
				oldVariant.addVariante(term);
			}

			oldVariant.addVariante(oldStarTerm);

			oldStarTerm.getVariantes().clear();

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Return all the relations in which both the term and its variants are
	// involved

	/**
	 * Gets the relations from term2.
	 *
	 * @param term the term
	 * @return the relations from term2
	 */
	@SuppressWarnings("unchecked")
	public static List<ITermRelation> getRelationsFromTerm2(ITerm term) {
		try {
			List<ITermRelation> relations = null;
			List<ITermRelation> tmp = null;
			Iterator<ITermRelation> it;
			ITermRelation rel;

			relations = new ArrayList<ITermRelation>();

			// ES: ne fonctionne pas actuellement
			// List<ITermRelation> relationsTerm1 =
			// UtilTools.setToList(term.getRelationsTerm1());
			// List<ITermRelation> relationsTerm2 =
			// UtilTools.setToList(term.getRelationsTerm2());

			// ES patch

			tmp = getDafoeSession().createCriteria(TermRelationImpl.class)
					.list();
			it = tmp.iterator();

			while (it.hasNext()) {
				rel = it.next();
				if ((rel.getTerm1() == term)
						|| (rel.getTerm2() == term
								|| (term.getVariantes()
										.contains(rel.getTerm1())) || (term
								.getVariantes().contains(rel.getTerm2())))) {
					relations.add(rel);
				}
			}

			// relationsTerm1.addAll(relationsTerm2);

			// relations = relationsTerm1;

			return relations;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ITermRelation>();
		}
	}

	// 

	/**
	 * Gets all terminological relation types from database.
	 *
	 * @return the relation types
	 */
	@SuppressWarnings("unchecked")
	public static List<ITypeRelationTermino> getRelationTypes() {
		List<ITypeRelationTermino> relationTypes = null;

		try {

			relationTypes = getDafoeSession().createCriteria(
					TypeRelationTerminoImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return relationTypes;
	}

	// 

	/**
	 * Gets all methods from database.
	 *
	 * @return the methods
	 */
	@SuppressWarnings("unchecked")
	public static List<IMethod> getMethods() {
		List<IMethod> methods = null;

		try {

			methods = getDafoeSession().createCriteria(IMethod.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return methods;
	}

	//

	/**
	 * Gets the variantes from term.
	 *
	 * @param term the term
	 * @return the variantes from term
	 */
	public static List<ITerm> getVariantesFromTerm(ITerm term) {
		Set<ITerm> variants = null;
		List<ITerm> result = null;

		try {

			variants = term.getVariantes();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (variants != null) {
			result = new ArrayList<ITerm>(variants);
		}

		return result;
	}

	// 

	/**
	 * Configure delete relation.
	 *
	 * @param relation the relation
	 */
	private static void configureDeleteRelation(ITermRelation relation) {

		//int relationId = relation.getId();
		ITerm term1 = relation.getTerm1();
		ITerm term2 = relation.getTerm2();
		ITypeRelationTermino relationType = relation.getTypeRel();

		Set<IOriginRelation> originRelations = relation.getOriginRelation();
		Set<ITermRelation> relationsFromTerm1 = term1.getRelationsWhereInTerm1();
		Set<ITermRelation> relationsFromTerm2 = term2.getRelationsWhereInTerm2();
		Set<ITermRelation> relationsFromType = relationType.getTermRelations();
		//Set<ITerminoConceptRelation> tcRelations = relation.getMappedTerminoConceptRelations();

		relationsFromTerm1.remove(relation);
		relationsFromTerm2.remove(relation);
		relationsFromType.remove(relation);

		deleteOriginRelations(UtilTools.setToList(originRelations));

	}

	// 

	/**
	 * Delete relations from database.
	 *
	 * @param relations the relations
	 */
	public static void deleteRelations(List<ITermRelation> relations) {
		Transaction dTx;
		Iterator<ITermRelation> it;

		try {

			it = relations.iterator();

			while (it.hasNext()) {

				ITermRelation relation = it.next();

				configureDeleteRelation(relation);

			}

			it = relations.iterator();

			dTx = getDafoeSession().beginTransaction();

			while (it.hasNext()) {

				ITermRelation relation = it.next();

				terminology.removeTerminoObject(relation);
			}

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 

	/**
	 * Configure delete origin relation.
	 *
	 * @param originRelation the origin relation
	 */
	private static void configureDeleteOriginRelation(
			IOriginRelation originRelation) {

		IMethod method = originRelation.getMethod();
		ITermRelation rt = originRelation.getTermRelation();
		ISentence sentence = originRelation.getSentence();

		method.getOriginRelations().remove(originRelation);
		rt.getOriginRelation().remove(originRelation);
		sentence.getOriginRelations().remove(originRelation);

	}

	// 

	/**
	 * Delete origin relations.
	 *
	 * @param origins the origins
	 */
	public static void deleteOriginRelations(List<IOriginRelation> origins) {
		Transaction dTx;
		try {

			if (origins.size() > 0) {
				dTx = getDafoeSession().beginTransaction();

				Iterator<IOriginRelation> it = origins.iterator();

				while (it.hasNext()) {
					IOriginRelation origin = it.next();

					configureDeleteOriginRelation(origin);

					Object o = getDafoeSession().load(TermRelationImpl.class,
							origin.getId());

					getDafoeSession().delete(o);

				}

				dTx.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 

	/**
	 * Configure delete relation type.
	 *
	 * @param rtType the rt type
	 */
	private static void configureDeleteRelationType(ITypeRelationTermino rtType) {

		Set<IMethod> methods = rtType.getMethods();

		Iterator<IMethod> it = methods.iterator();

		while (it.hasNext()) {

			IMethod method = it.next();

			method.getTypeRelation().remove(rtType);

		}

		deleteRelations(UtilTools.setToList(rtType.getTermRelations()));

	}

	// 

	/**
	 * Delete relation types from database.
	 *
	 * @param rtTypes the rt types
	 */
	public static void deleteRelationTypes(List<ITypeRelationTermino> rtTypes) {
		Transaction dTx;

		try {
			if (rtTypes != null) {

				Iterator<ITypeRelationTermino> it = rtTypes.iterator();

				while (it.hasNext()) {

					ITypeRelationTermino rtType = it.next();

					configureDeleteRelationType(rtType);

				}

				dTx = getDafoeSession().beginTransaction();

				it = rtTypes.iterator();

				while (it.hasNext()) {

					ITypeRelationTermino rtType = it.next();

					Object o = getDafoeSession().load(
							TypeRelationTerminoImpl.class, rtType.getId());

					getDafoeSession().delete(o);

				}

				dTx.commit();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Group terms around a star term.
	 *
	 * @param starTerm the star term
	 * @param variants the variants
	 */
	public static void groupTerms(ITerm starTerm, List<ITerm> variants) {
		Transaction dTx;
		Iterator<ITerm> it;
		ITerm variant;

		try {

			dTx = getDafoeSession().beginTransaction();

			it = variants.iterator();

			Set<ITerminoConcept> tcs = starTerm.getMappedTerminoConcepts();

			while (it.hasNext()) {
				variant = it.next();

				// If a term to be grouped has variants, each of these
				// variants becomes
				// a variant of the star term

				Set<ITerm> vars = variant.getVariantes();

				if (vars != null) {

					Iterator<ITerm> itVars = vars.iterator();

					while (itVars.hasNext()) {
						ITerm var = itVars.next();
						starTerm.addVariante(var);
						// TMP: var.setStarTerm(starTerm);
						var.setState(starTerm.getState());
					}

				}

				starTerm.addVariante(variant);
				// variant.setStarTerm(starTerm);
				variant.setState(starTerm.getState());
				variant.getVariantes().clear();

				// linked TCs are retrieved
				Set<ITerminoConcept> variantRelatedTCs = variant
						.getMappedTerminoConcepts();

				if (variantRelatedTCs != null) {

					tcs.addAll(variantRelatedTCs);

				}
			}

			// starTerm and its variant are all linked to the retrieved TCs

			Set<ITerm> groupTerms = new HashSet<ITerm>();
			groupTerms.add(starTerm);
			groupTerms.addAll(starTerm.getVariantes());

			Iterator<ITerm> groupTermsIt = groupTerms.iterator();

			while (groupTermsIt.hasNext()) {
				ITerm term = groupTermsIt.next();
				// System.out.println("T: " + term.getLabel());

				if (tcs != null) {
					Iterator<ITerminoConcept> ittc = tcs.iterator();

					while (ittc.hasNext()) {
						ITerminoConcept tc = ittc.next();
						// System.out.println("TC: " + tc.getLabel());
						term.addMappedTerminoConcept(tc);
					}

				}

			}

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Ungroup terms.
	 *
	 * @param terms the terms
	 */
	public static void unGroupTerms(List<ITerm> terms) {
		Transaction dTx;
		Iterator<ITerm> it;
		ITerm starTerm, variant;

		try {

			dTx = getDafoeSession().beginTransaction();

			it = terms.iterator();

			while (it.hasNext()) {
				starTerm = it.next();

				Set<ITerm> vars = starTerm.getVariantes();

				if (vars != null) {

					Iterator<ITerm> itVars = vars.iterator();

					while (itVars.hasNext()) {

						variant = itVars.next();

						itVars.remove();

						starTerm.removeVariante(variant);

					}

				}

			}

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 

	/**
	 * Update term status.
	 *
	 * @param term the term
	 * @param status the status
	 */
	public static void updateTermStatus(ITerm term, TERMINO_OBJECT_STATE status) {
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			term.setState(status);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 

	/**
	 * Update term label.
	 *
	 * @param term the term
	 * @param label the label
	 */
	public static void updateTermLabel(ITerm term, String label) {
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			term.setLabel(label);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 

	/**
	 * Update status of a list of terms.
	 *
	 * @param terms the terms
	 * @param status the status
	 */
	public static void updateTermsStatus(List<ITerm> terms, TERMINO_OBJECT_STATE status) {
		Iterator<ITerm> it = terms.iterator();
		ITerm term, variante;
		Set<ITerm> variantes = null;

		while (it.hasNext()) {
			term = it.next();
			updateTermStatus(term, status);

			variantes = term.getVariantes();

			if (variantes != null) {
				Iterator<ITerm> itVars = variantes.iterator();

				while (itVars.hasNext()) {
					variante = itVars.next();
					updateTermStatus(variante, status);
				}

			}

		}

	}

	// 

	/**
	 * Update linguistic status of a term.
	 *
	 * @param term the term
	 * @param linguisticStatus the linguistic status
	 */
	public static void updateTermLinguisticStatus(ITerm term,
			LINGUISTIC_STATUS linguisticStatus) {
		//VT: use type-safe approach
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			
			//term.setNamedEntity(linguisticStatus);
             term.setLinguisticStatus(linguisticStatus);
			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// 

	/**
	 * Update linguistic status of a list of term.
	 *
	 * @param terms the terms
	 * @param linguisticStatus the linguistic status
	 */
	public static void updateTermLinguisticStatus(List<ITerm> terms,
			LINGUISTIC_STATUS linguisticStatus) {
		//VT: use type-safe approach
		Set<ITerm> variantes = null;

		for(ITerm term: terms) {
			
			updateTermLinguisticStatus(term, linguisticStatus);

			// also change the linguistic status of the variantes
			variantes = term.getVariantes();

			if (variantes != null) {
				
				for(ITerm variante: variantes){
					
					updateTermLinguisticStatus(variante, linguisticStatus);
				}

			}

		}
	}
	// 

	/**
	 * Update method.
	 *
	 * @param method the method
	 * @param tool the tool
	 * @param relationType the relation type
	 */
	public static void updateMethod(IMethod method, String tool,
			ITypeRelationTermino relationType) {
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			if (tool != null) {

				method.setTool(tool);

			}

			if (relationType != null) {

				method.addTypeRelationTermino(relationType);
			}

			dTx.commit();

			if (relationType != null) {
				getDafoeSession().refresh(relationType);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 

	/**
	 * Configure delete method.
	 *
	 * @param method the method
	 */
	private static void configureDeleteMethod(IMethod method) {

		Set<ITypeRelationTermino> rtTypes = method.getTypeRelation();

		Iterator<ITypeRelationTermino> it = rtTypes.iterator();

		while (it.hasNext()) {

			ITypeRelationTermino rtType = it.next();

			rtType.getMethods().remove(method);

			getDafoeSession().refresh(rtType);

		}

		Set<IOriginRelation> origins = method.getOriginRelations();

		deleteOriginRelations(UtilTools.setToList(origins));

	}

	// 

	/**
	 * Delete a list of methods.
	 *
	 * @param methods the list of methods to delete
	 */
	public static void deleteMethods(List<IMethod> methods) {
		Transaction dTx;

		if (methods != null) {

			try {

				Iterator<IMethod> it = methods.iterator();

				while (it.hasNext()) {

					IMethod method = it.next();

					configureDeleteMethod(method);

				}

				dTx = getDafoeSession().beginTransaction();

				it = methods.iterator();

				while (it.hasNext()) {

					IMethod method = it.next();

					configureDeleteMethod(method);

					Object o = getDafoeSession().load(MethodImpl.class,
							method.getId());

					getDafoeSession().delete(o);

				}

				dTx.commit();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 

	/**
	 * Creates terminological relation from another terminological relation.
	 *
	 * @param relation the old relation
	 * @return the new relation
	 */
	public static ITermRelation createRelation(ITermRelation relation) {
		Transaction dTx;
		//ITerm term1, term2;
		//ITypeRelationTermino rtType;
		ITermRelation newRelation = null;

		if (relation != null) {

			try {

				dTx = getDafoeSession().beginTransaction();

				newRelation = new TermRelationImpl();

				newRelation.setTerm1(relation.getTerm1());
				newRelation.setTerm2(relation.getTerm2());
				newRelation.setTypeRel(relation.getTypeRel());
				newRelation.setState(TERMINO_OBJECT_STATE.VALIDATED);
				terminology.addTerminoObject(newRelation);

				getDafoeSession().save(terminology);

				dTx.commit();

				getDafoeSession().refresh(terminology);

				getDafoeSession().refresh(relation.getTerm1());

				getDafoeSession().refresh(relation.getTerm2());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return newRelation;
	}

	// 

	/**
	 * Update relation.
	 *
	 * @param relation the relation
	 * @param term1 the term1
	 * @param term2 the term2
	 * @param relationType the relation type
	 * @param status the status
	 */
	public static void updateRelation(ITermRelation relation, ITerm term1,
			ITerm term2, ITypeRelationTermino relationType, TERMINO_OBJECT_STATE status) {
		Transaction dTx;
		ITerm oldTerm1, oldTerm2;
		ITypeRelationTermino oldRelationType;

		if (relation != null) {

			try {

				dTx = getDafoeSession().beginTransaction();

				oldTerm1 = relation.getTerm1();
				oldTerm2 = relation.getTerm2();
				oldRelationType = relation.getTypeRel();

				if ((term1 != null) && (term1 != oldTerm1)) {
					relation.setTerm1(term1);
				}

				if ((term2 != null) && (term2 != oldTerm2)) {
					relation.setTerm2(term2);
				}

				if ((relationType != null) && (oldRelationType != relationType)) {
					relationType.addTermRelation(relation);
				}

				//VT: unuseful, due to type-safe
				/*
				if (status != -1) {
					relation.setState(status);
				}
				*/
				relation.setState(status);
				
				dTx.commit();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	//

	/**
	 * Creates the terminological relation type.
	 *
	 * @param label the label
	 * @return the i type relation termino
	 */
	public static ITypeRelationTermino createRelationType(String label) {
		ITypeRelationTermino relationType = new TypeRelationTerminoImpl();
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			relationType.setLabel(label);

			relationType.setState(TERMINO_OBJECT_STATE.VALIDATED);

			getDafoeSession().save(relationType);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return relationType;
	}

	//

	/**
	 * Creates the method.
	 *
	 * @param pattern the pattern
	 * @param tool the tool
	 * @return the method
	 */
	public static IMethod createMethod(String pattern, String tool) {
		IMethod method = new MethodImpl();
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			method.setPattern(pattern);

			method.setTool(tool);

			getDafoeSession().save(method);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return method;
	}

	//

	/**
	 * Unlink methods from a type of terminological relation.
	 *
	 * @param rtType the terminological relation type
	 * @param methods the methods
	 */
	public static void unlinkMethods(ITypeRelationTermino rtType,
			List<IMethod> methods) {
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			Iterator<IMethod> it = methods.iterator();

			while (it.hasNext()) {

				IMethod method = it.next();

				method.getTypeRelation().remove(rtType);
			}

			dTx.commit();

			getDafoeSession().refresh(rtType);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Update the label of a relation type.
	 *
	 * @param relationType the relation type to update
	 * @param label the new label
	 */
	public static void updateRelationType(ITypeRelationTermino relationType,
			String label) {
		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			relationType.setLabel(label);

			dTx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Gets termino concept related to a term by mapping.
	 *
	 * @param term the term
	 * @return termino-concepts related to the term 
	 */
	public static List<ITerminoConcept> getTerminoConceptFromTerm(ITerm term) {
		List<ITerminoConcept> terminoConcepts = new ArrayList<ITerminoConcept>();

		terminoConcepts = UtilTools.setToList(term.getMappedTerminoConcepts());

		return terminoConcepts;
	}

	//

//	/**
//	 * Gets the termino concept relation type from relation type.
//	 *
//	 * @param relationType the relation type
//	 * @return the termino concept relation type from relation type
//	 */
//	public static List<ITypeRelationTc> getTerminoConceptRelationTypeFromRelationType(
//			ITypeRelationTermino relationType) {
//		List<ITypeRelationTc> rtcTypes = new ArrayList<ITypeRelationTc>();
//
//		// ES: to be done
//
//		return rtcTypes;
//	}

	//

	/**
	 * Gets the terminological relation type object by id.
	 *
	 * @param id the id of the terminological relation type
	 * @return the terminological relation type
	 */
	public static ITypeRelationTermino getRTTypeById(int id) {

		Object o = getDafoeSession().load(TypeRelationTerminoImpl.class, id);

		return (ITypeRelationTermino) o;
	}

	// 

	/**
	 * Gets the terminological relation type object by label.
	 *
	 * @param label the label of the terminological relation type
	 * @return the terminological relation type
	 */
	@SuppressWarnings("unchecked")
	public static ITypeRelationTermino getRTTypeByLabel(String label) {
		List<ITypeRelationTermino> queryResult;
		ITypeRelationTermino rtType = null;

		try {

			SQLQuery query = getDafoeSession().createSQLQuery(
					"SELECT * FROM m21_type_relation WHERE label = '" + label
							+ "'").addEntity(TypeRelationTerminoImpl.class);

			if (query != null) {
				queryResult = query.list();

				if (queryResult.size() != 0) {
					rtType = queryResult.get(0);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rtType;
	}

	//

	/**
	 * Creates occurrences of a termino-concept from occurrences of a term.
	 *
	 * @param term the term
	 * @param tc the TerminoConcept
	 */
	public static void initTerminoConceptOccurrences(ITerm term,
			ITerminoConcept tc) {

		Transaction dTx;

		try {

			dTx = getDafoeSession().beginTransaction();

			Set<ITermOccurrence> termOccs = getTermOccurrences(term);
			Iterator<ITermOccurrence> itTermOccs = termOccs.iterator();

			while (itTermOccs.hasNext()) {
				ITermOccurrence termOcc = itTermOccs.next();

				ITerminoConceptOccurrence tcOcc = new TerminoConceptOccurrenceImpl();

				tcOcc.setRelatedTermOccurence(termOcc);
				termOcc.setRelatedTerminoConceptOccurrence(tcOcc);
				tc.addTerminoConceptOccurrence(tcOcc);

				getDafoeSession().save(tcOcc);

			}

			dTx.commit();

			getDafoeSession().refresh(tc);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 

	/**
	 * Gets occurrences of a term, also retrieve occurrence of all its variantes
	 *
	 * @param term the term
	 * @return  occurrences of term
	 */
	private static Set<ITermOccurrence> getTermOccurrences(ITerm term) {
		Set<ITermOccurrence> termOccs = new HashSet<ITermOccurrence>();

		termOccs.addAll(term.getTermOccurrences());

		Set<ITerm> vars = term.getVariantes();
		Iterator<ITerm> it = vars.iterator();

		while (it.hasNext()) {
			termOccs.addAll(it.next().getTermOccurrences());
		}

		return termOccs;
	}

	//

//	/**
//	 * Removes the termino concept occurrences.
//	 *
//	 * @param term the term
//	 * @param tc the terminoconcept
//	 */
//	public static void removeTerminoConceptOccurrences(ITerm term,
//			ITerminoConcept tc) {
//		Transaction dTx;
//
//		try {
//
//			dTx = getDafoeSession().beginTransaction();
//
//			dTx.commit();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
}
