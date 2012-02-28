package org.dafoe.plugin.imp.syntex.model;

import java.util.List;
import java.util.Vector;

import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.SentenceImpl;


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
	/** The order of appareance of the sentence in the corpus */
	private int order = 0;
	
	/** Build a sentence.
	 * @param sentence The sentence content.
	 * @param sentenceId The sentence content.
	 */
	public SyntexSentence(String sentenceId, String sentence, int order) {
		this.sentence = sentence;
		this.sentenceId = sentenceId;
		this.order = order;
		this.occurrenceIds = new Vector<String>();
	}
	
	public ISentence getDafoeSentence() {
		ISentence dafoeSentence = new SentenceImpl();
		dafoeSentence.setContent(getSentence());
		dafoeSentence.setOrderNumber(getOrder());
		return dafoeSentence;
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
	
	/** The order.
	 * @return The order.
	 */
	public int getOrder() {
		return order;
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
