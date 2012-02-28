/**
 * 
 */
package org.dafoe.plugin.imp.yatea.yateaSAX.parse;

import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.plugin.imp.yatea.yateaSAX.item.YateaOccurrence;
import org.dafoe.plugin.imp.yatea.yateaSAX.item.YateaReliableAnchor;
import org.dafoe.plugin.imp.yatea.yateaSAX.item.YateaTermCandidate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class extending the default SAX handler. Throws methods and procedures when a mark is encountered
 * in the XML File.
 * The Yatea Handler save all the informations of a term, during the parse, into a temporary structure,
 * then save this term and its informations into the database.
 * Also create a temporary table to save a mapping between the Yatea_ID and the DAFOE_database_ID of a term.
 * 
 * @author Athier
 */
public class YateaHandler extends DefaultHandler
{
		// Term candidate structure, used as a buffer during the parse
		private YateaTermCandidate term = null;
		// Occurrence structure, idem
		private YateaOccurrence occurrence = null;
		// Anchor structure, idem
		private YateaReliableAnchor anchor = null;
		// Buffer used to collect information between marks
		private StringBuilder buffer;
		// Flag to notify in which part the parser is
		private boolean inReliableAnchor = false, inSyntacticA = false, inOccurrence = false;
		
		// Session to connect to the database
		Session db_session;
		// Terminology to connect TerminoObjects with
		ITerminology _termino;
		// Sentence equivalent table
		SentenceCacheMap sentenceTable;
		
		/**
		 * Constructor...
		 */
		public YateaHandler(Session session, ITerminology termino, SentenceCacheMap sentenceTable)
		{
			super();
			db_session = session;
			_termino = termino;
			this.sentenceTable = sentenceTable;
		}

		/**
		 * Called function when starting the parse of a document.
		 * Create a temporary table to map Yatea_ID with DAFOE_database_ID.
		 */
		@Override
		public void startDocument() throws SAXException
		{
			// Notify the beginning...
//			System.out.println("Debut de l'analyse du document");
			
			// Create a temporary table needed to store the relation Yatea_ID <-> Dafoe database ID
/*			
			Transaction tx = db_session.beginTransaction();
			db_session.createSQLQuery("CREATE TABLE temp_ref_term (id_hib INTEGER, id_yatea VARCHAR (50), PRIMARY KEY (id_yatea));")
			.executeUpdate();
			tx.commit();
*/
		}

		/**
		 * Called function when ending the parse of a document.
		 */
		@Override
		public void endDocument() throws SAXException
		{
			Transaction tx = db_session.beginTransaction();
			db_session.saveOrUpdate(_termino);
			tx.commit();
			// Notify the end...
//			System.out.println("Fin de l'analyse du document" );
		}

		/**
		 * Function called when the parser detect a new starting element.
		 * Initialize structures used as buffers to save the elements encountered relative to one
		 * candidate term. Theses structures are kept in memory only until a new candidate term is encountered
		 * (it doesn't load all the document in memory, but only one element at the same time).
		 */
		@Override
		public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws SAXException
		{
			// If it's a new term candidate, initialize the structure
			if (rawName.equals("TERM_CANDIDATE"))
			{
				term = new YateaTermCandidate(_termino);
			}
				
			
			// If it's a new occurrence, set the flag to notify that the parser
			// is in an occurrence, then create a new structure
			else if (rawName.equals("OCCURRENCE"))
			{
				inOccurrence = true;
				occurrence = new YateaOccurrence();
			}

			// If it's a new reliable anchor, set the flag and create a new structure
			else if (rawName.equals("RELIABLE_ANCHOR"))
			{
				inReliableAnchor = true;
				anchor = new YateaReliableAnchor();
			}
			
			// If the parser enter in the syntactic analysis part, flag it
			else if (rawName.equals("SYNTACTIC_ANALYSIS"))
				inSyntacticA = true;

			// If nothing of these marks, then there will be a content to save :
			// Initialize a new buffer.
			else
				buffer = new StringBuilder();
		}

