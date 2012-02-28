/*******************************************************************************************************************************
 * (c) Copyright 2007, 2010 CRITT Informatique and INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, Télécom ParisTech, CNRS/UTC.
 * All rights reserved.
 * This program has been developed by the CRITT Informatique for the ANR DAFOE4App Project.
 * This program and the accompanying materials are made available under the terms
 * of the CeCILL-C Public License v1 which accompanies this distribution,
 * and is available at http://www.cecill.info/licences/Licence_CeCILL-C_V1-fr.html
 *
 * Contributors:
 *     INSERM, LISI/ENSMA, MONDECA, LIPN, IRIT, SUPELEC, Télécom ParisTech, CNRS/UTC and CRITT Informatique - specifications
 *     CRITT Informatique - initial API and implementation
 ********************************************************************************************************************************/
package org.dafoe.framework.samples.hibernate;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dafoe.framework.core.terminological.model.ICorpus;
import org.dafoe.framework.core.terminological.model.IDocument;
import org.dafoe.framework.core.terminological.model.IMethod;
import org.dafoe.framework.core.terminological.model.ISentence;
import org.dafoe.framework.core.terminological.model.ITerm;
import org.dafoe.framework.core.terminological.model.ITermOccurrence;
import org.dafoe.framework.core.terminological.model.ITermProperty;
import org.dafoe.framework.core.terminological.model.ITermRelation;
import org.dafoe.framework.core.terminological.model.ITermSaillance;
import org.dafoe.framework.core.terminological.model.ITerminology;
import org.dafoe.framework.core.terminological.model.ITypeRelationTermino;
import org.dafoe.framework.core.terminological.model.LINGUISTIC_STATUS;
import org.dafoe.framework.core.terminological.model.TERMINO_OBJECT_STATE;
import org.dafoe.framework.datasourcemanager.model.IDataSource;
import org.dafoe.framework.datasourcemanager.model.impl.DataSource;
import org.dafoe.framework.provider.hibernate.factory.model.impl.SessionFactoryImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.CorpusImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.MethodImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.SentenceImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermPropertyImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermRelationImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TermSaillanceImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TerminologyImpl;
import org.dafoe.framework.provider.hibernate.terminological.model.impl.TypeRelationTerminoImpl;
import org.dafoe.framework.provider.hibernate.util.Constants;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;


/**
 * The Class TerminologicalSamples provides examples of how to manage data in
 * the terminological layer.
 * 
 * @author <a href="mailto:teguiak@gmail.com">Henry Valéry TEGUIAK</a>
 */
public class TerminologicalSamples {

	/**
	 * Instantiates a new terminological samples.
	 */
	public TerminologicalSamples() {
		//IDataSource con= new DataSource("jdbc:postgresql://localhost:5432/DAFOE2","postgres","postgres");

	}
   
	/**
	 * Open connection to a database.
	 */
	public static void initDataSource(){
		IDataSource con= new DataSource("jdbc:postgresql://localhost:5432/DAFOE2","postgres","postgres");
		
        SessionFactoryImpl.openDynamicSession(con);
	}
	
	/**
	 * load the open connection.
	 */
	static Session getDafoeSession()  {
		initDataSource();		
		//VT: load a dynamic session per database
		return SessionFactoryImpl.getCurrentDynamicSession();
	}
	
	
	/**
	 * Load a term from its label
	 * return null if the term does not exist.
	 */
	
