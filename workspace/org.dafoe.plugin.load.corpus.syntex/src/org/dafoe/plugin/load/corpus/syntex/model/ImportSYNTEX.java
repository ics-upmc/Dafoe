/**
 * 
 */
package org.dafoe.plugin.load.corpus.syntex.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dafoe.framework.core.terminological.model.ICorpus;
import org.dafoe.framework.core.terminological.model.IDocument;
import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermOccurrence;
import org.dafoe.framework.core.terminological.model.ITermSaillance;
import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.framework.core.terminological.model.LINGUISTIC_STATUS;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.CorpusImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.DocumentImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.SentenceImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermOccurrenceImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermSaillanceImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TerminologyImpl;
import org.dafoe.plugin.load.corpus.syntex.model.SyntexTerm.SyntexCategory;
import org.hibernate.Session;
import org.hibernate.Transaction;



/** Syntex corpus for Dafoe.
 * @author Laurent Mazuel
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a> CRITT CRCFAO
 */
public class ImportSYNTEX  {

	/** The log level to be published on the console handler. */
	public static final Level logLevel = Level.INFO;

	/** The no rmalized filenames for SyntexFile.
	 * @author Laurent Mazuel
	 */
	private enum SyntexFiles {
		SYNTEX_SEQ("syntex_seq.txt"),
		SYNTEX_LISTE("syntex_liste.txt"),
		SYNTEX_OCC("syntex_occ.txt");

		private String filename = null;

		private SyntexFiles(String filename) {
			this.filename = filename;
		}

		/** Test if the file in parameter has the name of this instance.
		 * @param file
		 * @return <code>true</code> if this corresponds, <code>false</code> otherwise.
		 */
		public boolean nameCorrespondsToTheFile(File file) {
			return file.getName().toLowerCase().equals(this.filename.toLowerCase());
		}
	}

	/** Map between corpusid and Corpus classes. */
	private Map<String, SyntexCorpus> syntexCorpus = null;
	private Map<String, SyntexTerm> syntexTerms = null;
	private Map<String, SyntexTermOccurrence> syntexTermOccurrences = null;

	/** Logger system */
	private Logger log = null;
	
	
	//VT:
	private Map<String, ITerm> hashMapTerm = new HashMap<String, ITerm>();
	
	/** The dafoe session. */
	private Session dafoeSession;
	

	/** Build an empty syntex corpus instance.
	 */
	public ImportSYNTEX() {
		// Common fields
		syntexCorpus = new TreeMap<String, SyntexCorpus>();
		syntexTerms = new TreeMap<String, SyntexTerm>();
		syntexTermOccurrences = new TreeMap<String, SyntexTermOccurrence>();
		// Log system
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(ImportSYNTEX.logLevel);
		log = Logger.getLogger(ImportSYNTEX.class.getName());
		log.setUseParentHandlers(false); // Do not use default handlers
		log.addHandler(consoleHandler);
		log.setLevel(ImportSYNTEX.logLevel);
	}

	/** Build a syntex corpus instance and load a corpus.
	 * @param syntexDirectory The directory which contains a corpus.
	 * @throws NullPointerException If parameter is <code>null</code>.
	 * @throws IllegalArgumentException If parameter is not a valid syntex directory.
	 * @throws FileNotFoundException If path located by parameter does not exist.
	 * @throws IOException If something happens while loading syntex files.
	 * @see #addSyntexCorpus(File)
	 */
	public ImportSYNTEX(File syntexDirectory) throws IOException {
		this();
		addSyntexCorpus(syntexDirectory);
	}

