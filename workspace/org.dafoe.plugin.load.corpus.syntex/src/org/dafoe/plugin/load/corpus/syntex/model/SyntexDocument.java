package org.dafoe.plugin.load.corpus.syntex.model;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;


/** Represent a Syntex document.
 * @author Laurent Mazuel
 */
public class SyntexDocument {

	/** Syntex paragraphs in this document. */
	private SortedMap<String, SyntexSentence> sentences = null;
	/** The label of this document. */
	private String documentLabel = null;
	/** The id of this document */
	private String documentId = null;
	
	/** Prefix number by 0. Max number is 9999 */
	private static final DecimalFormat myFormatter = new DecimalFormat("0000");
	
	/** Logger system */
	private Logger log = null;
	
	/** Build a syntex document instance.
	 * @param documentLabel
	 * @param documentId
	 */
	public SyntexDocument(String documentId, String documentLabel) {
		this.documentLabel = documentLabel;
		this.documentId = documentId;
		this.sentences = new TreeMap<String, SyntexSentence>();
		// Log system
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(ImportSYNTEX.logLevel);
		log = Logger.getLogger(SyntexDocument.class.getName());
		log.setUseParentHandlers(false); // Do not use default handlers
		log.addHandler(consoleHandler);
		log.setLevel(ImportSYNTEX.logLevel);
	}

	/** Return all sentences ids from this syntex document.
	 * @return An unmodifiable list of paragraph ids.
	 */
	public List<String> getAllSentenceIds() {
		return Collections.unmodifiableList(new Vector<String>(sentences.keySet()));
	}
	
	/** Return all paragraphs.
	 * @return An unmodifiable list of paragraphs.
	 */
	public Collection<SyntexSentence> getAllSentences() {
		return Collections.unmodifiableCollection(sentences.values());
	}
		
	/** The label of this document.
	 * @return
	 */
	public String getDocumentLabel() {
		return documentLabel;
	}

	/** Get a specific syntex paragraph from its id.
	 * @param sentenceId
	 * @return
	 */
	public SyntexSentence getSyntexSentence(String sentenceId) {
		return sentences.get(sentenceId);
	}
	
	/** Add a sentence to this instance.
	 * @param sentence
	 * @throws NullPointerException if parameter is <code>null</code>
	 * @throws IllegalArgumentException if document already contains this sentence.
	 */
	public void addSentence(SyntexSentence sentence) {
		String sentenceId = sentence.getSentenceId();
		if(sentences.containsKey(sentenceId))
			throw new IllegalArgumentException("Document "+documentId+" already contains sentence: "+sentenceId);
		sentences.put(sentenceId, sentence);
	}

	/** Get the id of this document.
	 * @return
	 */
	public String getDocumentId() {
		return documentId;
	}
	
	/** Build a sentence id which is not contained in the "sentences" map of this document id.
	 * @param syntexSeqId The syntex seq id.
	 * @param lineCounter The line counter in "syntex_seq" file
	 * @return An unique sentenceid
	 */
	public String buildNextUniqueSentenceId(int syntexSeqId, int lineCounter) {
		String basicSentenceId = buildBasicSeqId(syntexSeqId);
		// If key exist, special treatment needed
		if(sentences.containsKey(basicSentenceId)) {
			String finalSentenceId = basicSentenceId + "A" + lineCounter;
//			log.info("I have change the Id from "+basicSentenceId+" to "+finalSentenceId);
			return finalSentenceId;
		}
		return basicSentenceId;
	}
	
	/** Build a standard sentence id from a given syntex seq id and this document id.
	 * The returning string is not tested on the keys off "sentences" map.
	 * @param syntexSeqId A syntex seq id.
	 * @return A sentence id
	 * @see #buildNextUniqueSentenceId(int, int)
	 */
	public String buildBasicSeqId(int syntexSeqId) {
		if(syntexSeqId > 9999) {
			log.severe("Warning, syntex seq number superior to 9999. Sentence Id will be incorrect.");
		}
		String prefixedSyntexSeqId = myFormatter.format(syntexSeqId);
		return documentId + '-' + prefixedSyntexSeqId;
	}
	
	/** Return the next lexicographic sentence id of "sentenceid" parameter in "sentences" map.
	 * @param sentenceId A sentence id.
	 * @return The next lexicographic sentence id.
	 * @throws IllegalArgumentException If "sentenceid" parameter is not a valid key in the map.
	 */
	public String getNextSentenceId(String sentenceId) {
		if(!sentences.containsKey(sentenceId))
			throw new IllegalArgumentException("This document does not contain this id: "+sentenceId);
		SortedMap<String, SyntexSentence> subSentences = sentences.tailMap(sentenceId);
		if(subSentences.size() < 2) {
			log.warning("SentenceId does not have successor!!!" +sentenceId);
		}
		return subSentences.keySet().toArray(new String[0])[1]; // The second element
	}
	
}

