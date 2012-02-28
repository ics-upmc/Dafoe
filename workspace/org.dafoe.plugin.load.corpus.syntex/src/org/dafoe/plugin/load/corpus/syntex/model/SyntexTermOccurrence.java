package org.dafoe.plugin.load.corpus.syntex.model;

/** An occurence of term in a sentence.
 * @author Laurent Mazuel
 */
public class SyntexTermOccurrence {
	
	/** the id of this instance	 */
	private String syntexTermOccurrenceId = null;
	/** The syntex term */
	private SyntexTerm syntexTerm = null;
	/** The syntex sentence */
	private SyntexSentence syntexSentence = null;
	
	/** The starting position of the term in the sentence */
	private int occurrenceStartPosition = 0;
	/** The length of the occurrence in the sentence. */
	private int occurrenceLength = 0;
	
	/** Build a syntex term occurrence instance.
	 * This constructor add the builded instance to the syntex term and the syntex sentence in parameter.
	 * @param syntexTermOccurrenceId
	 * @param syntexTerm
	 * @param syntexSentence
	 */
	public SyntexTermOccurrence(String syntexTermOccurrenceId, SyntexTerm syntexTerm, SyntexSentence syntexSentence) {
		if(syntexTerm == null) {
			throw new IllegalArgumentException("Term cannot be null.\nTermOccId: "+syntexTermOccurrenceId);
		}
		if(syntexSentence == null) {
			throw new IllegalArgumentException("Sentence cannot be null.\nTermOccId: "+syntexTermOccurrenceId);
		}
		this.syntexTermOccurrenceId = syntexTermOccurrenceId;
		this.syntexTerm = syntexTerm;
		this.syntexSentence = syntexSentence;
		this.syntexTerm.addOccurrenceId(syntexTermOccurrenceId);
		this.syntexSentence.addOccurrenceId(syntexTermOccurrenceId);
		
		String originalTermFrom = syntexTerm.getOriginalForm();
		String sentence = syntexSentence.getSentence();
		int indexOfSubSentence = sentence.indexOf(originalTermFrom);
		if(indexOfSubSentence != -1) {
			// I found it!!!
			occurrenceStartPosition = indexOfSubSentence;
			occurrenceLength = originalTermFrom.length();
		}
		else {
			// FIXME on rate systematiquement les bout de phrase avec des verbes, voir pour faire mieux...
			// Argh... It is not here...
			occurrenceStartPosition = 0;
			occurrenceLength = sentence.length();
		}
	}

	/**
	 * @return the syntexSentence
	 */
	public SyntexSentence getSyntexSentence() {
		return syntexSentence;
	}

	/**
	 * @return the syntexTerm
	 */
	public SyntexTerm getSyntexTerm() {
		return syntexTerm;
	}

	/** The occurrence id of this instance.
	 * @return
	 */
	public String getSyntexTermOccurrenceId() {
		return syntexTermOccurrenceId;
	}
	
	/** the starting position
	 * @return
	 */
	public int getOccurrenceStartPosition() {
		return occurrenceStartPosition;
	}
	
	/** the end position
	 * @return
	 */
	public int getOccurrenceLength() {
		return occurrenceLength;
	}
}