	/** Add a syntex corpus to this instance.
	 * @param syntexDirectory
	 * @return <code>true</code> if all is Ok, <code>false</code> otherwise.
	 * @throws NullPointerException If parameter is <code>null</code>.
	 * @throws IllegalArgumentException If parameter is not a valid syntex directory.
	 * @throws FileNotFoundException If path located by parameter does not exist.
	 * @throws IOException If something happens while loading syntex files.
	 */
	public boolean addSyntexCorpus(File syntexDirectory) throws IOException {
		if(syntexDirectory == null)
			throw new NullPointerException("Path to syntex directory cannot be null");
		if(!syntexDirectory.exists()) 
			throw new FileNotFoundException("Path to syntex directory must exist: "+syntexDirectory);
		if(!syntexDirectory.isDirectory())
			throw new IllegalArgumentException("Path to syntex must be a directory: "+syntexDirectory);

		// Find the files,
		log.info("Search for the Syntex files in folder: "+syntexDirectory);
		List<File> filesInDirectory = Arrays.asList(syntexDirectory.listFiles());
		File syntexOccFile = null;
		File syntexSeqFile = null;
		File syntexListeFile = null;
		for(File fileInDirectory : filesInDirectory) {
			// Test if this file is "syntex_occ"
			if(SyntexFiles.SYNTEX_OCC.nameCorrespondsToTheFile(fileInDirectory)) {
				log.fine("I have found "+fileInDirectory);
				syntexOccFile = fileInDirectory;
				continue; // Continue to next file
			}
			// Test if this file is "syntex_seq"
			else if(SyntexFiles.SYNTEX_SEQ.nameCorrespondsToTheFile(fileInDirectory)) {
				log.fine("I have found "+fileInDirectory);
				syntexSeqFile = fileInDirectory;
				continue; // Continue to next file
			}
			// Test if this file is "syntex_liste"
			else if(SyntexFiles.SYNTEX_LISTE.nameCorrespondsToTheFile(fileInDirectory)) {
				log.fine("I have found "+fileInDirectory);
				syntexListeFile = fileInDirectory;
				continue; // Continue to next file
			}
		}
		// Test if I have all the needed files
		if(syntexOccFile == null)
			throw new IllegalArgumentException("Unable to find file "+SyntexFiles.SYNTEX_OCC);
		if(syntexSeqFile == null)
			throw new IllegalArgumentException("Unable to find file "+SyntexFiles.SYNTEX_SEQ);
		if(syntexListeFile == null)
			throw new IllegalArgumentException("Unable to find file "+SyntexFiles.SYNTEX_LISTE);

		// Now, open the files!!!

		log.info("Build a corpusId...");
		// Build a determinist corpus id
		/* Algorithm: concat:
		 * - "CiD"
		 * - the last 6 numbers of syntex_occ file size in hex (in many cases it will be unique) */
		String corpusNumId = Long.toHexString(syntexOccFile.length());
		corpusNumId = corpusNumId.substring(0, Math.min(corpusNumId.length(), 6));
		while(corpusNumId.length() < 6) { // Complete with "0"
			corpusNumId = "0" + corpusNumId;
		}
		// Here I'm sure that corpusNumId.length() == 6
		assert corpusNumId.length() == 6;
		String generatedCorpusId = "CiD" + corpusNumId;
		// Currently, I have no idea of a corpus label... I will used the corpus id...
		String generatedCorpusLabel = new String(generatedCorpusId);
		log.info("Corpus Id generated is: "+generatedCorpusId);

		// Build the syntex corpus instance
		SyntexCorpus localSyntexCorpus = new SyntexCorpus(generatedCorpusId, generatedCorpusLabel);
		syntexCorpus.put(generatedCorpusId, localSyntexCorpus);

		// Extract terms from syntex_liste file
		extractTermsFromSyntexListeFile(syntexListeFile, generatedCorpusId);

		// Extract sentences from syntex_seq file
		extractSentencesFromSyntexSeqFile(syntexSeqFile, generatedCorpusId);

		// Extract occurences from syntex_occ file
		extractOccurencesFromSyntexOccFile(syntexOccFile, generatedCorpusId);

		// I have finished!!!
		return true;
	}

