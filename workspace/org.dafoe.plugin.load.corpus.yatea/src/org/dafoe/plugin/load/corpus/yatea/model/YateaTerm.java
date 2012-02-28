package org.dafoe.plugin.load.corpus.yatea.model;

/**
 * 
 * @author sylvie Szulman
 * LIPN
 * Projet Dafoe
 * Aout 2009
 * class for a Yatea term 
 */
import org.jdom.Element;

import java.util.*;
import java.io.*;



@SuppressWarnings("serial")
public class YateaTerm implements Serializable{

/*<!ELEMENT  TERM_CANDIDATE
                        (ID,FORM, LEMMA?, MORPHOSYNTACTIC_FEATURES?,
                         HEAD, NUMBER_OCCURRENCES, LIST_OCCURRENCES,
                         TERM_CONFIDENCE, LIST_RELIABLE_ANCHORS?,
                         LOG_INFORMATION,
                         (SYNTACTIC_ANALYSIS|
                         SYNTACTIC_ANALYSIS_COORD)?) > */
	private String ID = null; //  Identifier      
	private String FORM = null; //Inflectional form of a term or a word 
	private String LEMMA = null; //Canonical form of term  or a word

	/*
	 * <!ELEMENT  MORPHOSYNTACTIC_FEATURES 
                        (SYNTACTIC_CATEGORY, TYPE?, GENDER?, 
                         NUMBER?, CASE?, MOOD_VFORM?, TENSE?,
                         PERSON?, DEGREE?, POSSESSOR?, 
                         FORMATION?)                                 > 
	 */
	/*<!--                    syntactic_category                         --> 
	<!--                    Multext POS categories                     -->
	<!--                    Noun (N), Verb (V), Adjective (A), Pronoun
	                        (P), Determiner (D), Article (T), Adverb
	                        (R), Adposition (S) Conjunction (C),
	                        Numerals (M), Interjection (I), Unique (U)
	                        Resiual (X), Abbreviation (Y)              -->*/
	private String SYNTACTIC_CATEGORY = null; //cat�gorie syntaxique
	/*
	 * <!--                    type                                       -->
<!--                    applicable for nouns, verbs, adjectives,
                        pronouns, determiners, adverbs,
                        adpositions, conjunctions, numerals        -->
	 */
	private String TYPE = null; 
	/*
	 * <!--                    gender                                     -->
<!--                    applicable for nouns, verbs, adjectives,
                        pronouns, determiners, numerals            -->
	 */
	private String GENDER = null;
	/*
	 * !--                    number                                     -->
<!--                    applicable for nouns, verbs, adjectives,
                        pronouns, determiners, numerals            -->
	 */
	private String NUMBER = null;
	/*
	 * <!--                    case                                       -->
<!--                    applicable for nouns, adjectives,
                        pronouns, determiners, numerals            -->
	 */
	private String CASE = null;
	/*
	 * <!--                    mood_vform                                 -->
<!--                    applicable for verbs                       -->
	 */
	private String MOOD_VFORM = null;
	/*
	 * <!--                    tense                                      -->
<!--                    applicable for verbs                       -->
	 */
	private String TENSE = null;
	/*
	 * <!--                    person                                     -->
<!--                    applicable for verbs, pronouns, determiners-->
	 */
	private String PERSON = null; 
	/*
	 * <!--                    degree                                     -->
<!--                    applicable for adjectives, adverbs         -->
	 */
	private String DEGREE = null;
	/*
	 * <!--                    possessor                                  -->
<!--                    applicable for pronouns, determiners       -->
	 */
	private String POSSESSOR = null; 
	/*
	 * <!--                    formation                                  -->
<!--                    applicable for adpositions                 -->
	 */
	private String FORMATION = null;
	
	private String HEAD = null; //Head of the term identifier
	private String NUMBER_OCCURRENCES = null; //Number of Occurrences
	//LIST_OCCURRENCES
	/*
	 * <!--                    List of the occurrences of the terms       -->
<!ELEMENT  LIST_OCCURRENCES
                        (OCCURRENCE+)                                  >
<!--                    Occurrence information                     -->
*/
	private ArrayList<YateaOccurrence> LIST_OCCURRENCES = new ArrayList<YateaOccurrence>();
	private String TERM_CONFIDENCE = null; //Confidence of the term
	private ArrayList<String> LIST_RELIABLE_ANCHORS = new ArrayList<String>(); //
	private String LOG_INFORMATION = null;
	private ArrayList<String> SYNTACTIC_ANALYSIS = null;
	private ArrayList<String> SYNTACTIC_ANALYSIS_COORD = null;
	private ArrayList<String> ensTextOccu =null;
	
