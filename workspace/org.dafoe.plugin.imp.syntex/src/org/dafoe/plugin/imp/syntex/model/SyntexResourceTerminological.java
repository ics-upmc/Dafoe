package org.dafoe.plugin.imp.syntex.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermOccurrence;
import org.dafoe.framework.core.terminological.model.ITermSaillance;
import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.CorpusImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.SentenceImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermOccurrenceImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TerminologyImpl;
import org.dafoe.plugin.imp.syntex.model.SyntexTerm.SyntexCategory;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class SyntexResourceTerminological {
	/** The log level to be published on the console handler. */
	static final Level logLevel = Level.INFO;

	/** Logger system */
	private Logger log = null;
	
	private static final int TERMS_FLUSH_THRESHOLD = 5000;
	private static final int OCC_FLUSH_THRESHOLD = 120000;

	/** The normalized filenames for SyntexFile.
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
	private SortedMap<String, Integer> syntexTerms = null;
	private SortedMap<String, Integer> syntexSentences = null;

	/** Charset of syntex files */
	private static Charset ISO_LATIN_1 = Charset.forName("ISO-8859-1");

	/** Session of Dafoe */
	private Session dafoeSession = null;

	/** Build an empty syntex corpus instance.
	 */
	public SyntexResourceTerminological() {
		// Common fields
		syntexTerms = new TreeMap<String, Integer>();
		syntexSentences = new TreeMap<String, Integer>();
		// Log system
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(SyntexResourceTerminological.logLevel);
		log = Logger.getLogger(SyntexResourceTerminological.class.getName());
		log.setUseParentHandlers(false); // Do not use default handlers
		log.addHandler(consoleHandler);
		log.setLevel(SyntexResourceTerminological.logLevel);
		// Dafoe session
		dafoeSession = SessionFactoryImpl.getCurrentDynamicSession();
		dafoeSession.setFlushMode(FlushMode.COMMIT);
	}

	public void reset() {
		syntexTerms.clear();
		syntexSentences.clear();
	}

	/** Parsing a syntex corpus and commit it to Dafoe DB.
	 * @param syntexDirectory
	 * @return <code>true</code> if all is Ok, <code>false</code> otherwise.
	 * @throws NullPointerException If parameter is <code>null</code>.
	 * @throws IllegalArgumentException If parameter is not a valid syntex directory.
	 * @throws FileNotFoundException If path located by parameter does not exist.
	 * @throws IOException If something happens while loading syntex files.
	 */
	public boolean parseSyntexCorpus(File syntexDirectory) throws IOException {
		if(syntexDirectory == null)
			throw new NullPointerException("Path to syntex directory cannot be null");
		if(!syntexDirectory.exists()) 
			throw new FileNotFoundException("Path to syntex directory must exist: "+syntexDirectory);
		if(!syntexDirectory.isDirectory())
			throw new IllegalArgumentException("Path to syntex must be a directory: "+syntexDirectory);

		// Take the time!
		long before = System.currentTimeMillis();

		// Find the files,
		log.info("Search for the Syntex files in folder: "+syntexDirectory);
		File syntexOccFile = null;
		File syntexSeqFile = null;
		File syntexListeFile = null;
		for(File fileInDirectory : syntexDirectory.listFiles()) {
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
		reset(); // Make sure instance is ready to work

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
		//		SyntexCorpus localSyntexCorpus = new SyntexCorpus(generatedCorpusId, generatedCorpusLabel);

		// Force a frame using block to free all memory created in
		{
			log.info("Parsing Terminology (terms)");
			Integer terminoId;
			{
				Transaction tx = dafoeSession.beginTransaction();
				// Create a terminology
				ITerminology myTerminology = TerminologyImpl.getInstance();
				// Initialize needed, since extra lazy for terms
				Hibernate.initialize(myTerminology.getTerms());
				myTerminology.setName("Terminology_" + generatedCorpusId);
				myTerminology.setNameSpace("Terminology." + generatedCorpusId);
				// VT: terminology language must be egal to corpus language: TO BE
				// IMPLEMENT
				myTerminology.setLanguage("Terminology_language");
				// Get an ID
				if(myTerminology.getId() == null) {
					dafoeSession.save(myTerminology);
				}
				else {
					dafoeSession.update(myTerminology);
				}
				dafoeSession.flush();
				log.info("Committing Terminology Metadata to Dafoe DB");
				tx.commit();
				log.info("Committing Terminology Metadata successed.");
				terminoId = myTerminology.getId();
			}
			// Extract terms from syntex_liste file and fill terminology
			extractTermsFromSyntexListeFile(syntexListeFile, generatedCorpusId, terminoId);
			// Commiting termino
			log.info("Committing Terminology to Dafoe DB");
			//			dafoeSession.saveOrUpdate(myTerminology);
			//			dafoeSession.flush();
			log.info("Committing Terminology successed.");
		}

		{
			Transaction tx = dafoeSession.beginTransaction();
			CorpusImpl dafoeCorpus = new CorpusImpl();
			dafoeCorpus.setName(generatedCorpusLabel);
			dafoeCorpus.setLanguage("fr");

			// Extract sentences from syntex_seq file
			extractSentencesFromSyntexSeqFile(syntexSeqFile, generatedCorpusId, dafoeCorpus);

			log.info("Committing CorpusId to Dafoe DB");
			dafoeSession.save(dafoeCorpus);
			dafoeSession.flush();
			tx.commit();
			log.info("Committing CorpusId successed.");		
		}

		// Transaction and commit will be manage in the method
		log.info("Parsing Occurences");
		extractOccurencesFromSyntexOccFile(syntexOccFile, generatedCorpusId);
		log.info("Parsing Occurences OK");

		// I have finished!!!
		reset(); // Empty work memory

		// Take the final time!
		long duration = (System.currentTimeMillis() - before) / 1000L;

		log.info("Syntex import finished in: "+
				(duration / 60)+
				"min, "+
				(duration % 60)+
		"sec");		

		return true;
	}

	/** Get a Dafoe Term instance from an import term id. May be <code>null</code>.
	 * @param importTermId The import term id.
	 * @return The Dafoe instance of the term.
	 */
	private ITerm getDafoeTermFromImportId(String importTermId) {
		if(syntexTerms.containsKey(importTermId)) {
			return (ITerm)dafoeSession.load(
					TermImpl.class, syntexTerms.get(importTermId));
		}
		return null;
	}

	/** Get a Dafoe Sentence instance from an import sentence id. May be <code>null</code>.
	 * @param importSentenceId The import sentence id.
	 * @return The Dafoe instance of the sentence.
	 */
	private ISentence getDafoeSentenceFromImportId(String importSentenceId) {
		if(syntexSentences.containsKey(importSentenceId)) {
			return (ISentence)dafoeSession.load(
					SentenceImpl.class, syntexSentences.get(importSentenceId));
		}
		return null;		
	}

	/** Return the next lexicographic sentence id of "sentenceid" parameter in "sentences" map.
	 * @param sentenceId A sentence id.
	 * @return The next lexicographic sentence id.
	 * @throws IllegalArgumentException If "sentenceid" parameter is not a valid key in the map.
	 */
	public String getNextSentenceId(String sentenceId) {
		if(!syntexSentences.containsKey(sentenceId))
			throw new IllegalArgumentException("This document does not contain this id: "+sentenceId);
		SortedMap<String, Integer> subSentences = syntexSentences.tailMap(sentenceId);
		if(subSentences.size() < 2) {
			log.warning("SentenceId does not have successor!!! " +sentenceId);
		}
		String nextSentence = subSentences.keySet().toArray(new String[0])[1]; // The second element
		// Verify that the they were in the same document
		if(getDocumentIdFromSentenceId(sentenceId).equals(getDocumentIdFromSentenceId(nextSentence))) {
			log.warning("Next sentence if not consistent!!! " +sentenceId+" "+nextSentence);
			throw new IllegalArgumentException("Error while getting next sentence id. See logs for details.");
		}
		return nextSentence;
	}	

	/** Extract terms from file "syntex_occ" and fullfill map "syntexTermOccurrences".
	 * @param syntexOccFile The file "syntex_occ"
	 * @param generatedCorpusId The local corpus id
	 * @throws FileNotFoundException If the file is not found, which may not occured using this method in {@link #parseSyntexCorpus(File)}
	 * @throws IOException If something happens during reading the file
	 */
	private void extractOccurencesFromSyntexOccFile(File syntexOccFile, String generatedCorpusId) throws FileNotFoundException, IOException {
		String line;
		log.info("Read "+syntexOccFile+" file");
		BufferedReader occReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(syntexOccFile), ISO_LATIN_1));
		line = null;
		int lineCounter = 0;
		Set<String> usedSentenceId = new TreeSet<String>();
		String lastSentenceId = null;

		Transaction tx = dafoeSession.beginTransaction();

		while((line = occReader.readLine()) != null) {
			lineCounter++;
			// Do not read first line, always column title
			if(lineCounter == 1) {
				continue;
			}
			if(lineCounter % OCC_FLUSH_THRESHOLD == 0) {
				log.info("Threashold occurences readed, make flush (lines: "+lineCounter+")");
				// Commit and reinit transaction
				dafoeSession.flush();
				tx.commit();
				tx = dafoeSession.beginTransaction();
				log.info("Flush finished.");
			}

			String[] splittedLine = line.split("\t");
			String localDocId = splittedLine[0]; // docId of file "syntex_seq"
			String localParaId = splittedLine[1]; // seqId of file "syntex_seq"
			String localTermId = splittedLine[2]; // termId of file "syntex_liste"

			// Term of occurence
			String importTermId = SyntexTerm.buildTermId(generatedCorpusId, localTermId);
			ITerm pointedTerm = getDafoeTermFromImportId(importTermId);
			if(pointedTerm == null) {
				log.warning("This occurrence is ignored, term id does not exist: "+importTermId);
				log.warning("See for errors during term construction");
				continue;
			}
			// Sentence of occurrence
			String documentId = generatedCorpusId + localDocId;
			String initialSedId = SyntexDocument.buildBasicSeqId(documentId, Integer.valueOf(localParaId));
			String pointedSeqId = initialSedId;
			// This id was used before...
			if(lastSentenceId != null && 
					!lastSentenceId.startsWith(pointedSeqId) &&
					usedSentenceId.contains(pointedSeqId)) {

				while(usedSentenceId.contains(pointedSeqId)) {
					pointedSeqId = getNextSentenceId(pointedSeqId);
				}
				//				log.info("The id "+initialSedId+" was already used, replace by "+pointedSeqId);
			}
			ISentence pointedSentence = getDafoeSentenceFromImportId(pointedSeqId);
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

			// Creating Dafoe instance of occurence
			ITermOccurrence localTermOccurrence = new TermOccurrenceImpl();

			// FIXME Think about a workaround (using original form which was lost...)
			int searchingPos = pointedSentence.getContent().indexOf(pointedTerm.getLabel());
			if(searchingPos != -1) {
				localTermOccurrence.setPosition(searchingPos);
			}	
			else {
				localTermOccurrence.setPosition(0);
			}
			localTermOccurrence.setLength(pointedTerm.getLabel().length());
			pointedSentence.addTermOccurrence(localTermOccurrence, pointedTerm);

			// Commiting to Dafoe
			dafoeSession.update(pointedSentence);
			dafoeSession.update(pointedTerm);
			dafoeSession.save(localTermOccurrence);
		}
		log.info("Making last occurence commit");
		dafoeSession.flush();
		tx.commit();
		log.info("Last occurence commit successed");
		log.info("Totals occurences lines readed: "+lineCounter);
		occReader.close();
		//		
		//		log.info("Committing Occurences to Dafoe DB");
		//		log.info("Committing Occurences Successed");
	}

	/** Extract terms from file "syntex_seq" and fullfill map "syntexCorpus".
	 * @param syntexSeqFile The file "syntex_seq"
	 * @param generatedCorpusId The local corpus id
	 * @throws FileNotFoundException If the file is not found, which may not occured using this method in {@link #parseSyntexCorpus(File)}
	 * @throws IOException If something happens during reading the file
	 */
	private void extractSentencesFromSyntexSeqFile(File syntexSeqFile, String corpusId, CorpusImpl localSyntexCorpus) throws FileNotFoundException, IOException {
		// syntex_seq: contains all sentences, and document id
		log.info("Read "+syntexSeqFile+" file");
		BufferedReader seqReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(syntexSeqFile), ISO_LATIN_1));
		String line = null;
		int lineCounter = 0;
		SortedMap<String, SyntexDocument> documentCache = new TreeMap<String, SyntexDocument>();
		while((line = seqReader.readLine()) != null) {
			lineCounter++;
			// Do not read first line, always column title
			if(lineCounter == 1) {
				continue;
			}
			if(lineCounter % 1000 == 0) {
				log.info("Sentences readed: "+lineCounter);
			}
			// Loading data
			String[] splittedLine = line.split("\t");
			String docId = splittedLine[0];
			String importDocId = corpusId + docId;

			SyntexDocument localDocument = null;
			if(!documentCache.containsKey(importDocId)) {
				localDocument = new SyntexDocument(importDocId, docId);
				// Saving dafoe an building to attribute an Id
				dafoeSession.save(localDocument.getDafoeDocument());
				localSyntexCorpus.addDocument(localDocument.getDafoeDocument());
				documentCache.put(importDocId, localDocument);
			}
			else {
				localDocument = documentCache.get(importDocId) ;
			}

			// Now work on the sentence
			String sentId = splittedLine[1];
			String sentence = splittedLine[2];
			String importSentenceId = localDocument.buildNextUniqueSentenceId(
					Integer.valueOf(sentId), lineCounter);

			// Building Sentence
			SyntexSentence localSentence = new SyntexSentence(importSentenceId, sentence, lineCounter);
			ISentence dafoeSentence = localSentence.getDafoeSentence();
			dafoeSession.save(dafoeSentence); // Get an Id (necessary for Hashset)
			syntexSentences.put(importSentenceId, dafoeSentence.getId());

			// Adding Sentence to Dafoe doc (adding doc also doc to sentence) 
			localDocument.getDafoeDocument().addSentence(dafoeSentence);
		}
		log.info("Totals sentences lines readed: "+lineCounter);
		seqReader.close();
	}

	/** Extract terms from file "syntex_liste" and fulfill map "syntexTerms".
	 * @param syntexListeFile The file "syntex_liste"
	 * @param generatedCorpusId The corpus id
	 * @throws FileNotFoundException If the file is not found, which may not occurred using this method in {@link #parseSyntexCorpus(File)}
	 * @throws IOException If something happens during reading the file
	 */
	private void extractTermsFromSyntexListeFile(
			File syntexListeFile,
			String generatedCorpusId,
			Integer myTerminoId)
	throws FileNotFoundException, IOException {

		String line = null;
		// 2- syntex_liste: terms
		log.info("Read "+syntexListeFile+" file");
		BufferedReader termsReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(syntexListeFile), ISO_LATIN_1));
		int lineCounter=0;

		Transaction tx = dafoeSession.beginTransaction();

		ITerminology myTerminology = (ITerminology)dafoeSession.load(
				TerminologyImpl.class, myTerminoId);

		while((line = termsReader.readLine()) != null) {
			lineCounter++;
			if(lineCounter % TERMS_FLUSH_THRESHOLD == 0) {
				log.info("Terms readed, make flush (lines: "+lineCounter+")");
				dafoeSession.update(myTerminology);
				// Commit and reinit transaction
				dafoeSession.flush();
				tx.commit();
				tx = dafoeSession.beginTransaction();
				log.info("Flush finished.");
				myTerminology = (ITerminology)dafoeSession.load(
						TerminologyImpl.class, myTerminoId);
			}
			String[] splittedLine = line.split("\t");
			// Do not read first line, always column title
			if(lineCounter == 1) {
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

			// Build term
			String importTermId = SyntexTerm.buildTermId(generatedCorpusId, termId);			
			SyntexTerm localTerm = new SyntexTerm(importTermId, localCat, lemme, originalForm);
			ITerm dafoeTerm = localTerm.getDafoeTerm(); // Create Dafoe instance
			localTerm = null; // GC free
			dafoeSession.save(dafoeTerm); // Attribute id, necessary for hashcode

			// Parsing Saillance (do not manage id saillance, made by "setSaillanceCriteria")
			SyntexTermSaillance localSaillance = new SyntexTermSaillance(splittedLine, localTerm);
			ITermSaillance dafoeSaillance = localSaillance.getDafoeTermSaillance();
			localSaillance = null; // GC free

			// Assigning Saillance to Term (Dafoe/Hibernate)
			// This method manage respective assignment and "save" of ITermSaillance
			dafoeTerm.setSaillanceCriteria(dafoeSaillance);

			// Adding terms to this termino-object (resp. assignment)
			myTerminology.addTerminoObject(dafoeTerm);

			// Mapping terms
			assert dafoeTerm.getId() != null; // At least one call to save before
			syntexTerms.put(importTermId, dafoeTerm.getId());
		}
		log.info("Making last term commit");
		dafoeSession.update(myTerminology);
		dafoeSession.flush();
		tx.commit();
		log.info("Last term commit successed");

		log.info("Totals terms lines readed: "+lineCounter);
		termsReader.close();
	}

	/** Get the corpusId from a document Id.
	 * @param documentId The document id.
	 * @return The corpus id.
	 */
	public String getCorpusIdFromDocumentId(String documentId) {
		if(documentId == null)
			throw new NullPointerException("Parameter cannot be null.");
		return documentId.substring(0, 9);
	}

	public String getDocumentIdFromSentenceId(String sentenceId) {
		if(sentenceId == null)
			throw new NullPointerException("Parameter cannot be null.");
		return sentenceId.substring(0, sentenceId.lastIndexOf('-'));
	}

	//	/** Get a document instance from an document id.
	//	 * @param sentenceId
	//	 * @return An instance of SyntexSentence, or <code>null</code> if nothing is found.
	//	 * @throws IllegalArgumentException If parameter is <code>null</code>.
	//	 */
	//	private SyntexDocument getDocumentFromDocumentId(String docId) {
	//		if(docId == null) 
	//			throw new IllegalArgumentException("Dcoument Id cannot be null");
	//		String corpusId = getCorpusIdFromDocumentId(docId);
	//		if(syntexCorpus.containsKey(corpusId)) {
	//			SyntexCorpus corpus = syntexCorpus.get(corpusId);
	//			return corpus.getSyntexDocument(docId);
	//		}
	//		// If not found...
	//		return null;
	//	}
	//	
	//	/** Get a sentence instance from an sentence id.
	//	 * @param sentenceId
	//	 * @return An instance of SyntexSentence, or <code>null</code> if nothing is found.
	//	 * @throws IllegalArgumentException If parameter is <code>null</code>.
	//	 */
	//	private SyntexSentence getSentenceFromSentenceId(String sentenceId) {
	//		if(sentenceId == null) 
	//			throw new IllegalArgumentException("Sentence Id cannot be null");
	//		String docId = sentenceId.substring(0, sentenceId.lastIndexOf('-'));
	//		SyntexDocument document = getDocumentFromDocumentId(docId);
	//		if(document != null) {
	//			return document.getSyntexSentence(sentenceId);
	//		}
	//		// If not found...
	//		return null;
	//	}

}