		/**
		 * Function called when the parser detect an ending mark of an element.
		 * Save contents in temporary structures related to one candidate term.
		 * If it's the end of the candidate term, save it in the DB,
		 * then drop the structure (was just a buffer, doesn't keep it in memory).
		 */
		@Override
		public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException
		{
			// Ending mark of a term candidate
			if (rawName.equals("TERM_CANDIDATE"))
			{
				// Save it to the DB
				term.saveToDB(db_session, sentenceTable);
				// Print it
				// System.out.println(term.toString());
				// Drop the structure
				term = null;
			}
			
			// Ending mark of an occurrence of the current term
			else if (rawName.equals("OCCURRENCE"))
			{
				// Add the occurrence to the list of term's occurrences
				term.addOccuToList(occurrence);
				// Drop the structure
				occurrence = null;
				// Flag the end of occurrence's part
				inOccurrence = false;
			}

			// Ending mark of a reliable anchor of the current term
			else if (rawName.equals("RELIABLE_ANCHOR"))
			{
				// Add the anchor to the list of term's anchors
				term.addReliableAnchor(anchor);
				// Drop the structure
				anchor = null;
				// Flag the end of anchor's part
				inReliableAnchor = false;
			}
			
			// Ending mark of syntactic Analysis
			else if (rawName.equals("SYNTACTIC_ANALYSIS"))
				inSyntacticA = false;	// Flag the end of this part
			
			// If nothing of these, then it's a mark with content :
			else
			{
			// It exists multiple "ID" in a term candidate
				if (rawName.equals("ID"))
				{
					// If it's an occurrence's ID
					if (inOccurrence)
						occurrence.setId(buffer.toString());
					// If it's a reliable anchor's ID
					else if (inReliableAnchor)
						anchor.setId(buffer.toString());
					// If not, it's a term ID
					else
						term.setId(buffer.toString());
				}
			
			// FORM mark
				else if (rawName.equals("FORM"))
				{
					// Can be a reliable anchor's form
					if (inReliableAnchor)
						anchor.setForm(buffer.toString());
					// Or a term's form
					else
						term.setForm(buffer.toString());	
				}	
			
			// LEMMA mark
				else if (rawName.equals("LEMMA"))
					term.setLemma(buffer.toString());
			
			// NUMBER_OCCURRENCES mark	
				else if (rawName.equals("NUMBER_OCCURRENCES"))
					term.setNumber_occu(buffer.toString());

			// HEAD mark : exists multiple times
				else if (rawName.equals("HEAD"))
				{
					// If it's in the syntactic analysis
					if (inSyntacticA)
						term.setSa_head(buffer.toString().replaceAll("[\n\t ]*", ""));
					// Else, it's the head of the term candidate
					else
						term.setHead(buffer.toString());
				}
				
			// TERM_CONFIDENCE mark	
				else if (rawName.equals("TERM_CONFIDENCE"))
					term.setTerm_confidence(buffer.toString());

			// LOG_INFORMATION	mark
				else if (rawName.equals("LOG_INFORMATION"))
					term.setLog_information(buffer.toString());

			// SYNTACTIC_CATEGORY mark	
				else if (rawName.equals("SYNTACTIC_CATEGORY"))
					term.setSyntactic_category(buffer.toString());
				
			// MODIFIER mark	
				else if (rawName.equals("MODIFIER"))
					term.setSa_modifier(buffer.toString().replaceAll("[\n\t ]*", ""));

			// MNP mark
				else if (rawName.equals("MNP"))
					occurrence.setMnp(buffer.toString());

			// DOC mark
				else if (rawName.equals("DOC"))
					occurrence.setDoc(buffer.toString());

			// SENTENCE mark
				else if (rawName.equals("SENTENCE"))
					occurrence.setSentence(buffer.toString());

			// START_POSITION mark
				else if (rawName.equals("START_POSITION"))
					occurrence.setStart_position(buffer.toString());

			// END_POSITION mark
				else if (rawName.equals("END_POSITION"))
					occurrence.setEnd_position(buffer.toString());
				
			// ORIGIN mark
				else if (rawName.equals("ORIGIN"))
					anchor.setOrigin(buffer.toString());
				
				// Once the content is saved in the correct field, reset the buffer for the next field
				buffer = null;
			}
		}

		/**
		 * Function called when the parser encounter a text between marks.
		 */
		@Override
		public void characters(char[] ch, int start, int length) throws SAXException
		{
			// Get the content and save it in the buffer
			if(buffer != null)
				buffer.append(ch,start,length); 
		}
}