	/** Extract terms from file "syntex_occ" and fullfill map "syntexTermOccurrences".
	 * @param syntexOccFile The file "syntex_occ"
	 * @param generatedCorpusId The local corpus id
	 * @throws FileNotFoundException If the file is not found, which may not occured using this method in {@link #addSyntexCorpus(File)}
	 * @throws IOException If something happens during reading the file
	 */
	private void extractOccurencesFromSyntexOccFile(File syntexOccFile, String generatedCorpusId) throws FileNotFoundException, IOException {
		String line;
		boolean firstLine;
		log.info("Read "+syntexOccFile+" file");
		BufferedReader occReader = new BufferedReader(new FileReader(syntexOccFile));
		line = null;
		firstLine = true;
		int lineNumber = 0;
		Set<String> usedSentenceId = new TreeSet<String>();
		String lastSentenceId = null;
		while((line = occReader.readLine()) != null) {
			lineNumber++;
			// Do not read first line, always column title
			if(firstLine) {
				firstLine = false;
				continue;
			}
			String[] splittedLine = line.split("\t");
			String localDocId = splittedLine[0]; // docId of file "syntex_seq"
			String localParaId = splittedLine[1]; // seqId of file "syntex_seq"
			String localTermId = splittedLine[2]; // termId of file "syntex_liste"

			// Term of occurence
			String dafoeTermId = SyntexTerm.buildTermId(generatedCorpusId, localTermId);
			SyntexTerm pointedTerm = syntexTerms.get(dafoeTermId);
			if(pointedTerm == null) {
				log.warning("This occurrence is ignored, term id does not exist: "+dafoeTermId);
				log.warning("See for errors during term construction");
				continue;
			}
			// Sentence of occurrence
			String documentId = generatedCorpusId + localDocId;
			SyntexDocument localDocument = getDocumentFromDocumentId(documentId);
			String initialSedId = localDocument.buildBasicSeqId(Integer.valueOf(localParaId));
			String pointedSeqId = initialSedId;
			// This id was used before...
			if(lastSentenceId != null && !lastSentenceId.startsWith(pointedSeqId) && usedSentenceId.contains(pointedSeqId)) {
				while(usedSentenceId.contains(pointedSeqId)) {
					pointedSeqId = localDocument.getNextSentenceId(pointedSeqId);
				}
//				log.info("The id "+initialSedId+" was already used, replace by "+pointedSeqId);
			}
			SyntexSentence pointedSentence = getSentenceFromSentenceId(pointedSeqId);
			if(pointedSentence == null) {
				log.warning("This occurrence is ignored, sentence id does not exist: "+pointedSeqId);
				log.warning("See for errors during sentence construction");
				continue;
			}
			lastSentenceId = pointedSeqId;
			usedSentenceId.add(pointedSeqId);

			// Sentence of occurrence
			if(pointedSentence == null) {
				log.warning("Unable to work with occurence, builded paraId: "+pointedSeqId);
			}
			// Compute an occurrence id
			String dafoeOccId = "Occ"+lineNumber;
			SyntexTermOccurrence localTermOccurrence = new SyntexTermOccurrence(dafoeOccId, pointedTerm, pointedSentence);
			syntexTermOccurrences.put(dafoeOccId, localTermOccurrence);
		}
		occReader.close();
	}

	/** Extract terms from file "syntex_seq" and fullfill map "syntexCorpus".
	 * @param syntexSeqFile The file "syntex_seq"
	 * @param generatedCorpusId The local corpus id
	 * @throws FileNotFoundException If the file is not found, which may not occured using this method in {@link #addSyntexCorpus(File)}
	 * @throws IOException If something happens during reading the file
	 */
	private void extractSentencesFromSyntexSeqFile(File syntexSeqFile, String generatedCorpusId) throws FileNotFoundException, IOException {
		// syntex_seq: contains all sentences, and document id
		log.info("Read "+syntexSeqFile+" file");
		SyntexCorpus localSyntexCorpus = syntexCorpus.get(generatedCorpusId);
		BufferedReader seqReader = new BufferedReader(new FileReader(syntexSeqFile));
		String line = null;
		boolean firstLine = true;
		int lineCounter = 0;
		while((line = seqReader.readLine()) != null) {
			lineCounter++;
			// Do not read first line, always column title
			if(firstLine) {
				firstLine = false;
				continue;
			}
			// Loading data
			String[] splittedLine = line.split("\t");
			String docId = splittedLine[0];
			String dafoeDocId = generatedCorpusId + docId;
			SyntexDocument localDocument = localSyntexCorpus.getSyntexDocument(dafoeDocId);
			if(localDocument == null) {
				// Build a doc, with the dafoe id as id and the syntex id as label
				localDocument = new SyntexDocument(dafoeDocId, docId);
				localSyntexCorpus.putDocument(dafoeDocId, localDocument);
			}
			String seqId = splittedLine[1];
			String sentence = splittedLine[2];
			String sentenceId = localDocument.buildNextUniqueSentenceId(Integer.valueOf(seqId), lineCounter);
			SyntexSentence localSentence = new SyntexSentence(sentenceId, sentence);
			localDocument.addSentence(localSentence);
		}
		seqReader.close();
	}

