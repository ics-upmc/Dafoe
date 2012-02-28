package org.dafoe.plugin.load.corpus.yatea.model;

/**
 * 
 * @author sylvie Szulman
 * LIPN
 * Projet Dafoe
 * 2009
 * class for a corpus 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a> CRITT CRCFAO
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
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
import org.dafoe.plugin.load.corpus.yatea.adaptater.Services;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ImportYATEA  {
	
	/** Common variables **/

	/** name of the treetagger file in xml format **/
	public String nomFichTTXML = "fTempTT2XML.xml";
	/** name of the corpus in xml format **/
	public String nomFichCorpusXML = "fTempCorpus2XML.xml";
	/** Yatea file name **/
	private String filename = null;
	/** yatea file reference **/
	private File yateaFile = null;
	/** yatea directory **/
	private File yateaDirectory = null;

	/** The log level to be published on the console handler. */
	static final Level logLevel = Level.INFO;
	/** Logger system */
	private Logger log = null;

	/** Yatea documents in this corpus. */
	private Map<String, YateaDoc> ensDoc = new TreeMap<String, YateaDoc>();

	/** if use of many corpus **/
	public static Map<String, ImportYATEA> YateaLesCorpus = new TreeMap<String, ImportYATEA>();

	/** Yatea corpus id */
	private String corpusId = null;

	/** table contains for each ID, its instance of YateaTerm **/
	private TreeMap<String, YateaTerm> ensCTYatea = new TreeMap<String, YateaTerm>();

	public static ResourceBundle res = ResourceBundle
			.getBundle("org.dafoe.plugin.load.corpus.yatea.outils.ResYatea");
	
	//VT:
	private Map<String, ITerm> hashMapTerm = new HashMap<String, ITerm>();
	
	/** The dafoe session. */
	private Session dafoeSession;

	public ImportYATEA() {

	}

	public void initYateaFile(File yateaResultFileXML) throws Exception {
		// language used
		if (Locale.getDefault() == Locale.ENGLISH) {
			ImportYATEA.res = ResourceBundle
					.getBundle("org.dafoe.plugin.load.corpus.yatea.outils.ResYatea_UK");
		}
		// Log system
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(ImportYATEA.logLevel);
		log = Logger.getLogger(ImportYATEA.class.getName());
		log.setUseParentHandlers(false); // Do not use default handlers
		log.addHandler(consoleHandler);
		log.setLevel(ImportYATEA.logLevel);
		/** initialize file yate and directory **/
		this.yateaFile = yateaResultFileXML;
		this.filename = yateaResultFileXML.getName();
		this.yateaDirectory = yateaResultFileXML.getParentFile();
		if (yateaDirectory == null)
			throw new NullPointerException(
					"Path to yatea directory cannot be null");

		// Find the files,
		log.info("Search for the Yatea file in folder: " + yateaDirectory);
		List<File> filesInDirectory = Arrays.asList(yateaDirectory.listFiles());
		log.info("Open the file yatea...");
		// Now, open the file!!!
		if (!(this.readCorpusXML()))
			throw new Exception(ImportYATEA.res.getString("pas_charg_yatea"));

		log.info("Corpus Id generated is: " + this.corpusId);

		// Build the yatea corpus instance
		ImportYATEA.YateaLesCorpus.put(corpusId, this);

	}

	public TreeMap<String, YateaTerm> getEnsCTYatea() {
		return ensCTYatea;
	}
	
	
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dafoe.framework.core.loader.IResourceCorpus#getAllCorpusIds()
	 * the corpus ID is the name of the file without extension
	 */
	public List<String> getAllCorpusIds() {

		List<String> corpusIds = new ArrayList<String>();

		corpusIds.add(this.corpusId);

		return corpusIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dafoe.framework.core.loader.IResourceCorpus#getAllDocumentIds()
	 * 
	 * @return all document ids
	 * 
	 * Each document has a number ID
	 */
	public List<String> getAllDocumentIds() {
		Set<String> ensIDDoc = this.ensDoc.keySet();
		return new ArrayList<String>(ensIDDoc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.loader.IResourceCorpus#getAllDocumentIdsFromCorpusId
	 * (java.lang.String) ??
	 */
	public List<String> getAllDocumentIdsFromCorpusId(String corpusId) {

		if (corpusId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));

		if (this.corpusId.equals(corpusId)) {
			return this.getAllDocumentIds();
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.loader.IResourceCorpus#
	 * getAllRelatedSentenceIdsFromTermId(java.lang.String)
	 */
	public List<String> getAllRelatedSentenceIdsFromTermId(String termId) {
		if (termId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		YateaTerm instYateaTerm = this.ensCTYatea.get(termId);
		return instYateaTerm.getListSentenceOccu();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.loader.IResourceCorpus#
	 * getAllRelatedTermIdsFromSentenceId(java.lang.String) Returns all related
	 * term ids from sentence id.
	 * 
	 * @param sentenceId the sentence id
	 * 
	 * @return all related term ids from sentence id
	 */
	public List<String> getAllRelatedTermIdsFromSentenceId(String sentenceId) {
		if (sentenceId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		ArrayList<String> ensTermsIds = new ArrayList<String>();
		Set<String> lesTermesID = this.ensCTYatea.keySet();
		for (String x : lesTermesID) {
			// return all the occurrences of the term
			YateaTerm aTerm = this.ensCTYatea.get(x);
			// take all the occurrences of a term
			// test if the sentence of each occurrence is sentenceId
			ArrayList<YateaOccurrence> ensOccu = aTerm.getLIST_OCCURRENCES();
			// each occurrence know its sentence
			for (YateaOccurrence aOccu : ensOccu) {
				if (sentenceId.equals(aOccu.getSENTENCE())) {
					ensTermsIds.add(x);
				}
			}

		}
		return ensTermsIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dafoe.framework.core.loader.IResourceCorpus#getAllSentenceIds()
	 * Returns all sentence ids.
	 * 
	 * @return all sentence ids
	 */
	public List<String> getAllSentenceIds() {
		Set<String> ensIdDoc = this.ensDoc.keySet();
		ArrayList<String> ensAllSent = new ArrayList<String>();
		for (String aIdDoc : ensIdDoc) {
			ensAllSent.addAll(this.getAllSentenceIdsFromDocumentId(aIdDoc));
		}
		return ensAllSent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.loader.IResourceCorpus#
	 * getAllSentenceIdsFromDocumentId(java.lang.String) Returns all sentence
	 * ids from document id.
	 * 
	 * @param documentId the document id
	 * 
	 * @return all sentence ids from document id
	 */
	public List<String> getAllSentenceIdsFromDocumentId(String documentId) {
		if (documentId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		ArrayList<String> ensAllSent = new ArrayList<String>();
		YateaDoc aDoc = this.ensDoc.get(documentId);
		ArrayList<YateaSentence> ensSentADoc = aDoc.getEnsSent();
		for (YateaSentence aSent : ensSentADoc) {
			ensAllSent.add(aSent.getID());
		}
		return ensAllSent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dafoe.framework.core.loader.IResourceCorpus#getAllTermIds() *
	 * Returns all term ids.
	 * 
	 * @return all term ids
	 */
	public List<String> getAllTermIds() {
		return new ArrayList<String>(this.ensCTYatea.keySet());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.loader.IResourceCorpus#getAllTermOccurenceIds()
	 */
	public List<String> getAllTermOccurenceIds() {
		ArrayList<String> ensAllTermOccuIds = new ArrayList<String>();
		List<String> ensAllTermsId = this.getAllTermIds();
		for (String x : ensAllTermsId) {
			ensAllTermOccuIds.addAll(getAllTermOccurenceIdsFromTermId(x));
		}
		return ensAllTermOccuIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.loader.IResourceCorpus#
	 * getAllTermOccurenceIdsFromTermId(java.lang.String)
	 */
	public List<String> getAllTermOccurenceIdsFromTermId(String termId) {
		if (termId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		ArrayList<String> ensATermOccuIds = new ArrayList<String>();
		YateaTerm aTerm = this.ensCTYatea.get(termId);
		ArrayList<YateaOccurrence> ensYateaoccu = aTerm.getLIST_OCCURRENCES();
		for (YateaOccurrence aOccu : ensYateaoccu) {
			ensATermOccuIds.add(aOccu.getID());
		}
		return ensATermOccuIds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.loader.IResourceCorpus#
	 * getAllTermOccurrenceIdsFromSentenceId(java.lang.String) * Returns all
	 * term occurrence ids from sentence id.
	 * 
	 * @param sentenceId the sentence id
	 * 
	 * @return all term occurrence ids from sentence id
	 */
	public List<String> getAllTermOccurrenceIdsFromSentenceId(String sentenceId) {
		if (sentenceId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		ArrayList<String> ensAllTermOccuIdsSent = new ArrayList<String>();
		Set<String> lesTermesID = this.ensCTYatea.keySet();
		for (String x : lesTermesID) {
			// return all the occurrences of the term
			YateaTerm aTerm = this.ensCTYatea.get(x);
			// take all the occurrences of a term
			// test if the sentence of each occurrence is sentenceId
			ArrayList<YateaOccurrence> ensOccu = aTerm.getLIST_OCCURRENCES();
			// each occurrence know its sentence
			for (YateaOccurrence aOccu : ensOccu) {
				if (sentenceId.equals(aOccu.getSENTENCE())) {
					ensAllTermOccuIdsSent.add(aOccu.getID());
				}
			}

		}
		return ensAllTermOccuIdsSent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.loader.IResourceCorpus#getCorpusIdFromDocumentId
	 * (java.lang.String) each ID document must be different returns corpus id
	 * from document id.
	 * 
	 * @param documentId the document id
	 * 
	 * @return the corpus id from document id
	 */
	public String getCorpusIdFromDocumentId(String documentId) {
		if (documentId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		// ID document = ID corpus + "_"+idDoc given by yatea
		return documentId.substring(0, documentId.indexOf("_"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.loader.IResourceCorpus#getCorpusLabel(java.lang
	 * .String) the corpuslbel is the corpus ID
	 */
	public String getCorpusLabel(String corpusId) {
		if (corpusId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		return corpusId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.loader.IResourceCorpus#getDocumentLabel(java
	 * .lang.String)
	 * 
	 * @return all document ids
	 * 
	 * no distinction between document label and document ID
	 */
	public String getDocumentLabel(String documentId) {
		if (documentId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		return documentId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.loader.IResourceCorpus#
	 * getRelatedSentenceIdFromTermOccurrenceId(java.lang.String) return null if
	 * termOccurId is not an occuId
	 */
	public String getRelatedSentenceIdFromTermOccurrenceId(String termOccurId) {
		if (termOccurId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		YateaTerm aTerm = this.getYateaTermFromOccur(termOccurId);
		List<YateaOccurrence> ensOccu = aTerm.getLIST_OCCURRENCES();
		for (YateaOccurrence aOccu : ensOccu) {
			if (aOccu.getID().equals(termOccurId))
				return aOccu.getSENTENCE();
		}
		// if not found
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.loader.IResourceCorpus#
	 * getRelatedTermIdFromTermOccurrenceId(java.lang.String) return null if
	 * termOccurId does not exists
	 */
	public String getRelatedTermIdFromTermOccurrenceId(String termOccurId) {
		if (termOccurId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		List<String> ensAllTermsId = this.getAllTermIds();
		for (String x : ensAllTermsId) {
			YateaTerm aTerm = this.ensCTYatea.get(x);
			List<YateaOccurrence> ensOccu = aTerm.getLIST_OCCURRENCES();
			for (YateaOccurrence aOccu : ensOccu) {
				if (aOccu.getID().equals(termOccurId))
					return x;
			}
		}
		// if not found
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.loader.IResourceCorpus#getSentenceContent(java
	 * .lang.String) * Returns sentence content.
	 * 
	 * @param sentenceId the sentence id
	 * 
	 * @return the sentence content
	 */
	public String getSentenceContent(String sentenceId) {
		if (sentenceId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		YateaSentence aSent = YateaSentence.getUneSentence(sentenceId);

		return aSent.getContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.loader.IResourceCorpus#getTermLabel(java.lang
	 * .String) * Returns term label.
	 * 
	 * Gets the term occurrence label.
	 * 
	 * @param termOccurId the term occur id
	 * 
	 * @return the term occurrence label
	 */
	public String getTermOccurrenceLabel(String termOccurId) {
		if (termOccurId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		YateaTerm aTerm = this.getYateaTermFromOccur(termOccurId);
		if (aTerm == null)
			return null;
		for (YateaOccurrence x : aTerm.getLIST_OCCURRENCES()) {
			if (x.getID().equals(termOccurId)) {
				System.out.println("x.getID() = " + x.getID());
				return x.getTextOccu();
			}
		}
		// if not found
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.loader.IResourceCorpus#getTermOccurrenceEndPosition
	 * (java.lang.String) /** Returns term occurrence length.
	 * 
	 * @param termOccurId the term occur id
	 * 
	 * @return the term occurrence length
	 */

	public int getTermOccurrenceLength(String termOccurId) {
		if (termOccurId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		List<String> ensAllTermsId = this.getAllTermIds();
		for (String x : ensAllTermsId) {
			YateaTerm aTerm = this.ensCTYatea.get(x);
			List<YateaOccurrence> ensOccu = aTerm.getLIST_OCCURRENCES();
			for (YateaOccurrence aOccu : ensOccu) {
				int length = Integer.parseInt(aOccu.getEND_POSITION())
						- Integer.parseInt(aOccu.getSTART_POSITION());
				if (aOccu.getID().equals(termOccurId))
					return length;
			}
		}
		// if not found
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.dafoe.framework.core.loader.IResourceCorpus#
	 * getTermOccurrenceStartPosition(java.lang.String)
	 */
	public int getTermOccurrencePosition(String termOccurId) {
		if (termOccurId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		List<String> ensAllTermsId = this.getAllTermIds();
		for (String x : ensAllTermsId) {
			YateaTerm aTerm = this.ensCTYatea.get(x);
			List<YateaOccurrence> ensOccu = aTerm.getLIST_OCCURRENCES();
			for (YateaOccurrence aOccu : ensOccu) {
				if (aOccu.getID().equals(termOccurId))
					return Integer.parseInt(aOccu.getSTART_POSITION());
			}
		}
		// if not found
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.loader.IResourceCorpus#isIndifferentiatedTerm
	 * (java.lang.String)
	 */
	public boolean isIndifferentiatedTerm(String termId) {
		if (termId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		return isTerm(termId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.dafoe.framework.core.loader.IResourceCorpus#isTerm(java.lang.String)
	 */
	public boolean isTerm(String termId) {
		if (termId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		return this.ensCTYatea.containsKey(termId);
	}

	public boolean isNamedEntityTerm(String termId) {
		if (termId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		return false;
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

	
	// ***** adhoc approach for term saillance managment : TO BE DELETE FOR A GENERIC APPROACH ****
	public int getTermFrequency(String termId) {
		return this.getAllTermOccurenceIdsFromTermId(termId).size(); 
	}

	public int getTermHeadNeighborSize(String termId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTermHeadProductivity(String termId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTermModifierNeighborSize(String termId) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getTermModifierProductivity(String termId) {
		// TODO Auto-generated method stub
		return 0;
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
		// TODO Auto-generated method stub
		return 0;
	}
//**** end term saillance
	
	

	/**
	 * Test if the file in parameter has the name of this instance.
	 * 
	 * @param file
	 * @return <code>true</code> if this corresponds, <code>false</code>
	 *         otherwise.
	 */
	public boolean nameCorrespondsToTheFile(File file) {
		return file.getName().toLowerCase().equals(this.filename.toLowerCase());
	}

	/**
	 * Read Yatea file return true if accurate else false
	 */

	public boolean readCorpusXML() throws Exception {

		// definition of corpus ID
		this.setCorpusIds();
		Document doc = null;
		Element racine = null;

		SAXBuilder builder = new SAXBuilder(false);
		// for ignoring the dtd line
		builder.setValidation(false);
		builder
				.setFeature(
						"http://apache.org/xml/features/nonvalidating/load-external-dtd",
						false);

		doc = builder.build(yateaFile);
		racine = doc.getRootElement();

		// <!ELEMENT TERM_EXTRACTION_RESULTS (LIST_TERM_CANDIDATES,LIST_WORDS)>

		Element listeTC = racine.getChild("LIST_TERM_CANDIDATES");
		java.util.List<Element> ensTC = listeTC.getChildren("TERM_CANDIDATE");

		for (Iterator<Element> i = ensTC.iterator(); i.hasNext();) {
			Element unElemTc = i.next();
			YateaTerm tc = new YateaTerm();
			tc.litXml(unElemTc);
			// load by ID
			this.ensCTYatea.put(tc.getID(), tc);
		}
		this.readCorpusTTandYatea();
		this.readCorpusYateaXML();

		return true;

	}

	public void transformeTreeTagger_XML() {

		String fileNameTT = this.filename.substring(0, this.filename
				.indexOf("."))
				+ ".tt";
		File f = new File(this.yateaDirectory, fileNameTT);
		File ftempTT2XML = null; // output file

		try {
			// creating an temporary file for the treettager file in xml format
			ftempTT2XML = new File(this.yateaDirectory, this.nomFichTTXML);

			// creating an temporary file for the corpus file in xml format
			File ftempCorpus2XML = new File(this.yateaDirectory,
					this.nomFichCorpusXML);

			// open file txt treetagger
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			// open file xml treetagger
			FileWriter writer;
			writer = new FileWriter(ftempTT2XML);
			// open corpus file in format xml
			FileWriter writerCorpus = new FileWriter(ftempCorpus2XML);
			// entete du fichier
			// <!ELEMENT corpus (doc*)>
			// <!ELEMENT doc(sentence*)>

			org.jdom.Element corpus = new Element("CORPUS");
			Element corpusSent = new Element("CORPUS");
			Document myDocument = new Document(corpus);
			// corpus document
			Document corpusDocument = new Document(corpusSent);

			org.jdom.Element doc = new Element("DOC");
			org.jdom.Element docCorpus = new Element("DOC");

			corpus.addContent(doc);
			corpusSent.addContent(docCorpus);
			// processing Texte_3349 DOCUMENT Texte_3349
			String line = br.readLine();
			StringTokenizer st = new StringTokenizer(line);

			String id_doc = "1";
			int indSent = 1;

			doc.setAttribute("ID", id_doc); // ajout de l'identifiant du
											// document
			docCorpus.setAttribute("ID", id_doc);

			// first sentence
			Element sent = new Element("SENTENCE");
			sent.setAttribute("numero", new Integer(indSent).toString());
			// first sentence of the corpus
			Element sentCorpus = new Element("SENTENCE");
			sentCorpus.setAttribute("numero", new Integer(indSent).toString());

			// test if the first line contains the line Document
			int indDocument = line.indexOf("DOCUMENT");
			if (indDocument != -1) {
				line = br.readLine();
				st = new StringTokenizer(line);
			}

			int indxD = 0;
			int indxF = 0;
			String ph = "";

			while (line != null) {
				Element descMot = new Element("DESC_MOT");
				st = new StringTokenizer(line);
				String mot = st.nextToken("\t");
				// compute a sent
				ph = ph + mot + " ";
				Element elemMot = new Element("mot");

				elemMot.addContent(mot);
				descMot.addContent(elemMot);
				String type = "";
				if (st.hasMoreTokens()) {
					type = st.nextToken("\t");
					Element elemType = new Element("typeGram");
					elemType.addContent(type);
					descMot.addContent(elemType);
					String lemme = st.nextToken("\t");
					Element elemLemme = new Element("lemme");
					elemLemme.addContent(lemme);
					descMot.addContent(elemLemme);
					indxF = indxD + mot.length();
				}

				if (type.equals("DOCUMENT")) {

					// new document

					// initialization
					ph = "";
					// update count of doc
					doc = new Element("DOC");
					corpus.addContent(doc);
					docCorpus = new Element("DOC");
					corpusSent.addContent(docCorpus);
					int id_doc_int = Integer.parseInt(id_doc);
					id_doc_int++;
					id_doc = "" + id_doc_int;
					doc.setAttribute("ID", id_doc);
					docCorpus.setAttribute("ID", id_doc);
					indSent = 1;
					// create new sentence
					sent = new Element("SENTENCE");
					sent
							.setAttribute("numero", new Integer(indSent)
									.toString());
					sentCorpus = new Element("SENTENCE");
					sentCorpus.setAttribute("numero", new Integer(indSent)
							.toString());
					ph = "";
				} else {
					if ((type.trim().equals("SENT"))
							|| type.equals("SENT_NOPUNCT")) {

						// last of sentence
						indxD = 0;
						doc.addContent(sent);
						docCorpus.addContent(sentCorpus);
						sentCorpus.addContent(ph);
						// create new sentence
						indSent++;
						sent = new Element("SENTENCE");
						sent.setAttribute("numero", new Integer(indSent)
								.toString());
						sentCorpus = new Element("SENTENCE");
						sentCorpus.setAttribute("numero", new Integer(indSent)
								.toString());
						ph = "";
					} else {
						Element nbChDeb = new Element("START_POSITION");
						nbChDeb.addContent(new Integer(indxD).toString());
						descMot.addContent(nbChDeb);
						Element nbChFin = new Element("END_POSITION");
						nbChFin.addContent(new Integer(indxF - 1).toString());
						descMot.addContent(nbChFin);
						indxD = indxF + 1;
						sent.addContent(descMot);
					}
				}
				line = br.readLine();

			}// end of read
			// last sentence
			doc.addContent(sent);
			docCorpus.addContent(sentCorpus);
			sentCorpus.addContent(ph);

			br.close();
			fr.close();

			// writing
			Format form = Format.getPrettyFormat();
			form.setEncoding("ISO-8859-1");
			XMLOutputter outputter = new XMLOutputter(form);
			outputter.output(myDocument, writer);
			writer.close();

			XMLOutputter outputterC = new XMLOutputter(form);
			outputterC.output(corpusDocument, writerCorpus);
			writerCorpus.close();
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	/**
	 * read Treetagger file creating Treetagger file in format XML
	 * 
	 * @return
	 */
	public void readCorpusTTandYatea() throws Exception {

		// //creating Treetagger file in format XML
		this.transformeTreeTagger_XML();
		File f = new File(this.yateaDirectory, this.nomFichTTXML);
		SAXBuilder builder = new SAXBuilder(false);
		if (f == null)
			return;
		Document doc = builder.build(f);
		org.jdom.Element racine = doc.getRootElement();
		java.util.List<org.jdom.Element> listeElemDoc = racine
				.getChildren("DOC");

		for (Iterator<Element> i = listeElemDoc.iterator(); i.hasNext();) {
			Element unElem = i.next();
			YateaDoc unDoc = new YateaDoc();
			unDoc.litXml(unElem);
			// modify ID document to have a unique ID if many corpus are used
			String ancIDDoc = unDoc.getID();
			String newIDDoc = this.corpusId + "_" + ancIDDoc;
			this.ensDoc.put(newIDDoc, unDoc);
			unDoc.setID(newIDDoc);
		}
		// add text occurrences
		this.readCorpusYateaXML();
	}

	/**
	 * Read corpus as used by Yatea //<!ELEMENT corpus(doc*)> //<!ELEMENT
	 * doc(sentence *)> //<!ELEMENT sentence #PCDATA > //<!Attribute sentence
	 * numero >
	 */
	public void readCorpusYateaXML() throws Exception {

		File f = new File(this.yateaDirectory, this.nomFichCorpusXML);
		SAXBuilder builder = new SAXBuilder(false);
		Document doc = builder.build(f);
		org.jdom.Element racine = doc.getRootElement();

		List<Element> listeDoc = racine.getChildren("DOC");
		for (Iterator<Element> i = listeDoc.iterator(); i.hasNext();) {
			Element unDoc = i.next();
			String id_doc = unDoc.getAttributeValue("ID");

			List<Element> ensSent = unDoc.getChildren("SENTENCE");

			for (Iterator<Element> j = ensSent.iterator(); j.hasNext();) {
				Element unSent = j.next();
				String numero = unSent.getAttributeValue("numero");
				String texte = unSent.getText();
				Vector<String> ligne = new Vector<String>();
				ligne.add(id_doc);
				ligne.add(numero);
				ligne.add(texte);
			}
		}
	}

	public void setCorpusIds() {
		// the label is the name without its extension
		this.corpusId = yateaFile.getName().substring(0,
				yateaFile.getName().indexOf("."));
	}

	/**************************************************************************************/

	/**
	 * Gets the canonical form of the term.
	 * 
	 * @param termId
	 *            the term identifier
	 * 
	 * @return the canonical form of the term
	 */
	public String getTermCanonicalForm(String termId) {
		if (termId == null)
			throw new NullPointerException(ImportYATEA.res
					.getString("Para_null"));
		ArrayList<String> ensATermOccuIds = new ArrayList<String>();
		YateaTerm aTerm = this.ensCTYatea.get(termId);
		return aTerm.getLEMMA();
	}

	/**
	 * Gets the frequency.
	 * 
	 * @param termId
	 *            the term identifier
	 * 
	 * @return the frequency
	 */

	/**
	 * return an instance of a term corresponding to an occurrence
	 */
	private YateaTerm getYateaTermFromOccur(String termOccurId) {
		List<String> ensAllTermsId = this.getAllTermIds();
		for (String x : ensAllTermsId) {
			YateaTerm aTerm = this.ensCTYatea.get(x);
			List<YateaOccurrence> ensOccu = aTerm.getLIST_OCCURRENCES();
			for (YateaOccurrence aOccu : ensOccu) {
				if (aOccu.getID().equals(termOccurId))
					return aTerm;
			}
		}
		// if not found
		return null;

		

	}
	
	//**************** VT:
	//@Override
	public boolean initConnection(File filesDirectory) throws Exception {
		if (filesDirectory instanceof File) {
			File yateaFile = Services.findYateaFileTerms(filesDirectory);
 
			initYateaFile(yateaFile);
		}
		return true;

	}


//************** VT: 
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
	
}// fin classe
