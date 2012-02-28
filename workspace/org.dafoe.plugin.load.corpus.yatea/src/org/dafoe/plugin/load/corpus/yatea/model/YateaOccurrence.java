package org.dafoe.plugin.load.corpus.yatea.model;
/**
 * 
 * @author sylvie Szulman
 * LIPN
 * Projet Dafoe
 * Aout 2009
 * class for a Yatea occurrence
 */
import org.jdom.Element;
import java.io.*;
@SuppressWarnings("serial")
public class YateaOccurrence implements Serializable, Comparable<YateaOccurrence>{

	/*
	 * <!ELEMENT  OCCURRENCE   (ID, DOC, SENTENCE, START_POSITION,
                         END_POSITION, OCCURRENCE_CONFIDENCE?)       >
	 */
	private String ID = null; //identifiant
	private String DOC = null; //Document identifier
	private String SENTENCE = null; //Sentence identifier 
	private String START_POSITION = null;  //Start position of the term 
	private String END_POSITION = null;    //End position of the term
	private String OCCURRENCE_CONFIDENCE = null; //Confidence of the occurrence of the term 
	//add text from corpus file
	private String textOccu = null;
	public YateaOccurrence() {
	}
	
	public YateaOccurrence(String ID, String DOC, String SENTENCE, String START_POSITION,
                         String END_POSITION, String OCCURRENCE_CONFIDENCE){
		this.ID = ID;
		this.DOC = DOC;
		this.SENTENCE = SENTENCE;
		this.START_POSITION = START_POSITION;
		this.END_POSITION = END_POSITION;
		this.OCCURRENCE_CONFIDENCE = OCCURRENCE_CONFIDENCE;
	}
	
	public YateaOccurrence(String ID, String DOC, String SENTENCE, String START_POSITION,
            String END_POSITION){
		this.ID = ID;
		this.DOC = DOC;
		this.SENTENCE = SENTENCE;
		this.START_POSITION = START_POSITION;
		this.END_POSITION = END_POSITION;
	}
    public void litXml(Element elem){
		this.ID = elem.getChildText("ID");
		this.DOC = elem.getChildText("DOC");
		
//		System.out.println("litXml - Occurrence DOC "+this.DOC);
		this.SENTENCE = elem.getChildText("SENTENCE");
		this.START_POSITION = elem.getChildText("START_POSITION");
		this.END_POSITION = elem.getChildText("END_POSITION");
		if(elem.getChild("OCCURRENCE_CONFIDENCE")!=null){
			this.OCCURRENCE_CONFIDENCE = elem.getChildText("OCCURRENCE_CONFIDENCE");
		}
		
		
    }
	
	public String getDOC() {
		return DOC;
	}

	public void setDOC(String doc) {
		DOC = doc;
	}

	public String getEND_POSITION() {
		return END_POSITION;
	}

	public void setEND_POSITION(String end_position) {
		END_POSITION = end_position;
	}

	public String getID() {
		return ID;
	}

	public void setID(String id) {
		ID = id;
	}

	public String getOCCURRENCE_CONFIDENCE() {
		return OCCURRENCE_CONFIDENCE;
	}

	public void setOCCURRENCE_CONFIDENCE(String occurrence_confidence) {
		OCCURRENCE_CONFIDENCE = occurrence_confidence;
	}

	public String getSENTENCE() {
		return SENTENCE;
	}

	public void setSENTENCE(String sentence) {
		SENTENCE = sentence;
	}

	public String getSTART_POSITION() {
		return START_POSITION;
	}

	public void setSTART_POSITION(String start_position) {
		START_POSITION = start_position;
	}
	public String toString(){
		return "ID "+this.ID+"\n DOC "+this.DOC+"\n SENTENCE "+this.SENTENCE+"\n START_POSITION "+this.START_POSITION+
		"\n END_POSITION "+this.END_POSITION;
	}

	public String getTextOccu() {
		//compute textOccu
		int deb = Integer.parseInt(this.START_POSITION);
		int fin = Integer.parseInt(this.END_POSITION);
		/*System.out.println("deb = " + deb);
		System.out.println("fin = " + fin);
		System.out.println("this.getSENTENCE() = " + this.getSENTENCE());
		System.out.println("YateaSentence.getUneSentence(this.getSENTENCE()) = " + YateaSentence.getUneSentence(this.getSENTENCE()));
		System.out.println("YateaSentence.getUneSentence(this.getSENTENCE()).getContent()" + YateaSentence.getUneSentence(this.getSENTENCE()).getContent());
		System.out.println("taille = " + YateaSentence.getUneSentence(this.getSENTENCE()).getContent().length());*/
		this.textOccu = YateaSentence.getUneSentence(this.getSENTENCE()).getContent().substring(deb,fin+1);
	
		System.out.println("this.textOccu = " + this.textOccu);
		return textOccu;
	}

	public void setTextOccu(String textOccu) {
		this.textOccu = textOccu;
	}
	public boolean equals(Object obj){
		if(obj == null )return false;
		if(!(obj instanceof YateaOccurrence)) return false;
		return this.DOC.equals(((YateaOccurrence)obj).DOC) &&
		       this.SENTENCE.equals(((YateaOccurrence)obj).SENTENCE) &&
		       this.START_POSITION.equals(((YateaOccurrence)obj).START_POSITION);
	}
	public int compareTo(YateaOccurrence oc){
		if(this.DOC.compareTo(oc.DOC) ==0){
			if(this.SENTENCE.compareTo(oc.SENTENCE)==0){
				return this.START_POSITION.compareTo(oc.START_POSITION);
			}
			else
			{
			return this.SENTENCE.compareTo(oc.SENTENCE);
			}
		}
		else
			return this.DOC.compareTo(oc.DOC);
	}

}