	/** Extract terms from file "syntex_liste" and fullfill map "syntexTerms".
	 * @param syntexListeFile The file "syntex_liste"
	 * @param generatedCorpusId The corpus id
	 * @throws FileNotFoundException If the file is not found, which may not occured using this method in {@link #addSyntexCorpus(File)}
	 * @throws IOException If something happens during reading the file
	 */
	private void extractTermsFromSyntexListeFile(File syntexListeFile, String generatedCorpusId) throws FileNotFoundException, IOException {
		String line;
		boolean firstLine;
		// 2- syntex_liste: terms
		log.info("Read "+syntexListeFile+" file");
		BufferedReader listeReader = new BufferedReader(new FileReader(syntexListeFile));
		line = null;
		firstLine = true;
		while((line = listeReader.readLine()) != null) {
			String[] splittedLine = line.split("\t");
			// Do not read first line, always column title
			if(firstLine) {
				firstLine = false;
				int columnNum = splittedLine.length;
				if(columnNum < 18) {
					log.warning("\"syntex_list\" has only "+columnNum+" columns.");
				}
				continue;
			}
			// Loading data
			String termId = splittedLine[0]; // syntex termId
			String cat = splittedLine[1]; // syntex category
			String lemme = splittedLine[2]; // syntex lemmatized form
			String originalForm = splittedLine[3]; // syntex original form

			SyntexCategory localCat = null;
			for(SyntexCategory sCat : SyntexCategory.values()) {
				if(sCat.name().equals(cat)) {
					localCat = sCat;
					sCat.increment();
					break;
				}
			}
			if(localCat == null) {
				log.warning("This category is not a Syntex category: "+cat);
				log.warning("The following will not be stored, errors can occured: "+termId+":"+lemme);
				continue;
			}
			String dafoeTermId = SyntexTerm.buildTermId(generatedCorpusId, termId);
			SyntexTerm localTerm = new SyntexTerm(dafoeTermId, localCat, lemme, originalForm);
			SyntexTermSaillance localSaillance = new SyntexTermSaillance(splittedLine, localTerm);
			localTerm.setSaillance(localSaillance);
			syntexTerms.put(dafoeTermId, localTerm);
		}
		listeReader.close();
	}

	/** Get a document instance from an document id.
	 * @param sentenceId
	 * @return An instance of SyntexSentence, or <code>null</code> if nothing is found.
	 * @throws IllegalArgumentException If parameter is <code>null</code>.
	 */
	private SyntexDocument getDocumentFromDocumentId(String docId) {
		if(docId == null) 
			throw new IllegalArgumentException("Dcoument Id cannot be null");
		String corpusId = getCorpusIdFromDocumentId(docId);
		if(syntexCorpus.containsKey(corpusId)) {
			SyntexCorpus corpus = syntexCorpus.get(corpusId);
			return corpus.getSyntexDocument(docId);
		}
		// If not found...
		return null;
	}

	/** Get a sentence instance from an sentence id.
	 * @param sentenceId
	 * @return An instance of SyntexSentence, or <code>null</code> if nothing is found.
	 * @throws IllegalArgumentException If parameter is <code>null</code>.
	 */
	private SyntexSentence getSentenceFromSentenceId(String sentenceId) {
		if(sentenceId == null) 
			throw new IllegalArgumentException("Sentence Id cannot be null");
		String docId = sentenceId.substring(0, sentenceId.lastIndexOf('-'));
		SyntexDocument document = getDocumentFromDocumentId(docId);
		if(document != null) {
			return document.getSyntexSentence(sentenceId);
		}
		// If not found...
		return null;
	}

	public List<String> getAllCorpusIds() {
		// Return all keys from the map
		return new Vector<String>(syntexCorpus.keySet());
	}

	public List<String> getAllDocumentIds() {
		// Concatenate all documents ids from all corpus
		List<String> finalDocumentsIds = new Vector<String>();
		for(SyntexCorpus corpus : syntexCorpus.values()) {
			finalDocumentsIds.addAll(corpus.getAllDocumentsIds());
		}
		return finalDocumentsIds;
	}

	public List<String> getAllDocumentIdsFromCorpusId(String corpusId) {
		if(syntexCorpus.containsKey(corpusId)) {
			return syntexCorpus.get(corpusId).getAllDocumentsIds();
		}
		else {
			return new Vector<String>();
		}
	}

