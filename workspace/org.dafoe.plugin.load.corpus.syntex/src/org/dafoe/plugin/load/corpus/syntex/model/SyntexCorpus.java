package org.dafoe.plugin.load.corpus.syntex.model;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class SyntexCorpus {

	/** Syntex documents in this corpus. */
	private Map<String, SyntexDocument> documents = null;
	/** Syntex corpus id */
	private String corpusId = null;
	/** Syntex corpus label */
	private String corpusLabel = null;

	/**
	 * @param corpusLabel
	 */
	public SyntexCorpus(String corpusId, String corpusLabel) {
		this.corpusId = corpusId;
		this.corpusLabel = corpusLabel;
		this.documents = new TreeMap<String, SyntexDocument>();
	}

	/** Return all documents id of this syntex corpus.
	 * @return
	 */
	public List<String> getAllDocumentsIds() {
		// Return all keys from the map
		return new Vector<String>(documents.keySet());
	}

	/** Return all the sentences ids from this corpus.
	 * @return
	 */
	public List<String> getAllSentenceIdsFromAllDocuments() {
		// Concatenate all sentences ids from all documents
		List<String> finalSentencesIds = new Vector<String>();
		for(SyntexDocument doc : documents.values()) {
			finalSentencesIds.addAll(doc.getAllSentenceIds());
		}
		return finalSentencesIds;
	}

	/** Return the document from the document id. May be <code>null</code> if no document exist from
	 * this id.
	 * @param documentId The document id.
	 * @return A {@link SyntexDocument} instance, or <code>null</code>.
	 */
	public SyntexDocument getSyntexDocument(String documentId) {
		return documents.get(documentId);
	}
	
	public String getCorpusLabel() {
		return corpusLabel;
	}
	
	public void putDocument(String documentid, SyntexDocument document) {
		documents.put(documentid, document);
	}
	
	public String getCorpusId() {
		return corpusId;
	}

}
