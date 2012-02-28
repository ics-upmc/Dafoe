/**
 * 
 */
package org.dafoe.plugin.imp.yatea.yateaSAX.parse;

import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermSyntacticRelation;
import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermSyntacticRelationImpl;
import org.dafoe.plugin.imp.yatea.yateaSAX.item.ImportYATEA;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Class extending a Default SAX Handler. Throws procedures and methods when
 * specifics elements are encountered in a XML document.
 * 
 * The syntactic handler class grab only the syntactic relation in a Yatea XML file.
 * It loads theses relations into the database.
 * 
 * NOTE : It uses a temporary table into the database, and delete it at the end.
 * 
 * TODO : What about the terminology hack ?
 * 
 * @author Athier
 */
public class SyntacticHandler extends DefaultHandler
{
	private StringBuffer buffer;
	// Flag to notify in which part the parser is
	private boolean inSyntacticA = false;

	private String sa_head = null;
	private String sa_modifier = null;

	// Session to connect to the database
	Session db_session;
	
	// Terminology :
	ITerminology _termino;

	/**
	 * Constructor...
	 */
	public SyntacticHandler(Session session, ITerminology terminology)
	{
		super();
		_termino = terminology;
		db_session = session;
		
	}

	/**
	 * Called function when starting the parse of a document.
	 */
	public void startDocument() throws SAXException
	{
		// Notify the beginning...
//		System.out.println("Debut de l'analyse du document");
	}

	/**
	 * Called function when ending the parse of a document. Delete the temporary table.
	 */
	public void endDocument() throws SAXException
	{
/*		
    	Transaction tx = db_session.beginTransaction();
    	db_session.createSQLQuery("DROP TABLE temp_ref_term;")
    		.executeUpdate();
    	tx.commit();
*/
		
//		// Notify the end...
//		System.out.println("Fin de l'analyse du document" );
	}

	/**
	 * Function called when the parser detect a new starting element. Detects when a syntactic
	 * analysis starts.
	 */
	public void startElement(String nameSpaceURI, String localName, String rawName, Attributes attributs) throws SAXException
	{
		// If it's a new term candidate
		if (rawName.equals("TERM_CANDIDATE"))
		{ return; }

		// If it's a new occurrence
		else if (rawName.equals("OCCURRENCE"))
		{ return; }

		// If it's a new reliable anchor
		else if (rawName.equals("RELIABLE_ANCHOR"))
		{ return; }

		// If the parser enter in the syntactic analysis part, flag it
		else if (rawName.equals("SYNTACTIC_ANALYSIS"))
			inSyntacticA = true;

		// If nothing of these marks, then there will be a content to save :
		// Initialize a new buffer.
		else
			buffer = new StringBuffer();
	}

	/**
	 * Function called when the parser detect an ending mark of an element. Detects only the end
	 * of syntactic analysis. In this case, start a transaction with the database and, get back
	 * the mapping between Yatea_ID and DAFOE_database_ID, then create and save a new syntactic
	 * relation between these terms.
	 * 
	 * TODO : HACK -> The syntactic relation needs a terminology to be saved...
	 */
	public void endElement(String nameSpaceURI, String localName, String rawName) throws SAXException
	{
		if (rawName.equals("SYNTACTIC_ANALYSIS"))
		{
			inSyntacticA = false;	// Flag the end of this part

			// Start a transaction
			Transaction tx = db_session.beginTransaction();
			
			// Create the new syntactic relation
			ITermSyntacticRelation syntactic_rel = new TermSyntacticRelationImpl();
			
			// Get back the database_ID of the head element (we only have the yatea_ID)
/*			
			Integer id_head = (Integer) db_session.createSQLQuery("SELECT id_hib FROM temp_ref_term WHERE id_yatea=?;")
			.setString(0, sa_head)
			.uniqueResult();
*/			
			Integer id_head = ImportYATEA.getFromCache(sa_head);
			
			// Get back the database_ID of the modifier element (we only have the yatea_ID)
/*			
			Integer id_modifier = (Integer) db_session.createSQLQuery("SELECT id_hib FROM temp_ref_term WHERE id_yatea=?;")
			.setString(0, sa_modifier)
			.uniqueResult();
*/			
			Integer id_modifier = ImportYATEA.getFromCache(sa_modifier);
			
			// Load the  corresponding terms with their ID
			ITerm head_term = (ITerm) db_session.get(TermImpl.class, id_head);
			ITerm modifier_term = (ITerm) db_session.get(TermImpl.class, id_modifier);
			
//			System.out.println("Relation => Head : " + sa_head + " -- Modifier : " + sa_modifier);
//			System.out.println("Transcription => Head : "+ id_head +" -- Modifier : " + id_modifier);
				
			// Set the syntactic relation between terms
			syntactic_rel.setHeadTerm(head_term);
			syntactic_rel.setModifierTerm(modifier_term);
			
			// Save the relation
			db_session.save(syntactic_rel);
			
			// TOOD : HACK -> To be saved, the relation need to have a terminology saved too.
			// Or it makes a transient object error.
//			hack_terminology.addTerminoObject(head_term);
//			hack_terminology.addTerminoObject(modifier_term);
			_termino.addTerminoObject(syntactic_rel);
			db_session.save(_termino);
			
//			System.out.println("Syntactic relation "+ syntactic_rel.getId() +" : Head -> "+ head_term.getId() +" | Modifier -> "+modifier_term.getId());

			tx.commit();
		}

		// Else, it's a different mark :
		else
		{		
			// HEAD mark : exists multiple times
			if (rawName.equals("HEAD"))
			{
				// If it's in the syntactic analysis
				if (inSyntacticA)
					sa_head = buffer.toString().replaceAll("[\n\t ]*", "");
			}

			// MODIFIER mark	
			else if (rawName.equals("MODIFIER"))
			{
				sa_modifier = buffer.toString().replaceAll("[\n\t ]*", "");
			}

			// Once the content is saved in the correct field, reset the buffer for the next field
			buffer = null;
		}
	}

	/**
	 * Function called when the parser encounter a text between marks.
	 * Save the content into a buffer.
	 */
	public void characters(char[] ch, int start, int end) throws SAXException
	{
		//  Get the content
		String lecture = new String(ch,start,end);
		// Save it in the buffer
		if(buffer != null)
			buffer.append(lecture); 
	}
}
