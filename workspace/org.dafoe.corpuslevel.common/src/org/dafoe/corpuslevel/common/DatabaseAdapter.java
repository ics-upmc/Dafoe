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
package org.dafoe.corpuslevel.common;

import java.util.ArrayList;
import java.util.List;

import org.dafoe.framework.core.terminological.model.IDocument;
import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermOccurrence;
import org.dafoe.framework.core.terminological.model.ITermSaillance;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.DocumentImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.SentenceImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermOccurrenceImpl;
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

	/** The docs. */
	protected static List<IDocument> docs = new ArrayList<IDocument>();
	
	/** The sentences. */
	protected static List<ISentence> sentences = new ArrayList<ISentence>();
	
	/** The terms. */
	protected static List<ITerm> terms = new ArrayList<ITerm>();

	/**
	 * Gets the current created dafoe session.
	 *
	 * @return the dafoe session
	 */
	public static Session getDafoeSession() {
		return SessionFactoryImpl.getCurrentDynamicSession();
	}

	//

	/**
	 * Load all documents from the database.
	 */
	@SuppressWarnings("unchecked")
	public static void loadDocuments() {
		try {
			// docs =
			// (List<IDocument>)getDafoeSession().createCriteria(IDocument.class).list();
			docs = (List<IDocument>) getDafoeSession().createCriteria(
					DocumentImpl.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Load documents matching with a give research criteria.
	 *
	 * @param research the research
	 */
	@SuppressWarnings("unchecked")
	public static void loadDocuments(String recherche) {
		if (recherche == "") {
			try {
				// docs =
				// (List<IDocument>)getDafoeSession().createCriteria(IDocument.class).list();
				docs = (List<IDocument>) getDafoeSession().createCriteria(
						DocumentImpl.class).list();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				docs = getDafoeSession().createSQLQuery(
						"SELECT m11_document.* "
								+ "FROM m11_document WHERE name LIKE '"
								+ recherche + "%' ORDER BY num_order")
						.addEntity(DocumentImpl.class).list();
				// VT : HQL query to hide the logical structure of the database
//				docs = getDafoeSession()
//						.createQuery(
//								"FROM DocumentImpl as doc WHERE doc.name LIKE ? ORDER BY doc.num_order")
//						.setString(0, recherche).list();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	//

	/**
	 * Gets the documents.
	 *
	 * @return the documents
	 */
	public static List<IDocument> getDocuments() {
		return docs;
	}

	//

	/**
	 * Load all sentences from the database.
	 */
	@SuppressWarnings("unchecked")
	public static void loadSentences() {
		try {
			// sentences
			// =(List<ISentence>)getDafoeSession().createCriteria(ISentence.class).list();
			sentences = (List<ISentence>) getDafoeSession().createCriteria(
					SentenceImpl.class).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Load sentences of a document.
	 *
	 * @param doc the doc
	 */
	@SuppressWarnings("unchecked")
	public static void loadSentences(IDocument doc) {
		sentences = (List<ISentence>) doc.getSentences();
	}

	//

	/**
	 * Align sentences.
	 *
	 * @param currentTerm the current term
	 */
	@SuppressWarnings("unchecked")
	public static void alignSentences(ITerm currentTerm) {
		sentences = new ArrayList<ISentence>();
		if (currentTerm != null) {

			sentences = getDafoeSession()
					.createSQLQuery(
							"SELECT m11_sentence.* "
									+ "FROM m11_sentence inner join m11_termoccurrence on m11_termoccurrence.sentence_id =m11_sentence.id"
									+ " WHERE m11_termoccurrence.term_id = "
									+ currentTerm.getId().toString()
									+ " ORDER BY num_order").addEntity(
							SentenceImpl.class).list();

		}

	}

	//

	/**
	 * Sequence sentences.
	 *
	 * @param currentTerm the current term
	 */
	@SuppressWarnings("unchecked")
	public static void sequenceSentences(ITerm currentTerm) {
		sentences = new ArrayList<ISentence>();
		try {
			sentences = getDafoeSession()
					.createSQLQuery(
							"SELECT m11_sentence.* FROM m11_sentence WHERE num_order>= "
									+ "(SELECT min( m11_sentence.num_order) FROM m11_sentence inner join m11_termoccurrence "
									+ " on m11_termoccurrence.sentence_id =m11_sentence.id "
									+ "WHERE m11_termoccurrence.term_id ="
									+ currentTerm.getId().toString()
									+ " GROUP BY m11_sentence.num_order ORDER BY num_order )  "
									+ "ORDER BY num_order ").addEntity(
							SentenceImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//

	/**
	 * Gets the sentences.
	 *
	 * @return the sentences
	 */
	public static List<ISentence> getSentences() {
		return sentences;
	}

	//

	/**
	 * Load all terms from the database.
	 */
	@SuppressWarnings("unchecked")
	public static void loadTerms() {

		terms = new ArrayList<ITerm>();

		try {
			// terms
			// =(List<ITerm>)getDafoeSession().createCriteria(ITerm.class).list();

			 terms = getDafoeSession().createSQLQuery("SELECT m21_term.* "+
			 " FROM m21_term ORDER BY label").addEntity(TermImpl.class).list();
			
			// VT: HQL  ----- ok
//			terms = getDafoeSession().createQuery(
//					"FROM TermImpl as term ORDER BY term.Label ").list();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Load terms.
	 *
	 * @param sentence the sentence
	 */
	@SuppressWarnings("unchecked")
	public static void loadTerms(ISentence sentence) {

		terms = new ArrayList<ITerm>();

		try {
			terms = getDafoeSession()
					.createSQLQuery(
							"SELECT m21_term.* "
									+ " FROM m21_term "
									+ " inner join m11_termoccurrence on m11_termoccurrence.term_id=m21_term.id "
									+ " WHERE m11_termoccurrence.sentence_id="
									+ sentence.getId() + " ORDER BY label")
					.addEntity(TermImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Load terms.
	 *
	 * @param doc the doc
	 */
	@SuppressWarnings("unchecked")
	public static void loadTerms(IDocument doc) {

		terms = new ArrayList<ITerm>();

		try {
			terms = getDafoeSession()
					.createSQLQuery(
							"SELECT DISTINCT m21_term.* "
									+ "FROM m21_term inner join m11_termoccurrence on m21_term.id= m11_termoccurrence.term_id "
									+ " inner join m11_sentence on m11_termoccurrence.sentence_id = m11_sentence.id"
									+ " WHERE m11_sentence.doc_id = "
									+ doc.getId().toString()
									+ " ORDER BY label").addEntity(
							TermImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Load terms.
	 *
	 * @param doc the doc
	 * @param research the research
	 */
	@SuppressWarnings("unchecked")
	public static void loadTerms(IDocument doc, String research) {

		terms = new ArrayList<ITerm>();

		try {
			terms = getDafoeSession()
					.createSQLQuery(
							"SELECT DISTINCT m21_term.* "
									+ "FROM m21_term inner join m11_termoccurrence on m21_term.id= m11_termoccurrence.term_id "
									+ " inner join m11_sentence on m11_termoccurrence.sentence_id =m11_sentence.id"
									+ " WHERE m11_sentence.doc_id = "
									+ doc.getId().toString()
									+ " and m21_term.label LIKE '" + research
									+ "%' ORDER BY label").addEntity(
							TermImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Load terms matching the give research criteria.
	 *
	 * @param research the research
	 */
	@SuppressWarnings("unchecked")
	public static void loadTerms(String research) {

		terms = new ArrayList<ITerm>();

		try {

			
			terms = getDafoeSession().createQuery(
					"SELECT * FROM m21_term WHERE label LIKE '" + research
							+ "%' ORDER BY label").list();
			
			
			// VT : HQL query to hide the logical structure of the database
//			terms = getDafoeSession()
//					.createQuery(
//							"FROM TermImpl as term WHERE term.Name LIKE ? ORDER BY term.Label")
//					.setString(0, recherche+"%").list();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//

	/**
	 * Gets the terms.
	 *
	 * @return the terms
	 */
	public static List<ITerm> getTerms() {
		return terms;
	}

	// VT: delete corpus

	/**
	 * Delete a given document from the database.
	 *
	 * @param doc the doc
	 */
	public static void deleteDocument(IDocument doc) {

		Transaction tx = getDafoeSession().beginTransaction();

		// save terms
		List<ITerm> terms = getTermFromDocument(doc);

		System.out.println("size= " + terms.size());

		getDafoeSession().delete(doc);

		// delete terms
		deleteTerms(terms);

		tx.commit();
	}

	//

	/**
	 * Delete a list of term from the database.
	 *
	 * @param terms the terms
	 */
	public static void deleteTerms(List<ITerm> terms) {

		for (ITerm term : terms) {
			// delete saillance before
			ITermSaillance saill = term.getSaillanceCriteria();
			if (saill != null) {
				getDafoeSession().delete(saill);
			}

			getDafoeSession().delete(term);

		}

	}

	//

	/**
	 * Gets the term of document from the database.
	 *
	 * @param doc the doc
	 * @return the term from document
	 */
	@SuppressWarnings("unchecked")
	public static List<ITerm> getTermFromDocument(IDocument doc) {

		List<ITerm> terms = new ArrayList<ITerm>();

		try {
			terms = getDafoeSession()
					.createSQLQuery(
							"SELECT DISTINCT m21_term.* "
									+ "FROM m21_term inner join m11_termoccurrence on m21_term.id= m11_termoccurrence.term_id "
									+ " inner join m11_sentence on m11_termoccurrence.sentence_id =m11_sentence.id"
									+ " WHERE m11_sentence.doc_id = "
									+ doc.getId().toString()
									+ " ORDER BY label").addEntity(
							TermImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return terms;
	}

	//

	/**
	 * Gets the occurrences of a term in a sentence from the database.
	 *
	 * @param term the term
	 * @param sentence the sentence
	 * @return the occurrences
	 */
	@SuppressWarnings("unchecked")
	public static List<ITermOccurrence> getOccurrences(ITerm term,
			ISentence sentence) {
		List<ITermOccurrence> result = new ArrayList<ITermOccurrence>();

		
		
		SQLQuery query = getDafoeSession().createSQLQuery(
				"SELECT * FROM m11_termoccurrence WHERE ((term_id = "
						+ term.getId() + ") AND ( sentence_id = "
						+ sentence.getId() + "))").addEntity(
				TermOccurrenceImpl.class);

        
		
		//VT: HQL replace SQL query to hide the logical structure of the database
//		Query query = getDafoeSession()
//				.createQuery(
//						"FROM TermOccurrenceImpl as to WHERE to.RelatedTerm= ? AND to.RelatedSentence= ?")
//				.setEntity(0, term)
//				.setEntity(1, sentence);
		if (query != null) {

			result = query.list();

		}

		return result;
	}

}