	public YateaTerm() {
		super();
	}

	public YateaTerm(String ID, String lemme, String nbr, ArrayList<YateaOccurrence> ensOccu, String form){
		super();
		this.ID = ID;
		this.LEMMA = lemme;
		this.NUMBER_OCCURRENCES= nbr;
		this.LIST_OCCURRENCES = new ArrayList<YateaOccurrence>(ensOccu);
		this.ensTextOccu = new ArrayList<String>();	
		this.FORM = form;
		}
	
	public void litXml(Element elem){
		this.ID = elem.getChildText("ID");
		if(elem.getChild("FORM") !=null){
			this.FORM = elem.getChildText("FORM");
		}
      
		if(elem.getChild("LEMMA") != null){
			this.LEMMA = elem.getChildText("LEMMA");
		}
		Element elemAux = elem.getChild("MORPHOSYNTACTIC_FEATURES");
		if(elemAux !=null){
			if(elemAux.getChild("SYNTACTIC_CATEGORY")!=null){
				this.SYNTACTIC_CATEGORY = elemAux.getChildText("SYNTACTIC_CATEGORY");
			}
		}
		
		if(elem.getChild("TYPE")!=null){
				this.TYPE = elem.getChildText("TYPE");
		}
		if(elem.getChild("GENDER")!=null){
			this.GENDER = elem.getChildText("GENDER");
		}
		if(elem.getChild("NUMBER")!=null){
			this.NUMBER = elem.getChildText("NUMBER");
		}
		if(elem.getChild("CASE")!=null){
			this.CASE = elem.getChildText("CASE");
		}
		if(elem.getChild("MOOD_VFORM")!=null){
			this.MOOD_VFORM = elem.getChildText("MOOD_VFORM");
		}
		if(elem.getChild("TENSE")!=null){
			this.TENSE = elem.getChildText("TENSE");
		}
		if(elem.getChild("PERSON")!=null){
			this.PERSON = elem.getChildText("PERSON");
		}
		if(elem.getChild("DEGREE")!=null){
			this.DEGREE = elem.getChildText("DEGREE");
		}
		if(elem.getChild("POSSESSOR")!=null){
			this.POSSESSOR = elem.getChildText("POSSESSOR");
		}
		if(elem.getChild("FORMATION")!=null){
			this.FORMATION = elem.getChildText("FORMATION");
		}
		this.HEAD = elem.getChildText("HEAD");
		this.NUMBER_OCCURRENCES = elem.getChildText("NUMBER_OCCURRENCES");
		Element listeOccTag = elem.getChild("LIST_OCCURRENCES");
		java.util.List listeOcc = listeOccTag.getChildren("OCCURRENCE");
		for(int i=0; i< listeOcc.size();i++){
			YateaOccurrence uneOc = new YateaOccurrence();
			uneOc.litXml((Element)listeOcc.get(i));
			this.LIST_OCCURRENCES.add(uneOc);
		}
		 
		this.TERM_CONFIDENCE = elem.getChildText("TERM_CONFIDENCE");
		if(elem.getChild("LIST_RELIABLE_ANCHORS")!=null){
			java.util.List  listeRA = elem.getChildren("RELIABLE_ANCHOR");
			for(int i=0; i<listeRA.size();i++){
				//a traiter
			}
		}
        this.LOG_INFORMATION = elem.getChildText("LOG_INFORMATION");
		if(elem.getChild("SYNTACTIC_ANALYSIS")!=null){
			// a traiter
		}
		if(elem.getChild("SYNTACTIC_ANALYSIS_COORD")!=null){
			// a traiter
		}
        
		
	}