	public List<String> getAllRelatedSentenceIdsFromTermId(String termId) {
		if(termId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTerms.containsKey(termId)) {
			SyntexTerm localTerm = syntexTerms.get(termId);
			List<String> localOccIds = localTerm.getOccurrenceIds();
			List<String> relatedSentenceIds = new Vector<String>();
			for(String occId : localOccIds) {
				if(syntexTermOccurrences.containsKey(occId)) {
					SyntexTermOccurrence localTermOccurrence = syntexTermOccurrences.get(occId);
					SyntexSentence localSentence = localTermOccurrence.getSyntexSentence();
					relatedSentenceIds.add(localSentence.getSentenceId());
				}
			}
			return relatedSentenceIds;
		}
		return null;
	}

	public List<String> getAllRelatedTermIdsFromSentenceId(String sentenceId) {
		// "getSentenceFromSentenceId" may return NullPointerException if sentenceId is null
		SyntexSentence localSentence = getSentenceFromSentenceId(sentenceId);
		if(localSentence != null) {
			List<String> localOccIds = localSentence.getOccurrenceIds();
			List<String> relatedTermIds = new Vector<String>();
			for(String occId : localOccIds) {
				if(syntexTermOccurrences.containsKey(occId)) {
					SyntexTermOccurrence localTermOccurrence = syntexTermOccurrences.get(occId);
					SyntexTerm localTerm = localTermOccurrence.getSyntexTerm();
					relatedTermIds.add(localTerm.getTermId());
				}
			}
			return relatedTermIds;
		}
		return null;
	}

	public List<String> getAllSentenceIds() {
		// Concatenate all sentences ids from all corpus
		List<String> finalSentencesIds = new Vector<String>();
		for(SyntexCorpus corpus : syntexCorpus.values()) {
			finalSentencesIds.addAll(corpus.getAllSentenceIdsFromAllDocuments());
		}
		return finalSentencesIds;
	}

	public List<String> getAllSentenceIdsFromDocumentId(String documentId) {
		if(documentId == null)
			throw new NullPointerException("Parameter cannot be null.");
		// Get the corpus id from document id
		String corpusId = this.getCorpusIdFromDocumentId(documentId);
		// Get the corpus instance and return sentences id.
		if(syntexCorpus.containsKey(corpusId)) {
			return syntexCorpus.get(corpusId).getAllSentenceIdsFromAllDocuments();
		}
		else {
			return new Vector<String>();
		}
	}

	public List<String> getAllTermIds() {
		return new Vector<String>(syntexTerms.keySet());
	}

	public List<String> getAllTermOccurenceIds() {
		return new Vector<String>(syntexTermOccurrences.keySet());
	}

	public List<String> getAllTermOccurenceIdsFromTermId(String termId) {
		if(termId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTerms.containsKey(termId)) {
			SyntexTerm localTerm = syntexTerms.get(termId);
			return localTerm.getOccurrenceIds();
		}
		else {
			return new Vector<String>();
		}
	}

	public List<String> getAllTermOccurrenceIdsFromSentenceId(String sentenceId) {
		// Method "getSentenceFromSentenceId" may throw "NullPointerException" if parameter is null
		SyntexSentence localSentence = getSentenceFromSentenceId(sentenceId);
		if(localSentence != null) {
			return localSentence.getOccurrenceIds();
		}
		else {
			return new Vector<String>();
		}
	}

	public String getCorpusIdFromDocumentId(String documentId) {
		if(documentId == null)
			throw new NullPointerException("Parameter cannot be null.");
		return documentId.substring(0, 9);
	}

	public String getCorpusLabel(String corpusId) {
		if(corpusId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexCorpus.containsKey(corpusId)) {
			return syntexCorpus.get(corpusId).getCorpusLabel();
		}
		else {
			return null;
		}
	}

	public String getDocumentLabel(String documentId) {
		if(documentId == null)
			throw new NullPointerException("Parameter cannot be null.");
		// Get the corpus id from document id
		String corpusId = this.getCorpusIdFromDocumentId(documentId);
		// Get the corpus instance and return sentences id.
		if(syntexCorpus.containsKey(corpusId)) {
			return syntexCorpus.get(corpusId).getSyntexDocument(documentId).getDocumentLabel();
		}
		else {
			return null;
		}
	}

	public String getRelatedSentenceIdFromTermOccurrenceId(String termOccurId) {
		if(termOccurId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTermOccurrences.containsKey(termOccurId)) {
			SyntexTermOccurrence localTermOccurrence = syntexTermOccurrences.get(termOccurId);
			return localTermOccurrence.getSyntexSentence().getSentenceId();
		}
		return null;
	}

