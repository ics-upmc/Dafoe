/**
 * 
 */
package org.dafoe.plugin.imp.yatea.yateaSAX.parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.dafoe.framework.core.terminological.model.ICorpus;
import org.dafoe.framework.core.terminological.model.IDocument;
import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.DocumentImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.SentenceImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * 
 * @author Athier
 */
public class YateaSentencesParser
{
	/** Charset iso-latin-1 */
	public static Charset ISO_LATIN_1 = Charset.forName("ISO-8859-1");
	/** Charset utf-8 */
	public static Charset UTF8 = Charset.forName("UTF-8");
	
	/**
	 * Read a file containing the sentences (one per line) of the corpus with the formatting -> Id:sentence
	 * Save these sentences into the database.
	 * 
	 * @param corpus		Object ICoprus to link the sentences and docs with
	 * @param db_session	Session to connect to the DB
	 * @param yatea_sent	File containing all the sentences of all the docs (Yatea's result)
	 * @return Equivalence table between Yatea Sentence id and Hibernate Id
	 */
	public static SentenceCacheMap importSentencesBD(ICorpus corpus, Session db_session, File yatea_sent)
	{
		// Variables :
		String line = "";
		String[] sent_and_id = null;
		IDocument doc = null;
		Transaction transaction = null;
		SentenceCacheMap cacheMap = new SentenceCacheMap();

		try
		{
			// Original file :
//			System.out.println("Opening file : " + yatea_sent.getName());
			BufferedReader corpReader = new BufferedReader(
					new InputStreamReader(new FileInputStream(yatea_sent), UTF8));

			// Start to read the doc line by line
//			System.out.println("Importing all the sentences...");
			
			line = corpReader.readLine();
			
			if (line.matches("Document [0-9]* - .*"))
			{
				String[] nomDoc = line.split(" - ");
				doc = new DocumentImpl();
				
				doc.setName(nomDoc[1]);
				corpus.addDocument(doc);
				
				transaction = db_session.beginTransaction();
				db_session.save(doc);
				transaction.commit();
				
				// Next line
				line = corpReader.readLine();
			}
			else
			{
				doc = new DocumentImpl();
				
				doc.setName("Document 1");
				corpus.addDocument(doc);
				
				transaction = db_session.beginTransaction();
				db_session.save(doc);
				transaction.commit();
			}
			
			while (line != null)
			{
				if (line.matches("Document [0-9]* - .*"))
				{
					String[] nomDoc = line.split(" - ");
					doc = new DocumentImpl();
					
					doc.setName(nomDoc[1]);
					corpus.addDocument(doc);
					
					transaction = db_session.beginTransaction();
					db_session.save(doc);
					transaction.commit();
				}
				else
				{
					// Split the sentence -> ID : sentence
					sent_and_id = line.split(":", 2);
					
					// Create a new object ISentence
					ISentence sent = new SentenceImpl();
					// Set informations
					final int parsedInt = Integer.parseInt(sent_and_id[0]);
					sent.setOrderNumber(parsedInt);
					sent.setContent(sent_and_id[1]);
					
					doc.addSentence(sent);
					
					// Start a transaction and save the sentence
					transaction = db_session.beginTransaction();
					db_session.save(sent);
					cacheMap.put(doc.getId()-1, parsedInt, sent.getId());
					db_session.saveOrUpdate(doc);
					transaction.commit();
				}

				// Next line
				line = corpReader.readLine();
			}

			corpReader.close();
//			System.out.println("... Done.");
			return cacheMap;
		}
		catch (IOException e)
		{
			System.out.println("An error as occured while importing the sentences");
			e.printStackTrace();
			return new SentenceCacheMap();
		}
	}
}
