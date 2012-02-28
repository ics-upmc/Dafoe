/**
 * 
 */
package org.dafoe.plugin.imp.yatea.yateaSAX.item;

import java.util.ArrayList;
import java.util.List;

import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermOccurrence;
import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.SentenceImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermOccurrenceImpl;
import org.dafoe.plugin.imp.yatea.yateaSAX.parse.SentenceCacheMap;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

/**
 * Object representing a term in Yatea's XML.
 * 
 * @author Athier
 */
public class YateaTermCandidate {
	private String id, form, lemma;

	private String syntactic_category;

	private String head;
	private String number_occu;

	private List<YateaOccurrence> list_occurrences;

	private String term_confidence;
	private List<YateaReliableAnchor> list_reliable_anchors;
	private String log_information;

	private String sa_head;
	private String sa_modifier;

	private ITerminology _termino;

	public YateaTermCandidate(ITerminology terminology) {
		this._termino = terminology;
		list_occurrences = new ArrayList<YateaOccurrence>();
		list_reliable_anchors = new ArrayList<YateaReliableAnchor>();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the form
	 */
	public String getForm() {
		return form;
	}

	/**
	 * @param form
	 *            the form to set
	 */
	public void setForm(String form) {
		this.form = form;
	}

	/**
	 * @return the lemma
	 */
	public String getLemma() {
		return lemma;
	}

	/**
	 * @param lemma
	 *            the lemma to set
	 */
	public void setLemma(String lemma) {
		this.lemma = lemma;
	}

	/**
	 * @return the number_occu
	 */
	public String getNumber_occu() {
		return number_occu;
	}

	/**
	 * @param numberOccu
	 *            the number_occu to set
	 */
	public void setNumber_occu(String numberOccu) {
		number_occu = numberOccu;
	}

	/**
	 * @return the syntactic_category
	 */
	public String getSyntactic_category() {
		return syntactic_category;
	}

	/**
	 * @param syntacticCategory
	 *            the syntactic_category to set
	 */
	public void setSyntactic_category(String syntacticCategory) {
		syntactic_category = syntacticCategory;
	}

	/**
	 * @return the head
	 */
	public String getHead() {
		return head;
	}

	/**
	 * @param head
	 *            the head to set
	 */
	public void setHead(String head) {
		this.head = head;
	}

	/**
	 * @return the term_confidence
	 */
	public String getTerm_confidence() {
		return term_confidence;
	}

	/**
	 * @param termConfidence
	 *            the term_confidence to set
	 */
	public void setTerm_confidence(String termConfidence) {
		term_confidence = termConfidence;
	}

	/**
	 * @return the log_information
	 */
	public String getLog_information() {
		return log_information;
	}

	/**
	 * @param logInformation
	 *            the log_information to set
	 */
	public void setLog_information(String logInformation) {
		log_information = logInformation;
	}

	/**
	 * @return the sa_head
	 */
	public String getSa_head() {
		return sa_head;
	}

	/**
	 * @param saHead
	 *            the sa_head to set
	 */
	public void setSa_head(String saHead) {
		sa_head = saHead;
	}

	/**
	 * @return the sa_modifier
	 */
	public String getSa_modifier() {
		return sa_modifier;
	}

	/**
	 * @param saModifier
	 *            the sa_modifier to set
	 */
	public void setSa_modifier(String saModifier) {
		sa_modifier = saModifier;
	}

	/**
	 * @return the list_occurences
	 */
	public List<YateaOccurrence> getList_occurences() {
		return list_occurrences;
	}

	/**
	 * @param listOccurences
	 *            the list_occurences to set
	 */
	public void setList_occurences(List<YateaOccurrence> listOccurences) {
		list_occurrences = listOccurences;
	}

	public void addOccuToList(YateaOccurrence occu) {
		list_occurrences.add(occu);
	}

	/**
	 * @return the list_reliable_anchors
	 */
	public List<YateaReliableAnchor> getList_reliable_anchors() {
		return list_reliable_anchors;
	}

	/**
	 * @param listReliableAnchors
	 *            the list_reliable_anchors to set
	 */
	public void setList_reliable_anchors(
			List<YateaReliableAnchor> listReliableAnchors) {
		list_reliable_anchors = listReliableAnchors;
	}

	public void addReliableAnchor(YateaReliableAnchor anchor) {
		list_reliable_anchors.add(anchor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "YateaTermCandidate ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (form != null ? "form=" + form + ", " : "")
				+ (lemma != null ? "lemma=" + lemma + ", " : "")
				+ (number_occu != null ? "number_occu=" + number_occu + ", "
						: "")
				+ (syntactic_category != null ? "syntactic_category="
						+ syntactic_category + ", " : "")
				+ (head != null ? "head=" + head + ", " : "")
				+ (term_confidence != null ? "term_confidence="
						+ term_confidence + ", " : "")
				+ (sa_head != null ? "sa_head=" + sa_head + ", " : "")
				+ (sa_modifier != null ? "sa_modifier=" + sa_modifier + ", "
						: "")
				+ (log_information != null ? "log_information="
						+ log_information + ", " : "")
				+ (list_occurrences != null ? "list_occurrences="
						+ list_occurrences + ", " : "")
				+ (list_reliable_anchors != null ? "list_reliable_anchors="
						+ list_reliable_anchors : "") + "]";
	}

	/**
	 * Function saving all of the term in the database. If the lemma of the
	 * current term already exists, link the occurrences of the current term to
	 * the one already existing in the database. Also create a reference in a
	 * temporary table between Yatea_ID and DAFOE_database_ID.
	 * 
	 * @param hSession
	 *            Session requested to connect to the DB
	 * @return True if OK.
	 */
	public boolean saveToDB(Session hSession, SentenceCacheMap sentenceTable) {
		// System.out.print("Saving to DB : "+ id +" ...");

		// Start the transaction
		Transaction tx = hSession.beginTransaction();

		// Check in the DB if a term with this lemma already exists
		ITerm oldTerm = null;
		// oldTerm = (ITerm) hSession.createQuery(
		// "FROM TermImpl as term where UPPER(term.Label)=?").setString(0,
		// lemma.toUpperCase()).uniqueResult();

		Criteria crit = hSession.createCriteria(ITerm.class);
		crit.setMaxResults(1);
		crit.add(Expression.eq("Label", lemma).ignoreCase());
		oldTerm = (ITerm) crit.uniqueResult();

		// If the term already exists
		if (oldTerm != null) {
			// Add the occu of the current term to the one existing
			addOccuToTerm(hSession, oldTerm, sentenceTable);
			// Create the ref (used for syntactic relation)
			createRefId(hSession, oldTerm);
			// Save the term...
			hSession.saveOrUpdate(oldTerm);
		} else {
			// Create the new term object
			ITerm newTerm = new TermImpl();
			newTerm.setLabel(lemma); // Set its label
			// VT: use type-safe approach
			// newTerm.setStatus(0);
			newTerm.setState(TERMINO_OBJECT_STATE.STUDIED);

			// Save the term
			hSession.save(newTerm);
			// Add its occurrences
			addOccuToTerm(hSession, newTerm, sentenceTable);
			// Save a reference between Hibernate id and Yatea id
			createRefId(hSession, newTerm);

			// Save the new term
			hSession.save(newTerm);

			// connect with the terminology
			_termino.addTerminoObject(newTerm);
		}

		// Commit (flush the DB)
		tx.commit();

		// System.out.println("... Done.");

		return (true);
	}

	/**
	 * Add all of the occurrences of the current term to the object "ITerm" in
	 * the database.
	 * 
	 * @param hSession
	 *            Session used to connect to the database
	 * @param term
	 *            The object term to which add the occurrences
	 * @return True if OK.
	 */
	private boolean addOccuToTerm(Session hSession, ITerm term,
			SentenceCacheMap sentenceTable) {
		// Get every term's occurrences and save them into the DB, with all the
		// relations...
		for (YateaOccurrence occu : list_occurrences) {
			// Create new object occurrence
			ITermOccurrence newOccu = new TermOccurrenceImpl();

			// Get the length of the occurrence
			int length = Integer.parseInt(occu.getEnd_position())
					- Integer.parseInt(occu.getStart_position());

			// Set the position and the length of the occurrence
			newOccu.setPosition(Integer.parseInt(occu.getStart_position()));
			newOccu.setLength(length);

			// Save the occurrence object to the DB
			hSession.save(newOccu);

			// Add the relation occurrence/term to the DB
			term.addTermOccurrence(newOccu);

			// Get the sentence in the DB related with this occurrence
			// ISentence sent = (ISentence)
			// hSession.createQuery("FROM SentenceImpl as sent where sent.OrderNumber=? and sent.Document=?")
			// .setInteger(0, Integer.parseInt(occu.getSentence()))
			// .setInteger(1, Integer.parseInt(occu.getDoc()) + 1)
			// .uniqueResult();
			Integer sentenceHibernateId = sentenceTable.get(Integer
					.parseInt(occu.getDoc()), Integer.parseInt(occu
					.getSentence()));
			ISentence sent = (ISentence) hSession.load(SentenceImpl.class,
					sentenceHibernateId);
			// Add the relation occurrence/term/sentence to the DB
			sent.addTermOccurrence(newOccu, term);
			// Update the object in the DB
			hSession.update(sent);
		}

		return (true);
	}

	/**
	 * Insert in the temporary table a reference between the current Yatea term
	 * and the object ITerm given in argument.
	 * 
	 * @param hSession
	 *            Session to connect to the DB
	 * @param term
	 *            The term to ref with this current Yatea's term
	 * @return True if OK.
	 */
	private boolean createRefId(Session hSession, ITerm term) {
		/*
		 * hSession.createSQLQuery("INSERT INTO temp_ref_term (id_hib, id_yatea) VALUES (?, ?);"
		 * ) .setInteger(0, term.getId()) .setString(1, id) .executeUpdate();
		 */
		ImportYATEA.putInCache(id, term.getId());

		return true;
	}
}