	public String getRelatedTermIdFromTermOccurrenceId(String termOccurId) {
		if(termOccurId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTermOccurrences.containsKey(termOccurId)) {
			SyntexTermOccurrence localTermOccurrence = syntexTermOccurrences.get(termOccurId);
			return localTermOccurrence.getSyntexTerm().getTermId();
		}
		return null;
	}

	public String getSentenceContent(String sentenceId) {
		// "getSentenceFromSentenceId" may return "NullPointerException" if parameter is null
		SyntexSentence localSentence = getSentenceFromSentenceId(sentenceId);
		if(localSentence != null) {
			return localSentence.getSentence();
		}
		return null;
	}

	
	public String getTermLabel(String termId) {
		if(termId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTerms.containsKey(termId)) {
			return syntexTerms.get(termId).getLemme();
		}
		else {
			return null;
		}
	}

	public int getTermOccurrenceLength(String termOccurId) {
		if(termOccurId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTermOccurrences.containsKey(termOccurId)) {
			return syntexTermOccurrences.get(termOccurId).getOccurrenceLength();
		}
		return 0;
	}

	public int getTermOccurrencePosition(String termOccurId) {
		if(termOccurId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTermOccurrences.containsKey(termOccurId)) {
			return syntexTermOccurrences.get(termOccurId).getOccurrenceStartPosition();
		}
		return 0;
	}

	public boolean isIndifferentiatedTerm(String termId) {
		if(termId == null)
			throw new NullPointerException("Parameter cannot be null.");
		return isTerm(termId);
	}

	public boolean isNamedEntityTerm(String termId) {
		if(termId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTerms.containsKey(termId)) {
			return syntexTerms.get(termId).isNamedTerm();
		}
		else {
			return false;
		}
	}

	public boolean isTerm(String termId) {
		if(termId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTerms.containsKey(termId)) {
			return syntexTerms.get(termId).isTerm();
		}
		else {
			return false;
		}
	}

