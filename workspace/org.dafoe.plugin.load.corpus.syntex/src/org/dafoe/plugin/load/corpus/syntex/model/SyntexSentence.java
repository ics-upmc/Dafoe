package org.dafoe.plugin.load.corpus.syntex.model;

import java.util.List;
import java.util.Vector;


/** Represent a sentence in Syntex.
 * @author Laurent Mazuel
 */
public class SyntexSentence {

	/** The sentence content */
	private String sentence = null;
	/** The sentence id */
	private String sentenceId = null;
	/** The list of occurences ids for terms */
	private List<String> occurrenceIds = null;
	
	/** Build a sentence.
	 * @param sentence The sentence content.
	 * @param sentenceId The sentence content.
	 */
	public SyntexSentence(String sentenceId, String sentence) {
		this.sentence = sentence;
		this.sentenceId = sentenceId;
		this.occurrenceIds = new Vector<String>();
	}
	/** The sentence content.
	 * @return the sentence content.
	 */
	public String getSentence() {
		return sentence;
	}
	/** The sentences id.
	 * @return the sentenceId
	 */
	public String getSentenceId() {
		return sentenceId;
	}

	/** The list of occurrences of this sentence.
	 * @return
	 */
	public List<String> getOccurrenceIds() {
		return occurrenceIds;
	}
	
	/** Add an occurrence of terms in this sentence.
	 * @param occId
	 */
	void addOccurrenceId(String occId) {
		occurrenceIds.add(occId);
	}
}
