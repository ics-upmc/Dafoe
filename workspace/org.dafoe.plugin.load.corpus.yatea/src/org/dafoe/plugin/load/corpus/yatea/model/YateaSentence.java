package org.dafoe.plugin.load.corpus.yatea.model;
/**
 * 
 * @author sylvie Szulman
 * LIPN
 * Projet Dafoe
 * Aout 2009
 * It is a sentence as SENT treetagger
 * class for a sentence  
 */
import org.jdom.Element;


import java.util.*;
import java.io.*;

/**
 * 
 * @author sylvie
 *
 */
@SuppressWarnings("serial")
public class YateaSentence implements Serializable{
	private String ID;
	private ArrayList<String> phrase = null;// contains all the words of the sentence
	private ArrayList<String> ensLemmes = null; //contains all the lemma of the sentence
	public static ArrayList<YateaSentence> ensSentence = new ArrayList<YateaSentence>();
	
	public YateaSentence(String ID) {
		super();
		this.ID = ID;
		phrase = new ArrayList<String>();
		ensLemmes = new ArrayList<String>();
		ensSentence.add(this);
	}
	public YateaSentence(){
		phrase = new ArrayList<String>();
		ensLemmes = new ArrayList<String>();
		ensSentence.add(this);
	}
	
	public void litXml(Element elem){
		this.ID = elem.getAttributeValue("numero");
		
		List<Element> ensElem = elem.getChildren("DESC_MOT");
		
		for(Iterator<Element> i= ensElem.iterator();i.hasNext();){
			Element unElem = i.next();
			
			String lemme = unElem.getChildText("lemme");
			this.ensLemmes.add(lemme);
			//sentence building
			phrase.add(unElem.getChildText("mot"));
		}
	}
	
	public ArrayList<String> getEnsLemmes() {
		return ensLemmes;
	}
	public void setEnsLemmes(ArrayList<String> ensLemmes) {
		this.ensLemmes = ensLemmes;
	}

	public String getID() {
		return ID;
	}
	public void setID(String ID) {
		this.ID = ID;
	}
	public ArrayList<String> getPhrase() {
		return phrase;
	}
	public void setPhrase(ArrayList<String> phrase) {
		this.phrase = phrase;
	}
	/**
	 * compute the sent content from the set of words phrase
	 * @return
	 */
	public String getContent(){
		String content = "";
		for(String x:this.phrase){
			content=content+" "+x;
		}
		return content;
	}
	public static ArrayList<YateaSentence> getEnsSentence() {
		return ensSentence;
	}
	public static YateaSentence getUneSentence(String numero) {
		//sentence are numbered from 1
		return ensSentence.get(Integer.parseInt(numero)-1);
	}
	

}