	public String getAdditionalInfo(String termRelId) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<String> getAllTermRelationIds() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLefttContentInSentence(String termRelId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getMiddleInSentence(String termRelId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRightContentInSentence(String termRelId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTermRelationMethod(String termRelId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTermRelationSentenceId(String termRelId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTermRelationTerm1Id(String termRelId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTermRelationTerm2Id(String termRelId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTermRelationTypeRelation(String termRelId) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isExaminated() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getTermCanonicalForm(String termId) {
		if(termId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTerms.containsKey(termId)) {
			return syntexTerms.get(termId).getLemme();
		}
		else {
			return null;
		}
	}

	public int getTermFrequency(String termId) {
		if(termId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTerms.containsKey(termId)) {
			return syntexTerms.get(termId).getSaillance().getFreq();
		}
		else {
			return -1;
		}
	}

	public int getTermHeadNeighborSize(String termId) {
		if(termId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTerms.containsKey(termId)) {
			return syntexTerms.get(termId).getSaillance().getNbvoiscont();
		}
		else {
			return -1;
		}
	}

	public int getTermHeadProductivity(String termId) {
		if(termId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTerms.containsKey(termId)) {
			return syntexTerms.get(termId).getSaillance().getProdT();
		}
		else {
			return -1;
		}
	}

	public int getTermModifierNeighborSize(String termId) {
		if(termId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTerms.containsKey(termId)) {
			return syntexTerms.get(termId).getSaillance().getNbvoisterm();
		}
		else {
			return -1;
		}
	}

	public int getTermModifierProductivity(String termId) {
		if(termId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTerms.containsKey(termId)) {
			return syntexTerms.get(termId).getSaillance().getProdE();
		}
		else {
			return -1;
		}
	}

	public int getTermPositionWeight(String termId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTermTfIdf(String termId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTermTypographicalWeight(String termId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTermVariantesSize(String termId) {
		if(termId == null)
			throw new NullPointerException("Parameter cannot be null.");
		if(syntexTerms.containsKey(termId)) {
			return syntexTerms.get(termId).getSaillance().getNbvar();
		}
		else {
			return -1;
		}
	}

	public List<String> getAllTermSyntacticRelationIds() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTermSyntacticRelationHeadTerm(String termSynRelId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTermSyntacticRelationModifierTerm(String termSynRelId) {
		// TODO Auto-generated method stub
		return null;
	}
	

	// ********************begin VALERY***********************
	//********* i prefer enter by syntex file instaed of syntex directory
	/**
	 * Detect path where all syntexFile are located using one selected
	 * syntexFile .
	 * 
	 * @param oneSyntexFile
	 * @see #addSyntexCorpus(File)
	 */
	public boolean addSyntexFile(File oneSyntexFile) throws IOException {
		boolean isDir = false;
		File syntexDirectory = oneSyntexFile.getParentFile();

		isDir = syntexDirectory.isDirectory();

		if (isDir) {

			List<File> filesInDirectory = Arrays.asList(syntexDirectory
					.listFiles());
			for (File fileInDirectory : filesInDirectory) {

				System.out.println(fileInDirectory);
			}
			this.addSyntexCorpus(syntexDirectory);
		}

		return isDir;
	}

	// ****************************************end VALERY***********************

	
		// *************begin new Spec ***********************

	
	//@Override
	public boolean initConnection(File filesDirectory) throws Exception {
		if (filesDirectory instanceof File) {
			File oneSyntexFile = (File) filesDirectory;

			this.addSyntexFile(oneSyntexFile);
		}
		return true;
		
	}

	
	public String getTermOccurrenceLabel(String termOccurId) {
		// TODO Auto-generated method stub
		return null;
	}

	//@Override
	public boolean importData() {
		List<ICorpus> allCorpus= new ArrayList<ICorpus>();
		
		System.out.println("*************************** begin  Load= "
				+ System.currentTimeMillis());

		// recuperation des propriétes pos et doc par defaut d'un term

		/*List<ITermProperty> termProps = dafoeSession.createCriteria(
				TermPropertyImpl.class).list();
		ITermProperty posProp = termProps.get(0);
		ITermProperty docProperty = termProps.get(1);
		*/

		List<String> corpusIds = this.getAllCorpusIds();

		System.out.println("CORPUS SIZE= " + corpusIds.size());

		// String currentCorpusId =corpusIds.get(0);

		// List<String> docsId=
		// corpusRessourceManager.getAllDocumentIdsFromCorpusId(currentCorpusId);

		for (int i = 0; i < corpusIds.size(); i++) {

			String currentCorpusId = corpusIds.get(i);

			// creation de la terminologie

			ITerminology myTerminology = TerminologyImpl.getInstance();
			myTerminology.setName("Terminology_" + currentCorpusId);
			myTerminology.setNameSpace("Terminology." + currentCorpusId);

			// VT: terminology language must be egal to corpus language: TO BE
			// IMPLEMENT
			myTerminology.setLanguage("Terminology_language");

			ICorpus currentCorpus = mapCorpus(currentCorpusId);

			List<String> docIds = this.getAllDocumentIdsFromCorpusId(currentCorpusId);

			System.out.println("DOC SIZE= " + docIds.size());

			for (int j = 0; j < docIds.size(); j++) {
				String currentDocId = docIds.get(j);

				IDocument currentDoc = mapDocument(currentDocId);

				List<String> sentences = this
						.getAllSentenceIdsFromDocumentId(currentDocId);

				for (int k = 0; k < sentences.size(); k++) {
					String currentSentenceId = sentences.get(k);

					ISentence currentSentence = mapSentence(currentSentenceId,
							k + 1);

					List<String> termOccs = this
							.getAllTermOccurrenceIdsFromSentenceId(currentSentenceId);

					for (int l = 0; l < termOccs.size(); l++) {

						String currentTermOccId = termOccs.get(l);

						ITermOccurrence currentTermOccurrence = mapTermoccurrence(currentTermOccId);

						String currentTermId = this
								.getRelatedTermIdFromTermOccurrenceId(currentTermOccId);

						ITerm currentTerm;

						// the term wasn't already mapped, then we map it and put
						// it into the hashMapTerm.
						if (!hashMapTerm.containsKey(currentTermId)) {

							currentTerm = mapTerm(currentTermId);

							hashMapTerm.put(currentTermId, currentTerm);

						} else {// the term was already created, then we
								// retrieve it from the hashMapTerm

							currentTerm = hashMapTerm.get(currentTermId);

						}
						myTerminology.addTerminoObject(currentTerm);
						//
						currentSentence.addTermOccurrence(
								currentTermOccurrence, currentTerm);

					} // fin termOcc

					currentDoc.addSentence(currentSentence);
				}// fin sentence
                System.out.println("currentCorpus.addDocument(currentDoc)");
				currentCorpus.addDocument(currentDoc);
			}// fin document

			
			
			dafoeSession = SessionFactoryImpl.getCurrentDynamicSession();
			try {

				// debut de la transaction
				Transaction tx = dafoeSession.beginTransaction();

				dafoeSession.save(myTerminology);

				dafoeSession.save(currentCorpus);

				// sauvegarde définitive
				tx.commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			allCorpus.add(currentCorpus);
		}// fin corpus

		System.out.println("*************************** end ="
				+ System.currentTimeMillis());

		return true;
	}
	
	    
	//************************ VT: MAPPING ************************************	
	/**
	 * Map corpus.
	 * 
	 * @param corpusId
	 *            the corpus id
	 * 
	 * @return the i corpus
	 */
	private ICorpus mapCorpus(String corpusId) {
		System.out.println("MAP CORPUS " + corpusId);

		ICorpus corpus = CorpusImpl.getInstance();
		corpus.setName(this.getCorpusLabel(corpusId));

		System.out.println("corpus= " + corpus.getName());
		return corpus;

	}

	

	/**
	 * Map document.
	 * 
	 * @param docId
	 *            the doc id
	 * 
	 * @return the i document
	 */
	private IDocument mapDocument(String docId) {
		System.out.println("MAP DOCUMENT " + docId);

		IDocument doc = new DocumentImpl();
		doc.setName(this.getDocumentLabel(docId));

		System.out.println("doc= " + doc.getName());
		return doc;
	}

	

	/**
	 * Map sentence.
	 * 
	 * @param sentenceId
	 *            the sentence id
	 * 
	 * @return the i sentence
	 */
	/*
	 * private ISentence mapSentence(String sentenceId) {
	 * System.out.println("MAP SENTENCE " + sentenceId);
	 * 
	 * ISentence sent = new SentenceImpl();
	 * sent.setContent(corpusResourceManager.getSentenceContent(sentenceId));
	 * 
	 * System.out.println("sentence= " + sent.getContent()); return sent; }
	 */

	private ISentence mapSentence(String sentenceId, int sentenceOrder) {
		System.out.println("MAP SENTENCE " + sentenceId);

		ISentence sent = new SentenceImpl();
		sent.setContent(this.getSentenceContent(sentenceId));
		sent.setOrderNumber(sentenceOrder);
		System.out.println("sentence= " + sent.getContent());
		return sent;
	}

	

	/**
	 * Map term.
	 * 
	 * @param termId
	 *            the term id
	 * 
	 * @return the i term
	 */
	private ITerm mapTerm(String termId) {
		System.out.println("MAP TERM " + termId);

		ITerm term = new TermImpl();
		term.setLabel(this.getTermCanonicalForm(termId));
		//VT: use type-safe approach
		term.setState(TERMINO_OBJECT_STATE.STUDIED);
		term.setLinguisticStatus(LINGUISTIC_STATUS.INDIFFERENTIATED);

		System.out.println("term= " + term.getLabel());
		
		// add saillance
		ITermSaillance saill=mapTermSaillance(termId);
		term.setSaillanceCriteria(saill);
		

		return term;
	}
   
	private ITermSaillance mapTermSaillance(String termId){
		System.out.println("MAP SAILLANCE for " + termId);
		ITermSaillance saill= new TermSaillanceImpl();
		
		saill.setFrequency(this.getTermFrequency(termId));
		saill.setHeadProductivity(this.getTermHeadProductivity(termId));
		saill.setModifierProductivity(this.getTermModifierProductivity(termId));
		saill.setNbVar(this.getTermVariantesSize(termId));
		saill.setPositionWeight(this.getTermPositionWeight(termId));
		saill.setTfIdf(this.getTermTfIdf(termId));
		saill.setTypographicalWeight(this.getTermTypographicalWeight(termId));
		
		
		return saill;
	}
	
	
	/**
	 * Map termoccurrence.
	 * 
	 * @param termOccId
	 *            the term occ id
	 * 
	 * @return the i term occurrence
	 */
	private ITermOccurrence mapTermoccurrence(String termOccId) {
		System.out.println("MAP TERMOCCURRENCE " + termOccId);

		ITermOccurrence to = new TermOccurrenceImpl();
		to.setPosition(this
				.getTermOccurrencePosition(termOccId));
		to.setLength(this.getTermOccurrenceLength(termOccId));

		System.out.println("termOcc pos= " + to.getPosition() + " length= "
				+ to.getLength());
		return to;

	}

	
// VT: end	
	
	
	// ****************************************end VALERY***********************

}