	@SuppressWarnings("unchecked")
	public static ITerm getTermFromLabel(String label){
		List<ITerm> terms= new ArrayList<ITerm>();
		ITerm term = null;
		
		try {
			terms = getDafoeSession().createSQLQuery("SELECT DISTINCT m21_term.* "+
					" FROM m21_term WHERE m21_term.label = '"+label+"' ORDER BY label").addEntity(TermImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Terms size= "+terms.size());
		if(terms.size()>0){
			term= terms.get(0);
			System.out.println(term.getId());
		}
		
		return term;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<ITerm> getTermsFromLabel(String label){
		List<ITerm> terms= new ArrayList<ITerm>();
		
		try {
			terms = getDafoeSession().createSQLQuery("SELECT m21_term.* "+
					" FROM m21_term WHERE m21_term.label = '"+label+"' ORDER BY label").addEntity(TermImpl.class).list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Terms size= "+terms.size());
		return terms;
	}
	 

	/**
	 * This example show how to delete an instance of ISentence in the database
	 * knowing its id .
	 * 
	 * @param sentenceId
	 *            the sentence id
	 * @throws DafoeException 
	 */
	public void deleteSentenceSample(int sentenceId) throws HibernateException {

		Session hSession = getDafoeSession();
		// debut de la transaction
		Transaction tx = hSession.beginTransaction();
        Object o= hSession.load(SentenceImpl.class, sentenceId);
		
		hSession.delete(o);

		tx.commit();

		hSession.close();

		System.out.println("*************************** end ");
	}

	@SuppressWarnings("unchecked")
	public static void deleteObjectSample(Class cls, int objectId) throws HibernateException {

		Session hSession = getDafoeSession();
		// debut de la transaction
		Transaction tx = hSession.beginTransaction();
		
        Object o= hSession.load(cls, objectId);
		
		hSession.delete(o);

		tx.commit();

		hSession.close();

		System.out.println("*************************** end ");
	}

	// 
	/**
	 * This sample show how to delete a relation using its id.
	 * 
	 * @param relId
	 *            the relation id
	 */
	public static void deleteRelationSample(int relId) throws HibernateException{


		Session hSession = getDafoeSession();
		// debut de la transaction
		Transaction tx = hSession.beginTransaction();

		// recuperation de l'objet relation
		ITermRelation rel = (ITermRelation) hSession.load(
				TermRelationImpl.class, relId);

		// la relation dépend (association 1:N) de deux termes: term1 et term2
		ITerm term1 = rel.getTerm1();

		ITerm term2 = rel.getTerm2();

		// il faut explicitement ordonner au termes de supprimer la liasion avec
		// la relation

		term1.getRelationsWhereInTerm1().remove(rel);

		term2.getRelationsWhereInTerm2().remove(rel);

		// une fois les deux termes ont supprimer la liaison, ont peut supprimer
		// la relation
		// dSession.delete(TermRelationImpl.class,relId);

		tx.commit();

		hSession.close();

		System.out.println("*************************** end ");
	}

	/**
	 * This example show how to get an instance of ISentence knowing its Id .
	 * 
	 * @param sentenceId
	 *            the sentence id
	 * 
	 * @return the sentence
	 */
	public ISentence getSentenceSample(int sentenceId) throws HibernateException{

		Session hSession = getDafoeSession();
		
		return (ISentence) hSession.load(SentenceImpl.class, sentenceId);
	}

	/**
	 * This example show how to get all instance of ISentence .
	 * 
	 * @return the all sentence
	 */
	@SuppressWarnings("unchecked")
	public List<ISentence> getAllSentenceSample() throws HibernateException{

		Session hSession = getDafoeSession();

		List<ISentence> sentences = hSession.createCriteria(SentenceImpl.class).list();
				

		for (ISentence s : sentences) {
			IDocument doc = s.getDocument();
			System.out.println("sentence id= " + s.getId() + " || content= "
					+ s.getContent() + " || doc label= " + doc.getId()
					+ " || corpus label= " + doc.getRelatedCorpus().getName());
		}

		return sentences;
	}

	/**
	 * Creates the term sample.
	 */
	 
	/**
	 * Gets the all term sample.
	 * 
	 * @return the all term sample
	 */
	@SuppressWarnings("unchecked")
	public static List<ITerm> getAllTermSample() throws HibernateException{

		System.out.println("ALL TERMS :");
		
		//with session per transaction pattern
		//ISessionDafoe dSession = SessionFactoryImpl.getSessionDafoe();
        
		//without session per transaction pattern
		Session hSession = getDafoeSession();
		
		System.out.println("AFTER CREATE SESSION");
	
		
		List<ITerm> terms = new ArrayList<ITerm>();// tSession.findAllTerm();

		// debut de la transaction
	    Transaction tx = hSession.beginTransaction();

		terms =  hSession.createCriteria(TermImpl.class).list();
		// terms=dSession.findAllInstance1("TermImpl");

		 tx.commit();

		 
		 for (ITerm t : terms) {

			// System.out.println("term id= " + t.getId() +
			// " || Label= "+t.getLabel()+"|| occur size= "+t.getTermOccurrences().size()+"|| var size= "+t.getVariantes().size());

			// Set<ITerm> vars=t.getVariantes();

			System.out.println("term id= " + t.getId() + " || Label= "
					+ t.getLabel() + "|| var size= " + t.getVariantes().size());

		   //getAllTermOccurrence(t);
            
		   getAllVariantes(t);
		   
			// getAllTermProperty(t);

			// getAllTermSaillance(t);
		}

		System.out.println("=============> END: " + terms.size() + " terms loaded.");

		System.out.println("First term = " + terms.get(0).getLabel());
		System.out.println("Last term = " + terms.get(terms.size()-1).getLabel());

		return terms;
	}

	@SuppressWarnings("unchecked")
	public static void findAllInstance(Class cls) throws HibernateException{

		Session hSession = getDafoeSession();

		List<Object> objects = new ArrayList<Object>();// tSession.findAllTerm();
		objects = hSession.createCriteria(cls).list();

		System.out.println("SIZE= " + objects.size());
	}

	/**
	 * Gets the all term occurrence.
	 * 
	 * @param term
	 *            the term
	 * 
	 * @return the all term occurrence
	 */
	public static void getAllTermOccurrence(ITerm term) throws HibernateException{

		System.out.println("     TERMOCCURRENCES de : " + term.getLabel());
		Set<ITermOccurrence> occurs = term.getTermOccurrences();
		Iterator<ITermOccurrence> iter = occurs.iterator();

		while (iter.hasNext()) {
			System.out.println("position occur =" + iter.next().getPosition());
		}
	}

	public static void getAllVariantes(ITerm term){
		System.out.println("     VARIANTES de : " + term.getLabel());
		Set<ITerm> vars = term.getVariantes();
		Iterator<ITerm> iter = vars.iterator();

		while (iter.hasNext()) {
			System.out.println("var label =" + iter.next().getLabel());
		}
	}
	/**
	 * Gets the all term property.
	 * 
	 * @param term
	 *            the term
	 * 
	 * @return the all term property
	 */
	public void getAllTermProperty(ITerm term) throws HibernateException{

		System.out.println("     TERMPROPERTIES de : " + term.getLabel());
		Set<ITermProperty> props = term.getProperties();
		Iterator<ITermProperty> iter = props.iterator();

		while (iter.hasNext()) {
			ITermProperty prop = iter.next();
			System.out.println("term property label  =" + prop.getLabel()
					+ " type= " + prop.getType());
		}
	}

	public static void getAllTermSaillance(ITerm term) throws HibernateException{

		System.out.println("     SAILLANCE de : " + term.getLabel());
		ITermSaillance sail = term.getSaillanceCriteria();

		if (sail != null) {
			System.out.println("Frequency =" + sail.getFrequency());
			System.out.println("ModifierProductivity ="
					+ sail.getModifierProductivity());
			System.out.println("PositionWeight =" + sail.getPositionWeight());
			System.out.println("NbVar =" + sail.getNbVar());
			System.out.println("HeadProductivity ="
					+ sail.getHeadProductivity());
			System.out.println("TfIdf =" + sail.getTfIdf());
			System.out.println("TypographicalWeight ="
					+ sail.getTypographicalWeight());
		}
	}

	/**
	 * Gets the all corpus sample.
	 * 
	 * @return the all corpus sample
	 */
	@SuppressWarnings("unchecked")
	public List<ICorpus> getAllCorpusSample() throws HibernateException{
		Session hSession = getDafoeSession();

		List<ICorpus> corpus = hSession.createCriteria(CorpusImpl.class).list();

		for (ICorpus c : corpus) {

			System.out.println("corpus id= " + c.getId() + " label= "
					+ c.getName());
		}

		return corpus;
	}

	/**
	 * Testget star term.
	 */
	@SuppressWarnings("unchecked")
	public void testgetStarTerm() throws HibernateException{

		Session hSession = getDafoeSession();

		List<ITerm> terms = hSession.createCriteria(TermImpl.class).list();

		for (ITerm t : terms) {

			System.out.println("StarTerm de: " + t.getLabel() + "= "
					+ t.getStarTerm().getLabel());

		}
	}

	/**
	 * Testget various.
	 */
	@SuppressWarnings("unchecked")
	public void testgetVarious() throws HibernateException{
		Session hSession = getDafoeSession();

		List<ITerm> terms = hSession.createCriteria(TermImpl.class).list();

		for (ITerm t : terms) {

			System.out.println("VARIOUS de: " + t.getLabel());
			// Set<ITerm> vars= t.getVarious();
			Set<ITerm> vars = t.getVariantes();

			Iterator<ITerm> iter = vars.iterator();

			while (iter.hasNext()) {
				System.out.println("    VARIOUS = " + iter.next().getLabel());
			}

		}

	}

	// a complete transaction to save fast all terminoobject
	/**
	 * This example show how to create and save terminology object in a
	 * transaction.
	 */
	public static void testTerminologyTransaction() throws HibernateException{

		System.out.println("*************************** begin");

		Session hSession = getDafoeSession();

		// debut de la transaction
		Transaction tx = hSession.beginTransaction();

		// Session s= HibernateUtil.getCurrentSession();
		// Transaction tx = s.beginTransaction();

		// creation de la terminologie

		ITerminology myTerminology = TerminologyImpl.getInstance();
		myTerminology.setName("Terminology12_tegs");
		myTerminology.setNameSpace("Terminology.tegs");
		myTerminology.setLanguage("fr");

		// creation d'une propriété terminologique

		ITermProperty prop = new TermPropertyImpl();
		prop.setLabel("doc12_tegs");
		prop.setType("String");

		// creation des termes

		ITerm term1 = new TermImpl();
		term1.setLabel("term12_tegs");
		//VT: use type-safe approcah 
		//term1.setNamedEntity(ILinguisticStatus.TERM); // n'est pas une entité nommée
		term1.setLinguisticStatus(LINGUISTIC_STATUS.TERM);
		term1.setState(TERMINO_OBJECT_STATE.STUDIED);
		//term1.addTermProperty(prop);
		
		ITermSaillance saill = new TermSaillanceImpl();

		saill.setFrequency(20);
		saill.setHeadProductivity(22);
		saill.setModifierProductivity(23);
		saill.setNbVar(24);
		saill.setPositionWeight(2);
		saill.setTfIdf(17);
		saill.setTypographicalWeight(4);

		//term1.addSaillance(saill);

		term1.setSaillanceCriteria(saill);

		//
		myTerminology.addTerminoObject(term1);

		/*
		 ITerm term2 = new TermImpl();
		 
		term2.setLabel("term1212_tegs");
		term2.setNamedEntity(ILinguisticStatus.NAMED_ENTITY); // c'est une entité nommée
		term2.setStatus(ITerminoObjectStatus.CONCEPTUALIZED);
		term2.addTermProperty(prop);

		//
		myTerminology.addTerminoObject(term2);

		// creation d'occurrence

		ITermOccurrence tocc = new TermOccurrenceImpl();
		tocc.setLength(10);
		tocc.setPosition(100);

		// creation d'une sentence

		ISentence sent = new SentenceImpl();
		sent.setContent("sentence12_tegs");
		sent.addTermOccurrence(tocc, term1);

		// creation de document

		IDocument doc = new DocumentImpl();
		doc.setName("doc12_tegs");
		doc.addSentence(sent);

		// creation de corpus

		ICorpus corpus = new CorpusImpl();
		corpus.setName("corpus12_tegs");
		corpus.addDocument(doc);
*/
		// dSession.save(term1);

		// sauvegarde temporaire
		// d'abord la terminologie, ensuite le corpus

		hSession.save(myTerminology);

		//hSession.save(corpus);

		// sauvegarde définitive

		tx.commit();

		hSession.close();

		System.out.println("*************************** end ");
	}

	/**
	 * Test save term property transaction.
	 */
	public static void testSaveTermPropertyTransaction() throws HibernateException{

		System.out.println("*************************** begin");

		Session hSession = getDafoeSession();
		Transaction tx = hSession.beginTransaction();

		// Session s= HibernateUtil.getCurrentSession();
		// Transaction tx = s.beginTransaction();

		ITermProperty prop = new TermPropertyImpl();
		prop.setLabel("doc1_tegs");
		prop.setType("String");

		ITerm term = new TermImpl();
		term.setLabel("term1_tegs");
		term.addTermProperty(prop);

		hSession.save(term);

		tx.commit();

		hSession.close();

		System.out.println("*************************** end ");
	}



	public static void testSaveTermSaillance() throws HibernateException{
		System.out.println("*************************** begin");

		Session hSession = getDafoeSession();

		// ITerm term1= (ITerm) dSession.load(TermImpl.class,5);
		Transaction tx = hSession.beginTransaction();

		// creation des termes

		ITerm term1 = new TermImpl();
		term1.setLabel("term_saill_15_tegs");
		// term1.setNamedEntity(noBool); // n'est pas une entité nommée
		 term1.setState(TERMINO_OBJECT_STATE.STUDIED);
		// term1.addTermProperty(prop);

		ITermSaillance saill = new TermSaillanceImpl();

		saill.setFrequency(20);
		saill.setHeadProductivity(22);
		saill.setModifierProductivity(23);
		saill.setNbVar(24);
		saill.setPositionWeight(2);
		saill.setTfIdf(17);
		saill.setTypographicalWeight(4);

		// term1.addSaillance(saill);

		term1.setSaillanceCriteria(saill);

		hSession.save(term1);

		// saill.setTerm(term1);

		// dSession.save(saill);

		tx.commit();

		hSession.close();

		System.out.println("*************************** end ");
	}

	/**
	 * This sample show how to remove variante relationship between two terms.
	 * 
	 * @param varId
	 *            the variante id
	 */

	public static void testRemoveVarianteSample(int varId) throws HibernateException{

		System.out.println("REMOVE VARIANTE: ");
		Session hSession = getDafoeSession();
		// debut de la transaction

		// recuperation de la variante en question
		ITerm termVar = (ITerm) hSession.load(TermImpl.class, varId);

		ITerm starTerm = termVar.getStarTerm();

		//int id = starTerm.getId();

		System.out.println("before remove variante, var size= "
				+ starTerm.getVariantes().size());

		// ITransactionDafoe dTx = dSession.beginTransaction();
		// ici je dis tout simplement que sont attribut starTerm pointe vers
		// null:
		// ceci coupera l'association sans supprimer la variante de la bd
		//termVar.setStarTerm(null);
		starTerm.getVariantes().remove(termVar);

		// dSession.refresh(starTerm);
		hSession.flush();
		// System.out.println("after remove variante, var size= "+starTerm.getVariantes().size());

		// persistance de l'opération dans la bd
		// dTx.commit();
		// dSession.clear();
		// starTerm.getVariantes().remove(termVar);
		System.out.println("after remove variante, var size= "
				+ starTerm.getVariantes().size());
		// dSession.close();

		System.out.println("*************************** end ");

	}

	public static void deleteSaillanceCriteria(ITerm term) throws HibernateException{

		Session hSession = getDafoeSession();

		ITermSaillance termSaillance = term.getSaillanceCriteria();

		Transaction tx;

		try {

			if (termSaillance != null) {

				tx = hSession.beginTransaction();

				int termSaillanceId = termSaillance.getId();

				termSaillance.setTerm(null);
				

				Object o= hSession.load(TermSaillanceImpl.class, termSaillanceId);
				hSession.delete(o);
				
				tx.commit();

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		System.out.println("*************************** end ");
	}



	public static void testSaveWithoutCacheTransaction() throws HibernateException{
		Session hSession = getDafoeSession();

		// debut de la transaction
		Transaction tx = hSession.beginTransaction();

		System.out.println("*************************** begin ");

		for (int i = 0; i < 100000; i++) {
			/*
			 * ITerminoObjectStatus status=new TerminoObjectStatusImpl();
			 * status.setCode(i); status.setLabel("STATUS"+i);
			 * 
			 * dSession.save(status);
			 */

			ITerm term = new TermImpl();
			term.setLabel("term_" + i);
			hSession.save(term);
		}
		ITerm term_last = new TermImpl();
		term_last.setLabel("term_last");
		hSession.save(term_last);

		tx.commit();

		System.out.println("*************************** end ");
	}

	public static void testSaveWithCacheTransaction() throws HibernateException{
		int batch_size = Constants.HIBERNATE_BATCH_SIZE;
		Session hSession = getDafoeSession();

		// debut de la transaction
		Transaction tx = hSession.beginTransaction();

		System.out.println("*************************** begin ");
		for (int i = 0; i < 1000000; i++) {
			/*
			 * ITerminoObjectStatus status=new TerminoObjectStatusImpl();
			 * status.setCode(i); status.setLabel("STATUS"+i);
			 * 
			 * dSession.save(status);
			 */
			ITerm term = new TermImpl();
			term.setLabel("myterm_" + i);
			hSession.save(term);

			if (i % batch_size == 0) {
				hSession.flush();
				hSession.clear();
			}
		}

		ITerm term_last = new TermImpl();
		term_last.setLabel("myterm_last");
		hSession.save(term_last);
		
		tx.commit();

		System.out.println("*************************** end ");
	}

	@SuppressWarnings("deprecation")
	public static void testEric()throws HibernateException{
		System.out.println("=============> START");

		Connection con =  getDafoeSession().connection();

		Statement stmt;

		ResultSet res;

		ITerm term;

		List<ITerm> terms = new ArrayList<ITerm>();

		try {

		stmt = con.createStatement();

		stmt.execute("SELECT * from m21_term");

		res = stmt.getResultSet();

		while (res.next()){

		term = new TermImpl();

		term.setLabel(res.getString("label"));

		terms.add(term);

		}

		} catch (SQLException e) {

		e.printStackTrace();

		}

		System.out.println("=============> END: " + terms.size() + " terms loaded.");

		System.out.println("First term = " + terms.get(0).getLabel());
		System.out.println("Last term = " + terms.get(terms.size()-1).getLabel());

		}
	
	
	@SuppressWarnings("unchecked")
	public static void testMethod(){
		
		IMethod m = null;

		ITypeRelationTermino rt = null;

		Session session = getDafoeSession();

		System.out.println("DafoeSession = " + session.toString());

		System.out.println("=============> START");

		Transaction dTx = session.beginTransaction();

		SQLQuery query = session.createSQLQuery("SELECT * FROM m21_method WHERE id = 1").addEntity(MethodImpl.class);

		if (query != null){

		List<IMethod> queryResult = query.list();

		if (queryResult.size() != 0){

		m = queryResult.get(0);

		}

		}

		System.out.println("Method = " + m.getTool());

		query = session.createSQLQuery("SELECT * FROM m21_type_relation WHERE id = 2").addEntity(TypeRelationTerminoImpl.class);

		if (query != null){

		List<ITypeRelationTermino> queryResult = query.list();

		if (queryResult.size() != 0){

		rt = queryResult.get(0);

		}

		}

		System.out.println("RT Type = " + rt.getLabel());

		rt.addMethod(m);

		//m.addTypeRelationTermino(rt);

		dTx.commit();

		session.close(); 
		System.out.println("=============> END");
	}
	
	
// VT: delete corpus
	
	public static void deleteDocument(IDocument doc){
		
		Transaction tx= getDafoeSession().beginTransaction();
		
		List<ITerm> terms= getTermFromDocument(doc);
		
		System.out.println("size= "+terms.size());
		
		getDafoeSession().delete(doc);
		
		deleteTermList(terms);
		
		tx.commit();
	}

	
	public static void deleteTermList(List<ITerm> terms){
			
		for(ITerm term: terms){
			
			getDafoeSession().delete(term);
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public static List<Integer> getTermIdsFromDoc(IDocument doc){
		List<Integer> termIds= new ArrayList<Integer>();
		String query=" SELECT DISTINCT m11_termoccurrence.term_id FROM  m11_sentence,m11_termoccurrence WHERE m11_sentence.doc_id="+doc.getId()+ " AND m11_sentence.id=m11_termoccurrence.sentence_id";
		
		termIds= getDafoeSession().createSQLQuery(query).list();

		return termIds;
	}
	
	
	@SuppressWarnings("unchecked")
	public static List<ITerm> getTermFromDocument(IDocument doc){
		
		List<ITerm> terms = new ArrayList<ITerm>();

		try {
			terms = getDafoeSession().createSQLQuery("SELECT DISTINCT m21_term.* "+
					"FROM m21_term inner join m11_termoccurrence on m21_term.id= m11_termoccurrence.term_id "+
					" inner join m11_sentence on m11_termoccurrence.sentence_id =m11_sentence.id"+
					" WHERE m11_sentence.doc_id = "+doc.getId().toString()+" ORDER BY label")
					.addEntity(TermImpl.class).list();  


		} catch (Exception e) {
			e.printStackTrace();
		}

		return terms;
	}
	
	//fin
	
	@SuppressWarnings("unchecked")
	public static void testDeleteDocument(){
		
		List<IDocument>	docs = (List<IDocument>)getDafoeSession().createCriteria(IDocument.class).list();
        IDocument currentDoc=docs.get(0);
		System.out.println("------ doc label ="+currentDoc.getName());
		
		deleteDocument(currentDoc);
	}
	
	/**
	 * The main method is used to test samples method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) throws HibernateException{

		// new TerminologicalSamples().deleteSentenceSample(2);//ok
		// new TerminologicalSamples().createSentenceSample(2);//ok

		//new TerminologicalSamples().getAllSentenceSample();//ok

		// new TerminologicalSamples().createSentenceSample(3);//ok

		// new TerminologicalSamples().createTermSample();//ok

		// getAllTermSample();//ok

		new TerminologicalSamples().getAllCorpusSample();//ok

		// new TerminologicalSamples().testgetStarTerm();//ok

		// new TerminologicalSamples().testgetVarious();//ok

		// testTerminologyTransaction();//ok

		// testSaveTermPropertyTransaction();//ok

		// testSaveExtendedBoolean();//ok

		// testSaveTerminoObjectStatus();//ok

		// deleteTerminoObjectStatusSample(5); //ok

		// testSaveTermSaillance(); //ok

		// deleteObjectSample(TermRelationImpl.class,5); // non ok
		// TermImpl t= new TermImpl();

		// deleteRelationSample(3); //ok

		// testRemoveVarianteSample(32); ok

		 //getAllTermSample();//ok

		// ISessionDafoe dafoeSession = SessionFactoryImpl.getSessionDafoe();

		// ITerm term= (ITerm) dafoeSession.load(TermImpl.class, 43);

		// deleteSaillanceCriteria(term);//ok

		// findAllInstance(TermRelationImpl.class);//ok

		// System.out.println("LOCAL NAME ="+getLocaleName("org.dafoe.framework.core.samples.TerminologicalSamples"));

		
		// testSaveWithoutCacheTransaction();//Exception in thread "main"
		// java.lang.reflect.UndeclaredThrowableException
		
		//testSaveWithCacheTransaction(); //ok
		
		//testEric();
		 
		// testMethod(); //ok
		
		//testDeleteDocument();//
		
	 //getTermsFromLabel("aspect"); //OK
		//getTermFromLabel("aspect"); //OK
	}
}