	public String getFORM() {
		return FORM;
	}
	public void setFORM(String form) {
		FORM = form;
	}
	public String getID() {
		return ID;
	}
	public void setID(String id) {
		ID = id;
	}
	public String getLEMMA() {
		if (this.LEMMA == null){
			return "";
		}
		return this.LEMMA;
	}
	public void setLEMMA(String lemma) {
		LEMMA = lemma;
	}
	public String getCASE() {
		return CASE;
	}
	public void setCASE(String case1) {
		CASE = case1;
	}
	public String getDEGREE() {
		return DEGREE;
	}
	public void setDEGREE(String degree) {
		DEGREE = degree;
	}
	public String getFORMATION() {
		return FORMATION;
	}
	public void setFORMATION(String formation) {
		FORMATION = formation;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gender) {
		GENDER = gender;
	}
	public String getMOOD_VFORM() {
		return MOOD_VFORM;
	}
	public void setMOOD_VFORM(String mood_vform) {
		MOOD_VFORM = mood_vform;
	}
	public String getNUMBER() {
		return NUMBER;
	}
	public void setNUMBER(String number) {
		NUMBER = number;
	}
	public String getPERSON() {
		return PERSON;
	}
	public void setPERSON(String person) {
		PERSON = person;
	}
	public String getPOSSESSOR() {
		return POSSESSOR;
	}
	public void setPOSSESSOR(String possessor) {
		POSSESSOR = possessor;
	}
	public String getSYNTACTIC_CATEGORY() {
		return SYNTACTIC_CATEGORY;
	}
	public void setSYNTACTIC_CATEGORY(String syntactic_category) {
		SYNTACTIC_CATEGORY = syntactic_category;
	}
	public String getTENSE() {
		return TENSE;
	}
	public void setTENSE(String tense) {
		TENSE = tense;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String type) {
		TYPE = type;
	}
	public String getHEAD() {
		return HEAD;
	}
	public void setHEAD(String head) {
		HEAD = head;
	}
	public ArrayList<YateaOccurrence> getLIST_OCCURRENCES() {
		return LIST_OCCURRENCES;
	}
	public void setLIST_OCCURRENCES(ArrayList<YateaOccurrence> list_occurrences) {
		LIST_OCCURRENCES = list_occurrences;
		NUMBER_OCCURRENCES = ""+this.LIST_OCCURRENCES.size();
	}
	public void addLIST_OCCURRENCES(ArrayList<YateaOccurrence> list_occurrences){
		//System.err.println("trace ava "+this.NUMBER_OCCURRENCES+" ajout "+list_occurrences.size());
		this.LIST_OCCURRENCES.addAll(list_occurrences);
		this.NUMBER_OCCURRENCES = ""+this.LIST_OCCURRENCES.size();
		System.err.println("nb "+this.NUMBER_OCCURRENCES);
	}
	public String getNUMBER_OCCURRENCES() {
		return NUMBER_OCCURRENCES;
	}
	public void setNUMBER_OCCURRENCES(String number_occurrences) {
		NUMBER_OCCURRENCES = number_occurrences;
	}
	
    public String toString(){
		String st = "ID "+this.ID+"\n Form "+this.FORM+
		            "\n LEMMA "+this.LEMMA;
		return st;
    }
	public ArrayList<String> getListSentenceOccu(){
	//	System.err.println("taille "+this.LIST_OCCURRENCES.size());
		ArrayList<String> listSentOccu = new ArrayList<String>();
		for(Iterator<YateaOccurrence> i=LIST_OCCURRENCES.iterator();i.hasNext();){
			YateaOccurrence occ = (YateaOccurrence)i.next();
	//		System.err.println("sent "+occ.toString());
			listSentOccu.add(occ.getSENTENCE());
		}
		
		return listSentOccu;
	}
	public boolean equals(Object obj){
		if(obj == null) return false;
		if(!(obj instanceof YateaTerm)) return false;
		return this.ID.equals(((YateaTerm)obj).ID);
	}
	
	

	
	
	
	public ArrayList<String> getEnsTextOccu() {
		if(ensTextOccu == null){
			ensTextOccu = new ArrayList<String>();
			ArrayList<YateaOccurrence> vTexte = this.getLIST_OCCURRENCES();
			if (!vTexte.isEmpty()) {
				for (int i = 0; i < vTexte.size(); i++) {
					YateaOccurrence occu = vTexte.get(i);
					String occuT = occu.getTextOccu();
					ensTextOccu.add(occuT);
				}
			}
		}
		return ensTextOccu;
	}
	public void setEnsTextOccu(ArrayList<String> ensTextOccu) {
		this.ensTextOccu = ensTextOccu;
	}


	public int compareTo(YateaTerm obj){
		return this.getLEMMA().compareTo(obj.getLEMMA());
	}
}
