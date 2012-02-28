package org.dafoe.plugin.load.corpus.syntex.model;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

/** A syntex term.
 * @author Laurent Mazuel
 */
public class SyntexTerm {
	
	/** L'ensemble des categories lexicales associés à Syntex.
	 * @author Laurent Mazuel
	 */
	public enum SyntexCategory {
		Adj,		// Adjectif
		Adv,		// Adverbe
		Nom,		// nom commun
		NomInc,		// ?????
		NomXX,		// ?????
		NomPr,		// nom propre
		NomPrXXInc, // mot inconnu
		Ppa,		// participe passé
		Ppr,		// participe présent
		V,			// verbe
		CSub,		// "que", "qu'"
		Prep,		// Preposition
		Typo, 		// Typo "(", ",", etc.
		SAdj,		// Syntagme dont la tête est un Adjectif
		SAdv,		// Syntagme dont la tête est un Adverbe
		SNom,		// Syntagme dont la tête est un nom commun
		SNomInc,	// ?????
		SNomPr,		// Syntagme dont la tête est un nom propre
		SNomPrXXInc, // Syntagme dont la tête est un mot inconnu
		SPpa,		// Syntagme dont la tête est un participe passé
		SPpr,		// Syntagme dont la tête est un participe présent
		SPrep,		// Syntagme preposition
		SV,			// Syntagme dont la tête est un verbe
		SCCoord;	// ?????
		
		/** For some stat */
		private int count = 0;
		
		/** For some stat */
		public void increment() {
			count++;
		}
		
		/** For some stat
		 * @return the number of occurrence of this cat since the last loading of JVM.
		 */
		public int getCount() {
			return count;
		}
	}
	
	/** The term id. */
	private String termId = null;
	/** The lemmatized form	. */
	private String lemme = null;
	/** The syntex original form. */
	private String originalForm = null;
	/** The syntex category. */
	private SyntexCategory cat = null;
	/** The list of occurences id to sentence. */
	private List<String> occurrenceIds = null;
	/** The saillance linked to this term */
	private SyntexTermSaillance saillance = null;
	
	/** Build a syntex term instance.
	 * @param termId
	 * @param syntexcat
	 * @param lemme
	 * @param originalForm
	 */
	public SyntexTerm(String termId, SyntexCategory syntexcat, String lemme, String originalForm) {
		this.termId = termId;
		this.cat = syntexcat;
		this.lemme = lemme;
		this.originalForm = originalForm;
		this.occurrenceIds = new Vector<String>();
	}
	
	/** The lemmatized form.
	 * @return The lemmatized form.
	 */
	public String getLemme() {
		return lemme;
	}
	
	/** The lexical category.
	 * @return The lexical category.
	 */
	public SyntexCategory getCategory() {
		return cat;
	}
	
	/** Returns the statut of named term.
	 * @return <code>true</code> if term is named term, <code>false</code> otherwise.
	 */
	public boolean isNamedTerm() {
		return cat == SyntexCategory.SNomPr || cat == SyntexCategory.NomPr;
	}
	
	/** Returns the statut of term.
	 * @return <code>true</code> if this instance is a term, <code>false</code> otherwise.
	 */
	public boolean isTerm() {
		return cat != SyntexCategory.Typo;
	}

	/** Get the occurrence ids list.
	 * @return
	 */
	public List<String> getOccurrenceIds() {
		return Collections.unmodifiableList(occurrenceIds);
	}
	
	/** Get the term id.
	 * @return
	 */
	public String getTermId() {
		return termId;
	}
	
	/** Get the syntex original form.
	 * @return
	 */
	public String getOriginalForm() {
		return originalForm;
	}
	
	/** Add an occurence of sentence for this term.
	 * @param occId
	 */
	void addOccurrenceId(String occId) {
		occurrenceIds.add(occId);
	}
	
	/** Return the saillance information for this term.
	 * @return
	 */
	public SyntexTermSaillance getSaillance() {
		return saillance;
	}
	
	/** Set the saillance information for this term.
	 * @param saillanceInstance
	 */
	public void setSaillance(SyntexTermSaillance saillanceInstance) {
		this.saillance = saillanceInstance;
	}
	
	/** Build a term id using a corpus id and a syntex term id.
	 * @param corpusId
	 * @param syntexTermId
	 * @return
	 */
	public static String buildTermId(String corpusId, String syntexTermId) {
		return corpusId + syntexTermId;
	}
}
