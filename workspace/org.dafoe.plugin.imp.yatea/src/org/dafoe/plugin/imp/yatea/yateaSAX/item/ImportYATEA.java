/**
 * 
 */
package org.dafoe.plugin.imp.yatea.yateaSAX.item;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.dafoe.framework.core.terminological.model.ICorpus;
import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSource;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.CorpusImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TerminologyImpl;
import org.dafoe.plugin.imp.yatea.yateaSAX.parse.SentenceCacheMap;
import org.dafoe.plugin.imp.yatea.yateaSAX.parse.SyntacticHandler;
import org.dafoe.plugin.imp.yatea.yateaSAX.parse.YateaHandler;
import org.dafoe.plugin.imp.yatea.yateaSAX.parse.YateaSentencesParser;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class containing the function that executes the chain of process to load
 * Yatea's resource into the database.
 * 
 * TODO : Take off the "hack" functions to used those of the framework.
 * 
 * @author Athier
 * @author <a href="mailto:teguiak@gmail.com">Henry Val�ry TEGUIAK</a> CRITT
 *         CRCFAO
 */
public class ImportYATEA {
	/** Common variables **/

	// VT:
	/** yatea file reference to yatea terms **/
	private File yateaTerms = null;
	/** yatea file reference to yatea terms **/
	private File yateaSentences = null;
	/** yatea directory **/
	private File yateaDirectory = null;
	// end VT:

	public static final int TERMS_FLUSH_THRESHOLD = 5000;

	/** The log level to be published on the console handler. */
	static final Level logLevel = Level.INFO;

	/** Logger system */
	private Logger log = null;

	public ImportYATEA() {
		// Log system
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(ImportYATEA.logLevel);
		log = Logger.getLogger(ImportYATEA.class.getName());
		log.setUseParentHandlers(false); // Do not use default handlers
		log.addHandler(consoleHandler);
		log.setLevel(ImportYATEA.logLevel);
	}

	/**
	 * TEST Function used only for test purposes. Initialize a connection to the
	 * database.
	 */
	static void initDafoeDataSource() {
		IDataSource con = new DataSource(
				"jdbc:postgresql://localhost:5432/DAFOE2", "postgres",
				"postgres");
		SessionFactoryImpl.openDynamicSession(con);
	}

	/**
	 * Function getting the current dynamic session to the database. The
	 * commented line is used for test purpose...
	 * 
	 * @return La session courrante de connection � la base de donn�es
	 */
	public static Session getDafoeSession() {
		// VT:
		// A plugin does not need to open a specific connection to the
		// DataSource
		// then a connection has already been opened when running the platform
		// just return the already opened connection to the DataSource
		// initDafoeDataSource(); // Uncomment to test...
		// VT: load a dynamic session per database

		return SessionFactoryImpl.getCurrentDynamicSession();
	}

	/* Cache managment for yatea id to hibernate id of terms */
	private static SortedMap<String, Integer> termsCache = null;

	public static void clearCache() {
		termsCache = new TreeMap<String, Integer>();
	}

	public static Integer getFromCache(String yateaId) {
		return termsCache.get(yateaId);
	}

	public static void putInCache(String yateaId, Integer hibernateId) {
		termsCache.put(yateaId, hibernateId);
	}

	/**
	 * Import into the database all the informations from Yatea's results. These
	 * results should be in two files : one containing the sentences, one XML
	 * containing the terms.
	 * 
	 * @param corpusName
	 * @param file_YTerms
	 * @param file_YSent
	 */
	public void importYateaResources(String corpusName, File file_YTerms,
			File file_YSent) {
		// System.out.println("Term File : " + file_YTerms.getName());
		// System.out.println("Sentence File : " + file_YSent.getName());

		log
				.info("Starting Yatea importation of corpus: \"" + corpusName
						+ "\"");

		// Take the time!
		long before = System.currentTimeMillis();

		Session db_session = getDafoeSession();

		// deleteTempTableIfExists(db_session);
		termsCache = new TreeMap<String, Integer>();

		// Create a terminology connecting all the TerminoObjects
		ITerminology termino = TerminologyImpl.getInstance();
		termino.setName(corpusName);
		termino.setNameSpace("Euhm");
		termino.setLanguage("English");

		// Create a corpus to link with the documents and texts
		ICorpus corpus = CorpusImpl.getInstance();
		corpus.setName(corpusName);

		{ // Save empty structure
			Transaction transaction = db_session.beginTransaction();
			db_session.save(termino);
			db_session.save(corpus);
			transaction.commit();
		}

		try {
			// Import sentences into DB
			SentenceCacheMap sentenceTable = YateaSentencesParser
					.importSentencesBD(corpus, db_session, file_YSent);

			long duration = (System.currentTimeMillis() - before) / 1000L;
			log.info("Yatea sentences finished in: " + (duration / 60)
					+ "min, " + (duration % 60) + "sec");

			// Set up the parser
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parserYatea = factory.newSAXParser();

			long beforeSax1 = System.currentTimeMillis();
			// First handler for the first processing (creating all the terms)
			DefaultHandler yatea_handler = new YateaHandler(db_session, termino, sentenceTable);
			// Parse and so import terms into DB
			parserYatea.parse(file_YTerms, yatea_handler);
			duration = (System.currentTimeMillis() - beforeSax1) / 1000L;
			log.info("Yatea SAX pass 1 finished in: " + (duration / 60)
					+ "min, " + (duration % 60) + "sec");

			long beforeSax2 = System.currentTimeMillis();
			// Second handler for the second processing (create syntactic
			// relations)
			DefaultHandler syntactic_handler = new SyntacticHandler(db_session,
					termino);
			// Parse again to create relations into DB
			parserYatea.parse(file_YTerms, syntactic_handler);

			duration = (System.currentTimeMillis() - beforeSax2) / 1000L;
			log.info("Yatea SAX pass 2 finished in: " + (duration / 60)
					+ "min, " + (duration % 60) + "sec");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Take the final time!
		long duration = (System.currentTimeMillis() - before) / 1000L;

		log.info("Yatea import finished in: " + (duration / 60) + "min, "
				+ (duration % 60) + "sec");
	}

	// ***************************** VT: **********************************/
	/*
	 * @Override public boolean importData() { importYateaResources("Corpus",
	 * this.yateaTerms, this.yateaSentences);
	 * 
	 * return true; }
	 * 
	 * // VT:
	 * 
	 * @Override public boolean initConnection(File filesDirectory) throws
	 * Exception { if (filesDirectory instanceof File) {
	 * initYateaFile(filesDirectory); } return true;
	 * 
	 * }
	 */

	// VT:
	// use a selected Directory to initialize all required files (Terms and
	// Sentences)
	public void initYateaFile(File currentDirectory) throws Exception {
		this.yateaDirectory = currentDirectory;

		// Find the files,
		this.yateaTerms = findYateaFileTerms(this.yateaDirectory);
		System.out.println("yateaTerms= " + this.yateaTerms.getAbsolutePath());

		this.yateaSentences = findYateaFileSentences(this.yateaDirectory);
		System.out.println("yateaSentences= "
				+ this.yateaSentences.getAbsolutePath());

	}

	// VT:
	// Identification between terms and sentence files:
	// a suffixed approach based on the file name
	private final static String YATEA_FILE_TERMS_SUFFIX = "_terms.xml";

	private final static String YATEA_FILE_SENTENCES_SUFFIX = "_sentences.txt";

	private static File findYateaFileTerms(File yateaDirectory) {
		List<File> filesInDirectory = Arrays.asList(yateaDirectory.listFiles());

		for (File f : filesInDirectory) {
			if (f.isFile() && f.getName().endsWith(YATEA_FILE_TERMS_SUFFIX)) {
				return f;
			}
		}
		return null;
	}

	private static File findYateaFileSentences(File yateaDirectory) {
		List<File> filesInDirectory = Arrays.asList(yateaDirectory.listFiles());

		for (File f : filesInDirectory) {
			if (f.isFile() && f.getName().endsWith(YATEA_FILE_SENTENCES_SUFFIX)) {
				return f;
			}
		}
		return null;
	}

	// ***************************** VT: end **********************************/

	private static void deleteTempTableIfExists(Session db_session) {
		Transaction tx = db_session.beginTransaction();
		db_session.createSQLQuery("DROP TABLE IF EXISTS temp_ref_term;")
				.executeUpdate();
		tx.commit();

		// System.out.println("Temp_Tab deleted....");
	}
}
